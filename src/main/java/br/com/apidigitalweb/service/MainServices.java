package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.dto.ordem.OrdemDto;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.repository.OrdemServicoRepository;

@Service
public class MainServices implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	@Autowired
	private OrdemVendasLojaService ordemVendasLojaService;

	@Autowired
	private OrdemVendasWebService ordemVendasWebService;

	@Autowired
	private OrdemContratoService ordemContratoService;

	public List<OrdemDto> allOpenOrdem(String nome) {
		
		List<OrdemDto> allOpenOrdem = new ArrayList<>();
		allOpenOrdem.addAll(ordemServicoRepository.allOrdemDto(StatusActiv.ABERTO.getDescricao(),nome));

		return allOpenOrdem;
	}
}
