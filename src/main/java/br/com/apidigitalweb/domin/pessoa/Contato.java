package br.com.apidigitalweb.domin.pessoa;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class Contato implements Serializable {
	private static final long serialVersionUID = 1L;

	protected String contato;
	protected String emailcontato;
	protected String telefonecontato;
	protected String setorcontato;
}
