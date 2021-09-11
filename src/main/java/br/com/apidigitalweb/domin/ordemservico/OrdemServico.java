package br.com.apidigitalweb.domin.ordemservico;

import java.io.Serializable;

import javax.persistence.Entity;

import br.com.apidigitalweb.domin.ordem.BaseOrdem;
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
public class OrdemServico  extends BaseOrdem implements  Serializable {

	private static final long serialVersionUID = 1L;
	 
}
