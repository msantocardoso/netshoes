package br.com.netshoes.repository.services;

import br.com.netshoes.infrastructure.exception.RepositoryException;
import br.com.netshoes.model.Endereco;

public interface WebServiceEndereco {

	Endereco buscaPorCep(String cep) throws RepositoryException;
}
