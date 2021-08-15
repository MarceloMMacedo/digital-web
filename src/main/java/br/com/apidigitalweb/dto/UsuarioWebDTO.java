package br.com.apidigitalweb.dto;

import br.com.apidigitalweb.domin.pessoa.Funcionario;
import lombok.Data;

@Data
public class UsuarioWebDTO {
	private String id;
	private String email;
	private String avatar;
	private String regra;
	private String nomeGuerra;

	public UsuarioWebDTO(Long id, String email, String avatar, String regra, String nomeGuerra) {
		this.id = String.valueOf(id);
		this.email = email;
		this.avatar = avatar;
		this.regra = regra;
		this.nomeGuerra = nomeGuerra;
	}

	public UsuarioWebDTO(Funcionario f) {
		this.id = String.valueOf(f.getId());
		this.email = f.getEmail();
		this.avatar = f.getImagemView();
		this.regra = f.getRolers();
		this.nomeGuerra = f.getNomeGuerra();
	}
}
