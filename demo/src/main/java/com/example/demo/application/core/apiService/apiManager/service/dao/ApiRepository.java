package com.example.demo.application.core.apiService.apiManager.service.dao;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo.application.core.apiService.apiManager.service.dao.dto.ApiDto;

@EnableScan
public interface ApiRepository extends PagingAndSortingRepository<ApiDto, String> {

	List<ApiDto> findAll();
	
	List<ApiDto> findByApiKeyword(String apiKeyword);
	
	List<ApiDto> findByCategoryLabel(String categoryLabel);
	
	List<ApiDto> findByServiceLabel(String serviceLabel);
	
	List<ApiDto> findByCategoryLabelAndServiceLabel(String categoryLabel, String serviceLabel);
	
	List<ApiDto> findByApiNameAndCategoryLabelAndServiceLabel(String apiName, String categoryLabel, String serviceLabel);
	
	List<ApiDto> findByApiLabelAndCategoryLabelAndServiceLabel(String apiLabel, String categoryLabel, String serviceLabel);
	
	List<ApiDto> findByApiKeywordAndCategoryLabelAndServiceLabel(String apiKeyword, String categoryLabel, String serviceLabel);
	
	void deleteByCategoryLabelAndServiceLabelAndApiName(String categoryLabel, String serviceLabel, String apiName);	// delete specific
	
	void deleteByCategoryLabelAndServiceLabel(String categoryLabel, String serviceLabel);	// delete all in category & service
	
	void deleteByCategoryLabel(String categoryLabel);	// delete all in category
	
	void deleteAll();
}