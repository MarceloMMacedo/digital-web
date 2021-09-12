package br.com.apidigitalweb.domin.ordemvendaweb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.converters.CalnalOrdemConverter;
import br.com.apidigitalweb.converters.StatusConverter;
import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.ordem.BaseOrdem;
import br.com.apidigitalweb.domin.ordem.FinanceiroOrdem;
import br.com.apidigitalweb.domin.ordemservico.ItensMaterialInServiceLoja;
import br.com.apidigitalweb.domin.ordemvendaloja.ItensMaterialInVendaLoja;
import br.com.apidigitalweb.domin.pessoa.Cliente;
import br.com.apidigitalweb.domin.pessoa.Contato;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.domin.pessoa.Funcionario;
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
public class OrdemVendaWeb extends BaseOrdem implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonIgnore
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(mappedBy = "ordem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ItensMaterialInVendaWeb> itensMaterialInVendaWebs;

	@Override
	public double getTotalItensMaterial() {
		totalItensMaterial = 0;
		try {

			for (ItensMaterialInVendaWeb itensInsService : getItensMaterialInVendaWebs()) {
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

		for (ItensMaterialInVendaWeb itensInsService : getItensMaterialInVendaWebs()) {
			getItensInsServices.add(new ItensInsOrdemDto(itensInsService));
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
}
