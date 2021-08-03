package br.com.apidigitalweb.domin.ordem;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.converters.SimNaoConverter;
import br.com.apidigitalweb.converters.StatusConverter;
import br.com.apidigitalweb.domin.estoque.ItemProdutoAnuncio;
import br.com.apidigitalweb.domin.estoque.Produto;
import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.domin.pessoa.Contato;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class FinanceiroOrdem implements Serializable {
	private static final long serialVersionUID = 1L;

	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" }) 
	private Banco banco;

	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" }) 
	protected CentroCusto centroCusto;

	@Convert(converter = SimNaoConverter.class)
	private String faturavel;

	private int parcelas;

	private Date datavencimento;

}
