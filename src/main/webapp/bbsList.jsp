<%@page import="com.mbc.pro.dto.BbsDto"%>
<%@page import="java.util.List"%>
<%@page import="com.mbc.pro.dao.BbsDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String category = request.getParameter("category");
	String keyword = request.getParameter("keyword");
	if (category == null)
		category = "";
	if (keyword == null)
		keyword = "";

	// 페이지 번호
	String sPageNumber = request.getParameter("pageNumber");
	int pageNumber = 0;
	if (sPageNumber != null && !sPageNumber.equals("")) {
		pageNumber = Integer.parseInt(sPageNumber);
	}

	BbsDao dao = BbsDao.getInstance();
	List<BbsDto> list = dao.getBbsSearchPagingList(category, keyword, pageNumber);

	// 글의 총 개수
	int count = dao.getAllBbs(category, keyword);
	// 페이지 계산
	int pageBbs = count / 10;	// 한 페이지 당 글 10개씩
	if ((count % 10) > 0)
		pageBbs += 1;
%>
<%!
	// 화살표
	public String arrow(int depth) {
		String rs = "<img src='./images/arrow.png' width='20px' height='20px'/>";
		String nbsp = "&nbsp;&nbsp;&nbsp;&nbsp;";
		String ts = "";
		
		for (int i=0; i<depth; i++) {
			ts += nbsp;
		}
		return depth == 0 ? "" : ts+rs;
	}
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
	<h2><a href="bbsList.jsp" style="text-decoration:none; color:black;">
		게시판
	</a></h2>
	<div class="center">
		<table border="1">
		<col width="70"/><col width="600"/><col width="100"/><col width="150"/>
			<thead>
			<tr>
				<th>번호</th><th>제목</th><th>조회수</th><th>작성자</th>
			</tr>
			</thead>
			<tbody>
				<% if (list == null || list.size() == 0) { %>
				<tr>
					<td colspan="4">- 작성된 글이 없습니다 -</td>
				</tr>
				<% } else {
					for (int i = 0; i < list.size(); i++) {
						BbsDto bbs = list.get(i); %>
						<tr>
							<td><%=bbs.getSeq()%></td>
							<td style="text-align:left;">
								<%=arrow(bbs.getDepth())%>
								
								<a href="bbsDetail.jsp?seq=<%=bbs.getSeq()%>"><%=bbs.getTitle()%></a>
							</td>
							<td><%=bbs.getReadcount()%></td>
							<td><%=bbs.getId()%></td>
						</tr>
					<% }
				} %>
			</tbody>
		</table>
		<br/><br/>

		<% for (int i = 0; i < pageBbs; i++) {
				if (pageNumber == i) { %>
					<span style="font-size: 15pt; color: blue; font-weight: bold;"><%=i + 1%></span>
		<% } else { %>
			<a href="#none" title="<%=i + 1%>페이지" onclick="goPage(<%=i%>)"
				style="font-size: 15pt; color: #000000; font-weight: bold; text-decoration: none;">
				[<%=i + 1%>]</a>
		<% }
		} %>
		<br/><br/>

		<select id="category">
			<option value="">검색</option>
			<option value="title">제목</option>
			<option value="content">내용</option>
			<option value="writer">작성자</option>
		</select>
		<input type="text" id="keyword" size="20" value="<%=keyword%>"/>
		<button type="button" onclick="searchBtn()">검색</button>
		<br/><br/>

		<a href="bbsWrite.jsp">글 작성하기</a>
	</div>

	<script type="text/javascript">
		function searchBtn() {
			let category = document.getElementById("category").value;
			let keyword = document.getElementById("keyword").value;
			if (category == "") {
				alert("- 카테고리를 선택해주십시오 -");
				return;
			} else if (keyword.trim() == "") {
				alert("- 검색어를 입력해 주십시오 -");
				return;
			}
			location.href = "bbsList.jsp?category=" + category + "&keyword=" + keyword.trim();
		}

		function goPage(pageNumber) {
	        location.href = "bbsList.jsp?category=<%=category%>"
	                      + "&keyword=<%=keyword%>"
	                      + "&pageNumber=" + pageNumber;
	    }
		/*function goPage(pageNumber) {
		let category = document.getElementById("category").value;
		let keyword = document.getElementById("keyword").value;
		location.href = "bbsList.jsp?category=" + category
				+ "&keyword=" + keyword.trim()
				+ "&pageNumber=" + pageNumber;
		}*/
	</script>
</body>
</html>