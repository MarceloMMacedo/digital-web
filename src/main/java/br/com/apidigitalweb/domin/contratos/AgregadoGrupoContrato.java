package br.com.apidigitalweb.domin.contratos;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class AgregadoGrupoContrato
 */

@Embeddable
@Data
@NoArgsConstructor
public class AgregadoGrupoContrato {
 
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private CentroCusto centroCusto;
	private double percentual;
	private String nome;

	 

}
