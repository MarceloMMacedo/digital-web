package br.com.apidigitalweb.domin.ordemcontrato;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.domin.ordem.BaseOrdem;
import br.com.apidigitalweb.dto.ordem.ItensInsOrdemDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class OrdemVendaWeb
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
public class OrdemContrato extends BaseOrdem implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(mappedBy = "ordem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ItensMaterialInContratoLoja> itensMaterialInContratoLojas;

	@JsonIgnore
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(mappedBy = "ordem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ItensMaoObraInContrato> itensMaoObraInContratos;

 

	@Override
	public double getTotal() {
		total = 0;
		try {

			for (ItensInsOrdemDto itensInsOrdemDto : getItensInsOrdemDtos()) {
				total += itensInsOrdemDto.getValortotal();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return total;
	}
	@Override
	public double getTotalItensMaoObra() {
		totalItensMaoObra = 0;
		try {

			for (ItensMaoObraInContrato itensInsService : getItensMaoObraInContratos()) {
				totalItensMaoObra += itensInsService.getValortotal();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return totalItensMaoObra;
	}
	@Override
	public double getTotalItensMaterial() {
		totalItensMaterial = 0;
		try {

			for (ItensMaterialInContratoLoja itensInsService : getItensMaterialInContratoLojas()) {
				totalItensMaterial += itensInsService.getValortotal();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return totalItensMaterial;
	}
	
	@Override
	public List<ItensInsOrdemDto> getItensInsOrdemDtos() {
		List<ItensInsOrdemDto> getItensInsServices = new ArrayList<>();

		for (ItensMaoObraInContrato itensInsService : getItensMaoObraInContratos()) {
			getItensInsServices.add(new ItensInsOrdemDto(itensInsService));
		}

		for (ItensMaterialInContratoLoja itensInsService : getItensMaterialInContratoLojas()) {
			getItensInsServices.add(new ItensInsOrdemDto(itensInsService));
		}

		return getItensInsServices;
	}

}
