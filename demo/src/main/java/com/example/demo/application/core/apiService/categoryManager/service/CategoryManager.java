package com.example.demo.application.core.apiService.categoryManager.service;

import java.util.List;
import java.util.Map;

import com.example.demo.application.core.apiService.categoryManager.model.Category;
import com.example.demo.application.core.apiService.categoryManager.model.ProcessSideEnum;
import com.example.demo.application.core.apiService.categoryManager.model.exception.ExceptionCategoryAlreayExist;
import com.example.demo.application.core.apiService.categoryManager.model.exception.ExceptionCategoryBadRequest;
import com.example.demo.application.core.apiService.categoryManager.model.exception.ExceptionCategoryManagerFailure;
import com.example.demo.application.core.apiService.categoryManager.model.exception.ExceptionCategoryNotFound;

public interface CategoryManager {

	public Boolean isInitialized();
	
	public Category addCategory(String categoryName, Map<String, Object> attributes, String categoryLabel, ProcessSideEnum processSideEnum) throws ExceptionCategoryBadRequest, ExceptionCategoryAlreayExist, ExceptionCategoryManagerFailure;
	
	public Category getCategory(String categoryName) throws ExceptionCategoryBadRequest, ExceptionCategoryNotFound;
	
	public List<Category> getAllCategory();
	
	public List<Category> getAllCategoryByProcessSide(ProcessSideEnum processSideEnum) throws ExceptionCategoryBadRequest;
	
	public Category updateCategory(String categoryName, Map<String, Object> attributes, Boolean merge) throws ExceptionCategoryBadRequest, ExceptionCategoryNotFound, ExceptionCategoryManagerFailure;
	
	public Category updateCategoryName(String categoryId, String categoryName) throws ExceptionCategoryBadRequest, ExceptionCategoryNotFound, ExceptionCategoryManagerFailure;
	
	public Boolean deleteCategory(String categoryId) throws ExceptionCategoryBadRequest, ExceptionCategoryNotFound, ExceptionCategoryManagerFailure;
	
	public Boolean deleteAllCategory();
	
	public Boolean deleteAllCategoryByProcessSide(ProcessSideEnum processSideEnum);
	
	public Boolean isValidCategory(String categoryName);
}