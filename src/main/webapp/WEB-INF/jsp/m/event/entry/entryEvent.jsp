<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main"/>	
<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<script>

	// 응모하기
	function goSave(){
		var individualInfoYn = $("input:checkbox[id='individualInfoYn']").is(":checked");		// 개인정보 수집 및 이용 동의
		
		if($.trim($("#entryNo").val()) == ""){
			alert("응모번호를 입력해 주세요.");
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
		
		if($.trim($("#yyyy").val()) == "" || $.trim($("#mm").val()) == "" || $.trim($("#dd").val()) == ""){
			alert("생년월일을 선택해 주세요.");
			return false;
		}else{
			var birthDate = $.trim($("#yyyy").val()) + $.trim($("#mm").val()) + $.trim($("#dd").val());
			$("#birthDate").val(birthDate);
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
		
		if($("input[name=purchaseType]:checked").length <1){
			alert("왁스 구입 여부를 선택해 주세요."); 
			return false;
		}
		
		if(!individualInfoYn){
            alert("개인정보 수집 및 이용에 동의해 주세요.");
            return false;
        }
		
		// 응모번호 확인
		$.ajax({
			 url: "${CTX}/ajax/event/entry/entryNoCheckAjax.do",
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
			 		var frm = document.entryFrom;
					frm.action="${CTX}/event/entry/entryEventSave.do";
					frm.submit();						
				 }else{
					 alert("응모번호가 유효하지 않거나 이미 응모하기가 되었습니다.");
					 return false;
				 } 
			 }
		});
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
	
	// 응모하기 레이어 팝업
	function entryLayer(){
		// 응모하기 종료 여부 체크
		$.ajax({
			 url: "${CTX}/ajax/event/entry/entryDateCheckAjax.do",
			 type: "get",	
			 async: false,
			 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 error: function(request, status, error){ 
			 	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			 },
			 success: function(flag){
				 if(Number(flag) > 0){
					 alert("응모하기 기간이 종료 되었습니다.");
					 return false;
				 }else{
			 		$("#entryNo")	.val("");
					$("#entryNm").val("");
					$("#telNo").val("");
					$("#yyyy").val("");
					$("#mm").val("");
					$("#dd").val("");
					$("#zipCd")	.val("");
					$("#addr").val("");
					$("#oldZipCd").val("");
					$("#oldAddr").val("");
					$("#addrDetail").val("");
					$("input[id=individualInfoYn]").prop("checked",false);			
					$("input:radio[name='purchaseType'][value='']").prop("checked", false);   
				
				 	$('[data-remodal-id=popSubscript]').remodal().open();			// 응모하기 레이어 팝업 오픈			
				 } 
			 }
		});
	}

</script>
</head>
<body>
	<div class="content comm-event evt-event-view">
		<page:applyDecorator  name="mobile.eventmenu" encoding="UTF-8"/>

		<div class="box-search">
			<!-- <div class="row">
				<div class="col col-12">
					<div class="form-control">
						<div class="opt_select">
							<select>
								<option>갸스비X쇼미더머니6 스페셜 이벤트</option>
								<option>갸스비 퍼펙트 홀드 왁스 LIMITED EDITION 발매</option>
								<option>매너남들을 위한 갸스비 오일크리어 필름 증정</option>
							</select>
						</div>
					</div>
				</div>
			</div> -->
		</div>

		<div class="event-view">
			<div class="event-img">
				<img src="${CTX}/images/${DEVICE}/contents/img_event_showme.jpg" alt="이벤트이미지">
				<div class="row">
					<div class="col col-6">
						<a href="javascript:" class="btn full" data-toggle="popup" data-target="" style="background: #d62200;color: #fff;border-color: #d62200;" onclick="entryLayer();"><span class="txt">응모하기</span></a>
					</div>
					<div class="col col-6">
						<a href="https://m.gatsby.co.kr/m/cscenter/notice/noticeList.do" class="btn black full" ><span class="txt">콘서트 당첨 확인</span></a>
					</div>
				</div>
				<img src="${CTX}/images/${DEVICE}/contents/img_event_showme2.jpg" alt="이벤트이미지">
				<div>
					<div class="row">
						<div class="col col-4">
							<a href="#" class="btn full" data-toggle="popup" data-target="#popWinner1"><span class="txt">1차 이벤트<br>당첨자 보기</span></a>
						</div>
						<div class="col col-4">
							<a href="#" class="btn full" data-toggle="popup" data-target="#popWinner2"><span class="txt">2차 이벤트<br>당첨자 보기</span></a>
						</div>
						<div class="col col-4">
							<a href="#" class="btn full" data-toggle="popup" data-target="#popWinner3"><span class="txt">3차 이벤트<br>당첨자 보기</span></a>
						</div>
					</div>
				</div>
				<!-- 쇼미이벤트 응모 -->
				<div id="popSubscript" class="popup type-page">
					<style type="text/css">
					#popSubscript{background-color: #cbcbcb;border-color: #d68900;border-left: none;}
					#popSubscript .pop-top{border: none;}
					#popSubscript .pop-mid{}
					#popSubscript .pop-mid label{display: block;margin-bottom: 5px;}
					#popSubscript .opt_select{width: 100%;background-color: #fff;}
					#popSubscript .radiobox{display: block;}
					#popSubscript .radiobox .lbl{margin: 0;}
					#popSubscript .radiobox .lbl-txt{width: 100%;margin: 0;border-color: #fff;background-color: #fff;}
					#popSubscript .radiobox .radio:checked + .lbl-txt{border-color: #d62200;background-color: #d62200;}
					</style>
		
					<h1 class="hide">GATSBY</h1>
					<div class="pop-top">
						<h2>쇼미더머니 공식 굿즈 응모</h2>
					</div>
			
					<form id="entryFrom" name="entryFrom" enctype="multipart/form-data" method="post" onsubmit="return false">
						<input type="hidden" name="birthDate" id="birthDate">
						<input type="hidden" name="oldZipCd" id="oldZipCd">
						<input type="hidden" name="oldAddr" id="oldAddr">
						
						<div class="pop-mid">
							<div style="margin: 0 -25px 10px;padding: 20px 25px;background-color: #676767;">
								<div class="row">
									<div class="col col-12">
										<div class="form-control">
											<input type="text" class="input" name="entryNo" id="entryNo" placeholder="응모번호">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col col-12">
									<label>신청자명</label>
									<div class="form-control">
										<input type="text" class="input" name="entryNm" id="entryNm" maxlength="10">
									</div>
								</div>
						
								<div class="col col-12">
									<label>전화번호</label>
									<div class="form-control">
										<input type="text" class="input" name="telNo" id="telNo" maxlength="15">
									</div>
								</div>
						
								<div class="col col-12">
									<label>생년월일</label>
									<div class="row">
										<div class="col col-4">
											<div class="opt_select">
												<select id="yyyy">
													<option value="">년</option>
													<c:forEach var="i" begin="0" end="${2010-1970}">
													    <c:set var="yearOption" value="${2010-i}" />
													    <option value="${yearOption}">${yearOption}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="col col-4">
											<div class="opt_select">
												<select id="mm">
													<option value="">월</option>
													<c:forEach var="i" begin="1" end="12" step="1" >
								                        <option value="<fmt:formatNumber value="${i}" minIntegerDigits="2"/>"> ${i}월</option>
								                    </c:forEach>
												</select>
											</div>
										</div>
										<div class="col col-4">
											<div class="opt_select">
												<select id="dd">
													<option value="">일</option>
													<c:forEach var="i" begin="1" end="31" step="1" >
								                        <option value="<fmt:formatNumber value="${i}" minIntegerDigits="2"/>"> ${i}일</option>
								                    </c:forEach>
												</select>
											</div>
										</div>
									</div>
								</div>
						
								<div class="col col-12">
									<label>주소</label>
									<div class="row">
										<div class="col col-8">
											<div class="form-control">
												<input type="text" class="input" name="zipCd" id="zipCd" placeholder="우편번호" readonly="readonly" maxlength="5">
											</div>
										</div>
										<div class="col col-4">
											<button type="button" class="btn full ico-chev" onclick="addrPopup();"><span class="txt">우편번호 검색</span></button>
										</div>
									</div>
									<div class="row">
										<div class="col col-12">
											<div class="form-control">
												<input type="text" class="input" name="addr" id="addr" placeholder="주소" readonly="readonly">
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col col-12">
											<div class="form-control">
												<input type="text" class="input" name="addrDetail" id="addrDetail" placeholder="나머지 주소" maxlength="200">
											</div>
										</div>
									</div>
									<small class="note">※ 경품을 받으실 주소를 정확하게 입력해 주십시오.</small>
								</div>
						
								<div class="col col-12">
									<label>성별</label>
									<div class="row">
										<div class="col col-6" style="padding-right: 0;">
											<span class="radiobox">
												<input type="radio" name="gender" id="gender1" value="M"class="radio" checked>
												<label for="gender1" class="lbl-txt">남자</label>
											</span>
										</div>
										<div class="col col-6" style="padding-left: 0;">
											<span class="radiobox">
												<input type="radio" name="gender" id="gender2" value="F" class="radio">
												<label for="gender2" class="lbl-txt">여자</label>
											</span>
										</div>
									</div>
								</div>
						
								<div class="col col-12">
									<label>평소 왁스를 구입하시는 곳은?</label>
									<div style="padding: 15px 20px;background-color: #fff;">
										<div class="row">
											<div class="col col-4">
												<span class="radiobox">
													<input type="radio"  class="radio" name=purchaseType id="purchaseType1" value="드럭스토어" checked>
													<label for="purchaseType1" class="lbl">드럭스토어</label>
												</span>
											</div>
											<div class="col col-4">
												<span class="radiobox">
													<input type="radio" class="radio" name=purchaseType id="purchaseType2" value="편의점">
													<label for="purchaseType2" class="lbl">편의점</label>
												</span>
											</div>
											<div class="col col-4">
												<span class="radiobox">
													<input type="radio" class="radio" name=purchaseType id="purchaseType3" value="마트">
													<label for="purchaseType3" class="lbl">마트</label>
												</span>
											</div>
										</div>
										<div class="row">
											<div class="col col-4">
												<span class="radiobox">
													<input type="radio" class="radio" name=purchaseType id="purchaseType4" value="온라인">
													<label for="purchaseType4" class="lbl">온라인</label>
												</span>
											</div>
											<div class="col col-4">
												<span class="radiobox">
													<input type="radio" class="radio" name=purchaseType id="purchaseType5" value="기타">
													<label for="purchaseType5" class="lbl">기타</label>
												</span>
											</div>
										</div>
									</div>
								</div>
						
								<div class="col col-12">
									<div style="padding: 15px 20px;background-color: #fff;">
										<span class="checkbox">
											<input type="checkbox" class="check" id="individualInfoYn">
											<label for="individualInfoYn" class="lbl">개인정보 수집 동의</label>
										</span>
										<p style="margin-left: 10px;margin-bottom: 10px;text-indent: -10px;">* 입력하신 정보는 당첨자 경품 발송을 위해서만 사용합니다. 기타 다른 목적으로는 일절 사용되지 않습니다.</p>
										<a href="${CTX}/etc/privacy.do" class="btn black"><span class="txt">개인정보처리방침 보러가기</span></a>
									</div>
								</div>
						
								<div class="col col-12">
									<div class="btn-box confirm">
										<a href="javascript:" class="btn" style="background-color: #5a5a5a;border-color: #5a5a5a;" data-dismiss="popup"><span class="txt" style="color: #fff;">취소</span></a>
										<a href="javascript:" class="btn" style="background-color: #d62200;border-color: #d62200;" onclick="goSave();"><span class="txt" style="color: #fff;">확인</span></a>
									</div>
								</div>
							</div>
						</div>
					</form>
					<a href="javascript:" class="btn_close" data-dismiss="popup"><span class="hide">닫기</span></a>
				</div>
	
				<!-- 쇼미이벤트 당첨자 1차 -->
				<div id="popWinner1" class="popup type-page">
					<style type="text/css">
					#popWinner1{background-color: #cbcbcb;border-color: #d68900;border-left: none;}
					#popWinner1 .pop-top{border: none;}
					#popWinner1 .pop-mid{}
					#popWinner1 .pop-mid label{display: block;margin-bottom: 5px;}
					#popWinner1 .result-box{padding: 15px 20px;max-height: 30vh;background-color: #fff;overflow: hidden;overflow-y: auto;}
					#popWinner1 .result-box ul{overflow: hidden;}
					#popWinner1 .result-box li{width: 50%;float: left;}
					</style>
	
					<h1 class="hide">GATSBY</h1>
					<div class="pop-top">
						<h2>1차 당첨자 발표</h2>
					</div>
	
					<div class="pop-mid">
						<div style="margin: 0 -25px 10px;padding: 20px 25px;background-color: #d72300;">
							<p style="font-size: 14px;color: #fff;text-align: center;">당첨을 축하드립니다~!<br>경품은 응모시 입력해 주신 주소로 발송해 드립니다.</p>
						</div>
	
						<div class="row">
							<div class="col col-12">
								<label>방청권 티켓 (1인 2매)</label>
								<div class="result-box">
									<ul>
										<li>유*호 (3609)</li>
										<li>이*효 (3672)</li>
										<li>최*우 (8241)</li>
										<li>김*현 (4820)</li>
										<li>한*철 (3968)</li>
									</ul>
								</div>
							</div>
						
							<div class="col col-12">
								<label>쇼미더머니 패치 스티커</label>
								<div class="result-box">
									<ul>
										<li>강*일 (7610)</li>
										<li>강*석 (9336)</li>
										<li>곽*수 (5328)</li>
										<li>곽*영 (3753)</li>
										<li>구*민 (9266)</li>
										<li>구*혜 (7005)</li>
										<li>김*용 (6441)</li>
										<li>김*준 (4407)</li>
										<li>김*현 (6750)</li>
										<li>김*규 (6617)</li>
										<li>김*서 (2469)</li>
										<li>김*지 (2059)</li>
										<li>김*호 (1151)</li>
										<li>김*윤 (5297)</li>
										<li>김*기 (0630)</li>
										<li>김*호 (0550)</li>
										<li>김*신 (7918)</li>
										<li>김*호 (2525)</li>
										<li>김*태 (3445)</li>
										<li>김*형 (8536)</li>
										<li>김*희 (0925)</li>
										<li>김*원 (1615)</li>
										<li>김*영 (0991)</li>
										<li>김*성 (5588)</li>
										<li>박*규 (6738)</li>
										<li>박*영 (8323)</li>
										<li>박*우 (0444)</li>
										<li>박*우 (0935)</li>
										<li>배*길 (5655)</li>
										<li>배*석 (3671)</li>
										<li>백*원 (9766)</li>
										<li>빙*희 (0842)</li>
										<li>서*현 (2872)</li>
										<li>서*엽 (8135)</li>
										<li>석*원 (4288)</li>
										<li>설*기 (0926)</li>
										<li>송*제 (3500)</li>
										<li>송*현 (9917)</li>
										<li>송*슬 (6196)</li>
										<li>송*영 (5595)</li>
										<li>양*우 (4093)</li>
										<li>엄*훈 (4955)</li>
										<li>엄*현 (1721)</li>
										<li>오*훈 (0543)</li>
										<li>오*경 (3697)</li>
										<li>오*정 (8001)</li>
										<li>유*구 (4330)</li>
										<li>강*수 (5229)</li>
										<li>윤*국 (1973)</li>
										<li>윤*현 (9103)</li>
										<li>윤*재 (9215)</li>
										<li>이*범 (5288)</li>
										<li>이*주 (9469)</li>
										<li>이*진 (6970)</li>
										<li>이*철 (2070)</li>
										<li>이*산 (7292)</li>
										<li>이*원 (8261)</li>
										<li>이*볕 (6408)</li>
										<li>이*랑 (2540)</li>
										<li>장*선 (0713)</li>
										<li>장*석 (9144)</li>
										<li>전*영 (6478)</li>
										<li>전*진 (8510)</li>
										<li>정*훈 (1759)</li>
										<li>정*우 (0141)</li>
										<li>조*권 (9928)</li>
										<li>채*원 (4977)</li>
										<li>최*현 (1147)</li>
										<li>최*조 (8125)</li>
										<li>최*른 (9320)</li>
										<li>하*인 (3265)</li>
										<li>홍*훈 (5242)</li>
									</ul>
								</div>
							</div>
						
								<div class="col col-12">
									<div class="btn-box confirm">
										<a href="#none" class="btn" data-dismiss="popup" style="background-color: #5a5a5a;border-color: #5a5a5a;"><span class="txt" style="color: #fff;">닫기</span></a>
									</div>
								</div>
						</div>
					</div>
					<a href="#none" class="btn_close" data-dismiss="popup"><span class="hide">닫기</span></a>
				</div>
	
	
				<!-- 쇼미이벤트 당첨자 2차 -->
				<div id="popWinner2" class="popup type-page">
					<style type="text/css">
					#popWinner2{background-color: #cbcbcb;border-color: #d68900;border-left: none;}
					#popWinner2 .pop-top{border: none;}
					#popWinner2 .pop-mid{}
					#popWinner2 .pop-mid label{display: block;margin-bottom: 5px;}
					#popWinner2 .result-box{padding: 15px 20px;max-height: 30vh;background-color: #fff;overflow: hidden;overflow-y: auto;}
					#popWinner2 .result-box ul{overflow: hidden;}
					#popWinner2 .result-box li{width: 50%;float: left;}
					</style>
				
					<h1 class="hide">GATSBY</h1>
					<div class="pop-top">
						<h2>2차 당첨자 발표</h2>
					</div>
				
					<div class="pop-mid">
						<div style="margin: 0 -25px 10px;padding: 20px 25px;background-color: #d72300;">
							<p style="font-size: 14px;color: #fff;text-align: center;">당첨을 축하드립니다~!<br>경품은 응모시 입력해 주신 주소로 발송해 드립니다.</p>
						</div>
				
						<div class="row">
							<div class="col col-12">
								<label>방청권 티켓 (1인 2매)</label>
								<div class="result-box">
									<ul>
										<li>조*우 (9598)</li>
										<li>이*준 (9111)</li>
										<li>이*준 (0685)</li>
										<li>박*태 (5489)</li>
										<li>이*철 (4996)</li>
									</ul>
								</div>
							</div>
						
							<div class="col col-12">
								<label>쇼미더머니 패치 스티커</label>
								<div class="result-box">
									<ul>
										<li>서*석 (7300)</li>
										<li>배*현 (8241)</li>
										<li>*찬 (0708)</li>
										<li>최*희 (0382)</li>
										<li>김*재 (8198)</li>
										<li>유*나 (1002)</li>
										<li>최*욱 (4503)</li>
										<li>정*욱 (7756)</li>
										<li>김*철 (3760)</li>
										<li>정*리 (9157)</li>
										<li>최*영 (3124)</li>
										<li>김*희 (4061)</li>
										<li>김*지 (9395)</li>
										<li>유*진 (1484)</li>
										<li>이*경 (0699)</li>
										<li>최*호 (6653)</li>
										<li>이*진 (0954)</li>
										<li>이*진 (0954)</li>
										<li>김*린 (3589)</li>
										<li>김*지 (5386)</li>
										<li>송*녕 (0125)</li>
										<li>김*환 (1538)</li>
										<li>조*솔 (8498)</li>
										<li>김*혜 (5149)</li>
										<li>채*국 (7876)</li>
										<li>김*영 (6144)</li>
										<li>김*환 (1133)</li>
										<li>임*식 (7441)</li>
										<li>김*환 (1531)</li>
										<li>빙*진 (8913)</li>
										<li>손*우 (4342)</li>
										<li>류*윤 (7206)</li>
										<li>*군 (0669)</li>
										<li>성*윤 (4018)</li>
										<li>신*섭 (4945)</li>
										<li>함*웅 (0080)</li>
										<li>장*름 (8684)</li>
										<li>이*형 (1309)</li>
										<li>배*준 (1082)</li>
										<li>*솔 (1990)</li>
										<li>이*원 (0268)</li>
										<li>조*현 (9521)</li>
										<li>권*진 (3101)</li>
										<li>이*화 (8043)</li>
										<li>전*근 (1886)</li>
										<li>한*민 (5985)</li>
										<li>류*기 (4832)</li>
										<li>윤*석 (8959)</li>
										<li>김*겸 (0918)</li>
										<li>김*중 (9272)</li>
										<li>이*우 (9923)</li>
										<li>윤*정 (3943)</li>
										<li>박*성 (5853)</li>
										<li>최*열 (5391)</li>
										<li>이*희 (9859)</li>
										<li>구*모 (3256)</li>
										<li>이*주 (4315)</li>
										<li>이*현 (0495)</li>
										<li>이*현 (7890)</li>
										<li>송*호 (9207)</li>
										<li>최*은 (8801)</li>
										<li>유*원 (7083)</li>
										<li>이*원 (3483)</li>
										<li>박*배 (7635)</li>
										<li>김*관 (8886)</li>
										<li>장*언 (7727)</li>
										<li>김*준 (3077)</li>
										<li>황*범 (4868)</li>
										<li>백*현 (4783)</li>
										<li>홍*재 (1841)</li>
										<li>서*용 (0327)</li>
										<li>*선 (1126)</li>
										<li>김*석 (2555)</li>
										<li>신*규 (1934)</li>
										<li>이*건 (9101)</li>
										<li>최*규 (7148)</li>
										<li>김*원 (2556)</li>
										<li>박*철 (0474)</li>
										<li>김*민 (4706)</li>
										<li>김*경 (0226)</li>
										<li>이*원 (0221)</li>
										<li>신*섭 (6735)</li>
										<li>*준 (2506)</li>
										<li>은*곤 (8456)</li>
										<li>박*익 (4494)</li>
										<li>이*석 (1409)</li>
										<li>김*헌 (2590)</li>
										<li>최*원 (0905)</li>
										<li>조*규 (8545)</li>
										<li>장*진 (2085)</li>
										<li>엄*훈 (4955)</li>
										<li>정*화 (7202)</li>
										<li>류*선 (9899)</li>
										<li>최*필 (1136)</li>
										<li>박*태 (5489)</li>
										<li>최*락 (1899)</li>
										<li>허*웅 (9395)</li>
										<li>이*용 (1960)</li>
										<li>김*국 (4191)</li>
										<li>황*환 (2720)</li>
										<li>김*삼 (7213)</li>
										<li>이*민 (0211)</li>
										<li>김*호 (0738)</li>
										<li>서*재 (1256)</li>
										<li>배*현 (1406)</li>
										<li>백*민 (0535)</li>
										<li>조*현 (4096)</li>
										<li>김*성 (9434)</li>
										<li>김*진 (8509)</li>
										<li>주*우 (7911)</li>
										<li>박*우 (2302)</li>
										<li>이*성 (7373)</li>
										<li>이*석 (6370)</li>
										<li>이*호 (5664)</li>
										<li>윤*현 (9549)</li>
										<li>김*식 (6192)</li>
										<li>이*지 (5739)</li>
										<li>박*철 (5373)</li>
										<li>민*선 (1652)</li>
										<li>이*헌 (0317)</li>
									</ul>
								</div>
							</div>
						
							<div class="col col-12">
								<div class="btn-box confirm">
									<a href="#none" class="btn" data-dismiss="popup" style="background-color: #5a5a5a;border-color: #5a5a5a;"><span class="txt" style="color: #fff;">닫기</span></a>
								</div>
							</div>
						</div>
					</div>
					<a href="#none" class="btn_close" data-dismiss="popup"><span class="hide">닫기</span></a>
				</div>
				
				<!-- 쇼미이벤트 당첨자 3차 -->
				<div id="popWinner3" class="popup type-page">
					<style type="text/css">
					#popWinner3{background-color: #cbcbcb;border-color: #d68900;border-left: none;}
					#popWinner3 .pop-top{border: none;}
					#popWinner3 .pop-mid{}
					#popWinner3 .pop-mid label{display: block;margin-bottom: 5px;}
					#popWinner3 .result-box{padding: 15px 20px;max-height: 30vh;background-color: #fff;overflow: hidden;overflow-y: auto;}
					#popWinner3 .result-box ul{overflow: hidden;}
					#popWinner3 .result-box li{width: 50%;float: left;}
					</style>
				
					<h1 class="hide">GATSBY</h1>
					<div class="pop-top">
						<h2>3차 당첨자 발표</h2>
					</div>
				
					<div class="pop-mid">
						<div style="margin: 0 -25px 10px;padding: 20px 25px;background-color: #d72300;">
							<p style="font-size: 14px;color: #fff;text-align: center;">당첨을 축하드립니다~!<br>경품은 응모시 입력해 주신 주소로 발송해 드립니다.</p>
						</div>
				
						<div class="row">
							<div class="col col-12">
								<label>방청권 티켓 (1인 2매)</label>
								<div class="result-box">
									<ul>
										<li>최*몽 (4898)</li>
										<li>윤*태 (4434)</li>
										<li>오*록 (2605)</li>
										<li>양*두 (3766)</li>
										<li>조*진 (9770)</li>
									</ul>
								</div>
							</div>
						
							<div class="col col-12">
								<label>쇼미더머니 패치 스티커</label>
								<div class="result-box">
									<ul>
										<li>이*민 (2540)</li>
										<li>최*봉 (0197)</li>
										<li>이*미 (4328)</li>
										<li>이*화 (4337)</li>
										<li>김*원 (8296)</li>
										<li>김*효 (1757)</li>
										<li>윤*창 (7578)</li>
										<li>유*롬 (6691)</li>
										<li>이*정 (0791)</li>
										<li>김*우 (9290)</li>
										<li>김*식 (4216)</li>
										<li>황*우 (0714)</li>
										<li>문*원 (8804)</li>
										<li>배*철 (5529)</li>
										<li>이*은 (3685)</li>
										<li>봉*준 (9054)</li>
										<li>정*명 (9774)</li>
										<li>방*은 (0926)</li>
										<li>강*천 (2907)</li>
										<li>양*성 (6644)</li>
										<li>양*석 (7123)</li>
										<li>황* (3451)</li>
										<li>차*정 (2748)</li>
										<li>이*언 (1116)</li>
										<li>권*환 (2286)</li>
										<li>김*현 (9867)</li>
										<li>양*상 (8866)</li>
										<li>이*재 (4726)</li>
										<li>류*형 (8597)</li>
										<li>최*욱 (2158)</li>
										<li>김*홍 (8284)</li>
										<li>안*민 (4747)</li>
										<li>유*균 (7313)</li>
										<li>김*수 (0384)</li>
										<li>유*규 (8115)</li>
										<li>김*태 (5696)</li>
										<li>김*준 (7378)</li>
										<li>윤*환 (3024)</li>
										<li>박*우 (2959)</li>
										<li>손*식 (5872)</li>
										<li>남*연 (4989)</li>
										<li>최*열 (8168)</li>
										<li>박*아 (1988)</li>
										<li>강*원 (2749)</li>
										<li>조*상 (9789)</li>
										<li>하*인 (3265)</li>
										<li>이*주 (1706)</li>
										<li>박*영 (8624)</li>
										<li>이*무 (0063)</li>
										<li>박*민 (3408)</li>
										<li>권*민 (5376)</li>
										<li>나*민 (0421)</li>
										<li>김*준 (8942)</li>
										<li>오*준 (1228)</li>
										<li>이*윤 (0730)</li>
										<li>한*진 (5277)</li>
										<li>박*홍 (2654)</li>
										<li>임*성 (3077)</li>
										<li>이*호 (9365)</li>
										<li>윤*욱 (1119)</li>
										<li>장*해 (8280)</li>
										<li>박*우 (2862)</li>
										<li>이*화 (2846)</li>
										<li>오*훈 (5424)</li>
										<li>양*영 (9305)</li>
										<li>최*민 (5900)</li>
										<li>방*환 (2242)</li>
										<li>민*찬 (1214)</li>
										<li>송*주 (4544)</li>
										<li>황*주 (1011)</li>
										<li>이*연 (9137)</li>
										<li>최*철 (3828)</li>
										<li>오*준 (2190)</li>
										<li>김*택 (7435)</li>
										<li>이*운 (5186)</li>
										<li>허*석 (9649)</li>
										<li>이*채 (7982)</li>
										<li>이*형 (1676)</li>
										<li>김*석 (3783)</li>
										<li>정*경 (7991)</li>
										<li>강*섭 (7590)</li>
										<li>손*훈 (6521)</li>
										<li>박*희 (2802)</li>
										<li>김*찬 (5975)</li>
										<li>장*영 (0830)</li>
										<li>이*규 (9195)</li>
										<li>정*원 (0418)</li>
										<li>뿌* (1552)</li>
										<li>손*근 (4123)</li>
										<li>김*현 (9867)</li>
										<li>정*환 (9429)</li>
										<li>박*근 (8476)</li>
										<li>추*석 (7557)</li>
										<li>지*배 (3111)</li>
										<li>박*덕 (4254)</li>
										<li>배*성 (9498)</li>
										<li>최*원 (0667)</li>
										<li>이*금 (0114)</li>
										<li>박*우 (0598)</li>
										<li>유*훈 (0742)</li>
									</ul>
								</div>
							</div>
							<div class="col col-12">
								<div class="btn-box confirm">
									<a href="#none" class="btn" data-dismiss="popup" style="background-color: #5a5a5a;border-color: #5a5a5a;"><span class="txt" style="color: #fff;">닫기</span></a>
								</div>
							</div>
						</div>
					</div>
					<a href="#none" class="btn_close" data-dismiss="popup"><span class="hide">닫기</span></a>
				</div>
				<div class="comment-control">
					<div class="comment-btn">
						<a href="${CTX}/event/event/eventList.do" class="btn"><span class="txt">목록</span></a>
					</div>
				</div>
			</div>
	</div>
</body>
</html>