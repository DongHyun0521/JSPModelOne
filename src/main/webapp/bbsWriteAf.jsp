<%@page import="com.mbc.pro.dao.BbsDao"%>
<%@page import="com.mbc.pro.dto.BbsDto"%>
<%@page import="com.mbc.pro.dto.MemberDto"%>
<%@page import="com.mbc.pro.dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	//JSPModelOne - bbsWriteAf.jsp
	MemberDto mem = (MemberDto)session.getAttribute("login");
	if(mem == null) {
	    response.sendRedirect("login.jsp");
	    return;
	}
	/*String _id = mem.getId();*/
	
	String id = request.getParameter("id");
	String title = request.getParameter("title");
	String content = request.getParameter("content");
	
	BbsDao dao = BbsDao.getInstance();
	int count = dao.bbsWrite(new BbsDto(id, title, content));
	
	if (count > 0) {%>
	<script>
		alert("- 글 작성 완료 -");
		location.href = "bbsList.jsp";
	</script>
	<%} else {%>
	<script>
		alert("- 글 작성 실패 -");
		location.href = "bbsWrite.jsp";
	</script>
	<%}
%>