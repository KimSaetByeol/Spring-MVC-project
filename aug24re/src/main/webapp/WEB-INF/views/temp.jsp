<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>미디어 쿼리 + 시멘틱 태그</title>
<style type="text/css">
@media screen and (max-width: 768px){
	body{
		background-color: aqua;
	}
}

@media all and (min-width: 768px) and (max-width: 1023px){
	body{
		background-color: gray;
	}
}

@media all and (min-width: 1024px){
	body{
		background-color:pink;
	}
}
</style>
</head>
<body>
	<div id="container">
		<header>
			헤더입니다.
		</header>
		<main>
			본문입니다.
		</main>
		<footer>
			푸터입니다.
		</footer>
	</div>
</body>
</html>