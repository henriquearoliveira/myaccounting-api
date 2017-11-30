package br.com.contability.teste.lambdaCalcula;

public class ConsomeCalculadora {
	
	public void consome() {
		
		// SOMA
		Calculadora<Integer> calculadora = new Calculadora<Integer>(10, 20);
		calculadora.gerenciadorCalculadoraValores((v1, v2) -> v1+v2);
		
	}

}
