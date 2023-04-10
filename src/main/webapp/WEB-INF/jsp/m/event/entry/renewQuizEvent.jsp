\<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main"/>	

<script>
	$(document).ready(function(){
		// 댓글 입력 창 클릭
		$(".answerBtn").click(function(){
			if("${IS_LOGIN}" == "false"){
				if(confirm("<spring:message code='common.util011'/>") == true){
					location.href="${CTX}/login/loginPage.do?refererYn=Y"; 
				}
			}
			else {
				var quiz_no = $(this).data("quiz-no");
				var answer_no = "";
				 var radioname = "rdo_answer"+quiz_no;
				if($("input:radio[name='"+radioname+"']:checked").length<1){
					alert("퀴즈 답변을 선택해 주세요."); 
					return false;
				}
				else {
					answer_no = $("input:radio[name='"+radioname+"']:checked").val();
					goAnswer(quiz_no, answer_no);
				}			
	
			}
		});
		
	});


	
	function goAnswer(quiz_no, answer_no){
		 $.ajax({
			 url: "${CTX}/ajax/event/entry/renewQuizAnswerAjax.do",
			 data: {'quizNo' : quiz_no, 'answerNo' : answer_no},
			 type: "post",	
			 async: false,
			 cache: false,
			 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 error: function(request, status, error){ 
			 	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			 },
			 success: function(data){
				// chk : -100:로그인체크, -200 : 종료된이벤트,  -300 이미 참여한 퀴즈, 100 : 정답, 200:오답
				if (data == -100) {
					alert('로그아웃되었습니다. 재로그인후 참여해주세요');	location.href="${CTX}/login/loginPage.do?refererYn=Y"; 
				} else if (data == -200) {
					alert('종료된 이벤트입니다.');
				} else if (data == -300) {
					alert('이미 참여하신 퀴즈입니다.');
				} else if (data == 200) {
					//팝업 초기화
					var popQzComplete = $('[data-remodal-id=popupComplete]').remodal();
					var popQzFail = $('[data-remodal-id=popupFail]').remodal();
					
					//팝업 호출
					popQzFail.open();
					
				} 
				else if (data == 100) //정답
				{
					var giftname = "";
					
					var tag = "";
					if (quiz_no == "1") {
						giftname = "갸스비 '순토 엠빗 3'";
						tag = "ga";
					} else if (quiz_no == "2") {
						giftname = "비페스타 'LG 프라엘 더마 LED 마스크'";
						tag = "bi";
					}else if (quiz_no == "3") {
						giftname = "루시도엘 '다이슨 헤어 드라이어' ";
						tag = "lu";
					}else if (quiz_no == "4") {
						giftname = "마마버터 '전-상품'";
						tag = "ma";
					}else if (quiz_no == "5") {
						giftname = "덴탈프로 '전-상품'";
						tag = "de";
					}else if (quiz_no == "6") {
						giftname = "찰리 '전-상품'";
						tag = "ch";
					}
					
					$($("#qz-item1-"+tag)).addClass("clear");
					$($("#qz-item2-"+tag)).addClass("clear");
					
					
					$("#giftname").html(giftname);
					
					//팝업 초기화
					var popQzComplete = $('[data-remodal-id=popupComplete]').remodal();
					var popQzFail = $('[data-remodal-id=popupFail]').remodal();
					
					//팝업 호출
					popQzComplete.open();
				}
			 }
		}); 
	}
	
