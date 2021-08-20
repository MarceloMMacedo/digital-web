package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.contratos.Contrato;
import br.com.apidigitalweb.domin.contratos.FaturaContrato;
import br.com.apidigitalweb.domin.ordemservico.FaturaOrdemServico;
import br.com.apidigitalweb.domin.ordemvenda.FaturaVenda;
import br.com.apidigitalweb.domin.pessoa.Cliente;
import br.com.apidigitalweb.dto.SampleDto;
import br.com.apidigitalweb.dto.financeiro.ContasReceberDto;
import br.com.apidigitalweb.dto.financeiro.FaturaContratoDto;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;
import br.com.apidigitalweb.dto.financeiro.contas.ReciboContratoDto;
import br.com.apidigitalweb.dto.financeiro.contas.ReciboDto;
import br.com.apidigitalweb.enuns.TipoFaturaEnum;
import br.com.apidigitalweb.repository.ClienteRepository;
import br.com.apidigitalweb.repository.ContratoRepository;
import br.com.apidigitalweb.repository.FaturaContratoRepository;
import br.com.apidigitalweb.repository.FaturaOrdemServicoRepository;
import br.com.apidigitalweb.repository.FaturaVendasRepository;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Service
public class ContasReceberService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private FaturaContratoRepository faturaContratoRepository;

	@Autowired
	private FaturaOrdemServicoRepository faturaOrdemServicoRepository;

	@Autowired
	private FaturaVendasRepository faturaVendasRepository;

	@Autowired
	private FaturaContratoService faturaContratoService;

	@Autowired
	private FaturaOrdemServicoService faturaOrdemServicoService;

	@Autowired
	private FaturaVendaService faturaVendaService;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ContratoRepository contratoService;

	public ContasReceberDto ContasReceberCliente(Long id, String status) {
		Cliente c = clienteRepository.findById(id).get();

		ContasReceberDto ContasReceberCliente = new ContasReceberDto(new SampleDto(c, ""),
				faturaContratoRepository.findAllClienteAndStatus(c, status),
				faturaVendasRepository.findAllClienteAndStatus(c, status),
				faturaOrdemServicoRepository.findAllClienteAndStatus(c, status));
		return ContasReceberCliente;
	}

	public ContasReceberDto ContasReceberClientebetween(Long id, Date inicio, Date fim, String status) {
		Cliente c = clienteRepository.findById(id).get();
		ContasReceberDto contasReceberCliente = new ContasReceberDto();
		contasReceberCliente.setDatainicio(inicio);
		contasReceberCliente.setDatafim(fim);
		List<FaturaContratoDto> dtoscontrato = new ArrayList<>();

		/* contratos */
		List<FaturaContrato> cps = faturaContratoRepository
				.findAllByClienteAndStatusAndDataVencimentoBetweenOrderByDataVencimento(c, status, inicio, fim);
		for (FaturaContrato f : cps) {
			dtoscontrato.add(new FaturaContratoDto(f));
		}
		List<FaturaContratoDto> sortedListContrato = dtoscontrato.stream()
				.sorted(Comparator.comparing(FaturaContratoDto::getDataVencimento)).collect(Collectors.toList());
		contasReceberCliente.setCliente(new SampleDto(c, ""));
		contasReceberCliente.setFaturasContrato(sortedListContrato);

		/* vendasa */
		List<FaturasDto> dtos = new ArrayList<>();

		dtos.clear();
		List<FaturaVenda> cpsv = faturaVendasRepository
				.findAllByClienteAndStatusAndDataVencimentoBetweenOrderByDataVencimento(c, status, inicio, fim);
		for (FaturaVenda f : cpsv) {
			dtos.add(new FaturasDto(f));
		}

		List<FaturasDto> sortedList = dtos.stream().sorted(Comparator.comparing(FaturasDto::getDataVencimento))
				.collect(Collectors.toList());
		contasReceberCliente.setFaturasVendas(sortedList);

		/* servicos */

		dtos.clear();
		List<FaturaOrdemServico> cpss = faturaOrdemServicoRepository
				.findAllByClienteAndStatusAndDataVencimentoBetweenOrderByDataVencimento(c, status, inicio, fim);
		for (FaturaOrdemServico f : cpss) {
			dtos.add(new FaturasDto(f));
		}

		sortedList = dtos.stream().sorted(Comparator.comparing(FaturasDto::getDataVencimento))
				.collect(Collectors.toList());
		contasReceberCliente.setCliente(new SampleDto(c, ""));
		contasReceberCliente.setFaturasServico(sortedList);
		return contasReceberCliente;
	}

	public ContasReceberDto ContasReceberbetween(Date inicio, Date fim, String status) {
		ContasReceberDto contasReceberCliente = new ContasReceberDto();

		contasReceberCliente.setDatainicio(inicio);
		contasReceberCliente.setDatafim(fim);

		List<FaturaContratoDto> dtoscontrato = new ArrayList<>();

		List<FaturaContrato> cps = faturaContratoRepository
				.findAllByStatusAndDataVencimentoBetweenOrderByDataVencimento(status, inicio, fim);

		for (FaturaContrato f : cps) {
			dtoscontrato.add(new FaturaContratoDto(f));
		}

		List<FaturaContratoDto> sortedListContrato = dtoscontrato.stream()
				.sorted(Comparator.comparing(FaturaContratoDto::getDataVencimento)).collect(Collectors.toList());

		contasReceberCliente.setFaturasContrato(sortedListContrato);

		/* vendasa */

		List<FaturasDto> dtos = new ArrayList<>();
		dtos.clear();
		List<FaturaVenda> cpsv = faturaVendasRepository
				.findAllByStatusAndDataVencimentoBetweenOrderByDataVencimento(status, inicio, fim);
		for (FaturaVenda f : cpsv) {
			dtos.add(new FaturasDto(f));
		}

		List<FaturasDto> sortedList = dtos.stream().sorted(Comparator.comparing(FaturasDto::getDataVencimento))
				.collect(Collectors.toList());
		contasReceberCliente.setFaturasVendas(sortedList);

		/* servicos */

		dtos.clear();
		List<FaturaOrdemServico> cpss = faturaOrdemServicoRepository
				.findAllByStatusAndDataVencimentoBetweenOrderByDataVencimento(status, inicio, fim);
		for (FaturaOrdemServico f : cpss) {
			dtos.add(new FaturasDto(f));
		}

		sortedList = dtos.stream().sorted(Comparator.comparing(FaturasDto::getDataVencimento))
				.collect(Collectors.toList());
		contasReceberCliente.setFaturasServico(sortedList);

		return contasReceberCliente;// ReceberCliente;
	}

	public ReciboContratoDto reciboContratoDto(Long id) {
		FaturaContrato f = faturaContratoService.fingbyid(id);
		Contrato c = contratoService.findById(f.getContrato().getId()).get();
		return new ReciboContratoDto(c, f);
	}

	public ReciboDto recibovenda(Long id) {
		FaturaVenda f = faturaVendasRepository.findById(id).get();
		ReciboDto dto = new ReciboDto(f.getOrdemVenda(), f);
		return dto;
	}

	public ReciboDto reciboservico(Long id) {
		FaturaOrdemServico f = faturaOrdemServicoRepository.findById(id).get();
		ReciboDto dto = new ReciboDto(f.getOrdemServico(), f);
		return dto;
	}

	public void validarleitura() {

	}

	public void quitarfatura(FaturasDto obj) {
		if (obj.getOrigem().equals(TipoFaturaEnum.Contrato.getDescricao()))
			faturaContratoService.quitar(obj);
		if (obj.getOrigem().equals(TipoFaturaEnum.Venda.getDescricao()))
			faturaVendaService.quitar(obj);
		if(obj.getOrigem().equals(TipoFaturaEnum.Servico.getDescricao()))
			faturaOrdemServicoService.quitar(obj);
	}

}
