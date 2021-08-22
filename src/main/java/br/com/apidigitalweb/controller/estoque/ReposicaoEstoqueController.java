package br.com.apidigitalweb.controller.estoque;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.dto.reposicao.ReposicaoDto;
import br.com.apidigitalweb.service.ReposicaoEstoqueService;

@RestController
@RequestMapping(value = ("/reposicaoestoque"))
public class ReposicaoEstoqueController implements Serializable {
	 
		private static final long serialVersionUID = 1L;
		@Autowired
		private ReposicaoEstoqueService service;
		
		
		@RequestMapping(value = "",method = RequestMethod.GET)
		public ResponseEntity<ReposicaoDto> reposicaodto(){
			ReposicaoDto dto=service.reposicaoDto();
			return ResponseEntity.ok(dto);
		}
		

}
