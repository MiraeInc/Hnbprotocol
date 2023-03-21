<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main"/>	
<script>

$(function(){
	$(".dotdotdot").dotdotdot({
		watch: true,
		height: 63,
		wrap: "letter"
	});
});

// 페이지 이동
function goPage(page){
	$("#pageNo").val(page);
	
	var frm = document.tipForm;
	frm.action = "${CTX}/style/tipList.do";
	frm.submit();
}

</script>
</head>
<body>
<form name="tipForm" id="tipForm" method="get" onsubmit="return false;">
	<input type="hidden" name="pageNo" id="pageNo" value="${schVO.pageNo}"/>
	
	<div class="content comm-styleg styleg-tip">
		<page:applyDecorator  name="mobile.stylemenu" encoding="UTF-8"/> 
		
		<div class="page-body">
			<div class="tab-style">
				<a href="${CTX}/style/tipList.do" class="tab-link active"><span>스타일링 팁</span></a>
				<a href="${CTX}/style/howtouseList.do" class="tab-link"><span>상품 사용법</span></a>
			</div>

			<div class="brand-filter">
				<c:if test="${tipBrandCnt.allCnt ne 0}">
				<a href="${CTX}/style/tipList.do" data-brand="all" class="<c:if test="${VO.brandIdx eq null || VO.brandIdx eq ''}">active</c:if>"><span>ALL</span></a>
				</c:if>
				<c:if test="${tipBrandCnt.gbCnt ne 0}">
				<a href="${CTX}/style/tipList.do?brandIdx=1" data-brand="gatsby" class="<c:if test="${VO.brandIdx eq 1}">active</c:if>"><span>갸스비</span></a>
				</c:if>
				<c:if test="${tipBrandCnt.bfCnt ne 0}">
				<a href="${CTX}/style/tipList.do?brandIdx=3" data-brand="bifesta" class="<c:if test="${VO.brandIdx eq 3}">active</c:if>"><span>비페스타</span></a>
				</c:if>
				<c:if test="${tipBrandCnt.ldCnt ne 0}">
				<a href="${CTX}/style/tipList.do?brandIdx=4" data-brand="lucidol" class="<c:if test="${VO.brandIdx eq 4}">active</c:if>"><span>루시도엘</span></a>
				</c:if>
				<c:if test="${tipBrandCnt.brCnt ne 0}">
				<a href="${CTX}/style/tipList.do?brandIdx=9" data-brand="barrier" class="<c:if test="${VO.brandIdx eq 9}">active</c:if>"><span>베리어리페어</span></a>
				</c:if>
<%--
				<c:if test="${tipBrandCnt.mmCnt ne 0}">
				<a href="${CTX}/style/tipList.do?brandIdx=6" data-brand="mamabutter" class="<c:if test="${VO.brandIdx eq 6}">active</c:if>"><span>마마버터</span></a>
				</c:if>
 --%>
				<c:if test="${tipBrandCnt.dpCnt ne 0}">
				<a href="${CTX}/style/tipList.do?brandIdx=7" data-brand="dentalpro" class="<c:if test="${VO.brandIdx eq 7}">active</c:if>"><span>덴탈프로</span></a>
				</c:if>
<%--
				<c:if test="${tipBrandCnt.clCnt ne 0}">
				<a href="${CTX}/style/tipList.do?brandIdx=8" data-brand="charley" class="<c:if test="${VO.brandIdx eq 8}">active</c:if>"><span>찰리</span></a>
				</c:if>
				<c:if test="${tipBrandCnt.gpCnt ne 0}">
				<a href="${CTX}/style/tipList.do?brandIdx=10" data-brand="gpcreate" class="<c:if test="${VO.brandIdx eq 10}">active</c:if>"><span>GPCREATE</span></a>
				</c:if>
 --%>
			</div>
			
			<div class="tips-list">
				<c:forEach var="list" items="${styleTipList}" varStatus="idx">
					<c:if test="${list.tipGubun eq 'I'}">	<%-- 이미지 --%>
					<div class="tips-item">
						<c:set var="link" value=""/>
						<c:choose>
							<c:when test="${list.linkYn eq 'Y'}">
								<c:set var="link" value="${list.moLinkUrl}"/>
							</c:when>
							<c:otherwise>
								<c:set var="link" value="${CTX}/style/tipView.do?tipIdx=${list.tipIdx}"/>
							</c:otherwise>
						</c:choose>
						
						<div class="tips-thumb" onclick="location.href='${link}'">
							<img src="${IMGPATH}/tip/${list.tipIdx}/${list.tipImg}" alt="${list.tipTitle}"/>
						</div>
						<div class="tips-prod">
							<a href="${CTX}/product/productView.do?goodsCd=${list.goodsCd}&choiceCateIdx=${list.choiceCateIdx}" class="prod-link">
								<div class="prod-thumb">
									<c:set var="imgSplit" value="${fn:split(list.mainFile ,'.') }"/>
									<img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}">
								</div>
								<div class="prod-info">
									<p class="prod-desc">${list.tipTitle}</p>
									<c:if test="${(list.goodsIdx ne null && list.goodsIdx ne '') && (list.goodsNm ne null && list.goodsNm ne '')}">
									<p class="prod-name">${list.goodsNm}</p>
									<div class="prod-price">
										<fmt:formatNumber var="discountRate" value="${list.discountRate}" type="number" maxFractionDigits="0"/>
										<c:if test="${discountRate != 0}" >
											<del class="price-origin"><fmt:formatNumber value="${list.price}" /></del>
											<span class="price-rate">${discountRate}%</span>
										</c:if>
										<span class="price-sale"><fmt:formatNumber value="${list.discountPrice}" /></span>
									</div>
									</c:if>
								</div>
							</a>
						</div>
					</div>
					</c:if>
					<c:if test="${list.tipGubun eq 'V'}">	<%-- 동영상 --%>
					<div class="tips-item">
						<div class="tips-thumb">
							<iframe width="100%" src="https://www.youtube.com/embed/${list.videoUrl}?rel=0&autohide=1&autoplay=0&showinfo=0&controls=0" frameborder="0" title="" marginheight="0" marginwidth="0" allowfullscreen></iframe>
						</div>
						<div class="tips-prod">
							<a href="${CTX}/product/productView.do?goodsCd=${list.goodsCd}&choiceCateIdx=${list.choiceCateIdx}">
								<div class="prod-info">
									<p class="prod-desc">${list.tipTitle}</p>
									<c:if test="${(list.goodsIdx ne null && list.goodsIdx ne '') && (list.goodsNm ne null && list.goodsNm ne '')}">
									<p class="prod-name">${list.goodsNm}</p>
									<div class="prod-price">
										<fmt:formatNumber var="discountRate" value="${list.discountRate}" type="number" maxFractionDigits="0"/>
										<c:if test="${discountRate != 0}" >
											<del class="price-origin"><fmt:formatNumber value="${list.price}" /></del>
											<span class="price-rate">${discountRate}%</span>
										</c:if>
										<span class="price-sale"><fmt:formatNumber value="${list.discountPrice}" /></span>
									</div>
									</c:if>
								</div>
							</a>
						</div>
					</div>
					</c:if>
				</c:forEach>
			</div>
			
			<div class="pagin-nav">
				<c:out value="${page.pageStr}" escapeXml="false"/>
			</div>
		</div>
	</div>
</form>
</body>
</html>