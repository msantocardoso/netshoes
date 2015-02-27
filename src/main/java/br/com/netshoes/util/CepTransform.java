package br.com.netshoes.util;

import java.util.ArrayList;
import java.util.List;

public class CepTransform {

	private int indice;

	private String base;

	private StringBuilder cep;

	private final List<String> transformacoes = new ArrayList<String>(8);

	public CepTransform(String cep) {
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

		this.transformacoes.add(this.cep.toString());
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

	public List<String> getTransformacoes() {
		return this.transformacoes;
	}

	private void proximoIndice() {
		indice--;
	}

	public boolean hasNext() {
		return (indice > 0);
	}

	public String getBase() {
		return this.base;
	}
}