package com.hh.projectxx.base.manager;


import com.hh.projectxx.base.db.entity.User;
import com.hh.projectxx.base.db.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserManager {
	@Resource
	private UserMapper userMapper;
	
	public User getUserByPassport(String passport) {
		if (StringUtils.isEmpty(passport)) {
			return null;
		}
		return userMapper.selectByPassport(passport);
	}
	
	public boolean userSignUp(User user) {
		if (user == null || user.getPassport() == null || user.getPassword() == null) {
			return false;
		}
		try {
			userMapper.insert(user);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public User getUserById(int userId) {
		return userMapper.selectByPrimaryKey(userId);
	}
	
	public int updateUser(User user) {
		return userMapper.updateByPrimaryKeySelective(user);
	}
}
