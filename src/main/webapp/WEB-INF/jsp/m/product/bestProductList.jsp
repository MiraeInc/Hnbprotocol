<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<c:set var="gnbActive" value="best" scope="request"/>
<meta name="decorator" content="mobile.main" />	
<script>
	$(window).on('load', function(){
		$('.product-items').productList();
		$('.product-tabs').productTabs();
	})
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
<c:choose>
	<c:when test="${gnbBrand eq 'default'}">
		<c:set var="g_brand_idx" value="1"  />
	</c:when>
	<c:when test="${gnbBrand eq 'gatsby'}">
		<c:set var="g_brand_idx" value="1"  />
	</c:when>
	<c:when test="${gnbBrand eq 'bifesta'}">
		<c:set var="g_brand_idx" value="3" />
	</c:when>
	<c:when test="${gnbBrand eq 'lucidol'}">
		<c:set var="g_brand_idx" value="4"/>
	</c:when>
	<c:when test="${gnbBrand eq 'mamabutter'}">
		<c:set var="g_brand_idx" value="6"/>
	</c:when>
	<c:when test="${gnbBrand eq 'dentalpro'}">
		<c:set var="g_brand_idx" value="7"  />
	</c:when>
	<c:when test="${gnbBrand eq 'charley'}">
		<c:set var="g_brand_idx" value="8" />
	</c:when>
	<c:otherwise>
		<c:set var="g_brand_idx" value="1"  />
	</c:otherwise>
</c:choose>
	<div class="content comm-product">

	    <div class="breadcrumb">
			<ul>
				<li>
					<a href="${CTX}/main.do"><span>Home</span></a>
				</li>
				<li class="current">
					<a href="#none" class="cate-link"><span>베스트</span></a>
					<div class="cate-list">
						<ul>
						<c:forEach var="list1" items="${categoryList}" varStatus="idx1">
						<c:if test="${list1.depthLv eq 1 and list1.brandIdx eq g_brand_idx}">
							<li><a href="${CTX }/product/productList.do?schCateIdx=${list1.cateIdx}&schCateFlag=ALL" >${list1.cateNm}</a></li>
							</c:if>
					</c:forEach>
							<li><a href="${CTX}/event/exhibition/exhibitionView.do?exhibitionIdx=79">아울렛</a></li>
							<li><a href="${CTX}/product/bestProductList.do?gnbBrand=${gnbBrand}">BEST</a></li>
							<li><a href="${CTX}/event/event/eventList.do?brandIdx=${g_brand_idx}&gnbBrand=${gnbBrand}">이벤트</a></li>
							<li><a href="${CTX}/style/tipList.do?brandIdx=${g_brand_idx}&gnbBrand=${gnbBrand}">TIPS</a></li>
							<li><a href="${CTX}/brand/brandAboutMandom.do?brandIdx=${g_brand_idx}&gnbBrand=${gnbBrand}">브랜드</a></li>
							
						</ul>
					</div>
				</li>
			</ul>
		</div>
		
		<div class="page-title type-text">
			<h2 class="title">베스트</h2>
		</div>

	    <div class="page-body">
	        <div class="brand-filter">
				<a href="${CTX}/product/bestProductList.do?brandIdx=1&gnbBrand=${VO.gnbBrand}" <c:if test="${VO.brandIdx eq '1' }">class="active"</c:if> data-brand="gatsby"><span>갸스비</span></a>
				<a href="${CTX}/product/bestProductList.do?brandIdx=3&gnbBrand=${VO.gnbBrand}" <c:if test="${VO.brandIdx eq '3' }">class="active"</c:if> data-brand="bifesta"><span>비페스타</span></a>
				<a href="${CTX}/product/bestProductList.do?brandIdx=4&gnbBrand=${VO.gnbBrand}" <c:if test="${VO.brandIdx eq '4' }">class="active"</c:if> data-brand="lucidol"><span>루시도엘</span></a>
				<a href="${CTX}/product/bestProductList.do?brandIdx=9&gnbBrand=${VO.gnbBrand}" <c:if test="${VO.brandIdx eq '9' }">class="active"</c:if> data-brand="barrier"><span>베리어리페어</span></a>
<%--
				<a href="${CTX}/product/bestProductList.do?brandIdx=6&gnbBrand=${VO.gnbBrand}" <c:if test="${VO.brandIdx eq '6' }">class="active"</c:if> data-brand="mamabutter"><span>마마버터</span></a>
 --%>
				<a href="${CTX}/product/bestProductList.do?brandIdx=7&gnbBrand=${VO.gnbBrand}" <c:if test="${VO.brandIdx eq '7' }">class="active"</c:if> data-brand="dentalpro"><span>덴탈프로</span></a>
