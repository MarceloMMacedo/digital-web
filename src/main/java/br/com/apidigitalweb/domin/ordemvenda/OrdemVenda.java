package br.com.apidigitalweb.domin.ordemvenda;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apidigitalweb.converters.CalnalOrdemConverter;
import br.com.apidigitalweb.converters.StatusConverter;
import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.ordem.BaseOrdem;
import br.com.apidigitalweb.domin.ordem.FinanceiroOrdem;
import br.com.apidigitalweb.domin.pessoa.Cliente;
import br.com.apidigitalweb.domin.pessoa.Contato;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.domin.pessoa.Funcionario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class OrdemVenda
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
public class OrdemVenda  extends BaseOrdem implements  Serializable {

	private static final long serialVersionUID = 1L;
	 
	 
}
