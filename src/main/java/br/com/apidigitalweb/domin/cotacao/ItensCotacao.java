package br.com.apidigitalweb.domin.cotacao;

import java.io.Serializable;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import br.com.apidigitalweb.converters.TipoAnuncioConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class ItensCotacao implements Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String descricao;
	private Integer qtd;
	private Long anuncio;
	private double valorinterno;
	private double subtotal;
	private double valor;	
	protected String unidade;

	@Convert(converter = TipoAnuncioConverter.class)
	private String tipoanuncio;
 
	@Transient
	private double total;
	

	public double getTotal() {
		total = 0;
		try {

			total = qtd * valor;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return total;
	}

 
}