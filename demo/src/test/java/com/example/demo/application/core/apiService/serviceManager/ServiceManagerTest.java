package com.example.demo.application.core.apiService.serviceManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.application.core.apiService.serviceManager.model.Service;
import com.example.demo.application.core.apiService.serviceManager.service.ServiceManager;

@SpringBootTest
class ServiceManagerTest {

	@Autowired
	ServiceManager serviceManager;
	
	@Test
	void testCategoryManager() {
		
		String serviceName = "";
		String serviceLabel = "";
		String categoryLabel = "";
		Service service = null;
		List<Service> services = null;
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("key1", "value1");
		attributes.put("key2", 2);
		
		Map<String, Object> attributes2 = new HashMap<>();
		Map<String, Object> subAttributes = new HashMap<>();
		attributes2.put("key1", "value2");
		attributes2.put("key2", 3);
		subAttributes.put("key1-1", 11);
		subAttributes.put("key1-2", 12);
		attributes2.put("key3", subAttributes);
		
		try {
			System.out.println("1) addService");
			serviceName = "AccuWeather";
			serviceLabel = "1234";
			categoryLabel = "ee2cea9f-2d48-48ca-b059-4feb2c38fc96";
			serviceManager.addService(serviceName, subAttributes, serviceLabel, categoryLabel);
			
			System.out.println("2) addService");
			serviceName = "KorWeather";
			serviceLabel = "5678";
			categoryLabel = "ee2cea9f-2d48-48ca-b059-4feb2c38fc96";
			serviceManager.addService(serviceName, subAttributes, serviceLabel, categoryLabel);
		
			System.out.println("3) addService");
			serviceName = "NaverMap";
			serviceLabel = "4321";
			categoryLabel = "1d0fd231-3051-4362-872b-48a0fff99564";
			serviceManager.addService(serviceName, subAttributes, serviceLabel, categoryLabel);
			
			System.out.println("4) addService");
			serviceName = "DaumMap";
			serviceLabel = "9876";
			categoryLabel = "1d0fd231-3051-4362-872b-48a0fff99564";
			serviceManager.addService(serviceName, subAttributes, serviceLabel, categoryLabel);
			
			System.out.println("5) addService");
			serviceName = "MyAuth";
			serviceLabel = "9999";
			categoryLabel = "td0fd231-3051-4362-872b-48a0fff9956t";
			serviceManager.addService(serviceName, subAttributes, serviceLabel, categoryLabel);
			
			System.out.println("6) addCategory");
			serviceName = "MyHome";
			serviceLabel = "8888";
			categoryLabel = "te2cea9f-3051-4362-872b-4feb2c38fc9t";
			serviceManager.addService(serviceName, subAttributes, serviceLabel, categoryLabel);
			
			System.out.println("7) getAllService = " + serviceManager.getAllService().toString());
			
			System.out.println("8) updateService");
			serviceManager.updateService("AccuWeather", attributes2, true);
			
			System.out.println("9) isValidService");
			System.out.println("is valid = " + serviceManager.isValidService("DaumMap"));
			
			System.out.println("10) getAllService = " + serviceManager.getAllService().toString());
			
			System.out.println("11) getService");
			service = serviceManager.getService("MyAuth");
			System.out.println("service = " + service.toString());
			
			System.out.println("12) getAllServiceByCategoryLabel");
			services = serviceManager.getAllServiceByCategoryLabel("ee2cea9f-2d48-48ca-b059-4feb2c38fc96");
			System.out.println(services.toString());
			
			System.out.println("13) deleteService");
			System.out.println(serviceManager.deleteService(service.getServiceName()));
			
			System.out.println("14) getAllService = " + serviceManager.getAllService().toString());
			
			System.out.println("15) deleteAllService");
			System.out.println(serviceManager.deleteAllService());
			
			System.out.println("16) getAllService = " + serviceManager.getAllService().toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}