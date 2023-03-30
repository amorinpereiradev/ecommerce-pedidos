package com.ecommerce.application.controllers;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.application.dtos.PedidoGetDto;
import com.ecommerce.application.dtos.PedidoPostDto;
import com.ecommerce.infrastructure.interfaces.IAuthService;
import com.ecommerce.infrastructure.interfaces.ICheckoutService;
import com.ecommerce.infrastructure.models.AuthRequestModel;
import com.ecommerce.infrastructure.models.AuthResponseModel;
import com.ecommerce.infrastructure.models.CheckoutRequestModel;
import com.ecommerce.infrastructure.models.CheckoutResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = "Pedidos")
@RestController
@RequiredArgsConstructor
public class PedidosController {
	
	@Autowired
	private IAuthService  authService;
	
	@Autowired
	private ICheckoutService CheckoutService;

	@Value("${api.pagamentos.user.name}")
	private String name;
	
	@Value("${api.pagamentos.user.key}")
	private String key;
	
	@ApiOperation("Serviço para cadastro de pedido")
	@PostMapping("/v1/pedidos")
	public ResponseEntity<PedidoGetDto> post(@Valid @RequestBody PedidoPostDto dto) {
		
		try {
			
			//fazendo autenticação
			AuthRequestModel auth = new AuthRequestModel();
			auth.setNome(name);
			auth.setKey(key);
			
			AuthResponseModel authResponse = authService.create(auth);
			
			CheckoutRequestModel checkoutRequest = new CheckoutRequestModel();
			
			checkoutRequest.setNumber(dto.getCobranca().getNumeroCartao());
			checkoutRequest.setHolderName(dto.getCobranca().getNomeTitular());
			checkoutRequest.setHolderDocument(dto.getCobranca().getCpfTitular());
			checkoutRequest.setExpirationMonth(dto.getCobranca().getMesVencimento());
			checkoutRequest.setExpirationYear(dto.getCobranca().getAnoVencimento());
			checkoutRequest.setCvv(dto.getCobranca().getCodigoSeguranca());
			checkoutRequest.setAmount(new BigDecimal(999.99));
			checkoutRequest.setDueAt(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			checkoutRequest.setInstallments(dto.getQtdParcelas());
			
			CheckoutResponseModel checkoutResponse = CheckoutService.create(checkoutRequest, authResponse.getAccessToken());
			
			return ResponseEntity.status(HttpStatus.CREATED).body(null);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
	
	}

	
}

