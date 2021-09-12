package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.CentroCusto;
import br.com.apidigitalweb.domin.financeiro.MovBanco;
import br.com.apidigitalweb.domin.financeiro.MovCentroCusto;
import br.com.apidigitalweb.domin.ordem.CentroCustoFatura;
import br.com.apidigitalweb.domin.ordemservico.FaturaOrdemServico;
import br.com.apidigitalweb.domin.ordemservico.OrdemServico;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;
import br.com.apidigitalweb.enuns.StatusActiv;
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
	@Autowired
	private BancoService bancoService;

	@Autowired
	private MovBancoService movBancoService;

	@Autowired
	private MovCentroCustoService movCentroCustoService;

	@Autowired
	private CentroCustoService centroCustoService;

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
		
	/*	for (ItensMaterialInVendaLoja it : ordemVenda.getItensOrdemVenda()) {
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
		*/
		return getAgregado;
	}
	
	
	public void quitar(FaturasDto obj) {
		FaturaOrdemServico fatura= new FaturaOrdemServico();
		BeanUtils.copyProperties(fatura, obj, getNullPropertyNames(fatura)); // Perform update operation
		saveobj(fatura.getId(), fatura); 
		
		for (CentroCustoFatura centroCustoFatura : fatura.getCentroCustoFaturas()) { 
			centroCustoFatura.getCentroCusto().setSaldo(centroCustoFatura.getCentroCusto().getSaldo() + centroCustoFatura.getValorFinal());
			centroCustoService.saveobj(centroCustoFatura.getCentroCusto().getId(), centroCustoFatura.getCentroCusto());
			MovCentroCusto c = new MovCentroCusto();
			c.setCentroCusto(centroCustoFatura.getCentroCusto());
			c.setDataMovimento(new Date());
			c.setValor(centroCustoFatura.getValorFinal());
			c.setDescricao("Recebimento de Fatura de contrato nro.:" + "" + fatura.getNumeroparcela() + " id:" + "" + fatura.getId());
			c = movCentroCustoService.newobj(c);

		}
		

		Banco banco = bancoService.fingbyid(fatura.getBanco().getId());
		banco.setSaldo(banco.getSaldo() + fatura.getTotal());
		bancoService.saveobj(banco.getId(), banco);

		MovBanco m = new MovBanco();
		m.setBanco(banco);
		m.setDataMovimento(new Date());
		m.setValor(fatura.getTotal());
		m.setDescricao(
				"Recebimento de Fatura de contrato nro.:" + "" + fatura.getNumeroparcela() + " id:" + "" + fatura.getId());
		m.setFatura(
				fatura.getNome() + "-Fatura Contas Pagar de parcela:" + "" + fatura.getNumeroparcela() + " id:" + "" + fatura.getId());
		m = movBancoService.newobj(m);

		 
		fatura.setStatus(StatusActiv.QUIT.getDescricao());

		fatura = repo.save(fatura);
		/* contasPagar = contasPagarRepository.findById(f.getContasPagar().getId()).get();
		if (contasPagar.getFaturasAberta().size() == 0) {
			contasPagar.setStatus(StatusActiv.QUIT.getDescricao());
			contasPagarRepository.save(contasPagar);

		}*/
	}
}
