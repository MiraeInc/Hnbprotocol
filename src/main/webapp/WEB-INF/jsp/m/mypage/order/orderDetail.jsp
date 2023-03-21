<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_shopping" />
<meta name="menu_3depth" content="${VO.orderMenu}" />
<meta name="menu_no" content="mypage_010" />

<script language="JavaScript" src="${lgScriptUrl}"></script>
<script language="JavaScript" src="${lgEscrowScriptUrl}"></script>
<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<script type="text/javascript">

	<%-- 검색 --%>
	function goSearch(){
		var frm = document.orderForm;
		frm.action="${CTX}/mypage/myOrderList.do";
		frm.submit();
	}

	<%-- 목록 --%>
	function goList() {
		history.go(-1);
	}
	
	function PaycoPrint(orderno) {
		window.open("${paycoUrl}/outseller/receipt/"+orderno,"paycoprintwin",'scrollbars=yes,toolbar=yes,location=no,resizable=yes,status=yes,menubar=no,resizable=yes,width=300,height=400,left=0,top=0');
	}
	
	function LgDacomPrint() {
		showReceiptByTID('${orderInfo.lgdMid}','${orderInfo.lgdTid}', '${authData}');
	}

	function KcpPrint(type) {
		var cmd="";
		var tno = "${orderInfo.lgdTid}";
		var order_no = "${orderInfo.orderCd}";
		var mony = "${orderInfo.totalPayPrice}";
		var status = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes, width=420,height=670";
		if (type=='PAY_TYPE10') {
			 cmd = "card_bill";
		} else if (type = 'PAY_TYPE20') {
			 cmd = "acnt_bill";
		} else if (type = 'PAY_TYPE25') {
			 cmd = "vcnt_bill";
		} else if (type = 'PAY_TYPE20') {
			 cmd = "mcash_bill";
		}
		window.open("https://admin8.kcp.co.kr/assist/bill.BillActionNew.do?cmd="+cmd+"&tno="+tno+"&order_no="+order_no+"&trade_mony="+mony,"kcpwin","toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes, width=420,height=540");

	}
	
	// 스마일페이 영수증 출력
	function SmilePayPrint(tid) { 
	 	var status = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes, width=420,height=540"; 
	 	var url = "https://mms.cnspay.co.kr/trans/retrieveIssueLoader.do?TID="+tid+"&type=0"; 
	 	window.open(url,"popupIssue",status); 
	} 

	<%-- LG U+ 에스크로 구매확정 페이지 호출 --%> 
	function linkESCROW(oid){
		var agent = navigator.userAgent.toLowerCase();
		 
		if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ) {
			checkDacomESC("${orderInfo.lgdMid}", oid, "");
			location.reload();
		}
		else {
			alert("LG U+ 에스크로 구매확정/취소하기 페이지는 인터넷 익스플로러(IE)에서만 지원됩니다.");
		}
	}

	function escrowKcpConfirm(oid, tid) {
		var url = "https://admin.kcp.co.kr/Modules/Sale/ESCROW/n_order_confirm.jsp?tno="+tid+"&shop_idxx=${kcpSiteCd}&ordr_idxx="+oid;
		window.open(url, "kcpPop", "width=550, height=480;");	
	}

	function escrowKcpCancel(oid, tid) {
		var url = "https://admin.kcp.co.kr/Modules/Sale/ESCROW/n_deli_cancel.jsp?tno="+tid+"&shop_idxx=${kcpSiteCd}&ordr_idxx="+oid;
		window.open(url, "kcpPop", "width=550, height=480;");	
	}
	
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

	
</script>
</head>
<body>
<form name="lguForm" id = "lguForm" method="post" target="_BLANK" action="https://pgweb.uplus.co.kr:7086/ms/interface/retrieveReceiptInfoCard.do">
	<input type="hidden" name="transactionid" id="lgd_tid" value="">
