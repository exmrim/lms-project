package com.project.lms.course.model;

import com.project.lms.member.admin.model.CommonParam;

import lombok.Data;

@Data
public class CourseParam extends CommonParam {
	
	long id;
	long categoryId;
	
}
