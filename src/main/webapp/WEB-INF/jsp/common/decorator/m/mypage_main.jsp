<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE html>
<html lang="ko">
<head>

<meta name="google-site-verification" content="wgIiYXH4a2mB0VAj6Zg1rlSpg3YKD7TBU-Y2Kfzsh7M" />

<title><spring:message code='common.title' /></title>
<meta charset="UTF-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="author" content="">
<meta name="HandheldFriendly" content="true"/>
<meta name="MobileOptimized" content="320"/>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
<meta name="format-detection" content="telephone=no, address=no, email=no">
<title><spring:message code='common.title' /></title>
<link rel="apple-touch-icon" href="${CTX}/images/favicon-ios.png">
<link rel="shortcut icon" href="${CTX}/images/favicon-android.png">

<!-- GATSBY MOBILE CSS -->
<link rel="stylesheet" href="${CTX}/css/${DEVICE}/normalize.css">
<link rel="stylesheet" href="${CTX}/css/${DEVICE}/swiper.min.css">
<link rel="stylesheet" href="${CTX}/css/lightslider.min.css">
<link rel="stylesheet" href="${CTX}/css/remodal.min.css">
<link rel="stylesheet" href="${CTX}/css/remodal-default-theme.css">
<link rel="stylesheet" href="${CTX}/css/jquery-ui.min.css">

<!-- GATSBY 2018 RENEWAL -->
<link rel="stylesheet" href="${CTX}/css/${DEVICE}/common_new.css">
<link rel="stylesheet" href="${CTX}/css/${DEVICE}/contents_new.css">

<!-- GATSBY MOBILE JS -->
<script type="text/javascript" src="https://wcs.naver.net/wcslog.js"></script>
<script src="${CTX}/js/jquery-3.2.1.min.js"></script>
<script src="${CTX}/js/${DEVICE}/swiper.min.js"></script>
<script src="${CTX}/js/jquery-ui.min.js"></script>
<script src="${CTX}/js/jquery-migrate-3.0.0.min.js"></script>
<script src="${CTX}/js/lightslider.min.js"></script>
<script src="${CTX}/js/${DEVICE}/hammer.min.js"></script>
<script src="${CTX}/js/${DEVICE}/masonry.pkgd.min.js"></script>
<script src="${CTX}/js/${DEVICE}/ui.site.new.js"></script>
<script src="${CTX}/js/remodal.min.js"></script>
<script src="${CTX}/js/commonScript.js"></script>
<script src="${CTX}/js/${DEVICE}/cart.js"></script>

<%-- 
 <!-- GATSBY MOBILE CSS -->
<link rel="stylesheet" href="${CTX}/css/${DEVICE}/normalize.css">
<link rel="stylesheet" href="${CTX}/css/lightslider.min.css">
<link rel="stylesheet" href="${CTX}/css/remodal.min.css">
<link rel="stylesheet" href="${CTX}/css/remodal-default-theme.css">
<link rel="stylesheet" href="${CTX}/css/jquery-ui.min.css">

<!-- GATSBY 2018 RENEWAL -->
<link rel="stylesheet" href="${CTX}/css/${DEVICE}/common_2018.css">
<link rel="stylesheet" href="${CTX}/css/${DEVICE}/contents_2018.css">

<!-- GATSBY MOBILE JS -->
<script type="text/javascript" src="https://wcs.naver.net/wcslog.js"></script>
<script src="${CTX}/js/jquery-3.2.1.min.js"></script>
<script src="${CTX}/js/jquery-ui.min.js"></script>
<script src="${CTX}/js/${DEVICE}/hammer.min.js"></script>
<script src="${CTX}/js/lightslider.min.js"></script>
<script src="${CTX}/js/${DEVICE}/ui.site.js"></script>
<script src="${CTX}/js/remodal.min.js"></script>
<script src="${CTX}/js/commonScript.js"></script>
<script src="${CTX}/js/${DEVICE}/cart.js"></script>
 --%>

<decorator:head/>
<decorator:usePage id="thePage" />
<c:set var="menu_2depth" value="${thePage.getProperty('meta.menu_2depth')}"/>
<c:set var="menu_3depth" value="${thePage.getProperty('meta.menu_3depth')}"/>
<c:set var="menu_no" value="${thePage.getProperty('meta.menu_no')}"/>

<script type="text/javascript">
	// mobon Enliple Tracker Start
	var device = "${DEVICE}".toUpperCase();
	(function(a,g,e,n,t){a.enp=a.enp||function(){(a.enp.q=a.enp.q||[]).push(arguments)};n=g.createElement(e);n.async=!0;n.defer=!0;n.src="https://cdn.megadata.co.kr/dist/prod/enp_tracker_self_hosted.min.js";t=g.getElementsByTagName(e)[0];t.parentNode.insertBefore(n,t)})(window,document,"script");
	enp('create', 'common', 'mandom', { device: device });    
	enp('send', 'common', 'mandom');
	// mobon Enliple Tracker End

	function goMain(){
		location.href = "${CTX}/mypage/order/main.do"; 
	}
	

</script>

<script>
	if("<spring:message code='server.status'/>" == "LIVE"){
		// GA
		(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
			new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
			j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
			'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
			})(window,document,'script','dataLayer','GTM-NNQDJVC');
		
		(function(j,en,ni,fer) {
        j['dmnaid']=fer;j['dmnatime']=new Date();j['dmnanocookie']=false;j['dmnajennifer']='JENNIFER_FRONT@TXID';
        var a=en.createElement(ni);a.src='https://d-collect.jennifersoft.com/'+fer+'/demian.js';
        a.async=true;en.getElementsByTagName(ni)[0].parentNode.appendChild(a);
    }(window,document,'script','b6695836-d184-60ef-e0fd-3c3f50508811'));		
	}
</script>
	<!-- Facebook Pixel Code -->
	<script>
	if("<spring:message code='server.status'/>" == "LIVE")
	{
		!function(f,b,e,v,n,t,s)
		{
			if(f.fbq)return;n=f.fbq=function(){n.callMethod?n.callMethod.apply(n,arguments):n.queue.push(arguments)};
			if(!f._fbq)f._fbq=n;n.push=n;n.loaded=!0;n.version='2.0';
			n.queue=[];t=b.createElement(e);t.async=!0;
			t.src=v;s=b.getElementsByTagName(e)[0];
			s.parentNode.insertBefore(t,s)
		}(window,document,'script','https://connect.facebook.net/en_US/fbevents.js');fbq('init', '285644202255951');fbq('track', 'PageView');
	}	
	</script>
	<noscript>
	if("<spring:message code='server.status'/>" == "LIVE")
	{
		<img height="1" width="1" src="https://www.facebook.com/tr?id=285644202255951&ev=PageView&noscript=1"/>
	}	
	</noscript>
	<!-- End Facebook Pixel Code -->
	
	<!-- Facebook Pixel Code -->
	<script>
	if("<spring:message code='server.status'/>" == "LIVE"){
		!function(f,b,e,v,n,t,s)
		{
			if(f.fbq)return;n=f.fbq=function(){n.callMethod?n.callMethod.apply(n,arguments):n.queue.push(arguments)};
			if(!f._fbq)f._fbq=n;n.push=n;n.loaded=!0;n.version='2.0';
			n.queue=[];t=b.createElement(e);t.async=!0;
			t.src=v;s=b.getElementsByTagName(e)[0];
			s.parentNode.insertBefore(t,s)
		}(window,document,'script', 'https://connect.facebook.net/en_US/fbevents.js');fbq('init', '2749400795297654');fbq('track', 'PageView');
	}
	</script>
	<noscript>
	if("<spring:message code='server.status'/>" == "LIVE"){
		<img height="1" width="1" src="https://www.facebook.com/tr?id=2749400795297654&ev=PageView&noscript=1"/>
	}
	</noscript>
	<!-- End Facebook Pixel Code -->
	
	<script type="text/javascript" charset="UTF-8" src="//t1.daumcdn.net/adfit/static/kp.js"></script>
	<script type="text/javascript">
	      kakaoPixel('466785529738862663').pageView();
	</script>
