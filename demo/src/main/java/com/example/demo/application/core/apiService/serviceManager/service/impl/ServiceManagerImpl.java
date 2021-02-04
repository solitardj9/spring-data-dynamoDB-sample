package com.example.demo.application.core.apiService.serviceManager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.application.core.apiService.serviceManager.model.exception.ExceptionServiceAlreayExist;
import com.example.demo.application.core.apiService.serviceManager.model.exception.ExceptionServiceBadRequest;
import com.example.demo.application.core.apiService.serviceManager.model.exception.ExceptionServiceManagerFailure;
import com.example.demo.application.core.apiService.serviceManager.model.exception.ExceptionServiceNotFound;
import com.example.demo.application.core.apiService.serviceManager.service.ServiceManager;
import com.example.demo.application.core.apiService.serviceManager.service.dao.ServiceRepository;
import com.example.demo.application.core.apiService.serviceManager.service.dao.ServiceTableRepository;
import com.example.demo.application.core.apiService.serviceManager.service.dao.dto.ServiceDto;
import com.example.demo.util.jsonUtil.JsonUtil;

@Service("serviceManager")
public class ServiceManagerImpl implements ServiceManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(ServiceManagerImpl.class);
	
	@Autowired
	ServiceRepository serviceRepository;
	
	@Autowired
	ServiceTableRepository serviceTableRepository;
	
	private final Long DEFAULT_VERSION = 1L;
	
	private Boolean isInitialized = false;
	
	@PostConstruct
	public void init() {
		//
		LOG.info("[ServiceManager].init : ServiceManager start");
		
		if (serviceTableRepository.isExistTable()) {
			LOG.info("[ServiceManager].init : Table is exist? true");
		}
		else {
			LOG.info("[ServiceManager].init : Table is exist? false");
			serviceTableRepository.createTable();
			LOG.info("[ServiceManager].init : Table is exist? " + serviceTableRepository.isExistTable());
		}
		
		isInitialized = true;
	}
	
	@Override
	public Boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public com.example.demo.application.core.apiService.serviceManager.model.Service addService(String serviceName, Map<String, Object> attributes, String serviceLabel, String categoryLabel) throws ExceptionServiceBadRequest, ExceptionServiceAlreayExist, ExceptionServiceManagerFailure {
		//
		if (serviceName == null || serviceName.isEmpty()) {
			LOG.error("[ServiceManager].addService : error = serviceName is invalid.");
			throw new ExceptionServiceBadRequest();
		}
		
		if (serviceLabel == null || serviceLabel.isEmpty()) {
			LOG.error("[ServiceManager].addService : error = serviceLabel is invalid.");
			throw new ExceptionServiceBadRequest();
		}
		
		if (categoryLabel == null || categoryLabel.isEmpty()) {
			LOG.error("[ServiceManager].addService : error = categoryLabel is invalid.");
			throw new ExceptionServiceBadRequest();
		}
		
		ServiceDto serviceDto = getServiceDtoByServiceName(serviceName);
		if (serviceDto != null) {
			LOG.error("[ServiceManager].addService : error = service is already exist.");
			throw new ExceptionServiceAlreayExist();
		}
		
		if (attributes == null)
			attributes = new HashMap<>();
		
		try {
			String serviceId = UUID.randomUUID().toString();
			serviceDto = new ServiceDto(serviceId, serviceName, serviceLabel, categoryLabel, attributes, DEFAULT_VERSION);
			return convertServiceDtoToService(saveServiceDtoAndGetServiceDto(serviceDto));
		} catch (Exception e) {
			LOG.error("[ServiceManager].addService : error = " + e);
			throw new ExceptionServiceManagerFailure();
		}
	}
	
	@Override
	public com.example.demo.application.core.apiService.serviceManager.model.Service getService(String serviceName) throws ExceptionServiceBadRequest, ExceptionServiceNotFound {
		//
		if (serviceName == null || serviceName.isEmpty()) {
			LOG.error("[ServiceManager].getService : error = serviceName is invalid.");
			throw new ExceptionServiceBadRequest();
		}
		
		ServiceDto serviceDto = getServiceDtoByServiceName(serviceName);
		if (serviceDto == null) {
			LOG.error("[ServiceManager].getService : error = service is not exist.");
			throw new ExceptionServiceNotFound();
		}
		
		return convertServiceDtoToService(serviceDto);
	}
	
	@Override
	public com.example.demo.application.core.apiService.serviceManager.model.Service getServiceByServiceNameAndCategoryLabel(String serviceName, String categoryLabel) throws ExceptionServiceBadRequest, ExceptionServiceNotFound {
		// 
		if (serviceName == null || serviceName.isEmpty()) {
			LOG.error("[ServiceManager].getServiceByServiceNameAndCategoryLabel : error = serviceName is invalid.");
			throw new ExceptionServiceBadRequest();
		}
		
		if (categoryLabel == null || categoryLabel.isEmpty()) {
			LOG.error("[ServiceManager].getServiceByServiceNameAndCategoryLabel : error = categoryLabel is invalid.");
			throw new ExceptionServiceBadRequest();
		}
		
		ServiceDto serviceDto = getAllServiceDtoByServiceNameAndCategoryLabel(serviceName, categoryLabel);
		if (serviceDto == null) {
			LOG.error("[ServiceManager].getServiceByServiceNameAndCategoryLabel : error = service is not exist.");
			throw new ExceptionServiceNotFound();
		}
		
		return convertServiceDtoToService(serviceDto);
	}
	
	@Override
	public com.example.demo.application.core.apiService.serviceManager.model.Service getServiceByServiceLabelAndCategoryLabel(String serviceLabel, String categoryLabel) throws ExceptionServiceBadRequest, ExceptionServiceNotFound {
		//
		if (serviceLabel == null || serviceLabel.isEmpty()) {
			LOG.error("[ServiceManager].getServiceByServiceLabelAndCategoryLabel : error = serviceLabel is invalid.");
			throw new ExceptionServiceBadRequest();
		}
		
		if (categoryLabel == null || categoryLabel.isEmpty()) {
			LOG.error("[ServiceManager].getServiceByServiceLabelAndCategoryLabel : error = categoryLabel is invalid.");
			throw new ExceptionServiceBadRequest();
		}
		
		ServiceDto serviceDto = getAllServiceDtoByServiceLabelAndCategoryLabel(serviceLabel, categoryLabel);
		if (serviceDto == null) {
			LOG.error("[ServiceManager].getServiceByServiceLabelAndCategoryLabel : error = service is not exist.");
			throw new ExceptionServiceNotFound();
		}
		
		return convertServiceDtoToService(serviceDto);
	}
	
	@Override
	public List<com.example.demo.application.core.apiService.serviceManager.model.Service> getAllService() {
		//
		List<com.example.demo.application.core.apiService.serviceManager.model.Service> services = new ArrayList<>();
		
		List<ServiceDto> serviceDtos = getAllServiceDto();
		if (serviceDtos != null) {
			for (ServiceDto serviceDto : serviceDtos) {
				services.add(convertServiceDtoToService(serviceDto));
			}
		}
		
		return services;
	}
	
	@Override
	public List<com.example.demo.application.core.apiService.serviceManager.model.Service> getAllServiceByCategoryLabel(String categoryLabel) throws ExceptionServiceBadRequest {
		//
		List<com.example.demo.application.core.apiService.serviceManager.model.Service> services = new ArrayList<>();
		
		List<ServiceDto> serviceDtos = getAllServiceDtoByCategoryLabel(categoryLabel);
		if (serviceDtos != null) {
			for (ServiceDto serviceDto : serviceDtos) {
				services.add(convertServiceDtoToService(serviceDto));
			}
		}
		
		return services;
	}

	@Override
	public com.example.demo.application.core.apiService.serviceManager.model.Service updateService(String serviceName, Map<String, Object> attributes, Boolean merge) throws ExceptionServiceBadRequest, ExceptionServiceNotFound, ExceptionServiceManagerFailure {
		//
		try {
			if (serviceName == null || serviceName.isEmpty()) {
				LOG.error("[ServiceManager].updateService : error = serviceName is invalid.");
				throw new ExceptionServiceBadRequest();
			}
			
			ServiceDto serviceDto = getServiceDtoByServiceName(serviceName);
			if (serviceDto == null) {
				LOG.error("[ServiceManager].updateService : error = service is not exist.");
				throw new ExceptionServiceNotFound();
			}
			
			try {
				if (merge) {
					Map<String, Object> mergedAttributes = JsonUtil.mergeJsonMap(serviceDto.getAttributes(), attributes);
					serviceDto.setAttributes(mergedAttributes);
				}
				else {
					serviceDto.setAttributes(attributes);
				}
			} catch (Exception e) {
				LOG.error("[ServiceManager].updateService : error = attributes is invallid. " + e);
			}
			
			serviceDto.setVersion(serviceDto.getVersion() + 1L);
			
			return convertServiceDtoToService(saveServiceDtoAndGetServiceDto(serviceDto));
		} catch (Exception e) {
			LOG.error("[ServiceManager].updateService : error = " + e);
			throw new ExceptionServiceManagerFailure();
		}
	}
		
	@Override
	public com.example.demo.application.core.apiService.serviceManager.model.Service updateServiceName(String serviceId, String serviceName) throws ExceptionServiceBadRequest, ExceptionServiceNotFound, ExceptionServiceManagerFailure {
		//
		try {
			if (serviceId == null || serviceId.isEmpty()) {
				LOG.error("[ServiceManager].updateServiceName : error = serviceId is invalid.");
				throw new ExceptionServiceBadRequest();
			}
			
			if (serviceName == null || serviceName.isEmpty()) {
				LOG.error("[ServiceManager].updateServiceName : error = serviceName is invalid.");
				throw new ExceptionServiceBadRequest();
			}
			
			ServiceDto serviceDto = getServiceDtoByServiceId(serviceId);
			if (serviceDto == null) {
				LOG.error("[ServiceManager].updateServiceName : error = service is not exist.");
				throw new ExceptionServiceNotFound();
			}
			
			serviceDto.setServiceName(serviceName);
			serviceDto.setVersion(serviceDto.getVersion() + 1L);
			
			return convertServiceDtoToService(saveServiceDtoAndGetServiceDto(serviceDto));
		} catch (Exception e) {
			LOG.error("[ServiceManager].updateServiceName : error = " + e);
			throw new ExceptionServiceManagerFailure();
		}
	}
	
	@Override
	public Boolean deleteService(String serviceId) throws ExceptionServiceBadRequest, ExceptionServiceNotFound, ExceptionServiceManagerFailure {
		//
		if (serviceId == null || serviceId.isEmpty()) {
			LOG.error("[ServiceManager].deleteService : error = serviceId is invalid.");
			throw new ExceptionServiceBadRequest();
		}
		
		ServiceDto serviceDto = getServiceDtoByServiceId(serviceId);
		if (serviceDto == null) {
			LOG.error("[ServiceManager].deleteService : error = service is not exist.");
			throw new ExceptionServiceNotFound();
		}
		
		try {
			return deleteServiceDto(serviceDto);
		} catch (Exception e) {
			LOG.error("[ServiceManager].deleteService : error = " + e);
			throw new ExceptionServiceManagerFailure();
		}
	}
	
	@Override
	public Boolean deleteAllService() {
		//
		return deleteAllServiceDtos();
	}
	
	@Override
	public Boolean isValidService(String serviceName) {
		//
		if (serviceName == null || serviceName.isEmpty())
			return false;
		
		try {
			return isExist(serviceName);
		} catch(Exception e) {
			LOG.error("[ServiceManager].isValidService : error = " + e);
			return false;
		}
	}
	
	
	
	
	private ServiceDto getServiceDtoByServiceName(String serviceName) {
		//
		try {
			List<ServiceDto> serviceDtos = serviceRepository.findByServiceName(serviceName);
			if (serviceDtos != null && !serviceDtos.isEmpty())
				return serviceDtos.get(0);
			else
				return null;
		} catch(Exception e) {
			LOG.error("[ServiceManager].getServiceDtoByServiceName : error = " + e);
			return null;
		}
	}
	
	private ServiceDto getServiceDtoByServiceId(String serviceId) {
		//
		try {
			Optional<ServiceDto> serviceDto = serviceRepository.findById(serviceId);
			if (serviceDto.isPresent())
				return serviceDto.get();
			else
				return null;
		} catch(Exception e) {
			LOG.error("[ServiceManager].getServiceDtoByServiceId : error = " + e);
			return null;
		}
	}
	
	private ServiceDto getAllServiceDtoByServiceNameAndCategoryLabel(String serviceName, String categoryLabel) {
		//
		try {
			List<ServiceDto> serviceDtos = serviceRepository.findByServiceNameAndCategoryLabel(serviceName, categoryLabel);
			if (serviceDtos != null && !serviceDtos.isEmpty())
				return serviceDtos.get(0);
			else
				return null;
		} catch(Exception e) {
			LOG.error("[ServiceManager].getAllServiceDtoByServiceNameAndCategoryLabel : error = " + e);
			return null;
		}
	}
	
	private ServiceDto getAllServiceDtoByServiceLabelAndCategoryLabel(String serviceLabel, String categoryLabel) {
		//
		try {
			List<ServiceDto> serviceDtos = serviceRepository.findByServiceLabelAndCategoryLabel(serviceLabel, categoryLabel);
			if (serviceDtos != null && !serviceDtos.isEmpty())
				return serviceDtos.get(0);
			else
				return null;
		} catch(Exception e) {
			LOG.error("[ServiceManager].getAllServiceDtoByServiceLabelAndCategoryLabel : error = " + e);
			return null;
		}
	}
	
	private List<ServiceDto> getAllServiceDto() {
		//
		try {
			List<ServiceDto> serviceDtos = serviceRepository.findAll();
			return serviceDtos;
		} catch(Exception e) {
			LOG.error("[ServiceManager].getAllServiceDto : error = " + e);
			return null;
		}
	}
	
	private List<ServiceDto> getAllServiceDtoByCategoryLabel(String categoryLabel) {
		//
		try {
			return serviceRepository.findByCategoryLabel(categoryLabel);
		} catch(Exception e) {
			LOG.error("[ServiceManager].getAllServiceDtoByCategoryLabel : error = " + e);
			return null;
		}
	}
	
	private Boolean saveServiceDto(ServiceDto serviceDto) {
		//
		try {
			serviceRepository.save(serviceDto);
			return true;
		} catch(Exception e) {
			LOG.error("[ServiceManager].saveServiceDto : error = " + e);
			return false;
		}
	}
	
	private ServiceDto saveServiceDtoAndGetServiceDto(ServiceDto serviceDto) {
		//
		try {
			saveServiceDto(serviceDto);
			return getServiceDtoByServiceId(serviceDto.getServiceId());
		} catch(Exception e) {
			LOG.error("[ServiceManager].saveServiceDtoAndGetServiceDto : error = " + e);
			return null;
		}
	}
	
	private Boolean deleteServiceDto(ServiceDto serviceDto) {
		//
		try {
			serviceRepository.delete(serviceDto);
			return true;
		} catch(Exception e) {
			LOG.error("[ServiceManager].deleteServiceDto : error = " + e);
			return false;
		}
	}
	
	private Boolean deleteAllServiceDtos() {
		//
		try {
			serviceRepository.deleteAll();
			return true;
		} catch(Exception e) {
			LOG.error("[ServiceManager].deleteAllServiceDtos : error = " + e);
			return false;
		}
	}
	
	private Boolean isExist(String serviceName) {
		//
		try {
			return serviceRepository.existsByServiceName(serviceName);
		} catch(Exception e) {
			LOG.error("[ServiceManager].isExist : error = " + e);
			return false;
		}
	}

	private com.example.demo.application.core.apiService.serviceManager.model.Service convertServiceDtoToService(ServiceDto serviceDto) {
		//
		if (serviceDto != null)
			return new com.example.demo.application.core.apiService.serviceManager.model.Service(serviceDto.getServiceId(), serviceDto.getServiceName(), serviceDto.getServiceLabel(), serviceDto.getCategoryLabel(), serviceDto.getAttributes(), serviceDto.getVersion());
		return null;
	}
}