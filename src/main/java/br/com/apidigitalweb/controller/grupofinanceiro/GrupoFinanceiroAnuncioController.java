package br.com.apidigitalweb.controller.grupofinanceiro;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.controller.BaseController;
import br.com.apidigitalweb.domin.financeiro.GrupoFinanceiroAnuncio;
import br.com.apidigitalweb.repository.GrupoFinanceiroAnuncioRepository;
import br.com.apidigitalweb.service.BaseServic;
import br.com.apidigitalweb.service.GrupoFinanceiroAnuncioService;

@RestController
@RequestMapping(value = ("/grupofinanceiroanuncio"))
public class GrupoFinanceiroAnuncioController extends BaseController<GrupoFinanceiroAnuncio> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	GrupoFinanceiroAnuncioService service; 
	
	@Override
	public BaseServic<GrupoFinanceiroAnuncio> getService() {
		// TODO Auto-generated method stub
		return service;
	} 
}
