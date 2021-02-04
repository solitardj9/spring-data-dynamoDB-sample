package com.example.demo.application.core.apiService.categoryManager.service.impl;

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

import com.example.demo.application.core.apiService.categoryManager.model.Category;
import com.example.demo.application.core.apiService.categoryManager.model.ProcessSideEnum;
import com.example.demo.application.core.apiService.categoryManager.model.exception.ExceptionCategoryAlreayExist;
import com.example.demo.application.core.apiService.categoryManager.model.exception.ExceptionCategoryBadRequest;
import com.example.demo.application.core.apiService.categoryManager.model.exception.ExceptionCategoryManagerFailure;
import com.example.demo.application.core.apiService.categoryManager.model.exception.ExceptionCategoryNotFound;
import com.example.demo.application.core.apiService.categoryManager.service.CategoryManager;
import com.example.demo.application.core.apiService.categoryManager.service.dao.CategoryRepository;
import com.example.demo.application.core.apiService.categoryManager.service.dao.CategoryTableRepository;
import com.example.demo.application.core.apiService.categoryManager.service.dao.dto.CategoryDto;
import com.example.demo.util.jsonUtil.JsonUtil;

@Service("categoryManager")
public class CategoryManagerImpl implements CategoryManager {

	private static final Logger LOG = LoggerFactory.getLogger(CategoryManagerImpl.class);
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	CategoryTableRepository categoryTableRepository;
	
	private final Long DEFAULT_VERSION = 1L;
	
	private Boolean isInitialized = false;
	
	@PostConstruct
	public void init() {
		//
		LOG.info("[CategoryManager].init : CategoryManager start");
		
		if (categoryTableRepository.isExistTable()) {
			LOG.info("[CategoryManager].init : Table is exist? true");
		}
		else {
			LOG.info("[CategoryManager].init : Table is exist? false");
			categoryTableRepository.createTable();
			LOG.info("[CategoryManager].init : Table is exist? " + categoryTableRepository.isExistTable());
		}
		
		isInitialized = true;
	}
	
	@Override
	public Boolean isInitialized() {
		return isInitialized;
	}
	
	@Override
	public Category addCategory(String categoryName, Map<String, Object> attributes, String categoryLabel, ProcessSideEnum processSideEnum) throws ExceptionCategoryBadRequest, ExceptionCategoryAlreayExist, ExceptionCategoryManagerFailure {
		//
		if (categoryName == null || categoryName.isEmpty()) {
			LOG.error("[CategoryManager].addCategory : error = categoryName is invalid.");
			throw new ExceptionCategoryBadRequest();
		}
		
		if (categoryLabel == null || categoryLabel.isEmpty()) {
			LOG.error("[CategoryManager].addCategory : error = categoryLabel is invalid.");
			throw new ExceptionCategoryBadRequest();
		}
		
		if (processSideEnum == null) {
			LOG.error("[CategoryManager].addCategory : error = processSideEnum is invalid.");
			throw new ExceptionCategoryBadRequest();
		}
		
		CategoryDto categoryDto = getCategoryDtoByCategoryName(categoryName);
		if (categoryDto != null) {
			LOG.error("[CategoryManager].addCategory : error = category is already exist.");
			throw new ExceptionCategoryAlreayExist();
		}
		
		if (attributes == null)
			attributes = new HashMap<>();
		
		try {
			String categoryId = UUID.randomUUID().toString();
			categoryDto = new CategoryDto(categoryId, categoryName, categoryLabel, attributes, processSideEnum.getSideType(), DEFAULT_VERSION);
			return convertCategoryDtoToCategory(saveCategoryDtoAndGetCategoryDto(categoryDto));
		} catch (Exception e) {
			LOG.error("[CategoryManager].addCategory : error = " + e);
			throw new ExceptionCategoryManagerFailure();
		}
	}

	@Override
	public Category getCategory(String categoryName) throws ExceptionCategoryBadRequest, ExceptionCategoryNotFound {
		//
		if (categoryName == null || categoryName.isEmpty()) {
			LOG.error("[CategoryManager].getCategory : error = categoryName is invalid.");
			throw new ExceptionCategoryBadRequest();
		}
		
		CategoryDto categoryDto = getCategoryDtoByCategoryName(categoryName);
		if (categoryDto == null) {
			LOG.error("[CategoryManager].getCategory : error = category is not exist.");
			throw new ExceptionCategoryNotFound();
		}
		
		return convertCategoryDtoToCategory(categoryDto);
	}

