package br.com.contability.exceptions;

public class ObjetoComDependenciaException extends RuntimeException {
	private static final long serialVersionUID = -8697787632237739440L;

	public ObjetoComDependenciaException() {
		super("Objeto com dependências");
	}

	public ObjetoComDependenciaException(String mensagem) {
		super(mensagem);

	}

}
