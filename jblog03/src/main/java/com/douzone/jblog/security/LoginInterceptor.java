package com.douzone.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.UserVo;

public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private UserVo userVo;
	
	@Autowired
	private UserService userService;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		userVo.setId(request.getParameter("id"));
		userVo.setPassword(request.getParameter("password"));
		
		UserVo authUser = userService.getUser(userVo);
		if(authUser == null) {
			request.setAttribute("userVo", userVo);		
			request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response);
			return false;
		}
			
		/* session 처리 */
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		
		response.sendRedirect(request.getContextPath() + "/" + authUser.getId());
		
		return false;
	}
}
