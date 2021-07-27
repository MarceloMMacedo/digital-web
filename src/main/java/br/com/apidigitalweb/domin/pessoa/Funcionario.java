package br.com.apidigitalweb.domin.pessoa;

import java.io.Serializable;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.apidigitalweb.converters.FuncaoConverter;
import br.com.apidigitalweb.converters.StatusConverter;
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.domin.BaseEntityPessoa;
import br.com.apidigitalweb.dto.pessoa.BasePessoaJuridicaDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Funcionario extends BasePessoaJuridica implements Serializable, BaseEntity, BaseEntityPessoa {

	private static final long serialVersionUID = 1L;

	private String nomeGuerra;

	@Convert(converter = FuncaoConverter.class)
	private String funcao;

	@Convert(converter = StatusConverter.class)
	private String status;
	
	private String mesFerias;
	private double salario; 
	public Funcionario(BasePessoaJuridicaDTO p, Endereco e) {

		this.atividade_principal = p.getAtividade_principal();
		this.data_situacao = p.getData_situacao();
		this.tipo = p.getTipo();
		this.atividades_secundarias = p.getAtividades_secundarias();
		this.qsa = p.getQsa();
		this.situacao = p.getSituacao();
		this.endereco = e;
		this.enderecos = p.getEnderecos();
		this.porte = p.getPorte();
		this.abertura = p.getAbertura();
		this.natureza_juridica = p.getNatureza_juridica();
		this.fantasia = p.getFantasia();
		this.cnpj = p.getCnpj();
		this.ultima_atualizacao = p.getUltima_atualizacao();
		this.status = p.getStatus();
		this.efr = p.getEfr();
		this.motivo_situacao = p.getMotivo_situacao();
		this.situacao_especial = p.getSituacao_especial();
		this.data_situacao_especial = p.getData_situacao_especial();
		this.capital_social = p.getCapital_social();
		this.nome = p.getNome();
		this.descricao = p.getDescricao();
		this.ie = p.getIe();
		this.telefone = p.getTelefone();
		this.contato = p.getContato();
		this.contatos = p.getContatos();
		this.imagem = p.getImagem();
		this.extension = p.getExtension();
		this.limitecredito = p.getLimitecredito();
		this.email = p.getEmail();
		this.senha = p.getSenha();
		this.rolers = p.getRolers();
	}

	@Override
	public Funcionario loadCnpjThis(BasePessoaJuridicaDTO p, Endereco e) {

		this.atividade_principal = p.getAtividade_principal();
		this.data_situacao = p.getData_situacao();
		this.tipo = p.getTipo();
		this.atividades_secundarias = p.getAtividades_secundarias();
		this.qsa = p.getQsa();
		this.situacao = p.getSituacao();
		this.endereco = e;
		this.enderecos = p.getEnderecos();
		this.porte = p.getPorte();
		this.abertura = p.getAbertura();
		this.natureza_juridica = p.getNatureza_juridica();
		this.fantasia = p.getFantasia();
		this.cnpj = p.getCnpj();
		this.ultima_atualizacao = p.getUltima_atualizacao();
		this.status = p.getStatus();
		this.efr = p.getEfr();
		this.motivo_situacao = p.getMotivo_situacao();
		this.situacao_especial = p.getSituacao_especial();
		this.data_situacao_especial = p.getData_situacao_especial();
		this.capital_social = p.getCapital_social();
		this.nome = p.getNome();
		this.descricao = p.getDescricao();
		this.ie = p.getIe();
		this.telefone = p.getTelefone();
		this.contato = p.getContato();
		this.contatos = p.getContatos();
		this.imagem = p.getImagem();
		this.extension = p.getExtension();
		this.limitecredito = p.getLimitecredito();
		this.email = p.getEmail();
		this.senha = p.getSenha();
		this.rolers = p.getRolers();
		return this;
	}

}
