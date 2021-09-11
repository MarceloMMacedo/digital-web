package br.com.apidigitalweb.domin.ordem;

import java.io.Serializable;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.converters.SimNaoConverter;
import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class OrdemAtividade extends BaseDomain implements Serializable {

	protected static final long serialVersionUID = 1L;
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private CentroCusto centroCusto;
	
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Banco banco;
	
	@Convert(converter = SimNaoConverter.class)
	private String faturavel;

}
