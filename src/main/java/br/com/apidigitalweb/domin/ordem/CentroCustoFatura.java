package br.com.apidigitalweb.domin.ordem;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.domin.financeiro.BaseFatura;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class CentroCustoFatura implements Serializable {

	protected static final long serialVersionUID = 1L;
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private CentroCusto centroCusto;

	private double pecentual;

	private double valor;

	@Transient
	private double valorFinal;

	public CentroCustoFatura(CentroCusto centroCusto, double valor) {
		super();
		this.centroCusto = centroCusto;
		this.valor = valor;
	}

	/*
	 * public double getValor() { valor=0; try { valor=fatura.getTotal() * } catch
	 * (Exception e) { // TODO: handle exception } return valor; }
	 */

	public double getValorFinal() {
		valorFinal = 0;
		try {
			
			valorFinal = valor * pecentual / 100;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return valorFinal;
	}

}
