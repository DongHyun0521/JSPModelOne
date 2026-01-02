<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>JSPModelOne: 회원가입</title>
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
	<h2>회원가입</h2>
	<div class="center">
		<form action = "regiAf.jsp" method="post">
			<table>
				<tr>
					<th>아이디</th>
					<td>
						<input type="text" name="id" id="id" size="20"/><br/>
						<p id="id_check" style="font-size: 8px"></p>
						<button type="button" id="id_chk_btn">아이디 중복 확인</button>
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td>
						<input type="text" name="pw" id="pw" size="20"/><br/>
					</td>
				</tr>
				<tr>
					<th>이름</th>
					<td>
						<input type="text" name="name" id="name" size="20"/><br/>
					</td>
				</tr>
				<tr>
					<th>이메일</th>
					<td>
						<input type="text" name="email" id="email" size="20"/><br/>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
					<input type="submit" value="회원가입 완료"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function () {
			$("#id_chk_btn").click(function () {
				$.ajax({
					url: "idcheck.jsp",
					type: "post",
					dataType: "text",
					data: {"id": $("#id").val()},
					success: function (data) {
						//alert(data);
						if (data.trim() == "1") {
							$("#id_check").css("color", "red");
							$("#id_check").text("- 이미 사용 중인 아이디입니다 -");
							$("#id").val("");
						}
						else if (data.trim() == "0") {
							$("#id_check").css("color", "blue");
							$("#id_check").text("- 사용 가능한 아이디입니다 -");
						}
					},
					error: function () {
						alert("에러");
					}
				});
			});
		});
	</script>
</body>
</html>