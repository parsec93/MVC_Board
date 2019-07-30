<%@page import="vo.BoardBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	BoardBean article = (BoardBean)request.getAttribute("article");
    	String nowPage = request.getParameter("page");
    
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<script type="text/javascript">
	function modifyBoard(password){
		alert("수정 하는 버튼");
		
// 		if(password != boardForm.board_pass.value){
// 			alert("패스워드가 틀렸습니다.");
// 			 document.getElementById("board_pass").focus();
// 			return false;
// 		}
		
		return true;
	}
	
	
</script>

<title>MVC 게시판</title>
<style type="text/css">
	#registForm {
		width: 500px;
		height: 610px;
		border: 1px solid red;
		margin: auto;
	}
	
	h2 {
		text-align: center;
	}
	
	table {
		margin: auto;
		width: 450px;
		border: 1px solid darkgray;
	}
	
	.td_left {
		width: 150px;
		background: orange;
	}
	
	.td_right {
		width: 300px;
		background: skyblue;
	}
	
	#commandCell {
		text-align: center;
	}
</style>
</head>
<body>
	<!-- 게시판 글 등록 -->
	<section id="writeForm">
		<h2>게시판 글 수정</h2>
		<form action="BoardModifyPro.bo" method="post" name="boardForm" onsubmit = "return modifyBoard('<%=article.getBoard_pass() %>')">
		<input type = "hidden" name = "board_num" value ="<%=article.getBoard_num() %>">
		<input type = "hidden" name = "page" value ="<%=nowPage %>">
		
			<table>
				<tr>
					<!-- label 태그를 사용하여 해당 레이블 클릭 시 for 속성에 지정된 이름과 같은 id 속성을 갖는 텍스트필드로 커서 요청 -->
					<td class="td_left"><label for="board_name">글쓴이</label></td>
					<td class="td_right"><input type="text" name="board_name" id="board_name" value="<%=article.getBoard_name() %>" /></td>
				</tr>
				<tr>
					<td class="td_left"><label for="board_pass">비밀번호</label></td>
					<td class="td_right"><input type="password" name="board_pass" id="board_pass" required="required" /></td>
				</tr>
				<tr>
					<td class="td_left"><label for="board_subject">제목</label></td>
					<td class="td_right"><input type="text" name="board_subject" id="board_subject" value="<%=article.getBoard_subject() %>" /></td>
				</tr>
				<tr>
					<td class="td_left"><label for="board_content">내용</label></td>
					<td class="td_right"><textarea name="board_content" id="board_content" cols="40" rows="15" required="required" ><%=article.getBoard_content() %></textarea></td>
				</tr>
			</table>
			<section id="commandCell">
				<input type="submit" value="등록" />&nbsp;&nbsp;
				<input type="button" value="뒤로" onclick = "history.back()" />
			</section>
		</form>	
	</section>
</body>
</html>















