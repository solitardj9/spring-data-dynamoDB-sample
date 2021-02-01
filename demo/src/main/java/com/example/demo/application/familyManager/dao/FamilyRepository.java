package com.example.demo.application.familyManager.dao;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo.application.familyManager.dao.dto.FamilyDto;

@EnableScan
public interface FamilyRepository extends PagingAndSortingRepository<FamilyDto, String> {

	List<FamilyDto> findAll();
	
	List<FamilyDto> findByFamilyName(String familyName);
	
	void deleteAll();
}