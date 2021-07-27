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
import br.com.apidigitalweb.domin.estoque.AnuncioLoja;
import br.com.apidigitalweb.domin.estoque.AnuncioServico;
import br.com.apidigitalweb.domin.estoque.AnuncioWeb;
import br.com.apidigitalweb.domin.financeiro.AgregadoGrupoFinanceiro;
import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.domin.ordem.CentroCustoFatura;
import br.com.apidigitalweb.domin.ordemvenda.FaturaVenda;
import br.com.apidigitalweb.domin.ordemvenda.ItensOrdemVenda;
import br.com.apidigitalweb.domin.ordemvenda.OrdemVenda;
import br.com.apidigitalweb.repository.FaturaContratoRepository;
import br.com.apidigitalweb.repository.FaturaVendasRepository;

@Service
public class FaturaContratoService extends BaseServic<FaturaContrato> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private FaturaContratoRepository repo;

	 
	@Override
	public Page<FaturaContrato> findallpage(String find, Pageable page) {
		Page<FaturaContrato> findall = super.findallpage(find, page);
		findall.forEach(x -> {
			try{
				x.getCentroCustoFaturas().addAll(getAgregado(x.getGrupoFinanceiro(), x.getTotal()));

			}catch (Exception e) {
				// TODO: handle exception
			}
		});
		return findall;
	}

	@Override
	public FaturaContrato fingbyid(Long id) {
		FaturaContrato x = fingbyid(id);
		try{
			x.getCentroCustoFaturas().addAll(getAgregado(x.getGrupoFinanceiro(), x.getTotal()));

		}catch (Exception e) {
			// TODO: handle exception
		}

		return super.fingbyid(id);
	}

	public List<FaturaContrato> getAllCentroCusto(CentroCusto centroCusto, String status) {
		List<FaturaContrato> getAllCentroCusto = new ArrayList<>();
		getAllCentroCusto = repo.findAllByCentroCustoAndStatus(centroCusto, status);
		return getAll(getAllCentroCusto);
	}
	public List<FaturaContrato> getAllBanco(Banco banco, String status) {
		List<FaturaContrato> getAllBanco = new ArrayList<>();
		getAllBanco = repo.findAllByBancoAndStatus(banco, status);
		return getAll(getAllBanco);
	}
	public List<FaturaContrato> getAll(List<FaturaContrato> lista) {
		List<FaturaContrato> getAll = new ArrayList<>();
		getAll = lista;
		getAll.stream().forEach(x -> {
			try{
				x.getCentroCustoFaturas().addAll(getAgregado(x.getGrupoFinanceiro(), x.getTotal()));

			}catch (Exception e) {
				// TODO: handle exception
			}
		});
		return super.getAll();
	}

	public List<CentroCustoFatura> getAgregado(GrupoFinanceiroContrato gr, double valor) {
		List<CentroCustoFatura> getAgregado = new ArrayList<CentroCustoFatura>();

		for (AgregadoGrupoContrato financeiro : gr.getAgregados()) {
			CentroCustoFatura custoFatura = new CentroCustoFatura(financeiro.getCentroCusto(), valor);
			getAgregado.add(custoFatura);
		}
		return getAgregado;
	}
}
