package com.douzone.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.jblog.security.Auth;
import com.douzone.jblog.security.AuthUser;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;

	@RequestMapping({"", "/{pathNo1}", "/{pathNo1}/{pathNo2}"})
	public String index(
		@PathVariable("id") String id,
		@PathVariable("pathNo1") Optional<Long> pathNo1,
		@PathVariable("pathNo2") Optional<Long> pathNo2,
		Model model) {
		
		Long categoryNo = 0L;
		Long postNo = 0L;
		
		if(pathNo2.isPresent()) {
			categoryNo = pathNo1.get();
			postNo = pathNo2.get();
		} else if(pathNo1.isPresent()) {
			categoryNo = pathNo1.get();
		}
		
		BlogVo blogVo = blogService.getBlog(id);
		List<CategoryVo> categorylist = categoryService.getCategoryList(id);
		
		for(CategoryVo vo : categorylist) {
			System.out.println("tt" + vo);
		}
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("categorylist", categorylist);
		model.addAttribute("categoryNo", categoryNo);
		// return "BlogController.index(" + id + ", " + categoryNo + ", " + postNo + ")";
		return "blog/main";
	}
	
	@ResponseBody
	@Auth
	@RequestMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String id, @AuthUser UserVo authUser) {
		if(!authUser.getId().equals(id)) {
			return "redirect:/";
		}
		
		return "BlogController.adminBasic";
	}
}