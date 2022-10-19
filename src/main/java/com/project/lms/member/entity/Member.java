package com.project.lms.member.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Member implements MemberCode {
	
	//@Column(name="user_id", nullable=false)
	@Id
	private String userId;
	
	private String userName;
	private String userPhone;
	private String userPass;
	private LocalDateTime regDt;
	private LocalDateTime updDt;
	
	private boolean emailAuthYn;
	private LocalDateTime emailAuthDt;
	private String emailAuthKey;
	
	private String resetPasswordKey;
	private LocalDateTime resetPasswordLimitDt;	
	
	private boolean adminYn;
	
	private String userStatus;
	
	private String zipcode;
	private String addr;
	private String addrDetail;
	
}
