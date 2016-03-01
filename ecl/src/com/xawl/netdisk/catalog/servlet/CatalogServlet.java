package com.xawl.netdisk.catalog.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Util.Tool;
import cn.itcast.servlet.BaseServlet;

import com.xawl.netdisk.catalog.domain.Catalog;
import com.xawl.netdisk.catalog.service.CatalogService;

public class CatalogServlet extends BaseServlet {
	private CatalogService service = new CatalogService();

	
	
	public String myCatalog(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String cid = request.getParameter("cid");
		if (cid == null || cid.isEmpty()) {
			cid = "1";
		}
		Catalog c = service.findByCidToCatalog(cid);
		request.setAttribute("catalog", c);
		return "f:/home.jsp";
	}

	// 创建文件夹
	public String createCatalog(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String cid = request.getParameter("cid");// 拿到他的父cid

		String cName = request.getParameter("name");
		cName = java.net.URLDecoder.decode(cName, "UTF-8");

		Catalog parent = service.findByCid(cid);
		Catalog c = new Catalog();
		// 开始封装文件夹的信息
		c.setcId(Tool.randomId());
		c.setParent(parent);
		c.setcName(cName);
		SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd");
		String newTime = simp.format(new Date()).toString();
		c.setcDate(newTime);
		c.setIsShare("0");
		service.createCatalog(c);
		// 然后在转发到当前目录下
		/*
		 * 这有2种方案，1 是用ajax创建文件夹 2是用servlet创建 先用2 ，
		 */
		return "r:/CatalogServlet?method=myCatalog&cid=" + cid;
	}

	public String deleteCatalog(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		String pid = request.getParameter("pid");
		service.deleteByCatalog(cid, this.getServletContext());
		return "r:/CatalogServlet?method=myCatalog&cid=" + pid;

	}
	

}
