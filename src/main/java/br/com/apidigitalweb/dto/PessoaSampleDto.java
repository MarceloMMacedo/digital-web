package br.com.apidigitalweb.dto;

import java.io.Serializable;

import br.com.apidigitalweb.domin.pessoa.Cliente;
import br.com.apidigitalweb.domin.pessoa.Empresa;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PessoaSampleDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String cnpj;
	private String imagemview;
	private Endereco endereco = new Endereco();

	public PessoaSampleDto(Empresa e) {
		super();
		// this.id = e.getid;
		this.name = e.getName();
		this.cnpj = e.getCnpj();
		this.imagemview = e.getImagemview();
		endereco = new Endereco(e.getCep(), e.getLogradouro(), e.getComplemento(), e.getBairro(), e.getLocalidade(),
				e.getNumero(), e.getUf());
	}

	public PessoaSampleDto(Cliente e) {
		super();
		try {
			this.id = e.getId();
			this.name = e.getNome();
			this.cnpj = e.getCnpj();
			// this.imagemview = e.getImagemview();
			endereco = e.getEndereco();
		} catch (Exception er) {
			// TODO: handle exception
		}
	}

}
