package br.com.netshoes.service;

import br.com.netshoes.infrastructure.exception.CepInvalidoException;
import br.com.netshoes.model.Endereco;
import br.com.netshoes.repository.services.WebServiceEndereco;

public interface EnderecoService {

	void inserir(Endereco pEndereco) throws CepInvalidoException;

	void atualizar(Endereco pEndereco) throws CepInvalidoException;

	void remover(Endereco pEndereco);

	Endereco buscarPorId(long pId);

	Endereco buscarPorCep(String string, WebServiceEndereco pWebServiceEndereco) throws CepInvalidoException;
}
