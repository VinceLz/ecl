package com.xawl.user.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

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
			String sql = "insert into  user (uId,userName,uPassword,cId,uTime,role) values(?,?,?,?,?,?)";
			Object[] params = {user.getuId(),user.getUserName(),
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
	/*
	 * 返回所有普通会员
	 */
	public List<User> getUserList() throws SQLException{
		String sql="select * from user";
		return qr.query(sql, new BeanListHandler<User>(User.class));
	}

	//删除一个用户
	public Boolean deleteUser(String uid) throws SQLException {
		String sql="delete from user where uId=?";
		return qr.update(sql,uid)==1; 
	}
	///=查找uid----cid
	public String findUserToCid(String uid) throws SQLException {
		String sql="select * from user where uId=?";
		return (String)qr.query(sql, new MapHandler(),uid).get("cId");
	}
}
