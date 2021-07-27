package br.com.apidigitalweb.controller.estoque;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.controller.BaseController;
import br.com.apidigitalweb.domin.estoque.AnuncioLoja;
import br.com.apidigitalweb.dto.AnuncioLojaDTO;
import br.com.apidigitalweb.dto.BaseDto;
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
		// TODO Auto-generated method stub
		return super.findallpagedto(nome, page);
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
}
