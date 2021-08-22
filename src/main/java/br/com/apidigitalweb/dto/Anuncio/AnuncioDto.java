package br.com.apidigitalweb.dto.Anuncio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.apidigitalweb.controller.estoque.ListaImagens;
import br.com.apidigitalweb.domin.estoque.AnuncioContrato;
import br.com.apidigitalweb.domin.estoque.BaseAnuncio;
import br.com.apidigitalweb.domin.estoque.DescricaoAnuncio;
import br.com.apidigitalweb.domin.estoque.ItemProdutoAnuncio;
import br.com.apidigitalweb.dto.SampleDto;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class AnuncioDto  implements Serializable {

	private static final long serialVersionUID = 1L;
 
	protected Long id;
	protected String nome;
	protected String descricao;
	protected String imagem;
	protected String extension;
 
	protected String imagemView;
	 
	private SampleDto grupopreco=new SampleDto();

	 
	private  Date dataVencimento;
	 
	private String status;
	private Integer saldo = 0;
	private Integer saldoMinimo = 0;
	private Integer saldoReserva = 0;
	private Integer saldoMaximo = 0;
	private double peso;
	private double largura;
	private double comprimento;
	private double altura;
	private double desconto;


	private String isPrecificado;

	private String unidade;


	private double valorInterno;


	private double valorFinal = 0.0;

	private double saldoReposicao;

	private List<ItemProdutoAnuncioDto> itensProduto = new ArrayList<ItemProdutoAnuncioDto>();


	private List<DescricaoAnuncio> descricoes = new ArrayList<DescricaoAnuncio>();


	private List<ListaImagens> imagens = new ArrayList<ListaImagens>();


	private SampleDto tocontrato =new SampleDto();
 
	

	public void setAnuncioDto(BaseAnuncio b) {
		 
		this.id = b.getId();
		this.nome = b.getNome();
		this.descricao = b.getDescricao();
		this.imagem = b.getImagem();
		this.extension = b.getExtension();
		this.imagemView = b.getImagemView();
		try {
		this.grupopreco = new SampleDto(b.getGrupopreco(),"");
		}catch (Exception e) {
			// TODO: handle exception
		}
		this.dataVencimento = b.getDataVencimento();
		this.status = b.getStatus();
		this.saldo = b.getSaldo();
		this.saldoMinimo = b.getSaldoMinimo();
		this.saldoReserva = b.getSaldoReserva();
		this.saldoMaximo = b.getSaldoMaximo();
		this.peso = b.getPeso();
		this.largura = b.getLargura();
		this.comprimento = b.getComprimento();
		this.altura = b.getAltura();
		this.desconto = b.getDesconto();
		this.isPrecificado = b.getIsPrecificado();
		this.unidade = b.getUnidade();
		this.valorInterno = b.getValorInterno();
		this.valorFinal = b.getValorFinal();
		this.saldoReposicao = b.getSaldoReposicao();
		  
		for (ItemProdutoAnuncio  i: b.getItensProduto()) {
			this.itensProduto.add(new ItemProdutoAnuncioDto(i));
		};
		this.descricoes = b.getDescricoes();
		this.imagens = b.getImagens();
		//this.contrato = new SampleDto(b.getco);
	}



	public AnuncioDto(BaseAnuncio b) {
		super();
		 setAnuncioDto(b);
	}
	public AnuncioDto(AnuncioContrato b ) {
		super();
		 setAnuncioDto(b);
			try {
				tocontrato = new SampleDto(b.getContrato(), "");
			} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
