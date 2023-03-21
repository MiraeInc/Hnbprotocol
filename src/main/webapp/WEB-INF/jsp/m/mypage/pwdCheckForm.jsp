<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main" />
<script>document.domain = "<spring:message code='server.defaultDomain'/>"</script>
<script>

	// 비빌번호 확인
	function pwdCheck(){
		if($.trim($("#memberPwd").val()) == ""){
			alert("비밀번호를 입력해주세요.");
			$("#memberPwd").focus();
			return false;
		}
		
		// 현재 비밀번호 체크
		$.ajax({
			 url: "${CTX}/ajax/login/checkPwdAjax.do",
			 data : {
				 			"memberIdx"	:	$("#memberIdx").val(),
				 			"memberPwd"	:	$("#memberPwd").val(),
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
					 alert("비밀번호를 정확하게 입력 해 주세요.");
					 $("#checkPwdFlag").val("N");
				 }else{
					$("#checkPwdFlag").val("Y");
				 }
			 }
		});
		
		if($("#checkPwdFlag").val() == "Y"){
			var frm = document.pwdCheckForm;
			frm.action = "${CTX}/mypage/pwdCheck.do";
			frm.submit();
		}
	}
	
	// SNS 계정 인증
	function pwdSnsCheck(){
		owScrollYesCenter("${snsAuthUrl}", "_blank", "444", "510");
	}
	
</script>
</head>
<body>
	<main id="main" class="container" role="main">
		<c:choose>
			<c:when test="${USERINFO.memberLoginType eq 'MEMBER'}">
				<form name="pwdCheckForm" method="post" onsubmit="return false;">
					<input type="hidden" name="checkPwdFlag" id="checkPwdFlag" value="N">
					<input type="hidden" name="memberIdx" id="memberIdx" value="${VO.memberIdx}">
					<input type="hidden" name="refererUrl" id="refererUrl" value="${VO.refererUrl}">
					
					<div class="content comm-mypage comm-auth">
		                <div class="page-body">
							<div class="section-intro">
		                		<div class="intro-title">
		                			<h3 class="tit">비밀번호 재확인</h3>
		                		</div>
		                		<p class="intro-text2">
									안전한 정보 보호를 위해<br/> 비밀번호를 다시 한번 확인합니다
								</p>     
		                	</div>
		                	<div class="auth-form">
		                		<div class="row">
		                			<div class="col col-12">
		                				<div class="form-control">
		                					<input type="password" name="memberPwd" id="memberPwd" class="input" placeholder="비밀번호" />
		                				</div>
		                			</div>
		                		</div>
		                	</div>
		                	<div class="btn-box confirm">
		                        <button type="button" class="btn" onclick="history.back();"><span class="txt">취소</span></button>
		                        <button type="button" class="btn black" onclick="pwdCheck();"><span class="txt">확인</span></button>
		                    </div>
		                </div>
	            	</div>
				</form>
			</c:when>
			<c:otherwise>
				<div class="content comm-mypage comm-auth">
	                <div class="page-body">
						<div class="section-intro">
							<div class="intro-title">
								<h3 class="tit">비밀번호 재확인</h3>
							</div>
							<p class="intro-text2 sns">
								고객님의 소중한 개인정보를 보호하기 위해<br/>
								SNS 계정을 다시 한번 확인하고 있습니다.
							</p>
						</div>
						<div class="btn-box confirm">
							<button type="button" class="btn black full" onclick="pwdSnsCheck();"><span class="txt">SNS 계정 인증</span></button>
						</div>
					</div>
            	</div>
			</c:otherwise>
		</c:choose>
	</main>
</body>
</html>