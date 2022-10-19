package com.project.lms.admin.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.project.lms.admin.dto.CategoryDto;
import com.project.lms.category.model.CategoryInput;


public interface CategoryService {

	List<CategoryDto> list();
	
	boolean add(String categoryName);
	
	boolean update(CategoryInput parameter);
	
	boolean delete(long id);
	
	List<CategoryDto> frontList(CategoryDto parameter);
}
