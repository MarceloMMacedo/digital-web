package br.com.apidigitalweb.domin.estoque;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.converters.SimNaoConverter;
import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.financeiro.GrupoFinanceiroAnuncio;
import br.com.apidigitalweb.enuns.SimNaoEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class AnuncioServico extends BaseDomain implements Serializable, BaseEntity {

	private static final long serialVersionUID = 1L;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JoinColumn
	@ManyToOne(fetch = FetchType.LAZY)
	private GrupoFinanceiroAnuncio grupopreco;

	@Transient
	private double valorFinal = 0.0;

	private double valorInterno;

	private double desconto;

	@Convert(converter = SimNaoConverter.class)
	private String isPrecificado;

	private double valorPredefinido;

	public double getValorFinal() {
		if (isPrecificado.equals(SimNaoEnum.Sim.getDescricao())) {
			valorFinal = valorPredefinido;
		} else {
			valorFinal = getValorInterno();
			try {
				valorFinal += valorFinal * getGrupopreco().getPercentualTotal() / 100;
				valorFinal -= valorFinal * desconto / 100;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return valorFinal;
	}

	public double getDesconto() {
		try {
			if (desconto > getGrupopreco().getPercentualDesconto())
				desconto = getGrupopreco().getPercentualDesconto();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return desconto;
	}

}
