package br.com.apidigitalweb.dto.financeiro.contas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.apidigitalweb.dto.financeiro.ItemMonthReportDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
public class ResumoContas implements Serializable {
	private static final long serialVersionUID = 1L;

	@Data
	@NoArgsConstructor
	public class DataChart implements Serializable {
		private static final long serialVersionUID = 1L;

		private String label;
		private List<Double> data = new ArrayList<>();

	}

	private String nome;
	private double valor;
	private int exercicio;

	private double totalresumocontratos;
	private double totalresumovendas;
	private double totalresumoservicos;
	private double totalresumopagar;

	private List<String> lineChartLabels = new ArrayList<>();

	@JsonIgnore
	private List<ItemMonthReportDto> resumocontratos = new ArrayList<>();
	@JsonIgnore
	private List<ItemMonthReportDto> resumovendas = new ArrayList<>();
	@JsonIgnore
	private List<ItemMonthReportDto> resumoservicos = new ArrayList<>();
	@JsonIgnore
	private List<ItemMonthReportDto> resumopagar = new ArrayList<>();

	private DataChart chartscontrato = new DataChart();
	private DataChart chartsvendas = new DataChart();
	private DataChart chartsservicos = new DataChart();
	private DataChart chartspagar = new DataChart();

	public ResumoContas() {
		for (int i = 0; i < 14; i++) {
			lineChartLabels.add(getmes(i));
		}

	}
	public static void loadDataCharcontaspagar(ResumoContas contasReceber) {
		contasReceber.getChartspagar().setLabel("Pagar");
		for (ItemMonthReportDto objects : contasReceber.getResumopagar()) {
			contasReceber.getChartspagar().getData().add(objects.getEntradas());
		}
	}
	public static void loadDataCharcontrato(ResumoContas contasReceber) {
		contasReceber.getChartscontrato().setLabel("Contratos");
		for (ItemMonthReportDto objects : contasReceber.getResumocontratos()) {
			contasReceber.getChartscontrato().getData().add(objects.getEntradas());
		}
	}

	public static void loadDataCharvendas(ResumoContas contasReceber) {
		contasReceber.getChartsvendas().setLabel("Vendas");
		for (ItemMonthReportDto objects : contasReceber.getResumovendas()) {
			contasReceber.getChartsvendas().getData().add(objects.getEntradas());
		}
	}

	public static void loadDataCharservicos(ResumoContas contasReceber) {
		contasReceber.getChartsservicos().setLabel("Serviços");
		for (ItemMonthReportDto objects : contasReceber.getResumoservicos()) {
			contasReceber.getChartsservicos().getData().add(objects.getEntradas());
		}
	}

	private String getmes(int mes) {
		switch (mes) {
		case 0:
			return "Anterior";

		case 1:
			return "Janeiro";
		case 2:
			return "Fevereiro";
		case 3:
			return "Março";
		case 4:
			return "Abril";
		case 5:
			return "Maio";
		case 6:
			return "Junho";
		case 7:
			return "Julho";
		case 8:
			return "Agosto";
		case 9:
			return "Setembro";
		case 10:
			return "Outubro";
		case 11:
			return "Novembro";
		case 12:
			return "Dezembro";
		case 13:
			return "Posterior";
		}
		return null;
	}
}
