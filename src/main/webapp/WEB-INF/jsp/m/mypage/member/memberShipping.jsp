<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_member" />
<meta name="menu_no" content="mypage_100" />
<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<script>

	// 배송지 등록 레이어
	function shippingLayer(flag, idx){
		var shippingCnt = "${fn:length(list)}";
		
		if(shippingCnt == 50){
			alert("배송지는 최대50개까지 등록이 가능합니다.");
			return false;
		}
		
		if(flag == "I"){
			// 초기값 삭제
			$("#shippingNm").val("");
			$("#receiverNm").val("");
			$("#phoneNo").val("");
			$("#telNo").val("");
			$("#zipCd").val("");
			$("#addr").val("");
			$("#addrDetail").val("");
		}
		
		$.ajax({
			 url: "${CTX}/ajax/mypage/member/memberShippingLayerAjax.do",
			 data : {
				 			"addressIdx"	:	idx,
				 			"statusFlag"	:	flag
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
				 createNumeric("#phoneNo, #telNo");
			 }
		}); 
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
					 location.reload();
				 }
			 }
		});
	}
	
	//기본배송지 설정
	function defaultShipping(addressIdx){
		$.ajax({
			 url: "${CTX}/ajax/mypage/member/shippingDefalutUpdateAjax.do",
			 data : {
		 					"addressIdx"	:	addressIdx
				 		},
			 type: "post",	
			 async: false,
			 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 error: function(request, status, error){ 
			 	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			 },
			 success: function(flag){
				 if(Number(flag) > 0){
					 location.reload();
				 }
			 }
		});
	}
	
	// 배송지 삭제
	function shippingDelete(addressIdx){
		if(confirm("배송지 목록을 삭제하시겠습니까?") == true){
			$.ajax({
				 url: "${CTX}/ajax/mypage/member/memberShippingSaveAjax.do",
				 data : {
					 			"addressIdx"			:	addressIdx,
					 			"statusFlag"			:	"D"
					 		},
				 type: "post",	
				 async: false,
				 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
				 error: function(request, status, error){ 
				 	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
				 },
				 success: function(flag){
					 if(Number(flag) > 0){
						 location.reload();
					 }
				 }
			});
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
	<div class="content comm-order comm-mypage mypage-shipping">

    	<div class="page-body">
   			<ul class="bu-list type-extra" style="margin-bottom: 15px">
   				<li><span class="bu">※</span> 배송지는 최대 50개까지 등록이 가능합니다.</li>
	   		</ul>
       		<button type="button" class="btn full outline-green ico-chev" data-toggle="popup" data-target="#popDelivery" onclick="shippingLayer('I','0');"><span class="txt">배송지 등록</span></button>
	         
         	<div class="shipping-list">
         		<ul>
         			<c:forEach var="list" items="${list}">
	         			<li>
	         				<div class="item">
		         				<p class="item-name">[${list.receiverNm}]
		         					<c:if test="${list.defaultYn eq 'Y'}">
				         				<span class="em">[기본 배송지]</span>
		         					</c:if>
		         				</p>	
		         				<p class="item-addr">
		         					${list.phoneNo}<br/>${list.addr}
		         				</p>
		         			</div>
		         			<div class="btn-box">
		         				<div class="col col-3">
		         					<button type="button" class="btn full outline-green" data-toggle="popup" data-target="#popDelivery" onclick="shippingLayer('U','${list.addressIdx}');"><span class="txt">수정</span></button>
		         				</div>
		         				<div class="col col-3">
		         					<button type="button" class="btn full" onclick="shippingDelete('${list.addressIdx}');"><span class="txt">삭제</span></button>
		         				</div>
		         			<c:if test="${list.defaultYn ne 'Y'}">
		         				<div class="col col-6">
		         					<button type="button" class="btn full black" onclick="defaultShipping('${list.addressIdx}');"><span class="txt">기본 배송지로 설정</span></button>
		         				</div>
		         			</c:if>
	        				</div>
		         		</li>
         			</c:forEach>
       			</ul>
         	</div>
	     </div>
	 </div>	
	
	<!--  배송지 등록/수정 -->
	<div id="popDelivery" class="popup type-page popup-cashreceipt">
		<%-- AJAX --%>
	</div>
</body>
</html>