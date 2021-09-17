package br.com.apidigitalweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.ordemservico.ItensMaterialInServiceLoja;
@Repository
public interface ItensMaterialInServiceLojaRepository extends JpaRepository<ItensMaterialInServiceLoja, Long> {

}
