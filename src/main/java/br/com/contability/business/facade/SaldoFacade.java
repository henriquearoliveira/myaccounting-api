package br.com.contability.business.facade;

//@Component
public class SaldoFacade {
	
	/*@Autowired
	private LancamentoServices lancamentoServices;
	
	@Autowired
	private SaldoServices saldoServices;

	*//**
	 * @param usuario
	 * @param lancamento
	 *//*
	public void atualizaSaldoUsuario(Lancamento lancamento) {
		
		BigDecimal saldoValor = lancamentoServices.getSaldo(lancamento.getUsuario(), lancamento.getDataHoraLancamento());
		
		BigDecimal saldoValorProvavel = lancamentoServices.getSaldoProvavel(lancamento.getUsuario(), lancamento.getDataHoraLancamento());

		Saldo saldo = saldoServices.getSaldo(lancamento.getUsuario().getId(), lancamento.getDataHoraLancamento());
		
		LocalDateTime dataHoraCadastro = LocalDateTime.of(lancamento.getDataHoraLancamento(), LocalTime.now());
		LocalDateTime dataHoraLancamento = LocalDateTime.of(lancamento.getDataHoraLancamento(), LocalTime.now());
		
		if (saldo == null){
			
			saldo = new Saldo();
			saldo.setDataHoraCadastro(dataHoraCadastro);
			saldo.setDataHoraLancamento(dataHoraLancamento);
			saldo.setSaldoAtual(saldoValor);
			saldo.setSaldoProvavel(saldoValorProvavel);
			saldo.setUsuario(lancamento.getUsuario());
			
			saldoServices.insere(saldo, null);
			
		} else
			
			saldo.setSaldoAtual(saldoValor);
			saldo.setSaldoProvavel(saldoValorProvavel);
			saldoServices.atualiza(saldo, null);
		
	}*/

}
