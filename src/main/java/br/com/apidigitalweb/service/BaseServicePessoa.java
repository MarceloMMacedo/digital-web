package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import br.com.apidigitalweb.config.exception.AuthorizationException;
import br.com.apidigitalweb.config.security.UserSS;
import br.com.apidigitalweb.config.services.UserService;
import br.com.apidigitalweb.domin.BaseEntityPessoa;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.dto.pessoa.BasePessoaJuridicaDTO;
import br.com.apidigitalweb.enuns.Perfil;
import br.com.apidigitalweb.openfaing.ReceitaWsFeignPessoaJuridica;
import br.com.apidigitalweb.openfaing.ViaCEPClient;

public class BaseServicePessoa<T extends BaseEntityPessoa> extends BaseServic<T> implements Serializable {
 
	private static final long serialVersionUID = 1L;

	@Autowired
	ReceitaWsFeignPessoaJuridica feignPessoaJuridica;

	@Autowired
	private ViaCEPClient viaCEPClient;

	public T receitaws(Long id, String cnpj) {
		cnpj = cnpj.replaceAll("\\p{Punct}", "");
		T f = null;
		BasePessoaJuridicaDTO t = null;
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		try {

			t = feignPessoaJuridica.buscaPorcnpj(cnpj);
			Endereco e = new Endereco(t.getCep(), t.getLogradouro(), t.getComplemento(), t.getBairro(),
					t.getMunicipio(), t.getNumero(), t.getUf());

			if (id > 0) {
				f = getRepo().findById(id).get();
				f.loadCnpjThis(t, e);
			} else {
				f = getClasse().newInstance();
				f = (T) f.loadCnpjThis(t, e);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return f;
	}

	@Override
	public T newobj(T obj) {
		if (obj.getEmail() == null || obj.getEmail().equals(""))
			obj.setEmail(UUID.randomUUID().toString());
		ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();
		T f = null;
		try {
			f = getClasse().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

		Example<T> example = Example.of(f, matcher);
		List<T> p = repo.findAll(example);
		if (p.size() > 0) {
			throw new AuthorizationException("Já existe um cadastro para o nome:" + obj.getNome());
		}

		return super.newobj(obj);
	}

	public Endereco getEndereco(String cep) {
		cep = cep.replaceAll("\\p{Punct}", "");
		Endereco e = viaCEPClient.buscaEnderecoPor(cep);
		return e;

	}
	public List<String> listaRules() {
		List<String> listaUnidade = new ArrayList<>();
		for (Perfil string : Perfil.values()) {
			listaUnidade.add(string.getDescricao());
		}
		return listaUnidade;
	}
}
