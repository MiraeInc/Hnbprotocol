<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<c:set var="gnbActive" value="exhibition" scope="request"/>
<meta name="decorator" content="mobile.main"/>	
<script>

// 페이지 이동
function goPage(page){
	$("#pageNo").val(page);
	
	var frm = document.exhibitionForm;
	frm.action = "${CTX}/event/exhibition/exhibitionList.do";
	frm.submit();
}

</script>
</head>
<body>
<form name="exhibitionForm" id="exhibitionForm" method="get" onsubmit="return false;">
	<input type="hidden" name="pageNo" id="pageNo" value="${schVO.pageNo}"/>
	<input type="hidden" name="exhibitionIdx" id="exhibitionIdx" value=""/> <%-- 기획전 일련번호 --%>

	<div class="content comm-event event-main">
		<page:applyDecorator  name="mobile.eventmenu" encoding="UTF-8"/>
		
		<div class="page-body">
			<div class="brand-filter">
			
				<a href="${CTX}/event/exhibition/exhibitionList.do" data-brand="all" class="<c:if test="${VO.brandIdx eq null || VO.brandIdx eq ''}">active</c:if>"><span>ALL</span></a>
				<a href="${CTX}/event/exhibition/exhibitionList.do?brandIdx=1" data-brand="gatsby" class="<c:if test="${VO.brandIdx eq 1}">active</c:if>"><span>갸스비</span></a>
				<a href="${CTX}/event/exhibition/exhibitionList.do?brandIdx=3" data-brand="bifesta" class="<c:if test="${VO.brandIdx eq 3}">active</c:if>"><span>비페스타</span></a>
				<a href="${CTX}/event/exhibition/exhibitionList.do?brandIdx=4" data-brand="lucidol" class="<c:if test="${VO.brandIdx eq 4}">active</c:if>"><span>루시도엘</span></a>
				<a href="${CTX}/event/exhibition/exhibitionList.do?brandIdx=9" data-brand="barrier" class="<c:if test="${VO.brandIdx eq 9}">active</c:if>"><span>베리어리페어</span></a>
<%--
				<a href="${CTX}/event/exhibition/exhibitionList.do?brandIdx=6" data-brand="mamabutter" class="<c:if test="${VO.brandIdx eq 6}">active</c:if>"><span>마마버터</span></a>
 --%>
				<a href="${CTX}/event/exhibition/exhibitionList.do?brandIdx=7" data-brand="dentalpro" class="<c:if test="${VO.brandIdx eq 7}">active</c:if>"><span>덴탈프로</span></a>
<%--
				<a href="${CTX}/event/exhibition/exhibitionList.do?brandIdx=8" data-brand="charley" class="<c:if test="${VO.brandIdx eq 8}">active</c:if>"><span>찰리</span></a>
				<a href="${CTX}/event/exhibition/exhibitionList.do?brandIdx=10" data-brand="gpcreate" class="<c:if test="${VO.brandIdx eq 10}">active</c:if>"><span>GPCREATE</span></a>
 --%>
			</div>
			
			<c:choose>
				<c:when test="${!empty exhibitionList}">
					<div class="event-list">
						<ul>
							<c:forEach var="list" items="${exhibitionList}" varStatus="idx">
								<c:if test="${list.device ne 'M'}">
									<li>
										<a href="${CTX}/event/exhibition/exhibitionView.do?exhibitionIdx=${list.exhibitionIdx}" class="link-detail-go">
											<span class="event-img">
												<img src="${IMGPATH}/exhibition/${list.exhibitionIdx}/${list.mExhibitionImg}" alt="${list.title}"/>
											</span>
											<p class="writing-date">${list.startDt} - ${list.endDt}</p>
										</a>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
					</c:when>
					<c:otherwise>
					<div class="preparing">
						<div class="inner-box">
							<img src="${CTX}/images/${DEVICE}/common/txt_comingsoon.png" alt="coming soon!">
							<p class="big-txt">기획전 준비 중 입니다.</p>
							<p class="small-txt">현재 기획전 준비 중 입니다. 알찬 기획전으로 찾아 뵙겠습니다.</p>
						</div>
					</div>
					</c:otherwise>
				</c:choose>
		</div>
		
		<c:if test="${!empty exhibitionList}">
			<div class="helper-box">
				<div class="helper-box-inner inset">
					<ul>
						<li>기획전 조기 종료 시 다른 기획전으로 대체 되거나 취소 될 수 있습니다. 이점 양해 부탁드립니다.</li>
					</ul>
				</div>
			</div>
		</c:if>
    </div>
</form>
</body>
</html>