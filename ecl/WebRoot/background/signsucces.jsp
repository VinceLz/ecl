<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ECL实验室</title>
<link rel="shortcut icon" href="images/logo_small.png">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/bootstrap.css">
</head>
<body>
	<div class="top" id="all">
		<!--顶部导航-->
		<div class="top_nav">
			<nav class="navbar navbar-dark bg-inverse">
			<div class="nav_width">
				<a class="navbar-brand" href="#"> <img src="images/logo1.png" />
					<p>ECL实验室</p>
				</a>
				<ul class="nav navbar-nav pull-right">
					<li class="nav-item active"><a class="nav-link"
						href="<c:url value='user/login.jsp' />">登录<span
							class="sr-only">(current)</span></a></li>
					<li class="nav-item"><a class="nav-link" href="#">帮助中心</a></li>
				</ul>
			</div>
			</nav>
		</div>
	</div>
	<div class="middle clearfix">
		<div class="signtitle"></div>
		<div class="signsucess">
			<img src="images/sign-check-icon.png" />
			<h3>恭喜您注册成功！</h3>
			<a href="<c:url value='background/login.jsp' />">返回首页登陆</a>
		</div>
	</div>
	<!--底部-->
	<div class="footer">
		<p>Copyright &copy; 2015 信息工程学院版权所有</p>
	</div>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.js"></script>
</body>
</html>
