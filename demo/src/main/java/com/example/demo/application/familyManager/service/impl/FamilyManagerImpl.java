package com.example.demo.application.familyManager.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.application.familyManager.dao.FamilyRepository;
import com.example.demo.application.familyManager.dao.FamilyTableRepository;
import com.example.demo.application.familyManager.dao.dto.FamilyDto;
import com.example.demo.application.familyManager.service.FamilyManager;

@Service("familyManager")
public class FamilyManagerImpl implements FamilyManager {

	private static final Logger LOG = LoggerFactory.getLogger(FamilyManagerImpl.class);
	
	@Autowired
	FamilyRepository familyRepository;
	
	@Autowired
	FamilyTableRepository familyTableRepository;
	
	@PostConstruct
	public void init() {
		//
		LOG.info("[FamilyManager].init : FamilyManager start");
		
		if (familyTableRepository.isExistTable()) {
			LOG.info("[FamilyManager].init : Table is exist? true");
		}
		else {
			LOG.info("[FamilyManager].init : Table is exist? false");
			familyTableRepository.createTable();
			LOG.info("[FamilyManager].init : Table is exist? " + familyTableRepository.isExistTable());
		}
	}
	
	@Override
	public void writeSamples() {
		//
		FamilyDto familyDto = null;
		Map<String, Object> remark = new HashMap<>();
		remark.put("key1", "1");
		remark.put("key2", "2");
		
		familyDto = new FamilyDto();
		familyDto.setFamilyId("ID_01");
		familyDto.setFamilyName("AAA");
		familyDto.setRemark(remark);
		familyTableRepository.putItem(familyDto);
		
		familyDto = new FamilyDto();
		familyDto.setFamilyId("ID_02");
		familyDto.setFamilyName("BBB");
		familyDto.setRemark(remark);
		familyTableRepository.putItem(familyDto);
		
		familyDto = new FamilyDto();
		familyDto.setFamilyId("ID_03");
		familyDto.setFamilyName("CCC");
		familyDto.setRemark(remark);
		familyTableRepository.putItem(familyDto);
		
		LOG.info("Family Table = " + familyTableRepository.scan().toString());
		
		LOG.info("FamilyDto = " + familyTableRepository.getItem("ID_02").toString());

		LOG.info("FamilyDtos = " + familyTableRepository.getItemsByFamilyName("AAA").toString());
		
		// delete ID_03
		familyTableRepository.deleteItem(familyDto);
		LOG.info("Family Table = " + familyTableRepository.scan().toString());
		
		// delete ID_02
		familyTableRepository.deleteItem("ID_02");
		LOG.info("Family Table = " + familyTableRepository.scan().toString());
		
		// delete all
		familyTableRepository.deleteAllItems();
		LOG.info("Family Table = " + familyTableRepository.scan().toString());
		
		familyTableRepository.deleteTable();
		LOG.info("[FamilyManager].writeSamples : Table is exist? " + familyTableRepository.isExistTable());
	}
	
	@Override
	public void writeSamples2() {
		//
		FamilyDto familyDto = null;
		Map<String, Object> remark = new HashMap<>();
		remark.put("key1", "1");
		remark.put("key2", "2");
		
		familyDto = new FamilyDto();
		familyDto.setFamilyId("ID_01");
		familyDto.setFamilyName("AAA");
		familyDto.setRemark(remark);
		familyRepository.save(familyDto);
		
		familyDto = new FamilyDto();
		familyDto.setFamilyId("ID_02");
		familyDto.setFamilyName("BBB");
		familyDto.setRemark(remark);
		familyRepository.save(familyDto);
		
		familyDto = new FamilyDto();
		familyDto.setFamilyId("ID_03");
		familyDto.setFamilyName("CCC");
		familyDto.setRemark(remark);
		familyRepository.save(familyDto);
		
		LOG.info("Family Table = " + streamJoin(familyRepository.findAll()));
		
		LOG.info("FamilyDto = " + familyRepository.findById("ID_02").toString());
		
		LOG.info("FamilyDtos = " + streamJoin(familyRepository.findByFamilyName("AAA")));
		
		// delete ID_03
		familyRepository.delete(familyDto);
		LOG.info("Family Table = " + streamJoin(familyRepository.findAll()));
		
		// delete ID_02
		familyRepository.deleteById("ID_02");
		LOG.info("Family Table = " + streamJoin(familyRepository.findAll()));
		
		// delete all
		familyRepository.deleteAll();
		LOG.info("Family Table = " + streamJoin(familyRepository.findAll()));
		
		familyTableRepository.deleteTable();
		LOG.info("[FamilyManager].writeSamples : Table is exist? " + familyTableRepository.isExistTable());
	}
	
	// https://stackoverflow.com/questions/42627108/best-way-to-convert-iterablecharacter-to-string
	private String streamJoin(Iterable<FamilyDto> familyDtos){
		//
	    return StreamSupport.stream(familyDtos.spliterator(), true)
	                        .map(Object::toString)
	                        .collect(Collectors.joining(""));
	}
}