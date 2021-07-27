package br.com.apidigitalweb.config.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.config.security.UserSS;
import br.com.apidigitalweb.domin.pessoa.Funcionario;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.repository.FuncionarioRepository;
 

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private FuncionarioRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		 
		Funcionario cli = repo.findByEmailAndStatus(email,  StatusActiv.ATIVO.getDescricao() );
		if (cli == null) {
			throw new UsernameNotFoundException(email);
		}
	//	System.out.println(cli.getRolers());
		return new UserSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getRolers() );
	}
}
