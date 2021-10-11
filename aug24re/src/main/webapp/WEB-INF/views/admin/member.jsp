<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member</title>
<link href="./css/index.css" rel="stylesheet">
<style type="text/css">
#list{
	margin: 0 auto;
	padding:10px;
	width: 900px;
	box-sizing: border-box;
}
table{
	width: 100%;
	height: 100%;
	border-collapse: collapse;
	text-align: center;
}
tr{
	height: 30px;
}
td{
	border-bottom: 1px solid lavender;
}
tr:hover{
	background-color: lavender;
}
th{
	background-color: pink;
}
</style>

<script type="text/javascript">
function down(sm_no, sm_grade){
	//alert("down" + sm_no);
	var sm_grade = parseInt(sm_grade)-1;
	location.href="./gradeDown?sm_no="+sm_no+"&sm_grade="+sm_grade;
}
function up(sm_no, sm_grade){
	//alert("up" + sm_no);
	var sm_grade = parseInt(sm_grade)+1;
	location.href="./gradeUp?sm_no="+sm_no+"&sm_grade="+sm_grade;
}
function userDelete(sm_no){
	if(confirm("사용자를 삭제할까요?")){
		alert(sm_no + "번을 삭제합니다.");
		location.href="./userDelete?sm_no="+sm_no;
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
		
	<h1>전체 사용자 리스트</h1>
	<div id="list">
	
	<table>
		<tr>
			<th>아이디</th>
			<th>이름</th>
			<th>가입일</th>
			<th>등급</th>
			<th>삭제</th>
		</tr>
		<c:forEach items="${list }" var="l">
		<tr <c:if test="${l.sm_grade lt 5}">
				style="background-color: #c0c0c0"</c:if> >
			<td>${l.sm_id }</td>
			<td>${l.sm_name }</td>
			<td>${l.sm_joindate }</td>
			<td>
				<button <c:if test="${l.sm_grade eq 0 }">
							disabled="disabled"
						</c:if> onclick="down(${l.sm_no}, ${l.sm_grade})">◀</button>
				${l.sm_grade }
				<button <c:if test="${l.sm_grade eq 9 }">
							disabled="disabled"
						</c:if> onclick="up(${l.sm_no}, ${l.sm_grade})">▶</button>
			</td>
			<td><img alt="delete" src="../images/u_delete.png" width="24px" onclick="userDelete(${l.sm_no })"></td>
		</tr>
		</c:forEach>
	</table>
	</div>
</div><!-- main -->
</div><!-- container -->
</body>
</html>