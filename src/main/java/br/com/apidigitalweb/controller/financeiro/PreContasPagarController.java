package br.com.apidigitalweb.controller.financeiro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.controller.BaseController;
import br.com.apidigitalweb.domin.financeiro.contaspagar.PreContasPagar;
import br.com.apidigitalweb.service.BaseServic;
import br.com.apidigitalweb.service.PreContasPagarService;

@RestController
@RequestMapping(value = ("/precontaspagar"))
public class PreContasPagarController  extends BaseController< PreContasPagar> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PreContasPagarService service;
	 
	
	@Override
	public BaseServic< PreContasPagar> getService() {
		// TODO Auto-generated method stub
		return service;
	}

}
