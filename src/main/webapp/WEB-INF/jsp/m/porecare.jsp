<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE html>
<html class="no-js" lang="ko">
<head>

<meta charset="UTF-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge">
	<meta name="description" content="'헤아쟈무 뭐야?' '무빙 뭐야?' 남성 화장품 브랜드 '갸스비'의 사이트. 제품 정보, 제품 사용법, 이벤트, CM도 공개 중">
	<meta name="keywords" content="헤어 스타일링, 헤어 왁스, 무빙, 헤아쟈무 그리스 스타일링 그리스, 헤어 컬러 턴 컬러, 면도 셀프 컷, 좋아 면도기, 헤어 트리머, 눈썹, 몸 종이, 바디 시트 세안 시트 세안 종이, 얼굴 종이, 스킨 케어">
	<meta name="author" content="">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>GATSBY [갸스비]</title>
	<link rel="apple-touch-icon" href="${CTX}/images/w/favicon-152.png">
	<link rel="shortcut icon" href="${CTX}/images/w/favicon.ico">

	<!-- Stylesheets -->
	<link rel="stylesheet" href="${CTX}/css/normalize.css">
	<link rel="stylesheet" href="${CTX}/css/style_new.css">
	<link rel="stylesheet" href="${CTX}/css/template_new.css">
	<link rel="stylesheet" href="${CTX}/css/jquery-ui.min.css">
	<link rel="stylesheet" href="${CTX}/css/lightslider.min.css">
	<link rel="stylesheet" href="${CTX}/css/w/layout_new.css">
	<link rel="stylesheet" href="${CTX}/css/w/contents_new.css">
	<link rel="stylesheet" href="${CTX}/css/w/swiper.min.css">
	<link rel="stylesheet" href="${CTX}/css/remodal.min.css">
	<link rel="stylesheet" href="${CTX}/css/remodal-default-theme.css">

	<script src="${CTX}/js/modernizr-2.8.3.min.js"></script>
	<script src="${CTX}/js/jquery-3.2.1.min.js"></script>
	<script src="${CTX}/js/jquery-ui.min.js"></script>
	<script src="${CTX}/js/console.js"></script>
	<script src="${CTX}/js/lightslider.min.js"></script>
	<script src="${CTX}/js/w/swiper.min.js"></script>
	<script src="${CTX}/js/w/jquery.dotdotdot.min.js"></script>
	<script src="${CTX}/js/w/ui.site.new.js"></script>
	<script src="${CTX}/js/remodal.min.js"></script>

<script>
	$(function(){
		$(".cart-preview").remove();
		
		var swiper = new Swiper('#mainBanner', {
			autoplay: {
				delay: 3500	
			},
			autoplayDisableOnInteraction: false,
			centeredSlides: true,
			slidesPerView: 'auto',
			spaceBetween: 40,
			slidesOffsetBefore: 268,
			loop: true,
			a11y: true,
			navigation: {
				nextEl: '.swiper-button-next',
				prevEl: '.swiper-button-prev',
			},
		});
	})
</script>
</head>

