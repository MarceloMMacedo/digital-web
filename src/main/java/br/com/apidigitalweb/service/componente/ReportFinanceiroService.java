package br.com.apidigitalweb.service.componente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.dto.financeiro.ItemMonthReportDto;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.repository.FaturaContasPagarRepository;
import br.com.apidigitalweb.repository.FaturaContratoRepository;
import br.com.apidigitalweb.repository.FaturaOrdemServicoRepository;
import br.com.apidigitalweb.repository.FaturaVendasRepository;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Service
public class ReportFinanceiroService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private FaturaContratoRepository contratoRepository;

	@Autowired
	private FaturaOrdemServicoRepository ordemServicoRepository;

	@Autowired
	private FaturaVendasRepository vendasRepository;

	@Autowired
	private FaturaContasPagarRepository contasPagarRepository;

	private int exercicio;
	public List<ItemMonthReportDto> monthReportDtos = new ArrayList<ItemMonthReportDto>();

	public List<ItemMonthReportDto> reportFinanceiroService(int exercicio) {
		ReportFinanceiroService reportFinanceiroService = new ReportFinanceiroService();
		List<ItemMonthReportDto> monthReportDtos = new ArrayList<ItemMonthReportDto>();
		this.exercicio = exercicio;
		double valorentrada = 0;
		double valorsaida = 0;
		valorentrada += contratoRepository.totalPeriodo( exercicio - 1, StatusActiv.ABERTO.getId());
		valorentrada += ordemServicoRepository.totalPeriodo(  exercicio - 1, StatusActiv.ABERTO.getId());
		valorentrada += vendasRepository.totalPeriodo(  exercicio - 1, StatusActiv.ABERTO.getId());
		valorsaida += contasPagarRepository.totalPeriodo(  exercicio - 1, StatusActiv.ABERTO.getId());
		ItemMonthReportDto dto = new ItemMonthReportDto(1, exercicio - 1, valorentrada, valorsaida);
		dto.setMes("Anterior");
		monthReportDtos.add(dto); 

		for (int i = 1; i < 13; i++) {

			valorentrada = 0;
			valorsaida = 0;
			valorentrada += contratoRepository.totalMesPeriodo(i, exercicio, StatusActiv.ABERTO.getId());
			valorentrada += ordemServicoRepository.totalMesPeriodo(i, exercicio, StatusActiv.ABERTO.getId());
			valorentrada += vendasRepository.totalMesPeriodo(i, exercicio, StatusActiv.ABERTO.getId());
			valorsaida += contasPagarRepository.totalMesPeriodo(i, exercicio, StatusActiv.ABERTO.getId());
			dto = new ItemMonthReportDto(i, exercicio, valorentrada, valorsaida);
			monthReportDtos.add(dto);
		}

		return monthReportDtos;
	}

}
