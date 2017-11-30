package br.com.contability.business;

public enum TipoDeOpcoes {
	
	DEPOSITO_CONTA("Deposito em conta")/*, LANCAMENTO_PROXIMO_MES("Lançamento próximo mês")*/;
	
	private String descricao;

	private TipoDeOpcoes(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
