package com.douzone.jblog.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
		Map<String, Object> map = blogService.getBlog(id);
		List<PostVo> postList = postService.getPostList(((List<CategoryVo>) map.get("categorylist")).get(categoryNo.intValue()));
		map.put("postList", postList);
		map.put("categoryNo", categoryNo);
		map.put("postNo", postNo);
		
		model.addAllAttributes(map);
		return "blog/main";
	}
	
	@Auth
	@RequestMapping(value="/admin/basic", method=RequestMethod.GET)
	public String adminBasic(@PathVariable("id") String id, Model model) {
		Map<String, Object> map = blogService.getBlog(id);
		
		model.addAllAttributes(map);
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
	public String adminCategory(@PathVariable("id") String id, Model model) {
		Map<String, Object> map = blogService.getBlog(id);
		
		model.addAllAttributes(map);
		return "blog/admin/category";
	}
	
	@Auth
	@RequestMapping(value="/admin/category", method=RequestMethod.POST)
	public String adminCategory(@PathVariable("id") String id, @ModelAttribute CategoryVo categoryVo) {
		categoryVo.setBlogId(id);
		categoryService.addCategory(categoryVo);
		
		return "redirect:/" + id + "/admin/category";
	}
	
	// post도 삭제해야하는지 고려??
	@Auth
	@RequestMapping(value="/admin/category/delete/{no}", method=RequestMethod.GET)
	public String adminCategoryDelete(@PathVariable("id") String id, @PathVariable("no") Long no) {
		categoryService.removeCategory(no);
		
		return "redirect:/" + id + "/admin/category";
	}
	
	@Auth
	@RequestMapping(value="/admin/write", method=RequestMethod.GET)
	public String adminWrite(@PathVariable("id") String id, Model model) {
		Map<String, Object> map = blogService.getBlog(id);
		
		model.addAllAttributes(map);
		return "blog/admin/write";
	}
	
	@Auth
	@RequestMapping(value="/admin/write", method=RequestMethod.POST)
	public String adminWrite(@PathVariable("id") String id, @ModelAttribute PostVo postVo, @RequestParam(value="category", required=true, defaultValue="") String category) {
		CategoryVo categoryVo = categoryService.getCategoryNo(category, id);
		postVo.setCategoryNo(categoryVo.getNo());
		postService.addPost(postVo);
		
		return "redirect:/" + id;
	}
}