<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_activity" />
<meta name="menu_no" content="mypage_060" />
<script>

// 페이지 이동
function goPage(page){
	$("#pageNo").val(page);
	
	var frm = document.reviewForm;
	frm.action = "${CTX}/mypage/review/noWriteReviewList.do";
	frm.submit();
}

// 작성 한 후기
function changeTab(){
	var frm = document.reviewForm;
	frm.action = "${CTX}/mypage/review/writeReviewList.do";
	frm.submit();
}

</script>
</head>
<body>
<form name="reviewForm" id="reviewForm" method="get" onsubmit="return false;">
	<input type="hidden" name="pageNo" id="pageNo" value="${schVO.pageNo}"/>
	
	<div class="content comm-order comm-mypage mypage-review">
		<div class="page-filter">
			<ul>
				<li><a href="javascript:" class="active">작성 가능한 후기 (${noWriteCount})</a></li>
				<li><a href="javascript:" onclick="changeTab();">작성한 후기 (${writeCount})</a></li>
			</ul>
		</div>
		
        <div class="page-body">
			<div class="order-form">
				<c:choose>
					<c:when test="${fn:length(noWriteList) > 0}">
						<div class="form-group">
							<div class="form-body">
								<div class="order-goods">
									<ul>
										<c:forEach var="list" items="${noWriteList}" varStatus="idx">
										<li>
											<div class="item">
												<div class="item-view">
													<div class="view-thumb">
														<c:set var="imgSplit" value="${fn:split(list.imgFile ,'.')}"/>
														<img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}" alt="">
													</div>
													<div class="view-info">
														<div class="badge-box">
															<c:if test="${list.opoYn eq 'Y' }" ><span class="badge type1">1+1</span></c:if>
															<c:if test="${list.tpoYn eq 'Y' }" ><span class="badge type2">2+1</span></c:if>
															<c:if test="${list.newYn eq 'Y' }" ><span class="badge type3">NEW</span></c:if>
															<c:if test="${list.bestYn eq 'Y' }" ><span class="badge type4">BEST</span></c:if>
															<c:if test="${list.saleiconYn eq 'Y' }" ><span class="badge type5">SALE</span></c:if>
															<c:if test="${list.pointiconYn eq 'Y' }" ><span class="badge type3"><i>POINT</i> X2</span></c:if>
														</div>
														<c:choose>
															<c:when test="${list.brandIdx eq 1}"><p class="text-gatsby">GATSBY</p></c:when>
															<c:when test="${list.brandIdx eq 3}"><p class="text-bifesta">BIFESTA</p></c:when>
															<c:when test="${list.brandIdx eq 4}"><p class="text-lucidol">LUCIDO-L</p></c:when>
															<c:when test="${list.brandIdx eq 6}"><p class="text-mama">MAMA BUTTER</p></c:when>
															<c:when test="${list.brandIdx eq 7}"><p class="text-dental">DENTALPRO</p></c:when>
															<c:when test="${list.brandIdx eq 8}"><p class="text-charley">CHARLEY</p></c:when>
														</c:choose>
														<p class="name">${list.goodsNm}</p>
														<%-- <p class="price"><fmt:formatNumber value="${list.price}" pattern="#,###"/><span class="qty"> / 수량: ${list.orderCnt}</span></p> --%>
													</div>
												</div>
												<div class="item-payment">
													<dl>
														<dd class="full">
															<div class="btn-box">
																<div class="col col-6">
																	<a href="${CTX}/mypage/review/reviewWrite.do?statusFlag=I&orderDetailIdx=${list.orderDetailIdx}&winnerIdx=${list.winnerIdx}&goodsIdx=${list.goodsIdx}&layerType=review" class="btn full btn-write-review type-left"><span class="txt">후기작성</span></a>
																</div>
															</div>
														</dd>
													</dl>
												</div>
											</div>
										</li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="form-group">
							<div class="no-contents">
								<p>작성 가능한 내역이 없습니다.</p>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			
			<c:if test="${fn:length(noWriteList) > 0}">
				<div class="pagin-nav">
					<c:out value="${page.pageStr}" escapeXml="false" />
				</div>
			</c:if>
	
			<div class="helper-box">
				<div class="helper-box-inner">
					<strong class="tit" data-icon="pen">상품 후기 작성 안내</strong>
					<ul>
						<li>다른 분들의 쇼핑에 도움이 될 수 있는 솔직한 후기를 올려주세요.</li>
						<li>후기를 작성해주시면 포인트를 적립해 드립니다.<br/> (일반 후기 <span class="em">100P</span>, 포토후기 <span class="em">500P</span>)</li>
						<li>후기 작성은 [발송완료] 단계 이후부터 작성 가능합니다.</li>
						<li>후기 작성은 구매하신 상품의 구매 확정 후 1회에 한해 작성 가능합니다.</li>
						<li>구매하신 상품과 관련 없는 내용을 작성할 경우 임의 삭제 될 수 있습니다.(포인트 지급 불가)</li>
						<li>포토후기의 경우, 구매하신 상품과 관련 없는 이미지를 등록한 경우 임의 삭제 될 수 있습니다.(포인트 지급 불가)</li>
						<li>비방, 욕설 등 적절치 않은 단어 포함 시 임의 삭제 됩니다. (포인트 지급 불가)</li>
						<li>개인정보보호법에 의거 개인의 연락처, 카드 번호등의 개인정보는 작성치 말아 주십시오.</li>
						<li>작성해 주신 후기의 저작권은 (주)엠와이지비 에 있습니다.</li>
					</ul>
				</div>
			</div>
		</div>    
	</div>
</form>
</body>
</html>