<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>JSPModelOne: 로그인</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script src="http://lab.alexcican.com/set_cookies/cookie.js" type="text/javascript" ></script>
	<style type="text/css">
		.center {
			margin: auto;
			width: 60%;
			border: 2px solid black;
			padding: 10px;
		}
	</style>
</head>
<body>
	<h2>Login</h2>
	<div class="center">
	<form action="loginAf.jsp" method="post">
		<table border="1">
			<tr>
				<th>아이디</th>
				<td>
					<input type="text" id="id" name="id" size="20"/><br/>
					<input type="checkbox" id="chk_save_id" name="id" size="20"/>아이디 저장하기
				</td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input type="password" id="pw" name="pw" size="20"></td><br/>
			</tr>
			<tr>
				<td colspan="2" align="center">
				<input type="submit" id="login" value="로그인하기"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="regi.jsp">회원가입</a>
			</tr>
		</table>
	</form>
	</div>
	
	<script type="text/javascript">
		// cookie에서 user_id 산출
		let user_id = $.cookie("user_id");
		
		if (user_id != null) {	// 쿠키에 아이디가 존재할 때
			$("#id").val(user_id);
			$("#chk_save_id").prop("checked", true);
		}
		
		$("#chk_save_id").click(function () {
			if ($("#chk_save_id").is(":checked") == true) {	// 아이디 저장이 체크 되었을 때
				if ($("#id").val().trim() == "") {
					alert("- 아이디를 입력해주세요 -")
					$("#chk_save_id").prop("checked", false);	// 체크 풀기
				}
				else {
					$.cookie("user_id", $("#id").val().trim(), {expires:7, path:'/'})	// 7일 후 만료
				}
			}
			else {
				$.removeCookie("user_id", {path:'/'});
			}
		});
	</script>
</body>
</html>