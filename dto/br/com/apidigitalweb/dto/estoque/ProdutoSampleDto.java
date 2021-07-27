package br.com.apidigitalweb.dto.estoque;

import java.io.Serializable;

import br.com.apidigitalweb.domin.estoque.Produto;
import lombok.Data;
@Data
public class ProdutoSampleDto implements Serializable  {

	private static final long serialVersionUID = 1L;
 
	private Long id;
	private String nome; 
	private double valor= 0.0;
	public ProdutoSampleDto(Produto p) {
		super();
		this.id = p.getId();
		this.nome = p.getNome();
		this.valor = p.getValorfinal();
	}
	
	
}
