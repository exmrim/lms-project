package com.project.lms.member.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.common.model.ResponseResult;
import com.project.lms.course.dto.TakeCourseDto;
import com.project.lms.course.model.ServiceResult;
import com.project.lms.course.model.TakeCourseInput;
import com.project.lms.course.service.TakeCourseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ApiMemberController {

	private final TakeCourseService takeCourseService;

	@PostMapping("/api/member/course/cancel.api")
	public ResponseEntity<?> cancelCourse(Model model, @RequestBody TakeCourseInput parameter, Principal principal) {

		String userId = principal.getName();
		
		TakeCourseDto detail = takeCourseService.detail(parameter.getTakeCourseId());
		if(detail == null) {
			ResponseResult responseResult = new ResponseResult(false, "Course registration information does not exist.");
			return ResponseEntity.ok().body(responseResult);
		}
		
		if(userId == null || !userId.equals(detail.getUserId())) {
			ResponseResult responseResult = new ResponseResult(false, "You can only modify your course registration information.");
			return ResponseEntity.ok().body(responseResult);
		}
		
		ServiceResult result = takeCourseService.cancel(parameter.getTakeCourseId());
		if(!result.isResult()) {
			ResponseResult responseResult = new ResponseResult(false, result.getMessage());
			return ResponseEntity.ok().body(responseResult);
		}
		
		ResponseResult responseResult = new ResponseResult(true);
		return ResponseEntity.ok().body(responseResult);
	}
	
}
