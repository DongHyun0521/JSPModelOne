<%@page import="com.mbc.pro.dto.MemberDto"%>
<%@page import="com.mbc.pro.dto.BbsDto"%>
<%@page import="com.mbc.pro.dao.BbsDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int seq = Integer.parseInt(request.getParameter("seq"));
	
	BbsDto dto = new BbsDto();
	dto.setSeq(seq);
	
	dto = BbsDao.getInstance().bbsDetail(dto);
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>JSPModelOne - answer.jsp</title>
</head>
<body>
	<div align="center">
		<h2>기본글</h2>
		<table border="1">
		<col width="200px"/><col width="400px"/>
			<tr>
				<th>작성자</th><th><%=dto.getId()%></th>
			</tr>
			<tr>
				<th>작성일</th><th><%=dto.getWdate()%></th>
			</tr>
			<tr>
				<th>조회수</th><th><%=dto.getReadcount()%></th>
			</tr>
			<tr>
				<th>제목</th><th><%=dto.getTitle()%></th>
			</tr>
			<tr>
				<th>내용</th>
				<th>
					<textarea rows="15" cols="90" readonly>
						<%=dto.getContent()%>
					</textarea>
				</th>
			</tr>
		</table>
		<h2>답글</h2>
		<%
			MemberDto mem = (MemberDto)session.getAttribute("login");
		%>
		<form action="answerAf.jsp" method="post">
			<input type="hidden" name="seq" value="<%=dto.getSeq()%>"/>
			<table border="1">
			<col width="200px"/><col width="400px"/>
			<tr>
				<th>아이디</th>
				<th>
					<input type="text" name="id" size="50px" value="<%=mem.getId()%>" readonly/>
				</th>
			</tr>
			<tr>
				<th>제목</th>
				<td>
					<input type="text" name="title" size="50px"/>
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
					<input type="text" name="content" size="50px"/>
				</td>
			</tr>
			</table>
			<button type="submit">답글 쓰기</button>
		</form>
	</div>
</body>
</html>