package br.com.apidigitalweb.dto.financeiro.contas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import org.joda.time.DateTime;

import br.com.apidigitalweb.domin.contratos.Contrato;
import br.com.apidigitalweb.domin.contratos.FaturaContrato;
import br.com.apidigitalweb.domin.contratos.FichaLeitura;
import br.com.apidigitalweb.domin.contratos.ItensContratoPatrimonio;
import br.com.apidigitalweb.util.Extenso;
import lombok.Data;

@Data
public class ReciboContratoDto implements Serializable {
	@Data
	public class EquipamentoRecibo implements Serializable {
		private static final long serialVersionUID = 1L;
		private String imagem;
		private String serial;
		private String setor;
		private double valor;
		private Long idpatrimonio;
		private String descricao;
		private int medidorAnteriorA3;
		private int medidorAnteriorA4;
		private int medidorAnteriorfinalA3;
		private int medidorAnteriorfinalA4;
		private int producao;
		private int excedente;
		private double valorExcedente;
		private double valorFinal;

		public EquipamentoRecibo(ItensContratoPatrimonio i) {
			super();
			this.serial = i.getPatrimonio().getSerial();
			this.setor = i.getSetor();
			this.valor = i.getValorFinal();
			descricao = i.getPatrimonio().getNome();
			idpatrimonio = i.getPatrimonio().getId();

		}

		public EquipamentoRecibo(FichaLeitura f) {
			super();
			this.serial = f.getItensContratoPatrimonio().getPatrimonio().getSerial();
			this.setor = f.getItensContratoPatrimonio().getSetor();
			this.valor = f.getValorFinal();
			descricao = f.getItensContratoPatrimonio().getPatrimonio().getNome();
			idpatrimonio = f.getItensContratoPatrimonio().getPatrimonio().getId();

			this.medidorAnteriorA3 = f.getMedidorAnteriorA3();
			this.producao = f.getProducao();
			this.valorExcedente = f.getValorExcedente();
			this.medidorAnteriorA4 = f.getMedidorAnteriorA4();

			this.medidorAnteriorfinalA4 = f.getMedidores().getMedidorA4Final();
			 excedente=f.getExcedente();
			this.medidorAnteriorfinalA3 = f.getMedidores().getMedidorA3Final();
			
			this.valorFinal = f.getValorFinal();
		}

	}

	private static final long serialVersionUID = 1L;

	private String descricao;
	private String periodo;
	private double valor;
	private Date vencimento;
	private Long idcontrato;
	private Long idfatura;

	private double acrecimosatraso;
	private double valorexcedente;
	private double valorcontrato;

	private List<EquipamentoRecibo> equipamentos = new ArrayList<ReciboContratoDto.EquipamentoRecibo>();

	public ReciboContratoDto(Contrato c, FaturaContrato f) {
		super();
		this.descricao = "         Recebi da(o) " + c.getCliente().getNome().toUpperCase() + " a quantia de "
				+ new Extenso(f.getTotal()).toString().toUpperCase()
				+ " referente a aluguel de equipamentos abaixo descritos localizado na(o) "
				+ c.getCliente().getEndereco().getLogradouro() + ", " + c.getCliente().getEndereco().getNumero() + " - "
				+ c.getCliente().getEndereco().getBairro() + " - " + c.getCliente().getEndereco().getLocalidade();
		this.valor = f.getTotal();
		this.idcontrato = c.getId();
		this.idfatura = f.getId();
		vencimento = f.getDataVencimento();
		DateTime plusPeriod = new DateTime(vencimento);
		plusPeriod = plusPeriod.minus(org.joda.time.Period.months(1));
		int mes = plusPeriod.getMonthOfYear();
		int ano = plusPeriod.getYear();
		periodo = getmes(mes) + "/" + "" + ano;
		for (FichaLeitura i : f.getFichaLeitura()) {
			equipamentos.add(new EquipamentoRecibo(i));
		}
		/*
		 * for (ItensContratoPatrimonio i : c.getItenspatrimonio()) {
		 * equipamentos.add(new EquipamentoRecibo(i)); }
		 */
		acrecimosatraso = f.getJurus() + f.getMulta();
		valorexcedente = f.getValorExcedente();
		this.valor = f.getTotal();
		valorcontrato=c.getValorFinal();
	}

	private String getmes(int mes) {
		switch (mes) {
		case 1:
			return "Janeiro";
		case 2:
			return "Fevereiro";
		case 3:
			return "Março";
		case 4:
			return "Abril";
		case 5:
			return "Maio";
		case 6:
			return "Junho";
		case 7:
			return "Julho";
		case 8:
			return "Agosto";
		case 9:
			return "Setembro";
		case 10:
			return "Outubro";
		case 11:
			return "Novembro";
		case 12:
			return "Dezembro";
		}
		return null;
	}
}
