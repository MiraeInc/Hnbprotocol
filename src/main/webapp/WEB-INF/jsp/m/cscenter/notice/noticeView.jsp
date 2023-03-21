<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main"/>

<script>

// 파일 다운로드
function fileDownload(folder, fileNm){
	location.href="${CTX}/common/downloadFile.do?fileFolder="+folder+"&uploadFileNm="+fileNm;
}

</script>

</head>
<body>
<form name="noticeForm" id="noticeForm" method="get" onsubmit="return false;">
	<c:out value="${searchHiddenParams}" escapeXml="false"/>

	<div class="content comm-customer customer-notice">
	
		<page:applyDecorator  name="mobile.csmenu" encoding="UTF-8"/>

		<div class="page-title hide">
			<h2>공지사항</h2>
		</div>
		
		<div class="page-body">
			<div class="notice-wrap">
				<div class="notice-view">
					<span class="notice-label">${detail.noticeTypeNm}</span>
					<p>${detail.title}</p>
					<span class="notice-date">${detail.regDt}</span>
					<div class="notice-content">
						<c:out value="${detail.mNoticeDesc}" escapeXml="false"/>
					</div>
					<c:if test="${!empty fileList}">
						<div class="notice-file">
							<strong>첨부파일</strong>
							<c:forEach var="list" items="${fileList}" varStatus="idx">
								<a href="javascript:" onclick="fileDownload('${list.tableIdx}','${list.cscenterFileIdx}');">${list.realCscenterFile}</a>
							</c:forEach>
						</div>
					</c:if>
				</div>
				<div class="notice-control">
					<div class="notice-navi">
						<c:choose>
							<c:when test="${not empty prevNotice}">
								<a href="${CTX}/cscenter/notice/noticeView.do?noticeIdx=${prevNotice.noticeIdx}" class="notice-prev writing-no">
									<span>이전 글</span>
								</a>
							</c:when>
							<c:otherwise>
								<a href="javascript:" class="notice-prev writing-no"><span>이전 글</span></a>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${not empty nextNotice}">
								<a href="${CTX}/cscenter/notice/noticeView.do?noticeIdx=${nextNotice.noticeIdx}" class="notice-next">
									<span>다음 글</span>
								</a>
							</c:when>
							<c:otherwise>
								<a href="javascript:" class="notice-next"><span>다음 글</span></a>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="notice-btn">
						<a href="${CTX}/cscenter/notice/noticeList.do" class="btn"><span class="txt">목록</span></a>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>
</body>
</html>