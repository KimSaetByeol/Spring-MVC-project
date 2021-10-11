<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>detail : ${dto.sb_no }</title>
<link href="./css/index.css" rel="stylesheet">
<style type="text/css">
#detail{
	margin: 0 auto;
	width: 800px;
	background-color: lightpink;
	height: auto;
	margin: 5px;
	padding: 5px;
}
#content{
	min-height: 200px;
}
</style>
<script type="text/javascript">
function like(sb_no){
	//alert(no + "좋아요를 눌렀습니다.");
	location.href="./like?sb_no="+sb_no;
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
	
	<div id="detail">
	글 번호 : ${dto.sb_no }<br>
	글 제목 : ${dto.sb_title } | 
	좋아요 : 
	<span>${dto.sb_like }</span>
	<c:if test="${sessionScope.sm_id ne null }">
		<img alt="like" src="./images/like.png" onclick="return like(${dto.sb_no});" style="vertical-align: bottom;">
	</c:if>
	
	<br>
	글쓴이 : ${dto.sm_name } (${dto.sm_id })
	<c:if test="${sessionScope.sm_id eq dto.sm_id }">
		| <button onclick="location.href='./update?sb_no=${dto.sb_no }'">수정하기</button>
		<button onclick="location.href='./delete?sb_no=${dto.sb_no }'">삭제하기</button><br>
	</c:if>
	<br>
	날짜 : ${dto.sb_date } | 조회수 : ${dto.sb_count }
	
	
	<hr>
	<div id="content">${dto.sb_content }</div>
	<hr>
	
	<br>
	<a href="./board">보드로 이동</a>
	<a href="./write?sb_cate=${dto.sb_cate }">글쓰기</a>
	<br>
	</div><!-- end of detail -->
</div><!-- end of main -->
</div><!-- end of container -->
</body>
</html>