package br.com.apidigitalweb.domin.ordemvenda;

import java.io.Serializable;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import br.com.apidigitalweb.converters.ItemOrdemVSCConverter;
import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.estoque.AnuncioContrato;
import br.com.apidigitalweb.domin.estoque.AnuncioLoja;
import br.com.apidigitalweb.domin.estoque.AnuncioWeb;
import br.com.apidigitalweb.domin.ordemservico.MaoObra;
import br.com.apidigitalweb.enuns.ItemOrdemVSCEnum;
import lombok.Data;

/**
 * Class ItensOrdemVenda
 */
@Data
@Embeddable
public class ItensOrdemVenda   implements  Serializable {

	private static final long serialVersionUID = 1L;

	private long idProduto;
	private int quantidade;
	private double valorUnitario;
	private double desconto;
	@Transient
	private double valorDesconto;
	@Transient
	private double valortotal;

	private String produto;
	/**
	 * 
	 * 0-AnuncioWeb 1-AnuncioLoja 2-AnuncioContrato
	 */
	@Convert(converter = ItemOrdemVSCConverter.class)
	private String origemProduto;

	public double getValorDesconto() {
		valorDesconto = 0;
		try {
			valorDesconto = valorUnitario * desconto / 100;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return valorDesconto;

	}

	public double getValortotal() {
		valortotal = 0;
		try {
			valortotal = valorUnitario - getValorDesconto();
		} catch (Exception e) {
			// TODO: handle exception
		}
		valortotal += valortotal * quantidade;
		return valortotal;

	}

	public ItensOrdemVenda(AnuncioLoja a) {
		super();
		this.idProduto = a.getId();
		this.quantidade = 0;
		this.valorUnitario = a.getValorFinal();
		this.desconto = a.getDesconto();
		this.produto = a.getNome();
		this.origemProduto = ItemOrdemVSCEnum.AnuncioLoja.getDescricao();
	}

	public ItensOrdemVenda(AnuncioWeb a) {
		super();
		this.idProduto = a.getId();
		this.quantidade = 0;
		this.valorUnitario = a.getValorFinal();
		this.desconto = a.getDesconto();
		this.produto = a.getNome();
		this.origemProduto = ItemOrdemVSCEnum.AnuncioLoja.getDescricao();
	}

	public ItensOrdemVenda(AnuncioContrato a) {
		super();
		this.idProduto = a.getId();
		this.quantidade = 0;
		this.valorUnitario = a.getValorFinal();
		this.desconto = 0;
		this.produto = a.getNome();
		this.origemProduto = ItemOrdemVSCEnum.AnuncioLoja.getDescricao();
	}
	public ItensOrdemVenda(MaoObra a) {
		super();
		this.idProduto = a.getId();
		this.quantidade = 0;
		this.valorUnitario = a.getValorTotal();
		this.desconto = 0;
		this.produto = a.getNome();
		this.origemProduto = ItemOrdemVSCEnum.MaoObra.getDescricao();
	}
}
