package br.com.apidigitalweb.converters;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.apidigitalweb.enuns.StatusActiv;

@Converter
public class StatusConverter implements
AttributeConverter<String, Integer>{

	@Override
	public Integer convertToDatabaseColumn(String attribute) { 
		return StatusActiv.findById(attribute);
	}

	@Override
	public String convertToEntityAttribute(Integer dbData) { 
		return  StatusActiv.getById(dbData);
	}

	 
}
