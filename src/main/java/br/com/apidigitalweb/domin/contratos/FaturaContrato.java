package br.com.apidigitalweb.domin.contratos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.converters.SimNaoConverter;
import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.BaseFatura;
import br.com.apidigitalweb.domin.pessoa.Cliente;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.service.extrafatura.DatPeriodo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class FaturaContrato
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class FaturaContrato extends BaseFatura implements Serializable {

	private static final long serialVersionUID = 1L;

	private double valorContrato;

	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

	@JoinColumn()
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Contrato contrato;

	@ElementCollection	 
	@CollectionTable(joinColumns = @JoinColumn(name = "id"))
	private List<FichaLeitura> fichaLeitura = new ArrayList<FichaLeitura>();

	@JoinColumn()
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private GrupoFinanceiroContrato grupoFinanceiro;

	public FaturaContrato(String nome, String descricao, int totalParcela, int numeroparcela, Date dataVencimento,
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

	public FaturaContrato(DatPeriodo d, Contrato c) {
		this.cliente = c.getCliente();
		this.contrato = c;
		this.grupoFinanceiro = c.getGrupoFinanceiro();
		this.nome = "Contrato nro.:" + "" + c.getId(); 
		this.totalParcela = c.getPeriodo();
		this.numeroparcela = d.getPeriodo();
		this.dataVencimento = d.getDate();
		this.status = StatusActiv.ABERTO.getDescricao();
		this.valor = c.getValorFinal();
		try {
			this.banco = c.getGrupoFinanceiro().getBanco();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
