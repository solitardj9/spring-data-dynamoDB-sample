package com.example.demo.system.database.dynamoDB.service;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.example.demo.system.database.dynamoDB.service.impl.AmazonDynamoDBSiEnum;

public interface AmazonDynamoDBManager {

	@SuppressWarnings("rawtypes")
	public Boolean createTable(Class _class, Projection projection, ProvisionedThroughput provisionedThroughput);
	
	public Boolean deleteTable(String tableName);
	
	public Boolean isExistTable(String tableName);

	public DescribeTableResult describeTable(String tableName);
	
	public Boolean putItem(Object item);
	
	public Boolean putItemByCondition(Object item, DynamoDBSaveExpression saveExpression);
	
	public Boolean putItemByCondition(PutItemRequest putItemRequest);
	
	public Boolean updateItemByCondition(UpdateItemRequest updateItemRequest);
	
	@SuppressWarnings("rawtypes")
	public List<Object> scan(Class _class);
	
	@SuppressWarnings("rawtypes")
	public Object getItem(Class _class, Object hashKey);
	
	@SuppressWarnings("rawtypes")
	public List<Object> query(Class _class, String indexName, String keyConditionExpression, Map<String, AttributeValue> expressionAttributeValues, AmazonDynamoDBSiEnum amazonDynamoDBSiEnum);
	
	public Boolean deleteItem(Object object);
	
	@SuppressWarnings("rawtypes")
	public Boolean deleteItemByHashKey(Class _class, Object hashKey);
	
	@SuppressWarnings("rawtypes")
	public Boolean deleteAllItems(Class _class);	
}