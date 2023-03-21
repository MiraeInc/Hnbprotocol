<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<c:choose>
	<c:when test="${detail.exhibitionIdx eq 79}">
		<c:set var="gnbActive" value="outlet" scope="request"/>
	</c:when>
	<c:otherwise>
		<c:set var="gnbActive" value="exhibition" scope="request"/>
	</c:otherwise>
</c:choose>
<meta name="decorator" content="mobile.main"/>	
<script>

$(window).on('load', function(){
	$('.product-items').productList();
});

// 목록
function goList(){
	var frm = document.exhibitionForm;
	frm.action = "${CTX}/event/exhibition/exhibitionList.do";
	frm.submit();
}

// 다른 기획전 이동
function changeExhibition(){
	$("#exhibitionIdx").val($("#exhibitionSel").val());
	
	if($("#exhibitionSel").val() != ""){
		var frm = document.exhibitionForm;
		frm.action = "${CTX}/event/exhibition/exhibitionView.do";
		frm.submit();
	}
}

// 장바구니 담기
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

// 해시태그검색
function hashtagSearchClick(c) {
	 var hairstyle1 = "" ;
	 var hairstyle2 = "" ;
	 var hairstyle3 = "" ;
	 var hairstyle4 = "" ;
	 var glossy1 = "";
	 var glossy2 = "";
	 var glossy3 = "";
	 var strong1 = "";
	 var strong2 = "";
	 var strong3 = "";
	 var hashtag = "";

	 hashtag = $(c).attr("data-hashtag");
	 
	 if (hashtag != "") {
		 hashtag = "#"+hashtag;
		 
		$("#searchFrm input:hidden[name='hairstyle10']").val(hairstyle1);
		$("#searchFrm input:hidden[name='hairstyle20']").val(hairstyle2);
		$("#searchFrm input:hidden[name='hairstyle30']").val(hairstyle3);
		$("#searchFrm input:hidden[name='hairstyle40']").val(hairstyle4);
		$("#searchFrm input:hidden[name='strong1']").val(strong1);	
		$("#searchFrm input:hidden[name='strong2']").val(strong2);
		$("#searchFrm input:hidden[name='strong3']").val(strong3);
		$("#searchFrm input:hidden[name='glossy1']").val(glossy1);	
		$("#searchFrm input:hidden[name='glossy2']").val(glossy2);
		$("#searchFrm input:hidden[name='glossy3']").val(glossy3);
		$("#searchFrm input:hidden[name='hashtag']").val("");	
		$("#searchFrm input:hidden[name='keyword']").val(hashtag);
		$("#searchFrm input:hidden[name='pageNo']").val("1");
		$("#searchFrm input:hidden[name='exhPageNo']").val("1");
		
		$("form[name='searchFrm']").attr("action", "${CTX}/etc/searchResult.do");
		$("form[name='searchFrm']").submit();
	 }
}

</script>
</head>
<body>
<form name="searchFrm" id="searchFrm">
	<input type="hidden" name="hairstyle10" id="hairstyle10" value="">
	<input type="hidden" name="hairstyle20" id="hairstyle20" value="">
	<input type="hidden" name="hairstyle30" id="hairstyle30" value="">
	<input type="hidden" name="hairstyle40" id="hairstyle40" value="">
	<input type="hidden" name="strong1" id="strong1" value="">
	<input type="hidden" name="strong2" id="strong2" value="">
	<input type="hidden" name="strong3" id="strong3" value="">
	<input type="hidden" name="glossy1" id="glossy1" value="">
	<input type="hidden" name="glossy2" id="glossy2" value="">
	<input type="hidden" name="glossy3" id="glossy3" value="">
	<input type="hidden" name="hashtag" id="hashtag" value="">
	<input type="hidden" name="keyword" id="keyword" value="">
	<input type="hidden" name="pageNo" id="pageNo" value="1">
	<input type="hidden" name="pageBlock" id="pageBlock" value="4">
