package br.com.apidigitalweb.service;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import br.com.apidigitalweb.config.exception.AuthorizationException;
import br.com.apidigitalweb.config.security.UserSS;
import br.com.apidigitalweb.config.services.UserService;
import br.com.apidigitalweb.domin.ordemservico.OrdemServico;
import br.com.apidigitalweb.domin.pessoa.Funcionario;
import br.com.apidigitalweb.dto.ordem.OrdemDto;

@Service
public class OrdemServicoService extends BaseServicOrdem<OrdemServico> implements Serializable {

	private static final long serialVersionUID = 1L;

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
			obj = repo.findById(id).get();
			obj.setImagemView(downloadFile(obj.getImagem() + "." + obj.getExtension()));
			return new OrdemDto(obj);
		} catch (Exception e) {
			// TODO: handle exception
		}
		obj=repo.findById(id).get();
		posfingbyid(obj);
		
		return  new OrdemDto(obj);
	}
	
	 
}
