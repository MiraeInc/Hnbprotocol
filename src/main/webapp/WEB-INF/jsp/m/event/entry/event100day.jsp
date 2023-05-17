\<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main"/>	

<script>
$(document).ready(function(){
	$("#replyContents").click(function(){
		if("${IS_LOGIN}" == "false"){
			if(confirm("<spring:message code='common.util011'/>") == true){
				location.href="${CTX}/login/loginPage.do?refererYn=Y"; 
			}
		}
	});
});

// 커플링 신청
function goRing(){
	if("${IS_LOGIN}" == "false"){
		if(confirm("<spring:message code='common.util011'/>") == true){
			location.href="${CTX}/login/loginPage.do?refererYn=Y"; 
		}
	}else{
		if(confirm("신청 하시겠습니까?") == true){
			$.ajax({			
				url: "${CTX}/ajax/event/entry/day100ringSaveAjax.do",
				type: "POST",	
				async: false,
				contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
			 	error: function(request, status, error){ 
			 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
				},
				success: function(flag){
					if(Number(flag) > 0){ 
						alert("커플링 & 커플사진 신청이 접수되었습니다.");
					}else if(Number(flag)==-1){
						alert("이미 신청 하셨습니다.");
					}else if(Number(flag) == -100) {
						alert("로그인이 필요합니다.");
						location.href="${CTX}/login/loginPage.do?refererYn=Y";
					}else{
						alert("오류가 발생 하였습니다.");
					}
					document.location.reload();
				}
			});
		}
	}
}

