package br.com.contability.exceptions;

public class ObjetoComDependenciaExceptionModel extends RuntimeException {
	private static final long serialVersionUID = -8697787632237739440L;

	public ObjetoComDependenciaExceptionModel() {
		super("Objeto com dependências");
	}

	public ObjetoComDependenciaExceptionModel(String mensagem) {
		super(mensagem);

	}

}
