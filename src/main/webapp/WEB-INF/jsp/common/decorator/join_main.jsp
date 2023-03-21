<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE">
<html class="no-js" lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="author" content="">
<meta name="HandheldFriendly" content="true"/>
<meta name="MobileOptimized" content="320"/>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
<meta name="format-detection" content="telephone=no, address=no, email=no">
<title><spring:message code="common.title"/></title>
<link rel="apple-touch-icon" href="${CTX}/images/favicon-152.png">
<link rel="shortcut icon" href="${CTX}/images/favicon.ico">

<!-- Stylesheets -->

<link rel="stylesheet" href="${CTX}/css/normalize.css">
<link rel="stylesheet" href="${CTX}/css/style_2018.css">
<link rel="stylesheet" href="${CTX}/css/template_2018.css">
<link rel="stylesheet" href="${CTX}/css/login_2018.css">
<link rel="stylesheet" href="${CTX}/css/lightslider.min.css">
<link rel="stylesheet" href="${CTX}/css/remodal.min.css">
<link rel="stylesheet" href="${CTX}/css/remodal-default-theme.css">

<!-- Scripts -->
<script type="text/javascript" src="https://wcs.naver.net/wcslog.js"> </script>
<script src="${CTX}/js/commonScript.js"></script>
<script src="${CTX}/js/${DEVICE}/cart.js"></script>
<script src="${CTX}/js/modernizr-2.8.3.min.js"></script>
<script src="${CTX}/js/jquery-3.2.1.min.js"></script>
<script src="${CTX}/js/console.js"></script>
<script src="${CTX}/js/jquery.fittext.js"></script>
<script src="${CTX}/js/ui.login.js"></script>
<script src="${CTX}/js/remodal.min.js"></script>

 
<!-- Stylesheets -->
<%-- 
<link rel="stylesheet" href="https://image.mandom.co.kr/css/normalize.css">
<link rel="stylesheet" href="https://image.mandom.co.kr/css/style_2018.css">
<link rel="stylesheet" href="https://image.mandom.co.kr/css/template_2018.css">
<link rel="stylesheet" href="https://image.mandom.co.kr/css/login_2018.css">
<link rel="stylesheet" href="https://image.mandom.co.kr/css/lightslider.min.css">
<link rel="stylesheet" href="https://image.mandom.co.kr/css/remodal.min.css">
<link rel="stylesheet" href="https://image.mandom.co.kr/css/remodal-default-theme.css">

<!-- Scripts -->
<script type="text/javascript" src="https://wcs.naver.net/wcslog.js"> </script>
<script src="https://image.mandom.co.kr/js/commonScript.js"></script>
<script src="https://image.mandom.co.kr/js/${DEVICE}/cart.js"></script>
<script src="https://image.mandom.co.kr/js/modernizr-2.8.3.min.js"></script>
<script src="https://image.mandom.co.kr/js/jquery-3.2.1.min.js"></script>
<script src="https://image.mandom.co.kr/js/console.js"></script>
<script src="https://image.mandom.co.kr/js/jquery.fittext.js"></script>
<script src="https://image.mandom.co.kr/js/ui.login.js"></script>
<script src="https://image.mandom.co.kr/js/remodal.min.js"></script>
 --%>
 
<script>document.domain = "<spring:message code='server.defaultDomain'/>"</script>
<!-- 리마케팅 -->
<script type="text/javascript" src="//adimg.daumcdn.net/rt/roosevelt.js" async></script>
<script type="text/javascript">
		// mobon Enliple Tracker Start
		var device = "${DEVICE}".toUpperCase();
		(function(a,g,e,n,t){a.enp=a.enp||function(){(a.enp.q=a.enp.q||[]).push(arguments)};n=g.createElement(e);n.async=!0;n.defer=!0;n.src="https://cdn.megadata.co.kr/dist/prod/enp_tracker_self_hosted.min.js";t=g.getElementsByTagName(e)[0];t.parentNode.insertBefore(n,t)})(window,document,"script");
	    enp('create', 'common', 'mandom', { device: device });    
	    enp('send', 'common', 'mandom');
		// mobon Enliple Tracker End
