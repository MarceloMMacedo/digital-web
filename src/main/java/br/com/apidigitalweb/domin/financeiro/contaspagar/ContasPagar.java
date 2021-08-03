package br.com.apidigitalweb.domin.financeiro.contaspagar;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.converters.StatusConverter;
import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.domin.ordem.FinanceiroOrdem;
import br.com.apidigitalweb.domin.pessoa.Fornecedor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ContasPagar extends BaseDomain implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1L;
	
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Fornecedor fonercedor;

	private String descricao;
	private String nf;
	private double valor;
	private Date dataAbertura; 

	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" }) 
	@ManyToOne(fetch = FetchType.LAZY)
	private HistoricoContaPagar historico;

	@Convert(converter = StatusConverter.class)
	private String status;

	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" }) 
	@ManyToOne(fetch = FetchType.LAZY)
	private GrupoContaPagar grupoFinanceiro;
	
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(mappedBy = "contasPagar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<FaturaContasPagar> faturas;

	@Embedded
	@Column(name = "id", nullable = true, insertable = true, updatable = true)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private FinanceiroOrdem financeiro = new FinanceiroOrdem();

}
