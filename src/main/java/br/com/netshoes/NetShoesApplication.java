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
}
