package br.com.apidigitalweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.ordem.OrdemAtividade;

@Repository
public interface OrdemAtividadeRepository extends JpaRepository<OrdemAtividade, Long> {

}
