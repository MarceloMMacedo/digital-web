package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.config.exception.AuthorizationException;
import br.com.apidigitalweb.config.security.UserSS;
import br.com.apidigitalweb.config.services.UserService;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.domin.pessoa.Fornecedor;
import br.com.apidigitalweb.dto.pessoa.BasePessoaJuridicaDTO;
import br.com.apidigitalweb.openfaing.ReceitaWsFeignPessoaJuridica;
import br.com.apidigitalweb.openfaing.ViaCEPClient;
import br.com.apidigitalweb.util.CopyObject;

@Service
public class FornecedorService extends BaseServicePessoa<Fornecedor> implements Serializable {
	private static final long serialVersionUID = 1L;

	/*@Autowired
	ReceitaWsFeignPessoaJuridica feignPessoaJuridica;

	@Autowired
	private ViaCEPClient viaCEPClient;

	public Fornecedor receitaws(Long id, String cnpj) {
		cnpj = cnpj.replaceAll("\\p{Punct}", "");
		Fornecedor f = null;
		BasePessoaJuridicaDTO t = null;
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		try {

			t = feignPessoaJuridica.buscaPorcnpj(cnpj);
			Endereco e = new Endereco(t.getCep(), t.getLogradouro(), t.getComplemento(), t.getBairro(),
					t.getMunicipio(), t.getNumero(), t.getUf());
			if (id == 0 || id == null) {
				f = new Fornecedor(t,e);
				 

			} else {
				f = (Fornecedor) CopyObject.complemenForm(f, t, Fornecedor.class);
				f.setEndereco(e);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return f;
	}

	@Override
	public Fornecedor newobj(Fornecedor obj) {
		if (obj.getEmail() == null || obj.getEmail().equals(""))
			obj.setEmail(UUID.randomUUID().toString());
		ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();
		Fornecedor f = new Fornecedor();
		f.setCnpj(obj.getCnpj());
		ExampleMatcher matcher = ExampleMatcher.matchingAll()
				.withMatcher("cnpj", match -> match.contains().ignoreCase())
				// ### This is (probably?) the bit that's wrong - none of these made any
				// difference
				// .withMatcher("categories.id", match -> match.contains())
				// .withMatcher("categories.name", match -> match.contains().ignoreCase())
				// .withMatcher("categories", match -> match.contains())
				// ###
				.withIgnoreNullValues() // ignore unset properties when finding
				.withIgnorePaths("id"); // ignore primitives as they default to 0

		Example<Fornecedor> example = Example.of(f, matcher);
		List<Fornecedor> p = repo.findAll(example);
		if (p.size() > 0) {
			throw new AuthorizationException("Já existe um cadastro para o nome:" + obj.getNome());
		}

		return super.newobj(obj);
	}
	*/
}
