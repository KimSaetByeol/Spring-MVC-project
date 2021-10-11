<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>category Update</title>
</head>
<body>
	<h1>수정하기</h1>
	<div>
		<form action="./categoryUpdate" method="post">
			<input type="text" name="category" value="${cate.sc_category }">
			<input type="hidden" name="sc_no" value="${cate.sc_no }">
			<button type="submit">수정하기</button> 
		</form>
			<button onclick="location.href='./category'">취소</button>
	</div>
</body>
</html>