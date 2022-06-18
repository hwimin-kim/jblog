package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.UserRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.UserVo;

@Service
public class UserService {	
	@Autowired
	private BlogVo blogVo;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BlogRepository blogRepository;
	
	public void addUser(UserVo userVo, String defaultLogo) {
		userRepository.insert(userVo);
		
		blogVo.setId(userVo.getId());
		blogVo.setTitle(userVo.getId() + "의 블로그 입니다.");
		blogVo.setLogo(defaultLogo);
			
		blogRepository.insert(blogVo);
		
	}

	public int checkUser(UserVo userVo) {
		return userRepository.findCountById(userVo.getId());
	}

}
