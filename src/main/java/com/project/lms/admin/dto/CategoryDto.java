package com.project.lms.admin.dto;

import java.util.ArrayList;
import java.util.List;

import com.project.lms.category.entity.Category;
import com.project.lms.member.admin.dto.MemberDto;

import com.project.lms.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {

	Long id;
	
	String categoryName;
	int sortValue;
	boolean usingYn;
	
	//ADD Column
	int courseCount;
	
	public static List<CategoryDto> of(List<Category> categories) {
		
		if(categories != null) {
			List<CategoryDto> categoryList = new ArrayList<>();
			for(Category x:categories) {
				categoryList.add(of(x)); 
			}
			return categoryList;
		}
		
		return null;
		
	}
	
	public static CategoryDto of(Category category) {
		return CategoryDto.builder()
				.id(category.getId())
				.categoryName(category.getCategoryName())
				.sortValue(category.getSortValue())
				.usingYn(category.isUsingYn())
				.build();
	}
	
}
