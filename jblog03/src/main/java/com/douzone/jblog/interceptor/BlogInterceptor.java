package com.douzone.jblog.interceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.BlogVo;



public class BlogInterceptor implements HandlerInterceptor {
	@Autowired
	private BlogService blogService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		ServletContext  application = request.getServletContext();
//		if(application.getAttribute("blog") == null) {
//			String urlPath = request.getRequestURI();
//			String[] urlArr = urlPath.split("/");
//			
//			BlogVo vo = blogService.getBlog(null);
//			application.setAttribute("blog", vo);
//			response.sendRedirect(request.getContextPath());
//			return false;
//		} 
		return true;
	}
	
}
