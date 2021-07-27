package br.com.apidigitalweb.domin.estoque;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.converters.CategoriaProdutoConverter;
import br.com.apidigitalweb.converters.SimNaoConverter;
import br.com.apidigitalweb.converters.UnidadeProdutoConverter;
import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.enuns.SimNaoEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor 
public class Produto extends BaseDomain implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * @ElementCollection
	 * 
	 * @CollectionTable() private List<String> categorias;
	 */
	@JoinColumn
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Modelo modelo;

	private String descricao;
	private String imagem1;
	private String imagem2;
	private String fabricante;

	 @ElementCollection 
	 @CollectionTable( joinColumns = @JoinColumn(name = "id") )  
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<FornecedorProduto> fornecedoresproduto = new ArrayList<>();
	
	/*@JsonIgnore
	@OneToMany(mappedBy = "produto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<AnuncioLoja> anuncioLojas = new ArrayList<>();
	*/
	/*
	 * @ElementCollection
	 * 
	 * @CollectionTable( joinColumns = @JoinColumn(name = "id") )
	 * 
	 * @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" }) private
	 * List<FornecedorProduto> fornecedoresproduto=new ArrayList<>();
	 */

	@Convert(converter = CategoriaProdutoConverter.class)
	private String categoria;

	private double peso;
	private double largura;
	private double comprimento;
	private double altura;

	@Convert(converter = UnidadeProdutoConverter.class)
	private String unidade;

	@Convert(converter = SimNaoConverter.class)
	private String isvalue = "Não";

	@Transient
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double valorFornecedo;

	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double valorfinal;

	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private double valorinterno;

	private String status;
	
	@Transient
	private SaldosProduto saldosProduto=new SaldosProduto();

	public double getValorFornecedo() {
		valorFornecedo = 0;
		try {
			for (FornecedorProduto fornecedorProduto : getFornecedoresproduto()) {
				if (fornecedorProduto.getValor() > valorFornecedo)
					valorFornecedo = fornecedorProduto.getValor();
			}
		} catch (Exception e) {

		}
		return valorFornecedo;
	}

	public double getValorfinal() {

		if (isvalue.equals(SimNaoEnum.Nao.getDescricao())) {
			return getValorinterno();
		} else
			return getValorFornecedo();
	}

}
