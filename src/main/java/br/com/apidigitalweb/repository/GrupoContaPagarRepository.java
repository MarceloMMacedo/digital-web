package br.com.apidigitalweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.financeiro.contaspagar.GrupoContaPagar;

@Repository
public interface GrupoContaPagarRepository extends JpaRepository<GrupoContaPagar, Long> {

}
