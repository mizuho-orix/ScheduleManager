<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User, model.Mutter, model.Schedule, java.util.List" %>
<%
	//セッションスコープに保存されたユーザー情報を取得
	User loginUser = (User)session.getAttribute("loginUser");

	//リクエストスコープに保存されたつぶやきリストを取得
	List<Mutter> mutterList = (List<Mutter>)request.getAttribute("mutterList");

	// セッションスコープに保存されたスケジュールリストを取得	
	List<Schedule> scheduleList = (List<Schedule>)session.getAttribute("scheduleList");
	
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
		    <div class="menu">
				<ul class="command">
				  <li id="add-task">新規予定入力</li>
				  <li>予定の編集</li>
				  <li>活動履歴</li>
				</ul>
			<div class="today-task">
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
			<form action="Main" method="gett">
			<input type="text" name="text">
			<input type="submit" value="つぶやく">
			</form>

			<% if (scheduleList != null) { %>
				<p>スケジュールリスト読み込めてる</p>
			<% } %>
			<% for (Schedule schedule : scheduleList) { %>
				<p><%= schedule.getSchedule_Name() %></p>
			<% } %>
		</section>
	</div>

	<div id="modal" class="modal">
	  <div class="modal-content">
	    <span class="close-button">&times;</span>
	    <h2>予定の詳細</h2>
	    <p id="modal-task-name"></p>
	    <p id="modal-task-comment"></p>
	  </div>
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

<script>
	// セッションスコープのscheduleListをjsonに変換する
	let scheduleList = [
		// scheduleListが取得できているか判定して変数に格納
		// (スクリプト文の中の「scheduleList」はリクエストスコープ)
		<% if (session.getAttribute("scheduleList") != null) {
			List<Schedule> calendarSchedule = (List<Schedule>) session.getAttribute("scheduleList");

			// calendarScheduleの要素数だけ繰り返す
			for (int i = 0; i < calendarSchedule.size(); i++) {
				// リストのi番目の各要素をそれぞれ変数に格納
				Schedule s = calendarSchedule.get(i);
				int id = s.getId();
				String date = s.getSchedule_Date().toString(); // 例: "2025-11-17"
				String name = s.getSchedule_Name().replace("\"", "\\\""); // ダブルクォートをエスケープ
				String comment = s.getComment().replace("\"", "\\\"");
		%>
				// JSONファイルに変換（ファイル名：scheduleList）
				// idは数値型、その他は文字列
				{
				id: <%= id %>,
				date: "<%= date %>",
				name: "<%= name %>",
				comment: "<%= comment %>"
				}

				// iがcalendarScheduleの要素数の間、JSONの末尾に「,」を付ける
				// 最後の要素数の場合は何も付けない
				<%= (i < calendarSchedule.size() - 1) ? "," : "" %>
		<%	}
		} 	%>
	 ];
</script>

<script src="<%= request.getContextPath() %>/js/calendar.js" type="text/javascript"></script>	
</html>