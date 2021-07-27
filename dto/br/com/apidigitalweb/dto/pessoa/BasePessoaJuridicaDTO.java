package br.com.apidigitalweb.dto.pessoa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.apidigitalweb.converters.PerfilConverter;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.domin.pessoa.QualName;
import br.com.apidigitalweb.domin.pessoa.TextCode;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
public class BasePessoaJuridicaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<TextCode> atividade_principal = new ArrayList<TextCode>();
	private String data_situacao;
	private String tipo;
	private String uf;

	private List<TextCode> atividades_secundarias = new ArrayList<TextCode>();

	private List<QualName> qsa = new ArrayList<QualName>();

	protected List<Endereco> enderecos = new ArrayList<>();

	private String situacao;
	private String bairro;
	private String logradouro;

	private String numero;
	private String cep;
	private String municipio;

	private String porte;
	private String abertura;
	private String natureza_juridica;
	private String fantasia;
	private String cnpj;
	private String ultima_atualizacao;
	private String status;
	private String complemento;
	private String efr;
	private String motivo_situacao;
	private String situacao_especial;
	private String data_situacao_especial;
	private String capital_social;

	private String nome;
	private String descricao;
	private String ie;
	private String telefone;
	private String contato;
	private String emailcontato;
	private String telefonecontato;
	private String setorcontato;

	private String imagem;
	private String extension;

	private BigDecimal limitecredito;

	private String email;

	private String senha;
	private String rolers;

}
