package com.project.lms.category.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.lms.admin.dto.CategoryDto;
import com.project.lms.admin.service.CategoryService;
import com.project.lms.category.model.CategoryInput;
import com.project.lms.member.admin.dto.MemberDto;
import com.project.lms.member.admin.model.MemberParam;

import com.project.lms.member.admin.model.MemberInput;
import com.project.lms.member.entity.Member;
import com.project.lms.member.service.MemberService;
import com.project.lms.util.PageUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminCategoryController {
	
	private final CategoryService categoryService;
	
	@GetMapping("/admin/category/list.do")
	public String list(Model model, MemberParam parameter) {
		
		List<CategoryDto> list = categoryService.list();
		model.addAttribute("list", list);
		
		return "admin/category/list";
	}
	
	@PostMapping("/admin/category/add.do")
	public String add(Model model, CategoryInput parameter) {
		
		boolean result = categoryService.add(parameter.getCategoryName());
		
		return "redirect:/admin/category/list.do";
	}
	
	@PostMapping("/admin/category/delete.do")
	public String delete(Model model, CategoryInput parameter) {
		
		boolean result = categoryService.delete(parameter.getId());
		
		return "redirect:/admin/category/list.do";
	}
	
	@PostMapping("/admin/category/update.do")
	public String update(Model model, CategoryInput parameter) {
		
		boolean result = categoryService.update(parameter);
		
		return "redirect:/admin/category/list.do";
	}
	
}
