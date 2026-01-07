<%@page import="com.mbc.pro.dto.BbsDto"%>
<%@page import="com.mbc.pro.dao.BbsDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
<%
	int seq = Integer.parseInt(request.getParameter("seq"));

	String id = request.getParameter("id");
	String title = request.getParameter("title");
	String content = request.getParameter("content");
	
	BbsDao dao = BbsDao.getInstance();
	int count = dao.answer(seq, new BbsDto(id, title, content));
	
	if (count > 0) {
		%>
		<script type="text/javascript">
			alert("- 답글 입력 성공 -");
			location.href = "bbsList.jsp";
		</script>
		<%
	} else {
		%>
		<script type="text/javascript">
			alert("- 답글 입력 실패 -");
			location.href = "bbsDetail.jsp?seq=" + <%=seq%>;
		</script>
		<%
	}
%>
</body>
</html>