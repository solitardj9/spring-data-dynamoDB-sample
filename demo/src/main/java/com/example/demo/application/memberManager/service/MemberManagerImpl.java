package com.example.demo.application.memberManager.service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.application.memberManager.MemberManager;
import com.example.demo.application.memberManager.dao.MemberRepository;
import com.example.demo.application.memberManager.dao.MemberTableRepository;
import com.example.demo.application.memberManager.dao.dto.MemberDto;

@Service("memberManager")
public class MemberManagerImpl implements MemberManager {

	private static final Logger LOG = LoggerFactory.getLogger(MemberManagerImpl.class);
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	MemberTableRepository memberTableRepository;
	
	@PostConstruct
	public void init() {
		//
		LOG.info("[MemberManager].init : MemberManager start");
		
		if (memberTableRepository.isExistTable()) {
			LOG.info("[MemberManager].init : Table is exist? true");
		}
		else {
			LOG.info("[FamilyManager].init : Table is exist? false");
			memberTableRepository.createTable();
			LOG.info("[MemberManager].init : Table is exist? " + memberTableRepository.isExistTable());
		}
	}
	
	@Override
	public void writeSamples() {
		//
		MemberDto memberDto = null;
		Map<String, Object> remark = new HashMap<>();
		remark.put("key1", "1");
		remark.put("key2", "2");
		
		memberDto = new MemberDto();
		memberDto.setMemberId("ID_0001");
		memberDto.setMemberType(1);
		memberDto.setMemberAge(30);
		memberDto.setFamilyId("ID_01");
		memberDto.setRemark(remark);
		memberRepository.save(memberDto);
		
		memberDto = new MemberDto();
		memberDto.setMemberId("ID_0002");
		memberDto.setMemberType(2);
		memberDto.setMemberAge(29);
		memberDto.setFamilyId("ID_01");
		memberDto.setRemark(remark);
		memberRepository.save(memberDto);
		
		memberDto = new MemberDto();
		memberDto.setMemberId("ID_0003");
		memberDto.setMemberType(1);
		memberDto.setMemberAge(28);
		memberDto.setFamilyId("ID_02");
		memberDto.setRemark(remark);
		memberRepository.save(memberDto);
		
		memberDto = new MemberDto();
		memberDto.setMemberId("ID_0004");
		memberDto.setMemberType(2);
		memberDto.setMemberAge(31);
		memberDto.setFamilyId("ID_02");
		memberDto.setRemark(remark);
		memberRepository.save(memberDto);
		
		LOG.info("Member Table = " + streamJoin(memberRepository.findAll()));
		
		LOG.info("MemberDto = " + memberRepository.findById("ID_0001").toString());
		
		LOG.info("MemberDtos : findAllByMemberIdAndMemberType = " + streamJoin(memberRepository.findAllByMemberIdAndMemberType("ID_0001", 1)));
		
		LOG.info("MemberDtos : findAllByFamilyId = " + streamJoin(memberRepository.findAllByFamilyId("ID_01")));
		
		LOG.info("MemberDtos : findAllByFamilyIdAndMemberType = " + streamJoin(memberRepository.findAllByFamilyIdAndMemberType("ID_02", 1)));
		
		LOG.info("MemberDtos : findAllByFamilyIdAndMemberAge = " + streamJoin(memberRepository.findAllByFamilyIdAndMemberAge("ID_02", 28)));
		
		LOG.info("MemberDtos : findAllByFamilyIdAndMemberAgeLessThan = " + streamJoin(memberRepository.findAllByFamilyIdAndMemberAgeLessThan("ID_02", 30)));
		
		// delete ID_0004
		memberRepository.delete(memberDto);
		LOG.info("Member Table = " + streamJoin(memberRepository.findAll()));
		
		// delete ID_02
		memberRepository.deleteById("ID_0003");
		LOG.info("Member Table = " + streamJoin(memberRepository.findAll()));
		
		// delete all
		memberRepository.deleteAll();
		LOG.info("Member Table = " + streamJoin(memberRepository.findAll()));
		
		memberTableRepository.deleteTable();
		LOG.info("[MemberManager].writeSamples : Table is exist? " + memberTableRepository.isExistTable());
	}
	
	// https://stackoverflow.com/questions/42627108/best-way-to-convert-iterablecharacter-to-string
	private String streamJoin(Iterable<MemberDto> memberDtos){
		//
	    return StreamSupport.stream(memberDtos.spliterator(), true)
	                        .map(Object::toString)
	                        .collect(Collectors.joining(""));
	}
}