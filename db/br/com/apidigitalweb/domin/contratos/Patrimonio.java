package br.com.apidigitalweb.domin.contratos;

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
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.converters.TipoProdutosConverter;
import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
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
public class Patrimonio extends BaseDomain implements BaseEntity, Serializable {

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
	
	private String codPatrimonio;

	@AttributeOverrides({ @AttributeOverride(name = "medidorA4Inicial", column = @Column(name = "medidorA4Inicial")),
			@AttributeOverride(name = "medidorA3Inicial", column = @Column(name = "medidorA3Inicial")),
			@AttributeOverride(name = "medidorA4Final", column = @Column(name = "medidorA4Final")),
			@AttributeOverride(name = "medidorA3Final", column = @Column(name = "medidorA3Final")) })
	@Embedded
	@Column(name = "id", nullable = true, insertable = true, updatable = true)
	private Medidor medidoresInicial = new Medidor();

	@ElementCollection
	@CollectionTable(joinColumns = @JoinColumn(name = "id"))
	private List<Medidores> medidores;

	private Long contrato;

	@AttributeOverrides({
			@AttributeOverride(name = "medidorA4Inicial", column = @Column(name = "medidorA4InicialServico")),
			@AttributeOverride(name = "medidorA3Inicial", column = @Column(name = "medidorA3InicialServico")),
			@AttributeOverride(name = "medidorA4Final", column = @Column(name = "medidorA4FinalServico")),
			@AttributeOverride(name = "medidorA3Final", column = @Column(name = "medidorA3FinalServico")) })
	@Embedded

	private Medidor medidorServico = new Medidor();
	@AttributeOverrides({
			@AttributeOverride(name = "medidorA4Inicial", column = @Column(name = "medidorA4InicialContrato")),
			@AttributeOverride(name = "medidorA3Inicial", column = @Column(name = "medidorA3InicialContrato")),
			@AttributeOverride(name = "medidorA4Final", column = @Column(name = "medidorA4FinalContrato")),
			@AttributeOverride(name = "medidorA3Final", column = @Column(name = "medidorA3FinalContrato")) })
	@Embedded
	private Medidor medidorContrato = new Medidor();
	
	private String serial;

}
