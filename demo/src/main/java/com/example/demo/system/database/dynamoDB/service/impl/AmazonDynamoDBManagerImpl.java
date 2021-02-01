package com.example.demo.system.database.dynamoDB.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.example.demo.system.database.dynamoDB.service.AmazonDynamoDBManager;

//@EnableDynamoDBRepositories(basePackages = {"com.example.demo.application.familyManager.dao", "com.example.demo.application.memberManager.dao"})
@Service("amazonDynamoDBManager")
public class AmazonDynamoDBManagerImpl implements AmazonDynamoDBManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(AmazonDynamoDBManagerImpl.class);
	
	@Autowired
	AmazonDynamoDB amazonDynamoDb;
	
	@Autowired
	DynamoDBMapper dynamoDbMapper;
	
	/**
	 * 
	 *  keySchemaElements
	 *  		<-- new KeySchemaElement("id", KeyType.HASH)
	 *  attributeDefinitions
	 *  		<-- new AttributeDefinition("id", ScalarAttributeType.S)
	 *  		<-- new AttributeDefinition("mentionId", ScalarAttributeType.N)
	 *  		<-- new AttributeDefinition("createdAt", ScalarAttributeType.S)
	 *  provisionedThroughput
	 *  		<-- new ProvisionedThroughput(1L, 1L)
	 *  globalSecondaryIndexes
	 *  		<-- new GlobalSecondaryIndex())
	 *  			.withIndexName("byMentionId")
	 *  			.withKeySchema(
	 *  				new KeySchemaElement("mentionId", KeyType.HASH),
	 *  				new KeySchemaElement("createdAt", KeyType.RANGE))
	 *  				.withProjection(
	 *  					(new Projection()).withProjectionType(ProjectionType.ALL))
	 *  				.withProvisionedThroughput(new ProvisionedThroughput(1L, 1L))
	 *  localSecondaryIndexes
	 *  		<-- new LocalSecondaryIndex())
	 *  			.withIndexName("byMentionId")
	 *  			.withKeySchema(
	 *  				new KeySchemaElement("mentionId", KeyType.HASH),
	 *  				new KeySchemaElement("createdAt", KeyType.RANGE))
	 *  				.withProjection(
	 *  					(new Projection()).withProjectionType(ProjectionType.ALL))
	 *   
	 * @param _class
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Boolean createTable(Class _class, Projection projection, ProvisionedThroughput provisionedThroughput) {
		//
		CreateTableRequest createTableRequest = null;
		
		if (provisionedThroughput != null) {
			createTableRequest = dynamoDbMapper.generateCreateTableRequest(_class).withProvisionedThroughput(provisionedThroughput);
		}
		else
			createTableRequest = dynamoDbMapper.generateCreateTableRequest(_class).withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
															  
		if (provisionedThroughput != null) {
			createTableRequest.getGlobalSecondaryIndexes().forEach(idx -> idx.setProvisionedThroughput(provisionedThroughput));
			createTableRequest.getGlobalSecondaryIndexes().forEach(idx -> idx.setProjection(projection));
		}
		else
			createTableRequest.getGlobalSecondaryIndexes().forEach(idx -> idx.withProvisionedThroughput(new ProvisionedThroughput(1L, 1L)).withProjection(new Projection().withProjectionType("ALL")));
			
		
		boolean hasTableBeenCreated = TableUtils.createTableIfNotExists(amazonDynamoDb, createTableRequest);
		return hasTableBeenCreated;
	}
	
	

	@Override
	public Boolean deleteTable(String tableName) {
		//
		DeleteTableRequest deleteTableRequest = new DeleteTableRequest();
		
		deleteTableRequest.setTableName(tableName);
		
		boolean hasTableBeenDeleted = TableUtils.deleteTableIfExists(amazonDynamoDb, deleteTableRequest);
		return hasTableBeenDeleted;
	}
	
	@Override
	public Boolean isExistTable(String tableName) {
		//
		try {
			DescribeTableResult describeTableResult = amazonDynamoDb.describeTable(tableName);
			if (describeTableResult != null)
				return true;
			else
				return false;
		} catch (Exception e) {
			LOG.info("[AmazonDynamoDBManager].descrbeTableAsBoolean : error = " + e);
			return false;
		}
	}
	
	@Override
	public DescribeTableResult describeTable(String tableName) {
		//
		DescribeTableResult describeTableResult = amazonDynamoDb.describeTable(tableName);
		return describeTableResult;
	}
	
	@Override
	public Boolean putItem(Object item) {
		//
		try {
			dynamoDbMapper.save(item);
			return true;
		} catch (Exception e) {
			LOG.error("[AmazonDynamoDBManager].putItem : error = " + e);
			return false;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Object> scan(Class _class) {
		//
		try {
			DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
					
			List<Object> result = dynamoDbMapper.scan(_class, scanExpression);
			return result;
		} catch (Exception e) {
			LOG.error("[AmazonDynamoDBManager].scan : error = " + e);
			return null;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object getItem(Class _class, Object hashKey) {
		//
		try {
			Object result = dynamoDbMapper.load(_class, hashKey);
			return result;
		} catch (Exception e) {
			LOG.error("[AmazonDynamoDBManager].getItem : error = " + e);
			return null;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Object> query(Class _class, String indexName, String keyConditionExpression, Map<String, AttributeValue> expressionAttributeValues, AmazonDynamoDBSiEnum amazonDynamoDBSiEnum) {
		//
		if (expressionAttributeValues == null)
			expressionAttributeValues = new HashMap<>();
				
		DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression().withKeyConditionExpression(keyConditionExpression)
																			   .withExpressionAttributeValues(expressionAttributeValues)
																			   .withIndexName(indexName);
		
		if (amazonDynamoDBSiEnum.equals(AmazonDynamoDBSiEnum.GSI))
			queryExpression.setConsistentRead(false);		// cannot use consistent read on GSI
		
		List<Object> result = dynamoDbMapper.query(_class, queryExpression);
		return result;
	}
	
	@Override
	public Boolean deleteItem(Object object) {
		//
		try {
			dynamoDbMapper.delete(object);
			return true;
		} catch (Exception e) {
			LOG.error("[AmazonDynamoDBManager].deleteItem : error = " + e);
			return false;
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Boolean deleteItemByHashKey(Class _class, Object hashKey) {
		//
		try {
			Object object = getItem(_class, hashKey);
			if (object != null) {
				dynamoDbMapper.delete(object);
				return true;
			}
			else
				return false;
		} catch (Exception e) {
			LOG.error("[AmazonDynamoDBManager].deleteItem : error = " + e);
			return false;
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Boolean deleteAllItems(Class _class) {
		//
		try {
			List<Object> list = scan(_class);
			for (Object iter : list) {
				deleteItem(iter);
			}
			return true;
		} catch (Exception e) {
			LOG.error("[AmazonDynamoDBManager].getItem : error = " + e);
			return false;
		}
	}
}