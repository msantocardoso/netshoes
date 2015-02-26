package br.com.netshoes.resource;

import javax.ws.rs.core.MediaType;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.netshoes.model.Endereco;
import br.com.netshoes.server.ServerManager;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class EnderecoResourceTest {

	private static final String MAPPING_FEATURE_JSON = "com.sun.jersey.api.json.POJOMappingFeature";
	private static ServerManager server;
	private static Client client;
	private static WebResource webResource;

	@BeforeClass
	public static void setUpBeforeClass() {
		createServerManager();
		createClientAndWebResource();
	}

	static void createServerManager() {
		server = new ServerManager();
		server.start();
	}

	static void createClientAndWebResource() {
		ClientConfig config = new DefaultClientConfig();

		config.getProperties().put(MAPPING_FEATURE_JSON, true);

		client = Client.create(config);
		webResource = client.resource(ServerManager.URI);
	}

	@AfterClass
	public static void tearDownAfterClass() {
		server.stop();
		client.destroy();

		server = null;
		client = null;
		webResource = null;
	}

	@Test
	public void deveRecuperarEnderecoComIdentificador1() {

		String lJSON = webResource.path("/enderecos/01313000").accept(MediaType.APPLICATION_JSON).get(String.class);

		Endereco lEndereco = new Gson().fromJson(lJSON, Endereco.class);

		Assert.assertNotNull(lEndereco);

		Assert.assertEquals(1, lEndereco.getCodigo());
		Assert.assertEquals("Bela Vista", lEndereco.getBairro());
		Assert.assertEquals("01313000", lEndereco.getCep());
		Assert.assertEquals("Avenida Nove de Julho", lEndereco.getLogradouro());
	}
}
