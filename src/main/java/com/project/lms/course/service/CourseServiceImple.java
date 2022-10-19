package com.project.lms.course.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.project.lms.course.dto.CourseDto;
import com.project.lms.course.entity.Course;
import com.project.lms.course.entity.TakeCourse;
import com.project.lms.course.mapper.CourseMapper;
import com.project.lms.course.model.CourseInput;
import com.project.lms.course.model.CourseParam;
import com.project.lms.course.model.ServiceResult;
import com.project.lms.course.model.TakeCourseInput;
import com.project.lms.course.repository.CourseRepository;
import com.project.lms.course.repository.TakeCourseRepository;
import com.project.lms.member.admin.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CourseServiceImple implements CourseService{

	private final CourseRepository courseRepository;
	private final TakeCourseRepository takeCourseRepository;
	private final CourseMapper courseMapper;
	
	private LocalDate getLocalDate(String value) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			return LocalDate.parse(value, formatter);
		} catch(Exception e) {
			
		}
		
		return null;
		
	}

	@Override
	public boolean add(CourseInput parameter) {
		
		LocalDate saleEndDt = getLocalDate(parameter.getSaleEndDtText());
		
		Course course = Course.builder()
				.categoryId(parameter.getCategoryId())
				.subject(parameter.getSubject())
				.keyword(parameter.getKeyword())
				.summary(parameter.getSummary())
				.contents(parameter.getContents())
				.price(parameter.getPrice())
				.salePrice(parameter.getSalePrice())
				.saleEndDt(saleEndDt)
				.filename(parameter.getFilename())
				.urlFilename(parameter.getUrlFilename())
				.regDt(LocalDateTime.now())
				.build();
		courseRepository.save(course);
		
		return true;
	}
	
	@Override
	public boolean upd(CourseInput parameter) {
		
		LocalDate saleEndDt = getLocalDate(parameter.getSaleEndDtText());
		
		Optional<Course> optionalCourse = courseRepository.findById(parameter.getId());
		if(!optionalCourse.isPresent()) {
			return false;
		}
		
		Course course = optionalCourse.get();
		course.setCategoryId(parameter.getCategoryId());
		course.setSubject(parameter.getSubject());
		course.setKeyword(parameter.getKeyword());
		course.setSummary(parameter.getSummary());
		course.setContents(parameter.getContents());
		course.setPrice(parameter.getPrice());
		course.setSalePrice(parameter.getSalePrice());
		course.setSaleEndDt(saleEndDt);
		course.setFilename(parameter.getFilename());
		course.setUrlFilename(parameter.getUrlFilename());
		course.setUpdDt(LocalDateTime.now());
		courseRepository.save(course);
		
		return true;
	}

	@Override
	public List<CourseDto> list(CourseParam parameter) {

		long totalCount = courseMapper.selectListCount(parameter);
		List<CourseDto> list = courseMapper.selectList(parameter);
		
		if(!CollectionUtils.isEmpty(list)) {
			int i = 0;
			for(CourseDto x : list) {
				x.setTotalCount(totalCount);
				x.setSeq(totalCount-parameter.getPageStart()-i);
				i++;
			}
		}
		
		return list;

	}

	@Override
	public CourseDto getById(long id) {

		return courseRepository.findById(id).map(CourseDto::of).orElse(null);

	}

	@Override
	public boolean del(String idList) {
		
		if(idList != null && idList.length() > 0) {
			String[] ids = idList.split(",");
			for(String x : ids) {
				long id = 0L;
				try {
					id =  Long.parseLong(x);
				} catch(Exception e) {
					
				}
				
				if(id > 0) {
					courseRepository.deleteById(id);
				}
			}
		}
		
		return true;
	}

	@Override
	public List<CourseDto> frontList(CourseParam parameter) {
		
		if(parameter.getCategoryId() < 1) {
			List<Course> courseList = courseRepository.findAll();
			return CourseDto.of(courseList);
		}
		 
		Optional<List<Course>> optionalCourseList = courseRepository.findByCategoryId(parameter.getCategoryId());
		if(optionalCourseList.isPresent()) {
			return CourseDto.of(optionalCourseList.get());
		}
		
		return null;
		
	}

	@Override
	public CourseDto frontDetail(long id) {
		
		Optional<Course> optionalCourse = courseRepository.findById(id);
		if(optionalCourse.isPresent()) {
			return CourseDto.of(optionalCourse.get()); 
		}
		return null;
	}

	@Override
	public ServiceResult req(TakeCourseInput parameter) {
		
		ServiceResult result = new ServiceResult();
		
		Optional<Course> optionalCourse = courseRepository.findById(parameter.getCourseId());
		if(!optionalCourse.isPresent()) {
			result.setResult(false);
			result.setMessage("Course does not exist.");
			return result;
		}
		
		Course course = optionalCourse.get();
		
		String[] statusList = {TakeCourse.STATUS_REQ, TakeCourse.STATUS_COMPLETE};
		long count = takeCourseRepository.countByCourseIdAndUserIdAndStatusIn(course.getId(), parameter.getUserId(), Arrays.asList(statusList));
		if(count > 0) {
			result.setResult(false);
			result.setMessage("This course has already been requested.");
			return result;
		}
		
		TakeCourse takeCourse = TakeCourse.builder()
				.courseId(course.getId())
				.userId(parameter.getUserId())
				.payPrice(course.getSalePrice())
				.regDt(LocalDateTime.now())
				.status(TakeCourse.STATUS_REQ)
				.build();
		takeCourseRepository.save(takeCourse);
		
		result.setResult(true);
		result.setMessage("");
		
		return result;
	}

	@Override
	public List<CourseDto> listAll() {
		
		List<Course> courseList = courseRepository.findAll();
		
		return CourseDto.of(courseList);
	}


	
	
}
