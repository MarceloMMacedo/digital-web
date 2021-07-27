package br.com.apidigitalweb.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.apidigitalweb.domin.BaseEntityPessoa;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.dto.SampleDto;
import br.com.apidigitalweb.service.BaseServicePessoa;
import lombok.Data;

@Data
public class BaseControllerPessoa<T extends BaseEntityPessoa> extends BaseController<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	BaseServicePessoa<T> service;

	@GetMapping(value = "/{id}/receitaws")
	public ResponseEntity<?> receitaws(@PathVariable("id") Long id,
			@RequestParam(defaultValue = "", value = "cnpj") String cnpj) {
		T t = getService().receitaws(id, cnpj);
		return ResponseEntity.ok(t);
	}

	@GetMapping(value = "/cep")
	public ResponseEntity<?> getcep(@RequestParam(defaultValue = "", value = "cep") String cep) {
		Endereco t = getService().getEndereco(cep);
		return ResponseEntity.ok(t);
	}

	@GetMapping(value = "/listarules")
	public ResponseEntity<?> listaRules( ) {
		List< String> lista=new ArrayList<>();
		lista = getService().listaRules();
		return ResponseEntity.ok(lista);
	}
}
