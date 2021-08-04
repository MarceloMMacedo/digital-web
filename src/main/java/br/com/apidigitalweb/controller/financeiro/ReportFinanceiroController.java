package br.com.apidigitalweb.controller.financeiro;

import java.io.Serializable;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.dto.financeiro.ContasPagarDto;
import br.com.apidigitalweb.dto.financeiro.ItemMonthReportDto;
import br.com.apidigitalweb.service.ContasaPagarService;
import br.com.apidigitalweb.service.componente.ReportFinanceiroService;

@RestController
@RequestMapping(value = ("/reportfinanceiro"))
public class ReportFinanceiroController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ReportFinanceiroService service;

	@Autowired
	private ContasaPagarService contasaPagarService;

	@GetMapping(value = "/listacontaspagarsintetico")
	public ResponseEntity<List<ContasPagarDto>> contaspagardto() {
		return ResponseEntity.ok(contasaPagarService.contaspagardtos());
	}

	@GetMapping(value = "/periodosinteticofuturos/{exercicio}")
	public ResponseEntity<List<ItemMonthReportDto>> periodosintetico(@PathVariable int exercicio) {
		return ResponseEntity.ok(service.reportFinanceiroFuturos(exercicio));
	}

	@GetMapping(value = "/periodosinteticorealizados/{exercicio}")
	public ResponseEntity<List<ItemMonthReportDto>> periodosinteticorealizados(@PathVariable int exercicio) {
		return ResponseEntity.ok(service.reportFinanceiroRealizados(exercicio));
	}

}
