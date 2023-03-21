<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="join.main" />

	<!-- 전환페이지 설정 -->
	<script type="text/javascript"> 
		if("<spring:message code='server.status'/>" == "LIVE"){
			var _nasa={};
			_nasa["cnv"] = wcs.cnv("2","10"); // 전환유형, 전환가치 설정해야함. 설치매뉴얼 참고
			
			// 카카오 애드
			//<![CDATA[ 
			  var DaumConversionDctSv="type=M,orderID=,amount="; 
			  var DaumConversionAccountID="KpsXDQ3Qr_GUnGx-PGVwwg00"; 
			  if(typeof DaumConversionScriptLoaded=="undefined"&&location.protocol!="file:"){ 
			    var DaumConversionScriptLoaded=true; 
			    document.write(unescape("%3Cscript%20type%3D%22text/javas"+"cript%22%20src%3D%22"+(location.protocol=="https:"?"https":"http")+"%3A//t1.daumcdn.net/cssjs/common/cts/vr200/dcts.js%22%3E%3C/script%3E")); 
			  } 
			//]]>
			
			  <!-- Facebook Pixel Code -->
			  fbq('track', 'CompleteRegistration');

		}
	</script> 

	<!-- 카카오펙셀스크립트 : 가입완료 -->
	<script type="text/javascript" charset="UTF-8" src="//t1.daumcdn.net/adfit/static/kp.js"></script>
	<script type="text/javascript">
	      kakaoPixel('466785529738862663').pageView();
	      kakaoPixel('466785529738862663').completeRegistration();
	</script>
	
<script>

	<%-- 세션ID --%>
	function getSessionId(){
		if(localStorage.getItem("mandomSessionId") == null){
			localStorage.setItem("mandomSessionId","<%=session.getId()%>");
		}
	
		return localStorage.getItem("mandomSessionId");
	}

	//장바구니 담기
	function goCart(goodsIdx, goodsCd, goodsNm, goodsPrice){
		addCart(goodsIdx, 1, goodsCd, goodsNm, goodsPrice);
	}
	
	// 바로구매
	function buyNow(goodsIdx){
		var arrOrder = new Array();
		var goodsObj = new Object();
		goodsObj.goodsIdx = Number(goodsIdx);
		goodsObj.goodsCnt = 1;
		arrOrder.push(goodsObj);
		orderNow(arrOrder);
	}
	
