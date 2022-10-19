package com.project.lms.category.model;

import lombok.Data;

@Data
public class CategoryInput {

	Long id;
	String categoryName;
	int sortValue;
	boolean usingYn;
}
