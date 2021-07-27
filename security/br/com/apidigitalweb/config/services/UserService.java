package br.com.apidigitalweb.config.services;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.apidigitalweb.config.security.UserSS;

public class UserService {
	
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
}
