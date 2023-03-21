<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_member" />
<meta name="menu_no" content="mypage_120" />
<script>

	// 탈퇴
	function withdraw(){
		var submitFlag = "Y";
		var orderCnt = "${memberInfo.orderCnt}";
		var checkCnt = $('input:checkbox[name="reason"]:checked').length;

		if(orderCnt > 0){
			alert("진행중인 주문/교환/반품이 있어서 탈퇴가 어렵습니다.\n자세한 내용을 확인하시려면 고객센터로 연락주세요.");
			submitFlag = "N";
			return false;
		} 
		
		if(checkCnt == 0){
			alert("탈퇴 사유를 선택해 주세요.");
		 	submitFlag = "N";
   	  	 	return false;
		}
		
		$('input:checkbox[name="reason"]').each(function() {
      		if(this.checked){
    	  		if(this.value == 'WITHDRAW_REASON_ETC'){
    		  		if($.trim($("#etcReason").val()) == ""){
		  				alert("기타 사유를 입력해 주세요.");
	    				$("#etcReason").focus();
	    				submitFlag = "N";
	    				return false;
    				}
	    	 	 }
		    	  submitFlag = "Y";
		      }
		 });
		
		if(submitFlag == "Y"){
 			var params = $("form[name=withdrawForm]").serialize() ;
			
			$.ajax({
				 url: "${CTX}/ajax/mypage/member/memberWithdrawSaveAjax.do",
				 type: "post",	
				 data:params,
				 async: false,
				 cache: false,
				 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
				 error: function(request, status, error){ 
				 	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
				 },
				 success: function(flag){
					 if(Number(flag) > 0){
						 alert("회원 탈퇴가 정상적으로 되었습니다.\n이용해 주셔서 감사합니다.");
						 location.href = "${CTX}/main.do";
					 }
				 }
			}); 
		}
	}
	
	// 기타 선택시 체크
	function etcReasonCheck(){
		var etcReasonChk = $('input:checkbox[id="etcReasonChk"]').is(":checked");
		
		if(etcReasonChk == true){
			$("#etcReason").removeAttr("readonly");
		}else{
			$("#etcReason").attr("readonly","readonly").val("");
		}
	}

</script>
</head>
<body>
	<form name="withdrawForm" method="post" onsubmit="return false;">
		<div class="content comm-auth comm-mypage mypage-withdraw">
    	
            <div class="page-body">
                <div class="section-intro">
                	<div class="intro-title">
	                	<h3 class="tit">사이트를 이용하는데<br/> 불편하신 점이 있으셨나요?</h3>
	                	<p>
		                	불편하셨던 점이나 불만사항을 알려주시면 적극 반영하여
							보다 나은 면역공방가 될 수 있도록 노력하겠습니다. <br/>
							회원 탈퇴 전 아래 사항을 확인해 주세요.
						</p>
	                </div>
	                
	                <ul class="bu-list">
	                	<li><span class="bu">·</span> <strong class="em">진행 주문건이 있을 경우 탈퇴 처리가 불가능합니다.</strong> <br/> (구매 확정 완료 이후 탈퇴를 진행해주시기 바랍니다.)</li>
						<li><span class="bu">·</span> 회원정보는 개인정보 보호방침ㆍ취급방침에 따라 안전하게 삭제됩니다.</li>
						<li><span class="bu">·</span> 회원님의 동의 없이는 기재하신 회원정보가 공개되지 않습니다.</li>
						<li><span class="bu">·</span> <strong class="em">한번 탈퇴한 아이디는 재 사용이 불가능합니다.</strong></li>
						<li><span class="bu">·</span> <strong class="em">탈퇴 시 보유하신 포인트와 쿠폰은 모두 삭제 됩니다.</strong></li>
	                </ul>
                </div>
                
                <div class="section section-status first">
                	<div class="section-title">
                		<h3 class="tit">${memberInfo.nowDt}</h3>
                	</div>
                	<ul class="member-status">
	                	<li>
	                		<strong class="tit">보유 포인트</strong> 
	                		<span class="val"><span class="em"><fmt:formatNumber value="${memberInfo.pointPrice}"/></span>P</span>
	                	</li>
	                	<li>
	                		<strong class="tit">진행중인 주문 건</strong>
	                		<span class="val"><span class="em"><fmt:formatNumber value="${memberInfo.orderCnt}"/></span>건</span>
	                	</li>
	                </ul>
                </div>
                
                <div class="section section-withdraw">
                	<div class="section-title">
                		<h3 class="tit">회원 탈퇴 사유</h3>
                	</div>
                	
                	<div class="withdraw-form">
	                	<c:forEach var="list" items="${withdrawList}" varStatus="idx">
	                		<c:choose>
								<c:when test="${list.commonCd ne 'WITHDRAW_REASON_ETC' }">
									<div class="row">
										<div class="col col-12">
											<span class="checkbox">
				                                <input type="checkbox" name="reason" id="chk${idx.index}" class="check" value="${list.commonCd}" />
				                                <label for="chk${idx.index}" class="lbl">${list.cdNm}</label>
				                            </span>
										</div>                
				                	</div>
								</c:when>
								<c:otherwise>
									<div class="row">
										<div class="col col-12">
											<span class="checkbox">
				                                <input type="checkbox" name="reason" id="etcReasonChk" class="check" value="${list.commonCd}" onclick="etcReasonCheck();" />
				                                <label for="etcReasonChk" class="lbl">${list.cdNm}</label>
				                            </span>
										</div>                
				                	</div>
				                	<div class="row">
										<div class="col col-12">
											<div class="form-control">
												<input type="text" name="etcReason" id="etcReason" class="input" placeholder="기타 사유를 입력해주세요." />
											</div>
										</div>                
				                	</div>
								</c:otherwise>
							</c:choose>
	                	</c:forEach>
                	</div>
                </div>
                <div class="btn-box confirm">
					<a href="${CTX}/mypage/order/main.do" class="btn"><span class="txt">취소</span></a>
					<a href="javascript:" class="btn black" onclick="withdraw();"><span class="txt">탈퇴</span></a>
				</div>
            </div>
        </div>	
	</form>
</body>
</html>