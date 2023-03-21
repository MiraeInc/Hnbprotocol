<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_shopping" />
<meta name="menu_no" content="mypage_020" />
<script>

// 장바구니 담기 (1개 상품)
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

// 위시 삭제 (하트 : 1개 상품)
function delWish(idx){
	if(confirm("삭제 하시겠습니까?")){
		$("#wishIdx").val(idx);
		
		var frm = document.wishForm;
		frm.action = "${CTX}/mypage/wish/wishDelete.do";
		frm.submit();
	}
}

// 위시 삭제 (체크박스)
function chkDelete(flag){
	if(flag=="ALL"){
		$("input[name=chkList]").prop("checked", true);
	}
	
	var checkRow = "";
	 
	$("input[name='chkList']:checked" ).each(function (){
		checkRow = checkRow + $(this).val()+",";
	});
	checkRow = checkRow.substring(0,checkRow.lastIndexOf(","));
  
	if(checkRow == ""){
	  alert("삭제 할 상품을 선택 해 주세요.");
	  return false;
	}
  
	if(confirm("삭제 하시겠습니까?")){
		$.ajax({
			url: "${CTX}/ajax/mypage/wish/wishDeleteAjax.do",
		 	data: {
						"wishListIdx"	:	checkRow
		 			 },
		 	type: "post",	
		 	async: false,
		 	cache:false,
		 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 	error: function(request, status, error){
				if(request.status == "403"){
					alert("<spring:message code='common.login001'/>"); 
					location.href="${CTX}/login/loginPage.do?refererYn=Y";
				}else{
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);	
				}
			},
			success: function(flag){
				if(Number(flag) > 0){ 
					alert("삭제 되었습니다.");
					document.location.reload();
				}
			}
		}); 
	}
}

// 장바구니 담기 (체크 박스)
function chkAddCart(flag){
	var arr = new Array();
	var arrGtm = new Array();	// Google Tag Manager
	
	if(flag=="ALL"){
		$("input[name=chkList]").prop("checked", true);
	}
	
	var checkProduct = 0;
	 
	$("input[name='chkList']:checked").each(function (index) {	
		var obj = new Object();
		
		var goodsIdx = $(this).attr("data-goodsIdx");
		var goodsCnt = 1;
		
		obj.goodsIdx = goodsIdx;
		obj.goodsCnt = goodsCnt;
		obj.sessionId = getSessionId();
		
		arr.push(obj);
		
		checkProduct+=1;
		
		// Begin Google Tag Manager			
		var goodsCd = $(this).attr("data-goodsCd");
		var goodsNm = $(this).attr("data-goodsNm");
		var goodsPrice = uncomma($(this).attr("data-goodsPrice"));
		
		var objGtm = { 'name': goodsNm,
		        'id': goodsCd,
		        'price': goodsPrice,
		        'brand': 'Gatsby',
		        'category': '',
		        'variant': '',
		        'quantity': goodsCnt
			};
		
		arrGtm.push(objGtm);
		// End Google Tag Manager
	});
	
	if(checkProduct == 0){
	  alert("장바구니에 담을 상품을 선택 해 주세요.");
	  $("input[name=chkList]").prop("checked", false);
	  return false;
	}
	
	// 상품 담은 후 체크박스 초기화
	$("input[name=chkList]").prop("checked", false);
	
	// 장바구니 담기
	addCartObject(arr);
	
	if("<spring:message code='server.status'/>" == "LIVE"){
		// Begin Google Tag Manager
		// Measure adding a product to a shopping cart by using an 'add' actionFieldObject
		// and a list of productFieldObjects.
		dataLayer.push({
		  'event': 'addToCart',
		  'ecommerce': {
		    'currencyCode': 'KRW',
		    'add': {                                // 'add' actionFieldObject measures.
		      'products': arrGtm
		    }
		  }
		});
		// End Google Tag Manager
	}
}

// 체크박스 전체선택/해제
function checkAll(){
     if($("#chkAll").is(':checked')){
      $("input[name=chkList]").prop("checked", true);
    }else{
      $("input[name=chkList]").prop("checked", false);
    } 
}

