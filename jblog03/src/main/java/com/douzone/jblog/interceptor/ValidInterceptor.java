package com.douzone.jblog.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import com.douzone.jblog.service.BlogService;

public class ValidInterceptor implements HandlerInterceptor {
	@Autowired
	private BlogService blogService;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String userKey = BindingResult.MODEL_KEY_PREFIX + "userVo";
		BindingResult userResult = (BindingResult) modelAndView.getModel().get(userKey);
		
		if(userResult != null && userResult.hasErrors()) {
			modelAndView.addObject("result", userResult.getModel());
			modelAndView.setViewName("user/join");
		}
		
		String categoryKey = BindingResult.MODEL_KEY_PREFIX + "categoryVo";
		BindingResult categoryResult = (BindingResult) modelAndView.getModel().get(categoryKey);
		
		Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		String blogId = (String)pathVariables.get("id");
		
		if(categoryResult != null && categoryResult.hasErrors()) {
			Map<String, Object> map = blogService.getBlog(blogId);
			modelAndView.addAllObjects(map);
			modelAndView.addObject("result", categoryResult.getModel());
			modelAndView.setViewName("blog/admin/category");
		}
	}
	
	
}
