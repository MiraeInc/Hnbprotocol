<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_shopping" />
<meta name="menu_3depth" content="mypage_cancel" />
<meta name="menu_no" content="mypage_010" />
<script type="text/javascript">

	// 검색
	function goSearch(){
		var frm = document.orderForm;
		frm.action="${CTX}/mypage/order/myCancelList.do";
		frm.submit();
	}
	
	// 페이지 이동
	function goPage(page){
		$("#pageNo").val(page);
		var frm = document.orderForm;
		frm.action = "${CTX}/mypage/order/myCancelList.do";
		frm.submit();
	}

</script>
</head>
<body>
	<form name="orderForm" id="orderForm" method="get">
		<input type="hidden" name="pageNo" id="pageNo" value="${SCHVO.pageNo}"/>
		
								<!-- [D] content start here! -->
			<div class="content comm-order comm-mypage mypage-order">
				<script>
					$(function(){
						//datepicker
						$(".form-datepicker").datepicker();
					});
				</script>

				<div class="page-filter">
					<ul>
						<li><a href="${CTX}/mypage/order/myOrderList.do"  >주문 내역 (<fmt:formatNumber value="${cnt_All}" groupingUsed="true"/>)</a></li>
						<li><a href="${CTX}/mypage/order/myCancelList.do" class="active">취소 내역 (<fmt:formatNumber value="${cnt_800}" groupingUsed="true"/>)</a></li>
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
										<select name="schStatus" id="schStatus"  required>
											<option value="" <c:if test="${SCHVO.schStatus eq null or SCHVO.schStatus eq ''}">selected</c:if>>전체</option>
											<option value="890" <c:if test="${SCHVO.schStatus eq '890'}">selected</c:if>>취소 완료</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col col-5">
								<div class="form-control">
									<div class="opt_select">
										<select name="schType" onchange="setDate($(this).val());" >
											<option value="">기간</option>
											<option value="2" <c:if test="${schVO.schType eq '2'}">selected</c:if>>1주일</option>
											<option value="3" <c:if test="${schVO.schType eq '3'}">selected</c:if>>1개월</option>
											<option value="4" <c:if test="${schVO.schType eq null or schVO.schType eq '' or schVO.schType eq '4'}">selected</c:if>>3개월</option>
											<option value="5" <c:if test="${schVO.schType eq '5'}">selected</c:if>>6개월</option>
											<option value="6" <c:if test="${schVO.schType eq '6'}">selected</c:if>>1년</option>
											<option value="7" <c:if test="${schVO.schType eq '7'}">selected</c:if>>2년</option>
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
								<!-- 컨텐츠 없을때 -->
								<div class="form-group">
									<div class="no-contents">
										<p>취소 내역이 없습니다.</p>
									</div>
								</div>
								<!-- //컨텐츠 없을때 -->
					</c:when>
					<c:otherwise>				
						<c:forEach var="list" items="${list}" varStatus="idx">
						<div class="form-group">
							<a href="${CTX}/mypage/order/orderDetail.do?orderCd=${list.orderCd}&orderMenu=mypage_cancel">
							<div class="form-title">
                                <h3 class="title">${list.orderDt} <span class="sub">(주문번호 : ${list.orderCd} ｜ 접수일 : ${list.orderDt})</span></h3>
