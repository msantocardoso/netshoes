package br.com.netshoes;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class NetShoesApplication extends ResourceConfig {

	public NetShoesApplication() {
		packages("br.com.netshoes");
	}
}
