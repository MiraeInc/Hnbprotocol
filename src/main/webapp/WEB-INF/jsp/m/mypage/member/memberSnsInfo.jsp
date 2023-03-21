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
		createNumeric("#phoneNo");
	})
	
	// SNS 회원 정보 수정
	function memberSnsModify(){
		if($.trim($("#memberNm").val()) == ""){
			alert("성명을 입력해 주세요.");
			$("#memberNm").focus();
			return false;
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
		
		// 마케팅 메일 수신 동의
		if ($("#chkEmailYn").is(":checked")){
			$("#emailYn").val("Y");
		}else{
			$("#emailYn").val("N");
		}
		
		// SMS 수신 동의
		if ($("#chkSmsYn").is(":checked")){
			$("#smsYn").val("Y");
		}else{
			$("#smsYn").val("N");
		}
		
		var frm = document.memberForm;
		frm.action = "${CTX}/mypage/member/memberModify.do";
		frm.submit();
		
	}
	
</script>
</head>
<body>
	<form name="memberForm" method="post" onsubmit="return false;">
		<input type="hidden" name="memberIdx" id="memberIdx" value="${detail.memberIdx}">
		<input type="hidden" name="joinType" id="joinType" value="1">
		<input type="hidden" name="smsYn" id="smsYn" value="">
		<input type="hidden" name="emailYn" id="emailYn" value="">
					
		<div class="content comm-auth comm-mypage mypage-modify">
		
	        <div class="page-body">
				<div class="member-social">
	          		<div class="social-wrap">
	          			<c:if test="${detail.snsType eq 'SNS_TYPE10'}">
		                	<span class="social-icon">
		                        <img src="${CTX}/images/m/common/img_sns_kakao.png" alt="" />
		                    </span>                        
		                    <dl class="summary-detail">
		                        <dt>카카오톡</dt>
		                        <dd></dd>
		                    </dl>
	          			</c:if>
	          			<c:if test="${detail.snsType eq 'SNS_TYPE20'}">
	          				<span class="social-icon">
		                        <img src="${CTX}/images/m/common/img_sns_naver.png" alt="" />
		                    </span>                        
		                    <dl class="summary-detail">
		                        <dt>네이버</dt>
		                        <dd></dd>
		                    </dl>
	          			</c:if>
	          			<c:if test="${detail.snsType eq 'SNS_TYPE40'}">
	          				<span class="social-icon">
		                        <img src="${CTX}/images/m/common/img_sns_payco.png" alt="" />
		                    </span>                        
		                    <dl class="summary-detail">
		                        <dt>페이코</dt>
		                        <dd></dd>
		                    </dl>
	          			</c:if>
	                </div>
				</div>
				<div>
					<div class="row">
						<div class="col col-12">
	                    	<div class="form-control">
	                            <input type="text" id="" class="input readonly" readonly="readonly" value="${detail.email}" />
	                        </div>
						</div>
					</div>
					<div class="row">
						<div class="col col-12">
	                        <span class="checkbox">
	                            <input type="checkbox" id="chkEmailYn" class="check" <c:if test="${detail.emailYn eq 'Y' }">checked</c:if>/>
	                            <label for="chkEmailYn" class="lbl">마케팅 메일 수신 동의</label>
	                        </span>
						</div>
					</div>
					<div class="row">
						<div class="col col-6">
	                        <div class="form-control">
	                            <input type="text" class="input" name="memberNm" id="memberNm" value="${detail.memberNm}" maxlength="10" />
	                        </div>
						</div>
						<div class="col col-6">
	                        <div class="optbox">
	                            <span class="radiobox">
	                                <input type="radio" name="gender" id="genderM" value="M" class="radio" <c:if test="${detail.gender eq 'M'}">checked</c:if>/>
	                                <label for="genderM" class="lbl-txt">남</label>
	                            </span>
	                            <span class="radiobox">
	                                <input type="radio" name="gender" id="genderF" value="F" class="radio" <c:if test="${detail.gender eq 'F'}">checked</c:if>/>
	                                <label for="genderF" class="lbl-txt">여</label>
	                            </span>
	                        </div>
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
								<input type="checkbox" id="chkSmsYn" class="check" <c:if test="${detail.smsYn eq 'Y' }">checked</c:if>/>
								<label for="chkSmsYn" class="lbl">SMS 수신 동의</label>
							</span>
				
						</div>
					</div>
				</div>
	
				<div class="btn-box confirm">
					<button type="button" class="btn" onclick="location.href='${CTX}/mypage/order/main.do'"><span class="txt">취소</span></button>
					<button type="button" class="btn black" onclick="memberSnsModify();"><span class="txt">수정</span></button>
				</div>
	        </div>
	    </div>
	</form>
</body>
</html>