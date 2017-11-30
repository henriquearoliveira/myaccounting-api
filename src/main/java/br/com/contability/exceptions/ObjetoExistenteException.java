package br.com.contability.exceptions;

public class ObjetoExistenteException extends RuntimeException {
	private static final long serialVersionUID = -6183188524594522807L;

	public ObjetoExistenteException() {
		super("Objeto já existente");
	}

	public ObjetoExistenteException(String mensagem) {
		super(mensagem);

	}

	public ObjetoExistenteException(String mensagem, String pagina) {
		super(mensagem);
	}

}
