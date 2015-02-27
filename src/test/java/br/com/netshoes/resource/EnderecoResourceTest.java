package br.com.netshoes.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.netshoes.model.Endereco;
import br.com.netshoes.server.ServerManager;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

public class EnderecoResourceTest {

	private static final String CEP_INVALIDO = "Cep inválido!";

	private static final String NETSHOES_TEST_CONTEXT = "http://localhost:8080/netshoes-test";

	private static final ServerManager server = new ServerManager();

	private static final Client CLIENT = Client.create();
	private static final WebResource webResource = CLIENT.resource(NETSHOES_TEST_CONTEXT);

	@BeforeClass
	public static void setUpBeforeClass() {
		createServerManager();
	}

	static void createServerManager() {
		server.start();
	}

	@AfterClass
	public static void tearDownAfterClass() {
		server.stop();
		CLIENT.destroy();
	}

	@Test
	public void deveRetornarLogradouroAvenidaNoveDeJulhoPorCep() {

		final String lCEP = "01313001";

		Endereco lEndereco = webResource.path("/enderecos/"+lCEP).accept(MediaType.APPLICATION_JSON).get(Endereco.class);

		assertNotNull("Endereço nulo!", lEndereco);

		assertEquals(lCEP, lEndereco.getCep());
		assertEquals("Avenida Nove de Julho", lEndereco.getRua());
		assertEquals("Bela Vista", lEndereco.getBairro());
		assertEquals("São Paulo", lEndereco.getCidade());
		assertEquals("SP", lEndereco.getEstado());
	}

	@Test
	public void deveSimularCepValidoDiversasBuscasDeEndereco() {

		Endereco lEndereco = webResource.path("/enderecos/11013006").accept(MediaType.APPLICATION_JSON).get(Endereco.class);

		assertNotNull("Endereço nulo!", lEndereco);

		assertEquals("11013000", lEndereco.getCep());
		assertEquals("Rua João Pessoa", lEndereco.getRua());
		assertEquals("Centro", lEndereco.getBairro());
		assertEquals("Santos", lEndereco.getCidade());
		assertEquals("SP", lEndereco.getEstado());
	}

	@Test
	public void deveSimularVariasTentativasDeBuscaDeEndereco() {

		Endereco lEndereco = webResource.path("/enderecos/12345678").accept(MediaType.APPLICATION_JSON).get(Endereco.class);

		assertNotNull("Endereço nulo!", lEndereco);

		assertEquals("12340000", lEndereco.getCep());
		assertEquals("Avenida S\u00e3o Gabriel", lEndereco.getRua());
		assertEquals("Jardim S\u00e3o Gabriel", lEndereco.getBairro());
		assertEquals("Jacare\u00ed", lEndereco.getCidade());
		assertEquals("SP", lEndereco.getEstado());
	}

	@Test(expected=UniformInterfaceException.class)
	public void deveLancarExceptionAoPassarUmaSequenciaDeZeros() {
		webResource.path("/enderecos/00000000").accept(MediaType.APPLICATION_JSON).get(Endereco.class);
	}

	@Test
	public void deveRetornarStatusCodeBadRequestEMsgCepInvalidoAoPassarUmaSequenciaDeZeros() {
		try {
			webResource.path("/enderecos/00000000").accept(MediaType.APPLICATION_JSON).get(Endereco.class);
		} catch (UniformInterfaceException lUniformInterface) {
			assertEquals(CEP_INVALIDO, lUniformInterface.getResponse().getEntity(String.class));
			assertEquals(Status.BAD_REQUEST.getStatusCode(), lUniformInterface.getResponse().getStatus());
		}
	}

	@Test
	public void deveStatusCodeBadRequestEMsgCepInvalidoAoUtilizarCepInvalidoComZeros() {
		try {
			webResource.path("/enderecos/00000-000").accept(MediaType.APPLICATION_JSON).get(Endereco.class);
		} catch (UniformInterfaceException lUniformInterface) {
			assertEquals(CEP_INVALIDO, lUniformInterface.getResponse().getEntity(String.class));
			assertEquals(Status.BAD_REQUEST.getStatusCode(), lUniformInterface.getResponse().getStatus());
		}
	}

	@Test
	public void deveStatusCodeBadRequestEMsgCepInvalidoAoPassarCepComMenosDeOitoCaracteres() {
		try {
			webResource.path("/enderecos/12345").accept(MediaType.APPLICATION_JSON).get(Endereco.class);
		} catch (UniformInterfaceException lUniformInterface) {
			assertEquals(CEP_INVALIDO, lUniformInterface.getResponse().getEntity(String.class));
			assertEquals(Status.BAD_REQUEST.getStatusCode(), lUniformInterface.getResponse().getStatus());
		}
	}

	@Test
	public void deveStatusCodeBadRequestEMsgCepInvalidoAoPassarCaracteresEspeciaisNoCep() {

		try {
			webResource.path("/enderecos/1#@$%&* ").accept(MediaType.APPLICATION_JSON).get(Endereco.class);
		} catch (UniformInterfaceException lUniformInterface) {
			assertEquals(CEP_INVALIDO, lUniformInterface.getResponse().getEntity(String.class));
			assertEquals(Status.BAD_REQUEST.getStatusCode(), lUniformInterface.getResponse().getStatus());
		}
	}

	@Test
	public void deveStatusCodeBadRequestEMsgCepInvalidoAoPassarCepComMaisDeOitoCaracteres() {

		try {
			webResource.path("/enderecos/0123456789").accept(MediaType.APPLICATION_JSON).get(Endereco.class);
		} catch (UniformInterfaceException lUniformInterface) {
			assertEquals(CEP_INVALIDO, lUniformInterface.getResponse().getEntity(String.class));
			assertEquals(Status.BAD_REQUEST.getStatusCode(), lUniformInterface.getResponse().getStatus());
		}
	}
}
