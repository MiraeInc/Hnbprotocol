<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main"/>	
<script>

	// 저장
	function goSave(){
		
		var predictFlag=0;
		
		// 토종비결 종료일자 체크
		$.ajax({
			 url: "${CTX}/ajax/event/entry/predictDateCheckAjax.do",
			 type: "post",	
			 async: false,
			 cache: false,
			 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 error: function(request, status, error){ 
			 	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			 },
			 success: function(flag){
				 if(Number(flag) > 0){
					 alert("토종비결 서비스가 종료 되었습니다.");
					 predictFlag =1;
					 return false;
				 }
			 }
		});
		
		if(predictFlag == 0){
			if("${IS_LOGIN}" == "false"){
				if(confirm("<spring:message code='common.util011'/>") == true){
					location.href="${CTX}/login/loginPage.do?refererYn=Y"; 
				}
			}else{
				// if($("input[name=pgender]:checked").length <1){
				//	alert("성별을 선택해 주세요.");
				//	return false;
				// }
				
				if($("input[name=pleap]:checked").length <1){
					alert("양력/음력을 선택해 주세요."); 
					return false;
				}
				
				if($.trim($("#pyear").val()) == "" || $.trim($("#pmonth").val()) == "" || $.trim($("#pday").val()) == ""){
					alert("생년월일을 선택해 주세요.");
					return false;
				}
				
				if($("#phour").val() == ""){
					alert("테어난시를 선택해 주세요.");
					return false;
				}
				
				if($("#pminute").val() == ""){
					alert("태어난분을 선택해 주세요.");
					return false;
				}
				
				$.ajax({
					 url: "${CTX}/ajax/event/entry/predictEventSaveAjax.do",
					 data: $("#predictFrom").serialize(),
					 type: "post",	
					 async: false,
					 cache: false,
					 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
					 error: function(request, status, error){ 
					 	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
					 },
					 success: function(flag){
						 if(Number(flag) > 0){
					 		var frm = document.predictFrom;
							frm.action="http://unse.fortune82.com/cp/gatsby/2018/mwtojung.asp";
							frm.target = "_blank";
							frm.submit();						
						 }
					 }
				});
			}
		}
	}
	
	// 회원가입 페이지
	function join(){
		if("${IS_LOGIN}" == "false"){
			location.href = "${CTX}/login/loginPage.do?refererYn=Y";			
		}else{
			alert("이미 로그인 되었습니다.");
			return false;
		}
	}

