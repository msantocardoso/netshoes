package br.com.netshoes.infrastructure;

import java.util.ArrayList;
import java.util.List;

public class ModificadorCep {

	private int indice;

	private String base;

	private StringBuilder cep;

	private final List<String> modificacoes = new ArrayList<String>(8);

	public ModificadorCep(String cep) {
		this.base = cep;
		this.cep = new StringBuilder(cep);
		this.indice = cep.length();
	}

	public String getCep() {

		if(!primeiraExecucao())
			transformar();

		String cep = this.cep.toString();

		this.proximoIndice();

		return cep;
	}

	private void transformar() {
		while(!caracterAtualValido() && dentroDoIntervalo()) {
			this.proximoIndice();
		}

		modificarCaracter();

		this.modificacoes.add(this.cep.toString());
	}

	private void modificarCaracter() {
		this.cep.setCharAt(this.indice, '0');
	}

	private boolean primeiraExecucao() {
		return this.indice == cep.length();
	}

	private boolean caracterAtualValido() {
		return this.cep.charAt(this.indice) != '0';
	}

	private boolean dentroDoIntervalo() {
		return (this.indice >= 0 && this.indice < cep.length());
	}

	public List<String> getModificacoes() {
		return this.modificacoes;
	}

	private void proximoIndice() {
		indice--;
	}

	public boolean temProximo() {
		return (indice > 0);
	}

	public String getBase() {
		return this.base;
	}
}