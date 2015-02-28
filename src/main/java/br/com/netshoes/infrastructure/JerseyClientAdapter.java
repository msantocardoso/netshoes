package br.com.netshoes.infrastructure;

import javax.ws.rs.core.Response;

import br.com.netshoes.infrastructure.exception.JerseyClientException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

public class JerseyClientAdapter<T> implements ClientAdapter<T> {

	private Class<T> classType;
	private String mediaType;

	private Client client;
	private WebResource webResource;

	public JerseyClientAdapter(String pURL, String pMediaType, Class<T> pClassType) {

		this.classType = pClassType;
		this.mediaType = pMediaType;

		this.client = Client.create();
		this.webResource = client.resource(pURL);
	}

	private Builder getWebResourceBuilder(String pParam) {
		return webResource.path(pParam).accept(this.mediaType);
	}

	public <P> T get(P pParam) {
		T lResult = null;
		try {
			lResult = this.getWebResourceBuilder((String) pParam).get(classType);
		} catch (UniformInterfaceException | ClientHandlerException lClientExc) {
			if(lClientExc instanceof UniformInterfaceException)
				throw this.wrapperException((UniformInterfaceException) lClientExc);

			throw new JerseyClientException(lClientExc);
		}

		return lResult;
	}

	private JerseyClientException wrapperException(UniformInterfaceException pInterfaceException) {

		return new JerseyClientException(Response.status(pInterfaceException.getResponse().getStatus()).entity(pInterfaceException.getResponse().getEntity(String.class)).type(pInterfaceException.getResponse().getType()).build());
	}

	public <P> T post(P pParam) {

		T lResult = null;
		try {
			lResult = this.getWebResourceBuilder((String) pParam).post(classType);
		} catch (UniformInterfaceException | ClientHandlerException lClientExc) {
			if(lClientExc instanceof UniformInterfaceException)
				throw this.wrapperException((UniformInterfaceException) lClientExc);

			throw new JerseyClientException(lClientExc);
		}

		return lResult;
	}

	public <P> T put(P pParam) {

		T lResult = null;
		try {
			lResult = this.getWebResourceBuilder((String) pParam).put(classType);
		} catch (UniformInterfaceException | ClientHandlerException lClientExc) {
			if(lClientExc instanceof UniformInterfaceException)
				throw this.wrapperException((UniformInterfaceException) lClientExc);

			throw new JerseyClientException(lClientExc);
		}

		return lResult;
	}

	public <P> T delete(P pParam) {

		T lResult = null;
		try {
			lResult = this.getWebResourceBuilder((String) pParam).delete(classType);
		} catch (UniformInterfaceException | ClientHandlerException lClientExc) {
			if(lClientExc instanceof UniformInterfaceException)
				throw this.wrapperException((UniformInterfaceException) lClientExc);

			throw new JerseyClientException(lClientExc);
		}

		return lResult;
	}
}

