package com.project.lms.member.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.lms.admin.dto.CategoryDto;

@Mapper
public interface CategoryMapper {
	
	List<CategoryDto> select(CategoryDto parameter);

}
