<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE">
<html>
<head>
<meta name="decorator" content="join.main" />
<script>

	$(document).ready(function(){
		createNumeric("#senderPhoneNo");
		$("#mandomSessionId").val(localStorage.getItem("mandomSessionId"));					// 비회원 로컬스토리지 세션 ID
		
		// 비회원 주문내역 포커스
		if($("#memberFlag").val() == "N"){
			$("#memberTap1").removeClass("active");
			$("#loginTab1").removeClass("active").hide();
			$("#memberTap2").addClass("active");
			$("#loginTab2").addClass("active").show();
		}
		
		// 회원 로그인 
		$("#memberId, #memberPwd").keydown(function(key){
			if(key.keyCode == 13){
				goLoginCheck("1");
			}
		});
		
		// 비회원 주문내역
		$("#orderCd, #senderNm, #senderPhoneNo").keydown(function(key){
			if(key.keyCode == 13){
				goLoginCheck("2");
			}
		});

		// 아이폰일 경우 sns 간편로그인 숨김처리
		if (window.navigator.userAgent.indexOf("app_ios") !== -1) {
			$(".sns_simple_link").css("display", "none");
		}
	})

	function goLoginCheck(type){
		var frm = document.writeform;
		if(type =="1"){																		// 일반회원 로그인
			if($("input[name=cookieIdCheck]:checked").length >0){
				$("#saveMemberIdFlag").val("Y");
			}else{
				$("#saveMemberIdFlag").val("N");
			}	
			if($.trim($("#memberId").val()) == ""){ alert("아이디를 입력하세요."); $("#memberId").focus(); return false;}
			if($.trim($("#memberPwd").val()) == ""){ alert("비밀번호를 입력하세요."); $("#memberPwd").focus();return false;}
			$("#memberOrderYn").val("Y");
			frm.action = "${CTX}/login/loginCheck.do";
			frm.submit();
		}else if(type =="2"){																// 비회원 로그인
			
			if($.trim($("#orderCd").val()) == ""){ alert("주문번호를 입력하세요."); $("#orderCd").focus(); return false;}
			if($.trim($("#senderNm").val()) == ""){ alert("이름를 입력하세요."); $("#senderNm").focus();return false;}
			if($.trim($("#senderPhoneNo").val()) == ""){ alert("핸드폰 번호를 입력하세요."); $("#senderPhoneNo").focus();return false;}
			if(phoneCheck($("#senderPhoneNo").val()) == false){
				alert("잘못된 휴대폰 번호입니다.");
				$("#senderPhoneNo").focus();
				return false;
			}
			$("#memberOrderYn").val("N");
			frm.action = "${CTX}/login/noLoginCheck.do";
			frm.submit();
			
		}
	}

	// SNS 로그인 팝업
	function snsLoginPopup(snsType){
		// N : 네이버, F : 페이스북, K : 카카오, P : 페이코, W:원더로그인

		/*
		팝업방식이 모바일 웹에서 동작을 제대로 안하는 문제때문에 페이지 링크 방식으로 변경
		*/

		if(snsType == "N"){
		    location.href = "${naverAuthUrl}";
			// owScrollYesCenter("${naverAuthUrl}", "_blank", "444", "510");
		}else if(snsType == "K"){
		    location.href = "${kakaoAuthUrl}";
			// owScrollYesCenter("${kakaoAuthUrl}", "_blank", "444", "510");
		}else if(snsType == "W"){
		    location.href = "${wonderAuthUrl}";
			// owScrollYesCenter("${wonderAuthUrl}", "_blank", "444", "510");
		}else{
	        location.href = "${paycoAuthUrl}";
			// owScrollYesCenter("${paycoAuthUrl}", "_blank", "444", "510");
		}
	}
	
	// 비회원 구매
	function noMemberPurchase(){
		$("#sessionId").val(getSessionId());
		
		var frm = document.orderForm;
		frm.action="${CTX}/order/cartOrderProc.do";
		frm.submit();
	}

	<%-- 세션ID --%>
	function getSessionId(){
		if(localStorage.getItem("mandomSessionId") == null){
			localStorage.setItem("mandomSessionId","<%=session.getId()%>");
		}
	
		return localStorage.getItem("mandomSessionId");
	}

