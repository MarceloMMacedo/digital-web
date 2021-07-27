package br.com.apidigitalweb.controller.pessoa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.apidigitalweb.domin.pessoa.Empresa;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.service.EmpresaService;

@RestController
@RequestMapping(value = "/empresas")
public class EmpresaController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private Empresa empresa;

	@Autowired
	private EmpresaService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Empresa> getEmpresa() {
		return ResponseEntity.ok().body(service.getEmpresa());
	}

	@RequestMapping(value = "/{cnpj}", method = RequestMethod.GET)
	public ResponseEntity<Empresa> getEmpresacnpj(@PathVariable String cnpj) {
		return ResponseEntity.ok().body(service.loadCnpj(cnpj));
	}

	@RequestMapping(value = "/newempresa/{cnpj}", method = RequestMethod.GET)
	public ResponseEntity<Empresa> getnewEmpresacnpj(@PathVariable String cnpj) {
		return ResponseEntity.ok().body(service.newemtras(cnpj));
	}

	@RequestMapping(value = "/getendereco/{cep}", method = RequestMethod.GET)
	public ResponseEntity<Endereco> getendereco(@PathVariable String cep) {
		return ResponseEntity.ok().body(service.getendereco(cep));
	}

	@PostMapping(value = "/uploadfile")
	public ResponseEntity<String> uplaodImage(@RequestBody MultipartFile file) {

		return ResponseEntity.ok(service.uploadFile(file));
	}

	@PutMapping(value = "/saveobj")
	public ResponseEntity<Long> saveobj(@RequestBody Empresa obj) {
		service.update(obj);
		return ResponseEntity.ok().build();
	}

}
