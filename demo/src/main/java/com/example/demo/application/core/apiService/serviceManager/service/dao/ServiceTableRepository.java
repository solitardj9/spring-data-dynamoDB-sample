package com.example.demo.application.core.apiService.serviceManager.service.dao;

import java.util.HashMap;
import java.util.Map;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.example.demo.application.core.apiService.serviceManager.service.dao.dto.ServiceDto;
import com.example.demo.system.database.dynamoDB.service.AmazonDynamoDBManager;

@Repository
@EnableDynamoDBRepositories(basePackages = "com.example.demo.application.core.apiService.serviceManager.service.dao")
public class ServiceTableRepository {

	@Autowired
	AmazonDynamoDBManager amazonDynamoDBManager;
	
	@Value("${application.apiService.dynamoTable.service.provisionedThroughput.readCapacityUnits}")
	private Long readCapacityUnits;
	
	@Value("${application.apiService.dynamoTable.service.provisionedThroughput.writeCapacityUnits}")
	private Long writeCapacityUnits;
	
	public final static String SERVICE_TABLE_NAME = "sgw_service";
	
	public Boolean createTable() {
		//
		Projection projection = new Projection().withProjectionType(ProjectionType.ALL);
		ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput(readCapacityUnits, writeCapacityUnits);
		return amazonDynamoDBManager.createTable(ServiceDto.class, projection, provisionedThroughput);
	}
	
	public Boolean isExistTable() {
		//
		return amazonDynamoDBManager.isExistTable(SERVICE_TABLE_NAME);
	}
	
	public Boolean deleteTable() {
		//
		return amazonDynamoDBManager.deleteTable(SERVICE_TABLE_NAME);
	}
	
	public Boolean putItemByPartitionKeyCondition(ServiceDto serviceDto) {
		//
		Map<String, ExpectedAttributeValue> expectedAttributes = new HashMap<>();
		expectedAttributes.put("serviceName", new ExpectedAttributeValue()
													.withValue(new AttributeValue(serviceDto.getServiceName()))
													.withComparisonOperator(ComparisonOperator.NE)
							  );
		
		DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
		saveExpression.setExpected(expectedAttributes);
		
		return amazonDynamoDBManager.putItemByCondition(serviceDto, saveExpression);
	}
}