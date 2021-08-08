package br.com.apidigitalweb.dto.financeiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.domin.contratos.FaturaContrato;
import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.BaseFatura;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.domin.financeiro.contaspagar.ContasPagar;
import br.com.apidigitalweb.domin.financeiro.contaspagar.FaturaContasPagar;
import br.com.apidigitalweb.domin.financeiro.contaspagar.GrupoContaPagar;
import br.com.apidigitalweb.domin.financeiro.contaspagar.HistoricoContaPagar;
import br.com.apidigitalweb.domin.ordem.CentroCustoFatura;
import br.com.apidigitalweb.domin.ordemservico.FaturaOrdemServico;
import br.com.apidigitalweb.domin.ordemvenda.FaturaVenda;
import br.com.apidigitalweb.domin.pessoa.Fornecedor;
import br.com.apidigitalweb.dto.SampleDto;
import br.com.apidigitalweb.enuns.TipoFaturaEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FaturasPagarDto implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Long id;
	protected String nome;
	protected String descricao;
	protected String imagem;
	protected String extension;

	@Transient
	protected String imagemView;

	protected int totalParcela;
	protected int numeroparcela;

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

	private SampleDto historico;

	private SampleDto grupofinanceiro;

	private SampleDto contasPagar;

	private SampleDto fornecedor;

	protected SampleDto banco;

	protected SampleDto centroCusto;

	protected List<CentroCustoFatura> centroCustoFaturas = new ArrayList<>();

	public FaturasPagarDto(FaturaContasPagar b) {
		super();
		this.id = b.getId();
		this.nome = b.getNome();
		this.numeroparcela = b.getNumeroparcela();
		this.dataVencimento = b.getDataVencimento();
		this.dataPagamento = b.getDataPagamento();
		this.diaQuitacao = b.getDiaQuitacao();
		this.referente = b.getDescricao();
		this.status = b.getStatus();
		this.valor = b.getValor();
		this.jurus = b.getJurus();
		this.multa = b.getMulta();
		this.desconto = b.getDesconto();
		imagemView=b.getImagemView();
		this.total = b.getTotal();
		historico = new SampleDto(b.getHistorico(), "");
		grupofinanceiro = new SampleDto(b.getGrupofinanceiro(), "");
		contasPagar = new SampleDto(b.getContasPagar(), "");
		fornecedor = new SampleDto(b.getFornecedor(), "");
		banco = new SampleDto(b.getBanco(), "");
		centroCusto = new SampleDto(b.getCentroCusto(), "");

	}
}
