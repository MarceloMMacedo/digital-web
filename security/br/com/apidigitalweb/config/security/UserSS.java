package br.com.apidigitalweb.config.security;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
 
import br.com.apidigitalweb.enuns.Perfil;

public class UserSS implements UserDetails {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String email;
	private String senha; 
	private Collection<? extends GrantedAuthority> authorities;

	public UserSS() {
	}

	public UserSS(Long id, String email, String senha,   String  perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha; 
		Collection c = new LinkedList<>();
		Perfil p =Perfil.findById(perfis);
		c.add(new SimpleGrantedAuthority(p.getId()));
		 
		this.authorities =c;// perfis.stream().map(x -> new SimpleGrantedAuthority(x.getId())).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	 

	/*
	 * public boolean hasRole(Perfil perfil) { return getAuthorities().contains(new
	 * SimpleGrantedAuthority(perfil.getDescricao())); }
	 */

	public boolean hasRole(Perfil perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}
}
