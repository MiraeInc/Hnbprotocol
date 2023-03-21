<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main"/>	

<script>

$(function(){
	createNumeric(".createNumeric");
	
	$(document).on('click', '.btn_inq', function(){
		var detlPos = $('.btn_inq_form').offset().top;
		$('html, body').animate( { scrollTop : detlPos }, 400 );
	});

	$(document).on('click', '.btn_inq_form', function(){
		$('#divCreatorForm').toggle();
	});

	$(document).on('click', '#btnInqClose', function(){
		$('#divCreatorForm').hide();
	});

	// 파일업로드 UI
	$('input[data-target="file"]').on('change', function (e) {
					
		var $file = $(this);
		var target = $file.attr('data-target');
		
		if(window.FileReader){  // modern browser
			var filename = $(this)[0].files[0].name;
		}
		else {  // old IE
			var filename = $(this).val().split('/').pop().split('\\').pop();  // 파일명만 추출
		}

		// 추출한 파일명 삽입
		$('[data-id='+ target +']').text(filename);
	});
});

// 첨부파일 체크 콜백 함수
function callbackFileCheck(val){
	if(val =="5"){alert("동영상 파일만 업로드 가능합니다."); return;}
}

// 크리에이터 지원
function saveCreator(){
	if($.trim($("#memberNm").val()) ==""){
		alert("1번 문항 작성해 주세요");
		$("#memberNm").focus(); 
		return false;
	}
	
	if($.trim($("#age").val()) ==""){
		alert("2번 문항 작성해 주세요");
		$("#age").focus(); 
		return false;
	}
	
	if($.trim($("#phoneNo").val()) ==""){
		alert("3번 문항 작성해 주세요");
		$("#phoneNo").focus(); 
		return false;
	}
	
	if($.trim($("#area").val()) ==""){
		alert("4번 문항 작성해 주세요");
		$("#area").focus(); 
		return false;
	}
	
	if($.trim($("#introduce").val()) ==""){
		alert("5번 문항 작성해 주세요");
		$("#introduce").focus(); 
		return false;
	}
	
	if($.trim($("#moment").val()) ==""){
		alert("6번 문항 작성해 주세요");
		$("#moment").focus(); 
		return false;
	}
	
	/* if($.trim($("#videoNm").val()) ==""){
		alert("대표 컨텐츠를 업로드 해 주세요.");
		$("#videoNm").focus(); 
		return false;
	} */
	
	if($("#policyAgree1").prop("checked") == false && $("#policyAgree2").prop("checked") == false){
		alert("11번 문항 체크해 주세요");
		$("#policyAgree1").focus(); 
		return false;
	}
	
	if(confirm("제출 하시겠습니까?") == true){
		var frm = document.eventForm;
		frm.action = "${CTX}/event/entry/creatorSave.do";
		frm.submit();
	}
}

</script>

