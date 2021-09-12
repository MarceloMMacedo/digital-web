package br.com.apidigitalweb.domin.ordemservico;

import java.io.Serializable;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.converters.ItemOrdemVSCConverter;
import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.financeiro.maoobra.MaoObra;
import br.com.apidigitalweb.enuns.ItemOrdemVSCEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class ItensMaterialInContratoLoja
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
public class ItensMaoObraInService extends BaseDomain implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1L;
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private MaoObra produto;
	private int quantidade;
	private double valorUnitario;
	private double desconto;
	@Transient
	private double valorDesconto;
	@Transient
	private double valortotal;
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private OrdemServico ordem;
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

	/*
	 * public ItensMaoObraInContrato(AnuncioLoja a) { super(); this.idProduto =
	 * a.getId(); this.quantidade = 0; this.valorUnitario = a.getValorFinal();
	 * this.desconto = a.getDesconto(); this.produto = a.getNome();
	 * this.origemProduto = ItemOrdemVSCEnum.AnuncioLoja.getDescricao(); }
	 * 
	 * public ItensMaoObraInContrato(AnuncioWeb a) { super(); this.idProduto =
	 * a.getId(); this.quantidade = 0; this.valorUnitario = a.getValorFinal();
	 * this.desconto = a.getDesconto(); this.produto = a.getNome();
	 * this.origemProduto = ItemOrdemVSCEnum.AnuncioLoja.getDescricao(); }
	 * 
	 * public ItensMaoObraInContrato(AnuncioContrato a) { super(); this.idProduto =
	 * a.getId(); this.quantidade = 0; this.valorUnitario = a.getValorFinal();
	 * this.desconto = 0; this.produto = a.getNome(); this.origemProduto =
	 * ItemOrdemVSCEnum.AnuncioLoja.getDescricao(); }
	 */
	public ItensMaoObraInService(MaoObra a) {
		super();
		this.produto = a;
		this.quantidade = 0;
		this.valorUnitario = a.getValorTotal();
		this.desconto = 0;
		setNome(a.getNome());
		this.origemProduto = ItemOrdemVSCEnum.MaoObra.getDescricao();
	}
}
