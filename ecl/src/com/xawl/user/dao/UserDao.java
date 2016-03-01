package com.xawl.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.jdbc.TxQueryRunner;

import com.xawl.user.domain.User;

public class UserDao {
	private QueryRunner qr = new TxQueryRunner();
	/**
	 * 根据uid找到用户
	 * 
	 * @param uid
	 * @return
	 */
	public User findByUid(String uid) {
		try {
			String sql = "select * from user where uid=?";
			return qr.query(sql, new BeanHandler<User>(User.class), uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 按用户名查找用户
	 * 
	 * @param Username
	 * @return
	 */
	public User findByUsername(String Username) {
		try {
			String sql = "select * from user where username=?";
			return qr.query(sql, new BeanHandler<User>(User.class), Username);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 */
	public void addUser(User user) {
		try {
			String sql = "insert into  user (username,upassword,cid,utime,role) values(?,?,?,?,?)";
			Object[] params = {user.getUserName(),
					user.getuPassword(), user.getcId(),user.getuTime(),user.getRole() };
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据uid和valuename更新数据
	 * 
	 * @param uid
	 * @param valueName
	 * @param number
	 */
	public void updateUserByUid(String uid, String valueName, int number) {
		try {
			String sql = "UPDATE user SET " + valueName + "=" + valueName + "+"
					+ number + " where uid=?";
			qr.update(sql, uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
