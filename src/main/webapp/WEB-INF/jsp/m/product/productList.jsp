<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<c:set var="layout_type" value="${gnbBrand}" scope="request" />
<meta name="decorator" content="mobile.main"/>	

<script>
$(window).on('load', function(){
	$('.product-items').productList();
	$('.product-tabs').productTabs();
});

	//검색
	function goSearch(){
		var frm = document.productForm;
		frm.action="${CTX}/product/productList.do";
		frm.submit();
	}
	
	// 페이지 이동
	function goPage(page){
		$("#pageNo").val(page);
		var frm = document.productForm;
		frm.action = "${CTX}/product/productList.do";
		frm.submit();
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

</script>
</head>
<body>
	<form name="productForm" id="productForm" method="get">
		<input type="hidden" name="schGbn" id="schGbn" value="${SCHVO.schGbn}"/>
		<input type="hidden" name="schCateIdx" id="schCateIdx" value="${SCHVO.schCateIdx}" >
		<input type="hidden" name="sch2depthCateIdx" id="sch2depthCateIdx" value="${SCHVO.sch2depthCateIdx}" >
		<input type="hidden" name="sch3depthCateIdx" id="sch3depthCateIdx" value="${SCHVO.sch3depthCateIdx}" >
		<input type="hidden" name="schCateFlag" id="schCateFlag" value="${SCHVO.schCateFlag}" >
		<input type="hidden" name="pageNo" id="pageNo" value="${SCHVO.pageNo}"/>
		<input type="hidden" name="setFlag" id="setFlag" value="${VO.setFlag}"/>
		
		<div class="content comm-product">
			<div class="breadcrumb">
				<c:set var="navi" value="${fn:split(productNavi.cateNavi ,'>')}"/>
	   			<ul>
	   				<li>																															<!-- 1뎁스 (HOME) -->
						<a href="${CTX}/${gnbBrand}/main.do"><span>H</span></a>
					</li>
	           		<li>																															<!-- 2뎁스  -->
	           			<a href="javascript:" class="cate-link">${fn:trim(navi[0])}</a>
	   					<div class="cate-list">
	               			<ul>
		               			<c:forEach var="list" items="${cate1DepthList}">
							    	<c:if test="${list.cateNm ne fn:trim(navi[0]) }">
							    	<li><a href="${CTX}/product/productList.do?schGbn=${SCHVO.schGbn}&schCateIdx=${list.cateIdx}">${list.cateNm}</a></li>
							    	</c:if>
							</c:forEach>
							<li><a href="${CTX}/event/exhibition/exhibitionView.do?exhibitionIdx=79">아울렛</a></li>
							<li><a href="${CTX}/product/bestProductList.do?gnbBrand=${gnbBrand}">BEST</a></li>
							<li><a href="${CTX}/event/event/eventList.do">이벤트</a></li>
							<li><a href="${CTX}/style/tipList.do">TIPS</a></li>
							<li><a href="${CTX}/brand/brandAboutMandom.do">브랜드</a></li>
	                   		</ul>
	                 	</div>
	               	</li>
	               	<li>
	               	
               			<c:choose>
							<c:when test="${empty fn:trim(navi[1]) }" >
								<a href="${CTX}/product/productList.do?schGbn=${SCHVO.schGbn}&schCateIdx=${SCHVO.schCateIdx}" <c:if test="${fn:length(cate2DepthList) > 0 }">	class="cate-link"</c:if> >전체</a>
							</c:when>
							<c:otherwise>
										<c:choose>
											<c:when test="${!empty SCHVO.sch3depthCateIdx}" >
												<a href="${CTX}/product/productList.do?schGbn=${SCHVO.schGbn}&schCateIdx=${cateIdx2}&sch2depthCateIdx=${cateIdx}&sch3depthCateIdx=0" <c:if test="${fn:length(cate2DepthList) > 0 }">	class="cate-link"</c:if> >${fn:trim(navi[1])}</a>
											</c:when>
											<c:otherwise>
												<a href="${CTX}/product/productList.do?schGbn=${SCHVO.schGbn}&schCateIdx=${SCHVO.schCateIdx}&sch2depthCateIdx=${SCHVO.sch2depthCateIdx}" <c:if test="${fn:length(cate2DepthList) > 0 }">	class="cate-link"</c:if> >${fn:trim(navi[1])}</a>
											</c:otherwise>
										</c:choose>
							</c:otherwise>
						</c:choose>
							
	               		<c:if test="${fn:length(cate2DepthList) > 0 }">					
	               		<div class="cate-list">
	               			<ul>																																						
									<li><a href="${CTX}/product/productList.do?schGbn=${SCHVO.schGbn}&schCateIdx=${SCHVO.sch2depthCateIdx}">전체</a></li>
								<c:forEach var="list" items="${cate2DepthList}">
								<c:if test="${list.cateNm ne fn:trim(navi[1]) }">
									<c:choose>
										<c:when test="${list.depthFlag eq 'Y'}">
											<li><a href="${CTX}/product/productList.do?schGbn=${SCHVO.schGbn}&schCateIdx=${list.cateIdx}&sch2depthCateIdx=${list.upperCateIdx}&sch3depthCateIdx=0">${list.cateNm}</a></li>
										</c:when>
										<c:otherwise>
											<li><a href="${CTX}/product/productList.do?schGbn=${SCHVO.schGbn}&schCateIdx=${list.cateIdx}&sch2depthCateIdx=${list.upperCateIdx}">${list.cateNm}</a></li>
										</c:otherwise>
									</c:choose>
								</c:if>
							</c:forEach>
							</ul>
						</div>
						</c:if>
					</li>
					<!-- 3뎁스 -->
					<c:if test="${fn:length(cate3DepthList) > 0 }">		
					<li>
			              	<c:choose>
							<c:when test="${empty fn:trim(navi[2]) }" >
								<a href="${CTX}/product/productList.do?schGbn=${SCHVO.schGbn}&schCateIdx=${SCHVO.schCateIdx}&sch2depthCateIdx=${SCHVO.sch2depthCateIdx}&sch3depthCateIdx=0" <c:if test="${fn:length(cate3DepthList) > 0 }">	class="cate-link"</c:if> >전체</a>
							</c:when>
							<c:otherwise>
								<a href="${CTX}/product/productList.do?schGbn=${SCHVO.schGbn}&schCateIdx=${SCHVO.schCateIdx}&sch2depthCateIdx=${SCHVO.sch2depthCateIdx}&sch3depthCateIdx=${SCHVO.sch3depthCateIdx}" <c:if test="${fn:length(cate3DepthList) > 0 }">	class="cate-link"</c:if> >${fn:trim(navi[2])}</a>
							</c:otherwise>
						</c:choose>
						
			            <c:if test="${fn:length(cate3DepthList) > 0 }">		
								<div class="cate-list">
	               				<ul>								
									<c:if test="${SCHVO.sch3depthCateIdx ne '0'}" >
										<li><a href="${CTX}/product/productList.do?schGbn=${SCHVO.schGbn}&schCateIdx=${SCHVO.schCateIdx}&sch2depthCateIdx=${SCHVO.sch2depthCateIdx}&sch3depthCateIdx=0">전체</a></li>
									</c:if>
									<c:forEach var="list" items="${cate3DepthList}">
										<c:if test="${list.cateNm ne fn:trim(navi[2]) }">
											<li><a href="${CTX}/product/productList.do?schGbn=${SCHVO.schGbn}&schCateIdx=${list.upperCateIdx}&sch2depthCateIdx=${SCHVO.sch2depthCateIdx}&sch3depthCateIdx=${list.cateIdx}">${list.cateNm}</a></li>
										</c:if>
									</c:forEach>
									</ul>
								</div>
					</c:if>
			         </li>
					</c:if>
			           		
	       		</ul>
		  	</div>
			
		 	<div class="page-visual">
		 		<!-- 상단 이미지 -->
				<c:if test="${mFileImgPath ne ''}">
					<img src="${IMGPATH}/${mFileImgPath}" alt="페이지 상단 비쥬얼 이미지">
				</c:if>
			</div>
			 
			<div class="page-body">
				<!-- 
					[D] 디자인상으로는 3개일경우 4개일 경우가 있었는데 정확한 기준을 모르겠습니다 
					    일단 한 줄에 표현되는 수에 따라 data-size를 정해주세요
				-->
				<c:choose>
					<c:when test="${(empty fn:trim(navi[1])) && fn:length(cate2DepthList) == 0 }" > <!-- 1뎁스만 있을경우 1뎁스 표시, 2뎁스가 있으면 2뎁스목록 표시 -->
						<c:set var="MenuList"  value="${cate1DepthList}"/>
						<c:set var="depth_lvl" value="1"/>
					</c:when>
					<c:otherwise>
						<c:set var="MenuList"  value="${cate2DepthList}"/>
						<c:set var="depth_lvl"  value="2"/>
					</c:otherwise>
				</c:choose>
				
				<c:set var="menu_size"  value="${ fn:length(MenuList)}"/>
				
				<!--  전체메뉴가 추가되면 메뉴사이즈 +1,     -->
				 <c:if test="${depth_lvl == 2 and  (fn:trim(navi[0]) ne '헤어스타일링'  and  fn:trim(navi[0]) ne 'HAIR')}">		
						<c:set var="menu_size" value="${ fn:length(MenuList)+1}"/>
				</c:if>
				
				
				<c:if test="${ menu_size > 0}">
				<c:choose>
					<c:when test="${ menu_size%3 == 0}" > 
						<c:set var="dsize" value="3"/>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${ menu_size <= 3}" > 
								<c:set var="dsize" value="3"/>
							</c:when>
							<c:when test="${ menu_size == 5}" > 
								<c:set var="dsize" value="3"/>
							</c:when>
							<c:otherwise>
								<c:set var="dsize" value="4"/>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				
				
				
				<!--  통합 카테고리이고 헤어이면 무조건 3으로 고정-->
				 <c:if test="${depth_lvl == 2 and  fn:trim(navi[0]) eq 'HAIR'}">		
						<c:set var="dsize" value="3"/>
				</c:if>
				
				<div class="product-tabs" data-size="${ dsize}">
				<ul>
						<c:forEach var="list" items="${MenuList}" varStatus="idx">
						<c:if test="${layout_type ne 'lucido'}">
							<c:if test="${idx.index eq 0 and  (fn:trim(navi[0]) ne '헤어스타일링'  and  fn:trim(navi[0]) ne 'HAIR' )}"> <!--   헤어일경우 첫번째로 나와야 하기 때문에 전체를 넣지 않는다. -->		
								<c:choose>
								<c:when test="${ !empty SCHVO.sch2depthCateIdx }">
									<li class="tab-item <c:if test="${list.cateIdx eq SCHVO.sch2depthCateIdx}">active</c:if>">
									<a href="${CTX}/product/productList.do?schGbn=${SCHVO.schGbn}&schCateIdx=${SCHVO.sch2depthCateIdx}" class="tab-anchor" ><span>전체</span></a>
									</li>
								</c:when>
								<c:otherwise>
									<li class="tab-item <c:if test="${list.upperCateIdx eq SCHVO.schCateIdx}">active</c:if>">
									<a href="${CTX}/product/productList.do?schGbn=${SCHVO.schGbn}&schCateIdx=${SCHVO.schCateIdx}" class="tab-anchor" ><span>전체</span></a>
									</li>
								</c:otherwise>
								</c:choose>
							</c:if>
						</c:if>										
							<li class="tab-item <c:if test="${list.cateIdx eq SCHVO.schCateIdx  or  ((fn:trim(navi[0]) eq '헤어스타일링'  or  fn:trim(navi[0]) eq 'HAIR' ) and (empty SCHVO.sch2depthCateIdx and list.depthFlag eq 'Y' ))}">active</c:if>">
								<c:choose>
									<c:when test="${depth_lvl == 1 }" > <!-- 1뎁스만 있을경우 1뎁스 표시 -->
										<c:choose>
											<c:when test="${list.depthFlag eq 'Y'}" > 
												<a href="${CTX}/product/productList.do?schGbn=${SCHVO.schGbn}&schCateIdx=${list.cateIdx}" class="tab-anchor"><span>${list.cateNm}</span></a>
											</c:when>
											<c:otherwise>
												<a href="${CTX}/product/productList.do?schGbn=${SCHVO.schGbn}&schCateIdx=${list.cateIdx}" class="tab-anchor">${list.cateNm}</a>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${list.depthFlag eq 'Y'}" > 
												<a href="${CTX}/product/productList.do?schGbn=${SCHVO.schGbn}&schCateIdx=${list.cateIdx}&sch2depthCateIdx=${list.upperCateIdx}" class="tab-anchor" ><span>${list.cateNm}</span></a>
											</c:when>
											<c:otherwise>
												<a href="${CTX}/product/productList.do?schGbn=${SCHVO.schGbn}&schCateIdx=${list.cateIdx}&sch2depthCateIdx=${list.upperCateIdx}" class="tab-anchor" >${list.cateNm}</a>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
								
									<c:if test="${list.depthFlag eq 'Y' }">
										<c:if test="${fn:length(cateMenu3DepthList) > 0 }">		
										<ul class="sub-tabs">
											<c:set var="isfirst" value="1" />
											<c:forEach var="list2" items="${cateMenu3DepthList}" varStatus="idx2">
													<c:if test="${isfirst eq '1'}">
													<li <c:if test="${empty SCHVO.sch3depthCateIdx or SCHVO.sch3depthCateIdx eq '0'}">class='active'</c:if>><a href="${CTX}/product/productList.do?schGbn=${SCHVO.schGbn}&schCateIdx=${list.cateIdx}&sch2depthCateIdx=${list.upperCateIdx}" ><span>전체</span></a>
													</c:if>
													<c:if test="${list.cateIdx eq list2.upperCateIdx}">
													<li <c:if test="${SCHVO.sch3depthCateIdx eq list2.cateIdx}">class='active'</c:if>><a href="${CTX}/product/productList.do?schGbn=${SCHVO.schGbn}&schCateIdx=${list.cateIdx}&sch2depthCateIdx=${list.upperCateIdx}&sch3depthCateIdx=${list2.cateIdx}"><span>${list2.cateNm}</span></a></li>
													<c:set var="isfirst" value="2" />
													</c:if>
											</c:forEach>
										</ul>
										</c:if>
									</c:if>
							</li>
						</c:forEach>
				</ul>		
				</div>	
				</c:if>
				
			
			
				<c:choose>
					<c:when test="${empty list}">
						<div class="preparing">
							<div class="inner-box">
			                	<img src="${CTX}/images/${DEVICE}/contents/txt_noproducts.png" alt="no products.">
		                		<p class="big-txt">판매중인 상품이 없습니다.</p>
							</div>
	                	</div>
					</c:when>
					<c:otherwise>
						<div class="product-list">
							<div class="product-header">
								<div class="product-sort">
									<span class="opt_select">
					                	<label for="schOrderGubun" class="hide">상품정렬</label>
					               		<select name="schOrderGubun" id="schOrderGubun" onchange="goSearch();">
					               			<option value="1" <c:if test="${SCHVO.schOrderGubun eq '1' or empty SCHVO.schOrderGubun}">selected</c:if>>판매순</option>
					                 		<option value="2" <c:if test="${SCHVO.schOrderGubun eq '2'}">selected</c:if>>신상품순</option>
					                 		<option value="3" <c:if test="${SCHVO.schOrderGubun eq '3'}">selected</c:if>>높은할인율순</option>
					                 		<option value="4" <c:if test="${SCHVO.schOrderGubun eq '4'}">selected</c:if>>높은가격순</option>
					                 		<option value="5" <c:if test="${SCHVO.schOrderGubun eq '5'}">selected</c:if>>낮은가격순</option>
					                	</select>
					             	</span>
								</div>
								<p class="product-stat">총 <strong><fmt:formatNumber value="${totalCount}"/></strong>개의 상품이 있습니다.</p>
							</div>
	
							<div class="product-items">
								<ul class="type-default">
									<c:forEach var="list" items="${list}" varStatus="idx">
										<li <c:if test="${list.soldoutYn eq 'Y'}">class="soldout"</c:if>>
											<div class="item">
												<div class="item-wrap">
													<a href="${CTX}/product/productView.do?goodsCd=${list.goodsCd}" class="item-anchor">
														<span  class="badge-box">
															<c:if test="${list.opoYn eq 'Y' }" ><span class="badge type1">1+1</span></c:if>
							                        		<c:if test="${list.tpoYn eq 'Y' }" ><span class="badge type2">2+1</span></c:if>
															<c:if test="${list.newYn eq 'Y' }" ><span class="badge type3">NEW</span></c:if>
															<c:if test="${list.bestYn eq 'Y' }" ><span class="badge type4">BEST</span></c:if>
															<c:if test="${list.saleiconYn eq 'Y' }" ><span class="badge type5">SALE</span></c:if>
															<c:if test="${list.pointiconYn eq 'Y' }" ><span class="badge type3">POINT <em>x2</em></span></c:if>
														</span >
														<div class="item-thumb">
							           						<c:set var="imgSplit" value="${fn:split(list.mainFile ,'.') }"/>
															<img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_M.${imgSplit[1]}">
							                     		</div>
															<fmt:formatNumber  var="discountRate" value="${list.discountRate}" type="number" maxFractionDigits="0"/>
															<p class="item-summary">${list.shortInfo}</p>
															<p class="item-name">${list.goodsTitle}</p>
															<div class="item-price">
																<del  class="origin"><c:if test="${discountRate != 0}" ><fmt:formatNumber value="${list.price}"/>원</c:if></del>																
																<c:if test="${discountRate != 0}" >
																	<span class="discount"><em>${discountRate}</em>%</span>
																</c:if>
																<span class="price"><fmt:formatNumber value="${list.discountPrice}"/>원</span>
															</div>
															<%-- <div class="item-btn">
																<c:if test="${list.soldoutYn ne 'Y'}">
									                           		<a href="javascript:" class="link-preview" onclick="buyNow('${list.goodsIdx}');"><i></i>바로구매</a>
										                           	<a id="cartBtn" href="javascript:" class="link-cart" onclick="goCart('${list.goodsIdx}','<c:out value="${list.goodsCd}"/>','<c:out value="${list.goodsTitle}"/>','<fmt:formatNumber value="${list.discountPrice}" groupingUsed="false"/>');"><i></i>장바구니</a>
										                       	</c:if>
															</div> --%>
													</a>
													<a href="javascript:" id="cartBtn" class="btn-cart" onclick="goCart('${list.goodsIdx}','<c:out value="${list.goodsCd}"/>','<c:out value="${list.goodsTitle}"/>','<fmt:formatNumber value="${list.discountPrice}" groupingUsed="false"/>');"><span class="hide">장바구니</span></a>
												</div>
												<c:if test="${list.autoCouponYn eq 'Y' || list.onlineYn eq 'Y'}">
													<div class="item-etc">
						                   				<c:if test="${list.onlineYn eq 'Y'}">
															<img src="${CTX}/images/${DEVICE}/common/ico_coupon_mobile.png" alt="MOBILE ONLY" class="coupon"  />
														</c:if>
														<c:if test="${list.autoCouponYn eq 'Y'}">
															<img src="${CTX}/images/${DEVICE}/common/ico_coupon_recommend.png" alt="COUPON" class="coupon"  />
														</c:if>
													</div>
												</c:if>
											</div>
										</li>
									</c:forEach>
								</ul>
							</div>
							<div class="pagin-nav">
								 <c:out value="${page.pageStr}" escapeXml="false"/>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
			</div>		 
		</div>
	</form>
</body>
</html>