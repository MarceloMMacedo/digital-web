package br.com.apidigitalweb.dto.financeiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.domin.financeiro.contaspagar.ContasPagar;
import br.com.apidigitalweb.domin.financeiro.contaspagar.HistoricoContaPagar;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContasPagarDtoReport implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nome;
	private List<HistoricoContasPagarDto> historicoContasPagarDtos = new ArrayList<HistoricoContasPagarDto>();
	private double total;
	public double getTotal() {
		total = 0;
		for (HistoricoContasPagarDto contasPagarDto : historicoContasPagarDtos) {
			total += contasPagarDto.getTotal();
		}
		return total;
	}

	public ContasPagarDtoReport(ContasPagar contasPagar) {
		nome = contasPagar.getNome();
		historicoContasPagarDtos.add(new HistoricoContasPagarDto(contasPagar.getGrupoFinanceiro().getNome(),
				contasPagar.getFaturas(), contasPagar.getFinanceiro().getParcelas()));
	}

	public static List<ContasPagarDtoReport> listaContasPagarDto(List<ContasPagar> contasPagars) {
		List<ContasPagarDtoReport> listaContasPagarDto = new ArrayList<>();
		/*
		 * for (ContasPagar c : contasPagars) { listaContasPagarDto.add(new
		 * ContasPagarDtoReport(c)); }
		 */

		return listaContasPagarDto;
	}

}
