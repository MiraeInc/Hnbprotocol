<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_activity" />
<meta name="menu_no" content="mypage_050" />
<script>

// 페이지 이동
function goPage(page) {
	$("#pageNo").val(page);

	var frm = document.sampleForm;
	frm.action = "${CTX}/mypage/sample/sampleList.do";
	frm.submit();
}

</script>
</head>
<body>
<form name="sampleForm" id="sampleForm" method="post" onsubmit="return false;">
	<input type="hidden" name="pageNo" id="pageNo" value="${schVO.pageNo}"/>
	
	<div class="content comm-order comm-mypage mypage-sample">

		<div class="page-body">
			<div class="order-form">
				<c:choose>
					<c:when test="${fn:length(sampleList) > 0}">
						<div class="form-group">
							<div class="form-body">
								<div class="order-goods">
									<ul>
										<c:forEach var="list1" items="${sampleList}" varStatus="idx1">
										<li>
											<div class="item">
												<div class="item-view">
													<div class="view-thumb">
														<c:choose>
															<c:when test="${!empty list1.wGoodsIdx}">
																<c:set var="imgSplit" value="${fn:split(list1.wMainFile ,'.')}" />
																<img src="${IMGPATH}/goods/${list1.wGoodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}" alt="제품썸네일 이미지" data-source="${IMGPATH}/goods/${list1.wGoodsIdx}/${list1.wMainFile}">
															</c:when>
															<c:otherwise>
																<c:set var="imgSplit" value="${fn:split(list1.mainFile ,'.')}" />
																<img src="${IMGPATH}/goods/${list1.goodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}" alt="제품썸네일 이미지" data-source="${IMGPATH}/goods/${list1.goodsIdx}/${list1.mainFile}">
															</c:otherwise>
														</c:choose>
													</div>
													<div class="view-info">
														<p class="date">${list1.sampleDt}</p>
														<div class="badge-box">
															<c:if test="${list1.opoYn eq 'Y' }" ><span class="badge type1">1+1</span></c:if>
															<c:if test="${list1.tpoYn eq 'Y' }" ><span class="badge type2">2+1</span></c:if>
															<c:if test="${list1.newYn eq 'Y' }" ><span class="badge type3">NEW</span></c:if>
															<c:if test="${list1.bestYn eq 'Y' }" ><span class="badge type4">BEST</span></c:if>
															<c:if test="${list1.saleiconYn eq 'Y' }" ><span class="badge type5">SALE</span></c:if>
															<c:if test="${list1.pointiconYn eq 'Y' }" ><span class="badge type3"><i>POINT</i> X2</span></c:if>
														</div>
														<c:choose>
															<c:when test="${list1.brandIdx eq 1}"><p class="text-gatsby">GATSBY</p></c:when>
															<c:when test="${list1.brandIdx eq 3}"><p class="text-bifesta">BIFESTA</p></c:when>
															<c:when test="${list1.brandIdx eq 4}"><p class="text-lucidol">LUCIDO-L</p></c:when>
															<c:when test="${list1.brandIdx eq 6}"><p class="text-mama">MAMA BUTTER</p></c:when>
															<c:when test="${list1.brandIdx eq 7}"><p class="text-dental">DENTALPRO</p></c:when>
															<c:when test="${list1.brandIdx eq 8}"><p class="text-charley">CHARLEY</p></c:when>
														</c:choose>
														<p class="name">${list1.sampleTitle}</p>
													</div>
												</div>
												<div class="item-payment">
													<dl>
														<dd class="full">
															<div class="btn-box">
																<c:set var="count1" value="0" /> 
																<c:forEach var="list2" items="${sampleReplyList}" varStatus="idx2">
																<c:if test="${list1.sampleIdx eq list2.sampleIdx}">
																<div class="col col-12">
																	<span class="btn full gray"><span class="txt">신청완료</span></span>
																</div>
																<c:set var="count1" value="${count1+1}"/>
																</c:if>
																</c:forEach>
																
																<c:if test="${count1 eq 0}">
																	<c:if test="${list1.status eq 'ING'}">
																	<div class="col col-12">
																		<a href="${CTX}/style/sampleView.do?sampleIdx=${list1.sampleIdx}" class="btn full outline-green ico-chev"><span class="txt">신청하기</span></a>
																	</div>
																	</c:if>
																</c:if>
																
																<c:if test="${list1.winnerYn eq 'Y'}">
																<div class="col col-12">
																	<a href="${CTX}/cscenter/notice/noticeView.do?noticeIdx=${list1.noticeIdx}" class="btn full ico-chev"><span class="txt">당첨자 보기</span></a>
																</div>
																</c:if>
																<c:if test="${(list1.winnerIdx ne null && list1.winnerIdx ne '') && (list1.reviewWinnerIdx eq null || list1.reviewWinnerIdx eq '')}">
																<div class="col col-6 col-6-offset">
																	<a href="${CTX}/mypage/review/reviewWrite.do?statusFlag=I&orderDetailIdx=&winnerIdx=${list1.winnerIdx}&goodsIdx=${list1.goodsIdx}&layerType=sample" class="btn full btn-write-review"><span class="txt">후기작성</span></a>
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
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="form-group">
							<div class="form-body">
								<div class="no-contents">
									<p>진행 내역이 없습니다.</p>
								</div>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			
			<c:if test="${fn:length(sampleList) > 0}">
				<div class="pagin-nav">
					<c:out value="${page.pageStr}" escapeXml="false" />
				</div>
			
				<div class="guidebox">
					<div class="guide-title">
						<h3 class="tit"><span class="i"><img src="${CTX}/images/${DEVICE}/common/ico_helper_alert.png" alt="" /></span> 이 달의 정품 신청 안내</h3>
					</div>
					<div class="guide-sample">
						<img src="${CTX}/images/${DEVICE}/contents/img_guide_sample.png" alt="" />
						<ul class="bu-list">
							<li><span class="bu">-</span> 한 상품에 대해 1회만 참여 가능합니다.</li>
							<li><span class="bu">-</span> 매월 1회 추첨을 통해 당첨되신 분에게 신청하신 체험 상품을 무료로 보내 드립니다.</li>
							<li><span class="bu">-</span> 당첨자는 공지사항을 통해 고지해드립니다.</li>
							<li><span class="bu">-</span> 당첨되신 분은 체험 상품을 사용해 보시고 후기를 작성해 주셔야 합니다.</li>
						</ul>
					</div>
				</div>
			</c:if>
		</div>
	</div>
</form>
</body>
</html>