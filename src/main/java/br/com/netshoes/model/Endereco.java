package br.com.netshoes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "endereco")
@Entity
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(nullable = false, length = 8)
	private String cep;

	@Column(nullable = false, length = 50)
	private String rua;

	@Column(nullable = false, length = 50)
	private Integer numero;

	@Column(nullable = true, length = 50)
	private String complemento;

	@Column(nullable = true, length = 30)
	private String bairro;

	@Column(nullable = false, length = 30)
	private String cidade;

	@Column(nullable = false, length = 30)
	private String estado;

	@Override
	public String toString() {
		StringBuilder lJSON = new StringBuilder();

		lJSON.append("{");
		lJSON.append("id: " + this.id);
		lJSON.append(", cep: " + this.cep);
		lJSON.append(", rua: " + this.rua);
		lJSON.append(", complemento: " + this.complemento);
		lJSON.append(", bairro: " + this.bairro);
		lJSON.append(", cidade: " + this.cidade);
		lJSON.append(", estado: " + this.estado);
		lJSON.append("}");

		return lJSON.toString();
	}

	public long getId() {
		return id;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}