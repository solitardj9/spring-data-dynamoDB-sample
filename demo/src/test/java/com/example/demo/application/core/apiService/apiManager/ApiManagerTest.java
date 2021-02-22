package com.example.demo.application.core.apiService.apiManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.application.core.apiService.apiManager.model.Api;
import com.example.demo.application.core.apiService.apiManager.service.ApiManager;

@SpringBootTest
public class ApiManagerTest {

	@Autowired
	ApiManager apiManager;
	
	@Test
	void testApiManager() {
		
		String apiName = "";
		String apiKeyword = "";
		String apiLabel = "";
		String categoryLabel = "";
		String serviceLabel = "";
		
		Api api = null;
		List<Api> apis = null;
		
		Map<String, Object> apiInfo = new HashMap<>();
		apiInfo.put("key1", "value1");
		apiInfo.put("key2", 2);
		
		Map<String, Object> apiInfo2 = new HashMap<>();
		Map<String, Object> subAttributes = new HashMap<>();
		apiInfo2.put("key1", "value2");
		apiInfo2.put("key2", 3);
		subAttributes.put("key1-1", 11);
		subAttributes.put("key1-2", 12);
		apiInfo2.put("key3", subAttributes);
		
		try {
			System.out.println("1) addApi");
			apiName = "AccuWeatherApi_1";
			apiKeyword = "AccuWeatherApi_key";
			apiLabel = "1234_1";
			serviceLabel = "1234";
			categoryLabel = "ee2cea9f-2d48-48ca-b059-4feb2c38fc96";
			apiManager.addApi(apiName, apiKeyword, apiInfo, apiLabel, categoryLabel, serviceLabel);
			
//			System.out.println("1-1) addApi");
//			apiName = "AccuWeatherApi_1";
//			apiKeyword = "AccuWeatherApi_key";
//			apiLabel = "1234_1";
//			serviceLabel = "1234";
//			categoryLabel = "ee2cea9f-2d48-48ca-b059-4feb2c38fc96";
//			apiManager.addApi(apiName, apiKeyword, apiInfo, apiLabel, categoryLabel, serviceLabel);
			
			System.out.println("2) addApi");
			apiName = "AccuWeatherApi_2";
			apiKeyword = "AccuWeatherApi_key";
			apiLabel = "1234_2";
			serviceLabel = "1234";
			categoryLabel = "ee2cea9f-2d48-48ca-b059-4feb2c38fc96";
			apiManager.addApi(apiName, apiKeyword, apiInfo, apiLabel, categoryLabel, serviceLabel);
			
			System.out.println("3) addApi");
			apiName = "KorWeatherApi_1";
			apiKeyword = "KorWeatherApi_key_1";
			apiLabel = "5678_1";
			serviceLabel = "5678";
			categoryLabel = "ee2cea9f-2d48-48ca-b059-4feb2c38fc96";
			apiManager.addApi(apiName, apiKeyword, apiInfo, apiLabel, categoryLabel, serviceLabel);
			
			System.out.println("4) addApi");
			apiName = "KorWeatherApi_2";
			apiKeyword = "KorWeatherApi_key_2";
			apiLabel = "5678_2";
			serviceLabel = "5678";
			categoryLabel = "ee2cea9f-2d48-48ca-b059-4feb2c38fc96";
			apiManager.addApi(apiName, apiKeyword, apiInfo, apiLabel, categoryLabel, serviceLabel);
			
			System.out.println("5) addApi");
			apiName = "NaverMapApi_1";
			apiKeyword = "NaverMapApi_key_1";
			apiLabel = "4321_1";
			serviceLabel = "4321";
			categoryLabel = "1d0fd231-3051-4362-872b-48a0fff99564";
			apiManager.addApi(apiName, apiKeyword, apiInfo, apiLabel, categoryLabel, serviceLabel);
			
			System.out.println("6) addApi");
			apiName = "NaverMapApi_2";
			apiKeyword = "NaverMapApi_key_2";
			apiLabel = "4321_2";
			serviceLabel = "4321";
			categoryLabel = "1d0fd231-3051-4362-872b-48a0fff99564";
			apiManager.addApi(apiName, apiKeyword, apiInfo, apiLabel, categoryLabel, serviceLabel);
			
			System.out.println("7) addApi");
			apiName = "DaumMapApi_1";
			apiKeyword = "DaumMapApi_key_1";
			apiLabel = "9876_1";
			serviceLabel = "9876";
			categoryLabel = "1d0fd231-3051-4362-872b-48a0fff99564";
			apiManager.addApi(apiName, apiKeyword, apiInfo, apiLabel, categoryLabel, serviceLabel);
			
			System.out.println("8) addApi");
			apiName = "DaumMapApi_2";
			apiKeyword = "DaumMapApi_key_2";
			apiLabel = "9876_2";
			serviceLabel = "9876";
			categoryLabel = "1d0fd231-3051-4362-872b-48a0fff99564";
			apiManager.addApi(apiName, apiKeyword, apiInfo, apiLabel, categoryLabel, serviceLabel);
			
			System.out.println("9) addApi");
			apiName = "MyAuthApi_1";
			apiKeyword = "MyAuthApi_key_1";
			apiLabel = "9999_1";
			serviceLabel = "9999";
			categoryLabel = "td0fd231-3051-4362-872b-48a0fff9956t";
			apiManager.addApi(apiName, apiKeyword, apiInfo, apiLabel, categoryLabel, serviceLabel);
			
			System.out.println("10) addApi");
			apiName = "MyHomeApi_1";
			apiKeyword = "MyHomeApi_key_1";
			apiLabel = "8888_1";
			serviceLabel = "8888";
			categoryLabel = "te2cea9f-3051-4362-872b-4feb2c38fc9t";
			apiManager.addApi(apiName, apiKeyword, apiInfo, apiLabel, categoryLabel, serviceLabel);
			
			System.out.println("11) getAllApi = " + apiManager.getAllApi().toString());
			
			System.out.println("12) updateApi");
			apiManager.updateApi("MyHomeApi_1", apiInfo, true);
			
			System.out.println("13) updateApi with keyword");
			apiManager.updateApiKeyword("MyHomeApi_1", "MyHomeApi_1_v11");
			
			System.out.println("14) isValidApi");
			System.out.println("is valid = " + apiManager.isValidApi("MyAuthApi_1"));
			
			System.out.println("15) getAllApi = " + apiManager.getAllApi().toString());
			
			System.out.println("16) getAllApiByCategoryLabel = " + apiManager.getAllApiByCategoryLabel("ee2cea9f-2d48-48ca-b059-4feb2c38fc96").toString());
			
			System.out.println("17) getAllApiByCategoryLabelAndServiceLabel = " + apiManager.getAllApiByCategoryLabelAndServiceLabel("ee2cea9f-2d48-48ca-b059-4feb2c38fc96", "1234").toString());
			
			System.out.println("18) getAllApiByApiKeywordAndCategoryLabelAndServiceLabel = " + apiManager.getAllApiByApiKeywordAndCategoryLabelAndServiceLabel("AccuWeatherApi_key", "ee2cea9f-2d48-48ca-b059-4feb2c38fc96", "1234").toString());
			
			System.out.println("19) deleteApi");
			System.out.println(apiManager.deleteApi("MyHomeApi_1"));
			
			System.out.println("20) getAllApi = " + apiManager.getAllApi().toString());
			
			System.out.println("21) deleteAllApi");
			System.out.println(apiManager.deleteAllApi());
			
			System.out.println("22) getAllApi = " + apiManager.getAllApi().toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}