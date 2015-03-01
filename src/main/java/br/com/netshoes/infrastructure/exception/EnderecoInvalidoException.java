package br.com.netshoes.infrastructure.exception;

public class EnderecoInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EnderecoInvalidoException() {
		super();
	}

	public EnderecoInvalidoException(String pMensagem) {
		super(pMensagem);
	}

	public EnderecoInvalidoException(Throwable pException) {
		super(pException);
	}

	public EnderecoInvalidoException(String pMensagem, Throwable pException) {
		super(pMensagem, pException);
	}
}
