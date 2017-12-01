package br.com.contability.business.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.contability.business.Categoria;
import br.com.contability.business.Conta;
import br.com.contability.business.Lancamento;
import br.com.contability.business.Lancamentos;
import br.com.contability.business.TipoDeCategoria;
import br.com.contability.business.TipoDeOpcoes;
import br.com.contability.business.Usuario;
import br.com.contability.business.services.CategoriaServices;
import br.com.contability.business.services.ContaServices;
import br.com.contability.business.services.LancamentoServices;
import br.com.contability.business.services.TrataParametrosServices;
import br.com.contability.comum.AuthenticationAbstract;
import br.com.contability.utilitario.CaixaDeFerramentas;

@Controller
@RequestMapping("/lancamento")
public class LancamentoResources {
	
	@Autowired
	private AuthenticationAbstract auth;

	@Autowired
	private CategoriaServices categoriaServices;

	@Autowired
	private ContaServices contaServices;

	@Autowired
	private LancamentoServices lancamentoServices;
	
	private List<Lancamento> listaLancamentos = new ArrayList<>();
	
	@Autowired
	private TrataParametrosServices parametrosServices;
	
	/*@Autowired
	private SaldoServices saldoServices;*/
	
	@GetMapping()
	public ResponseEntity<Void> novo() {
		
		Usuario usuario = auth.getAutenticacao();
		
		categoriaServices.seleciona(usuario)
		.stream().sorted(Comparator.comparing(Categoria::getDescricao)).collect(Collectors.toList());
		
		contaServices.seleciona(usuario);
		
		return ResponseEntity.noContent().build();

	}
	
	@GetMapping("/import")
	public ResponseEntity<Void> novoFileImport(@RequestBody Lancamento lancamento) {

		auth.getAutenticacao();
		
		return ResponseEntity.noContent().build();
		
	}
	
	@PostMapping("/import") // REQUESTBODY DARIA NA MESMA. HEHEHHEHE
	public ResponseEntity<Void> gravaFileImport(@RequestBody Lancamento lancamento,
			@RequestParam(value = "file", required = false) MultipartFile file, RedirectAttributes attributes) {
		
		Usuario usuario = auth.getAutenticacao();
		
		List<Lancamento> lancamentosPlanilha = lancamentoServices.configuraPlanilha(file);
		
		Lancamentos lancamentos = new Lancamentos();
		lancamentos.setLancamentos(lancamentosPlanilha);
		
		categoriaServices.seleciona(usuario);
		
		return ResponseEntity.noContent().build();

	}
	
	@PostMapping("/importlancamentos")
	public ResponseEntity<Void> gravaLancamentoFileImport(@RequestBody Lancamentos lancamentos) {
		
		Usuario usuario = auth.getAutenticacao();
		
		lancamentoServices.gravaImportacao(usuario, lancamentos);
		
		return ResponseEntity.noContent().build();

	}
	
	@RequestMapping(value = "/arquivoexemplo", method = RequestMethod.GET, produces = {MediaType.ALL_VALUE})
	public @ResponseBody void downloadA(HttpServletResponse response) throws IOException {
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("fileExcel/exemplo.xls").getFile());
		
