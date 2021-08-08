package br.com.apidigitalweb.controller.financeiro;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.controller.BaseController;
import br.com.apidigitalweb.domin.financeiro.contaspagar.FaturaContasPagar;
import br.com.apidigitalweb.dto.financeiro.ContasPagarDtoReport;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;
import br.com.apidigitalweb.dto.financeiro.FaturasPagarDto;
import br.com.apidigitalweb.service.BaseServic;
import br.com.apidigitalweb.service.ContasaPagarService;
import br.com.apidigitalweb.service.FaturaContasPagarService;
import br.com.apidigitalweb.service.componente.ReportFinanceiroService;

@RestController
@RequestMapping(value = "/faturacontaspaga")
public class FaturaContasPagarController extends BaseController<FaturaContasPagar> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private FaturaContasPagarService service;

	@Override
	public BaseServic<FaturaContasPagar> getService() {
		return service;
	}
	@Autowired
	private ReportFinanceiroService reportFinanceiroService;

	@Autowired
	private ContasaPagarService contasaPagarService;

	@GetMapping(value = "/listacontaspagarsintetico")
	public ResponseEntity<List<ContasPagarDtoReport>> contaspagardto() {
		return ResponseEntity.ok(contasaPagarService.contaspagardtosrepor());
	}


	@GetMapping(value = "/fingiddto/{id}")
	public ResponseEntity<FaturasPagarDto> fingiddto(@PathVariable Long id) {
		return ResponseEntity.ok(service.findbyiddto(id));
	}

	@PutMapping(value = "/quitar/{id}")
	public ResponseEntity<Void> quitar(@PathVariable Long id) {
		service.quitarfatura(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/faturasopen")
	public ResponseEntity<List<FaturasDto>> faturasopen() {
		return ResponseEntity.ok(service.faturasopen());
	}

}
