package com.example.demo.application.core.apiService.categoryManager.service.dao.dto;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.example.demo.application.core.apiService.categoryManager.service.dao.CategoryTableRepository;
import com.example.demo.system.database.dynamoDB.service.impl.AmazonDynamoDbConverterConfig.JsonConverter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = CategoryTableRepository.CATEGORY_TABLE_NAME)
public class CategoryDto {

	@DynamoDBHashKey(attributeName = "categoryName")
	private String categoryName;	// Globally Unique Name
	
	@DynamoDBAttribute(attributeName = "categoryLabel")
	private String categoryLabel;	// Permanent Label
	
	@DynamoDBAttribute
	@DynamoDBTypeConverted(converter = JsonConverter.class)
	private Map<String, Object> attributes;
	
	@DynamoDBAttribute(attributeName = "processSide")
	private String processSide;		// Inside or Outside
    
	@DynamoDBAttribute(attributeName = "version")
	private Long version;
}