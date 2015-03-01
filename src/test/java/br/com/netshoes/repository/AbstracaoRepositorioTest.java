package br.com.netshoes.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import br.com.netshoes.Servidor;
import br.com.netshoes.infrastructure.exception.CepInvalidoException;
import br.com.netshoes.model.Endereco;
import br.com.netshoes.service.EnderecoService;

public abstract class AbstracaoRepositorioTest {

	protected static final String CEP_BASE = "01313001";

	private static final Servidor server = new Servidor();

	private static EntityManagerFactory entityManagerFactory;

	protected static EntityManager entityManager;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		server.start();

		entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        entityManager = entityManagerFactory.createEntityManager();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		server.stop();

		entityManager.close();
		entityManagerFactory.close();
	}

	public abstract EnderecoService getEnderecoService();

	protected Endereco inserir(Endereco pEndereco) throws CepInvalidoException {

		getEnderecoService().inserir(pEndereco);

		return pEndereco;
	}
}