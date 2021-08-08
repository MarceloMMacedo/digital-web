package br.com.apidigitalweb.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.financeiro.MovCentroCusto;
import br.com.apidigitalweb.repository.MovCentroCustoRepository;

@Service
public class MovCentroCustoService extends BaseServic<MovCentroCusto> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	MovCentroCustoRepository repo; 
	 

}
