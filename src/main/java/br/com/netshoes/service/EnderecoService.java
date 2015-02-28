package br.com.netshoes.service;

import org.apache.log4j.Logger;

import br.com.netshoes.infrastructure.ModificadorCep;
import br.com.netshoes.infrastructure.exception.CepInvalidoException;
import br.com.netshoes.infrastructure.exception.NenhumRegistroEncontrado;
import br.com.netshoes.model.Endereco;
import br.com.netshoes.repository.EnderecoRepository;

public class EnderecoService {
	private final Logger LOGGER = Logger.getLogger(EnderecoService.class);

	private final String MSG_CEP_INVALIDO = "Cep inv\u00e1lido!";

	private final EnderecoRepository enderecoRepository = new EnderecoRepository();

	public Endereco buscaPorCep(String cep) throws CepInvalidoException {
		this.validar(cep);

		Endereco lEndereco = null;
		ModificadorCep lModificador = new ModificadorCep(cep);

		String lCEP = null;
		int lTentativa = 1;
		do {
			lCEP = lModificador.getCep();

			LOGGER.info("Tentativa #"+(lTentativa++) + " - Buscando endereço relacionado ao CEP: [" + lCEP + "]...");
			try {
				lEndereco = enderecoRepository.buscaPorCep(lCEP);
				if(lEndereco!=null)
					break;
			} catch (NenhumRegistroEncontrado lNaoEncontrado) {
				LOGGER.info("Nenhum endereço relacionado ao CEP: [" + cep + "]...");
			}
		} while (lModificador.temProximo());

		return lEndereco;
	}

	private void validar(String cep) throws CepInvalidoException {
		if(cep == null || cep.isEmpty() ) {
			throw new CepInvalidoException("Cep \u00e9 obrigat\u00f3rio!");
		}

		if("00000000".equals(cep) || "00000-000".equals(cep)) {
			throw new CepInvalidoException(MSG_CEP_INVALIDO);
		}

		if(cep.contains("-") && !cep.matches("\\d{5}-\\d{3}")) {
			throw new CepInvalidoException(MSG_CEP_INVALIDO);
		}

		if(!cep.matches("\\d{8}")) {
			throw new CepInvalidoException(MSG_CEP_INVALIDO);
		}
	}
}
