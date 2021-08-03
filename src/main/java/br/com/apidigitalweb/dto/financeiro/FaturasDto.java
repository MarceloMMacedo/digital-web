package br.com.apidigitalweb.dto.financeiro;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.apidigitalweb.domin.contratos.FaturaContrato;
import br.com.apidigitalweb.domin.financeiro.BaseFatura;
import br.com.apidigitalweb.domin.financeiro.contaspagar.FaturaContasPagar;
import br.com.apidigitalweb.domin.ordemservico.FaturaOrdemServico;
import br.com.apidigitalweb.domin.ordemvenda.FaturaVenda;
import br.com.apidigitalweb.enuns.TipoFaturaEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FaturasDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private String nome;
	private String origem;
	private String numeroparcela;

	@DateTimeFormat(iso = ISO.DATE_TIME, pattern = "dd/MM/yyyy HH:mm:ss")
	private Date dataVencimento;

	@DateTimeFormat(iso = ISO.DATE_TIME, pattern = "dd/MM/yyyy HH:mm:ss")
	private Date dataPagamento;
	@DateTimeFormat(iso = ISO.DATE_TIME, pattern = "dd/MM/yyyy HH:mm:ss")
	private Date diaQuitacao;

	private String referente;
	private String status;
	private double valor;
	private double jurus;
	private double multa;
	private double desconto;
	private double total;

	public FaturasDto(BaseFatura b, String origem) {
		super();
		this.id = b.getId();
		this.nome = b.getNome();
		this.origem = origem;
		this.numeroparcela = "" + b.getNumeroparcela() + "/" + "" + b.getTotalParcela();
		this.dataVencimento = b.getDataVencimento();
		this.dataPagamento = b.getDataPagamento();
		this.diaQuitacao = b.getDiaQuitacao();
		this.referente = b.getDescricao();
		this.status = b.getStatus();
		this.valor = b.getValor();
		this.jurus = b.getJurus();
		this.multa = b.getMulta();
		this.desconto = b.getDesconto();
		this.total = b.getTotal();
	}

	public FaturasDto(FaturaContrato b) {
		super();
		this.id = b.getId();
		this.nome = b.getNome();
		this.origem = TipoFaturaEnum.Contrato.toString();
		this.numeroparcela = "" + b.getNumeroparcela() + "/" + "" + b.getTotalParcela();
		this.dataVencimento = b.getDataVencimento();
		this.dataPagamento = b.getDataPagamento();
		this.diaQuitacao = b.getDiaQuitacao();
		this.referente = b.getDescricao();
		this.status = b.getStatus();
		this.valor = b.getValor();
		this.jurus = b.getJurus();
		this.multa = b.getMulta();
		this.desconto = b.getDesconto();
		this.total = b.getTotal();
	}

	public FaturasDto(FaturaOrdemServico b) {
		super();
		this.id = b.getId();
		this.nome = b.getNome();
		this.origem = TipoFaturaEnum.Contrato.toString();
		this.numeroparcela = "" + b.getNumeroparcela() + "/" + "" + b.getTotalParcela();
		this.dataVencimento = b.getDataVencimento();
		this.dataPagamento = b.getDataPagamento();
		this.diaQuitacao = b.getDiaQuitacao();
		this.referente = b.getDescricao();
		this.status = b.getStatus();
		this.valor = b.getValor();
		this.jurus = b.getJurus();
		this.multa = b.getMulta();
		this.desconto = b.getDesconto();
		this.total = b.getTotal();
	}

	public FaturasDto(FaturaVenda b) {
		super();
		this.id = b.getId();
		this.nome = b.getNome();
		this.origem = TipoFaturaEnum.Contrato.toString();
		this.numeroparcela = "" + b.getNumeroparcela() + "/" + "" + b.getTotalParcela();
		this.dataVencimento = b.getDataVencimento();
		this.dataPagamento = b.getDataPagamento();
		this.diaQuitacao = b.getDiaQuitacao();
		this.referente = b.getDescricao();
		this.status = b.getStatus();
		this.valor = b.getValor();
		this.jurus = b.getJurus();
		this.multa = b.getMulta();
		this.desconto = b.getDesconto();
		this.total = b.getTotal();
	}

	public FaturasDto(FaturaContasPagar b) {
		super();
		this.id = b.getId();
		this.nome = b.getNome();
		this.origem = TipoFaturaEnum.Contrato.toString();
		this.numeroparcela = "" + b.getNumeroparcela() + "/" + "" + b.getTotalParcela();
		this.dataVencimento = b.getDataVencimento();
		this.dataPagamento = b.getDataPagamento();
		this.diaQuitacao = b.getDiaQuitacao();
		this.referente = b.getDescricao();
		this.status = b.getStatus();
		this.valor = b.getValor();
		this.jurus = b.getJurus();
		this.multa = b.getMulta();
		this.desconto = b.getDesconto();
		this.total = b.getTotal();
	}
}
