<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>TaskManager</title>
		<!-- cssを指定 -->
		<link rel="stylesheet" href="<%= request.getContextPath() %>/css/index.css"> 
	</head>

	<body>
		<div class="logout-container">
			<h1>ログアウト完了</h1><br>
			<p>ログアウトしました</p>
			<a href="index.jsp">トップへ</a>		
		</div>
	</body>
</html>