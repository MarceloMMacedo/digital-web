package br.com.apidigitalweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.cotacao.Cotacao;
@Repository
public interface CotacaoRepository extends JpaRepository<Cotacao, Long> {

}
