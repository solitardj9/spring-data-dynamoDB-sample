package com.example.demo.system.database.dynamoDB.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * reference : https://woowabros.github.io/study/2019/06/05/spring-data-dynamodb-1.html
 * 
 * @author HoYoung.Rhee
 *
 */
public class AmazonDynamoDbConverterConfig {

	public static class LocalDateTimeConverter implements DynamoDBTypeConverter<Date, LocalDateTime> {
		
		private static final Logger LOG = LoggerFactory.getLogger(LocalDateTimeConverter.class);
		
		@Override
		public Date convert(LocalDateTime source) {
			return Date.from(source.toInstant(ZoneOffset.UTC));
		}
		
		@Override
		public LocalDateTime unconvert(Date source) {
			return source.toInstant().atZone(TimeZone.getDefault().toZoneId()).toLocalDateTime();
		}
	}
	
	static public class JsonConverter implements DynamoDBTypeConverter<String, Map<String, Object>> {

		private static final Logger LOG = LoggerFactory.getLogger(JsonConverter.class);
		
		private static ObjectMapper om = new ObjectMapper();
		
	    @Override
	    public String convert(Map<String, Object> object) {
	    	// Convert the object to a DynamoDB json string
	    	String jsonString = "{}";
			try {
				jsonString = om.writeValueAsString(object);
				return jsonString;
			} catch (JsonProcessingException e) {
				LOG.error("[JsonConverter].convert : error = " + e);
				return jsonString;
			}
	    }

	    @SuppressWarnings("unchecked")
		@Override
	    public Map<String, Object> unconvert(String jsonString) {
	    	// Convert s to a Map<String, Object> here.
	    	Map<String, Object> item = new HashMap<>();
			try {
				item = om.readValue(jsonString, Map.class);
				return item;
			} catch (JsonProcessingException e) {
				LOG.error("[JsonConverter].unconvert : error = " + e);
				return item;
			}
	    }
	}
}