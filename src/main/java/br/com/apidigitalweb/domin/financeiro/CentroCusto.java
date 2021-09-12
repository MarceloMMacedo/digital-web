package br.com.apidigitalweb.domin.financeiro;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.contratos.FaturaContrato;
import br.com.apidigitalweb.domin.financeiro.contaspagar.FaturaContasPagar;
import br.com.apidigitalweb.domin.ordemservico.FaturaOrdemServico;
import br.com.apidigitalweb.domin.ordemvendaloja.FaturaVendaLoja;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class CentroCusto
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
public class CentroCusto extends BaseDomain implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1L;

	//
	// Fields
	//

	private Double valorPagar;
	private Double valorReceber;
	private Double saldo;
	
	@JsonIgnore
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(mappedBy = "centroCusto", fetch = FetchType.LAZY, cascade = CascadeType.ALL,targetEntity =MovCentroCusto.class)
	private List<MovCentroCusto> movCentroCustos;
	
	@Transient
	private double entradasFuturo;

	@Transient
	private double saidasFuturo;

	@Transient 
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<FaturasDto> faturasAbertaEntrada = new LinkedList<>();

	@Transient 
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<FaturasDto> faturasaAbertaPagar = new LinkedList<>();
	@Transient
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<FaturasDto> faturasAberta = new LinkedList<>();

	@Transient
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<MovBanco> entradasRealizados = new LinkedList<>();
	
	 
	@Transient
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<MovBanco> saidasRealizados = new LinkedList<>();

	@JsonIgnore
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(mappedBy = "banco", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = MovBanco.class)
	private List<MovBanco> movBanco;


	public CentroCusto(Double valorPagar, Double valorReceber, Double saldo, String descricao) {
		super();
		this.valorPagar = valorPagar;
		this.valorReceber = valorReceber;
		this.saldo = saldo;
		setDescricao(descricao);
	}
	public List<FaturasDto> getFaturasAberta() {
		for (FaturasDto f : faturasaAbertaPagar) {
			f.setValor(f.getValor() * -1);
		}
		faturasAberta.addAll(faturasAbertaEntrada);
		faturasAberta.addAll(faturasaAbertaPagar);
		return faturasAberta;
	}
}
