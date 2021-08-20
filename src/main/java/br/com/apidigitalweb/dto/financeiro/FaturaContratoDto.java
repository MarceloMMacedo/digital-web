package br.com.apidigitalweb.dto.financeiro;

import br.com.apidigitalweb.domin.contratos.FaturaContrato;
import br.com.apidigitalweb.domin.contratos.FichaLeitura;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FaturaContratoDto extends FaturasDto {
	private static final long serialVersionUID = 1L;


	public FaturaContratoDto(FaturaContrato f) {
		super(f); 
		for(FichaLeitura fi:f.getFichaLeitura()) {
			getFichaLeitura().add(new FichaLeituraDto(fi));
		}
	}
}
