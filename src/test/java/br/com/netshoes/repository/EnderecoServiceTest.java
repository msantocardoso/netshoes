package br.com.netshoes.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import br.com.netshoes.infrastructure.exception.CepInvalidoException;
import br.com.netshoes.infrastructure.exception.EnderecoInvalidoException;
import br.com.netshoes.model.Endereco;
import br.com.netshoes.service.EnderecoService;
import br.com.netshoes.service.EnderecoServiceImpl;

public class EnderecoServiceTest extends AbstracaoRepositorioTest {

	private static final String CEP_TESTE = "01313020";
	private static final String CEP_INVALIDO = "12003321651651651";

	EnderecoService service =  new EnderecoServiceImpl(entityManager);

	@Override
	public EnderecoService getEnderecoService() {
		return service;
	}

	@Test
	public void deveExecutarCrudDeEndereco() throws CepInvalidoException {

		Endereco lEndereco = new Endereco();

		lEndereco.setCep(CEP_TESTE);
		lEndereco.setRua("Rua Doutor Plínio Barreto");
		lEndereco.setNumero(15);
		lEndereco.setBairro("Bela Vista");
		lEndereco.setCidade("São Paulo");
		lEndereco.setEstado("SP");

		// inserção
		inserir(lEndereco);

		// consulta por id
    	lEndereco = service.buscarPorId(lEndereco.getId());

		assertNotNull("Endereço nulo!", lEndereco);

		assertEquals(CEP_TESTE, lEndereco.getCep());
		assertEquals("Rua Doutor Plínio Barreto", lEndereco.getRua());
		assertEquals(Integer.valueOf(15), lEndereco.getNumero());
		assertEquals("Bela Vista", lEndereco.getBairro());
		assertEquals("São Paulo", lEndereco.getCidade());
		assertEquals("SP", lEndereco.getEstado());

		// atualização
		lEndereco.setRua( "Rua Dr. Plinio Barreto");

		service.atualizar(lEndereco);

		// consulta por id
    	lEndereco = service.buscarPorId(lEndereco.getId());

    	assertEquals("Rua Dr. Plinio Barreto", lEndereco.getRua());

		// remoção
		service.remover(lEndereco);

		lEndereco = service.buscarPorId(lEndereco.getId());

        assertNull(lEndereco);
	}

    @Test(expected = EnderecoInvalidoException.class)
	public void deveLancaEnderecoInvalidoExceptionAoTentarInserirUmEnderecoSemARua() throws CepInvalidoException {

		Endereco lEndereco = new Endereco();

		lEndereco.setCep(CEP_TESTE);
		lEndereco.setNumero(15);
		lEndereco.setBairro("Bela Vista");
		lEndereco.setCidade("São Paulo");
		lEndereco.setEstado("SP");

    	service.inserir(lEndereco);
	}


    @Test(expected = EnderecoInvalidoException.class)
	public void deveLancaEnderecoInvalidoExceptionAoTentarInserirUmEnderecoSemCidade() throws CepInvalidoException {

		Endereco lEndereco = new Endereco();

		lEndereco.setCep(CEP_TESTE);
		lEndereco.setRua("Rua Doutor Plínio Barreto");
		lEndereco.setNumero(15);
		lEndereco.setBairro("Bela Vista");
		lEndereco.setEstado("SP");

    	service.inserir(lEndereco);
	}

    @Test(expected = EnderecoInvalidoException.class)
	public void deveLancaEnderecoInvalidoExceptionAoTentarInserirUmEnderecoSemNumero() throws CepInvalidoException {

		Endereco lEndereco = new Endereco();

		lEndereco.setCep(CEP_TESTE);
		lEndereco.setRua("Rua Doutor Plínio Barreto");
		lEndereco.setBairro("Bela Vista");
		lEndereco.setCidade("São Paulo");
		lEndereco.setEstado("SP");

    	service.inserir(lEndereco);
	}

