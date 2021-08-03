package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.financeiro.contaspagar.ContasPagar;
import br.com.apidigitalweb.dto.financeiro.ContasPagarDto;
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
	private FaturaContratoRepository contratoRepository;

	public List<ContasPagarDto> contaspagardtos() {
		System.out.println(contasPagarRepository.totalMesPeriodo(9, 2021, 02));
		System.out.println(contratoRepository.totalMesPeriodo(9, 2021, 02));
		List<ContasPagarDto> lista = new ArrayList<ContasPagarDto>();
		List<ContasPagar> listap = repo.findAllContasPagarPagar(StatusActiv.ABERTO.getDescricao());
		for (ContasPagar contasPagar : listap) {
			if (contasPagar.getFaturas().size() > 0)
				lista.add(new ContasPagarDto(contasPagar));
		}
		return lista;

	}
}
