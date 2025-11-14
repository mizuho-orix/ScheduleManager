<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User, model.Mutter, java.util.List" %>
<%
	//セッションスコープに保存されたユーザー情報を取得
	User loginUser = (User)session.getAttribute("loginUser");

	//リクエストスコープに保存されたつぶやきリストを取得
	List<Mutter> mutterList = (List<Mutter>)request.getAttribute("mutterList");
	
	//リクエストスコープに保存されたエラーメッセージを取得
	String errorMsg = (String)request.getAttribute("errorMsg");
%>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>TaskManager</title>
	<!-- cssを指定 -->
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/main.css"> 
</head>
<body>
	<div class="contents">
		<header class="page-header">
			<div class="logo">TaskManager</div>
			<nav>
				<ul class="login-status">
					<li><%= loginUser.getName_Sei() + " " + loginUser.getName_Mei() %>さん、ログイン中</li>
					<li><a href="Logout">ログアウト</a></li>
				</ul>
			</nav>
		</header>
		
		<section>
			<h1>本日のタスク</h1>
		    <div class="menu">
				<ul class="command">
				  <li id="add-task">新規予定入力</li>
				  <li>予定の編集</li>
				  <li>活動履歴</li>
				</ul>
			<div class="today-task">
				本日の予定表示
			</div>
	</div>




	<div class="controls">
		<label for="year">年:</label>
		<select id="year"></select>
		<label for="month">月:</label>
		<select id="month"></select>
	</div>

	<table id="calendar">
	  <thead>
	    <tr>
	      <th>日</th><th>月</th><th>火</th><th>水</th><th>木</th><th>金</th><th>土</th>
	    </tr>
	  </thead>
	  <tbody></tbody>
	</table>







			<a href="Main"><input type="submit" value="更新"></a>
			<form action="Main" method="post">
			<input type="text" name="text">
			<input type="submit" value="つぶやく">
			</form>
			<br>
		</section>
	</div>
</body>
<footer>
</footer>

	<!--	jQuery読み込み-->
	<script src="<%= request.getContextPath() %>/js/jquery-3.4.1.min.js" type="text/javascript"></script>

	<!--	jsp内で読み取ったセッションスコープの内容を外部jsファイルで使用する為に
			jspファイル内でスコープの内容を変数に格納 -->
	<script>
		const userId = "<%= loginUser.getId() %>";
	</script>

	<script src="<%= request.getContextPath() %>/js/calendar.js" type="text/javascript"></script>	
</html>