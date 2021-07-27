package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.apidigitalweb.converters.StatusConverter;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.contratos.Medidor;
import lombok.Data;


@Embeddable
@Data
public class RemocaoPatrimonio  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@DateTimeFormat(iso = ISO.DATE, pattern = "dd/MM/yyyy")
	private Date dataremocao;
	
	private String motivoremocao;
	
	 
	@AttributeOverrides({
			@AttributeOverride(name = "medidorA4Inicial", column = @Column(name = "medidorA4InicialRemocao")),
			@AttributeOverride(name = "medidorA3Inicial", column = @Column(name = "medidorA3InicialRemocao")),
			@AttributeOverride(name = "medidorA4Final", column = @Column(name = "medidorA4FinalRemocao")),
			@AttributeOverride(name = "medidorA3Final", column = @Column(name = "medidorA3FinalRemocao")) })
	@Embedded
	private Medidor medidorRemocao = new Medidor();
}
