<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<c:set var="gnbActive" value="event" scope="request"/>
<meta name="decorator" content="mobile.main"/>	
<script>

// 페이지 이동
function goPage(page){
	$("#pageNo").val(page);
	
	var frm = document.eventForm;
	frm.action = "${CTX}/event/event/eventList.do";
	frm.submit();
}

</script>
</head>
<body>
<form name="eventForm" id="eventForm" method="get" onsubmit="return false;">
	<input type="hidden" name="pageNo" id="pageNo" value="${schVO.pageNo}"/>
	<input type="hidden" name="eventIdx" id="eventIdx" value=""/>
	
	<div class="content comm-event event-main">
	    <page:applyDecorator  name="mobile.eventmenu" encoding="UTF-8"/>
	
		<div class="page-body">
			<div class="brand-filter">
				<a href="${CTX}/event/event/eventList.do" data-brand="all" class="<c:if test="${VO.brandIdx eq null || VO.brandIdx eq ''}">active</c:if>"><span>ALL</span></a>
				<a href="${CTX}/event/event/eventList.do?brandIdx=1" data-brand="gatsby" class="<c:if test="${VO.brandIdx eq 1}">active</c:if>"><span>갸스비</span></a>
				<a href="${CTX}/event/event/eventList.do?brandIdx=3" data-brand="bifesta" class="<c:if test="${VO.brandIdx eq 3}">active</c:if>"><span>비페스타</span></a>
				<a href="${CTX}/event/event/eventList.do?brandIdx=4" data-brand="lucidol" class="<c:if test="${VO.brandIdx eq 4}">active</c:if>"><span>루시도엘</span></a>
				<a href="${CTX}/event/event/eventList.do?brandIdx=9" data-brand="barrier" class="<c:if test="${VO.brandIdx eq 9}">active</c:if>"><span>베리어리페어</span></a>
<%--
				<a href="${CTX}/event/event/eventList.do?brandIdx=6" data-brand="mamabutter" class="<c:if test="${VO.brandIdx eq 6}">active</c:if>"><span>마마버터</span></a>
 --%>
				<a href="${CTX}/event/event/eventList.do?brandIdx=7" data-brand="dentalpro" class="<c:if test="${VO.brandIdx eq 7}">active</c:if>"><span>덴탈프로</span></a>
<%--
				<a href="${CTX}/event/event/eventList.do?brandIdx=8" data-brand="charley" class="<c:if test="${VO.brandIdx eq 8}">active</c:if>"><span>찰리</span></a>
				<a href="${CTX}/event/event/eventList.do?brandIdx=10" data-brand="gpcreate" class="<c:if test="${VO.brandIdx eq 10}">active</c:if>"><span>GPCREATE</span></a>
 --%>
			</div>
			
			<c:choose>
				<c:when test="${!empty ingEventList}">
					<div class="event-list">
						<ul>
							<c:forEach var="list" items="${ingEventList}" varStatus="idx">
								<c:if test="${list.device ne 'P'}">
								<li> <%-- 종료 이벤트 : class="event-end" --%>
									<c:choose>
										<c:when test="${list.moLinkYn eq 'N'}">
											<a href="${CTX}/event/event/eventView.do?eventIdx=${list.eventIdx}" class="link-detail-go">
										</c:when>
										<c:when test="${list.moLinkYn eq 'Y'}">
											<a href="${list.moLinkUrl}" <c:if test="${list.moLinkWindow eq 'N'}">target="_blank"</c:if> class="link-detail-go">
										</c:when>
									</c:choose>
										<span class="event-img">
											<img src="${IMGPATH}/event/${list.eventIdx}/${list.mEventImg}" alt="${list.title}"/>
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
							<p class="big-txt">이벤트 준비 중 입니다.</p>
							<p class="small-txt">현재 이벤트 준비 중 입니다. 알찬 이벤트로 찾아 뵙겠습니다.</p>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
		
		<c:if test="${!empty ingEventList}">
			<div class="helper-box">
				<div class="helper-box-inner inset">
					<ul>
						<li>이벤트 조기 종료 시 다른 이벤트로 대체 되거나 취소 될 수 있습니다. 이점 양해 부탁드립니다.</li>
					</ul>
				</div>
			</div>
		</c:if>
	</div>
</form>

<script type="text/javascript">
(function(window, document, $){
    //이벤트 탭
    $.touchflow({
        wrap: '.tab-menu',
        container: '.menu-inner',
        list: '.menu-list',
        tablink: '.menu-link'
    });
})(window, document, jQuery);
</script>

</body>
</html>