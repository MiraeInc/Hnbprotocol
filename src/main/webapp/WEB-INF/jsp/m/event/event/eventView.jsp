<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<c:set var="gnbActive" value="event" scope="request"/>
<meta property="og:type" content="website">
<meta property="og:title" content="${detail.title}">
<meta property="og:description" content="${detail.eventInfo}">
<c:choose>
	<c:when test="${detail.eventIdx eq '116'}">
		<meta property="og:image" content="https://www.mandom.co.kr/upload/imgServer/event/116/20190311091355.jpg">
	</c:when>
	<c:when test="${detail.eventIdx eq '120'}">
		<meta property="og:image" content="https://www.mandom.co.kr/upload/imgServer/banner/1032/20190315092705.png">
	</c:when>
	<c:otherwise>
		<meta property="og:image" content="${IMGPATH}/event/${detail.eventIdx}/${detail.mEventImg}">
	</c:otherwise>
</c:choose>
<meta name="decorator" content="mobile.main"/>	
<script>
<%-- 이벤트 이메일 링크 잘못가서 강제 redirect --%>
if("<%=request.getParameter("eventIdx")%>" == "62"){
	location.href="${CTX}/event/entry/event100day.do";
}

$(document).ready(function(){
	var eventIdx = $("#eventIdx").val();
	
	var schCheck = "${schVO.schCheck}";
	if(schCheck == "Y"){
		$("#reply_box").attr("tabindex", -1).focus();
	}

	// 댓글 내용 focus 이벤트		
	$("#replyContents").click(function(){
		<c:if test="${IS_LOGIN eq false}"> 
		if(confirm("<spring:message code='common.util011'/>") == true){
			location.href="${CTX}/login/loginPage.do?refererYn=Y";
		}
		</c:if>
	});
	
	// 투표 이벤트 카운트 (개발96, 실131)
	if(eventIdx == 131){
		$.ajax({
			url: "${CTX}/ajax/event/event/getVoteNum.do",
			data: {
						"eventIdx"	:	eventIdx
					 },
			type: "POST",	
			async: false,
			contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data){
				if(data.result == true){
					$(".vote1 > .cnt").html(data.data1);
					$(".vote2 > .cnt").html(data.data2);
				}else{
					alert(data.msg);
				}
			}
		});
	}
	
	// BA 출시 기념 투표 이벤트 (D:102, R:142)
	if(eventIdx == 142){
		$.ajax({
			url: "${CTX}/ajax/event/event/getVoteNum.do",
			data: {
						"eventIdx"	:	eventIdx
					 },
			type: "POST",	
			async: false,
			contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data){
				if(data.result == true){
					$(".vote1 > .txt").html(data.data1);
					$(".vote2 > .txt").html(data.data2);
					$(".vote3 > .txt").html(data.data3);
					$(".vote4 > .txt").html(data.data4);
					$(".vote5 > .txt").html(data.data5);
					$(".vote6 > .txt").html(data.data6);
				}else{
					alert(data.msg);
				}
			}
		});
	}
	
	// 2020 투표 이벤트 (D:170, R:172)
	if(eventIdx == 172){
		$.ajax({
			url: "${CTX}/ajax/event/event/getVoteNum.do",
			data: {
						"eventIdx"	:	eventIdx
					 },
			type: "POST",	
			async: false,
			contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data){
				if(data.result == true){
					$(".vote1 > .txt").html(data.data1);
					$(".vote2 > .txt").html(data.data2);
					$(".vote3 > .txt").html(data.data3);
					$(".vote4 > .txt").html(data.data4);
					$(".vote5 > .txt").html(data.data5);
					$(".vote6 > .txt").html(data.data6);
				}else{
					alert(data.msg);
				}
			}
		});
	}
});

