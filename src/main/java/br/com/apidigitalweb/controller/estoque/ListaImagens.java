package br.com.apidigitalweb.controller.estoque;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class ListaImagens implements Serializable {

	private static final long serialVersionUID = 1L;

	private String imagem;
	private String extension;
	@Transient
	private String srcImagem;
	
	public ListaImagens(String imagem, String extension) {
		super();
		this.imagem = imagem;
		this.extension = extension;
	}
}
