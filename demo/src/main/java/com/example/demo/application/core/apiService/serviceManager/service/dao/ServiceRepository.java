package com.example.demo.application.core.apiService.serviceManager.service.dao;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo.application.core.apiService.serviceManager.service.dao.dto.ServiceDto;

@EnableScan
public interface ServiceRepository extends PagingAndSortingRepository<ServiceDto, String> {

	List<ServiceDto> findAll();
	
	List<ServiceDto> findByCategoryLabel(String categoryLabel);
	
	List<ServiceDto> findByServiceNameAndCategoryLabel(String serviceName, String categoryLabel);
	
	List<ServiceDto> findByServiceLabelAndCategoryLabel(String serviceLabel, String categoryLabel);
	
	void deleteByServiceNameAndCategoryLabel(String serviceName, String categoryLabel);		// delete specific
	
	void deleteByCategoryLabel(String categoryLabel);	// delete all in category
	
	void deleteAll();
}