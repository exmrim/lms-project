package com.project.lms.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.lms.course.dto.CourseDto;
import com.project.lms.course.model.CourseParam;

@Mapper
public interface CourseMapper {

	long selectListCount(CourseParam parameter);
	List<CourseDto> selectList(CourseParam parameter);
}
