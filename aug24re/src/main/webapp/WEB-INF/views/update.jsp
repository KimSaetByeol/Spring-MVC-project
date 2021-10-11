<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${dto.sb_no }번 글 수정하기</title>
<style type="text/css">
#update{
	height: 300px;
	width: 800px;
}
input{
	width: 100%;
	height: 30px;
}
textarea{
	width: 100%;
	height: 700px;
}
</style>
</head>
<body>
<div id="container">
	<div id="header">
		<%-- <jsp:include page="menu.jsp"/> --%>
		<c:import url="/menu" />
	</div>
	<div id="main">
	
	<div id="update">
	<form action="update" method="post">
		<input type="text" name="sb_title" value="${dto.sb_title }">
		${dto.sb_date }에 쓴 글
		<textarea name="sb_content">${dto.sb_content }</textarea>
		<input type="hidden" name="sb_no" value="${dto.sb_no}">
		<button>글쓰기</button>
	</form>
	</div><!-- end of update -->
</div><!-- end of header -->
</div><!-- end of container -->
</body>
</html>