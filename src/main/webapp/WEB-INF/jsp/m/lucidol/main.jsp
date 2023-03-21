<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE">
<html>
<head>
<meta name="decorator" content="mobile.main" />	
<script>

$(window).on('load', function(){

	var mpbTemp = 0;
	
	$('.product-bestpicks').find('.item').each(function(idx){
		var $this = $(this);
		if(mpbTemp < $this.outerHeight()) {
			mpbTemp = $this.outerHeight()
		}
	});
	
	$('.product-bestpicks').find('.item').css('height', mpbTemp);
	
})

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

// 바로구매
function buyNow(goodsIdx){
	var arrOrder = new Array();
	var goodsObj = new Object();
	goodsObj.goodsIdx = Number(goodsIdx);
	goodsObj.goodsCnt = 1;
	arrOrder.push(goodsObj);
	orderNow(arrOrder);
}

var pageNo = Number("${schVO.pageNo}");

// best 리스트 더보기
function listMore(){
	pageNo = pageNo+1;
	$.ajax({
		 url: "${CTX}/ajax/main/bestListMoreAjax.do",
		 data : {	
			 			"pageNo"	:	pageNo,
			 			"schType"	:	"0"
		 		   },  
		 type: "GET",
		 dataType: "html",
		 async: false,
		 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 error: function(request, status, error){ 
		 	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
		 },
		 success: function(responseData){ 
			 $("#bestList").append(responseData);  
			 
			 var lastPageNo = Number("${page.finalPageNo}");

			 if(pageNo >= lastPageNo){
				 $("#MOREBTN").remove();
			 }
		 }
	});  
}

</script>

<!-- PAGE SCRIPT -->
<script>
	$(function(){
		//슬라이드 배너
		$('#mainBanner .slide-list').lightSlider({
			item:1,
			auto: true,
			pager: false,
			loop: true,
			slideMargin: 0,
			pause: 3000,
			onSliderLoad: function(el) {
				bannerPager(el.getCurrentSlideCount, el.getTotalSlideCount)
			},
			onAfterSlide: function(el) {
				bannerPager(el.getCurrentSlideCount, el.getTotalSlideCount)
			}
		});
		
	})

	function bannerPager(current, total){
		var $pager = $('#mainBanner').find('.slide-pager');
		$pager.find('.current').text(current);
		$pager.find('.total').text(total);
	}
	
</script>
</head>

