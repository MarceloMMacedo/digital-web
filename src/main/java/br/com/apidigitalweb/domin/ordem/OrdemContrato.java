package br.com.apidigitalweb.domin.ordem;

import java.io.Serializable;

import javax.persistence.Entity;

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
public class OrdemContrato  extends BaseOrdem implements  Serializable {

	private static final long serialVersionUID = 1L;
	
	 
}
