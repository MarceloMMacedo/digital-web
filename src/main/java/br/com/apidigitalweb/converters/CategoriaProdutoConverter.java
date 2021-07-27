package br.com.apidigitalweb.converters;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.apidigitalweb.enuns.EnumCategoriaProduto;
import br.com.apidigitalweb.enuns.TipoProdutoEnum;

@Converter
public class CategoriaProdutoConverter implements
AttributeConverter<String, Integer>{

	@Override
	public Integer convertToDatabaseColumn(String attribute) { 
		return EnumCategoriaProduto. findById(attribute);
	}

	@Override
	public String convertToEntityAttribute(Integer dbData) { 
		return  EnumCategoriaProduto.getById(dbData);
	}

	 
}
