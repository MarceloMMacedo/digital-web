package br.com.apidigitalweb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.domin.ordemvendaloja.FaturaVendaLoja;
import br.com.apidigitalweb.domin.pessoa.Cliente;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;

@Repository
public interface FaturaVendasRepository extends JpaRepository<FaturaVendaLoja, Long> {

	List<FaturaVendaLoja> findAllByCentroCustoAndStatus(CentroCusto centroCusto, String status);

	/*
	 * @Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaVendaLoja e where e.centroCusto=?1 and e.status=?2"
	 * ) double totalAbertoByCentroCusto(CentroCusto centroCusto, String status);
	 */
	List<FaturaVendaLoja> findAllByClienteAndStatus(Cliente cliente, String status);

	List<FaturaVendaLoja> findAllByBancoAndStatus(Banco cliente, String status);

	List<FaturaVendaLoja> findByStatus(String status);

	//List<FaturaVendaLoja> findByOrdemVendaAndStatus(ItensMaterialInServiceWeb ordemVenda, String status);

	@Query("SELECT new br.com.apidigitalweb.dto.financeiro.FaturasDto(f) from FaturaVendaLoja f where f.cliente=?1 and f.status=?2 order by f.dataVencimento")
	List<FaturasDto> findAllClienteAndStatus(Cliente cliente, String status);

	/*
	 * @Query("SELECT new br.com.apidigitalweb.dto.financeiro.FaturasDto(f) from FaturaVendaLoja f "
	 * +
	 * "where f.cliente=?1 and f.status=?2 and  f.dataVencimento BETWEEN =?3 AND =?4  order by f.dataVencimento"
	 * ) List<FaturasDto> findAllClienteAndStatusBetoweend(Cliente cliente, String
	 * status,Date inicio,Date fim);
	 */
	List<FaturaVendaLoja> findAllByStatusAndDataVencimentoBetweenOrderByDataVencimento(String status, Date inicio,
			Date fim);

	List<FaturaVendaLoja> findAllByClienteAndStatusAndDataVencimentoBetweenOrderByDataVencimento(Cliente cliente, String status, Date inicio,
			Date fim);

	@Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaVendaLoja e where e.status=?1")
	double totalAll(String status);

	@Query("SELECT new br.com.apidigitalweb.dto.financeiro.FaturasDto(f) from FaturaVendaLoja f where f.banco=?1 and f.status=?2")
	List<FaturasDto> allBanco(Banco banco, String status);

	@Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaVendaLoja e where e.cliente=?1 and e.status=?2")
	double totalAbertoByCliente(Cliente cliente, String status);

	/*@Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaVendaLoja e where e.ordemVenda=?1 and e.status=?2")
	double totalAbertoByOrdemVenda(ItensMaterialInServiceWeb ordemVenda, String status);
*/
	@Query("SELECT SUM( e.valor - e.desconto + e.jurus + e.multa) from FaturaVendaLoja e where e.banco=?1 and e.status=?2")
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
	double totalPeriodo(int ano, int status);

	@Query(value = "select  COALESCE( sum(valor+jurus+multa-desconto)  ,0) as total from fatura_venda  "
			+ "where date_part('year',data_vencimento)<?1  and status=?2", nativeQuery = true)
	double totalPeriodoAnterio(int ano, int status);

	@Query(value = "select  COALESCE( sum(valor+jurus+multa-desconto)  ,0) as total from fatura_venda  "
			+ "where date_part('year',data_vencimento)>?1  and status=?2", nativeQuery = true)
	double totalPeriodoPosterior(int ano, int status);

	@Query(value = "select  COALESCE( sum(valor+jurus+multa-desconto)  ,0) as total,\n"
			+ "date_part('month',data_vencimento) as month\n"
			+ "from fatura_venda   where date_part('year',data_vencimento)=?1 and   status=?2 \n"
			+ "group by date_part('month',data_vencimento)\n"
			+ "order by date_part('month',data_vencimento)", nativeQuery = true)
	List<Object[]> itemMonthReportDtos(int ano, int status);

}