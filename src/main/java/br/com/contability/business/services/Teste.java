package br.com.contability.business.services;

import br.com.contability.exceptions.ObjetoInexistenteException;

public enum Teste {
	
	PRIMEIRO_VALOR("Primeiro"), SEGUNDO_VALOR("Segundo");
	
	private String valor;

	private Teste(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}
	
	public static Teste porString(String valor){
		
		for (Teste teste : Teste.values()) {
			
			if(valor == teste.getValor())
				return teste;
			
		}
		
		throw new ObjetoInexistenteException();
		
	}

}