// 댓글 저장
function goSave(){
	if($.trim($("#replyContents").val()) ==""){ // 내용
		alert("댓글 내용을 입력 해 주세요."); 
		$("#replyContents").focus(); 
		return false;
	}
	if($.trim($("#replyContents").val().length) > 100){ // 내용 길이
		alert("100자를 초과 할 수 없습니다."); 
		$("#replyContents").focus(); 
		return false;
	}
	
	if(confirm("등록 하시겠습니까?") == true){
		$.ajax({			
			url: "${CTX}/ajax/event/entry/day100replySaveAjax.do",
			data: {
						"eventIdx"	:	$("#eventIdx").val(),
						"replyContents" : $("#replyContents").val()
					 },
			type: "POST",	
			async: false,
			contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(flag){
				if(Number(flag) > 0){ 
					alert("등록 되었습니다.");
					location.href = "${CTX}/event/entry/event100day.do";
				}else if(Number(flag)==-1){
					alert("이미 등록 하셨습니다.");
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

// 다른 이벤트 이동
function changeEvent(){
	var linkYn = $("#eventSel option:selected").attr("attr-yn");
	var linkUrl = $("#eventSel option:selected").attr("attr-url");
	var linkWindow = $("#eventSel option:selected").attr("attr-window");
	var eventIdx = $("#eventSel option:selected").val();
	
	if(linkYn == "Y"){
		if(linkWindow == "N"){
			var openWindow = window.open("about:blank");
			
			openWindow.location.href=linkUrl;	
		}else{
			document.location.href=linkUrl;
		}
		
	}else{
		$("#eventIdx").val(eventIdx);
		
		if($("#eventSel").val() != ""){
			var frm = document.eventForm;
			frm.action = "${CTX}/event/event/eventView.do";
			frm.submit();
		}
	}
}

// 페이지 이동
function goPage(page){
	$("#pageNo").val(page);
	
	var frm = document.eventForm;
	frm.action = "${CTX}/event/entry/event100day.do";
	frm.submit();
}
</script>

</head>
<body>
<form id="eventForm" name="eventForm" method="post">
	<input type="hidden" name="pageNo" id="pageNo" value="${schVO.pageNo}"/>
	<input type="hidden" name="eventIdx" id="eventIdx" value="62"/>
	
	<div class="content comm-event evt-event-view">
		<page:applyDecorator  name="mobile.eventmenu" encoding="UTF-8"/>
		
		<div class="page-title">
			<h2><img src="${CTX}/images/m/event/tit_evt_main.png" alt="이벤트" /></h2>
		</div>
		
		<div class="page-body">
			
			<div class="box-search">
				<div class="form-control">
					<div class="opt_select">
						<select name="eventSel" id="eventSel" onchange="changeEvent();">
	                    	<option value="">다른 이벤트 보기</option>
	                        <c:forEach var="list" items="${ingEventList}" varStatus="idx">
								<c:if test="${(list.eventIdx ne 62) and list.device ne 'P'}">
								<option value="${list.eventIdx}" attr-yn="${list.moLinkYn}" attr-url="${list.moLinkUrl}" attr-window="${list.moLinkWindow}">${list.title} (${list.startDt} ~ ${list.endDt})</option>
								</c:if>
							</c:forEach>
	                    </select>
					</div>
				</div>
			</div>

			<div class="event-view event-100day">
				<div class="event-img">
					<img src="${CTX}/images/m/promotion/100day/img_promo_100day.png" />
					<p class="hide">
						신청만하면 커플링 + 커플사진 (1명)<br/>
						100만원 상당 골든듀 레브 소네트 커플링, 인생 커플 사진 스튜디오촬영<br/>
						<br/>
						· 회원이면 누구나 신청 가능합니다.<br/>
						· 제세공과금 22%는 고객님 부담입니다.<br/>
						· 당첨자 1분에게는 7월 4일(수) , 개별 연락 드리므로 회원정보가 정확한지 확인하여 주세요.<br/>
						· 커플링은 당사 사정에 따라 동일한 가격대의 상품으로 교체될 수 있습니다.
					</p>
					<a href="http://www.goldendew.com/goods/detail.asp?gno=1516" style="position: absolute; left: 11.6%; top: 28.2%; width: 26.1%; height: 6.6%;"><span class="hide">커플링 상세보기</span></a>
					<a href="javascript:" style="position: absolute;left: 11.6%; top: 45.7%; width: 76.8%; height: 3.3%;" onclick="goRing();"><span class="hide">커플링 &amp; 커플사진 신청</span></a>
					<a href="#reply-box" style="position: absolute; left: 64.5%; top: 50.2%; width: 24%; height: 2.0%;"><span class="hide">100일 축하 댓글 남겨주시면 당첨 확률이 높아져요~ 댓글남기러가기</span></a>
					<p class="hide">
						축하 댓글만 쓰면 (매일 50명) 바나나우유 <br/>
						· 회원이면 누구나 댓글 등록 가능합니다. <br/>
						· 1일 50분 한정입니다. <br/>
						· 영업일 기준으로 댓글 등록 다음날, 회원 정보에 등록된 휴대폰 번호에 근거하여 기프티콘 발송합니다. <br/>
						· 기프티콘은 당사 사정에 따라 동일한 가격대의 상품으로 교체될 수 있습니다. <br/>
					</p>
					<a href="#reply-box" style="position: absolute; left: 11.6%; top: 82.7%; width: 76.8%; height: 3.3%;"><span class="hide">축하 댓글 쓰기</span></a>
				</div>
				
				<c:if test="${detail.replyYn eq 'Y'}">
					<div class="comment-box" id="reply-box">
						<div class="reply-title">
							<strong>COMMENT</strong>
							<p class="text">맨담몰의 100일을 축하해 주세요!</p>
							<p class="text-gift">정성껏 코멘트를 남겨주시는 분께  「바나나우유」를 선물로 드립니다.<br/>(1일 50분 한정)</p>
						</div>
						
						<div class="reply-box">
							<div class="textarea-box">
								<textarea rows="3" name="replyContents" id="replyContents" placeholder="댓글은 100자 이내로 작성해 주세요."></textarea>
								<div class="btn-box confirm">
									<a href="javascript:" onclick="goSave();" class="btn black"><span class="txt">축하글<br/>남기기</span></a>
								</div>
							</div>
							<small>타인에 글을 무단 도용한 글, 이벤트와 무관한 글, 상업적이거나 선정적인 글, 중복된 글은 운영자 판단 하에 임의로 삭제될 수 있습니다.</small>
						</div>
	
						<div class="comment-list">
							<ul>
								<c:forEach var="list" items="${replyList}" varStatus="idx">
									<c:set var="type" value=""/>
							
									<c:choose>
										<c:when test="${list.regType eq 0 or list.regType eq 1 or list.regType eq 2 or list.regType eq 3}">
											<c:set var="type" value="type1"/>
										</c:when>
										<c:when test="${list.regType eq 4 or list.regType eq 5 or list.regType eq 6}">
											<c:set var="type" value="type2"/>
										</c:when>
										<c:otherwise>
											<c:set var="type" value="type3"/>
										</c:otherwise>
									</c:choose>
									<li>
										<div class="comment-wrap ${type}">
											<div class="comment-inner">
												<p class="comment-ti">${list.memberNm }</p>
												<p class="comment-date"><span>${list.regMd}</span></p>
												<p class="comment-txt"><c:out value="${list.replyContents}" escapeXml="false"/></p>
											</div>
										</div>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>

					<div class="pagin-nav">
						<c:out value="${page.pageStr}" escapeXml="false"/>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</form>
</body>
</html>