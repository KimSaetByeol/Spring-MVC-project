<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 홈페이지</title>
<link href="./css/index.css" rel="stylesheet">
<style type="text/css">
#login{
	margin: 0 auto;
	width: 300px;
	height: 300px;
	padding: 10px;
	background-color: dimgray;
	text-align: center;
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
}

#login input{
	height: 30px;
	width: 90%;
	margin-bottom: 10px;
}

#login button{
	height: 30px;
	width: 100px;
}

#loginimg{
	height: 100px;
}
</style>
</head>
<body>
	<div id="container">
		<div id="header">
			<jsp:include page="menu.jsp"/>
			<%-- <c:import url="/menu" /> --%>
		</div>
		<div id="main">
			<div id="login">
			<img id="loginimg" alt="login" src="./images/login2.png">
				<form action="./login" method="post">
					<input type="text" name="id" required="required">
					<input type="password" name="pw" required="required">
					<button type="submit">로그인</button>
					<button type="reset">초기화</button>
				</form>
				<button onclick="location.href='./join'">회원가입</button>
			</div>
		</div>
	</div>
</body>
</html>