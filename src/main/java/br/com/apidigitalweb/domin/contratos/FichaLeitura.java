package br.com.apidigitalweb.domin.contratos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.converters.StatusConverter;
import br.com.apidigitalweb.enuns.SimNaoEnum;
import lombok.Data;

/**
 * Class FichaLeitura
 */
@Data
@Embeddable
public class FichaLeitura {

	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private ItensContratoPatrimonio itensContratoPatrimonio;

	@Embedded
	@Column(name = "id", nullable = true, insertable = true, updatable = true)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Medidor medidores = new Medidor();

	@DateTimeFormat(iso = ISO.DATE, pattern = "dd/MM/yyyy")
	private Date dataLeitura;
	
	private double valorContrato;

	@Transient
	private int medidorAnteriorA3;
	@Transient
	private int medidorAnteriorA4;
	@Transient
	private int medidorConvertidoA4;

	/**
	 * Itens_Patrimonio_franquia - producao
	 */
	@Transient
	private int excedente;

	/**
	 * medidor.producaoconvertidaA4 -
	 * itensContratoPatrimonio.patrimonio.medidorcontrato.producaoconvertidaA4
	 */
	@Transient
	private int producao;

	/**
	 * produção * ItemPatrimocio_valorUnitarioFranquia
	 */
	@Transient
	private double valorExcedente;

	/**
	 * valorfinal_itempatrimonio + valorProducao
	 */
	@Transient
	private double valorFinal;

	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Contrato contrato;
	@Convert(converter = StatusConverter.class)
	protected String status;

	public FichaLeitura(ItensContratoPatrimonio itensContratoPatrimonio, Contrato contrato) {
		super();
		this.itensContratoPatrimonio = itensContratoPatrimonio;
		this.contrato = contrato;
	}

	private int getMedidorAnteriorA3() {
		try {
			return itensContratoPatrimonio.getPatrimonio().getMedidorContrato().getMedidorA3Final();
		} catch (Exception e) {
			return 0;
		}
	}

	private int getMedidorAnteriorA4() {
		try {
			return itensContratoPatrimonio.getPatrimonio().getMedidorContrato().getMedidorA4Final();
		} catch (Exception e) {
			return 0;
		}
	}

	private int getMedidorConvertidoA4() {
		int leitura = getMedidorAnteriorA3() * 2;
		return getMedidorAnteriorA4() + leitura;
	}

	public int getProducao() {
		producao = 0;
		try {
			producao = medidores.getMedidorA3Final() * 2 + medidores.getMedidorA4Final() - getMedidorConvertidoA4();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return producao;
	}

	public int getExcedente() {
		excedente = 0;
		try {
			excedente = getProducao() - itensContratoPatrimonio.getFranquia();
			if (excedente < 0 || itensContratoPatrimonio.getIsFranquado() == SimNaoEnum.Nao.getDescricao())
				excedente = 0;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return excedente;
	}

	public double getValorExcedente() {
		valorExcedente = 0;
		try {
			valorExcedente = getExcedente() * itensContratoPatrimonio.getValorUnitarioFranquia();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return valorExcedente;
	}

	public double getValorFinal() {
		valorFinal = 0;
		try {
			valorFinal = getValorExcedente() + contrato.getValorFinal();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return valorFinal;
	}
}
