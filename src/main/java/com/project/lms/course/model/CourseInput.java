package com.project.lms.course.model;

import lombok.Data;

@Data
public class CourseInput {
	
	long categoryId;
	long id;
	
	String subject;
	String keyword;
	String summary;
	String contents;
	long price;
	long salePrice;
	String saleEndDtText;
	
	String idList;
	
	String filename;
	String urlFilename;
}
