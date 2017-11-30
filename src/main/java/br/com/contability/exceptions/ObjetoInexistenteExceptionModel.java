package br.com.contability.exceptions;

public class ObjetoInexistenteExceptionModel extends RuntimeException {
	private static final long serialVersionUID = 2185739775799837242L;

	public ObjetoInexistenteExceptionModel() {
		super("O objeto requisitado não existe");
	}

	public ObjetoInexistenteExceptionModel(String mensagem) {
		super(mensagem);
	}

}
