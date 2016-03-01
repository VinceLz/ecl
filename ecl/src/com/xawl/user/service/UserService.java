package com.xawl.user.service;

import com.xawl.user.dao.UserDao;
import com.xawl.user.domain.User;

public class UserService {
	private UserDao userDao=new UserDao();
	/**
	 * 注册
	 * 
	 * @param form
	 * @throws UserException
	 */
	public void regist(User form) throws UserException {
		User user = userDao.findByUsername(form.getUserName());
		if (user != null) {
			throw new UserException("用户名已被注册");
		}
		userDao.addUser(form);
	}

}
