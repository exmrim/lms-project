package com.project.lms.course.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.course.entity.TakeCourse;

public interface TakeCourseRepository extends JpaRepository<TakeCourse, Long>{

	long countByCourseIdAndUserIdAndStatusIn(long courseId, String userId, Collection<String> statusList);
	
}
