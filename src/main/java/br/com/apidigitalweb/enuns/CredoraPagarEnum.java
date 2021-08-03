package br.com.apidigitalweb.enuns;

public enum CredoraPagarEnum { 
	ADMIN(1,"Administrador Geral"),  
	FINANCEIRO(3,"Operador  Financeiro" ),
	ADMINFINANCEIRO(4,"Adminstarador Financeiro"),  
	tecnico(6,"Técnico"),
	ESTOQUE (7,"Estoquista") ;

	private int id;
	private String descricao;

	 

	private CredoraPagarEnum(int valor, String descricao) {
		this.id = valor;
		this.descricao = descricao;
	}

	public static String getById(int id) {
		for (CredoraPagarEnum e : values()) {
			if (e.id == (id))
				return e.getDescricao();
		}
		return null;
	}

	public static int findById(String s) {
		for (CredoraPagarEnum e : values()) {
			if (e.getDescricao().equals(s) )
				return e.getId();
		}
		return 0;
	}

	 

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	 

}
