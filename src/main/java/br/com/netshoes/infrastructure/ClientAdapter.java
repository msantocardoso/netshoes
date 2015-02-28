package br.com.netshoes.infrastructure;

import br.com.netshoes.infrastructure.exception.JerseyClientException;

public interface ClientAdapter<T> {

	public <P> T get(P pParam) throws JerseyClientException;

	public <P> T post(P pParam) throws JerseyClientException;

	public <P> T put(P pParam) throws JerseyClientException;

	public <P> T delete(P pParam) throws JerseyClientException;
}