</head>
<body>
<form id="eventForm" name="eventForm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="pageNo" id="pageNo" value="${schVO.pageNo}"/>
	<input type="hidden" name="eventIdx" id="eventIdx" value="${detail.eventIdx}"/>
	
	<div class="content comm-event evt-event-view">
		<page:applyDecorator  name="mobile.eventmenu" encoding="UTF-8"/>
		
		<div class="page-body">
			<div class="event-view">
				<style>
				    img {max-width: 100%;}
				
				    .creator-top {position: relative;}
				    .creator-top .btn_inq {position: absolute; left: 35.3%; top: 10.7%; width: 28.3%; height: 1.2%;}
				    .creator-top .btn_inq_form {position: absolute; left: 24.5%; top: 97.6%; width: 48.4%; height: 1.5%;}
				
				    .creator-body {position: relative; display: none;}
				    
				    .creator-form {padding: 15px 8.7% 30px; background:#00bbbe url('${CTX}/images/m/creator/creator_form.png') 0 0 repeat-y; background-size: 100%;}
				    .creator-form .cform-text {margin-bottom: 10px; color: #ff2b2b; font-size: 9px;}
				    .creator-form .cform-row {margin-bottom: 10px; }
				    .creator-form .cform-row.last {margin-bottom: 25px;}
				    .creator-form .cform-row .form-title {margin-bottom :7px; font-size: 10px; }
				    .creator-form .cform-row .form-title i {color: #ff2b2b;}
				    .creator-form .cform-row .form-body {font-size: 9px;}
				    .creator-form .cform-row .textarea {width: 100%; height: 50px; padding: 6px 5px; border: 1px solid #d7d7d7;}
				    .creator-form .cform-row .input {width: 100%; height: 25px; padding: 6px 5px; border: 1px solid #d7d7d7;}
				    .creator-form .cform-row .form-custom {margin-right: 65px;}
				    .creator-form .cform-row .form-custom label {font-size: 9px;}
				    .creator-form .cform-submit {text-align: center;}
				    .creator-form .cform-submit a {display: inline-block;}
				    .creator-form .cform-submit img {width: 48.4%;}
				
				    .filebox {display:inline-block; position:relative; margin-right: 10px; vertical-align: middle}
				    .filebox .btn-fake {display: inline-block;}
				    .filebox .btn-fake img {height: 25px;}
				    .filebox .file {position: absolute; right: 0; top: 0; opacity: 0; width:100%; height:100%; filter: alpha(opacity=0); -ms-filter: "alpha(opacity=0)"; -khtml-opacity: 0; -moz-opacity: 0;}
				    .filebox + p {display: inline-block; }
				
				    .form-control.sym-txt {padding-right:15px}
				    .form-control.sym-txt:after {position:absolute; right:0; top:0; min-width:15px; text-align: right; line-height: 24px; font-size: 12px; content: attr(data-format)}
				
				    .radiobox {margin-right: 35px;}
				    .radiobox .lbl {font-size: 10px; }
				    .radiobox .lbl:before {width: 18px; height: 18px; background-image: url('${CTX}/images/m/creator/ico_radiobox.png'); background-size: 18px 36px; background-position: left top;}
				    .radiobox .radio:checked + .lbl:before { background-position: left bottom;}
				</style>

				<div class="creator-top">
					<img src="${CTX}/images/m/creator/creator_top.png" alt="‘맨담코리아 공식몰’과 함께 할 크리에이터 제 1기를 모집합니다!" />
					<div class="hide">
						<strong>맨담코리아 공식몰</strong>
						맨담코리아의 공식쇼핑몰로서 갸스비, 비페스타, 루시도엘
						남성 헤어 스타일링의 중심, 최강 클렌징 총집함, 아르간 헤어 오일의 자존심
						등을 비롯한 다양한 브랜드 판매

						<strong>모집분야</strong>
						뷰티, 헤어스타일링에 관심이 많은 20대~30대 남자, 여자 각 1명

						<strong>응모컨텐츠</strong>
						다양한 주제, 컨셉의 제품 리뷰 컨텐츠(영상 제출)
						콘텐츠 길이 제한 없음, 브랜드 상관 없음, 기존 제작된 콘텐츠 제출 하여도 무방

						<strong>활동혜택</strong>
						1. 맨담코리아 공식몰 '공식 크리에이터' 기회
						2. 자사 제품 무상지원 (공식몰 월 10만 포인트 지급)
						3. 공식 크리에이터 제작 지원금 지급, 월 2편 콘텐츠 제작, 150만원 지원금 지급 블로그, 유튜브 운영

						<strong>크리에이터 활동 내용</strong>
						맨담코리아 공식물 동영상 콘텐츠 직접 제작(월 2편)
						맨담코리아 공식몰 네이버 블로그 직접 운영(월 2건)
						제작물의 저직권은 맨담코리아에 속합니다.

						<strong>모집일정</strong>
						지원기간: 6월 19일 ~ 7월 12일 (24일간)
						발표: 7월 19일
						활동기간: 2019년 8월 ~ 12월(5개월)

						<strong>참여방법</strong>
						언박싱, 뷰티, 헤어스타일링, 자유주제
						타사제품 사용하여 리뷰하여도 무방함

						step1. 네이버에서 맨담몰을 검색한다.
						step2. 이벤트를 클릭하여 모집 이벤트 접속!
						step3. 지원하기를 통해 정보입력 후 제출
					</div>

					<a href="#divCreatorForm" class="btn_inq"><img src="${CTX}/images/m/creator/btn_inq_1.png" alt="지원하기" /></a>
					<a href="javascript:void(0);" class="btn_inq_form"><img src="${CTX}/images/m/creator/btn_inq_2.png" alt="지원서작성"/></a>
				</div>
				
				<div id="divCreatorForm" class="creator-body">
					<div class="creator-form">
						<div class="cform-text">* 별표 모양은 ‘필수 항목’ 입니다.</div>
						<div class="cform-row">
							<p class="form-title">
								<strong>1. 이름(본명)과 '별명, 닉네임'을 적어주세요 <i>*</i> </strong>
							</p>
							<div class="form-body">
								<input type="text" class="input" name="memberNm" id="memberNm" value=""/>
							</div>
						</div>
						
						<div class="cform-row">
							<p class="form-title">
								<strong>2. 나이를 알려주세요  <i>*</i></strong> 
							</p>
							<div class="form-body">
								<input type="text" class="input" name="age" id="age" value=""/>
							</div>
						</div>
						
						<div class="cform-row">
							<p class="form-title">
								<strong>3. 연락 가능한 연락처를 알려주세요  <i>*</i> </strong>(당선자에 한하여 개별 연락 드립니다)  
							</p>
							<div class="form-body">
								<input type="text" class="input" name="phoneNo" id="phoneNo" value=""/>
							</div>
						</div>
						
						<div class="cform-row">
							<p class="form-title">
								<strong>4. 활동하시는 지역이 어디 인가요?  <i>*</i> </strong>(서울, 강원, 부산, 제주 등 지역명으로 표기) 
							</p>
							<div class="form-body">
								<input type="text" class="input" name="area" id="area" value=""/>
							</div>
						</div>
						
						<div class="cform-row">
							<p class="form-title">
								<strong>5. 간단한 자기소개 부탁 드립니다 ^_^  <i>*</i></strong> 
							</p>
							<div class="form-body">
								<textarea class="textarea" name="introduce" id="introduce"></textarea>
							</div>
						</div>

						<div class="cform-row">
							<p class="form-title">
								<strong>6. 맨담코리아 공식몰 크리에이터에 지원하게 된 계기는 무엇인가요?  <i>*</i> </strong>
							</p>
							<div class="form-body">
								<textarea class="textarea" name="moment" id="moment"></textarea>
							</div>
						</div>

						<div class="cform-row">
							<p class="form-title">
								<strong>7. 현재 운영하고 있는 유튜브 채널, SNS가 있다면 알려주세요!</strong><br/>
								(자유롭게 작성하여 주시고, 없으실 경우 아무런 불이익이 없음을 알려드립니다.)
							</p>
							<div class="form-body">
								<textarea class="textarea" name="snsUrl" id="snsUrl"></textarea>
							</div>
						</div>

						<div class="cform-row">
							<p class="form-title">
								<strong>8. 이번 크리에이터 모집에 제출하고 싶은 1개의 대표 컨텐츠를 업로드 해주세요  <!-- <i>*</i> --> </strong><br/>
								(1GB 미만 / 해당 컨텐츠로 심사 진행하게 됩니다) <br/>
								[URL로 대체 가능 합니다. -> 9번 문항]
							</p>
							<div class="form-body">
								<div class="filebox">
									<span class="btn-fake"><img src="${CTX}/images/m/creator/btn_file.png" alt="파일찾기" /></span>
									<input type="file" class="file" data-target="file" name="videoNm" id="videoNm" onchange="fileCheck(this,'video');"/>
								</div>
								<p data-id="file"></p>
							</div>
						</div>

						<div class="cform-row">
							<p class="form-title">
								<strong>9. URL 제출을 원하시는 경우 입력 해주세요</strong>
							</p>
							<div class="form-body">
								<input type="text" class="input" name="videoUrl" id="videoUrl" value=""/>
							</div>
						</div>

						<div class="cform-row">
							<p class="form-title">
								<strong>10. 그 외 맨담코리아 공식몰 관계자에게 전하고 싶은 메시지를 적어주세요</strong>  (문의사항, 전하고자 하는 메시지 등)
							</p>
							<div class="form-body">
								<input type="text" class="input" name="message" id="message" value=""/>
							</div>
						</div>

						<div class="cform-row last">
							<p class="form-title">
								<strong>11. 당선자 선발 및 공지에 필요한 ‘이름 / 나이 / 연락처’에 대한 개인정보제공에 동의 하십니까?  <i>*</i> </strong><br/>
								(개인정보는 당선자 발표일 이후 7일 이내 폐기되며, 당선자 공지 연락 이외에 아무런 마케팅 활용에 사용되지 않습니다.<br/>
								개인정보 제공 여부는 자유 이지만, 동의 할 수 없는 경우에는 선발 대상에서 제외 됨을 알려드립니다.) 
							</p>
							<div class="form-body">
								<span class="radiobox">
									<input type="radio" id="policyAgree1" class="radio" name="privacyYn" value="Y"/>
									<label for="policyAgree1" class="lbl">네</label>
								</span>
								<span class="radiobox">
									<input type="radio" id="policyAgree2" class="radio" name="privacyYn" value="N"/>
									<label for="policyAgree2" class="lbl">아니오</label>
								</span>
							</div>
						</div>

						<div class="cform-submit">
							<a href="javascript:void(0);" onclick="saveCreator();"><img src="${CTX}/images/m/creator/btn_submit.png" alt="" /></a>
						</div>
						
						<div class="cform-submit" style="margin-top: 10px;">
							<a href="javascript:void(0);" id="btnInqClose"><img src="${CTX}/images/m/creator/btn_close.png" alt="" /></a>
						</div>
					</div>

				</div>
				<div class="creator-footer">
					<img src="${CTX}/images/m/creator/creator_footer.png" alt="">
				</div>
			</div>
		</div>
	</div>
</form>
</body>
</html>