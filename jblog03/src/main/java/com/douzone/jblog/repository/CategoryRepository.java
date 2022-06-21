package com.douzone.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		return insert(vo);
	}
	
	public int insert(CategoryVo categoryVo) {
		return sqlSession.insert("category.insert", categoryVo);
	}
	
	public List<CategoryVo> findAllbyId(String blogId) {
		return sqlSession.selectList("category.select", blogId);
	}

	public int delete(Long no) {
		return sqlSession.delete("category.delete", no);
	}

	public CategoryVo findNobyName(String categoryName, String blogId) {
		Map<String,Object> map = new HashMap<>();
		map.put("categoryName", categoryName);
		map.put("blogId", blogId);
		
		return sqlSession.selectOne("category.selectNo", map);
	}
}
