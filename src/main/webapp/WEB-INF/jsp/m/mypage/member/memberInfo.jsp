<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_member" />
<meta name="menu_no" content="mypage_110" />
<script>

	$(document).ready(function(){
		createNumeric("#phoneNo, #birthDate");
	})
	
	// 환불계좌관리 이동
	function goMemberAccount(){
		location.href = "${CTX}/mypage/member/memberAccount.do";
	}
	
	// 수정
	function memberModify(){
		var emailGubun = $("#emailGubun option:selected").attr("data-cdNm");
		var email;
		
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
		
		var frm = document.memberForm;
		frm.action = "${CTX}/mypage/member/memberModify.do";
		frm.submit();
		
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
				 			"email"	:	$("#email").val(),
				 			"memberIdx"	:	$("#memberIdx").val()
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
	
</script>
</head>
<body>
	<form name="memberForm" method="post" onsubmit="return false;">
		<input type="hidden" name="checkEmailFlag" id="checkEmailFlag" value="N">
		<input type="hidden" name="memberIdx" id="memberIdx" value="${detail.memberIdx}">
		<input type="hidden" name="checkEmail" id="checkEmail" value="${detail.email}">
		<input type="hidden" name="email" id="email" value="">
		<input type="hidden" name="smsYn" id="smsYn" value="">
		<input type="hidden" name="emailYn" id="emailYn" value="">
		<input type="hidden" name="joinType" id="joinType" value="0">
		
		<input type="hidden" name="smsYnOrg" id="smsYnOrg" value="${detail.smsYn}">
		<input type="hidden" name="emailYnOrg" id="emailYnOrg" value="${detail.emailYn}">
		
		<div class="content comm-mypage mypage-modify">
		    
		    <div class="page-body">
		        
		        <div class="tab-menu-line tab-2">
		            <ul>
		                <li class="active"><a href="javascript:">회원정보 관리</a></li>
		                <li><a href="javascript:" onclick="goMemberAccount();">환불계좌 관리</a></li>
		            </ul>
		        </div>
		
		        <div class="order-form">
		            <div class="form-group">
		                <div class="group-title">
		                    <h3 class="tit ico-req"><span class="em">필수 항목</span></h3>
		                </div>
		                <div class="row">
		                    <div class="col col-6">
		                        <div class="form-control">
		                            <input type="text" class="input readonly" value="${detail.memberNm}" readonly="readonly"/>
		                        </div>
		                    </div>
		                    <div class="col col-6">
		                        <div class="optbox">
		                            <span class="radiobox">
		                                <input type="radio" name="gender" id="genderM" value="M" class="radio" <c:if test="${detail.gender eq 'M'}">checked</c:if>>
		                                <label for="genderM" class="lbl-txt">남</label>
		                            </span>
		                            <span class="radiobox">
		                                <input type="radio" name="gender" id="genderF" value="F" class="radio" <c:if test="${detail.gender eq 'F'}">checked</c:if>>
		                                <label for="genderF" class="lbl-txt">여</label>
		                            </span>
		                        </div>
		                    </div>
		                </div>
		                <div class="row">
		                    <div class="col col-12">
		                        <div class="form-control">
		                            <input type="text" class="input readonly" readonly="readonly" value="${detail.memberId}"/>
		                        </div>
		                    </div>
		                </div>
		                
		                <div class="row-group row-password">
		                    <a href="#formRowPassword" class="btn full ico-toggle" data-toggle="collapse"><span class="txt">비밀번호 변경</span></a>
		                    <div id="formRowPassword" class="group-password" style="display: none;">
		                        <div class="row">
		                            <div class="col col-12">
		                                <div class="form-control">
		                                    <input type="password" name="memberPwd" id="memberPwd" class="input" placeholder="비밀번호" maxlength="15">
		                                </div>
		                                <p class="note">* 비밀번호 (8~15자 영문, 숫자, 특수기호 중 2가지 조합)</p>
		                            </div>
		                        </div>        
		                        <div class="row">
		                            <div class="col col-12">
		                                <div class="form-control">
		                                    <input type="password" name="chkMemberPwd" id="chkMemberPwd" class="input" placeholder="비밀번호 확인" maxlength="15">
		                                </div>
		                            </div>
		                        </div>    
		                    </div>
		                </div>
		                <div class="row">
							<div class="col col-6">
								<div class="form-control">
									<c:set var="email" value="${fn:split(detail.email ,'@') }"/>
									<input type="text" class="input" id="email1" value="${email[0]}" placeholder="이메일" maxlength="50">
								</div>
							</div>
							<c:set var="emailFlag" value="0"/>
							<c:set var="emailNm" value=""/>
							<c:forEach var="list" items="${mailList}">
								<c:if test="${emailFlag eq 0}">
									<c:choose>
										<c:when test="${list.cdNm eq email[1]}">
											<c:set var="emailFlag" value="1"/>
											<c:set var="emailNm" value="${list.cdNm}"/>
										</c:when>
										<c:otherwise>
											<c:set var="emailNm" value="직접입력"/>
										</c:otherwise>
									</c:choose>
								</c:if>
							</c:forEach>
							<div class="col col-6">
								<div class="form-control sym-at">
									<div class="opt_select">
										<select name="emailGubun" id="emailGubun">
											<option value="" disabled hidden selected>선택</option>
											<c:forEach var="list" items="${mailList}">
												<option value="${list.commonCd}" data-cdNm="${list.cdNm}" <c:if test="${list.commonCd eq 'MAIL_KIND_ETC'}">data-etc-target="email1"</c:if> <c:if test="${emailNm eq list.cdNm}">selected</c:if>  >
													${list.cdNm}
												</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col col-12">
								<div class="form-control" data-etc-name="email1" <c:if test="${emailFlag eq 0}">style="display:block;"</c:if>>
									<input type="text" class="input" id="email2" <c:if test="${emailFlag eq 0}">value="${email[1]}" </c:if> placeholder="이메일 직접입력" maxlength="50">
								</div>
							</div>
						</div>
		                <div class="row">
		                    <div class="col col-6">
		                        <span class="checkbox">
		                            <input type="checkbox" id="chkEmailYn" class="check" <c:if test="${detail.emailYn eq 'Y' }">checked</c:if>>
		                            <label for="chkEmailYn" class="lbl">마케팅 메일 수신 동의</label>
		                        </span>
		                    </div>
		                    <div class="col col-6" style="padding-left: 30px;">
									<button type="button" class="btn full" onclick="emailChk(this);"><span class="txt">이메일 중복확인</span></button>
								</div>
		                </div>
		                <div class="row">
		                    <div class="col col-12">
		                        <div class="form-control">
		                            <input type="text" class="input" name="phoneNo" id="phoneNo" value="${detail.phoneNo}" placeholder="휴대폰번호 (‘-’를 제외한 숫자만 입력)" maxlength="11" />
		                        </div>
		                    </div>
		                </div>
		                <div class="row">
		                    <div class="col col-12">
		                        <span class="checkbox">
		                            <input type="checkbox" id="chkSmsYn" class="check" <c:if test="${detail.smsYn eq 'Y' }">checked</c:if>>
		                            <label for="chkSmsYn" class="lbl">SMS 수신 동의</label>
		                        </span>
		                    </div>
		                </div>
		            </div>
		            <div class="form-group form-etc">
		            	<div class="group-title">
							<h3 class="tit ico-req2"><span>선택 항목</span></h3>
						</div>						
		                <div class="row">
		                    <div class="form-label"><label for="">생년월일</label></div>
		                    <div class="col col-12">
		                        <div class="form-control">
		                            <input type="text" name="birthDate" id="birthDate" class="input" value="${detail.birthDate}" placeholder="생년월일 8자리 (예: 19800101)" maxlength="8" <c:if test="${!empty detail.birthDate}">readonly</c:if>/>
		                        </div>
		                    </div>
		                </div>
		                <p class="note">* 생년월일을 입력하시면 생일쿠폰을 받으실 수 있습니다.</p>
<%-- 
						<div class="row">
							<div class="form-label"><label for="">가입경로</label></div>
							<div class="col col-12">
								<div class="optgroup">
									<span class="radiobox">
										<input type="radio" class="radio" name="optKnow" id="optKnow1">
										<label for="optKnow1" class="lbl">인터넷 포탈 검색</label>
									</span>
									<span class="radiobox">
										<input type="radio" class="radio" name="optKnow" id="optKnow2">
										<label for="optKnow2" class="lbl">신문 / 잡지 광고</label>
									</span>
									<span class="radiobox">
										<input type="radio" class="radio" name="optKnow" id="optKnow3">
										<label for="optKnow3" class="lbl">지인소개</label>
									</span>
								</div>
								<div class="optgroup">
									<span class="radiobox">
										<input type="radio" class="radio" name="optKnow" id="optKnow4">
										<label for="optKnow4" class="lbl">SNS</label>
									</span>
									<span class="radiobox">
										<input type="radio" class="radio" name="optKnow" id="optKnow5">
										<label for="optKnow5" class="lbl">기타 (직접입력)</label>
									</span>
								</div>
							</div>
						</div>
		                <div class="row">
		                    <div class="form-label"><label for="">추천인</label></div>
		                    <div class="col col-12">
		                        <div class="form-control">
		                            <input type="text" name="recommender" id="recommender" class="input" value="${detail.recommender}" placeholder="아이디 or 이메일" <c:if test="${!empty detail.recommender}">readonly</c:if>/>
		                        </div>
		                    </div>
		                </div>
						<div class="note">
							<p>* 추천인 혜택</p>
							<ul class="bu-list">
								<li><i class="bu">-</i> 가입 완료하시면 회원님께 1,000포인트 추가 적립</li>
								<li><i class="bu">-</i> 고객님께서 첫 주문을 하시면 최초 1회 추천인에게 1,000 포인트 지급</li>
							</ul>
						</div>
--%>
		            </div>
		        </div>
		
		        <div class="btn-box confirm">
		            <button type="button" class="btn" onclick="location.href='${CTX}/mypage/order/main.do'"><span class="txt">취소</span></button>
		            <button type="button" class="btn black" onclick="memberModify();"><span class="txt">수정</span></button>
		        </div>
		
		        
		    </div>
		    
		    <div class="guidebox">
	            <div class="guide-cont">
	                <ul class="bu-list">
	                    <li><span class="bu">-</span> 회원정보는 개인정보 보호방침ㆍ취급방침에 따라 안전하게 보호됩니다.</li>
	                    <li><span class="bu">-</span> 회원님의 동의 없이는 기재하신 회원정보가 공개되지 않습니다.</li>
	                    <li><span class="bu">-</span> 보다 다양한 서비스를 받으시려면 정확한 정보를 항상 유지해 주셔야 합니다.</li>
	                    <li><span class="bu">-</span> 비밀번호가 타인에게 노출되지 않도록 주의하시고, 비밀번호는 주기적으로 변경하시는 것이 좋습니다.</li>
	                </ul>
	            </div>
	        </div>
	        
		</div>		
	</form>
</body>
</html>