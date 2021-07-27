package br.com.apidigitalweb.domin.financeiro;

import java.io.Serializable;
import java.util.ArrayList;
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
import br.com.apidigitalweb.domin.ordemvenda.FaturaVenda;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Banco extends BaseDomain implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1L;

	private String banco;
	private String agencia;
	private String conta;
	private double saldo;

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

	public void setBanco(double entradasFuturo, double saidasFuturo) {

		this.entradasFuturo = entradasFuturo;
		this.saidasFuturo = saidasFuturo;

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
