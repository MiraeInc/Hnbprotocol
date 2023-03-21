<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_activity" />
<meta name="menu_no" content="mypage_060" />
<script>

$(function(){
	
	//후기사진 슬라이더
	$('.photo-slider').lightSlider({
        item:1,
        loop:false,
        pager: false,
        slideMargin: 0,
        addClass: 'review-photo'
    });		
	
});

// 리뷰 삭제
function goDelete(){
	if(confirm("정말 삭제하시겠습니까?\n삭제하시면 적립된 포인트는 회수됩니다.")){
		location.href='${CTX}/mypage/review/reviewDelete.do?reviewIdx=${detail.reviewIdx}&reviewPoint=${detail.reviewPoint}&layerType=${VO.layerType}';
	}
}

</script>
</head>
<body>
	<div class="content comm-order comm-mypage mypage-review">
		<div class="page-body">
			<div class="review-view">
				<div class="order-goods">
					<ul>
						<li>
							<div class="item">
								<div class="item-view">
									<div class="view-thumb">
										<c:set var="imgSplit" value="${fn:split(detail.mainFile ,'.')}"/>
										<img src="${IMGPATH}/goods/${detail.goodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}" alt="상품 이미지">
									</div>
									<div class="view-info">
										<div class="rate">
											<span class="rate-star" data-rate="${detail.rating}"><i></i></span>
										</div>
										<p class="name">${detail.goodsNm}</p>
									</div>
								</div>
							</div>
						</li>
					</ul>
				</div>	
					
				<div class="view-hair">
					<ul>
						<c:if test="${!empty detail.hairStyle}"> 
						<li>모발 길이 <span class="c"><span>${detail.hairStyleNm}</span></span></li>
						</c:if>
						<c:if test="${!empty detail.hairType}">
						<li>모발 타입 <span class="c"><span>${detail.hairTypeNm}</span></span></li>
						</c:if>
					</ul>
				</div>
					
				<div class="view-photo">
					<ul class="photo-slider">
						<c:if test="${detail.img1 ne null && detail.img1 ne ''}">
						<li>
							<c:set var="imgSplit" value="${fn:split(detail.img1 ,'.')}"/>
							<img src="${IMGPATH}/review/${detail.reviewIdx}/${imgSplit[0]}_T200.${imgSplit[1]}">
						</li>
						</c:if>
						<c:if test="${detail.img2 ne null && detail.img2 ne ''}">
						<li>
							<c:set var="imgSplit" value="${fn:split(detail.img2 ,'.')}"/>
							<img src="${IMGPATH}/review/${detail.reviewIdx}/${imgSplit[0]}_T200.${imgSplit[1]}">
						</li>
						</c:if>
						<c:if test="${detail.img3 ne null && detail.img3 ne ''}">
						<li>
							<c:set var="imgSplit" value="${fn:split(detail.img3 ,'.')}"/>
							<img src="${IMGPATH}/review/${detail.reviewIdx}/${imgSplit[0]}_T200.${imgSplit[1]}">
						</li>
						</c:if>
						<c:if test="${detail.img4 ne null && detail.img4 ne ''}">
						<li>
							<c:set var="imgSplit" value="${fn:split(detail.img4 ,'.')}"/>
							<img src="${IMGPATH}/review/${detail.reviewIdx}/${imgSplit[0]}_T200.${imgSplit[1]}">
						</li>
						</c:if>
					</ul>
				</div>
					
				<div class="view-cont">${detail.reviewDesc}</div>	
			</div>
			<c:if test="${IS_LOGIN eq true}">
				<c:if test="${detail.memberIdx eq memberIdx}">
					<div class="btn-box confirm">
						<button type="button" class="btn" onclick="goDelete();"><span class="txt">삭제</span></button>
						<a href="${CTX}/mypage/review/reviewWrite.do?statusFlag=U&orderDetailIdx=${detail.orderDetailIdx}&winnerIdx=${detail.winnerIdx}&reviewIdx=${detail.reviewIdx}&layerType=${VO.layerType}" class="btn outline-green"><span class="txt">수정</span></a>
					</div>
				</c:if>
			</c:if>
		</div>
	</div>
</body>
</html>