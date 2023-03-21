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
	frm.action = "${CTX}/mypage/review/writeReviewList.do";
	frm.submit();
}

// 작성 가능 한 후기
function changeTab(){
	var frm = document.reviewForm;
	frm.action = "${CTX}/mypage/review/noWriteReviewList.do";
	frm.submit();
}

</script>
</head>
<body>
<form name="reviewForm" id="reviewForm" method="get" onsubmit="return false;">
	<input type="hidden" name="pageNo" id="pageNo" value="${schVO.pageNo}"/>
	
	<div class="content comm-mypage mypage-review-write">
		<div class="page-filter">
			<ul>
				<li><a href="javascript:" onclick="changeTab();">작성 가능한 후기 (${noWriteCount})</a></li>
				<li><a href="javascript:" class="active">작성한 후기 (${writeCount})</a></li>
			</ul>
		</div>
		
		<div class="page-body">
			<div class="order-form">
			<c:choose>
				<c:when test="${fn:length(writeList) > 0}">
				<div class="review-list">
					<ul>
						<c:forEach var="list" items="${writeList}" varStatus="idx">
						<li>
							<div class="item" onclick="location.href='${CTX}/mypage/review/reviewDetail.do?reviewIdx=${list.reviewIdx}&layerType=review'">
								<div class="item-status">
									<div class="badge-box">
										<c:choose>
											<c:when test="${list.img1 ne null}">
												<span class="badge type-photo">포토</span>
											</c:when>
											<c:otherwise>
												<span class="badge type-text">일반</span>
											</c:otherwise>
										</c:choose>
									</div>
									<p class="odate"><c:if test="${list.orderDt ne null}">주문일자 : ${list.orderDt}</c:if></p>
								</div>
								<div class="item-title">
									<div class="thumb">
										<c:choose>
											<c:when test="${list.img1 ne null}">
												<c:set var="imgSplit1" value="${fn:split(list.img1 ,'.')}"/>
												<img src="${IMGPATH}/review/${list.reviewIdx}/${imgSplit1[0]}_T90.${imgSplit1[1]}" alt="">
											</c:when>
											<c:otherwise>
												<c:set var="imgSplit" value="${fn:split(list.mainFile ,'.')}"/>
												<img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}" alt="">
											</c:otherwise>
										</c:choose>
									</div>
									<div class="product">
										<div class="">
											<span class="rate-star" data-rate="${list.rating}"><i></i></span>
										</div>
										<p class="name">${list.goodsNm}</p>
									</div>
								</div>
								<div class="item-detail">
									<c:out value="${list.reviewDesc}" escapeXml="false"/>
								</div>
								<p class="item-date">${list.regDt}</p>
							</div>
						</li>
						</c:forEach>
					</ul>
				</div>

				<div class="pagin-nav">
					<c:out value="${page.pageStr}" escapeXml="false" />
				</div>
				
				</c:when>
				<c:otherwise>
					<div class="form-group">
						<div class="no-contents">
							<p>작성한 후기 내역이 없습니다.</p>
						</div>
					</div>
				</c:otherwise>
			</c:choose>

			<div class="helper-box">
				<div class="helper-box-inner">
					<strong class="tit" data-icon="pen">상품 후기 작성 안내</strong>
					<ul>
						<li>다른 분들의 쇼핑에 도움이 될 수 있는 솔직한 후기를 올려주세요.</li>
						<li>후기를 작성해주시면 포인트를 적립해 드립니다. (일반 후기 <span class="em">100P</span>, 포토후기 <span class="em">500P</span>)</li>
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
	</div>
</form>
</body>
</html>