package br.com.apidigitalweb.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.config.security.JWTUtils;
import br.com.apidigitalweb.config.security.UserSS;
import br.com.apidigitalweb.config.services.UserService;
import br.com.apidigitalweb.dto.requetpass;
import br.com.apidigitalweb.service.FuncioanarioService;

@RestController
public class AuthResource {
	
	@Autowired
	private JWTUtils jwtUtil;

	@Autowired
	private FuncioanarioService funcioanarioService;

	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/restpasswordfuncionario", method = RequestMethod.POST)
	public ResponseEntity<Void> restpasswordfuncionario(@RequestBody requetpass email) {
		funcioanarioService.setpassword(email.getEmail());
		return ResponseEntity.noContent().build();
	}
	
}

