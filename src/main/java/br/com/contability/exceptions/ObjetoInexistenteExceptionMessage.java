package br.com.contability.exceptions;

public class ObjetoInexistenteExceptionMessage extends RuntimeException {
	private static final long serialVersionUID = 2185739775799837242L;
	
	private String redirect;

	public ObjetoInexistenteExceptionMessage() {
		super("O objeto requisitado não existe");
	}

	public ObjetoInexistenteExceptionMessage(String redirect, String mensagem) {
		super(mensagem);
		this.redirect = redirect;
	}

	public String getRedirect() {
		return redirect;
	}
}
