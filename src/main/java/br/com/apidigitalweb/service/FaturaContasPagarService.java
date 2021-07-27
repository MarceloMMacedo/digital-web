package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.contratos.AgregadoGrupoContrato;
import br.com.apidigitalweb.domin.contratos.FaturaContrato;
import br.com.apidigitalweb.domin.contratos.GrupoFinanceiroContrato;
import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.domin.financeiro.contaspagar.FaturaContasPagar;
import br.com.apidigitalweb.domin.financeiro.contaspagar.HistoricoContaPagar;
import br.com.apidigitalweb.domin.ordem.CentroCustoFatura;
import br.com.apidigitalweb.domin.ordemvenda.FaturaVenda;
import br.com.apidigitalweb.repository.FaturaContasPagarRepository;

@Service
public class FaturaContasPagarService extends BaseServic<FaturaContasPagar> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private FaturaContasPagarRepository repo;
	
	@Override
	public Page<FaturaContasPagar> findallpage(String find, Pageable page) {
		Page<FaturaContasPagar> findall = super.findallpage(find, page);
		findall.forEach(x -> {
			try{
				x.getCentroCustoFaturas().addAll(getAgregado(x.getHistorico(), x.getTotal()));

			}catch (Exception e) {
				// TODO: handle exception
			}
		});
		return findall;
	}

	@Override
	public FaturaContasPagar fingbyid(Long id) {
		FaturaContasPagar x = fingbyid(id);
		try{
			x.getCentroCustoFaturas().addAll(getAgregado(x.getHistorico(), x.getTotal()));

		}catch (Exception e) {
			// TODO: handle exception
		}

		return super.fingbyid(id);
	}

	public List<FaturaContasPagar> getAllCentroCusto(CentroCusto centroCusto, String status) {
		List<FaturaContasPagar> getAllCentroCusto = new ArrayList<>();
		getAllCentroCusto = repo.findAllByCentroCustoAndStatus(centroCusto, status);
		return getAll(getAllCentroCusto);
	}

	public List<FaturaContasPagar> getAllBanco(Banco banco, String status) {
		List<FaturaContasPagar> getAllBanco = new ArrayList<>();
		getAllBanco = repo.findAllByBancoAndStatus(banco, status);
		return getAll(getAllBanco);
	}
	public List<FaturaContasPagar> getAllHistoricoContaPagar(HistoricoContaPagar historicoContaPagar, String status) {
		List<FaturaContasPagar> getAllCentroCusto = new ArrayList<>();
		getAllCentroCusto = repo.findAllByHistoricoAndStatus(historicoContaPagar, status);
		return getAll(getAllCentroCusto);
	}
	public List<FaturaContasPagar> getAll(List<FaturaContasPagar> lista) {
		List<FaturaContasPagar> getAll = new ArrayList<>();
		getAll = lista;
		getAll.stream().forEach(x -> {
			try{
				x.getCentroCustoFaturas().addAll(getAgregado(x.getHistorico(), x.getTotal()));

			}catch (Exception e) {
				// TODO: handle exception
			}
		});
		return super.getAll();
	}

	public List<CentroCustoFatura> getAgregado(HistoricoContaPagar gr, double valor) {
		List<CentroCustoFatura> getAgregado = new ArrayList<CentroCustoFatura>();

		//for (AgregadoGrupoContrato financeiro : gr.getAgregados()) 
		{
			CentroCustoFatura custoFatura = new CentroCustoFatura(gr.getCentroCusto(), valor);
			getAgregado.add(custoFatura);
		}
		return getAgregado;
	}
}