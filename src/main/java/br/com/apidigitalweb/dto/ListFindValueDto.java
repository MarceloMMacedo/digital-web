package br.com.apidigitalweb.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListFindValueDto implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String value;
	private Long option;
	public ListFindValueDto(String value, Long key) {
		super();
		this.value = value;
		this.option = key;
	}
	 

}
