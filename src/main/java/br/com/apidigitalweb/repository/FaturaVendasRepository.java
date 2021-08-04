package br.com.apidigitalweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.domin.ordemvenda.FaturaVenda;
import br.com.apidigitalweb.domin.ordemvenda.OrdemVenda;
import br.com.apidigitalweb.domin.pessoa.Cliente;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;

@Repository
public interface FaturaVendasRepository extends JpaRepository<FaturaVenda, Long> {

	List<FaturaVenda> findAllByCentroCustoAndStatus(CentroCusto centroCusto, String status);

	/*
	 * @Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaVenda e where e.centroCusto=?1 and e.status=?2"
	 * ) double totalAbertoByCentroCusto(CentroCusto centroCusto, String status);
	 */
	List<FaturaVenda> findAllByClienteAndStatus(Cliente cliente, String status);

	List<FaturaVenda> findAllByBancoAndStatus(Banco cliente, String status);

	List<FaturaVenda> findByStatus(String status);

	List<FaturaVenda> findByOrdemVendaAndStatus(OrdemVenda ordemVenda, String status);

	@Query("SELECT new br.com.apidigitalweb.dto.financeiro.FaturasDto(f) from FaturaVenda f where f.banco=?1 and f.status=?2")
	List<FaturasDto> allBanco(Banco banco, String status);

	@Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaVenda e where e.cliente=?1 and e.status=?2")
	double totalAbertoByCliente(Cliente cliente, String status);

	@Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaVenda e where e.ordemVenda=?1 and e.status=?2")
	double totalAbertoByOrdemVenda(OrdemVenda ordemVenda, String status);

	@Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaVenda e where e.banco=?1 and e.status=?2")
	double totalAbertoByBanco(Banco banco, String status);

	@Query(value = "SELECT sum(c.valor) FROM fatura_venda_centro_custo_faturas c, fatura_venda f\n"
			+ "where f.id=c.id and f.status=?1 and f.id=?2", nativeQuery = true)
	double totalAbertoByStatus(int status, long id);

	@Query(value = "select  COALESCE( sum(valor+jurus+multa-desconto)  ,0) as total from fatura_venda  "
			+ "where date_part('month',data_vencimento)=?1 and date_part('year',data_vencimento)=?2 "
			+ " and status=?3", nativeQuery = true)
	double totalMesPeriodo(int mes, int ano, int status);
	
	@Query(value = "select  COALESCE( sum(valor+jurus+multa-desconto)  ,0) as total from fatura_venda  "
			+ "where date_part('year',data_vencimento)=?1  and status=?2", nativeQuery = true)
	double totalPeriodo(  int ano, int status);
	@Query(value = "select  COALESCE( sum(valor+jurus+multa-desconto)  ,0) as total from fatura_venda  "
			+ "where date_part('year',data_vencimento)<?1  and status=?2", nativeQuery = true)
	double totalPeriodoAnterio(int ano, int status);

	@Query(value = "select  COALESCE( sum(valor+jurus+multa-desconto)  ,0) as total from fatura_venda  "
			+ "where date_part('year',data_vencimento)>?1  and status=?2", nativeQuery = true)
	double totalPeriodoPosterior(int ano, int status);

}