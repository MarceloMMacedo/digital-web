package br.com.apidigitalweb.controller.ordem;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.service.MainServices;

@RestController
@RequestMapping(value = ("/mainorder"))
public class MainOrderController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private MainServices service;

	@GetMapping(value = "/all")
	public ResponseEntity<List<?>> allordem(@RequestParam(defaultValue = "", value = "nome") String nome) {

		return ResponseEntity.ok(service.allOpenOrdem(nome));
	}

}
