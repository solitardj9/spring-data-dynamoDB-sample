package com.example.demo.application.core.apiService.serviceManager.service;

import java.util.List;
import java.util.Map;

import com.example.demo.application.core.apiService.serviceManager.model.Service;
import com.example.demo.application.core.apiService.serviceManager.model.exception.ExceptionServiceAlreayExist;
import com.example.demo.application.core.apiService.serviceManager.model.exception.ExceptionServiceBadRequest;
import com.example.demo.application.core.apiService.serviceManager.model.exception.ExceptionServiceManagerFailure;
import com.example.demo.application.core.apiService.serviceManager.model.exception.ExceptionServiceNotFound;

public interface ServiceManager {

	public Boolean isInitialized();
	
	public Service addService(String serviceName, Map<String, Object> attributes, String serviceLabel, String categoryLabel) throws ExceptionServiceBadRequest, ExceptionServiceAlreayExist, ExceptionServiceManagerFailure;
	
	public Service getService(String serviceName) throws ExceptionServiceBadRequest, ExceptionServiceNotFound;
	
	public Service getServiceByServiceNameAndCategoryLabel(String serviceName, String categoryLabel) throws ExceptionServiceBadRequest, ExceptionServiceNotFound;
	
	public Service getServiceByServiceLabelAndCategoryLabel(String serviceLabel, String categoryLabel) throws ExceptionServiceBadRequest, ExceptionServiceNotFound;
	
	public List<Service> getAllService();

	public List<Service> getAllServiceByCategoryLabel(String categoryLabel) throws ExceptionServiceBadRequest;
	
	public Service updateService(String serviceName, Map<String, Object> attributes, Boolean merge) throws ExceptionServiceBadRequest, ExceptionServiceNotFound, ExceptionServiceManagerFailure;
	
	public Boolean deleteService(String serviceName) throws ExceptionServiceBadRequest, ExceptionServiceNotFound, ExceptionServiceManagerFailure;
	
	public Boolean deleteAllService();
	
	public Boolean isValidService(String serviceName);
	
	public void deleteTable();
}