</script>
</head>
<body>
	<c:set var="status"><spring:message code="server.status" /></c:set>
	<c:if test="${status eq 'LIVE'}" >
		<script type="text/javascript">
			var google_conversion_id = 833778563;
			var google_conversion_language = "en";
			var google_conversion_format = "3";
			var google_conversion_color = "ffffff";
			var google_conversion_label = "OeiiCIS51nUQg-fJjQM";
			var google_remarketing_only = false;
		</script>
		<script type="text/javascript" src="//www.googleadservices.com/pagead/conversion.js"></script>
		<noscript>
			<div style="display:inline;">
			<img height="1" width="1" style="border-style:none;" alt="" src="//www.googleadservices.com/pagead/conversion/833778563/?label=OeiiCIS51nUQg-fJjQM&amp;guid=ON&amp;script=0"/>
			</div>
		</noscript>
	</c:if>

	<div id="single">
		<header id="header">
			<h1 id="logo">
				<a href="${CTX}/main.do">
					<img src="${CTX}/images/logo_mandom.png" alt="Mandom">
				</a>
			</h1>
			<h2 class="page-title">회원가입</h2>
		</header>
		<article id="container" class="clearfix">
			<div id="content" class="inner">
				<div class="thanks">
					<strong class="thanks_title">${detail.memberNm}님, 회원이 되신 것을<br>진심으로 환영합니다.</strong>

					<!--
					<div class="thanks_gift">
						<ul>
							<li>
								<%-- <p>
									<img src="${CTX }/images/txt_gift_1.png" alt="1">
									<c:choose>
										<c:when test="${VO.compareFlag eq 'Y'}">
											<span>5,000P 지급 완료!</span>
										</c:when>
										<c:otherwise>
											<span>회원가입포인트 지급 완료!</span>
										</c:otherwise>
									</c:choose>
								</p> --%>
								<p>
									<img src="${CTX}/images/txt_gift_1.png" alt="1">
									<span>
										<c:choose>
											<c:when test="${detail.joinType eq '0' }"><fmt:formatNumber value="${detail.pointPrice}" /></c:when>
											<c:otherwise>2,000</c:otherwise>
										</c:choose>
										P 지급 완료!
									</span>
								</p>
							</li>
							<li>
								<p>
									<img src="${CTX}/images/txt_gift_2.png" alt="2">
									<span>무료배송 쿠폰 지급 완료!</span>
								</p>
							</li>
						</ul>
					</div>
					-->

					<div class="button-group">
						<a href="${CTX}/main.do" class="btn btn-black">쇼핑하기</a>
					</div>
					<c:if test="${VO.compareFlag eq 'Y'}">
						<div style="margin-top: 1.25rem; text-align: center">
							<a href="javascript:void(0);" onclick="buyNow('823');"><img src="${CTX}/images/banner_20191028.jpg" style="max-width:100%;" alt="" /></a>
						</div>
					</c:if>
				</div>

				<!--
				<div class="best_product">
					<h3 class="best_title">
						<img src="${CTX}/images/txt_join_bestproduct.png" alt="best Products">
					</h3>
					<div class="product-list">
						<div class="product-items">
							<ul class="clearfix">
								<c:forEach var="list" items="${list}" begin="0" end="3">
									<li <c:if test="${list.soldoutYn eq 'Y'}">class="soldout"</c:if>>
										<div class="item-show">
											<div class="badge-box">
												<c:if test="${list.opoYn eq 'Y' }" ><span class="badge type1">1+1</span></c:if>
												<c:if test="${list.tpoYn eq 'Y' }" ><span class="badge type2">2+1</span></c:if>
												<c:if test="${list.newYn eq 'Y' }" ><span class="badge type3">NEW</span></c:if>
												<c:if test="${list.bestYn eq 'Y' }" ><span class="badge type4">BEST</span></c:if>
												<c:if test="${list.saleiconYn eq 'Y' }" ><span class="badge type5">SALE</span></c:if>
												<c:if test="${list.pointiconYn eq 'Y' }" ><span class="badge type3"><i>POINT</i> X2</span></c:if>
											</div>
											<div class="item-img">
												<c:set var="imgSplit" value="${fn:split(list.mainFile ,'.') }"/>
												<img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_M.${imgSplit[1]}">
												<%-- <c:forEach var="imgList" items="${productSubImgList}" varStatus="idx">
													<c:if test="${list.goodsIdx eq imgList.goodsIdx}" >
														<c:set var="subImgSplit" value="${fn:split(imgList.imgFile ,'.') }"/>
														<img src="${IMGPATH}/goods/${imgList.goodsIdx}/${subImgSplit[0]}_M.${subImgSplit[1]}" class="img-extra">
													</c:if>
												</c:forEach>
												<div class="item-quick">
													<a href="javascript:" class="btn-buy" onclick="buyNow('${list.goodsIdx}');"><span>바로구매</span></a>
													<a id="cartBtn" href="javascript:" class="btn-cart" onclick="goCart('${list.goodsIdx}','<c:out value="${list.goodsCd}"/>','<c:out value="${list.goodsTitle}"/>','<fmt:formatNumber value="${list.discountPrice}" groupingUsed="false"/>');"><span>장바구니</span></a>
												</div> --%>
											</div>
											
											<div class="item-info">
												<fmt:formatNumber  var="discountRate" value="${list.discountRate}" type="number" maxFractionDigits="0"/>
												<c:if test="${discountRate != 0}" >
													<del class="item-origin"><fmt:formatNumber value="${list.price}" /></del>
												</c:if>
												<p class="item-price">
													<c:if test="${discountRate != 0}" >
														<span class="color-1" data-format="%">${discountRate}</span>
													</c:if>
													<fmt:formatNumber value="${list.discountPrice}" />
												</p>
												<div class="item-desc">
													<p class="item-detail truncate color-1">${list.shortInfo}</p>
													<strong>${list.goodsTitle}</strong>
												</div>
												<div class="item-coupon">
													<c:if test="${list.onlineYn eq 'Y'}">
														<img src="${CTX}/images/${DEVICE}/contents/ico_coupon_online.png" alt="쿠폰">
													</c:if>
													<c:if test="${list.autoCouponYn eq 'Y'}">
														<img src="${CTX}/images/${DEVICE}/contents/ico_coupon_recommend.png" alt="쿠폰">
													</c:if>
												</div>
											</div>
										</div>
										<a href="${CTX}/product/productView.do?goodsCd=${list.goodsCd}&choiceCateIdx=${list.choiceCateIdx}&gnbBrand=${gnbBrand}" class="item-link"><span class="sr-only">상품상세보기</span></a>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
				-->
			</div>
		</article>
	</div>
	
	<!-- 바로구매용 -->
	<form name="commonOrderForm" id="commonOrderForm" method="post" onsubmit="return false;">
		<input type="hidden" name="sessionId" id="commonSessionId" value=""/>
		<input type="hidden" name="orderGoodsInfoListStr" id="commonOrderGoodsInfoListStr" value=""/>
	</form>
	
	<!-- 장바구니 이동 팝업 -->
	<div class="remodal popup" data-remodal-id="pop_go_cart">
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
			<a href="javascript:void(0);" class="btn-pc btn-small" data-remodal-action="cancel">취소</a>
			<a href="${CTX}/cart.do" class="btn-pc btn-green btn-small">확인</a>
		</div>
	</div>
	<!-- //장바구니 이동 팝업 -->
	
	<!-- 알럿 팝업 -->
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
	
	<!-- WIDERPLANET  SCRIPT START 2017.8.29 -->
	<div id="wp_tg_cts" style="display:none;"></div>
	<script type="text/javascript">
		var wptg_tagscript_vars = wptg_tagscript_vars || [];
		wptg_tagscript_vars.push(
		(function() {
		    return {
		        wp_hcuid:"",  /*Cross device targeting을 원하는 광고주는 로그인한 사용자의 Unique ID (ex. 로그인 ID, 고객넘버 등)를 암호화하여 대입.
		                     *주의: 로그인 하지 않은 사용자는 어떠한 값도 대입하지 않습니다.*/
		        ti:"37336",
		        ty:"Join",                        /*트래킹태그 타입*/
		        device:"mobile",                  /*디바이스 종류 (web 또는 mobile)*/
		        items:[{
		            i:"회원가입",          /*전환 식별 코드 (한글, 영문, 숫자, 공백 허용)*/
		            t:"회원가입",          /*전환명 (한글, 영문, 숫자, 공백 허용)*/
		            p:"1",                   /*전환가격 (전환 가격이 없을 경우 1로 설정)*/
		            q:"1"                   /*전환수량 (전환 수량이 고정적으로 1개 이하일 경우 1로 설정)*/
		        }]
		    };
		}));
	</script>
	<script type="text/javascript" async src="//cdn-aitg.widerplanet.com/js/wp_astg_4.0.js"></script>
</body>
</html>