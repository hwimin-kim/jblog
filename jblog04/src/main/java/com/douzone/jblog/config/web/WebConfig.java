package com.douzone.jblog.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.douzone.config.web.FileUploadConfig;
import com.douzone.config.web.MessageConfig;
import com.douzone.config.web.MvcConfig;
import com.douzone.config.web.SecurityConfig;
import com.douzone.jblog.interceptor.BlogMainInterceptor;
import com.douzone.jblog.interceptor.LoginCheckInterceptor;
import com.douzone.jblog.interceptor.ValidInterceptor;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.douzone.jblog.controller"})
@Import({MvcConfig.class, SecurityConfig.class, MessageConfig.class, FileUploadConfig.class})
public class WebConfig implements WebMvcConfigurer {

	// interceptors
	@Bean
	public HandlerInterceptor loginCheckInterceptor() {
		return new LoginCheckInterceptor();
	}
	
	@Bean
	public HandlerInterceptor validInterceptor() {
		return new ValidInterceptor();
	}
	
	@Bean
	public HandlerInterceptor blogMainInterceptor() {
		return new BlogMainInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
		.addInterceptor(loginCheckInterceptor())
		.addPathPatterns("/user/login")
		.addPathPatterns("/user/join");
	
		registry
			.addInterceptor(validInterceptor())
			.addPathPatterns("/user/join")
			.addPathPatterns("/*/admin/category");
		
		registry
			.addInterceptor(blogMainInterceptor())
			.addPathPatterns("/{id:(?!assets).*}")
			.addPathPatterns("/{id:(?!assets).*}/*")
			.addPathPatterns("/{id:(?!assets).*}/*/*")
			.excludePathPatterns("/assets/**")
			.excludePathPatterns("/user/auth")
			.excludePathPatterns("/user/logout")
			.excludePathPatterns("/{id:(?!assets).*}/admin/*");
	}
}
