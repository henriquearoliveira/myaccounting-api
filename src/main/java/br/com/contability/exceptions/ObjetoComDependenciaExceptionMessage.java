package br.com.contability.exceptions;

public class ObjetoComDependenciaExceptionMessage extends RuntimeException {
	private static final long serialVersionUID = -8697787632237739440L;
			
	private String redirect;

	public ObjetoComDependenciaExceptionMessage() {
		super("Objeto com dependências");
	}

	public ObjetoComDependenciaExceptionMessage(String redirect, String mensagem) {
		super(mensagem);
		this.redirect = redirect;
	}
	
	public String getRedirect() {
		return redirect;
	}

}
