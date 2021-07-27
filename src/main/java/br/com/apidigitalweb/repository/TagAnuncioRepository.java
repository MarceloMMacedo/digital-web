package br.com.apidigitalweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.estoque.TagAnuncio;

@Repository
public interface TagAnuncioRepository  extends JpaRepository<TagAnuncio, Long>  {
}
