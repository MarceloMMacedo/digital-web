package br.com.apidigitalweb.dto.reposicao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Transient;

import br.com.apidigitalweb.converters.TipoAnuncioConverter;
import br.com.apidigitalweb.domin.cotacao.Cotacao;
import br.com.apidigitalweb.domin.cotacao.ItensCotacao;
import br.com.apidigitalweb.domin.pessoa.Contato;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.dto.SampleDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CotacaoDto implements Serializable {





	private static final long serialVersionUID = 1L;

	@Data
	@NoArgsConstructor
	public class ItensCotacaoDto implements Serializable {

		private static final long serialVersionUID = 1L;

		private String descricao;
		private int qtd; 
		private Long anuncio;
		private double valorinterno;
		private double subtotal;
		private double valor;
		protected String unidade;
		  
	 

		@Convert(converter = TipoAnuncioConverter.class)
		private String tipoanuncio;

		@Transient
		private double total;
		 

		public ItensCotacaoDto(ItensCotacao i) {
			super();

			this.descricao = i.getDescricao(); 
			qtd=i.getQtd();
			this.valor = i.getValor();
			valorinterno=i.getValorinterno();
			this.anuncio = i.getAnuncio();
			this.tipoanuncio = i.getTipoanuncio();
			subtotal=i.getSubtotal();
			setUnidade(i.getUnidade());
			//this.total = i.getTotal();
		}


		public double getTotal() {
			total = 0;
			try {

				total = qtd * valor;
			} catch (Exception e) {
				// TODO: handle exception
			}
			return total;
		}

	}

	private Long id;
	private String nome;
	private Date dataAbertura;
	private Date dataFim;
	private SampleDto fornecedor=new SampleDto();;
	private Contato contato = new Contato();
	private Endereco endereco = new Endereco();
	private String status;
	private List<ItensCotacaoDto> itensCotacaos = new ArrayList<ItensCotacaoDto>();
	private double total;
	private String tipoFrete;
	private double valorFrete;

 

	public CotacaoDto(Cotacao c) {
		super();
		this.id = c.getId();
		this.nome = c.getDescricao();
		this.dataAbertura = c.getDataAbertura();
		this.dataFim = c.getDataFim();
		this.fornecedor = new SampleDto(c.getFornecedor(), "");
		if(c.getFornecedor().getEndereco()!=null)	endereco = c.getFornecedor().getEndereco();
		if( c.getFornecedor().getContato()!=null)	contato = c.getFornecedor().getContato();
		
		this.status = c.getStatus();
		 for (ItensCotacao i : c.getItensCotacaos()) {
			itensCotacaos.add(new ItensCotacaoDto(i));
		} 
	//	this.total = c.getTotal();
		this.tipoFrete = c.getTipoFrete();
		this.valorFrete = c.getValorFrete();
	}
	public double getTotal() {
		total=0;
		 for (ItensCotacaoDto i : getItensCotacaos()) {
			// System.out.println(  i.getTotal());
				total= total + i.getTotal();
			} 
		return total;
	}
}
