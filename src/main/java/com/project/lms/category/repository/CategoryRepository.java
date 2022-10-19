package com.project.lms.category.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.category.entity.Category;
import com.project.lms.member.entity.Member;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	//Optional<List<Category>> findAllOrderBySortValueDesc();
	
	
}
