package com.project.lms.admin.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.project.lms.admin.dto.CategoryDto;
import com.project.lms.category.entity.Category;
import com.project.lms.category.model.CategoryInput;
import com.project.lms.category.repository.CategoryRepository;
import com.project.lms.components.MailComponents;
import com.project.lms.member.admin.mapper.CategoryMapper;
import com.project.lms.member.admin.mapper.MemberMapper;



import com.project.lms.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;

	private Sort getSortBySortValueDesc() {
		return Sort.by(Sort.Direction.DESC, "sortValue");
	}
	@Override
	public boolean add(String categoryName) {
		
		Category category = Category.builder()
				.categoryName(categoryName)
				.usingYn(true)
				.sortValue(0)
				.build();
		categoryRepository.save(category);
			
		return true;
	}

	@Override
	public boolean update(CategoryInput parameter) {
		Optional<Category> optionalCategory = categoryRepository.findById(parameter.getId());
		if(optionalCategory.isPresent()) {
			Category category = optionalCategory.get();
			
			category.setCategoryName(parameter.getCategoryName());
			category.setSortValue(parameter.getSortValue());
			category.setUsingYn(parameter.isUsingYn());
			categoryRepository.save(category);
		}
		return true;
	}

	@Override
	public boolean delete(long id) {
		
		categoryRepository.deleteById(id);
		
		return true;
	}


	@Override
	public List<CategoryDto> list() {
		
		List<Category> categories = categoryRepository.findAll(getSortBySortValueDesc());
		return CategoryDto.of(categories);
		//return categoryRepository.findAllOrderBySortValueDesc().map(CategoryDto::of).orElse(null);
	}
	@Override
	public List<CategoryDto> frontList(CategoryDto parameter) {
		
		return categoryMapper.select(parameter);
	}

	
	
	
	
}
