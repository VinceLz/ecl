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
<script type="text/javascript">
	window.onload = function check() {
		var input1 = document.getElementById("input1");
		var input2 = document.getElementById("input2");
		var input3 = document.getElementById("input3");
		var input4 = document.getElementById("input4");
		var span1 = document.getElementById("span1");
		var span2 = document.getElementById("span2");
		var span3 = document.getElementById("span3");
		var submit = document.getElementById("submit");
		var flag = [false,false,false];
		
		submit.onclick = function(){
			if(!(flag[0]&&flag[1]&&flag[2])){
				return false;	
			}
		}
		input1.onkeyup = function() {
			var input1Value = input1.value;
			if (input1Value.length > 10 || input1Value.length < 4) {
				span1.innerText = "用户名的长度必须在4到10个字符";
				flag[0] = false;
			} else {
				span1.innerText = "";
				flag[0] = true;
			}
			if(flag[0]&&flag[1]&&flag[2]){
				submit.className="btn btn-primary-outline";
			}else{
				submit.className="btn btn-primary-outline disabled";
			}
		}
		input2.onkeyup = function() {
			var input2Value = input2.value;
			if (input2Value.length > 20 || input2Value.length < 8) {
				span2.innerText = "密码的长度必须在8到20个字符";
				flag[1] = false;
			} else {
				span2.innerText = "";
				flag[1] = true;
			}
			if(flag[0]&&flag[1]&&flag[2]){
				submit.className="btn btn-primary-outline";
			}else{
				submit.className="btn btn-primary-outline disabled";
			}
		}
		input3.onkeyup = function() {
			if (input2.value != input3.value) {
				span3.innerText = "需与第一次输入的密码保持一致";
				flag[2] = false;
			} else {
				span3.innerText = "";
				flag[2] = true;
			}
			if(flag[0]&&flag[1]&&flag[2]){
				submit.className="btn btn-primary-outline";
			}else{
				submit.className="btn btn-primary-outline disabled";
			}
		}

		input4.onkeydown = function() {
			var input4Value = input4.value;
			if (input4Value.length > 3) {
				if (event.keyCode != 8) {
					return false;
				}
			}
		}
	}
</script>
<body>
<div class="top" id="all">
   <!--顶部导航-->
	<div class="top_nav">
      <nav class="navbar navbar-dark bg-inverse">
      	<div class="nav_width">
          <a class="navbar-brand" href="#">
            <img src="images/logo1.png" width="100px" height="100px"/>
            <p>ECL实验室</p>
          </a>
          <ul class="nav navbar-nav pull-right">
            <li class="nav-item active">
              <a class="nav-link" href="<c:url value='user/login.jsp' />">登录<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">帮助中心</a>
            </li>
          </ul>
        </div>
      </nav>
    </div>
    
  
  
</div>
<div class="middle sign_middle clearfix">
	 <div class="signtitle">
     </div> 
     <div class="inputlist">
     <form name="form2"  action="<c:url value='/UserServlet' />" method="post" onsubmit="return checkform2()">
         <input type="hidden" name="method" value="regist" />
         <div class="input-group">
          <p id="span1" style="color: red;">${unmsg }</p>
           <input id="input1" type="text" class="form-control" name="userName" placeholder="请输入用户名" aria-describedby="basic-addon1" value="${form.userName }">
            <label>用户名</label> 
         </div>
         
         <div class="input-group">
        <p id="span2" style="color: red;">${pwmsg}</p>
           <input id="input2" type="password" class="form-control" name="uPassword" placeholder="请输入密码" aria-describedby="basic-addon1"  value="${form.uPassword }" >
            <label>密码</label>
         </div>
         
         <div class="input-group">
          <p id="span3" style="color: red;"></p>
           <input id="input3" type="password" class="form-control" name="password2" placeholder="请再次输入密码" aria-describedby="basic-addon1">
           <label>密码</label>
         </div>
         
        
         
         <div  class="input-group">
           <input id="submit"  class="btn btn-primary-outline disabled"  type="submit" value="注册">
         </div>
     </form>
    
     </div>
     
</div>

   




    <!--底部-->
    <div class="footer">
    <p>
    Copyright &copy; 2015 ECL实验室版权所有
    </p>
    </div>
<!-- Include all compiled plugins (below), or include individual files as needed --> 
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.js"></script> 


</body>
</html>