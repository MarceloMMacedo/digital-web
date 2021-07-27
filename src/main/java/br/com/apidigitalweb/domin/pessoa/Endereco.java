package br.com.apidigitalweb.domin.pessoa;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class Endereco implements Serializable { 
	private static final long serialVersionUID = 1L;
	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String localidade;
	private String numero;
	private String uf;
	private String ibge;
	private String gia;
	private String ddd;
	private String siafi;

	public Endereco(String cep, String logradouro, String complemento, String bairro, String localidade, String numero,
			String uf) {
		super();
		this.cep = cep;
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.bairro = bairro;
		this.localidade = localidade;
		this.numero = numero;
		this.uf = uf;
	}

}
