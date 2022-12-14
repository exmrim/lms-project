package com.project.lms.course.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.project.lms.course.entity.TakeCourse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TakeCourseDto {

	long id;
	
	long courseId;
	String userId;
	
	long payPrice;
	String status;
	
	LocalDateTime regDt;
	
	String subject;
	String userPhone;
	String userName;
	
	long totalCount;
	long seq;
	
	public String getRegDtText() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
		
		return regDt != null ? regDt.format(formatter) : "";
	}

	public static TakeCourseDto of(TakeCourse x) {
		
		return TakeCourseDto.builder()
				.id(x.getId())
				.courseId(x.getCourseId())
				.userId(x.getUserId())
				.payPrice(x.getPayPrice())
				.status(x.getStatus())
				.regDt(x.getRegDt())
				.build();
	}
	
}
