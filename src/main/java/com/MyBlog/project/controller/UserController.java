package com.MyBlog.project.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.MyBlog.project.config.auth.PrincipalDetails;

@Controller
public class UserController {
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
    @GetMapping("/user/updateForm")
    public String updateForm(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        model.addAttribute("principal", principalDetails.getUser());
        return "user/updateForm";
    }
}
