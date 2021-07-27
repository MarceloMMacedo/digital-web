package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.contratos.FaturaContrato;
import br.com.apidigitalweb.domin.contratos.GrupoFinanceiroContrato;
import br.com.apidigitalweb.domin.financeiro.Banco;
import br.com.apidigitalweb.domin.financeiro.BaseFatura;
import br.com.apidigitalweb.dto.financeiro.FaturasDto;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.enuns.TipoFaturaEnum;
import br.com.apidigitalweb.repository.FaturaContratoRepository;
import br.com.apidigitalweb.repository.GrupoFinanceiroContratoRepository;

@Service
public class GrupoFinanceiroContratoService extends BaseServic<GrupoFinanceiroContrato> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private GrupoFinanceiroContratoRepository repo;

	@Autowired
	private FaturaContratoRepository faturaContratoRepository;

	@Override
	public GrupoFinanceiroContrato newobj(GrupoFinanceiroContrato obj) {

		return super.newobj(obj);
	}

	@Override
	public GrupoFinanceiroContrato fingbyid(Long id) {
		GrupoFinanceiroContrato x = super.fingbyid(id);
		try {
			if (x.getBanco() == null)
				x.setBanco(new Banco());
		} catch (Exception e) {
			x.setBanco(new Banco());
	}
		{
			double valor = 0;
			List<FaturaContrato> listfaturaContasPagar = faturaContratoRepository.findAllByGrupoFinanceiroAndStatus(x,
					StatusActiv.ABERTO.getDescricao());
			x.getFaturasAberta().addAll(faturaCP(listfaturaContasPagar, TipoFaturaEnum.ContasPagar.getDescricao()));

			for (FaturaContrato f : listfaturaContasPagar) {
				valor += f.getTotal();
			}
			x.setTotalReceber(valor);

		}

		return x;
	}

	private List<FaturasDto> faturaCP(List<FaturaContrato> faturas, String origem) {
		List<FaturasDto> faturasDtos = new ArrayList<>();
		for (BaseFatura b : faturas) {
			FaturasDto f = new FaturasDto(b, origem);
			faturasDtos.add(f);
		}

		return faturasDtos;
	}
}
