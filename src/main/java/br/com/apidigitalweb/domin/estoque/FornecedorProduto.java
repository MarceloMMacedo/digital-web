package br.com.apidigitalweb.domin.estoque;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.domin.pessoa.Fornecedor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Embeddable
@NoArgsConstructor
public class FornecedorProduto implements Serializable {


	private static final long serialVersionUID = 1L;
 
	
	private String nome; 

  
	 
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Fornecedor fornecedor;

	private double valor;

	public FornecedorProduto(String nome, Fornecedor fornecedor, double valor) {
		super();
		this.nome = nome;
		this.fornecedor = fornecedor;
		this.valor = valor;
	}
}
