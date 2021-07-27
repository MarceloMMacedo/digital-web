package br.com.apidigitalweb.domin.financeiro;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.converters.TipoMovimentoConverter;
import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class MovBanco
 */
@Getter
@Setter 
@NoArgsConstructor
@Entity
public class MovBanco extends BaseDomain implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1L;

	
	@JoinColumn()
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Banco banco;
	private Date dataMovimento;
	private double valor;
	/**
	 * entrada ou saida
	 */
	@Convert(converter = TipoMovimentoConverter.class)
	private String tipo;
	private Long codFaturaSaida;
	private Long codFaturaEntrada;

	//

}
