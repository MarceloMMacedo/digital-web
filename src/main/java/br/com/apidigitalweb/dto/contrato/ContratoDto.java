package br.com.apidigitalweb.dto.contrato;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.apidigitalweb.domin.contratos.Contrato;
import br.com.apidigitalweb.domin.contratos.FaturaContrato;
import br.com.apidigitalweb.domin.contratos.ItensContratoPatrimonio;
import br.com.apidigitalweb.domin.estoque.AnuncioContrato;
import br.com.apidigitalweb.domin.pessoa.Empresa;
import br.com.apidigitalweb.dto.BaseDto;
import br.com.apidigitalweb.dto.PessoaSampleDto;
import br.com.apidigitalweb.dto.SampleDto;
import br.com.apidigitalweb.dto.Anuncio.AnuncioDto;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.enuns.TipoFaturaEnum;
import lombok.Data;

@Data
public class ContratoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Long id;
	protected String nome;
	protected String descricao;
	protected String imagem;
	protected String extension;

	protected String imagemView;

	private PessoaSampleDto empresa;
	private PessoaSampleDto cliente;

	@DateTimeFormat(iso = ISO.DATE, pattern = "dd/MM/yyyy")
	private Date dataInicio;
	private int periodo;
	private int diaLeitura;

	private int diaVencimento;
	private List<ItensContratoPatrimonio> itenspatrimonio = new ArrayList<ItensContratoPatrimonio>();
	private SampleDto grupoFinanceiro;
	private List<FaturasDto> faturaContratos;
	private double valorFinal;
	@Transient // To Do
	private List<AnuncioContrato> anuncioContratos = new ArrayList<AnuncioContrato>();

	@Transient // to do
	private List<FaturasDto> faturaAberta = new ArrayList<FaturasDto>();

	@Transient // To Do
	private List<FaturasDto> faturaQuit = new ArrayList<FaturasDto>();

	private double valorAberto;
	private double valorQuitado;

	public ContratoDto(Empresa e, Contrato c) {
		super();
		id = c.getId();
		nome = c.getNome();
		descricao = c.getDescricao();
		imagem = c.getImagem();
		extension = c.getExtension();

		imagemView = c.getImagemView();
		try {
			this.empresa = new PessoaSampleDto(e);

		} catch (Exception e1w) {
			// TODO: handle exception
		}
		try {
			this.cliente = new PessoaSampleDto(c.getCliente());
		} catch (Exception ew2) {
			// TODO: handle exception
		}
		this.dataInicio = c.getDataInicio();
		this.periodo = c.getPeriodo();
		this.diaLeitura = c.getDiaLeitura();
		this.diaVencimento = c.getDiaVencimento();
		itenspatrimonio = c.getItenspatrimonio();
		// this.itenspatrimonio = c.getItenspatrimonio().stream().map(x -> new
		// ItensContratoPatrimonioDTO(x)) .collect(Collectors.toList());
		try {
			this.grupoFinanceiro = new SampleDto(c.getGrupoFinanceiro(), "");

		} catch (Exception ew) {
			// TODO: handle exception
		}

		this.valorFinal = c.getValorFinal();

		this.faturaContratos = c.getFaturaContratos().stream()
				.map(x -> new FaturasDto(x, TipoFaturaEnum.Contrato.getDescricao())).collect(Collectors.toList());
		if (c.getAnuncioContratos() != null)
			this.anuncioContratos = c.getAnuncioContratos();

		List<FaturaContrato> anuncioCont = new ArrayList<>();

		anuncioCont = c.getFaturaContratos().stream()
				.filter(y -> y.getStatus().equals(StatusActiv.ABERTO.getDescricao())).collect(Collectors.toList());

		this.faturaAberta = anuncioCont.stream().map(x -> new FaturasDto(x, TipoFaturaEnum.Contrato.getDescricao()))
				.collect(Collectors.toList());

		anuncioCont = c.getFaturaContratos().stream().filter(y -> y.getStatus().equals(StatusActiv.QUIT.getDescricao()))
				.collect(Collectors.toList());

		this.faturaQuit = anuncioCont.stream().map(x -> new FaturasDto(x, TipoFaturaEnum.Contrato.getDescricao()))
				.collect(Collectors.toList());

		valorAberto = faturaAberta.stream().map(x -> x.getTotal()).reduce((double) 0, Double::sum);

		valorQuitado = faturaQuit.stream().map(x -> x.getTotal()).reduce((double) 0, Double::sum);

	}

}
