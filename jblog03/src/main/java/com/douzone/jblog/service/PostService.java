package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepository; 

	public int addPost(PostVo postVo) {
		return postRepository.insert(postVo);
	}

	public List<PostVo> getPostList(CategoryVo categoryVo) {
		return postRepository.findAll(categoryVo);
	}

	public boolean getPostNo(Long postNo, Long categoryNo) {
		
		// 포스트가 0개인 경우
		if(postRepository.findNoByCategoryNo(categoryNo) == 0)
			return true;
		
		// 찾으려는 포스트가 범위를 벗어난 경우
		if(postNo >= postRepository.findNoByCategoryNo(categoryNo))
			return false;
		
		return true;
	}

}
