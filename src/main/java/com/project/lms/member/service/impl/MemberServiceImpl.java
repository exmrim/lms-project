package com.project.lms.member.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.project.lms.components.MailComponents;
import com.project.lms.course.model.ServiceResult;
import com.project.lms.member.admin.dto.MemberDto;
import com.project.lms.member.admin.mapper.MemberMapper;
import com.project.lms.member.admin.model.MemberParam;
import com.project.lms.member.entity.Member;
import com.project.lms.member.entity.MemberCode;
import com.project.lms.member.exception.MemberNotEmailAuthException;
import com.project.lms.member.exception.MemberStopUserException;
import com.project.lms.member.model.MemberInput;
import com.project.lms.member.model.ResetPasswordInput;
import com.project.lms.member.repository.MemberRepository;
import com.project.lms.member.service.MemberService;
import com.project.lms.util.PasswordUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{
	
	private final MemberRepository memberRepository;
	private final MailComponents mailComponents;
	private final MemberMapper memberMapper;
	
	@Override
	public boolean register(MemberInput parameter) {
		
		Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
		if(optionalMember.isPresent()) {
			return false;
		}
		
		String encPassword = BCrypt.hashpw(parameter.getUserPass(), BCrypt.gensalt());
		String uuid = UUID.randomUUID().toString();

		Member member = Member.builder()
				.userId(parameter.getUserId())
				.userName(parameter.getUserName())
				.userPhone(parameter.getUserPhone())
				.userPass(encPassword)
				.regDt(LocalDateTime.now())
				.emailAuthYn(false)
				.emailAuthKey(uuid)
				.userStatus(Member.MEMBER_STATUS_REQ)
				.build();
		
		memberRepository.save(member);
		
		String mail = parameter.getUserId();
		String subject = "Congratulations for your sign in";
		String text = "Please click on the link below"+"<div><a href='http://localhost:8080/member/email-auth?id="+uuid+"'>Success</a></div>";
		mailComponents.sendMail(mail, subject, text);
		
		return true;
	}
	
	@Override
	public boolean emailAuth(String uuid) {
		
		Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);
		
		if(!optionalMember.isPresent()) {
			return false;
		}
		
		Member member = optionalMember.get();
		
		if(member.isEmailAuthYn()) {
			return false;
		}
		
		member.setUserStatus(Member.MEMBER_STATUS_ING);
		member.setEmailAuthYn(true);
		member.setEmailAuthDt(LocalDateTime.now());
		memberRepository.save(member);
		return true;
	}

	@Override
	public boolean sendResetPassword(ResetPasswordInput parameter) {
		
		Optional<Member> optionalMember = memberRepository.findByUserIdAndUserName(parameter.getUserId(), parameter.getUserName());
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("Member information does not exist.");
		}
		
		Member member = optionalMember.get();
		
		String uuid = UUID.randomUUID().toString();
		
		member.setResetPasswordKey(uuid);
		member.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));
		memberRepository.save(member);
		
		String mail = parameter.getUserId();
		String subject = "Please check your new password.";
		String text = "Please click on the link below"+"<div><a href='http://localhost:8080/member/reset/password?id="+uuid+"'>Success</a></div>";
		mailComponents.sendMail(mail, subject, text);
		
		
		return true;
	}
	
	@Override
	public boolean resetPassword(String uuid, String password) {
		
		Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("Member information does not exist.");
		}
		
		Member member = optionalMember.get();
		
		if(member.getResetPasswordLimitDt() == null) {
			throw new RuntimeException("This is not a valid date.");
		}
		
		if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("This is not a valid date.");
		}
		
		String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		member.setUserPass(encPassword);
		member.setResetPasswordKey("");
		member.setResetPasswordLimitDt(null);
		memberRepository.save(member);
		
		return true;
		
	}
	
	@Override
	public boolean checkResetPassword(String uuid) {
		
		Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
		if(!optionalMember.isPresent()) {
			return false;
		}
		
		Member member = optionalMember.get();
		
		if(member.getResetPasswordLimitDt() == null) {
			throw new RuntimeException("This is not a valid date.");
		}
		
		if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("This is not a valid date.");
		}
		
		
		return true;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Member> optionalMember = memberRepository.findById(username);
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("Member information does not exist.");
		}
		
		Member member = optionalMember.get();
		
		if(Member.MEMBER_STATUS_REQ.equals(member.getUserStatus())) { //if(Member.MEMBER_STATUS_REQ.equals(member.getUserStatus()))
			throw new MemberNotEmailAuthException("Please approve the email.");
		}
		
		if(Member.MEMBER_STATUS_STOP.equals(member.getUserStatus())) {
			throw new MemberStopUserException("This member is suspended.");
		}
		
		if(Member.MEMBER_STATUS_WITHDRAW.equals(member.getUserStatus())) {
			throw new MemberStopUserException("This member is withdrawn.");
		}
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		if(member.isAdminYn()) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		
		return new User(member.getUserId(), member.getUserPass(), grantedAuthorities);
	}

	@Override
	public List<MemberDto> list(MemberParam parameter) {
		
		//MemberDto parameter = new MemberDto();
		long totalCount = memberMapper.selectListCount(parameter);
		List<MemberDto> list = memberMapper.selectList(parameter);
		
		if(!CollectionUtils.isEmpty(list)) {
			int i = 0;
			for(MemberDto x : list) {
				x.setTotalCount(totalCount);
				x.setSeq(totalCount-parameter.getPageStart()-i);
				i++;
			}
		}
		
		return list;
		//return memberRepository.findAll();
	}

	@Override
	public MemberDto detail(String userId) {
		
		Optional<Member> optionalMember = memberRepository.findById(userId);
		if(!optionalMember.isPresent()) {
			return null;
		}
		
		Member member = optionalMember.get();
		
		return MemberDto.of(member);

	}

	@Override
	public boolean updateStatus(String userId, String userStatus) {

		Optional<Member> optionalMember = memberRepository.findById(userId);
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("Member information does not exist.");
		}
		
		Member member = optionalMember.get();
		
		member.setUserStatus(userStatus);
		memberRepository.save(member);
		
		return true;
	}

	@Override
	public boolean updatePassword(String userId, String userPass) {
		
		Optional<Member> optionalMember = memberRepository.findById(userId);
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("Member information does not exist.");
		}
		
		Member member = optionalMember.get();
		
		String encPassword = BCrypt.hashpw(userPass, BCrypt.gensalt());
		
		member.setUserPass(encPassword);
		memberRepository.save(member);
		
		return true;
	}

	@Override
	public ServiceResult updateMemberPassword(MemberInput parameter) {
		
		String userId = parameter.getUserId();
		
		Optional<Member> optionalMember = memberRepository.findById(userId);
		if(!optionalMember.isPresent()) {
			return new ServiceResult(false, "Member information does not exist.");
		}
		
		Member member = optionalMember.get();
		
		if(!PasswordUtils.equals(parameter.getUserPass(), member.getUserPass())) {
			return new ServiceResult(false, "Password does not match.");
		}
		
		String encPassword = PasswordUtils.encPassword(parameter.getNewPassword());
		
		member.setUserPass(encPassword);
		memberRepository.save(member);
		
		return new ServiceResult(true);
	}

	@Override
	public ServiceResult updateMember(MemberInput parameter) {
		
		String userId = parameter.getUserId();
		
		Optional<Member> optionalMember = memberRepository.findById(userId);
		if(!optionalMember.isPresent()) {
			return new ServiceResult(false, "Member information does not exist.");
		}
		
		Member member = optionalMember.get();
		
		member.setUserPhone(parameter.getUserPhone());
		member.setZipcode(parameter.getZipcode());
		member.setAddr(parameter.getAddr());
		member.setAddrDetail(parameter.getAddrDetail());
		member.setUpdDt(LocalDateTime.now());
		memberRepository.save(member);
		
		return new ServiceResult(true);
	}

	@Override
	public ServiceResult withdraw(String userId, String password) {
		
		Optional<Member> optionalMember = memberRepository.findById(userId);
		if(!optionalMember.isPresent()) {
			return new ServiceResult(false, "Member information does not exist.");
		}
		
		Member member = optionalMember.get();
		
		if(!PasswordUtils.equals(password, member.getUserPass())) {
			return new ServiceResult(false, "Password does not match.");
		}
		
		member.setUserName("Withdraw Member");
		member.setUserPhone("");
		member.setUserPass("");
		member.setRegDt(null);
		member.setUpdDt(null);
		member.setEmailAuthDt(null);
		member.setEmailAuthKey("");
		member.setEmailAuthYn(false);
		member.setResetPasswordKey("");
		member.setResetPasswordLimitDt(null);
		member.setUserStatus(MemberCode.MEMBER_STATUS_WITHDRAW);
		member.setZipcode("");
		member.setAddr("");
		member.setAddrDetail("");
		memberRepository.save(member);
		
		return new ServiceResult(true);
	}
	
	
}
