<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<meta charset="UTF-8">
	<title>index</title>
</head>
<body>
	<button onclick="location.href='./board'">보드로 이동</button>
	<c:redirect url="./board"></c:redirect>
</body>
</html>
