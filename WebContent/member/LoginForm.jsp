<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 현재 세션에 저장된 id 값이 있을 경우 메인 페이지로 이동("잘못된 접근입니다" 출력)
	String sId = (String)session.getAttribute("sId");
	if(sId != null) {
	    out.println("<script>");                                     
	    out.println("alert('잘못된 접근입니다!')");
	    out.println("location.href='Main.bo'");
	    out.println("</script>");
	}
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>로그인</h1>
	<form action="LoginPro.me" method="post">
		아이디 : <input type="text" name="id"><br>
		패스워드 : <input type="password" name="password"><br>
		<input type="submit" value="로그인">
	</form>
</body>
</html>