package br.com.apidigitalweb.controller.financeiro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.controller.BaseController;
import br.com.apidigitalweb.domin.financeiro.contaspagar.ContasPagar;
import br.com.apidigitalweb.dto.BaseDto;
import br.com.apidigitalweb.dto.financeiro.ContasPagarDto;
import br.com.apidigitalweb.service.ContasaPagarService;
import br.com.apidigitalweb.service.HistoricoContaPagarService;

@RestController
@RequestMapping(value = ("/contaspagar"))
public class ContasaPagarController extends BaseController<ContasPagar> {

	private static final long serialVersionUID = 1L;
	@Autowired
	private ContasaPagarService service;


	@GetMapping(value = "/contaspagardto")
	public ResponseEntity<List<ContasPagarDto>> contasPagarDtos() {
		List<ContasPagarDto> contaspagardto = service.contasPagarDtos();
		return ResponseEntity.ok(contaspagardto);
	}
	@GetMapping(value = "/getcontaspagardto/{id}")
	public ResponseEntity<ContasPagarDto> getcontaspagardto(@PathVariable Long id) {
		return ResponseEntity.ok(service.getcontaspagardto(id));
	}

}
