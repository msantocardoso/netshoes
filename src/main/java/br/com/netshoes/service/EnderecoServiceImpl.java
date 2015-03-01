package br.com.netshoes.service;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import br.com.netshoes.infrastructure.ModificadorCep;
import br.com.netshoes.infrastructure.exception.CepInvalidoException;
import br.com.netshoes.infrastructure.exception.EnderecoInvalidoException;
import br.com.netshoes.infrastructure.exception.NenhumRegistroEncontrado;
import br.com.netshoes.model.Endereco;
import br.com.netshoes.repository.EnderecoRepository;
import br.com.netshoes.repository.EnderecoRepositoryImpl;
import br.com.netshoes.repository.services.WebServiceEndereco;
import br.com.netshoes.repository.services.WebServiceEnderecoNetshoes;

public class EnderecoServiceImpl implements EnderecoService {

	private static final String LINE_BREAK = System.getProperty("line.separator");

	private final Logger LOGGER = Logger.getLogger(EnderecoServiceImpl.class);

	private final String MSG_CEP_INVALIDO = "Cep inv\u00e1lido!";

	private final EnderecoRepository enderecoRepository;

	public EnderecoServiceImpl(EntityManager pEntityManager) {
		this.enderecoRepository = new EnderecoRepositoryImpl(pEntityManager);
	}

	public EnderecoServiceImpl() {
		this.enderecoRepository = new EnderecoRepositoryImpl();
	}

	private void validar(String cep) throws CepInvalidoException {
		if (cep == null || cep.isEmpty()) {
			throw new CepInvalidoException("Cep \u00e9 obrigat\u00f3rio!");
		}

		if ("00000000".equals(cep) || "00000-000".equals(cep)) {
			throw new CepInvalidoException(MSG_CEP_INVALIDO);
		}

		if (cep.contains("-") && !cep.matches("\\d{5}-\\d{3}")) {
			throw new CepInvalidoException(MSG_CEP_INVALIDO);
		}

		if (!cep.matches("\\d{8}")) {
			throw new CepInvalidoException(MSG_CEP_INVALIDO);
		}
	}

	private void validar(Endereco pEndereco) throws CepInvalidoException {

		StringBuilder lErros = new StringBuilder();

		if (pEndereco == null) {
			lErros.append("Campo endereço é obrigatório!");
			lErros.append(LINE_BREAK);
		}

		this.buscarPorCep(pEndereco.getCep(), new WebServiceEnderecoNetshoes());

		if (pEndereco.getRua() == null || pEndereco.getRua().isEmpty()) {
			lErros.append("Campo rua é obrigatório!");
			lErros.append(LINE_BREAK);
		}

		if (pEndereco.getNumero() == null) {
			lErros.append("Campo número é obrigatório!");
			lErros.append(LINE_BREAK);
		}

		if (pEndereco.getCidade() == null || pEndereco.getCidade().isEmpty()) {
			lErros.append("Campo cidade é obrigatório!");
			lErros.append(LINE_BREAK);
		}

		if (pEndereco.getEstado() == null || pEndereco.getEstado().isEmpty()) {
			lErros.append("Campo estado é obrigatório!");
			lErros.append(LINE_BREAK);
		}

		if(lErros.length()>0) {
			StringBuilder lMensagemErro = new StringBuilder("Ocorreu os seguintes erros durante a execução do serviço: " + LINE_BREAK);

			lMensagemErro.append(lErros.toString());

			throw new EnderecoInvalidoException(lMensagemErro.toString());
		}
	}

	@Override
	public void inserir(Endereco pEndereco) throws CepInvalidoException {
		this.validar(pEndereco);

		this.enderecoRepository.inserir(pEndereco);
	}

	@Override
	public void atualizar(Endereco pEndereco) throws CepInvalidoException {
		this.validar(pEndereco);

		this.enderecoRepository.atualizar(pEndereco);
	}

	@Override
	public void remover(Endereco pEndereco) {
		this.enderecoRepository.remover(pEndereco);
	}

	@Override
	public Endereco buscarPorId(long pId) {
		return this.enderecoRepository.buscarPorId(pId);
	}

	@Override
	public Endereco buscarPorCep(String cep, WebServiceEndereco pWebServiceEndereco) throws CepInvalidoException {
		this.validar(cep);

		Endereco lEndereco = null;
		ModificadorCep lModificador = new ModificadorCep(cep);

		String lCEP = null;
		int lTentativa = 1;
		do {
			lCEP = lModificador.getCep();

			LOGGER.info("Tentativa #" + (lTentativa++) + " - Buscando endereço relacionado ao CEP: [" + lCEP + "]...");
			try {
				lEndereco = this.enderecoRepository.buscarPorCep(lCEP, pWebServiceEndereco);
				if (lEndereco != null)
					break;
			} catch (NenhumRegistroEncontrado lNaoEncontrado) {
				LOGGER.info("Nenhum endereço relacionado ao CEP: [" + cep + "]...");
			}
		} while (lModificador.temProximo());

		return lEndereco;
	}
}