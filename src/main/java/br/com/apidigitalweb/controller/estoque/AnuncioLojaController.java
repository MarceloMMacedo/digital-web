package br.com.apidigitalweb.controller.estoque;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.controller.BaseController;
import br.com.apidigitalweb.domin.estoque.AnuncioLoja;
import br.com.apidigitalweb.dto.AnuncioLojaDTO;
import br.com.apidigitalweb.dto.BaseDto;
import br.com.apidigitalweb.dto.Anuncio.AnuncioDto;
import br.com.apidigitalweb.service.AnuncioLojaService;
import br.com.apidigitalweb.service.BaseServic;

@RestController
@RequestMapping(value = ("/anunciosloja"))
public class AnuncioLojaController extends BaseController<AnuncioLoja> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	AnuncioLojaService service;
	
	@Override
	public BaseServic<AnuncioLoja> getService() {
		// TODO Auto-generated method stub
		return service;
	}
	@Override
	public ResponseEntity<Page<BaseDto>> findallpagedto(String nome, Pageable page) {
		Page<BaseDto> p=service.findallpagedto(nome, page);
		return ResponseEntity.ok(p)  ;
	}
	
	@PutMapping(value = "/newobjloja")
	public ResponseEntity<?> newobj(@RequestBody AnuncioLojaDTO obj) {
		try {
			obj = service.newobj(obj);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.ok(obj);
	}
	
	@GetMapping(value = "/anuncios")
	public ResponseEntity<Page<AnuncioDto>> anuncios(@RequestParam(defaultValue = "", value = "nome") String nome,
			Pageable page) {
		 
	 
		
		return ResponseEntity.ok(service.anuncios(nome, page));
	}
	
	@GetMapping(value = "/anuncio/{id}")
	public ResponseEntity<AnuncioDto> fingidanunciodto(@PathVariable Long id) {
	AnuncioLoja a=getService().fingbyid(id);
	AnuncioDto anuncioDto=new AnuncioDto(a);
		return ResponseEntity.ok(anuncioDto);
	}
}
