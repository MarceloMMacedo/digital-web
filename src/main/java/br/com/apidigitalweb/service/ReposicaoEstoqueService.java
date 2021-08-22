package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.estoque.AnuncioContrato;
import br.com.apidigitalweb.domin.estoque.AnuncioLoja;
import br.com.apidigitalweb.domin.estoque.AnuncioWeb;
import br.com.apidigitalweb.dto.reposicao.ReposicaoDto;

@Service
public class ReposicaoEstoqueService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private AnuncioContratoService anuncioContratoService;

	@Autowired
	private AnuncioLojaService anuncioLojaService;

	@Autowired
	private AnuncioWebService anuncioWebService;

	public ReposicaoDto reposicaoDto() {
		List<AnuncioContrato> anuncioContratos = anuncioContratoService.getAll().stream().filter(x -> x.getSaldoReposicao()>0).collect(Collectors.toList());
	//anuncioContratos=anuncioContratos.stream().filter(x -> x.getSaldoReposicao()>0).collect(Collectors.toList());
		List<AnuncioLoja> anuncioLojas = anuncioLojaService.getAll().stream().filter(x -> x.getSaldoReposicao()>0).collect(Collectors.toList());
		List<AnuncioWeb> anuncioWebs = anuncioWebService.getAll().stream().filter(x -> x.getSaldoReposicao()>0).collect(Collectors.toList());
		ReposicaoDto dto=new ReposicaoDto(anuncioLojas, anuncioWebs, anuncioContratos);
		return dto;

	}

}
