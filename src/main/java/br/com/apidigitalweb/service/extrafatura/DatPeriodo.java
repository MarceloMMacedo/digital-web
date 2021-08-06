package br.com.apidigitalweb.service.extrafatura;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DatPeriodo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Date date;
	private int periodo;
	private Date dataleitura;

	public DatPeriodo(Date  date, int periodo,int diavencimento ,int dialeitura ) {
		super();
		this.date = DatPeriodo.dateTime(date,diavencimento, periodo);
		this.periodo = periodo;
		dataleitura=DatPeriodo.datedayTime(this.date,dialeitura);
		
	}
	

	public DatPeriodo(Date  date, int periodo ) {
		super();
		this.date = DatPeriodo.dateTimePagar(date, periodo);
		this.periodo = periodo;
	 
		
	}
	public static Date dateTimePagar(Date  dt, int periodo) {
		DateTime plusPeriod = new DateTime(dt);
		plusPeriod = plusPeriod.plus(org.joda.time.Period.months(periodo));
		
		 	
		
		int dayOfWeekEndDateNumber = Integer.valueOf(plusPeriod.dayOfWeek().getAsString());
		if (dayOfWeekEndDateNumber == 6 || dayOfWeekEndDateNumber == 7) {
			int DaysToAdd = 8 - dayOfWeekEndDateNumber;
			plusPeriod = plusPeriod.plusDays(DaysToAdd);
			dayOfWeekEndDateNumber = Integer.valueOf(plusPeriod.dayOfWeek().getAsString());
		}
		return plusPeriod.toDate();
	}
	public static Date datedayTime(Date  dt,int dialeitura ) {
	
		DateTime plusPeriod = new DateTime(dt);
		int mes=plusPeriod.getMonthOfYear();
		int ano=plusPeriod.getYear();
		plusPeriod=new DateTime(ano, mes, dialeitura, 0, 0,0,0 );	
		
		plusPeriod = plusPeriod.minusDays(2);
		int dayOfWeekEndDateNumber = Integer.valueOf(plusPeriod.dayOfWeek().getAsString());
		if (dayOfWeekEndDateNumber == 6 || dayOfWeekEndDateNumber == 7) {
			int DaysToAdd = 8 - dayOfWeekEndDateNumber;
			plusPeriod = plusPeriod.plusDays(DaysToAdd);
			dayOfWeekEndDateNumber = Integer.valueOf(plusPeriod.dayOfWeek().getAsString());
		}
		return plusPeriod.toDate();
	}

	public static Date dateTime(Date  dt,int diavencimento, int periodo) {
		DateTime plusPeriod = new DateTime(dt);
		plusPeriod = plusPeriod.plus(org.joda.time.Period.months(periodo));
		
		 
		int mes=plusPeriod.getMonthOfYear();
		int ano=plusPeriod.getYear();
		plusPeriod=new DateTime(ano, mes, diavencimento, 0, 0,0,0 );	
		
		int dayOfWeekEndDateNumber = Integer.valueOf(plusPeriod.dayOfWeek().getAsString());
		if (dayOfWeekEndDateNumber == 6 || dayOfWeekEndDateNumber == 7) {
			int DaysToAdd = 8 - dayOfWeekEndDateNumber;
			plusPeriod = plusPeriod.plusDays(DaysToAdd);
			dayOfWeekEndDateNumber = Integer.valueOf(plusPeriod.dayOfWeek().getAsString());
		}
		return plusPeriod.toDate();
	}

	public static List<DatPeriodo> listaDates(Date dt, List<Integer> listaperiodo,int diavencimento,int dialeitura) {
		List<DatPeriodo> listaDates = new ArrayList<>();
 
		for (int i = 0; i < listaperiodo.size(); i++) {
			listaDates.add(new DatPeriodo(dt, listaperiodo.get(i),diavencimento,dialeitura));
		}
		return listaDates;
	}
	
	
	public static List<DatPeriodo> listaDates(Date dt, List<Integer> listaperiodo ) {
		List<DatPeriodo> listaDates = new ArrayList<>();
 
		for (int i = 0; i < listaperiodo.size(); i++) {
			listaDates.add(new DatPeriodo(dt, listaperiodo.get(i) ));
		}
		return listaDates;
	}
}