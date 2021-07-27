package br.com.apidigitalweb.domin.contratos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.estoque.AnuncioContrato;
import br.com.apidigitalweb.domin.financeiro.GrupoFinanceiroContrato;
import br.com.apidigitalweb.domin.pessoa.Cliente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class Contrato
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Contrato extends BaseDomain implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1L;
 
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(mappedBy = "contrato", fetch = FetchType.LAZY, cascade = CascadeType.ALL,targetEntity =AnuncioContrato.class)
	private List<AnuncioContrato> movCentroCustos;
	
	@DateTimeFormat(iso = ISO.DATE, pattern = "dd/MM/yyyy")
	private Date dataInicio;
	private int periodo;
	private int diaLeitura;
	
	@DateTimeFormat(iso = ISO.DATE, pattern = "dd/MM/yyyy")
	private  Date diaVencimento;
	
	@ElementCollection
	@CollectionTable(joinColumns = @JoinColumn(name = "id"))
	private List<ItensPatrimonio> itenspatrimonio = new ArrayList<>();
	
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private GrupoFinanceiroContrato grupoFinanceiro;
	
	

	@Transient
	private double valorFinal;
 
	public double getValorFinal() {
		valorFinal = 0;
		try {
		for (ItensPatrimonio itensPatrimonio2 :itenspatrimonio) {
			valorFinal+=itensPatrimonio2.getValorFinal();
		}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return valorFinal;
	}
}
