package com.example.demo.application.core.apiService.categoryManager.service.dao;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo.application.core.apiService.categoryManager.service.dao.dto.CategoryDto;

@EnableScan
public interface CategoryRepository extends PagingAndSortingRepository<CategoryDto, String> {

	List<CategoryDto> findAll();
	
	List<CategoryDto> findByCategoryName(String categoryName);
	
	List<CategoryDto> findByProcessSide(String processSide);
	
	void deleteAll();

	void deleteByCategoryName(String categoryName);
	
	void deleteByProcessSide(String processSide);
	
	Boolean existsByCategoryName(String categoryName);
}