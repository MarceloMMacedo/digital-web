package br.com.apidigitalweb.domin.estoque;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class AnuncioLoja
 */
@Data
@Embeddable
@NoArgsConstructor
public class ItemProdutoAnuncio implements Serializable {

	private static final long serialVersionUID = 1L;
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Produto produto;
	private double quantidade;
	@Transient
	private double valor;

	@Transient
	private double subtotal;
	@Transient
	private String descricao;

	public ItemProdutoAnuncio(Produto produto, double quantidade, double valor, String descricao) {
		super();
		this.produto = produto;
		this.quantidade = quantidade;
	}

	public double getValor() {
		this.valor = 0.0;
		try {
			return produto.getValorfinal();
		} catch (Exception e) {
		}
		return valor;
	}

	public String getDescricao() { 
		try {return produto.getDescricao();}
		catch (Exception e) {
			 
		}
		return descricao;
	}

	public double getSubtotal() {
		subtotal=0;
		subtotal+=getQuantidade() * getValor();
		return subtotal;
	}

	 

}