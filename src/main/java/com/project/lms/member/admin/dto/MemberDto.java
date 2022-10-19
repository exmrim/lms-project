package com.project.lms.member.admin.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.project.lms.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberDto {

	String userId;
	String userName;
	String userPhone;
	String userPass;
	
	LocalDateTime regDt;
	LocalDateTime updDt;
	
	boolean emailAuthYn;
	LocalDateTime emailAuthDt;
	String emailAuthKey;
	
	String resetPasswordKey;
	LocalDateTime resetPasswordLimitDt;
	
	boolean adminYn;
	String userStatus;
	
	long totalCount;
	long seq;
	
	private String zipcode;
	private String addr;
	private String addrDetail;
	
	public static MemberDto of(Member member) {
		
		return MemberDto.builder()
				.userId(member.getUserId())
				.userName(member.getUserName())
				.userPhone(member.getUserPhone())
				.regDt(member.getRegDt())
				.updDt(member.getUpdDt())
				.emailAuthYn(member.isEmailAuthYn())
				.emailAuthDt(member.getEmailAuthDt())
				.emailAuthKey(member.getEmailAuthKey())
				.resetPasswordKey(member.getResetPasswordKey())
				.resetPasswordLimitDt(member.getResetPasswordLimitDt())
				.adminYn(member.isAdminYn())
				.userStatus(member.getUserStatus())
				.zipcode(member.getZipcode())
				.addr(member.getAddr())
				.addrDetail(member.getAddrDetail())
				.build();
	}
	
	public String getRegDtText() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
		
		return regDt != null ? regDt.format(formatter) : "";
	}
	
	public String getUpdDtText() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
		
		return updDt != null ? updDt.format(formatter) : "";
	}
}
