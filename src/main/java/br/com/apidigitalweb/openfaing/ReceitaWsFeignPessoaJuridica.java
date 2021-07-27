package br.com.apidigitalweb.openfaing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.pessoa.Cliente;
import br.com.apidigitalweb.domin.pessoa.Fornecedor;
import br.com.apidigitalweb.dto.pessoa.BasePessoaJuridicaDTO;

@Component
@FeignClient(url = "https://www.receitaws.com.br/", name = "receitaws")
public interface ReceitaWsFeignPessoaJuridica {

	@GetMapping("/v1/cnpj/{cnpj}")
	BasePessoaJuridicaDTO buscaPorcnpj(@PathVariable("cnpj") String cep);

}
