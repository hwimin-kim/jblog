package com.douzone.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.jblog.security.AuthUser;
import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
		
	@RequestMapping("/login")
	public String login(@AuthUser UserVo authUser, Model model) {
		// 이미 로그인한 경우
		if(authUser != null)
			return "redirect:/";
					
		model.addAttribute("authUser", authUser);
		return "user/login";
	}
	
	@RequestMapping("/auth")
	public void auth() {
	}
	
	@RequestMapping("/logout")
	public void logout() {
	}	
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@AuthUser UserVo authUser) {
		// 이미 로그인한 경우
		if(authUser != null)
			return "redirect:/";
		
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute("userVo") UserVo userVo, Model model) {
		// 이미 등록된 회원(id)인 경우
		if(userService.checkUser(userVo) != 0) {
			model.addAttribute("userVo", userVo);
			return "user/join";
		}
		
		userService.addUser(userVo);
		
		return "redirect:/user/joinsuccess";
	}
		
	@RequestMapping("/joinsuccess")
	public String joinsuccess(@AuthUser UserVo authUser, Model model) {
		model.addAttribute("authUser", authUser);
		return "user/joinsuccess";
	}
}
