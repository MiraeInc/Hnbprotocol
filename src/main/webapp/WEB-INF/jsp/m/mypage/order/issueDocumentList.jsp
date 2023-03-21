<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_shopping" />
<meta name="menu_3depth" content="mypage_issueDocument" />
<meta name="menu_no" content="mypage_010" />
<script type="text/javascript">
$(document).ready(function(){
	<%-- 숫자만 입력 --%>
	createNumeric("#CASH_RECEIPT_NO");
});

	// 검색
	function goSearch(){
		var frm = document.orderForm;
		frm.action="${CTX}/mypage/order/issueDocumentList.do";
		frm.submit();
	}
	
	// 페이지 이동
	function goPage(page){
		$("#pageNo").val(page);
		var frm = document.orderForm;
		frm.action = "${CTX}/mypage/order/issueDocumentList.do";
		frm.submit();
	}
	
	function popCashbill(orderCd) {
		$("#frmCash input:hidden[name='ORDER_CD']").val(orderCd);
	}
	
	function confirmCashBill() {
		if($("#frmCash input:radio[name='CASH_RECEIPT_GUBUN']:checked").length == 0){
			alert("현금영수증 유형을 선택해 주세요.");
			return false;			
		}

		if($.trim($("#frmCash input:text[name='CASH_RECEIPT_NO']").val()) == ""){
			alert("현금영수증 번호를 입력해 주세요.");
			$("#frmCash input:text[name='CASH_RECEIPT_NO']").focus();
			return false;
		}
		if (confirm('현금영수증 발급 하시겠습니까?'))
 		{
			$.ajax({			
				url: getContextPath()+"/ajax/order/CashReceiptReq.do",
				data: $("#frmCash").serialize() ,
			 	type: "post",
			 	async: false,
			 	cache: false,
			 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 	error: function(request, status, error){ 
			 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
				},
				success: function(data){
					if(data.result == true){
						alert("현금영수증 발급 하였습니다.");
						location.reload();
					}else{
						if(data.msg != "" && data.msg !=null){
							alert(data.msg);
						}
					}
				 }
			});
		} 
		
	}

	function popTaxBill(orderIdx) {
		$("#frmTax input:hidden[name='ORDER_IDX']").val(orderIdx);
	}
	
	function confirmTaxBill() {

		if($.trim($("#frmTax input:text[name='BUSINESS_NO']").val()) == ""){
			alert("사업자번호를 입력해 주세요.");
			$("#frmTax input:text[name='BUSINESS_NO']").focus();
			return false;
		}

		if($.trim($("#frmTax input[name='COMPANY_NM']").val()) == ""){
			alert("사업자명을 입력해 주세요.");
			$("#frmTax input[name='COMPANY_NM']").focus();
			return false;
		}
		if($.trim($("#frmTax input[name='CEO_NM']").val()) == ""){
			alert("대표자명을 입력해 주세요.");
			$("#frmTax input[name='CEO_NM']").focus();
			return false;
		}
		if($.trim($("#frmTax input[name='ADDR']").val()) == ""){
			alert("주소를 입력해 주세요.");
			$("#frmTax input[name='ADDR']").focus();
			return false;
		}
		if($.trim($("#frmTax input[name='UPTAE']").val()) == ""){
			alert("업태를 입력해 주세요.");
			$("#frmTax input[name='UPTAE']").focus();
			return false;
		}
		if($.trim($("#frmTax input[name='JONGMOK']").val()) == ""){
			alert("종목을 입력해 주세요.");
			$("#frmTax input[name='JONGMOK']").focus();
			return false;
		}
		if($.trim($("#frmTax input[name='DAMDANG_NM']").val()) == ""){
			alert("담당자명을 입력해 주세요.");
			$("#frmTax input[name='DAMDANG_NM']").focus();
			return false;
		}
		if($.trim($("#frmTax input[name='DAMDANG_EMAIL']").val()) == ""){
			alert("담당자이메일을 입력해 주세요.");
			$("#frmTax input[name='DAMDANG_EMAIL']").focus();
			return false;
		}

		if (confirm('세금계산서를 발급 신청하시겠습니까?'))
 		{
			$.ajax({			
				url: getContextPath()+"/ajax/order/TaxBillReq.do",
				data: $("#frmTax").serialize() ,
			 	type: "post",
			 	async: false,
			 	cache: false,
			 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 	error: function(request, status, error){ 
					 dclick = false;
			 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
				},
				success: function(data){
					if(data.result == true){
						alert("세금계산서 발급신청이 완료되었습니다.");
					}else{
						if(data.msg != "" && data.msg !=null){
							alert(data.msg);
						}
					}
				 }
			});
		}
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
						<li><a href="${CTX}/mypage/order/myCancelList.do">취소 내역 (<fmt:formatNumber value="${cnt_800}" groupingUsed="true"/>)</a></li>
						<li><a href="${CTX}/mypage/order/myReturnList.do" >반품 내역 (<fmt:formatNumber value="${cnt_700}" groupingUsed="true"/>)</a></li>
						<li><a href="${CTX}/mypage/order/myExchangeList.do">교환 내역 (<fmt:formatNumber value="${cnt_600}" groupingUsed="true"/>)</a></li>
						<li><a href="${CTX}/mypage/order/issueDocumentList.do" class="active">증빙 서류 발급</a></li>
					</ul>
				</div>

				<div class="page-body">

					<div class="list-filter">
						<div class="row">
							<div class="col col-5">
								<div class="form-control">
									<div class="opt_select">
										<select name="schStatus" id="schStatus">
											<option value="" <c:if test="${SCHVO.schStatus eq null or SCHVO.schStatus eq ''}">selected</c:if>>전체</option>
											<option value="PAY_TYPE20" <c:if test="${SCHVO.schStatus eq 'PAY_TYPE20'}">selected</c:if>>실시간계좌이체</option>
											<option value="PAY_TYPE25" <c:if test="${SCHVO.schStatus eq 'PAY_TYPE25'}">selected</c:if>>가상계좌</option>
											<!-- option value="PAY_TYPE35" <c:if test="${SCHVO.schStatus eq 'PAY_TYPE35'}">selected</c:if>>PAYCO</option -->
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
								<!-- 주문 내역이 없을경우 -->
								<div class="form-group">
									<div class="no-contents">
										<p>발급 가능한 증빙 내역이 없습니다.</p>
									</div>
								</div>
								<!-- //주문 내역이 없을경우 -->
							</c:when>
							<c:otherwise>					
						
								<c:forEach var="list" items="${list}" varStatus="idx">	
									<div class="form-group">
										<div class="form-title">
			                                <h3 class="title">${list.orderDt} <span class="sub">(주문번호 : ${list.orderCd} ｜ 접수일 : ${list.orderDt})</span></h3>
										</div>
										<div class="form-body">
											<div class="order-goods type-evidence">
												<ul>
												<c:forEach var="goodsList" items="${list.mobileProductList}" varStatus="goodsIdx">
													<li>
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
																	<p class="price"><fmt:formatNumber value="${goodsList.payPrice}" groupingUsed="true"/></p>
																</div>
															</div>
															<div class="item-payment">
																<dl>
																	<dt><strong class="em">${goodsList.detailStatusNm}</strong></dt>
																	
																	<dd>
																		결제 수단 : ${list.payNm}
																	</dd>
																	
																</dl>   
															</div>
														</div>
													</li>
												</c:forEach>
												</ul>
											</div>
											<div class="btn-box">
												<c:choose>
													<c:when test="${list.taxStatus eq null or list.taxStatus eq ''}">
														<c:if test="${list.cashReceiptGubun eq '0' or list.cashReceiptGubun eq ''}">
															<div class="col col-6">
																<button type="button" class="btn full ico-chev" data-toggle="popup" data-target="#popCashbillReg" onclick="popCashbill('${list.orderCd}');"><span class="txt">현금영수증 발급</span></button>
															</div>
														</c:if>
														<div class="col col-6">
															<button type="button" class="btn full outline-green ico-chev" data-toggle="popup" data-target="#popTaxbillReg"  onclick="popTaxBill('${list.orderIdx}');"><span class="txt">세금계산서 발급</span></button>
														</div>
													</c:when>
													<c:otherwise>
														<div class="col col-12">
															<c:choose>
																<c:when test="${list.taxStatus eq '10'}">	
																	<button type="button" class="btn full gray"><span class="txt">세금계산서 신청 완료</span></button>
																</c:when>
																<c:when test="${list.taxStatus eq '20'}">	
																	<button type="button" class="btn full gray"><span class="txt">세금계산서 발급 완료</span></button>										
																</c:when>
															</c:choose>	
														</div>
													</c:otherwise>
												</c:choose>	
											</div>
										</div>
									</div>
								</c:forEach>
								
							</c:otherwise>
						</c:choose>	
					</div>
					<!-- //주문내역 -->

					<c:if test="${totalCount gt 0}">
						<!-- 페이징 -->
						<div class="pagin-nav">
							<c:out value="${page.pageStr}" escapeXml="false"/>
						</div>
						<!-- //페이징 -->
					</c:if>
						
				</div>
				
				<div class="guidebox">
					<div class="guide-title">
						<h3 class="tit"><span class="i"><img src="${CTX}/images/${DEVICE}/common/ico_helper_alert.png" alt="" /></span>발급 안내</h3>
					</div>
					<div class="guide-list">
						<ul>
							<li>
								<span class="i"><img src="${CTX}/images/${DEVICE}/contents/ico_moeny.png" style="width: 50px" alt="" /></span>
								<strong class="tit">현금 영수증</strong>
								<div>
									현금 결제수단으로 1원 이상 결제한 내역은 국세청에 
									자진 발급 됩니다. 현금영수증 신청을 원하실 경우, 
									국세청 홈페이지(<a href="http://www.hometax.go.kr" class="em">www.hometax.go.kr</a>)에 가셔서 
									가맹점 사업자번호, 금액, 승인번호, 거래일자를 
									등록하셔야 합니다.
								</div>
							</li>
							<li>
								<span class="i"><img src="${CTX}/images/${DEVICE}/contents/ico_paper.png" style="width:42px"  alt="" /></span>
								<strong class="tit">세금계산서 발급</strong>
								<div>
									세금계산서는 상품출고일로부터 익월 5일까지만 신청 가능합니다.
									신용카드 매출전표를 발급 한 경우에는 세금계산서 발행이 불가합니다.
								</div>
							</li>
						</ul>
					</div>
					<div class="guide-cont">
						<ul class="bu-list">
							<li class="em"><span class="bu">*</span> 주문 상태가 ‘구매 확정’ 단계에서만 발행이 가능합니다.</li>
						</ul>
					</div>
				</div>
					
			</div>
			<!-- [D] //content start end! -->

	</form>
	

<!-- 세금계산서 -->

<!-- 세금계산서 신청 -->
<div id="popTaxbillReg" class="popup type-page popup-taxinvoice">
<form name="frmTax" id="frmTax" method="POST" >
<input type="hidden" name="ORDER_IDX" value="">

	<h1 class="hide">GATSBY</h1>
	<!-- pop-top -->
	<div class="pop-top">
		<h2>세금계산서 신청</h2>
	</div>
	<!-- //pop-top -->
	<!-- pop-mid -->
	<div class="pop-mid">
	
		<div class="order-form">
			<div class="form-group">
				<div class="form-body">
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
								<input type="text" class="input" placeholder="사업자 등록 번호" name="BUSINESS_NO" id="BUSINESS_NO"/>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
								<input type="text" class="input" placeholder="사업장 명" name="COMPANY_NM" id="COMPANY_NM"/>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
								<input type="text" class="input" placeholder="대표자 명" name="CEO_NM" id="CEO_NM"/>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
								<input type="text" class="input" placeholder="사업장 주소" name="ADDR" id="ADDR"/>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
								<input type="text" class="input" placeholder="업태" name="UPTAE" id="UPTAE"/>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
								<input type="text" class="input" placeholder="종목" name="JONGMOK" id="JONGMOK"/>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
								<input type="text" class="input" placeholder="담당자 명" name="DAMDANG_NM" id="DAMDANG_NM" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
								<input type="text" class="input" placeholder="담당자 이메일" name="DAMDANG_EMAIL" id="DAMDANG_EMAIL" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="btn-box confirm">
			<button type="button" class="btn outline-green" onclick="confirmTaxBill();"><span class="txt">세금계산서 신청</span></button>
		</div>
		
		<div class="guidebox" style="margin-left: 0; margin-right: 0;">
			<div class="guide-cont">
				<ul class="bu-list">
					<li><span class="bu">-</span> 정확한 사업자 정보를 입력해주시기 바랍니다.</li>
					<li><span class="bu">-</span> 세금계산서는 전자 세금계산서로 발행이 되며 신청하신 담당자 이메일로 발송이 됩니다.</li>
					<li><span class="bu">-</span> 궁금하신 사항은 고객센터 02-544-1191으로 연락주시기 바랍니다.</li>
				</ul>
			</div>
		</div>
		
	</div>
	<!-- //pop-mid -->
	<a href="#none" class="btn_close" data-dismiss="popup"><span class="hide">닫기</span></a>
</form>	
</div>

<!-- //세금계산서 -->
	
<!-- 현금영수증 발급 -->
<div id="popCashbillReg" class="popup type-page popup-cashreceipt">
<form name="frmCash" id="frmCash" method="POST" >
<input type="hidden" name="ORDER_CD" value="">

	<h1 class="hide">GATSBY</h1>
	<!-- pop-top -->
	<div class="pop-top">
		<h2>현금영수증 발급</h2>
	</div>
	<!-- //pop-top -->
	<!-- pop-mid -->
	<div class="pop-mid">
	
		<dl class="receipt-number">
			<dt>주문 번호</dt>
			<dd><span class="em">20105250001</span></dd>
		</dl>
		
		<div class="order-form">
			<div class="form-group">
				<div class="form-body">
					<div class="row">
						<div class="form-label">
							<label for="">현금영수증</label>
						</div>
						<div class="col col-12">
							<div class="optgroup">
								<span class="radiobox">
									<input type="radio" name="CASH_RECEIPT_GUBUN" value="1" id="chkPersonal" class="radio" />
									<label for="chkPersonal" class="lbl">개인</label>
								</span>
								<span class="radiobox">
									<input type="radio" name="CASH_RECEIPT_GUBUN" value="2" id="chkCompany" class="radio" />
									<label for="chkCompany" class="lbl">사업자</label>
								</span>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
								<input type="text" class="input" name="CASH_RECEIPT_NO" id="CASH_RECEIPT_NO" value="<c:out value="${orderInfo.cashReceiptNo}"/>" maxlength="20" placeholder="휴대전화 또는 사업자 등록번호" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="btn-box confirm">
			<button type="button" class="btn outline-green" onclick="confirmCashBill();" ><span class="txt">현금영수증 발급 신청</span></button>
		</div>
		
		<div class="guidebox" style="margin-left: 0; margin-right: 0;">
			<div class="guide-cont">
				<ul class="bu-list">
					<li><span class="bu">-</span> 정확한 정보를 입력해주시기 바랍니다.</li>
					<li><span class="bu">-</span> 궁금하신 사항은 고객센터 02-544-1191으로 연락주시기 바랍니다.</li>
				</ul>
			</div>
		</div>
		
	</div>
	<!-- //pop-mid -->
	<a href="javascript:void(0);" class="btn_close" data-dismiss="popup"><span class="hide">닫기</span></a>
</form>	
</div>
<!-- //현금영수증 -->
</body>
</html>