package br.com.apidigitalweb.service.componente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.contratos.FaturaContrato;
import br.com.apidigitalweb.dto.financeiro.ItemMonthReportDto;
import br.com.apidigitalweb.dto.financeiro.contas.ResumoContas;
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

	public List<ItemMonthReportDto> reportFinanceiroFuturos(int exercicio) {
		ReportFinanceiroService reportFinanceiroService = new ReportFinanceiroService();
		List<ItemMonthReportDto> monthReportDtos = new ArrayList<ItemMonthReportDto>();
		this.exercicio = exercicio;
		double valorentrada = 0;
		double valorsaida = 0;
		valorentrada += contratoRepository.totalPeriodoAnterio(exercicio, StatusActiv.ABERTO.getId());
		valorentrada += ordemServicoRepository.totalPeriodoAnterio(exercicio, StatusActiv.ABERTO.getId());
		valorentrada += vendasRepository.totalPeriodoAnterio(exercicio, StatusActiv.ABERTO.getId());
		valorsaida += contasPagarRepository.totalPeriodoAnterio(exercicio, StatusActiv.ABERTO.getId());
		ItemMonthReportDto dto = new ItemMonthReportDto(1, exercicio, valorentrada, valorsaida);
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
		valorentrada = 0;
		valorsaida = 0;
		valorentrada += contratoRepository.totalPeriodoPosterior(exercicio, StatusActiv.ABERTO.getId());
		valorentrada += ordemServicoRepository.totalPeriodoPosterior(exercicio, StatusActiv.ABERTO.getId());
		valorentrada += vendasRepository.totalPeriodoPosterior(exercicio, StatusActiv.ABERTO.getId());
		valorsaida += contasPagarRepository.totalPeriodoPosterior(exercicio, StatusActiv.ABERTO.getId());
		dto = new ItemMonthReportDto(1, exercicio, valorentrada, valorsaida);
		monthReportDtos.add(dto);

		dto.setMes("Postrior");
		return monthReportDtos;
	}

	public List<ItemMonthReportDto> reportFinanceiroRealizados(int exercicio) {
		ReportFinanceiroService reportFinanceiroService = new ReportFinanceiroService();
		List<ItemMonthReportDto> monthReportDtos = new ArrayList<ItemMonthReportDto>();
		this.exercicio = exercicio;
		double valorentrada = 0;
		double valorsaida = 0;
		valorentrada += contratoRepository.totalPeriodoAnterio(exercicio, StatusActiv.QUIT.getId());
		valorentrada += ordemServicoRepository.totalPeriodoAnterio(exercicio, StatusActiv.QUIT.getId());
		valorentrada += vendasRepository.totalPeriodoAnterio(exercicio, StatusActiv.QUIT.getId());
		valorsaida += contasPagarRepository.totalPeriodoAnterio(exercicio, StatusActiv.QUIT.getId());
		ItemMonthReportDto dto = new ItemMonthReportDto(1, exercicio, valorentrada, valorsaida);
		dto.setMes("Anterior");
		monthReportDtos.add(dto);

		for (int i = 1; i < 13; i++) {

			valorentrada = 0;
			valorsaida = 0;
			valorentrada += contratoRepository.totalMesPeriodo(i, exercicio, StatusActiv.QUIT.getId());
			valorentrada += ordemServicoRepository.totalMesPeriodo(i, exercicio, StatusActiv.QUIT.getId());
			valorentrada += vendasRepository.totalMesPeriodo(i, exercicio, StatusActiv.QUIT.getId());
			valorsaida += contasPagarRepository.totalMesPeriodo(i, exercicio, StatusActiv.QUIT.getId());
			dto = new ItemMonthReportDto(i, exercicio, valorentrada, valorsaida);
			monthReportDtos.add(dto);
		}
		valorentrada = 0;
		valorsaida = 0;
		valorentrada += contratoRepository.totalPeriodoPosterior(exercicio, StatusActiv.QUIT.getId());
		valorentrada += ordemServicoRepository.totalPeriodoPosterior(exercicio, StatusActiv.QUIT.getId());
		valorentrada += vendasRepository.totalPeriodoPosterior(exercicio, StatusActiv.QUIT.getId());
		valorsaida += contasPagarRepository.totalPeriodoPosterior(exercicio, StatusActiv.QUIT.getId());
		dto = new ItemMonthReportDto(1, exercicio, valorentrada, valorsaida);
		monthReportDtos.add(dto);

		dto.setMes("Postrior");
		return monthReportDtos;
	}

	public ResumoContas contasReceber(int exercicio) {
		ResumoContas contasReceber = new ResumoContas();
		contasReceber.setExercicio(exercicio);

		/**** calculo de valores total do período ****/
		try {
			List<FaturaContrato> contratos=contratoRepository.findByStatus(StatusActiv.ABERTO.getDescricao());
			double val=0;
			for (FaturaContrato faturaContrato : contratos) {
				val+=faturaContrato.getTotal();
			}
			contasReceber.setTotalresumocontratos(val);

		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			contasReceber.setTotalresumoservicos(ordemServicoRepository.totalAll(StatusActiv.ABERTO.getDescricao()));

		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			contasReceber.setTotalresumocontratos(vendasRepository.totalAll(StatusActiv.ABERTO.getDescricao()));

		} catch (Exception e) {
			// TODO: handle exception
		}

		contasReceber.setValor(contasReceber.getTotalresumocontratos() + contasReceber.getTotalresumovendas()
				+ contasReceber.getTotalresumoservicos());
		/*** Contratos ***/
		double valorentrada = 0;
		double valorsaida = 0;
		try {
			valorentrada = contratoRepository.totalPeriodoAnterio(exercicio, StatusActiv.ABERTO.getId());

		} catch (Exception e) {
			// TODO: handle exception
		}
		ItemMonthReportDto dto = new ItemMonthReportDto(1, exercicio, valorentrada, valorsaida);
		dto.setMes("Anterior");
		contasReceber.getResumocontratos().add(dto);
		for (int i = 1; i < 13; i++) {
			valorentrada = contratoRepository.totalMesPeriodo(i, exercicio, StatusActiv.ABERTO.getId());
			contasReceber.getResumocontratos().add(new ItemMonthReportDto(valorentrada, i));
		}
		/*
		 * contasReceber.getResumocontratos().addAll(ItemMonthReportDto
		 * .itemMonthReportDto(contratoRepository.itemMonthReportDtos(exercicio,
		 * StatusActiv.ABERTO.getId())));
		 */

		valorentrada = 0;
		try {
			valorentrada += contratoRepository.totalPeriodoPosterior(exercicio, StatusActiv.ABERTO.getId());
		} catch (Exception e) {
			// TODO: handle exception
		}
		dto = new ItemMonthReportDto(1, exercicio, valorentrada, valorsaida);
		monthReportDtos.add(dto);
		dto.setMes("Posterior");
		contasReceber.getResumocontratos().add(dto);

		/*** vendas ***/
		valorentrada = 0;
		valorsaida = 0;
		try {
			valorentrada = vendasRepository.totalPeriodoAnterio(exercicio, StatusActiv.ABERTO.getId());

		} catch (Exception e) {
			// TODO: handle exception
		}
		dto = new ItemMonthReportDto(1, exercicio, valorentrada, valorsaida);
		dto.setMes("Anterior");
		contasReceber.getResumovendas().add(dto);
		for (int i = 1; i < 13; i++) {
			valorentrada = vendasRepository.totalMesPeriodo(i, exercicio, StatusActiv.ABERTO.getId());
			contasReceber.getResumovendas().add(new ItemMonthReportDto(valorentrada, i));
		}
		/*
		 * contasReceber.getResumovendas().addAll(ItemMonthReportDto
		 * .itemMonthReportDto(vendasRepository.itemMonthReportDtos(exercicio,
		 * StatusActiv.ABERTO.getId())));
		 */
		valorentrada = 0;
		try {
			valorentrada += vendasRepository.totalPeriodoPosterior(exercicio, StatusActiv.ABERTO.getId());

		} catch (Exception e) {
			// TODO: handle exception
		}
		dto = new ItemMonthReportDto(1, exercicio, valorentrada, valorsaida);
		monthReportDtos.add(dto);
		dto.setMes("Postrior");
		contasReceber.getResumovendas().add(dto);

		/*** servicos ***/
		valorentrada = 0;
		valorsaida = 0;
		try {
			valorentrada = ordemServicoRepository.totalPeriodoAnterio(exercicio, StatusActiv.ABERTO.getId());

		} catch (Exception e) {
			// TODO: handle exception
		}
		dto = new ItemMonthReportDto(1, exercicio, valorentrada, valorsaida);
		dto.setMes("Anterior");
		contasReceber.getResumoservicos().add(dto);
		for (int i = 1; i < 13; i++) {
			valorentrada = ordemServicoRepository.totalMesPeriodo(i, exercicio, StatusActiv.ABERTO.getId());
			contasReceber.getResumoservicos().add(new ItemMonthReportDto(valorentrada, i));
		}
		/*
		 * contasReceber.getResumoservicos().addAll(ItemMonthReportDto
		 * .itemMonthReportDto(ordemServicoRepository.itemMonthReportDtos(exercicio,
		 * StatusActiv.ABERTO.getId())));
		 */
		valorentrada = 0;
		try {
			valorentrada += ordemServicoRepository.totalPeriodoPosterior(exercicio, StatusActiv.ABERTO.getId());

		} catch (Exception e) {
			// TODO: handle exception
		}
		dto = new ItemMonthReportDto(1, exercicio, valorentrada, valorsaida);
		monthReportDtos.add(dto);
		dto.setMes("Postrior");
		contasReceber.getResumoservicos().add(dto);

		/*** Chats ***/
		ResumoContas.loadDataCharcontrato(contasReceber);
		ResumoContas.loadDataCharvendas(contasReceber);
		ResumoContas.loadDataCharservicos(contasReceber);

		return contasReceber;
	}

	public ResumoContas contasPagar(int exercicio) {
		ResumoContas contaspagar = new ResumoContas();
		contaspagar.setExercicio(exercicio);

		/**** calculo de valores total do período ****/
		try {
			contaspagar.setTotalresumopagar(contasPagarRepository.totalAll(StatusActiv.ABERTO.getDescricao()));

		} catch (Exception e) {
			// TODO: handle exception
		}
		contaspagar.setValor(contaspagar.getTotalresumopagar());
		
		/*** pagar ***/
		// double valorentrada = 0;
		double valorsaida = 0;
		try {
			valorsaida = contasPagarRepository.totalPeriodoAnterio(exercicio, StatusActiv.ABERTO.getId());

		} catch (Exception e) {
			// TODO: handle exception
		}
		ItemMonthReportDto dto = new ItemMonthReportDto(1, exercicio, valorsaida,0);
		dto.setMes("Anterior");
		contaspagar.getResumopagar().add(dto);
		for (int i = 1; i < 13; i++) {
			valorsaida = contasPagarRepository.totalMesPeriodo(i, exercicio, StatusActiv.ABERTO.getId());
			contaspagar.getResumopagar().add(new ItemMonthReportDto( valorsaida,i));
		}
		/*
		 * contasReceber.getResumocontratos().addAll(ItemMonthReportDto
		 * .itemMonthReportDto(contasPagarRepository.itemMonthReportDtos(exercicio,
		 * StatusActiv.ABERTO.getId())));
		 */

		valorsaida = 0;
		try {
			valorsaida += contasPagarRepository.totalPeriodoPosterior(exercicio, StatusActiv.ABERTO.getId());
		} catch (Exception e) {
			// TODO: handle exception
		}
		dto = new ItemMonthReportDto(1, exercicio,  valorsaida,0);
		monthReportDtos.add(dto);
		dto.setMes("Posterior");
		contaspagar.getResumopagar().add(dto);

		/*** Chats ***/
		ResumoContas.loadDataCharcontaspagar(contaspagar);

		return contaspagar;
	}

}
