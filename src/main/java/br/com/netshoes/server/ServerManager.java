package br.com.netshoes.server;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;


public class ServerManager {

	private final Logger LOGGER = Logger.getLogger(ServerManager.class);

	public static final URI URI = UriBuilder.fromUri("http://localhost/").port(8080).build();
	private final ResourceConfig RESOURCE_CONFIG = new PackagesResourceConfig("br.com.netshoes");
	private HttpServer server;

	public ServerManager() {
		Map<String, Object> lMapConfig = new HashMap<String, Object>();

		lMapConfig.put("com.sun.jersey.api.json.POJOMappingFeature", true);

		RESOURCE_CONFIG.setPropertiesAndFeatures(lMapConfig);
	}
	public void start() {

		try {
			LOGGER.info("Inicializando servidor...");

			this.server = GrizzlyServerFactory.createHttpServer(URI, RESOURCE_CONFIG);

			this.server.start();
		} catch (IOException lIOExc) {
			LOGGER.error("Inicializando servidor...", lIOExc);
			throw new RuntimeException("Falha ao inicializar o servidor.");
		}
	}

	public void stop() {
		LOGGER.info("Finalizando servidor...");

		this.server.shutdown();
	}
}
