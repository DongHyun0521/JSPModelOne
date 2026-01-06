<%@page import="com.mbc.pro.dto.BbsDto"%>
<%@page import="com.mbc.pro.dto.MemberDto"%>
<%@page import="com.mbc.pro.dao.BbsDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	MemberDto mem = (MemberDto)session.getAttribute("login");
	if (mem == null) {
	    response.sendRedirect("login.jsp");
	    return;
	}
	int seq = Integer.parseInt(request.getParameter("seq"));

	BbsDao dao = BbsDao.getInstance();
	int count = dao.bbsDelete(new BbsDto(seq, mem.getId()));

	if (count > 0) {%>
	<script>
		alert("- 글 삭제 완료 -");
		location.href = "bbsList.jsp";
	</script>
	<%} else {%>
	<script>
		alert("- 글 삭제 실패 -");
		location.href = "bbsDetail.jsp?seq=" + seq;
	</script>
<%}%>