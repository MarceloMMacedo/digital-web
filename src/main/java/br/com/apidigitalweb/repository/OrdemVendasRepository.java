package br.com.apidigitalweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.ordemvenda.OrdemVenda;
@Repository
public interface OrdemVendasRepository extends JpaRepository<OrdemVenda, Long> {

}
