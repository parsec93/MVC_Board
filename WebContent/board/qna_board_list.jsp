<%@page import="vo.pageInfo"%>
<%@page import="vo.BoardBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	// Action 클래스에서 request 객체의 setAttibute() 메서드로 저장되어 전달된 객체 가져오기(Object 타입이므로 형변환 필요)
	ArrayList<BoardBean> articleList = (ArrayList<BoardBean>)request.getAttribute("articleList");
	pageInfo pageInfo = (pageInfo)request.getAttribute("pageInfo");
	
	// PageInfo 객체로부터 페이징 정보 가져오기
	int listCount = pageInfo.getListCount();
	int nowPage = pageInfo.getPage();
	int startPage = pageInfo.getStartPage();
	int endPage = pageInfo.getEndPage();
	int maxPage = pageInfo.getMaxPage();
	
	String sId = (String)session.getAttribute("sId");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
		width: 800px;
		border: 1px solid darkgray;
	}
	
	a {
		text-decoration: none;
	}

	#tr_top {
		background: orange;
		width: 800px; 
		text-align: center;
	}
	
	#writeButton {
		margin: auto;
		width: 800px;
		text-align: right;
	}
	
	#pageList {
		margin: auto;
		width: 800px;
		text-align: center;
	}
	
	#emptyArea {
		margin: auto;
		width: 800px;
		text-align: center;
	}
</style>
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
	<!-- 게시판 리스트 -->
	<section id="listForm">
		<h2>글 목록</h2>
		<table>
		<%if(articleList != null & listCount > 0) {%>
				<tr id="tr_top">
					<td width="100">번호</td>
					<td width="400">제목</td>
					<td width="150">작성자</td>
					<td width="150">날짜</td>
					<td width="100">조회수</td>
				</tr>
			<%for(int i = 0; i < articleList.size(); i++) {%>
					<tr>
						<td align="center"><%=articleList.get(i).getBoard_num() %></td>
						<td>
						<%if(articleList.get(i).getBoard_re_lev() != 0) { %>
								<%for(int j = 0; j <= articleList.get(i).getBoard_re_lev() * 2; j++) { %> 
										&nbsp;
								<%} %>▶
						<%} else {%>
								▶
						<%} %>
							<a href="BoardDetail.bo?board_num=<%=articleList.get(i).getBoard_num()%>&page=<%=nowPage%>">
								<%=articleList.get(i).getBoard_subject() %>
							</a>
						</td>
						<td align="center"><%=articleList.get(i).getBoard_name() %></td>
						<td align="center"><%=articleList.get(i).getBoard_date() %></td>
						<td align="center"><%=articleList.get(i).getBoard_readcount() %></td>
					</tr>
			<%} %>
		</table>		
	</section>
	
	<section id="writeButton">
		<a href="BoardWriteForm.bo"><input type="button" value="글쓰기"></a>
	</section>
	
	<section id="pageList">
	<%if(nowPage <= 1) { %>
			[이전]&nbsp;
	<%} else {%>
			<a href="BoardList.bo?page=<%=nowPage - 1%>">[이전]</a>&nbsp;
	<%} %>
	
	<%for(int i = startPage; i <= endPage; i++) {
		    if(i == nowPage) {%>
				[<%=i %>]
		<%} else {%>
				<a href="BoardList.bo?page=<%=i %>">[<%=i %>]</a>&nbsp;
		<%} %>
	<%} %>
	
	<%if(nowPage >= maxPage) {%>
			&nbsp;[다음]
	<%} else { %>
			<a href="BoardList.bo?page=<%=nowPage + 1%>">&nbsp;[다음]</a>
	<%} %>
	</section>
<%} else {%>
	<section id="emptyArea">등록된 글이 없습니다.</section>
<%} %>
</body>
</html>


















