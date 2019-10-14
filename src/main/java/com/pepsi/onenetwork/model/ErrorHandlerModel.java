package com.pepsi.onenetwork.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorHandlerModel {

	String referenceId;
	String ConnectorServiceRequest;
	String processStatus;
	int retriesLeft;
}