</head>

<body>

	<!-- 로딩 이미지 -->
	<div class="loading-container" id="loadingImg" style="display:none;">
	    <div class="loading"></div>
		<div id="loading-img"><img src="${CTX}/images/${DEVICE}/common/logo_G.png"></div>
	</div>

	<c:set var="status"><spring:message code="server.status" /></c:set>
	<c:if test="${status eq 'LIVE'}" >
		<!-- Google Tag Manager (noscript) -->
		<noscript>
			<iframe src="https://www.googletagmanager.com/ns.html?id=GTM-NNQDJVC" height="0" width="0" style="display:none;visibility:hidden"></iframe>
		</noscript>
		<!-- End Google Tag Manager (noscript) -->
	</c:if>

	<div id="wrap">
	<page:applyDecorator  name="mobile.gnb" encoding="UTF-8"/>
 	<main id="main" class="container" role="main">
		<c:if test="${menu_2depth ne 'mypage_main'}">
 		<div class="breadcrumb">
			<ul>
				<!-- ========================== 1depth ========================== -->
				
				<li>
					<a href="${CTX}/main.do"><span>Home</span></a>
				</li>
				<li>
					<a href="javascript:"><span class="cate-link" onclick="goMain();">마이페이지</span></a>
				</li>
				
				<!-- ========================== 2depth ========================== -->
				<%-- <li>
					<!-- 나의 쇼핑 내역 ====================== -->
					<c:if test="${menu_2depth eq 'mypage_shopping'}">
						<a href="${CTX}/mypage/order/myOrderList.do" class="cate-link">나의 쇼핑 내역</a>
						<div class="cate-list">
							<ul>
								<c:choose>
									<c:when test="${not empty USERINFO.memberId and USERINFO.memberFlag eq 'Y'}">
										<li><a href="${CTX}/mypage/sample/sampleList.do">활동 내역</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${CTX}/mypage/inquiry/inquiryList.do">활동 내역</a></li>
									</c:otherwise>
								</c:choose>
								<c:if test="${not empty USERINFO.memberId and USERINFO.memberFlag eq 'Y'}">
									<li><a href="${CTX}/mypage/member/billkeyList.do">회원 정보</a></li>
								</c:if>
							</ul>
						</div>
					</c:if>
					<!-- 활동 내역 ======================== -->
					<c:if test="${menu_2depth eq 'mypage_activity'}">
						<a href="${CTX}/mypage/sample/sampleList.do" class="cate-link">활동 내역</a>
						<div class="cate-list">
							<ul>
								<li><a href="${CTX}/mypage/order/myOrderList.do">나의 쇼핑 내역</a></li>
								<c:if test="${not empty USERINFO.memberId and USERINFO.memberFlag eq 'Y'}">
									<li><a href="${CTX}/mypage/member/billkeyList.do">회원 정보</a></li>
								</c:if>
							</ul>
						</div>
					</c:if>
					<!-- 회원 정보 ======================== -->
					<c:if test="${menu_2depth eq 'mypage_member'}">
						<a href="${CTX}/mypage/member/billkeyList.do" class="cate-link">회원 정보</a>
						<div class="cate-list">
							<ul>
								<li><a href="${CTX}/mypage/order/myOrderList.do">나의 쇼핑 내역</a></li>
								<li><a href="${CTX}/mypage/sample/sampleList.do">활동 내역</a></li>
							</ul>
						</div>
					</c:if>
				</li>	 --%>
				
				<!-- ========================== 3depth ========================== -->
				
				<!-- 나의 쇼핑 내역 ============================ -->
				<c:if test="${menu_no eq 'mypage_010'}">
					<li>
						<a href="javascript:" class="cate-link"><span>주문관리</span></a>
						<div class="cate-list">
							<ul>
								<c:if test="${not empty USERINFO.memberId and USERINFO.memberFlag eq 'Y'}">
									<li><a href="${CTX}/mypage/wish/wishList.do"><span>위시리스트</span></a></li>
									<li><a href="${CTX}/mypage/point/pointList.do"><span>포인트</span></a></li>
									<li><a href="${CTX}/mypage/point/couponList.do"><span>쿠폰</span></a></li>
									<%-- <li><a href="${CTX}/mypage/sample/sampleList.do"><span>이 달의 정품 신청</span></a></li> --%>
									<li><a href="${CTX}/mypage/review/noWriteReviewList.do"><span>상품후기</span></a></li>
									<li><a href="${CTX}/mypage/inquiry/inquiryList.do"><span>1:1 문의</span></a></li>
									<%-- <li><a href="${CTX}/mypage/member/billkeyList.do"><span>원클릭 결제 카드 관리</span></a></li> --%>
									<c:if test="${USERINFO.memberLoginType eq 'MEMBER'}">
										<li><a href="${CTX}/mypage/member/snsConnect.do"><span>SNS 계정 연결 관리</span></a></li>
									</c:if>
									<li><a href="${CTX}/mypage/member/memberShipping.do"><span>배송지 관리</span></a></li>
									<li><a href="${CTX}/mypage/member/memberInfo.do"><span>개인정보 수정</span></a></li>
									<li><a href="${CTX}/mypage/member/memberWithdraw.do"><span>회원탈퇴</span></a></li>
								</c:if>
								<c:if test="${empty USERINFO.memberId and USERINFO.memberFlag eq 'N'}">
									<li><a href="${CTX}/mypage/inquiry/inquiryList.do"><span>1:1 문의</span></a></li>
								</c:if>
							</ul>
						</div>
					</li>
				</c:if>
				<c:if test="${menu_no eq 'mypage_020'}">
					<li class="current">
						<a href="javascript:" class="cate-link"><span>위시리스트</span></a>
						<div class="cate-list">
							<ul>
								<li><a href="${CTX}/mypage/order/myOrderList.do"><span>주문관리</span></a></li>
								<li><a href="${CTX}/mypage/point/pointList.do"><span>포인트</span></a></li>
								<li><a href="${CTX}/mypage/point/couponList.do"><span>쿠폰</span></a></li>
								<%-- <li><a href="${CTX}/mypage/sample/sampleList.do"><span>이 달의 정품 신청</span></a></li> --%>
								<li><a href="${CTX}/mypage/review/noWriteReviewList.do"><span>상품후기</span></a></li>
								<li><a href="${CTX}/mypage/inquiry/inquiryList.do"><span>1:1 문의</span></a></li>
								<%-- <li><a href="${CTX}/mypage/member/billkeyList.do"><span>원클릭 결제 카드 관리</span></a></li> --%>
								<c:if test="${USERINFO.memberLoginType eq 'MEMBER'}">
									<li><a href="${CTX}/mypage/member/snsConnect.do"><span>SNS 계정 연결 관리</span></a></li>
								</c:if>
								<li><a href="${CTX}/mypage/member/memberShipping.do"><span>배송지 관리</span></a></li>
								<li><a href="${CTX}/mypage/member/memberInfo.do"><span>개인정보 수정</span></a></li>
								<li><a href="${CTX}/mypage/member/memberWithdraw.do"><span>회원탈퇴</span></a></li>
							</ul>
						</div>
					</li>
				</c:if>
				<c:if test="${menu_no eq 'mypage_030'}">
					<li class="current">
						<a href="javascript:" class="cate-link"><span>포인트</span></a>
						<div class="cate-list">
							<ul>
								<li><a href="${CTX}/mypage/order/myOrderList.do"><span>주문관리</span></a></li>
								<li><a href="${CTX}/mypage/wish/wishList.do"><span>위시리스트</span></a></li>
								<li><a href="${CTX}/mypage/point/couponList.do"><span>쿠폰</span></a></li>
								<%-- <li><a href="${CTX}/mypage/sample/sampleList.do"><span>이 달의 정품 신청</span></a></li> --%>
								<li><a href="${CTX}/mypage/review/noWriteReviewList.do"><span>상품후기</span></a></li>
								<li><a href="${CTX}/mypage/inquiry/inquiryList.do"><span>1:1 문의</span></a></li>
								<%-- <li><a href="${CTX}/mypage/member/billkeyList.do"><span>원클릭 결제 카드 관리</span></a></li> --%>
								<c:if test="${USERINFO.memberLoginType eq 'MEMBER'}">
									<li><a href="${CTX}/mypage/member/snsConnect.do"><span>SNS 계정 연결 관리</span></a></li>
								</c:if>
								<li><a href="${CTX}/mypage/member/memberShipping.do"><span>배송지 관리</span></a></li>
								<li><a href="${CTX}/mypage/member/memberInfo.do"><span>개인정보 수정</span></a></li>
								<li><a href="${CTX}/mypage/member/memberWithdraw.do"><span>회원탈퇴</span></a></li>
							</ul>
						</div>
					</li>
				</c:if>
				<c:if test="${menu_no eq 'mypage_040'}">
					<li class="current">
						<a href="javascript:" class="cate-link"><span>쿠폰</span></a>
						<div class="cate-list">
							<ul>
								<li><a href="${CTX}/mypage/order/myOrderList.do"><span>주문관리</span></a></li>
								<li><a href="${CTX}/mypage/wish/wishList.do"><span>위시리스트</span></a></li>
								<li><a href="${CTX}/mypage/point/pointList.do"><span>포인트</span></a></li>
								<%-- <li><a href="${CTX}/mypage/sample/sampleList.do"><span>이 달의 정품 신청</span></a></li> --%>
								<li><a href="${CTX}/mypage/review/noWriteReviewList.do"><span>상품후기</span></a></li>
								<li><a href="${CTX}/mypage/inquiry/inquiryList.do"><span>1:1 문의</span></a></li>
								<%-- <li><a href="${CTX}/mypage/member/billkeyList.do"><span>원클릭 결제 카드 관리</span></a></li> --%>
								<c:if test="${USERINFO.memberLoginType eq 'MEMBER'}">
									<li><a href="${CTX}/mypage/member/snsConnect.do"><span>SNS 계정 연결 관리</span></a></li>
								</c:if>
								<li><a href="${CTX}/mypage/member/memberShipping.do"><span>배송지 관리</span></a></li>
								<li><a href="${CTX}/mypage/member/memberInfo.do"><span>개인정보 수정</span></a></li>
								<li><a href="${CTX}/mypage/member/memberWithdraw.do"><span>회원탈퇴</span></a></li>
							</ul>
						</div>
					</li>
				</c:if>
				<!-- 활동 내역 ============================ -->
				<c:if test="${menu_no eq 'mypage_050'}">
					<li class="current">
						<a href="javascript:" class="cate-link"><span>이 달의 정품 신청</span></a>
						<div class="cate-list">
							<ul>
								<li><a href="${CTX}/mypage/order/myOrderList.do"><span>주문관리</span></a></li>
								<li><a href="${CTX}/mypage/wish/wishList.do"><span>위시리스트</span></a></li>
								<li><a href="${CTX}/mypage/point/pointList.do"><span>포인트</span></a></li>
								<li><a href="${CTX}/mypage/point/couponList.do"><span>쿠폰</span></a></li>
								<li><a href="${CTX}/mypage/review/noWriteReviewList.do"><span>상품후기</span></a></li>
								<li><a href="${CTX}/mypage/inquiry/inquiryList.do"><span>1:1 문의</span></a></li>
								<%-- <li><a href="${CTX}/mypage/member/billkeyList.do"><span>원클릭 결제 카드 관리</span></a></li> --%>
								<c:if test="${USERINFO.memberLoginType eq 'MEMBER'}">
									<li><a href="${CTX}/mypage/member/snsConnect.do"><span>SNS 계정 연결 관리</span></a></li>
								</c:if>
								<li><a href="${CTX}/mypage/member/memberShipping.do"><span>배송지 관리</span></a></li>
								<li><a href="${CTX}/mypage/member/memberInfo.do"><span>개인정보 수정</span></a></li>
								<li><a href="${CTX}/mypage/member/memberWithdraw.do"><span>회원탈퇴</span></a></li>
							</ul>
						</div>
					</li>
				</c:if>
				<c:if test="${menu_no eq 'mypage_060'}">
					<li class="current">
						<a href="javascript:" class="cate-link"><span>상품후기</span></a>
						<div class="cate-list">
							<ul>
								<li><a href="${CTX}/mypage/order/myOrderList.do"><span>주문관리</span></a></li>
								<li><a href="${CTX}/mypage/wish/wishList.do"><span>위시리스트</span></a></li>
								<li><a href="${CTX}/mypage/point/pointList.do"><span>포인트</span></a></li>
								<li><a href="${CTX}/mypage/point/couponList.do"><span>쿠폰</span></a></li>
								<%-- <li><a href="${CTX}/mypage/sample/sampleList.do"><span>이 달의 정품 신청</span></a></li> --%>
								<li><a href="${CTX}/mypage/inquiry/inquiryList.do"><span>1:1 문의</span></a></li>
								<%-- <li><a href="${CTX}/mypage/member/billkeyList.do"><span>원클릭 결제 카드 관리</span></a></li> --%>
								<c:if test="${USERINFO.memberLoginType eq 'MEMBER'}">
									<li><a href="${CTX}/mypage/member/snsConnect.do"><span>SNS 계정 연결 관리</span></a></li>
								</c:if>
								<li><a href="${CTX}/mypage/member/memberShipping.do"><span>배송지 관리</span></a></li>
								<li><a href="${CTX}/mypage/member/memberInfo.do"><span>개인정보 수정</span></a></li>
								<li><a href="${CTX}/mypage/member/memberWithdraw.do"><span>회원탈퇴</span></a></li>
							</ul>
						</div>
					</li>
				</c:if>
				<c:if test="${menu_no eq 'mypage_070'}">
					<li class="current">
						<a href="javascript:" class="cate-link"><span>1:1 문의</span></a>
						<div class="cate-list">
							<ul>
								<c:if test="${not empty USERINFO.memberId and USERINFO.memberFlag eq 'Y'}">
									<li><a href="${CTX}/mypage/order/myOrderList.do"><span>주문관리</span></a></li>
									<li><a href="${CTX}/mypage/wish/wishList.do"><span>위시리스트</span></a></li>
									<li><a href="${CTX}/mypage/point/pointList.do"><span>포인트</span></a></li>
									<li><a href="${CTX}/mypage/point/couponList.do"><span>쿠폰</span></a></li>
									<%-- <li><a href="${CTX}/mypage/sample/sampleList.do"><span>이 달의 정품 신청</span></a></li> --%>
									<li><a href="${CTX}/mypage/review/noWriteReviewList.do"><span>상품후기</span></a></li>
									<%-- <li><a href="${CTX}/mypage/member/billkeyList.do"><span>원클릭 결제 카드 관리</span></a></li> --%>
									<c:if test="${USERINFO.memberLoginType eq 'MEMBER'}">
										<li><a href="${CTX}/mypage/member/snsConnect.do"><span>SNS 계정 연결 관리</span></a></li>
									</c:if>
									<li><a href="${CTX}/mypage/member/memberShipping.do"><span>배송지 관리</span></a></li>
									<li><a href="${CTX}/mypage/member/memberInfo.do"><span>개인정보 수정</span></a></li>
									<li><a href="${CTX}/mypage/member/memberWithdraw.do"><span>회원탈퇴</span></a></li>
								</c:if>
								<c:if test="${empty USERINFO.memberId and USERINFO.memberFlag eq 'N'}">
									<li><a href="${CTX}/mypage/order/myOrderList.do"><span>주문관리</span></a></li>
								</c:if>
							</ul>
						</div>
					</li>
				</c:if>
				<!-- 회원 정보 ============================ -->
				<c:if test="${menu_no eq 'mypage_080'}">
					<li class="current">
						<a href="javascript:" class="cate-link"><span>원클릭 결제 카드 관리</span></a>
						<div class="cate-list">
							<ul>
								<li><a href="${CTX}/mypage/order/myOrderList.do"><span>주문관리</span></a></li>
								<li><a href="${CTX}/mypage/wish/wishList.do"><span>위시리스트</span></a></li>
								<li><a href="${CTX}/mypage/point/pointList.do"><span>포인트</span></a></li>
								<li><a href="${CTX}/mypage/point/couponList.do"><span>쿠폰</span></a></li>
								<%-- <li><a href="${CTX}/mypage/sample/sampleList.do"><span>이 달의 정품 신청</span></a></li> --%>
								<li><a href="${CTX}/mypage/review/noWriteReviewList.do"><span>상품후기</span></a></li>
								<li><a href="${CTX}/mypage/inquiry/inquiryList.do"><span>1:1 문의</span></a></li>
								<c:if test="${USERINFO.memberLoginType eq 'MEMBER'}">
									<li><a href="${CTX}/mypage/member/snsConnect.do"><span>SNS 계정 연결 관리</span></a></li>
								</c:if>
								<li><a href="${CTX}/mypage/member/memberShipping.do"><span>배송지 관리</span></a></li>
								<li><a href="${CTX}/mypage/member/memberInfo.do"><span>개인정보 수정</span></a></li>
								<li><a href="${CTX}/mypage/member/memberWithdraw.do"><span>회원탈퇴</span></a></li>
							</ul>
						</div>
					</li>
				</c:if>
				<c:if test="${menu_no eq 'mypage_090'}">
					<li class="current">
						<a href="javascript:" class="cate-link"><span>SNS 계정 연결 관리</span></a>
						<div class="cate-list">
							<ul>
								<li><a href="${CTX}/mypage/order/myOrderList.do"><span>주문관리</span></a></li>
								<li><a href="${CTX}/mypage/wish/wishList.do"><span>위시리스트</span></a></li>
								<li><a href="${CTX}/mypage/point/pointList.do"><span>포인트</span></a></li>
								<li><a href="${CTX}/mypage/point/couponList.do"><span>쿠폰</span></a></li>
								<%-- <li><a href="${CTX}/mypage/sample/sampleList.do"><span>이 달의 정품 신청</span></a></li> --%>
								<li><a href="${CTX}/mypage/review/noWriteReviewList.do"><span>상품후기</span></a></li>
								<li><a href="${CTX}/mypage/inquiry/inquiryList.do"><span>1:1 문의</span></a></li>
								<%-- <li><a href="${CTX}/mypage/member/billkeyList.do"><span>원클릭 결제 카드 관리</span></a></li> --%>
								<li><a href="${CTX}/mypage/member/memberShipping.do"><span>배송지 관리</span></a></li>
								<li><a href="${CTX}/mypage/member/memberInfo.do"><span>개인정보 수정</span></a></li>
								<li><a href="${CTX}/mypage/member/memberWithdraw.do"><span>회원탈퇴</span></a></li>
							</ul>
						</div>
					</li>
				</c:if>
				<c:if test="${menu_no eq 'mypage_100'}">
					<li class="current">
						<a href="javascript:" class="cate-link"><span>배송지 관리</span></a>
						<div class="cate-list">
							<ul>
								<li><a href="${CTX}/mypage/order/myOrderList.do"><span>주문관리</span></a></li>
								<li><a href="${CTX}/mypage/wish/wishList.do"><span>위시리스트</span></a></li>
								<li><a href="${CTX}/mypage/point/pointList.do"><span>포인트</span></a></li>
								<li><a href="${CTX}/mypage/point/couponList.do"><span>쿠폰</span></a></li>
								<%-- <li><a href="${CTX}/mypage/sample/sampleList.do"><span>이 달의 정품 신청</span></a></li> --%>
								<li><a href="${CTX}/mypage/review/noWriteReviewList.do"><span>상품후기</span></a></li>
								<li><a href="${CTX}/mypage/inquiry/inquiryList.do"><span>1:1 문의</span></a></li>
								<%-- <li><a href="${CTX}/mypage/member/billkeyList.do"><span>원클릭 결제 카드 관리</span></a></li> --%>
								<c:if test="${USERINFO.memberLoginType eq 'MEMBER'}">
									<li><a href="${CTX}/mypage/member/snsConnect.do"><span>SNS 계정 연결 관리</span></a></li>
								</c:if>
								<li><a href="${CTX}/mypage/member/memberInfo.do"><span>개인정보 수정</span></a></li>
								<li><a href="${CTX}/mypage/member/memberWithdraw.do"><span>회원탈퇴</span></a></li>
							</ul>
						</div>
					</li>
				</c:if>
				<c:if test="${menu_no eq 'mypage_110'}">
					<li class="current">
						<a href="javascript:" class="cate-link"><span>개인정보 수정</span></a>
						<div class="cate-list">
							<ul>
								<li><a href="${CTX}/mypage/order/myOrderList.do"><span>주문관리</span></a></li>
								<li><a href="${CTX}/mypage/wish/wishList.do"><span>위시리스트</span></a></li>
								<li><a href="${CTX}/mypage/point/pointList.do"><span>포인트</span></a></li>
								<li><a href="${CTX}/mypage/point/couponList.do"><span>쿠폰</span></a></li>
								<%-- <li><a href="${CTX}/mypage/sample/sampleList.do"><span>이 달의 정품 신청</span></a></li> --%>
								<li><a href="${CTX}/mypage/review/noWriteReviewList.do"><span>상품후기</span></a></li>
								<li><a href="${CTX}/mypage/inquiry/inquiryList.do"><span>1:1 문의</span></a></li>
								<%-- <li><a href="${CTX}/mypage/member/billkeyList.do"><span>원클릭 결제 카드 관리</span></a></li> --%>
								<c:if test="${USERINFO.memberLoginType eq 'MEMBER'}">
									<li><a href="${CTX}/mypage/member/snsConnect.do"><span>SNS 계정 연결 관리</span></a></li>
								</c:if>
								<li><a href="${CTX}/mypage/member/memberShipping.do"><span>배송지 관리</span></a></li>
								<li><a href="${CTX}/mypage/member/memberWithdraw.do"><span>회원탈퇴</span></a></li>
							</ul>
						</div>
					</li>
				</c:if>
				<c:if test="${menu_no eq 'mypage_120'}">
					<li class="current">
						<a href="javascript:" class="cate-link"><span>회원탈퇴</span></a>
						<div class="cate-list">
							<ul>
								<li><a href="${CTX}/mypage/order/myOrderList.do"><span>주문관리</span></a></li>
								<li><a href="${CTX}/mypage/wish/wishList.do"><span>위시리스트</span></a></li>
								<li><a href="${CTX}/mypage/point/pointList.do"><span>포인트</span></a></li>
								<li><a href="${CTX}/mypage/point/couponList.do"><span>쿠폰</span></a></li>
								<%-- <li><a href="${CTX}/mypage/sample/sampleList.do"><span>이 달의 정품 신청</span></a></li> --%>
								<li><a href="${CTX}/mypage/review/noWriteReviewList.do"><span>상품후기</span></a></li>
								<li><a href="${CTX}/mypage/inquiry/inquiryList.do"><span>1:1 문의</span></a></li>
								<%-- <li><a href="${CTX}/mypage/member/billkeyList.do"><span>원클릭 결제 카드 관리</span></a></li> --%>
								<c:if test="${USERINFO.memberLoginType eq 'MEMBER'}">
									<li><a href="${CTX}/mypage/member/snsConnect.do"><span>SNS 계정 연결 관리</span></a></li>
								</c:if>
								<li><a href="${CTX}/mypage/member/memberShipping.do"><span>배송지 관리</span></a></li>
								<li><a href="${CTX}/mypage/member/memberInfo.do"><span>개인정보 수정</span></a></li>
							</ul>
						</div>
					</li>
				</c:if>
				
				<c:if test="${menu_3depth eq 'mypage_order'}">
					<li class="current">
						<a href="javascript:" class="cate-link"><span>주문 내역</span></a>
						<div class="cate-list">
							<ul>
								<li><a href="${CTX}/mypage/order/myCancelList.do"><span>취소내역</span></a></li>
								<li><a href="${CTX}/mypage/order/myReturnList.do"><span>반품 내역</span></a></li>
								<li><a href="${CTX}/mypage/order/myExchangeList.do"><span>교환 내역</span></a></li>
								<li><a href="${CTX}/mypage/order/issueDocumentList.do"><span>증빙 서류 발급</span></a></li>
							</ul>
						</div>
					</li>
				</c:if>
				
				<c:if test="${menu_3depth eq 'mypage_cancel'}">
					<li class="current">
						<a href="javascript:" class="cate-link"><span>취소 내역</span></a>
						<div class="cate-list">
							<ul>
								<li><a href="${CTX}/mypage/order/myOrderList.do"><span>주문 내역</span></a></li>
								<li><a href="${CTX}/mypage/order/myReturnList.do"><span>반품 내역</span></a></li>
								<li><a href="${CTX}/mypage/order/myExchangeList.do"><span>교환 내역</span></a></li>
								<li><a href="${CTX}/mypage/order/issueDocumentList.do"><span>증빙 서류 발급</span></a></li>
							</ul>
						</div>
					</li>
				</c:if>
				
				<c:if test="${menu_3depth eq 'mypage_return'}">
					<li class="current">
						<a href="javascript:" class="cate-link"><span>반품 내역</span></a>
						<div class="cate-list">
							<ul>
								<li><a href="${CTX}/mypage/order/myOrderList.do"><span>주문 내역</span></a></li>
								<li><a href="${CTX}/mypage/order/myCancelList.do"><span>취소내역</span></a></li>
								<li><a href="${CTX}/mypage/order/myExchangeList.do"><span>교환 내역</span></a></li>
								<li><a href="${CTX}/mypage/order/issueDocumentList.do"><span>증빙 서류 발급</span></a></li>
							</ul>
						</div>
					</li>
				</c:if>
				
				<c:if test="${menu_3depth eq 'mypage_exchange'}">
					<li class="current">
						<a href="javascript:" class="cate-link"><span>교환 내역</span></a>
						<div class="cate-list">
							<ul>
								<li><a href="${CTX}/mypage/order/myOrderList.do"><span>주문 내역</span></a></li>
								<li><a href="${CTX}/mypage/order/myCancelList.do"><span>취소내역</span></a></li>
								<li><a href="${CTX}/mypage/order/myReturnList.do"><span>반품 내역</span></a></li>
								<li><a href="${CTX}/mypage/order/issueDocumentList.do"><span>증빙 서류 발급</span></a></li>
							</ul>
						</div>
					</li>
				</c:if>
				
				<c:if test="${menu_3depth eq 'mypage_issueDocument'}">
					<li class="current">
						<a href="javascript:" class="cate-link"><span>증빙 서류 발급</span></a>
						<div class="cate-list">
							<ul>
								<li><a href="${CTX}/mypage/order/myOrderList.do"><span>주문 내역</span></a></li>
								<li><a href="${CTX}/mypage/order/myCancelList.do"><span>취소내역</span></a></li>
								<li><a href="${CTX}/mypage/order/myReturnList.do"><span>반품 내역</span></a></li>
								<li><a href="${CTX}/mypage/order/myExchangeList.do"><span>교환 내역</span></a></li>
							</ul>
						</div>
					</li>
				</c:if>
			</ul>
		</div>
		</c:if>
		<decorator:body/>
 	</main>
	<page:applyDecorator  name="mobile.footer" encoding="UTF-8"/>
	</div>
	


