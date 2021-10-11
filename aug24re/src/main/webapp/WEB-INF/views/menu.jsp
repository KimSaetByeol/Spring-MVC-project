<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
			<div id="loginform">
				<c:choose>
					<c:when test="${sessionScope.sm_name ne null }">
						${sessionScope.sm_name }님 반갑습니다.
						<a href="./logout">로그아웃</a>
					</c:when>
					<c:otherwise>
						<form action="./login" method="post">
							<input type="text" name="id" required="required" placeholder="아이디를 입력하세요.">
							<input type="password" name="pw" required="required" placeholder="비밀번호를 입력하세요.">
							<button type="submit">로그인</button>
						</form>
					</c:otherwise>
				</c:choose>
			</div>
			<div id="menu">
				<c:forEach items="${categoryList }" var="cl">
					<a href="./board?sb_cate=${cl.sc_no }">${cl.sc_category }</a> |
				</c:forEach>
				<a href="./admin/category">category</a> |
				<a href="./admin/member">member</a> |
			</div>