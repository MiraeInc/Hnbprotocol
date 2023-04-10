<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

// 댓글 저장
function goSave(){
	var gubun = $("input:radio[name=gubun]:checked").val();
	if($.trim(gubun) ==""){ // 구분
		alert("가장 마음에 드는 리뉴얼 내용을 선택하여 주세요"); 
		$("#gubun").focus(); 
		return false;
	}
	if($.trim($("#replyContents").val()) ==""){ // 내용
		alert("댓글 내용을 입력 해 주세요."); 
		$("#replyContents").focus(); 
		return false;
	}
	if($.trim($("#replyContents").val().length) > 300){ // 내용 길이
		alert("300자를 초과 할 수 없습니다."); 
		$("#replyContents").focus(); 
		return false;
	}
	
	if(confirm("등록 하시겠습니까?") == true){
		$.ajax({			
			url: "${CTX}/ajax/event/entry/renewReplySaveAjax.do",
			data: {
						"eventIdx"	:	$("#eventIdx").val(),
						"gubun"		:	gubun,
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
					location.href = "${CTX}/event/entry/renewEvent.do";
				}/* else if(Number(flag)==-1){
					alert("이미 등록 하셨습니다.");
				} */else if(Number(flag) == -100) {
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
	frm.action = "${CTX}/event/entry/renewEvent.do";
	frm.submit();
}

// key press
function keychk(e){
	if(e.keyCode == 13){
		e.returnValue = false;
		e.cancelBubble = true;
	}
}

// SNS 공유
function snsShare(snsType){
	// N : 네이버, F : 페이스북, K : 카카오스토리, T : 트위터
	var nowPage = document.location.href;
	var title = "새단장 맨담몰 다주는 이벤트";
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

</script>

</head>
<body>
<form id="eventForm" name="eventForm" method="post">
	<input type="hidden" name="pageNo" id="pageNo" value="${schVO.pageNo}"/>
	<input type="hidden" name="eventIdx" id="eventIdx" value="${detail.eventIdx}"/>
	
	<div class="content comm-event evt-event-view">
		<page:applyDecorator  name="mobile.eventmenu" encoding="UTF-8"/>
		
		<div class="page-title">
			<h2><img src="${CTX}/images/${DEVICE}/event/tit_evt_main.png" alt="이벤트" /></h2>
		</div>
		
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
				<style>
					.event-view img {max-width: 100%;}
					
					.renew-top {position: relative;}
					.renew-menus {position: absolute; left: 0; bottom: 0; text-align: center; font-size: 0;}
					.renew-menus .menus-anchor {display: inline-block; width: 44.9%; height: 27%; vertical-align: bottom;}
					.renew-menus .menus-anchor:first-child {margin-right: 1.4%;}
					
					.renew-comment-box {background-color: #fff5d3;}
					
					
					.renew-comment-share {position: relative;}
					.renew-comment-share .share-box {position: absolute; left: 20%; top: 66.4%; right: 20%;}
					.renew-comment-share .share-anchor {position: absolute; left: 0; top: 24.5%; width: 14%; height: 52.7%;}
					.renew-comment-share .share-anchor.anchor-naver {left: 6.3%;}
					.renew-comment-share .share-anchor.anchor-facebook {left: 24.9%;}
					.renew-comment-share .share-anchor.anchor-kakao {left: 43.2%;}
					.renew-comment-share .share-anchor.anchor-twitter {left: 61.8%;}
					.renew-comment-share .share-anchor.anchor-url {left: 80.2%;}
					
					
					.renew-comment-select {padding: 0 4.5%; margin-bottom: 25px;}
					.renew-comment-select:after {content:''; display: block; clear: both;}
					.renew-comment-select .select-item {position: relative; float: left; width: 32%; margin-left: 2%;}
					.renew-comment-select .select-item:first-child {margin-left: 0;}
					.renew-comment-select .select-item img {width: 100%;}
					.renew-comment-select .select-item input[type="radio"] {z-index: -1; position: absolute; left: 0; top: 0; opacity : 0;}
					.renew-comment-select .select-item label {position: relative; display: block; width: 100%; height: 100%;}
					.renew-comment-select .select-item input[type="radio"]:checked + label:after {content: ''; position: absolute; left: 0; top: 0; width: 100%; height: 100%; background: url('${CTX}/images/${DEVICE}/promotion/renew/img_cm_o_c.png') 0 0 no-repeat; background-size: cover; }

					.renew-comment-write {position: relative; margin-bottom: 15px;  padding: 0 4.5%; background: none;}
					.renew-comment-write .textarea-box {position: relative; height: 100px; padding-right: 24%;}
					.renew-comment-write .textarea-box textarea {height: 100px; font-size: 12px; border: 0;}
					.renew-comment-write .textarea-box .btn-box {position: absolute; right: 0; top: 0; height: 100%; width: 24%; margin: 0;}
					.renew-comment-write .textarea-box .btn {display: table-cell; min-width: auto; width: 1%; height: 100px; margin: 0; padding:0; vertical-align: middle; font-size: 12px; font-weight: bold; background-color: #333;}

					@media only screen and (max-device-width : 320px){
						.renew-comment-write .textarea-box textarea::placeholder {font-size: 11px;}
					}


					.renew-comment-list {border:0; padding: 0 4.5%;}
					.renew-comment-list li {position: relative; margin-top: 10px; padding: 8px; background-color: #fff; border: 0; }
					.renew-comment-list li:first-child {margin-top: 0;}
					.renew-comment-list li .comment-ti {position: absolute; left: auto; top: auto; right: 0; bottom: 0;}
					.renew-comment-list li .comment-txt {min-height: 90px; color: #333333;}
					.renew-comment-list li .comment-date {position: absolute; right: auto; top: auto; left: 0; bottom: 0;}
					.renew-comment-list li .comment-inner {padding-bottom: 30px;}
					.renew-comment-list li .comment-wrap {position: relative; padding: 30px 10px 10px;}
					.renew-comment-list li .comment-wrap:before {position: absolute; left: 38px;top: 4px; font-weight: bold;font-size: 9px; }
					.renew-comment-list li .comment-wrap:after {content: ''; position: absolute; left: 6px; top: -9px; width: 22px; height: 31px; background:url('${CTX}/images/${DEVICE}/promotion/renew/img_cm_cw_bg.png') 0 0 no-repeat; background-size: cover;}
					.renew-comment-list li .comment-wrap.type1 {border: 1px solid #ffc500;}
					.renew-comment-list li .comment-wrap.type1:before {content:'다양해진 브랜드와 상품들'; position: absolute; color: #ffc500; border-bottom: 1px solid #ffc500;}
					.renew-comment-list li .comment-wrap.type1:after {background-image: url('${CTX}/images/${DEVICE}/promotion/renew/img_cm_cw_bg.png')}
					.renew-comment-list li .comment-wrap.type2 {border: 1px solid #107dff;}
					.renew-comment-list li .comment-wrap.type2:before {content:'스타일링 컨텐츠 강화'; position: absolute;color: #107dff; border-bottom: 1px solid #107dff;}
					.renew-comment-list li .comment-wrap.type2:after {background-image: url('${CTX}/images/${DEVICE}/promotion/renew/img_cm_cw_bg2.png')}
					.renew-comment-list li .comment-wrap.type3 {border: 1px solid #ff3477;}
					.renew-comment-list li .comment-wrap.type3:before {content:'더 편해진 모바일웹';  color: #ff3477; border-bottom: 1px solid #ff3477;}
					.renew-comment-list li .comment-wrap.type3:after {background-image: url('${CTX}/images/${DEVICE}/promotion/renew/img_cm_cw_bg3.png')}
					.renew-pagin {padding-bottom: 35px; background-color: #fff5d3;}
				</style>
				
				
				<div class="renew-top">
					<img src="${CTX}/images/${DEVICE}/promotion/renew/img_cm_tit.png" alt="" />
					<div class="renew-menus">
						<a href="#none" class="menus-anchor"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_cm_mn_on.png" alt="100% 당첨, 소문 내면 맛있는 라면!"></a>
						<a href="${CTX}/event/entry/renewQuizEvent.do?eventIdx=88" class="menus-anchor"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_mn_off.png" alt="브랜드 퀴즈, POINT 다-주고! 엄청난 선물 더-주고!"></a>
					</div>
				</div>

				<div class="renew-row">
					<img src="${CTX}/images/${DEVICE}/promotion/renew/img_cm_c_1.png" alt="" />
				</div>
				<div class="renew-row">
					<img src="${CTX}/images/${DEVICE}/promotion/renew/img_cm_c_2.png" alt="" />
				</div>

				<c:if test="${detail.replyYn eq 'Y'}">
				<div class="comment-box renew-comment-box">
				
					<div class="renew-comment-share">
						<img src="${CTX}/images/${DEVICE}/promotion/renew/img_cm_intr.png" alt="" />
						
						<div class="share-box">
							<img src="${CTX}/images/${DEVICE}/promotion/renew/img_cm_share.png" alt="" />
							<a href="javascript:void(0);" class="share-anchor anchor-naver" onclick="snsShare('N');"><span class="hide">네이버</span></a>
							<a href="javascript:void(0);" class="share-anchor anchor-facebook" onclick="snsShare('F');"><span class="hide">페이스북</span></a>
							<a href="javascript:void(0);" class="share-anchor anchor-kakao" onclick="snsShare('K');"><span class="hide">카카오</span></a>
							<a href="javascript:void(0);" class="share-anchor anchor-twitter" onclick="snsShare('T');"><span class="hide">트위터</span></a>
							<a href="javascript:void(0);" class="share-anchor anchor-url" onclick="urlShare();"><span class="hide">주소공유</span></a>
						</div> 
					</div>
					
					<div class="renew-comment-select">
						<div class="select-item select-item-1">
							<input type="radio" id="optRenewLike1" name="gubun" value="1"/>
							<label for="optRenewLike1"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_cm_o_1.png" alt="다양해진 브랜드와 상품들" /></label>
						</div>
						<div class="select-item select-item-2">
							<input type="radio" id="optRenewLike2" name="gubun" value="2"/>
							<label for="optRenewLike2"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_cm_o_2.png" alt="스타일링 컨텐츠 강화" /></label>
						</div>
						<div class="select-item select-item-3">
							<input type="radio" id="optRenewLike3" name="gubun" value="3"/>
							<label for="optRenewLike3"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_cm_o_3.png" alt="더 편해진 모바일 웹" /></label>
						</div>
					</div>
					
					<div class="reply-box renew-comment-write">
						<div class="textarea-box">
							<textarea name="replyContents" id="replyContents" onkeypress="keychk(event);"
							placeholder="로그인 후 작성 가능합니다.&#13;&#10;내용과 무관한 댓글, 악플, 홍보성 광고글 등은 삭제될 수 있습니다.&#13;&#10;댓글은 300자 이내로 작성해 주세요."></textarea>
							<div class="btn-box confirm">
								<a href="javascript:void(0);" onclick="goSave();" class="btn black"><span class="txt">댓글<br/>남기기</span></a>
							</div>
						</div>
					</div>
					
					<div class="comment-list renew-comment-list">
						<ul>
							<c:forEach var="list" items="${replyList}" varStatus="idx">
							<li>
								<div class="comment-wrap type${list.gubun}"> <%-- type1, type2, type3 --%>
									<div class="comment-inner">
										<p class="comment-ti">${list.memberNm}</p>
										<p class="comment-date"><span>${list.regMd}</span></p>
										<p class="comment-txt"><c:out value="${list.replyContents}" escapeXml="false"/></p>
									</div>
								</div>
							</li>
							</c:forEach>
						</ul>
					</div>
				</div>

				<div class="pagin-nav renew-pagin">
					<c:out value="${page.pageStr}" escapeXml="false"/>
				</div>
				</c:if>

				<div class="renew-row">
					<img src="${CTX}/images/${DEVICE}/promotion/renew/img_cm_nt.png" alt="">
				</div>
			</div>
		</div>
	</div>
</form>
</body>
</html>