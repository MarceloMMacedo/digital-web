package br.com.apidigitalweb.domin;

import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.dto.pessoa.BasePessoaJuridicaDTO;

public interface BaseEntityPessoa extends BaseEntity {
	String getEmail();

	void setEmail(String email);

	String getCnpj();
	void setCnpj(String cnpj);

	BaseEntity loadCnpjThis(BasePessoaJuridicaDTO p, Endereco e);

}
