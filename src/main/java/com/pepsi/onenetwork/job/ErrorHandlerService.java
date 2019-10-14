package com.pepsi.onenetwork.job;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.pepsi.onenetwork.configuration.ErrorHandlerConfiguration;
import com.pepsi.onenetwork.model.ConnectorServiceRequestModel;
import com.pepsi.onenetwork.model.ErrorHandlerModel;
import com.pepsi.onenetwork.util.ErrorHandlerUtil;
import com.pepsi.onenetwork.util.RestTemplateUtil;

@Component
public class ErrorHandlerService {

	@Autowired
	ErrorHandlerConfiguration config;
	
	@Autowired
	ErrorHandlerUtil errorHandlerUtil;
	
	@Autowired
	RestTemplateUtil restTemplateUtil;
	
	@Scheduled(fixedRate = 5000)
	public void create() throws URISyntaxException
	{
		RestTemplate restTemplate = new RestTemplate();
		URI uri = new URI("https://jsontocsvconvert.cfapps.io/jsontocsv");
		HttpHeaders header = new HttpHeaders();
		header.setBasicAuth("admin123", "admin@123");
		header.set("Content-Type", "application/json");
		String json = "[{\"param3\": \"value3\",\"param2\": \"value2\",\"param1\": \"value1\",\"paramC\": \"valueC\",\"paramB\": \"valueB\",\"paramA\": \"valueA\"},{\"param3\": \"value3\",\"param2\": \"value2\",\"param1\": \"value1\",\"paramC\": \"valueC\",\"paramB\": \"valueB\",\"paramA\": \"valueA\"},{\"param3\": \"value3\",\"param2\": \"value2\",\"param1\": \"value1\",\"paramC\": \"valueC\",\"paramB\": \"valueB\",\"paramA\": \"valueA\"}]";		
		HttpEntity<String> request = new HttpEntity<String>(json,header);
		ResponseEntity<String> response = restTemplate.exchange("https://jsontocsvconvert.cfapps.io/jsontocsv", HttpMethod.POST, request, String.class);
		System.out.println("response : "+response);
	}
	
	@Scheduled(cron = "* 0/10 * * * ?")
	public void postRetryRequestDetailsForConnectorService()
	{
		String dataServiceResponse = restTemplateUtil.getRetryRequestsFromDataService();
		if(dataServiceResponse != null)
		{
			List<ConnectorServiceRequestModel> requestList = errorHandlerUtil.convertFromJsonToList(dataServiceResponse);
			if(requestList.size() > 0)
			{
				for(ConnectorServiceRequestModel model : requestList)
				{
					try {
						ResponseEntity<String> connectorResponseEntity = restTemplateUtil.postRetryRequestToConnectorService(model);
						ErrorHandlerModel errorHandlerModel = new ErrorHandlerModel();
						errorHandlerModel.setReferenceId(model.getId());
						errorHandlerModel.setRetriesLeft(Integer.parseInt(config.getDefaultRetryCount()));
						if(connectorResponseEntity.getStatusCode().is2xxSuccessful())
						{
							
						}
						else
						{
							
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
	}
}
