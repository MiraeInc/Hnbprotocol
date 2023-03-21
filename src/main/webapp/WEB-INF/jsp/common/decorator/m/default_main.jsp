<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta name="google-site-verification" content="wgIiYXH4a2mB0VAj6Zg1rlSpg3YKD7TBU-Y2Kfzsh7M" />
<script type="text/javascript">
		// mobon Enliple Tracker Start
		var device = "${DEVICE}".toUpperCase();
		(function(a,g,e,n,t){a.enp=a.enp||function(){(a.enp.q=a.enp.q||[]).push(arguments)};n=g.createElement(e);n.async=!0;n.defer=!0;n.src="https://cdn.megadata.co.kr/dist/prod/enp_tracker_self_hosted.min.js";t=g.getElementsByTagName(e)[0];t.parentNode.insertBefore(n,t)})(window,document,"script");
	    enp('create', 'common', 'mandom', { device: device });    
	    enp('send', 'common', 'mandom');
		// mobon Enliple Tracker End
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
<%--  네이버 메인보드이벤트 CDN 적용전 --%>
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
<script src="${CTX}/js/${DEVICE}/jquery.dotdotdot.min.js"></script>
<script src="${CTX}/js/remodal.min.js"></script>
<script src="${CTX}/js/commonScript.js"></script>
<script src="${CTX}/js/${DEVICE}/cart.js"></script>
<script src="${CTX}/js/${DEVICE}/imagesloaded.pkgd.min.js"></script>
 
<!-- GATSBY MOBILE JS -->
<%-- 
<script type="text/javascript" src="https://wcs.naver.net/wcslog.js"></script>
<script src="https://image.mandom.co.kr/js/jquery-3.2.1.min.js"></script>
<script src="https://image.mandom.co.kr/js/${DEVICE}/swiper.min.js"></script>
<script src="https://image.mandom.co.kr/js/jquery-ui.min.js"></script>
<script src="https://image.mandom.co.kr/js/jquery-migrate-3.0.0.min.js"></script>
<script src="https://image.mandom.co.kr/js/lightslider.min.js"></script>
<script src="https://image.mandom.co.kr/js/${DEVICE}/hammer.min.js"></script>
<script src="https://image.mandom.co.kr/js/${DEVICE}/masonry.pkgd.min.js"></script>
<script src="https://image.mandom.co.kr/js/${DEVICE}/ui.site.new.js"></script>
<script src="https://image.mandom.co.kr/js/${DEVICE}/jquery.dotdotdot.min.js"></script>
<script src="https://image.mandom.co.kr/js/remodal.min.js"></script>
<script src="https://image.mandom.co.kr/js/commonScript.js"></script>
<script src="https://image.mandom.co.kr/js/${DEVICE}/cart.js"></script>
<script src="https://image.mandom.co.kr/js/${DEVICE}/imagesloaded.pkgd.min.js"></script>
  --%>

<!-- 리마케팅 -->
<script type="text/javascript" src="//adimg.daumcdn.net/rt/roosevelt.js" async></script>
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

<script async src="https://www.googletagmanager.com/gtag/js?id=AW-833778563"></script>
<script>
	if("<spring:message code='server.status'/>" == "LIVE")
	{
		  window.dataLayer = window.dataLayer || [];
		  function gtag(){dataLayer.push(arguments);}
		  gtag('js', new Date());		
		  gtag('config', 'AW-833778563');
	}
</script>


<decorator:head/>
</head>

<body data-main="${layout_type}">

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
		<decorator:body/>
 	</main>
	<page:applyDecorator  name="mobile.footer" encoding="UTF-8"/>
	</div>
	
<c:if test="${status eq 'LIVE'}" >
	<!-- WIDERPLANET  SCRIPT START 2017.8.29 -->
	<div id="wp_tg_cts" style="display:none;"></div>
	<script type="text/javascript">
	var wptg_tagscript_vars = wptg_tagscript_vars || [];
	wptg_tagscript_vars.push(
	(function() {
		return {
			wp_hcuid:"",  	/*Cross device targeting을 원하는 광고주는 로그인한 사용자의 Unique ID (ex. 로그인 ID, 고객넘버 등)를 암호화하여 대입.
					 *주의: 로그인 하지 않은 사용자는 어떠한 값도 대입하지 않습니다.*/
			ti:"37336",	/*광고주 코드*/
			ty:"Home",	/*트래킹태그 타입*/
			device:"mobile"	/*디바이스 종류 (web 또는 mobile)*/
		};
	}));
	</script>
	<script type="text/javascript" async src="//cdn-aitg.widerplanet.com/js/wp_astg_4.0.js"></script>
	<!-- // WIDERPLANET  SCRIPT END 2017.8.29 -->
	
	<script type="text/javascript" charset="UTF-8" src="//t1.daumcdn.net/adfit/static/kp.js"></script>
	<script type="text/javascript">
	      kakaoPixel('466785529738862663').pageView();
	</script>
</c:if>		
	
	<!-- 공통 적용 스크립트 , 모든 페이지에 노출되도록 설치. 단 전환페이지 설정값보다 항상 하단에 위치해야함 --> 
	<script type="text/javascript"> 
	if (!wcs_add) var wcs_add={};
	wcs_add["wa"] = "s_322915267c39";
	if (!_nasa) var _nasa={};
	wcs.inflow("mandom.co.kr");
	wcs_do(_nasa);
	</script>
</body>