package br.com.apidigitalweb.domin.financeiro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.converters.SimNaoConverter;
import br.com.apidigitalweb.converters.StatusConverter;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.ordem.CentroCustoFatura;
import br.com.apidigitalweb.domin.pessoa.Contato;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.domin.pessoa.QualName;
import br.com.apidigitalweb.domin.pessoa.TextCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class BaseFatura implements Serializable, BaseEntity {

	protected static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	protected Long id;
	protected String nome;
	protected String descricao;
	protected String imagem;
	protected String extension;

	@Transient
	protected String imagemView;

	protected int totalParcela;
	protected int numeroparcela;
	@DateTimeFormat(iso = ISO.DATE_TIME, pattern = "dd/MM/yyyy HH:mm:ss")
	protected Date dataVencimento;
	@DateTimeFormat(iso = ISO.DATE_TIME, pattern = "dd/MM/yyyy HH:mm:ss")
	protected Date dataPagamento;
	@DateTimeFormat(iso = ISO.DATE_TIME, pattern = "dd/MM/yyyy HH:mm:ss")
	private Date diaQuitacao;
	private String referente;
	@Convert(converter = StatusConverter.class)
	protected String status;
	protected double valor;
	protected double jurus;
	protected double multa;
	protected double desconto;
	@Transient
	protected double total;

	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	protected Banco banco;

	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	protected CentroCusto centroCusto;
	
	//@ElementCollection
   // @CollectionTable(  joinColumns = @JoinColumn(name = "id"))
	@Transient
	protected List<CentroCustoFatura> centroCustoFaturas=new ArrayList<>();
	
	 
	

	public double getTotal() {
		total = 0;
		try {
			total = valor - desconto + jurus + multa;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return total;
	}




	public BaseFatura(String nome, String descricao, int totalParcela, int numeroparcela, Date dataVencimento,
			String status, double valor, Banco banco) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.totalParcela = totalParcela;
		this.numeroparcela = numeroparcela;
		this.dataVencimento = dataVencimento;
		this.status = status;
		this.valor = valor;
		this.banco = banco;
	}

}
