package com.example.demo.application.core.apiService.apiManager.service;

import java.util.List;
import java.util.Map;

import com.example.demo.application.core.apiService.apiManager.model.Api;
import com.example.demo.application.core.apiService.apiManager.model.exception.ExceptionApiAlreayExist;
import com.example.demo.application.core.apiService.apiManager.model.exception.ExceptionApiBadRequest;
import com.example.demo.application.core.apiService.apiManager.model.exception.ExceptionApiManagerFailure;
import com.example.demo.application.core.apiService.apiManager.model.exception.ExceptionApiNotFound;

public interface ApiManager {

	public Boolean isInitialized();
	
	public Api addApi(String apiName, String apiKeyword, Map<String, Object> apiInfo, String apiLabel, String categoryLabel, String serviceLabel) throws ExceptionApiBadRequest, ExceptionApiAlreayExist, ExceptionApiManagerFailure;
	
	public Api getApi(String apiName) throws ExceptionApiBadRequest, ExceptionApiNotFound;
	
	public Api getApiByApiNameAndCategoryLabelAndServiceLabel(String apiName, String categoryLabel, String serviceLabel) throws ExceptionApiBadRequest, ExceptionApiNotFound;
	
	public Api getApiByApiLabelAndCategoryLabelAndServiceLabel(String apiLabel, String categoryLabel, String serviceLabel) throws ExceptionApiBadRequest, ExceptionApiNotFound;
	
	public List<Api> getAllApi();
	
	public List<Api> getAllApiByCategoryLabel(String categoryLabel) throws ExceptionApiBadRequest;
	
	public List<Api> getAllApiByCategoryLabelAndServiceLabel(String categoryLabel, String serviceLabel) throws ExceptionApiBadRequest;
	
	public List<Api> getAllApiByApiKeywordAndCategoryLabelAndServiceLabel(String apiKeyword, String categoryLabel, String serviceLabel) throws ExceptionApiBadRequest;
	
	public Api updateApi(String apiName, Map<String, Object> apiInfo, Boolean merge) throws ExceptionApiBadRequest, ExceptionApiNotFound, ExceptionApiManagerFailure;
	
	public Api updateApiKeyword(String apiName, String apiKeyword) throws ExceptionApiBadRequest, ExceptionApiAlreayExist, ExceptionApiNotFound, ExceptionApiManagerFailure;

	public Boolean deleteApi(String apiName) throws ExceptionApiBadRequest, ExceptionApiNotFound, ExceptionApiManagerFailure;
	
	public Boolean deleteAllApi();
	
	public Boolean isValidApi(String apiName);
	
	public void deleteTable();
}