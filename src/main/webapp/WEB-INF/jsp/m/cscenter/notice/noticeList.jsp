<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main"/>	

<script>

// 페이지 이동
function goPage(page){
	$("#pageNo").val(page);

	var frm = document.noticeForm;
	frm.action = "${CTX}/cscenter/notice/noticeList.do";
	frm.submit();
}

// 검색
function goSearch(){
	$("#pageNo").val(1);
	
	var frm = document.noticeForm;
	frm.action = "${CTX}/cscenter/notice/noticeList.do";
	frm.submit();
}
</script>

</head>
<body>
<form name="noticeForm" id="noticeForm" method="get" onsubmit="return false;">
	<input type="hidden" name="pageNo" id="pageNo" value="${schVO.pageNo}"/>
	
	<div class="content comm-customer customer-notice">
	
		<page:applyDecorator  name="mobile.csmenu" encoding="UTF-8"/>

        <div class="page-tab type2">
			<ul>
				<li class="<c:if test="${schVO.schCheck eq null || schVO.schCheck eq ''}">active</c:if>">
					<a href="javascript:void(0);" <c:if test="${schVO.schCheck ne null && schVO.schCheck ne ''}">onclick="location.href='${CTX}/cscenter/notice/noticeList.do';"</c:if>>전체</a>
				</li>
				<c:forEach var="list" items="${noticeType}" varStatus="idx">
				<li class="<c:if test="${list.commonCd eq schVO.schCheck}">active</c:if>">
					<a href="javascript:void(0);" <c:if test="${schVO.schCheck ne list.commonCd}">onclick="location.href='${CTX}/cscenter/notice/noticeList.do?schCheck=${list.commonCd}';"</c:if>>${list.cdNm}</a>
				</li>
				</c:forEach>
			</ul>
		</div>

		<div class="page-body">
			<div class="box-search">
				<div class="row">
					<div class="col col-4">
						<div class="form-control">
							<div class="opt_select">
								<select name="schSubType" id="schSubType">
									<option value="ALL" <c:if test="${schVO.schSubType eq null || schVO.schSubType eq ''}">selected</c:if>>전체</option>
									<option value="TITLE" <c:if test="${schVO.schSubType eq 'TITLE'}">selected</c:if>>제목</option>
									<option value="CONTENT" <c:if test="${schVO.schSubType eq 'CONTENT'}">selected</c:if>>내용</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col col-8">
						<div class="form-control search-keyword">
							<input type="text" name="schValue" id="schValue" class="input input-search-keyword" placeholder="키워드를 입력해주세요." value="${schVO.schValue}">
							<input type="submit" class="btn-search-keyword" value="검색" onclick="goSearch();">
						</div>
					</div>
				</div>
				
				<c:if test="${schVO.schValue ne null && schVO.schValue ne ''}">
					<div class="result-search-keyword">
						<p>“<span>${schVO.schValue}</span>”에 대해 총 <span>${totalCount+topCount}</span>건 검색 되었습니다.</p>
					</div>
				</c:if>
			</div>

			<c:choose>
				<c:when test="${fn:length(noticeList) > 0 || fn:length(noticeTopList) > 0}">
		        <div class="notice-wrap">
					<c:forEach var="list" items="${noticeTopList}" varStatus="idx" end="4">
		        	<%-- TOP 공지 --%>
		            <a href="${CTX}/cscenter/notice/noticeView.do?noticeIdx=${list.noticeIdx}" class="notice-box sticky">
		                <span class="notice-label">${list.noticeTypeNm}</span>
		                <p>${list.title}</p>
		                <span class="notice-date">${list.regDt}</span>
		            </a>
		            </c:forEach>
		            <c:forEach var="list" items="${noticeList}" varStatus="idx">
		            <%-- 일반 공지 --%>
		            <a href="${CTX}/cscenter/notice/noticeView.do?noticeIdx=${list.noticeIdx}" class="notice-box">
		                <span class="notice-label">${list.noticeTypeNm}</span>
		                <p>${list.title}</p>
		                <span class="notice-date">${list.regDt}</span>
		            </a>
		            </c:forEach>
		        </div>
		        <div class="pagin-nav">
		            <c:out value="${page.pageStr}" escapeXml="false"/>
		        </div>
		        </c:when>
		        <c:otherwise>
		        <div class="notice-wrap">
		        	<div class="no-contents">
		                <p>
		                <c:choose>
		                	<c:when test="${schVO.schValue ne null && schVO.schValue ne ''}">
		                	검색결과가 없습니다.
		                	</c:when>
		                	<c:otherwise>
		                	공지사항이 없습니다.
		                	</c:otherwise>
		                </c:choose>
		                </p>
		            </div>
		        </div>
		        </c:otherwise>
	        </c:choose>
		</div>
	</div>
</form>

<script type="text/javascript">
(function(window, document, $){
    $(".dropdown-select").dropdownAction();
})(window, document, jQuery);
</script>

</body>
</html>