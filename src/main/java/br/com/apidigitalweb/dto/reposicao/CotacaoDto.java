package br.com.apidigitalweb.dto.reposicao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;

import br.com.apidigitalweb.converters.StatusConverter;
import br.com.apidigitalweb.domin.cotacao.Cotacao;
import br.com.apidigitalweb.domin.cotacao.ItensCotacao;
import br.com.apidigitalweb.domin.pessoa.Contato;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.domin.pessoa.Fornecedor;
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
		private SampleDto produto;

		private int quantidade;
		private double valor;

		private SampleDto anuncio;

		private String tipoAnuncio;

		private double total;

		public ItensCotacaoDto(ItensCotacao i) {
			super();
			this.produto = new SampleDto(i.getProduto(), "");
			this.quantidade = i.getQuantidade();
			this.valor = i.getValor();
			this.anuncio = (new SampleDto(i.getAnuncio(), ""));
			this.tipoAnuncio = i.getTipoAnuncio();
			this.total = i.getTotal();
		}
	}
	private Long id;
	private String nome; 
	private Date dataAbertura;
	private Date dataFim;
	private SampleDto fornecedor;
	private Contato contato = new Contato();
	private Endereco endereco = new Endereco();
	private String status;
	private List<ItensCotacaoDto> itensCotacaos = new ArrayList<ItensCotacaoDto>();
	private double total;
	private String tipoFrete;
	private double valorFrete;

	public CotacaoDto(Cotacao c) {
		super();
		this.dataAbertura = c.getDataAbertura();
		this.dataFim = c.getDataFim();
		this.fornecedor = new SampleDto(c.getFornecedor(), "");
		this.status = c.getStatus();
		for (ItensCotacao i : c.getItensCotacaos()) {
			itensCotacaos.add(new ItensCotacaoDto(i));
		}
		this.total = c.getTotal();
		this.tipoFrete = c.getTipoFrete();
		this.valorFrete = c.getValorFrete();
		endereco = c.getFornecedor().getEndereco();
		contato = c.getFornecedor().getContato();
	}

}
