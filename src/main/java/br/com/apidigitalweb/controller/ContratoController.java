package br.com.apidigitalweb.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.domin.contratos.Contrato;
import br.com.apidigitalweb.dto.contrato.ContratoDto;
import br.com.apidigitalweb.dto.contrato.SampleContratoDto;
import br.com.apidigitalweb.service.ContratoService;

@RestController
@RequestMapping(value = "/contratos")
public class ContratoController extends BaseController<Contrato> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ContratoService service;

	@RequestMapping(value = "/page/all")
	public ResponseEntity<Page<SampleContratoDto>> findallpageAll(
			@RequestParam(defaultValue = "", value = "nome") String nome, Pageable page) {

		Page<SampleContratoDto> pages = service.findallpagesampledto(nome, page);

		return ResponseEntity.ok(pages);
	}
	@GetMapping(value = "/contrato/{id}")
	public ResponseEntity<ContratoDto> findbyid(@PathVariable Long id) {
		
		
		return ResponseEntity.ok(service.findbyid(id) );
	}
}
