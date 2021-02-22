package com.example.demo.application.familyManager.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.example.demo.application.familyManager.dao.FamilyRepository;
import com.example.demo.application.familyManager.dao.FamilyTableRepository;
import com.example.demo.application.familyManager.dao.dto.FamilyDto;
import com.example.demo.application.familyManager.service.FamilyManager;
import com.google.common.collect.ImmutableMap;

@Service("familyManager")
public class FamilyManagerImpl implements FamilyManager {

	private static final Logger LOG = LoggerFactory.getLogger(FamilyManagerImpl.class);
	
	@Autowired
	FamilyRepository familyRepository;
	
	@Autowired
	FamilyTableRepository familyTableRepository;
	
	@PostConstruct
	public void init() {
		//
		LOG.info("[FamilyManager].init : FamilyManager start");
		
		if (familyTableRepository.isExistTable()) {
			LOG.info("[FamilyManager].init : Table is exist? true");
		}
		else {
			LOG.info("[FamilyManager].init : Table is exist? false");
			familyTableRepository.createTable();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			LOG.info("[FamilyManager].init : Table is exist? " + familyTableRepository.isExistTable());
		}
	}
	
	@Override
	public void writeSamples() {
		//
		FamilyDto familyDto = null;
		Map<String, Object> remark = new HashMap<>();
		remark.put("key1", "1");
		remark.put("key2", "2");
		
		familyDto = new FamilyDto();
		familyDto.setFamilyId("ID_01");
		familyDto.setFamilyName("AAA");
		familyDto.setRemark(remark);
		familyTableRepository.putItem(familyDto);
		
		familyDto = new FamilyDto();
		familyDto.setFamilyId("ID_02");
		familyDto.setFamilyName("BBB");
		familyDto.setRemark(remark);
		familyTableRepository.putItem(familyDto);
		
		familyDto = new FamilyDto();
		familyDto.setFamilyId("ID_03");
		familyDto.setFamilyName("CCC");
		familyDto.setRemark(remark);
		familyTableRepository.putItem(familyDto);
		
		LOG.info("Family Table = " + familyTableRepository.scan().toString());
		
		LOG.info("FamilyDto = " + familyTableRepository.getItem("ID_02").toString());

		LOG.info("FamilyDtos = " + familyTableRepository.getItemsByFamilyName("AAA").toString());
		
		// delete ID_03
		familyTableRepository.deleteItem(familyDto);
		LOG.info("Family Table = " + familyTableRepository.scan().toString());
		
		// delete ID_02
		familyTableRepository.deleteItem("ID_02");
		LOG.info("Family Table = " + familyTableRepository.scan().toString());
		
		// delete all
		familyTableRepository.deleteAllItems();
		LOG.info("Family Table = " + familyTableRepository.scan().toString());
		
		familyTableRepository.deleteTable();
		LOG.info("[FamilyManager].writeSamples : Table is exist? " + familyTableRepository.isExistTable());
	}
	
	@Override
	public void writeSamples2() {
		//
		FamilyDto familyDto = null;
		Map<String, Object> remark = new HashMap<>();
		remark.put("key1", "1");
		remark.put("key2", "2");
		
		familyDto = new FamilyDto();
		familyDto.setFamilyId("ID_01");
		familyDto.setFamilyName("AAA");
		familyDto.setRemark(remark);
		familyRepository.save(familyDto);
		
		familyDto = new FamilyDto();
		familyDto.setFamilyId("ID_02");
		familyDto.setFamilyName("BBB");
		familyDto.setRemark(remark);
		familyRepository.save(familyDto);
		
		familyDto = new FamilyDto();
		familyDto.setFamilyId("ID_03");
		familyDto.setFamilyName("CCC");
		familyDto.setRemark(remark);
		familyRepository.save(familyDto);
		
		familyDto = new FamilyDto();
		familyDto.setFamilyId("ID_04");
		familyDto.setFamilyName("DDD");
		familyDto.setRemark(remark);
		DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
		Map<String, ExpectedAttributeValue> expectedAttributes = new HashMap<>();
		expectedAttributes.put("familyId", new ExpectedAttributeValue().withValue(new AttributeValue("ID_04")).withComparisonOperator(ComparisonOperator.NE));
		saveExpression.setExpected(expectedAttributes);
		familyTableRepository.putItemByCondition(familyDto, saveExpression);
		
//		PutItemRequest putItemRequest = new PutItemRequest().withTableName("family")
//				.withItem(new ImmutableMap.Builder()
//                        .put("familyId", new AttributeValue("ID_04"))
//                        .put("familyName", new AttributeValue("AAA"))
//                        .build())
//                .withExpected(new ImmutableMap.Builder()
//                        // When exists is false and the id already exists a ConditionalCheckFailedException will be thrown
//                        .put("familyName", new ExpectedAttributeValue(false))
//                        .build());
//		familyTableRepository.putItemByCondition(putItemRequest);
		
		LOG.info("Family Table = " + streamJoin(familyRepository.findAll()));
		
		Map<String, AttributeValue> key = new HashMap<>();
	    key.put("familyId", new AttributeValue().withS("ID_04"));
	 
	    Map<String, AttributeValue> attributeValues = new HashMap<>();
	    attributeValues.put(":familyName", new AttributeValue().withS("AAA"));
	 
	    UpdateItemRequest updateItemRequest = new UpdateItemRequest()
	            .withTableName("family")
	            .withKey(key)
	            .withUpdateExpression("set familyName = :familyName")
	            //.withConditionExpression("attribute_not_exists(familyName)")
	            .withConditionExpression("NOT contains(familyName, AAA)")
	            .withExpressionAttributeValues(attributeValues);
        familyTableRepository.updateItemByCondition(updateItemRequest);
        
        
        
		
		LOG.info("Family Table = " + streamJoin(familyRepository.findAll()));
		
		LOG.info("FamilyDto = " + familyRepository.findById("ID_02").toString());
		
		LOG.info("FamilyDtos = " + streamJoin(familyRepository.findByFamilyName("AAA")));
		
		// delete ID_03
		familyRepository.delete(familyDto);
		LOG.info("Family Table = " + streamJoin(familyRepository.findAll()));
		
		// delete ID_02
		familyRepository.deleteById("ID_02");
		LOG.info("Family Table = " + streamJoin(familyRepository.findAll()));
		
		// delete all
		familyRepository.deleteAll();
		LOG.info("Family Table = " + streamJoin(familyRepository.findAll()));
		
//		familyTableRepository.deleteTable();
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		LOG.info("[FamilyManager].writeSamples : Table is exist? " + familyTableRepository.isExistTable());
	}
	
	// https://stackoverflow.com/questions/42627108/best-way-to-convert-iterablecharacter-to-string
	private String streamJoin(Iterable<FamilyDto> familyDtos){
		//
	    return StreamSupport.stream(familyDtos.spliterator(), true)
	                        .map(Object::toString)
	                        .collect(Collectors.joining(""));
	}
}