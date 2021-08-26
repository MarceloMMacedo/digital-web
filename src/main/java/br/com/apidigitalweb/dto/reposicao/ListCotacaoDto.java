package br.com.apidigitalweb.dto.reposicao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.apidigitalweb.domin.cotacao.Cotacao;
import br.com.apidigitalweb.domin.cotacao.ItensCotacao;
import br.com.apidigitalweb.domin.pessoa.Contato;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.dto.SampleDto;
import br.com.apidigitalweb.dto.reposicao.CotacaoDto.ItensCotacaoDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListCotacaoDto  implements Serializable { 
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	private Date dataAbertura; 
	private SampleDto fornecedor=new SampleDto();
	private String status; 
	private double total;
	private String tipoFrete;
	private double valorFrete;
	public ListCotacaoDto(Cotacao c) {
		super();
		this.id = c.getId();
		this.nome = c.getDescricao();
		this.dataAbertura = c.getDataAbertura();
		 
		this.fornecedor = new SampleDto(c.getFornecedor(), "");
		 total=0;
		this.status = c.getStatus();
		 for (ItensCotacao i : c.getItensCotacaos()) {
			 total= total + i.getTotal();
		} 
	//	this.total = c.getTotal();
		this.tipoFrete = c.getTipoFrete();
		this.valorFrete = c.getValorFrete();
	}
 
}
