package br.com.apidigitalweb.domin.estoque;

import java.io.Serializable;

import javax.persistence.Entity;

import br.com.apidigitalweb.domin.BaseDomain;
import br.com.apidigitalweb.domin.BaseEntity;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class Unidade extends BaseDomain implements BaseEntity, Serializable {

 
	private static final long serialVersionUID = 1L;

}
