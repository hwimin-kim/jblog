package com.douzone.jblog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.douzone.jblog.vo.UserVo;



public class LoginCheckInterceptor implements HandlerInterceptor {

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		// 로그인한 유저가 [로그인], [회원가입] 폼에 접근할 경우 접근 제한
		if(authUser != null) {
			response.sendRedirect(request.getContextPath());
			return false;
		}
		
		return true;
	}
	
}
