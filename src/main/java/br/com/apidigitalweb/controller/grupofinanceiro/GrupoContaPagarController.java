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
import br.com.apidigitalweb.domin.financeiro.contaspagar.GrupoContaPagar;
import br.com.apidigitalweb.service.BaseServic;
import br.com.apidigitalweb.service.GrupoContaPagarService;

@RestController
@RequestMapping(value = ("/grupocontapagar"))
public class GrupoContaPagarController extends BaseController<GrupoContaPagar> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private GrupoContaPagarService service;

	@Override
	public BaseServic<GrupoContaPagar> getService() {
		return service;
	}

	@DeleteMapping(value = "/deletehistorico/{id}")
	public ResponseEntity<Void> detetehistorico(@PathVariable Long id) {

		service.deletehintorico(id);
		return ResponseEntity.noContent().build();
	}
}