</script>
</head>
<body>
<form name="wishForm" id="wishForm" method="post" onsubmit="return false;">
	<input type="hidden" name="pageNo" id="pageNo" value="${schVO.pageNo}"/>
	<input type="hidden" name="wishIdx" id="wishIdx" value=""/>
	
	<div class="content comm-mypage mypage-wish">

		<div class="page-body">
			<div class="order-form">
				<div class="order-util">
					<span class="checkbox">
						<input type="checkbox" id="chkAll" class="check" onclick="checkAll();"/>
						<label for="chkAll" class="lbl">고객님께서 좋아요 한 상품 총 <span class="em">${totalCount}</span>개</label>
					</span>
					<c:if test="${fn:length(wishList) > 0}">
					<div class="btns">
						<button type="button" class="btn" onclick="chkDelete('ALL');"><span class="txt">전체 삭제</span></button>
					</div>
					</c:if>
				</div>
        		<c:choose>
        			<c:when test="${fn:length(wishList) > 0}">
        				<div class="form-group">
							<div class="form-body">
								<div class="order-goods">
									<ul>
										<c:forEach var="list" items="${wishList}" varStatus="idx">
										<li>
											<a href="${CTX}/product/productView.do?goodsCd=${list.goodsCd}">
											<div class="item <c:if test="${list.soldoutYn eq 'Y'}">soldout</c:if>">
												<div class="item-check">
													<span class="checkbox">
														<input type="checkbox" name="chkList" id="item-${list.wishIdx}" class="check" value="${list.wishIdx}" data-goodsIdx="${list.goodsIdx}" data-goodsCd="${list.goodsCd}"/>
														<label for="item-${list.wishIdx}" class="lbl"><span class="hide">선택</span></label>
													</span>
												</div>
												<div class="item-view">
													<div class="view-thumb">
														<c:set var="imgSplit" value="${fn:split(list.mainFile ,'.') }"/>
														<img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_M.${imgSplit[1]}">
													</div>
													<div class="view-info">
														<div class="badge-box">
															<c:if test="${list.opoYn eq 'Y' }" ><span class="badge type1">1+1</span></c:if>
															<c:if test="${list.tpoYn eq 'Y' }" ><span class="badge type2">2+1</span></c:if>
															<c:if test="${list.newYn eq 'Y' }" ><span class="badge type3">NEW</span></c:if>
															<c:if test="${list.bestYn eq 'Y' }" ><span class="badge type4">BEST</span></c:if>
															<c:if test="${list.saleiconYn eq 'Y' }" ><span class="badge type5">SALE</span></c:if>
															<c:if test="${list.pointiconYn eq 'Y' }" ><span class="badge type3">POINT <em>X2</em></span></c:if>
														</div>
														<c:choose>
															<c:when test="${list.brandIdx eq 1}"><p class="text-gatsby">${list.brandNm}</p></c:when>		
															<c:when test="${list.brandIdx eq 3}"><p class="text-bifesta">${list.brandNm}</p></c:when>
															<c:when test="${list.brandIdx eq 4}"><p class="text-lucidol">${list.brandNm}</p></c:when>
															<c:when test="${list.brandIdx eq 6}"><p class="text-mama">${list.brandNm}</p></c:when>
															<c:when test="${list.brandIdx eq 7}"><p class="text-dental">${list.brandNm}</p></c:when>
															<c:when test="${list.brandIdx eq 8}"><p class="text-charley">${list.brandNm}</p></c:when>
														</c:choose>															
														<p class="name"><strong>${list.goodsTitle}</strong></p>
														<p class="price-wrap">
														<c:choose>
															<c:when test="${list.discountRate gt 0}">
				                                                <span class="discount"><em><fmt:formatNumber value="${list.discountRate}" groupingUsed="true"/></em>%</span>
				                                                <del class="origin"><em><fmt:formatNumber value="${list.price}" groupingUsed="true"/></em></del>
				                                                <span class="price"><em><fmt:formatNumber value="${list.discountPrice}" groupingUsed="true"/></em></span>
															</c:when>
															<c:otherwise>
				                                                <span class="price"><em><fmt:formatNumber value="${list.discountPrice}" groupingUsed="true"/></em></span>
															</c:otherwise>
														</c:choose>
														</p>
														<div class="coupon">
														<c:if test="${list.onlineYn eq 'Y'}">
															<img src="${CTX}/images/${DEVICE}/common/ico_coupon_mobile.png" alt="ONLINE ONLY" />
														</c:if>
														<c:if test="${list.autoCouponYn eq 'Y'}">
															<img src="${CTX}/images/${DEVICE}/common/ico_coupon_recommend.png" alt="COUPON" />
														</c:if>
														</div>
													</div>
												</div>
												<button type="button" class="btn-wish active" onclick="delWish('${list.wishIdx}')"><span class="hide">좋아요</span></button>
											</div>
											</a>
										</li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
		           		
						<div class="btn-box full">
							<div class="row">
								<div class="col col-6">
									<button type="button" class="btn full" onclick="chkDelete('SEL');"><span class="txt">선택상품 삭제</span></button>
								</div>
								<div class="col col-6">
									<button type="button" class="btn full" onclick="chkAddCart('SEL');"><span class="txt">선택상품 장바구니 담기</span></button>
								</div>
							</div>
							<div class="row">
								<div class="col col-12">
									<button type="button" class="btn black full" onclick="chkAddCart('ALL');"><span class="txt">전체상품 장바구니 담기</span></button>
								</div>
							</div>
						</div>
						
						<div class="pagin-nav">
							<c:out value="${page.pageStr}" escapeXml="false"/>
						</div>
        			</c:when>
        			<c:otherwise>
        				<div class="form-group">
							<div class="form-body">
								<div class="no-contents">
									<p>위시리스트에 담긴 상품이 없습니다.</p>
								</div>
							</div>
						</div>
        			</c:otherwise>
        		</c:choose>
			</div>
		</div>
	</div>
</form>
</body>
</html>