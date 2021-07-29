package br.com.apidigitalweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.contratos.ItensContratoPatrimonio;
@Repository
public interface ItensContratoPatrimonioRepository extends JpaRepository<ItensContratoPatrimonio, Long> {

}
