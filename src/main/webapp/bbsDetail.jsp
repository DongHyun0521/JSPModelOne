<%@page import="com.mbc.pro.dto.BbsDto"%>
<%@page import="com.mbc.pro.dao.BbsDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int seq = Integer.parseInt(request.getParameter("seq"));
	
	BbsDao dao = BbsDao.getInstance();
	BbsDto dto = new BbsDto();
	
	dto.setSeq(seq);
	dao.addReadcount(dto);
	dto = dao.bbsDetail(dto);
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>JSPModelOne - bbsDetail.jsp</title>
	<style type="text/css">
		table, th, td {
			border: 2px solid black;
			border-collapse: collapse;
		}
	</style>
</head>
<body>
	<div align="center">
		<h2>글 상세</h2>
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
				<th>제목</th><td><%=dto.getTitle()%></td>
			</tr>
			<tr>
				<th>내용</th>
				<th><textarea rows="15" cols="90" readonly="readonly"><%=dto.getContent()%></textarea></th>
			</tr>
		</table>
		
		<button type="button" onclick="location.href='bbsList.jsp'">글 목록</button>
		<button type="button" onclick="location.href='bbsWrite.jsp'">글 수정</button>
		<button type="button" onclick="location.href='bbsDelete.jsp'">글 삭제</button>
	</div>
</body>
</html>