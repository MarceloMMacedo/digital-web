package br.com.apidigitalweb.controller.ordem;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.domin.ordemservico.ItensMaoObraInService;
import br.com.apidigitalweb.domin.ordemservico.ItensMaterialInServiceLoja;
import br.com.apidigitalweb.domin.ordemservico.OrdemServico;
import br.com.apidigitalweb.dto.ordem.OrdemDto;
import br.com.apidigitalweb.service.BaseServicOrdem;
import br.com.apidigitalweb.service.OrdemServicoService;

@RestController
@RequestMapping(value = ("/ordemservicos"))
public class OrdemServicoController extends BaseOrdemController<OrdemServico> implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private OrdemServicoService service;
	
	@Override
	public BaseServicOrdem<OrdemServico> getService() { 	
		return  service;
	}
	
	@GetMapping(value = "/ordem/{id}")
	public ResponseEntity<OrdemDto> fingiddto(@PathVariable Long id) {
		return ResponseEntity.ok(service.fingbyiddto(id));
	}
	
	@PostMapping(value = "/addItensMaoObraInService/{id}")
	public ResponseEntity<Void> addItensMaoObraInService(@PathVariable("id") Long id,
			@RequestBody ItensMaoObraInService item) {
		service	.addItensMaoObraInService(id, item);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/addItensItensMaterialInServiceLoja/{id}")
	public ResponseEntity<OrdemDto> addItensItensMaterialInServiceLoja(@PathVariable("id") Long id,
			@RequestBody List<ItensMaterialInServiceLoja> item) {
		service	.addItensItensMaterialInServiceLoja(id, item);
		return ResponseEntity.ok(service.fingbyiddto(id));
	}
	
	
	@PostMapping(value = "/removeitensMaoObraInService/{id}")
	public ResponseEntity<Void> removeitensMaoObraInService(@PathVariable("id") Long id)  {
		service	.removeitensMaoObraInService(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/removeItensItensMaterialInServiceLoja/{id}")
	public ResponseEntity<Void> removeItensItensMaterialInServiceLoja(@PathVariable("id") Long id ) {
		service	.removeItensItensMaterialInServiceLoja(id);
		return ResponseEntity.noContent().build();
	}
	
}