    @Test(expected = EnderecoInvalidoException.class)
	public void deveLancaEnderecoInvalidoExceptionAoTentarInserirUmEnderecoSemEstado() throws CepInvalidoException {

		Endereco lEndereco = new Endereco();

		lEndereco.setCep(CEP_TESTE);
		lEndereco.setRua("Rua Doutor Plínio Barreto");
		lEndereco.setNumero(15);
		lEndereco.setBairro("Bela Vista");
		lEndereco.setCidade("São Paulo");

    	service.inserir(lEndereco);
	}

    @Test(expected = CepInvalidoException.class)
	public void deveLancaEnderecoInvalidoExceptionAoTentarInserirUmEnderecoSemCep() throws CepInvalidoException {

		Endereco lEndereco = new Endereco();

		lEndereco.setRua("Rua Doutor Plínio Barreto");
		lEndereco.setNumero(15);
		lEndereco.setBairro("Bela Vista");
		lEndereco.setCidade("São Paulo");
		lEndereco.setEstado("SP");

    	service.inserir(lEndereco);
	}

    @Test(expected = CepInvalidoException.class)
 	public void deveLancaEnderecoInvalidoExceptionAoTentarAlterarEnderecoSemCep() throws CepInvalidoException {

		Endereco lEndereco = new Endereco();

		lEndereco.setRua("Rua Doutor Plínio Barreto");
		lEndereco.setNumero(15);
		lEndereco.setBairro("Bela Vista");
		lEndereco.setCidade("São Paulo");
		lEndereco.setEstado("SP");

		service.atualizar(lEndereco);
 	}

    @Test(expected = EnderecoInvalidoException.class)
 	public void deveLancaEnderecoInvalidoExceptionAoTentarAlterarEnderecoSemRua() throws CepInvalidoException {

		Endereco lEndereco = new Endereco();

		lEndereco.setCep(CEP_TESTE);
		lEndereco.setNumero(15);
		lEndereco.setBairro("Bela Vista");
		lEndereco.setCidade("São Paulo");
		lEndereco.setEstado("SP");

     	lEndereco.setRua(null);

     	service.atualizar(lEndereco);
 	}

    @Test(expected = EnderecoInvalidoException.class)
    public void deveLancaEnderecoInvalidoExceptionAoTentarAlterarEnderecoSemNumero() throws CepInvalidoException {

		Endereco lEndereco = new Endereco();

		lEndereco.setCep(CEP_TESTE);
		lEndereco.setRua("Rua Doutor Plínio Barreto");
		lEndereco.setBairro("Bela Vista");
		lEndereco.setCidade("São Paulo");
		lEndereco.setEstado("SP");

    	service.atualizar(lEndereco);
    }

    @Test(expected = EnderecoInvalidoException.class)
    public void deveLancaEnderecoInvalidoExceptionAoTentarAlterarEnderecoSemCidade() throws CepInvalidoException {

		Endereco lEndereco = new Endereco();

		lEndereco.setCep(CEP_TESTE);
		lEndereco.setRua("Rua Doutor Plínio Barreto");
		lEndereco.setNumero(15);
		lEndereco.setBairro("Bela Vista");
		lEndereco.setEstado("SP");

    	service.atualizar(lEndereco);
    }

    @Test(expected = EnderecoInvalidoException.class)
    public void deveLancaEnderecoInvalidoExceptionAoTentarAlterarEnderecoSemEstado() throws CepInvalidoException {

		Endereco lEndereco = new Endereco();

		lEndereco.setCep(CEP_TESTE);
		lEndereco.setRua("Rua Doutor Plínio Barreto");
		lEndereco.setNumero(15);
		lEndereco.setBairro("Bela Vista");
		lEndereco.setCidade("São Paulo");

    	service.atualizar(lEndereco);
    }

    @Test(expected = CepInvalidoException.class)
	public void deveLancarCepInvalidoExceptionAoTentarPersistirEndereco() throws CepInvalidoException {

		Endereco lEndereco = new Endereco();

		lEndereco.setCep(CEP_INVALIDO);
		lEndereco.setRua("Rua Cep Inválido");
		lEndereco.setBairro("Invalido");
		lEndereco.setCidade("São Paulo");
		lEndereco.setEstado("SP");

		service.inserir(lEndereco);
	}
}