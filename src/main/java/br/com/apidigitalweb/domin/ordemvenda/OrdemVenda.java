package br.com.apidigitalweb.domin.ordemvenda;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.converters.CalnalOrdemConverter;
import br.com.apidigitalweb.converters.StatusConverter;
import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.ordem.FinanceiroOrdem;
import br.com.apidigitalweb.domin.pessoa.Cliente;
import br.com.apidigitalweb.domin.pessoa.Contato;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.domin.pessoa.Funcionario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class OrdemVenda
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
public class OrdemVenda  extends BaseDomain implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * 0-WEB 1-ONSITE 2-TELEFONE 4-WHATSAPP 5-OUTROS
	 */
	@Convert(converter = CalnalOrdemConverter.class)
	private String canal;

	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

	//@DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE, pattern = "dd/MM/yyyy HH:mm:ss")
	private Date dataAbertura;

	//@DateFormat(iso = ISO.DATE, pattern = "dd/MM/yyyy HH:mm:ss")
	private Date dataProgramada;

	//	@DateFormat(iso = ISO.DATE, pattern = "dd/MM/yyyy HH:mm:ss")
	private Date dataConclusao;
	
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Funcionario vendedor;
	
	@ElementCollection
    @CollectionTable(  joinColumns = @JoinColumn(name = "id"))
	protected List<ItensOrdemVenda> itensOrdemVenda=new ArrayList<>();

	@Transient
	private double total;

	/**
	 * 
	 * Aberto Concluido Aguardandoproduto
	 */
	@Convert(converter = StatusConverter.class)
	private String status;
	
	@Embedded
	@Column(name = "id", nullable = true, insertable = true, updatable = true)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	protected Endereco endereco = new Endereco();

	@Embedded
	@Column(name = "id", nullable = true, insertable = true, updatable = true)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	protected Contato contato = new Contato();

	private String formaEntrega;
	
	private String setorentrega;
	
	private double valorEntrega;


	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "banco", column = @Column(name = "bancoOS")),
		@AttributeOverride(name = "faturavel", column = @Column(name = "faturavelOS")),
		@AttributeOverride(name = "datavencimento", column = @Column(name = "datavencimentoOS")),
		@AttributeOverride(name = "parcelas", column = @Column(name = "parcelasOS")) })
	@Column(name = "id", nullable = true, insertable = true, updatable = true)
	protected FinanceiroOrdem financeiroOrdem = new FinanceiroOrdem();
	 
}
