<%@page import="com.mbc.pro.dao.MemberDao"%>
<%@page import="com.mbc.pro.dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// JSPModelOne - regiAf.jsp
	String id = request.getParameter("id").trim();
	String pw = request.getParameter("pw").trim();
	String name = request.getParameter("name").trim();
	String email = request.getParameter("email").trim();
	
	MemberDto dto = new MemberDto(id, pw, name, email, 3);
	int result = MemberDao.getInstance().addMember(dto);
	
	if (result > 0) {
		%><script type="text/javascript">
		alert("- 회원가입 성공 -");
		location.href = "login.jsp";
		</script><%
	} else {
		%><script type="text/javascript">
		alert("- 회원가입 실패 -");
		location.href = "regi.jsp";
		</script><%
	}
%>