//목록
function goList(){
	var frm = document.eventForm;
	frm.action = "${CTX}/event/event/eventList.do";
	frm.submit();
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


</script>


</head>
<body>
	
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
		<!-- s: 리뉴얼 퀴즈 -->
				<div class="event-view">

					<style>
						.event-view {max-width: 690px; margin: 0 auto;}
						.event-view img {max-width: 100%;}
						
						.renew-top {position: relative;}
						.renew-menus {position: absolute; left: 0; bottom: 0; text-align: center; font-size: 0;}
						.renew-menus .menus-anchor {display: inline-block; width: 44.9%; height: 27%; vertical-align: bottom;}
						.renew-menus .menus-anchor:first-child {margin-right: 1.4%;}
						
						.renew-qz-guide {position: relative;}
						.renew-qz-guide a {position: absolute; left: 67.4%; top: 68.4%; width: 22.8%; height: 8.5%;}

						.renew-qz-map {position: relative}
						.renew-qz-map .map-item {position: absolute; width: 27.1%}
						.renew-qz-map .map-item.clear:after {content: ''; position: absolute; left: 0; top: 0; width: 96.3%; height: 83.5%; background: url('${CTX}/images/${DEVICE}/promotion/renew/img_qz_map_cl.png') center center no-repeat; background-size: cover;}
						.renew-qz-map .map-item-ga {left: 6.5%; top: 12.3%;}
						.renew-qz-map .map-item-bi {left: 37.1%; top: 12.3%;}
						.renew-qz-map .map-item-lu {left: 67%; top: 12.3%;}
						.renew-qz-map .map-item-ma {left: 6.5%; top: 54.7%;}
						.renew-qz-map .map-item-de {left: 37.1%; top: 54.7%;}
						.renew-qz-map .map-item-ch {left: 67%; top: 54.7%;}

						.renew-qz-list {padding: 0 4.5% 40px; background-color: #193c5c;}
						.renew-qz-list li {margin-top: 18px; padding: 15px 0; background-color: #ffffff; border-radius: 5px}
						.renew-qz-list li:first-child {margin: 0;}
						.renew-qz-list .item-title {display: block; width: 28.3%; margin: 0 auto; font-size: 0;}
						.renew-qz-list .item-select {margin-bottom: 25px; padding: 0 4.8%; counter-reset: c;}
						.renew-qz-list .item-select:after {display: block; clear: both; content: '';}
						.renew-qz-list .item-select .select-block {position: relative; float: left; width: 25%;}
						.renew-qz-list .item-select .select-block img {width: 100%;}
						.renew-qz-list .item-select .select-block label {position: absolute; left: 0; top: 0; width: 100%; height: 100%; padding: 5%;}
						.renew-qz-list .item-select .select-block label > .txt {position: relative; display: block; width: 100%; height: 100%; line-height: 100%; border: 2px solid #ddd; border-radius: 100%}
						.renew-qz-list .item-select .select-block label > .num {position: absolute; z-index:1; left: 8%; top: 8%; width: 25%; height: 25%; text-align: center; border-radius: 100%; background-color: #000000; color: #fff; content: counter(c);counter-increment: c; font-weight: bold; font-size: 9px;}
						.renew-qz-list .item-select .select-block label > .num span {position: absolute; left: 0; top: 50%; width: 100%; line-height: 1; text-align: center; transform: translateY(-50%)}
						.renew-qz-list .item-select .select-block label > .txt > span {position: absolute; left: 0; top: 50%; width: 100%; text-align: center; transform: translateY(-50%); font-size: 10px; line-height: 1.2; font-weight: bold;}
						.renew-qz-list .item-select .select-block input[type="radio"] {position: absolute; z-index: -1; left: 0; top: 0; opacity: 0;}
						.renew-qz-list .item-select .select-block input[type="radio"]:checked + label > .txt {border-color: #107dff; background-color: #107dff;}
						.renew-qz-list .item-select .select-block input[type="radio"]:checked + label > .txt > span {color: #fff;}

						.renew-qz-list .item-quiz {margin-top: 15px; margin-bottom: 25px; text-align: center; font-size: 0;}
						.renew-qz-list .item-btns { padding: 0 4.8%;}
						.renew-qz-list .item-btns:after {display: block; clear: both; content:'';}
						.renew-qz-list .item-btns a {float: left; width: 49%;}
						.renew-qz-list .item-btns a:nth-child(1) { margin-right: 2%;}
						
						.renew-qz-list .qz-item {position: relative;}
						.renew-qz-list .qz-item.clear:after {content: ''; z-index: 10; position: absolute; left: 0; top: 0; width: 100%; height: 100%; border-radius: 5px; background:rgba(0, 0, 0, .85) url('${CTX}/images/${DEVICE}/promotion/renew/img_qz_item_cl.png') center center no-repeat; background-size: contain;}

						.renew-qz-list .qz-item-bi .select-block label > .num {background-color: #6d5a22;}
						.renew-qz-list .qz-item-bi .select-block input[type="radio"]:checked + label > .txt {border-color: #ecd593; background-color: #ecd593;}
						.renew-qz-list .qz-item-bi .select-block input[type="radio"]:checked + label > .txt > span {color:#000000;}
						
						.renew-qz-list .qz-item-lu .select-block label > .num {background-color: #572023;}
						.renew-qz-list .qz-item-lu .select-block input[type="radio"]:checked + label > .txt {border-color: #e9adb0; background-color: #e9adb0;}
						.renew-qz-list .qz-item-lu .select-block input[type="radio"]:checked + label > .txt > span {color:#000000;}
						
						.renew-qz-list .qz-item-ma .select-block label > .num {background-color: #ecc403;}
						.renew-qz-list .qz-item-ma .select-block input[type="radio"]:checked + label > .txt {border-color: #fff5c2; background-color: #fff5c2;}
						.renew-qz-list .qz-item-ma .select-block input[type="radio"]:checked + label > .txt > span {color:#000000;}
						
						.renew-qz-list .qz-item-de .select-block label > .num {background-color: #e83435;}
						.renew-qz-list .qz-item-de .select-block input[type="radio"]:checked + label > .txt {border-color: #ffbfbf; background-color: #ffbfbf;}
						.renew-qz-list .qz-item-de .select-block input[type="radio"]:checked + label > .txt > span {color:#000000;}
						
						.renew-qz-list .qz-item-ch .select-block label > .num {background-color: #555555;}
						.renew-qz-list .qz-item-ch .select-block input[type="radio"]:checked + label > .txt {border-color: #afabaa; background-color: #afabaa;}
						.renew-qz-list .qz-item-ch .select-block input[type="radio"]:checked + label > .txt > span {color:#000000;}
						
						.renew-modal {padding: 0; max-width: 72.3%; background: none;}
						.renew-modal img {max-width: 100%;}
						.renew-modal .remodal-close {left: auto; z-index: 1; right: 13px; top: 13px; width: 22px; height: 22px; background: url('${CTX}/images/${DEVICE}/promotion/renew/img_qz_po_x.png') 0 0 no-repeat; background-size: cover}
						.renew-modal .remodal-close::before {opacity: 0}

						.pop-qz {}
						/* .pop-qz-fail {position: relative; padding-top: 450px; background: url('${CTX}/images/${DEVICE}/promotion/renew/img_2_qz_po_f.png') 0 0 no-repeat} */
						/* .pop-qz-comp {position: relative; padding-top: 450px; background: url('${CTX}/images/${DEVICE}/promotion/renew/img_2_qz_po_c.png') 0 0 no-repeat} */
						.pop-qz-comp .qz-result {position: absolute; left: 0; top: 50%; width: 100%; text-align: center; color: #000000; font-size: 10px;}
						.pop-qz .qz-share {position: absolute; left: 5%; bottom: 4.5%; width: 90%; background-color: #f0f0f0; border-radius: 9px;}
						.pop-qz .qz-share {padding: 15px 0;}
						.pop-qz .qz-share p {line-height: 1.2; font-size: 10px; color: #000000}
						.pop-qz .qz-share-sns {margin-top: 10px; font-size: 0; text-align: center;}
						.pop-qz .qz-share-sns a {display: inline-block; width: 29px; margin: 0 5px;}
						
						@media only screen and (max-device-width : 320px){
							.pop-qz-comp .qz-result {font-size: 9px;}
							.pop-qz .qz-share {padding: 10px 0;}
							.pop-qz .qz-share p {font-size: 9px;}
							.pop-qz .qz-share-sns {margin-top: 5px;}
							.pop-qz .qz-share-sns a {margin: 0 2px;}
						}



						
					</style>
					
					<div class="renew-top">
						<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_tit.png" alt="" />
						<div class="renew-menus">
							<a href="${CTX}/event/entry/renewEvent.do?eventIdx=87" class="menus-anchor"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_cm_mn_off.png" alt="100% 당첨, 소문 내면 맛있는 라면!"></a>
							<a href="#none" class="menus-anchor"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_mn_on.png" alt="브랜드 퀴즈, POINT 다-주고! 엄청난 선물 더-주고!"></a>
						</div>
					</div>

					<div class="renew-row renew-qz-guide">
						<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_c_1.png" alt="">
						<a href="#gift"><span class="hide">선물 보러가기</span></a>
					</div>

					<div class="renew-row renew-qz-map">
						<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_map.png" alt="" />
						<!-- [D] 완료된 퀴즈일 경우 map-item에 clear 추가 -->
						<!-- 
						- map-tem-ga : 갸스비
						- map-tem-bi : 비페스타
						- map-tem-lu : 루시도엘
						- map-tem-ma : 마마버터
						- map-tem-de : 덴탈프로
						- map-tem-ch : 찰리
						-->
						<div id="qz-item1-ga" class="map-item map-item-ga <c:if test="${!empty joinQuiz1}">clear</c:if>"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_map_1.png" alt="" /></div>
						<div id="qz-item1-bi" class="map-item map-item-bi <c:if test="${!empty joinQuiz2}">clear</c:if>"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_map_2.png" alt="" /></div>
						<div id="qz-item1-lu" class="map-item map-item-lu <c:if test="${!empty joinQuiz3}">clear</c:if>"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_map_3.png" alt="" /></div>
						<div id="qz-item1-ma" class="map-item map-item-ma <c:if test="${!empty joinQuiz4}">clear</c:if>"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_map_4.png" alt="" /></div>
						<div id="qz-item1-de" class="map-item map-item-de <c:if test="${!empty joinQuiz5}">clear</c:if>"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_map_5.png" alt="" /></div>
						<div id="qz-item1-ch" class="map-item map-item-ch <c:if test="${!empty joinQuiz6}">clear</c:if>"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_map_6.png" alt="" /></div>
					</div>

					<div class="renew-row renew-qz-list">
						<ul>
							<!-- s: 갸스비퀴즈 -->
							<!-- [D] 완료된 퀴즈일 경우 li에 clear 추가 -->
							<!-- <li class="qz-item qz-item-ga clear"> -->
							<li class="qz-item qz-item-ga <c:if test="${!empty joinQuiz1}">clear</c:if>" id="qz-item2-ga">
								<div class="qz-item-inner">
									<strong class="item-title"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_ga_t.png" alt=""></strong>
									<p class="item-quiz"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_ga_q.png" alt="" /></p>
									<div class="item-select">
										<div class="select-block">
											<input type="radio" id="optQuizGatsby1" name="rdo_answer1" value="1"  />
											<label for="optQuizGatsby1">
												<span class="num"><span>1</span></span>
												<span class="txt"><span>무빙워크</span></span>
											</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
										<div class="select-block">
											<input type="radio" id="optQuizGatsby2" name="rdo_answer1" value="2"  />
											<label for="optQuizGatsby2">
												<span class="num"><span>2</span></span>
												<span class="txt"><span>무빙러버</span></span>
											</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
										<div class="select-block">
											<input type="radio" id="optQuizGatsby3" name="rdo_answer1" value="3"  />
											<label for="optQuizGatsby3">
												<span class="num"><span>3</span></span>
												<span class="txt"><span>무비월드</span></span>
											</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
										<div class="select-block">
											<input type="radio" id="optQuizGatsby4" name="rdo_answer1" value="4"  />
											<label for="optQuizGatsby4">
												<span class="num"><span>4</span></span>
												<span class="txt"><span>무빙도어</span></span>
											</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
									</div>
									<div class="item-btns">
										<a href="${CTX}/brand/brandGatsby.do?brandIdx=1" target="_new"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_ga_ht.png" alt="" /></a>
										<a href="#none"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_bt_ok.png" alt="" class="answerBtn" data-quiz-no="1" /></a>
									</div>
								</div>
							</li>
							<!-- s: 비페스타 -->
							<li class="qz-item qz-item-bi <c:if test="${!empty joinQuiz2}">clear</c:if>" id="qz-item2-bi">
								<div class="qz-item-inner">
									<strong class="item-title"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_bi_t.png" alt=""></strong>
									<p class="item-quiz"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_bi_q.png" alt="" /></p>
									<div class="item-select">
										<div class="select-block">
											<input type="radio" id="optQuizBifesta1" name="rdo_answer2" value="1" />
											<label for="optQuizBifesta1">
												<span class="num"><span>1</span></span>
												<span class="txt"><span>저보습<br/>저자극</span></span>
											</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
										<div class="select-block">
											<input type="radio" id="optQuizBifesta2" name="rdo_answer2" value="2" />
											<label for="optQuizBifesta2">
												<span class="num"><span>2</span></span>
												<span class="txt"><span>중보습<br/>고자극</span></span>
											</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
										<div class="select-block">
											<input type="radio" id="optQuizBifesta3" name="rdo_answer2" value="3" />
											<label for="optQuizBifesta3">
												<span class="num"><span>3</span></span>
												<span class="txt"><span>고보습<br/>고자극</span></span>
											</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
										<div class="select-block">
											<input type="radio" id="optQuizBifesta4" name="rdo_answer2" value="4" />
											<label for="optQuizBifesta4">
												<span class="num"><span>4</span></span>
												<span class="txt"><span>고보습<br/>저자극</span></span>
											</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
									</div>
									<div class="item-btns">
										<a href="${CTX}/brand/brandBifesta.do" target="_new"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_bi_ht.png" alt="" /></a>
										<a href="#none"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_bt_ok.png" alt="" class="answerBtn" data-quiz-no="2" /></a>
									</div>
								</div>
							</li>

							<!-- s: 루시도엘 -->
							<li class="qz-item qz-item-lu <c:if test="${!empty joinQuiz3}">clear</c:if>" id="qz-item2-lu">
								<div class="qz-item-inner">
									<strong class="item-title"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_lu_t.png" alt=""></strong>
									<p class="item-quiz"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_lu_q.png" alt="" /></p>
									<div class="item-select">
										<div class="select-block">
											<input type="radio" id="optQuizLucidol1" name="rdo_answer3" value="1" />
											<label for="optQuizLucidol1">
												<span class="num"><span>1</span></span>
												<span class="txt"><span>올리브<br/>오일</span></span>
											</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
										<div class="select-block">
											<input type="radio" id="optQuizLucidol2" name="rdo_answer3" value="2" />
											<label for="optQuizLucidol2">
												<span class="num"><span>2</span></span>
												<span class="txt"><span>코코넛<br/>오일</span></span>
											</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
										<div class="select-block">
											<input type="radio" id="optQuizLucidol3" name="rdo_answer3" value="3" />
											<label for="optQuizLucidol3">
													<span class="num"><span>3</span></span>
													<span class="txt"><span>아르간<br/>오일</span></span>
												</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
										<div class="select-block">
											<input type="radio" id="optQuizLucidol4" name="rdo_answer3" value="4" />
											<label for="optQuizLucidol4">
												<span class="num"><span>4</span></span>
												<span class="txt"><span>옥수수<br/>튀김 오일</span></span>
											</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
									</div>
									<div class="item-btns">
										<a href="${CTX}/brand/brandLucidoL.do" target="_new"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_lu_ht.png" alt="" /></a>
										<a href="#none"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_bt_ok.png" alt="" class="answerBtn" data-quiz-no="3" /></a>
									</div>
								</div>
							</li>

							<!-- s: 마마버터 -->
							<li class="qz-item qz-item-ma <c:if test="${!empty joinQuiz4}">clear</c:if>" id="qz-item2-ma">
								<div class="qz-item-inner">
									<strong class="item-title"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_ma_t.png" alt=""></strong>
									<p class="item-quiz"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_ma_q.png" alt="" /></p>
									<div class="item-select">
										<div class="select-block">
											<input type="radio" id="optQuizMama1" name="rdo_answer4" value="1"  />
											<label for="optQuizMama1">
												<span class="num"><span>1</span></span>
												<span class="txt"><span>버터플라이</span></span>
											</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
										<div class="select-block">
											<input type="radio" id="optQuizMama2" name="rdo_answer4" value="2" />
											<label for="optQuizMama2">
												<span class="num"><span>2</span></span>
												<span class="txt"><span>버터쿠키</span></span>
											</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
										<div class="select-block">
											<input type="radio" id="optQuizMama3" name="rdo_answer4" value="3"  />
											<label for="optQuizMama3">
												<span class="num"><span>3</span></span>
												<span class="txt"><span>마아가린</span></span>
											</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
										<div class="select-block">
											<input type="radio" id="optQuizMama4" name="rdo_answer4" value="4"  />
											<label for="optQuizMama4">
												<span class="num"><span>4</span></span>
												<span class="txt"><span>시어버터</span></span>
											</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
									</div>
									<div class="item-btns">
										<a href="${CTX}/brand/brandMama.do" target="_new"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_ma_ht.png" alt="" /></a>
										<a href="#none"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_bt_ok.png" alt="" class="answerBtn" data-quiz-no="4" /></a>
									</div>
								</div>
							</li>

							<!-- s: 덴타프로 -->
							<li class="qz-item qz-item-de <c:if test="${!empty joinQuiz5}">clear</c:if>" id="qz-item2-de">
								<div class="qz-item-inner">
									<strong class="item-title"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_de_t.png" alt=""></strong>
									<p class="item-quiz"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_de_q.png" alt="" /></p>
									<div class="item-select">
										<div class="select-block">
											<input type="radio" id="optQuizDental1" name="rdo_answer5" value="1"  />
											<label for="optQuizDental1">
												<span class="num"><span>1</span></span>
												<span class="txt"><span>0</span></span>
											</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
										<div class="select-block">
											<input type="radio" id="optQuizDental2" name="rdo_answer5" value="2"  />
											<label for="optQuizDental2">
												<span class="num"><span>2</span></span>
												<span class="txt"><span>1</span></span>
											</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
										<div class="select-block">
											<input type="radio" id="optQuizDental3" name="rdo_answer5" value="3"  />
											<label for="optQuizDental3">
												<span class="num"><span>3</span></span>
												<span class="txt"><span>2</span></span>
											</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
										<div class="select-block">
											<input type="radio" id="optQuizDental4" name="rdo_answer5" value="4"  />
											<label for="optQuizDental4">
												<span class="num"><span>4</span></span>
												<span class="txt"><span>3</span></span>
											</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
									</div>
									<div class="item-btns">
										<a href="${CTX}/brand/brandDental.do" target="_new"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_de_ht.png" alt="" /></a>
										<a href="#none"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_bt_ok.png" alt="" class="answerBtn" data-quiz-no="5" /></a>
									</div>
								</div>
							</li>

							<!-- s: 찰리 -->
							<li class="qz-item qz-item-ch <c:if test="${!empty joinQuiz6}">clear</c:if>" id="qz-item2-ch">
								<div class="qz-item-inner">
									<strong class="item-title"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_ch_t.png" alt=""></strong>
									<p class="item-quiz"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_ch_q.png" alt="" /></p>
									<div class="item-select">
										<div class="select-block">
											<input type="radio" id="optQuizCharley1" name="rdo_answer6" value="1" />
											<label for="optQuizCharley1">
												<span class="num"><span>1</span></span>
												<span class="txt"><span>동산</span></span>
											</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
										<div class="select-block">
											<input type="radio" id="optQuizCharley2" name="rdo_answer6" value="2" />
											<label for="optQuizCharley2">
												<span class="num"><span>2</span></span>
												<span class="txt"><span>등산</span></span>
											</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
										<div class="select-block">
											<input type="radio" id="optQuizCharley3" name="rdo_answer6" value="3" />
											<label for="optQuizCharley3">
												<span class="num"><span>3</span></span>
												<span class="txt"><span>탄산</span></span>
											</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
										<div class="select-block">
											<input type="radio" id="optQuizCharley4" name="rdo_answer6" value="4" />
											<label for="optQuizCharley4">
												<span class="num"><span>4</span></span>
												<span class="txt"><span>우산</span></span>
											</label>
											<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_blank.png" alt="" />
										</div>
									</div>
									<div class="item-btns">
										<a href="${CTX}/brand/brandCharley.do" target="_new"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_ch_ht.png" alt="" /></a>
										<a href="#none"><img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_bt_ok.png" alt="" class="answerBtn" data-quiz-no="6"  /></a>
									</div>
								</div>
							</li>


						</ul>
					</div>
					<a name="gift"></a>
					<div class="renew-row">
						<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_c_3.png" alt="">
					</div>

					<div class="renew-row">
						<img src="${CTX}/images/${DEVICE}/promotion/renew/img_cm_nt.png" alt="">
					</div>

				</div>
				<!-- e: 리뉴얼 퀴즈 -->


				<!-- s: 팝업: 정답일경우 -->
				<div class="remodal renew-modal" data-remodal-id="popupComplete">
					<button data-remodal-action="close" class="remodal-close"></button>
					<div class="pop-qz pop-qz-comp">
						<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_po_co.png" alt="">
						<div class="qz-result">
							<strong class="hide">정답입니다!</strong>
							500P는 10.1(월) 일괄 적립됩니다.<br/>
							<span id="giftname">갸스비 경품 ‘순토 엠빗 3’ </span> 경품에 자동 응모 되셨습니다.
						</div>
						<div class="qz-share">
							<p>
								친구에게 다주고! 더주는!<br/>
								퀴즈 이벤트를 공유해 주세요~
							</p>
							<div class="qz-share-sns">
								<a href="#none" onclick="snsShare('N');"><img src="${CTX}/images/w/promotion/renew/img_2_qz_po_s_n.png" alt="네이버" ></a>
								<a href="#none" onclick="snsShare('F');"><img src="${CTX}/images/w/promotion/renew/img_2_qz_po_s_f.png" alt="페이스북"></a>
								<a href="#none" onclick="snsShare('K');"><img src="${CTX}/images/w/promotion/renew/img_2_qz_po_s_k.png" alt="카카오톡"></a>
								<a href="#none" onclick="snsShare('T');"><img src="${CTX}/images/w/promotion/renew/img_2_qz_po_s_t.png" alt="트위터"></a>
								<a href="#none" onclick="urlShare();"><img src="${CTX}/images/w/promotion/renew/img_2_qz_po_s_u.png" alt="URL"></a>
							</div>
						</div>
					</div>
				</div>

				<!-- s: 팝업: 오답일경우 -->
				<div class="remodal renew-modal" data-remodal-id="popupFail">
					<button data-remodal-action="close" class="remodal-close"></button>
					<div class="pop-qz pop-qz-fail">
						<img src="${CTX}/images/${DEVICE}/promotion/renew/img_qz_po_fail.png" alt="" />
						<div class="qz-share">
							<p>
								친구에게 다주고! 더주는!<br/>
								퀴즈 이벤트를 공유해 주세요~
							</p>
							<div class="qz-share-sns">
								<a href="#none" onclick="snsShare('N');"><img src="${CTX}/images/w/promotion/renew/img_2_qz_po_s_n.png" alt="네이버" ></a>
								<a href="#none" onclick="snsShare('F');"><img src="${CTX}/images/w/promotion/renew/img_2_qz_po_s_f.png" alt="페이스북"></a>
								<a href="#none" onclick="snsShare('K');"><img src="${CTX}/images/w/promotion/renew/img_2_qz_po_s_k.png" alt="카카오톡"></a>
								<a href="#none" onclick="snsShare('T');"><img src="${CTX}/images/w/promotion/renew/img_2_qz_po_s_t.png" alt="트위터"></a>
								<a href="#none" onclick="urlShare();"><img src="${CTX}/images/w/promotion/renew/img_2_qz_po_s_u.png" alt="URL"></a>
							</div>
						</div>
					</div>
				</div>
		
		
		
		
		</div>
	</div>
</body>
</html>