</script>

<!-- Facebook Pixel Code -->
<script>
if("<spring:message code='server.status'/>" == "LIVE")
{
	// GA
	(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
		new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
		j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
		'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
		})(window,document,'script','dataLayer','GTM-NNQDJVC');
	
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

<script>
	if("<spring:message code='server.status'/>" == "LIVE" && "${CTX}" == "/w")
	{
    (function(j,en,ni,fer) {
        j['dmnaid']=fer;j['dmnatime']=new Date();j['dmnanocookie']=false;j['dmnajennifer']='JENNIFER_FRONT@TXID';
        var a=en.createElement(ni);a.src='https://d-collect.jennifersoft.com/'+fer+'/demian.js';
        a.async=true;en.getElementsByTagName(ni)[0].parentNode.appendChild(a);
    }(window,document,'script','3f25de5b-ae9a-bc87-a010-860e6853604f'));
  }
</script>


<script>
	if("<spring:message code='server.status'/>" == "LIVE" && "${CTX}" == "/m")
	{
    (function(j,en,ni,fer) {
        j['dmnaid']=fer;j['dmnatime']=new Date();j['dmnanocookie']=false;j['dmnajennifer']='JENNIFER_FRONT@TXID';
        var a=en.createElement(ni);a.src='https://d-collect.jennifersoft.com/'+fer+'/demian.js';
        a.async=true;en.getElementsByTagName(ni)[0].parentNode.appendChild(a);
    }(window,document,'script','b6695836-d184-60ef-e0fd-3c3f50508811'));		
	}
</script>

<noscript>
if("<spring:message code='server.status'/>" == "LIVE")
{
	<img height="1" width="1" src="https://www.facebook.com/tr?id=285644202255951&ev=PageView&noscript=1"/>
}	
</noscript>
<!-- End Facebook Pixel Code -->

<decorator:head/>
</head>
<body>
	<c:set var="status"><spring:message code="server.status" /></c:set>
	<c:if test="${status eq 'LIVE'}" >
		<noscript>
			<iframe src="https://www.googletagmanager.com/ns.html?id=GTM-NNQDJVC" height="0" width="0" style="display:none;visibility:hidden"></iframe>
		</noscript>
	</c:if>
	
	<decorator:body/>
	
	<c:if test="${status eq 'LIVE'}" >
	</c:if>
	<!-- 공통 적용 스크립트 , 모든 페이지에 노출되도록 설치. 단 전환페이지 설정값보다 항상 하단에 위치해야함 --> 
		<script type="text/javascript"> 
		if (!wcs_add) var wcs_add={};
		<c:choose>
			<c:when test="${DEVICE eq 'm'}">
				wcs_add["wa"] = "s_322915267c39";  //s_3c6b953b5d1
			</c:when>
			<c:otherwise>
				wcs_add["wa"] = "s_322915267c39";
			</c:otherwise>
		</c:choose>
		if (!_nasa) var _nasa={};
		wcs.inflow("mandom.co.kr");
		wcs_do(_nasa);
		</script>	
		
	<!-- 알럿 팝업 --><%--비회원 장바구니 때문에 추가 --%>
	<div class="remodal popup" data-remodal-id="pop_alert">
		<a href="javascript:void(0);" class="ico_pop_close" data-remodal-action="close">
			<img src="${CTX}/images/${DEVICE}/contents/ico_pop_close.png" alt="닫기">
		</a>
		<div class="pop-top">
			<h2>알림</h2>
		</div>
	
		<div class="pop-mid">
			<p></p>
		</div>
	
		<div class="pop-btn-wrap">
			<a href="javascript:void(0);" class="btn-pc btn-green btn-small" data-remodal-action="confirm">확인</a>
		</div>
	</div>
	<!-- //알럿 팝업 -->
</body>
</html>