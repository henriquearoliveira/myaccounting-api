package br.com.contability.exceptions;

public class ObjetoNaoAutorizadoException extends RuntimeException {
	private static final long serialVersionUID = -6939782211030477063L;

	public ObjetoNaoAutorizadoException() {
		super("O objeto nao esta autorizado");
	}

	public ObjetoNaoAutorizadoException(String mensagem) {
		super(mensagem);
	}

}