<%--
				<a href="${CTX}/product/bestProductList.do?brandIdx=8&gnbBrand=${VO.gnbBrand}" <c:if test="${VO.brandIdx eq '8' }">class="active"</c:if> data-brand="charley"><span>찰리</span></a>
 --%>
			</div>
	        
	        <div class="product-list">
	        	<div class="product-best">
	        		<c:forEach var="list" items="${list}" varStatus="idx">
		        		<c:if test="${idx.count eq 1}">
			        		<div class="item">
								<div class="item-num"><img src="${CTX}/images/${DEVICE}/common/num_best_1.png" alt="" /></div>
								<div class="badge-box">
									<c:if test="${list.opoYn eq 'Y' }" ><span class="badge type1">1+1</span></c:if>
	                        		<c:if test="${list.tpoYn eq 'Y' }" ><span class="badge type2">2+1</span></c:if>
									<c:if test="${list.newYn eq 'Y' }" ><span class="badge type3">NEW</span></c:if>
									<c:if test="${list.bestYn eq 'Y' }" ><span class="badge type4">BEST</span></c:if>
									<c:if test="${list.saleiconYn eq 'Y' }" ><span class="badge type5">SALE</span></c:if>
									<c:if test="${list.pointiconYn eq 'Y' }" ><span class="badge type3">POINT <em>x2</em></span></c:if>
								</div>
								<div class="item-img">
									<c:set var="imgSplit" value="${fn:split(list.mainFile ,'.') }"/>
	                                <a href="${CTX}/product/productView.do?goodsCd=${list.goodsCd}">
	                                	<img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_B.${imgSplit[1]}">
	                                </a>
								</div>
								<div class="item-info">
									<fmt:formatNumber  var="discountRate" value="${list.discountRate}" type="number" maxFractionDigits="0"/>
									<div class="item-desc">
										<a href="${CTX}/product/productView.do?goodsCd=${list.goodsCd}">
											<p class="item-summary">${list.shortInfo}</p>
											<p class="item-name">${list.goodsTitle}</p>
											<p class="item-price">
												<c:if test="${discountRate != 0}" >
													<span class="discount"><em>${discountRate}</em>%</span>
													<span class="origin"><em><fmt:formatNumber value="${list.price}"/></em>원</span>
												</c:if>
												<span class="price"><em><fmt:formatNumber value="${list.discountPrice}"/></em>원</span>
											</p>
										</a>
									</div>
									<c:if test="${list.onlineYn eq 'Y' || list.autoCouponYn eq 'Y'}">
										<div class="item-coupon">
											<c:if test="${list.onlineYn eq 'Y'}">
												<img src="${CTX}/images/${DEVICE}/common/ico_coupon_recommend.png" alt="COUPON" />
											</c:if>
											<c:if test="${list.autoCouponYn eq 'Y'}">
												<img src="${CTX}/images/${DEVICE}/common/ico_coupon_mobile.png" alt="MOBILE ONLY" />
											</c:if>
										</div>
									</c:if>
									<div class="item-btn">
										<c:if test="${list.soldoutYn ne 'Y'}">
											<a href="javascript:" class="link-preview" onclick="buyNow('${list.goodsIdx}');"><i></i>바로구매</a>
											<a id="cartBtn" href="javascript:" class="link-cart" onclick="goCart('${list.goodsIdx}','<c:out value="${list.goodsCd}"/>','<c:out value="${list.goodsTitle}"/>','<fmt:formatNumber value="${list.discountPrice}" groupingUsed="false"/>');"><i></i>장바구니</a>
										</c:if>
									</div>
								</div>
							</div>
						</c:if>
					</c:forEach>
				</div>
				<div class="product-items nobor">
	                <ul class="type-default">
					<c:forEach var="list" items="${list}" varStatus="idx">
					<c:if test="${idx.count > 1}">
						<li <c:if test="${list.soldoutYn eq 'Y'}">class="soldout"</c:if>>
	                        <div class="item">
	                        	<div class="item-wrap">
			                        <a href="${CTX}/product/productView.do?goodsCd=${list.goodsCd}" class="item-anchor">
			                            <div class="item-num"><img src="${CTX}/images/m/common/num_best_${idx.count}.png" alt="" /></div>
			                            <div class="badge-box">
			                            	<c:if test="${list.opoYn eq 'Y' }" ><span class="badge type1">1+1</span></c:if>
			                        		<c:if test="${list.tpoYn eq 'Y' }" ><span class="badge type2">2+1</span></c:if>
											<c:if test="${list.newYn eq 'Y' }" ><span class="badge type3">NEW</span></c:if>
											<c:if test="${list.bestYn eq 'Y' }" ><span class="badge type4">BEST</span></c:if>
											<c:if test="${list.saleiconYn eq 'Y' }" ><span class="badge type5">SALE</span></c:if>
											<c:if test="${list.pointiconYn eq 'Y' }" ><span class="badge type3">POINT <em>x2</em></span></c:if>
			                            </div>
			                            <div class="item-thumb">
		                                	<c:set var="imgSplit" value="${fn:split(list.mainFile ,'.') }"/>
		                                	<img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_M.${imgSplit[1]}">
			                            </div>
			                            <p class="item-summary">${list.shortInfo}</p>
										<p class="item-name">${list.goodsTitle}</p>
										<fmt:formatNumber  var="discountRate" value="${list.discountRate}" type="number" maxFractionDigits="0"/>
										<div class="item-price">
											<del class="origin"><c:if test="${discountRate != 0}" ><fmt:formatNumber value="${list.price}"/></c:if></del>
											<c:if test="${discountRate != 0}" >
												<span class="discount"><em>${discountRate}</em>%</span>
											</c:if>
											<span class="price"><fmt:formatNumber value="${list.discountPrice}"/></span>
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
					</c:if>
				</c:forEach>
					</ul>
				</div>
	        </div>
	    </div>
	</div>	
</body>
</html>