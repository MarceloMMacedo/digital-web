package br.com.apidigitalweb.domin.financeiro.contaspagar;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.domin.contratos.Contrato;
import br.com.apidigitalweb.domin.financeiro.BaseFatura;
import br.com.apidigitalweb.domin.pessoa.Fornecedor;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.service.extrafatura.DatPeriodo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class FaturaContasPagar  extends BaseFatura implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@JsonIgnore
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private HistoricoContaPagar historico;
	
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private GrupoContaPagar grupofinanceiro;


	@JsonIgnore
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private ContasPagar contasPagar;
	
 
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Fornecedor fornecedor;
	
	
	public FaturaContasPagar	(DatPeriodo d, ContasPagar c) {
		this.fornecedor = c.getFonercedor();
		this.contasPagar = c;
		this.historico = c.getHistorico();
		this.nome =  c.getHistorico().getNome()+" - " + ""+d.getPeriodo(); 
		this.totalParcela = c.getFinanceiro().getParcelas();
		this.numeroparcela = d.getPeriodo();
		this.dataVencimento = d.getDate();
		this.status = StatusActiv.ABERTO.getDescricao();
		this.valor = c.getValor()/c.getFinanceiro().getParcelas();
		this.descricao=c.getDescricao();
		grupofinanceiro=c.getGrupoFinanceiro();
		try {
			this.banco = c.getFinanceiro().getBanco();
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			this.centroCusto = c.getFinanceiro().getCentroCusto();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}	
}
