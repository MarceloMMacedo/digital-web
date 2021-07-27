package br.com.apidigitalweb.domin.pessoa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.converters.PerfilConverter;
import br.com.apidigitalweb.domin.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Data
@NoArgsConstructor
public   class BasePessoaJuridica implements Serializable, BaseEntity {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	protected Long id;
	
	@ElementCollection
    @CollectionTable(  joinColumns = @JoinColumn(name = "id"))
	protected List<TextCode> atividade_principal = new ArrayList<TextCode>();
	protected String data_situacao;
	protected String tipo; 
	
	@ElementCollection
    @CollectionTable(  joinColumns = @JoinColumn(name = "id"))
	protected List<TextCode> atividades_secundarias = new ArrayList<TextCode>();
	
	@ElementCollection
    @CollectionTable(  joinColumns = @JoinColumn(name = "id"))
	protected List<QualName> qsa = new ArrayList<QualName>();
	protected String situacao;
	
	@Embedded
	@Column(name = "id", nullable = true, insertable = true, updatable = true)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	protected Endereco endereco=new Endereco();
	
	@ElementCollection
    @CollectionTable(  joinColumns = @JoinColumn(name = "id"))
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	protected List<Endereco> enderecos = new ArrayList<>();
	 
	protected String porte;
	protected String abertura;
	protected String natureza_juridica;
	protected String fantasia;
	protected String cnpj;
	protected String ultima_atualizacao;
	protected String status; 
	protected String efr;
	protected String motivo_situacao;
	protected String situacao_especial;
	protected String data_situacao_especial;
	protected String capital_social;

	protected String nome;
	protected String descricao; 
	protected String ie;
	protected String telefone;
	protected String contato;
	protected String emailcontato;
	protected String telefonecontato;
	protected String setorcontato;

	protected String imagem;
	protected String extension;
	
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	protected BigDecimal limitecredito;

	@Column(unique = true)
	protected String email;

	@JsonIgnore
	protected String senha;
	// @ElementCollection(fetch=FetchType.EAGER)
	// @CollectionTable()
	@Convert(converter = PerfilConverter.class)
	protected String rolers;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasePessoaJuridica other = (BasePessoaJuridica) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	

	 
}
