package br.com.contability.business;

import java.util.List;

public class Lancamentos {
	
	private List<Lancamento> lancamentos;
	
	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}
	
	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

}