	    InputStream in = new FileInputStream(file);
	    response.setContentType(MediaType.ALL_VALUE);
	    response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
	    response.setHeader("Content-Length", String.valueOf(file.length()));
	    FileCopyUtils.copy(in, response.getOutputStream());
	}

	@GetMapping("/{idLancamento}")
	public ResponseEntity<Void> get(@PathVariable Object idLancamento, Model model) {

		Usuario usuario = auth.getAutenticacao();
		
		lancamentoServices.getLancamento(usuario, idLancamento);
		
		return ResponseEntity.noContent().build();

	}

	@PostMapping
	public ResponseEntity<Void> salvar(@Valid Lancamento lancamento, BindingResult result, RedirectAttributes attributes,
			Model model, HttpSession session) {
		
		Usuario usuario = auth.getAutenticacao();
		
		lancamentoServices.grava(lancamento, usuario, session);

		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/saldo")
	public ResponseEntity<Double> getSaldo() {
		
		auth.getAutenticacao();
		
		BigDecimal saldo = lancamentoServices.getSaldo(listaLancamentos);
		
		return ResponseEntity.ok(saldo.doubleValue());
	}
	
	@PostMapping("/lancamentoOuDepositoProximoMes")
	public ResponseEntity<Void> lancamentoOuDepositoProximoMes(@RequestParam String date, @RequestParam TipoDeOpcoes opcao,
			@RequestParam(required = false) Conta conta, @RequestParam String valor) {
		
		Usuario usuario = auth.getAutenticacao();
		
		lancamentoServices.gravaLancamentoProximoMesOuDeposito(date, conta, opcao, valor, usuario);
		
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/lista")
	public ResponseEntity<Void> lista(Model model, Lancamento lancamento) {
		
		Usuario usuario = auth.getAutenticacao();


		contaServices.selecionaComOpcaoTodas(usuario);

		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/vencidos")
	public ResponseEntity<Void> vencidos() {
		
		Usuario usuario = auth.getAutenticacao();

		contaServices.selecionaComOpcaoTodas(usuario);

		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/vencidosComConta")
	public ResponseEntity<List<LocalDate>> vencidosComConta(@RequestParam Object conta){
		
		Usuario usuario = auth.getAutenticacao();
		
		List<Lancamento> lancamentosVencidos = lancamentoServices
				.selecionaVencidosAnteriorA(usuario, LocalDate.now(), conta);
		
		return ResponseEntity.ok(
				lancamentosVencidos.stream().map(l -> l.getDataHoraLancamento())
				.distinct().collect(Collectors.toList()));
	}
	
	@PostMapping("/tabela")
	public ResponseEntity<Void> mostraTabelaCadastrados(@RequestParam String date, @RequestParam Object conta,
			@RequestParam(value = "mobile", required = false) String mobile) {
		
		Usuario usuario = auth.getAutenticacao();
		
		LocalDate localDate = CaixaDeFerramentas.calendarFromStringMesAnoDate(date);
		
		listaLancamentos = lancamentoServices.seleciona(usuario, localDate, conta);
		
		
		BigDecimal saldo = lancamentoServices.getSaldo(listaLancamentos);
		
		BigDecimal saldoProvavel = lancamentoServices.getSaldoProvavel(listaLancamentos);
		
		List<Lancamento> listaOrganizada = lancamentoServices.listaPorCategoriaDataDescricao(listaLancamentos);
		
		List<Lancamento> lancamentosOrdenadosAndMesAtual = listaOrganizada.stream()
				/*.sorted(com) FICA NO SERVICES AGORA /* Comparator.comparing(Lancamento::getDataHoraLancamento).thenComparing(Lancamento::getDescricao) */
				.filter(l -> l.getDataHoraLancamento().getMonth() == localDate.getMonth())
				.collect(Collectors.toList());
		
		Double totalDebitos = lancamentosOrdenadosAndMesAtual.stream().mapToDouble(
				l -> l.getCategoria().getTipoDeCategoria() == TipoDeCategoria.DESPESA
				? l.getValorLancamento().doubleValue() 
				: 0.00).sum();
		
		Double totalReceitas = lancamentosOrdenadosAndMesAtual.stream().mapToDouble(
				l -> l.getCategoria().getTipoDeCategoria() == TipoDeCategoria.RECEITA
				? l.getValorLancamento().doubleValue() 
				: 0.00).sum();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		/*return mobile == null ? "lancamento/Tabela :: tabelaLancamento"
				: "lancamento/TabelaMobile :: tabelaLancamentoMobile";*/
		
		return ResponseEntity.noContent().build();

	}
	
//	private Conta contaUsuario = null;
	@GetMapping("/tabelaVencidos")
	public ResponseEntity<Void> mostraTabelaVencidos(@RequestParam("dataVencido") String calendarString,
			@RequestParam Object conta, @RequestParam(value = "mobile", required = false) String mobile) {
		
		// contaUsuario = null; // NECESSÁRIO DEVIDO AO ATRIBUTO ESTAR DECLARADO NA CLASSE E NÃO NO MÉTODO.
		Usuario usuario = auth.getAutenticacao();
		
		LocalDate localDate = CaixaDeFerramentas.calendarFromStringDiaMesAnoDate(calendarString);
		
		List<Lancamento> listaLancamentos = lancamentoServices.selecionaVencidosDa(usuario, localDate, conta);
		
		Long idConta = parametrosServices.trataParametroLongException(conta);
		
		/*if (idConta != 0)
			contaUsuario = contaServices.get(idConta, null);*/
		
		/*model.addAttribute("lancamentos", contaUsuario == null ? listaLancamentos :
			listaLancamentos.stream().filter(l -> l.getConta() == contaUsuario).collect(Collectors.toList()));
		model.addAttribute("total", listaLancamentos == null ? null : listaLancamentos.stream()
				.map(l -> l.getValorLancamento()).reduce(BigDecimal.ZERO, BigDecimal::add));
		
		return mobile == null ? "lancamento/Tabela :: tabelaLancamento"
				: "lancamento/TabelaMobile :: tabelaLancamentoMobile";*/
		
		return ResponseEntity.noContent().build();

	}
	
	@DeleteMapping("/remover/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id){
		
		Usuario usuario = auth.getAutenticacao();
		
		lancamentoServices.remove(usuario, id);
		
		return ResponseEntity.ok().build();
	}

}