<body>
	<div class="content comm-main">
	
		<%-- 메인배너 --%>
		<c:if test="${!empty mainBannerList}">
			<div id="mainBanner" class="main-banners">
				<ul class="slide-list">
					<c:forEach var="list" items="${mainBannerList}" varStatus="idx">
						<c:if test="${list.deviceGubun eq 'M' or list.deviceGubun eq 'A'}">
						<li>
						<c:choose>
							<c:when test="${list.moLinkYn eq 'N'}">
							<a href="javascript:">
							</c:when>
							<c:when test="${list.moLinkYn eq 'Y'}">
							<a href="${list.moLinkUrl}" <c:if test="${list.moLinkFlag eq '_BLANK'}">target="_BLANK"</c:if>>
							</c:when>				
						</c:choose>
								<img src="${IMGPATH}/banner/${list.bannerIdx}/${list.moBannerImg}" alt="배너이미지"/>
							</a>
						</li>
						</c:if>
					</c:forEach>
				</ul>
				<div class="slide-pager">
					<span class="current"></span>
					<span class="total"></span>
				</div>
			</div>
		</c:if>
		<%-- 메인배너 --%>
		
		<%-- BEST PICK --%>
		<c:if test="${!empty bestList}">
		<div class="main-bestpick">
			<div class="bestpick-title">
				<h3>BEST PICK +</h3>
			</div>
			<div class="product-bestpicks">
				<ul>
					<c:forEach var="list" items="${bestList}" varStatus="idx" begin="0" end="3">
					<li <c:if test="${list.soldoutYn eq 'Y'}">class="soldout"</c:if>>
						<a href="${CTX}/product/productView.do?goodsCd=${list.goodsCd}&choiceCateIdx=${list.choiceCateIdx}">
							<div class="item">
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
									<c:if test="${discountRate != 0}" >
										<span class="discount"><em>${discountRate}</em>%</span>
										<del class="origin"><fmt:formatNumber value="${list.price}" /></del>
									</c:if>
									<span class="price"><fmt:formatNumber value="${list.discountPrice}" /></span>
								</div>
								<div class="item-etc">
									<c:if test="${list.autoCouponYn eq 'Y'}">
										<img src="${CTX}/images/${DEVICE}/common/ico_coupon_recommend.png" class="coupon" alt="COUPON" />
									</c:if>
									<c:if test="${list.onlineYn eq 'Y'}">
										<img src="${CTX}/images/${DEVICE}/common/ico_coupon_mobile.png" class="coupon" alt="MOBILE ONLY" />
									</c:if>
								</div>
							</div>
						</a>
					</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		</c:if>
			
		<%-- 타임세일 --%>
		<c:if test="${!empty timeSale}">		
		<div class="main-timesale">
			<div class="timesale-title">
				<p>TIME SALE</p>
			</div>
			<div class="product-items">
				<div class="item">
					<div class="item-img">
						<a href="${CTX}/event/timeSale/timeSaleList.do">
							<c:set var="imgSplit" value="${fn:split(timeSale.mainFile ,'.')}"/>
							<img src="${IMGPATH}/goods/${timeSale.goodsIdx}/${imgSplit[0]}_M.${imgSplit[1]}" alt="">
						</a>
					</div>
					<div class="item-info">
						<div class="item-desc">
							<a href="#none">
								<p class="item-name">${timeSale.goodsTitle}</p>
								<p class="item-price">
									<fmt:formatNumber  var="discountRate" value="${timeSale.discountRate}" type="number" maxFractionDigits="0"/>
									<span class="discount"><em>${discountRate}</em>%</span>
									<span class="origin"><em>${timeSale.price}</em></span>
									<span class="price"><em><fmt:formatNumber value="${timeSale.discountPrice}" /></em></span>
								</p>
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		</c:if>
		<%-- 타임세일 --%>

		<%-- 중간배너 --%>
		<c:if test="${!empty moMidBanner}">
		<div class="main-banner">
			<c:choose>
				<c:when test="${moMidBanner.moLinkYn eq 'N'}">
					<a href="javascript:">
				</c:when>
				<c:when test="${moMidBanner.moLinkYn eq 'Y'}">
					<a href="${moMidBanner.moLinkUrl}" <c:if test="${moMidBanner.moLinkFlag eq '_BLANK'}">target="_BLANK"</c:if>>
				</c:when>
			</c:choose>
				<img src="${IMGPATH}/banner/${moMidBanner.bannerIdx}/${moMidBanner.moBannerImg}"/>
			</a>	
		</div>
		</c:if>
		<%-- 중간배너 --%>
		
		<%-- 제품소개 --%>
		<c:if test="${!empty sourceBanner}">
			<c:out value="${sourceBanner.mGoodsDesc}" escapeXml="false"/>
		</c:if>
		<%-- 제품소개 --%>
	</div>
	
<script type="text/javascript">
(function(window, document, $){

	//탑배너
	var recmProductSlider = $('.top-banner > ul').lightSlider({
		item:1,
		auto:true,
		loop:true,
		pager:true,
		slideMargin: 0,
		controls: false,
		pauseOnHover:true,
		pause:3000,
		addClass: 'top-banner-list'
	});

})(window, document, jQuery);
</script>
	
<!-- 팝업 -->
<c:forEach var="list" items="${popupList}" varStatus="idx" end="0">
	<c:if test="${list.lucidolYn eq '1'}">
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
				<a href="#none" class="btn-close" data-remodal-action="close">닫기</a>
			</div>
		</div>
	</c:if>
</c:forEach>
<!-- //팝업 -->

</body>
</html>