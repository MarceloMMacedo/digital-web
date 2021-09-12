package br.com.apidigitalweb.dto.ordem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.apidigitalweb.domin.ordem.BaseOrdem;
import br.com.apidigitalweb.domin.ordem.FinanceiroOrdem;
import br.com.apidigitalweb.domin.ordemservico.OrdemServico;
import br.com.apidigitalweb.domin.pessoa.Contato;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.domin.pessoa.EquipamentoCliente;
import br.com.apidigitalweb.dto.SampleDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrdemDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	private String descricao;
	private String imagem;
	private String extension;
	private String imagemView;
	private String canal;
	private SampleDto cliente;
	private Date dataAbertura;
	private Date dataProgramada;
	private Date dataConclusao;
	private SampleDto vendedor;
	private EquipamentoCliente equipamento;
	private SampleDto tecnico;
	private String garantia;
	private List<ItensInsOrdemDto> itensInsOrdemDtos = new ArrayList<>();
	private String setorentrega;
	private double total;
	private String status;
	private Endereco endereco = new Endereco();
	private Contato contato = new Contato();
	private String formaEntrega;
	private double valorEntrega;

	private String origem;

	private FinanceiroOrdem financeiroOrdem = new FinanceiroOrdem();
	public void setOrdemDto(BaseOrdem b, String origem, List<ItensInsOrdemDto> itensInsOrdemDtos) {

		this.id = b.getId();
		this.nome = b.getNome();
		this.descricao = b.getDescricao();
		this.imagem = b.getImagem();
		this.extension = b.getExtension();
		this.imagemView = b.getImagemView();
		this.canal = b.getCanal();
		this.cliente = new SampleDto(b.getCliente(), "");
		this.dataAbertura = b.getDataAbertura();
		this.dataProgramada = b.getDataProgramada();
		this.dataConclusao = b.getDataConclusao();
		this.vendedor = new SampleDto(b.getVendedor(), "");
		this.equipamento = b.getEquipamento();
		this.tecnico = new SampleDto(b.getTecnico(), "");
		this.garantia = b.getGarantia();
		this.itensInsOrdemDtos = itensInsOrdemDtos;
		this.setorentrega = b.getSetorentrega();
		this.total = b.getTotal();
		this.status = b.getStatus();
		this.endereco = b.getEndereco();
		this.contato = b.getContato();
		this.formaEntrega = b.getFormaEntrega();
		this.valorEntrega = b.getValorEntrega();
		this.origem = origem;
		this.financeiroOrdem = b.getFinanceiroOrdem();
	}
	public  OrdemDto(OrdemServico b ) {
		 super();
		 setOrdemDto(b, "Ordem Serviço", b.getItensInsOrdemDtos());
		
	}
}
