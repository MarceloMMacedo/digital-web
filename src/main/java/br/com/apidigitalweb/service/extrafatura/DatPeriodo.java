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

	public DatPeriodo(DateTime date, int periodo) {
		super();
		this.date = DatPeriodo.dateTime(date, periodo);
		this.periodo = periodo;
	}

	public static Date dateTime(DateTime dt, int periodo) {
		DateTime plusPeriod = new DateTime();
		plusPeriod = dt.plus(org.joda.time.Period.months(periodo));
		int dayOfWeekEndDateNumber = Integer.valueOf(plusPeriod.dayOfWeek().getAsString());
		if (dayOfWeekEndDateNumber == 6 || dayOfWeekEndDateNumber == 7) {
			int DaysToAdd = 8 - dayOfWeekEndDateNumber;
			plusPeriod = plusPeriod.plusDays(DaysToAdd);
			dayOfWeekEndDateNumber = Integer.valueOf(plusPeriod.dayOfWeek().getAsString());
		}
		return plusPeriod.toDate();
	}

	public static List<DatPeriodo> listaDates(Date dt, List<Integer> listaperiodo) {
		List<DatPeriodo> listaDates = new ArrayList<>();
		for (int i = 0; i < listaperiodo.size(); i++) {
			listaDates.add(new DatPeriodo(new DateTime(dt), listaperiodo.get(i)));
		}
		return listaDates;
	}
}