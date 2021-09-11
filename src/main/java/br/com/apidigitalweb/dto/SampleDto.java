package br.com.apidigitalweb.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.estoque.AnuncioLoja;
import br.com.apidigitalweb.domin.estoque.Produto;
import br.com.apidigitalweb.domin.pessoa.BasePessoa;
import br.com.apidigitalweb.domin.pessoa.Funcionario;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SampleDto implements Serializable  {

	private static final long serialVersionUID = 1L;
 
	private Long id;
	private String nome;
 
	
	public SampleDto(BasePessoa b,String imagem) {
		super();
		this.id = b.getId();
		this.nome = b.getNome();
		 
	}public SampleDto(Funcionario b) {
		super();
		this.id = b.getId();
		this.nome = b.getNome();
		 
	}
	public SampleDto(BaseDomain b,String imagem) {
		super();
		this.id = b.getId();
		this.nome = b.getNome();
		 
	}
	public SampleDto(BaseEntity b,String imagem) {
		super();
		this.id = b.getId();
		this.nome = b.getNome();
		 
	}
	
	public SampleDto(Produto b,String imagem) {
		super();
		this.id = b.getId();
		this.nome = b.getNome();
		 
		 
	}
	public SampleDto(AnuncioLoja b,String imagem) {
		super();
		this.id = b.getId();
		this.nome = b.getNome();
	 
		 
	}
}