</script>
</head>
<body>
	<div class="content comm-event evt-event-view">
		<page:applyDecorator  name="mobile.eventmenu" encoding="UTF-8"/>
		
		<div class="event-view">
			
			<style type="text/css">
				/* 기본폼 변경 */
				.opt_select {position: relative; display: inline-block; height: 24px;  border: 1px solid #ddd; z-index: 0;}
				.opt_select:after {top: 9px; right: 7px; width: 7px; height: 4px;}
				.opt_select select {padding-left: 6px; padding-right: 20px;}
				.form-control.sym-txt {padding-right:15px}
				.form-control.sym-txt:after {position:absolute; right:0; top:0; min-width:15px; text-align: right; line-height: 24px; font-size: 12px; content: attr(data-format)}
				.radiobox .lbl {font-size: 12px;}
				.radiobox .lbl:before {background-image: url('../../images/m/predictEvent/ico_radiobox.png');}
				
				.event-view {max-width: 690px;}
				.event-view img {max-width: 100%;}
	
	
				.event-body  {position: relative; padding-bottom: 30px; background: url('../../images/m/predictEvent/event_form_pattern.jpg') 0 0 repeat;}
				.event-body .btn-link {display: block; margin: 0 20px;}
				.event-body .body-form {position: relative; background-color: #fff;}
				.event-body .body-form h2 {padding-top: 25px; text-align: center; font-size: 15px; font-weight: 600;}
				.event-body .body-form h2 span {color: #1b3557;}
				
				.event-body .body-form.form1 {margin: 0 20px; height: 128px;}
				.event-body .body-form.form1 a {position: absolute; left:50%; bottom: 25px; width: 210px; height: 39px; margin-left:-105px;}
				
				.event-body .body-form.form2 {margin: 10px 20px 0; padding-bottom: 25px;}
				.event-body .body-form.form2 dl {margin: 20px 20px 0;}
				.event-body .body-form.form2 dt {margin-bottom: 10px; font-size: 12px; font-weight: 600;}
				.event-body .body-form.form2 dd {margin-bottom: 18px;}
				.event-body .body-form.form2 dd:last-child {margin: 0}
	
				.event-body .body-form.form2 .radiobox {margin-left: 10px;}
				.event-body .body-form.form2 .radiobox:first-child {margin-left: 0;}
	
				.event-body .body-policy {margin:20px; text-align: center;}
			</style>
			
			<form id="predictFrom" name="predictFrom" method="post">
				<input type="hidden" name="cp" value="gatsby" />
			
				<div class="event-top">
					<img src="${CTX}/images/${DEVICE}/predictEvent/event_top.jpg" alt="" />
				</div>
		
				<div class="event-body">
		
					<div class="body-top">
						<img src="${CTX}/images/${DEVICE}/predictEvent/event_body_top.png" alt="갸스비 회원이면 누구나! 무료 신년운세!" />
					</div>
		
					<div class="body-form form1">
						<h2><span>01</span> 로그인 / 회원가입</h2>
						<a href="javascript:" onclick="join();"><img src="${CTX}/images/${DEVICE}/predictEvent/btn_join.jpg" alt="로그인 / 회원가입 하러가기" /></a>
					</div>
		
					<div class="body-form form2">
						<h2><span>02</span> 생년월일 입력</h2>
						<dl>
							<dt>성별</dt>
							<dd>
								<span class="radiobox">
									<input type="radio" name="pgender" id="optGender1" class="radio" value="1" />
									<label for="optGender1" class="lbl">남자</label>
								</span>
								<span class="radiobox">
									<input type="radio" name="pgender" id="optGender2" class="radio" value="2"/>
									<label for="optGender2" class="lbl">여자</label>
								</span>
							</dd>
							<dt>양력 / 음력</dt>
							<dd>
								<span class="radiobox">
									<input type="radio" name="pleap" id="optMonthType1" class="radio" value="1"/>
									<label for="optMonthType1" class="lbl">양력</label>
								</span>
								<span class="radiobox">
									<input type="radio" name="pleap" id="optMonthType2" class="radio" value="2"/>
									<label for="optMonthType2" class="lbl">음력</label>
								</span>
								<span class="radiobox">
									<input type="radio" name="pleap" id="optMonthType3" class="radio" value="3"/>
									<label for="optMonthType3" class="lbl">음력(윤달)</label>
								</span>
							</dd>
							<dt>생년월일</dt>
							<dd>
								<div class="row">
									<div class="col col-4">
										<div class="form-control sym-txt" data-format="년">
											<div class="opt_select">
												<select name="pyear"  id="pyear">
													<option value="">선택</option>
													<c:forEach var="i" begin="0" end="${2018-1940}">
												    	<c:set var="yearOption" value="${2018-i}" />
												    	<option value="${yearOption}">${yearOption}</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
									<div class="col col-4">
										<div class="form-control sym-txt" data-format="월">
											<div class="opt_select">
												<select name="pmonth" id="pmonth">
													<option value="">선택</option>
													<c:forEach var="i" begin="1" end="12" step="1" >
								                        <option value="<fmt:formatNumber value="${i}"/>"> ${i}</option>
								                    </c:forEach>
												</select>
											</div>
										</div>
									</div>
									<div class="col col-4">
										<div class="form-control sym-txt" data-format="일">
											<div class="opt_select">
												<select name="pday" id="pday">
													<option value="">선택</option>
													<c:forEach var="i" begin="1" end="31" step="1" >
							                        	<option value="<fmt:formatNumber value="${i}"/>"> ${i}</option>
								                    </c:forEach>
												</select>
											</div>
										</div>
									</div>
								</div>
							</dd>
							<dt>태어난 시</dt>
							<dd>
								<div class="row">
									<div class="col col-4">
										<div class="form-control sym-txt" data-format="시">
											<div class="opt_select">
												<select name="phour" id="phour">
													<option value="">선택</option>
													<c:forEach var="i" begin="0" end="23" step="1" >
								                        <option value="<fmt:formatNumber value="${i}"/>"> ${i}</option>
								                    </c:forEach>
								                    <option value="<fmt:formatNumber value="24"/>">모름</option>
												</select>
											</div>
										</div>
									</div>
									<div class="col col-4">
										<div class="form-control sym-txt" data-format="분">
											<div class="opt_select">
												<select name="pminute" id="pminute">
													<option value="">선택</option>
													<c:forEach var="i" begin="0" end="59" step="1" >
							                        	<option value="<fmt:formatNumber value="${i}"/>"> ${i}</option>
								                    </c:forEach>
						                    	 	<option value="<fmt:formatNumber value="60"/>">모름</option>
												</select>
											</div>
										</div>
									</div>
								</div>
							</dd>
						</dl>
					</div>
		
					<div class="body-policy">
						<img src="${CTX}/images/${DEVICE}/predictEvent/event_policy.png" alt="" />
						<p class="hide">
							입력하신 운세정보(성별, 양력/음력, 생년월일, 태어난시)는 
							운세결과 제공 용도로만 이용되며,
							다른 목적을 위해 활용 또는 저장되지 않습니다.
						</p>
					</div>
		
					<a href="javascript:" class="btn-link" onclick="goSave();"><img src="${CTX}/images/${DEVICE}/predictEvent/btn_link.jpg" alt="" /></a>
				</div>
		
				<div class="event-footer">
					<img src="${CTX}/images/${DEVICE}/predictEvent/event_footer.jpg" alt="" />
				</div>
			</form>
		</div>
	
		<div class="comment-control">
			<div class="comment-btn">
				<a href="${CTX}/event/event/eventList.do" class="btn"><span class="txt">목록</span></a>
			</div>
		</div>
	</div>
</body>
</html>