</form>

	<form name="orderForm" id="orderForm" method="get">
	
				<div class="content comm-order comm-mypage mypage-order">
                
                <div class="page-body">
                    
                    <!-- 주문내역 -->
                    <!-- 
						수정 : 2017-08-17
						- 통일성을 위해서 구조변경, form-group 추가 
					-->
                    <div class="order-form">

                        <div class="form-group">
                        	<div class="form-title">
	                            <h3 class="title">${orderInfo.orderDt} <span class="sub">(주문번호 : ${orderInfo.orderCd} ｜ 최종결제금액 <fmt:formatNumber value="${orderInfo.totalPayPrice}" groupingUsed="true"/>원)</span></h3>
	                        </div>
	                        <div class="form-body">
	                            <div class="order-goods">
	                                <ul>
										<c:forEach var="list" items="${list}" varStatus="idx">
	                                    <li>
	                                        <div class="item">
											<c:if test="${orderInfo.enableClaim > 0 and orderInfo.escrowYn eq 'N'}"> <%-- 에스크로건이 아니면 배송중(발송완료) 체크박스 추가 --%>		
                                              	<c:if test="${list.detailStatusCd eq '400' or list.detailStatusCd eq '500' or list.detailStatusCd eq '670' or list.detailStatusCd eq '690'}">	<%-- // 발송 완료, 배송 완료, 교환 완료만 교환/신청 가능, 반품은 교환 불가에서도 가능 --%>
		                                            <div class="item-check">
														<span class="checkbox">
															<input type="checkbox" name="chkGoods" id="chkGoods_${list.orderDetailIdx}" value="${list.orderDetailIdx}" data-radio-check="${orderInfo.orderCd}" data-goodsnm="${list.goodsNm}"  class="check" />
															<label for="chkGoods_${list.orderDetailIdx}" class="lbl"><span class="hide">선택</span></label>
														</span>
													</div>													
												</c:if>
											</c:if>
	                                            <div class="item-view">
	                                                <div class="view-thumb">
	                                                	<c:set var="imgSplit" value="${fn:split(list.mainFile ,'.') }"/>
	                                                	<img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}" onerror="this.src='/w/images/${DEVICE}/noimage.jpg'" alt="상품 썸네일 이미지">
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
																		<c:if test="${orderInfo.escrowYn eq 'N'}">		<%-- 에스크로건이 아닐때만 구매확정 버튼 표시 --%>
			                                                            	<div class="col col-6">
			                                                                    <a href="javascript:void(0);" class="btn ico-chev full"  onclick="reviewOrderWrite('I','${list.orderDetailIdx}','','${list.goodsIdx}');"><span class="txt">구매 확정</span></a>
			                                                                </div>
																		</c:if>
																	</c:if>
                                                                <c:set var="completcnt" value="1"/>
																</c:if>
																<c:if test="${list.detailStatusCd eq '900' && list.reviewCnt == 0}">	<%-- 구매확정 이면 후기작성 버튼 표시 --%>
	                                                                <div class="col col-12">
	                                                                    <a href="javascript:void(0);" class="btn full btn-write-review" onclick="reviewWrite('I','${list.orderDetailIdx}','','${list.goodsIdx}');"><span class="txt">후기작성</span></a>
	                                                                </div>
																</c:if>
	                                                            </div>
	                                                    </dd>
	                                                </dl>   
	                                            </div>
	                                        </div>
	                                    </li>
	                                    </c:forEach>
	                                </ul>
	                            </div>
	                            <div class="btn-box">
	                               <c:if test="${orderInfo.notcancelcnt eq '0' }">	<%-- 입금전(주문접수), 결제완료 --%>
	                                    <div class="col col-6">
										<c:choose>
											<c:when test="${orderInfo.payType eq 'PAY_TYPE25' and orderInfo.orderStatusCd ne '000' and orderInfo.orderStatusCd ne '100'}">	<%-- 가상계좌고 입금된 상태면 환불 은행, 계좌등 정보 받기 위해 팝업 호출 --%>
		                                        <button type="button" class="btn black full" onclick="javascript:cancelOrder(this,'${orderInfo.orderCd}')"><span class="txt">주문 취소</span></button>
											</c:when>
											<c:otherwise>
		                                        <button type="button" class="btn black full" onclick="orderCancel('${orderInfo.orderCd}')"><span class="txt">주문 취소</span></button>
											</c:otherwise>
										</c:choose>
	                                    </div>
	                                    <div class="col col-6">
	                                        <button type="button" class="btn full" data-toggle="popup" data-target="#popDelivery"  onclick="popDeliveryChange('${orderInfo.orderCd}');"><span class="txt">배송지 변경</span></button>
	                                    </div>
	                                 </c:if>
	                                    
									<c:if test="${orderInfo.enableClaim > 0 and orderInfo.escrowYn eq 'N'}">		<%-- 에스크로건이 아니면 교환, 반품 버튼 표시 --%>  
										<div class="col col-3">
	                                        <button type="button" class="btn black full" onclick="javascript:exchangeOrder(this,'${orderInfo.orderCd}');"><span class="txt">교환</span></button>
	                                    </div>
	                                    <div class="col col-3">
	                                        <button type="button" class="btn black full" onclick="javascript:returnOrder(this,'${orderInfo.orderCd}');"><span class="txt">반품</span></button>
	                                    </div>
									</c:if>
									
									<c:if test="${completcnt eq '1'}">
										<c:choose>
											<c:when test="${orderInfo.escrowYn eq 'N'}">		<%-- 에스크로건이 아니면 전체구매확정 버튼, 에스크로건은 에스크로 구매확정/취소하기 버튼 표시 --%>
									  			<div class="col col-6">
		                                        <button type="button" class="btn green full" onclick="AllCompletStatus('${orderInfo.orderCd}');"><span class="txt">전체구매확정</span></button>
		                                         </div>
											</c:when>
											<c:otherwise>
		                                        <%-- <button type="button" class="btn green full" onclick="linkESCROW('${orderInfo.orderCd}');"><span class="txt">에스크로 구매확정/취소하기</span></button> --%>
		                                        <div class="col col-6">
		                                        <button type="button" class="btn green full" onclick="escrowKcpConfirm('${orderInfo.orderCd}','${orderInfo.lgdTid}');"><span class="txt">에스크로 구매확정</span></button>
		                                        </div>
		                                        <div class="col col-6">
		                                        <button type="button" class="btn black full" onclick="escrowKcpCancel('${orderInfo.orderCd}','${orderInfo.lgdTid}');"><span class="txt">에스크로 구매취소</span></button>
		                                        </div>
											</c:otherwise>
										</c:choose>
	                                   
									</c:if>
	                            </div>
	                        </div>
						</div>
						

						<!-- 최초 결제 내역 -->
                        <div class="form-group form-payment">
                        	<div class="form-title">
	                            <h3 class="title">최초 결제 내역</h3>
	                            <button type="button" class="btn-toggle active" data-toggle="collapse" data-target="#formPayment"><span class="hide">접기</span></button>
	                        </div>
	                        <div id="formPayment" class="form-body">
	                            <div class="payment-final">
	                                <ul>
	                                    <li>
	                                        <div class="item">
	                                            <strong>총 상품 금액 </strong>
	                                            <p><fmt:formatNumber value="${orderInfo.totalOrderPrice}" groupingUsed="true"/></p>
	                                        </div>
	                                       
	                                    </li>
	                                    <li>
	                                        <div class="item">
	                                            <strong>총 할인 금액 <button type="button" class="btn_toggle" data-toggle="collapse" data-target="#totalDiscount"><span class="hide">펼치기</span></button></strong>
	                                            <p><span class="em"><fmt:formatNumber value="${orderInfo.totalPromotioncodePrice + orderInfo.totalGiftCouponPrice + orderInfo.totalCartCouponPrice + orderInfo.totalPointPrice}" groupingUsed="true"/></span></p>
	                                        </div>
	                                        <dl id="totalDiscount" class="item-sub">
													<dt>상품쿠폰</dt>
													<dd><fmt:formatNumber value="${orderInfo.totalGiftCouponPrice}" groupingUsed="true"/></dd>
												<c:if test="${orderInfo.totalPromotioncodePrice lt 1}">	<%--0이 0.00으로 반환돼서 1보다 작은으로 --%>
													<dt>장바구니쿠폰<dt>
													<dd><fmt:formatNumber value="${orderInfo.totalCartCouponPrice}" groupingUsed="true"/></dd>
												</c:if>
												<c:if test="${orderInfo.totalPromotioncodePrice gt 0}">
													<dt>프로모션코드<dt>
													<dd><fmt:formatNumber value="${orderInfo.totalPromotioncodePrice}" groupingUsed="true"/></dd>
												</c:if>
													<dt>포인트할인<dt>
													<dd><fmt:formatNumber value="${orderInfo.totalPointPrice}" groupingUsed="true"/>P</dd>
	                                        </dl>
	                                    </li>
	                                    <li>
	                                        <div class="item">
	                                            <strong>총 배송비 <button type="button" class="btn_toggle" data-toggle="collapse" data-target="#totalShipping"><span class="hide">펼치기</span></button></strong>
	                                            <p><fmt:formatNumber value="${orderInfo.shippingPrice}" groupingUsed="true"/></p>
	                                        </div>
	                                        <dl id="totalShipping" class="item-sub">
	                                        <c:if test="${orderInfo.shippingPrice gt 0 or orderInfo.freeShippingCouponPrice gt 0}">
	                                            <dt>기본 배송비</dt>
	                                            <dd><c:choose><c:when test="${orderInfo.shippingPrice gt 0}"><fmt:formatNumber value="${orderInfo.shippingPrice}" groupingUsed="true"/></c:when><c:otherwise><fmt:formatNumber value="${orderInfo.freeShippingCouponPrice}" groupingUsed="true"/></c:otherwise></c:choose></dd>
	                                            <c:if test="${orderInfo.freeShippingCouponPrice gt 0}">
												<dt>배송비 쿠폰</dt>
												<dd>	-<fmt:formatNumber value="${orderInfo.freeShippingCouponPrice}" groupingUsed="true"/></dd>
												</c:if>
	                                         </c:if>
	                                        </dl>
	                                    </li>
	                                    <li class="total">
	                                        <div class="item">
	                                            <strong>최종 결제 금액</strong>
	                                            <p><fmt:formatNumber value="${orderInfo.totalPayPrice}" groupingUsed="true"/></p>
	                                        </div>
	                                        <dl class="item-sub type2">
	                                            <dt><span class="em">적립 포인트</span></dt>
	                                            <dd><fmt:formatNumber value="${orderInfo.totalSavePoint}" groupingUsed="true"/>P</dd>
	                                        </dl>
	                                    </li>
	                                </ul>
	                            </div>
	                        </div>
                        </div>
						<!-- //최초 결제 내역 -->


						<!-- 최종 결제 내역 -->
						<div class="form-group form-payment">
							<div class="form-title">
								<h3 class="title">최종 결제 내역</h3>
								<button type="button" class="btn-toggle active" data-toggle="collapse" data-target="#formDecide"><span class="hide">접기</span></button>
							</div>
							<div id="formDecide" class="form-body">
								<div class="payment-decide">
									<strong>${orderInfo.payNm}</strong>
									<p><fmt:formatNumber value="${orderInfo.totalPayPrice}" groupingUsed="true"/></p>
								</div>
							</div>
						</div>
						<!-- //최종 결제 내역 -->

						<c:if test="${(freeGiftList != null and fn:length(freeGiftList) gt 0) or (priceGiftList != null and fn:length(priceGiftList) gt 0)}">
						<!-- 사은품 -->
						<div class="form-group form-gift-get">
							<div class="form-title">
								<h3 class="title">사은품</h3>
								<button type="button" class="btn-toggle active" data-toggle="collapse" data-target="#formGift"><span class="hide">접기</span></button>
							</div>
							<div id="formGift" class="form-body">
								<div class="ui-gift">
								<c:if test="${freeGiftList != null and fn:length(freeGiftList) gt 0}">
									<div class="gift-list">
										<h4 class="tit"><c:out value="${freeGiftList[0].termNm}"/></h4>
										<ul>
										<c:forEach var="list" items="${freeGiftList}" varStatus="idx">
											<li>
												<div class="item">
												<c:set var="imgSplit" value="${fn:split(list.giftImg ,'.') }"/>
													<div class="item-thumb"><img src="${IMGPATH}/gift/${list.giftIdx}/${imgSplit[0]}.${imgSplit[1]}" alt="사은품 이미지"></div>	<%--사은품은 원본 이미지로 표시 --%>
													<div class="item-info">
														<p class="name"><c:out value="${list.giftNm}"/></p>
													</div>
												</div>
											</li>
										</c:forEach>
										</ul>
