package br.com.netshoes.repository.services;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import br.com.netshoes.infrastructure.ClientAdapter;
import br.com.netshoes.infrastructure.JerseyClientAdapter;
import br.com.netshoes.infrastructure.exception.JerseyClientException;
import br.com.netshoes.infrastructure.exception.NenhumRegistroEncontrado;
import br.com.netshoes.infrastructure.exception.RepositoryException;
import br.com.netshoes.model.Endereco;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebServiceEnderecoExterno implements WebServiceEndereco {

	private final Logger LOGGER = Logger.getLogger(WebServiceEnderecoExterno.class);

	private ClientAdapter<String> clientAdapter = new JerseyClientAdapter<String>("http://api.postmon.com.br/v1/cep", MediaType.APPLICATION_JSON, String.class);

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Endereco buscaPorCep(String cep) throws RepositoryException {
		Endereco lEndereco = null;
		String lResult = "";
		try {

			lResult = clientAdapter.get(cep);

			LOGGER.info("Endereço encontrado: " + lResult);

			lEndereco = this.extractEndereco(lResult);
		} catch (JerseyClientException lClientException) {
			if (lClientException.getResponse().getStatus() == Status.NOT_FOUND.getStatusCode())
				throw new NenhumRegistroEncontrado();

			throw lClientException;
		}

		return lEndereco;
	}

	private Endereco extractEndereco(String pResult) {

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
