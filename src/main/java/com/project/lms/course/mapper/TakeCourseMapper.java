package com.project.lms.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.lms.course.dto.TakeCourseDto;
import com.project.lms.course.model.TakeCourseParam;

@Mapper
public interface TakeCourseMapper {

	long selectListCount(TakeCourseParam parameter);
	List<TakeCourseDto> selectList(TakeCourseParam parameter);
	List<TakeCourseDto> selectListMyCourse(TakeCourseParam parameter);
	
}
