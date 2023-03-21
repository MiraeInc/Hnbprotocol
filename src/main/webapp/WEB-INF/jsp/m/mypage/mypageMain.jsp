<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_main" />
<meta name="menu_no" content="mypage_000" />

<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<script language="JavaScript" src="${lgEscrowScriptUrl}"></script>
<script type="text/javascript">

<%-- 주문 취소 --%>
function orderCancel(orderCd) {
	//$('[data-remodal-id=popConfirm]').off("cancellation");
	//$('[data-remodal-id=popConfirm]').on("cancellation" , function() {});
	
	$('[data-remodal-id=popConfirm]').off("confirmation");
	
	$('[data-remodal-id=popConfirm]').on("confirmation", function () {
		//이중클릭 방지
		$('[data-remodal-id=popConfirm]').off("confirmation");
		$.ajax({			
			url: getContextPath()+"/ajax/order/OrderAllCancel.do",
			data: {"orderCd" : orderCd},
		 	type: "post",
		 	async: false,
		 	cache: false,
		 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data){
				if(data.result == true){
					$('[data-remodal-id=pop_reload_alert] div.pop-alert p').html("주문 취소 하였습니다.");
					$('[data-remodal-id=pop_reload_alert]').remodal().open();
				}else{
					if(data.msg != "" && data.msg !=null){
						$('[data-remodal-id=pop_alert] div.pop-alert p').html(data.msg);
						$('[data-remodal-id=pop_alert]').remodal().open();
					}
				}
			 }
		});
	} );
	
	$('[data-remodal-id=popConfirm] div.pop-alert p').html("주문 취소 하시겠습니까?");
	$('[data-remodal-id=popConfirm]').remodal().open();	
}


//배송지변경
function popDeliveryChange(orderCd) {
	
	$.ajax({			
		url: getContextPath()+"/ajax/order/deliveryChangeLayer.do",
		data: {"orderCd" : orderCd},
	 	type: "post",
	 	async: false,
	 	cache: false,
	 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
	 	error: function(request, status, error){ 
	 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
		},
		success: function(data){
			$('#popDelivery').html(data);	
		 }
	});
}

function AllCompletStatus(orderCd) {

	$('[data-remodal-id=popConfirm]').off("confirmation");
	
	$('[data-remodal-id=popConfirm]').on("confirmation", function () {
		//이중클릭 방지
		$('[data-remodal-id=popConfirm]').off("confirmation");
		$.ajax({			
			url: getContextPath()+"/ajax/order/AllCompletStatus.do",
			data: {"orderCd" : orderCd},
		 	type: "post",
		 	async: false,
		 	cache: false,
		 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data){
				if(data.result == true){
					$('[data-remodal-id=pop_reload_alert] div.pop-alert p').html("구매 확정으로 변경 하였습니다.");
					$('[data-remodal-id=pop_reload_alert]').remodal().open();
				}else{
					if(data.msg != "" && data.msg !=null){
						$('[data-remodal-id=pop_alert] div.pop-alert p').html(data.msg);
						$('[data-remodal-id=pop_alert]').remodal().open();
					}
				}
			 }
		});
	} );
	
	$('[data-remodal-id=popConfirm] div.pop-alert p').html("구매확정 하시겠습니까?");
	$('[data-remodal-id=popConfirm]').remodal().open();	
}


// 리뷰 작성 팝업
function reviewWrite(flag, orderDetailIdx, winnerIdx, goodsIdx) {
	window.location.href = "${CTX}/mypage/review/reviewWrite.do?statusFlag=I&goodsIdx="+goodsIdx+"&orderDetailIdx="+orderDetailIdx+"&layerType=review";
}

// 구매확정 + 리뷰 작성 팝업
function reviewOrderWrite(flag, orderDetailIdx, winnerIdx, goodsIdx) {
	window.location.href = "${CTX}/mypage/review/reviewWrite.do?statusFlag=I&goodsIdx="+goodsIdx+"&orderDetailIdx="+orderDetailIdx+"&layerType=order";
}
	
