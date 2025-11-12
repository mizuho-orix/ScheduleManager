<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="model.User" %>

<%
	//セッションスコープからユーザー情報を取得
	User loginUser = (User)session.getAttribute("loginUser");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TaskManager</title>
</head>
<body>
	<h1>ログイン確認</h1>
	
	<!--
	ログインに成功しているとセッションスコープ「loginUser」が
	作成されてloginResultが呼び出されるので、
	loginUserがnullでなければログインは成功。
	loginUserがnullの場合は、ログインに失敗してloginUserが
	作成されないので失敗と判断してトップ画面に戻す
		-->
	<% if (loginUser != null) { %>
		<p>ログインに成功しました</p>
		<p>ようこそ<%= loginUser.getName() %>さん</p>
		<a href="Main">つぶやき投稿・閲覧へ</a>
	<% } else { %>
		<p>ログインに失敗しました</p>
		<a href="index.jsp">トップへ</a>
	<% } %>
</body>
</html>