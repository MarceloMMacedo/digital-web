package br.com.apidigitalweb.domin.contratos;

import java.io.Serializable;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.converters.SimNaoConverter;
import br.com.apidigitalweb.enuns.SimNaoEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class ItensPatrimonio implements Serializable {

	private static final long serialVersionUID = 1L;

	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Patrimonio patrimonio=new Patrimonio();
	private double valorUnitarioFranquia;
	@Convert(converter = SimNaoConverter.class)
	private String isFranquado;
	private int franquia;
	private double valorFranquia;
	private double valorPreDeterminado;

	@Transient
	private double valorFinal;

	public ItensPatrimonio(Patrimonio patrimonio, double valorUnitarioFranquia, String isFranquado, int franquia,
			double valorFranquia) {
		super();
		this.patrimonio = patrimonio;
		this.valorUnitarioFranquia = valorUnitarioFranquia;
		this.isFranquado = isFranquado;
		this.franquia = franquia;
		this.valorFranquia = valorFranquia;
	}

	public ItensPatrimonio(Object ID, Object FRANQUIA, Object IS_FRANQUADO, Object PATRIMONIO_ID, Object VALOR_FRANQUIA,
			Object VALOR_PRE_DETERMINADO, Object VALOR_UNITARIO_FRANQUIA) {

		this.franquia = (int) FRANQUIA;
		this.isFranquado = SimNaoEnum.getById((Integer) IS_FRANQUADO);
		this.patrimonio=new Patrimonio();
		this.patrimonio.setId((Long.valueOf(String.valueOf(PATRIMONIO_ID))) );
		this.valorFranquia = (double) VALOR_FRANQUIA;
		valorPreDeterminado = (double) VALOR_PRE_DETERMINADO;
		valorUnitarioFranquia = (double) VALOR_UNITARIO_FRANQUIA;

	}

	public ItensPatrimonio(ItensPatrimonio item) {
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
