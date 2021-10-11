<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>category</title>
<style type="text/css">
	table {
		margin: 0 auto;
		width: 500px;
		border-collapse: collapse;;
}
tr:nth-child(even){
	background-color: gray;
}
tr:hover{
	background-color: #c0c0c0;
}
td{
	border-bottom: 1px gray solid; 
}
</style>
</head>
<body>
	<table>
		<tr>
			<th>번호</th>
			<th>이름</th>
			<th>날짜</th>
			<th>노출여부</th>
		</tr>
		<c:forEach items="${category }" var="c">
			<tr>
				<td>${c.sc_no }</td>
				<td>${c.sc_category }</td>
				<td>${c.sc_date }</td>
				<td>
					<a href="./categoryUpdate?sc_no=${c.sc_no }">[수정]</a>
					<a href="./categoryPrivate?sc_no=${c.sc_no }">
						<c:choose>
							<c:when test="${c.sc_visible eq 1 }">
								<img alt="" src="../images/on.png"
									style="vertical-align: middle;">
							</c:when>
							<c:otherwise>
								<img alt="" src="../images/off.png"
									style="vertical-align: middle;">
							</c:otherwise>
						</c:choose>
					</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	
	<form action="./category" method="post">
		<input type="text" name="categoryName">
		<button type="submit">등록</button>
	</form>
	<hr>

</body>
</html>