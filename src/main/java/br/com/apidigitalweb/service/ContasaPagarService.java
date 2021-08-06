package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.financeiro.contaspagar.ContasPagar;
import br.com.apidigitalweb.dto.financeiro.ContasPagarDto;
import br.com.apidigitalweb.dto.financeiro.ContasPagarDtoReport;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.repository.ContasPagarRepository;
import br.com.apidigitalweb.repository.FaturaContasPagarRepository;
import br.com.apidigitalweb.repository.FaturaContratoRepository;

@Service
public class ContasaPagarService extends BaseServic<ContasPagar> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContasPagarRepository repo;

	@Autowired
	private FaturaContasPagarRepository contasPagarRepository;
	
	@Autowired
	private FaturaContasPagarService contasPagarService;

	@Autowired
	private FaturaContratoRepository contratoRepository;

	public List<ContasPagarDtoReport> contaspagardtosrepor() {
		System.out.println(contasPagarRepository.totalMesPeriodo(9, 2021, 02));
		System.out.println(contratoRepository.totalMesPeriodo(9, 2021, 02));
		List<ContasPagarDtoReport> lista = new ArrayList<ContasPagarDtoReport>();
		List<ContasPagar> listap = repo.findAllContasPagarPagar(StatusActiv.ABERTO.getDescricao());
		for (ContasPagar contasPagar : listap) {
			if (contasPagar.getFaturas().size() > 0)
				lista.add(new ContasPagarDtoReport(contasPagar));
		}
		return lista;

	}

	@Override
	public void posNewObj(ContasPagar obj) {
		contasPagarService.regerarparcelas(obj.getId());
	}

	public List<ContasPagarDto> contasPagarDtos() {
		List<ContasPagarDto> contasPagarDtos = new ArrayList<ContasPagarDto>();
		for (ContasPagar c : getAll()) {
			contasPagarDtos.add(new ContasPagarDto(c));
		}
		return contasPagarDtos;
	}
	
	public ContasPagarDto getcontaspagardto(Long id) {
		return new ContasPagarDto(fingbyid(id));
	}

	@Override
	public void prenew(ContasPagar obj) {
		obj.setNome(obj.getHistorico().getNome()+"/"+""+obj.getFinanceiro().getParcelas()); 
	}

}
