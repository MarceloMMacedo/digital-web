package br.com.apidigitalweb.domin.pessoa;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.converters.TipoProdutosConverter;
import br.com.apidigitalweb.domin.contratos.Medidor;
import br.com.apidigitalweb.domin.estoque.Modelo;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class Patrimonio
 */
@Data
@Embeddable
@NoArgsConstructor
public class EquipamentoCliente  implements  Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * copiadora,impressora,transformador
	 */
	//@Convert(converter = TipoProdutosConverter.class)
	private String tipo;
	 
	
	

	
	@JoinColumn(columnDefinition = " bigint ")
	@Column(name = "modelo_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })  
	private Modelo modelo;
	   
	private Long contrato;

	@AttributeOverrides({
			@AttributeOverride(name = "medidorA4Inicial", column = @Column(name = "medidorA4InicialServico")),
			@AttributeOverride(name = "medidorA3Inicial", column = @Column(name = "medidorA3InicialServico")),
			@AttributeOverride(name = "medidorA4Final", column = @Column(name = "medidorA4FinalServico")),
			@AttributeOverride(name = "medidorA3Final", column = @Column(name = "medidorA3FinalServico")) })
	@Embedded 
	private Medidor medidorServico = new Medidor();
	 
 
	private String serial;
	
	private String local;

}
