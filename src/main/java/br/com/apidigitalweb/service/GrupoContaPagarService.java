package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.config.exception.AuthorizationException;
import br.com.apidigitalweb.config.security.UserSS;
import br.com.apidigitalweb.config.services.UserService;
import br.com.apidigitalweb.domin.contratos.FaturaContrato;
import br.com.apidigitalweb.domin.financeiro.BaseFatura;
import br.com.apidigitalweb.domin.financeiro.contaspagar.FaturaContasPagar;
import br.com.apidigitalweb.domin.financeiro.contaspagar.GrupoContaPagar;
import br.com.apidigitalweb.domin.financeiro.contaspagar.HistoricoContaPagar;
import br.com.apidigitalweb.domin.ordem.CentroCustoFatura;
import br.com.apidigitalweb.domin.ordemservico.FaturaOrdemServico;
import br.com.apidigitalweb.domin.ordemvendaloja.FaturaVendaLoja;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.enuns.TipoFaturaEnum;
import br.com.apidigitalweb.repository.GrupoContaPagarRepository;
import br.com.apidigitalweb.repository.HistoricoContaPagarRepository;

@Service
public class GrupoContaPagarService extends BaseServic<GrupoContaPagar> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private GrupoContaPagarRepository repo;

	@Autowired
	private FaturaContasPagarService faturaContasPagarService;

	@Autowired
	private HistoricoContaPagarRepository historicoContaPagarRepository;
	
	public void deletehintorico(long id) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		historicoContaPagarRepository.deleteById(id);
	}
	
	/*@Override
	public GrupoContaPagar saveobj(Long id, GrupoContaPagar obj) {
		try {
			for (HistoricoContaPagar h : obj.getHistoricos()) {
				h.setGrupocontaspagar(obj);
			}
			historicoContaPagarRepository.saveAll(obj.getHistoricos());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return super.saveobj(id, obj);
	}
*/
	@Override
	public void preSaveObj(GrupoContaPagar obj) {
		try {
			for (HistoricoContaPagar h : obj.getHistoricos()) {
				h.setGrupocontaspagar(obj);
			}
			historicoContaPagarRepository.saveAll(obj.getHistoricos());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Override
	public GrupoContaPagar fingbyid(Long id) {
		GrupoContaPagar g = super.fingbyid(id);
		{
			g.getHistoricos().stream().forEach(x -> {
				// Faturas pagar
				double valors = 0;
				List<FaturaContasPagar> listfaturaContasPagar = faturaContasPagarService.getAllHistoricoContaPagar(x,
						StatusActiv.ABERTO.getDescricao());
				x.getFaturasAberta().addAll(faturaCP(listfaturaContasPagar, TipoFaturaEnum.ContasPagar.getDescricao()));

				x.setSaidasFuturo(valors);
			});
		}

		return g;
	}

	private List<FaturasDto> faturasDtosVendas(List<FaturaVendaLoja> faturaVendas, String origem) {
		List<FaturasDto> faturasDto = new ArrayList<>();
		for (BaseFatura b : faturaVendas) {
			faturasDto.add(new FaturasDto(b, origem));
		}

		return faturasDto;
	}

	private List<FaturasDto> faturaCP(List<FaturaContasPagar> faturas, String origem) {
		List<FaturasDto> faturasDtos = new ArrayList<>();
		for (BaseFatura b : faturas) {
			FaturasDto f = new FaturasDto(b, origem);
			faturasDtos.add(f);
		}

		return faturasDtos;
	}
}
