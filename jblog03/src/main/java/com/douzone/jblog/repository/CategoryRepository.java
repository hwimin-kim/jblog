package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {
	@Autowired
	private CategoryVo vo;
	
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(String blogVoId) {
		vo.setName("기타");
		vo.setDescription("카테고리를 지정하지 않는 경우");
		vo.setBlogId(blogVoId);
		
		return sqlSession.insert("category.insert", vo);
	}

}