<%-- 								<div class="date">접수일 <strong>${list.orderDt}</strong></div> --%>
							</div>
							</a>
							<div class="form-body">
								<div class="order-goods">
									<ul>
									<c:forEach var="goodsList" items="${list.goodsList}" varStatus="goodsIdx">
											<li onclick="location.href='${CTX}/product/productView.do?goodsCd=${goodsList.goodsCd}';">
											<div class="item">
												<div class="item-view">
													<div class="view-thumb"> 
													<c:set var="imgSplit" value="${fn:split(goodsList.mainFile ,'.') }"/>
														<img src="${IMGPATH}/goods/${goodsList.goodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}" onerror="this.src='${CTX}/images/${DEVICE}/noimage.jpg'" alt="상품 썸네일 이미지">
													</div>
													<div class="view-info">
														<div class="badge-box">
															<c:if test="${goodsList.opoYn eq 'Y' }" ><span class="badge type1">1+1</span></c:if>
															<c:if test="${goodsList.tpoYn eq 'Y' }" ><span class="badge type2">2+1</span></c:if>
															<c:if test="${goodsList.newYn eq 'Y' }" ><span class="badge type3">NEW</span></c:if>
															<c:if test="${goodsList.bestYn eq 'Y' }" ><span class="badge type4">BEST</span></c:if>
															<c:if test="${goodsList.saleiconYn eq 'Y' }" ><span class="badge type5">SALE</span></c:if>
															<c:if test="${goodsList.pointiconYn eq 'Y' }" ><span class="badge type3">POINT <em>X2</em></span></c:if>
														</div>
														<c:choose>
															<c:when test="${goodsList.brandIdx eq 1}"><p class="text-gatsby">${goodsList.brandNm}</p></c:when>		
															<c:when test="${goodsList.brandIdx eq 3}"><p class="text-bifesta">${goodsList.brandNm}</p></c:when>
															<c:when test="${goodsList.brandIdx eq 4}"><p class="text-lucidol">${goodsList.brandNm}</p></c:when>
															<c:when test="${goodsList.brandIdx eq 6}"><p class="text-mama">${goodsList.brandNm}</p></c:when>
															<c:when test="${goodsList.brandIdx eq 7}"><p class="text-dental">${goodsList.brandNm}</p></c:when>
															<c:when test="${goodsList.brandIdx eq 8}"><p class="text-charley">${goodsList.brandNm}</p></c:when>
														</c:choose>															
														<p class="name">${goodsList.goodsNm}</p>
														<p class="status">${goodsList.claimStatus}</p>                                                	
													</div>
												</div>
											</div>
											</li>
									</c:forEach>
									</ul>
								</div>
							</div>
						</div>
						</c:forEach>
						<!-- 페이징 -->
						<div class="pagin-nav">
							<c:out value="${page.pageStr}" escapeXml="false"/>
						</div>
						<!-- //페이징 -->
					</c:otherwise>
				</c:choose>
						
						
					</div>
					<!-- //주문내역 -->
					
					<div class="guidebox">
						<div class="guide-title">
							<h3 class="tit"><span class="i"><img src="${CTX}/images/${DEVICE}/common/ico_helper_alert.png" alt="" /></span> 취소단계 안내</h3>
						</div>
						<div class="cancel-guide">
							<ol>
								<li>
									<strong class="step"><img src="${CTX}/images/${DEVICE}/common/num_1.png" alt="1" /> 취소</strong>
									<ul class="bu-list">
										<li><span class="bu">-</span> 주문접수~결제완료 상태에서는 취소가 가능하지만,<br/> 상품 준비 중 ~ 발송완료 상태에서는 취소가 불가능합니다.</li>
										<li><span class="bu">-</span> 상품 준비 중 단계에서는 옵션 및 배송지 변경이 불가능하므로,<br/> 변경을 원하신다면 고객센터로 연락해 주세요.</li>
									</ul>
								</li>
								<li>
									<strong class="step"><img src="${CTX}/images/${DEVICE}/common/num_2.png" alt="2" /> 담당자 확인</strong>
								</li>
								<li>
									<strong class="step"><img src="${CTX}/images/${DEVICE}/common/num_3.png" alt="3" /> 취소처리(환불) 완료</strong>
									<ul class="bu-list">
										<li><span class="bu">-</span> 카드 결제 취소 : 카드 결제는 즉시 자동 취소됩니다.<br/>(단, 카드사의 실제 처리는 약 4~5일 소요)</li>
										<li><span class="bu">-</span> 실시간 계좌이체 : 초기 이체 계좌로 영업일 기준 평균 2~3일 안으로 환불해 드립니다. (토/일/공휴일 제외)</li>
										<li><span class="bu">-</span> 가상계좌 취소 : 등록하신 환불 계좌로 영업일 기준 평균 6~7일 안으로 환불해 드립니다. (토/일/공휴일 제외)</li>
										<li><span class="bu">-</span> 포인트 재 적립 : 사용하신 포인트는 취소처리 완료 후 자동으로 복원됩니다.</li>
									</ul>
								</li>
							</ol>
						</div>
					</div>
					
				</div>
				
			</div>
			<!-- [D] //content start end! -->

	</form>
</body>
</html>