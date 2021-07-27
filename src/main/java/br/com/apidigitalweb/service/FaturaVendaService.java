package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.estoque.AnuncioLoja;
import br.com.apidigitalweb.domin.estoque.AnuncioServico;
import br.com.apidigitalweb.domin.estoque.AnuncioWeb;
import br.com.apidigitalweb.domin.financeiro.AgregadoGrupoFinanceiro;
import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.domin.financeiro.contaspagar.HistoricoContaPagar;
import br.com.apidigitalweb.domin.ordem.CentroCustoFatura;
import br.com.apidigitalweb.domin.ordemservico.FaturaOrdemServico;
import br.com.apidigitalweb.domin.ordemvenda.FaturaVenda;
import br.com.apidigitalweb.domin.ordemvenda.ItensOrdemVenda;
import br.com.apidigitalweb.domin.ordemvenda.OrdemVenda;
import br.com.apidigitalweb.repository.FaturaVendasRepository;

@Service
public class FaturaVendaService extends BaseServic<FaturaVenda> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private FaturaVendasRepository repo;

	@Autowired
	AnuncioLojaService anuncioLojaService;

	@Autowired
	AnuncioWebService anuncioWebService;

	@Autowired
	AnuncioServicoService anuncioServicoService;

	@Override
	public Page<FaturaVenda> findallpage(String find, Pageable page) {
		Page<FaturaVenda> findall = super.findallpage(find, page);
		findall.forEach(x -> {
			{
				x.getCentroCustoFaturas().addAll(getAgregado(x.getOrdemVenda(), x.getTotal()));

			}
		});
		return findall;
	}

	public List<FaturaVenda> getAllBanco(Banco banco, String status) {
		List<FaturaVenda> getAllBanco = new ArrayList<>();
		getAllBanco = repo.findAllByBancoAndStatus(banco, status);
		return getAll(getAllBanco);
	}

	public List<FaturaVenda> getAllCentroCusto(CentroCusto centroCusto, String status) {
		List<FaturaVenda> getAllCentroCusto = new ArrayList<>();
		getAllCentroCusto = repo.findAllByCentroCustoAndStatus(centroCusto, status);
		return getAll(getAllCentroCusto);
	}
	
	public List<FaturaVenda> getAll(List<FaturaVenda> lista) {
		List<FaturaVenda> getAll = new ArrayList<>();
		getAll = lista;
		getAll.stream().forEach(x -> {
			{
				x.getCentroCustoFaturas().addAll(getAgregado(x.getOrdemVenda(), x.getTotal()));

			}
		});
		return super.getAll();
	}

	public List<CentroCustoFatura> getAgregado(OrdemVenda ordemVenda, double valor) {
		List<CentroCustoFatura> getAgregado = new ArrayList<CentroCustoFatura>();
		try {
		if (ordemVenda.getItensOrdemVenda() != null)
			
			for (ItensOrdemVenda it : ordemVenda.getItensOrdemVenda()) {
				switch (it.getOrigemProduto()) {
				case "AnuncioLoja": {
					AnuncioLoja a = anuncioLojaService.fingbyid(it.getIdProduto());
					for (AgregadoGrupoFinanceiro financeiro : a.getGrupopreco().getAgregadoGrupo()) {
						CentroCustoFatura custoFatura = new CentroCustoFatura(financeiro.getCentroCusto(),
								valor * it.getQuantidade());
						getAgregado.add(custoFatura);
					}

				}
					break;

				case "AnuncioWeb": {
					AnuncioWeb a = anuncioWebService.fingbyid(it.getIdProduto());
					for (AgregadoGrupoFinanceiro financeiro : a.getGrupopreco().getAgregadoGrupo()) {
						CentroCustoFatura custoFatura = new CentroCustoFatura(financeiro.getCentroCusto(),
								valor * it.getQuantidade());
						getAgregado.add(custoFatura);
					}

				}
					break;

				case "Mão de Obra": {
					AnuncioServico a = anuncioServicoService.fingbyid(it.getIdProduto());
					for (AgregadoGrupoFinanceiro financeiro : a.getGrupopreco().getAgregadoGrupo()) {
						CentroCustoFatura custoFatura = new CentroCustoFatura(financeiro.getCentroCusto(),
								valor * it.getQuantidade());
						getAgregado.add(custoFatura);
					}

				}
					break;

				default:
					break;
				}

			}}
	catch (Exception e) {
			// TODO: handle exception
		}
		return getAgregado;
	}
}
