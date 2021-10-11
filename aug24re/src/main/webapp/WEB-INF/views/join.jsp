<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link href="./css/index.css" rel="stylesheet">
<style type="text/css">
#join {
	margin: 0 auto;
	height: auto;
	width: 500px;
	background-color: lavender;
	padding: 10px;
	text-align: center;
}

table {
	margin: 0 auto;
	height: 100%;
	width: 100%;
	border-collapse: collapse;
	margin-bottom: 10px;
}

th, td {
	border-bottom: 1px solid silver;
}

td {
	text-align: left;
}

input {
	width: 180px;;
	height: 40px;
	margin: 5px;
	padding: 5px;
}

button {
	width: 45%;
	height: 40px;
	font-size: large;
}
</style>
<!-- jquery 가져오기 -->
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.6.0.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		//alert("정상 작동 합니다.");
	});
	
	function check(){
		//alert($("#id").val());
		var id = $("#id").val();
		if(id == "" || id.length < 5){
			//alert("id를 5자 이상 입력하세요.");
			$("#checkResult").css("color", "red");
			$("#checkResult").text("ID를 5자 이상 입력하세요.");
			$("#id").focus();
			$("#joinbtn").attr("disabled", true);
			return false;
		} else {
			$.ajax({
				url : "./checkID",
				type : "post",
				cache : false,
				dataType : "html",
				data : {'id' : id},
				success : function(data){
					//alert(data);
					if(data == 0){
						//alert("가입할 수 있습니다.");
					} else {
						//alert("이미 가입된 id입니다.");
						$("#checkResult").css("color", "green");
						$("#checkResult").text("이미 가입된 ID입니다.");
						$("#joinbtn").attr("disabled", true);
					}
				},
				error : function(request, status, error){
					alert(error);
				}
			});
			
			//alert("가입 가능합니다.");
			$("#checkResult").css("color", "green");
			$("#checkResult").text("가입 가능한 id입니다.");
			$("#joinbtn").attr("disabled", false);
		}
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
			<div id="join">
				<h1>회원가입하기</h1>
				<form action="./join" method="post">
					<table>
						<tr>
							<th>아 이 디</th>
							<td><input type="text" name="id" id="id" required="required" onchange="return check()">
							<p id="checkResult">아이디를 입력하세요.</p></td>
						</tr>
						<tr>
							<th>이 &emsp; 름</th>
							<td><input type="text" name="name" id="name" required="required"></td>
						</tr>
						<tr>
							<th>비밀번호</th>
							<td><input type="password" name="pw" id="pw1" required="required">
								<input type="password" name="pw" id="pw1" required="required"></td>
						</tr>
						<tr>
							<th>이 메 일</th>
							<td><input type="email" name="email" id="email" required="required"></td>
						</tr>
					</table>
					<button type="submit" id="joinbtn" disabled="disabled"> 가입하기</button>
					<button type="reset">입력취소</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>