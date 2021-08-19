package br.com.apidigitalweb.dto.financeiro;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.apidigitalweb.domin.contratos.FichaLeitura;
import br.com.apidigitalweb.domin.contratos.ItensContratoPatrimonio;
import br.com.apidigitalweb.domin.contratos.Medidor;
import br.com.apidigitalweb.dto.SampleDto;
import br.com.apidigitalweb.dto.contrato.ItensContratoPatrimonioDTO;
import lombok.Data;
@Data
public class FichaLeituraDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private ItensContratoPatrimonioDTO itensContratoPatrimonio;
	private Medidor medidores = new Medidor();
	private Date dataLeitura;
	private double valorContrato;
	private int medidorAnteriorA3;
	private int medidorAnteriorA4;
	private int medidorConvertidoA4;
	private int excedente;
	private int producao;
	private double valorExcedente;
	private double valorFinal;
	private SampleDto contrato;
	private String status;
	
	public FichaLeituraDto(FichaLeitura f) {
		super();
		this.itensContratoPatrimonio =new ItensContratoPatrimonioDTO( f.getItensContratoPatrimonio()) ;
		this.medidores = f.getMedidores();
		this.dataLeitura = f.getDataLeitura();
		this.valorContrato = f.getValorContrato();
		this.medidorAnteriorA3 = f.getMedidorAnteriorA3();
		this.medidorAnteriorA4 =f.getMedidorAnteriorA4();
		this.medidorConvertidoA4 = f.getMedidorConvertidoA4();
		this.excedente = f.getExcedente();
		this.producao = f.getProducao();
		this.valorExcedente = f.getValorExcedente();
		this.valorFinal = f.getValorFinal();
		this.contrato = new SampleDto(f.getContrato(),"");
		this.status = f.getStatus();
	}
	
	

}
