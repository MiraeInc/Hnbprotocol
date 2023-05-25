<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="join.main" />
<script>

	var doneSubmit = false;			// 중복 가입 제한

	$(document).ready(function(){
		createNumeric("#phoneNo");
	})
	
	// 확인
	function goSave(){
		var regexName = /^[가-힣]{2,4}$/; 
		var emailGubun = $("#emailGubun option:selected").attr("data-cdNm");
		var email;
		
		// 이름
		if($.trim($("#memberNm").val()) == ""){
			alert("이름을 입력해 주세요.");
			$("#memberNm").focus();
			return false;
		}
		
		// 성별
		// if($("input[name=gender]:checked").length <1){
        //	alert("성별을 선택해 주세요.");
        //	return false;
        // }
	
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
		
		var serviceCheck = $("input:checkbox[id='serviceCheck']").is(":checked");
		var privacyCheck = $("input:checkbox[id='privacyCheck']").is(":checked"); 
		var marketingYn = $("input:checkbox[id='marketingYn']").is(":checked");
		var individualInfoYn = $("input:checkbox[id='individualInfoYn']").is(":checked");
		
		if(!serviceCheck){
            alert("서비스 이용약관에 동의해 주세요.");
            return false;
        }
		
        if(!privacyCheck) {
            alert("개인정보 수집이용 동의에 동의해 주세요.");
            return false;
        }
        
        if(marketingYn){
        	$("#hiddenMarketingYn").val("Y");
        }else{
        	$("#hiddenMarketingYn").val("N");
        }
        
        if(individualInfoYn){
        	$("#hiddenIndividualInfoYn").val("Y");
        }else{
        	$("#hiddenIndividualInfoYn").val("N");
        }
        
		if(doneSubmit == false){					// 중복 가입 방지
			doneSubmit = true;
		
			var frm = document.joinSnsForm;
			frm.action = "${CTX}/member/memberSave.do";
			frm.submit();
		}
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
					<!-- FORM -->
					<form name="joinSnsForm" method="post" onsubmit="return false;">
						<input type="hidden" name="memberId" id="memberId" value="${VO.snsCd }" >
						<input type="hidden" name="marketingYn" id="hiddenMarketingYn" >
						<input type="hidden" name="individualInfoYn" id="hiddenIndividualInfoYn" >
						<input type="hidden" name="email" id="email" value="">
						<input type="hidden" name="smsYn" id="smsYn" value="">
						<input type="hidden" name="emailYn" id="emailYn" value="">
						<input type="hidden" name="joinType" id="joinType" value="1">
						<input type="hidden" name="checkEmailFlag" id="checkEmailFlag" value="N">
						<input type="hidden" name="checkEmail" id="checkEmail" value="">
						<input type="hidden" name="snsType" id="snsType" value="${VO.snsType }">
						
						<strong class="requirement requied">SNS 간편가입 (회원정보 입력)</strong>
						<div class="form-group form-hold">
							<div class="form-holder" style="width: 48%;">
								<input type="text" class="form-control" name="memberNm" id="memberNm" value="${VO.memberNm }" placeholder="이름" maxlength="8">
							</div>
							<div class="form-holder" style="font-size: 0;">
								<div class="form-radiobox">
									<input type="radio" class="form-control" name="gender" <c:if test="${VO.gender eq 'M' }">checked</c:if> value="M">
									<label>남</label>
								</div>
								<div class="form-radiobox">
									<input type="radio" class="form-control" name="gender" <c:if test="${VO.gender eq 'F' }">checked</c:if> value="F">
									<label>여</label>
								</div>
							</div>
						</div>
						<div class="form-group form-hold">
							<div class="form-holder" data-extend="@"  style="width:52%">
								<c:set var="email" value="${fn:split(VO.email ,'@') }"/>
								<input type="text" class="form-control" id="email1" value="${email[0]}" placeholder="이메일" maxlength="50">
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
							
							<div class="form-holder" style="width: 22%">
								<select class="form-control" name="emailGubun" id="emailGubun" required>
									<option value="" disabled hidden selected>선택</option>
									<c:forEach var="list" items="${mailList}">
										<option value="${list.commonCd}" data-cdNm="${list.cdNm}" <c:if test="${list.commonCd eq 'MAIL_KIND_ETC'}">data-etc-target="email1"</c:if> <c:if test="${emailNm eq list.cdNm}">selected</c:if>  >
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
						
						<div class="form-group" data-etc-name="email1" <c:if test="${emailFlag eq 0}">style="display:block;"</c:if>>
							<input type="text" class="form-control" id="email2" value="${email[1]}" placeholder="이메일 직접입력" maxlength="50">
						</div>
						<div class="form-group form-hold">
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

						<div class="inner_terms">
							<div class="form-group form-indent">
								<div class="form-custom">
									<input type="checkbox" class="form-control" id="serviceCheck" data-radio-check="temp1">
									<label>서비스 이용약관 <span class="highlight">(필수)</span></label>
								</div>
							</div>
							<div class="textarea-box">
								<button class="textarea-btn" type="button"></button>
								<div class="textarea-wrap">
									<div class="textarea-scroll">
										<div class="etc-section">
											<dl>
												<dt id="term-1">제 1 조 (목적)</dt>
												<dd>
													<p>이 약관은 (주)엠와이지비(이하 "회사"라 한다)가 운영하는 면역공방몰(http://hnb.miraeint.co.kr 이하 "몰"이라 한다)에서 제공하는 인터넷 관련 서비스(이하 "서비스"라 한다)를
													이용함에 있어 "몰"과 이용자의 권리&middot;의무 및 책임사항을 규정함을 목적으로 합니다.<br>※「PC통신, 모바일 등을 이용하는 전자거래에 대해서도 그 성질에 반하지 않는 한 이 약관을 준용합니다」</p>
												</dd>
	
												<dt id="term-2">제 2 조 (정의)</dt>
												<dd>
													<ol class="indent">
														<li>1. "몰"이란 회사가 재화 또는 용역을 이용자에게 제공하기 위하여 컴퓨터 등 정보통신설비를 이용하여 재화 또는 용역을 거래할 수 있도록 설정한 가상의 영업장을 말하며, 아울러 사이버몰을 운영하는 사업자의 의미로도 사용합니다.</li>
														<li>2. "이용자"란 "몰"에 접속하여 이 약관에 따라 "몰"이 제공하는 서비스를 받는 회원 및 비회원을 말합니다.</li>
														<li>3. "회원"이라 함은 "몰"에 개인정보를 제공하여 회원등록을 한 자로서, "몰"의 정보를 지속적으로 제공받으며, "몰"이 제공하는 서비스를 계속적으로 이용할 수 있는 자를 말합니다.</li>
														<li>4. "비회원"이라 함은 회원에 가입하지 않고 "몰"이 제공하는 서비스를 이용하는 자를 말합니다.</li>
													</ol>
												</dd>
	
												<dt id="term-3">제 3 조 (약관의 명시와 개정)</dt>
												<dd>
													<ol class="indent">
														<li>1. "몰"은 이 약관의 내용과 상호, 영업소 소재지, 대표자의 성명, 사업자등록번호, 통신판매업신고번호, 개인정보관리책임자, 연락처(전화, 팩스, 전자우편 주소 등) 등을 이용자가 알 수 있도록 "몰"의 초기 서비스화면(전면)에 게시합니다.</li>
														<li>2. "몰"은 약관의규제에관한법률, 전자거래기본법, 전자서명법, 정보통신망이용촉진등에관한법률, 방문판매등에관한법률, 소비자보호법 등 관련법을 위배하지 않는 범위에서 이 약관을 개정할 수 있습니다.</li>
														<li>3. "몰"이 약관을 개정할 경우에는 적용일자 및 개정사유를 명시하여 현행약관과 함께 몰의 초기화면에 그 적용일자 7일 이전부터 적용일자 전일까지 공지합니다.</li>
														<li>4. "몰"이 약관을 개정할 경우에는 그 개정약관은 그 적용일자 이후에 체결되는 계약에만 적용되고 그 이전에 이미 체결된 계약에 대해서는 개정 전의 약관조항이 그대로 적용됩니다.
														다만 이미 계약을 체결한 이용자가 개정약관 조항의 적용을 받기를 원하는 뜻을 제3항에 의한 개정약관의 공지기간 내에 "몰"에 송신하여 "몰"의 동의를 받은 경우에는 개정약관 조항이 적용됩니다.</li>
														<li>5. 이 약관에서 정하지 아니한 사항과 이 약관의 해석에 관하여는 정부가 제정한 전자거래소비자보호지침 및 관계법령 또는 상관례에 따릅니다.</li>
													</ol>
												</dd>
	
												<dt id="term-4">제 4 조 (서비스의 제공 및 변경)</dt>
												<dd>
													<ol class="indent">
														<li>1. "몰"은 다음과 같은 업무를 수행합니다.
															<span>
															1) 재화 또는 용역에 대한 정보 제공 및 구매계약의 체결<br>
															2) 구매계약이 체결된 재화 또는 용역의 배송<br>
															3) 기타 "몰"이 정하는 업무
															</span>
														</li>
														<li>2. "몰"은 재화의 품절 또는 기술적 사양의 변경 등의 경우에는 장차 체결되는 계약에 의해 제공할 재화&middot;용역의 내용을 변경할 수 있습니다.
														이 경우에는 변경된 재화&middot;용역의 내용 및 제공일자를 명시하여 현재의 재화&middot;용역의 내용을 게시한 곳에 그 제공일자 이전 7일부터 공지합니다.</li>
														<li>3. "몰"이 제공하기로 이용자와 계약을 체결한 서비스의 내용을 재화의 품절 또는 기술적 사양의 변경 등의 사유로 변경할 경우에는 "몰"은 이로 인하여 이용자가 입은 손해를 배상합니다.
														단, "몰"에 고의 또는 과실이 없는 경우에는 그러하지 아니합니다.</li>
													</ol>
												</dd>
	
												<dt id="term-5">제 5 조 (서비스의 중단)</dt>
												<dd>
													<ol class="indent">
														<li>1. "몰"은 컴퓨터 등 정보통신설비의 보수점검&middot;교체 및 고장, 통신의 두절 등의 사유가 발생한 경우에는 서비스의 제공을 일시적으로 중단할 수 있습니다.</li>
														<li>2. 제1항에 의한 서비스 중단의 경우에는 "몰"은 제8조에 정한 방법으로 이용자에게 통지합니다.</li>
														<li>3. "몰"은 제1항의 사유로 서비스의 제공이 일시적으로 중단됨으로 인하여 이용자 또는 제3자가 입은 손해에 대하여 배상합니다. 단, "몰"에 고의 또는 과실이 없는 경우에는 그러하지 아니합니다.</li>
													</ol>
												</dd>
	
												<dt id="term-6">제 6 조 (회원가입)</dt>
												<dd>
													<ol class="indent">
														<li>1. 이용자는 "몰"이 정한 가입 양식에 따라 회원정보를 기입한 후 이 약관에 동의한다는 의사표시를 함으로서 회원가입을 신청합니다.</li>
														<li>2. "몰"은 제1항과 같이 회원으로 가입할 것을 신청한 이용자 중 다음 각호에 해당하지 않는 한 회원으로 등록합니다.
															<span>
															1) 가입신청자가 이 약관 제7조 제3항에 의하여 이전에 회원자격을 상실한 적이 있는 경우. 다만, 제7조 제3항에 의한 회원자격 상실 후 3년이 경과한 자로서 "몰"의 회원재가입 승낙을 얻은 경우에는 예외로 한다.<br>
															2) 등록 내용에 허위, 기재누락, 오기가 있는 경우<br>
															3) 기타 회원으로 등록하는 것이 "몰"의 기술상 현저히 지장이 있다고 판단되는 경우
															</span>
														</li>
													</ol>
													<p>회원가입계약의 성립시기는 "몰"의 승낙이 회원에게 도달한 시점으로 합니다.</p>
													<p>회원은 제18조 제1항에 의한 등록사항에 변경이 있는 경우, 즉시 전자우편 기타 방법으로 "몰"에 대하여 그 변경사항을 알려야 합니다.</p>
												</dd>
	
												<dt id="term-7">제 7 조 (회원 탈퇴 및 자격 상실 등)</dt>
												<dd>
													<ol class="indent">
														<li>1. 회원은 "몰"에 언제든지 탈퇴를 요청할 수 있으며 "몰"은 즉시 회원탈퇴를 처리합니다.</li>
														<li>2. 회원이 다음 각호의 사유에 해당하는 경우, "몰"은 회원자격을 제한 및 정지시킬 수 있습니다.
															<span>
															1) 가입 신청시에 허위 내용을 등록한 경우<br>
															2) "몰"을 이용하여 구입한 재화&middot;용역 등의 대금, 기타 "몰" 이용에 관련하여 회원이 부담하는 채무를 기일에 이행하지 않는 경우<br>
															3) 다른 사람의 "몰" 이용을 방해하거나 그 정보를 도용하는 등 전자거래질서를 위협하는 경우<br>
															4) "몰"을 이용하여 법령과 이 약관이 금지하거나 공서양속에 반하는 행위를 하는 경우
															</span>
														</li>
														<li>3. "몰"이 회원 자격을 제한&middot;정지 시킨후, 동일한 행위가 2회 이상 반복되거나 30일 이내에 그 사유가 시정되지 아니하는 경우 "몰"은 회원자격을 상실시킬 수 있습니다.</li>
														<li>4. 재판매 목적으로 "몰"에서 재화 등을 중복 구매하는 등 "몰"의 거래질서를 방해하는 경우, "몰"은 당해 회원의 회원자격을 상실 시킬 수 있습니다.</li>
														<li>5. "몰"이 회원자격을 상실시키는 경우에는 회원등록을 말소합니다. 이 경우 회원에게 이를 통지하고, 회원등록 말소 전에 소명할 기회를 부여합니다.</li>
													</ol>
												</dd>
	
												<dt id="term-8">제 8 조 (회원에 대한 통지)</dt>
												<dd>
													<ol class="indent">
														<li>1. "몰"이 회원에 대한 통지를 하는 경우, 회원이 "몰"에 제출한 전자우편 주소로 할 수 있습니다.</li>
														<li>2. "몰"은 불특정다수 회원에 대한 통지의 경우 1주일 이상 "몰" 게시판에 게시함으로서 개별 통지에 갈음할 수 있습니다. 다만, 회원 본인의 거래와 관련하여 중대한 영향을 미치는 사항에 대하여는 개별통지를 합니다.</li>
													</ol>
												</dd>
	
												<dt id="term-9">제 9 조 (구매신청)</dt>
												<dd>
													<p>"몰" 이용자는 "몰" 상에서 이하의 방법에 의하여 구매를 신청합니다.
														<span>
														1) 성명, 주소, 연락처 번호의 입력<br>
														2) 재화 또는 용역의 선택<br>
														3) 결제방법의 선택<br>
														4) 재화 등의 구매신청 및 이에 관한 확인 또는 "몰"의 확인에 대한 동의
														</span>
													</p>
												</dd>
	
												<dt id="term-10">제 10 조 (계약의 성립)</dt>
												<dd>
													<ol class="indent">
														<li>1. "몰"은 제9조와 같은 구매신청에 대하여 다음 각호에 해당하지 않는 한 승낙합니다.
															<span>
															1) 신청 내용에 허위, 기재누락, 오기가 있는 경우<br>
															2) 미성년자가 청소년보호법에서 금지하는 재화 및 용역을 구매하는 경우<br>
															3) 기타 구매신청에 승낙하는 것이 "몰" 기술상 현저히 지장이 있다고 판단하는 경우<br>
															4) 구매신청 고객이 제7조에 따른 회원 자격이 제한&middot;정지 또는 상실된 회원으로 확인되었을 경우
															</span>
														</li>
														<li>2. "몰"의 승낙이 제13조 제1항의 수신확인통지 형태로 이용자에게 도달한 시점에 계약이 성립한 것으로 봅니다.</li>
													</ol>
												</dd>
	
												<dt id="term-11">제 11 조 (지급방법)</dt>
												<dd>
													<p>"몰"에서 구매한 재화 또는 용역에 대한 대금지급방법은 다음 각호의 하나로 할 수 있습니다.
														<span>
														1) 계좌이체<br>
														2) 신용카드 결제<br>
														3) 온라인 입금<br>
														4) 휴대폰 결제<br>
														5) 간편 결제(페이코, 페이나우)
														</span>
													</p>
												</dd>
	
												<dt id="term-12">제 12 조 (수신확인통지&middot;구매신청 변경 및 취소)</dt>
												<dd>
													<ol class="indent">
														<li>1. "몰"은 이용자의 구매신청이 있는 경우 이용자에게 수신확인통지를 합니다.</li>
														<li>2. 수신확인통지를 받은 이용자는 의사표시의 불일치 등이 있는 경우에는 수신확인통지를 받은 후 즉시 구매신청 변경 및 취소를 요청할 수 있습니다.</li>
														<li>3. "몰"은 배송 전 이용자의 구매신청 변경 및 취소 요청이 있는 때에는 지체없이 그 요청에 따라 처리합니다.</li>
													</ol>
												</dd>
	
												<dt id="term-13">제 13 조 (배송)</dt>
												<dd>
													<p>"몰"은 이용자가 구매한 재화에 대해 배송수단, 수단별 배송비용 부담자, 수단별 배송기간 등을 명시합니다. 만약 "몰"의 고의&middot;과실로 약정 배송기간을 초과한 경우에는 그로 인한 이용자의 손해를 배상합니다.</p>
												</dd>
	
												<dt id="term-14">제 14 조 (환급, 반품 및 교환)</dt>
												<dd>
													<ol class="indent">
														<li>1. "몰"은 이용자가 구매신청한 재화 또는 용역이 품절 등의 사유로 재화의 인도 또는 용역의 제공을 할 수 없을 때에는 지체없이 그 사유를 이용자에게 통지하고,
														사전에 재화 또는 용역의 대금을 받은 경우에는 대금을 받은 날부터 3일이내에, 그렇지 않은 경우에는 그 사유발생일로부터 3일 이내에 계약해제 및 환급절차를 취합니다.</li>
														<li>2. 다음 각호의 경우에는 "몰"은 배송된 재화일지라도 재화를 반품받은 다음 영업일 이내에 이용자의 요구에 따라 즉시 환급, 반품 및 교환 조치를 합니다. 다만, 그 요구기한은 배송된 날로부터 20일 이내로 합니다.
															<span>
															1) 배송된 재화가 주문내용과 상이하거나 "몰"이 제공한 정보와 상이할 경우<br>
															2) 배송된 재화가 파손, 손상되었거나 오염되었을 경우<br>
															3) 재화가 광고에 표시된 배송기간보다 늦게 배송된 경우<br>
															4) 방문판매등에관한법률 제18조에 의하여 광고에 표시하여야 할 사항을 표시하지 아니한 상태에서 이용자의 청약이 이루어진 경우
															</span>
														</li>
													</ol>
												</dd>
	
												<dt id="term-15">제 15 조 (개인정보보호)</dt>
												<dd>
													<ol class="indent">
														<li>1. "몰"은 이용자의 정보수집시 구매계약 이행에 필요한 최소한의 정보를 수집합니다. 다음 사항을 필수사항으로 하며 그 외 사항은 선택사항으로 합니다.
															<span>
															1) 로그인 ID(회원의 경우)<br>
															2) 비밀번호(회원의 경우)<br>
															3) 이름<br>
															4) 이메일<br>
															5) 휴대폰번호<br>
															6) 주문기록
															</span>
														</li>
														<li>2. "몰"이 이용자의 개인식별이 가능한 개인정보를 수집하는 때에는 반드시 당해 이용자의 동의를 받습니다.</li>
														<li>3. 제공된 개인정보는 당해 이용자의 동의없이 목적 외의 이용이나 제3자에게 제공할 수 없으며, 이에 대한 모든 책임은 "몰"이 집니다. 다만, 다음의 경우에는 예외로 합니다.
															<span>
															1) 배송업무상 배송업체에게 배송에 필요한 최소한의 이용자의 정보(성명, 주소, 전화번호)를 알려주는 경우<br>
															2) 통계작성, 학술연구 또는 시장조사를 위하여 필요한 경우로서 특정 개인을 식별할 수 없는 형태로 제공하는 경우<br>
															3) 재화 등의 거래에 따른 대금정산을 위하여 필요한 경우<br>
															4) 도용방지를 위하여 본인확인에 필요한 경우<br>
															5) 법률의 규정 또는 법률에 의하여 필요한 불가피한 사유가 있는 경우
															</span>
														</li>
														<li>4. "몰"이 제2항과 제3항에 의해 이용자의 동의를 받아야 하는 경우에는 개인정보관리 책임자의 신원(소속, 성명 및 전화번호 기타 연락처), 정보의 수집목적 및 이용목적,
														제3자에 대한 정보제공 관련사항(제공받는 자, 제공목적 및 제공할 정보의 내용)등 정보통신망이용촉진등에관한법률 제16조 제3항이 규정한 사항을 미리 명시하거나 고지해야 하며 이용자는 언제든지 이 동의를 철회할 수 있습니다.</li>
														<li>5. 이용자는 언제든지 "몰"이 가지고 있는 자신의 개인정보에 대해 열람 및 오류정정을 요구할 수 있으며 "몰"은 이에 대해 지체없이 필요한 조치를 취할 의무를 집니다.
														이용자가 오류의 정정을 요구한 경우에는 "몰"은 그 오류를 정정할 때까지 당해 개인정보를 이용하지 않습니다.</li>
														<li>6. "몰"은 개인정보 보호를 위하여 관리자를 한정하여 그 수를 최소화하며 신용카드, 은행계좌 등을 포함한 이용자의 개인정보의 분실, 도난, 유출, 변조 등으로 인한 이용자의 손해에 대하여 모든 책임을 집니다.</li>
														<li>7. "몰" 또는 그로부터 개인정보를 제공받은 제3자는 개인정보의 수집목적 또는 제공받은 목적을 달성한 때에는 당해 개인정보를 지체없이 파기합니다.</li>
													</ol>
												</dd>
	
												<dt id="term-16">제 16 조 ("몰"의 의무)</dt>
												<dd>
													<ol class="indent">
														<li>1. "몰"은 법령과 이 약관이 금지하거나 공서양속에 반하는 행위를 하지 않으며 이 약관이 정하는 바에 따라 지속적이고, 안정적으로 재화·용역을 제공하는 데 최선을 다하여야 합니다.</li>
														<li>2. "몰"은 이용자가 안전하게 인터넷 서비스를 이용할 수 있도록 이용자의 개인정보(신용정보 포함)보호를 위한 보안 시스템을 갖추어야 합니다.</li>
														<li>3. "몰"이 상품이나 용역에 대하여 '표시·광고의공정화에관한법률' 제3조 소정의 부당한 표시·광고행위를 함으로써 이용자가 손해를 입은 때에는 이를 배상할 책임을 집니다.</li>
														<li>4. "몰"은 이용자가 원하지 않는 영리목적의 광고성 전자우편을 발송하지 않습니다.</li>
													</ol>
												</dd>
	
												<dt id="term-17">제 17 조 (회원의 ID 및 비밀번호에 대한 의무)</dt>
												<dd>
													<ol class="indent">
														<li>1. 제18조의 경우를 제외한 ID와 비밀번호에 관한 관리책임은 회원에게 있습니다.</li>
														<li>2. 회원은 자신의 ID 및 비밀번호를 제3자에게 이용하게 해서는 안됩니다.</li>
														<li>3. 회원이 자신의 ID 및 비밀번호를 도난당하거나 제3자가 사용하고 있음을 인지한 경우에는 바로 "몰"에 통보하고 "몰"의 안내가 있는 경우에는 그에 따라야 합니다.</li>
													</ol>
												</dd>
	
												<dt id="term-18">제 18 조 (이용자의 의무)</dt>
												<dd>
													<p>이용자는 다음 행위를 하여서는 안됩니다.
														<span>
														1) 신청 또는 변경시 허위내용의 등록<br>
														2) 타인의 정보 도용<br>
														3) "몰"에 게시된 정보의 변경<br>
														4) "몰"이 정한 정보 이외의 정보(컴퓨터 프로그램 등)의 송신 또는 게시<br>
														5) "몰" 기타 제3자의 저작권 등 지적재산권에 대한 침해<br>
														6) "몰" 기타 제3자의 명예를 손상시키거나 업무를 방해하는 행위<br>
														7) 외설 또는 폭력적인 메시지·화상·음성 기타 공서양속에 반하는 정보를 몰에 공개 또는 게시하는 행위
														</span>
													</p>
												</dd>
	
												<dt id="term-19">제 19 조 (연결 "몰"과 피연결 "몰" 간의 관계)</dt>
												<dd>
													<ol class="indent">
														<li>1. 상위 "몰"과 하위 "몰"이 하이퍼 링크(예 : 하이퍼 링크의 대상에는 문자, 그림 및 동화상 등이 포함됨)방식 등으로 연결된 경우, 전자를 연결 "몰"(웹 사이트)이라고 하고 후자를 피연결 "몰"(웹 사이트)이라고 합니다.</li>
														<li>2. 연결 "몰"은 피연결 "몰"이 독자적으로 제공하는 재화·용역에 의하여 이용자와 행하는 거래에 대해서 보증책임을 지지 않는다는 뜻을 연결 "몰"의 사이트에서 명시한 경우에는 그 거래에 대한 보증책임을지지 않습니다.</li>
													</ol>
												</dd>
	
												<dt id="term-20">제 20 조 (저작권의 귀속 및 이용제한)</dt>
												<dd>
													<ol class="indent">
														<li>1. "몰"이 작성한 저작물에 대한 저작권 기타 지적재산권은 "몰"에 귀속합니다.</li>
														<li>2. 이용자는 "몰"을 이용함으로써 얻은 정보를 "몰"의 사전 승낙없이 복제, 송신, 출판, 배포, 방송 기타 방법에 의하여 영리목적으로 이용하거나 제3자에게 이용하게 하여서는 안됩니다.</li>
													</ol>
												</dd>
	
												<dt id="term-21">제 21 조 (분쟁해결)</dt>
												<dd>
													<ol class="indent">
														<li>1. "몰"은 이용자가 제기하는 정당한 의견이나 불만을 반영하고 그 피해를 보상처리하기 위하여 피해보상처리기구를 설치·운영합니다.</li>
														<li>2. "몰"은 이용자로부터 제출되는 불만사항 및 의견은 우선적으로 그 사항을 처리합니다. 다만, 신속한 처리가 곤란한 경우에는 이용자에게 그 사유와 처리일정을 즉시 통보해 드립니다.</li>
														<li>3. "몰"과 이용자간에 발생한 분쟁은 전자거래기본법 제28조 및 동 시행령 제15조에 의하여 설치된 전자거래분쟁조정위원회의 조정에 따를 수 있습니다.</li>
													</ol>
												</dd>
	
												<dt id="term-22">제 22 조 (재판권 및 준거법)</dt>
												<dd>
													<ol class="indent">
														<li>1. "몰"과 이용자 간에 발생한 전자거래 분쟁에 관한 소송은 제소 당시의 이용자의 주소에 의하고, 주소가 없는 경우에는 거소를 관할하는 지방법원의 전속관할로 합니다.
														다만, 제소 당시 이용자의 주소 또는 거소가 분명하지 않거나 외국 거주자의 경우에는 민사소송법상의 관할법원에 제기합니다.</li>
														<li>2. "몰"과 이용자간에 제기된 전자거래 소송에는 한국법을 적용합니다.</li>
													</ol>
												</dd>
											</dl>
										</div>
									</div>
								</div>
							</div>

							<div class="form-group form-indent">
								<div class="form-custom">
									<input type="checkbox" class="form-control" id="privacyCheck" data-radio-check="temp1">
									<label>개인정보 수집 &middot; 이용 동의 <span class="highlight">(필수)</span></label>
								</div>
							</div>
							<div class="textarea-box">
								<button class="textarea-btn" type="button"></button>
								<div class="textarea-wrap">
									<div class="textarea-scroll">
										<div class="terms">
											<div class="terms-row">
												<div class="terms-head">
													<p>개인정보의 수집 및 이용목적</p>
												</div>
												<div class="terms-body" style="width: 70%;">
													<p>회사는 수집한 개인정보를 다음의 목적을 위해 활용합니다.</p>
													<ul>
														<li>- 서비스 제공에 관한 계약 이행 및 서비스 제공에 따른 요금정산
															<p>콘텐츠 제공, 구매 및 요금 결제, 물품배송 또는 청구지 등 발송, 금융거래 본인 인증 및 금융 서비스</p>
														</li>
														<li>- 회원 관리
															<p>회원제 서비스 이용에 따른 본인확인, 개인 식별, 불량회원의 부정 이용 방지와 비인가 사용 방지, 가입 의사 확인, 연령확인, 불만처리 등 민원처리, 고지사항 전달</p>
														</li>
														<li>- 마케팅 및 광고에 활용
															<p>이벤트 등 광고성 정보 전달, 접속 빈도 파악 또는 회원의 서비스 이용에 대한 통계</p>
														</li>
													</ul>
												</div>
											</div>
	
											<div class="terms-row">
												<div class="terms-head">
													<p>수집하는 개인정보 항목</p>
												</div>
												<div class="terms-body" style="width: 70%;">
													<ul>
														<li>- 필수 항목 : 이름, 로그인 ID, 비밀번호, 이메일, 휴대폰번호, 주문기록</li>
														<li>- 선택 항목 : 생년월일, 환불받을 계좌번호</li>
													</ul>
												</div>
											</div>
	
											<div class="terms-row">
												<div class="terms-head">
													<p>개인정보의 보유 및 이용기간</p>
												</div>
												<div class="terms-body" style="width: 70%;">
													<p>원칙적으로, 개인정보 수집 및 이용목적이 달성된 후에는 해당 정보를 지체 없이 파기합니다.</p>
													<p>단, 관계법령의 규정에 의하여 보존할 필요가 있는 경우 회사는 아래와 같이 관계법령에서 정한 일정한 기간 동안 회원정보를 보관합니다.</p>
													<ul>
														<li>- 보존 항목 : 이름, 아이디, 비밀번호, 이메일 주소, 휴대폰번호, 주문기록</li>
														<li>- 보존 근거 : 전자상거래등에서의 소비자보호에 관한 법률</li>
														<li>- 보존 기간 : 5년</li>
														<li>- 표시/광고에 관한 기록 : 6개월 (전자상거래등에서의 소비자보호에 관한 법률)</li>
														<li>- 계약 또는 청약철회 등에 관한 기록 : 5년 (전자상거래등에서의 소비자보호에 관한 법률)</li>
														<li>- 대금결제 및 재화 등의 공급에 관한 기록 : 5년 (전자상거래등에서의 소비자보호에 관한 법률)</li>
														<li>- 소비자의 불만 또는 분쟁처리에 관한 기록 : 3년 (전자상거래등에서의 소비자보호에 관한 법률)</li>
														<li>- 신용정보의 수집/처리 및 이용 등에 관한 기록 : 3년 (신용정보의 이용 및 보호에 관한 법률)</li>
													</ul>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>

							<div class="form-group form-indent">
								<div class="form-custom">
									<input type="checkbox" class="form-control" data-radio-check="temp1">
									<label>마케팅 정보 활용 동의 <span class="highlight">(선택)</span></label>
								</div>
							</div>
							<div class="textarea-box">
								<button class="textarea-btn" type="button"></button>
								<div class="textarea-wrap">
									<div class="textarea-scroll">
										<div class="terms">
											<div class="terms-row">
												<div class="terms-body">
													<p>고객님께 더 나은 서비스 제공을 목적으로 아래와 같이 개인정보를 제공 &middot; 이용 &middot; 위탁 하고 있습니다.</p>
													<p>회사가 수집한 개인정보는 고객님의 사전 동의 없이 공개되지 않습니다.</p>
												</div>
											</div>
	
											<div class="terms-row">
												<div class="terms-head">
													<p>수집하는 개인정보 항목</p>
												</div>
												<div class="terms-body" style="width: 70%;">
													<ul>
														<li>- 필수 항목 : 이름, 아이디, 이메일 주소, 휴대폰번호, 주문기록</li>
														<li>- 선택 항목 : 생년월일, 환불받을 계좌번호</li>
													</ul>
												</div>
											</div>
	
											<div class="terms-row">
												<div class="terms-head">
													<p>개인정보의 수집 및 이용목적</p>
												</div>
												<div class="terms-body" style="width: 70%;">
													<p>제공하신 정보는 이벤트 등 광고성 정보 전달, 접속 빈도 파악 또는 회원의 서비스 이용 향상을 위한 통계에 사용됩니다.</p>
												</div>
											</div>
	
											<div class="terms-row">
												<div class="terms-head">
													<p>동의 거부 권리 및 동의 거부에 따른 제한사항</p>
												</div>
												<div class="terms-body" style="width: 70%;">
													<p>귀하는 개인정보 제공 및 동의를 거부할 권리가 있으며, 위 항목 동의 거부 시 제공 서비스 이용에 제약이 있을 수 있습니다.</p>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>

							<div class="form-group form-indent">
								<div class="form-custom">
									<input type="checkbox" class="form-control" data-radio-check="temp1">
									<label>개인정보 제공 <span class="highlight">(선택)</span></label>
								</div>
							</div>
							<div class="textarea-box">
								<button class="textarea-btn" type="button"></button>
								<div class="textarea-wrap">
									<div class="textarea-scroll">
										<div class="terms">
											<div class="terms-row">
												<div class="terms-head">
													<p>수집하는 개인정보 항목</p>
												</div>
												<div class="terms-body" style="width: 70%;">
													<ul>
														<li>- 필수 항목 : 이름, 아이디, 이메일 주소, 휴대폰번호, 주문기록</li>
														<li>- 선택 항목 : 생년월일, 환불받을 계좌번호</li>
													</ul>
												</div>
											</div>
	
											<div class="terms-row">
												<div class="terms-head">
													<p>개인정보의 수집 및 이용목적</p>
												</div>
												<div class="terms-body" style="width: 70%;">
													<p>제공하신 정보는 이벤트 등 광고성 정보 전달, 접속 빈도 파악 또는 회원의 서비스 이용 향상을 위한 통계에 사용됩니다.</p>
												</div>
											</div>
	
											<div class="terms-row">
												<div class="terms-head">
													<p>동의 거부 권리 및 동의 거부에 따른 제한사항</p>
												</div>
												<div class="terms-body" style="width: 70%;">
													<p>귀하는 개인정보 제공 및 동의를 거부할 권리가 있으며, 위 항목 동의 거부 시 제공 서비스 이용에 제약이 있을 수 있습니다.</p>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>

							<div class="check_all">
								<div class="form-custom">
									<input type="checkbox" class="form-control" data-radio-all="temp1">
									<label>전체 동의</label>
								</div>
							</div>
						</div>

						<div class="button-group">
							<a href="javascript:" class="btn btn-black" onclick="goSave();">확인</a>
						</div>
					</form>
					<!-- //FORM -->
				</div>
			</div>
		</article>
	</div>
</body>
</html>