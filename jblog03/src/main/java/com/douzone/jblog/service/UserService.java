package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.repository.UserRepository;
import com.douzone.jblog.vo.UserVo;

@Service
public class UserService {	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public boolean addUser(UserVo userVo) {
		if(userRepository.insert(userVo) == 1) 		
			if(blogRepository.insert(userVo.getId()) == 1) 
				return categoryRepository.insert(userVo.getId()) == 1;
							
		return false;
	}

	public int checkUser(UserVo userVo) {
		return userRepository.findCountById(userVo.getId());
	}
	
	public UserVo getUser(UserVo userVo) {
		return userRepository.findById(userVo);
	}


}
