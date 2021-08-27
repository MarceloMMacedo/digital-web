package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.financeiro.contaspagar.PreContasPagar;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.repository.PreContasPagarRepository;

@Service
public class PreContasPagarService extends BaseServic<PreContasPagar> implements Serializable {
	private static final long serialVersionUID = 1L;


	@Autowired
	private PreContasPagarRepository repo;
	
	 @Override
	public Page<PreContasPagar> findallpage(String find, Pageable page) {
		// TODO Auto-generated method stub
		return  repo.findAllByStatus(StatusActiv.ABERTO.getDescricao(), page);
	}

	
}
