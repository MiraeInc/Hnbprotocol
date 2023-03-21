<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<meta property="og:type" content="website">
<meta property="og:title" content="${sampleInfo.sampleTitle}">
<meta property="og:description" content="${sampleInfo.sampleTitle}">
<c:choose>
	<c:when test="${sampleInfo.status eq 'E'}">
		<meta property="og:image" content="${IMGPATH}/sample/${sampleInfo.sampleIdx}/${sampleInfo.endBanner}">
	</c:when>
	<c:otherwise>
		<meta property="og:image" content="${IMGPATH}/sample/${sampleInfo.sampleIdx}/${sampleInfo.ingBanner}">
	</c:otherwise>
</c:choose>

<meta name="decorator" content="mobile.main"/>	
<script>

$(document).ready(function(){
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
});

// 댓글 저장
function goSave(){
	var status = "${sampleInfo.status}";
	var startDt = "${sampleInfo.startDt}";
	
	if(status == "E"){
		alert("종료 된 정품신청 입니다.");
		return false;
	}else if(status == "P"){
		alert(startDt+"일부터 작성 가능합니다.");
		return false;
	}else if(status == "I"){
		if($.trim($("#replyContents").val()) ==""){ // 내용
			alert("댓글 내용을 입력 해 주세요."); 
			$("#replyContents").focus(); 
			return false;
		}
		if($.trim($("#replyContents").val().length) > 500){ // 내용 길이
			alert("500자를 초과 할 수 없습니다."); 
			$("#replyContents").focus(); 
			return false;
		}
		
		if(confirm("등록 하시겠습니까?") == true){
			$.ajax({			
				url: "${CTX}/ajax/style/sampleReplySave.do",
				data: {
							"sampleIdx"	:	$("#sampleIdx").val(),
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
}

// 댓글 삭제
function replyDelete(idx){
	if(confirm("삭제 하시겠습니까?") == true){
		$.ajax({			
			url: "${CTX}/ajax/style/sampleReplyDeleteAjax.do",
			data: {
						"sampleReplyIdx"	:	idx,
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

//페이지 이동
function goPage(page){
	$("#pageNo").val(page);
	$("#schCheck").val("Y");
	
	var frm = document.sampleForm;
	frm.action = "${CTX}/style/sampleView.do";
	frm.submit();
}

// SNS 공유
function snsShare(snsType){
	// N : 네이버, F : 페이스북, K : 카카오스토리, T : 트위터
	var nowPage = document.location.href;
	var title = "${sampleInfo.sampleTitle}";
 	var size = 'width=550 height=550';
	var url = "";
	
	if(snsType == "N"){
		url = 'http://share.naver.com/web/shareView.nhn?url='+encodeURIComponent(nowPage)+'&title='+encodeURIComponent(title);
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

// 정품신청
function sampleAppl(){
	var isLogin = "${IS_LOGIN}";
	if(isLogin == "false"){
		if(confirm("<spring:message code='common.util011'/>") == true){
			location.href="${CTX}/login/loginPage.do?refererYn=Y";
		}
	}else{
		var sampleIdx = $("#sampleIdx").val();
		var status = "${sampleInfo.status}";
		var startDt = "${sampleInfo.startDt}";
		var addrYn = "${sampleInfo.addrYn}";
		
		if(status == "E"){
			alert("종료 된 정품신청 입니다.");
			return false;
		}else if(status == "P"){
			alert(startDt+"일부터 신청 가능합니다.");
			return false;
		}else if(status == "I"){
			if(addrYn == "Y"){ // 정품 신청
				$.ajax({
					 url: "${CTX}/ajax/style/sampleApplAjax.do",
					 data : {
						 			"sampleIdx"	:	sampleIdx
						 		},
					 type: "post",	
					 async: false,
					 cache: false,
					 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
					 error: function(request, status, error){
						alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);	
					 },
					 success: function(flag){ 
						 if(Number(flag) > 0){ 
							alert("정품 체험 신청이 정상적으로 등록 되었습니다.\n당첨 시 기본 배송지로 정품이 발송되오니, 배송지 정보를 반드시 확인하세요.");
							document.location.reload();
						}else if(Number(flag)==-1){
							alert("이미 신청 하셨습니다.");
						}else if(Number(flag) == -100) {
							alert("로그인이 필요합니다.");
							location.href="${CTX}/login/loginPage.do?refererYn=Y";
						}else{
							alert("오류가 발생 하였습니다.");
						}
					 }
				});
			}else{ // 기본 배송지 등록
				alert("정품 체험 신청에 당첨 시, 기본 배송지로 정품이 발송되오니, 배송지 정보를 반드시 등록하신 후 다시 신청하여 주세요.");
				
				// 초기값 삭제
				$("#shippingNm").val("");
				$("#receiverNm").val("");
				$("#phoneNo").val("");
				$("#telNo").val("");
				$("#zipCd").val("");
				$("#addr").val("");
				$("#addrDetail").val("");
				
				$.ajax({
					 url: "${CTX}/ajax/mypage/member/memberShippingLayerAjax.do",
					 data : {
						 			"statusFlag"	:	"I",
						 			"compareFlag"	:	"sample"
						 		},
					 type: "post",	
					 async: false,
					 cache: false,
					 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
					 error: function(request, status, error){
						alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);	
					 },
					 success: function(responseData){ 
						 $("#popDelivery").html(responseData);
						 $("#popDelivery").addClass("active");
						 createNumeric("#phoneNo, #telNo");
					 }
				});
			}
		}
	}
}

// 배송지 저장/수정
function shippingSave(flag, idx){
	var phoneNo = $("#phoneNo").val();
	
	if($.trim($("#shippingNm").val()) == ""){
		alert("배송지명을 입력해 주세요.");
		$("#shippingNm").focus();
		return false;
	}
	
	if($.trim($("#receiverNm").val()) == ""){
		alert("받으시는분을 입력해 주세요.");
		$("#receiverNm").focus();
		return false;
	}
	
	
	if($.trim(phoneNo) == ""){
		alert("휴대폰을 입력해 주세요.");
		$("#phoneNo").focus();
		return false;
	}
	
	// 휴대폰 유효성 체크
	if(phoneCheck(phoneNo) == false){
		alert("잘못된 휴대폰 번호입니다.");
		$("#phoneNo").focus();
		return false;
	}
	
	if($.trim($("#zipCd").val()) == ""){
		alert("우편번호 검색을 해주세요.");
		return false;
	}
	
	if($.trim($("#addrDetail").val()) == ""){
		alert("나머지 주소를 입력해 주세요.");
		$("#addrDetail").focus();
		return false;
	}
	
	if ($("#defaultChk").is(":checked")){
		$("#defaultYn").val("Y");
	}else{
		$("#defaultYn").val("N");
	}
	
	// 배송지 저장/수정
	$.ajax({
		 url: "${CTX}/ajax/mypage/member/memberShippingSaveAjax.do",
		 data : {
			 			"addressIdx"			:	idx,
			 			"shippingNm"		:	$("#shippingNm").val(),
			 			"receiverNm"			:	$("#receiverNm").val(),
			 			"phoneNo"				:	$("#phoneNo").val(),
			 			"telNo"					:	$("#telNo").val(),
			 			"zipCd"					:	$("#zipCd").val(),
			 			"addr"						:	$("#addr").val(),
			 			"oldZipCd"				:	$("#oldZipCd").val(),
			 			"oldAddr"				:	$("#oldAddr").val(),
			 			"addrDetail"			:	$("#addrDetail").val(),
			 			"defaultYn"				:	$("#defaultYn").val(),
			 			"statusFlag"			:	flag
			 		},
		 type: "post",	
		 async: false,
		 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 error: function(request, status, error){ 
		 	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
		 },
		 success: function(flag){
			 if(Number(flag) > 0){
				 alert("배송지 정보가 등록되었습니다.");
				 location.reload();
			 }
		 }
	});
}

// 배송지확인
function addrCheck(){
	var isLogin = "${IS_LOGIN}";
	if(isLogin == "false"){
		if(confirm("<spring:message code='common.util011'/>") == true){
			location.href="${CTX}/login/loginPage.do?refererYn=Y";
		}
	}else{
		location.href="${CTX}/mypage/member/memberShipping.do";
	}
}

// 우편번호찾기
function addrPopup(){
	execDaumPostcode(callback);		
}

// CALLBACK 주소정보 값
var callback = function(zipCd, oldZipCd, addr, oldAddr){
	$("#zipCd").val(zipCd);					// 우편번호(신)
	$("#addr").val(addr);						// 주소 (신)
	$("#oldZipCd").val(oldZipCd);		// 우편번호 (구)
	$("#oldAddr").val(oldAddr);			// 주소 (구)
	
	$("#addrDetail").focus();
}

</script>
</head>
<body>
<form name="sampleForm" id="sampleForm" method="get" onsubmit="return false;">
	<input type="hidden" name="pageNo" id="pageNo" value="${schVO.pageNo}"/>
	<input type="hidden" name="schCheck" id="schCheck" value="${schVO.schCheck}"/>
	<input type="hidden" name="sampleIdx" id="sampleIdx" value="${sampleInfo.sampleIdx}"/>
	<input type="hidden" name="sampleReplyIdx" id="sampleReplyIdx" value=""/> <%-- 정품 댓글 일련번호 --%>
	
	<div class="content comm-styleg styleg-sample">
		<page:applyDecorator  name="mobile.eventmenu" encoding="UTF-8"/>           
		
		<%-- <div class="page-title">
			<h2><img src="${CTX}/images/${DEVICE}/event/tit_evt_sample.png" alt="정품 신청"></h2>
		</div> --%>
		
		<c:choose>
			<c:when test="${empty sampleInfo}">
				<div class="preparing">
					<div class="inner-box">
						<img src="${CTX}/images/${DEVICE}/common/txt_comingsoon.png" alt="coming soon!">
						<p class="big-txt">이 달의 신청이 종료되었습니다.</p>
						<div class="btn-box">
							<a href="${CTX}/cscenter/notice/noticeList.do" class="btn small outline-green ico-chev"><span class="txt">당첨자 안내</span></a>
						</div>
						<p class="small-txt">
							다음 정품 신청도 많은 기대 부탁 드립니다.<br/>
							곧 찾아 뵙겠습니다.<br/>
							감사합니다.
						</p>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="sample-view">
					<c:choose>
						<c:when test="${sampleInfo.sampleType eq 'I'}">
							<div class="sample-img">
								<c:choose>
									<c:when test="${sampleInfo.linkYn eq 'Y'}">
									<c:set var="linkWindow"/>
									<c:choose>
										<c:when test="${sampleInfo.linkWindow eq 'N'}">
											<c:set var="linkWindow" value="_blank"/>
										</c:when>
										<c:otherwise>
											<c:set var="linkWindow" value="_self"/>
										</c:otherwise>
									</c:choose>
									<a href="${sampleInfo.linkMoUrl}" target="${linkWindow}">
									</c:when>
									<c:otherwise>
									<a href="${CTX}/product/productView.do?goodsCd=${sampleInfo.goodsCd}" title="상품보기">
									</c:otherwise>
								</c:choose>
									<img src="${IMGPATH}/sample/${sampleInfo.sampleIdx}/${sampleInfo.mBannerImg}" alt="${sampleInfo.sampleTitle}"/>
				                </a>
							</div>
						</c:when>
						<c:otherwise>
							<c:out value="${sampleInfo.mSampleDesc}" escapeXml="false"/>
						</c:otherwise>
					</c:choose>
		
					<div class="comment-share">
						<img src="${CTX}/images/${DEVICE}/event/bg_share.png" alt="" />
						<!-- SNS공유(네이버, 페이스북, 카카오, 트위터, 직접공유 순) --> 
						<a href="javascript:void(0);" onclick="snsShare('N');" class="lnk-nv"><span class="hide">네이버</span></a> 
						<a href="javascript:void(0);" onclick="snsShare('F');" class="lnk-fb"><span class="hide">페이스북</span></a> 
						<a href="javascript:void(0);" onclick="snsShare('K');" class="lnk-ka"><span class="hide">카카오</span></a> 
						<a href="javascript:void(0);" onclick="snsShare('T');" class="lnk-tw"><span class="hide">트위터</span></a> 
						<a href="javascript:void(0);" onclick="urlShare();" class="lnk-url"><span class="hide">직접공유</span></a>
					</div>
					 
					<div class="comment-box">
						<p class="comment-txt1">댓글</p>
						<div class="reply-box">
							<div class="textarea-box">
								<textarea rows="3" placeholder="내용과 무관한 댓글, 악플, 홍보성 광고글 등은 삭제될 수 있습니다." name="replyContents" id="replyContents"></textarea>
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
		
						<p class="comment-txt2">총 <strong>${replyCount}</strong>개의 댓글이 달렸습니다.</p>
						<div class="comment-list">
							<ul>
								<c:forEach var="list" items="${sampleReplyList}" varStatus="idx">
									<li>
										<div class="comment-wrap">
											<div class="comment-inner">
												<c:choose>
													<c:when test="${list.snsType eq 'SNS_TYPE10'}">
														<p class="comment-author ico_k">${list.memberNm}</p>
													</c:when>
													<c:when test="${list.snsType eq 'SNS_TYPE20'}">
														<p class="comment-author ico_n">${list.memberNm}</p>
													</c:when>
													<c:when test="${list.snsType eq 'SNS_TYPE30'}">
														<p class="comment-author ico_f">${list.memberNm}</p>
													</c:when>
													<c:otherwise>
														<p class="comment-author">${list.memberNm}</p>
													</c:otherwise>
												</c:choose>
												<p class="comment-date"><span>${list.regYmd}</span><span>${list.regHms}</span></p>
												<p class="comment-txt"><c:out value="${list.replyContents}" escapeXml="false"/></p>
												<c:if test="${USERINFO.memberIdx eq list.regIdx}">
													<a href="javascript:" class="comment-del" onclick="replyDelete('${list.sampleReplyIdx}');">
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
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</form>

<!--  배송지 등록/수정 -->
<div id="popDelivery" class="popup type-page popup-cashreceipt">
	<%-- AJAX --%>
</div>
</body>
</html>