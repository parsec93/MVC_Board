<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String sId = (String)session.getAttribute("sId");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<header>
		<p align="right">
		<% if(sId==null){
			%> <a href="LoginForm.me">로그인</a><%
		} else { %>
		  <%=sId %>님 | <a href="LogoutPro.me">로그아웃</a><% } %>
		</p>
	</header>
	<h1>메인 페이지</h1>
<!-- 	<input type="button" value="글쓰기" onclick="location.href='BoardWriteForm.bo'">  -->
	<a href="BoardList.bo"><input type="button" value="게시판"></a> 
</body> 
  
</html>