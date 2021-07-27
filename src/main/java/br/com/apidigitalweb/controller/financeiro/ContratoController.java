package br.com.apidigitalweb.controller.financeiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apidigitalweb.controller.BaseController;
import br.com.apidigitalweb.domin.contratos.Contrato;
import br.com.apidigitalweb.domin.contratos.ItensContratoPatrimonio;
import br.com.apidigitalweb.domin.contratos.Patrimonio;
import br.com.apidigitalweb.repository.ContratoRepository;
import br.com.apidigitalweb.service.BaseServic;
import br.com.apidigitalweb.service.ContratoService;
 
public class ContratoController extends BaseController<Contrato> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	ContratoService service;

	@Autowired
	ContratoRepository rep;

	@Override
	public BaseServic<Contrato> getService() {
		// TODO Auto-generated method stub
		return service;
	}

	/*
	 * @DeleteMapping(value = "/fornecedorContrato/{id}") public
	 * ResponseEntity<Void> deletefornecedorContrato(@PathVariable Long id) {
	 * service.deletefornecedorContrato(id); return
	 * ResponseEntity.noContent().build();
	 * 
	 * }
	 */

	@GetMapping("/itens/{id}")
	public ResponseEntity<List<ItensContratoPatrimonio>> itens(@PathVariable Long id) {
		List<ItensContratoPatrimonio> itens = new ArrayList<ItensContratoPatrimonio>();
		for (Object[] o : rep.findAllItenspatrimonio()) {
			
			itens.add(new ItensContratoPatrimonio((Object) o[0] , (Object) o[1] , (Object) o[2] ,  (Object) o[3] , (Object) o[4] , 
					(Object) o[5] , (Object) o[6]));
			System.out.println((Object) o[0] + "," + (Object) o[1] + "," + (Object) o[2] + "," +  (Object) o[3] + "," + (Object) o[4] + "," + 
					(Object) o[5] + "," + (Object) o[6]);
			 
		}
		return ResponseEntity.ok(itens);
	}
}
