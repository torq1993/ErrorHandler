package com.pepsi.onenetwork.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.pepsi.onenetwork.configuration.ErrorHandlerConfiguration;
import com.pepsi.onenetwork.constants.IntakeServiceConstants;
import com.pepsi.onenetwork.model.ConnectorServiceRequestModel;
import com.pepsi.onenetwork.models.request.ConnectorRequestModel;

@Component
public class RestTemplateUtil {

	static final Logger logger = LoggerFactory.getLogger(RestTemplateUtil.class);

	@Autowired
	@Qualifier("Internal")
	RestTemplate restTemplate;
	
	@Autowired
	ErrorHandlerConfiguration errorHandlerConfiguration;
	
	public String getRetryRequestsFromDataService()
	{
		ResponseEntity<String> response = null;
		String responseBody = null;
		try
		{
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
			response = restTemplate.exchange(errorHandlerConfiguration.getDataServiceRetryDetailsEndpointUrl(), HttpMethod.GET, requestEntity, String.class);
			responseBody = response.getBody();
		}
		catch(Exception e)
		{
			logger.error("Exception in RestTemplateUtil.getRetryRequestsFromDataService : "+e);
		}
		return responseBody;
	}
	
	public ResponseEntity<String> updateRetryRequestDetailsToDataService()
	{
		return null;
	}
	
	public ResponseEntity<String> postRetryRequestToConnectorService(ConnectorServiceRequestModel connectorRequestModel)
			throws Exception {
		ResponseEntity<String> response = null;
		try {
			HttpHeaders header = new HttpHeaders();
			header.setBasicAuth(errorHandlerConfiguration.getConnectorServiceUsername(), errorHandlerConfiguration.getConnectorServicePassword());
			HttpEntity<ConnectorServiceRequestModel> requestEntity = new HttpEntity<>(connectorRequestModel, header);
			response = restTemplate.exchange(errorHandlerConfiguration.getConnectorServicekEndpointUrl(), HttpMethod.POST,
					requestEntity, String.class);
			if (response != null
					&& (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError())) {
				logger.error("Error in getting response from service");
				throw new Exception(IntakeServiceConstants.FAILURE_RESPONSE);
			}
		} catch (RestClientException e) {
			throw new RuntimeException("Exception in RestTemplateUtil.postRetryRequestToConnectorService while calling URL:"
					+ errorHandlerConfiguration.getConnectorServicekEndpointUrl(), e);
		}
		return response;
	}
	
}
