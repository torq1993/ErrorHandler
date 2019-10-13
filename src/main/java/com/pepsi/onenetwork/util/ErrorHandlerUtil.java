package com.pepsi.onenetwork.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pepsi.onenetwork.configuration.ErrorHandlerConfiguration;
import com.pepsi.onenetwork.model.ConnectorServiceRequestModel;

@Component
public class ErrorHandlerUtil {
	
	static final Logger logger = LoggerFactory.getLogger(RestTemplateUtil.class);
	
	@Autowired
	RestTemplateUtil restTemplateUtil;
	
	@Autowired
	ErrorHandlerConfiguration config;
	
	@Autowired
	@Qualifier("Internal")
	RestTemplate restTemplate;
	
	public List convertFromJsonToList(String jsonString)
	{
		ObjectMapper objectMapper = new ObjectMapper();
		List<ConnectorServiceRequestModel> requestList = new ArrayList<ConnectorServiceRequestModel>();
		List<HashMap<String,String>> requestListMap = null;
		try {
//			requestList = objectMapper.readValue(jsonString, new TypeReference<List<ConnectorServiceRequestModel>>(){});
			requestListMap = objectMapper.readValue(jsonString, List.class);
			for(HashMap<String,String> entry : requestListMap)
			{
				try
				{
					ConnectorServiceRequestModel csrModel = objectMapper.convertValue(entry, ConnectorServiceRequestModel.class);
					requestList.add(csrModel);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Exception in ErrorHandlerUtil.convertFromJsonToList "+e);
		}
		//return requestList;
		return requestList;
	}
	
	public void getRetryRequestDetailsForPepsiCoConnectorService()
	{
		//ResponseEntity<String> response = restTemplateUtil.restExchangeToPepsicoConnecterService();
		
	}
	public /*Confirm type*/ void updateRetryRequestDetailsForPepsiCoConnectorService()
	{
		
	}
	
	public static void main(String args[])
	{
		String json = "[{\"param3\": \"value3\",\"param2\": \"value2\",\"param1\": \"value1\",\"paramC\": \"valueC\",\"paramB\": \"valueB\",\"paramA\": \"valueA\"},{\"param3\": \"value3\",\"param2\": \"value2\",\"param1\": \"value1\",\"paramC\": \"valueC\",\"paramB\": \"valueB\",\"paramA\": \"valueA\"},{\"param3\": \"value3\",\"param2\": \"value2\",\"param1\": \"value1\",\"paramC\": \"valueC\",\"paramB\": \"valueB\",\"paramA\": \"valueA\"}]";
	
		ErrorHandlerUtil obj = new ErrorHandlerUtil();
		List<ConnectorServiceRequestModel> requestList = obj.convertFromJsonToList(json);
		System.out.println("++++++++ "+requestList);
	}
}
