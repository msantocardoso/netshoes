package br.com.netshoes.infrastructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModificadorCaracteres {

	private int indice;

	private String base;

	private StringBuilder referencia;

	private char novo;

	private final List<String> modificacoes = new ArrayList<String>(8);

	public ModificadorCaracteres(String pBase, char pNovo) {
		this.base = pBase;
		this.referencia = new StringBuilder(pBase);
		this.indice = pBase.length();
		this.novo = pNovo;
	}

	public String getCep() {

		if(!primeiraExecucao())
			transformar();

		String referencia = this.referencia.toString();

		this.proximoIndice();

		return referencia;
	}

	private void transformar() {
		while(!caracterAtualValido() && dentroDoIntervalo()) {
			this.proximoIndice();
		}

		modificarCaracter();

		this.modificacoes.add(this.referencia.toString());
	}

	private void modificarCaracter() {
		this.referencia.setCharAt(this.indice, this.novo);
	}

	private boolean primeiraExecucao() {
		return this.indice == referencia.length();
	}

	private boolean caracterAtualValido() {
		return this.referencia.charAt(this.indice) != this.novo;
	}

	private boolean dentroDoIntervalo() {
		return (this.indice >= 0 && this.indice < referencia.length());
	}

	public List<String> getModificacoes() {
		return Collections.unmodifiableList(this.modificacoes);
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