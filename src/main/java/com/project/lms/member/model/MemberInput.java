package com.project.lms.member.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class MemberInput {
	
	private String userId;
	private String userName;
	private String userPhone;
	private String userPass;
	
	private String newPassword;
	
	private String zipcode;
	private String addr;
	private String addrDetail;

}
