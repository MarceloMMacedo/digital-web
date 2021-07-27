package br.com.apidigitalweb.domin.pessoa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.apidigitalweb.converters.PerfilConverter;
import br.com.apidigitalweb.domin.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class BasePessoa implements Serializable, BaseEntity {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	private String nome;
	private String descricao;
	private String cnpjcpf;
	private String ie;
	private String telefone;
	private String contato;
	private String emailcontato;
	private String telefonecontato;
	private String setorcontato;
	
	@Embedded
	@Column(name = "id", nullable = true, insertable = true, updatable = true)
	private Endereco endereco=new Endereco();
	
	private String status;
	private String imagem;
	private String extension;

	@DateTimeFormat(iso = ISO.DATE, pattern = "dd/MM/yyyy")
	private Date data_situacao;
	private String tipo;
	private String porte;
	private String natureza_juridica;
	private String capital_social;
	private String atividade_principal;
	private String nameFantasia;
	private BigDecimal limitecredito;
 
	@Column(unique = true)
	private String email;

	@JsonIgnore
	private String senha;
	//@ElementCollection(fetch=FetchType.EAGER)
		//@CollectionTable()
	 	@Convert(converter = PerfilConverter.class)
		private String rolers ;
	 	
	 	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasePessoa other = (BasePessoa) obj;
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
