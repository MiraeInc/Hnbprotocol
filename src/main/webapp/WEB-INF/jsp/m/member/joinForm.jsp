<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="join.main" />
<script>

	var doneSubmit = false;			// 중복 가입 제한

	$(document).ready(function(){
		createNumeric("#phoneNo, #birthDate");
		cfHistoryNoBack();												// 뒤로가기 방지 호출
	})
	
	// 뒤로가기 방지
	function cfHistoryNoBack(){
	    if (window.history) {
	    window.history.forward(1);    //모든 브라우저에서 호출
	    }
	}
	
	// 확인
	function goSave(){
		var regexName = /^[가-힣]{2,4}$/; 
		var emailGubun = $("#emailGubun option:selected").attr("data-cdNm");
		var subscribeType = $(":input[name=subscribeType]:radio:checked").val();	
		var email;
		
		// 이름
		if($.trim($("#memberNm").val()) == ""){
			alert("이름을 입력해 주세요.");
			$("#memberNm").focus();
			return false;
		}
		
		// 성별
		if($("input[name=gender]:checked").length <1){
			alert("성별을 선택해 주세요."); 
			return false;
		}

		// 아이디
		if($.trim($("#memberId").val()) == ""){
			alert("아이디를 입력해 주세요.");
			$("#memberId").focus();
			return false;
		}
		
		if($("#checkMemberFlag").val() == "N"){
			alert("아이디 중복체크를 해주세요."); 
			return false;
		}
		
		if($("#checkMemberId").val() != $("#memberId").val()){
			alert("아이디 중복체크를 해주세요."); 
			return false;
		}
		
		// 비밀번호
		if($.trim($("#memberPwd").val()) == ""){
			alert("비밀번호를 입력해 주세요.");
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
			alert("비밀번호를 입력해 주세요.");
			$("#chkMemberPwd").focus();
			return false;
		}
		
		if($.trim($("#memberPwd").val()) != $.trim($("#chkMemberPwd").val())){
			alert("입력하신 비밀번호가 서로 다릅니다."); 
			return false;
		}

		// 이메일
		if($.trim($("#email1").val()) == ""){
			alert("이메일을 입력해 주세요.");
			$("#email1").focus();
			return false;
		}
		
		// 이메일 선택
		if($.trim($("#emailGubun").val()) == ""){
			alert("이메일을 선택해 주세요.");
			return false;
		}
		
		// 이메일 직접입력
		if($("#emailGubun").val() =="MAIL_KIND_ETC"){
			if($.trim($("#email2").val()) == ""){
				alert("이메일을 직접 입력해 주세요.");
				$("#email2").focus();
				return false;
			}else{
				email = $.trim($("#email1").val()) + "@" + $.trim($("#email2").val());
			}
		}else{
			email = $.trim($("#email1").val()) + "@" + $.trim(emailGubun);
		}
		
		// 이메일 유효성 체크
		if(emailCheck(email) ==false){
			alert("잘못된 이메일 주소입니다."); 
			return false;
		}
		
		$("#email").val(email);
		
		if($("#checkEmail").val() != $("#email").val()){
			alert("이메일 중복체크를 해주세요."); 
			return false;
		}
		
		// 마케팅 메일 수신 동의
		if ($("#chkEmailYn").is(":checked")){
			$("#emailYn").val("Y");
		}else{
			$("#emailYn").val("N");
		}
		
		// 휴대폰
		var phoneNo = $("#phoneNo").val();
		
		if($.trim(phoneNo) == ""){
			alert("휴대폰번호를 입력해 주세요.");
			$("#phoneNo").focus();
			return false;
		}
		
		// 휴대폰 유효성 체크
		if(phoneCheck(phoneNo) == false){
			alert("잘못된 휴대폰 번호입니다.");
			$("#phoneNo").focus();
			return false;
		}
		
		// SMS 수신 동의
		if ($("#chkSmsYn").is(":checked")){
			$("#smsYn").val("Y");
		}else{
			$("#smsYn").val("N");
		}
		
		// 생년월일 
		if($("#birthDate").val() != ""){
	 		if(checkBirthDate($("#birthDate").val()) ==false){
				alert("생년월일을 확인해 주세요."); 
				$("#birthDate").focus();
				return false;
			}
		}
		
		// 가입경로 기타 선택시
		if(subscribeType == "SUBSCRIBE_TYPE_ETC"){
			if($.trim($("#subscribe").val()) == ""){
				alert("기타(직접입력)를 입력해 주세요.");
				$("#subscribe").focus();
				return false;
			}
		}
		
		// 추천인 체크
  		/*if($("#recommender").val() != ""){
 			if($("#checkRecommenderFlag").val() == "N"){
 				alert("추천인 확인체크를 해주세요."); 
 				return false;
 			}
			
 			if($("#checkRecommender").val() != $("#recommender").val()){
 				alert("추천인 확인체크를 해주세요."); 
 				return false;
 			}
 		}*/
	
		if(doneSubmit == false){					// 중복 가입 방지
			doneSubmit = true;
		
			var frm = document.joinForm;
			frm.action = "${CTX}/member/memberSave.do";
			frm.submit();
		}
	}
	
	// =========	아이디 중복 체크	=========
	function duplicateChkMemberId($this){
		if($.trim($("#memberId").val()) == ""){
			alert("아이디를 입력해 주세요.");
			$("#memberId").focus();
			return false;
		}
		
		if(checkId($("#memberId").val()) == false){
			alert("아이디는 영문,숫자 조합 6~15자입니다.");
			$("#memberId").focus();
			return false;
		}
		
		if(checkBlank($("#memberId").val()) == false){
			alert("아이디에 공백이 있습니다."); 
			return false;
		}
		
		$.ajax({
			 url: "${CTX}/ajax/member/duplicateCheckMemberIdAjax.do",
			 data : {
				 			"memberId"	:	$("#memberId").val() 
				 		},
			 type: "GET",	
			 async: false,
			 cache: false,
			 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 error: function(request, status, error){ 
			 	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			 },
			 success: function(flag){
				 if(Number(flag) > 0){
					 $("#checkMemberFlag").val("N");
					 $("#checkMemberId").val("");
					 alert("이미 사용중인 아이디입니다.");
				 }else{
					 $("#checkMemberFlag").val("Y");
					 $("#checkMemberId").val($("#memberId").val());
					 alert("사용가능한 아이디입니다.");
					 $($this).addClass("active");
				 } 
			 }
		});
	}
	
	// =========	이메일 중복 체크	=========
	function emailChk($this){
		var email;
		var emailGubun = $("#emailGubun option:selected").attr("data-cdNm");
		
		// 이메일
		if($.trim($("#email1").val()) == ""){
			alert("이메일을 입력해 주세요.");
			$("#email1").focus();
			return false;
		}
		
		// 이메일 선택
		if($.trim($("#emailGubun").val()) == ""){
			alert("이메일을 선택해 주세요.");
			return false;
		}
		
		// 이메일 직접입력
		if($("#emailGubun").val() =="MAIL_KIND_ETC"){
			if($.trim($("#email2").val()) == ""){
				alert("이메일을 직접 입력해 주세요.");
				$("#email2").focus();
				return false;
			}else{
				email = $.trim($("#email1").val()) + "@" + $.trim($("#email2").val());
			}
		}else{
			email = $.trim($("#email1").val()) + "@" + $.trim(emailGubun);
		}
		
		// 이메일 유효성 체크
		if(emailCheck(email) == false){
			alert("잘못된 이메일 주소입니다."); 
			return false;
		}
		
		$("#email").val(email);
		
		// 이메일 중복 체크
		$.ajax({
			 url: "${CTX}/ajax/member/duplicateCheckEmailAjax.do",
			 data : {
				 			"email"	:	$("#email").val()  
				 		},
			 type: "GET",	
			 async: false,
			 cache: false,
			 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 error: function(request, status, error){ 
			 	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			 },
			 success: function(flag){
				 if(Number(flag) >0){ 
					 $("#checkEmailFlag").val("N");
					 $("#checkEmail").val("");
					 alert("이미 사용중인 이메일입니다.");
					 return false;
				 }else{
					 $("#checkEmail").val($("#email").val());
					 $("#checkEmailFlag").val("Y");
					 alert("사용가능한 이메일입니다.");
					 $($this).addClass("active");
				 }
			 }
		});
	}
	
	// =========	추천인 중복 체크	=========
	function duplicateChkRecommender($this){
		if($.trim($("#recommender").val()) == ""){
			alert("추천인을 입력해 주세요.");
			$("#recommender").focus();
			return false;
		}
		
		$.ajax({
			 url: "${CTX}/ajax/member/duplicateCheckRecommenderAjax.do",
			 data : {
				 			"recommender"	:	$("#recommender").val() 
				 		},
			 type: "GET",	
			 async: false,
			 cache: false,
			 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 error: function(request, status, error){ 
			 	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			 },
			 success: function(flag){
				 if(Number(flag) > 0){
					 if(Number(flag) == 100){
						 $("#checkRecommenderFlag").val("N");
						 $("#checkRecommender").val("");
						 alert("추천인이 5회 제한 회수를 초과하셨습니다. 더 이상 추천이 불가능 합니다.");
						 $($this).removeClass("active");
					 }else{
						 $("#checkRecommenderFlag").val("Y");
						 $("#checkRecommender").val($("#recommender").val());
						 alert("추천인 확인이 되었습니다. 가입을 완료하시면 포인트가 지급됩니다.");
						 $($this).addClass("active");
					 }
				 }else{
					 $("#checkRecommenderFlag").val("N");
					 $("#checkRecommender").val("");
					 alert("추천인 대상이 없습니다. 추천인 이메일 주소를 다시 한 번 확인해 주세요.");
				 }
			 }
		});
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
			<h2 class="page-title">회원가입</h2>
		</header>
		<article id="container" class="clearfix">
			<div id="content" class="inner">
				<div class="user_information">
					<form name="joinForm"  method="post" onsubmit="return false;">
						<input type="hidden" name="marketingYn" value="${VO.marketingYn}" >
						<input type="hidden" name="individualInfoYn" value="${VO.individualInfoYn}" >
						<input type="hidden" name="email" id="email" value="">
						<input type="hidden" name="smsYn" id="smsYn" value="">
						<input type="hidden" name="emailYn" id="emailYn" value="">
						<input type="hidden" name="checkMemberFlag" id="checkMemberFlag" value="N">
						<input type="hidden" name="checkMemberId" id="checkMemberId" value="">
						<input type="hidden" name="checkEmailFlag" id="checkEmailFlag" value="N">
						<input type="hidden" name="checkEmail" id="checkEmail" value="">
						<input type="hidden" name="checkRecommenderFlag" id="checkRecommenderFlag" value="N">
						<input type="hidden" name="checkRecommender" id="checkRecommender" value="">
						<input type="hidden" name="joinType" id="joinType" value="0">
						
						<strong class="requirement requied">필수 항목</strong>
						<div class="form-group form-hold">
							<div class="form-holder hasClear" style="width: 48%;">
								<input type="text" class="form-control" name="memberNm" id="memberNm" placeholder="이름" maxlength="8">
							</div>
							<div class="form-holder" style="font-size: 0;">
								<div class="form-radiobox">
									<input type="radio" class="form-control" name="gender" value="M">
									<label>남</label>
								</div>
								<div class="form-radiobox">
									<input type="radio" class="form-control" name="gender" value="F">
									<label>여</label>
								</div>
							</div>
						</div>
						<div class="form-group form-hold">
							<div class="form-holder" style="width: 74%">
								<input type="text" class="form-control" name="memberId" id="memberId" placeholder="아이디 (6~15자 영문,숫자 조합)" maxlength="16">
							</div>
							<div class="form-holder">
								<div class="check-btn">
									<a href="javascript:" class="btn btn-formcheck" onclick="duplicateChkMemberId(this);">중복확인</a>
								</div>
							</div>
						</div>
						<div class="form-group">
							<input type="password" class="form-control" name="memberPwd" id="memberPwd" placeholder="비밀번호 (8~15자 영문, 숫자, 특수기호 중 2가지 조합)" maxlength="15">
						</div>
						<div class="form-group">
							<input type="password" class="form-control" name="chkMemberPwd" id="chkMemberPwd" placeholder="비밀번호 확인" maxlength="15">
						</div>
						
						<div class="form-group form-hold">
							<div class="form-holder" data-extend="@" style="width: 52%">
								<input type="text" class="form-control" id="email1" placeholder="이메일" maxlength="50">
							</div>
							<div class="form-holder" style="width: 22%">
								<select class="form-control" name="emailGubun" id="emailGubun" required>
									<option value="" disabled hidden selected>선택</option>
									<c:forEach var="list" items="${mailList}">
										<option value="${list.commonCd}" data-cdNm="${list.cdNm}" <c:if test="${list.commonCd eq 'MAIL_KIND_ETC'}">data-etc-target="email1"</c:if>>
											${list.cdNm}
										</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-holder">
								<div class="check-btn">
									<a href="javascript:" class="btn btn-formcheck" onclick="emailChk(this);">중복확인</a>
								</div>
							</div>
						</div>
						<div class="form-group" data-etc-name="email1">
							<input type="text" class="form-control" id="email2" placeholder="이메일 직접입력" maxlength="50">
						</div>
						
						<div class="form-group form-indent">
							<div class="form-holder" style="vertical-align: middle;">
								<div class="form-custom" style="margin-right: 0;">
									<input type="checkbox" class="form-control" id="chkEmailYn">  
									<label>마케팅 메일 수신 동의</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<input type="tel" class="form-control" name="phoneNo" id="phoneNo" placeholder="휴대폰번호 (‘-’를 제외한 숫자만 입력)" maxlength="11">
						</div>
						<div class="form-group form-indent">
							<div class="form-custom">
								<input type="checkbox" class="form-control" id="chkSmsYn">
								<label>SMS 수신 동의</label>
							</div>
						</div>

						<div class="inner_bg clearfix">
							<strong class="requirement">선택 항목</strong>
							<p class="funnel_path">생년월일</p>
							<div class="form-group">
								<input type="tel" class="form-control" name="birthDate" id="birthDate" placeholder="생년월일 8자리 (예: 19800101)" maxlength="8">
							</div>
							<p class="form-static text-left" style="margin: -.5rem 0 .5rem;">* 생년월일을 입력하시면, 생일 쿠폰 (10% 할인)을 받으실 수 있습니다.</p>
							<p class="funnel_path">가입경로</p>
							<div class="form-group">
								<c:forEach var="list" items="${subscribeList}">
									<div class="form-custom">
										<input type="radio" class="form-control" name="subscribeType" <c:if test="${list.commonCd eq 'SUBSCRIBE_TYPE_ETC'}">data-etc-target="etc"</c:if> value="${list.commonCd}">
										<label>${list.cdNm }</label>
									</div>
								</c:forEach>
							</div>
							<div class="form-group" data-etc-name="etc">
								<input type="text" class="form-control custom-type4" name="subscribe" id="subscribe" placeholder="기타 (직접입력)">
							</div>
							
							<!-- <p class="funnel_path">추천인</p>   
							<div class="form-group form-hold">
								<div class="form-holder" style="width: 74%">
									<input type="text" class="form-control" name="recommender" id="recommender" placeholder="이메일" maxlength="50">
								</div>
								<div class="form-holder">
									<div class="check-btn">
										<a href="javascript:" class="btn btn-formcheck" onclick="duplicateChkRecommender(this);">추천인 확인</a>
									</div>
								</div>
							</div>
							<p class="form-static text-left" style="margin-bottom: -.5rem;">
								* 추천 혜택
								<span>- 가입 완료하시면, 회원님께 1,000P 추가 적립됩니다.</span>
								<span>- 회원님이 입력하신 추천인께도 1,000P가 추가 적립됩니다.</span>
							</p> -->
						</div>

						<div class="button-group">
							<a href="javascript:" class="btn btn-black" onclick="goSave();">확인</a>
						</div>
					</form>
				</div>
			</div>
		</article>
	</div>
</body>
</html>