package br.com.apidigitalweb.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.contratos.Patrimonio;
import br.com.apidigitalweb.domin.estoque.AnuncioLoja;
import br.com.apidigitalweb.domin.estoque.AnuncioServico;
import br.com.apidigitalweb.domin.estoque.Produto;
import br.com.apidigitalweb.domin.pessoa.BasePessoa;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseDto implements Serializable  {

	private static final long serialVersionUID = 1L;
 
	private Long id;
	private String nome;
	private String descricao;
	private String imagem;
	private String modelo;
	private Date data;
	private double valor= 0.0;
	
	public BaseDto(BasePessoa b,String imagem) {
		super();
		this.id = b.getId();
		this.nome = b.getNome();
		this.descricao = b.getDescricao();
		this.imagem = imagem;  
	}
	public BaseDto(BaseDomain b,String imagem) {
		super();
		this.id = b.getId();
		this.nome = b.getNome();
		this.descricao = b.getDescricao();
		this.imagem = imagem;  
	}
	public BaseDto(BaseEntity b,String imagem) {
		super();
		this.id = b.getId();
		this.nome = b.getNome();
		this.descricao = b.getDescricao();
		this.imagem = imagem;  
	}
	
	public BaseDto(Produto b,String imagem) {
		super();
		this.id = b.getId();
		this.nome = b.getNome();
		this.descricao = b.getDescricao();
		this.imagem = imagem;  
		valor=b.getValorfinal();
		try{modelo=b.getModelo().getNome();}catch (Exception e) {
			// TODO: handle exception
		}
	}public BaseDto(Patrimonio b,String imagem) {
		super();
		this.id = b.getId();
		this.nome = b.getNome();
		this.descricao = b.getSerial();
		this.imagem = imagem;   
		try{modelo=b.getModelo().getNome();}catch (Exception e) {
			// TODO: handle exception
		}
	}
	public BaseDto(AnuncioLoja b,String imagem) {
		super();
		this.id = b.getId();
		this.nome = b.getNome();
		this.descricao = b.getDescricao();
		this.imagem = imagem;  
		valor=b.getValorFinal();
				
		 
	}
	public BaseDto(AnuncioServico b,String imagem) {
		super();
		this.id = b.getId();
		this.nome = b.getNome();
		this.descricao = b.getDescricao();
		this.imagem = imagem;  
		valor=b.getValorFinal(); 
	}
}
