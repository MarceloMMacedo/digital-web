package br.com.apidigitalweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.domin.pessoa.Fornecedor;

@Repository
public interface CentroCustoRepository  extends JpaRepository<CentroCusto, Long> {
	Page<CentroCusto> findByNomeContainingIgnoreCaseOrNomeIsNull(String nome,Pageable page);
}
