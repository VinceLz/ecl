<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
	$(document).ready(function() {
			
				
		$("#checkedAll").click(function(){
		  if($(this).prop("checked") == true){ //check all
			  $("input[name='commentIds']").prop("checked",true); 
		  }else{
			  $("input[name='commentIds']").prop("checked",false); 
		  }
	   });
    });
	  
	function commentDelete(uids){
			alert(uids);
		if(confirm("确认要删除这条评论吗?")){
			$.post("/ecl/UserServlet?method=ajaxDeleteUser",{uid:uids},
				function(result){
					if(result){
						alert("删除成功！");
						window.location.href="${pageContext.request.contextPath}/UserServlet?method=getUserList";
					}else{
						alert("删除失败");
					}
					}
			);
		}
	}
	
	function commentsDelete(){
		var chk_value =[];    
		 $('input[name="commentIds"]:checked').each(function(){    
		   chk_value.push($(this).val());    
		 });   
		 if(chk_value.length==0){
			alert("请选择要删除的数据！");
			return;
		  }
		 var commentIds=chk_value.join(",");
		 if(confirm("确认要删除这些评论吗?")){
				$.post("comment?action=delete",{commentIds:commentIds},
					function(result){
						 var result = eval('('+result+')');
						if(result.success){
							alert("成功删除"+result.delNums+"条数据！");
							window.location.href="${pageContext.request.contextPath}/comment?action=backList";
						}else{
							alert(result.errorMsg);
						}
					}
				);
			}
	}
</script>
<div class="data_list backMain">
	<div class="dataHeader navi">
		${navCode }
	</div>
	<div class="search_content" style="vertical-align:middle;padding-right: 20px;">
		<div style="float: left;padding-top: 10px;">
			<button class="btn-mini btn-danger" type="submit" onclick="commentsDelete()">批量删除（待定）</button>
		</div>
		<div style="float: right;">
			
		</div>
	</div>
	<div class="data_content">
	   <table class="table table-bordered table-hover">
		 <tr>
		 	<th><input type="checkbox" id="checkedAll"/></th>
			<th>序号</th>
			<th>用户名</th>
			<th>创建日期</th>
			<th>权限</th>
			<th>操作</th>
		</tr>
		<c:forEach var="user" items="${userlist }" varStatus="status" >
			<tr>
				<td><input type="checkbox" name="commentIds"  value="${user.uId }"/></td>
				<td>${status.index+1 }</td>
				<td>${user.userName }</td>
				<td>${user.uTime }</td>
				<td>普通会员</td>
				<td><button class="btn-mini btn-danger" id="${user.uId }" onClick="commentDelete('${user.uId }')" type="button">删除</button> <button class="btn-mini btn-danger" id="${user.uId }" onClick="commentDelete('${user.uId }')" type="button">拉入黑名单(待定)</button></td>
			</tr>
		</c:forEach>
	  </table>
	</div>
	<div class="pagination pagination-centered">
	 	 <ul>
	 	 	${pageCode }
	 	 </ul>
	</div>
</div>