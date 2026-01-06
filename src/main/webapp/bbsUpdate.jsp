<%@page import="com.mbc.pro.dto.BbsDto"%>
<%@page import="com.mbc.pro.dao.BbsDao"%>
<%@page import="com.mbc.pro.dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int seq = Integer.parseInt(request.getParameter("seq"));
	
	BbsDao dao = BbsDao.getInstance();
	BbsDto dto = new BbsDto();
	
	dto.setSeq(seq);
	dto = dao.bbsDetail(dto);
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>JSPModelOne - bbsUpdate.jsp</title>
	<style type="text/css">
		table, th, td {
			border: 2px solid black;
			border-collapse: collapse;
		}
	</style>
</head>
<body>
	<form action="bbsUpdateAf.jsp" method="post">
	<input type="hidden" name="seq" value="<%=dto.getSeq()%>">
	<div align="center">
		<h2>글 수정</h2>
		<table>
		<col width="200px"/><col width="400px"/> 
			<tr>
				<th>작성자</th><td><%=dto.getId()%></td>
			</tr>
			<tr>
				<th>작성일</th><td><%=dto.getWdate()%></td>
			</tr>
			<tr>
				<th>조회수</th><td><%=dto.getReadcount()%></td>
			</tr>
			<tr>
				<th>제목</th>
				<th><textarea name="title" rows="1" cols="90"><%=dto.getTitle()%></textarea></th>
			</tr>
			<tr>
				<th>내용</th>
				<th><textarea name="content" rows="15" cols="90"><%=dto.getContent()%></textarea></th>
			</tr>
		</table>
		<button type="submit">글 수정 완료</button>
	</div>
	</form>
</body>
</html>