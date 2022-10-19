package com.project.lms.course.service;

import java.util.List;

import com.project.lms.course.dto.CourseDto;
import com.project.lms.course.dto.TakeCourseDto;
import com.project.lms.course.model.ServiceResult;
import com.project.lms.course.model.TakeCourseParam;

public interface TakeCourseService {

	List<TakeCourseDto> list(TakeCourseParam parameter);
	
	ServiceResult updateStatus(long id, String status);

	List<TakeCourseDto> mycourse(String userId);

	TakeCourseDto detail(long id);
	
	ServiceResult cancel(long id);

}	
