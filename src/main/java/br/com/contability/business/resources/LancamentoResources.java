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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
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
import br.com.contability.comum.ModelConstruct;
import br.com.contability.comum.StringPaginasAndRedirect;
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
	public ModelAndView novo(Model model, Lancamento lancamento) {
		ModelConstruct.setAttributes(model, "activeLiLancamento", "activeNovo");
		
		Usuario usuario = auth.getAutenticacao();
		
		ModelAndView mv = new ModelAndView("lancamento/Lancamento");
		mv.addObject("categorias", categoriaServices.seleciona(usuario)
				.stream().sorted(Comparator.comparing(Categoria::getDescricao)).collect(Collectors.toList()));
		mv.addObject("contas", contaServices.seleciona(usuario));

		return mv;

	}
	
	@GetMapping("/import")
	public ModelAndView novoFileImport(Model model, Lancamento lancamento) {
		ModelConstruct.setAttributes(model, "activeLiLancamento", "activeNovoImport");
		
		auth.getAutenticacao();
		
		ModelAndView mv = new ModelAndView("lancamento/LancamentoImportFile");

		return mv;

	}
	
	@PostMapping("/import") // REQUESTBODY DARIA NA MESMA. HEHEHHEHE
	public ModelAndView gravaFileImport(Model model, Lancamento lancamento,
			@RequestParam(value = "file", required = false) MultipartFile file, RedirectAttributes attributes) {
		ModelConstruct.setAttributes(model, "activeLiLancamento", "activeNovoImport");
		
		Usuario usuario = auth.getAutenticacao();
		
		List<Lancamento> lancamentosPlanilha = lancamentoServices.configuraPlanilha(file);
		
		Lancamentos lancamentos = new Lancamentos();
		lancamentos.setLancamentos(lancamentosPlanilha);
		
		ModelAndView mv = new ModelAndView("lancamento/LancamentoImportFile");
		mv.addObject("lancamentos", lancamentos);
		mv.addObject("categorias", categoriaServices.seleciona(usuario));
		
		return mv;

	}
	
	/*@InitBinder
	private void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");//edit for the    format you need
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}*/
	
	@PostMapping("/importlancamentos") // REQUESTBODY DARIA NA MESMA. HEHEHHEHE
	public ModelAndView gravaLancamentoFileImport(Model model, Lancamentos lancamentos,
			RedirectAttributes attributes) {
		
		ModelConstruct.setAttributes(model, "activeLiLancamento", "activeNovoImport");
		
		Usuario usuario = auth.getAutenticacao();
		
		lancamentoServices.gravaImportacao(usuario, lancamentos);
		
		
		attributes.addFlashAttribute("mensagem", "Lancamentos gravados com sucesso");
		return new ModelAndView(StringPaginasAndRedirect.LANCAMENTO_IMPORT);

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
	
	/* DOWNLOAD EXIBINDO O ARQUIVO BAIXADO
	 * 
	 * OBS: FORMA MAIS ANTIGA
	 * 
	 * @GetMapping(value = "/arquivoexemplo", produces = "application/pdf")
	public @ResponseBody HttpEntity<byte[]> baixaArquivoExemploExcel() {
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("fileExcel/boleto.pdf").getFile());
		
	    byte[] document = null;
	    
		try {
			document = FileCopyUtils.copyToByteArray(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(new MediaType("application", "pdf"));
	    header.set("Content-Disposition", "inline; filename=" + file.getName());
	    header.setContentLength(document.length);
	    
	    return new HttpEntity<byte[]>(document, header);
	}*/
	
	/* DOWNLOAD EXIBINDO O ARQUIVO BAIXADO
	 * 
	 * OBS: FORMA MAIS ATUAL DE FAZER
	 * 
	 * @GetMapping(value = "/arquivoexemplo", produces = "application/pdf")
	@ResponseBody
	public Resource baixaArquivoExemploExcel(HttpServletResponse response) {
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("fileExcel/boleto.pdf").getFile());
		
	    response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "inline; filename=" + file.getName());
	    response.setHeader("Content-Length", String.valueOf(file.length()));
		
		System.out.println("teste");
		return new FileSystemResource(file);
	}*/

	@GetMapping("/{idLancamento}")
	public ModelAndView get(@PathVariable Object idLancamento, Model model) {
		ModelConstruct.setAttributes(model, "activeLiLancamento", "activeNovo");

		Usuario usuario = auth.getAutenticacao();
		
		ModelAndView mv = new ModelAndView("lancamento/Lancamento");

		return lancamentoServices.getLancamento(usuario, mv, idLancamento);

	}

	@PostMapping
	public ModelAndView salvar(@Valid Lancamento lancamento, BindingResult result, RedirectAttributes attributes,
			Model model, HttpSession session) {
		
		Usuario usuario = auth.getAutenticacao();
		
		if (result.hasErrors())
			return novo(model, lancamento);
		
		lancamentoServices.grava(lancamento, usuario, session);

		attributes.addFlashAttribute("mensagem", "Lancamento gravado com sucesso");
		return new ModelAndView(StringPaginasAndRedirect.LANCAMENTO);
	}
	
	@GetMapping("/saldo")
	public ResponseEntity<Double> getSaldo() {
		
		auth.getAutenticacao();
		
		BigDecimal saldo = lancamentoServices.getSaldo(listaLancamentos);
		
		return ResponseEntity.ok(saldo.doubleValue());
	}
	
	@PostMapping("/lancamentoOuDepositoProximoMes")
	public ModelAndView lancamentoOuDepositoProximoMes(@RequestParam String date, @RequestParam TipoDeOpcoes opcao,
			@RequestParam(required = false) Conta conta, @RequestParam String valor) {
		
		Usuario usuario = auth.getAutenticacao();
		
		lancamentoServices.gravaLancamentoProximoMesOuDeposito(date, conta, opcao, valor, usuario);
		
		return new ModelAndView("redirect:/lancamento/lista");
	}

	@GetMapping("/lista")
	public ModelAndView lista(Model model, Lancamento lancamento) {
		
		Usuario usuario = auth.getAutenticacao();

		ModelConstruct.setAttributes(model, "activeLiLancamento", "activeListagem");

		ModelAndView mv = new ModelAndView("lancamento/Listagem");
		mv.addObject("contas", contaServices.selecionaComOpcaoTodas(usuario));
		mv.addObject("opcoes", TipoDeOpcoes.values());

		return mv;
	}
	
	@GetMapping("/vencidos")
	public ModelAndView vencidos(Model model, Lancamento lancamento) {
		
		Usuario usuario = auth.getAutenticacao();

		ModelConstruct.setAttributes(model, "activeLiLancamento", "activeListagemVencidos");
		
		/* ISSO FOI ALTERADO DEVIDO A EU NÃO PRECISAR MAIS DO OBJETO INTEIRO, E SIM APENAS DA DATA,
		 * CONSEQUENTEMENTE CONSIGO FAZER COM O MAP NORMAL.
		 * 
		 * COM O FILTER NORMAL ESTÁ NO MÉTODO ABAIXO.
		 * 
		 * List<Lancamento> lancamentosVencidos = lancamentoServices.selecionaVencidosAnteriorA(usuario, LocalDate.now());
		
		// Function<Lancamento, LocalDate> teste = l -> l.getDataHoraLancamento(); MONTADO SEPARADAMENTE
		List<Lancamento> lancamentosVencidosDintinctosPorData = lancamentosVencidos.stream()
				.filter(CaixaDeFerramentas.distinctByKey(Lancamento::getDataHoraVencimento)).collect(Collectors.toList());
														// É OGRIGADO A USAR ASSSIM DEVIDO ESTAR EM OUTRA CLASSE
*/		
		ModelAndView mv = new ModelAndView("lancamento/ListagemVencidos");
		mv.addObject("contas", contaServices.selecionaComOpcaoTodas(usuario));
		mv.addObject("lancamentosVencidos", new ArrayList<>());

		return mv;
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
	
	/* APLICAR O SEGUINTE: QUANDO CLICAR EM TODAS OS LANÇAMENTOS ABRIR UMA LISTA COM ASPENAS AS PENDENTES
	 * E EM CIMA UM <SELECT> PREENCHIDO COM AS DATAS QUE CONTEM CONTAS VENCIDAS. ACREDITO QUE VAI FICAR LEGAL */

	@PostMapping("/tabela")
	public String mostraTabelaCadastrados(Model model, @RequestParam String date, @RequestParam Object conta,
			@RequestParam(value = "mobile", required = false) String mobile) {
		
		Usuario usuario = auth.getAutenticacao();
		
		LocalDate localDate = CaixaDeFerramentas.calendarFromStringMesAnoDate(date);
		
		listaLancamentos = lancamentoServices.seleciona(usuario, localDate, conta);
		
		if (listaLancamentos == null || listaLancamentos.isEmpty()) {
			return "lancamento/TabelasVazias :: listaVazia";
		}
		
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
		
		model.addAttribute("lancamentos", lancamentosOrdenadosAndMesAtual);
		model.addAttribute("receitas", totalReceitas);
		model.addAttribute("debitos", totalDebitos);
		model.addAttribute("saldo", saldo);
		model.addAttribute("saldoProvavel", saldoProvavel);
		model.addAttribute("contas", contaServices.selecionaComOpcaoTodas(usuario));
		
		return mobile == null ? "lancamento/Tabela :: tabelaLancamento"
				: "lancamento/TabelaMobile :: tabelaLancamentoMobile";

	}
	
	private Conta contaUsuario = null;
	@GetMapping("/tabelaVencidos")
	public String mostraTabelaVencidos(Model model, @RequestParam("dataVencido") String calendarString,
			@RequestParam Object conta, @RequestParam(value = "mobile", required = false) String mobile) {
		
		contaUsuario = null; // NECESSÁRIO DEVIDO AO ATRIBUTO ESTAR DECLARADO NA CLASSE E NÃO NO MÉTODO.
		Usuario usuario = auth.getAutenticacao();
		
		LocalDate localDate = CaixaDeFerramentas.calendarFromStringDiaMesAnoDate(calendarString);
		
		List<Lancamento> listaLancamentos = lancamentoServices.selecionaVencidosDa(usuario, localDate, conta);
		
		if (listaLancamentos.isEmpty())
			return "lancamento/TabelasVazias :: listaVazia";
		
		Long idConta = parametrosServices.trataParametroLongException(conta);
		
		if (idConta != 0)
			contaUsuario = contaServices.get(idConta, null);
		
		model.addAttribute("lancamentos", contaUsuario == null ? listaLancamentos :
			listaLancamentos.stream().filter(l -> l.getConta() == contaUsuario).collect(Collectors.toList()));
		model.addAttribute("total", listaLancamentos == null ? null : listaLancamentos.stream()
				.map(l -> l.getValorLancamento()).reduce(BigDecimal.ZERO, BigDecimal::add));
											// MANEIRA DIFERENTE DE SOMAR
											// FAÇO COM O MAPTOLONG NO INDEX RESOURCEs
											// NO FUNCTION <T, R> O PRIMEIRO É O TIPO QUE ELE VAI TRABALHAR, E O SEGUNDO É
											// O QUE ELE VAI RETORNAR
		
		return mobile == null ? "lancamento/Tabela :: tabelaLancamento"
				: "lancamento/TabelaMobile :: tabelaLancamentoMobile";

	}
	
	/* ENTENDENDO O STREAM
	 * Function<Lancamento, LocalDate> func = l -> l.getDataHoraVencimento();*/
	
	@DeleteMapping("/remover/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id){
		
		Usuario usuario = auth.getAutenticacao();
		
		lancamentoServices.remove(usuario, id);
		
		return ResponseEntity.ok().build();
	}

	/*
	 * APENAS TESTE MESMO Teste teste = Teste.porString("Primeiro");
	 * System.out.println(teste.name());
	 */
	
	/*public void teste () {
		
		List<Integer> list = new ArrayList<>();
		
		avaliaExpressao(list, (n)-> n > 5 && n < 10);
	
	}
	   

	public void avaliaExpressao(List<Integer> list, Predicate<Integer> predicate) {
	    list.forEach(n -> {
	      if(predicate.test(n)) {
	            System.out.println(n + " ");
	        }
    });*/
	    
}
