<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- cssを指定 -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/index.css"> 
<title>スケジュール管理（仮）</title>
</head>
<body>
	<div class="login-container">
		<h1>スケジュール管理（仮）</h1><br>
		
		<!-- 「Login」サーブレットのgetPostメソッドに飛ばす入力フォーム -->
		<form action="Login" method="post">
			ユーザー名：<input type="text" name="name"><br>
			パスワード：<input type="password" name="pass"><br>
			<input type="submit" value="ログイン">
		</form>
	</div>
</body>
</html>