<script type="text/javascript">
	
	<%-- 첨부파일 체크 콜백 함수 --%>
	function callbackFileCheck(val){
		if(val =="1"){alert("첨부파일은 최대 10Mb까지 업로드 가능합니다."); return;}
		if(val =="2"){alert("이미지 파일만 업로드 가능합니다."); return;}
		if(val =="3"){alert("문서 파일만 업로드 가능합니다."); return;}
	}

	<%-- 가상계좌 주문 취소 버튼클릭 --%>
	function cancelOrder(btn,orderCd){
		$(btn).attr("data-toggle","");
		$(btn).attr("data-target","");
	
		$("#cancelOrderCd").html(orderCd);
		$("#cancelForm #orderCd").val(orderCd);
		$(btn).attr("data-toggle","popup");
		$(btn).attr("data-target","#popOrderCancel");
		$(btn).trigger("toggle");
	}

	<%-- 교환 신청 버튼클릭--%>
	function exchangeOrder(btn, orderCd){		
		$(btn).attr("data-toggle","");
		$(btn).attr("data-target","");
		if($("[data-radio-check='"+orderCd+"']:checked").length == 0){
			alert("교환할 상품을 선택하세요.");
			return false;
		}
		
		$("#exchangeOrderCd").html(orderCd);
		var html = "";
		$("[data-radio-check='"+orderCd+"']:checked").each(function(){
			html += "<li><span class='bu'>-</span>" + $(this).data("goodsnm"); + "</li>";
		});
		$("#exchangeGoods").html(html);
		$("#exchangeForm #orderCd").val(orderCd);
		$("#exchangeForm #reasonCd").val("");
		$("#exchangeForm #reason").val("");
		$("#exchangeForm #returnUrl").val("1");		<%-- 교환, 반품 신청 완료 후 돌아갈 URL (0 : 마이페이지 메인, 1 : 주문관리 리스트, 2 : 주문상세) --%>
		$(btn).attr("data-toggle","popup");
		$(btn).attr("data-target","#pop_exchange");
		$(btn).trigger("toggle");
	}
	
	<%-- 반품 신청 버튼클릭 --%>
	function returnOrder(btn,orderCd){
		$(btn).attr("data-toggle","");
		$(btn).attr("data-target","");
	
		if($("[data-radio-check='"+orderCd+"']:checked").length == 0){
			alert("반품할 상품을 선택하세요.");
			return false;
		}
		
		$("#returnOrderCd").html(orderCd);
		var html = "";
		$("[data-radio-check='"+orderCd+"']:checked").each(function(){
			html += "<li><span class='bu'>-</span>" + $(this).data("goodsnm"); + "</li>";
		});
		$("#returnGoods").html(html);
		$("#returnForm #orderCd").val(orderCd);
		$("#returnForm #reasonCd").val("");
		$("#returnForm #reason").val("");
		$("#returnForm #returnUrl").val("1");		<%-- 교환, 반품 신청 완료 후 돌아갈 URL (0 : 마이페이지 메인, 1 : 주문관리 리스트, 2 : 주문상세) --%>
		$(btn).attr("data-toggle","popup");
		$(btn).attr("data-target","#pop_return");
		$(btn).trigger("toggle");
	}

	<%-- 가상계좌 주문 취소 --%>
	function requestCancel(){

		if($("#cancelForm #refundBankCode").val() == ""){
			alert("환불 계좌 은행을 선택하세요.");
			$("#cancelForm #refundBankCode").focus();
			return false;
		}
		
		if($.trim($("#cancelForm #refundDepositor").val()) == ""){
			alert("환불 계좌 예금주를 입력해 주세요.");
			$("#cancelForm #refundDepositor").focus();
			return false;
		}
		
		if($.trim($("#cancelForm #refundAccount").val()) == ""){
			alert("환불 계좌번호를 입력해 주세요.");
			$("#cancelForm #refundAccount").focus();
			return false;
		}

		$.ajax({			
			url: getContextPath()+"/ajax/order/OrderAllCancel.do",
			data: {
				"orderCd" 				: $("#cancelForm #orderCd").val(),
				"refundBankCode" 	: $("#cancelForm #refundBankCode").val(),
				"refundAccount"		: $("#cancelForm #refundAccount").val(),
				"refundDepositor"	: $("#cancelForm #refundDepositor").val()
			},
		 	type: "post",
		 	async: false,
		 	cache: false,
		 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data){
				if(data.result == true){
					$('[data-remodal-id=pop_reload_alert] div.pop-alert p').html("주문 취소 하였습니다.");
					$('[data-remodal-id=pop_reload_alert]').remodal().open();
				}else{
					if(data.msg != "" && data.msg !=null){
						alert(data.msg);
					}
				}
			 }
		});
	}

	<%-- 교환 신청 --%>
	function requestExchange(){
		if($("#exchangeForm #reasonCd").val() == ""){
			alert("구분 항목을 선택하세요.");
			$("#exchangeForm #reasonCd").focus();
			return false;
		}
		
		if($.trim($("#exchangeForm #reason").val()) == ""){
			alert("내용을 입력해 주세요.");
			$("#exchangeForm #reason").focus();
			return false;
		}
		
		<%-- 주문 상세 일련번호 ,로 묶어서 --%>
		var idxStr = "";
		$("input:checkbox[name='chkGoods']:checked").each(function(){
			if(idxStr != ""){
				idxStr += ",";
			}
			idxStr += $(this).val();
		});
		
		$("#exchangeForm #orderDetailIdxesStr").val(idxStr);
		
		var frm = document.exchangeForm;
		frm.action = "${CTX}/mypage/order/requestExchangeOrder.do";
		frm.submit();
	}
	
	<%-- 반품 신청 --%>
	function requestReturn(){
		if($("#returnForm #reasonCd").val() == ""){
			alert("구분 항목을 선택하세요.");
			$("#returnForm #reasonCd").focus();
			return false;
		}
		
		if($.trim($("#returnForm #reason").val()) == ""){
			alert("내용을 입력해 주세요.");
			$("#returnForm #reason").focus();
			return false;
		}
		
		if($("#returnForm #refundBankCode").val() == ""){
			alert("환불 계좌 은행을 선택하세요.");
			$("#returnForm #refundBankCode").focus();
			return false;
		}
		
		if($.trim($("#returnForm #refundDepositor").val()) == ""){
			alert("환불 계좌 예금주를 입력해 주세요.");
			$("#returnForm #refundDepositor").focus();
			return false;
		}
		
		if($.trim($("#returnForm #refundAccount").val()) == ""){
			alert("환불 계좌번호를 입력해 주세요.");
			$("#returnForm #refundAccount").focus();
			return false;
		}
		
		<%-- 주문 상세 일련번호 ,로 묶어서 --%>
		var idxStr = "";
		$("input:checkbox[name='chkGoods']:checked").each(function(){
			if(idxStr != ""){
				idxStr += ",";
			}
			idxStr += $(this).val();
		});
		
		$("#returnForm #orderDetailIdxesStr").val(idxStr);
		
		var frm = document.returnForm;
		frm.action = "${CTX}/mypage/order/requestReturnOrder.do";
		frm.submit();
		
	}

	<%-- 바로구매 --%>
	function buyNow(goodsIdx, goodsCnt){
		var arrOrder = new Array();
		var goodsObj = new Object();
		goodsObj.goodsIdx = Number(goodsIdx);
		goodsObj.goodsCnt = Number(goodsCnt);
		arrOrder.push(goodsObj);
		orderNow(arrOrder);
	}

	<%-- 배송추적 --%>
	function deliveryTracking(trackingUrl, invoiceNo){
		if($.trim(trackingUrl) == ""){
			alert("배송추적 URL이 없습니다. 관리자에게 문의하세요.");
			return false;
		}
		
		if($.trim(invoiceNo) == ""){
			alert("송장번호를 확인하세요!");
			return false;
		}
		
		var url = trackingUrl.replace("__INVOICENO__", invoiceNo);
		
		var openNewWindow = window.open("about:blank");
		
		openNewWindow.location.href = url;
	}
	$(function(){
		$('.filebox').filebox();
	});
