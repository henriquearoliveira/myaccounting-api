package br.com.contability.exceptions;

public class ObjetoExistenteExceptionMessage extends RuntimeException {
	private static final long serialVersionUID = -6183188524594522807L;
	private String redirect;

	public ObjetoExistenteExceptionMessage() {
		super("Objeto já existente");
	}

	public ObjetoExistenteExceptionMessage(String redirect, String mensagem) {
		super(mensagem);
		this.redirect = redirect;

	}
	
	public String getRedirect() {
		return redirect;
	}

}
