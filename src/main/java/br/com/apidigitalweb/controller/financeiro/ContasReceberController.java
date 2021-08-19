package br.com.apidigitalweb.controller.financeiro;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.domin.contratos.FaturaContrato;
import br.com.apidigitalweb.dto.financeiro.ContasReceberDto;
import br.com.apidigitalweb.dto.financeiro.contas.ReciboContratoDto;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.service.ContasReceberService;

@RestController
@RequestMapping(value = ("/contasreceber"))
public class ContasReceberController  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ContasReceberService service;
	
	@GetMapping(value = "/contasreceberaberto")
	public ResponseEntity<ContasReceberDto> contasreceberaberto(@RequestParam  Date inicio, @RequestParam  Date fim) {
		return ResponseEntity.ok(service.ContasReceberbetween(inicio,fim,StatusActiv.ABERTO.getDescricao()));
	}
	@GetMapping(value = "/contasreceberquit")
	public ResponseEntity<ContasReceberDto> contasreceberquit(@RequestParam  Date inicio, @RequestParam  Date fim) {
		return ResponseEntity.ok(service.ContasReceberbetween(inicio,fim,StatusActiv.QUIT.getDescricao()));
	}
	
	@GetMapping(value = "/contasreceberclientebetweenaberto")
	public ResponseEntity<ContasReceberDto> contasreceberclientebetweenaberto(@RequestParam  Long id, @RequestParam  Date inicio, @RequestParam  Date fim) {
		return ResponseEntity.ok(service.ContasReceberClientebetween(id,inicio,fim,StatusActiv.ABERTO.getDescricao()));
	}
	@GetMapping(value = "/contasreceberclientebetweenquit")
	public ResponseEntity<ContasReceberDto> contasreceberclientebetweenquit(@RequestParam  Long id, @RequestParam  Date inicio, @RequestParam  Date fim) {
		return ResponseEntity.ok(service.ContasReceberClientebetween(id,inicio,fim,StatusActiv.QUIT.getDescricao()));
	}
	@GetMapping(value = "/recibocontrato/{id}")
	public ResponseEntity<ReciboContratoDto> recibocontrato(@PathVariable Long id) {
		return ResponseEntity.ok(service.reciboContratoDto(id));
	}
	@PutMapping(value = "/quitar")
	public ResponseEntity<Void> quitar(@RequestBody FaturaContrato obj) {
		service.quitarfatura(obj);
		return ResponseEntity.noContent().build();
	}
}
