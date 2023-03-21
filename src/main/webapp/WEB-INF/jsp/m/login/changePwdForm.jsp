<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="join.main" />
<script>

	// 다음에 변경
	function nextChangePwd(){
		var params = $("form[name=changeForm]").serialize();
		
		$.ajax({
			 url: "${CTX}/ajax/login/nextChangePwdAjax.do",
			 data : params,
			 type: "post",	
			 async: false,
			 cache: false,
			 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 error: function(request, status, error){ 
			 	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			 },
			 success: function(flag){
				 if(Number(flag) >0){
					 location.href = "${CTX}/main.do";
				 }
			 }
		});
	}
	
	// 지금 변경
	function changePwd(){
		
		if($.trim($("#nowMemberPwd").val()) == ""){
			alert("현재 비밀번호를 입력해주세요.");
			$("#nowMemberPwd").focus();
			return false;
		}
		
		// 비밀번호
		if($.trim($("#memberPwd").val()) == ""){
			alert("새 비밀번호를 입력해주세요.");
			$("#memberPwd").focus();
			return false;
		}
		
		if(checkPwd("memberPwd") ==false){
			alert("비밀번호는 (8~15자 영문, 숫자, 특수기호 중 2가지 조합)으로 입력 바랍니다."); 
			$("#memberPwd").focus(); 
			return false;
		}
		
		// 비밀번호 확인
		if($.trim($("#chkMemberPwd").val()) == ""){
			alert("새 비밀번호를 다시 한 번 입력해주세요.");
			$("#chkMemberPwd").focus();
			return false;
		}
		
		if($.trim($("#memberPwd").val()) != $.trim($("#chkMemberPwd").val())){
			alert("새 비밀번호와 일치하지 않습니다."); 
			return false;
		}
		
		// 현재 비밀번호 체크
		$.ajax({
			 url: "${CTX}/ajax/login/checkPwdAjax.do",
			 data : {
				 			"memberIdx"	:	$("#memberIdx").val(),
				 			"memberPwd"	:	$("#nowMemberPwd").val(),
				 		},
			 type: "post",	
			 async: false,
			 cache: false,
			 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 error: function(request, status, error){ 
			 	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			 },
			 success: function(flag){
				 if(Number(flag) == 0){
					 alert("현재 비밀번호가 일치하지 않습니다.");
					 $("#checkPwdFlag").val("N");
				 }else{
					$("#checkPwdFlag").val("Y");
				 }
			 }
		});
		
		if($("#checkPwdFlag").val() == "Y"){
			if($.trim($("#nowMemberPwd").val()) == $.trim($("#memberPwd").val())){
				alert("현재 비밀번호와 다른 비밀번호로 설정해주세요."); 
				return false;
			}else{
				$.ajax({
					 url: "${CTX}/ajax/login/changePwdAjax.do",
					 data : {
						 			"memberIdx"	:	$("#memberIdx").val(),
						 			"memberId"	:	$("#memberId").val(),
						 			"memberPwd"	:	$("#memberPwd").val()
						 		},
					 type: "post",	
					 async: false,
					 cache: false,
					 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
					 error: function(request, status, error){ 
					 	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
					 },
					 success: function(flag){
						 if(Number(flag) > 0){
							 alert("비밀번호 변경이 완료 되었습니다.");
							 location.href = "${CTX}/main.do";
						 }
					 }
				});
			}
		}
	}
	
</script>
</head>
<body>
	<div id="single">
		<header id="header">
			<h1 id="logo">
				<a href="${CTX}/main.do">
					<img src="${CTX }/images/logo_mandom.png" alt="Mandom">
				</a>
			</h1>
			<h2 class="page-title">개인정보 보호를 위한<br/>비밀번호 변경</h2>
		</header>
		<article id="container" class="clearfix">
			<div id="content" class="inner">
				
				<!-- <p class="password_comment">고객님의 소중한 정보를 지키기 위해<br>지금 바로 비밀번호를 바꿔 주세요!</p> -->
				<p class="password_comment">
					안녕하세요, 고객님!<br/>
					고객님께서는 6개월 동안  <br/>
					<span class="color-1">동일한 비밀번호를</span> 사용하고 계십니다.<br/>
					<br/>
					주기적인 <span class="color-1">비밀번호 변경</span>으로<br/>
					소중한 개인정보를 보호해 주세요.
				</p>
				
				<form name="changeForm" method="post" onsubmit="return false;">
					<input type="hidden" name="memberIdx" id="memberIdx" value="${detail.memberIdx }" >
					<input type="hidden" name="memberId" id="memberId" value="${detail.memberId }" >
					<input type="hidden" name="memberPwd" value="${detail.memberPw }" >
					<input type="hidden" name="joinType" id="joinType" value="${detail.joinType }" >
					<input type="hidden" name="checkPwdFlag" id="checkPwdFlag" value="N">
		
					<div class="form-group">
						<input type="password" id="nowMemberPwd" class="form-control" placeholder="현재 비밀번호">
					</div>
					<div class="form-group">
						<input type="password" id="memberPwd" class="form-control" placeholder="새 비밀번호 (8~15이내 영문, 숫자, 특수기호 중  2가지 조합)">
					</div>
					<div class="form-group" style="margin-bottom: 0;">
						<input type="password" id="chkMemberPwd" class="form-control" placeholder="새 비밀번호 확인">
					</div>
					<p class="form-static text-left">* 현재와 다른 비밀번호를 사용해 주세요.</p>
					<div class="button-group">
						<a href="javascript:" class="btn btn-white" onclick="nextChangePwd();">다음에 변경</a>
						<a href="javascript:" class="btn btn-black2" onclick="changePwd();">지금 변경</a>
					</div>
				</form>
			</div>
		</article>
	</div>
</body>
</html>