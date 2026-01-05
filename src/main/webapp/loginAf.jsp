<%@page import="com.mbc.pro.dao.MemberDao"%>
<%@page import="com.mbc.pro.dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// JSPModelOne - loginAf.jsp
	String id = request.getParameter("id");
	String pw = request.getParameter("pw");
	
	MemberDao dao = MemberDao.getInstance();
	MemberDto mem = dao.login(id, pw);
	
	if (mem != null) {
		session.setAttribute("login", mem);
		session.setMaxInactiveInterval(60 * 60 * 2);
		
		%><script type="text/javascript">
		alert("<%=mem.getId() %>님 환영합니다");
		location.href = "bbsList.jsp";
		</script><%
		} else {
		%><script type="text/javascript">
		alert("- 로그인 실패 -");
		location.href = "login.jsp";
		</script><%
	}
	
	/*MemberDto dto = new MemberDto();
	dto.setId(id);
	dto.setPw(pw);
	int result = MemberDao.getInstance().login(dto);
	
	if (result > 0) {
		%><script type="text/javascript">
		alert("- 로그인 성공 -");
		location.href = "main.jsp";
		</script><%
	} else {
		%><script type="text/javascript">
		alert("- 로그인 실패 -");
		location.href = "login.jsp";
		</script><%
	}*/
%>