package br.com.apidigitalweb.service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.apidigitalweb.domin.contratos.Patrimonio;
import br.com.apidigitalweb.domin.estoque.Modelo;
import br.com.apidigitalweb.domin.estoque.Produto;
import br.com.apidigitalweb.dto.BaseDto;
import br.com.apidigitalweb.enuns.StatusActiv;
import br.com.apidigitalweb.repository.ModeloRepository;
import br.com.apidigitalweb.repository.PatrimonioRepository;

@Service
public class PatrimonioService extends BaseServic<Patrimonio>  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private PatrimonioRepository repo;
	
	@Override
	public List<BaseDto> getAllsample() {
		// TODO Auto-generated method stub
		return getAll().stream().map(x -> new BaseDto(x, downloadFile(x.getImagem() + "." + x.getExtension())))
				.collect(Collectors.toList());
	}

	@Override
	public Page<BaseDto> findallpagedto(String find, Pageable page) {

		Page<BaseDto> baseDtos = findallpage(find, page)
				.map(x -> new BaseDto(x, downloadFile(x.getImagem() + "." + x.getExtension())));
		return baseDtos;
	}
	@Override
	public Page<Patrimonio> findallpage(String find, Pageable page) {
		
	 	Page<Patrimonio> findallpage = repo.findByStatusAndNomeContainingIgnoreCaseOrNomeIsNull(StatusActiv.ATIVO.getDescricao(),find, page);
	 
		return findallpage;
	}
	
	
	public Page<Patrimonio> findallpagetombados(String find, Pageable page) {
		
		Page<Patrimonio> findallpage = repo.findByStatusAndNomeContainingIgnoreCaseOrNomeIsNull(StatusActiv.INATIVO.getDescricao(),find, page);
	 
		return findallpage;
	}
	@Override
	public void prenew(Patrimonio obj) {
		 obj.setStatus(StatusActiv.ATIVO.getDescricao());
	}
}
