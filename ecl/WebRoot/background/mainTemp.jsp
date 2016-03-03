<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/style/news.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/bootstrap/js/jQuery.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>

<script src="${pageContext.request.contextPath}/js/ckeditor/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<%
	String mainPage=(String)request.getAttribute("mainPage");
	if(mainPage==null || mainPage.equals("")){
		mainPage="/background/default.jsp";
	}
%>
<title>ECL实验室后台管理</title>
	
</head>
<body>
<div class="container">
	<jsp:include page="/background/common/head.jsp"/>
	<div class="row-fluid">
		<div class="span3">
			<div class="newsMenu">
				<ul class="nav nav-tabs nav-stacked">
				  <li><a href="${pageContext.request.contextPath}/background/mainTemp.jsp"><strong>主页</strong></a></li>
				  <li><a href="#"><strong>首页信息管理</strong></a></li>
				  <li><a href="${pageContext.request.contextPath}/news?action=preSave">&nbsp;&nbsp;信息添加</a></li>
				  <li><a href="${pageContext.request.contextPath}/news?action=backList">&nbsp;&nbsp;信息维护</a></li>
				    <li><a href="${pageContext.request.contextPath}/news?action=backList">&nbsp;&nbsp;图片维护</a></li>
				  <li><a href=""><strong>网盘管理</strong></a></li>
				  <li><a href="${pageContext.request.contextPath}/UserServlet?method=file">&nbsp;&nbsp;文件管理</a></li>
				  <li><a href="${pageContext.request.contextPath}/UserServlet?method=getUserList">&nbsp;&nbsp;用户管理</a></li>
				  <li><a href=""><strong>友情连接管理</strong></a></li>
				  <li><a href="${pageContext.request.contextPath}/link?action=preSave">&nbsp;&nbsp;友情连接添加</a></li>
				  <li><a href="${pageContext.request.contextPath}/link?action=backList">&nbsp;&nbsp;友情连接维护</a></li>
				  <li><a href=""><strong>系统管理</strong></a></li>
				  <li><a href="">&nbsp;&nbsp;密码修改</a></li>
				  <li><a href="">&nbsp;&nbsp;待定</a></li>
				</ul>
			</div>
		</div>
		<div class="span9">
			<jsp:include page="<%=mainPage %>"/>
		</div>
	</div>
	<jsp:include page="/foreground/common/foot.jsp"/>
</div>
</body>
</html>