package br.com.netshoes.infrastructure.exception;

public class CepInvalidoException extends Exception {

	private static final long serialVersionUID = 1L;

	public CepInvalidoException() {
		super();
	}

	public CepInvalidoException(String pMensagem) {
		super(pMensagem);
	}

	public CepInvalidoException(Throwable pException) {
		super(pException);
	}

	public CepInvalidoException(String pMensagem, Throwable pException) {
		super(pMensagem, pException);
	}
}

