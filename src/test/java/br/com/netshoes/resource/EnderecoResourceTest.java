package br.com.netshoes.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.netshoes.Servidor;
import br.com.netshoes.infrastructure.ClientAdapter;
import br.com.netshoes.infrastructure.JerseyClientAdapter;
import br.com.netshoes.infrastructure.exception.JerseyClientException;
import br.com.netshoes.model.Endereco;

public class EnderecoResourceTest {

	private static final String CEP_INVALIDO = "Cep inválido!";

	private static final Servidor server = new Servidor();

	private ClientAdapter<Endereco> clientAdapter = new JerseyClientAdapter<Endereco>("http://localhost:8080/netshoes-test", MediaType.APPLICATION_JSON, Endereco.class);

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
	}

	@SuppressWarnings("unchecked")
	private <T> T requestResource(String pCEP) {
		T lResponse = null;
		try {
			lResponse = (T) clientAdapter.get(pCEP);
		} catch (JerseyClientException lClientException) {
			assertEquals(CEP_INVALIDO, lClientException.getResponse().getEntity());
			assertEquals(Status.BAD_REQUEST.getStatusCode(), lClientException.getResponse().getStatus());
		}

		return lResponse;
	}

	@Test
	public void deveRetornarLogradouroAvenidaNoveDeJulhoPorCep() {

		final String lCEP = "01313001";

		Endereco lEndereco = this.requestResource("/enderecos/" + lCEP);

		assertNotNull("Endereço nulo!", lEndereco);

		assertEquals(lCEP, lEndereco.getCep());
		assertEquals("Avenida Nove de Julho", lEndereco.getRua());
		assertEquals("Bela Vista", lEndereco.getBairro());
		assertEquals("São Paulo", lEndereco.getCidade());
		assertEquals("SP", lEndereco.getEstado());
	}

	@Test
	public void deveSimularCepValidoDiversasBuscasDeEndereco() {

		Endereco lEndereco = this.requestResource("/enderecos/11013006");

		assertNotNull("Endereço nulo!", lEndereco);

		assertEquals("11013000", lEndereco.getCep());
		assertEquals("Rua João Pessoa", lEndereco.getRua());
		assertEquals("Centro", lEndereco.getBairro());
		assertEquals("Santos", lEndereco.getCidade());
		assertEquals("SP", lEndereco.getEstado());
	}

	@Test
	public void deveSimularVariasTentativasDeBuscaDeEndereco() {

		Endereco lEndereco = this.requestResource("/enderecos/12345678");

		assertNotNull("Endereço nulo!", lEndereco);

		assertEquals("12340000", lEndereco.getCep());
		assertEquals("Avenida S\u00e3o Gabriel", lEndereco.getRua());
		assertEquals("Jardim S\u00e3o Gabriel", lEndereco.getBairro());
		assertEquals("Jacare\u00ed", lEndereco.getCidade());
		assertEquals("SP", lEndereco.getEstado());
	}

	@Test(expected = JerseyClientException.class)
	public void deveLancarExceptionAoPassarUmaSequenciaDeZeros() {
		clientAdapter.get("/enderecos/00000000");
	}

	@Test
	public void deveRetornarStatusCodeBadRequestEMsgCepInvalidoAoPassarUmaSequenciaDeZeros() {
		this.requestResource("/enderecos/00000000");
	}

	@Test
	public void deveStatusCodeBadRequestEMsgCepInvalidoAoUtilizarCepInvalidoComZeros() {
		this.requestResource("/enderecos/00000-000");
	}

	@Test
	public void deveStatusCodeBadRequestEMsgCepInvalidoAoPassarCepComMenosDeOitoCaracteres() {
		this.requestResource("/enderecos/12345");
	}

	@Test
	public void deveStatusCodeBadRequestEMsgCepInvalidoAoPassarCaracteresEspeciaisNoCep() {

		this.requestResource("/enderecos/1#@$%&* ");
	}

	@Test
	public void deveStatusCodeBadRequestEMsgCepInvalidoAoPassarCepComMaisDeOitoCaracteres() {

		this.requestResource("/enderecos/0123456789");
	}
}