	@Override
	public List<Category> getAllCategory() {
		//
		List<Category> categories = new ArrayList<>();
		
		List<CategoryDto> categoryDtos = getAllCategoryDto();
		if (categoryDtos != null) {
			for (CategoryDto categoryDto : categoryDtos) {
				categories.add(convertCategoryDtoToCategory(categoryDto));
			}
		}
		
		return categories;
	}
	
	@Override
	public List<Category> getAllCategoryByProcessSide(ProcessSideEnum processSideEnum) throws ExceptionCategoryBadRequest {
		//
		if (processSideEnum == null) {
			LOG.error("[CategoryManager].getAllCategoryByProcessSide : error = processSideEnum is invalid.");
			throw new ExceptionCategoryBadRequest();
		}
		
		List<Category> categories = new ArrayList<>();
		
		List<CategoryDto> categoryDtos = getAllCategoryDtoByProcessSide(processSideEnum.getSideType());
		if (categoryDtos != null) {
			for (CategoryDto categoryDto : categoryDtos) {
				categories.add(convertCategoryDtoToCategory(categoryDto));
			}
		}
		
		return categories;
	}

	@Override
	public Category updateCategory(String categoryName, Map<String, Object> attributes, Boolean merge) throws ExceptionCategoryBadRequest, ExceptionCategoryNotFound, ExceptionCategoryManagerFailure {
		//
		try {
			if (categoryName == null || categoryName.isEmpty()) {
				LOG.error("[CategoryManager].updateCategory : error = categoryName is invalid.");
				throw new ExceptionCategoryBadRequest();
			}
			
			CategoryDto categoryDto = getCategoryDtoByCategoryName(categoryName);
			if (categoryDto == null) {
				LOG.error("[CategoryManager].updateCategory : error = category is not exist.");
				throw new ExceptionCategoryNotFound();
			}
			
			try {
				if (merge) {
					Map<String, Object> mergedAttributes = JsonUtil.mergeJsonMap(categoryDto.getAttributes(), attributes);
					categoryDto.setAttributes(mergedAttributes);
				}
				else {
					categoryDto.setAttributes(attributes);
				}
			} catch (Exception e) {
				LOG.error("[CategoryManager].updateCategory : error = attributes is invallid. " + e);
			}
			
			categoryDto.setVersion(categoryDto.getVersion() + 1L);
		
			return convertCategoryDtoToCategory(saveCategoryDtoAndGetCategoryDto(categoryDto));
		} catch (Exception e) {
			LOG.error("[CategoryManager].updateCategory : error = " + e);
			throw new ExceptionCategoryManagerFailure();
		}
	}
	
	@Override
	public Category updateCategoryName(String categoryId, String categoryName) throws ExceptionCategoryBadRequest, ExceptionCategoryNotFound, ExceptionCategoryManagerFailure {
		//
		try {
			if (categoryId == null || categoryId.isEmpty()) {
				LOG.error("[CategoryManager].updateCategoryName : error = categoryId is invalid.");
				throw new ExceptionCategoryBadRequest();
			}
			
			if (categoryName == null || categoryName.isEmpty()) {
				LOG.error("[CategoryManager].updateCategoryName : error = categoryName is invalid.");
				throw new ExceptionCategoryBadRequest();
			}
			
			CategoryDto categoryDto = getCategoryDtoByCategoryId(categoryId);
			if (categoryDto == null) {
				LOG.error("[CategoryManager].updateCategoryName : error = category is not exist.");
				throw new ExceptionCategoryNotFound();
			}
			
			categoryDto.setCategoryName(categoryName);
			categoryDto.setVersion(categoryDto.getVersion() + 1L);
			
			return convertCategoryDtoToCategory(saveCategoryDtoAndGetCategoryDto(categoryDto));
		} catch (Exception e) {
			LOG.error("[CategoryManager].updateCategoryName : error = " + e);
			throw new ExceptionCategoryManagerFailure();
		}
	}

	@Override
	public Boolean deleteCategory(String categoryId) throws ExceptionCategoryBadRequest, ExceptionCategoryNotFound, ExceptionCategoryManagerFailure {
		//
		if (categoryId == null || categoryId.isEmpty()) {
			LOG.error("[CategoryManager].deleteCategory : error = categoryId is invalid.");
			throw new ExceptionCategoryBadRequest();
		}
		
		CategoryDto categoryDto = getCategoryDtoByCategoryId(categoryId);
		if (categoryDto == null) {
			LOG.error("[CategoryManager].deleteCategory : error = category is not exist.");
			throw new ExceptionCategoryNotFound();
		}
		
		try {
			return deleteCategoryDto(categoryDto);
		} catch (Exception e) {
			LOG.error("[CategoryManager].deleteCategory : error = " + e);
			throw new ExceptionCategoryManagerFailure();
		}
	}

