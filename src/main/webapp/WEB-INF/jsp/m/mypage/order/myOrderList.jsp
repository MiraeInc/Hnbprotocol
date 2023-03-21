<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_shopping" />
<meta name="menu_3depth" content="mypage_order" />
<meta name="menu_no" content="mypage_010" />
<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<script language="JavaScript" src="${lgEscrowScriptUrl}"></script>
<script type="text/javascript">

	// 검색
	function goSearch(){
		var frm = document.orderForm;
		frm.action="${CTX}/mypage/order/myOrderList.do";
		frm.submit();
	}
	
	// 페이지 이동
	function goPage(page){
		$("#pageNo").val(page);
		var frm = document.orderForm;
		frm.action = "${CTX}/mypage/order/myOrderList.do";
		frm.submit();
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

	function escrowKcpConfirm(oid, tid) {
		var url = "https://admin.kcp.co.kr/Modules/Sale/ESCROW/n_order_confirm.jsp?tno="+tid+"&shop_idxx=${kcpSiteCd}&ordr_idxx="+oid;
		window.open(url, "kcpPop", "width=550, height=480;");	
	}

	function escrowKcpCancel(oid, tid) {
		var url = "https://admin.kcp.co.kr/Modules/Sale/ESCROW/n_deli_cancel.jsp?tno="+tid+"&shop_idxx=${kcpSiteCd}&ordr_idxx="+oid;
		window.open(url, "kcpPop", "width=550, height=480;");	
	}
</script>
</head>
<body>
	<form name="orderForm" id="orderForm" method="get">
		<input type="hidden" name="pageNo" id="pageNo" value="${SCHVO.pageNo}"/>
		
						<!-- [D] content start here! -->
			<div class="content comm-order comm-mypage mypage-order">

				<div class="page-filter">
					<ul>
						<li><a href="${CTX}/mypage/order/myOrderList.do"  class="active">주문 내역 (<fmt:formatNumber value="${cnt_All}" groupingUsed="true"/>)</a></li>
						<li><a href="${CTX}/mypage/order/myCancelList.do">취소 내역 (<fmt:formatNumber value="${cnt_800}" groupingUsed="true"/>)</a></li>
						<li><a href="${CTX}/mypage/order/myReturnList.do">반품 내역 (<fmt:formatNumber value="${cnt_700}" groupingUsed="true"/>)</a></li>
						<li><a href="${CTX}/mypage/order/myExchangeList.do">교환 내역 (<fmt:formatNumber value="${cnt_600}" groupingUsed="true"/>)</a></li>
						<li><a href="${CTX}/mypage/order/issueDocumentList.do">증빙 서류 발급</a></li>
					</ul>
				</div>

				<div class="page-body">

					<div class="list-filter">
						<div class="row">
							<div class="col col-5">
								<div class="form-control">
									<div class="opt_select">
										<select name="schStatus" id="schStatus" >
											<option value="" <c:if test="${SCHVO.schStatus eq null or SCHVO.schStatus eq ''}">selected</c:if>>전체</option>
											<option value="100" <c:if test="${SCHVO.schStatus eq '100'}">selected</c:if>>주문 접수</option>
											<option value="200" <c:if test="${SCHVO.schStatus eq '200'}">selected</c:if>>결제 완료</option>
											<option value="300" <c:if test="${SCHVO.schStatus eq '300'}">selected</c:if>>상품 준비중</option>
											<option value="400" <c:if test="${SCHVO.schStatus eq '400'}">selected</c:if>>발송 완료</option>
											<option value="900" <c:if test="${SCHVO.schStatus eq '900'}">selected</c:if>>구매 확정</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col col-5">
								<div class="form-control">
									<div class="opt_select">
										<select name="schType" onchange="setDate($(this).val());" >
											<option value="">기간</option>
											<option value="2" <c:if test="${SCHVO.schType eq '2'}">selected</c:if>>1주일</option>
											<option value="3" <c:if test="${SCHVO.schType eq '3'}">selected</c:if>>1개월</option>
											<option value="4" <c:if test="${SCHVO.schType eq null or SCHVO.schType eq '' or SCHVO.schType eq '4'}">selected</c:if>>3개월</option>
											<option value="5" <c:if test="${SCHVO.schType eq '5'}">selected</c:if>>6개월</option>
											<option value="6" <c:if test="${SCHVO.schType eq '6'}">selected</c:if>>1년</option>
											<option value="7" <c:if test="${SCHVO.schType eq '7'}">selected</c:if>>2년</option>
										</select>
									</div>
								</div>
							</div>
							
							<input type="hidden" name="schStartDt" id="schStartDt" value="${SCHVO.schStartDt}"/>
							<input type="hidden" name="schEndDt" id="schEndDt" value="${SCHVO.schEndDt}"/>
							
							<div class="col col-2">
								<button type="button" class="btn-search" onclick="javascript:goSearch();"><span class="hide">검색</span></button>
							</div>
						</div>
						<%-- <div class="row">
							<div class="col col-5">
								<div class="form-control">
									<input type="text" class="input form-datepicker" placeholder="검색시작일" name="schStartDt" id="schStartDt" value="${SCHVO.schStartDt}" />
								</div>
							</div>
							<div class="col col-5">
								<div class="form-control">
									<input type="text" class="input form-datepicker" placeholder="검색끝일" name="schEndDt" id="schEndDt" value="${SCHVO.schEndDt}" />
								</div>
							</div>
							<div class="col col-2">
								<button type="button" class="btn-search" onclick="javascript:goSearch();"><span class="hide">검색</span></button>
							</div>
						</div> --%>
					</div>
					
					<!-- 주문내역 -->
					<div class="order-form first">
					                <c:choose>
					<c:when test="${totalCount eq 0}">
                        	<!-- 주문내역 없을경우 -->
	                    	<div class="form-group">
									<div class="no-contents">
										<p>주문 내역이 없습니다.</p>
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
		                                        <button type="button" class="btn black full" onclick="javascript:cancelOrder(this,'${orderCd}');"><span class="txt">주문 취소</span></button>
											</c:when>
											<c:otherwise>
		                                        <button type="button" class="btn black full" onclick="orderCancel('${orderCd}');"><span class="txt">주문 취소</span></button>
											</c:otherwise>
										</c:choose>
	                                    </div>
	                                    <div class="col col-6">
	                                        <button type="button" class="btn full" data-toggle="popup" data-target="#popDelivery"  onclick="popDeliveryChange('${orderCd}');"><span class="txt">배송지 변경</span></button>
	                                    </div>
	                                 </c:if>
	                                    
									<c:if test="${enableClaim > 0 and escrowYn eq 'N'}">	<%-- 에스크로건이 아니면 배송중(발송완료) 이면 문구 표시 --%>
										<div class="col col-3">
	                                        <button type="button" class="btn black full"    onclick="javascript:exchangeOrder(this,'${orderCd}');"><span class="txt">교환</span></button>
	                                    </div>
	                                    <div class="col col-3">
	                                        <button type="button" class="btn black full" onclick="javascript:returnOrder(this,'${orderCd}');"><span class="txt">반품</span></button>
	                                    </div>
									</c:if>
									
									<c:if test="${completcnt eq '1'}">
										<c:choose>
											<c:when test="${escrowYn eq 'N'}">		<%-- 에스크로건이 아니면 전체구매확정 버튼, 에스크로건은 에스크로 구매확정/취소하기 버튼 표시 --%>
										  	<div class="col col-6">
			                                        <button type="button" class="btn green full" onclick="AllCompletStatus('${orderCd}');"><span class="txt">전체구매확정</span></button>
		                                    </div>
											</c:when>
											<c:otherwise>
		                                        <%-- <button type="button" class="btn green full" onclick="linkESCROW('${orderCd}');"><span class="txt">에스크로 구매확정/취소하기</span></button> --%>
												<div class="col col-6">
		                                        <button type="button" class="btn green full" onclick="escrowKcpConfirm('${list.orderCd}','${list.tid}');"><span class="txt">에스크로 구매확정</span></button>
		                                        </div>
		                                        <div class="col col-6">
		                                        <button type="button" class="btn black full" onclick="escrowKcpCancel('${list.orderCd}','${list.tid}');"><span class="txt">에스크로 구매취소</span></button>
		                                        </div>
											</c:otherwise>
										</c:choose>
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
	                                <a href="${CTX}/mypage/order/orderDetail.do?orderCd=${list.orderCd}&orderMenu=mypage_order" class="btn-view">상세보기 +</a>
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
	                                            
	                                                <div class="item-view" onclick="location.href='${CTX}/product/productView.do?goodsCd=${list.goodsCd}';">
	                                                    <div class="view-thumb">
	                                                    	<c:set var="imgSplit" value="${fn:split(list.mainFile ,'.') }"/>
	                                                        <img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}" onerror="this.src='${CTX}/images/${DEVICE}/noimage.jpg'" alt="상품 썸네일 이미지">
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
		                                        <button type="button" class="btn black full" onclick="javascript:cancelOrder(this,'${list.orderCd}');"><span class="txt">주문 취소</span></button>
											</c:when>
											<c:otherwise>
		                                        <button type="button" class="btn black full" onclick="orderCancel('${list.orderCd}');"><span class="txt">주문 취소</span></button>
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
									  	
										<c:choose>
											<c:when test="${list.escrowYn eq 'N'}">		<%-- 에스크로건이 아니면 전체구매확정 버튼, 에스크로건은 에스크로 구매확정/취소하기 버튼 표시 --%>
											<div class="col col-6">
		                                        <button type="button" class="btn green full" onclick="AllCompletStatus('${list.orderCd}');"><span class="txt">전체구매확정</span></button>
	                                    	</div>
											</c:when>
											<c:otherwise>
		                                        <%-- <button type="button" class="btn green full" onclick="linkESCROW('${list.orderCd}');"><span class="txt">에스크로 구매확정/취소하기</span></button> --%>
		                                        <div class="col col-6">
		                                        <button type="button" class="btn green full" onclick="escrowKcpConfirm('${list.orderCd}','${list.tid}');"><span class="txt">에스크로 구매확정</span></button>
		                                        </div>
		                                        <div class="col col-6">
		                                        <button type="button" class="btn black full" onclick="escrowKcpCancel('${list.orderCd}','${list.tid}');"><span class="txt">에스크로 구매취소</span></button>
		                                        </div>
											</c:otherwise>
										</c:choose>
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
					<!-- //주문내역 -->
					
					<!-- 페이징 -->
					<div class="pagin-nav">
						<c:out value="${page.pageStr}" escapeXml="false"/>
					</div>
					<!-- //페이징 -->
					
				</div>
				

				<div class="guidebox">
					<div class="guide-title">
						<h3 class="tit"><span class="i"><img src="${CTX}/images/${DEVICE}/common/ico_helper_alert.png" alt="" /></span> 주문진행 단계 안내</h3>
					</div>
					<div class="guide-order">
						<img src="${CTX}/images/${DEVICE}/contents/img_helper_orderstep.jpg" alt="" />
						<div class="hide">
							<ol>
								<li>
									<p>1. 주문접수</p>
									<p>주문 취소 가능/배송지 변경 가능/부분취소 불가</p>
								</li>
								<li>
									<p>2. 결제 완료</p>
									<p>주문 취소 가능/배송지 변경 가능/부분취소 불가</p>
								</li>
								<li>
									<p>3. 상품 준비 중</p>
									<p>주문 취소 불가/배송지 변경 불가/반품·교환 가능</p>
								</li>
								<li>
									<p>4. 발송 완료</p>
									<p>주문 취소 불가/배송지 변경 불가/반품·교환 가능</p>
								</li>
								<li>
									<p>5. 구매 확정</p>
									<p>반품·교환 불가</p>
								</li>
							</ol>
						</div>
					</div>
				</div>

			</div>
			<!-- [D] //content start end! -->

	</form>
	
	
<!-- 배송지 변경 -->
<div id="popDelivery" class="popup type-page popup-cashreceipt">

</div>
<!-- //배송지 변경 -->
	
<script>
	$(function(){
		//datepicker
		$(".form-datepicker").datepicker();
	});
</script>

</body>
</html>