package br.com.apidigitalweb.domin.financeiro;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class AgregadoGrupoFinanceiro
 */
@Data
@Embeddable
@NoArgsConstructor
public class AgregadoGrupoFinanceiro  implements   Serializable {



	private static final long serialVersionUID = 1L;

	private String descricao;
	private Double percentual;

	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private CentroCusto centrocusto;
	
	public AgregadoGrupoFinanceiro(String descricao, Double percentual, CentroCusto centrocusto) {
		super();
		this.descricao = descricao;
		this.percentual = percentual;
		this.centrocusto = centrocusto;
	}
}
