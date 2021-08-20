package br.com.apidigitalweb.dto.contrato;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.apidigitalweb.domin.contratos.Contrato;
import br.com.apidigitalweb.dto.BaseDto;
import br.com.apidigitalweb.dto.PessoaSampleDto;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SampleContratoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private PessoaSampleDto cliente;
	private String nome;
	@DateTimeFormat(iso = ISO.DATE, pattern = "dd/MM/yyyy")
	private Date dataInicio;
	private int periodo;
	private int diaLeitura;
	private double valorFinal;

	public SampleContratoDto(Contrato c) {
		super();
		try {
			this.id = c.getId();
			nome = c.getNome();
			this.cliente = new PessoaSampleDto(c.getCliente());
			this.dataInicio = c.getDataInicio();
			this.periodo = c.getPeriodo();
			this.diaLeitura = c.getDiaLeitura();
			this.valorFinal = c.getValorFinal();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