<body data-main="gatsby">
	<!--[if lt IE 10]>
	<p class="browserupgrade">현재 <strong>기능지원이 되지 않는 구버전</strong> 웹브라우저로 접속중입니다. 원활한 사이트이용을 위해 <a href="http://browsehappy.com/">최신 브라우저</a>로 업그레이드 하세요!</p>
	<![endif]-->
	<div id="wrap">
		<header id="header" class="header">
		<div class="header-top">
			<div class="inner">
				<div class="brand-menus">
					<%-- <a href="${CTX}/main.do" class="brand-mandom"><span class="sr-only">mandom</span></a> --%>
					<ul class="brand-link">
						<li class="link-gatsby <c:if test="${layout_type eq 'gatsby'}">active</c:if>"><a href="${CTX}/gatsby/main.do"><c:choose><c:when test="${layout_type eq 'gatsby'}">남성 토탈 케어, 갸스비</c:when><c:otherwise>GATSBY</c:otherwise></c:choose> </a></li>
						<li class="link-bifesta <c:if test="${layout_type eq 'bifesta'}">active</c:if> "><a href="${CTX}/bifesta/main.do"><c:choose><c:when test="${layout_type eq 'bifesta'}">더 깨끗한 클렌징, 비페스타</c:when><c:otherwise>BIFESTA</c:otherwise></c:choose> </a></li>
						<li class="link-lucidol <c:if test="${layout_type eq 'lucidol'}">active</c:if> "><a href="${CTX}/lucidol/main.do"><c:choose><c:when test="${layout_type eq 'lucidol'}">깃털처럼 가벼운 헤어오일, 루시도엘</c:when><c:otherwise>LUCIDO-L</c:otherwise></c:choose> </a></li>
						<%-- <li class="link-lucido <c:if test="${layout_type eq 'lucido'}">active</c:if> "><a href="${CTX}/lucido/main.do"><c:choose><c:when test="${layout_type eq 'lucido'}">남자의 나이를 자랑스럽게, 루시도</c:when><c:otherwise>LUCIDO</c:otherwise></c:choose> </a></li> --%>
						<li class="link-barrier <c:if test="${layout_type eq 'barrier'}">active</c:if> "><a href="${CTX}/barrier/main.do"><c:choose><c:when test="${layout_type eq 'barrier'}">촉촉탱탱 마스크팩, 베리어리페어</c:when><c:otherwise>BARRIER REPAIR</c:otherwise></c:choose> </a></li>				
						<%-- <li class="link-mamabutter <c:if test="${layout_type eq 'mamabutter'}">active</c:if> "><a href="${CTX}/mamabutter/main.do"><c:choose><c:when test="${layout_type eq 'mamabutter'}">시어버터 100%, 마마버터</c:when><c:otherwise>MAMA BUTTER</c:otherwise></c:choose> </a></li> --%>  
						<li class="link-dentalpro  <c:if test="${layout_type eq 'dentalpro'}">active</c:if> "><a href="${CTX}/dentalpro/main.do"><c:choose><c:when test="${layout_type eq 'dentalpro'}">프로의 치아관리, 덴탈프로</c:when><c:otherwise>DENTALPRO</c:otherwise></c:choose> </a></li> 
						<%-- <li class="link-charley  <c:if test="${layout_type eq 'charley'}">active</c:if> "><a href="${CTX}/charley/main.do"><c:choose><c:when test="${layout_type eq 'charley'}">깨끗한 몸과 마음의 힐링타임, 찰리</c:when><c:otherwise>CHARLEY</c:otherwise></c:choose> </a></li> --%>
						<%-- <li class="link-gpcreate  <c:if test="${layout_type eq 'gpcreate'}">active</c:if> "><a href="${CTX}/gpcreate/main.do"><c:choose><c:when test="${layout_type eq 'gpcreate'}">나만의 레시피 입욕제, 솔트바</c:when><c:otherwise>GP CREATE</c:otherwise></c:choose> </a></li> --%>
					</ul>
					<%--<a href="javascript:void(0);" class="brand-anchor"><span>브랜드 더보기</span></a> --%>
				</div>
				<jsp:directive.include file="/WEB-INF/jsp/common/layout/w/common_header.jsp"/>
			</div>
		</div>
		</header>

		<article id="container">
			<div id="content" class="clearfix">
				
				<style type="text/css">
					.sub-panel {width: 1320px; margin: 0 auto;}

					.gbfc_tab,
					.gbfc_menu,
					.gbfc_contbox {position: relative;}

					.gbfc_tabcont {display: none;}
					.gbfc_tabcont:first-child {display: block;}
				</style>

				<script>
					$(function(){
						$('.gbfc_menu a').on('click', function(e){
							e.preventDefault();
							
							var $this = $(this);
							var href = $this.attr('href');
							var detlPos = $(href).offset().top;
							
							$('html, body').animate( { scrollTop : detlPos }, 400 );
						});
						
						$('#gbfcCont04 a').on('click', function(e){
							e.preventDefault();
							
							var $this = $(this);
							var href = $this.attr('href');

							$(href).show().siblings('.gbfc_tabcont').hide();

							var detlPos = $(href).offset().top;
							
							$('html, body').animate( { scrollTop : detlPos }, 400 );

							
						});
						
						$('.gbfc_tabcont .gbfcTabMenu').on('click', function(e){
							e.preventDefault();
							
							var $this = $(this);
							var href = $this.attr('href');
							
							$(href).show().siblings('.gbfc_tabcont').hide();
						});

					})
				</script>

				<div class="sub-panel">
					
					<div class="evnt_gbfc">
						<div class="gbfc_menu">
							<img src="${CTX}/images/img_evnt_top.jpg" alt=""/>
							<a href="#gbfcCont01" style="position: absolute; left: 790px; top: 0; width: 115px; height: 100%;"><span class="sr-only">모공트러블이란</span></a>
							<a href="#gbfcCont03" style="position: absolute; left: 905px; top: 0; width: 105px; height: 100%;"><span class="sr-only">모공관리영상</span></a>
							<a href="#gbfcCont04" style="position: absolute; left: 1010px; top: 0; width: 115px; height: 100%;"><span class="sr-only">3대 모공트러블</span></a>
							<a href="#gbfcTab" style="position: absolute; left: 1125px; top: 0; width: 115px; height: 100%;"><span class="sr-only">모공관리방법</span></a>
						</div>
						<div class="gbfc_cont">
							<div class="gbfc_contbox">
								<img src="${CTX}/images/img_evnt_00.jpg" alt=""/>
							</div>
							<div id="gbfcCont01" class="gbfc_contbox">
								<img src="${CTX}/images/img_evnt_01.jpg" alt=""/>
							</div>
							<div id="gbfcCont02" class="gbfc_contbox">
								<img src="${CTX}/images/img_evnt_02.jpg" alt=""/>
							</div>
							<div id="gbfcCont03" class="gbfc_contbox">
								<img src="${CTX}/images/img_evnt_03.jpg" alt=""/>
								<iframe width="890" height="410" style="position: absolute; left: 210px; top: 322px;" src="https://www.youtube.com/embed/HQhReHqOccc" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>
							</div>
							<div id="gbfcCont04" class="gbfc_contbox">
								<img src="${CTX}/images/img_evnt_04.jpg" alt=""/>
								<a href="#gbfcTab01" style="position: absolute; left: 473px; top: 768px; width: 360px; height: 50px;"><span class="sr-only">건선모공관리방법</span></a>
								<a href="#gbfcTab02" style="position: absolute; left: 473px; top: 1388px; width: 360px; height: 50px;"><span class="sr-only">지성모공관리방법</span></a>
								<a href="#gbfcTab03" style="position: absolute; left: 473px; top: 1978px; width: 360px; height: 50px;"><span class="sr-only">블랙헤드관리방법</span></a>
							</div>
						</div>
						<div id="gbfcTab" class="gbfc_tab">
							<!-- #건성모공관리 -->
							<div id="gbfcTab01" class="gbfc_tabcont">
								<img src="${CTX}/images/img_evnt_05_01.jpg" alt=""/>
								<!-- #MENU -->
								<a href="#gbfcTab01" class="gbfcTabMenu" style="position: absolute; left: 200px; top: 262px; width: 300px; height: 70px;"><span class="sr-only">건선모공관리방법</span></a>
								<a href="#gbfcTab02" class="gbfcTabMenu" style="position: absolute; left: 500px; top: 262px; width: 300px; height: 70px;"><span class="sr-only">지성모공관리방법</span></a>
								<a href="#gbfcTab03" class="gbfcTabMenu" style="position: absolute; left: 800px; top: 262px; width: 300px; height: 70px;"><span class="sr-only">블랙헤드관리방법</span></a>
								<!-- #GIF -->
						        <img src="${CTX}/images/dry.gif" alt="" style="position: absolute; left: 331px; top: 693px;" /> <!-- 세안/건성피부 -->
						        <img src="${CTX}/images/allinone.gif" alt="" style="position: absolute; left: 331px; top: 1874px;" /> <!-- 올인원에센스 -->
						        <img src="${CTX}/images/sheetmask.gif" alt="" style="position: absolute; left: 331px; top: 3141px;" /> <!-- 시트마스크 -->
								<!-- #상품 -->
								<a href="https://www.mandom.co.kr/w/product/productView.do?goodsCd=4902806107777" style="position: absolute; left: 740px; top: 954px; width: 260px; height: 50px;"><span class="sr-only">세안제품 구매하기</span></a>
								<a href="https://www.mandom.co.kr/w/product/productView.do?goodsCd=4902806107791" style="position: absolute; left: 740px; top: 2083px; width: 260px; height: 50px;"><span class="sr-only">올인원 에센스 구매하기</span></a>
								<a href="https://www.mandom.co.kr/w/product/productView.do?goodsCd=4902806107845" style="position: absolute; left: 730px; top: 3346px; width: 260px; height: 50px;"><span class="sr-only">시트마스크 구매하기</span></a>
							</div>
							<!-- #지성모공관리 -->
							<div id="gbfcTab02" class="gbfc_tabcont">
								<img src="${CTX}/images/img_evnt_05_02.jpg" alt=""/>
								<!-- #MENU -->
								<a href="#gbfcTab01" class="gbfcTabMenu" style="position: absolute; left: 200px; top: 262px; width: 300px; height: 70px;"><span class="sr-only">건선모공관리방법</span></a>
								<a href="#gbfcTab02" class="gbfcTabMenu" style="position: absolute; left: 500px; top: 262px; width: 300px; height: 70px;"><span class="sr-only">지성모공관리방법</span></a>
								<a href="#gbfcTab03" class="gbfcTabMenu" style="position: absolute; left: 800px; top: 262px; width: 300px; height: 70px;"><span class="sr-only">블랙헤드관리방법</span></a>
								<!-- #GIF -->
        						<img src="${CTX}/images/oily.gif" alt="" style="position: absolute; left: 331px; top: 749px; " /> <!-- 세안/지성피부 -->
        						<img src="${CTX}/images/allinone.gif" alt="" style="position: absolute; left: 331px; top: 1974px;" /> <!-- 올인원에센스 -->
						        <img src="${CTX}/images/sheetmask.gif" alt="" style="position: absolute; left: 331px; top: 3241px;" /> <!-- 시트마스크 -->
								<!-- #상품 -->
								<a href="https://www.mandom.co.kr/w/product/productView.do?goodsCd=4902806107784" style="position: absolute; left: 740px; top: 954px; width: 260px; height: 50px;"><span class="sr-only">세안제품 구매하기</span></a>
								<a href="https://www.mandom.co.kr/w/product/productView.do?goodsCd=4902806107791" style="position: absolute; left: 740px; top: 2183px; width: 260px; height: 50px;"><span class="sr-only">올인원 에센스 구매하기</span></a>
								<a href="https://www.mandom.co.kr/w/product/productView.do?goodsCd=4902806107845" style="position: absolute; left: 730px; top: 3446px; width: 260px; height: 50px;"><span class="sr-only">시트마스크 구매하기</span></a>
							</div>
							<!-- #블랙헤드관리 -->
							<div id="gbfcTab03" class="gbfc_tabcont">
								<img src="${CTX}/images/img_evnt_05_03.jpg" alt=""/>
								<!-- #MENU -->
								<a href="#gbfcTab01" class="gbfcTabMenu" style="position: absolute; left: 200px; top: 262px; width: 300px; height: 70px;"><span class="sr-only">건선모공관리방법</span></a>
								<a href="#gbfcTab02" class="gbfcTabMenu" style="position: absolute; left: 500px; top: 262px; width: 300px; height: 70px;"><span class="sr-only">지성모공관리방법</span></a>
								<a href="#gbfcTab03" class="gbfcTabMenu" style="position: absolute; left: 800px; top: 262px; width: 300px; height: 70px;"><span class="sr-only">블랙헤드관리방법</span></a>
								<!-- #GIF -->
        						<img src="${CTX}/images/peelingpad.gif" alt="" style="position: absolute; left: 331px; top: 1453px; " /> <!-- 필링패드 -->
        						<img src="${CTX}/images/allinone.gif" alt="" style="position: absolute; left: 331px; top: 2700px;" /> <!-- 올인원에센스 -->
						        <img src="${CTX}/images/sheetmask.gif" alt="" style="position: absolute; left: 331px; top: 3975px;" /> <!-- 시트마스크 -->
								<!-- #상품 -->
								<a href="https://www.mandom.co.kr/w/product/productView.do?goodsCd=4902806107777" style="position: absolute; left: 360px; top: 954px; width: 260px; height: 50px;"><span class="sr-only">세안(건성)제품 구매하기</span></a>
								<a href="https://www.mandom.co.kr/w/product/productView.do?goodsCd=4902806107784" style="position: absolute; left: 700px; top: 954px; width: 260px; height: 50px;"><span class="sr-only">세안(지성)제품 구매하기</span></a>
								<a href="https://www.mandom.co.kr/w/product/productView.do?goodsCd=4902806107821" style="position: absolute; left: 740px; top: 1534px; width: 260px; height: 50px;"><span class="sr-only">필링패드 구매하기</span></a>
								<a href="https://www.mandom.co.kr/w/product/productView.do?goodsCd=4902806107791" style="position: absolute; left: 740px; top: 2909px; width: 260px; height: 50px;"><span class="sr-only">올인원 에센스 구매하기</span></a>
								<a href="https://www.mandom.co.kr/w/product/productView.do?goodsCd=4902806107845" style="position: absolute; left: 730px; top: 4172px; width: 260px; height: 50px;"><span class="sr-only">시트마스크 구매하기</span></a>
							</div>
						</div>
						<div class="gbfc_foot">
							<img src="${CTX}/images/img_evnt_foot.jpg" alt="" />
						</div>
					</div>

				</div>
			</div>
		</article>

	</div>
	
</body>

</html>