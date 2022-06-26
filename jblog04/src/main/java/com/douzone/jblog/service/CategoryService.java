package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.vo.CategoryVo;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<CategoryVo> getCategoryList(String blogId) {
		return categoryRepository.findAllbyId(blogId);
	}

	public int addCategory(CategoryVo categoryVo) {
		return categoryRepository.insert(categoryVo);
	}

	public int removeCategory(Long no) {
		return categoryRepository.delete(no);
	}

	public CategoryVo getCategoryNo(String categoryName, String blogId) {
		return categoryRepository.findNobyNameANDId(categoryName, blogId);
	}

	public boolean getCategoryNo(Long categoryNo, String blogId) {
		if(categoryNo >= categoryRepository.findNobyId(blogId)) {
			return false;
		}
		return true;
	}

}
