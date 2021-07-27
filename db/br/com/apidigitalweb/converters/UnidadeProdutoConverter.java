package br.com.apidigitalweb.converters;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.apidigitalweb.enuns.UnidadeProdutoEnum;

@Converter
public class UnidadeProdutoConverter implements
AttributeConverter<String, Integer>{

	@Override
	public Integer convertToDatabaseColumn(String attribute) { 
		return UnidadeProdutoEnum.findById(attribute);
	}

	@Override
	public String convertToEntityAttribute(Integer dbData) { 
		return  UnidadeProdutoEnum.getById(dbData);
	}

	 
}
