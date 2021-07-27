package br.com.apidigitalweb.dto.credenciais;

import java.io.Serializable;

public class CredenciaisDTOTokenEmployee implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;

	private String token;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
