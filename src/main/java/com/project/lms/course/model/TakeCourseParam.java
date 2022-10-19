package com.project.lms.course.model;

import com.project.lms.member.admin.model.CommonParam;

import lombok.Data;

@Data
public class TakeCourseParam extends CommonParam {

	long id;
	String status;
	String userId;
	long searchCourseId;
}
