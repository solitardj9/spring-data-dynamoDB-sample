package com.example.demo.application.core.apiService.apiManager.service.dao;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo.application.core.apiService.apiManager.service.dao.dto.ApiDto;

@EnableScan
public interface ApiRepository extends PagingAndSortingRepository<ApiDto, String> {

	List<ApiDto> findAll();
	
	List<ApiDto> findByApiName(String apiName);
	
	List<ApiDto> findByApiKeyword(String apiKeyword);
	
	List<ApiDto> findByCategoryLabel(String categoryLabel);
	
	List<ApiDto> findByServiceLabel(String serviceLabel);
	
	List<ApiDto> findByCategoryLabelAndServiceLabel(String categoryLabel, String serviceLabel);
	
	List<ApiDto> findByCategoryLabelAndServiceLabelAndApiName(String categoryLabel, String serviceLabel, String apiName);
	
	List<ApiDto> findByCategoryLabelAndServiceLabelAndApiKeyword(String categoryLabel, String serviceLabel, String apiKeyword);
	
	void deleteByApiKeyword(String apiKeyword);		// delete specific
	
	void deleteByCategoryLabelAndServiceLabelAndApiKeyword(String categoryLabel, String serviceLabel, String apiKeyword);	// delete specific
	
	void deleteByCategoryLabelAndServiceLabel(String categoryLabel, String serviceLabel);	// delete all in category & service
	
	void deleteByCategoryLabel(String categoryLabel);	// delete all in category
	
	void deleteAll();
}