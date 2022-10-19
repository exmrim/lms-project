package com.project.lms.course.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.project.lms.course.dto.TakeCourseDto;
import com.project.lms.course.entity.TakeCourse;
import com.project.lms.course.entity.TakeCourseCode;
import com.project.lms.course.mapper.TakeCourseMapper;
import com.project.lms.course.model.ServiceResult;
import com.project.lms.course.model.TakeCourseParam;
import com.project.lms.course.repository.TakeCourseRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TakeCourseServiceImple implements TakeCourseService{

	private final TakeCourseMapper takeCourseMapper;
	private final TakeCourseRepository takeCourseRepository;
	
	@Override
	public List<TakeCourseDto> list(TakeCourseParam parameter) {

		long totalCount = takeCourseMapper.selectListCount(parameter);
		List<TakeCourseDto> list = takeCourseMapper.selectList(parameter);
		
		if(!CollectionUtils.isEmpty(list)) {
			int i = 0;
			for(TakeCourseDto x : list) {
				x.setTotalCount(totalCount);
				x.setSeq(totalCount-parameter.getPageStart()-i);
				i++;
			}
		}
		
		return list;
	}

	@Override
	public ServiceResult updateStatus(long id, String status) {
		
		Optional<TakeCourse> optionalTakeCourse = takeCourseRepository.findById(id);
		if(!optionalTakeCourse.isPresent()) {
			return new ServiceResult(false, "This course information does not exist.");
		}

		TakeCourse takeCourse = optionalTakeCourse.get();
		
		takeCourse.setStatus(status);
		takeCourseRepository.save(takeCourse);
		
		return new ServiceResult(true);
	}

	@Override
	public List<TakeCourseDto> mycourse(String userId) {
		
		TakeCourseParam parameter = new TakeCourseParam();
		parameter.setUserId(userId);
		List<TakeCourseDto> list = takeCourseMapper.selectListMyCourse(parameter);
		
		return list;
	}

	@Override
	public TakeCourseDto detail(long id) {
		
		Optional<TakeCourse> optionalTakeCourse = takeCourseRepository.findById(id);
		if(optionalTakeCourse.isPresent()) {
			return TakeCourseDto.of(optionalTakeCourse.get());
		}
		return null; 
	}

	@Override
	public ServiceResult cancel(long id) {
		
		Optional<TakeCourse> optionalTakeCourse = takeCourseRepository.findById(id);
		if(!optionalTakeCourse.isPresent()) {
			return new ServiceResult(false, "Course registration information does not exist.");
		}
		
		TakeCourse takeCourse = optionalTakeCourse.get();
		takeCourse.setStatus(TakeCourseCode.STATUS_CANCEL);
		takeCourseRepository.save(takeCourse);
		
		return new ServiceResult(true); 
	}
	
	
}
