package br.com.netshoes.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import br.com.netshoes.infrastructure.exception.JerseyClientException;
import br.com.netshoes.model.Endereco;

public class EnderecoResourceTest extends AbstracaoEnderecoResourceTest {

	private static final String PATH_ENDERECOS = "/enderecos/";

	@Test
	public void deveRetornarLogradouroAvenidaNoveDeJulhoPorCep() {

		final String lCEP = "01313001";

		Endereco lEndereco = this.getResource(PATH_ENDERECOS + lCEP);

		assertNotNull("Endereço nulo!", lEndereco);

		assertEquals(lCEP, lEndereco.getCep());
		assertEquals("Avenida Nove de Julho", lEndereco.getRua());
		assertEquals("Bela Vista", lEndereco.getBairro());
		assertEquals("São Paulo", lEndereco.getCidade());
		assertEquals("SP", lEndereco.getEstado());
	}

	@Test
	public void deveSimularCepValidoDiversasBuscasDeEndereco() {

		Endereco lEndereco = this.getResource(PATH_ENDERECOS + "11013006");

		assertNotNull("Endereço nulo!", lEndereco);

		assertEquals("11013000", lEndereco.getCep());
		assertEquals("Rua João Pessoa", lEndereco.getRua());
		assertEquals("Centro", lEndereco.getBairro());
		assertEquals("Santos", lEndereco.getCidade());
		assertEquals("SP", lEndereco.getEstado());
	}

	@Test
	public void deveSimularVariasTentativasDeBuscaDeEndereco() {

		Endereco lEndereco = this.getResource(PATH_ENDERECOS + "12345678");

		assertNotNull("Endereço nulo!", lEndereco);

		assertEquals("12340000", lEndereco.getCep());
		assertEquals("Avenida S\u00e3o Gabriel", lEndereco.getRua());
		assertEquals("Jardim S\u00e3o Gabriel", lEndereco.getBairro());
		assertEquals("Jacare\u00ed", lEndereco.getCidade());
		assertEquals("SP", lEndereco.getEstado());
	}

	@Test(expected = JerseyClientException.class)
	public void deveLancarExceptionAoPassarUmaSequenciaDeZeros() {
		clientAdapter.get(PATH_ENDERECOS + "00000000");
	}

	@Test
	public void deveRetornarStatusCodeBadRequestEMsgCepInvalidoAoPassarUmaSequenciaDeZeros() {
		this.getResource(PATH_ENDERECOS + "00000000");
	}

	@Test
	public void deveStatusCodeBadRequestEMsgCepInvalidoAoUtilizarCepInvalidoComZeros() {
		this.getResource(PATH_ENDERECOS + "00000-000");
	}

	@Test
	public void deveStatusCodeBadRequestEMsgCepInvalidoAoPassarCepComMenosDeOitoCaracteres() {
		this.getResource(PATH_ENDERECOS + "12345");
	}

	@Test
	public void deveStatusCodeBadRequestEMsgCepInvalidoAoPassarCaracteresEspeciaisNoCep() {

		this.getResource(PATH_ENDERECOS + "1#@$%&* ");
	}

	@Test
	public void deveStatusCodeBadRequestEMsgCepInvalidoAoPassarCepComMaisDeOitoCaracteres() {

		this.getResource(PATH_ENDERECOS + "0123456789");
	}
}
