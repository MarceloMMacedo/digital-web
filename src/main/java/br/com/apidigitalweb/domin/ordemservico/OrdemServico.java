package br.com.apidigitalweb.domin.ordemservico;

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
import br.com.apidigitalweb.domin.ordemcontrato.ItensMaoObraInContrato;
import br.com.apidigitalweb.domin.ordemcontrato.ItensMaterialInContratoLoja;
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
public class OrdemServico extends BaseOrdem implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(mappedBy = "ordem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ItensMaterialInServiceLoja> itensmaterialInServiceLojas;

	@JsonIgnore
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(mappedBy = "ordem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ItensMaoObraInService> itensMaoObraInServices;

	@Override
	public List<ItensInsOrdemDto> getItensInsOrdemDtos() {
		List<ItensInsOrdemDto> getItensInsServices = new ArrayList<>();

		try {
			for (ItensMaoObraInService itensInsService : getItensMaoObraInServices()) {
				getItensInsServices.add(new ItensInsOrdemDto(itensInsService));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			for (ItensMaterialInServiceLoja itensInsService : getItensmaterialInServiceLojas()) {
				getItensInsServices.add(new ItensInsOrdemDto(itensInsService));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getItensInsServices;
	}

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

			for (ItensMaoObraInService itensInsService : getItensMaoObraInServices()) {
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

			for (ItensMaterialInServiceLoja itensInsService : getItensmaterialInServiceLojas()) {
				totalItensMaterial += itensInsService.getValortotal();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return totalItensMaterial;
	}

}
