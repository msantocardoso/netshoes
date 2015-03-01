package br.com.netshoes.infrastructure.exception;

public class RepositoryException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RepositoryException() {
		super();
	}

	public RepositoryException(String pMensagem) {
		super(pMensagem);
	}

	public RepositoryException(Throwable pException) {
		super(pException);
	}

	public RepositoryException(String pMensagem, Throwable pException) {
		super(pMensagem, pException);
	}
}
