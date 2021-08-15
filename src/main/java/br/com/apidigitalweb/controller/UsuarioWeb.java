package br.com.apidigitalweb.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.config.exception.AuthorizationException;
import br.com.apidigitalweb.config.security.UserSS;
import br.com.apidigitalweb.config.services.UserService;
import br.com.apidigitalweb.domin.pessoa.Funcionario;
import br.com.apidigitalweb.dto.UsuarioWebDTO;
import br.com.apidigitalweb.repository.FuncionarioRepository;
import br.com.apidigitalweb.service.FuncioanarioService;
import lombok.Data;

//@Data
@RestController

@RequestMapping(value = "/usuarioweb")
public class UsuarioWeb implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	@Autowired
	  FuncioanarioService service; ;
	
	@GetMapping()
	public ResponseEntity<UsuarioWebDTO> usuarioweb (){
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		Funcionario f= service.fingbyid(user.getId());
		
		UsuarioWebDTO usuarioWeb=new UsuarioWebDTO(f);
		System.out.println(usuarioWeb);
		return ResponseEntity.ok(usuarioWeb);
	}

}
