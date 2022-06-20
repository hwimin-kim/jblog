package com.douzone.jblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.jblog.security.AuthUser;
import com.douzone.jblog.vo.UserVo;

@Controller
public class MainController {

	@RequestMapping("/")
	public String index(@AuthUser UserVo authUser, Model model) {
		model.addAttribute("authUser", authUser);
		return "main/index";
	}
}
