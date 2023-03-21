<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<title>결과</title>
<script src="${CTX}/js/jquery-3.2.1.min.js"></script>
<script>document.domain = "<spring:message code='server.defaultDomain'/>"</script>
<script>
	//$(document).ready(function(){
	function go() {
		$("#mandomSessionId").val(localStorage.getItem("mandomSessionId"));					// 비회원 로컬스토리지 세션 ID	
		var snsJoinCheck = "${snsJoinCheck}";							// SNS 가입 체크 
		var snsConnectCheck = "${snsConnectCheck}";				// SNS 연동 체크
		var snsReJoinCheck = "${snsReJoinCheck}";					// SNS 재가입 체크
		var snsAuth = "${snsAuth}";												// 마이페이지 SNS 계정 인증 값
		var snsMode = $("#snsMode").val();
		var frm = document.joinSnsForm;
		
		var mDomain = "<spring:message code='server.mDomain'/>";			// 모바일 도메인 주소
		
		if(snsJoinCheck == 0 && snsMode == "join" && snsConnectCheck == 0 && snsReJoinCheck == 0){
			frm.action =  mDomain + "/m/member/joinSnsForm.do";
		}else if(snsJoinCheck == 0 && snsMode == "login" && snsConnectCheck == 0 && snsReJoinCheck == 0){
			frm.action = mDomain + "/m/member/joinSnsForm.do";
		}else if(snsAuth == "Y"){
			frm.action = mDomain + "/m/mypage/pwdCheck.do";
		}else{
			frm.action = mDomain + "/m/login/snsLoginCheck.do";
		}


		/*
		팝업방식이 모바일 웹에서 동작을 제대로 안하는 문제때문에 페이지 링크 방식으로 변경
		*/

		// window.opener.name = "joinSnsWindow";
		// frm.target = window.opener.name;

		frm.target = "_self";
		frm.submit();

		/*
		setTimeout(function() {
			self.close();	
		}, 2000);
        */
		
	}
</script>

</head>
<body onload="go();">
	<form name="joinSnsForm" id="joinSnsForm"  method="POST" >
		<input type="hidden" name="memberNm" value="${resultMap.name }" />
		<input type="hidden" name="email" value="${resultMap.email }" />
		<input type="hidden" name="gender" value="${resultMap.gender }" />
		<input type="hidden" name="snsCd" value="${resultMap.snsCd }" />
		<input type="hidden" name="snsType" value="${resultMap.snsType }" />
		<input type="hidden" id="snsMode" name="snsMode" value="${resultMap.snsMode }" />
		<input type="hidden" name="refererUrl" id="refererUrl" value="${snsReferer}">
	</form>
</body>
</html>
