package br.com.apidigitalweb.domin.ordemservico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;

import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.contratos.AgregadoGrupoContrato;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class GrupoFinanceiroContrato
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class MaoObra extends BaseDomain implements Serializable, BaseEntity {



	private static final long serialVersionUID = 1L;

	@ElementCollection
	@CollectionTable(joinColumns = @JoinColumn(name = "id"))
	private List<AgregadoGrupoContrato> agregados = new ArrayList<>();

	private double valor;

	private double percentualDesconto;

	@Transient
	private long percentualTotal;
	
	@Transient
	private double valorTotal;
	

	public long getPercentualTotal() {
		percentualTotal = 0;
		try {
			for (AgregadoGrupoContrato agregadoGrupoContrato : agregados) {
				percentualTotal += agregadoGrupoContrato.getPercentual();
			}
			percentualTotal -= percentualTotal  * percentualDesconto;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return percentualTotal;
	}

	public double getValorTotal() {
		valorTotal=0;
		try {
			valorTotal+=valor * getPercentualTotal();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return valorTotal;
	}

}
