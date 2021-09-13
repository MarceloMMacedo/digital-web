package br.com.apidigitalweb.service;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import br.com.apidigitalweb.config.security.UserSS;
import br.com.apidigitalweb.config.services.UserService;
import br.com.apidigitalweb.domin.ordemservico.OrdemServico;
import br.com.apidigitalweb.domin.pessoa.Funcionario;

@Service
public class OrdemServicoService extends BaseServicOrdem<OrdemServico> implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
 @Override
public void prenew(OrdemServico obj) {
	 UserSS user = UserService.authenticated();
	 Funcionario f=new Funcionario();
	 f.setId(user.getId());
	 obj.setVendedor(f);
	 super.prenew(obj);
}
}
