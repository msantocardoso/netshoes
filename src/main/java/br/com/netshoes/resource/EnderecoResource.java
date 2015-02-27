package br.com.netshoes.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.netshoes.exception.CepInvalidoException;
import br.com.netshoes.model.Endereco;
import br.com.netshoes.service.EnderecoService;

@Path("enderecos")
public class EnderecoResource {

	private final EnderecoService enderecoService = new EnderecoService();

	@GET
	@Path("{cep}")
	@Produces(MediaType.APPLICATION_JSON)
	public Endereco buscaPorCep(@PathParam("cep") String cep) throws CepInvalidoException {

		Endereco lEndereco = enderecoService.buscaPorCep(cep);

		if(lEndereco == null) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity("Nenhum endereço encontrado!").type("text/plain").build());
		}

		return lEndereco;
	}


}