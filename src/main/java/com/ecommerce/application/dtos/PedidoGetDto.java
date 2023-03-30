package com.ecommerce.application.dtos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PedidoGetDto {

	private String numeroPedido;
	private Date dataHoraPedido;
	private String tipoPagamento;
	private Integer qtdParcelas;
	private BigDecimal totalPedido;
	private String status;
	
	private ClienteGetDto cliente;
	private List<ItemPedidoGetDto> itensPedido;

	
}
