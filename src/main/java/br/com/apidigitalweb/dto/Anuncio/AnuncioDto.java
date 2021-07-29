package br.com.apidigitalweb.dto.Anuncio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.apidigitalweb.domin.estoque.AnuncioContrato;
import br.com.apidigitalweb.domin.estoque.AnuncioLoja;
import br.com.apidigitalweb.domin.estoque.ItemProdutoAnuncio;
import br.com.apidigitalweb.dto.BaseDto;
import lombok.Data;
@Data
public class AnuncioDto  implements Serializable {

	private static final long serialVersionUID = 1L;
 
	private Long id;
	private String nome;
	private Integer saldo=0;
	private String imagemView;
	private BaseDto modelo =new BaseDto(); 
	private double valor= 0.0;
	private List<ItemProdutoAnuncio> itensProduto = new ArrayList<ItemProdutoAnuncio>();
	
	public AnuncioDto(AnuncioContrato a) {
		super();
		this.id = a.getId();
		this.nome = a.getNome();
		this.saldo = a.getSaldo();
		this.imagemView = a.getImagemView();	 
		this.valor = a.getValorFinal();
		itensProduto=a.getItensProduto();
	}
	
	
}
