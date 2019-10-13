package com.pepsi.onenetwork.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@RefreshScope
public class ErrorHandlerConfiguration {
	
	@Value("${connectorservice.endpoint.url}")
	String connectorServicekEndpointUrl;
	
	@Value("${connectorservice.endpoint.username}")
	String connectorServiceUsername;
	
	@Value("${connectorservice.endpoint.password}")
	String connectorServicePassword;

	@Value("${dataservice.endpoint.retrydetails.url}")
	String dataServiceRetryDetailsEndpointUrl;
	
	@Value("${dataservice.endpoint.updateretrydetails.url}")
	String dataServiceUpdateRetryDetailsEndpointUrl;
	
	@Value("${errorhandler.retrycount}")
	String defaultRetryCount;
	
}
