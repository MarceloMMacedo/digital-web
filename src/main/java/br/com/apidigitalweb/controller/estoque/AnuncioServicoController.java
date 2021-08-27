package br.com.apidigitalweb.controller.estoque;

import java.io.Serializable;

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
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.controller.BaseController;
import br.com.apidigitalweb.domin.estoque.AnuncioLoja;
import br.com.apidigitalweb.domin.estoque.AnuncioServico;
import br.com.apidigitalweb.dto.AnuncioLojaDTO;
import br.com.apidigitalweb.dto.BaseDto;
import br.com.apidigitalweb.dto.Anuncio.AnuncioDto;
import br.com.apidigitalweb.service.AnuncioServicoService;
import br.com.apidigitalweb.service.BaseServic;

@RestController
@RequestMapping(value = ("/anuncioservicos"))
public class AnuncioServicoController extends BaseController<AnuncioServico> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	AnuncioServicoService service;
	
	@Override
	public BaseServic<AnuncioServico> getService() {
		// TODO Auto-generated method stub
		return service;
	}
	
	@GetMapping(value = "/anuncio/{id}")
	public ResponseEntity<AnuncioDto> fingidanunciodto(@PathVariable Long id) {
	AnuncioServico a=getService().fingbyid(id);
	AnuncioDto anuncioDto=new AnuncioDto(a);
		return ResponseEntity.ok(anuncioDto);
	}
	 
}
