package br.com.apidigitalweb.domin.pessoa;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data 
@Embeddable
public class QualName implements Serializable {
	private static final long serialVersionUID = 1L;
	private String qual;
	private String nome;
}
