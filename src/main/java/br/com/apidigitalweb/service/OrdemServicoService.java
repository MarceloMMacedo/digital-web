package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.config.exception.AuthorizationException;
import br.com.apidigitalweb.config.security.UserSS;
import br.com.apidigitalweb.config.services.UserService;
import br.com.apidigitalweb.domin.estoque.AnuncioLoja;
import br.com.apidigitalweb.domin.ordemservico.ItensMaoObraInService;
import br.com.apidigitalweb.domin.ordemservico.ItensMaterialInServiceLoja;
import br.com.apidigitalweb.domin.ordemservico.OrdemServico;
import br.com.apidigitalweb.domin.pessoa.Funcionario;
import br.com.apidigitalweb.dto.ordem.OrdemDto;
import br.com.apidigitalweb.repository.ItensMaoObraInServiceRepository;
import br.com.apidigitalweb.repository.ItensMaterialInServiceLojaRepository;

@Service
public class OrdemServicoService extends BaseServicOrdem<OrdemServico> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private ItensMaoObraInServiceRepository itensMaoObraInServiceRepository;
	@Autowired
	private ItensMaterialInServiceLojaRepository itensMaterialInServiceLojaRepository;
	@Autowired
	private AnuncioLojaService anuncioLojaService;

	@Override
	public void prenew(OrdemServico obj) {
		UserSS user = UserService.authenticated();
		Funcionario f = new Funcionario();
		f.setId(user.getId());
		obj.setVendedor(f);
		super.prenew(obj);
	}

	public OrdemDto fingbyiddto(Long id) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		try {
			OrdemServico obj = repo.findById(id).get();
			obj.setImagemView(downloadFile(obj.getImagem() + "." + obj.getExtension()));

			for (ItensMaterialInServiceLoja x : obj.getItensmaterialInServiceLojas()) {
				x.setImagemView(downloadFile(x.getImagem() + "." + x.getExtension()));
			}

			for (ItensMaoObraInService x : obj.getItensMaoObraInServices()) {
				x.setImagemView(downloadFile(x.getImagem() + "." + x.getExtension()));
			}
			return new OrdemDto(obj);
		} catch (Exception e) {
			// TODO: handle exception
		}
		obj = repo.findById(id).get();
		posfingbyid(obj);

		return new OrdemDto(obj);
	}

	public void addItensMaoObraInService(Long id, ItensMaoObraInService item) {
		OrdemServico obj = fingbyid(id);
		item.setOrdem(obj);
		itensMaoObraInServiceRepository.save(item);
	}
	public void removeitensMaoObraInService(Long id ) {		 
		itensMaoObraInServiceRepository.deleteById(id);
	}

	public void addItensItensMaterialInServiceLoja(Long id, List<ItensMaterialInServiceLoja> itens) {
		OrdemServico obj = fingbyid(id);
		
		for (ItensMaterialInServiceLoja item : itens) {
			AnuncioLoja anuncioLoja = anuncioLojaService.fingbyid(item.getProduto().getId());
			if (anuncioLoja.getSaldoDisponivel() >= item.getQuantidade()) {
				anuncioLoja.setSaldoReserva(anuncioLoja.getSaldoReserva() + item.getQuantidade());
				anuncioLojaService.getRepo().save(anuncioLoja);
				item.setOrdem(obj);
				itensMaterialInServiceLojaRepository.save(item);
			}
		}
		
		
	}

	public void removeItensItensMaterialInServiceLoja(Long id) {
		ItensMaterialInServiceLoja item = itensMaterialInServiceLojaRepository.findById(id).get();
		AnuncioLoja anuncioLoja = anuncioLojaService.fingbyid(item.getProduto().getId());
		{
			anuncioLoja.setSaldoReserva(anuncioLoja.getSaldoReserva() - item.getQuantidade());
			anuncioLojaService.getRepo().save(anuncioLoja);

			itensMaterialInServiceLojaRepository.deleteById(id);
		}

	}
}
