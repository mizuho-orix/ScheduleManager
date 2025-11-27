<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User, model.Mutter, model.Schedule, java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
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
					<li>${loginUser.name_Sei} ${loginUser.name_Mei} さん　ログイン中</li>
					<li><a href="Logout">ログアウト</a></li>
				</ul>
			</nav>
		</header>
		
		<section>
			<div class="menu">
				<ul class="command">
					<li id="add-task">新規予定入力</li>
					<li>活動履歴</li>
				</ul>
				<div class="today-task">
				</div>
			</div>
			
			<div class="controls">
				<div class="controls-left">
					<button id="prev-month">先月</button>
				</div>				
				
				<div class="controls-center">
					<label for="year">年:</label>
					<select id="year"></select>
					<label for="month">　月:</label>
					<select id="month"></select>				
				</div>
				
				<div>
					<button id="next-month">次月</button>
				</div>
			</div>
			
			<table id="calendar">
				<thead>
				<tr>
					<th>日</th><th>月</th><th>火</th><th>水</th><th>木</th><th>金</th><th>土</th>
				</tr>
				</thead>
				<tbody></tbody>
			</table>
		</section>
	</div>

	<!-- モーダル部分 -->
	<div id="modal" class="modal">
		<div class="modal-content">
			<span class="close-button">&times;</span>

			<!-- 予定の詳細を表示するエリア -->
			<div id="detail-view">
				<h2>予定の詳細</h2>
				<p id="modal-task-name"></p>
				<p id="modal-task-comment"></p>
				<button id="edit-task">予定の修正・削除</button>
			</div>

		    <!-- 修正フォームエリア（初期は非表示） -->
			<div id="edit-view" class="formBox" style="display:none;">
				<h2>予定修正</h2>
				
				<!-- 修正フォーム -->
				<form action="UpdateScheduleServlet" method="post">
					<label for="edit-Date">日付を選択：</label>
					<input type="date" id="edit-Date" name="edit-Date"><br>
					<label for="edit-ScheduleName">予定内容　：</label>
					<input type="text" id="edit-ScheduleName" name="edit-ScheduleName"><br>
					<div class="comment-Area displayFlex">
						<label for="edit-Comment">備考　　　：</label>
						<textarea id="edit-Comment" name="edit-Comment"></textarea><br>
					</div>		        
					<input type="hidden" id="edit-TaskId" name="edit-TaskId" value="">		        	
					<div class="edit-button">
						<button type="button" id="delete-Schedule">予定の削除</button>
						<button type="submit">修正を保存</button>
						<button type="button" id="cancel-edit">キャンセル</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
<footer>
</footer>
<!-- jQuery読み込み	-->
<script src="<%= request.getContextPath() %>/js/jquery-3.4.1.min.js" type="text/javascript"></script>

<!-- jsp内で読み取ったセッションスコープの内容を外部jsファイルで使用する為に
		jspファイル内でスコープの内容を変数に格納 -->
<script>
	const userId = "${loginUser.id}";
</script>

<script>
	// セッションスコープのscheduleListをjsonに変換する
	let scheduleList = [
		<c:forEach var="s" items="${scheduleList}" varStatus="status"> 	{
			id: ${s.id},
			date: "${s.schedule_Date}",
			name: "${fn:replace(s.schedule_Name, '\"', '\\\"')}",
			comment: "${fn:replace(s.comment, '\"', '\\\"')}"
			} 

			<c:if test="${!status.last}">,</c:if>
		</c:forEach>
	 ];
</script>

<script src="<%= request.getContextPath() %>/js/calendar.js" type="text/javascript"></script>	
</html>