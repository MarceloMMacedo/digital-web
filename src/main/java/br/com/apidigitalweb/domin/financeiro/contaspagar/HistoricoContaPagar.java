package br.com.apidigitalweb.domin.financeiro.contaspagar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.estoque.Produto;
import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;
import br.com.apidigitalweb.enuns.TipoFaturaEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class HistoricoContaPagar extends BaseDomain implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1L;
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Banco banco;

	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private CentroCusto centroCusto;

	@JsonIgnore
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private GrupoContaPagar grupocontaspagar;

	@JsonIgnore
	@OneToMany(mappedBy = "historico", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<FaturaContasPagar> produtos = new ArrayList<>();

	 

	@Transient
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<FaturasDto> faturasAberta = new LinkedList<>();

	@Transient
	private double saidasFuturo;
	
	@Transient
	private double totalPagar;
	public double getTotalPagar() {
		totalPagar = 0;
		try {
			for (FaturasDto faturasDto : getFaturasAberta()) {
				totalPagar += faturasDto.getTotal();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return totalPagar;
	}

}
