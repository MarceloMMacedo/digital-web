package br.com.apidigitalweb.dto.financeiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.apidigitalweb.domin.financeiro.contaspagar.ContasPagar;
import br.com.apidigitalweb.domin.ordem.FinanceiroOrdem;
import br.com.apidigitalweb.dto.SampleDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContasPagarDto implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Long id;
	protected String nome;
	protected String descricao;
	private String nf;
	private double valor;
	private Date dataAbertura;
	private SampleDto historico;

	private String status;
	private SampleDto grupoFinanceiro;
	private List<FaturasDto> faturasAberta = new ArrayList<FaturasDto>();
	private List<FaturasDto> faturasQuit = new ArrayList<FaturasDto>();
	private double totalAberto;
	private double totalQuit;
	private FinanceiroOrdemDto financeiro = new FinanceiroOrdemDto();

	public ContasPagarDto(ContasPagar c) {
		super();
		this.id = c.getId();
		this.nome = c.getNome();
		this.descricao = c.getDescricao();
		this.nf = c.getNf();
		this.valor = c.getValor();
		this.dataAbertura = c.getDataAbertura();
		this.historico = new SampleDto(c.getHistorico(), "");
		this.status = c.getStatus();
		this.grupoFinanceiro = new SampleDto(c.getGrupoFinanceiro(), "");
		this.faturasAberta = c.getFaturasAberta();
		this.faturasQuit = c.getFaturasQuit();
		this.totalAberto = c.getTotalAberto();
		this.totalQuit = c.getTotaQuit();
		this.financeiro = new FinanceiroOrdemDto(c.getFinanceiro());
	}

}
