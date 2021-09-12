package br.com.apidigitalweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.ordemvendaweb.OrdemVendaWeb;
@Repository
public interface OrdemVendasWebRepository extends JpaRepository<OrdemVendaWeb, Long> {

}
