package com.douzone.jblog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private PostRepository postRepository; 

	public Map<String, Object> getBlog(String blogId) {
		Map<String, Object> map = new HashMap<String, Object>();
		BlogVo blogVo = blogRepository.findAllById(blogId);
		List<CategoryVo> categorylist = categoryRepository.findAllbyId(blogId);
		
		map.put("blogVo", blogVo);
		map.put("categorylist", categorylist);
		
		return map;
	}
	
	public Map<String, Object> getBlog(String id, Long categoryNo, Long postNo) {
		Map<String, Object> map = getBlog(id);
		List<PostVo> postList = postRepository.findAll(((List<CategoryVo>) map.get("categorylist")).get(categoryNo.intValue()));
		map.put("postList", postList);
		map.put("categoryNo", categoryNo);
		map.put("postNo", postNo);
		
		return map;
	}

	public int updateBlog(BlogVo blogVo) {
		return blogRepository.update(blogVo);
	}

	public int checkId(String blogId) {
		return blogRepository.findById(blogId);
	}
}
