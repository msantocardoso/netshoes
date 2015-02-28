package br.com.netshoes.repository;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import br.com.netshoes.infrastructure.ClientAdapter;
import br.com.netshoes.infrastructure.JerseyClientAdapter;
import br.com.netshoes.infrastructure.exception.CepInvalidoException;
import br.com.netshoes.infrastructure.exception.JerseyClientException;
import br.com.netshoes.infrastructure.exception.NenhumRegistroEncontrado;
import br.com.netshoes.model.Endereco;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EnderecoRepository {

	private final Logger LOGGER = Logger.getLogger(EnderecoRepository.class);

	private ClientAdapter<String> clientAdapter = new JerseyClientAdapter<String>("http://api.postmon.com.br/v1/cep", MediaType.APPLICATION_JSON, String.class);

	private final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Recupera o endereço pelo cep.
	 *
	 * @param cep
	 * @return endereço preenchido
	 * @throws CepInvalidoException
	 */
	public Endereco buscaPorCep(String cep) {

		Endereco lEndereco = null;
		String lResult = "";
		try {

			lResult = clientAdapter.get(cep);

			LOGGER.info("Endereço encontrado: " + lResult);

			lEndereco = this.getEndereco(lResult);
		} catch (JerseyClientException lClientException) {
			if (lClientException.getResponse().getStatus() == Status.NOT_FOUND.getStatusCode())
				throw new NenhumRegistroEncontrado();

			throw lClientException;
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