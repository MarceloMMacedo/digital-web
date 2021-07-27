package br.com.apidigitalweb.domin.contratos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;
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
public class GrupoFinanceiroContrato extends BaseDomain implements Serializable, BaseEntity {

	private static final long serialVersionUID = 1L;

	@ElementCollection
	@CollectionTable(joinColumns = @JoinColumn(name = "id"))
	private List<AgregadoGrupoContrato> agregados = new ArrayList<>();

	@Transient
	private long percentualTotal;
	
	@JsonIgnore
	@OneToMany(mappedBy = "grupoFinanceiro", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<Contrato> contratos;
	
	@JoinColumn()
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY) 
	private Banco banco;
	
	@Transient
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<FaturasDto> faturasAberta = new LinkedList<>();

	@Transient
	private double totalReceber;
	
	public long getPercentualTotal() {
		percentualTotal=0;
		try {
			for (AgregadoGrupoContrato agregadoGrupoContrato : agregados) {
				percentualTotal+=agregadoGrupoContrato.getPercentual();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return percentualTotal;
	}

}
