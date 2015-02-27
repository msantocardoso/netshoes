package br.com.netshoes.exception;

public class NenhumRegistroEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NenhumRegistroEncontradoException() {
		super();
	}

	public NenhumRegistroEncontradoException(String pMensagem) {
		super(pMensagem);
	}

	public NenhumRegistroEncontradoException(Throwable pException) {
		super(pException);
	}

	public NenhumRegistroEncontradoException(String pMensagem, Throwable pException) {
		super(pMensagem, pException);
	}
}
