package br.com.apidigitalweb.dto.financeiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.converters.StatusConverter;
import br.com.apidigitalweb.domin.contratos.Contrato;
import br.com.apidigitalweb.domin.contratos.FaturaContrato;
import br.com.apidigitalweb.domin.contratos.FichaLeitura;
import br.com.apidigitalweb.domin.contratos.GrupoFinanceiroContrato;
import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.BaseFatura;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.domin.financeiro.contaspagar.FaturaContasPagar;
import br.com.apidigitalweb.domin.ordem.CentroCustoFatura;
import br.com.apidigitalweb.domin.ordemservico.FaturaOrdemServico;
import br.com.apidigitalweb.domin.ordemvenda.FaturaVenda;
import br.com.apidigitalweb.domin.pessoa.Cliente;
import br.com.apidigitalweb.dto.SampleDto;
import br.com.apidigitalweb.enuns.TipoFaturaEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FaturasDto implements Serializable {

	private static final long serialVersionUID = 1L;
	protected long id;
	protected String nome;
	protected String origem;
	protected int numeroparcela;
	protected String parcela;
	protected SampleDto fornecedor = new SampleDto();
	protected long idordem;
	@DateTimeFormat(iso = ISO.DATE_TIME, pattern = "dd/MM/yyyy HH:mm:ss")
	protected Date dataVencimento;

	@DateTimeFormat(iso = ISO.DATE_TIME, pattern = "dd/MM/yyyy HH:mm:ss")
	protected Date dataPagamento;
	@DateTimeFormat(iso = ISO.DATE_TIME, pattern = "dd/MM/yyyy HH:mm:ss")
	protected Date diaQuitacao;

	protected String referente;
	protected String status;
	protected double valor;
	protected double jurus;
	protected double multa;
	protected double desconto;
	protected double total;
	protected SampleDto banco = new SampleDto();
	protected SampleDto cliente = new SampleDto();
	protected SampleDto contrato = new SampleDto();
	protected SampleDto grupoFinanceiro = new SampleDto();
	protected double valorExcedente;
	//protected List<FichaLeitura> fichaLeitura = new ArrayList<FichaLeitura>();
	private List<FichaLeituraDto> fichaLeitura = new ArrayList<FichaLeituraDto>();
	protected String descricao;
	protected String imagem;
	protected String extension;

	protected String imagemView;

	protected int totalParcela;

	protected SampleDto centroCusto = new SampleDto();

//	protected List<CentroCustoFatura> centroCustoFaturas = new ArrayList<>();

	public void setnewFaturaDto(BaseFatura b, String orige) {
		this.id = b.getId();
		this.nome = b.getNome();
		this.origem = orige;
		this.numeroparcela = b.getNumeroparcela();
		this.parcela = "" + b.getNumeroparcela() + "/" + "" + b.getTotalParcela();
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
	try {
		banco = new SampleDto(b.getBanco(), "");
	} catch (Exception e) {
		// TODO: handle exception
	}	

	}

	public FaturasDto(BaseFatura b, String origem) {
		super();
		this.id = b.getId();
		this.nome = b.getNome();
		this.origem = origem;
		this.numeroparcela = b.getNumeroparcela();
		this.parcela = "" + b.getNumeroparcela() + "/" + "" + b.getTotalParcela();
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
		idordem = b.getContrato().getId();
		setnewFaturaDto(b, TipoFaturaEnum.Contrato.toString());
		try{
			cliente = new SampleDto(b.getCliente(), "");
		}catch (Exception e) {
			// TODO: handle exception
		}
		try{contrato = new SampleDto(b.getContrato(), "");
		
		}catch (Exception e) {
			// TODO: handle exception
		}
		try{grupoFinanceiro = new SampleDto(b.getGrupoFinanceiro(), "");
		
		}catch (Exception e) {
			// TODO: handle exception
		}
		valorExcedente = b.getValorExcedente();
		//fichaLeitura = b.getFichaLeitura(); 
		descricao = b.getDescricao();
		totalParcela = b.getTotalParcela(); 
		try {

			centroCusto = new SampleDto(b.getCentroCusto(), ""); 
		} catch (Exception e) {
			// TODO: handle exception
		}
	//	centroCustoFaturas = b.getCentroCustoFaturas();
		 
	}

	public FaturasDto(FaturaOrdemServico b) {
		super();

		idordem = b.getOrdemServico().getId();
		setnewFaturaDto(b, TipoFaturaEnum.Servico.toString());
		/*
		 * this.id = b.getId(); this.nome = b.getNome();
		 * idordem=b.getOrdemServico().getId(); this.origem =
		 * TipoFaturaEnum.Servico.toString(); this.numeroparcela=b.getNumeroparcela();
		 * this.parcela = "" + b.getNumeroparcela() + "/" + "" + b.getTotalParcela();
		 * this.dataVencimento = b.getDataVencimento(); this.dataPagamento =
		 * b.getDataPagamento(); this.diaQuitacao = b.getDiaQuitacao(); this.referente =
		 * b.getDescricao(); this.status = b.getStatus(); this.valor = b.getValor();
		 * this.jurus = b.getJurus(); this.multa = b.getMulta(); this.desconto =
		 * b.getDesconto(); this.total = b.getTotal();
		 */
	}

	public FaturasDto(FaturaVenda b) {
		super();
		this.id = b.getId();
		this.nome = b.getNome();
		idordem = b.getOrdemVenda().getId();
		this.origem = TipoFaturaEnum.Venda.toString();
		this.numeroparcela = b.getNumeroparcela();
		this.parcela = "" + b.getNumeroparcela() + "/" + "" + b.getTotalParcela();
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
		this.numeroparcela = b.getNumeroparcela();
		this.parcela = "" + b.getNumeroparcela() + "/" + "" + b.getTotalParcela();
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
		fornecedor = new SampleDto(b.getFornecedor(), "");
	}
}
