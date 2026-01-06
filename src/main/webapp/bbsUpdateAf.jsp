<%@page import="com.mbc.pro.dto.BbsDto"%>
<%@page import="com.mbc.pro.dao.BbsDao"%>
<%@page import="com.mbc.pro.dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	MemberDto mem = (MemberDto)session.getAttribute("login");
	if (mem == null) {
	    response.sendRedirect("login.jsp");
	    return;
	}
	
	int seq = Integer.parseInt(request.getParameter("seq"));
	String title = request.getParameter("title");
	String content = request.getParameter("content");
	
	BbsDao dao = BbsDao.getInstance();
	int count = dao.bbsUpdate(new BbsDto(seq, mem.getId(), title, content));
	
	if (count > 0) {%>
	<script>
		alert("- 글 수정 완료 -");
		location.href = "bbsDetail.jsp?seq=<%=seq%>";
	</script>
	<%} else {%>
	<script>
		alert("- 글 수정 실패 -");
		location.href = "bbsUpdate.jsp?seq=<%=seq%>";
	</script>
	<%}
%>