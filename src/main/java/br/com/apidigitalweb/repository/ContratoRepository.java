package br.com.apidigitalweb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.contratos.Contrato;
import br.com.apidigitalweb.domin.contratos.ItensContratoPatrimonio;
import br.com.apidigitalweb.domin.contratos.Patrimonio;
import br.com.apidigitalweb.domin.estoque.Produto;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long>  {
	Page<Contrato> findByNomeContainingIgnoreCaseOrNomeIsNull(String nome,Pageable page);
	
@Query(value="Select    i.ID , i.FRANQUIA, i.IS_FRANQUADO, i.PATRIMONIO_ID , i.VALOR_FRANQUIA  , i.VALOR_PRE_DETERMINADO , i.VALOR_UNITARIO_FRANQUIA from CONTRATO_ITENSPATRIMONIO  i", nativeQuery = true)
	List<Object[]> findAllItenspatrimonio();
	 
}