<%-- 										<strong class="status"><c:out value="${freeGiftList[0].statusNm}"/></strong> --%>
									</div>
								</c:if>
								<c:if test="${priceGiftList != null and fn:length(priceGiftList) gt 0}">
									<div class="gift-list">
										<h4 class="tit"><c:out value="${priceGiftList[0].termNm}"/></h4>
										<ul>
										<c:forEach var="list" items="${priceGiftList}" varStatus="idx">
											<li>
												<div class="item">
													<c:set var="imgSplit" value="${fn:split(list.giftImg ,'.') }"/>
													<div class="item-thumb"><img src="${IMGPATH}/gift/${list.giftIdx}/${imgSplit[0]}.${imgSplit[1]}" alt="사은품 이미지"></div>	<%--사은품은 원본 이미지로 표시 --%>
													<div class="item-info">
														<p class="name"><c:out value="${list.giftNm}"/></p>
													</div>
												</div>
											</li>
										</c:forEach>
										</ul>
<%-- 										<strong class="status"><c:out value="${priceGiftList[0].statusNm}"/></strong> --%>
									</div>
								</c:if>
								</div>
							</div>
						</div>
						<!-- //사은품 -->
						</c:if>
						   
					<c:choose>
						<c:when test="${orderInfo.payType eq 'PAY_TYPE35'}">	<%-- PAYCO 결제 --%>
						<!-- 결제 정보 - 페이코 -->
                        <div class="form-group">
                        	<div class="form-title">
	                            <h3 class="title">결제 정보</h3>
	                            <button type="button" class="btn-toggle active" data-toggle="collapse" data-target="#formPayMethod"><span class="hide">접기</span></button>
	                        </div>
	                        <div id="formPayMethod" class="form-body">
	                          
								<c:forEach var="paycoList" items="${paycoList}" varStatus="idx">
								  <div class="complete-info">
	                                <dl>
	                                    <dt>결제 금액</dt>
	                                    <dd><span class="em"><fmt:formatNumber value="${paycoList.paymentamt}" groupingUsed="true"/> </span></dd>
	                                    <c:if test="${idx.first}">
	                                    <dt>결제 수단</dt>
	                                    <dd><span class="em">${orderInfo.payNm}</span></dd>
	                                    </c:if>
	                                </dl>
	                                <c:if test="${idx.first}">	                                
	                                <button type="button" class="btn btn-receipt" onclick="PaycoPrint('${paycoList.orderno}');"><span class="txt">영수증 출력</span></button>
	                                </c:if>
	                            </div>
	                            <div class="complete-info">
	                                <h3 class="tit">결제 내역</h3>
	                                				<c:choose>
													<c:when test="${paycoList.paymentmethodcode eq '04'}">	<%-- 실시간 계좌이체 --%>
													<dl>
					                                    <dt>은행명</dt>
					                                    <dd><span class="em"><c:out value="${paycoList.bankname}"/></span></dd>
					                                    <dt>입금 완료일</dt>										<%-- 입금 완료 --%>
														<dd><span class="em"><c:out value="${orderInfo.payDtStr}"/></span></dd>
													</dl>	
													</c:when>
													<c:when test="${paycoList.paymentmethodcode eq '02'}">	<%-- 가상계좌 --%>
													 <dl>
					                                    <dt>은행명</dt>
					                                    <dd><span class="em"><c:out value="${paycoList.bankname}"/></span></dd>
					                                    <dt>계좌번호</dt>
					                                    <dd><span class="em"><c:out value="${paycoList.accountno}"/></span></dd>
				                                    	<c:choose>
														<c:when test="${orderInfo.orderStatusCd eq '100'}">	<%-- 주문 접수(입금 대기) --%>
														<dt>입금 기한</dt>
														<dd>
															<span class="em"><c:out value="${paycoList.expirationDt}"/> 23시 59분까지</span>
															<p>(※ 입금기한이 지나면 주문은 자동으로 취소됩니다.)</p>
														</dd>
														</c:when>
														<c:otherwise>				
														<dt>입금 완료일</dt>										<%-- 입금 완료 --%>
														<dd><span class="em"><c:out value="${orderInfo.payDtStr}"/></span></dd>
														</c:otherwise>
														</c:choose>
					                                </dl>
													</c:when>
													<c:when test="${paycoList.paymentmethodcode eq '60'}">	<%-- 휴대폰 간편결제 --%>
													<dl>
													<dt>결제 휴대폰 번호</dt>
				                                    <dd><span class="em"><c:out value="${paycoList.cellphoneno}"/></span></dd>
				                                    <dt>결제 일자</dt>
				                                    <dd><span class="em"><c:out value="${orderInfo.payDtStr}"/></span></dd>
				                                    </dl>
													</c:when>
													<c:when test="${paycoList.paymentmethodcode eq '31'}">	<%-- 신용카드 결제 --%>
					                                <dl>
					                                    <dt><c:out value="${paycoList.paymentmethodname}"/></dt>
					                                    <dd><span class="em"><c:out value="${paycoList.cardcompanyname}"/></span></dd>
					                                    <dt>할부 구분</dt>
														<c:choose>
					                                    	<c:when test="${paycoList.cardinstallmentmonthnumber ne null and paycoList.cardinstallmentmonthnumber ne '00'}">
					                                    		<dd><span class="em"><c:if test="${paycoList.cardinterstfreeyn eq 'Y'}">무이자 </c:if><fmt:parseNumber value="${paycoList.cardinstallmentmonthnumber}"/>개월</span></dd>
															</c:when>
															<c:otherwise>
																<dd><span class="em">일시불</span></dd>
															</c:otherwise>
														</c:choose>
					                                    <dt>결제 일자</dt>
					                                    <dd><span class="em"><c:out value="${orderInfo.payDtStr}"/></span></dd>
					                                </dl>
													</c:when>
					                                <c:otherwise>
					                                <dl>				
														<dt><c:out value="${paycoList.paymentmethodname}"/></dt>
														<dd><span class="em"></span></dd>
													</dl>
													</c:otherwise>
												</c:choose>
	                            </div>
							</c:forEach>
	                        </div>
                        </div>
						<!-- //결제 정보 - 페이코 -->
						
						</c:when>
						<c:when test="${orderInfo.payType eq 'PAY_TYPE55'}">	<%-- 스마일페이 결제 --%>
							<div class="form-group">
                        	<div class="form-title">
	                            <h3 class="title">결제 정보</h3>
	                            <button type="button" class="btn-toggle active" data-toggle="collapse" data-target="#formPayMethod"><span class="hide">접기</span></button>
	                        </div>
	                        <div id="formPayMethod" class="form-body">
	                            <div class="complete-info">
	                                <dl>
	                                    <dt>결제 금액</dt>
	                                    <dd><span class="em"><fmt:formatNumber value="${orderInfo.totalPayPrice}" groupingUsed="true"/></span></dd>
	                                    <dt>결제 수단</dt>
	                                    <dd><span class="em">${orderInfo.payNm}</span></dd>
	                                </dl>
	                                <button type="button" class="btn btn-receipt" onclick="SmilePayPrint('${smilepayInfo.tid}');"><span class="txt">영수증 출력</span></button>
	                            </div>
	                            <div class="complete-info">
	                                <h3 class="tit">결제 내역</h3>
	                                	<dl>
		                                    <dt>결제 카드</dt>
		                                    <dd><span class="em"><c:out value="${smilepayInfo.cardName}"/><c:if test="${smilepayInfo.cardCi eq '1'}">(체크카드)</c:if></span></dd>
		                                    <dt>할부 구분</dt>
		                                    <c:choose>
												<c:when test="${smilepayInfo.cardQuota eq '00'}">
													<dd><span class="em">일시불</span></dd>
												</c:when>
												<c:otherwise>
													<dd><span class="em">${smilepayInfo.cardQuota}개월</span></dd>
												</c:otherwise>
											</c:choose>
		                                </dl>
	                            </div>
	                        </div>
                        </div>
						</c:when>
						
						<c:when test="${orderInfo.payType eq 'PAY_TYPE60'}">	<%-- 원더페이 결제 --%>
							<div class="form-group">
                        	<div class="form-title">
	                            <h3 class="title">결제 정보</h3>
	                            <button type="button" class="btn-toggle active" data-toggle="collapse" data-target="#formPayMethod"><span class="hide">접기</span></button>
	                        </div>
	                        <div id="formPayMethod" class="form-body">
	                            <div class="complete-info">
	                                <dl>
	                                    <dt>결제 금액</dt>
	                                    <dd><span class="em"><fmt:formatNumber value="${wonderpayInfo.goodMny}" groupingUsed="true"/></span></dd>
	                                    <dt>결제 수단</dt>
	                                    <dd><span class="em">${orderInfo.payNm}</span></dd>
	                                </dl>
	                               
	                            </div>
	                            <div class="complete-info">
	                                <h3 class="tit">결제 내역</h3>
	                                	
	                                	<c:choose>
					                	<c:when test="${wonderpayInfo.payMethod eq 'PACA'}"> <!-- 카드결제 -->
					                	<dl>
					                		 <dt>결제 카드</dt>
                                 					<dd><span class="em">
                                 
					                		<c:choose>
							                	<c:when test="${wonderpayInfo.cardCd eq 'CCNH'}">NH카드</c:when>
							                	<c:when test="${wonderpayInfo.cardCd eq 'CMCF'}">해외마스터카드</c:when>
							                	<c:when test="${wonderpayInfo.cardCd eq 'CCBC'}">BC카드</c:when>
							                	<c:when test="${wonderpayInfo.cardCd eq 'CCSG'}">씨티카드(구,신세계)</c:when>
							                	<c:when test="${wonderpayInfo.cardCd eq 'CJCF'}">해외JCB카드</c:when>
							                	<c:when test="${wonderpayInfo.cardCd eq 'CCCT'}">씨티카드</c:when>
							                	<c:when test="${wonderpayInfo.cardCd eq 'CCKE'}">하나카드(외환)</c:when>
							                	<c:when test="${wonderpayInfo.cardCd eq 'CCHM'}">씨티카드(구,한미)</c:when>
							                	<c:when test="${wonderpayInfo.cardCd eq 'CCDI'}">현대카드</c:when>
							                	<c:when test="${wonderpayInfo.cardCd eq 'CVSF'}">해외비자</c:when>
							                	<c:when test="${wonderpayInfo.cardCd eq 'CCUF'}">은련카드</c:when>
							                	<c:when test="${wonderpayInfo.cardCd eq 'CCAM'}">롯데아멕스카드</c:when>
							                	<c:when test="${wonderpayInfo.cardCd eq 'CCSU'}">수협카드</c:when>
							                	<c:when test="${wonderpayInfo.cardCd eq 'CCLO'}">롯데카드</c:when>
							                	<c:when test="${wonderpayInfo.cardCd eq 'CCJB'}">전북카드</c:when>
							                	<c:when test="${wonderpayInfo.cardCd eq 'CCHN'}">하나카드</c:when>
							                	<c:when test="${wonderpayInfo.cardCd eq 'CCCJ'}">제주은행</c:when>
							                	<c:when test="${wonderpayInfo.cardCd eq 'CCSS'}">삼성카드</c:when>
							                	<c:when test="${wonderpayInfo.cardCd eq 'CCKJ'}">광주은행</c:when>
							                	<c:when test="${wonderpayInfo.cardCd eq 'CCLG'}">신한카드</c:when>
							                	<c:when test="${wonderpayInfo.cardCd eq 'CCKM'}">KB국민카드</c:when>
							                	<c:otherwise>
							                	${wonderpayInfo.cardCd}
							                	</c:otherwise>
								            </c:choose>
					                		</span></dd>
					                		<c:choose>
							                	<c:when test="${wonderpayInfo.quota eq '00'}"><dd><span class="em"> 할부 구분 : 일시불 </span></dd></c:when>
							                	<c:otherwise>
							                	<dd><span class="em"> 할부 구분 : ${wonderpayInfo.quota}개월 </span></dd>
							                	</c:otherwise>
							                </c:choose>
							                 </dl>
							                <c:if test="${wonderpayInfo.couponMny ne '0'}">
							                <dl>
							                	<dd><span class="em"> 카드결제금액 <fmt:formatNumber value="${wonderpayInfo.cardMny}" groupingUsed="true"/></span> </dd>
							                	<dd><span class="em">즉시할인 쿠폰적용 <fmt:formatNumber value="${wonderpayInfo.couponMny}" groupingUsed="true"/></span> </dd>
							                </dl>
							                </c:if>
							                
										</c:when>
					                	<c:otherwise>
					                	<dl>
					                		<dd><span class="em"> 즉시출금은행 :
					                		<c:choose>
							                	<c:when test="${wonderpayInfo.bankCd eq 'BK39'}">경남은행</c:when>
							                	<c:when test="${wonderpayInfo.bankCd eq 'BK88'}">신한은행</c:when>
							                	<c:when test="${wonderpayInfo.bankCd eq 'BK89'}">케이뱅크</c:when>
							                	<c:when test="${wonderpayInfo.bankCd eq 'BK34'}">광주은행</c:when>
							                	<c:when test="${wonderpayInfo.bankCd eq 'BK48'}">신협</c:when>
							                	<c:when test="${wonderpayInfo.bankCd eq 'BK81'}"> KEB 하나은행</c:when>
							                	<c:when test="${wonderpayInfo.bankCd eq 'BK04'}">국민은행</c:when>
							                	<c:when test="${wonderpayInfo.bankCd eq 'BK20'}">우리은행</c:when>
							                	<c:when test="${wonderpayInfo.bankCd eq 'BK03'}">기업은행</c:when>
							                	<c:when test="${wonderpayInfo.bankCd eq 'BK71'}">우체국</c:when>
							                	<c:when test="${wonderpayInfo.bankCd eq 'BK11'}">농협</c:when>
							                	<c:when test="${wonderpayInfo.bankCd eq 'BK35'}">제주은행</c:when>
							                	<c:when test="${wonderpayInfo.bankCd eq 'BK31'}">대구은행</c:when>
							                	<c:when test="${wonderpayInfo.bankCd eq 'BK90'}">카카오뱅크</c:when>
							                	<c:when test="${wonderpayInfo.bankCd eq 'BK32'}">부산은행</c:when>
							                	<c:when test="${wonderpayInfo.bankCd eq 'BK27'}">한국시티은행</c:when>
							                	<c:when test="${wonderpayInfo.bankCd eq 'BK64'}">산림조합</c:when>
							                	<c:when test="${wonderpayInfo.bankCd eq 'BK23'}">SC제일은행</c:when>
							                	<c:when test="${wonderpayInfo.bankCd eq 'BK45'}">새마을금고</c:when>
							                	<c:when test="${wonderpayInfo.bankCd eq 'BK02'}">산업은행</c:when>
							                	<c:when test="${wonderpayInfo.bankCd eq 'BK07'}">수협</c:when>
							                	<c:when test="${wonderpayInfo.bankCd eq 'BK37'}">전북은행</c:when>
							                	<c:otherwise>
							                	${wonderpayInfo.bankCd}
							                	</c:otherwise>
								            </c:choose>
					                		</span> </dd>
					                		 <dd><span>현금영수증발급여부 : 
 								                	   <c:choose>
								                	<c:when test="${wonderpayInfo.cashReceiptFlag eq 'Y'}">발급</c:when>
								                	<c:otherwise>
								                	미발급
								                	</c:otherwise>
								                </c:choose>
					                		</span></dd>
					                		</dl>
					                	</c:otherwise>
					                </c:choose>
	                            </div>
	                        </div>
                        </div>
						</c:when>
						
						<c:otherwise>		
						
						<!-- 결제 정보 - 카드 결제 -->
                        <div class="form-group">
                        	<div class="form-title">
	                            <h3 class="title">결제 정보</h3>
	                            <button type="button" class="btn-toggle active" data-toggle="collapse" data-target="#formPayMethod"><span class="hide">접기</span></button>
	                        </div>
	                        <div id="formPayMethod" class="form-body">
	                            <div class="complete-info">
	                                <dl>
	                                    <dt>결제 금액</dt>
	                                    <dd><span class="em"><fmt:formatNumber value="${orderInfo.totalPayPrice}" groupingUsed="true"/></span></dd>
	                                    <dt>결제 수단</dt>
	                                    <dd><span class="em">${orderInfo.payNm}</span></dd>
	                                </dl>
	                                <c:if test="${(orderInfo.totalPayPrice > 0 ) and (orderInfo.payType ne 'PAY_TYPE90')}"> <% //포인트결제 아닐경우 %>
	                                
	                                	<c:choose>
										 	<c:when test="${orderInfo.pgType eq 'KCP'}">
										 		<button type="button" class="btn btn-receipt" onclick="KcpPrint('${orderInfo.payType}');"><span class="txt">영수증 출력</span></button>
										 	</c:when>
										 	<c:otherwise>
										 		<button type="button" class="btn btn-receipt" onclick="LgDacomPrint();"><span class="txt">영수증 출력</span></button>
										 	</c:otherwise>
										 </c:choose>
	                                </c:if>
	                            </div>
	                            <div class="complete-info">
	                                <h3 class="tit">결제 내역</h3>
	                                <c:choose>
										<c:when test="${orderInfo.payType eq 'PAY_TYPE20'}">	<%-- 실시간 계좌이체 --%>
										<dl>
		                                    <dt>은행명</dt>
		                                    <dd><span class="em"><c:out value="${orderInfo.lgdFinancename}"/></span></dd>
		                                    <dt>예금주</dt>
		                                    <dd><span class="em"><c:out value="${orderInfo.lgdAccountowner}"/></span></dd>
		                                    <dt>입금 완료일</dt>										<%-- 입금 완료 --%>
											<dd><span class="em"><c:out value="${orderInfo.payDtStr}"/></span></dd>
										</dl>	
										</c:when>
										<c:when test="${orderInfo.payType eq 'PAY_TYPE25'}">	<%-- 가상계좌 --%>
										<dl>
		                                    <dt>은행명</dt>
		                                    <dd><span class="em"><c:out value="${orderInfo.lgdFinancename}"/></span></dd>
		                                    <dt>계좌번호</dt>
		                                    <dd><span class="em"><c:out value="${orderInfo.lgdAccountnum}"/></span></dd>
		                                    <dt>예금주</dt>
		                                    <dd><span class="em"><c:out value="${orderInfo.lgdPayer}"/></span></dd>
	                                    	<c:choose>
											<c:when test="${orderInfo.orderStatusCd eq '100'}">	<%-- 주문 접수(입금 대기) --%>
											<dt>입금 기한</dt>
											<dd>
												<span class="em"><c:out value="${orderInfo.depositDeadlineDt}"/> 23시 59분까지</span>
												<p>(※ 입금기한이 지나면 주문은 자동으로 취소됩니다.)</p>
											</dd>
											</c:when>
											<c:otherwise>				
											<dt>입금 완료일</dt>										<%-- 입금 완료 --%>
											<dd><span class="em"><c:out value="${orderInfo.payDtStr}"/></span></dd>
											</c:otherwise>
											</c:choose>
		                                </dl>
										</c:when>
										<c:when test="${orderInfo.payType eq 'PAY_TYPE30'}">	<%-- 휴대폰 결제 --%>
										<dl>
										<dt>결제 휴대폰 번호</dt>
	                                    <dd><span class="em"><c:out value="${orderInfo.lgdTelno}"/></span></dd>
	                                    <dt>결제 일자</dt>
	                                    <dd><span class="em"><c:out value="${orderInfo.payDtStr}"/></span></dd>
	                                    </dl>
										</c:when>
										<c:when test="${orderInfo.payType eq 'PAY_TYPE90'}">	<%-- 포인트 결제 --%>
										<dl>
											<dt>포인트/쿠폰 사용</dt>
											<dd><span class="em"><c:out value="${orderInfo.totalPointPrice}"/></span></dd>
										</dl>
										</c:when>
										<c:when test="${orderInfo.payType eq 'PAY_TYPE10' || orderInfo.payType eq 'PAY_TYPE15'  || orderInfo.payType eq 'PAY_TYPE50'}">	<%-- 신용카드, 빌키, 페이나우 결제 --%>
										<dl>
		                                    <dt>결제 카드</dt>
		                                    <dd><span class="em"><c:out value="${orderInfo.lgdFinancename}"/></span></dd>
		                                    <dt>할부 구분</dt>
		                                    <c:choose>
												<c:when test="${orderInfo.lgdCardinstallmonth ne null and orderInfo.lgdCardinstallmonth ne '00'}">
													<dd><span class="em"><c:if test="${orderInfo.lgdCardnointyn eq '1'}">무이자 </c:if><fmt:parseNumber value="${orderInfo.lgdCardinstallmonth}"/>개월</span></dd>
												</c:when>
												<c:otherwise>
													<dd><span class="em">일시불</span></dd>
												</c:otherwise>
											</c:choose>
		                                    <dt>결제 일자</dt>
		                                    <dd><span class="em"><c:out value="${orderInfo.payDtStr}"/></span></dd>
		                                </dl>
										</c:when>
									</c:choose>
	                            </div>
	                        </div>
                        </div>
						<!-- //결제 정보 -->

						</c:otherwise>
					</c:choose>				

						<!-- 배송 정보 -->
                        <div class="form-group">
                        	<div class="form-title">
	                            <h3 class="title">배송 정보</h3>
	                            <button type="button" class="btn-toggle active" data-toggle="collapse" data-target="#formShipInfo"><span class="hide">접기</span></button>
	                        </div>
	                        <div id="formShipInfo" class="form-body">
	                            <div class="complete-info">
	                                <h4 class="tit">주문하신 분</h4>
	                                <dl>
	                                    <dt>성명</dt>
	                                    <dd><c:out value="${orderInfo.senderNm}"/></dd>
	                                    <dt>휴대폰</dt>
	                                    <dd><c:out value="${orderInfo.senderPhoneNo}"/></dd>
	                                    <dt>이메일</dt>
	                                    <dd><c:out value="${orderInfo.senderEmail}"/></dd>
	                                </dl>
	                            </div>
	                            <div class="complete-info">
	                                <h4 class="tit">받으시는 분</h4>
	                                <dl>
	                                    <dt>성명</dt>
	                                    <dd><c:out value="${orderInfo.receiverNm}"/></dd>
	                                    <dt>휴대폰</dt>
	                                    <dd><c:out value="${orderInfo.receiverPhoneNo}"/></dd>
	                                    <dt>배송지 주소</dt>
	                                    <dd>
										<p style="margin-bottom: 10px">
										<c:out value="${orderInfo.receiverAddr}"/><br /> <c:out value="${orderInfo.receiverAddrDetail}"/>
										</p>
										<c:if test="${orderInfo.notcancelcnt eq '0'}">	<%-- 입금전(주문접수), 결제완료 일때는 주문취소, 배송지 변경 버튼 표시 --%>
										<button type="button" class="btn ico-chev" data-toggle="popup" data-target="#popDelivery" onclick="popDeliveryChange('${orderInfo.orderCd}');"><span class="txt">배송지 변경</span></button>
										</c:if>
	                                    </dd>
	                                    <dt>배송시 요청사항</dt>
	                                    <dd>
											<c:out value="${orderInfo.orderMemoStr}"/>
	                                    </dd>
	                                </dl>
	                            </div>
	                        </div>
                        </div>
                    	<!-- //배송 정보 -->


					</div>
                    

                    <div class="btn-box confirm">
                        <a href="javascript:void(0);" class="btn" onclick="goList();"><span class="txt">목록</span></a>
                    </div>

                
                </div>
            </div>
	</form>
	


<!-- 배송지 변경 -->
<div id="popDelivery" class="popup type-page popup-cashreceipt">

</div>
<!-- //배송지 변경 -->
	
</body>
</html>