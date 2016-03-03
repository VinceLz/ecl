<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
	function newsTypeDelete(newsTypeId){
		if(confirm("确认要删除这条新闻类别吗?")){
			$.post("newsType?action=delete",{newsTypeId:newsTypeId},
				function(result){
				    var result = eval('('+result+')');
					if(result.success){
						alert("删除成功！");
						window.location.href="${pageContext.request.contextPath}/newsType?action=backList";
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
	<div class="data_content">
	   <table class="table table-bordered table-hover">
		 <tr>
			<th>序号</th>
			<th>用户名</th>
			<th>文件夹创建日期</th>
			<th>操作</th>
		</tr>
		<c:forEach var="file" items="${fmanager }" varStatus="status" >
			<tr>
				<td>${status.index+1 }</td>
				<td>${file.userName }</td>
				<td>${file.uTime }</td>
				<td><a class="btn-mini btn-info" type="button"  href='<c:url value="/CatalogServlet?method=myCatalog&cid=${file.cId }"></c:url>'>管理文件(暂停跳转到网盘页面)</a><button class="btn-mini btn-info" type="button">删除</button></td>
			</tr>
		</c:forEach>
	  </table>
	</div>
</div>