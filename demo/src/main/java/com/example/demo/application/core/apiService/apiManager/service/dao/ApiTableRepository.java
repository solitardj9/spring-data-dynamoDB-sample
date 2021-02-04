package com.example.demo.application.core.apiService.apiManager.service.dao;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.example.demo.application.core.apiService.apiManager.service.dao.dto.ApiDto;
import com.example.demo.system.database.dynamoDB.service.AmazonDynamoDBManager;

@Repository
@EnableDynamoDBRepositories(basePackages = "com.example.demo.application.core.apiService.apiManager.service.dao")
public class ApiTableRepository {

	@Autowired
	AmazonDynamoDBManager amazonDynamoDBManager;
	
	@Value("${application.apiService.dynamoTable.api.provisionedThroughput.readCapacityUnits}")
	private Long readCapacityUnits;
	
	@Value("${application.apiService.dynamoTable.api.provisionedThroughput.writeCapacityUnits}")
	private Long writeCapacityUnits;
	
	public final static String API_TABLE_NAME = "sgw_api";
	
	public Boolean createTable() {
		//
		Projection projection = new Projection().withProjectionType(ProjectionType.ALL);
		ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput(readCapacityUnits, writeCapacityUnits);
		return amazonDynamoDBManager.createTable(ApiDto.class, projection, provisionedThroughput);
	}
	
	public Boolean isExistTable() {
		//
		return amazonDynamoDBManager.isExistTable(API_TABLE_NAME);
	}
	
	public Boolean deleteTable() {
		//
		return amazonDynamoDBManager.deleteTable(API_TABLE_NAME);
	}
}