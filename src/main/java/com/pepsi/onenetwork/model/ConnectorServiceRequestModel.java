package com.pepsi.onenetwork.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectorServiceRequestModel {

	private String connectorPayload;
	private String queueName;
	private String enterpriseName;
	private String sender;
	private String id;
	private String interfaceName;
	private String interfaceVersion;

}
