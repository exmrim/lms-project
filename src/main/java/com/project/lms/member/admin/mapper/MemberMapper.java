package com.project.lms.member.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.lms.member.admin.dto.MemberDto;
import com.project.lms.member.admin.model.MemberParam;

@Mapper
public interface MemberMapper {
	
	long selectListCount(MemberParam parameter);
	List<MemberDto> selectList(MemberParam parameter);

}
