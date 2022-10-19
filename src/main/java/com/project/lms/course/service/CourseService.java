package com.project.lms.course.service;

import java.util.List;

import com.project.lms.course.dto.CourseDto;
import com.project.lms.course.model.CourseInput;
import com.project.lms.course.model.CourseParam;
import com.project.lms.course.model.ServiceResult;
import com.project.lms.course.model.TakeCourseInput;

public interface CourseService {

	boolean add(CourseInput parameter);

	List<CourseDto> list(CourseParam parameter);

	CourseDto getById(long id);

	boolean upd(CourseInput parameter);

	boolean del(String idList);

	List<CourseDto> frontList(CourseParam parameter);

	CourseDto frontDetail(long id);

	ServiceResult req(TakeCourseInput parameter);

	List<CourseDto> listAll();
}	
