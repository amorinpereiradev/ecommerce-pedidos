package com.ecommerce.application.dtos;

import lombok.Data;

@Data
public class ClienteGetDto {

	private Integer id;
	private String nome;
	private String email;
	private String telefone;
	
}
