package br.com.apidigitalweb.domin.pessoa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.converters.TipoProdutosConverter;
import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.contratos.Medidor;
import br.com.apidigitalweb.domin.contratos.Medidores;
import br.com.apidigitalweb.domin.estoque.Modelo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class Patrimonio
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
public class EquipamentoCliente extends BaseDomain implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * copiadora,impressora,transformador
	 */
	@Convert(converter = TipoProdutosConverter.class)
	private String tipo;
	
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Modelo modelo;
	   
	private Long contrato;

	@AttributeOverrides({
			@AttributeOverride(name = "medidorA4Inicial", column = @Column(name = "medidorA4InicialServico")),
			@AttributeOverride(name = "medidorA3Inicial", column = @Column(name = "medidorA3InicialServico")),
			@AttributeOverride(name = "medidorA4Final", column = @Column(name = "medidorA4FinalServico")),
			@AttributeOverride(name = "medidorA3Final", column = @Column(name = "medidorA3FinalServico")) })
	@Embedded 
	private Medidor medidorServico = new Medidor();
	 

	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Cliente cliente;
	
	private String serial;

}
