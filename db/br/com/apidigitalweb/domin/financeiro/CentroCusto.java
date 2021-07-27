package br.com.apidigitalweb.domin.financeiro;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class CentroCusto
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
public class CentroCusto extends BaseDomain implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1L;

	//
	// Fields
	//

	private Double valorPagar;
	private Double valorReceber;
	private Double saldo;
	@JsonIgnore
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(mappedBy = "centroCusto", fetch = FetchType.LAZY, cascade = CascadeType.ALL,targetEntity =MovCentroCusto.class)
	private List<MovCentroCusto> movCentroCustos;
	
	

	public CentroCusto(Double valorPagar, Double valorReceber, Double saldo, String descricao) {
		super();
		this.valorPagar = valorPagar;
		this.valorReceber = valorReceber;
		this.saldo = saldo;
		setDescricao(descricao);
	}

}
