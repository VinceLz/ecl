package com.xawl.user.domain;

import java.util.Date;

public class User {
	private String uId;
	private String userName;
	private String uPassword;
	private String cId;
	private String uTime;
	private int role;
	private long fileSize;
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getuPassword() {
		return uPassword;
	}
	public void setuPassword(String uPassword) {
		this.uPassword = uPassword;
	}
	public String getcId() {
		return cId;
	}
	public void setcId(String cId) {
		this.cId = cId;
	}
	
	public String getuTime() {
		return uTime;
	}
	public void setuTime(String uTime) {
		this.uTime = uTime;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	@Override
	public String toString() {
		return "User [uId=" + uId + ", userName=" + userName + ", uPassword="
				+ uPassword + ", cId=" + cId + ", uTime=" + uTime + ", role="
				+ role + ", fileSize=" + fileSize + "]";
	}
	
	
}