</script>
	
	

<!-- 확인 선택시 페이지 reload -->
<div class="remodal popup" data-remodal-id="pop_reload_alert">
	<!-- pop-top -->
	<div class="pop-top">
		<h2>알림</h2>
	</div>
	<!-- //pop-top -->

	<!-- pop-mid -->
	<div class="pop-mid">
		<div class="pop-alert">
			<p></p>
		</div>
		
		<div class="btn-box confirm">
			<button type="button" class="btn outline-green" onclick="location.reload();"><span class="txt">확인</span></button>
		</div>
		
	</div>
	<!-- //pop-mid -->

	<!-- //pop-btn -->
	<a href="javascript:void(0);" class="btn_close" data-remodal-action="close"><span class="hide">닫기</span></a>
</div>
<!-- 확인 선택시 페이지 reload -->
	

<!-- Confirm 팝업 -->
<div class="remodal popup" data-remodal-id="popConfirm" data-remodal-options="hashTracking: false, closeOnOutsideClick: false">
	<!-- pop-top -->
	<div class="pop-top">
		<h2>확인</h2>
	</div>
	<!-- //pop-top -->

	<!-- pop-mid -->
	<div class="pop-mid">
		<div class="pop-alert">
			<p>사용하실 카드의 종류를 선택해 주세요.</p>
		</div>
		
		<div class="btn-box confirm">
			<button type="button" class="btn outline-green" data-remodal-action="confirm"><span class="txt">확인</span></button>
			<button type="button" class="btn" data-remodal-action="close"><span class="txt">취소</span></button>
		</div>
	</div>
	<!-- //pop-mid -->
	<!-- //pop-btn -->
	<a href="javascript:void(0);" class="btn_close" data-remodal-action="close"><span class="hide">닫기</span></a>

