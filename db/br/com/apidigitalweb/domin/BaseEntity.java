package br.com.apidigitalweb.domin;

public interface BaseEntity {
	Long getId();

	String getNome();
	
	void setNome(String nome);

	String getImagem();

	void setId(Long id);
	
	String	getDescricao();

	/*
	 * String getName();
	 * 
	 * String getAvatar();
	 * 
	 * String getAvatarView();
	 * 
	 * String getEmail();
	 */ 

	void setImagem(String imagem);
 

	String getExtension();

	void setExtension(String extension);

}
