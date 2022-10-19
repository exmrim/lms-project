package com.project.lms.course.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Lob;

import com.project.lms.course.entity.Course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CourseDto {

	Long id;
	
	long categoryId;
	String imagePath;
	String keyword;
	String subject;
	String summary;
	String contents;
	long price;
	long salePrice;
	LocalDate saleEndDt;
	LocalDateTime regDt;
	LocalDateTime updDt;
	
	String filename;
	String urlFilename;
	
	long totalCount;
	long seq;
	
	public static CourseDto of(Course course) {
		return CourseDto.builder()
				.id(course.getId())
				.categoryId(course.getCategoryId())
				.imagePath(course.getImagePath())
				.keyword(course.getKeyword())
				.subject(course.getSubject())
				.summary(course.getSummary())
				.contents(course.getContents())
				.price(course.getPrice())
				.salePrice(course.getSalePrice())
				.saleEndDt(course.getSaleEndDt())
				.filename(course.getFilename())
				.urlFilename(course.getUrlFilename())
				.regDt(course.getRegDt())
				.updDt(course.getUpdDt())
				.build();
	}
	
	public static List<CourseDto> of(List<Course> courses) {
		
		if(courses != null) {
			List<CourseDto> courseList = new ArrayList<>();
			for(Course x : courses) {
				courseList.add(CourseDto.of(x));
			}
			return courseList;
		}
		return null;
	}
	
}
