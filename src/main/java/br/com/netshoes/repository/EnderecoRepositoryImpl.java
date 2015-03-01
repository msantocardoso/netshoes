package br.com.netshoes.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import br.com.netshoes.infrastructure.exception.RepositoryException;
import br.com.netshoes.model.Endereco;
import br.com.netshoes.repository.services.WebServiceEndereco;

public class EnderecoRepositoryImpl implements EnderecoRepository {

	private EntityManager entityManager;

	public EnderecoRepositoryImpl() {}

	public EnderecoRepositoryImpl(EntityManager pEntityManager) {
		this.entityManager = pEntityManager;
	}

	@Override
	public void inserir(Endereco pEndereco) {
		EntityTransaction lTransaction = this.entityManager.getTransaction();
		try {
			lTransaction.begin();
			this.entityManager.persist(pEndereco);
			this.entityManager.flush();
		} catch (PersistenceException lPerException) {
			if(lTransaction.isActive())
				lTransaction.rollback();
			throw new RepositoryException(lPerException);
		} finally {
			lTransaction.commit();
		}
	}

	@Override
	public void atualizar(Endereco pEndereco) {
		EntityTransaction lTransaction = this.entityManager.getTransaction();
		try {
			lTransaction.begin();
			this.entityManager.merge(pEndereco);
			this.entityManager.flush();
		} catch (PersistenceException lPerException) {
			if(lTransaction.isActive())
				lTransaction.rollback();

			throw new RepositoryException(lPerException);
		} finally {
			lTransaction.commit();
		}
	}

	@Override
	public void remover(Endereco pEndereco) {

		EntityTransaction lTransaction = this.entityManager.getTransaction();
		try {
			lTransaction.begin();
			if (this.entityManager.contains(pEndereco)) {
				this.entityManager.remove(pEndereco);
			}
		} catch (PersistenceException lPerException) {
			if(lTransaction.isActive())
				lTransaction.rollback();
			throw new RepositoryException(lPerException);
		} finally {
			lTransaction.commit();
		}
	}

	@Override
	public Endereco buscarPorId(long pId) {
		Endereco lEndereco = null;
		try {
			lEndereco = this.entityManager.find(Endereco.class, pId);
		} catch (PersistenceException lPerException) {
			throw new RepositoryException(lPerException);
		}
		return lEndereco;
	}

	public Endereco buscarPorCep(String pCEP, WebServiceEndereco pWebServiceEndereco) {
		return pWebServiceEndereco.buscaPorCep(pCEP);
	}
}