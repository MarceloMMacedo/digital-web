package br.com.apidigitalweb.dto.ordem;

import java.io.Serializable;

import javax.persistence.Convert;
import javax.persistence.Transient;

import br.com.apidigitalweb.converters.ItemOrdemVSCConverter;
import br.com.apidigitalweb.domin.financeiro.maoobra.MaoObra;
import br.com.apidigitalweb.domin.ordemcontrato.ItensMaoObraInContrato;
import br.com.apidigitalweb.domin.ordemcontrato.ItensMaterialInContratoLoja;
import br.com.apidigitalweb.domin.ordemservico.ItensMaoObraInService;
import br.com.apidigitalweb.domin.ordemservico.ItensMaterialInServiceLoja;
import br.com.apidigitalweb.domin.ordemvendaloja.ItensMaterialInVendaLoja;
import br.com.apidigitalweb.domin.ordemvendaweb.ItensMaterialInVendaWeb;
import br.com.apidigitalweb.dto.SampleDto;
import br.com.apidigitalweb.enuns.ItemOrdemVSCEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class ItensMaterialInContratoLoja
 */
@Data
@NoArgsConstructor
public class ItensInsOrdemDto   implements  Serializable {

	private static final long serialVersionUID = 1L;
	protected Long id;
	protected String nome;
	protected String descricao;
	protected String imagem;
	protected String extension;	 
	protected String imagemView;	
	private SampleDto anuncioLoja=new SampleDto();
	private SampleDto anuncio=new SampleDto();
	private int quantidade;
	private double valorUnitario;
	private double desconto;
	@Transient
	private double valorDesconto;
	@Transient
	private double valortotal;	
	private SampleDto ordem=new SampleDto();
 
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


	public ItensInsOrdemDto(MaoObra a) {
		super(); 
		anuncio=new SampleDto(a, "");
		this.quantidade = 0;
		this.valorUnitario = a.getValorTotal();
		this.desconto = 0;
		setNome(a.getNome());
		this.origemProduto = ItemOrdemVSCEnum.MaoObra.getDescricao();
	}
	public ItensInsOrdemDto(ItensMaoObraInService a) {
		super(); 
		anuncio=new SampleDto(a.getProduto(), "");
		this.quantidade = a.getQuantidade();
		this.valorUnitario = a.getValorUnitario();
		this.desconto = a.getDesconto();
		setNome(a.getNome());
		this.origemProduto = ItemOrdemVSCEnum.MaoObra.getDescricao();
		ordem=new SampleDto(a.getOrdem(), "");
	}
	
	public ItensInsOrdemDto(ItensMaterialInServiceLoja a) {
		super(); 
		anuncio=new SampleDto(a.getProduto(), "");
		this.quantidade = a.getQuantidade();
		this.valorUnitario = a.getValorUnitario();
		this.desconto = a.getDesconto();
		setNome(a.getNome());
		this.origemProduto = ItemOrdemVSCEnum.AnuncioLoja.getDescricao();
		ordem=new SampleDto(a.getOrdem(), "");
	}
	
	public ItensInsOrdemDto(ItensMaterialInVendaWeb a) {
		super(); 
		anuncio=new SampleDto(a.getProduto(), "");
		this.quantidade = a.getQuantidade();
		this.valorUnitario = a.getValorUnitario();
		this.desconto = a.getDesconto();
		setNome(a.getNome());
		this.origemProduto = ItemOrdemVSCEnum.AnuncioWeb.getDescricao();
		ordem=new SampleDto(a.getOrdem(), "");
	}
	
	
	public ItensInsOrdemDto(ItensMaterialInVendaLoja a) {
		super(); 
		anuncio=new SampleDto(a.getProduto(), "");
		this.quantidade = a.getQuantidade();
		this.valorUnitario = a.getValorUnitario();
		this.desconto = a.getDesconto();
		setNome(a.getNome());
		this.origemProduto = ItemOrdemVSCEnum.AnuncioLoja.getDescricao();
		ordem=new SampleDto(a.getOrdem(), "");
	}
	

	
	public ItensInsOrdemDto(ItensMaterialInContratoLoja a) {
		super(); 
		anuncio=new SampleDto(a.getProduto(), "");
		this.quantidade = a.getQuantidade();
		this.valorUnitario = a.getValorUnitario();
		this.desconto = a.getDesconto();
		setNome(a.getNome());
		this.origemProduto = ItemOrdemVSCEnum.AnuncioContrato.getDescricao();
		ordem=new SampleDto(a.getOrdem(), "");
	}
	public ItensInsOrdemDto(ItensMaoObraInContrato a) {
		super(); 
		anuncio=new SampleDto(a.getProduto(), "");
		this.quantidade = a.getQuantidade();
		this.valorUnitario = a.getValorUnitario();
		this.desconto = a.getDesconto();
		setNome(a.getNome());
		this.origemProduto = ItemOrdemVSCEnum.MaoObra.getDescricao();
		ordem=new SampleDto(a.getOrdem(), "");
	}
	
	
}
