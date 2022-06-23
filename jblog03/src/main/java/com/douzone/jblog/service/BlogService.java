package com.douzone.jblog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	public Map<String, Object> getBlog(String blogId) {
		Map<String, Object> map = new HashMap<String, Object>();
		BlogVo blogVo = blogRepository.findAllById(blogId);
		List<CategoryVo> categorylist = categoryRepository.findAllbyId(blogId);
		
		map.put("blogVo", blogVo);
		map.put("categorylist", categorylist);
		
		return map;
	}

	public int updateBlog(BlogVo blogVo) {
		return blogRepository.update(blogVo);
	}

}
