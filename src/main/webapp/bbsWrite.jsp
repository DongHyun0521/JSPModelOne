<%@page import="com.mbc.pro.dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 로그인한 유저의 아이디
	MemberDto mem = (MemberDto)session.getAttribute("login");
	if (mem == null) {
		response.sendRedirect("login.jsp");
        return;
	}
	String id = mem.getId();
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>JSPModelOne - bbsWrite.jsp</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<style type="text/css">
		table {
			width: 430px;
		}
		table, th, td {
			border: 2px solid black;
			border-collapse: collapse;
		}
	</style>
</head>
<body>
	<h2>글 작성하기</h2>
	<form action="bbsWriteAf.jsp" method="post">
		<table>
		<tr>
			<th>아이디</th>
			<td><input id="id" name="id" class="form-control" type="text" value="<%=id%>" readonly="readonly" placeholder="작성자..."/></td>
		</tr>
		<tr>
			<th>제목</th>
			<td><input id="title" name="title" class="form-control" type="text" placeholder="제목..."/></td>
		</tr>
		<tr>
			<th>내용</th>
			<td><textarea id="content" name="content" class="form-control" rows="20" cols="50" placeholder="내용..."></textarea></td>
		</tr>
		</table>
		
		<button type="submit" value="글 작성">작성 완료</button>
	</form>
</body>
</html>