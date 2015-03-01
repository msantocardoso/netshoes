package br.com.netshoes;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;


public class Servidor {

	private final Logger LOGGER = Logger.getLogger(Servidor.class);

	private HttpServer server;

	public void start() {
		try {
			LOGGER.info("Inicializando servidor...");

			this.server = new HttpServer();

			this.server.addListener(new NetworkListener("GrizzlyServer", "localhost", 8080));

			WebappContext lWebContext = new WebappContext("Netshoes Test - Murilo Cardoso", "/netshoes-test");

			ServletRegistration lServletRegistration = lWebContext.addServlet( "jersey-servlet", "org.glassfish.jersey.servlet.ServletContainer");
			lServletRegistration.setInitParameter("jersey.config.server.provider.packages", "br.com.netshoes");
			lServletRegistration.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
			lServletRegistration.setInitParameter( "com.sun.jersey.spi.container.ContainerResponseFilters", "com.sun.jersey.server.linking.LinkFilter;com.sun.jersey.api.container.filter.LoggingFilter" );
			lServletRegistration.setLoadOnStartup(1);
			lServletRegistration.addMapping("/*");

			lWebContext.deploy(this.server);

			this.server.start();
		} catch (IOException lIOExc) {
			LOGGER.error("Erro durante a inicialização do servidor...", lIOExc);
			throw new RuntimeException("Falha ao inicializar o servidor.");
		}
	}

	public void stop() {
		LOGGER.info("Finalizando servidor...");

		this.server.shutdown();
	}
}
