package br.com.apidigitalweb.dto.financeiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.apidigitalweb.dto.SampleDto;
import lombok.Data;
import lombok.NoArgsConstructor;

 @Data
@NoArgsConstructor
public class ContasReceberDto  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private SampleDto cliente=new SampleDto();
	private SampleDto ordemvenda=new SampleDto();
	private SampleDto ordemservico=new SampleDto();

	@DateTimeFormat(iso = ISO.DATE , pattern = "dd/MM/yyyy")
	private Date datainicio;

	@DateTimeFormat(iso = ISO.DATE , pattern = "dd/MM/yyyy")
	private Date datafim;
	
	private List<FaturaContratoDto> faturasContrato=new ArrayList<>();
	private double totalContrato;
	

	private List<FaturasDto> faturasVendas=new ArrayList<>();
	private double totalVenda;
	

	private List<FaturasDto> faturasServico=new ArrayList<>();
	private double totalServico;
	
	private double total;

	public double getTotalContrato() {
		totalContrato=0;
		for(FaturasDto f: getFaturasContrato()) {
			totalContrato+=f.getTotal();
		}
		return totalContrato;
	}

	public double getTotalVenda() {
		totalVenda=0;
		for(FaturasDto f: getFaturasVendas()) {
			totalVenda+=f.getTotal();
		}
		return totalVenda;
	}

	public double getTotalServico() {
		totalServico=0;
		for(FaturasDto f: getFaturasServico()) {
			totalServico+=f.getTotal();
		}
		return totalServico;
	}

	public double getTotal() {
		total=getTotalContrato()+getTotalServico()+totalVenda;
		return total;
	}

	public ContasReceberDto(SampleDto cliente, List<FaturaContratoDto> faturasContrato, List<FaturasDto> faturasVendas,
			List<FaturasDto> faturasServico) {
		super();
		this.cliente = cliente;
		this.faturasContrato = faturasContrato;
		this.faturasVendas = faturasVendas;
		this.faturasServico = faturasServico;
	}
	
	
	

}
