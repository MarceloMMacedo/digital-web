package br.com.apidigitalweb.domin.contratos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class Medidores
 */
@Data
@Embeddable
@NoArgsConstructor
public class Medidor  implements Serializable{
	 
		private static final long serialVersionUID = 1L;

 
	private int medidorA4Inicial;
	private int medidorA3Inicial;
	private int medidorA4Final;
	private int medidorA3Final;

}
