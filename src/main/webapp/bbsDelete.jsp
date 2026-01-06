<%@page import="com.mbc.pro.dto.BbsDto"%>
<%@page import="com.mbc.pro.dao.BbsDao"%>
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
	<title>JSPModelOne - bbsDelete.jsp</title>
</head>
<body>
	<h2>- 글 삭제 완료 -</h2>
	<button type="button" onclick="location.href='bbsList.jsp'">글 목록</button>
</body>
</html>