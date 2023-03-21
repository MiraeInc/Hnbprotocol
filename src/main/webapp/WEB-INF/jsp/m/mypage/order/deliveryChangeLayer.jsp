<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<script>
$(document).ready(function(){
	<%-- 숫자만 입력 --%>
	createNumeric("#receiverPhoneNo,#receiverTelNo,#receiverZipCd");
	
	
});
<%-- 우편번호 검색 --%>
function addrPopup(){
	execDaumPostcode(callback);
}

<%-- CALLBACK 주소정보 값 --%> 
var callback = function(zipCd, oldZipCd, addr, oldAddr){
	$("#deliveryForm input[name='receiverZipCd']").val(zipCd);
	$("#deliveryForm input[name='receiverAddr']").val(addr);
	$("#deliveryForm input[name='receiverOldZipCd']").val(oldZipCd);
	$("#deliveryForm input[name='receiverOldAddr']").val(oldAddr);

	$("#deliveryForm input[name='receiverAddrDetail']").focus();
}

function confirmDeliveryForm() {

		<%-- 받는이 성명 --%>
		if($.trim($("#deliveryForm input:text[name='receiverNm']").val()) == ""){
			alert("받으시는 분 성명을 입력해 주세요.");
			$("#deliveryForm input:text[name='receiverNm']").focus();
			return false;
		}

		if($.trim($("#deliveryForm input[name='receiverPhoneNo']").val()) == ""){
			alert("받으시는 분 휴대폰 번호를 입력해 주세요.");
			$("#deliveryForm input[name='receiverPhoneNo']").focus();
			return false;
		}

		// 휴대폰 유효성 체크
		if(phoneCheck($.trim($("#deliveryForm input[name='receiverPhoneNo']").val())) == false){
			alert("잘못된 휴대폰 번호입니다.");
			$("#deliveryForm input[name='receiverPhoneNo']").focus();
			return false;
		}
		
		if($.trim($("#deliveryForm input[name='receiverZipCd']").val()) == ""){
			alert("받으시는 분 우편번호를 입력해 주세요.");
			$("#deliveryForm input[name='receiverZipCd']").focus();
			return false;
		}

		if($.trim($("#deliveryForm input:text[name='receiverAddr']").val()) == ""){
			alert("받으시는 분 주소를 입력해 주세요.");
			$("#deliveryForm input:text[name='receiverAddr']").focus();
			return false;
		}


		if($.trim($("#deliveryForm input:text[name='receiverAddr']").val()) == ""){
			alert("받으시는 분 주소를 입력해 주세요.");
			$("#deliveryForm input:text[name='receiverAddr']").focus();
			return false;
		}

		if($.trim($("#deliveryForm input:text[name='receiverAddrDetail']").val()) == ""){
			alert("받으시는 분 상세주소를 입력해 주세요.");
			$("#deliveryForm input:text[name='receiverAddrDetail']").focus();
			return false;
		}

		<c:if test="${!isnomember}">
		//배송지 추가 선택시
		if($("#deliveryForm  input:checkbox[name='addToAddress']:checked").length > 0){
			if($.trim($("#deliveryForm input:text[name='shippingNm']").val()) == ""){
				alert("배송지명을 입력해 주세요.");
				$("#deliveryForm input:text[name='shippingNm']").focus();
				return false;
			}
		}
		</c:if>
		
		if (confirm('배송지를 변경하시겠습니까?'))
 		{
			$.ajax({			
				url: getContextPath()+"/ajax/order/orderDeliveryChange.do",
				data: $("#deliveryForm").serialize() ,
			 	type: "post",
			 	async: false,
			 	cache: false,
			 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 	error: function(request, status, error){ 
					 dclick = false;
			 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
				},
				success: function(data){
					if(data.result == true){
						$('[data-remodal-id=pop_reload_alert] div.pop-mid p').html("배송지를 변경 하였습니다.");
						$('[data-remodal-id=pop_reload_alert]').remodal().open();
					}else{
						if(data.msg != "" && data.msg !=null){
							$('[data-remodal-id=pop_alert] div.pop-mid p').html(data.msg);
							$('[data-remodal-id=pop_alert]').remodal().open();
						}
					}
				 }
			});
		}
		
   }
   
