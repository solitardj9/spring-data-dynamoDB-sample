package com.example.demo.application.core.apiService.apiManager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.application.core.apiService.apiManager.model.Api;
import com.example.demo.application.core.apiService.apiManager.model.exception.ExceptionApiAlreayExist;
import com.example.demo.application.core.apiService.apiManager.model.exception.ExceptionApiBadRequest;
import com.example.demo.application.core.apiService.apiManager.model.exception.ExceptionApiManagerFailure;
import com.example.demo.application.core.apiService.apiManager.model.exception.ExceptionApiNotFound;
import com.example.demo.application.core.apiService.apiManager.service.ApiManager;
import com.example.demo.application.core.apiService.apiManager.service.dao.ApiRepository;
import com.example.demo.application.core.apiService.apiManager.service.dao.ApiTableRepository;
import com.example.demo.application.core.apiService.apiManager.service.dao.dto.ApiDto;
import com.example.demo.util.jsonUtil.JsonUtil;

@Service("apiManager")
public class ApiManagerImpl implements ApiManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(ApiManagerImpl.class);
	
	@Autowired
	ApiRepository apiRepository;
	
	@Autowired
	ApiTableRepository apiTableRepository;
	
	private final Long DEFAULT_VERSION = 1L;
	
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

	@Override
	public Api addApi(String apiName, String apiKeyword, Map<String, Object> apiInfo, String apiLabel, String categoryLabel, String serviceLabel) throws ExceptionApiBadRequest, ExceptionApiAlreayExist, ExceptionApiManagerFailure {
		//
		String badRequestParam = null;
		if (apiName == null || apiName.isEmpty()) badRequestParam = "apiName is invalid.";
		if (apiKeyword == null || apiKeyword.isEmpty()) badRequestParam = "apiKeyword is invalid.";
		if (apiLabel == null || apiLabel.isEmpty()) badRequestParam = "apiLabel is invalid.";
		if (categoryLabel == null || categoryLabel.isEmpty()) badRequestParam = "categoryLabel is invalid.";
		if (serviceLabel == null || serviceLabel.isEmpty()) badRequestParam = "serviceLabel is invalid.";
		if (badRequestParam != null) {
			LOG.error("[ApiManager].addApi : error = " + badRequestParam);
			throw new ExceptionApiBadRequest();
		}
		
		ApiDto apiDto = getApiDtoByApiName(apiName);
		if (apiDto != null) {
			LOG.error("[ApiManager].addApi : error = api is already exist.");
			throw new ExceptionApiAlreayExist();
		}
		
		if (apiInfo == null)
			apiInfo = new HashMap<>();
		
		try {
			apiDto = new ApiDto(apiName, apiKeyword, apiLabel, categoryLabel, serviceLabel, apiInfo, DEFAULT_VERSION);
			return convertApiDtoToApi(insertApiDtoAndGetApiDto(apiDto));
		} catch (Exception e) {
			LOG.error("[ApiManager].addApi : error = " + e);
			throw new ExceptionApiManagerFailure();
		}
	}
	
	@Override
	public Api getApi(String apiName) throws ExceptionApiBadRequest, ExceptionApiNotFound {
		//
		if (apiName == null || apiName.isEmpty()) {
			LOG.error("[ApiManager].getApi : error = apiName is invalid.");
			throw new ExceptionApiBadRequest();
		}
		
		ApiDto apiDto = getApiDtoByApiName(apiName);
		if (apiDto == null) {
			LOG.error("[ApiManager].getApi : error = apiName is not exist.");
			throw new ExceptionApiNotFound();
		}
		
		return convertApiDtoToApi(apiDto);
	}
	
	@Override
	public Api getApiByApiNameAndCategoryLabelAndServiceLabel(String apiName, String categoryLabel, String serviceLabel) throws ExceptionApiBadRequest, ExceptionApiNotFound {
		//
		String badRequestParam = null;
		if (apiName == null || apiName.isEmpty()) badRequestParam = "apiName is invalid.";
		if (categoryLabel == null || categoryLabel.isEmpty()) badRequestParam = "categoryLabel is invalid.";
		if (serviceLabel == null || serviceLabel.isEmpty()) badRequestParam = "serviceLabel is invalid.";
		if (badRequestParam != null) {
			LOG.error("[ApiManager].getApiByApiNameAndCategoryLabelAndServiceLabel : error = " + badRequestParam);
			throw new ExceptionApiBadRequest();
		}
		
		ApiDto apiDto = getApiDtoByApiNameAndCategoryLabelAndServiceLabel(apiName, categoryLabel, serviceLabel);
		if (apiDto == null) {
			LOG.error("[ApiManager].getApiByApiNameAndCategoryLabelAndServiceLabel : error = api is not exist.");
			throw new ExceptionApiNotFound();
		}
		
		return convertApiDtoToApi(apiDto);
	}
	
	@Override
	public Api getApiByApiLabelAndCategoryLabelAndServiceLabel(String apiLabel, String categoryLabel, String serviceLabel) throws ExceptionApiBadRequest, ExceptionApiNotFound {
		//
		String badRequestParam = null;
		if (apiLabel == null || apiLabel.isEmpty()) badRequestParam = "apiLabel is invalid.";
		if (categoryLabel == null || categoryLabel.isEmpty()) badRequestParam = "categoryLabel is invalid.";
		if (serviceLabel == null || serviceLabel.isEmpty()) badRequestParam = "serviceLabel is invalid.";
		if (badRequestParam != null) {
			LOG.error("[ApiManager].getApiByApiLabelAndCategoryLabelAndServiceLabel : error = " + badRequestParam);
			throw new ExceptionApiBadRequest();
		}
		
		ApiDto apiDto = getApiDtoByApiLabelAndCategoryLabelAndServiceLabel(apiLabel, categoryLabel, serviceLabel);
		if (apiDto == null) {
			LOG.error("[ApiManager].getApiByApiLabelAndCategoryLabelAndServiceLabel : error = api is not exist.");
			throw new ExceptionApiNotFound();
		}
		
		return convertApiDtoToApi(apiDto);
	}
	
	@Override
	public List<Api> getAllApi() {
		//
		List<Api> apis = new ArrayList<>();
		
		List<ApiDto> apiDtos = getAllApiDto();
		if (apiDtos != null) {
			for (ApiDto apiDto : apiDtos) {
				apis.add(convertApiDtoToApi(apiDto));
			}
		}
		
		return apis;
		
	}
	
	@Override
	public List<Api> getAllApiByCategoryLabel(String categoryLabel) throws ExceptionApiBadRequest {
		//
		if (categoryLabel == null || categoryLabel.isEmpty()) {
			LOG.error("[ApiManager].getAllApiByCategoryLabel : error = categoryLabel is invalid.");
			throw new ExceptionApiBadRequest();
		}
		
		List<Api> apis = new ArrayList<>();
		
		List<ApiDto> apiDtos = getAllApiDtoByCategoryLabel(categoryLabel);
		if (apiDtos != null) {
			for (ApiDto apiDto : apiDtos) {
				apis.add(convertApiDtoToApi(apiDto));
			}
		}
		
		return apis;
	}
	
	@Override
	public List<Api> getAllApiByCategoryLabelAndServiceLabel(String categoryLabel, String serviceLabel) throws ExceptionApiBadRequest {
		//
		String badRequestParam = null;
		if (categoryLabel == null || categoryLabel.isEmpty()) badRequestParam = "categoryLabel is invalid.";
		if (serviceLabel == null || serviceLabel.isEmpty()) badRequestParam = "serviceLabel is invalid.";
		if (badRequestParam != null) {
			LOG.error("[ApiManager].getAllApiByCategoryLabelAndServiceLabel : error = " + badRequestParam);
			throw new ExceptionApiBadRequest();
		}
		
		List<Api> apis = new ArrayList<>();
		
		List<ApiDto> apiDtos = getAllApiDtoByCategoryLabelAndServiceLabel(categoryLabel, serviceLabel);
		if (apiDtos != null) {
			for (ApiDto apiDto : apiDtos) {
				apis.add(convertApiDtoToApi(apiDto));
			}
		}
		
		return apis;
	}
	
	@Override
	public List<Api> getAllApiByApiKeywordAndCategoryLabelAndServiceLabel(String apiKeyword, String categoryLabel, String serviceLabel) throws ExceptionApiBadRequest {
		//
		String badRequestParam = null;
		if (apiKeyword == null || apiKeyword.isEmpty()) badRequestParam = "apiKeyword is invalid.";
		if (categoryLabel == null || categoryLabel.isEmpty()) badRequestParam = "categoryLabel is invalid.";
		if (serviceLabel == null || serviceLabel.isEmpty()) badRequestParam = "serviceLabel is invalid.";
		if (badRequestParam != null) {
			LOG.error("[ApiManager].getAllApiByApiKeywordAndCategoryLabelAndServiceLabel : error = " + badRequestParam);
			throw new ExceptionApiBadRequest();
		}
		
		List<Api> apis = new ArrayList<>();
		
		List<ApiDto> apiDtos = getAllApiDtoByApiKeywordAndCategoryLabelAndServiceLabel(apiKeyword, categoryLabel, serviceLabel);
		if (apiDtos != null) {
			for (ApiDto apiDto : apiDtos) {
				apis.add(convertApiDtoToApi(apiDto));
			}
		}
		
		return apis;
	}
	
	
	@Override
	public Api updateApi(String apiName, Map<String, Object> apiInfo, Boolean merge) throws ExceptionApiBadRequest, ExceptionApiNotFound, ExceptionApiManagerFailure {
		//
		try {
			if (apiName == null || apiName.isEmpty()) {
				LOG.error("[ApiManager].updateApi : error = apiName is invalid.");
				throw new ExceptionApiBadRequest();
			}
			
			ApiDto apiDto = getApiDtoByApiName(apiName);
			if (apiDto == null) {
				LOG.error("[ApiManager].updateApi : error = api is not exist.");
				throw new ExceptionApiNotFound();
			}
			
			try {
				if (merge) {
					Map<String, Object> mergedApiInfo = JsonUtil.mergeJsonMap(apiDto.getApiInfo(), apiInfo);
					apiDto.setApiInfo(mergedApiInfo);
				}
				else {
					apiDto.setApiInfo(apiInfo);
				}
			} catch (Exception e) {
				LOG.error("[ApiManager].updateApi : error = apiInfo is invallid. " + e);
			}
			
			apiDto.setVersion(apiDto.getVersion() + 1L);
			
			return convertApiDtoToApi(insertOrUpdateApiDtoAndGetApiDto(apiDto));
		} catch (Exception e) {
			LOG.error("[ApiManager].updateApi : error = " + e);
			throw new ExceptionApiManagerFailure();
		}
	}
	
	@Override
	public Api updateApiKeyword(String apiName, String apiKeyword) throws ExceptionApiBadRequest, ExceptionApiAlreayExist, ExceptionApiNotFound, ExceptionApiManagerFailure {
		//
		try {
			String badRequestParam = null;
			if (apiName == null || apiName.isEmpty()) badRequestParam = "apiName is invalid.";
			if (apiKeyword == null || apiKeyword.isEmpty()) badRequestParam = "apiKeyword is invalid.";
			if (badRequestParam != null) {
				LOG.error("[ApiManager].updateApiKeyword : error = " + badRequestParam);
				throw new ExceptionApiBadRequest();
			}
			
			ApiDto apiDto = getApiDtoByApiName(apiName);
			if (apiDto == null) {
				LOG.error("[ApiManager].updateApiKeyword : error = api is not exist.");
				throw new ExceptionApiNotFound();
			}
			
			apiDto.setApiKeyword(apiKeyword);
			apiDto.setVersion(apiDto.getVersion() + 1L);
			
			return convertApiDtoToApi(insertOrUpdateApiDtoAndGetApiDto(apiDto));
		} catch (Exception e) {
			LOG.error("[ApiManager].updateApiKeyword : error = " + e);
			throw new ExceptionApiManagerFailure();
		}
	}

	@Override
	public Boolean deleteApi(String apiName) throws ExceptionApiBadRequest, ExceptionApiNotFound, ExceptionApiManagerFailure {
		//
		if (apiName == null || apiName.isEmpty()) {
			LOG.error("[ApiManager].deleteApi : error = apiName is invalid.");
			throw new ExceptionApiBadRequest();
		}
		
		ApiDto apiDto = getApiDtoByApiName(apiName);
		if (apiDto == null) {
			LOG.error("[ApiManager].deleteApi : error = api is not exist.");
			throw new ExceptionApiNotFound();
		}
		
		try {
			return deleteApiDto(apiDto);
		} catch (Exception e) {
			LOG.error("[ApiManager].deleteApi : error = " + e);
			throw new ExceptionApiManagerFailure();
		}
	}
	
	@Override
	public Boolean deleteAllApi() {
		//
		return deleteAllApiDtos();
	}
	
	@Override
	public Boolean isValidApi(String apiName) {
		//
		if (apiName == null || apiName.isEmpty())
			return false;
		
		try {
			return isExist(apiName);
		} catch(Exception e) {
			LOG.error("[ApiManager].isValidApi : error = " + e);
			return false;
		}
	}
	
	@Override
	public void deleteTable() {
		//
		apiTableRepository.deleteTable();
	}

	private ApiDto getApiDtoByApiName(String apiName) {
		//
		try {
			Optional<ApiDto> apiDto = apiRepository.findById(apiName);
			if (apiDto.isPresent())
				return apiDto.get();
			else
				return null;
		} catch(Exception e) {
			LOG.error("[ApiManager].getApiDtoByApiName : error = " + e);
			return null;
		}
	}
	
	private ApiDto getApiDtoByApiNameAndCategoryLabelAndServiceLabel(String apiName, String categoryLabel, String serviceLabel) {
		//
		try {
			List<ApiDto> apiDtos = apiRepository.findByApiNameAndCategoryLabelAndServiceLabel(apiName, categoryLabel, serviceLabel);
			if (apiDtos != null && !apiDtos.isEmpty())
				return apiDtos.get(0);
			else
				return null;
		} catch(Exception e) {
			LOG.error("[ApiManager].getAllApiDtoByApiNameAndCategoryLabelAndServiceLabel : error = " + e);
			return null;
		}
	}
	
	private ApiDto getApiDtoByApiLabelAndCategoryLabelAndServiceLabel(String apiLabel, String categoryLabel, String serviceLabel) {
		//
		try {
			List<ApiDto> apiDtos = apiRepository.findByApiLabelAndCategoryLabelAndServiceLabel(apiLabel, categoryLabel, serviceLabel);
			if (apiDtos != null && !apiDtos.isEmpty())
				return apiDtos.get(0);
			else
				return null;
		} catch(Exception e) {
			LOG.error("[ApiManager].getApiDtoByApiLabelAndCategoryLabelAndServiceLabel : error = " + e);
			return null;
		}
	}
	
	private List<ApiDto> getAllApiDto() {
		//
		try {
			List<ApiDto> apiDtos = apiRepository.findAll();
			return apiDtos;
		} catch(Exception e) {
			LOG.error("[ApiManager].getAllApiDto : error = " + e);
			return null;
		}
	}
	
	private List<ApiDto> getAllApiDtoByCategoryLabel(String categoryLabel) {
		//
		try {
			return apiRepository.findByCategoryLabel(categoryLabel);
		} catch(Exception e) {
			LOG.error("[ApiManager].getAllApiDtoByCategoryLabel : error = " + e);
			return null;
		}
	}
	
	private List<ApiDto> getAllApiDtoByCategoryLabelAndServiceLabel(String categoryLabel, String serviceLabel) {
		//
		try {
			return apiRepository.findByCategoryLabelAndServiceLabel(categoryLabel, serviceLabel);
		} catch(Exception e) {
			LOG.error("[ApiManager].getAllApiDtoByCategoryLabelAndServiceLabel : error = " + e);
			return null;
		}
	}
	
	private List<ApiDto> getAllApiDtoByApiKeywordAndCategoryLabelAndServiceLabel(String apiKeyword, String categoryLabel, String serviceLabel) {
		//
		try {
			return apiRepository.findByApiKeywordAndCategoryLabelAndServiceLabel(apiKeyword, categoryLabel, serviceLabel);
		} catch(Exception e) {
			LOG.error("[ApiManager].getAllApiDtoByApiKeywordAndCategoryLabelAndServiceLabel : error = " + e);
			return null;
		}
	}
	
	private Boolean insertApiDto(ApiDto apiDto) {
		//
		try {
			apiTableRepository.putItemByPartitionKeyCondition(apiDto);
			return true;
		} catch(Exception e) {
			LOG.error("[ApiManager].insertApiDto : error = " + e);
			return false;
		}
	}
	
	private ApiDto insertApiDtoAndGetApiDto(ApiDto apiDto) {
		//
		try {
			insertApiDto(apiDto);
			return getApiDtoByApiName(apiDto.getApiName());
		} catch(Exception e) {
			LOG.error("[ApiManager].insertApiDtoAndGetApiDto : error = " + e);
			return null;
		}
	}

	private Boolean insertOrUpdateApiDto(ApiDto apiDto) {
		//
		try {
			apiRepository.save(apiDto);
			return true;
		} catch(Exception e) {
			LOG.error("[ApiManager].insertOrUpdateApiDto : error = " + e);
			return false;
		}
	}
	
	private ApiDto insertOrUpdateApiDtoAndGetApiDto(ApiDto apiDto) {
		//
		try {
			insertOrUpdateApiDto(apiDto);
			return getApiDtoByApiName(apiDto.getApiName());
		} catch(Exception e) {
			LOG.error("[ApiManager].insertOrUpdateApiDtoAndGetApiDto : error = " + e);
			return null;
		}
	}

	private Boolean deleteApiDto(ApiDto apiDto) {
		//
		try {
			apiRepository.delete(apiDto);
			return true;
		} catch(Exception e) {
			LOG.error("[ApiManager].deleteApiDto : error = " + e);
			return false;
		}
	}
	
	private Boolean deleteAllApiDtos() {
		//
		try {
			apiRepository.deleteAll();
			return true;
		} catch(Exception e) {
			LOG.error("[ApiManager].deleteAllApiDtos : error = " + e);
			return false;
		}
	}
	
	private Boolean isExist(String apiName) {
		//
		try {
			return apiRepository.existsById(apiName);
		} catch(Exception e) {
			LOG.error("[ApiManager].isExist : error = " + e);
			return false;
		}
	}
	
	private Api convertApiDtoToApi(ApiDto apiDto) {
		//
		if (apiDto != null)
			return new Api(apiDto.getApiName(), apiDto.getApiKeyword(), apiDto.getApiLabel(), apiDto.getCategoryLabel(), apiDto.getServiceLabel(), apiDto.getApiInfo(), apiDto.getVersion());
		return null;
	}
}