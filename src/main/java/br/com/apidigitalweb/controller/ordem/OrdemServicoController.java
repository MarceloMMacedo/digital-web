package br.com.apidigitalweb.controller.ordem;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.config.security.UserSS;
import br.com.apidigitalweb.domin.ordemservico.OrdemServico;
import br.com.apidigitalweb.dto.ordem.OrdemDto;
import br.com.apidigitalweb.repository.OrdemServicoRepository;
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
}
