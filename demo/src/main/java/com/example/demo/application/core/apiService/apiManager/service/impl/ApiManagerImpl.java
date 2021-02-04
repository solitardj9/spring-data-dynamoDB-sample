package com.example.demo.application.core.apiService.apiManager.service.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.application.core.apiService.apiManager.service.ApiManager;
import com.example.demo.application.core.apiService.apiManager.service.dao.ApiRepository;
import com.example.demo.application.core.apiService.apiManager.service.dao.ApiTableRepository;

@Service("apiManager")
public class ApiManagerImpl implements ApiManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(ApiManagerImpl.class);
	
	@Autowired
	ApiRepository apiRepository;
	
	@Autowired
	ApiTableRepository apiTableRepository;
	
	private Boolean isInitialized = false;
	
	@PostConstruct
	public void init() {
		//
		LOG.info("[ApiManager].init : ApiManager start");
		
		if (apiTableRepository.isExistTable()) {
			LOG.info("[ApiManager].init : Table is exist? true");
		}
		else {
			LOG.info("[ApiManager].init : Table is exist? false");
			apiTableRepository.createTable();
			LOG.info("[ApiManager].init : Table is exist? " + apiTableRepository.isExistTable());
		}
		
		isInitialized = true;
	}
	
	@Override
	public Boolean isInitialized() {
		return isInitialized;
	}
}