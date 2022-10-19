package com.project.lms.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.lms.components.MailComponents;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class mainController {
	
	private final MailComponents mailComponents;
	
	//request  -> web -> server
	//response -> server -> web
	@RequestMapping("/")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		
		
		return "index";
	}
	
	@RequestMapping("/error/denied")
	public String errorDenied(HttpServletRequest request, HttpServletResponse response) {
		
		
		return "error/denied";
	}
}
	