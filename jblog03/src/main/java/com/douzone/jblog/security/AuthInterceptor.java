package com.douzone.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.douzone.jblog.vo.UserVo;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 1. Handler 종류 확인
		if(handler instanceof HandlerMethod == false) {
			// DefaultServletHandler가 처리하는 정적 자원
			return true;
		}
		
		// 2. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		// 3. Handler Method의 @Auth 받아보기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		// 4. Handler Method에 @Auth가 없으면 Type 에 붙어 있는 지 확인
		if(auth == null) 
			auth = handlerMethod.getBeanType().getAnnotation(Auth.class);	
		
		// 5. Type과 Method 모두에 @Auth가 안 붙어 있는 경우
		if(auth == null)
			return true;

		// 6. Handler Method에 @Auth가 붙어 있기 때문에 인증(Authentication) 여부 확인
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		// 7. @Auth 가 적용되어 있지만 인증이 되어 있지 않음
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		String authUserId = authUser.getId();	
		String urlPath = request.getRequestURI();
		String[] urlArr = urlPath.split("/");
		
		// 8. @Auth 가 적용, 로그인한 유저가 다른 유저의 [블로그 관리]에 접근하는 것을 제한
		if(urlArr.length > 3) {	
			if(!authUserId.equals(urlArr[2]) && urlArr[3].equals("admin")) {		
				response.sendRedirect(request.getContextPath());
				return false;
			}
		}
		return true;
	}
	
}
