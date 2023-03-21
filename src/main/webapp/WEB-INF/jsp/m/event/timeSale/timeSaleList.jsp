<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main" />
<script>

	// 위시리스트 담기
	function goWish($this,goodsIdx){
		addWishList($this, goodsIdx);
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
	
	// 원클릭 결제 버튼
	function btnOneClickPay(){
		if("${IS_LOGIN}" == "false"){
			if(confirm("<spring:message code='common.util011'/>") == true){
				location.href="${CTX}/login/loginPage.do?refererYn=Y"; 
			 }
		}else{
			//대표 빌키가 있을경우
			if("${billkeyYn}" == "Y"){
				$('[data-remodal-id=pop_oneClick]').remodal().open();
			}
			else {
				$('[data-remodal-id=pop_oneclick_reg]').remodal().open();
			}
		}
	}
	
	// 윈클릭 결제
	function oneClickPay(goodsIdx){
		if($('#billkeypopup_agree_chk').is(":checked")) {
			var arr = new Array();
			var obj = new Object();
				
			obj.goodsIdx = Number(goodsIdx);
			obj.goodsCnt = 1;
			
			arr.push(obj);
			
			$("#commonOrderGoodsInfoListStr").val(JSON.stringify(arr));
			var frm = document.commonOrderForm;
			frm.action=getContextPath()+"/order/GoodsBillkeyOrder.do";
			frm.submit();
		}else {
			alert("구매진행에 동의 하여야 합니다.");
			$('#billkeypopup_agree_chk').focus()
			return false;
		}
	}
	
	// 장바구니 담기
	function goCart(goodsIdx, goodsCd, goodsNm, goodsPrice){
		addCart(goodsIdx, 1, goodsCd, goodsNm, goodsPrice);
	}
</script>
</head>
<body>
	<div class="content comm-event evt-timesale">
		<page:applyDecorator  name="mobile.eventmenu" encoding="UTF-8"/>
		
		 <div class="page-title">
			<h2 class="img-title"><img src="${CTX}/images/${DEVICE}/event/tit_evt_time.png" alt="타임세일" /></h2>
		</div>

		<div class="page-body">
			<div class="timesale-content <c:if test="${detail.maxCnt eq 0 or detail.status eq '2'}">soldout</c:if>">
				<c:if test="${!empty detail}">
					<div class="detail-option">
						<p class="time-date">${detail.timeSaleDate}</p>
						
						<div class="detail-timer">
							<c:set var="timeSale" value="${fn:split(detail.timeSale ,':')}"/>
							<strong class="detail-timer-title">
								<span>TIME SALE</span>
								<span>
									<c:choose>
										<c:when test="${detail.status eq '1' and detail.maxCnt ne '0'}">
											<i>남</i><i>은</i><i>시</i><i>간</i>
										</c:when>
										<c:when test="${detail.status eq '2'}">
											<i>진</i><i>행</i><i>예</i><i>정</i>						
										</c:when>
										<c:when test="${detail.maxCnt eq '0'}">
											<i>조</i><i>기</i><i>품</i><i>절</i>
										</c:when>
									</c:choose>
								</span>
							</strong>
							<c:choose>
								<c:when test="${detail.maxCnt eq '0' }">
									<div class="detail-clock" data-time-hour="00" data-time-min="00" data-time-sec="00"></div>
								</c:when>
								<c:otherwise>
									<div class="detail-clock" data-time-hour="${timeSale[0]}" data-time-min="${timeSale[1]}" data-time-sec="${timeSale[2]}"></div>
								</c:otherwise>
							</c:choose>
						</div>
						
						<div class="detail-title">
							<p class="title-brand">${detail.brandNm}</p>
							<p class="title-small">${detail.shortInfo}</p>
							<p class="title-big">${detail.goodsTitle}</p>
						</div>
	
						<div class="detail-image">
							<div class="detail-thumbs <c:if test="${detail.maxCnt eq '0' }">soldout</c:if>">
								<c:set var="imgSplit" value="${fn:split(detail.mainFile ,'.')}"/>
								<img src="${IMGPATH}/goods/${detail.goodsIdx}/${imgSplit[0]}_B.${imgSplit[1]}" alt="제품 상세이미지">
							</div>
						</div>
	
						<div class="timesale-info">
							<div class="info-detail-con">
								<div class="detail-val-horizon">
									<fmt:formatNumber  var="discountRate" value="${detail.discountRate}" type="number" maxFractionDigits="0"/>
									<div class="circle">${discountRate}<i>%</i></div>
								</div>
								<div class="product-price">
									<del class="item-origin"><fmt:formatNumber value="${detail.price}" /></del>
		                     		<p><fmt:formatNumber value="${detail.discountPrice}" /></p>
								</div>
							</div>
							<p class="quantity">잔여 수량 &nbsp;/&nbsp; 1회 구매제한 수량</p>
							<p class="quantity-number"><strong><fmt:formatNumber value="${detail.maxCnt}" /></strong> 개 &nbsp;/&nbsp; <strong><fmt:formatNumber value="${detail.limitCnt}" /></strong> 개</p>
							<p class="quantity-info">※ 해당 금액은 타임세일 판매 가격으로, 타임세일이 종료 되면 기존 판매가로 판매됩니다.</p>
						</div>
	
						<div class="detail-btns">
							<div class="row">
				            	<c:choose>
									<c:when test="${detail.maxCnt eq 0 or detail.status eq '2'}">
										<div class="col col-6">
					                        <a href="${CTX}/product/productView.do?goodsCd=${detail.goodsCd}" class="btn full detail-link"><span class="txt">상세보기</span></a>
					                    </div>
									</c:when>
									<c:otherwise>
										<div class="col col-6">
					                        <a href="${CTX}/product/productView.do?goodsCd=${detail.goodsCd}" class="btn full detail-link"><span class="txt">상세보기</span></a>
					                    </div>
										<div class="col col-6">
					                        <a href="javascript:" class="btn full" onclick="buyNow('${detail.goodsIdx}');"><span class="txt">바로구매</span></a>
					                    </div>
									</c:otherwise>
								</c:choose>
		            		</div>
		            		<div class="row">
		            			<c:choose>
									<c:when test="${detail.maxCnt eq 0 or detail.status eq '2'}"></c:when>
									<c:otherwise><!-- 
					                    <div class="col col-6">
					                        <a href="javascript:" class="btn full" onclick="btnOneClickPay();"><span class="txt">원클릭 결제</span></a>
					                    </div> -->
					                    <div class="col col-6">
					                        <a  id="cartBtn" href="javascript:" class="btn full black" onclick="goCart('${detail.goodsIdx}','<c:out value="${detail.goodsCd}"/>','<c:out value="${detail.goodsTitle}"/>','<fmt:formatNumber value="${detail.discountPrice}" groupingUsed="false"/>');"><span class="txt">장바구니</span></a>
					                    </div>
									</c:otherwise>
								</c:choose>
		            		</div>
						</div>
					</div>
				</c:if>
				
				<%-- <div class="timesale-banner">
					<img src="${CTX}/images/${DEVICE}/@dummy/banner_timesale_20180919.jpg" alt="" />
				</div> --%>

				<div class="helper-box">
					<div class="helper-box-inner">
						<strong class="tit" data-icon="speaker">타임세일 안내</strong>
						<ul>
							<li>타임 세일 상품은 당사의 사정에 따라 임의 변경 될 수 있습니다.</li>
							<li>타임 세일 상품은 1회 구매 제한 수량이 있습니다.</li>
							<li>주문은 결제 완료 기준이며, 여러 고객님 들께서 동시에 주문하신 경우, 결제 완료 순서에 의해 후순위 고객님의 주문은 자동 주문 취소가 될 수 있습니다.<br>이점 양해 부탁드립니다.</li>
							<li>타임 세일 상품의 경우 고객님의 단순 변심으로 인한 교환 및 반품은 불가능합니다.</li>
							<li>준비된 수량이 품절될 경우, 타임 세일은 조기 종료됩니다.</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		(function(window, document, $){
		    //타임세일
		     $(".detail-timer").timesale();
		})(window, document, jQuery);
</script>	
</body>
</html>