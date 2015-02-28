package br.com.netshoes.infrastructure.exception;

import javax.ws.rs.core.Response;

public class JerseyClientException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Response response;

	public JerseyClientException() {
		super();
	}

	public JerseyClientException(Response pResponse) {
		this.response = pResponse;
	}

	public JerseyClientException(String pMensagem) {
		super(pMensagem);
	}

	public JerseyClientException(Throwable pException) {
		super(pException);
	}

	public JerseyClientException(String pMensagem, Throwable pException) {
		super(pMensagem, pException);
	}

	public Response getResponse() {
		return this.response;
	}
}
