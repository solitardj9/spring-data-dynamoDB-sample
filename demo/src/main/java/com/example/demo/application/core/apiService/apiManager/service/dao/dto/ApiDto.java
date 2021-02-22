package com.example.demo.application.core.apiService.apiManager.service.dao.dto;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.example.demo.application.core.apiService.apiManager.service.dao.ApiTableRepository;
import com.example.demo.system.database.dynamoDB.service.impl.AmazonDynamoDbConverterConfig.JsonConverter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = ApiTableRepository.API_TABLE_NAME)
public class ApiDto {

	@DynamoDBHashKey(attributeName = "apiName")
	private String apiName;			// Globally Unique Name
	
	@DynamoDBAttribute(attributeName = "apiKeyword")
	private String apiKeyword;		// overlap available
	
	@DynamoDBAttribute(attributeName = "apiLabel")
	private String apiLabel;		// Permanent Label
	
	@DynamoDBAttribute(attributeName = "categoryLabel")
	private String categoryLabel;	// Permanent Label of category 
	
	@DynamoDBAttribute(attributeName = "serviceLabel")
	private String serviceLabel;	// Permanent Label of service 
	
	@DynamoDBAttribute
	@DynamoDBTypeConverted(converter = JsonConverter.class)
	private Map<String, Object> apiInfo;
    
	@DynamoDBAttribute(attributeName = "version")
	private Long version;
}