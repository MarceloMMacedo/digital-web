package br.com.apidigitalweb.domin;

public interface BaseEntity {
	Long getId();

	String getNome();

	void setNome(String nome);

	String getImagem();

	void setId(Long id);

	String getDescricao();

	String getImagemView();

	void setImagemView(String imageView);

	void setImagem(String imagem);

	String getExtension();

	void setExtension(String extension);

}
