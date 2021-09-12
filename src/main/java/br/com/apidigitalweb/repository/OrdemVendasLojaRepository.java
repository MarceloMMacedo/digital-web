package br.com.apidigitalweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.ordemvendaloja.OrdemVendaLoja;
@Repository
public interface OrdemVendasLojaRepository extends JpaRepository<OrdemVendaLoja, Long> {

}
