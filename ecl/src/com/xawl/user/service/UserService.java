package com.xawl.user.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;

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
		else {
			userDao.addUser(form);
		}
	}
	public User login(User form) throws UserException {
		User user = userDao.findByUsername(form.getUserName());
		if (user == null) {
			throw new UserException("用户不存在");
		}
		if (!user.getuPassword().equals(form.getuPassword())) {
			throw new UserException("密码不正确");
		}
		return user;
	}
	/*
	 * 得到所有普通会员
	 */
	public List<User> getUserList(){
		try {
			return userDao.getUserList();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//删除用户
	public Boolean deleteUser(String uid) {
			try {
				return userDao.deleteUser(uid);
			} catch (SQLException e) {
			throw new RuntimeException(e);
			}
					 
	}
	
	public String findUserToCid(String uid){
		try {
			return userDao.findUserToCid(uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
