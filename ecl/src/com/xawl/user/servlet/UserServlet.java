package com.xawl.user.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Util.Tool;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import com.xawl.netdisk.catalog.domain.Catalog;
import com.xawl.netdisk.catalog.service.CatalogService;
import com.xawl.user.domain.User;
import com.xawl.user.service.UserService;

public class UserServlet extends BaseServlet {
	private UserService userService = new UserService();
	private CatalogService catalogService = new CatalogService();

	public String regist(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		// 前台只能拿到user 和 password 其余的 需要自己封装

		if (form.getUserName() == null || form.getUserName().trim().isEmpty()
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

		// 是普通用户 1是超级管理员
		try {
			String cid = Tool.randomId();
			form.setcId(cid);
			form.setuTime(Tool.getCurrentTime());
			form.setRole(0);
			form.setuId(Tool.randomId());
			userService.regist(form);
			// 后台验证完毕 开发封装其他信息

			// 还需要马上创建目录
			Catalog catalog1 = new Catalog();
			catalog1.setcId(cid);
			catalog1.setcName(form.getUserName());
			catalog1.setcDate(Tool.getCurrentTime());
			catalog1.setCf(Tool.randomId());
			catalogService.createRootCatalig(catalog1);
			// 0
		} catch (com.xawl.user.service.UserException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f:/background/regist.jsp";
		}

		return "f:/background/signsucces.jsp";
	}

	// 登录
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		System.out.println(form.toString());
		if (form.getUserName() == null || form.getUserName().trim().isEmpty()
				|| form.getUserName().contains(" ")) {
			request.setAttribute("msg", "用户名不能为空，或者包含空格");
			request.setAttribute("form", form);
			return "f:/background/login.jsp";
		}
		if (form.getuPassword() == null || form.getuPassword().trim().isEmpty()
				|| form.getuPassword().contains(" ")) {
			request.setAttribute("msg", "密码不能为空，或者包含空格");
			request.setAttribute("form", form);
			return "f:/background/login.jsp";
		}
		try {
			User user = userService.login(form);

			request.getSession().invalidate();
			request.getSession().setAttribute("user", user);
			return "r:/CatalogServlet?method=myCatalogRoot";
		} catch (com.xawl.user.service.UserException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f:/background/login.jsp";
		}
	}
	// 查找所有用户并返回文件管理
	public String file(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 先查找所有user用户
		List<User> userlist = userService.getUserList();
		request.setAttribute("fmanager", userlist);
		request.setAttribute("mainPage", "comment/filemanager.jsp");
		return "f:/background/mainTemp.jsp";

	}

	// 退出功能
	public String quit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getSession().invalidate();
		return "r:/background/login.jsp";
	}

	// 管理用户 返回普通会员的list
	public String getUserList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<User> userList = userService.getUserList();
		request.setAttribute("userlist", userList);
		request.setAttribute("mainPage", "comment/commentList.jsp");
		return "f:/background/mainTemp.jsp";
	}

	// 删除用户
	public String ajaxDeleteUser(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("访问到了");
		String uid = request.getParameter("uid");
		System.out.println("开始删除了uid=" + uid);
		if (uid != null && !uid.isEmpty() && !uid.equals("")) {
			try {
				System.out.println("进来了");

				String cid = userService.findUserToCid(uid);
				catalogService.deleteAll(cid);

				Boolean b = userService.deleteUser(uid);
				response.getWriter().print(b);
				// 用户删除成功后需要把用户下面的所有数据信息删除已经删除本地文件
				// 但是考虑到数据的安全性，所有本地的文件不自动删除，由管理员手动删除。数据无价！！
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().print("false");
				System.out.println("删除失败");
			}
		}
		return null;
	}


	
}