function myAddrSelectChange() {
	var s = $("#deliveryForm #addressTabId option:selected");
	$("#deliveryForm input[name='receiverZipCd']").val($(s).data().zipcd);
	$("#deliveryForm input[name='shippingNm']").val($(s).data().shippingnm);
	$("#deliveryForm input:text[name='receiverAddr']").val($(s).data().addr);
	$("#deliveryForm input:hidden[name='receiverOldAddr']").val($(s).data().oldaddr);
	$("#deliveryForm input:hidden[name='receiverOldZipCd']").val($(s).data().oldzipcd);
	$("#deliveryForm input[name='receiverAddrDetail']").val($(s).data().addrdetail);
	$("#deliveryForm input[name='receiverPhoneNo']").val($(s).data().phoneno);
	$("#deliveryForm input[name='receiverTelNo']").val($(s).data().telno);
}
</script>

	<h1 class="hide">GATSBY</h1>
	<!-- pop-top -->
	<div class="pop-top">
		<h2>배송지 수정</h2>
	</div>
	<!-- //pop-top -->
	<!-- pop-mid -->
	<form name="deliveryForm" id="deliveryForm" method="post" >
	<input type="hidden" name="orderCd" id="orderCd" value="${orderInfo.orderCd}"/>
	<input type="hidden" name="receiverOldAddr" id="receiverOldAddr" value="${orderInfo.receiverOldAddr}"/>
	<input type="hidden" name="receiverOldZipCd" id="receiverOldZipCd" value="${orderInfo.receiverOldZipCd}"/>
	
	<div class="pop-mid">
	
		<div class="order-form">
			<div class="form-group">
				<div class="form-body">
				
					<c:if test="${!isnomember}">
					<div class="row">
						<div class="col col-12">
							<div class="form-label">
								<label for="">배송지 목록</label>
							</div>
							<div class="form-control">
							 <div class="opt_select">
							
								<select  onchange="myAddrSelectChange();" id="addressTabId" name="addressTabId">
										<option value="" disabled selected hidden>배송지를 선택해 주세요.</option>
										<c:forEach var="addr" items="${addressList}" varStatus="idx">
										<c:choose>
											<c:when test="${addr.type eq 'RECENT' }">
												<option value="${addr.addressIdx}"  data-shippingnm="" data-addr="${addr.addr}"  data-addrdetail="${addr.addrDetail}"  data-zipcd="${addr.zipCd}"  data-oldaddr="${addr.oldAddr}" data-oldzipcd="${addr.oldZipCd}"  data-telno="${addr.telNo}"  data-phoneno="${addr.phoneNo}"   >최근 배송지</option>
											</c:when> 
											<c:otherwise>
												<option value="${addr.addressIdx}" data-shippingnm="${addr.shippingNm}"  data-addr="${addr.addr}"  data-addrdetail="${addr.addrDetail}"  data-zipcd="${addr.zipCd}"  data-oldaddr="${addr.oldAddr}" data-oldzipcd="${addr.oldZipCd}"  data-telno="${addr.telNo}"  data-phoneno="${addr.phoneNo}"   >${addr.shippingNm}</option>
											</c:otherwise>
										</c:choose>
										</c:forEach>
										<option value="">신규등록</option>
									</select>
							</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col col-12">
							<div class="form-label">
								<label for="">배송지 정보</label>
							</div>
							<div class="form-control">
								<input type="text" class="input" placeholder="배송지명" name="shippingNm" id="shippingNm" value="" class="form-control" placeholder="" maxlength="30">
							</div>
						</div>
					</div>
					</c:if>
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
								<input type="text" class="input" placeholder="받으시는 분" required  name="receiverNm" id="receiverNm" value="${orderInfo.receiverNm}" maxlength="30">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
								<input type="text" class="input" placeholder="휴대폰번호 (‘-’를 제외한 숫자만 입력)" required name="receiverPhoneNo" id="receiverPhoneNo" value="${orderInfo.originReceiverPhoneNo}" maxlength="12">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
								<input type="text" class="input" placeholder="일반전화 (’-’를 제외한 숫자만 입력)" name="receiverTelNo" id="receiverTelNo" value="${orderInfo.receiverTelNo}" maxlength="12">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col col-7">
							<div class="form-control">
								<input type="text" class="input" required placeholder="우편번호" readonly="readonly" name="receiverZipCd" id="receiverZipCd" value="${orderInfo.receiverZipCd}" maxlength="5"  onclick="addrPopup();">
							</div>
						</div>
						<div class="col col-5">
							<button type="button" class="btn full ico-chev" onclick="addrPopup();"><span class="txt">우편번호 검색</span></button>
						</div>
					</div>
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
								<input type="text" class="input" placeholder="주소" required  readonly="readonly"  name="receiverAddr" id="receiverAddr" value="${orderInfo.receiverAddr}" maxlength="100">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
								<input type="text" class="input" placeholder="나머지 주소" required name="receiverAddrDetail" id="receiverAddrDetail" value="${orderInfo.receiverAddrDetail}"  maxlength="100">
							</div>
						</div>
					</div>
						<c:if test="${!isnomember}">			
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
								<span class="checkbox">
									<input type="checkbox"  class="check" name="addToAddress" id="addToAddress" value="Y">
									<label for="addToAddress" class="lbl">배송지 정보 저장</label>
								</span>
								&nbsp;&nbsp;
								<span class="checkbox">
									<input type="checkbox"  class="check" name="setAsDefaultAddress" id="setAsDefaultAddress" value="Y">
									<label for="setAsDefaultAddress" class="lbl">기본 배송지로 설정</label>
								</span>
							</div>
						</div>
					</div>					
				</c:if>
									
				</div>
			</div>
		</div>
		
		<div class="btn-box confirm">
			<button type="button" class="btn" data-dismiss="popup"><span class="txt">취소</span></button>
			<button type="button" class="btn outline-green" onclick="confirmDeliveryForm();"><span class="txt">수정</span></button>
		</div>
		
	</div>
	<!-- //pop-mid -->
	<a href="#none" class="btn_close" data-dismiss="popup" ><span class="hide">닫기</span></a>
	
	</form>
	
	
	
	
	
	
	<!-- //pop-btn -->