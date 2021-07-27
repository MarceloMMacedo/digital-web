package br.com.apidigitalweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.estoque.Produto;
import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.domin.pessoa.Fornecedor;

@Repository
public interface BancoRepository  extends JpaRepository<Banco, Long> {
	Page<Banco> findByBancoContainingIgnoreCaseOrBancoIsNull(String banco,Pageable page);
}