</div>
<!-- //Confirm 팝업 -->
	
<!-- 교환 신청 -->
<div id="pop_exchange" class="popup type-page popup-oneclick-manage">
	<form name="exchangeForm" id="exchangeForm" method="post" onsubmit="return false;" enctype="multipart/form-data">
	<input type="hidden" name="orderCd" id="orderCd" value=""/>
	<input type="hidden" name="orderDetailIdxesStr" id="orderDetailIdxesStr" value=""/>
	<input type="hidden" name="returnUrl" id="returnUrl" value=""/>
	
	<!-- [D] content start here! -->
		<div class="content comm-order comm-mypage mypage-return">
				<h1 class="hide">GATSBY</h1>
		<div class="pop-top">
			<h2>교환 신청</h2>
		</div>
		<!-- //pop-top -->
		<!-- pop-mid -->
		<div class="pop-mid">
					<div class="return-order">
						주문번호 <span class="em" id="exchangeOrderCd"></span>
					</div>
					
					<div class="return-products">
						<h3 class="tit">교환 상품</h3>
						<ul class="bu-list" id="exchangeGoods">
<%--
							<li><span class="bu">-</span> 아이스 데오도란트 바디 페이퍼 아이스 시트러스 10매</li>
							<li><span class="bu">-</span> 무빙러버 스파이키 엣지 80g</li>
							<li><span class="bu">-</span> 무빙러버 스파이키 엣지 15g_핸디</li>
							<li><span class="bu">-</span> 오일클리어 필름 70매</li>
							<li><span class="bu">-</span> 무빙러버 스파이키 엣지 15g_핸디</li>
							<li><span class="bu">-</span> 오일클리어 필름 70매</li>
 --%>
						</ul>
					</div>
					
					<div class="order-form first">
						<div class="form-group">
							<div class="form-body">
								<div class="row">
									<div class="col col-12">
										<div class="form-control">
											<div class="opt_select">
												<select name="reasonCd" id="reasonCd"  required>
													<option value="">선택하세요.</option>
													<c:forEach var="list" items="${exchangeReasonList}">
														<option value="${list.commonCd}">${list.cdNm}</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col col-12">
										<div class="form-control">
											<textarea name="reason" id="reason" maxlength="2000" rows="10" class="text" placeholder="내용을 입력해주세요." required></textarea>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col col-8">
										<div class="form-control">
											<input type="text" class="input" data-id="file" placeholder="파일을 선택해 주세요." readonly="readonly" />
										</div>
									</div>
									<div class="col col-4">
										<div class="filebox">
											<span class="btn ico-chev btn-fake"><span class="txt">파일찾기</span></span>
											<input type="file" class="file" data-target="file"  name="imgFile" id="imgFile" onchange="fileCheck(this,'image');" accept="image/gif, image/jpeg, image/png" />
										</div>
									</div>
								</div> 
								<ul class="bu-list" style="margin-top:5px">
									<li><span class="bu">*</span> 첨부파일은 용량 10MB이하로 등록, 확장자 JPG,PNG,GIF</li>
								</ul>
							</div>
						</div>
					</div>

					<div class="btn-box confirm">
						<button type="button" class="btn" data-dismiss="popup"><span class="txt">취소</span></button>
						<button type="button" class="btn outline-green" onclick="javascript:requestExchange();"><span class="txt">신청</span></button>
					</div>
					
					<div class="guidebox">
						<div class="return-guide">
							<h3 class="tit">교환 안내</h3>
							<div class="return-process">
								<img src="${CTX}/images/${DEVICE}/contents/img_guide_return.png" alt="" />
								<ol class="hide">
									<li>교환 신청</li>
									<li>교환 상품 회수 중 </li>
									<li>교환 상품 회수 및 확인 완료 </li>
									<li>교환 완료 (교환 상품 발송 완료)</li>
								</ol>
							</div>
							<ul class="bu-list">
								<li><span class="bu">-</span> 교환 신청은 상품 수령 후 14일 이내에 신청하실 수 있습니다.</li>
								<li><span class="bu">-</span> 교환 시 면역공방 지정 택배사 기사님이 방문하십니다.<br/> 면역공방 지정 택배사를 이용해 주세요.</li>
								<li><span class="bu">-</span> 상품 불량 및 오 배송 등의 이유로 교환하실 경우, 교환 배송비는 무료입니다.</li>
								<li><span class="bu">-</span> 단순 변심 및 고객님의 사정으로 교환하실 경우, 교환 배송비는 고객님 부담입니다.</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<!-- [D] //content start end! -->
			<a href="javascript:void(0);" class="btn_close" data-dismiss="popup"><span class="hide">닫기</span></a>
	</form>
