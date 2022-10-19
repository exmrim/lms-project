package com.project.lms.course.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.course.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{

	Optional<List<Course>> findByCategoryId(long categoryId);

	
	
}
