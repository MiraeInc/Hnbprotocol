<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE html>
<html>
<head>
<c:set var="layout_type" value="lucido" scope="request" />
<meta name="decorator" content="mobile.main" />	
<script>

$(window).on('load', function(){
	$('.product-items').productList();
	//리뷰 이미지 조정
	$('.main-review').reviewImage();
});

$(function(){
	var mainBannerSwiper = new Swiper('#mainBanner', {
		autoplay: {
			delay: 3500	
		},
		autoplayDisableOnInteraction: false,
		// centeredSlides: true,
		slidesPerView: 'auto',
		loop: true,
		a11y: true,
		pagination: {
			el: '.swiper-pagination',
			type: 'fraction',
		}
	});

	var mainBestSwiper = new Swiper('#mainBest', {
		autoplay: {
			delay: 3500	
		},
		autoplayDisableOnInteraction: false,
		// centeredSlides: true,
		slidesPerView: 'auto',
		loop: true,
		a11y: true,
		navigation: {
			nextEl: '.swiper-button-next',
			prevEl: '.swiper-button-prev',
		},
		on: {
			init: function(){

				var $obj = $(this.$el).closest('.bestprod-wrapper');
				
				$obj.prepend('<div id="mainBestPrev" class="swiper-clone-prev" />');
				$obj.append('<div id="mainBestNext" class="swiper-clone-next" />');
				
				$obj.find('.swiper-clone-next').html($obj.find('.swiper-slide-next').find('.prod-image').clone());
				$obj.find('.swiper-clone-prev').html($obj.find('.swiper-slide-prev').find('.prod-image').clone());
			},
			transitionStart: function(){
				var $obj = $(this.$el).closest('.bestprod-wrapper');
				$obj.find('.swiper-clone-prev').html($obj.find('.swiper-slide-active').prev().find('.prod-image').clone());
				$obj.find('.swiper-clone-next').html($obj.find('.swiper-slide-active').next().find('.prod-image').clone());
			}
		}
	});
});

$(document).ready(function(){
	// 메인 팝업
	<c:forEach var="list" items="${popupList}">
	var idx = "${list.popupIdx}";
	var gubun = "${list.deviceGubun}";
	var cookie = getCookie("mainPop"+gubun+idx);
	
	if(cookie=="" || cookie==null){
		noticePopup("popNotice");
	}
	</c:forEach>
});

//오늘 하루 보지 않기
function mainClosePop(idx){
	if($("#mainPop"+idx).prop("checked")){
		var idx = $("#mainPop"+idx).attr("data-idx");
		var gubun = $("#mainPop"+idx).attr("data-device");
		
		setCookie("mainPop"+gubun+idx, "mainPop"+gubun+idx, 1);
		$("[data-remodal-id=popNotice]").remodal().close();
	}
}

// 장바구니 담기
function goCart(goodsIdx, goodsCd, goodsNm, goodsPrice){
	addCart(goodsIdx, 1, goodsCd, goodsNm, goodsPrice);
}

</script>

<!-- Doyouad Start 삭제 하지 마세요. -->
<script type="text/javascript">
(function (w, d, s, n, t) {n = d.createElement(s);n.type = "text/javascript";n.setAttribute("id", "doyouadScript");n.setAttribute("data-user", "mandom");n.setAttribute("data-page", "view");n.async = !0;n.defer = !0;n.src = "https://cdn.doyouad.com/js/dyadTracker.js?v=" + new Date().toISOString().slice(0, 10).replace(/-/g, "");t = d.getElementsByTagName(s)[0];t.parentNode.insertBefore(n, t);})(window, document, "script");
</script>
<!-- Doyouad End -->

</head>