</div>
<!-- //교환 신청 -->



<!-- 반품 신청 -->
<div id="pop_return" class="popup type-page popup-oneclick-manage">
	<form name="returnForm" id="returnForm" method="post" onsubmit="return false;" enctype="multipart/form-data">
	<input type="hidden" name="orderCd" id="orderCd" value=""/>
	<input type="hidden" name="orderDetailIdxesStr" id="orderDetailIdxesStr" value=""/>
	<input type="hidden" name="returnUrl" id="returnUrl" value=""/>
	
	<!-- [D] content start here! -->
			<div class="content comm-order comm-mypage mypage-return">
				<h1 class="hide">GATSBY</h1>
		<div class="pop-top">
			<h2>반품 신청</h2>
		</div>
		<!-- //pop-top -->
		<!-- pop-mid -->
		<div class="pop-mid">
					<div class="return-order">
						주문번호 <span class="em" id="returnOrderCd"></span>
					</div>
					
					<div class="return-products">
						<h3 class="tit">반품 상품</h3>
						<ul class="bu-list" id="returnGoods">
<%--
							<li><span class="bu">-</span> 아이스 데오도란트 바디 페이퍼 아이스 시트러스 10매</li>
							<li><span class="bu">-</span> 무빙러버 스파이키 엣지 80g</li>
							<li><span class="bu">-</span> 무빙러버 스파이키 엣지 15g_핸디</li>
							<li><span class="bu">-</span> 오일클리어 필름 70매</li>
							<li><span class="bu">-</span> 무빙러버 스파이키 엣지 15g_핸디</li>
							<li><span class="bu">-</span> 오일클리어 필름 70매</li>
 --%>
						</ul>
					</div>

					<div class="order-form first">
						<div class="form-group">
							<div class="form-body">
								<div class="row">
									<div class="col col-12">
										<div class="form-control">
											<div class="opt_select">
												<select name="reasonCd" id="reasonCd" required>
													<option value="">선택하세요.</option>
													<c:forEach var="list" items="${returnReasonList}">
														<option value="${list.commonCd}">${list.cdNm}</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col col-12">
										<div class="form-control">
											<textarea name="reason" id="reason" maxlength="2000"  rows="10" class="text" placeholder="내용을 입력해주세요." required></textarea>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="form-label">
										<label><span class="required">*</span> 환불 계좌</label>
									</div>
									<div class="col col-6">
										<div class="form-control">
											<div class="opt_select">
												<select name="refundBankCode" id="refundBankCode" required>
													<option value="">은행 선택</option>
													<c:forEach var="list" items="${bankCodeList}">
														<option value="${list.commonCd}" <c:if test="${refundAccount.bankCode eq list.commonCd}">selected</c:if>>${list.cdNm}</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
									<div class="col col-6">
										<div class="form-control">
											<input type="text" class="input" placeholder="예금주" name="refundDepositor" id="refundDepositor" value="<c:out value="${refundAccount.depositor}"/>" maxlength="50"/>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col col-12">
										<div class="form-control">
											<input type="text" class="input" placeholder="계좌번호 (‘-’를 제외한 숫자만 입력)" name="refundAccount" id="refundAccount" value="<c:out value="${refundAccount.account}"/>" maxlength="50" />
										</div>
									</div>
								</div>
								<c:if test="${not empty USERINFO.memberId }">	<%-- 계좌 저장/관리는 회원만 --%>
								<div class="row">
									<div class="col col-7">
										<div class="form-control">
											<div class="optgroup">
												<span class="checkbox">
													<input type="checkbox"  class="check" name="saveRefundAccount" id="saveRefundAccount" value="Y" />
													<label for="saveRefundAccount" class="lbl">환불 계좌 저장</label>
												</span>
											</div>
										</div>
									</div>
									<div class="col col-5">
										<a href="${CTX}/mypage/member/memberAccount.do" class="btn full" style="margin: 5px 0;"><span class="txt">환불계좌등록관리</span></a>
									</div>
								</div>
								<ul class="bu-list" style="margin-top: 5px;">
									<li><span class="bu">*</span> 환불계좌는 마이페이지>개인정보수정에서 등록할 수 있습니다.</li>
								</ul>
								</c:if>
								<div class="row">
									<div class="col col-8">
										<div class="form-control">
											<input type="text" class="input" data-id="file" placeholder="파일을 선택해 주세요." readonly="readonly" />
										</div>
									</div>
									<div class="col col-4">
										<div class="filebox">
											<span class="btn ico-chev btn-fake"><span class="txt">파일찾기</span></span>
											<input type="file" class="file" data-target="file"  name="imgFile" id="imgFile" onchange="fileCheck(this,'image');" accept="image/gif, image/jpeg, image/png" />
										</div>
									</div>
								</div>
								<ul class="bu-list" style="margin-top:5px">
									<li><span class="bu">*</span> 첨부파일은 용량 10MB이하로 등록, 확장자 JPG,PNG,GIF</li>
								</ul> 
							</div>
						</div>
					</div>

					<div class="btn-box confirm">
						<button type="button" class="btn" data-dismiss="popup"><span class="txt">취소</span></button>
						<button type="button" class="btn outline-green" onclick="javascript:requestReturn();"><span class="txt">신청</span></button>
					</div>
					
					<div class="guidebox">
						<div class="return-guide">
							<h3 class="tit">반품 안내</h3>
							<div class="return-process">
								<img src="${CTX}/images/${DEVICE}/contents/img_guide_return2.png" alt="" />
								<ol class="hide">
									<li>반품 신청</li>
									<li>반품 상품 회수 중 </li>
									<li>반품 상품 회수 및 확인 완료 </li>
									<li>반품 완료 (환불 처리 완료)</li>
								</ol>
							</div>
							<ul class="bu-list">
								<li><span class="bu">-</span> 반품 신청은 상품 수령 후 14일 이내에 신청하실 수 있습니다.</li>
								<li><span class="bu">-</span> 반품 시 면역공방 지정 택배사 기사님이 방문하십니다.<br/>면역공방 지정 택배사를 이용해 주세요.</li>
								<li><span class="bu">-</span> 상품 불량 및 오 배송 등의 이유로 반품하실 경우, 반품 배송비는 무료입니다.</li>
								<li><span class="bu">-</span> 단순 변심 및 고객님의 사정으로 반품하실 경우, 반품 배송비는 고객님 부담입니다. </li>
								<li><span class="bu">-</span> 쿠폰을 사용한 주문을 취소/반품하여 쿠폰사용이 취소된 경우, 유효기간 내에서 재발급 됩니다. (단, 취소 및 반품 시점에 쿠폰 사용 잔여 기간이 3일 이내인 경우 1주 연장)</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<!-- [D] //content start end! -->
			<a href="javascript:void(0);" class="btn_close" data-dismiss="popup"><span class="hide">닫기</span></a>
	</form>
