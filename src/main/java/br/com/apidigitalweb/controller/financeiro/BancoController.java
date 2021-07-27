package br.com.apidigitalweb.controller.financeiro;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.controller.BaseController;
import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.service.BancoService;
import br.com.apidigitalweb.service.BaseServic;

@RestController
@RequestMapping(value = ("/bancos"))
class BancoController extends BaseController<Banco> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	BancoService service;
	
	@Override
	public BaseServic<Banco> getService() {
		// TODO Auto-generated method stub
		return service;
	}

}
