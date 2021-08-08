package br.com.apidigitalweb.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.financeiro.MovBanco;
import br.com.apidigitalweb.repository.MovBancoRepository;
import lombok.Data;

@Service
public class MovBancoService extends BaseServic<MovBanco> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	MovBancoRepository repo; 
	 

}
