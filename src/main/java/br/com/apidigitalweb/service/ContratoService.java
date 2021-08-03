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
import br.com.apidigitalweb.controller.estoque.AnuncioContratoController;
import br.com.apidigitalweb.domin.contratos.Contrato;
import br.com.apidigitalweb.domin.contratos.ItensContratoPatrimonio;
import br.com.apidigitalweb.domin.contratos.Patrimonio;
import br.com.apidigitalweb.domin.estoque.AnuncioContrato;
import br.com.apidigitalweb.domin.pessoa.Empresa;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.dto.SampleDto;
import br.com.apidigitalweb.dto.contrato.ContratoDto;
import br.com.apidigitalweb.dto.contrato.SampleContratoDto;
import br.com.apidigitalweb.dto.pessoa.BasePessoaJuridicaDTO;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.openfaing.ViaCEPClient;
import br.com.apidigitalweb.repository.AnuncioContratoRepository;
import br.com.apidigitalweb.repository.ContratoRepository;
import br.com.apidigitalweb.repository.FaturaContratoRepository;
import br.com.apidigitalweb.repository.ItensContratoPatrimonioRepository;
import br.com.apidigitalweb.repository.PatrimonioRepository;

@Service
public class ContratoService extends BaseServic<Contrato> implements Serializable {

	@Autowired
	ContratoRepository repo;

	@Autowired
	FaturaContratoRepository faturaContratoRepository;

	@Autowired
	PatrimonioRepository patrimonioRepository;

	@Autowired
	private ViaCEPClient viaCEPClient;

	@Autowired
	ItensContratoPatrimonioRepository itensContratoPatrimonioRepository;

	@Autowired
	private AnuncioContratoRepository anuncioContratoRepository;

	@Autowired
	private EmpresaService empresaService;

	public Page<SampleContratoDto> findallpagesampledto(String find, Pageable page) {
		Page<Contrato> findallpage = repo.findByNomeContainingIgnoreCaseOrNomeIsNull(find, page);
		Page<SampleContratoDto> findallpagesampledto = findallpage.map(x -> new SampleContratoDto(x));
		return findallpagesampledto;
	}

 

	public ContratoDto findbyid(long id) {
		ContratoDto contratoDto = new ContratoDto(empresaService.getEmpresa(), fingbyid(id));
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
			obj.getAnuncioContratos().stream().forEach(x -> {
				x.setImagemView(downloadFile(x.getImagem() + "." + x.getExtension()));
			});
			 
			return obj;
		} catch (Exception e) {
			throw new DataIntegrityViolationException("Contrato inxistente");
		}
	}

	@Override
	public void prenew(Contrato obj) {
		obj.setDataInicio(new Date());
		obj.setDiaLeitura(10);
		obj.setDiaVencimento(10);
		obj.setStatus(StatusActiv.ATIVO.getDescricao());
		super.prenew(obj);
	}

 
	@Override
	public void preSaveObj(Contrato obj) {
		for (ItensContratoPatrimonio i : obj.getItenspatrimonio()) {
			i.setContrato(new Contrato());
			i.getContrato().setId(obj.getId());
			Patrimonio p=patrimonioRepository.findById(i.getPatrimonio().getId()).get();
			p.setContrato(new Contrato());
			p.getContrato().setId(obj.getId());
			patrimonioRepository.save(p);
		}
		for (AnuncioContrato i : obj.getAnuncioContratos()) {
			i.setContrato(new Contrato());
			i.getContrato().setId(obj.getId());
			
			//AnuncioContrato p=anuncioContratoRepository.findById(i.getId()).get();
			//.setContrato(new Contrato());
		//	p.getContrato().setId(obj.getId());
			anuncioContratoRepository.save(i);
		}
		itensContratoPatrimonioRepository.saveAll(obj.getItenspatrimonio());
		 
	}

	public void deleteAnuncio(Long id) {
		anuncioContratoRepository.deleteById(id);
	}
	
	public void deleteItemcontrato(long id) {
		
		ItensContratoPatrimonio i = itensContratoPatrimonioRepository.findById(id).get();
		Patrimonio p=patrimonioRepository.findById(i.getPatrimonio().getId()).get();	
		p.setContrato(null);

		patrimonioRepository.save(p);
		itensContratoPatrimonioRepository.deleteById(id);
	}

	public List<SampleDto> findAllByContratoIsNull() {
		List<Patrimonio> p = patrimonioRepository.findAllByContratoIsNull();
		List<SampleDto> findAllByContratoIsNull = p.stream().map(x -> new SampleDto(x, ""))
				.collect(Collectors.toList());
		return findAllByContratoIsNull;

	}

	public Endereco getEndereco(String cep) {
		cep = cep.replaceAll("\\p{Punct}", "");
		Endereco e = viaCEPClient.buscaEnderecoPor(cep);
		return e;

	}

}
