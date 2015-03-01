package br.com.netshoes;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.ApplicationPath;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;

import br.com.netshoes.infrastructure.exception.CepInvalidoException;
import br.com.netshoes.model.Endereco;
import br.com.netshoes.repository.services.WebServiceEnderecoExterno;
import br.com.netshoes.service.EnderecoService;
import br.com.netshoes.service.EnderecoServiceImpl;

@ApplicationPath("/")
public class NetShoesApplication extends ResourceConfig {

	private static final Logger LOGGER = Logger.getLogger(NetShoesApplication.class);

	public NetShoesApplication() {
		packages("br.com.netshoes");
	}

	public static void main(String[] args) throws IOException {

		EntityManagerFactory lEntityFactory = Persistence.createEntityManagerFactory("persistence");
		EntityManager lEntityManager = lEntityFactory.createEntityManager();

		EnderecoService lEnderecoService = new EnderecoServiceImpl(lEntityManager);

		Endereco lEndereco = null;
		try {
			lEndereco = lEnderecoService.buscarPorCep("01313001", new WebServiceEnderecoExterno());

			lEndereco.setComplemento("Nº 141, Aptº 33A");

			lEnderecoService.inserir(lEndereco);
			LOGGER.info("Endereço criado: " + lEndereco);
			System.in.read();
			lEndereco.setComplemento("Novo complemento de teste!");

			lEnderecoService.atualizar(lEndereco);

			LOGGER.info("Endereço > "+lEnderecoService.buscarPorId(1L));

			lEnderecoService.remover(lEndereco);
		} catch (CepInvalidoException e) {
			e.printStackTrace();
		} finally {
			lEntityManager.close();
			lEntityFactory.close();
		}
	}
}