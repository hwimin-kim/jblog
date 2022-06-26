package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Repository
public class PostRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(PostVo postVo) {
		return sqlSession.insert("post.insert", postVo);
	}

	public List<PostVo> findAll(CategoryVo categoryVo) {
		return sqlSession.selectList("post.select", categoryVo);
	}

	public int findNoByCategoryNo(Long categoryNo) {
		return sqlSession.selectOne("post.selectNoByCategoryNo", categoryNo);
	}

}
