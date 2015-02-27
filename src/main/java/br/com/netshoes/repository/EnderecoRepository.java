package br.com.netshoes.repository;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import br.com.netshoes.exception.CepInvalidoException;
import br.com.netshoes.exception.NenhumRegistroEncontradoException;
import br.com.netshoes.model.Endereco;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

public class EnderecoRepository {

	private final Logger LOGGER = Logger.getLogger(EnderecoRepository.class);

	private static final String API_POSTMON_CEP = "http://api.postmon.com.br/v1/cep";

	private final Client client = Client.create();
	private final WebResource webResource = client.resource(API_POSTMON_CEP);

	private final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Recupera o endereço pelo cep.
	 *
	 * @param cep
	 * @return endereço preenchido
	 * @throws CepInvalidoException
	 */
	public Endereco findByCep(String cep) {

		Endereco lEndereco = null;
		String lResult = "";
		try {

			lResult = webResource.path(cep).accept(MediaType.APPLICATION_JSON).get(String.class);

			LOGGER.info("Endereço encontrado: " + lResult);

			lEndereco = this.getEndereco(lResult);
		} catch (UniformInterfaceException lUniformInterface) {
			if(lUniformInterface.getResponse().getStatus() == Status.NOT_FOUND.getStatusCode())
				throw new NenhumRegistroEncontradoException();

			throw lUniformInterface;
		}

		return lEndereco;
	}

	private Endereco getEndereco(String pResult) {

		Endereco lEndereco = null;

		JsonNode lRootNode;
		try {
			lRootNode = objectMapper.readTree(pResult);

			lEndereco = new Endereco();

			lEndereco.setCep(lRootNode.path("cep").asText());
			lEndereco.setRua(lRootNode.path("logradouro").asText());
			lEndereco.setBairro(lRootNode.path("bairro").asText());
			lEndereco.setCidade(lRootNode.path("cidade").asText());
			lEndereco.setEstado(lRootNode.path("estado").asText());
		} catch (JsonProcessingException e) {
			LOGGER.error("Falha ao converter a resposta do serviço de busca de cep: " + pResult, e);
			throw new WebApplicationException(500);
		} catch (IOException e) {
			LOGGER.error("Falha ao converter a resposta do serviço de busca de cep: " + pResult, e);
			throw new WebApplicationException(500);
		}

		return lEndereco;
	}
}
