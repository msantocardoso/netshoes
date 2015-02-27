package br.com.netshoes.service;

import org.apache.log4j.Logger;

import br.com.netshoes.exception.CepInvalidoException;
import br.com.netshoes.exception.NenhumRegistroEncontradoException;
import br.com.netshoes.model.Endereco;
import br.com.netshoes.repository.EnderecoRepository;
import br.com.netshoes.util.CepTransform;

public class EnderecoService {

	private static final String MSG_CEP_INVALIDO = "Cep inv\u00e1lido!";
	private final Logger LOGGER = Logger.getLogger(EnderecoService.class);
	private final EnderecoRepository enderecoRepository = new EnderecoRepository();

	public Endereco buscaPorCep(String cep) throws CepInvalidoException {
		this.validar(cep);

		Endereco lEndereco = this.busca(cep);

		return lEndereco;
	}

	private Endereco busca(String cep) {
		Endereco lEndereco = null;

		CepTransform lTransform = new CepTransform(cep);

		String lCEP = cep;
		int lTentativa = 1;
		do {
			try {
				lCEP = lTransform.getCep();
				LOGGER.info("Tentativa #"+(lTentativa++) + " - Buscando endereço relacionado ao CEP: [" + lCEP + "]...");

				lEndereco = enderecoRepository.findByCep(lCEP);
				if(lEndereco!=null)
					break;
			} catch (NenhumRegistroEncontradoException lNaoEncontrado) {
				LOGGER.info("Nenhum endereço relacionado ao CEP: [" + lCEP + "]...");
			}
		} while (lTransform.hasNext());

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
