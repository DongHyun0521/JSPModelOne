<%@page import="com.mbc.pro.dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// JSPModelOne - idcheck.jsp
	String id = request.getParameter("id").trim();
	MemberDao dao = MemberDao.getInstance();
	int result = dao.idcheck(id);
	out.print(result);
%>