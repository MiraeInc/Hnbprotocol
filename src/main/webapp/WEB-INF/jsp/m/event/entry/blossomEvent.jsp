<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main"/>	
<style>

    .evtReqContainer {padding-top: 80px; background: url('${CTX}/images/m/@dummy/event_mo_202002102.jpg') top left no-repeat; background-size: cover;}
    .evtReqWrap {padding: 0 15% 110px; background: url('${CTX}/images/m/@dummy/event_mo_202002103.jpg') bottom left no-repeat; background-size: contain;}
    .evtReqForm {}
    .evtReqForm .evtReqRow:first-child dd {margin: 0;}
    .evtReqRow {}
    .evtReqRow::after {display: block; clear: both; content: '';}
    .evtReqRow dt {float: left; width: 55px; line-height: 41px; font-size: 12px;}
    .evtReqRow dd {position: relative; margin: 26px 0 0; padding-left: 60px; border-bottom: 1px solid #887b72}
    .evtReqRow .evtReqInp {padding: 10px 0}
    .evtReqRow .evtReqInp input {width: 100%; height: 21px; line-height: 21px; font-size: 14px; border: 0; background:none}
    .evtReqRow .evtReqInfo {position: absolute; left: 0; bottom: -31px; padding: 5px 0; color: #fd434e; font-size: 10px;}
    .evtReqRow .evtReqFail {position: absolute; right: 0; bottom: -26px; padding: 5px 0; color: #fd434e; font-size: 9px;}
    
    .evtReqRow.rowAddr dd {padding-right: 70px}
    .evtReqRow.rowAddr .btnSrchAddr {position: absolute; right: 0; top: 50%; margin-top: -14px; padding: 0 8px; line-height: 28px; color: #ffffff; font-size: 12px; background: #000000;}

    .evtReqTerm {margin-top: 45px;}
    .evtReqTerm .termCheck {}
    .evtReqTerm .termCont {margin-top: 10px; height: 75px; padding: 10px; font-size: 12px; overflow-y: auto; border: 1px solid #f4f4f4;}
    .evtReqTerm .checkbox {line-height: 15px;}
    .evtReqTerm .checkbox .lbl {display:inline-block; position:relative; padding:1px 0 0 23px; font-size: 14px;}
    .evtReqTerm .checkbox .lbl:before {display:inline-block; position:absolute; left:0; top:0; width:18px; height:18px; background:url('/w/images/@dummy/event_pc_20200210_check.png') 0 0 no-repeat; background-size: 36px 18px; content:'';}
    .evtReqTerm .checkbox .check {position:absolute; left:0; top:0; z-index: -1;  opacity: 0.01; width:18px; height:18px;}
    .evtReqTerm .checkbox .check:checked + .lbl:before {background-position: -18px 0 ;}
</style>

<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<script>

//응모하기
function goSave(){
	var individualInfoYn = $("input:checkbox[id='individualInfoYn']").is(":checked");		// 개인정보 수집 및 이용 동의
	
	if($.trim($("#entryNo").val()) == ""){
		alert("행운번호를 입력해 주세요.");
		$("#entryNo").focus();
		return false;
	}
	if($.trim($("#entryNm").val()) == ""){
		alert("신청자명을 입력해 주세요.");
		$("#entryNm").focus();
		return false;
	}
	if($.trim($("#telNo").val()) == ""){
		alert("전화번호를 입력해 주세요.");
		$("#telNo").focus();
		return false;
	}
	
	if($.trim($("#zipCd").val()) == ""){
		alert("우편번호 검색을 해주세요.");
		return false;
	}
	
	if($.trim($("#addrDetail").val()) == ""){
		alert("상세주소를 입력해 주세요.");
		$("#addrDetail").focus();
		return false;
	}
	
	
	if(!individualInfoYn){
      alert("개인정보 수집 및 이용에 동의해 주세요.");
      return false;
  }
	
	// 응모번호 확인
	$.ajax({
		 url: "${CTX}/ajax/event/entry/entryNoCheck2020Ajax.do",
		 data : {
			 			"entryNo"	:	$("#entryNo").val() 
			 		},
		 type: "get",	
		 async: false,
		 cache: false,
		 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 error: function(request, status, error){ 
		 	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
		 },
		 success: function(flag){
			 if(Number(flag) > 0){
		 		var frm = document.entryForm;
				frm.action="${CTX}/event/entry/entryEvent2020Save.do";
				frm.submit();						
			 }else{
				 alert("행운번호가 유효하지 않거나 이미 응모하기가 되었습니다.");
				 return false;
			 } 
		 }
	});
}

	
	// 우편번호찾기
/* 	function addrPopup(){
		execDaumPostcode(callback);		
	}
	 */
	// CALLBACK 주소정보 값
	var callback = function(zipCd, oldZipCd, addr, oldAddr){
		$("#zipCd").val(zipCd);					// 우편번호(신)
		$("#addr").val(addr);						// 주소 (신)
		$("#oldZipCd").val(oldZipCd);		// 우편번호 (구)
		$("#oldAddr").val(oldAddr);			// 주소 (구)
		$("#addrDetail").focus();
   	}

	    function closeDaumPostcode() {
	        var element_layer = document.getElementById('post_layer_div');
	        // iframe을 넣은 element를 안보이게 한다.
	        element_layer.style.display = 'none';
	    }
	    // 브라우저의 크기 변경에 따라 레이어를 가운데로 이동시키고자 하실때에는
	    // resize이벤트나, orientationchange이벤트를 이용하여 값이 변경될때마다 아래 함수를 실행 시켜 주시거나,
	    // 직접 element_layer의 top,left값을 수정해 주시면 됩니다.
	    function initLayerPosition(){
	   	   	var width = 350; //우편번호서비스가 들어갈 element의 width
	         var height = 450; //우편번호서비스가 들어갈 element의 height
	         var borderWidth = 2; //샘플에서 사용하는 border의 두께

			 var element_layer = document.getElementById('post_layer_div');
	        // 위에서 선언한 값들을 실제 element에 넣는다.
	        element_layer.style.width = width + 'px';
	        element_layer.style.height = height + 'px';
	        element_layer.style.border = borderWidth + 'px solid';

	        // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
	        element_layer.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width)/2 - borderWidth) + 'px';
	        element_layer.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height)/2 - borderWidth) + 'px';
	    }
	<%-- 우편번호 검색 --%>
	function addrPopup(){
		// execDaumPostcode(callback);
		 var element_layer = document.getElementById('post_layer_div');
			
		 new daum.Postcode({
	            oncomplete: function(data) {
	                // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

	                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	                var fullAddr = data.address; // 최종 주소 변수
	                var extraAddr = ''; // 조합형 주소 변수

	                // 기본 주소가 도로명 타입일때 조합한다.
	                if(data.addressType === 'R'){
	                    //법정동명이 있을 경우 추가한다.
	                    if(data.bname !== ''){
	                        extraAddr += data.bname;
	                    }
	                    // 건물명이 있을 경우 추가한다.
	                    if(data.buildingName !== ''){
	                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                    }
	                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
	                    fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
	                }

	                // 우편번호와 주소 정보를 해당 필드에 넣는다.
	             //   document.getElementById('sample2_postcode').value = data.zonecode; //5자리 새우편번호 사용
	             //   document.getElementById('sample2_address').value = fullAddr;
	             //   document.getElementById('sample2_addressEnglish').value = data.addressEnglish;
	             	callback(data.zonecode, data.postcode, fullAddr, data.jibunAddress);
	                // iframe을 넣은 element를 안보이게 한다.
	                // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
	                element_layer.style.display = 'none';
	            },
	            width : '100%',
	            height : '100%',
	            maxSuggestItems : 5
	        }).embed(element_layer);

	        // iframe을 넣은 element를 보이게 한다.
	        element_layer.style.display = 'block';

	        // iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
	       initLayerPosition();
	}
	
</script>
</head>
<body>
<div class="content comm-event event-main">
	    <page:applyDecorator  name="mobile.eventmenu" encoding="UTF-8"/>
	
<div class="page-body">
			<div class="brand-filter">
				<a href="${CTX}/event/event/eventList.do" data-brand="all" class="<c:if test="${VO.brandIdx eq null || VO.brandIdx eq ''}">active</c:if>"><span>ALL</span></a>
				<a href="${CTX}/event/event/eventList.do?brandIdx=1" data-brand="gatsby" class="<c:if test="${VO.brandIdx eq 1}">active</c:if>"><span>갸스비</span></a>
				<a href="${CTX}/event/event/eventList.do?brandIdx=3" data-brand="bifesta" class="<c:if test="${VO.brandIdx eq 3}">active</c:if>"><span>비페스타</span></a>
				<a href="${CTX}/event/event/eventList.do?brandIdx=4" data-brand="lucidol" class="<c:if test="${VO.brandIdx eq 4}">active</c:if>"><span>루시도엘</span></a>
				<a href="${CTX}/event/event/eventList.do?brandIdx=9" data-brand="barrier" class="<c:if test="${VO.brandIdx eq 9}">active</c:if>"><span>베리어리페어</span></a>
<%--
				<a href="${CTX}/event/event/eventList.do?brandIdx=6" data-brand="mamabutter" class="<c:if test="${VO.brandIdx eq 6}">active</c:if>"><span>마마버터</span></a>
 --%>
				<a href="${CTX}/event/event/eventList.do?brandIdx=7" data-brand="dentalpro" class="<c:if test="${VO.brandIdx eq 7}">active</c:if>"><span>덴탈프로</span></a>
<%--
				<a href="${CTX}/event/event/eventList.do?brandIdx=8" data-brand="charley" class="<c:if test="${VO.brandIdx eq 8}">active</c:if>"><span>찰리</span></a>
				<a href="${CTX}/event/event/eventList.do?brandIdx=10" data-brand="gpcreate" class="<c:if test="${VO.brandIdx eq 10}">active</c:if>"><span>GPCREATE</span></a>
 --%>
			</div>
	<div class="event-view">
		<div class="event-img">
			<!-- 상품영역 -->
			<div style="position:relative;">
				<img src="${CTX}/images/m/@dummy/event_mo_202002101.jpg" alt="" />
				<div class="hide">
					루시도엘 봄한정 벚꽃 에디션<br/>
					루시도엘 벚꽃 에디션 사고 그립톡 친구 하나 나 하나<br/>
					<br/>
					루시도엘 벚꽃 에디션을 구입하면 <br/>
					패키지 안에 강한 작가의 한정 그립톡이 쏙쏙!<br/>
					<br/>
					친구의 그립톡은 응모카드에 있는 응모 번호로 응모해 주세요. <br/>
					추첨을 통해 250분께 강한 작가의 하트 그립톡을 추가 증정해 드립니다. <br/>
					<br/>
					응모 기간 : ~2020년 4월 16일 (목)까지<br/>
					당첨자 발표 : 2020년 4월 20일 (월) 개별 문자 발송<br/>
					스마트톡 발송 : 2020년 4월 29일 (수) 예정<br/>
					판매처 안내 : LOHB’s 일부 매장 / 올리브영 온라인 / 맨담코리아 공식몰<br/>
					<br/>
					이벤트 내용 및 일정은 당사의 사정에 따라 변경 될 수 있습니다.<br/>
					하나의 응모 번호에 대하여 1회 응모 가능합니다.
				</div>
				
				<a href="https://m.mandom.co.kr/m/product/productView.do?goodsCd=4902806407211" style="position:absolute; left: 11.8%; top: 60.2%; width:33.6%; height: 3.5%;"><span class="hide">상품보러가기</span></a>
				<a href="#eventForm01" style="position:absolute; left: 54.1%; top: 60.2%; width:33.6%; height: 3.5%;"><span class="hide">응모하러가기</span></a>
				
			</div>
			<!-- //상품영역 -->

			<!-- 응모영역 -->
			<form id=entryForm name="entryForm" method="post">
				<input type="hidden" name="oldZipCd" id="oldZipCd">
				<input type="hidden" name="oldAddr" id="oldAddr">
				<div id="eventForm01" style="position:relative;" class="evtReqContainer">
					<div class="evtReqWrap">
						<div class="evtReqForm">
							<dl class="evtReqRow">
								<dt>행운번호</dt>
								<dd>
									<div class="evtReqInp">
										<input type="text" id="entryNo" name="entryNo" maxlength="10"/>
									</div>
									<!-- <div class="evtReqFail">응모번호가 일치하지 않습니다. </div> -->
								</dd>
							</dl>
							<dl class="evtReqRow">
								<dt>성명</dt>
								<dd>
									<div class="evtReqInp">
										<input type="text" id="entryNm" name="entryNm" maxlength="10" />
									</div>
									<!-- <div class="evtReqFail">성명을 입력해 주세요. </div> -->
								</dd>
							</dl>
							<dl class="evtReqRow">
								<dt>전화번호</dt>
								<dd>
									<div class="evtReqInp">
										<input type="text" id="telNo" name="telNo" maxlength="15" />
									</div>
									<!-- <div class="evtReqFail">전화번호를 입력해 주세요.</div> -->
								</dd>
							</dl>
							<dl class="evtReqRow rowAddr">
								<dt>우편번호</dt>
								<dd>
									<div class="evtReqInp">
										<input type="text" id="zipCd" name="zipCd"  readonly="readonly" maxlength="5">
									</div>
									<a href="javascript:" class="btnSrchAddr" onclick="addrPopup();">우편번호</a>
									<!-- <div class="evtReqFail">전화번호를 입력해 주세요.</div> -->
								</dd>
							</dl>
							<dl class="evtReqRow">
								<dt>주소</dt>
								<dd>
									<div class="evtReqInp">
										<input type="text" id="addr" name="addr"  readonly="readonly">
									</div>
								</dd>
							</dl>
							<dl class="evtReqRow">
								<dt>상세주소</dt>
								<dd>
									<div class="evtReqInp">
										<input type="text" id="addrDetail" name="addrDetail"  maxlength="200">
									</div>
									<div class="evtReqInfo">※ 경품을 받으실 주소를 정확하게 입력해 주십시오.</div>
									<!-- <div class="evtReqFail">전화번호를 입력해 주세요.</div> -->
								</dd>
							</dl>
						</div>
						<div class="evtReqTerm">
							<div class="termCheck">
								<span class="checkbox">
									<input type="checkbox" id="individualInfoYn" class="check">
									<label for="individualInfoYn" class="lbl"><span class="txt">개인정보 수집 동의</span></label>
								</span>
							</div>
							<div class="termCont">
								이벤트 당첨 안내 및 경품 수령을 위해 개인정보 수집 및 활용에 대한 동의 입니다.<br/>
								관련 정보는 경품 발송일 1개월 이후 자동 폐기 처리됩니다.<br/>
								<br/>
								<strong>개인정보 수집 활용에 대한 동의</strong><br/>
								<br/>
								<strong>목적 :</strong> 이벤트 당첨자 본인 확인, 당첨 시 개별 연락 및 경품 배송<br/>
								<strong>항목 :</strong> 성명, 전화번호, 주소<br/>
								<strong>보유기간 :</strong> 경품 발송일 이후 1개월 이후까지<br/>
								<br/>
								본 이벤트 참여 고객은 개인정보 수집·이용에 대하여 동의를 거부할 권리를 가지고 있으며, 
								개인정보 수집·이용에 대한 미동의 시 이벤트에 참여하실 수 없습니다.
							</div>
						</div>

						<a href="javascript:" style="position: absolute; left: 0; bottom: 50px; width: 100%; text-align: center; " onclick="goSave();"><img src="${CTX}/images/m/@dummy/event_mo_202002104.jpg" style="height: 30px" /></a>
					</div>
					
				</div>
			</form>
			<!-- //응모영역 -->
		</div>
	</div>
</div>
</div>

<%-- 다음 우편번호 팝업 --%>
<div id="post_layer_div" style="display:none;position:fixed;overflow:hidden;z-index:2000000000000000000000000;-webkit-overflow-scrolling:touch;">
<img src="//t1.daumcdn.net/localimg/localimages/07/postcode/320/close.png" id="btnCloseLayer" style="cursor:pointer;position:absolute;right:-3px;top:-3px;z-index:1" onclick="closeDaumPostcode()" alt="닫기 버튼">
	<div  style="position:fixed; top:0; left:0; width:100%; height:100%;z-index:-1 ">
		<div style="position:absolute; top:0; left:0; width:100%; height:100%; background:#000; background:url(https://xpay.lgdacom.net/xpay/image/red_v25/common/bg.png); line-height:450px;"></div>
	</div>
</div>


</body>
</html>