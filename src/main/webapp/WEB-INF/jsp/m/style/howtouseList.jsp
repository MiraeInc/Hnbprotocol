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
	
	var frm = document.howtouseForm;
	frm.action = "${CTX}/style/howtouseList.do";
	frm.submit();
}

</script>
</head>
<body>
<form name="howtouseForm" id="howtouseForm" method="get" onsubmit="return false;">
	<input type="hidden" name="pageNo" id="pageNo" value="${schVO.pageNo}"/>
	
	<div class="content comm-styleg styleg-howto">
		<page:applyDecorator  name="mobile.stylemenu" encoding="UTF-8"/>
		
		<div class="page-body">
			<div class="tab-style">
				<a href="${CTX}/style/tipList.do" class="tab-link"><span>스타일링 팁</span></a>
				<a href="${CTX}/style/howtouseList.do" class="tab-link active"><span>상품 사용법</span></a>
			</div>

			<div class="brand-filter">
				<c:if test="${howtoBrandCnt.allCnt ne 0}">
				<a href="${CTX}/style/howtouseList.do" data-brand="all" class="<c:if test="${VO.brandIdx eq null || VO.brandIdx eq ''}">active</c:if>"><span>ALL</span></a>
				</c:if>
				<c:if test="${howtoBrandCnt.gbCnt ne 0}">
				<a href="${CTX}/style/howtouseList.do?brandIdx=1" data-brand="gatsby" class="<c:if test="${VO.brandIdx eq 1}">active</c:if>"><span>갸스비</span></a>
				</c:if>
				<c:if test="${howtoBrandCnt.bfCnt ne 0}">
				<a href="${CTX}/style/howtouseList.do?brandIdx=3" data-brand="bifesta" class="<c:if test="${VO.brandIdx eq 3}">active</c:if>"><span>비페스타</span></a>
				</c:if>
				<c:if test="${howtoBrandCnt.ldCnt ne 0}">
				<a href="${CTX}/style/howtouseList.do?brandIdx=4" data-brand="lucidol" class="<c:if test="${VO.brandIdx eq 4}">active</c:if>"><span>루시도엘</span></a>
				</c:if>
				<c:if test="${howtoBrandCnt.brCnt ne 0}">
				<a href="${CTX}/style/howtouseList.do?brandIdx=9" data-brand="barrier" class="<c:if test="${VO.brandIdx eq 9}">active</c:if>"><span>베리어리페어</span></a>
				</c:if>
<%--
				<c:if test="${howtoBrandCnt.mmCnt ne 0}">
				<a href="${CTX}/style/howtouseList.do?brandIdx=6" data-brand="mamabutter" class="<c:if test="${VO.brandIdx eq 6}">active</c:if>"><span>마마버터</span></a>
				</c:if>
 --%>
				<c:if test="${howtoBrandCnt.dpCnt ne 0}">
				<a href="${CTX}/style/howtouseList.do?brandIdx=7" data-brand="dentalpro" class="<c:if test="${VO.brandIdx eq 7}">active</c:if>"><span>덴탈프로</span></a>
				</c:if>
<%--
				<c:if test="${howtoBrandCnt.clCnt ne 0}">
				<a href="${CTX}/style/howtouseList.do?brandIdx=8" data-brand="charley" class="<c:if test="${VO.brandIdx eq 8}">active</c:if>"><span>찰리</span></a>
				</c:if>
				<c:if test="${howtoBrandCnt.gpCnt ne 0}">
				<a href="${CTX}/style/howtouseList.do?brandIdx=10" data-brand="gpcreate" class="<c:if test="${VO.brandIdx eq 10}">active</c:if>"><span>GPCREATE</span></a>
				</c:if>
 --%>
			</div>
		
			<div class="event-list">
				<ul>
					<c:forEach var="list" items="${howtouseList}" varStatus="idx">
						<li>
							<a href="${CTX}/style/howtouseView.do?howtouseIdx=${list.howtouseIdx}">
								<span class="event-img">
									<img src="${IMGPATH}/howtouse/${list.howtouseIdx}/${list.useImg}" alt="${list.title}"/>
								</span>
								<p class="big-txt dotdotdot">${list.title}</p>
								<c:if test="${list.shortInfo ne null && list.shortInfo ne ''}">
		                        	<p class="writing-info">${list.shortInfo}</p>
		                        </c:if>
							</a>
						</li>
					</c:forEach>
				</ul>
			</div>
			
			<div class="pagin-nav">
				<c:out value="${page.pageStr}" escapeXml="false"/>
			</div>
		</div>
	</div>
</form>
</body>
</html>