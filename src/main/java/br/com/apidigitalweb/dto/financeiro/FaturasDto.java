package br.com.apidigitalweb.dto.financeiro;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.apidigitalweb.domin.contratos.FaturaContrato;
import br.com.apidigitalweb.domin.financeiro.BaseFatura;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FaturasDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private String nome;
	private String origem;
	private String Numeroparcela;

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
		this.Numeroparcela = ""+b.getNumeroparcela() + "/" +""+b.getTotalParcela();
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
