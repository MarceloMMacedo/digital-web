package br.com.apidigitalweb.dto.financeiro;

import java.util.ArrayList;
import java.util.List;

import br.com.apidigitalweb.domin.contratos.FaturaContrato;
import br.com.apidigitalweb.domin.contratos.FichaLeitura;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaturaContratoDto extends FaturasDto {
	private static final long serialVersionUID = 1L;
	private List<FichaLeituraDto> fichaLeitura = new ArrayList<FichaLeituraDto>();

	public FaturaContratoDto(FaturaContrato f) {
		super(f); 
		for(FichaLeitura fi:f.getFichaLeitura()) {
			fichaLeitura.add(new FichaLeituraDto(fi));
		}
	}
}
