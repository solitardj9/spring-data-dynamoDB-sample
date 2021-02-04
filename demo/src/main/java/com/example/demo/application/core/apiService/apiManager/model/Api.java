package com.example.demo.application.core.apiService.apiManager.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api {

	private String apiId;
	
	private String apiName;			// overlap available
	
	private String apiKeyword;		// Globally Unique Name
	
	private String apiLabel;		// Permanent Label
	
	private String categoryLabel;	// Permanent Label of category 
	
	private String serviceLabel;	// Permanent Label of service 
	
	private Map<String, Object> apiInfo;
    
	private Long version;
}