package br.com.contability.exceptions;

public class ObjetoExistenteExceptionModel extends RuntimeException {
	private static final long serialVersionUID = -6183188524594522807L;

	public ObjetoExistenteExceptionModel() {
		super("Objeto já existente");
	}

	public ObjetoExistenteExceptionModel(String mensagem) {
		super(mensagem);

	}

}
