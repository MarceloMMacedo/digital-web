package br.com.apidigitalweb.controller.ordem;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.ordem.BaseOrdem;
import br.com.apidigitalweb.domin.pessoa.Cliente;
import br.com.apidigitalweb.domin.pessoa.Contato;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.domin.pessoa.EquipamentoCliente;
import br.com.apidigitalweb.dto.BaseDto;
import br.com.apidigitalweb.dto.SampleDto;
import br.com.apidigitalweb.service.BaseServic;
import br.com.apidigitalweb.service.BaseServicOrdem;
import lombok.Data;

@Data
public class BaseOrdemController<T extends BaseOrdem> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	BaseServicOrdem<T> service;

	@GetMapping(value = "/listany")
	public ResponseEntity<List<?>> listany() {
		List<?> pages = getService().listany();
		return ResponseEntity.ok(pages);
	}

	@GetMapping()
	public ResponseEntity<List<T>> getAll() {
		List<T> pages = getService().getAll();
		return ResponseEntity.ok(pages);
	}
	
	@GetMapping(value = "/lisalltecnicosampledto")
	public ResponseEntity<List<?>> lisalltecnicosampledto( ) {
		return ResponseEntity.ok(getService().lisalltecnicosampledto());
	}

	@GetMapping(value = "/getnamesclientes/{nome}")
	public ResponseEntity<List<?>> getnamesclientes(@PathVariable("nome") String id) {

		return ResponseEntity.ok(getService().getnamesclientes(id));
	}

	@GetMapping(value = "/getcontatoosclient/{id}")
	public ResponseEntity<List<?>> getcontatoosclient(@PathVariable("id") Long id) {
		return ResponseEntity.ok(getService().getcontatoosclient(id));
	}

	@PostMapping(value = "/addContato/{id}")
	public ResponseEntity<Void> addContato(@PathVariable("id") Long id, @RequestBody Contato contato) {
		getService().addContato(id, contato);
		return ResponseEntity.noContent().build();
	}

	@PostMapping(value = "/addEndereco/{id}")
	public ResponseEntity<Void> addAndress(@PathVariable("id") Long id, @RequestBody Endereco endereco) {
		getService().addAndress(id, endereco);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/getenderecosclient/{id}")
	public ResponseEntity<List<?>> getenderecosclient(@PathVariable("id") Long id) {
		return ResponseEntity.ok(getService().getenderecosclient(id));
	}

	@GetMapping(value = "/getequipamentoclient/{id}")
	public ResponseEntity<List<?>> getequipamentoclient(@PathVariable("id") Long id) {
		return ResponseEntity.ok(getService().getequipamentoclient(id));
	}

	@PostMapping(value = "/addequipamentocliente/{id}")
	public ResponseEntity<Void> addequipamentocliente(@PathVariable("id") Long id,
			@RequestBody EquipamentoCliente equipamentoCliente) {
		getService().addequipamentocliente(id, equipamentoCliente);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/cnpj/{cnpj}")
	public ResponseEntity<?> receitaws(@PathVariable("cnpj") String cnpj) {
		Cliente t = getService().receitaws(cnpj);
		return ResponseEntity.ok(t);
	}

	@GetMapping(value = "/cep/{cep}")
	public ResponseEntity<?> getcep(@PathVariable("cep") String cep) {
		Endereco t = getService().getEndereco(cep);
		return ResponseEntity.ok(t);
	}

	@GetMapping(value = "/getnomes")
	public ResponseEntity<List<String>> getnomes() {

		return ResponseEntity.ok(getService().getnomes());
	}

	@GetMapping(value = "/getallpagesampledto")
	public ResponseEntity<Page<SampleDto>> getallpagesampledto(
			@RequestParam(defaultValue = "", value = "nome") String nome, Pageable page) {
		Page<T> lista = getService().findallpage(nome, page);
		Page<SampleDto> pages = getService().findallpage(nome, page).map(x -> new SampleDto(x, ""));
		return ResponseEntity.ok(pages);
	}

	@GetMapping(value = "/getallsample")
	public ResponseEntity<List<BaseDto>> getAllSample() {

		List<BaseDto> pages = getService().getAllsample();

		return ResponseEntity.ok(pages);
	}

	@GetMapping(value = "/getallsampledto")
	public ResponseEntity<List<SampleDto>> getAllSampleDTO() {
		List<SampleDto> pages = getService().getSampleDto();
		return ResponseEntity.ok(pages);
	}

	@GetMapping(value = "/pagesample")
	public ResponseEntity<Page<BaseDto>> findallpagedto(@RequestParam(defaultValue = "", value = "nome") String nome,
			Pageable page) {
		Page<BaseDto> pages = getService().findallpagedto(nome, page);
		return ResponseEntity.ok(pages);
	}

	@GetMapping(value = "/page")
	public ResponseEntity<Page<T>> findallpage(@RequestParam(defaultValue = "", value = "nome") String nome,
			Pageable page) {

		Page<T> pages = getService().findallpage(nome, page);

		return ResponseEntity.ok(pages);
	}

	@GetMapping(value = "/fingbyid")
	public ResponseEntity<T> fingbyid(@RequestParam(defaultValue = "", value = "fingbyid") Long fingbyid) {
		return ResponseEntity.ok(getService().fingbyid(fingbyid));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<T> fingid(@PathVariable Long id) {
		return ResponseEntity.ok(getService().fingbyid(id));
	}

	public ResponseEntity<List<T>> fingbynome(
			@RequestParam(defaultValue = "", value = "fingbynome") String fingbynome) {

		return ResponseEntity.ok(getService().fingbynome(fingbynome));

	}

	@PutMapping(value = "/newobj")
	public ResponseEntity<?> newobj(@RequestBody T obj) {
		try {
			obj = getService().newobj(obj);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.ok(obj.getId());
	}

	@PostMapping(value = "/saveobj/{id}")
	public ResponseEntity<Long> saveobj(@RequestBody T obj, @PathVariable Long id) {
		obj = getService().saveobj(id, obj);
		return ResponseEntity.ok(obj.getId());
	}

	@PostMapping(value = "/{id}/uploadfile")
	public ResponseEntity<String> uplaodImage(@RequestBody MultipartFile file, @PathVariable Long id) {
		// TODO Auto- return ;
		try {
			String s = null;
			s = getService().uploadFile(file, id);

			return ResponseEntity.ok(s);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping(value = "/findbynome")
	public ResponseEntity<?> findbynome(@RequestParam(defaultValue = "", value = "nome") String nome) {
		try {
			return ResponseEntity.ok(getService().findbynome(nome));
		} catch (Exception e) {
			return ResponseEntity.ok(null);// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não
											// Encontrado");

		}
	}

	@GetMapping(value = "/listacategoriaproduto")
	public ResponseEntity<List<String>> listaCategoriaProduto() {
		return ResponseEntity.ok(getService().listaCategoriaProduto());
	}

	@GetMapping(value = "/listaunidade")
	public ResponseEntity<List<String>> listaUnidade() {
		return ResponseEntity.ok(getService().listaUnidade());
	}

	@GetMapping(value = "/tipopatrimonio")
	public ResponseEntity<List<String>> tipopatrimonio() {
		return ResponseEntity.ok(getService().tipopatriomonio());
	}

	/*
	 * uploadfile(event, controller: string,id): string {
	 * 
	 * const foto = event.target.files[0]; const formData = new FormData();
	 * formData.set('file', foto);
	 * this.hhtp.post(`${API_CONFIG.baseUrl}/${controller}/uploadfile?id=${id}`,
	 * formData, { observe: 'response', responseType: 'text' }) .toPromise() .then(
	 * (rest)=>{ this.spinner.hide(); return rest.body;; } )
	 * 
	 * return ""; }
	 */

}
