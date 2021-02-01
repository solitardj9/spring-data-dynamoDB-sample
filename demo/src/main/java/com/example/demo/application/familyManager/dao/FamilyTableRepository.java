package com.example.demo.application.familyManager.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.example.demo.application.familyManager.dao.dto.FamilyDto;
import com.example.demo.system.database.dynamoDB.service.AmazonDynamoDBManager;
import com.example.demo.system.database.dynamoDB.service.impl.AmazonDynamoDBSiEnum;

@Repository
@EnableDynamoDBRepositories(basePackages = "com.example.demo.application.familyManager.dao")
public class FamilyTableRepository {

	@Autowired
	AmazonDynamoDBManager amazonDynamoDBManager;
	
	private final static String FAMILY_TABLE_NAME = "family";
	
	public Boolean createTable() {
		//
		Projection projection = new Projection().withProjectionType(ProjectionType.ALL);
		ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput(1L, 1L);
		return amazonDynamoDBManager.createTable(FamilyDto.class, projection, provisionedThroughput);
	}
	
	public Boolean isExistTable() {
		//
		return amazonDynamoDBManager.isExistTable(FAMILY_TABLE_NAME);
	}
	
	public Boolean deleteTable() {
		//
		return amazonDynamoDBManager.deleteTable(FAMILY_TABLE_NAME);
	}
	
	public Boolean putItem(FamilyDto familyDto) {
		//
		return amazonDynamoDBManager.putItem(familyDto);
	}
	
	public List<FamilyDto> scan() {
		//
		List<FamilyDto> ret = null;
		
		List<Object> result = amazonDynamoDBManager.scan(FamilyDto.class);
		if (result != null) {
			ret = new ArrayList<>();
			for (Object iter : result) {
				ret.add((FamilyDto)iter);
			}
		}
		
		return ret;
	}
	
	public FamilyDto getItem(String familyId) {
		//
		return (FamilyDto)amazonDynamoDBManager.getItem(FamilyDto.class, familyId);
	}
	
	public List<FamilyDto> getItemsByFamilyName(String familyName) {
		//
		List<FamilyDto> ret = null;
		
		String keyConditionExpression = "familyName = :familyName";
		
		Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
		expressionAttributeValues.put(":familyName", new AttributeValue().withS(familyName));
		
		List<Object> result = amazonDynamoDBManager.query(FamilyDto.class, "byFamilyName", keyConditionExpression, expressionAttributeValues, AmazonDynamoDBSiEnum.GSI);
		if (result != null) {
			ret = new ArrayList<>();
			for (Object iter : result) {
				ret.add((FamilyDto)iter);
			}
		}
		
		return ret;
	}
	
	public Boolean deleteItem(FamilyDto familyDto) {
		//
		return amazonDynamoDBManager.deleteItem(familyDto);
	}
	
	public Boolean deleteItem(String familyId) {
		//
		return amazonDynamoDBManager.deleteItemByHashKey(FamilyDto.class, familyId);
	}
	
	public Boolean deleteAllItems() {
		//
		return amazonDynamoDBManager.deleteAllItems(FamilyDto.class);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	public Boolean createTable(String tableName) {
		//
		List<KeySchemaElement> keySchemaElements = new ArrayList<>();
		keySchemaElements.add(new KeySchemaElement("familyId", KeyType.HASH));
		
		List<AttributeDefinition> attributeDefinitions = new ArrayList<>();
		attributeDefinitions.add(new AttributeDefinition().withAttributeName("familyId").withAttributeType("S"));
		attributeDefinitions.add(new AttributeDefinition().withAttributeName("familyName").withAttributeType("S"));
		
		ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput(1L, 1L);

		GlobalSecondaryIndex globalSecondaryIndex = new GlobalSecondaryIndex();
		globalSecondaryIndex.setIndexName("byFamilyName");
		List<KeySchemaElement> keySchemaElementsOfGlobalSecondaryIndexes = new ArrayList<>();
		keySchemaElementsOfGlobalSecondaryIndexes.add(new KeySchemaElement("familyName", KeyType.HASH));
		globalSecondaryIndex.setKeySchema(keySchemaElementsOfGlobalSecondaryIndexes);
		globalSecondaryIndex.setProjection(new Projection().withProjectionType(ProjectionType.ALL));
		globalSecondaryIndex.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
		
		List<GlobalSecondaryIndex> globalSecondaryIndexes = new ArrayList<>();
		globalSecondaryIndexes.add(globalSecondaryIndex);
		
		return amazonDynamoDBManager.createTable(tableName, keySchemaElements, attributeDefinitions, provisionedThroughput, globalSecondaryIndexes, null);
	}
	//*/
}