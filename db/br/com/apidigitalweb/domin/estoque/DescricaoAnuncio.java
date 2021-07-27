package br.com.apidigitalweb.domin.estoque;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class AnuncioLoja
 */
@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class DescricaoAnuncio implements Serializable {

	public DescricaoAnuncio(String titulo, String descricao) {
		super();
		this.titulo = titulo;
		this.descricao = descricao;
	}
	private static final long serialVersionUID = 1L;

	private String titulo;
	private String descricao;

}
