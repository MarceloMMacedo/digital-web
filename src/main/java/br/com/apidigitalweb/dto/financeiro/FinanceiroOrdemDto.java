package br.com.apidigitalweb.dto.financeiro;

import java.io.Serializable;
import java.util.Date;

import br.com.apidigitalweb.domin.ordem.FinanceiroOrdem;
import br.com.apidigitalweb.dto.SampleDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FinanceiroOrdemDto  implements Serializable {
	private static final long serialVersionUID = 1L;

	 
	private SampleDto banco;

	 
	protected SampleDto centroCusto;
 
	private String faturavel;

	private int parcelas;

	private Date datavencimento;

	public FinanceiroOrdemDto(FinanceiroOrdem c) {
		super();
		this.banco    = new SampleDto(c.getBanco(), "");
		this.centroCusto =   new SampleDto(c.getCentroCusto(), "");
		this.faturavel = c.getFaturavel();
		this.parcelas = c.getParcelas();
		this.datavencimento = c.getDatavencimento();
	}
	

}
