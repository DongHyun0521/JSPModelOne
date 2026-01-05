<%@page import="com.mbc.pro.dto.BbsDto"%>
<%@page import="java.util.List"%>
<%@page import="com.mbc.pro.dao.BbsDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	BbsDao dao = BbsDao.getInstance();
	List<BbsDto> list = dao.getBbsList();
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>JSPModelOne - bbsList.jsp</title>
	<style type="text/css">
		.center {
			margin: auto;
			width: 1000px;
			text-align: center;
		}
		th {
			background-color: royalblue;
			color: white;
		}
		tr {
			line-height: 16px;
		}
		td {
			text-align: center;
			line-height: 16px;
		}
	</style>
</head>
<body>
	<h2>게시판</h2>
	<div class="center">
		<table border="1">
		<col width="70"/><col width="600"/><col width="100"/><col width="150"/>
			<thead>
			<tr>
				<th>번호</th><th>제목</th><th>조회수</th><th>작성자</th>
			</tr>
			</thead>
			<tbody>
				<%if (list == null || list.size() == 0) {%>
				<tr>
					<td colspan="4">- 작성된 글이 없습니다 -</td>
				</tr>
				<%} else {
					for (int i=0; i<list.size(); i++) {
						BbsDto bbs = list.get(i);%>
						<tr>
							<td><%=bbs.getSeq()%></td>
							<td style="text-align:left;">
							<a href="bbsDetail.jsp?seq=<%=bbs.getSeq()%>"><%=bbs.getTitle()%></a>
							</td>
							<td><%=bbs.getReadcount()%></td>
							<td><%=bbs.getId()%></td>
						</tr>
					<%}
				}%>
			</tbody>
		</table>
		<br/><br/>
		<a href="bbsWrite.jsp">글 작성하기</a>
	</div>
</body>
</html>