package br.com.apidigitalweb.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.apidigitalweb.domin.financeiro.contaspagar.PreContasPagar;
import br.com.apidigitalweb.repository.PreContasPagarRepository;

public class PreContasPagarService extends BaseServic<PreContasPagar> implements Serializable {

	@Autowired
	private PreContasPagarRepository repo;
	

}