<body>
	<div class="content">
		<%-- 메인배너 --%>
		<div class="main-banner">
		<c:if test="${!empty mainBannerList}">
			<div id="mainBanner" class="swiper-container">
				<div class="swiper-wrapper">
					<c:forEach var="list" items="${mainBannerList}" varStatus="idx">
					<c:if test="${list.deviceGubun eq 'M' or list.deviceGubun eq 'A'}">
					<div class="swiper-slide">
						<c:choose>
							<c:when test="${list.moLinkYn eq 'N'}">
							<a href="javascript:">
							</c:when>
							<c:when test="${list.moLinkYn eq 'Y'}">
							<a href="${list.moLinkUrl}" <c:if test="${list.moLinkFlag eq '_BLANK'}">target="_BLANK"</c:if>>
							</c:when>				
						</c:choose>
							<img src="${IMGPATH}/banner/${list.bannerIdx}/${list.bannerImg}" alt="배너이미지"/>
						</a>
					</div>
					</c:if>
					</c:forEach>
				</div>
			  	<div class="swiper-pagination"></div>
			</div>
		</c:if>
		</div>
		<%-- //메인배너 --%>
		<%-- 카테고리 --%>
		<div class="main-category">
			<ul>
				<li class="cate-item">
					<a href="${CTX}/product/productList.do?schCateIdx=96&schCateFlag=ALL" class="cate-link">
						<div class="cate-ico"><img src="${CTX}/images/${DEVICE}/main/ico_lucido_odorcontrol.png" alt="" /></div>
						<p class="cate-name">냄새케어</p>
					</a>
				</li>		
				<li class="cate-item">
					<a href="${CTX}/product/productList.do?schCateIdx=100&schCateFlag=ALL" class="cate-link">
						<div class="cate-ico"><img src="${CTX}/images/${DEVICE}/main/ico_lucido_hairstylecare.png" alt="" /></div>
						<p class="cate-name">헤어스타일케어</p>
					</a>
				</li>						
				<li class="cate-item">
					<a href="${CTX}/product/productList.do?schCateIdx=104&schCateFlag=ALL" class="cate-link">
						<div class="cate-ico"><img src="${CTX}/images/${DEVICE}/main/ico_lucido_facialcare.png" alt="" /></div>
						<p class="cate-name">훼이셜케어</p>
					</a>
				</li>
			</ul>
		</div>
		<%-- //카테고리 --%>
		

		
		<%-- s: 해더 롤링배너 : 모바일은 swiper코딩이 없어 일단 top 1 만 노출 --%>
		<c:if test="${!empty headerRollingBannerList}">		
		<div id="mainBanner3" class="main-banner3 swiper-container">
			<div class="swiper-wrapper">		
			<c:set var="rbCnt" value="0" />
			<c:forEach var="list" items="${headerRollingBannerList}" varStatus="idx">
				<c:if test="${list.deviceGubun eq 'A' or list.deviceGubun eq 'M'}">																		
					<c:choose>
						<c:when test="${list.moLinkYn eq 'Y'}">
						<div class="swiper-slide"><a href="${list.moLinkUrl}" <c:if test="${list.moLinkFlag eq '_BLANK'}">target="_BLANK"</c:if>><img src="${IMGPATH}/banner/${list.bannerIdx}/${list.moBannerImg}" alt="배너이미지"/></a></div>		
						</c:when>
						<c:otherwise>
						<div class="swiper-slide"><a href="#none"><img src="${IMGPATH}/banner/${list.bannerIdx}/${list.moBannerImg}" alt="배너이미지"/>	</a></div>											
						</c:otherwise>
					</c:choose>
					<c:set var="rbCnt" value="${rbCnt+1}" />
				</c:if>
			</c:forEach>			
			</div>
			<%-- 버튼추가, 배너개수가 하나일경우 미노출 --%>
			<c:if test="${rbCnt gt 1}">
			<div class="swiper-navigation">
				<div class="swiper-button-next"></div>
				<div class="swiper-button-prev"></div>
			</div>
			</c:if>
		</div>
		<script>
			var mainBanner3 = new Swiper('#mainBanner3', {
				autoplay: {
					delay: 3500	
				},
				loop: true,
				a11y: true,
				navigation: {
					nextEl: '.swiper-button-next',
					prevEl: '.swiper-button-prev'
				}
			});
		</script>
		</c:if>
		<%-- e: 해더 롤링배너 --%>
		
		<%-- NOW SALE --%>
		<div class="main-nowsale">
		<c:if test="${!empty saleList}">
			<h2 class="nowsale-title">NOW SALE</h2>
			<div class="product-items nobor">
				<ul class="type-default">
					<c:forEach var="list" items="${saleList}" varStatus="idx" end="3">
					<li <c:if test="${list.soldoutYn eq 'Y'}">class="soldout"</c:if>>
						<div class="item">
							<div class="item-wrap">
								<a href="${CTX}/product/productView.do?goodsCd=${list.goodsCd}&choiceCateIdx=${list.choiceCateIdx}" class="item-anchor">
									<span class="badge-box">
										<c:if test="${list.opoYn eq 'Y' }" ><span class="badge type1">1+1</span></c:if>
										<c:if test="${list.tpoYn eq 'Y' }" ><span class="badge type2">2+1</span></c:if>
										<c:if test="${list.newYn eq 'Y' }" ><span class="badge type3">NEW</span></c:if>
										<c:if test="${list.bestYn eq 'Y' }" ><span class="badge type4">BEST</span></c:if>
										<c:if test="${list.saleiconYn eq 'Y' }" ><span class="badge type5">SALE</span></c:if>
										<c:if test="${list.pointiconYn eq 'Y' }" ><span class="badge type3"><i>POINT</i> X2</span></c:if>
									</span>
									<div class="item-thumb">
										<c:set var="imgSplit" value="${fn:split(list.mainFile ,'.') }"/>
										<img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_M.${imgSplit[1]}">
									</div>
									<p class="item-summary">${list.shortInfo}</p>
									<p class="item-name">${list.goodsTitle}</p>
									<div class="item-price">
										<fmt:formatNumber var="discountRate" value="${list.discountRate}" type="number" maxFractionDigits="0"/>
										<del class="origin"><c:if test="${discountRate != 0}" ><fmt:formatNumber value="${list.price}" /></c:if></del>
										<c:if test="${discountRate != 0}" >
											<span class="discount"><em>${discountRate}</em>%</span>
										</c:if>
										<span class="price"><fmt:formatNumber value="${list.discountPrice}" /></span>
									</div>
								</a>
								<a href="javascript:" id="cartBtn" class="btn-cart" onclick="goCart('${list.goodsIdx}','<c:out value="${list.goodsCd}"/>','<c:out value="${list.goodsTitle}"/>','<fmt:formatNumber value="${list.discountPrice}" groupingUsed="false"/>');"><span class="hide">장바구니</span></a>
							</div>
							<c:if test="${list.autoCouponYn eq 'Y' || list.onlineYn eq 'Y'}">
							<div class="item-etc">
								<c:if test="${list.autoCouponYn eq 'Y'}">
									<img src="${CTX}/images/${DEVICE}/common/ico_coupon_recommend.png" class="coupon" alt="COUPON" />
								</c:if>
								<c:if test="${list.onlineYn eq 'Y'}">
									<img src="${CTX}/images/${DEVICE}/common/ico_coupon_mobile.png" class="coupon" alt="MOBILE ONLY" />
								</c:if>
							</div>
							</c:if>
						</div>
					</li>
					</c:forEach>
				</ul>
			</div>
		</c:if>
		</div>
		<%-- //NOW SALE --%>
		
		<%-- HTML 컨텐츠 --%>
		<c:if test="${!empty mainHtml}">
			<c:if test="${!empty mainHtml.mHtml}">
				<div class="main-banner2">
					<c:out value="${mainHtml.mHtml}" escapeXml="false"/>
				</div>
			</c:if>
		</c:if>
		<%-- //HTML 컨텐츠 --%>
		
		<%-- 리뷰 --%>
		<div class="main-review">
		<c:if test="${!empty reviewList}">
			<h2 class="review-title">REVIEW</h2>
			<ul class="review-list">
				<c:forEach var="list" items="${reviewList}" varStatus="idx" end="1">
				<li>
					<%-- <a href="${CTX}/mypage/review/reviewDetail.do?reviewIdx=${list.reviewIdx}&layerType=review" class="item"> --%>
					<a href="${CTX}/product/productView.do?goodsCd=${list.goodsCd}#reviewTab" class="item"> 
						<div class="item-title">
							<c:choose>
								<c:when test="${list.img1 ne null}">
									<c:set var="imgSplit" value="${fn:split(list.img1 ,'.')}"/>
									<img src="${IMGPATH}/review/${list.reviewIdx}/${imgSplit[0]}_T420.${imgSplit[1]}" onerror="this.src='${CTX}/images/${DEVICE}/noimage.jpg'" class="image" alt="리뷰 이미지">
									<img src="${CTX}/images/${DEVICE}/main/img_review_blank.png" alt="" />
								</c:when>
								<c:otherwise>
									<c:set var="imgSplit" value="${fn:split(list.mainFile ,'.')}"/>
									<img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_M.${imgSplit[1]}" onerror="this.src='${CTX}/images/${DEVICE}/noimage.jpg'" class="image" alt="제품 이미지">
									<img src="${CTX}/images/${DEVICE}/main/img_review_blank.png" alt="" />
								</c:otherwise>
							</c:choose>
						</div>
						<div class="item-info">
							<div class="info-title">
								<c:choose>
									<c:when test="${list.brandIdx eq 1}">
										<span class="ico-brand ico-gatsby"><span class="hide">Gatsby</span></span>
									</c:when>
									<c:when test="${list.brandIdx eq 3}">
										<span class="ico-brand ico-bifesta"><span class="hide">Bifesta</span></span>
									</c:when>
									<c:when test="${list.brandIdx eq 4}">
										<span class="ico-brand ico-lucidol"><span class="hide">Lucido-l</span></span>
									</c:when>
									<c:when test="${list.brandIdx eq 6}">
										<span class="ico-brand ico-mamabutter"><span class="hide">MamaButter</span></span>
									</c:when>
									<c:when test="${list.brandIdx eq 9}">
										<span class="ico-brand ico-barrier"><span class="hide">Barrier Repair</span></span>
									</c:when>
									<c:when test="${list.brandIdx eq 10}">
										<span class="ico-brand ico-gpcreate"><span class="hide">G.P.CREATE</span></span>
									</c:when>
									<c:when test="${list.brandIdx eq 13}">
										<span class="ico-brand ico-lucido"><span class="hide">Lucido</span></span>
									</c:when>
								</c:choose>
								<strong>${list.goodsNm}</strong>
							</div>		
							<p class="info-summary">
								${list.reviewDesc}
							</p>
							<div class="info-author">
								<span class="rate-star" data-rate="${list.rating}"><i></i></span>
								<span class="name">${list.memberNm}</span>
							</div>
						</div>
					</a>
				</li>
				</c:forEach>
			</ul>
		</c:if>
		</div>
		<%-- //리뷰 --%>
		
		<%-- 베스트 --%>
		<div class="main-bestprod">
		<c:if test="${!empty bestList}">
			<h2 class="title"><a href="${CTX}/product/bestProductList.do?gnbBrand=gatsby&brandIdx=1">Mandom<br/>Best Product</a></h2>
			<div class="bestprod-wrapper">
				<div id="mainBest" class="swiper-container">
					<div class="swiper-wrapper">
						<c:forEach var="list" items="${bestList}" varStatus="idx">
						<div class="swiper-slide">
							<a href="${CTX}/product/productView.do?goodsCd=${list.goodsCd}&choiceCateIdx=${list.choiceCateIdx}" class="prod-anchor">
								<div class="prod-image">
									<c:set var="imgSplit" value="${fn:split(list.imgFile2 ,'.') }"/>
									<img src="${IMGPATH}/goods/${list.gubunIdx}/${imgSplit[0]}_M.${imgSplit[1]}"/>
								</div>
								<p class="prod-name">${list.goodsNm}</p>
								<div class="prod-price">
									<fmt:formatNumber  var="discountRate" value="${list.discountRate}" type="number" maxFractionDigits="0"/>
									<c:if test="${discountRate != 0}">
										<del class="price-origin"><fmt:formatNumber value="${list.price}" /></del>
										<span class="price-rate">${discountRate}<span>%</span></span>
									</c:if>
									<span class="price-sale"><fmt:formatNumber value="${list.discountPrice}" /></span>
								</div>
							</a>
						</div>
						</c:forEach>
					</div>
					<div class="swiper-navigation">
						<div class="swiper-button-next"></div>
						<div class="swiper-button-prev"></div>
					</div>
				</div>
			</div>
		</c:if>
		</div>
		<%-- //베스트 --%>
		
	</div>
	
