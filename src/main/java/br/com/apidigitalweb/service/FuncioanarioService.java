package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import br.com.apidigitalweb.domin.pessoa.Cliente;
import br.com.apidigitalweb.domin.pessoa.Funcionario;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.repository.ClienteRepository;
import br.com.apidigitalweb.repository.FuncionarioRepository; 

@Service
public class FuncioanarioService extends BaseServicePessoa<Funcionario> implements Serializable {
	private static final long serialVersionUID = 1L;


	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	FuncionarioRepository repo;
	
	@Autowired
	 BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public Page<Funcionario> findallpage(String find, Pageable page) {
		return repo.findByNomeContainingIgnoreCase(find, page);
	}

	@Override
	public void prenew(Funcionario obj) {
		obj.setStatus(StatusActiv.ATIVO.getDescricao());
	} 
	
	public void setpassword(String email) {
		mailSender.send(prepareNewPasswordEmail(  email));
	
	} 
	protected SimpleMailMessage prepareNewPasswordEmail(String email) {
		SimpleMailMessage sm = new SimpleMailMessage();
		Funcionario f = repo.findByEmailAndStatus(email, StatusActiv.ATIVO.getDescricao());
		String senha=getRandomPass();
		f.setSenha(bCryptPasswordEncoder.encode(senha));
		repo.save(f);
		
		sm.setTo(f.getEmail());
		sm.setSubject("Solicitação de nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + senha);
		return sm;
	}

	public   String getRandomPass( ){
	char[] chart ={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

	char[] senha= new char[7];

	int chartLenght = chart.length;
	Random rdm = new Random();

	for (int x=0; x<7; x++)
	senha[x] = chart[rdm.nextInt(chartLenght)];

	return new String(senha);
	}

}
