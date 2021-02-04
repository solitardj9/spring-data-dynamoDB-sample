package com.example.demo.application.core.apiService.categoryManager.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

	private String categoryId;
	
	private String categoryName;	// Globally Unique Name
	
	private String categoryLabel;	// Permanent Label
	
	private Map<String, Object> attributes;
	
	private String processSide;		// Inside or Outside
    
	private Long version;
}