package br.com.apidigitalweb.controller.financeiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.controller.BaseController;
import br.com.apidigitalweb.domin.financeiro.contaspagar.HistoricoContaPagar;
import br.com.apidigitalweb.dto.SampleDto;
import br.com.apidigitalweb.service.BaseServic;
import br.com.apidigitalweb.service.HistoricoContaPagarService;

@RestController
@RequestMapping(value = "/historicocontapagar")
public class HistoricoContaPagarController extends BaseController<HistoricoContaPagar> implements Serializable {

	@Autowired
	private HistoricoContaPagarService service;

	@Override
	public BaseServic<HistoricoContaPagar> getService() {
		return service;
	}
	@GetMapping(value = "/getsampledtogetgrupo/{id}")
	public ResponseEntity<List<String>> getSampleDtogetgrupo(@PathVariable Long id) {
		List<String> lista=new  ArrayList<String>();
		 for(SampleDto s: service.getSampleDtogetgrupo(id)) {
			 lista.add(s.getNome());
		 };
		return ResponseEntity.ok(lista);
	}
}
