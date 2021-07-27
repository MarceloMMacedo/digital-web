package br.com.apidigitalweb.domin.financeiro;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class GrupoFinanceiroAnuncio extends BaseDomain implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1L;
	//
	// Fields
	//

	private Double percentualDesconto;
	private String descricao;
	@Transient
	private Double percentualTotal;

	@ElementCollection
	@CollectionTable(joinColumns = @JoinColumn(name = "id"))
	private List<AgregadoGrupoFinanceiro> agregadoGrupo=new ArrayList<>();
	//
	// Constructors
	//

	public GrupoFinanceiroAnuncio(String descricao, List<AgregadoGrupoFinanceiro> agregadoGrupo) {
		super();
		setNome ( descricao); 
		this.agregadoGrupo = agregadoGrupo;
	}

	public Double getPercentualTotal() {
		percentualTotal=0.0;
		
		for (AgregadoGrupoFinanceiro agregadoGrupoFinanceiro : agregadoGrupo) {
			percentualTotal+=agregadoGrupoFinanceiro.getPercentual();
		}
		return percentualTotal;
	}

}
