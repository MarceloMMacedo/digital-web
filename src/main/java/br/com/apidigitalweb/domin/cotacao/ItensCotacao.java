package br.com.apidigitalweb.domin.cotacao;

import java.io.Serializable;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.converters.TipoAnuncioConverter;
import br.com.apidigitalweb.domin.estoque.BaseAnuncio;
import br.com.apidigitalweb.domin.estoque.Produto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class ItensCotacao implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Produto produto;
	
	private int quantidade;
	private double valor;
	
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })	 
	private BaseAnuncio anuncio;
	
	@Convert(converter = TipoAnuncioConverter.class)
	private String tipoAnuncio;
	
	@Transient
	private double total;
	

}
