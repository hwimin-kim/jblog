package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.vo.BlogVo;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;

	public BlogVo getBlog(String blogId) {
		return blogRepository.findAllById(blogId);
	}

	public int updateBlog(BlogVo blogVo) {
		return blogRepository.update(blogVo);
	}

}
