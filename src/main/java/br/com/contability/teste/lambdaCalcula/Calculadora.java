package br.com.contability.teste.lambdaCalcula;

/**
 * @author Henrique
 * 
 * EXEMPLO DE LAMBADA PARA CALCULOS BASICOS MATEMÁTICOS
 *
 * @param <T>
 */
public class Calculadora<T> {
	
	private T valor1;
	private T valor2;

	public Calculadora(T valor1, T valor2) {
		this.valor1 = valor1;
		this.valor2 = valor2;
		
	}
	
	/*public void gerenciadorCalculadora() {
		
		Compute<Integer> soma = (n1, n2) -> n1+n2; 
		
		soma.calcula(2, 4); // FORMA NORMALZINHA
		
		 					// ISSO AQUI ESTARIA NA IMPLEMENTAÇÃO DE UM MÉTODO, EXEMPLO ESSE MÉTODO RECEBE UMA INTERFACE COMPUTE E AQUI DENTRO
							// FAZ A MAGINA ACONTECER, VOU EXEMPLICICAR NO MÉTODO ABAIXO. HEHE E TAMBÉM VOU IMPRIMIR.
		
		
							// DAI QUE VEM A QUESTÃO, O VALOR JÁ TEM QUE ESTAR INJETADO EM ALGUM LUGAR, OU EM ALGUM MOMENTO
		
	}*/
	
	/**
	 * @param calcula
	 */
	public void gerenciadorCalculadoraValores(Compute<T> calcula) { // QUEM CHAMAR ESSA INTERFACE QUE REALIZARA A CONTA NECESSÁRIA
		
		System.out.println(calcula.calcula(valor1, valor2));
		
	}
	
	/**
	 * INTERESSANTE NÃO CONSIGO CONSUMIR NA MESMA CLASSE
	 */
	/*public void consomeCalculadora() {
		
		gerenciadorCalculadoraValores((v1, v2) -> v1+v2 );
		
	}*/

}