</div>
<!-- //반품 신청 -->

<!-- 가상계좌 주문취소 -->
<div id="popOrderCancel" class="popup type-page mypage-return">
	<form name="cancelForm" id="cancelForm" method="post" onsubmit="return false;">
	<input type="hidden" name="orderCd" id="orderCd" value=""/>

	<h1 class="hide">GATSBY</h1>
	<!-- pop-top -->
	<div class="pop-top">
		<h2>주문 취소</h2>
	</div>
	<!-- //pop-top -->
	<!-- pop-mid -->
	<div class="pop-mid">
		
		<div class="return-order" style="border-bottom: 1px solid #ddd;">
			주문번호 <span class="em" id="cancelOrderCd"></span>
		</div>

		<div class="order-form first">
			<div class="form-group">
				<div class="form-body">
					<div class="row">
						<div class="form-label">
							<label><span class="required">*</span> 환불 계좌</label>
						</div>
						<div class="col col-6">
							<div class="form-control">
								<div class="opt_select">
									<select name="refundBankCode" id="refundBankCode" required>
										<option value="">은행 선택</option>
										<c:forEach var="list" items="${bankCodeList}">
											<option value="${list.commonCd}" <c:if test="${refundAccount.bankCode eq list.commonCd}">selected</c:if>>${list.cdNm}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="col col-6">
							<div class="form-control">
								<input type="text" class="input" placeholder="예금주" name="refundDepositor" id="refundDepositor" value="<c:out value="${refundAccount.depositor}"/>" maxlength="50"/>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
								<input type="text" class="input" placeholder="계좌번호 (‘-’를 제외한 숫자만 입력)" name="refundAccount" id="refundAccount" value="<c:out value="${refundAccount.account}"/>" maxlength="50" />
							</div>
						</div>
					</div>
				<c:if test="${not empty USERINFO.memberId }">	<%-- 계좌 저장/관리는 회원만 --%>
					<div class="row">
						<div class="col col-7">
							<div class="form-control">
								<div class="optgroup">
									<span class="checkbox">
										<input type="checkbox"  class="check" name="saveRefundAccount" id="saveRefundAccount" value="Y" />
										<label for="saveRefundAccount" class="lbl">환불 계좌 저장</label>
									</span>
								</div>
							</div>
						</div>
						<div class="col col-5">
							<a href="${CTX}/mypage/member/memberAccount.do" class="btn full" style="margin: 5px 0;"><span class="txt">환불계좌등록관리</span></a>
						</div>
					</div>
					<ul class="bu-list" style="margin-top: 5px;">
						<li><span class="bu">*</span> 환불계좌는 마이페이지>개인정보수정에서 등록할 수 있습니다.</li>
					</ul>
				</c:if>
				</div>
				
			</div>
		</div>
		
		<div class="btn-box confirm">
			<button type="button" class="btn" data-dismiss="popup"><span class="txt">취소</span></button>
			<button type="button" class="btn outline-green" onclick="javascript:requestCancel();"><span class="txt">신청</span></button>
		</div>
		
	</div>
	<!-- //pop-mid -->
	<a href="javascript:void(0);" class="btn_close" data-dismiss="popup"><span class="hide">닫기</span></a>
	</form>
</div>
<!-- //가상계좌 주문취소 -->


<c:if test="${status eq 'LIVE'}" >
	<!-- 공통 적용 스크립트 , 모든 페이지에 노출되도록 설치. 단 전환페이지 설정값보다 항상 하단에 위치해야함 --> 
	<script type="text/javascript"> 
	if (!wcs_add) var wcs_add={};
	wcs_add["wa"] = "s_322915267c39";
	if (!_nasa) var _nasa={};
	wcs.inflow("mandom.co.kr");
	wcs_do(_nasa);
	</script>
</c:if>

</body>