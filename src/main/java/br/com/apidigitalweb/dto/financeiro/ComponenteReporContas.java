package br.com.apidigitalweb.dto.financeiro;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.apidigitalweb.dto.SampleDto;
import lombok.Data;

@Data
public class ComponenteReporContas {
	private SampleDto cliente;
	private SampleDto ordemvenda;
	private SampleDto ordemservico;

	@DateTimeFormat(iso = ISO.DATE , pattern = "dd/MM/yyyy")
	private Date datainicio;

	@DateTimeFormat(iso = ISO.DATE , pattern = "dd/MM/yyyy")
	private Date datafim;
}
