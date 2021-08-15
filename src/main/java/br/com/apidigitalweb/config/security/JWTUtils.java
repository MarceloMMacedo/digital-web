package br.com.apidigitalweb.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtils {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	public String generateToken(String username, String role) {
		 

		String financeiro = "false";
		String estoque = "false";
		String servico = "false";
		switch (role) {
		case "ROLE_ADMG":
			financeiro = "true";
			estoque = "true";
			servico = "true";
			break;

		case "ROLE_OPF":
			financeiro = "true";
			estoque = "false";
			servico = "false";
			break;

		case "ROLE_SRV":
			financeiro = "false";
			estoque = "false";
			servico = "true";
			break;		 

		case "ROLE_EST":
			financeiro = "false";
			estoque = "true";
			servico = "false";
			break;

		default:
			financeiro = "true";
			estoque = "false";
			servico = "false";
			break;
		}
		return Jwts.builder().setSubject(username)
				.claim("role", role) 
				.claim("financeiro", financeiro)
				.claim("estoque",  estoque )
				.claim("servico", servico) 
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
	}

	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}

	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}
}
