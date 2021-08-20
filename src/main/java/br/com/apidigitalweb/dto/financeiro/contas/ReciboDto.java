package br.com.apidigitalweb.dto.financeiro.contas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.apidigitalweb.domin.contratos.FaturaContrato;
import br.com.apidigitalweb.domin.ordemservico.FaturaOrdemServico;
import br.com.apidigitalweb.domin.ordemservico.OrdemServico;
import br.com.apidigitalweb.domin.ordemvenda.FaturaVenda;
import br.com.apidigitalweb.domin.ordemvenda.ItensOrdemVenda;
import br.com.apidigitalweb.domin.ordemvenda.OrdemVenda;
import br.com.apidigitalweb.domin.pessoa.Contato;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.dto.SampleDto;
import br.com.apidigitalweb.enuns.TipoFaturaEnum;
import br.com.apidigitalweb.service.FaturaVendaService;
import br.com.apidigitalweb.util.Extenso;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReciboDto implements Serializable {

	@Data
	@NoArgsConstructor
	public class ItensRecibo implements Serializable {

		private static final long serialVersionUID = 1L;
		private int qtd;
		private String descricao;
		private double valorUnitario;
		private double valortotal;
		public ItensRecibo(int qtd, String descricao, double valor,double valortotal) {
			super();
			this.qtd = qtd;
			this.descricao = descricao;
			this.valorUnitario = valor;
			this.valortotal=valortotal;
		} 

	}

	private static final long serialVersionUID = 1L;

	private SampleDto cliente = new SampleDto();
	private Endereco endereco = new Endereco();
	private Contato contato = new Contato();
	private Date data;
	private String descricao;
	private String atividade;
	private String setorentrega;
	private Long id;
	private double valor;
	private List<ItensRecibo> itensrecibos=new ArrayList<ItensRecibo>();
	
	public ReciboDto(OrdemVenda o,FaturaVenda f) {
		super();
		this.cliente = new SampleDto(o.getCliente(), "");
		this.endereco = o.getEndereco();
		this.contato = o.getContato();
		this.data = o.getDataConclusao();

		this.valor=f.getTotal();
		this.descricao = "         Recebi da(o) " + getCliente().getNome().toUpperCase() + " a quantia de "
				+ new Extenso(valor).toString().toUpperCase()
				+ " referente a vendas/serviços abaixo descritos localizado na(o) "
				+  getEndereco().getLogradouro() + ", " +  getEndereco().getNumero() + " - "
				+  getEndereco().getBairro() + " - " +  getEndereco().getLocalidade();
		;
		this.atividade = TipoFaturaEnum.Venda.toString();
		this.setorentrega = o.getSetorentrega();
		this.id = f.getId();
		for(ItensOrdemVenda i:o.getItensOrdemVenda()) {
			ItensRecibo itensRecibo=new ItensRecibo(i.getQuantidade(), i.getProduto(), i.getValorUnitario(),i.getValortotal());
			itensrecibos.add(itensRecibo);
		}
		
	}
	public ReciboDto(OrdemServico o,FaturaOrdemServico  f) {
		super();
		this.cliente = new SampleDto(o.getCliente(), "");
		this.endereco = o.getEndereco();
		this.contato = o.getContato();
		this.data = o.getDataConclusao();

		this.valor=f.getTotal();
		this.descricao = "         Recebi da(o) " + getCliente().getNome().toUpperCase() + " a quantia de "
				+ new Extenso(valor).toString().toUpperCase()
				+ " referente a vendas/serviços abaixo descritos localizado na(o) "
				+  getEndereco().getLogradouro() + ", " +  getEndereco().getNumero() + " - "
				+  getEndereco().getBairro() + " - " +  getEndereco().getLocalidade();
		;
		this.atividade = TipoFaturaEnum.Servico.toString();
		this.setorentrega = o.getSetorentrega();
		this.id = f.getId();
		for(ItensOrdemVenda i:o.getItensOrdemVenda()) {
			ItensRecibo itensRecibo=new ItensRecibo(i.getQuantidade(), i.getProduto(), i.getValorUnitario(),i.getValortotal());
			itensrecibos.add(itensRecibo);
		}
		
	}

}
