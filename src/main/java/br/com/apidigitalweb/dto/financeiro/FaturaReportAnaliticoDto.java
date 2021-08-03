package br.com.apidigitalweb.dto.financeiro;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.domin.financeiro.contaspagar.FaturaContasPagar;
import br.com.apidigitalweb.dto.SampleDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FaturaReportAnaliticoDto implements Serializable {

 

	private static final long serialVersionUID = 1L;

	protected Long id;
	protected String nome;
	protected String descricao;
	protected String imagem;
	protected String extension;
	private String parcela;
	private SampleDto historico = new SampleDto();

	protected int totalParcela;
	protected int numeroparcela;
	@DateTimeFormat(iso = ISO.DATE, pattern = "dd/MM/yyyy")
	protected Date dataVencimento;

	@DateTimeFormat(iso = ISO.DATE, pattern = "dd/MM/yyyy")
	protected Date dataPagamento;
	protected double total;
	protected SampleDto centroCusto = new SampleDto();

	public FaturaReportAnaliticoDto(FaturaContasPagar faturaContasPagar, SampleDto historico, int totalParcela, SampleDto centroCusto) {
		super();
		id=faturaContasPagar.getId();
		nome=faturaContasPagar.getNome();
		parcela=""+faturaContasPagar.getNumeroparcela() +"/"+ ""+totalParcela;
		this.historico = historico;
		this.totalParcela = totalParcela;
		this.centroCusto = centroCusto;
		total=faturaContasPagar.getTotal();
		dataVencimento=faturaContasPagar.getDataVencimento();
		dataPagamento=faturaContasPagar.getDataPagamento();
	}

}
