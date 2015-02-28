package br.com.netshoes.infrastructure.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.netshoes.infrastructure.exception.JerseyClientException;

@Provider
public class JerseyClientExceptionMapper implements ExceptionMapper<JerseyClientException> {

	@Override
	public Response toResponse(JerseyClientException exception) {
		return exception.getResponse();
	}
}