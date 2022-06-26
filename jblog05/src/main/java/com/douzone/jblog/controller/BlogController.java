package com.douzone.jblog.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.security.Auth;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.FileUploadService;
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileUploadService fileUploadService;

	@RequestMapping({"", "/{pathNo1}", "/{pathNo1}/{pathNo2}"})
	public String index(@PathVariable("id") String id, @PathVariable(value="pathNo1", required=false) String pathNo1, @PathVariable(value="pathNo2", required=false) String pathNo2, Model model) {	
		Long categoryNo = Long.parseLong(Optional.ofNullable(pathNo1).orElse("0"));
		Long postNo = Long.parseLong(Optional.ofNullable(pathNo2).orElse("0"));
					
		model.addAllAttributes(blogService.getBlog(id, categoryNo, postNo));
		return "blog/main";
	}
	
	@Auth
	@RequestMapping(value="/admin/basic", method=RequestMethod.GET)
	public String adminBasic(@PathVariable("id") String id, Model model) {	
		model.addAllAttributes(blogService.getBlog(id));
		return "blog/admin/basic";
	}
	
	@Auth
	@RequestMapping(value="/admin/basic", method=RequestMethod.POST)
	public String adminBasic(@PathVariable("id") String id, BlogVo blogVo, @RequestParam(value="file", required=true, defaultValue="") MultipartFile multipartFile) {
		blogVo.setId(id);
		blogVo.setLogo(fileUploadService.restore(multipartFile));
		blogService.updateBlog(blogVo);
				
		return "redirect:/" + id;
	}
	
	@Auth
	@RequestMapping(value="/admin/category", method=RequestMethod.GET)
	public String adminCategory(@PathVariable("id") String id, @ModelAttribute("categoryVo") CategoryVo categoryVo, Model model) {	
		model.addAllAttributes(blogService.getBlog(id));
		return "blog/admin/category";
	}
	
	// 카테고리 중복 체크
	@Auth
	@RequestMapping(value="/admin/category", method=RequestMethod.POST)
	public String adminCategory(@PathVariable("id") String id, @ModelAttribute("categoryVo")@Valid CategoryVo categoryVo, BindingResult result) {
		if(!result.hasErrors()) {
			categoryVo.setBlogId(id);
			categoryService.addCategory(categoryVo);
		}
		
		return "redirect:/" + id + "/admin/category";
	}
	
	@Auth
	@RequestMapping(value="/admin/category/delete/{no}", method=RequestMethod.GET)
	public String adminCategoryDelete(@PathVariable("id") String id, @PathVariable("no") Long no) {
		categoryService.removeCategory(no);	
		return "redirect:/" + id + "/admin/category";
	}
	
	@Auth
	@RequestMapping(value="/admin/write", method=RequestMethod.GET)
	public String adminWrite(@PathVariable("id") String id, Model model) {		
		model.addAllAttributes(blogService.getBlog(id));
		return "blog/admin/write";
	}
	
	@Auth
	@RequestMapping(value="/admin/write", method=RequestMethod.POST)
	public String adminWrite(@PathVariable("id") String id, @ModelAttribute PostVo postVo, @RequestParam(value="category", required=true, defaultValue="") String category) {
		postVo.setCategoryNo(categoryService.getCategoryNo(category, id).getNo());
		postService.addPost(postVo);
		
		return "redirect:/" + id;
	}
}