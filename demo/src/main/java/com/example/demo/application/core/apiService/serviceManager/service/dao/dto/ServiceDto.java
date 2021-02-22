package com.example.demo.application.core.apiService.serviceManager.service.dao.dto;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.example.demo.application.core.apiService.serviceManager.service.dao.ServiceTableRepository;
import com.example.demo.system.database.dynamoDB.service.impl.AmazonDynamoDbConverterConfig.JsonConverter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = ServiceTableRepository.SERVICE_TABLE_NAME)
public class ServiceDto {
	
	@DynamoDBHashKey(attributeName = "serviceName")
	private String serviceName;		// Globally Unique Name
	
	@DynamoDBAttribute(attributeName = "serviceLabel")
	private String serviceLabel;	// Permanent Label
	
	@DynamoDBAttribute(attributeName = "categoryLabel")
	private String categoryLabel;	// Permanent Label of category 
	
	@DynamoDBAttribute
	@DynamoDBTypeConverted(converter = JsonConverter.class)
	private Map<String, Object> attributes;
    
	@DynamoDBAttribute(attributeName = "version")
	private Long version;
}