	@Override
	public Boolean deleteAllCategory() {
		//
		return deleteAllCategoryDtos();
	}
	
	@Override
	public Boolean deleteAllCategoryByProcessSide(ProcessSideEnum processSideEnum) {
		//
		return deleteAllCategoryDtosByProcessSide(processSideEnum.getSideType());
	}

	@Override
	public Boolean isValidCategory(String categoryName) {
		//
		if (categoryName == null || categoryName.isEmpty())
			return false;
		
		try {
			return isExist(categoryName);
		} catch(Exception e) {
			LOG.error("[CategoryManager].isValidCategory : error = " + e);
			return false;
		}
	}
	
	private CategoryDto getCategoryDtoByCategoryName(String categoryName) {
		//
		try {
			List<CategoryDto> categoryDtos = categoryRepository.findByCategoryName(categoryName);
			if (categoryDtos != null && !categoryDtos.isEmpty())
				return categoryDtos.get(0);
			else
				return null;
		} catch(Exception e) {
			LOG.error("[CategoryManager].getCategoryDtoByCategoryName : error = " + e);
			return null;
		}
	}
	
	private CategoryDto getCategoryDtoByCategoryId(String categoryId) {
		//
		try {
			Optional<CategoryDto> categoryDto = categoryRepository.findById(categoryId);
			if (categoryDto.isPresent())
				return categoryDto.get();
			else
				return null;
		} catch(Exception e) {
			LOG.error("[CategoryManager].getCategoryDtoByCategoryId : error = " + e);
			return null;
		}
	}
	
	private List<CategoryDto> getAllCategoryDto() {
		//
		try {
			List<CategoryDto> categoryDtos = categoryRepository.findAll();
			return categoryDtos;
		} catch(Exception e) {
			LOG.error("[CategoryManager].getAllCategoryDto : error = " + e);
			return null;
		}
	}
	
	private List<CategoryDto> getAllCategoryDtoByProcessSide(String processSide) {
		//
		try {
			return categoryRepository.findByProcessSide(processSide);
		} catch(Exception e) {
			LOG.error("[CategoryManager].getAllCategoryDtoByProcessSide : error = " + e);
			return null;
		}
	}
	
	private Boolean saveCategoryDto(CategoryDto categoryDto) {
		//
		try {
			categoryRepository.save(categoryDto);
			return true;
		} catch(Exception e) {
			LOG.error("[CategoryManager].saveCategoryDto : error = " + e);
			return false;
		}
	}
	
	private CategoryDto saveCategoryDtoAndGetCategoryDto(CategoryDto categoryDto) {
		//
		try {
			saveCategoryDto(categoryDto);
			return getCategoryDtoByCategoryId(categoryDto.getCategoryId());
		} catch(Exception e) {
			LOG.error("[CategoryManager].saveCategoryDtoAndGetCategoryDto : error = " + e);
			return null;
		}
	}
	
	private Boolean deleteCategoryDto(CategoryDto categoryDto) {
		//
		try {
			categoryRepository.delete(categoryDto);
			return true;
		} catch(Exception e) {
			LOG.error("[CategoryManager].deleteCategoryDto : error = " + e);
			return false;
		}
	}
	
	private Boolean deleteAllCategoryDtos() {
		//
		try {
			categoryRepository.deleteAll();
			return true;
		} catch(Exception e) {
			LOG.error("[CategoryManager].deleteAllCategoryDtos : error = " + e);
			return false;
		}
	}
	
	private Boolean deleteAllCategoryDtosByProcessSide(String processSide) {
		//
		try {
			categoryRepository.deleteByProcessSide(processSide);
			return true;
		} catch(Exception e) {
			LOG.error("[CategoryManager].deleteAllCategoryDtosByProcessSide : error = " + e);
			return false;
		}
	}
	
	private Boolean isExist(String categoryName) {
		//
		try {
			return categoryRepository.existsByCategoryName(categoryName);
		} catch(Exception e) {
			LOG.error("[CategoryManager].isExist : error = " + e);
			return false;
		}
	}

	private Category convertCategoryDtoToCategory(CategoryDto categoryDto) {
		//
		if (categoryDto != null)
			return new Category(categoryDto.getCategoryId(), categoryDto.getCategoryName(), categoryDto.getCategoryLabel(), categoryDto.getAttributes(), categoryDto.getProcessSide(), categoryDto.getVersion());
		return null;
	}
}
