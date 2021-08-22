package br.com.apidigitalweb.dto.reposicao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.apidigitalweb.domin.estoque.AnuncioContrato;
import br.com.apidigitalweb.domin.estoque.AnuncioLoja;
import br.com.apidigitalweb.domin.estoque.AnuncioWeb;
import br.com.apidigitalweb.domin.estoque.BaseAnuncio;
import br.com.apidigitalweb.dto.SampleDto;
import br.com.apidigitalweb.enuns.TipoAnuncioEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReposicaoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Data
	@NoArgsConstructor
	public class ItensReposicaoDto implements Serializable {

		private static final long serialVersionUID = 1L;
		private String descricao;
		private int qtd;
		private int id;
		private String tipoanuncio;
		private SampleDto anuncio;
		private double valorinterno;
		private double subtotal;
		private double valor;

		public ItensReposicaoDto(BaseAnuncio b, String tipoanuncio, int id) {
			super();
			this.descricao = b.getNome();
			this.qtd = (int) b.getSaldoReposicao();
			this.tipoanuncio = tipoanuncio;
			this.anuncio = new SampleDto(b, "");
			this.valorinterno = b.getValorInterno();
			this.valor = b.getValorFinal();
			this.id = id;
			subtotal=qtd*valorinterno;
		}
	}

	private List<ItensReposicaoDto> itensAnuncioLoja = new ArrayList<ItensReposicaoDto>();
	private double totalitensAnuncioLoja;
	private List<ItensReposicaoDto> itensAnuncioWeb = new ArrayList<ItensReposicaoDto>();
	private double totalitensAnuncioWeb;
	private List<ItensReposicaoDto> itensAnuncioContrato = new ArrayList<ItensReposicaoDto>();
	private double totalitensAnuncioContrato;

	public ReposicaoDto(List<AnuncioLoja> anuncioLojas, List<AnuncioWeb> anuncioWebs,
			List<AnuncioContrato> anuncioContratos) {

		totalitensAnuncioLoja = 0;
		for (AnuncioLoja anuncioLoja : anuncioLojas) {
			itensAnuncioLoja.add(new ItensReposicaoDto(anuncioLoja, TipoAnuncioEnum.Local.getDescricao(),itensAnuncioLoja.size()));
			totalitensAnuncioLoja += anuncioLoja.getValorInterno() * anuncioLoja.getSaldoReposicao();
		}

		totalitensAnuncioContrato = 0;
		for (AnuncioContrato anuncioContrato : anuncioContratos) {
			itensAnuncioContrato.add(new ItensReposicaoDto(anuncioContrato, TipoAnuncioEnum.Contrato.getDescricao(),itensAnuncioContrato.size()));
			totalitensAnuncioContrato += anuncioContrato.getValorInterno() * anuncioContrato.getSaldoReposicao();
		}
		totalitensAnuncioWeb = 0;
		for (AnuncioWeb anuncioWeb : anuncioWebs) {
			itensAnuncioWeb.add(new ItensReposicaoDto(anuncioWeb, TipoAnuncioEnum.Contrato.getDescricao(),itensAnuncioWeb.size()));
			totalitensAnuncioWeb += anuncioWeb.getValorInterno() * anuncioWeb.getSaldoReposicao();
		}

	}

}