<!-- 팝업 -->
<c:forEach var="list" items="${popupList}" varStatus="idx" end="0">
	<c:if test="${list.gatsbyYn eq '1'}">
		<c:set var="popWidth" value="${list.popWidth}"/> <%-- 모바일은 테두리 +px 하지 않음 --%>
		<c:choose>
			<c:when test="${list.popupIdx eq '75'}"> <%-- DEV:46 , REAL:75 --%>
				<div class="popup type-notice" data-remodal-mode="multiple" data-remodal-id="popNotice">
			</c:when>
			<c:otherwise>
				<div class="popup type-notice" data-remodal-mode="multiple" data-remodal-id="popNotice" style="max-width: ${popWidth}px !important; height: ${list.popHeight}px !important;">
			</c:otherwise>
		</c:choose>
			<div class="pop-mid">
				<c:if test="${list.textFlag eq 'L'}">
					<a href="<c:if test='${!empty list.moLinkUrl}'>${list.moLinkUrl}</c:if><c:if test='${empty list.moLinkUrl}'>javascript:</c:if>" <c:if test="${list.moLinkFlag eq '_BLANK'}">target="_BLANK"</c:if>>
						<img src="${IMGPATH}/popup/${list.popupIdx}/${list.moPopImg}" alt="${list.popTitle}"/>
					</a>
				</c:if>
				<c:if test="${list.textFlag eq 'H'}">
					${list.moPopDesc}
				</c:if>
			</div>
			<div class="pop-close">
				<div class="form-custom">
					<span class="checkbox">
				        <input type="checkbox" name="mainPop" id="mainPop${list.popupIdx}" class="check" data-idx="${list.popupIdx}" data-device="${list.deviceGubun}" onclick="mainClosePop('${list.popupIdx}');">
				        <label for="mainPop${list.popupIdx}" class="lbl">오늘 하루 안보기</label>
				    </span>
				</div>
				<a href="javascript:void(0);" class="btn-close" data-remodal-action="close">닫기</a>
			</div>
		</div>
	</c:if>
</c:forEach>
<!-- //팝업 -->

</body>
</html>