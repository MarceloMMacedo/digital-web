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
import br.com.apidigitalweb.domin.ordem.CentroCustoFatura;
import br.com.apidigitalweb.domin.ordemservico.FaturaOrdemServico;
import br.com.apidigitalweb.domin.ordemservico.OrdemServico;
import br.com.apidigitalweb.domin.ordemvenda.FaturaVenda;
import br.com.apidigitalweb.domin.ordemvenda.ItensOrdemVenda;
import br.com.apidigitalweb.domin.ordemvenda.OrdemVenda;
import br.com.apidigitalweb.repository.FaturaOrdemServicoRepository;

@Service
public class FaturaOrdemServicoService extends BaseServic<FaturaOrdemServico> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	FaturaOrdemServicoRepository repo;

	@Autowired
	AnuncioLojaService anuncioLojaService;

	@Autowired
	AnuncioWebService anuncioWebService;

	@Autowired
	AnuncioServicoService anuncioServicoService;

	@Override
	public Page<FaturaOrdemServico> findallpage(String find, Pageable page) {
		Page<FaturaOrdemServico> findall = super.findallpage(find, page);
		findall.forEach(x -> {
			{
				x.getCentroCustoFaturas().addAll(getAgregado(x.getOrdemServico(), x.getTotal()));
			}
		});
		return findall;
	}

	@Override
	public FaturaOrdemServico fingbyid(Long id) {
		FaturaOrdemServico x = super.fingbyid(id);
		try {
			x.getCentroCustoFaturas().addAll(getAgregado(x.getOrdemServico(), x.getTotal()));
		} catch (Exception e) {
		}
		return x;
	}

	public List<FaturaOrdemServico> getAllCentroCusto(CentroCusto centroCusto, String status) {
		List<FaturaOrdemServico> getAllCentroCusto = new ArrayList<>();
		getAllCentroCusto = repo.findAllByCentroCustoAndStatus(centroCusto, status);
		return getAll(getAllCentroCusto);
	}
	public List<FaturaOrdemServico> getAllBanco(Banco banco, String status) {
		List<FaturaOrdemServico> getAllBanco = new ArrayList<>();
		getAllBanco = repo.findAllByBancoAndStatus(banco, status);
		return getAll(getAllBanco);
	}

	public List<FaturaOrdemServico> getAll(List<FaturaOrdemServico> lista) {
		List<FaturaOrdemServico> getAll = new ArrayList<>();
		getAll = lista;
		getAll.stream().forEach(x -> { 
			{
				x.getCentroCustoFaturas().addAll(getAgregado(x.getOrdemServico(), x.getTotal()));
			}
		});
		return super.getAll();
	}

	public List<CentroCustoFatura> getAgregado(OrdemServico ordemVenda, double valor) {
		List<CentroCustoFatura> getAgregado = new ArrayList<CentroCustoFatura>();
		
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

		}
		return getAgregado;
	}
}
