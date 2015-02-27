package br.com.netshoes.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.netshoes.exception.CepInvalidoException;

@Provider
public class CepInvalidoMapper implements ExceptionMapper<CepInvalidoException> {

	@Override
	public Response toResponse(CepInvalidoException exception) {
		return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).type(MediaType.APPLICATION_JSON).build();
	}
}