package br.com.apidigitalweb.dto.financeiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemMonthReportDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private int month;
	private int year;
	private String mes;
	private double entradas;
	private double saidas;

	public ItemMonthReportDto(int month, int year, double entradas, double saidas) {
		super();
		this.month = month;
		this.year = year;
		this.mes = getmes(month);
		this.entradas = entradas;
		this.saidas = saidas;
	}

	public ItemMonthReportDto(List<Object[]> result) {
		for (Object[] objects : result) {
			this.entradas = (double) objects[0];
			this.month = (int) objects[1];
			this.mes = getmes(month);
		}
	}

	public ItemMonthReportDto(double entradas, int month) {
		super();
		this.month = month;
		this.year = year;
		this.mes = getmes(month);
		this.entradas = entradas;
		this.saidas = saidas;
	}

	public static List<ItemMonthReportDto> itemMonthReportDto(List<Object[]> result) {
		List<ItemMonthReportDto> itemMonthReportDtos = new ArrayList<>();
		for (Object[] objects : result) {
			Double num = (Double) objects[1];
			Integer i = num.intValue(); 
			itemMonthReportDtos.add(new ItemMonthReportDto((double) objects[0], i));
		}
		return itemMonthReportDtos;
	}

	private String getmes(int mes) {
		switch (mes) {
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
		}
		return null;
	}

}
