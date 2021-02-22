package com.example.demo.application.core.apiService.categoryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.application.core.apiService.categoryManager.model.Category;
import com.example.demo.application.core.apiService.categoryManager.model.ProcessSideEnum;
import com.example.demo.application.core.apiService.categoryManager.service.CategoryManager;

@SpringBootTest
public class CategoryManagerTest {

	@Autowired
	CategoryManager categoryManager;
	
	@Test
	void testCategoryManager() {
		
		String categoryName = "";
		String categoryLabel = "";
		ProcessSideEnum processSideOut = ProcessSideEnum.OUTSIDE;
		ProcessSideEnum processSideIn = ProcessSideEnum.INSIDE;
		Category category = null;
		List<Category> categories = null;
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
			System.out.println("1) addCategory");
			categoryName = "Weather";
			categoryLabel = "ee2cea9f-2d48-48ca-b059-4feb2c38fc96";
			categoryManager.addCategory(categoryName, attributes, categoryLabel, processSideOut);
		
			System.out.println("2) addCategory");
			categoryName = "Map";
			categoryLabel = "1d0fd231-3051-4362-872b-48a0fff99564";
			categoryManager.addCategory(categoryName, attributes, categoryLabel, processSideOut);
			
			System.out.println("3) addCategory");
			categoryName = "Auth";
			categoryLabel = "td0fd231-3051-4362-872b-48a0fff9956t";
			categoryManager.addCategory(categoryName, attributes, categoryLabel, processSideIn);
			
			System.out.println("4) addCategory");
			categoryName = "Home";
			categoryLabel = "te2cea9f-3051-4362-872b-4feb2c38fc9t";
			categoryManager.addCategory(categoryName, attributes, categoryLabel, processSideIn);
			
			System.out.println("5) getAllCategory = " + categoryManager.getAllCategory().toString());
			
			System.out.println("6) updateCategory");
			categoryManager.updateCategory("Weather", attributes2, true);
			
			System.out.println("7) isValidCategory");
			System.out.println("is valid = " + categoryManager.isValidCategory("Map"));
			
			System.out.println("8) getAllCategory = " + categoryManager.getAllCategory().toString());
			
			System.out.println("9) getCategory");
			category = categoryManager.getCategory("Weather");
			System.out.println("category = " + category.toString());
			
			System.out.println("10) getAllCategoryByProcessSide");
			categories =  categoryManager.getAllCategoryByProcessSide(ProcessSideEnum.INSIDE);
			System.out.println("category inside = " + categories.toString());
			categories =  categoryManager.getAllCategoryByProcessSide(ProcessSideEnum.OUTSIDE);
			System.out.println("category outside = " + categories.toString());
			
			System.out.println("11) deleteCategory");
			System.out.println(categoryManager.deleteCategory(category.getCategoryName()));
			
			System.out.println("12) getAllCategory = " + categoryManager.getAllCategory().toString());
			
			System.out.println("13) deleteAllCategoryByProcessSide");
			System.out.println(categoryManager.deleteAllCategoryByProcessSide(ProcessSideEnum.INSIDE));
			
			System.out.println("14) getAllCategory = " + categoryManager.getAllCategory().toString());
			
			System.out.println("15) deleteAllCategory");
			System.out.println(categoryManager.deleteAllCategory());
			
			System.out.println("16) getAllCategory = " + categoryManager.getAllCategory().toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}