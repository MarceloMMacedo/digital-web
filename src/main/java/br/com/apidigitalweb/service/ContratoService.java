package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.config.exception.AuthorizationException;
import br.com.apidigitalweb.config.security.UserSS;
import br.com.apidigitalweb.config.services.UserService;
import br.com.apidigitalweb.domin.contratos.Contrato;
import br.com.apidigitalweb.domin.contratos.Patrimonio;
import br.com.apidigitalweb.domin.pessoa.Empresa;
import br.com.apidigitalweb.dto.SampleDto;
import br.com.apidigitalweb.dto.contrato.ContratoDto;
import br.com.apidigitalweb.dto.contrato.SampleContratoDto;
import br.com.apidigitalweb.repository.ContratoRepository;
import br.com.apidigitalweb.repository.FaturaContratoRepository;
import br.com.apidigitalweb.repository.PatrimonioRepository;

@Service
public class ContratoService extends BaseServic<Contrato> implements Serializable {

	@Autowired
	ContratoRepository repo;

	@Autowired
	FaturaContratoRepository faturaContratoRepository;

	@Autowired
	PatrimonioRepository patrimonioRepository;

	public Page<SampleContratoDto> findallpagesampledto(String find, Pageable page) {
		Page<Contrato> findallpage = repo.findByNomeContainingIgnoreCaseOrNomeIsNull(find, page);
		Page<SampleContratoDto> findallpagesampledto = findallpage.map(x -> new SampleContratoDto(x));
		return findallpagesampledto;
	}

	public Contrato save(ContratoDto obj) {
		Contrato c = new Contrato();
		return c;
	}

	public ContratoDto findbyid(long id) {
		ContratoDto contratoDto = new ContratoDto(Empresa.getEmpresa(), fingbyid(id));
		return contratoDto;
	}

	@Override
	public Contrato fingbyid(Long id) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		try {
			obj = repo.findById(id).get();
			obj.setImagemView(downloadFile(obj.getImagem() + "." + obj.getExtension()));
			obj.setFaturaContratos(faturaContratoRepository.findAllByContrato(obj));
			return obj;
		} catch (Exception e) {
			throw new DataIntegrityViolationException("Contrato inxistente");
		}
	}

	@Override
	public void prenew(Contrato obj) {
		obj.setDataInicio(new Date());
		super.prenew(obj);
	}

	@Override
	public void preSaveObj(Contrato obj) {
		super.preSaveObj(obj);
	}

	public List<SampleDto> findAllByContratoIsNull() {
		List<Patrimonio> p = patrimonioRepository.findAllByContratoIsNull();
		List<SampleDto> findAllByContratoIsNull = p.stream().map(x -> new SampleDto(x, ""))
				.collect(Collectors.toList());
		return findAllByContratoIsNull;

	}
}
