package br.com.apidigitalweb.controller.grupofinanceiro;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.controller.BaseController;
import br.com.apidigitalweb.domin.contratos.GrupoFinanceiroContrato;
import br.com.apidigitalweb.domin.financeiro.contaspagar.GrupoContaPagar;
import br.com.apidigitalweb.repository.GrupoFinanceiroContratoRepository;
import br.com.apidigitalweb.service.BaseServic;
import br.com.apidigitalweb.service.GrupoContaPagarService;
import br.com.apidigitalweb.service.GrupoFinanceiroContratoService;

@RestController
@RequestMapping(value = ("/grupofinanceirocontrato"))
public class GrupoFinanceiroContratoController extends BaseController<GrupoFinanceiroContrato> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private GrupoFinanceiroContratoService service;

	@Override
	public BaseServic<GrupoFinanceiroContrato> getService() {
		return service;
	}
 
}
