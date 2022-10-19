package com.project.lms.course.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.admin.dto.CategoryDto;
import com.project.lms.admin.service.CategoryService;
import com.project.lms.common.model.ResponseResult;
import com.project.lms.course.dto.CourseDto;
import com.project.lms.course.model.CourseInput;
import com.project.lms.course.model.CourseParam;
import com.project.lms.course.model.ServiceResult;
import com.project.lms.course.model.TakeCourseInput;
import com.project.lms.course.service.CourseService;
import com.project.lms.member.admin.dto.MemberDto;
import com.project.lms.util.PageUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ApiCourseController extends BaseController{

	private final CourseService courseService;
	private final CategoryService categoryService;
	
	@PostMapping("/api/course/req.api")
	public ResponseEntity<?> courseReq(Model model
			, @RequestBody TakeCourseInput parameter
			, Principal principal) {
		
		parameter.setUserId(principal.getName());
		
		ServiceResult result = courseService.req(parameter);
		if(!result.isResult()) {
			ResponseResult responseResult = new ResponseResult(false, result.getMessage());
			return ResponseEntity.ok().body(responseResult);
		}
		
		ResponseResult responseResult = new ResponseResult(true);
		return ResponseEntity.ok().body(responseResult);
	}
	
	
}
