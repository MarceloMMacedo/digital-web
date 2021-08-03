package br.com.apidigitalweb.controller.financeiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.domin.financeiro.contaspagar.FaturaContasPagar;
import br.com.apidigitalweb.domin.financeiro.contaspagar.HistoricoContaPagar;
import br.com.apidigitalweb.dto.SampleDto;
import br.com.apidigitalweb.dto.financeiro.FaturaReportAnaliticoDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HistoricoContasPagarDto implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String nome;
	private List<FaturaReportAnaliticoDto> analiticoDtos = new ArrayList<>();

	private double total;

	public double getTotal() {
		total = 0;
		for (FaturaReportAnaliticoDto faturaReportAnaliticoDto : analiticoDtos) {
			total += faturaReportAnaliticoDto.getTotal();
		}
		return total;
	}

	public HistoricoContasPagarDto(String nome, List<FaturaContasPagar> contasPagars, int totalParcela) {
		 this.nome=nome;
		for (FaturaContasPagar f : contasPagars) {
			SampleDto hist=new SampleDto(f.getHistorico(),"");
			SampleDto cc=new SampleDto(f.getCentroCusto(),"");
			analiticoDtos.add(new FaturaReportAnaliticoDto(f, hist, totalParcela, cc));
		}
	}

}
