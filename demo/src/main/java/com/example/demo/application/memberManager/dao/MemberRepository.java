package com.example.demo.application.memberManager.dao;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo.application.memberManager.dao.dto.MemberDto;

@EnableScan
public interface MemberRepository extends PagingAndSortingRepository<MemberDto, String> {

	List<MemberDto> findAll();
	
	List<MemberDto> findAllByMemberIdAndMemberType(String memberId, Integer memberType);
	
	List<MemberDto> findAllByFamilyId(String familyId);
	
	List<MemberDto> findAllByFamilyIdAndMemberType(String familyId, Integer memberType);
	
	List<MemberDto> findAllByFamilyIdAndMemberAge(String familyId, Integer memberAge);
	
	List<MemberDto> findAllByFamilyIdAndMemberAgeLessThan(String familyId, Integer memberAge);
	
	void deleteAll();
	
	
	
	
	List<MemberDto> findAllByMemberIdAndMemberLevel(String memberId, Integer memberLevel);
	
	List<MemberDto> findAllByMemberIdAndMemberLevelLessThan(String memberId, Integer memberLevel);
	
	List<MemberDto> findAllByFamilyIdAndMemberLevel(String familyId, Integer memberLevel);
	
	List<MemberDto> findAllByFamilyIdAndMemberLevelLessThan(String familyId, Integer memberLevel);
	
	List<MemberDto> findAllByMemberLevelLessThan(Integer memberLevel);
}