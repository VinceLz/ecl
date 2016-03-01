package com.xawl.user.servlet;



import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.UserException;

import Util.Tool;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import com.xawl.netdisk.catalog.domain.Catalog;
import com.xawl.netdisk.catalog.service.CatalogService;
import com.xawl.user.domain.User;
import com.xawl.user.service.UserService;

public class UserServlet extends BaseServlet {
	private UserService userService=new UserService();
	private CatalogService catalogService=new CatalogService();
	public String regist(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User form=CommonUtils.toBean(request.getParameterMap(), User.class);
		//前台只能拿到user  和 password  其余的 需要自己封装
		
		if (form.getUserName()== null || form.getUserName().trim().isEmpty()
				|| form.getUserName().contains(" ")) {
			request.setAttribute("unmsg", "用户名不能为空，或者包含空格");
			request.setAttribute("form", form);
			return "f:/background/regist.jsp";
		}
		if (form.getuPassword() == null || form.getuPassword().trim().isEmpty()
				|| form.getuPassword().contains(" ")) {
			request.setAttribute("pwmsg", "密码不能为空，或者包含空格");
			request.setAttribute("form", form);
			return "f:/background/regist.jsp";
		}
			
		//后台验证完毕 开发封装其他信息
		String cid=Tool.randomId();
		form.setcId(cid);
		//还需要马上创建目录 
		Catalog catalog1=new Catalog();
		catalog1.setcName(form.getUserName());
		catalog1.setcDate(Tool.getCurrentTime());
		catalog1.setCf(Tool.randomId());
		catalogService.createRootCatalig(catalog1);
		form.setuTime(Tool.getCurrentTime());
		form.setRole(0);  //0是普通用户 1是超级管理员
		try {
			userService.regist(form);
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f:/background/regist.jsp";
		}

		return "f:/background/signsucces.jsp";
	}

}
