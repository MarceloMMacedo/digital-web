package br.com.apidigitalweb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.estoque.AnuncioLoja;
import br.com.apidigitalweb.domin.estoque.Produto;

@Repository

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	Page<Produto> findByNomeContainingIgnoreCase(String nome,Pageable page);
	 
	
	@Query(value = "select e.id from  anuncio_loja_itens_produto e where e.produto_id= ?1 group by e.id" ,nativeQuery = true)
	List<Object> listProdutoAnuncioLoja(Long id );
	
	@Query(value = "select e.id from  anuncio_web_itens_produto  e where e.produto_id= ?1 group by e.id" ,nativeQuery = true)
	List<Object> listProdutoAnuncioWeb(Long id );
	
	@Query(value = "select e.id from  anuncio_contrato_itens_produto  e where e.produto_id= ?1 group by e.id" ,nativeQuery = true)
	List<Object> listProdutoAnuncioContrato(Long id );
}