// 댓글 저장
function goSave(){
	if($.trim($("#replyContents").val()) ==""){ // 내용
		alert("댓글 내용을 입력 해 주세요."); 
		$("#replyContents").focus(); 
		return false;
	}
	if($.trim($("#replyContents").val().length) > 300){ // 내용 길이
		alert("댓글은 300자 이내로 작성해 주세요."); 
		$("#replyContents").focus(); 
		return false;
	}
	
	if(confirm("등록 하시겠습니까?") == true){
		$.ajax({			
			url: "${CTX}/ajax/event/event/eventReplySave.do",
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
					document.location.reload();
				}else if(Number(flag)==-1){
					alert("이미 등록 하셨습니다.");
				}else if(Number(flag) == -100) {
					alert("로그인이 필요합니다.");
					location.href="${CTX}/login/loginPage.do?refererYn=Y";
				}else{
					alert("오류가 발생 하였습니다.");
					document.location.reload();
				}
			}
		});
	}
}

// 댓글 삭제
function replyDelete(idx){
	if(confirm("삭제 하시겠습니까?") == true){
		$.ajax({			
			url: "${CTX}/ajax/event/event/eventReplyDeleteAjax.do",
			data: {
						"replyIdx"	:	idx,
					 },
			type: "POST",	
			async: false,
			contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(flag){
				if(Number(flag) > 0){ 
					alert("삭제 되었습니다.");
					document.location.reload();
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

//페이지 이동
function goPage(page){
	$("#pageNo").val(page);
	$("#schCheck").val("Y");
	
	var frm = document.eventForm;
	frm.action = "${CTX}/event/event/eventView.do";
	frm.submit();
}

//SNS 공유
function snsShare(snsType){
	// N : 네이버, F : 페이스북, K : 카카오스토리, T : 트위터
	var nowPage = document.location.href;
	var title = "${detail.title}";
 	var size = 'width=550 height=550';
	var url = "";
	
	if(snsType == "N"){
		url = 'https://share.naver.com/web/shareView.nhn?url='+encodeURIComponent(nowPage)+'&title='+encodeURIComponent(title);
	}else if(snsType == "F"){
		url = '//www.facebook.com/sharer/sharer.php?u='+nowPage+'&t='+title;
	}else if(snsType == "K"){
		url = 'https://story.kakao.com/share?url='+encodeURIComponent(nowPage);
	}else if(snsType == "T"){
	 	url = 'http://twitter.com/intent/tweet?text=' +encodeURIComponent(title) + '&url=' + nowPage
	}
	window.open(url,'_blank', size);  
}

// URL 공유
function urlShare(){
	var nowPage = document.location.href;
	var isIe = !!document.documentMode;
	if(isIe) {
		window.clipboardData.setData('Text', nowPage);
	    alert('클립보드에 복사가 되었습니다. \n\n\'Ctrl+V를 눌러 붙여넣기 해주세요.');
	} else {
		prompt('아래 주소를 복사 해주세요', nowPage);
	}
}

// 투표 이벤트
function voteClick(idx){
	$.ajax({			
		url: "${CTX}/ajax/event/event/eventVoteSave.do",
		data: {
					"eventIdx"	:	$("#eventIdx").val(),
					"voteNum"	:	idx
				 },
		type: "POST",	
		async: false,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
	 	error: function(request, status, error){ 
	 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
		},
		success: function(flag){
			if(Number(flag) > 0){ 
				alert("투표가 완료되었습니다.");
				document.location.reload();
			}else if(Number(flag) == -100) {
				alert("로그인이 필요합니다.");
				location.href="${CTX}/login/loginPage.do?refererYn=Y";
			}else{
				alert("오류가 발생 하였습니다.");
			}
		}
	});
}

// 댓글 포커스
function replyFocus(){
	$("#comment_box").attr("tabindex", -1).focus();
}

</script>
</head>
<body>
<form name="eventForm" id="eventForm" method="get" onsubmit="return false;">
	<input type="hidden" name="pageNo" id="pageNo" value="${schVO.pageNo}"/>
	<input type="hidden" name="schCheck" id="schCheck" value="${schVO.schCheck}"/>
	<input type="hidden" name="eventIdx" id="eventIdx" value="${detail.eventIdx}"/>
	
	<div class="content comm-event evt-event-view">
		<page:applyDecorator  name="mobile.eventmenu" encoding="UTF-8"/>
	
	<div class="page-body">
		<div class="box-search">
		    <div class="row">
		        <div class="col col-12">
		            <div class="form-control">
		                <div class="opt_select">
		                    <select name="eventSel" id="eventSel" onchange="changeEvent();">
		                    	<option value="">다른 이벤트 보기</option>
		                        <c:forEach var="list" items="${ingEventList}" varStatus="idx">
									<c:if test="${(detail.eventIdx ne list.eventIdx) and list.device ne 'P'}">
									<option value="${list.eventIdx}" attr-yn="${list.moLinkYn}" attr-url="${list.moLinkUrl}" attr-window="${list.moLinkWindow}">${list.title} (${list.startDt} ~ ${list.endDt})</option>
									</c:if>
								</c:forEach>
		                    </select>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>
		
		<div class="event-view">
	
			<div class="event-date">
				<p>이벤트 기간 <span>${detail.startDt} - ${detail.endDt}</span></p>
			</div>
			
	        <div class="event-img">
	            <c:out value="${detail.mEventDesc}" escapeXml="false"/>
	        </div>
			
			<c:if test="${detail.replyYn eq 'Y'}">
	        <!-- comment box -->
	        <div class="comment-box" id="comment_box">
	            <p class="comment-txt1">댓글</p>
	            <div class="reply-box">
	                <div class="textarea-box">
	                    <textarea rows="3" placeholder="내용과 무관한 댓글, 악플, 홍보성 광고글 등은 삭제될 수 있습니다.&#13;&#10;댓글은 300자 이내로 작성해 주세요." name="replyContents" id="replyContents"></textarea>
	                </div>
	                <c:choose>
						<c:when test="${empty USERINFO.memberIdx}">
						<small>*로그인 후 댓글을 남겨 주세요.</small>
						</c:when>
						<c:otherwise>
						<small>*욕설이나 비방 내용 또는 관련 없는 내용을 등록하실 경우 안내 없이 삭제 처리될 수 있습니다.</small>
						</c:otherwise>
					</c:choose>
	                <div class="btn-box confirm">
	                    <a href="javascript:" class="btn black" onclick="goSave();"><span class="txt">등록하기</span></a>
	                </div>
	            </div>
	
	            <strong class="comment-txt2" id="reply_box">총 <span>${replyCount}</span>개의 댓글이 달렸습니다.</strong>
	            <div class="comment-list">
	                <ul>
	                	<c:forEach var="list" items="${eventReplyList}" varStatus="idx">
	                    <li>
	                        <div class="comment-wrap">
	                            <div class="comment-inner">
	                            <%-- SNS_TYPE10 : kakao, SNS_TYPE20 : naver, SNS_TYPE30 : facebook --%>
								<c:choose>
									<c:when test="${list.snsType eq 'SNS_TYPE10'}">
									<p class="comment-ti ico_k">${list.memberNm}</p>
									</c:when>
									<c:when test="${list.snsType eq 'SNS_TYPE20'}">
									<p class="comment-ti ico_n">${list.memberNm}</p>
									</c:when>
									<c:when test="${list.snsType eq 'SNS_TYPE30'}">
									<p class="comment-ti ico_f">${list.memberNm}</p>
									</c:when>
									<c:otherwise>
									<p class="comment-ti">${list.memberNm}</p>
									</c:otherwise>
								</c:choose>
	                                <p class="comment-date"><span>${list.regYmd}</span><span>${list.regHms}</span></p>
	                                <p class="comment-txt"><c:out value="${list.replyContents}" escapeXml="false"/></p>
	                                <c:if test="${USERINFO.memberIdx eq list.regIdx}">
	                                <a href="javascript:" class="comment-del" onclick="replyDelete('${list.replyIdx}');">
	                                    <img src="${CTX}/images/${DEVICE}/common/ico_comment_del.png" alt="삭제">
	                                </a>
	                                </c:if>
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
	
	        <div class="comment-control">
	            <div class="comment-btn">
	                <a href="${CTX}/event/event/eventList.do" class="btn"><span class="txt">목록</span></a>
	            </div>
	        </div>
	    </div>
    </div>
    </div>
</form>
</body>
</html>