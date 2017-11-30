package br.com.contability.business.services;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.contability.business.Categoria;
import br.com.contability.business.Conta;
import br.com.contability.business.Lancamento;
import br.com.contability.business.Lancamentos;
import br.com.contability.business.TipoDeCategoria;
import br.com.contability.business.TipoDeOpcoes;
import br.com.contability.business.Usuario;
import br.com.contability.business.repository.LancamentoRepository;
import br.com.contability.comum.IServices;
import br.com.contability.comum.ServicesAbstract;
import br.com.contability.comum.SessaoServices;
import br.com.contability.exceptions.ObjetoInexistenteException;
import br.com.contability.exceptions.ObjetoInexistenteExceptionMessage;
import br.com.contability.utilitario.CaixaDeFerramentas;

@Service
public class LancamentoServices extends ServicesAbstract<Lancamento, LancamentoRepository>
		implements IServices<Lancamento> {

	@Autowired
	private ContaServices contaServices;

	@Autowired
	private CategoriaServices categoriaServices;

	@Autowired
	private ConfiguraArquivosServices arquivosServices;

	private LeitorPlanilhaStrategy<Lancamento, LancamentoServices> leitorPlanilhaExcel = new LeitorPlanilhasExcel<>(
			this);

	private LeitorPlanilhaStrategy<Lancamento, LancamentoServices> leitorPlanilhaLibreOffice = new LeitorPlanilhasLibreOffice<>(
			this);

	/*@Autowired
	private SaldoFacade saldoFacade;*/

	@Autowired
	private SessaoServices sessionServices;

	@Autowired
	private TrataParametrosServices parametroServices;

	/**
	 * @param lancamento
	 * @param usuario
	 */
	public void grava(Lancamento lancamento, Usuario usuario, HttpSession session) {

		lancamento.setUsuario(usuario);

		setaValorLancamento(lancamento);

		if (lancamento.getId() == null) {
			gravaLancamento(lancamento);
		} else {
			atualizaLancamento(lancamento, session);
		}

	}

	public void gravaLancamentoProximoMesOuDeposito(String date, Conta conta,
			TipoDeOpcoes opcao, String valor, Usuario usuario) {
		
		BigDecimal valorLancamento = CaixaDeFerramentas.converteStringToBidDecimal(valor);
		
		/*if (opcao.equals(TipoDeOpcoes.LANCAMENTO_PROXIMO_MES)) { // PODERIA CRIAR INTERFACE NO ENUM E VINCULAR DIRETO MAS NÃO COMPENSA
			
			LocalDateTime dataCorrigida = getDataFormatada(date);
			
			Categoria categoriaProximoMes = categoriaServices.categoriaProximoMes(usuario, dataCorrigida);
			Lancamento lancamento = criaLancamento(conta, valorLancamento, dataCorrigida, categoriaProximoMes);
			gravaLancamento(lancamento);
			
		} else */if (opcao.equals(TipoDeOpcoes.DEPOSITO_CONTA)) {
			
			verificaConta(conta);
			
			conta.setUsuario(usuario);
			
			LocalDateTime localDateTime = getDateTimeFromDate(date);
			Categoria categoriaDeposito = categoriaServices.categoriaDeposito(conta.getUsuario(), localDateTime);
			Lancamento lancamento = criaLancamento(conta, valorLancamento, localDateTime, categoriaDeposito);
			gravaLancamento(lancamento);
		}
		

	}

	private void verificaConta(Conta conta) {
		
		if (conta == null)
			throw new ObjetoInexistenteExceptionMessage("/lancamento/lista", "Conta não vinculada");
		
	}

	/*private LocalDateTime getDataFormatada(String date) {
		LocalDateTime localDateTime = getDateTimeFromDate(date);
		LocalDateTime dataCorrigida = localDateTime.plusMonths(1).withDayOfMonth(1);
		return dataCorrigida;
	}*/

	private LocalDateTime getDateTimeFromDate(String date) {
		
		LocalDate localDate = CaixaDeFerramentas.calendarFromStringMesAnoDate(date);
		return LocalDateTime.of(localDate, LocalTime.now());
	}

	/**
	 * @param conta
	 * @param valor
	 * @param usuario
	 * @param localDateTime
	 */
	private Lancamento criaLancamento(Conta conta, BigDecimal valor, LocalDateTime localDateTime, Categoria categoria) {
		
		Lancamento lancamento = new Lancamento();
		lancamento.setDataHoraCadastro(localDateTime);
		lancamento.setDataHoraLancamento(localDateTime.toLocalDate());
		lancamento.setConta(conta == null ? null : conta);
		lancamento.setCategoria(categoriaServices.categoriaDeposito(categoria.getUsuario(), localDateTime));
		lancamento.setDescricao(categoria.getDescricao() == "Deposito" ? "Deposito" : "Lancamento referente mês passado");
		lancamento.setUsuario(categoria.getUsuario());
		lancamento.setValorLancamento(valor);
		
		return lancamento;
	}

	private void atualizaLancamento(Lancamento lancamento, HttpSession session) {

		if (super.atualiza(lancamento, null) != null) {
//			saldoFacade.atualizaSaldoUsuario(lancamento);
			if (lancamento.getDataHoraVencimento() != null &&
					lancamento.getDataHoraVencimento().isBefore(LocalDate.now())) {
				sessionServices.atualizaVencidos(session,
						this.selecionaVencidosAnteriorA(lancamento.getUsuario(), LocalDate.now(), null));
			}
		}
		

	}

	private void gravaLancamento(Lancamento lancamento) {

		if (lancamento.getCategoria().getTipoDeCategoria() == TipoDeCategoria.DESPESA && lancamento.isParcelado()) {

			insereLancamentoParcelado(lancamento);

		} else {

			lancamento.setParcelado(false);
			lancamento.setParcelas(null);
			lancamento.setDataHoraVencimento(null);
			insereLancamento(lancamento);

		}

	}

	private void setaValorLancamento(Lancamento lancamento) {

		BigDecimal bigDecimal = CaixaDeFerramentas.converteStringToBidDecimal(lancamento.getValorConversao());

		lancamento.setValorLancamento(bigDecimal);

	}

	/**
	 * @param lancamento
	 * @param usuario
	 */
	private void insereLancamentoParcelado(Lancamento lancamento) {

		int quantidadeParcelas = lancamento.getParcelas();

		defineValorParcelado(lancamento, quantidadeParcelas);

		for (int i = 0; i < quantidadeParcelas; i++) {

			Lancamento lancamentoParcela = clonaLancamento(lancamento);

			// LocalDate dataLancamento = lancamentoParcela.getDataHoraLancamento();
			LocalDate dataVencimento = lancamentoParcela.getDataHoraVencimento();

			// LocalDate dataLancamentoPlus = dataLancamento.plusMonths(i);
			LocalDate dataVencimentoPlus = dataVencimento.plusMonths(i);

			lancamentoParcela.setParcelas(i + 1);
			lancamentoParcela.setDataHoraLancamento(dataVencimentoPlus);
			lancamentoParcela.setDataHoraVencimento(dataVencimentoPlus);

			insereLancamento(lancamentoParcela);

		}
	}

	private void defineValorParcelado(Lancamento lancamento, int quantidadeParcelas) {

		BigDecimal valorParcelado = lancamento.getValorLancamento()
				.divide(new BigDecimal(new String("" + quantidadeParcelas)), 2, BigDecimal.ROUND_HALF_UP);

		lancamento.setValorLancamento(valorParcelado);

	}

	private void insereLancamento(Lancamento lancamento) {

		super.insere(lancamento, null);
//			saldoFacade.atualizaSaldoUsuario(lancamento);

	}

	private Lancamento clonaLancamento(Lancamento lancamento) {

		Lancamento lancamentoClone = new Lancamento();
		lancamentoClone.setUsuario(lancamento.getUsuario());
		lancamentoClone.setCategoria(lancamento.getCategoria() == null ? null : lancamento.getCategoria());
		lancamentoClone.setConta(lancamento.getConta() == null ? null : lancamento.getConta());
		lancamentoClone.setDescricao(lancamento.getDescricao());

		lancamentoClone.setDataHoraCadastro(
				lancamento.getDataHoraCadastro() == null ? null : lancamento.getDataHoraCadastro());
		lancamentoClone.setDataHoraAtualizacao(
				lancamento.getDataHoraAtualizacao() == null ? null : lancamento.getDataHoraAtualizacao());
		lancamentoClone.setDataHoraVencimento(
				lancamento.getDataHoraVencimento() == null ? null : lancamento.getDataHoraVencimento());
		lancamentoClone.setDataHoraLancamento(
				lancamento.getDataHoraLancamento() == null ? null : lancamento.getDataHoraLancamento());

		lancamentoClone.setValorLancamento(lancamento.getValorLancamento());
		lancamentoClone.setPago(lancamento.isPago());
		lancamentoClone.setParcelado(lancamento.isParcelado());

		return lancamentoClone;
	}

	/**
	 * @param usuario
	 * @param calendar
	 * @return
	 */
	public List<Lancamento> seleciona(Usuario usuario, LocalDate localDate, Object id) {
		
		Long idConta = parametroServices.trataParametroLongException(id);
		
		verificaContaAoUsuario(usuario, idConta);
		
		return super.getJpa().selecionaLancamentos(usuario.getId(), idConta, localDate.getMonthValue(), localDate.getYear());
	}

	private void verificaContaAoUsuario(Usuario usuario, Long idConta) {
		
		if (idConta != 0)
			contaServices.getContaPelo(usuario, idConta);
		
	}

	/**
	 * @param usuario
	 * @param calendar
	 * @return
	 */
	public List<Lancamento> selecionaVencidosDa(Usuario usuario, LocalDate localDate, Object conta) {
		
		Long idConta = parametroServices.trataParametroLongException(conta);
		
		verificaContaAoUsuario(usuario, idConta);
		
		return super.getJpa().selecionaVencidosDa(usuario.getId(), localDate);
	}

	/**
	 * @param usuario
	 * @param localDate
	 * @return
	 */
	public List<Lancamento> selecionaVencidosAnteriorA(Usuario usuario, LocalDate localDate, Object id) {
		
		if (id == null) {
			return super.getJpa().selecionaVencidosTodasContas(usuario.getId(), localDate);
		}
		
		Long idConta = parametroServices.trataParametroLongException(id);
		
		verificaContaAoUsuario(usuario, idConta);
		
		return super.getJpa().selecionaVencidos(usuario.getId(), localDate, idConta);
	}

	/**
	 * @param usuario
	 * @param dataHoraLancamento
	 * @return saldo
	 */
	/*public BigDecimal getSaldo(Usuario usuario, LocalDate localDate) {
		return super.getJpa().getSaldo(usuario.getId(), localDate.getMonthValue(), localDate.getYear());
	}*/

	/**
	 * @param usuario
	 * @param localDate
	 * @return
	 */
	/*public BigDecimal getSaldoProvavel(Usuario usuario, LocalDate localDate) {
		return super.getJpa().getSaldoProvavel(usuario.getId(), localDate.getMonthValue(), localDate.getYear());
	}*/

	/*
	 * FAZENDO COM STREAM (PARTICULAMENTE ACHO MUITA COISA KKKKK) FUNCIONA
	 * PERFEITAMENTE // TESTE SALDO Usuario usuario = auth.getAutenticacao();
	 * List<Lancamento> lancamentos = lancamentoServices.getJpa()
	 * .selecionaLancamentosPuroStream(usuario.getId(),
	 * calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)); Double saldo =
	 * lancamentos.stream().mapToDouble(f -> f.getCategoria().getTipoDeCategoria()
	 * == TipoDeCategoria.RECEITA ? f.getValorLancamento().doubleValue() :
	 * (-f.getValorLancamento().doubleValue())).sum();
	 * 
	 * System.out.println(saldo);
	 */

	/**
	 * @param usuario
	 * @param mv
	 * @param idLancamento
	 * @param model
	 * @return mv
	 */
	public ModelAndView getLancamento(Usuario usuario, ModelAndView mv, Object id) {

		Long idLancamento = parametroServices.trataParametroLongMessage(id, "/lancamento");

		Optional<Lancamento> lancamento = super.getJpa().getLancamento(usuario.getId(), idLancamento);

		lancamento.orElseThrow(() -> new ObjetoInexistenteExceptionMessage("/lancamento", "Lançamento não encontrado"));

		lancamento.get().setValorConversao(lancamento.get().getValorLancamento().toString());

		mv.addObject("lancamento", lancamento.get());
		mv.addObject("categorias", categoriaServices.getPeloLancamento(lancamento.get().getId()));
		mv.addObject("contas", contaServices.getPeloLancamento(lancamento.get().getId()));

		return mv;

	}

	/**
	 * @param usuario
	 * @param id
	 */
	public void remove(Usuario usuario, Long lancamentoId) {

		if (lancamentoId == null || confirmaVinculo(usuario, lancamentoId))
			throw new ObjetoInexistenteException("Impossível encontrar o lançamento selecionado");


		super.remove(lancamentoId, null);
//		Lancamento lancamento = super.get(lancamentoId, null);
//		saldoFacade.atualizaSaldoUsuario(lancamento);

	}

	private boolean confirmaVinculo(Usuario usuario, Long lancamentoId) {

		Optional<Lancamento> lancamento = super.getJpa().getLancamento(usuario.getId(), lancamentoId);

		return !lancamento.isPresent();
	}

	public List<Lancamento> selecionaLancamentosAnoAtual(Usuario usuario) {

		return super.getJpa().selecionaLancamentosAnoAtual(usuario.getId());

	}

	/**
	 * @param multipartFile
	 * @return
	 */
	public List<Lancamento> configuraPlanilha(MultipartFile multipartFile) {

		File file = arquivosServices.configuraArquivo(multipartFile);

		String nomeArquivo = file.getName();

		String extensao = nomeArquivo.substring(nomeArquivo.lastIndexOf(".") + 1, nomeArquivo.length());

		return extensao.equals("ods") ? leitorPlanilhaLibreOffice.configuraFileToObject(file)
				: leitorPlanilhaExcel.configuraFileToObject(file);

	}

	/**
	 * @param usuario
	 * @param lancamentos
	 */
	public void gravaImportacao(Usuario usuario, Lancamentos lancamentoImportacao) {

		List<Lancamento> lancamentos = lancamentoImportacao.getLancamentos();

		verificaInconsistencias(lancamentos);

		retiraSinaisNegativos(lancamentos);

		setaUsuarios(lancamentos, usuario);

		lancamentos.forEach(l -> System.out.println("valor: " + l.getValorLancamento()));

		lancamentos.forEach(l -> gravaLancamento(l));
	}

	/**
	 * @param lancamentos
	 * @param usuario
	 */
	private void setaUsuarios(List<Lancamento> lancamentos, Usuario usuario) {

		lancamentos.forEach(l -> l.setUsuario(usuario));

	}

	private void retiraSinaisNegativos(List<Lancamento> lancamentos) {

		lancamentos.forEach(l -> {

			BigDecimal valor = l.getValorLancamento();

			BigDecimal valorCorrigido = CaixaDeFerramentas.alteraValorParaPositivo(valor);
			l.setValorLancamento(valorCorrigido);

		});

	}

	/**
	 * @param lancamentos
	 */
	private void verificaInconsistencias(List<Lancamento> lancamentos) {

		lancamentos.forEach(l -> {

			verificaData(l);

			verificaDescricao(l);

			verificaCategoria(l);

			verificaValorLancamento(l);

		});

	}

	private void verificaData(Lancamento l) {
		if (l.getCategoria() == null)
			throw new ObjetoInexistenteExceptionMessage("/import", "Data não informada.");
	}

	private void verificaDescricao(Lancamento l) {
		if (l.getCategoria() == null)
			throw new ObjetoInexistenteExceptionMessage("/import", "Descrição não informada.");
	}

	private void verificaCategoria(Lancamento l) {
		if (l.getCategoria() == null)
			throw new ObjetoInexistenteExceptionMessage("/import", "Categoria não vinculada.");
	}

	private void verificaValorLancamento(Lancamento l) {
		if (l.getCategoria() == null)
			throw new ObjetoInexistenteExceptionMessage("/import", "Valor não informado.");
	}

	@Override
	public void preencheObjetoPlanilhaExcel(List<Lancamento> objetos, Iterator<Row> iteratorRow) {

		while (iteratorRow.hasNext()) {

			Row nextRow = (Row) iteratorRow.next();

			if (pulaLinhaSeNecessario(nextRow.getRowNum()))
				continue;

			Iterator<Cell> cellIterator = nextRow.iterator();

			Lancamento objeto = new Lancamento();

			while (cellIterator.hasNext()) {

				Cell nextCell = (Cell) cellIterator.next();

				int columnIndex = nextCell.getColumnIndex();

				switch (columnIndex) {

				case 0:

					Calendar date = DateUtil.getJavaCalendar((double) getCellValue(nextCell));
					objeto.setDataHoraLancamento(CaixaDeFerramentas.converteDateToLocalDate(date.getTime()));
					break;

				case 1:
					objeto.setDescricao((String) getCellValue(nextCell));
					break;

				case 2:

					BigDecimal valorLancamento = configuraValorLancamento(nextCell);
					objeto.setValorLancamento(valorLancamento);
					break;

				default:
					break;
				}
			}

			objetos.add(objeto);

		}
	}

	/**
	 * @param nextRow
	 * @return
	 */
	private boolean pulaLinhaSeNecessario(int nextRow) {
		return nextRow == 0;
	}

	/**
	 * @param cell
	 * @return
	 */
	public Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();

		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();

		case Cell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();
		}

		return null;
	}

	/**
	 * @param nextCell
	 * @return
	 */
	private BigDecimal configuraValorLancamento(Cell nextCell) {

		Double valorExcel = (Double) getCellValue(nextCell);

		BigDecimal valorLancamento = BigDecimal.valueOf(valorExcel);
		return valorLancamento;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void preencheObjetoPlanilhaLibreOffice(List<Lancamento> objetos, Sheet sheet) {

		// Get row count and column count
		int nColCount = sheet.getColumnCount();
		int nRowCount = sheet.getRowCount();

		// ITERANDO EM CADA LINHA
		MutableCell cell = null;

		for (int nRowIndex = 0; nRowIndex < nRowCount; nRowIndex++) {

			if (pulaLinhaSeNecessario(nRowIndex))
				continue;

			Lancamento lancamento = new Lancamento();

			// ITERANDO SOBRE CADA COLUNA
			int nColIndex = 0;

			for (; nColIndex < nColCount; nColIndex++) {

				cell = sheet.getCellAt(nColIndex, nRowIndex);

				alimentaValor(nColIndex, lancamento, cell);

			}

			objetos.add(lancamento);
		}

	}

	/**
	 * @param nColIndex
	 * @param lancamento
	 */
	@SuppressWarnings("rawtypes")
	private void alimentaValor(int nColIndex, Lancamento lancamento, MutableCell cell) {

		switch (nColIndex) {

		case 0:

			lancamento
					.setDataHoraLancamento(CaixaDeFerramentas.stringToLocalDateLibreOffice(cell.getValue().toString()));

			break;

		case 1:

			lancamento.setDescricao(cell.getValue().toString());

			break;

		case 2:

			lancamento.setValorLancamento(new BigDecimal(cell.getValue().toString()));

			break;

		default:
			break;
		}

	}

	public BigDecimal getSaldo(List<Lancamento> listaLancamentos) {
		
		Double saldo = listaLancamentos.stream().mapToDouble(
				l -> l.getCategoria().getTipoDeCategoria() == TipoDeCategoria.RECEITA
				? l.getValorLancamento().doubleValue()
						: l.getCategoria().getTipoDeCategoria() == TipoDeCategoria.DESPESA && l.isPago()
						? (-l.getValorLancamento().doubleValue()) : 0 ).sum();
		
		return new BigDecimal(saldo);
	}

	public BigDecimal getSaldoProvavel(List<Lancamento> listaLancamentos) {
		
		Double saldo = listaLancamentos.stream().mapToDouble(
				l -> l.getCategoria().getTipoDeCategoria() == TipoDeCategoria.RECEITA
				? l.getValorLancamento().doubleValue()
						: l.getCategoria().getTipoDeCategoria() == TipoDeCategoria.DESPESA && (!l.isPago()
						|| l.isPago())
						? (-l.getValorLancamento().doubleValue()) : 0 ).sum();
		
		return new BigDecimal(saldo);
		
	}

	public List<Lancamento> listaPorCategoriaDataDescricao(List<Lancamento> listaLancamentos) {
		
		// FAÇO O COMPARATOR LEVANDO EM CONSIDERAÇÃO A CATEGORIA, DATA_HORA E POR ULTIMO A DESCRIÇÃO.
		Comparator<Lancamento> com = (l1, l2) -> {
			
			int categoria = l2.getCategoria().getTipoDeCategoria().compareTo(l1.getCategoria().getTipoDeCategoria());
			int dataHora = l1.getDataHoraLancamento().compareTo(l2.getDataHoraLancamento());
			int descricao = l1.getDescricao().compareTo(l2.getDescricao());
			
			if (categoria != 0) {
				return categoria;
			} else if (dataHora != 0) {
				return dataHora;
			} else if (descricao != 0) {
				return descricao;
			} else {
				return 0;
			}
			
		};
		
		return listaLancamentos.stream().sorted(com).collect(Collectors.toList());
	}

}
