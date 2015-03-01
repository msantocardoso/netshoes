package br.com.netshoes.repository;

import br.com.netshoes.infrastructure.exception.RepositoryException;
import br.com.netshoes.model.Endereco;
import br.com.netshoes.repository.services.WebServiceEndereco;

public interface EnderecoRepository {

	void inserir(Endereco pEndereco) throws RepositoryException;

	void atualizar(Endereco pEndereco) throws RepositoryException;

	void remover(Endereco pEndereco) throws RepositoryException;

	Endereco buscarPorId(long pId) throws RepositoryException;

	Endereco buscarPorCep(String string, WebServiceEndereco pWebServiceEndereco) throws RepositoryException;
}
