package br.com.contability.exceptions;

public class ObjetoNaoAutorizadoMessage extends RuntimeException {
	private static final long serialVersionUID = -6939782211030477063L;

	private String redirect;

	public ObjetoNaoAutorizadoMessage() {
		super("O objeto nao esta autorizado");
	}

	public ObjetoNaoAutorizadoMessage(String redirect, String mensagem) {
		super(mensagem);
		this.redirect = redirect;

	}

	public String getRedirect() {
		return redirect;
	}

}