</form>
<form name="exhibitionForm" id="exhibitionForm" method="get" onsubmit="return false;">
	<input type="hidden" name="exhibitionIdx" id="exhibitionIdx" value="${detail.exhibitionIdx}"/>
	
	<div class="content comm-event evt-special-view">
		<page:applyDecorator  name="mobile.eventmenu" encoding="UTF-8"/>

        <div class="page-body">
			<div class="box-search">
				<div class="form-control">
					<div class="opt_select">
						<select name="exhibitionSel" id="exhibitionSel" onchange="changeExhibition();">
							<option value="">다른 기획전 보기</option>
							<c:forEach var="list" items="${exhibitionList}" varStatus="idx">
								<c:if test="${(detail.exhibitionIdx ne list.exhibitionIdx) and list.device ne 'P'}">
									<option value="${list.exhibitionIdx}">${list.title} (${list.startDt} ~ ${list.endDt})</option> <%--  <c:if test="${list.exhibitionIdx eq detail.exhibitionIdx}">selected</c:if> --%>
								</c:if>
							</c:forEach>
                         </select>
					</div>
				</div>
			</div>
	
			<div class="event-view">
				
				<%--
				<div class="event-tag">
					<ul>
						<c:forEach var="hashtagList" items="${hashtagList}" varStatus="idx">
							<li data-hashtag="${hashtagList.hashtagNm}" onclick="hashtagSearchClick(this);"><p><span>#${hashtagList.hashtagNm}</span><p></li>
						</c:forEach>
					</ul>
				</div>
				 --%>
	
				<div class="event-date">
					<p>기획전 기간 <span>${detail.startDt} - ${detail.endDt}</span></p>
				</div>
	
				<div class="event-img">
					<c:out value="${detail.mExhibitionDesc}" escapeXml="false"/>
				</div>
				
				<div class="outlet-content">
					<%-- Type A --%>
					<c:if test="${detail.displayType eq 'A'}">
					<div class="outlet-container">
						<div class="product-items nobor">
							<ul class="type-default">
								<c:forEach var="goodsList" items="${goodsList}" varStatus="idx3">
								<li <c:if test="${goodsList.soldoutYn eq 'Y'}">class="soldout"</c:if>>
									<div class="item">
										<div class="item-wrap">
											<a href="${CTX}/product/productView.do?goodsCd=${goodsList.goodsCd}&srcPath=exhibition&srcInfo=${detail.exhibitionIdx}" class="item-anchor">
												<div class="badge-box">
													<c:if test="${goodsList.opoYn eq 'Y' }" ><span class="badge type1">1+1</span></c:if>
													<c:if test="${goodsList.tpoYn eq 'Y' }" ><span class="badge type2">2+1</span></c:if>
													<c:if test="${goodsList.newYn eq 'Y' }" ><span class="badge type3">NEW</span></c:if>
													<c:if test="${goodsList.bestYn eq 'Y' }" ><span class="badge type4">BEST</span></c:if>
													<c:if test="${goodsList.saleiconYn eq 'Y' }" ><span class="badge type5">SALE</span></c:if>
													<c:if test="${goodsList.pointiconYn eq 'Y' }" ><span class="badge type3">POINT <em>x2</em></span></c:if>
												</div>
												<div class="item-thumb">
													<c:set var="imgSplit" value="${fn:split(goodsList.mainFile ,'.') }"/>
			                                    	<img src="${IMGPATH}/goods/${goodsList.goodsIdx}/${imgSplit[0]}_M.${imgSplit[1]}">
												</div>
												<p class="item-summary">${goodsList.shortInfo}</p>
												<p class="item-name">${goodsList.goodsTitle}</p>
												<fmt:formatNumber  var="discountRate" value="${goodsList.discountRate}" type="number" maxFractionDigits="0"/>
												<p class="item-price">
													<del class="origin"><c:if test="${discountRate != 0}" ><fmt:formatNumber value="${goodsList.price}"/></c:if></del>
													<c:if test="${discountRate != 0}" >
														<span class="discount"><em>${discountRate}</em>%</span>
													</c:if>
					                               	<span class="price"><em><fmt:formatNumber value="${goodsList.discountPrice}"/></em>원</span>
												</p>
											</a>
											<a href="javascript:" id="cartBtn" class="btn-cart" onclick="goCart('${goodsList.goodsIdx}','<c:out value="${goodsList.goodsCd}"/>','<c:out value="${goodsList.goodsTitle}"/>','<fmt:formatNumber value="${goodsList.discountPrice}" groupingUsed="false"/>');"><span class="hide">장바구니</span></a>
										</div>
										<c:if test="${goodsList.autoCouponYn eq 'Y' || goodsList.onlineYn eq 'Y'}">
										<div class="item-etc">
											<c:if test="${goodsList.autoCouponYn eq 'Y'}">
												<img src="${CTX}/images/${DEVICE}/common/ico_coupon_recommend.png" class="coupon" alt="COUPON" />
											</c:if>
											<c:if test="${goodsList.onlineYn eq 'Y'}">
												<img src="${CTX}/images/${DEVICE}/common/ico_coupon_mobile.png" class="coupon" alt="MOBILE ONLY" />
											</c:if>
										</div>
										</c:if>
									</div>
								</li>
								</c:forEach>
							</ul>
						</div>
					</div>
					</c:if>
					
					<%-- Type B --%>
					<c:if test="${detail.displayType eq 'B'}">
					<c:forEach var="list" items="${groupList}" varStatus="idx">
					<div class="outlet-header">
						<h3 class="title">${list.groupTitle}</h3>
						<button type="button" class="btn-toggle active" data-toggle="collapse" data-target="#event-item-${list.groupIdx}"><span class="hide">접기</span></button>
					</div>
					<div id="event-item-${list.groupIdx}" class="outlet-container">
						<div class="product-items nobor">
							<ul class="type-default">
								<c:forEach var="goodsList" items="${goodsList}" varStatus="idx3">
								<c:if test="${list.groupIdx eq goodsList.groupIdx}">
								<li <c:if test="${goodsList.soldoutYn eq 'Y'}">class="soldout"</c:if>>
									<div class="item">
										<div class="item-wrap">
											<a href="${CTX}/product/productView.do?goodsCd=${goodsList.goodsCd}&srcPath=exhibition&srcInfo=${detail.exhibitionIdx}" class="item-anchor">
												<div class="badge-box">
													<c:if test="${goodsList.opoYn eq 'Y' }" ><span class="badge type1">1+1</span></c:if>
													<c:if test="${goodsList.tpoYn eq 'Y' }" ><span class="badge type2">2+1</span></c:if>
													<c:if test="${goodsList.newYn eq 'Y' }" ><span class="badge type3">NEW</span></c:if>
													<c:if test="${goodsList.bestYn eq 'Y' }" ><span class="badge type4">BEST</span></c:if>
													<c:if test="${goodsList.saleiconYn eq 'Y' }" ><span class="badge type5">SALE</span></c:if>
													<c:if test="${goodsList.pointiconYn eq 'Y' }" ><span class="badge type3">POINT <em>x2</em></span></c:if>
												</div>
												<div class="item-thumb">
													<c:set var="imgSplit" value="${fn:split(goodsList.mainFile ,'.') }"/>
		                                        	<img src="${IMGPATH}/goods/${goodsList.goodsIdx}/${imgSplit[0]}_M.${imgSplit[1]}">
												</div>
												<p class="item-summary">${goodsList.shortInfo}</p>
												<p class="item-name">${goodsList.goodsTitle}</p>
												<fmt:formatNumber  var="discountRate" value="${goodsList.discountRate}" type="number" maxFractionDigits="0"/>
												<div class="item-price">
													<del class="origin"><c:if test="${discountRate != 0}" ><fmt:formatNumber value="${goodsList.price}"/></c:if></del>
													<c:if test="${discountRate != 0}" >
														<span class="discount"><em>${discountRate}</em>%</span>
													</c:if>
													<span class="price"><fmt:formatNumber value="${goodsList.discountPrice}"/></span>
												</div>
											</a>
											<a href="javascript:" id="cartBtn" class="btn-cart" onclick="goCart('${goodsList.goodsIdx}','<c:out value="${goodsList.goodsCd}"/>','<c:out value="${goodsList.goodsTitle}"/>','<fmt:formatNumber value="${goodsList.discountPrice}" groupingUsed="false"/>');"><span class="hide">장바구니</span></a>
										</div>
										<c:if test="${goodsList.autoCouponYn eq 'Y' || goodsList.onlineYn eq 'Y'}">
										<div class="item-etc">
											<c:if test="${goodsList.autoCouponYn eq 'Y'}">
												<img src="${CTX}/images/${DEVICE}/common/ico_coupon_recommend.png" class="coupon" alt="COUPON" />
											</c:if>
											<c:if test="${goodsList.onlineYn eq 'Y'}">
												<img src="${CTX}/images/${DEVICE}/common/ico_coupon_mobile.png" class="coupon" alt="MOBILE ONLY" />
											</c:if>
										</div>
										</c:if>
									</div>
								</li>
								</c:if>
								</c:forEach>
							</ul>
						</div>
					</div>
					</c:forEach>
					</c:if>
				</div>
				
				<div class="outlet-bottom">
					<a href="${CTX}/event/exhibition/exhibitionList.do" class="btn"><span class="txt">목록</span></a>
				</div>
				
				<%-- <div class="outlet-nodata">
						<img src="${CTX}/images/${DEVICE}/common/ico_outlet_nodata.png" class="icon" alt="" />
					<p class="txt1">아울렛 준비 중 입니다.</p>
					<p class="txt2">현재 아울렛 준비 중 입니다, 알찬 아울렛으로 찾아 뵙겠습니다.</p>
				</div> --%>
			</div>
		</div>
	</div>
</form>

</body>
</html>