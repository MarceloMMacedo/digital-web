package br.com.apidigitalweb.dto.Anuncio;

import java.io.Serializable;
import java.util.Date;

import br.com.apidigitalweb.domin.estoque.AnuncioContrato;
import br.com.apidigitalweb.domin.estoque.AnuncioLoja;
import br.com.apidigitalweb.dto.BaseDto;
import lombok.Data;
@Data
public class AnuncioDto  implements Serializable {

	private static final long serialVersionUID = 1L;
 
	private Long id;
	private String nome;
	private Integer saldo=0;
	private String imagem;
	private BaseDto modelo =new BaseDto(); 
	private double valor= 0.0;
	public AnuncioDto(AnuncioContrato a) {
		super();
		this.id = a.getId();
		this.nome = a.getNome();
		this.saldo = a.getSaldo();
		this.imagem = a.getImagemView();	 
		this.valor = a.getValorFinal();
	}
	
	
}
