package com.project.lms.member.admin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.lms.course.controller.BaseController;
import com.project.lms.member.admin.dto.MemberDto;
import com.project.lms.member.admin.model.MemberParam;
import com.project.lms.member.admin.model.MemberInput;
import com.project.lms.member.entity.Member;
import com.project.lms.member.service.MemberService;
import com.project.lms.util.PageUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminMemberController extends BaseController{
	
	private final MemberService memberService;
	
	@GetMapping("/admin/member/list.do")
	public String list(Model model, MemberParam parameter) {
		
		parameter.init();
		
		List<MemberDto> members = memberService.list(parameter);
		
		
		long totalCount = 0;
		if(members != null && members.size() > 0) {
			totalCount = members.get(0).getTotalCount();
		}
		String queryString = parameter.getQueryString();
		String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);
		
		model.addAttribute("list", members);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pager", pagerHtml);
		
		return "admin/member/list";
	}
	
	@GetMapping("/admin/member/detail.do")
	public String detail(Model model, MemberParam parameter) {
		
		parameter.init();
		
		MemberDto member = memberService.detail(parameter.getUserId());
		model.addAttribute("member", member);
		
		return "admin/member/detail";
	}
	
	@PostMapping("/admin/member/status.do")
	public String status(Model model, MemberInput parameter) {
		
		boolean result = memberService.updateStatus(parameter.getUserId(), parameter.getUserStatus());
		
		return "redirect:/admin/member/detail.do?userId=" + parameter.getUserId();
	}
	
	@PostMapping("/admin/member/password.do")
	public String password(Model model, MemberInput parameter) {
		
		boolean result = memberService.updatePassword(parameter.getUserId(), parameter.getUserPass());
		
		return "redirect:/admin/member/detail.do?userId=" + parameter.getUserId();
	}
}
