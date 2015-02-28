package br.com.netshoes.infrastructure.exception;

public class NenhumRegistroEncontrado extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NenhumRegistroEncontrado() {
		super();
	}

	public NenhumRegistroEncontrado(String pMensagem) {
		super(pMensagem);
	}

	public NenhumRegistroEncontrado(Throwable pException) {
		super(pException);
	}

	public NenhumRegistroEncontrado(String pMensagem, Throwable pException) {
		super(pMensagem, pException);
	}
}
