package br.com.apidigitalweb.enuns;

public enum StatusActiv {
	ATIVO( 0, "Ativo"),
	INATIVO(1,  "Inativo"),
	ABERTO( 2, "Aberto"),
	QUIT(3,  "Quit"),
	ANDAMENTO(4,  "Andamento"),

	Aprovado( 5, "Aprovado"),
	Confefencia(6,  "ConferĂȘncia") ;
	 
	private int id;
	private String descricao;

	 

	private StatusActiv(int valor, String descricao) {
		this.id = valor;
		this.descricao = descricao;
	}

	public static String getById(int id) {
		for (StatusActiv e : values()) {
			if (e.id == (id))
				return e.getDescricao();
		}
		return null;
	}

	public static int findById(String s) {
		for (StatusActiv e : values()) {
			if (e.getDescricao().equals(s) )
				return e.getId();
		}
		return -1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	 
 
}
