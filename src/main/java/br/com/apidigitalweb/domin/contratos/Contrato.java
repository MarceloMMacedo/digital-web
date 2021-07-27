package br.com.apidigitalweb.domin.contratos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.estoque.AnuncioContrato;
import br.com.apidigitalweb.domin.pessoa.Cliente;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;
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

	@JsonIgnore
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(mappedBy = "contrato", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = AnuncioContrato.class)
	private List<AnuncioContrato> anuncioContratos;

	@DateTimeFormat(iso = ISO.DATE, pattern = "dd/MM/yyyy")
	private Date dataInicio;
	private int periodo;
	private int diaLeitura;
 
	private int diaVencimento;

	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(mappedBy = "contrato", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = ItensContratoPatrimonio.class)
	private List<ItensContratoPatrimonio> itenspatrimonio =new ArrayList<ItensContratoPatrimonio>() ;

	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private GrupoFinanceiroContrato grupoFinanceiro;

	@JsonIgnore
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(mappedBy = "contrato", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = FaturaContrato.class)
	private List<FaturaContrato> faturaContratos;

	@Transient
	private double valorFinal;

	@Transient // To Do
	private List<FaturasDto> anunciosContratos = new ArrayList<FaturasDto>();

	@Transient // to do
	private List<FaturasDto> faturaAberta = new ArrayList<FaturasDto>();

	@Transient // To Do
	private List<FaturasDto> faturaQuit = new ArrayList<FaturasDto>();

	public double getValorFinal() {
		valorFinal = 0;
		try {
			for (ItensContratoPatrimonio itensPatrimonio2 : itenspatrimonio) {
				valorFinal += itensPatrimonio2.getValorFinal();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return valorFinal;
	}
}