</script>
</head>
<body>
	<form name="orderForm" id="orderForm" method="post" onsubmit="return false;">
		<input type="hidden" name="sessionId" id="sessionId" value=""/>
		<input type="hidden" name="nomemberOrderYn" id="nomemberOrderYn" value="Y"/>
		<input type="hidden" name="orderGoodsInfoListStr" id="orderGoodsInfoListStr" value="${VO.orderGoodsInfoListStr}"/>
	</form>

	<div id="single">
		<header id="header">
			<h1 id="logo">
				<a href="${CTX}/main.do">
					<img src="${CTX }/images/logo_mandom.png" alt="Mandom">
				</a>
			</h1>
			<!-- 2023.04.11 수정 -->
			<!-- <h2 class="page-title">로그인</h2> -->
		</header>
		<article id="container" class="clearfix">
			<form name="writeform" id="writeform"  method="post" onsubmit="return false;">
				<input type="hidden" name="saveMemberIdFlag" id="saveMemberIdFlag" value=""/>
				<input type="hidden" name="mandomSessionId" id="mandomSessionId" value=""/>
				<input type="hidden" name="refererUrl" id="refererUrl" value="${VO.refererUrl}"/>
				<input type="hidden" name="memberFlag" id="memberFlag" value="${VO.memberFlag}"/>
				<input type="hidden" name="fromOrderFlag" id="fromOrderFlag" value="${VO.fromOrderFlag}"/>
				<input type="hidden" name="orderGoodsInfoListStr" id="orderGoodsInfoListStr" value="${VO.orderGoodsInfoListStr}"/>

				<div id="content" class="inner">
					<div class="tab" data-column="2">
						<div class="tab-buttons">
							<button id="memberTap1" data-tab-id="loginTab1" type="button" class="active">회원 로그인</button>
							<button id="memberTap2" data-tab-id="loginTab2" type="button">비회원 로그인</button>
						</div>
						<div class="tab-panel active" id="loginTab1">
							<div class="form-group">
								<input type="text" name="memberId" id="memberId" class="form-control" value="${cookieMemberId}" placeholder="아이디">
							</div>
							<div class="form-group">
								<input type="password" name="memberPwd" id="memberPwd" class="form-control" placeholder="비밀번호">
							</div>
							<div class="form-group">
								<div class="form-custom">
									<input type="checkbox" name="cookieIdCheck" id="cookieIdCheck"  <c:if test="${not empty cookieMemberId}"> checked</c:if> class="form-control"><label>아이디 저장</label>
								</div>
							</div>
							<div class="button-group">
								<a href="javascript:" class="btn btn-black" onclick="goLoginCheck('1');">로그인</a>
							</div>
						
							<div class="bullet_link">
								<a href="${CTX}/member/joinStep01.do">회원가입</a>
								<a href="${CTX}/login/findIdPwd.do">아이디/비밀번호 찾기</a>
							</div>
							
							<c:if test="${VO.fromOrderFlag eq 'Y' }">
								<div class="join_info">
									<p>로그인 하시어 구매하시면 더 많은 할인 혜택을 받으실 수 있습니다.<br>비회원으로 구매하실 분은 여기를 클릭해 주세요.</p>
									<div class="button-group">
										<a href="javascript:" class="btn btn-black" onclick="noMemberPurchase();">비회원 구매</a>
									</div>
								</div>
							</c:if>
							
							<div class="sns_simple_link">
								<strong><b>SNS 간편 로그인</b></strong>
								<!-- 2023.05.04 수정 -->
								<div class="sns_btn_cont">
									<a href="javascript:" onclick="snsLoginPopup('N');" class="sns_naver">
										<img src="${CTX}/images/ico_simplelink_naver.png" alt="네이버 SNS 간편 로그인">
										<span class="txt"><em>NAVER</em>로 로그인</span>
									</a>
									<a href="javascript:" onclick="snsLoginPopup('K');" class="sns_kakao">
										<img src="${CTX}/images/ico_simplelink_kakaotalk.png" alt="카카오톡 SNS 간편 로그인">
										<span class="txt"><em>카카오톡</em>으로 로그인</span>
									</a>
									<!-- <a href="javascript:" onclick="snsLoginPopup('A');" class="sns_apple">
										<img src="${CTX}/images/ico_simplelink_apple.png" alt="Apple SNS 간편 로그인">
										<span class="txt"><em>Apple</em>로 로그인</span>
									</a> -->
									<%--
									<a href="javascript:" onclick="snsLoginPopup('P');">
										<img src="${CTX}/images/ico_simplelink_payco.png" alt="페이코 SNS 간편 로그인">
									</a>
									<a href="javascript:" onclick="snsLoginPopup('W');">
										<img src="${CTX}/images/ico_simplelink_wonder.png" alt="원더쇼핑 간편 로그인">
									</a>
									<a href="javascript:" onclick="snsLoginPopup()">
										<img src="${CTX}/images/ico_simplelink_wonder.png" alt="원더 간편 로그인">
									</a>
									--%>
								</div>
							</div>
							<small>* 팝업이 차단되어 있을 경우 SNS 간편 로그인을 이용할 수 없으므로 팝업 차단 해제 후 이용 바랍니다.</small>
						</div>
	
						<div class="tab-panel" id="loginTab2">
							<div class="form-group">
								<input type="text" class="form-control" name="orderCd" id="orderCd" placeholder="주문번호"/>
							</div>
							<div class="form-group">
								<input type="text" class="form-control" name="senderNm" id="senderNm" placeholder="이름"/>
							</div>
							<div class="form-group">
								<input type="tel" class="form-control" name="senderPhoneNo" id="senderPhoneNo" placeholder="휴대폰번호 (‘-’를 제외한 숫자만 입력)" maxlength="11"/>
							</div>
							<div class="form-group">
								<p class="text-info">* 주문번호를 분실하신 경우, 고객센터(02-544-1191)로 문의 바랍니다.</p>
							</div>

							<div class="button-group">
								<a href="javascript:" class="btn btn-black" onclick="goLoginCheck('2');">주문내역 조회</a>
							</div>
							<div class="bullet_link">
								<a href="${CTX}/member/joinStep01.do">회원가입</a>
								<a href="${CTX}/login/findIdPwd.do">아이디/비밀번호 찾기</a>
							</div>
						</div>
					</div>
				</div>
			</form>
		</article>
	</div>
</body>
</html>