<%-- LG U+ 에스크로 구매확정 페이지 호출 --%> 
function linkESCROW(oid){
	var agent = navigator.userAgent.toLowerCase();
	 
	if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ) {
		checkDacomESC("${LGD_MID}", oid, "");
		location.reload();
	}
	else {
		alert("LG U+ 에스크로 구매확정/취소하기 페이지는 인터넷 익스플로러(IE)에서만 지원됩니다.");
	}
}

</script>
</head>
<body>
      <div class="content comm-order comm-mypage mypage-index">

				<!-- s: 히스토리 -->
				<div class="breadcrumb">
					<ul>
						<li>
							<a href="${CTX}/main.do"><span>Home</span></a>
						</li>
						<li class="current">
							<a href="${CTX}/mypage/order/main.do"><span>마이페이지</span></a>
						</li>
					</ul>
				</div>
				<!-- e: 히스토리 -->

                <div class="page-body">
                    <c:if test="${not empty USERINFO.memberId and USERINFO.memberFlag eq 'Y'}">	<%-- 회원 로그인일때만 헤더 표시 --%>
                    <!-- 회원정보 -->
                    	<c:choose>
						<c:when test="${USERINFO.levelIdx == 1}">
							<c:set var="nowGrade" value="grade-g"/>
							<c:set var="nextGrade" value="우수회원"/>
						</c:when>
						<c:when test="${USERINFO.levelIdx == 2}">
							<c:set var="nowGrade" value="grade-s"/>
							<c:set var="nextGrade" value="VIP회원"/>
						</c:when>
						<c:when test="${USERINFO.levelIdx == 3}">
							<c:set var="nowGrade" value="grade-v"/>
							<c:set var="nextGrade" value="VIP회원"/>
						</c:when>
						</c:choose>
                    <div class="section section-customer">
                        <div class="cust-info">
                            <a href="${CTX}/mypage/order/memberBenefit.do">
                                <span class="grade ${nowGrade}"><span class="hide">${USERINFO.gradeNm}</span></span>
                                <p>
                                    <strong class="name">${USERINFO.memberNm}</strong>님의<br/>
                                   	 회원 등급은 <strong class="level">${USERINFO.gradeNm}</strong> 입니다.                                    
                                </p>
                            </a>
                        </div>
                        <ul class="cust-membership">
                            <li>
                                <a href="${CTX}/mypage/point/couponList.do">
                                    <span class="tit">쿠폰</span>
                                    <span class="val"><strong><fmt:formatNumber value="${mypageCouponCnt}" groupingUsed="true"/></strong>장</span>
                                </a>
                            </li>
                            <li>
								<a href="${CTX}/mypage/point/pointList.do">
                                    <span class="tit">포인트</span>
                                    <span class="val"><strong><fmt:formatNumber value="${mypagePoint}" groupingUsed="true"/></strong>P</span>
                                </a>
                            </li>
                        </ul>
                        <c:choose>
							<c:when test="${USERINFO.levelIdx == 1}">
							<p class="txt"><strong class="amount"><fmt:formatNumber value="${nextLevelPrice}" groupingUsed="true"/></strong>원 추가 구매하시면 <strong class="em">우수회원</strong>이 되실 수 있습니다.</p>
							</c:when>
							<c:when test="${USERINFO.levelIdx == 2}">
							<p class="txt"><strong class="amount"><fmt:formatNumber value="${nextLevelPrice}" groupingUsed="true"/></strong>원 추가 구매하시면 <strong class="em">VIP회원</strong>이 되실 수 있습니다.</p>
							</c:when>
							<c:when test="${USERINFO.levelIdx == 3}">
							<p class="txt"><strong class="amount"><fmt:formatNumber value="${nextLevelPrice}" groupingUsed="true"/></strong>원 추가 구매하시면 <strong class="em">VIP회원</strong>을 유지 하실 수 있습니다.</p>
							</c:when>
						</c:choose>
                    </div>
                    <!-- //회원정보 -->
					</c:if>
                    <!-- 나의 쇼핑 내역 -->
                    <div class="section section-status">
                        <div class="section-title">
                            <h3 class="tit">나의 쇼핑 내역</h3>
                        </div>
                        
                        <!-- 
                            [D] 개발참고 
                            - 카운트가 없을경우 <span>n</span> 있을경우 <span class="em">n</span>
                        -->
                        <ul class="order-status">
                            <li>
                                <a href="${CTX}/mypage/order/myOrderList.do?schStatus=100">
                                    <strong>주문 접수</strong>
                                    <p><span <c:if test="${cnt_100 > 0}"> class="em" </c:if>><fmt:formatNumber value="${cnt_100}" groupingUsed="true"/></span></p>
                                </a>
                            </li>
                            <li>
                                <a href="${CTX}/mypage/order/myOrderList.do?schStatus=200">
                                    <strong>결제 완료</strong>
                                   <p><span <c:if test="${cnt_200 > 0}"> class="em" </c:if>><fmt:formatNumber value="${cnt_200}" groupingUsed="true"/></span></p>
                                </a>
                            </li>
                            <li>
                                <a href="${CTX}/mypage/order/myOrderList.do?schStatus=300">
                                    <strong>상품 준비 중</strong>
                                 <p><span <c:if test="${cnt_300 > 0}"> class="em" </c:if>><fmt:formatNumber value="${cnt_300}" groupingUsed="true"/></span></p>
                                </a>
                            </li>
                            <li>
                                 <a href="${CTX}/mypage/order/myOrderList.do?schStatus=400">
                                    <strong>발송 완료</strong>
                                 <p><span <c:if test="${cnt_400 > 0}"> class="em" </c:if>><fmt:formatNumber value="${cnt_400}" groupingUsed="true"/></span></p>
                                </a>
                            </li>
                            <li>
                                <a href="${CTX}/mypage/order/myOrderList.do?schStatus=900">
                                    <strong>구매확정</strong>
                                  <p><span <c:if test="${cnt_900 > 0}"> class="em" </c:if>><fmt:formatNumber value="${cnt_900}" groupingUsed="true"/></span></p>
                                </a>
                            </li>
                        </ul>
                        <ul class="complain-status">
                        	<li><a href="${CTX}/mypage/order/myCancelList.do"><strong>취소</strong><span><fmt:formatNumber value="${cnt_800}" groupingUsed="true"/></span></a></li>
							<li><a href="${CTX}/mypage/order/myExchangeList.do"><strong>교환</strong><span><fmt:formatNumber value="${cnt_600}" groupingUsed="true"/></span></a></li>
							<li><a href="${CTX}/mypage/order/myReturnList.do"><strong>반품</strong><span><fmt:formatNumber value="${cnt_700}" groupingUsed="true"/></span></a></li>
                        </ul>
                        <c:if test="${not empty USERINFO.memberId and USERINFO.memberFlag eq 'Y'}">	<%-- 회원 로그인--%>
                        <div class="btn-box">
                            <div class="col col-6">
                                <a href="${CTX}/mypage/order/myOrderList.do"  class="btn-myorder"><span class="txt">주문관리</span></a>
                            </div>
                            <div class="col col-6">
                                <a href="${CTX}/mypage/wish/wishList.do" class="btn-mywish"><span class="txt">위시리스트</span></a>
                            </div>
                        </div>
                        </c:if>
                    </div>
                    <!-- //나의 쇼핑 내역 -->

                    
                    <!-- 활동내역 -->
                    <div class="section section-activicy">
                        <div class="section-title">
                            <h3 class="tit">활동내역</h3>
                        </div>
                        <div class="activity">
                         <c:if test="${not empty USERINFO.memberId and USERINFO.memberFlag eq 'Y'}">	<%-- 회원 로그인--%>
                            <a href="${CTX}/mypage/sample/sampleList.do"  class="btn-sample"><i></i><span>이달의 정품 신청</span></a>
                            <a href="${CTX}/mypage/review/noWriteReviewList.do"  class="btn-review"><i></i><span>상품 후기</span></a>
                        </c:if>
                            <a href="${CTX}/mypage/inquiry/inquiryList.do"  class="btn-qna"><i></i><span>1:1 문의</span></a>
                        </div>
                    </div>
                    <!-- //활동내역 -->

                    <!-- 최근 주문 내역 -->
                    <div class="section section-order">
                        <div class="section-title">
                            <h3 class="tit">최근 주문 내역</h3>
                            <a href="${CTX}/mypage/order/myOrderList.do" class="btn-order-more"><i class="em">+</i>MORE</a>
                        </div>
                        
                        
                         <!-- 
							수정 : 2017-08-17
							- 통일성을 위해서 구조변경, form-group 추가 
						-->
                        <div class="order-form">
                <c:choose>
					<c:when test="${totalCount eq 0}">
                        	<!-- 주문내역 없을경우 -->
	                    	<div class="form-group">
	                           	<div class="no-contents">
	                           		<p>최근 주문하신 내역이 없습니다.</p>
	                           	</div>
	                        </div>
	                        <!-- //주문내역 없을경우 -->
                   </c:when>
				   <c:otherwise>  
				   		<c:set var="orderIdx" value=""/>
						<c:set var="orderCd" value=""/>
						<c:set var="orderStatusCd" value=""/>
						<c:set var="notcancelcnt" value=""/>
						<c:set var="completcnt" value="0"/> <%-- 구매확정 개수 --%>
						<c:set var="enableClaim" value="0"/>
						<c:set var="payType" value=""/>			<%-- 결제수단 (PAY_TYPE10 : 신용카드, PAY_TYPE15 : 빌키, PAY_TYPE20 : 실시간계좌, PAY_TYPE25 : 가상계좌, PAY_TYPE30 : 휴대폰, PAY_TYPE35 : PAYCO, PAY_TYPE40 : 카카오페이, PAY_TYPE45 : Npay, PAY_TYPE50 : PAYNOW, PAY_TYPE90 : 포인트) */ --%>
						<c:set var="escrowYn" value=""/>		<%-- 에스크로 여부 (Y : 에스크로 사용, N : 에스크로 사용 안 함) --%>
						
						<c:forEach var="list" items="${list}" varStatus="idx">
							<c:if test="${not idx.first and orderIdx ne list.orderIdx}">
	                                    </ul>
	                                </div>
	                                <div class="btn-box">
	                                <c:if test="${notcancelcnt eq '0' }">	<%-- 입금전(주문접수), 결제완료 --%>
	                                    <div class="col col-6">
										<c:choose>
											<c:when test="${payType eq 'PAY_TYPE25' and orderStatusCd ne '000' and orderStatusCd ne '100'}">	<%-- 가상계좌고 입금된 상태면 환불 은행, 계좌등 정보 받기 위해 팝업 호출 --%>
		                                        <button type="button" class="btn black full" onclick="javascript:cancelOrder(this,'${orderCd}')"><span class="txt">주문 취소</span></button>
											</c:when>
											<c:otherwise>
		                                        <button type="button" class="btn black full" onclick="orderCancel('${orderCd}')"><span class="txt">주문 취소</span></button>
											</c:otherwise>
										</c:choose>
	                                    </div>
	                                    <div class="col col-6">
	                                        <button type="button" class="btn full" data-toggle="popup" data-target="#popDelivery"  onclick="popDeliveryChange('${orderCd}');"><span class="txt">배송지 변경</span></button>
	                                    </div>
	                                 </c:if>
	                                    
									<c:if test="${enableClaim > 0 and escrowYn eq 'N'}">	<%-- 에스크로건이 아니면 배송중(발송완료) 이면 문구 표시 --%>
										<div class="col col-3">
	                                        <button type="button" class="btn black full" onclick="javascript:exchangeOrder(this,'${orderCd}');"><span class="txt">교환</span></button>
	                                    </div>
	                                    <div class="col col-3">
	                                        <button type="button" class="btn black full" onclick="javascript:returnOrder(this,'${orderCd}');"><span class="txt">반품</span></button>
	                                    </div>
									</c:if>
									
									<c:if test="${completcnt eq '1'}">
									  	<div class="col col-6">
										<c:choose>
											<c:when test="${escrowYn eq 'N'}">		<%-- 에스크로건이 아니면 전체구매확정 버튼, 에스크로건은 에스크로 구매확정/취소하기 버튼 표시 --%>
		                                        <button type="button" class="btn green full" onclick="AllCompletStatus('${orderCd}');"><span class="txt">전체구매확정</span></button>
											</c:when>
											<c:otherwise>
		                                        <button type="button" class="btn green full" onclick="linkESCROW('${orderCd}');"><span class="txt">에스크로 구매확정/취소하기</span></button>
											</c:otherwise>
										</c:choose>
	                                    </div>
									</c:if>
	                                </div>
	                            </div>
	                         </div>
	                        </c:if>
	                        
							<c:set var="completcnt" value="0"/>
							<c:if test="${idx.first or orderIdx ne list.orderIdx}">    
							<div class="form-group">
	                        	<div class="form-title">
	                                <h3 class="title">${list.orderDt} <span class="sub">(주문번호 : ${list.orderCd} ｜ 최종결제금액 <fmt:formatNumber value="${list.totalPayPrice}" groupingUsed="true"/>원)</span></h3>
	                                <a href="${CTX}/mypage/order/orderDetail.do?orderCd=${list.orderCd}" class="btn-view">상세보기 +</a>
	                            </div>
	                            <div class="form-body">
	                                <div class="order-goods">
	                                    <ul>
	                                    
	                        </c:if>     
	                                        <li>
	                                            <div class="item">
	                                              <c:if test="${list.enableClaim > 0 and list.escrowYn eq 'N'}"> <%-- 에스크로건이 아니면 배송중(발송완료) 체크박스 추가 --%>
		                                              <c:if test="${list.detailStatusCd eq '400' or list.detailStatusCd eq '500' or list.detailStatusCd eq '670' or list.detailStatusCd eq '690'}">	<%-- // 발송 완료, 배송 완료, 교환 완료만 교환/신청 가능, 반품은 교환 불가에서도 가능 --%>
			                                            <div class="item-check">
															<span class="checkbox">
																<input type="checkbox" name="chkGoods" id="chkGoods_${list.orderDetailIdx}" value="${list.orderDetailIdx}" data-radio-check="${list.orderCd}" data-goodsnm="${list.goodsNm}"  class="check" />
																<label for="chkGoods_${list.orderDetailIdx}" class="lbl"><span class="hide">선택</span></label>
															</span>
														</div>													
														</c:if>
													</c:if>
	                                            
	                                                <div class="item-view">
	                                                    <div class="view-thumb" onclick="location.href='${CTX}/product/productView.do?goodsCd=${list.goodsCd}';">
	                                                    	<c:set var="imgSplit" value="${fn:split(list.mainFile ,'.') }"/>
	                                                        <img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}" onerror="this.src='${CTX}/images/${DEVICE}/noimage.jpg'" alt="상품 썸네일 이미지">
	                                                    </div>
	                                                    <div class="view-info" onclick="location.href='${CTX}/product/productView.do?goodsCd=${list.goodsCd}';">
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
	                                                        <p class="name"><c:out value="${list.goodsNm}"/></p>
	                                                        <p class="price"><fmt:formatNumber value="${list.price}" groupingUsed="true"/><span class="qty"> / 수량: <fmt:formatNumber value="${list.orderCnt}" groupingUsed="true"/></span></p>
	                                                    </div>
	                                                </div>
	                                                <div class="item-payment">
	                                                    <dl>
	                                                        <dt><strong class="em"><c:out value="${list.detailStatusNm}"/></strong></dt>
	                                                        <dd class="full">
	                                                         	<div class="btn-box">
	                                                                <div class="col col-12">
	                                                                    <a href="javascript:void(0);" class="btn outline-green ico-chev full" onclick="buyNow('${list.goodsIdx}','${list.orderCnt}');"><span class="txt">바로구매</span></a>
	                                                                </div>
	                                                            </div>
	                                                            <div class="btn-box">
	                                                            <c:if test="${!empty list.invoiceNo and (list.detailStatusCd ge '400' or list.detailStatusCd eq '690')}">	<%-- 배송중(발송완료) 이전이환완료상태일경우 --%>
																    <div class="col col-6">
	                                                                    <a href="javascript:void(0);" class="btn ico-chev full" onclick="deliveryTracking('<c:out value="${list.trackingUrl}"/>','${list.invoiceNo}');"><span class="txt">배송추적</span></a>
	                                                                </div>
																</c:if>
																
                                                            	<c:if test="${list.detailStatusCd eq '400' or list.detailStatusCd eq '500' or list.detailStatusCd eq '670'  or list.detailStatusCd eq '690'}">	<%-- 배송중(발송완료), 배송완료, 교환불가, 교환완료시 구매확정가능 --%>
																	<c:if test="${not empty USERINFO.memberId}">	<%-- 회원일때만 구매확정 버튼 표시 --%>
																		<c:if test="${list.escrowYn eq 'N'}">		<%-- 에스크로건이 아닐때만 구매확정 버튼 표시 --%>
			                                                            	<div class="col col-6">
			                                                                    <a href="javascript:void(0);" class="btn ico-chev full"  onclick="reviewOrderWrite('I','${list.orderDetailIdx}','','${list.goodsIdx}');"><span class="txt">구매 확정</span></a>
			                                                                </div>
																		</c:if>
																	</c:if>
                                                                <c:set var="completcnt" value="1"/>
																</c:if>
																<c:if test="${list.detailStatusCd eq '900' && list.reviewCnt == 0}">	<%-- 구매확정 이면 후기작성 버튼 표시 --%>
	                                                                <div class="col col-6">
	                                                                    <a href="javascript:void(0);" class="btn full btn-write-review" onclick="reviewWrite('I','${list.orderDetailIdx}','','${list.goodsIdx}');"><span class="txt">후기작성</span></a>
	                                                                </div>
                                                                </c:if>
	                                                            </div>
	                                                            
	                                                        </dd>
	                                                    </dl>   
	                                                </div>
	                                            </div>
	                                        </li>
	                                         
	                          
	                          <c:if test="${idx.last}">
	                           </ul>
	                                </div>
	                                <div class="btn-box">
	                                <c:if test="${list.notcancelcnt eq '0' }">	<%-- 입금전(주문접수), 결제완료 --%>
	                                    <div class="col col-6">
										<c:choose>
											<c:when test="${list.payType eq 'PAY_TYPE25' and list.orderStatusCd ne '000' and list.orderStatusCd ne '100'}">	<%-- 가상계좌고 입금된 상태면 환불 은행, 계좌등 정보 받기 위해 팝업 호출 --%>
		                                        <button type="button" class="btn black full" onclick="javascript:cancelOrder(this,'${list.orderCd}')"><span class="txt">주문 취소</span></button>
											</c:when>
											<c:otherwise>
		                                        <button type="button" class="btn black full" onclick="orderCancel('${list.orderCd}')"><span class="txt">주문 취소</span></button>
											</c:otherwise>
										</c:choose>
	                                    </div>
	                                    <div class="col col-6">
	                                        <button type="button" class="btn full" data-toggle="popup" data-target="#popDelivery"  onclick="popDeliveryChange('${list.orderCd}');"><span class="txt">배송지 변경</span></button>
	                                    </div>
	                                 </c:if>
	                                    
	                                 <c:if test="${list.enableClaim > 0 and list.escrowYn eq 'N'}">	<%-- 에스크로건이 아니면 교환, 반품 버튼 표시 --%>
										<div class="col col-3">
	                                        <button type="button" class="btn black full" onclick="javascript:exchangeOrder(this,'${list.orderCd}');"><span class="txt">교환</span></button>
	                                    </div>
	                                    <div class="col col-3">
	                                        <button type="button" class="btn black full" onclick="javascript:returnOrder(this,'${list.orderCd}');"><span class="txt">반품</span></button>
	                                    </div>
									</c:if>
									
									<c:if test="${completcnt eq '1'}">
									  	<div class="col col-6">
										<c:choose>
											<c:when test="${list.escrowYn eq 'N'}">		<%-- 에스크로건이 아니면 전체구매확정 버튼, 에스크로건은 에스크로 구매확정/취소하기 버튼 표시 --%>
		                                        <button type="button" class="btn green full" onclick="AllCompletStatus('${list.orderCd}');"><span class="txt">전체구매확정</span></button>
											</c:when>
											<c:otherwise>
		                                        <button type="button" class="btn green full" onclick="linkESCROW('${list.orderCd}');"><span class="txt">에스크로 구매확정/취소하기</span></button>
											</c:otherwise>
										</c:choose>
	                                    </div>
									</c:if>
	                                </div>
	                            </div>
	                         </div>
	                         </c:if>
							<c:set var="orderIdx" value="${list.orderIdx}"/>
							<c:set var="orderCd" value="${list.orderCd}"/>
							<c:set var="orderStatusCd" value="${list.orderStatusCd}"/>
							<c:set var="notcancelcnt" value="${list.notcancelcnt}"/>
							<c:set var="enableClaim" value="${list.enableClaim}"/>
							<c:set var="payType" value="${list.payType}"/>
							<c:set var="escrowYn" value="${list.escrowYn}"/>		<%-- 에스크로 여부 (Y : 에스크로 사용, N : 에스크로 사용 안 함) --%>
	                    </c:forEach>
						</c:otherwise>
					</c:choose>
					
							                            
                        </div>
                        <!-- //수정 : 2017-08-17 -->
                        
                    </div>
                    <!-- //최근 주문 내역 -->
				<c:if test="${not empty USERINFO.memberId and USERINFO.memberFlag eq 'Y'}">	<%-- 회원 로그인일때만 헤더 표시 --%>
                    <!-- 회원정보 -->
                    <div class="section section-myaccount">
                        <div class="section-title">
                            <h3 class="tit">회원정보</h3>
                        </div>
                        <div class="customer-menus">
                            <ul>
                                <c:if test="${USERINFO.memberLoginType eq 'MEMBER'}">
                                <li><a href="${CTX}/mypage/member/snsConnect.do">SNS 계정 연결 관리</a></li>
                                </c:if>
                                <li><a href="${CTX}/mypage/member/memberShipping.do">배송지 관리</a></li>
                                <li><a href="${CTX}/mypage/member/memberInfo.do">개인정보 수정</a></li>
                                <li><a href="${CTX}/mypage/member/memberWithdraw.do">회원탈퇴</a></li>
                            </ul>
                        </div>
                    </div>
                    <!-- //회원정보 -->
                 </c:if>   
                </div>
            </div>
				
				


	
<!-- 배송지 변경 -->
<div id="popDelivery" class="popup type-page popup-cashreceipt">

</div>
<!-- //배송지 변경 -->
	
</body>
</html>