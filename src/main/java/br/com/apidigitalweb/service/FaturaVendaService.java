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
import br.com.apidigitalweb.domin.ordemvendaloja.FaturaVendaLoja;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.repository.FaturaVendasRepository;

@Service
public class FaturaVendaService extends BaseServic<FaturaVendaLoja> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private FaturaVendasRepository repo;

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
	public Page<FaturaVendaLoja> findallpage(String find, Pageable page) {
		Page<FaturaVendaLoja> findall = super.findallpage(find, page);
		findall.forEach(x -> {
			{
	//			x.getCentroCustoFaturas().addAll(getAgregado(x.getOrdemVenda(), x.getTotal()));

			}
		});
		return findall;
	}

	public List<FaturaVendaLoja> getAllBanco(Banco banco, String status) {
		List<FaturaVendaLoja> getAllBanco = new ArrayList<>();
		getAllBanco = repo.findAllByBancoAndStatus(banco, status);
		return getAll(getAllBanco);
	}

	public List<FaturaVendaLoja> getAllCentroCusto(CentroCusto centroCusto, String status) {
		List<FaturaVendaLoja> getAllCentroCusto = new ArrayList<>();
		getAllCentroCusto = repo.findAllByCentroCustoAndStatus(centroCusto, status);
		return getAll(getAllCentroCusto);
	}
	
	public List<FaturaVendaLoja> getAll(List<FaturaVendaLoja> lista) {
		List<FaturaVendaLoja> getAll = new ArrayList<>();
		getAll = lista;
		getAll.stream().forEach(x -> {
			{
			//	x.getCentroCustoFaturas().addAll(getAgregado(x.getOrdemVenda(), x.getTotal()));

			}
		});
		return super.getAll();
	}

	 
	
	public void quitar(FaturasDto obj) {
		FaturaVendaLoja fatura= new FaturaVendaLoja();
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
