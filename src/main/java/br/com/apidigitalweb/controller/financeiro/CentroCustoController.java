package br.com.apidigitalweb.controller.financeiro;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.controller.BaseController;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.service.BaseServic;
import br.com.apidigitalweb.service.CentroCustoService;

@RestController
@RequestMapping(value = ("/centrocustos"))
class CentroCustoController extends BaseController<CentroCusto> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	CentroCustoService service;
	
	@Override
	public BaseServic<CentroCusto> getService() {
		// TODO Auto-generated method stub
		return service;
	}

}
