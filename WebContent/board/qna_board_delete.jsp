<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 전달받은 request 객체로부터 게시물 번호(board_num)와 페이지번호(page) 가져오기
    int board_num = Integer.parseInt(request.getParameter("board_num"));
    String nowPage = request.getParameter("page");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC 게시판</title>
<style type="text/css">
	#passForm {
		width: 300px;
		margin: auto;
		border: 1px solid orange;
	}
</style>
</head>
<body>
	<section id="passForm">
		<form name="deleteForm" action="BoardDeletePro.bo?board_num=<%=board_num%>" method="post">
			<input type="hidden" name="page" value="<%=nowPage %>" />
			
			<table>
				<tr>
					<td><label>글 비밀번호 : </label></td>
					<td><input type="password" name="board_pass"></td>
				</tr>
				<tr>
					<td><input type="submit" value="삭제" /></td>
					<td><input type="reset" value="돌아가기" onclick="history.back()"/></td>
				</tr>
			</table>
		</form>
	</section>
	
</body>
</html>