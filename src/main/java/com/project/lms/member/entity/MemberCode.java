package com.project.lms.member.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public interface MemberCode {
	
	String MEMBER_STATUS_REQ = "REQ";
	
	String MEMBER_STATUS_ING = "ING";
	
	String MEMBER_STATUS_STOP = "STOP";
	
	String MEMBER_STATUS_WITHDRAW = "WITHDRAW";
	
}
