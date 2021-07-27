package br.com.apidigitalweb.domin.financeiro.contaspagar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.financeiro.BaseFatura;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;
import br.com.apidigitalweb.enuns.TipoFaturaEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class GrupoContaPagar extends BaseDomain implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "grupocontaspagar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<HistoricoContaPagar> historicos;

	@Transient
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<FaturasDto> faturasAberta = new LinkedList<>();

	@Transient
	private double totalPagar;

	public List<FaturasDto> getFaturasAberta() {
		List<FaturasDto> faturasAberta = new ArrayList<>();
		for (HistoricoContaPagar h : getHistoricos()) {
			faturasAberta.addAll(h.getFaturasAberta());
		}
		return faturasAberta;
	}

	public double getTotalPagar() {
		totalPagar = 0;
		try {
			for (FaturasDto faturasDto : getFaturasAberta()) {
				totalPagar += faturasDto.getTotal();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return totalPagar;
	}

}
