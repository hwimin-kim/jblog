package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;

@Repository
public class BlogRepository {	
	@Autowired
	private BlogVo vo;
	
	@Autowired
	private SqlSession sqlSession;
		
	public int insert(String userVoId) {
		vo.setId(userVoId);
		vo.setTitle(userVoId + "의 블로그 입니다.");
		
		return sqlSession.insert("blog.insert", vo);
	}

	public BlogVo findAllById(String blogId) {
		return sqlSession.selectOne("blog.select", blogId);
	}

	public int update(BlogVo blogVo) {
		return sqlSession.update("blog.update", blogVo);
	}

}
