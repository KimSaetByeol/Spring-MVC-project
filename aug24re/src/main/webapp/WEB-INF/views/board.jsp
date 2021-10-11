<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board</title>
<link href="./css/index.css" rel="stylesheet">
<style type="text/css">
table {
	margin: 0 auto;
	width: 800px;
	border-collapse: collapse;
	text-align: center;
}

th {
	background-color: pink;
	height: 30px;
}

tr {
	border: 1px gray solid;
	height: 30px;
}

#title{
text-align: left;
}
#paging{text-align:center;margin:10px auto;}
#paging img{vertical-align: middle;}
#paging a{display:inline-block; padding:0 3px;}
#paging a:hover{background-color:#EAFAF1;border-color:#eee;box-shadow:3px 3px 3px #7DCEA0;}
</style>

<script type="text/javascript">
function linkPage(pageNo){
	location.href="./board?pageNo="+ pageNo + "&sb_cate="+${sb_cate};
}
</script>

</head>
<body>
<div id="container">
	<div id="header">
		<%-- <jsp:include page="menu.jsp"/> --%>
		<c:import url="/menu" />
	</div>
	
	<div id="main">
	<h1>${category }</h1>
	
	<c:choose>
		<c:when test="${fn:length(list) gt 0 }">
			<table>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>날짜</th>
					<th>조회수</th>
					<th>글쓴이</th>
				</tr>
				<c:forEach items="${list }" var="l">
					<tr>
						<td id="no">${l.sb_nno }</td>
						<td id="title"><a href="./detail?sb_no=${l.sb_no}">${l.sb_title }</a></td>
						<td>${l.sb_date }</td>
						<td>${l.sb_count }</td>
						<td>${l.sm_name }(${l.sm_id })</td>
					</tr>
				</c:forEach>
			</table>
			<!-- 페이징 -->
			<div id="paging">
			<ui:pagination paginationInfo="${paginationInfo }" type="image" jsFunction="linkPage"/>
			</div>
		</c:when>
		<c:otherwise>
			출력할 내용이 없습니다.
		</c:otherwise>
	</c:choose>
	<br>
	
	<c:if test="${sessionScope.sm_name ne null }">
		<a href="./write?sb_cate=${sb_cate }">글쓰기</a><br>
	</c:if>
		
	</div><!-- end of main -->
</div><!-- end of container -->
</body>
</html>