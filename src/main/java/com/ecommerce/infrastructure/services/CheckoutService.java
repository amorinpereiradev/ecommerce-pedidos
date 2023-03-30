
package com.ecommerce.infrastructure.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.infrastructure.interfaces.ICheckoutService;
import com.ecommerce.infrastructure.models.CheckoutRequestModel;
import com.ecommerce.infrastructure.models.CheckoutResponseModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckoutService implements ICheckoutService {

	@Value("${api.pagamentos.uri}")
	private String uri;

	@Override
	public CheckoutResponseModel create(CheckoutRequestModel model, String accessToken) throws Exception {

		//enviando o token
		HttpHeaders httpHeaders = new HttpHeaders();//mandando o token no cabesalho da requisiçao
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);//avisando que esta mandando um jason
		httpHeaders.set("Authorization", "Bearer" + accessToken);
		
		//O httpEntity e para avisa o corpo de dados que estou enviando para requisisão
		HttpEntity<CheckoutRequestModel> entity = new HttpEntity<CheckoutRequestModel>(model, httpHeaders);
		
		RestTemplate restTemplate = new RestTemplate();
		
		CheckoutResponseModel response = restTemplate.postForEntity(uri + "/api/Checkout", entity, CheckoutResponseModel.class).getBody();
		
	return response;
}

}
