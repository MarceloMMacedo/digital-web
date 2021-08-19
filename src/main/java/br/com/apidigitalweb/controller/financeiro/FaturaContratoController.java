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
import br.com.apidigitalweb.domin.contratos.FaturaContrato;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.dto.SampleDto;
import br.com.apidigitalweb.dto.contrato.ContratoDto;
import br.com.apidigitalweb.dto.contrato.SampleContratoDto;
import br.com.apidigitalweb.service.ContratoService;
import br.com.apidigitalweb.service.FaturaContratoService;

@RestController
@RequestMapping(value = "/faturacontratos")
public class FaturaContratoController extends BaseController<FaturaContrato> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private FaturaContratoService service;

	@GetMapping(value = "/regerarfatura/{id}")
	public ResponseEntity<Void> regerarfatura(@PathVariable Long id) {
		service.regerarparcelas(id);
		return ResponseEntity.noContent().build();
	}
 
}
