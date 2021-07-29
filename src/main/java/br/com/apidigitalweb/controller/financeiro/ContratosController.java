package br.com.apidigitalweb.controller.financeiro;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.controller.BaseController;
import br.com.apidigitalweb.domin.contratos.Contrato;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.dto.SampleDto;
import br.com.apidigitalweb.dto.contrato.ContratoDto;
import br.com.apidigitalweb.dto.contrato.SampleContratoDto;
import br.com.apidigitalweb.service.ContratoService;

@RestController
@RequestMapping(value = "/contrato")
public class ContratosController extends BaseController<Contrato> implements Serializable {

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
	
	
	@GetMapping(value = "/patrimonios/getallsampledto")
	public ResponseEntity<List<SampleDto> > findAllByContratoIsNull() {
		return ResponseEntity.ok(service.findAllByContratoIsNull());
	}
	@GetMapping(value = "/cep")
	public ResponseEntity<?> getcep(@RequestParam(defaultValue = "", value = "cep") String cep) {
		Endereco t = service.getEndereco(cep);
		return ResponseEntity.ok(t);
	}
	@DeleteMapping(value = "/deleteitem/{id}")
	public ResponseEntity<Void > deleteitem(@PathVariable Long id) {
		service.deleteItemcontrato(id);
		return ResponseEntity.noContent().build() ;
	}
	
	@DeleteMapping(value = "/deleteanuncio/{id}")
	public ResponseEntity<Void > deleteanuncio(@PathVariable Long id) {
		service.deleteItemcontrato(id);
		return ResponseEntity.noContent().build() ;
	}
}
