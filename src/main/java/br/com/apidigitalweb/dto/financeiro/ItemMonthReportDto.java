package br.com.apidigitalweb.dto.financeiro;

import java.io.Serializable;

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

	 private String getmes(int mes) {
		 switch(mes) {
		 case 1:  return   "Janeiro";
		 case 2:  return "Fevereiro";
		 case 3:  return "Março";
		 case 4:  return "Abril";
		 case 5:  return "Maio";
		 case 6:  return "Junho";
		 case 7:  return "Julho";
		 case 8:  return "Agosto";
		 case 9:  return "Setembro";
		 case 10: return "Outubro";
		 case 11: return "Novembro";
		 case 12: return "Dezembro";
		}
		return null;
	}
	 
}
