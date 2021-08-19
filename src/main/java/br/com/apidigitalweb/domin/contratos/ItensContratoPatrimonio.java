package br.com.apidigitalweb.domin.contratos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.converters.SimNaoConverter;
import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.pessoa.Contato;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.enuns.SimNaoEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ItensContratoPatrimonio extends BaseDomain implements Serializable, BaseEntity {

	private static final long serialVersionUID = 1L;

	@JoinColumn
	@JsonIgnore
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Contrato contrato;

	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Patrimonio patrimonio ;

	private double valorUnitarioFranquia;

	@Convert(converter = SimNaoConverter.class)
	private String isFranquado;

	private int franquia;
	private double valorFranquia;
	private double valorPreDeterminado;

	private Date dataInstalacao;

	@Embedded
	@Column(name = "id", nullable = true, insertable = true, updatable = true)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Medidor medidorInstalacao=new Medidor();

	@Embedded
	@Column(name = "id", nullable = true, insertable = true, updatable = true)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	protected Endereco endereco = new Endereco();

	@Embedded
	@Column(name = "id", nullable = true, insertable = true, updatable = true)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	protected Contato contato = new Contato();

	@Transient
	private double valorFinal;

	private String setor;
	
	public ItensContratoPatrimonio(Patrimonio patrimonio, double valorUnitarioFranquia, String isFranquado, int franquia,
			double valorFranquia) {
		super();
		this.patrimonio = patrimonio;
		this.valorUnitarioFranquia = valorUnitarioFranquia;
		this.isFranquado = isFranquado;
		this.franquia = franquia;
		this.valorFranquia = valorFranquia;
	}

	public ItensContratoPatrimonio(Object ID, Object FRANQUIA, Object IS_FRANQUADO, Object PATRIMONIO_ID, Object VALOR_FRANQUIA,
			Object VALOR_PRE_DETERMINADO, Object VALOR_UNITARIO_FRANQUIA) {

		this.franquia = (int) FRANQUIA;
		this.isFranquado = SimNaoEnum.getById((Integer) IS_FRANQUADO);
		this.patrimonio = new Patrimonio();
		this.patrimonio.setId((Long.valueOf(String.valueOf(PATRIMONIO_ID))));
		this.valorFranquia = (double) VALOR_FRANQUIA;
		valorPreDeterminado = (double) VALOR_PRE_DETERMINADO;
		valorUnitarioFranquia = (double) VALOR_UNITARIO_FRANQUIA;

	}

	public ItensContratoPatrimonio(ItensContratoPatrimonio item) {
		super();
		this.patrimonio = item.getPatrimonio();
		this.valorUnitarioFranquia = item.getValorUnitarioFranquia();
		this.isFranquado = item.getIsFranquado();
		this.franquia = item.getFranquia();
		this.valorFranquia = item.getValorFranquia();
		valorPreDeterminado = item.getValorPreDeterminado();
	}

	public double getValorFinal() {
		valorFinal = 0;
		try {
			if (isFranquado == SimNaoEnum.Sim.getDescricao())
				valorFinal = franquia * valorUnitarioFranquia;
			else
				valorFinal = valorPreDeterminado;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return valorFinal;
	}

}
