package com.example.demo.application.core.apiService.serviceManager.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service {

	private String serviceId;
	
	private String serviceName;		// Globally Unique Name
	
	private String serviceLabel;	// Permanent Label
	
	private String categoryLabel;	// Permanent Label of category 
	
	private Map<String, Object> attributes;
    
	private Long version;
}