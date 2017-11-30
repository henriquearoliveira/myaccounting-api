package br.com.contability.exceptions;

public class ObjetoInexistenteException extends RuntimeException {
	private static final long serialVersionUID = 2185739775799837242L;

	public ObjetoInexistenteException() {
		super("O objeto requisitado não existe");
	}

	public ObjetoInexistenteException(String mensagem) {
		super(mensagem);
	}

}
