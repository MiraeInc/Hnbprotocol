<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<c:set var="gnbActive" value="sample" scope="request"/>
<meta name="decorator" content="mobile.main"/>	
<script>

// 페이지 이동
function goPage(page){
	$("#pageNo").val(page);
	
	var frm = document.sampleForm;
	frm.action = "${CTX}/style/sampleList.do";
	frm.submit();
}

// 후기 작성
function reviewWrite(sampleIdx){
	var isLogin = "${IS_LOGIN}";
	if(isLogin == "false"){
		if(confirm("<spring:message code='common.util011'/>") == true){
			location.href="${CTX}/login/loginPage.do?refererYn=Y";
		}
	}else{
		// 당첨여부 확인
		$.ajax({
			 url: "${CTX}/ajax/style/winnerCheckAjax.do",
			 data : {
				 			"sampleIdx"	:	sampleIdx
				 		},
			 type: "post",	
			 async: false,
			 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 error: function(request, status, error){ 
			 	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			 },
			 success: function(flag){
				 if(Number(flag) > 0){ 
					location.href="${CTX}/mypage/review/noWriteReviewList.do";
				}else if(Number(flag) == 0){
					alert("해당 정품신청의 당첨자가 아니십니다.");
				}else if(Number(flag) == -100) {
					alert("로그인이 필요합니다.");
					location.href="${CTX}/login/loginPage.do?refererYn=Y";
				}else{
					alert("오류가 발생 하였습니다.");
				}
			 }
		});
	}
}

</script>
</head>
<body>
<form name="sampleForm" id="sampleForm" method="get" onsubmit="return false;">
	<input type="hidden" name="pageNo" id="pageNo" value="${schVO.pageNo}"/>
	<input type="hidden" name="sampleIdx" id="sampleIdx" value=""/>
	
	<div class="content comm-event event-main">
	    <page:applyDecorator  name="mobile.eventmenu" encoding="UTF-8"/>
	
		<div class="page-body">
			<c:choose>
				<c:when test="${!empty sampleList}">
					<div class="sample-list">
						<ul>
							<c:forEach var="list" items="${sampleList}" varStatus="idx">
								<c:if test="${list.status ne 'P'}">
									<li>
										<c:choose>
											<c:when test="${list.status eq 'E'}">
												<div class="sample-image">
											</c:when>
											<c:when test="${list.status eq 'P'}">
												<a href="javascript:void(0);" class="sample-image" onclick="alert('${list.startDt}일 부터 신청 가능 합니다.');">
											</c:when>
											<c:otherwise>
												<a href="${CTX}/style/sampleView.do?sampleIdx=${list.sampleIdx}" class="sample-image">
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${list.status eq 'E'}">
												<img src="${IMGPATH}/sample/${list.sampleIdx}/${list.endBanner}" alt="${list.sampleTitle}"/>
											</c:when>
											<c:otherwise>
												<img src="${IMGPATH}/sample/${list.sampleIdx}/${list.ingBanner}" alt="${list.sampleTitle}"/>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${list.status eq 'E'}">
												</div>
											</c:when>
											<c:otherwise>
												</a>
											</c:otherwise>
										</c:choose>
										<c:if test="${list.status ne 'E'}">
											<p class="sample-date">${list.startDt} - ${list.endDt}</p>
										</c:if>
										<c:if test="${list.status eq 'E'}">
											<div class="sample-btns">
												<c:if test="${!empty list.noticeIdx}">
													<a href="${CTX}/cscenter/notice/noticeView.do?noticeIdx=${list.noticeIdx}" class="btn"><span class="txt">당첨자 보기</span></a>
												</c:if>
													<a href="javascript:void(0);" onclick="reviewWrite('${list.sampleIdx}');" class="btn black"><span class="txt">후기 작성</span></a>
											</div>
										</c:if>
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
		
		<c:if test="${!empty sampleList}">
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