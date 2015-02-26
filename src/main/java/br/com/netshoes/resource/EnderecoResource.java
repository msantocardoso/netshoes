package br.com.netshoes.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.netshoes.model.Endereco;

@Path("enderecos")
public class EnderecoResource {

	@Path("{cep}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Endereco findByCep(@PathParam("cep") String cep) {

		Endereco lEndereco = new Endereco();
		lEndereco.setCodigo(1);
		lEndereco.setBairro("Bela Vista");
		lEndereco.setCep(cep);
		lEndereco.setLogradouro("Avenida Nove de Julho");

		return lEndereco;
	}
}
