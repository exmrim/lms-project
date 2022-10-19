package com.project.lms.member.service;


import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.project.lms.course.model.ServiceResult;
import com.project.lms.member.admin.dto.MemberDto;
import com.project.lms.member.admin.model.MemberParam;
import com.project.lms.member.entity.Member;
import com.project.lms.member.model.MemberInput;
import com.project.lms.member.model.ResetPasswordInput;

public interface MemberService extends UserDetailsService {
	boolean register(MemberInput parameter);
	
	boolean emailAuth(String uuid);
	
	boolean sendResetPassword(ResetPasswordInput parameter);
	
	boolean resetPassword(String uuid, String password);
	
	boolean checkResetPassword(String uuid);
	
	
	/**
	 * 관리자에서만 사용 
	 */
	List<MemberDto> list(MemberParam parameter);

	MemberDto detail(String userId);

	boolean updateStatus(String userId, String userStatus);

	boolean updatePassword(String userId, String userPass);

	ServiceResult updateMember(MemberInput parameter);
	
	ServiceResult updateMemberPassword(MemberInput parameter);

	ServiceResult withdraw(String userId, String password);

	
}
