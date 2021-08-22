package br.com.apidigitalweb.dto.Anuncio;

import java.io.Serializable;

import br.com.apidigitalweb.domin.estoque.ItemProdutoAnuncio;
import br.com.apidigitalweb.dto.SampleDto;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class ItemProdutoAnuncioDto  implements Serializable {

	private static final long serialVersionUID = 1L;
	 
	private SampleDto produto;
	private double quantidade;	 
	private double valor; 
	private double subtotal;	 
	private String descricao;
	public ItemProdutoAnuncioDto(ItemProdutoAnuncio b) {
		super();
		this.produto =new SampleDto( b.getProduto(),"");
		this.quantidade = b.getQuantidade();
		this.valor = b.getValor();
		this.subtotal = b.getSubtotal();
		this.descricao = b.getDescricao();
	}
}
