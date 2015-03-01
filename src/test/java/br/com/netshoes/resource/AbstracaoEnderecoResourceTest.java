package br.com.netshoes.resource;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import br.com.netshoes.Servidor;
import br.com.netshoes.infrastructure.ClientAdapter;
import br.com.netshoes.infrastructure.JerseyClientAdapter;
import br.com.netshoes.infrastructure.exception.JerseyClientException;
import br.com.netshoes.model.Endereco;

public abstract class AbstracaoEnderecoResourceTest {

	final String APPLICATION_CONTEXT = "http://localhost:8080/netshoes-test";

	final String CEP_INVALIDO = "Cep inválido!";

	static final Servidor server = new Servidor();

	protected ClientAdapter<Endereco> clientAdapter = new JerseyClientAdapter<Endereco>(APPLICATION_CONTEXT, MediaType.APPLICATION_JSON, Endereco.class);

	@BeforeClass
	public static void setUpBeforeClass() {
		server.start();
	}

	@AfterClass
	public static void tearDownAfterClass() {
		server.stop();
	}

	@SuppressWarnings("unchecked")
	protected <T> T getResource(String pCEP) {
		T lResponse = null;
		try {
			lResponse = (T) clientAdapter.get(pCEP);
		} catch (JerseyClientException lClientException) {
			assertEquals(CEP_INVALIDO, lClientException.getResponse().getEntity());
			assertEquals(Status.BAD_REQUEST.getStatusCode(), lClientException.getResponse().getStatus());
		}

		return lResponse;
	}

}
