package com.douzone.jblog.interceptor;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.vo.CategoryVo;

public class BlogMainInterceptor implements HandlerInterceptor {
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PostService postService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		String blogId = (String)pathVariables.get("id");
		String pathNo1 = (String) pathVariables.get("pathNo1");
		String pathNo2 = (String) pathVariables.get("pathNo2");
	
		Long categoryNo;
		Long postNo;
		
		// 존재하지 않는 id의 블로그로 접근할 경우
		if(blogService.checkId(blogId) == 0) {
			response.sendRedirect(request.getContextPath());
			return false;
		}
		
		try {	
			// null check && Long Convert
			categoryNo = Long.parseLong(Optional.ofNullable(pathNo1).orElse("0"));
			postNo = Long.parseLong(Optional.ofNullable(pathNo2).orElse("0"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/" + blogId);
			return false;
		}
						
		// 블로그에 해당 카테고리가 없는 경우 
		if(!categoryService.getCategoryNo(categoryNo, blogId)) {
			response.sendRedirect(request.getContextPath() + "/" + blogId);
			return false;
		}
		
		List<CategoryVo> list = categoryService.getCategoryList(blogId);
		CategoryVo categoryVo =list.get(Long.valueOf(categoryNo).intValue());

		// 블로그에 해당 포스트가 없는 경우
		if(!postService.getPostNo(postNo, (long) categoryVo.getNo())) {
			response.sendRedirect(request.getContextPath() + "/" + blogId);
			return false;
		}
		
		return true;
	}
}
