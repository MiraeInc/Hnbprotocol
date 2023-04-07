<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<fmt:formatNumber var="totalPayPrice" value="${orderInfo.totalPayPrice}" groupingUsed="false" pattern="##0"/>		<%-- 결제금액 0.00 으로 표시돼서 소숫점 없앰 --%>
<!DOCTYPE>
<html>
<head>
<script>
	dataLayer = [];
</script>
<meta name="decorator" content="mobile.main"/>
<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<% 
/*
<script language="javascript" src="${xpayJsUrl}" type="text/javascript"></script>
 */ 
 %>
<script type="text/javascript" src="https://static-bill.nhnent.com/payco/checkout/js/payco_mobile.js" charset="UTF-8"></script>
<%--smilePay script 공통 --%>
<%-- <script src="${smilepay.webPath}/dlp/scripts/postmessage.js" charset="utf-8"></script>
<script src="${smilepay.webPath}/dlp/scripts/cnspay.js" charset="utf-8"></script>
 --%>
 <!-- smilepay javascript Library -->
<link rel="stylesheet" type="text/css" href="${smilepay.webPath}/dlp/css/pc/cnspay.css"/>
<script src="${smilepay.webPath}/dlp/scripts/smilepay.js" charset="utf-8"></script>

<!-- 거래등록 하는 kcp 서버와 통신을 위한 스크립트-->
<script type="text/javascript" src="${CTX}/js/m/kcp_approval_key.js"></script>

<script type="text/javascript">
	if("<spring:message code='server.status'/>" == "LIVE"){
		<!-- Facebook Pixel Code -->
		fbq('track', 'InitiateCheckout');    
	}

  function call_pay_form()
  {
    var v_frm = document.frm_kcp;
    // v_frm.action = PayUrl;

	v_frm.action = PayUrl.substring(0,PayUrl.lastIndexOf("/")) + "/jsp/encodingFilter/encodingFilter.jsp";             
	v_frm.PayUrl.value = PayUrl;         

	
    if (v_frm.Ret_URL.value == "")
    {
	  alert("연동시 Ret_URL을 반드시 설정하셔야 됩니다.");
      return false;
    }
    else
    {
      v_frm.submit();
    }
  }

   /* kcp 통신을 통해 받은 암호화 정보 체크 후 결제 요청 (변경불가) */
  function chk_pay()
  {
    self.name = "tar_opener";
    var pay_form = document.pay_form;

    if (pay_form.res_cd.value == "3001" )
    {
      alert("사용자가 취소하였습니다.");
      pay_form.res_cd.value = "";
    }

    if (pay_form.enc_info.value)
      pay_form.submit();
  }

</script>
<script type="text/javascript">
	
	var paymenttype = "";
	var modifyAddressIdx;	<%-- 수정/삭제할 배송지 일련번호 --%>

	<%-- 구매 버튼 연타 방지 --%>
	var didSubmit = false;
	
	window.onpageshow = function (event) {
		didSubmit = false;
	}
	<%-- 구매 버튼 연타 방지 끝 --%>
	
	
	//사은품 높이 처리
	$(window).on('load', function(){

		var giftTemp = 0;

		$('.gift-list').find('.item').each(function(idx){
			var $this = $(this);
			if(giftTemp < $this.outerHeight()) {
				giftTemp = $this.outerHeight()
			}
		});

		$('.gift-list').find('.item').css('height', giftTemp);

	})
	
	<!-- PAGE SCRIPT -->
	$(function(){	
		//입력 글자수 제한
		//dispay, max(바이트)
		$('#orderMemo').limitWrite(null, 90);
	
		//결제방법선택
		$(document).on('click', '.method-type a', function(e){

			e.preventDefault();
			
			var $this = $(this);
			var $root = $this.parents('.method-type');
			var $method = $('input[name="METHOD_TYPE"]');
			var href = $this.attr('href');
			
			//insert value
			$method.val($this.attr('data-method'))
			
			//interactions
			$this.addClass('active').closest('li').siblings().find('a').removeClass('active');
			
			if(href == '#methodOneclick'){
				$root.find('a').removeClass('active');
				$(href).show().siblings('.method-item').hide();
				$('.method-confirm').removeClass('noitem');
			}else if(href == '#none'){
				$('.method-item').hide();
				$('.method-confirm').addClass('noitem');
			}else{

				if(href == '#methodPayco' || href == "#methodPaynow"){
					$('.method-guide').hide();
					$('[data-id="'+href.replace('#', '')+'"]').show();
				}else{
					$('.method-guide').hide();
				}
				$(href).show().siblings('.method-item').hide();
				$('.method-confirm').removeClass('noitem');
				
			}	
		});
	
		//사은품 UI
		$.touchflow({
			wrap: '.tab-menu',
			container: '.menu-inner',
			list: '.menu-list',
			tablink: '.menu-link'
		});	

		$('#tabmenu').tabControl();
	});
	<!-- //PAGE SCRIPT -->

	$(document).ready(function(){
		
		<%-- 숫자만 입력 --%>
		createNumeric("#senderPhoneNo,#receiverPhoneNo,#receiverTelNo,#receiverZipCd,#totalPointPrice,#newReceiverPhoneNo,#newReceiverTelNo,#newReceiverZipCd");
		
		<%-- 새로고침시 스크롤 원 위치--%>
		var obj = $('html').scrollTop() !== 0 ? 'html' : 'body';
		$(obj).scrollTop(document.cookie.split(';')[0]);
		
		<%-- 카드선택 폼이 active가 있을 경우 기타 카드 초기화 --%>
		var sCardCode = "${orderInfo.selectCardCode}";
		if(sCardCode != "" && sCardCode != null){
			$("#etcCardVal").val("");
		}
	});

	<%-- 새로고침시 스크롤 위치 저장 --%>
	$(window).on("unload",function(e){
		var obj = $('html').scrollTop() !== 0 ? 'html' : 'body';
		document.cookie = $(obj).scrollTop();
	});

	// 구매 혜택 레이어 
	function benefit(type){
		if(type == 1){
			$("#purchaseTitle").text("무이자 할부 안내");
			$("#purchaseDesc2").hide();
			$("#purchaseDesc3").hide();
			$("#purchaseDesc1").show();
		}else if(type == 2){
			$("#purchaseTitle").text("카드 할인 혜택");
			$("#purchaseDesc1").hide();
			$("#purchaseDesc3").hide();
			$("#purchaseDesc2").show();
		}
	}

	<%-- 이메일 구분 선택 --%>
	$(document).on("change","#emailGubun",function(){
		if($(this).val() == "직접입력"){
			$("#email2").parent().show();
			$("#email2").focus();
		}else{
			$("#email2").parent().hide();
		}
	});

	<%-- 배송지 목록/새로입력 탭 전환 --%>
	function changeAddressTab(val){
		if(val == 0){
			$("#addressID1").addClass("active");
			$("#addressID2").removeClass("active");
			
			$("#divSameOrderInfo").hide();	
		}else{
			$("#addressID1").removeClass("active");
			$("#addressID2").addClass("active");

			$("#divSameOrderInfo").show();
		}		
	}
	
	<%-- 배송지 삭제 --%>
	function deleteAddress(val){
		modifyAddressIdx = val;
		
		if(!confirm($("input:radio[name='selectAddressIdx'][value='"+val+"']").data("receivernm")+" 배송지를 삭제하시겠습니까?")){
			return false;
		}
		
		putSavedata();	<%-- 호출전 할당할 데이터 --%>
		
		$.ajax({			
			url: getContextPath()+"/ajax/order/deleteAddressAjax.do",
			data: $("#orderForm").serialize() + "&modifyAddressIdx=" + modifyAddressIdx,
		 	type: "post",
		 	async: false,
		 	cache: false,
		 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data){
				if(data.result == true){
					location.reload();
				}else{
					if(data.msg != ""){
						alert(data.msg);
					}
				}
			 }
		});
	}
	
	<%-- 배송지 수정 취소 --%>
	function cancelModifyAddress(){
		$("#popModifyAddress").removeClass("active");
<%--		
		putSavedata();	<!-- 호출전 할당할 데이터 -->
		
		$.ajax({			
			url: getContextPath()+"/ajax/order/saveOrderInfoAjax.do",
			data: $("#orderForm").serialize(),
		 	type: "post",
		 	async: false,
		 	cache: false,
		 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data){
				if(data.result == true){
					location.reload();
				}else{
					if(data.msg != ""){
						alert(data.msg);
					}
				}
			 }
		});
--%>
	}
	
	<%-- 배송지 저장 --%>
	function saveAddress(){
		
		<%-- 배송지명 --%>
		/* if($.trim($("#newShippingNm").val()) == ""){
			alert("필수 항목을 입력해 주세요.(배송지명)");
			$("#newShippingNm").focus();
			return false;
		} */

		<%-- 받는이 성명 --%>
		if($.trim($("#newReceiverNm").val()) == "" || $.trim($("#newReceiverNm").val()).length < 2){
			alert("받으시는 분 성함을 확인해 주세요. (2글자 이상 입력)");
			$("#newReceiverNm").focus();
			return false;
		}

		<%-- 받는이 휴대폰 --%>
		if($.trim($("#newReceiverPhoneNo").val()) == ""){
			alert("필수 항목을 입력해 주세요.(받으시는 분 휴대폰)");
			$("#newReceiverPhoneNo").focus();
			return false;
		}

		<%-- 받는이 우편번호 --%>
		if($.trim($("#newReceiverZipCd").val()) == ""){
			alert("필수 항목을 입력해 주세요.(받으시는 분 우편번호)");
			$("#newReceiverZipCd").focus();
			return false;
		}

		<%-- 받는이 주소 --%>
		if($.trim($("#newReceiverAddr").val()) == "" || $.trim($("#newReceiverAddrDetail").val()) == ""){
			alert("필수 항목을 입력해 주세요.(받으시는 분 주소)");
			$("#newReceiverAddrDetail").focus();
			return false;
		}
		
		<%-- 이메일 주소 입력 --%>
		if($("#emailGubun").val() == "직접입력"){
			$("#senderEmail").val($.trim($("#email1").val()) + "@" + $.trim($("#email2").val()));	
		}else{
			$("#senderEmail").val($.trim($("#email1").val()) + "@" + $("#emailGubun").val());
		}		
		
		<%-- 배송지 정보,새로입력 선택한 탭 저장 --%>
		if($("#addressID2").hasClass("active")){
			$("#addressTabId").val("addressID2");
		}else if($("#addressID1").hasClass("active")){
			$("#addressTabId").val("addressID1");
		}else{
			$("#addressTabId").val("");
		}
		
		<c:choose>
			<c:when test="${totalPayPrice eq 0}">	<%-- 포인트 결제 (결제금액 0원) --%>
				paymenttype = "POINT";
				$("#selectPayType").val("POINT");
			</c:when>
			<c:otherwise>
				<%-- 결제 방식 --%>
				paymenttype = $("#METHOD_TYPE").val();
				$("#selectPayType").val(paymenttype);	<%-- 선택한 결제 방식 --%>
			</c:otherwise>
		</c:choose>
			
		<%-- 선택한 사은품 탭 --%>
		$("#giftTabId").val($("#giftTab button.active").attr("data-tab-id"));

		$.ajax({
			url: getContextPath()+"/ajax/order/saveAddressAjax.do",
			data: $("#orderForm").serialize() 
					+ "&modifyAddressIdx=" + modifyAddressIdx 
					+ "&newShippingNm=" + $("#newShippingNm").val()
					+ "&newReceiverNm=" + $("#newReceiverNm").val()
					+ "&newReceiverPhoneNo=" + $("#newReceiverPhoneNo").val()
					+ "&newReceiverTelNo=" + $("#newReceiverTelNo").val()
					+ "&newReceiverZipCd=" + $("#newReceiverZipCd").val()
					+ "&newReceiverAddr=" + $("#newReceiverAddr").val()
					+ "&newReceiverAddrDetail=" + $("#newReceiverAddrDetail").val()
					+ "&newSetAsDefaultAddress=" + $("#newSetAsDefaultAddress").val(),
		 	type: "post",
		 	async: false,
		 	cache: false,
		 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		 	error: function(request, status, error){
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			},
			success: function(data){
				if(data.result == true){
					location.reload();
				}else{
					if(data.msg != ""){
						alert(data.msg);
					}
				}
			 }
		});
	}
	
	<%-- 배송지 수정 --%>
	function modifyAddress(val){
		
		var inputObj = $("input:radio[name='selectAddressIdx'][value='"+val+"']");
		
		$("#newShippingNm").val(inputObj.data("shippingnm"));
		$("#newReceiverNm").val(inputObj.data("receivernm"));
		$("#newReceiverPhoneNo").val(inputObj.data("phoneno"));
		$("#newReceiverTelNo").val(inputObj.data("telno"));
		$("#newReceiverZipCd").val(inputObj.data("zipcd"));
		$("#newReceiverAddr").val(inputObj.data("addr"));
		$("#newReceiverAddrDetail").val(inputObj.data("addrdetail"));
		if(inputObj.data("defaultyn") == "Y"){
			$("#newSetAsDefaultAddress").prop("checked",true);
		}else{
			$("#newSetAsDefaultAddress").prop("checked",false);
		}
		
		modifyAddressIdx = val;
		
		$("#popModifyAddress").addClass("active");
	}
	
	<%-- 배송시 요청사항 선택 --%>
	$(document).on("change","#orderMemoVal",function(){
		if($(this).val() == "ORDER_MEMO90"){
			$("#orderMemo").parent().show();
			$("#orderMemo").focus();
		}else{
			$("#orderMemo").parent().hide();
		}
	});	

	<%-- 선물포장 선택 --%>
	$(document).on("click","#giftPackingYn",function(){
		var totalOrderPrice = Number(uncomma($("#totalOrderPrice").text()));
		var deliveryPrice = Number(uncomma($("#deliveryPrice").text()));

		if($(this).is(":checked")){
//			$(".tb-con-sty1").attr("rowspan","2");
//			$("#trGiftPacking").show();
			$("#totalPayPrice").text(comma(totalOrderPrice + deliveryPrice + parseInt("${shippingPrice}")));
			$("#totalPayPriceTop").text(comma(totalOrderPrice + deliveryPrice + parseInt("${shippingPrice}")));
		}else{
//			$(".tb-con-sty1").attr("rowspan","1");
//			$("#trGiftPacking").hide();
			$("#totalPayPrice").text(comma(totalOrderPrice + deliveryPrice));
			$("#totalPayPriceTop").text(comma(totalOrderPrice + deliveryPrice));
		}
	});	


	<%-- 호출전 할당할 데이터 --%>
	function putSavedata() {

		<%-- 이메일 주소 입력 --%>
		if($("#emailGubun").val() == "직접입력"){
			$("#senderEmail").val($.trim($("#email1").val()) + "@" + $.trim($("#email2").val()));	
		}else{
			$("#senderEmail").val($.trim($("#email1").val()) + "@" + $("#emailGubun").val());
		}		
		
		<%-- 배송지 정보,새로입력 선택한 탭 저장 --%>
		if($("#addressID2").hasClass("active")){
			$("#addressTabId").val("addressID2");
		}else if($("#addressID1").hasClass("active")){
			$("#addressTabId").val("addressID1");
		}else{
			$("#addressTabId").val("");
		}
		
		<c:choose>
			<c:when test="${totalPayPrice eq 0}">	<%-- 포인트 결제 (결제금액 0원) --%>
				paymenttype = "POINT";
				$("#selectPayType").val("POINT");
			</c:when>
			<c:otherwise>
				<%-- 결제 방식 --%>
				paymenttype = $("#METHOD_TYPE").val();
				$("#selectPayType").val(paymenttype);	<%-- 선택한 결제 방식 --%>
			</c:otherwise>
		</c:choose>
		
		<%-- 선택한 사은품 탭 --%>
		$("#giftTabId").val($("#giftTab button.active").attr("data-tab-id"));

	}

	<%-- 프로모션 코드 등록 --%>
	function applyPromotioncode(){
		var promotioncode = $.trim($("#promotioncode").val());
		$("#promotioncode").val(promotioncode);
		if(promotioncode == ""){
			alert("프로모션 코드를 입력해 주세요.");
			$("#promotioncode").focus();
			return false;
		}
		
		putSavedata();	<%-- 호출전 할당할 데이터 --%>
		
		$.ajax({			
			url: getContextPath()+"/ajax/order/PcodeApplyAjax.do",
			data: $("#orderForm").serialize(),
		 	type: "post",
		 	async: false,
		 	cache: false,
		 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data){
				if(data.result == true){
					alert("프로모션코드가 적용되었습니다.");
					location.reload();
				}else{
					if(data.msg != ""){
						alert(data.msg);
					}
				}
			 }
		});		
	}
	
	<%-- 랜덤 프로모션 코드 등록 --%>
	function applyPromotioncodeRandom(){
		var promotioncoderandom = $.trim($("#promotioncoderandom").val());
		$("#promotioncoderandom").val(promotioncoderandom);
		$("#randomcodeYn").val("Y");
		if(promotioncoderandom == ""){
			alert("카카오 플친 코드를 입력해 주세요.");
			$("#promotioncoderandom").focus();
			return false;
		}
		
		putSavedata();	<%-- 호출전 할당할 데이터 --%>
		
		$.ajax({			
			url: getContextPath()+"/ajax/order/PcodeApplyRandomAjax.do",
			data: $("#orderForm").serialize(),
		 	type: "post",
		 	async: false,
		 	cache: false,
		 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data){
				if(data.result == true){
					alert("카카오 플친 코드가 적용되었습니다.");
					location.reload();
				}else{
					if(data.msg != ""){
						alert(data.msg);
					}
				}
			 }
		});		
	}
	
	
	<%-- 프로모션 코드 초기화 --%>
	function resetPromotioncode(){
		
		var promotioncode = $.trim($("#promotioncode").val());
		if(promotioncode == ""){
			alert("적용된 프로모션코드가 존재하지 않습니다.");
			$("#promotioncode").focus();
			return false;
		}

		if(!confirm("프로모션코드를 초기화 하시겠습니까?")){
			return false;
		}

		putSavedata();
		
		$("#promotioncode").val("");
		$.ajax({			
			url: getContextPath()+"/ajax/order/PcodeApplyAjax.do",
			data: $("#orderForm").serialize(),
		 	type: "post",
		 	async: false,
		 	cache: false,
		 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data){
				if(data.result == true){
					alert("프로모션코드가 초기화 되었습니다.");
					location.reload();
				}else{
					if(data.msg != ""){
						alert(data.msg);
					}
				}
			 }
		});
	}
	
	<%-- 랜덤 프로모션 코드 초기화 --%>
	function resetPromotioncodeRandom(){
		
		var promotioncoderandom = $.trim($("#promotioncoderandom").val());
		if(promotioncoderandom == ""){
			alert("적용된 카카오 플친 코드가 존재하지 않습니다.");
			$("#promotioncoderandom").focus();
			return false;
		}

		if(!confirm("카카오 플친 코드를 초기화 하시겠습니까?")){
			return false;
		}

		putSavedata();
		
		$("#promotioncoderandom").val("");
		$("#randomcodeYn").val("N");
		$.ajax({			
			url: getContextPath()+"/ajax/order/PcodeApplyRandomAjax.do",
			data: $("#orderForm").serialize(),
		 	type: "post",
		 	async: false,
		 	cache: false,
		 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data){
				if(data.result == true){
					alert("카카오 플친 코드가 초기화 되었습니다.");
					location.reload();
				}else{
					if(data.msg != ""){
						alert(data.msg);
					}
				}
			 }
		});
	}
	
	<%-- 장바구니 쿠폰 변경 --%>
	function popCartCouponChange() {
		var select_discount = 0;
		select_discount = $("#cart_coupon_select option:selected").attr("data-discount");
		if (select_discount == 'undefined' || select_discount == '' || select_discount == null) {
			select_discount = '0';
		}
		select_discount = comma(select_discount);
		$("#cart_discount").html(select_discount);
	}
	
	<%-- 장바구니 쿠폰 적용 --%>
	function applyCartCoupon(isreset) {
		var couponIdx = 0;
		couponIdx = $("#cart_coupon_select option:selected").attr("value");
		
		if (isreset) { 
			couponIdx = '0';
		}

		if (couponIdx == 'undefined' || couponIdx == '' || couponIdx == null) {
			if($("#cartCouponIdx").val() == "0"){
				alert("쿠폰을 선택하세요!");
				return false;
			}

			couponIdx = '0';
		}		
		
		putSavedata();	<%-- 호출전 할당할 데이터 --%>
		
		$.ajax({			
			url: getContextPath()+"/ajax/order/applyCartCouponAjax.do",
			data: $("#orderForm").serialize()+"&couponIdx="+couponIdx,
		 	type: "post",
		 	async: false,
		 	cache: false,
		 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data){
				if(data.result == true){
					if (isreset) {
						alert("장바구니 쿠폰이 초기화 되었습니다.");
						location.reload();
					}
					else {
						if(couponIdx == "0"){
							alert("장바구니 쿠폰이 초기화 되었습니다.");
						}else{
							alert("장바구니 쿠폰이 적용 되었습니다.");
						}
						location.reload();
					}
				}else{
					if(data.msg != ""){
						alert(data.msg);
					}
				}
			 }
		});		
	}
	
	<%-- 장바구니 쿠폰 초기화 --%>
	function resetCartCoupon(){
		var couponIdx = 0;
		couponIdx = $("#cart_coupon_select option:selected").attr("value");
		if (couponIdx == 'undefined' || couponIdx == '' || couponIdx == null || couponIdx == 0) {
			alert("적용된 장바구니 쿠폰이 존재하지 않습니다.");
			return false;
		}
		
		if(!confirm("장바구니 쿠폰을 초기화 하시겠습니까?")){
			return false;
		}
		
		applyCartCoupon(true);
	}
	
	<%-- 배송비 쿠폰 변경 --%>
	function popShippingCouponChange() {
		var select_discount = 0;
		select_discount = $("#shipping_coupon_select option:selected").attr("data-discount");
		if (select_discount == 'undefined' || select_discount == '' || select_discount == null) {
			select_discount = '0';
		}
		select_discount = comma(select_discount);
		$("#shipping_discount").html(select_discount);
	}

	<%-- 배송비 쿠폰 초기화 --%>	
	function resetShippingCoupon() {
		var couponIdx = 0;
		couponIdx = $("#shipping_coupon_select option:selected").attr("value");
		if (couponIdx == 'undefined' || couponIdx == '' || couponIdx == null || couponIdx == 0) {
			alert("적용된 배송비 쿠폰이 존재하지 않습니다.");
			return false;
		}

		if(!confirm("배송비 쿠폰을 초기화 하시겠습니까?")){
			return false;
		}

		applyShippingCoupon(true);
	}
	
	<%-- 배송비 쿠폰 적용 --%>
	function applyShippingCoupon(isreset) {
		var couponIdx = 0;
		couponIdx = $("#shipping_coupon_select option:selected").attr("value");
		
		if (couponIdx == 'undefined' || couponIdx == '' || couponIdx == null) {
			if($("#freeShippingCouponIdx").val() == "0"){
				alert("쿠폰을 선택하세요!");
				return false;
			}
			
			couponIdx = '0';
		}
		
		if (isreset) { 
			couponIdx = '0';
		}
		
		putSavedata();
		
		$.ajax({			
			url: getContextPath()+"/ajax/order/applyShippingCouponAjax.do",
			data: $("#orderForm").serialize()+"&couponIdx="+couponIdx,
		 	type: "post",
		 	async: false,
		 	cache: false,
		 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data){
				if(data.result == true){
					if (isreset) {
						alert("배송비 쿠폰이 초기화 되었습니다.");
						location.reload();
					}
					else {
						if(couponIdx == "0"){
							alert("배송비 쿠폰이 초기화 되었습니다.");
						}else{
							alert("배송비 쿠폰이 적용 되었습니다.");
						}
						location.reload();
					}
				}else{
					if(data.msg != ""){
						alert(data.msg);
					}
				}
			 }
		});		
	}
	
	<%-- 모든 포인트 사용 --%>
	function useAllPoint(){
		var usepoint = 0;
		var shippingprice = Number("${orderInfo.shippingPrice}");
		var mypoint = $.trim($("#myPoint").val());
		var settlepoint= Number($.trim($("#LGD_AMOUNT").val())) - shippingprice; 
		var myPointApply = $.trim($("#myPointApply").val()); //기적용된 금액 
		var maxpoint = Number(settlepoint) + Number(myPointApply); //최대 적용가능금액 
		
		usepoint = maxpoint;
		
		if(Number(usepoint) > Number(mypoint) )	{
			usepoint = mypoint;
		}	 

		if(!confirm(comma(usepoint)+"포인트를 사용하시겠습니까?")){
			return false;
		}

		$("#totalPointPrice").val(usepoint);
		usePoint(false);
	}
	
	<%-- 포인트 초기화 --%>
	function resetPoint() {
		var usepoint = $.trim($("#totalPointPrice").val());
		if (usepoint == 'undefined' || usepoint == '' || usepoint == null || usepoint == 0) {
			alert("사용된 포인트가 존재하지 않습니다.");
			return false;
		}

		if(!confirm("포인트를 초기화 하시겠습니까?")){
			return false;
		}
					
		$("#totalPointPrice").val(usepoint);
		usePoint(true);
	}
	
	<%-- 포인트 사용 클릭 --%>
	function applyMyPoint() {
		var usepoint = uncomma($.trim($("#totalPointPrice").val()));
		var shippingprice = Number("${orderInfo.shippingPrice}");
		var mypoint = $.trim($("#myPoint").val());
		var settlepoint= Number($.trim($("#LGD_AMOUNT").val())) - shippingprice; 
		var myPointApply = $.trim($("#myPointApply").val()); //기적용된 금액 
		var maxpoint = Number(settlepoint) + Number(myPointApply); //최대 적용가능금액 
		var msg = "";
		
		if(usepoint == ""){
			alert("사용할 포인트를 입력해 주세요.");
			$("#totalPointPrice").focus();
			return false;
		}

		if(Number(maxpoint) > Number(mypoint) )	{
			maxpoint = mypoint;
		}	 
		
		if(Number(usepoint) > Number(maxpoint) )	{
			usepoint = maxpoint;
			msg = "최대 사용 가능 포인트는 "+comma(maxpoint)+"입니다.\n"+comma(usepoint)+"포인트를 사용하시겠습니까?";
		}	 
		else {
			msg = comma(usepoint)+"포인트를 사용하시겠습니까?";
		}

		if(!confirm(msg)){
			return false;
		}

		$("#totalPointPrice").val(usepoint);
		usePoint(false);
	}

	<%-- 포인트 적용 --%>
	function usePoint(isreset){
		var usepoint = $.trim($("#totalPointPrice").val());
		
		if (isreset) { 
			usepoint = '0';
		}
		
		$("#totalPointPrice").val(usepoint);
		
		putSavedata();
		
		$.ajax({			
			url: getContextPath()+"/ajax/order/pointApplyAjax.do",
			data: $("#orderForm").serialize()+"&usepoint="+usepoint,
		 	type: "post",
		 	async: false,
		 	cache: false,
		 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data){
				if(data.result == true){
					if (isreset) {
						alert("포인트를 초기화 하였습니다.");
						location.reload();
					}
					else {
						alert("포인트를 적용하였습니다.");
						location.reload();
					}
				}else{
					if(data.msg != ""){
						alert(data.msg);
					}
				}
			 }
		});
	}	

	<%-- 상품 쿠폰 팝업 --%>
	function useGoodsCoupon(){

		putSavedata();	<%-- 호출전 할당할 데이터 --%>
		
		<%-- 주문 정보 DB 저장 --%>
		$.ajax({			
			url: getContextPath()+"/ajax/order/saveOrderInfoAjax.do",
			data: $("#orderForm").serialize(),
		 	type: "post",
		 	async: false,
		 	cache: false,
		 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data1){
				if(data1.result == true){
					$("#pop_goods_coupon_list .product-info").each(function(){
						var detailIdx = $(this).data("detailidx");
						var giftCouponIdx = $(this).data("giftcouponidx");

						$.ajax({			
							url: getContextPath()+"/ajax/order/getUsableGoodsCouponAjax.do",
							data: $("#orderForm").serialize() + "&detailIdx=" + detailIdx,
						 	type: "post",
						 	async: false,
						 	cache: false,
						 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
						 	error: function(request, status, error){ 
						 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
							},
							success: function(data){
								if(data.result == true){
									var selectbox = $("#gift_coupon_select_"+detailIdx);
									selectbox.empty();

									if(data.couponList.length == 0){
										selectbox.append("<option value='0' data-discount='0'>선택</option>");
										selectbox.append("<option value='0' selected data-discount='0'>사용 가능한 쿠폰이 없습니다.</option>");
									}else{
										selectbox.append("<option value='' data-discount='0'>선택</option>");
										for(var i = 0;i < data.couponList.length;i++){
											var coupon_idx = data.couponList[i].coupon_IDX;
											var coupon_member_idx = data.couponList[i].coupon_MEMBER_IDX;
											
											var coupon_value = coupon_member_idx;
											if(coupon_member_idx == 0){
												coupon_value = coupon_idx*-1; //자동발급쿠폰인경우 - 붙임
											}
											var coupon_name = data.couponList[i].coupon_NM;
											var discount_price = data.couponList[i].discount_PRICE;
											var discount = data.couponList[i].discount;
											var discount_type = data.couponList[i].discount_TYPE;
											var selected = "";
											if (giftCouponIdx == coupon_value){
												selected = "selected";
											}
											selectbox.append("<option value='"+coupon_value+"' "+selected+" data-discount='"+discount+"'  data-discount-type='"+discount_type+"' data-discount-price='"+discount_price+"'>"+coupon_name+"</option>")
										}
									}
									
									$("#PopGoodsCoupon").addClass("active");
								}else{
									if(data.msg != ""){
										alert(data.msg);
									}
								}
							 }
						});	
					});
				}else{
					if(data1.msg != ""){
						alert(data1.msg);
					}
				}
			 }
		});	
	}

	<%-- 상품 쿠폰 변경 전 --%>
	function popGiftCouponFocus(val){
		$("#pop_goods_coupon_list .product-info[data-detailidx='"+val+"']").data("oldgiftcouponidx",$("#gift_coupon_select_"+val).val());
	}
	
	<%-- 상품 쿠폰 변경 --%>
	function popGiftCouponChange(val){

		<%-- 이미 같은 쿠폰이 다른 상품에 적용됐는지 --%>
		var oldCouponIdx = $("#pop_goods_coupon_list .product-info[data-detailidx='"+val+"']").data("oldgiftcouponidx");
		var newCouponIdx = $("#gift_coupon_select_"+val).val();
		
		if(Number(newCouponIdx) > 0){	<%-- 자동발급 쿠폰은 여러개 적용돼도 되니 조건 체크에서 제외 --%>
			var isCancel = false;
			
			$("#pop_goods_coupon_list .product-info").each(function(){
				var detailIdx = $(this).data("detailidx");
				if(detailIdx != val){	<%-- 자기 자신은 제외 --%>
					if(newCouponIdx == $("#gift_coupon_select_"+detailIdx).val()){
						if(!confirm("이미 다른 상품에 적용된 쿠폰입니다! 현재 상품에 적용하시겠습니까?")){
							$("#gift_coupon_select_"+val).val(oldCouponIdx);
							isCancel = true;
							return false;
						}
						
						$("#pop_goods_coupon_list .product-info[data-detailidx='"+detailIdx+"']").data("giftcouponidx","");	<%-- 이미 적용되어 있던 상품의 쿠폰 제거 --%>
						$("#gift_coupon_select_"+detailIdx).val("");
						$("#gift_discount_"+detailIdx).html("0");
					}
				}
			});
			
			<%-- 이미 다른 상품에 적용된 쿠폰입니다! 현재 상품에 적용하시겠습니까? 에서 취소시 쿠폰 변경 취소 --%>
			if(isCancel){
				return false;
			}
		}
		
		$("#pop_goods_coupon_list .product-info[data-detailidx='"+val+"']").data("giftcouponidx",$("#gift_coupon_select_"+val).val());

		var select_discount = 0;
		select_discount = $("#gift_coupon_select_"+val+" option:selected").data("discount-price");
		if(select_discount == 'undefined' || select_discount == '' || select_discount == null){
			select_discount = '0';
		}
		select_discount = comma(select_discount);
		$("#gift_discount_"+val).html(select_discount);
		
		<%-- 총 할인 금액 계산 --%>
		var totalDiscount = 0;
		$("#pop_goods_coupon_list .product-info").each(function(){
			var detailIdx = $(this).data("detailidx");
			
			var giftDiscount = Number(uncomma($("#gift_discount_"+detailIdx).text()));
			
			totalDiscount += giftDiscount;
		});
		
		if(totalDiscount == 0){
			$("#total_goods_discount").html("0");
		}else{
			$("#total_goods_discount").html("-"+comma(totalDiscount));
		}
	}
	
	<%-- 상품 쿠폰 적용 --%>
	function applyGiftCoupon(){
		
		if($("#pop_goods_coupon_list .product-info").length == 0){
			alert("쿠폰이 없습니다!");
			return false;
		}
		
		var couponInfo = "";
		$("#pop_goods_coupon_list .product-info").each(function(){
			var detailIdx = $(this).data("detailidx");
			var giftCouponIdx = $(this).data("giftcouponidx");
			
			if(couponInfo != ""){
				couponInfo += ":;:";
			}
			
			couponInfo += detailIdx + "," + giftCouponIdx;
		});
			
		$.ajax({			
			url: getContextPath()+"/ajax/order/applyGiftCouponAjax.do",
			data: $("#orderForm").serialize()+"&couponInfo="+couponInfo,
		 	type: "post",
		 	async: false,
		 	cache: false,
		 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data){
				if(data.result == true){
					alert("상품 쿠폰이 적용 되었습니다.");
					location.reload();
				}else{
					if(data.msg != ""){
						alert(data.msg);
					}
				}
			 }
		});		
	}

	<%-- 결제정보 선택 --%>
<%--
	$(document).on("click",".modify-info li",function(){
		if( $(this).children("a").text() == "KAKAOPAY" || $(this).children("a").text() == "NAVERPAY"){
			alert("개발중입니다.");
			return false;
		}
		
		var idx = $(".modify-info li").index(this);
		$(".modify-info li a").removeClass("active");
		$(this).children("a").addClass("active");
		$(".paytype").hide();
		if(idx <= 4){
			$(".paytype:eq("+idx+")").show();
		}else{
			$(".paytype:eq(5)").show();
		}
	});
--%>
	<%-- 카드 선택 --%>
	$(document).on("click",".card-list li",function(){
		var idx = $(".card-list li").index(this);
		$(".card-list li a").removeClass("active");
		$(this).children("a").addClass("active");
		
		$("#etcCardVal").val("");
		/* if(idx == 9){
			$("#etc_cart").show();
		}else{
			$("#etc_cart").hide();
		} */
	});
	
	<%-- 기타카드 선택 시 카드선택 체크 해제 --%>
	$(document).on("change","#etcCardVal",function(){
		var cardVal = $(this).val();
		if(cardVal != "" && cardVal != null){
			$(".card-list li a").removeClass("active");
		}
	});

	<%-- 현금영수증 선택 --%>
	$(document).on("click","input:radio[name='cashReceiptGubun']",function(){
		if($(this).val() == "0"){
			$("#cashReceiptNo").parent().parent().parent().hide();
		}else{
			$("#cashReceiptNo").parent().parent().parent().show();
			$("#cashReceiptNo").focus();
		}
	});

	<%-- 숫자만 남기고 모두 치환 --%>
	function exitInput($this){
		var val = $($this).val();
		$($this).val(val.replace(/[^0-9]/g,''));
	}

	<%-- 주문하신 분과 상품을 받는 분이 같습니다. 클릭 이벤트 --%>
	$(document).on("click","#sameOrderInfo",function(){
		if($(this).is(":checked")){
			$("#receiverNm").val($("#senderNm").val());
			$("#receiverPhoneNo").val($("#senderPhoneNo").val());
			$("#receiverTelNo").focus();
		}else{
			$("#receiverNm").val("");
			$("#receiverPhoneNo").val("");
			$("#receiverNm").focus();
		}
	});

    function closeDaumPostcode() {
        var element_layer = document.getElementById('post_layer_div');
        // iframe을 넣은 element를 안보이게 한다.
        element_layer.style.display = 'none';
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
	
	<%-- CALLBACK 주소정보 값 --%> 
	var callback = function(zipCd, oldZipCd, addr, oldAddr){
		$("#receiverZipCd").val(zipCd);
		$("#receiverAddr").val(addr);
		$("#receiverOldZipCd").val(oldZipCd);
		$("#receiverOldAddr").val(oldAddr);
		$("#receiverAddrDetail").focus();
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
	 
	<%-- 수정용 우편번호 검색 --%>
	function newAddrPopup(){
//		execDaumPostcode(newCallback);
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
	                //document.getElementById('sample2_postcode').value = data.zonecode; //5자리 새우편번호 사용
	                //document.getElementById('sample2_address').value = fullAddr;
	                //document.getElementById('sample2_addressEnglish').value = data.addressEnglish;
					newCallback(data.zonecode, data.postcode, fullAddr, data.jibunAddress);
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
	
	<%-- 수정용 CALLBACK 주소정보 값 --%> 
	var newCallback = function(zipCd, oldZipCd, addr, oldAddr){
		$("#newReceiverZipCd").val(zipCd);
		$("#newReceiverAddr").val(addr);
		$("#newReceiverOldZipCd").val(oldZipCd);
		$("#newReceiverOldAddr").val(oldAddr);
		$("#newReceiverAddrDetail").val("");
		
		$("#newReceiverAddrDetail").focus();
   	}

	<%-- 주문자 정보 세팅 --%>
	function setSenderInfo(flag){
		if(flag == 0){	<%-- 회원정보와 동일 --%>
			$("#senderNm").val("<c:out value="${memberDetail.memberNm}"/>");			
			$("#senderPhoneNo").val("<c:out value="${fn:replace(memberDetail.phoneNo,'-','')}"/>");
			$("#email1").val("<c:out value="${fn:split(memberDetail.email,'@')[0]}"/>");
			var emailExist = "N";
			$("#emailGubun option").each(function(){
				if("<c:out value="${fn:split(memberDetail.email,'@')[1]}"/>" == $(this).val()){
					$(this).prop("selected",true);
					emailExist = "Y";
					$("#email2").val("");
					$("#email2").parent().hide();
					return false;
				}
			});
			if(emailExist == "N"){
				$("#emailGubun").val("직접입력");
				$("#email2").parent().show();
				$("#email2").val("<c:out value="${fn:split(memberDetail.email,'@')[1]}"/>");
			}
		}else if(flag == 1){	<%-- 새로입력 --%>
			$("#senderNm").val("");			
			$("#senderPhoneNo").val("");
			$("#email1").val("");
			$("#emailGubun").val("");
			$("#email2").val("");
			$("#senderNm").focus();
		}
	}
	
	<%-- 결제하기 --%>
	function payment(){
		var senderemail = "";
		<%-- 상품 존재 여부 --%>
		if($(".tr_goods").length == 0){
			alert("상품 정보가 없습니다.");
			return false;
		}

		<%-- 주문자 성명 --%>
		if($.trim($("#senderNm").val()) == ""){
			alert("필수 항목을 입력해 주세요.(주문자 성명)");
			$("#senderNm").focus();
			return false;
		}
		
		<%-- 주문자 이메일 --%>
		if($.trim($("#email1").val()) == ""){
			alert("필수 항목을 입력해 주세요.(주문자 이메일)");
			$("#email1").focus();
			return false;
		}

		if($("#emailGubun").val() == ""){
			alert("필수 항목을 입력해 주세요.(주문자 이메일)");
			$("#emailGubun").focus();
			return false;
		}
		
		if($("#emailGubun").val() == "직접입력" && $.trim($("#email2").val()) == ""){
			alert("필수 항목을 입력해 주세요.(주문자 이메일)");
			$("#email2").focus();
			return false;
		}
		
		<%-- 이메일 주소 입력 --%>
		if($("#emailGubun").val() == "직접입력"){
			$("#senderEmail").val($.trim($("#email1").val()) + "@" + $.trim($("#email2").val()));	
			senderemail =$.trim($("#email1").val()) + "@" + $.trim($("#email2").val());
		}else{
			$("#senderEmail").val($.trim($("#email1").val()) + "@" + $("#emailGubun").val());
			senderemail =$.trim($("#email1").val()) + "@" + $("#emailGubun").val();
		}		
		
		<%-- 주문자 휴대폰 --%>
		if($.trim($("#senderPhoneNo").val()) == ""){
			alert("필수 항목을 입력해 주세요.(주문자 휴대폰)");
			$("#senderPhoneNo").focus();
			return false;
		}

		<%-- 배송지 정보,새로입력 선택한 탭 저장 --%>
		if($("#addressID2").hasClass("active")){
			$("#addressTabId").val("addressID2");
		}else if($("#addressID1").hasClass("active")){
			$("#addressTabId").val("addressID1");
		}else{
			$("#addressTabId").val("");
		}
		
		<c:choose>
			<c:when test="${addressList eq null or fn:length(addressList) eq 0}">	<%-- 배송지정보 - 없을때 --%>
				<%-- 받는이 성명 --%>
				if($.trim($("#receiverNm").val()) == "" || $.trim($("#receiverNm").val()).length < 2){
					alert("받으시는 분 성함을 확인해 주세요. (2글자 이상 입력)");
					$("#receiverNm").focus();
					return false;
				}
	
				<%-- 배송지명 --%>
				/* if($("#addToAddress").is(":checked")){
					if($.trim($("#shippingNm").val()) == ""){
						alert("필수 항목을 입력해 주세요.(배송지명)");
						$("#shippingNm").focus();
						return false;
					}
				} */
	
				<%-- 받는이 휴대폰 --%>
				if($.trim($("#receiverPhoneNo").val()) == ""){
					alert("필수 항목을 입력해 주세요.(받으시는 분 휴대폰)");
					$("#receiverPhoneNo").focus();
					return false;
				}
	
				<%-- 받는이 우편번호 --%>
				if($.trim($("#receiverZipCd").val()) == ""){
					alert("필수 항목을 입력해 주세요.(받으시는 분 주소)");
					$("#receiverZipCd").focus();
					return false;
				}
	
				<%-- 받는이 주소 --%>
				if($.trim($("#receiverAddr").val()) == "" || $.trim($("#receiverAddrDetail").val()) == ""){
					alert("필수 항목을 입력해 주세요.(받으시는 분 주소)");
					$("#receiverAddrDetail").focus();
					return false;
				}
				
				<%-- 에스크로 배송지 정보 파라미터 --%>
				$("#frm_lguplus input:hidden[name='LGD_ESCROW_ZIPCODE']").val($("#receiverZipCd").val());
				$("#frm_lgcyber input:hidden[name='LGD_ESCROW_ZIPCODE']").val($("#receiverZipCd").val());
				$("#frm_lguplus input:hidden[name='LGD_ESCROW_ADDRESS1']").val($("#receiverAddr").val());
				$("#frm_lgcyber input:hidden[name='LGD_ESCROW_ADDRESS1']").val($("#receiverAddr").val());
				$("#frm_lguplus input:hidden[name='LGD_ESCROW_ADDRESS2']").val($("#receiverAddrDetail").val());
				$("#frm_lgcyber input:hidden[name='LGD_ESCROW_ADDRESS2']").val($("#receiverAddrDetail").val());
				$("#frm_lguplus input:hidden[name='LGD_ESCROW_BUYERPHONE']").val($("#senderPhoneNo").val());
				$("#frm_lgcyber input:hidden[name='LGD_ESCROW_BUYERPHONE']").val($("#senderPhoneNo").val());
				
				<%-- 에스크로 배송지 정보 파라미터 --%>
				$("#frm_kcp input:hidden[name='rcvr_name']").val($("#receiverNm").val());
				$("#frm_kcp input:hidden[name='rcvr_tel1']").val($("#receiverTelNo").val());
				$("#frm_kcp input:hidden[name='rcvr_tel2']").val($("#receiverPhoneNo").val());
				$("#frm_kcp input:hidden[name='rcvr_mail']").val(senderemail);
				$("#frm_kcp input:hidden[name='rcvr_zipx']").val($("#receiverZipCd").val());
				$("#frm_kcp input:hidden[name='rcvr_add1']").val($("#receiverAddr").val());
				$("#frm_kcp input:hidden[name='rcvr_add2']").val($("#receiverAddrDetail").val());
			</c:when>
			<c:otherwise>	<%-- 배송지정보 - 있을때 --%>
				if($("#addressID1").hasClass("active")){	<%-- 배송지 목록 탭 활성화시 --%>
					if($("input:radio[name='selectAddressIdx']:checked").length == 0){	<%-- 선택된 배송지가 없을때 --%>
					alert("필수 항목을 선택해 주세요.(배송지)");
						$("input:radio[name='selectAddressIdx']:eq(0)").focus();
						return false;
					}else{
						if($("input:radio[name='selectAddressIdx']:checked").hasClass("editing")){	<%-- 선택된 배송지가 수정 중일때 --%>
						alert("배송지 수정중입니다.");
							$("input:radio[name='selectAddressIdx']:checked").focus();
							return false;
						}
					}
				}

				if($("#addressID1").hasClass("active")){	<%-- 배송지 목록 탭 활성화시 --%>

					<%-- 에스크로 배송지 정보 파라미터 --%>
					$("#frm_lguplus input:hidden[name='LGD_ESCROW_ZIPCODE']").val($("input:radio[name='selectAddressIdx']:checked").data("zipcd"));
					$("#frm_lgcyber input:hidden[name='LGD_ESCROW_ZIPCODE']").val($("input:radio[name='selectAddressIdx']:checked").data("zipcd"));
					$("#frm_lguplus input:hidden[name='LGD_ESCROW_ADDRESS1']").val($("input:radio[name='selectAddressIdx']:checked").data("addr"));
					$("#frm_lgcyber input:hidden[name='LGD_ESCROW_ADDRESS1']").val($("input:radio[name='selectAddressIdx']:checked").data("addr"));
					$("#frm_lguplus input:hidden[name='LGD_ESCROW_ADDRESS2']").val($("input:radio[name='selectAddressIdx']:checked").data("addrdetail"));
					$("#frm_lgcyber input:hidden[name='LGD_ESCROW_ADDRESS2']").val($("input:radio[name='selectAddressIdx']:checked").data("addrdetail"));
					$("#frm_lguplus input:hidden[name='LGD_ESCROW_BUYERPHONE']").val($("#senderPhoneNo").val());
					$("#frm_lgcyber input:hidden[name='LGD_ESCROW_BUYERPHONE']").val($("#senderPhoneNo").val());
	
					<%-- 에스크로 배송지 정보 파라미터 --%>
					$("#frm_kcp input:hidden[name='rcvr_name']").val($("input:radio[name='selectAddressIdx']:checked").data("receivernm"));
					$("#frm_kcp input:hidden[name='rcvr_tel1']").val($("input:radio[name='selectAddressIdx']:checked").data("telno"));
					$("#frm_kcp input:hidden[name='rcvr_tel2']").val($("input:radio[name='selectAddressIdx']:checked").data("phoneno"));
					$("#frm_kcp input:hidden[name='rcvr_mail']").val(senderemail);
					$("#frm_kcp input:hidden[name='rcvr_zipx']").val($("input:radio[name='selectAddressIdx']:checked").data("zipcd"));
					$("#frm_kcp input:hidden[name='rcvr_add1']").val($("input:radio[name='selectAddressIdx']:checked").data("addr"));
					$("#frm_kcp input:hidden[name='rcvr_add2']").val($("input:radio[name='selectAddressIdx']:checked").data("addrdetail"));
				}

				if($("#addressID2").hasClass("active")){	<%-- 새로입력 탭 활성화시 --%>
					<%-- 받는이 성명 --%>
					if($.trim($("#receiverNm").val()) == "" || $.trim($("#receiverNm").val()).length < 2){
						alert("받으시는 분 성함을 확인해 주세요. (2글자 이상 입력)");
						$("#receiverNm").focus();
						return false;
					}
		
					<%-- 배송지명 --%>
					/* if($("#addToAddress").is(":checked")){
						if($.trim($("#shippingNm").val()) == ""){
							alert("필수 항목을 입력해 주세요.(배송지명)");
							$("#shippingNm").focus();
							return false;
						}
					} */
		
					<%-- 받는이 휴대폰 --%>
					if($.trim($("#receiverPhoneNo").val()) == ""){
						alert("필수 항목을 입력해 주세요.(받으시는 분 휴대폰)");
						$("#receiverPhoneNo").focus();
						return false;
					}
		
					<%-- 받는이 우편번호 --%>
					if($.trim($("#receiverZipCd").val()) == ""){
						alert("필수 항목을 입력해 주세요.(받으시는 분 주소)");
						$("#receiverZipCd").focus();
						return false;
					}
		
					<%-- 받는이 주소 --%>
					if($.trim($("#receiverAddr").val()) == "" || $.trim($("#receiverAddrDetail").val()) == ""){
						alert("필수 항목을 입력해 주세요.(받으시는 분 주소)");
						$("#receiverAddrDetail").focus();
						return false;
					}

					<%-- 에스크로 배송지 정보 파라미터 --%>
					$("#frm_lguplus input:hidden[name='LGD_ESCROW_ZIPCODE']").val($("#receiverZipCd").val());
					$("#frm_lgcyber input:hidden[name='LGD_ESCROW_ZIPCODE']").val($("#receiverZipCd").val());
					$("#frm_lguplus input:hidden[name='LGD_ESCROW_ADDRESS1']").val($("#receiverAddr").val());
					$("#frm_lgcyber input:hidden[name='LGD_ESCROW_ADDRESS1']").val($("#receiverAddr").val());
					$("#frm_lguplus input:hidden[name='LGD_ESCROW_ADDRESS2']").val($("#receiverAddrDetail").val());
					$("#frm_lgcyber input:hidden[name='LGD_ESCROW_ADDRESS2']").val($("#receiverAddrDetail").val());
					$("#frm_lguplus input:hidden[name='LGD_ESCROW_BUYERPHONE']").val($("#senderPhoneNo").val());
					$("#frm_lgcyber input:hidden[name='LGD_ESCROW_BUYERPHONE']").val($("#senderPhoneNo").val());
					
					<%-- 에스크로 배송지 정보 파라미터 --%>
					$("#frm_kcp input:hidden[name='rcvr_name']").val($("#receiverNm").val());
					$("#frm_kcp input:hidden[name='rcvr_tel1']").val($("#receiverTelNo").val());
					$("#frm_kcp input:hidden[name='rcvr_tel2']").val($("#receiverPhoneNo").val());
					$("#frm_kcp input:hidden[name='rcvr_mail']").val(senderemail);
					$("#frm_kcp input:hidden[name='rcvr_zipx']").val($("#receiverZipCd").val());
					$("#frm_kcp input:hidden[name='rcvr_add1']").val($("#receiverAddr").val());
					$("#frm_kcp input:hidden[name='rcvr_add2']").val($("#receiverAddrDetail").val());
				}
			</c:otherwise>
		</c:choose>

		<%-- 배송시 요청사항이 직접입력이 아니면 직접입력 내용 초기화 --%>
		if($("#orderMemoVal").val() != "ORDER_MEMO90"){
			$("#orderMemo").val("");	
		}

		<c:if test="${priceGiftList ne null and fn:length(priceGiftList) gt 0}">	<%-- 구매 금액별 사은품 --%>
			if($("input:radio[name='selectPriceGiftIdx']:checked").length == 0){	<%-- 선택된 구매 금액별 사은품이 없을때 --%>
				alert("필수 항목을 입력해 주세요.(사은품)");
				return false;
			}	
		</c:if>

		<c:choose>
			<c:when test="${totalPayPrice eq 0}">	<%-- 포인트 결제 (결제금액 0원) --%>
				paymenttype = "POINT";
				$("#selectPayType").val("POINT");
			</c:when>
			<c:otherwise>
				<%-- 결제 방식 --%>
				paymenttype = $("#METHOD_TYPE").val();
				$("#selectPayType").val(paymenttype);	<%-- 선택한 결제 방식 --%>
			
				if(paymenttype != "BILLKEY" &&  
						paymenttype != "SC0010" &&  
						paymenttype != "SC0030" &&  
						paymenttype != "SC0040" &&  
						paymenttype != "SC0060" &&  
						paymenttype != "PAYCO" &&  
						paymenttype != "PAYNOW" &&  
						paymenttype != "SMILEPAY" &&  
						paymenttype != "WONDERPAY"){
					alert("필수 항목을 입력해 주세요.(결제 방식)");
					return false;
				}else{
					
					switch(paymenttype){
					<% 
					/* 원클릭결제
					case "BILLKEY" : 
									if($("#selectBillkeyVal").val() == ""){
										alert("필수 항목을 입력해 주세요.(나의 원클릭 결제 카드)");
										$("#selectBillkeyVal").focus();
										return false;
									}
									
									$("#billkeyIdx").val($("#selectBillkeyVal").val());
									$("#frm_billkeyauth input:hidden[name='LGD_PAN']").val($("#selectBillkeyVal option:selected").data("billkey"));
							break;

							*/
							%>									
						case "SC0010" : <%-- 신용카드 결제 --%>
								if($(".card-list li a.active").length == 0){
									/* alert("필수 항목을 입력해 주세요.(결제 카드)");
									return false; */
									var etcCardVal = $("#etcCardVal").val();
									if(etcCardVal == "" || etcCardVal == null){
										alert("결제 할 카드를 선택 해 주세요.");
										return false;
									}else{
										$("#LGD_USABLECARD").val(etcCardVal);
										$("#frm_kcp input:hidden[name='used_card']").val(etcCardVal);
									}
								}else{
									$("#LGD_USABLECARD").val($(".card-list li a.active").data("cardcode"));
									$("#frm_kcp input:hidden[name='used_card']").val($(".card-list li a.active").data("cardcode"));
									$("#selectCardCode").val($(".card-list li a.active").data("cardcode"));
								}
								break;
						case "SC0030" : <%-- 실시간 계좌 이체 --%>
									if($("input:radio[name='escrowYn1Val']:checked").length == 0){
										alert("필수 항목을 입력해 주세요.(에스크로 여부)");
										$("input:radio[name='escrowYn1Val']").focus();
										return false;
									}
									
	//								document.getElementById("LGD_ESCROW_USEYN", "frm_lguplus").value = $("input:radio[name='escrowYn1Val']:checked").val();
									$("#frm_lguplus input:hidden[name='LGD_ESCROW_USEYN']").val($("input:radio[name='escrowYn1Val']:checked").val());
									$("#frm_kcp input:hidden[name='escw_used']").val($("input:radio[name='escrowYn1Val']:checked").val());
									$("#frm_kcp input:hidden[name='pay_mod']").val($("input:radio[name='escrowYn1Val']:checked").val());
									
								break;
						case "SC0040" : <%-- 가상계좌 입금 --%>
									if($("#bankCode").val() == ""){
										alert("필수 항목을 입력해 주세요.(입금은행)");
										$("#bankCode").focus();
										return false;
									}
									
	//								document.getElementById("LGD_BANKCODE", "frm_lgcyber").value = $("#bankCode").val();	//은행
									$("#frm_lgcyber input:hidden[name='LGD_BANKCODE']").val($("#bankCode").val());	//은행
									if($("input:radio[name='escrowYn2Val']:checked").length == 0){
										alert("필수 항목을 입력해 주세요.(에스크로 여부)");
										$("input:radio[name='escrowYn2Val']").focus();
										return false;
									}
/*									
	//								document.getElementById("LGD_ESCROW_USEYN", "frm_lgcyber").value = $("input:radio[name='escrowYn2Val']:checked").val();	//에스크로여부
									$("#frm_lgcyber input:hidden[name='LGD_ESCROW_USEYN']").val($("input:radio[name='escrowYn2Val']:checked").val());	//에스크로여부
									
	//								document.getElementById("LGD_CASHRECEIPTUSE","frm_lgcyber").value = $("input:radio[name='cashReceiptGubun']:checked").val();  //현금영수증
   //								$("#frm_lgcyber input:hidden[name='LGD_CASHRECEIPTUSE']").val($("input:radio[name='cashReceiptGubun']:checked").val());	//현금영수증
									
									if($("input:radio[name='cashReceiptGubun']:checked").val() != "0" && $.trim($("#cashReceiptNo").val()) == ""){
										alert("필수 항목을 입력해 주세요.(현금영수증 번호)");
										$("#cashReceiptNo").focus();
										return false;
									}
*/								
	//								document.getElementById("LGD_CASHCARDNUM","frm_lgcyber").value = $("#cashReceiptNo").val();  //현금영수증번호
//									$("#frm_lgcyber input:hidden[name='LGD_CASHCARDNUM']").val($("#cashReceiptNo").val());	//현금영수증번호
									$("#frm_kcp input:hidden[name='escw_used']").val($("input:radio[name='escrowYn2Val']:checked").val());	//에스크로여부
									$("#frm_kcp input:hidden[name='pay_mod']").val($("input:radio[name='escrowYn2Val']:checked").val());
									$("#frm_kcp input:hidden[name='used_bank']").val("BK"+$("#bankCode").val());	//은행코드 (모바일일경우 BK를 붙임)
									$("#frm_kcp input:hidden[name='disp_tax_yn']").val("Y");	//현금영수증
								break;
						case "SC0060" : <%-- 휴대폰 결제 --%>
								break;
						case "PAYCO" : <%-- PAYCO --%>
								break;
		<%--
						case 6 : <!-- KAKAOPAY -->
								break;
						case 7 : <!-- NAVERPAY -->
								break;
			--%>		
						case "WONDERPAY" :
								break;
						
						case "SMILEPAY" : <%-- SMILEPAY --%>
								break;
					}
				}
			</c:otherwise>
		</c:choose>

		<%-- 구매 동의 --%>
		if(!$("#agreement").is(":checked")){
			alert("필수 항목을 입력해 주세요.(구매진행 동의)");
			$("#agreement").focus();
			return false;
		}

		<%-- LG U+ 구매자 정보 세팅 --%>
/*
		document.getElementById("LGD_BUYER", "frm_lguplus").value = $("#senderNm").val();
		document.getElementById("LGD_BUYER", "frm_lgcyber").value = $("#senderNm").val();
		document.getElementById("LGD_BUYER", "frm_billkeyauth").value = $("#senderNm").val();
		document.getElementById("LGD_BUYEREMAIL", "frm_lguplus").value = $("#senderEmail").val();
		document.getElementById("LGD_BUYEREMAIL", "frm_lgcyber").value = $("#senderEmail").val();
		document.getElementById("LGD_BUYEREMAIL", "frm_billkeyauth").value = $("#senderEmail").val();
		document.getElementById("LGD_BUYERPHONE", "frm_lguplus").value = $("#senderPhoneNo").val();
		document.getElementById("LGD_BUYERPHONE", "frm_lgcyber").value = $("#senderPhoneNo").val();
		document.getElementById("LGD_BUYERPHONE", "frm_billkeyauth").value = $("#senderPhoneNo").val();		
*/
		$("#frm_lguplus input:hidden[name='LGD_ACCOUNTOWNER']").val($("#senderNm").val());
		$("#frm_lgcyber input:hidden[name='LGD_ACCOUNTOWNER']").val($("#senderNm").val());
		$("#frm_billkeyauth input:hidden[name='LGD_ACCOUNTOWNER']").val($("#senderNm").val());
		$("#frm_lguplus input:hidden[name='LGD_BUYER']").val($("#senderNm").val());
		$("#frm_lgcyber input:hidden[name='LGD_BUYER']").val($("#senderNm").val());
		$("#frm_billkeyauth input:hidden[name='LGD_BUYER']").val($("#senderNm").val());
		$("#frm_lguplus input:hidden[name='LGD_BUYEREMAIL']").val($("#senderEmail").val());
		$("#frm_lgcyber input:hidden[name='LGD_BUYEREMAIL']").val($("#senderEmail").val());
		$("#frm_billkeyauth input:hidden[name='LGD_BUYEREMAIL']").val($("#senderEmail").val());
		$("#frm_lguplus input:hidden[name='LGD_BUYERPHONE']").val($("#senderPhoneNo").val());
		$("#frm_lgcyber input:hidden[name='LGD_BUYERPHONE']").val($("#senderPhoneNo").val());
		$("#frm_billkeyauth input:hidden[name='LGD_BUYERPHONE']").val($("#senderPhoneNo").val());

		<%-- KCP 구매자 정보 --%>
		$("#frm_kcp input:hidden[name='buyr_name']").val($("#senderNm").val());
		$("#frm_kcp input:hidden[name='buyr_mail']").val($("#senderEmail").val());
		$("#frm_kcp input:hidden[name='buyr_tel2']").val($("#senderPhoneNo").val());
		
		<%-- 선택한 사은품 탭 --%>
		$("#giftTabId").val($("#giftTab button.active").attr("data-tab-id"));
		
		<%-- 주문자 정보 DB 저장 & 유효성 체크 --%>
		$("#sessionId").val(getSessionId());
		
		<%-- 버튼 연타 방지 --%>		
		if(paymenttype == "POINT" || paymenttype == "PAYNOW" || paymenttype == "SC0010" || paymenttype == "SC0030" || paymenttype == "SC0060" || paymenttype == "SC0040"){	<%-- 포인트 결제, 페이나우, 신용카드, 실시간계좌, 휴대폰, 가상계좌일때만 버튼 연타 방지 처리 --%>
			if(didSubmit == true){
				return false;
			}
			
			didSubmit = true;
		}
		<%-- 버튼 연타 방지 끝 --%>

		$.ajax({			
			url: getContextPath()+"/ajax/order/saveOrderInfoValidationAjax.do",
			data: $("#orderForm").serialize(),
		 	type: "post",
		 	async: false,
		 	cache: false,
		 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data){
				if(data.result == true){
<%--
				<c:if test="${priceGiftList ne null and fn:length(priceGiftList) gt 0}">	/* 구매 금액별 사은품 */
					if($("input:radio[name='selectPriceGiftIdx']:checked").length == 0){	/* 선택된 구매 금액별 사은품이 없을때 */
						if(!confirm("사은품을 선택하지 않으셨습니다.\n구매를 진행하시겠어요?")){
							didSubmit = false;	/* 구매 버튼 연타 방지 초기화 */
							return false;
						}
					}	
				</c:if>
--%>
					if("<spring:message code='server.status'/>" == "LIVE"){
						// Begin Google Tag Manager
						var paymenttypeNm = "";
						if(paymenttype == "SC0010"){
							paymenttypeNm = '신용카드';
						}else if(paymenttype == "SC0030"){
							paymenttypeNm = '실시간계좌';
						}else if(paymenttype == "SC0060" ){
							paymenttypeNm = '휴대폰';
						}else if(paymenttype == "SC0040"){
							paymenttypeNm = '가상계좌';
						}else{
							paymenttypeNm = paymenttype;						
						}						
						
						var arrGtm = new Array();	// Google Tag Manager
						
						<c:forEach var="list" items="${list}" varStatus="idx">
							var goodsPrice = Number(uncomma('<fmt:formatNumber value="${list.discountPrice}" groupingUsed="false"/>'));
							var orderCnt = Number(uncomma('<fmt:formatNumber value="${list.orderCnt}" groupingUsed="false"/>'));
							
							var objGtm = {
							          'name': '<c:out value="${list.goodsNm}"/>',
							          'id': '<c:out value="${list.goodsCd}"/>',
							          'price': goodsPrice,
							          'brand': 'Gatsby',
							          'category': '',
							          'variant': '',
							          'quantity': orderCnt
							       };
							
							arrGtm.push(objGtm);
						</c:forEach>
						
						  dataLayer.push({
						    'event': 'checkout',
						    'ecommerce': {
						      'checkout': {
						        'actionField': {'step': 1, 'option': paymenttypeNm},
						        'products': arrGtm
						     }
						   },
						   'eventCallback': function() {
	
						   }
						  });	
						// End Google Tag Manager
					}
				
					<%-- 페이나우, 신용카드, 실시간계좌, 휴대폰 --%>
					if(paymenttype == "PAYNOW" || paymenttype == "SC0010" || paymenttype == "SC0030" || paymenttype == "SC0060" ){
						//xpayOpenWindow(paymenttype);
						kcpOpenWindow(paymenttype);
					}else if(paymenttype == "SC0040"){		<%-- 가상계좌 --%> 
						//lgcyberAccountConfirm();
						kcpOpenWindow(paymenttype);
					}else if(paymenttype == "BILLKEY"){	<%-- 빌키결제 --%> 
						//billkeyConfirmPop() ;
					}else if(paymenttype == "PAYCO"){ 		<%-- PAYCO --%>
						paycoOpenLayer();
					}else if(paymenttype == "POINT"){ 		<%-- 포인트 결제 (결제금액 0원) --%>
						var frm = document.orderForm;
						frm.action="${CTX}/order/pointPayment.do";
						frm.submit();
					}else if(paymenttype == "SMILEPAY"){
						smilepayOpenLayer();
					}else if(paymenttype == "WONDERPAY"){
						wonderpayOpenLayer();
					}

				}else{
					if(data.msg != ""){
						if(data.errorCode == "100"){		<%-- 주문 정보 없을때는 페이지 새로고침 시킴 --%>
							alert(data.msg);
							location.reload();
						}else{
							alert(data.msg);
						}
						
						didSubmit = false;	<%-- 구매 버튼 연타 방지 초기화 --%>
						
						return false;
					}
				}
			 }
		});
	}
	
	
	/*
	* FORM 명만  수정 가능
	*/
	 function getFormObject() {
	        return document.getElementById("frm_lguplus");
	}
	
	/*
	 * 인증결과 처리
	 */
	function payment_return() {
				
		didSubmit = false;	<%-- 구매 버튼 연타 방지 초기화 --%>

		var fDoc;
		
		fDoc = lgdwin.contentWindow || lgdwin.contentDocument;
			
		if (fDoc.document.getElementById('LGD_RESPCODE').value == "0000") {
			
				document.getElementById("LGD_PAYKEY").value = fDoc.document.getElementById('LGD_PAYKEY').value;
				document.getElementById("frm_lguplus").target = "_self";
				document.getElementById("frm_lguplus").action = "${CTX}/order/payres.do";
				document.getElementById("frm_lguplus").submit();
		} else {
			alert("LGD_RESPCODE (결과코드) : " + fDoc.document.getElementById('LGD_RESPCODE').value + "\n" + "LGD_RESPMSG (결과메시지): " + fDoc.document.getElementById('LGD_RESPMSG').value);
			closeIframe();
		}
	}
	
	//kcp 결제창 오픈 (신용카드10, 실시간계좌이체30, 가상계좌40, 휴대폰60)
	//paymenttype == "SC0010" || paymenttype == "SC0030" || paymenttype == "SC0060"
	function kcpOpenWindow(paymenttype) {
		var form = document.frm_kcp;    	 
         try
         {
        	 if (paymenttype == "SC0010") {
        		 $("#frm_kcp input:hidden[name='pay_method']").val("CARD");
        		 $("#frm_kcp input:hidden[name='ActionResult']").val("card");
        	 }
        	 else if (paymenttype == "SC0030") {
        		 $("#frm_kcp input:hidden[name='pay_method']").val("BANK"); 
        		 $("#frm_kcp input:hidden[name='ActionResult']").val("bank");
        	 }
        	 else if (paymenttype == "SC0040") {
        		 $("#frm_kcp input:hidden[name='pay_method']").val("VCNT"); 
        		 $("#frm_kcp input:hidden[name='ActionResult']").val("vcnt");
        	 }
        	 else if  (paymenttype == "SC0060") {
        		 $("#frm_kcp input:hidden[name='pay_method']").val("MOBX"); 
        		 $("#frm_kcp input:hidden[name='ActionResult']").val("mobx");
        	 }
        	 kcp_AJAX();
         }
         catch (e)
         {
             /* IE 에서 결제 정상종료시 throw로 스크립트 종료 */ 
         }
     }             
	
	//LGU+ 결제창 오픈
	function xpayOpenWindow(paymenttype) {
		var LGD_window_type = '${reqMap.LGD_WINDOW_TYPE}';
		var frameX	= "";
		var frameY	= "";
		
		if (paymenttype == "PAYNOW" ) {
			frameX	= "600";
			frameY	= "400";
			
//			document.getElementById("LGD_CUSTOM_USABLEPAY", "frm_lguplus").value = "SC0010";
//			document.getElementById("LGD_EASYPAY_ONLY", "frm_lguplus").value = "PAYNOW";
			$("#frm_lguplus input:hidden[name='LGD_CUSTOM_USABLEPAY']").val("SC0010");
			$("#frm_lguplus input:hidden[name='LGD_EASYPAY_ONLY']").val("PAYNOW");

//			lgdwin = openXpay(document.getElementById('frm_lguplus'), '${reqMap.CST_PLATFORM}', LGD_window_type, null , frameX, frameY);
			lgdwin = open_paymentwindow(document.getElementById('frm_lguplus'), '${reqMap.CST_PLATFORM}', LGD_window_type);
		}
		else
		{
//			document.getElementById("LGD_CUSTOM_USABLEPAY","frm_lguplus").value = paymenttype;
//			document.getElementById("LGD_EASYPAY_ONLY", "frm_lguplus").value = "";
			$("#frm_lguplus input:hidden[name='LGD_CUSTOM_USABLEPAY']").val(paymenttype);
			$("#frm_lguplus input:hidden[name='LGD_EASYPAY_ONLY']").val("");

			lgdwin = open_paymentwindow(document.getElementById('frm_lguplus'), '${reqMap.CST_PLATFORM}', LGD_window_type);
		}
	}



	//가상계좌 결제승인
	function lgcyberAccountConfirm() {
		//입력체크
<%--
		// 값 세팅
		document.getElementById("LGD_BANKCODE","frm_lgcyber").value = document.getElementById("cyberbank").value;  //은행
		document.getElementById("LGD_ESCROW_USEYN","frm_lgcyber").value = document.getElementById("escrowYn").value;  //에스크로여부
		document.getElementById("LGD_CASHRECEIPTUSE","frm_lgcyber").value = document.getElementById("cashreceiptuse").value;  //현금영수증
		document.getElementById("LGD_CASHCARDNUM","frm_lgcyber").value = document.getElementById("cashcardnum").value;  //현금영수증번호
--%>
		$("#loadingImg").show();	<%-- 로딩이미지 표시 --%>
		
		document.getElementById("frm_lgcyber").target = "_self";
		document.getElementById("frm_lgcyber").action = "${CTX}/order/cyberpayres.do";
		document.getElementById("frm_lgcyber").submit();
	}
	
	//빌키등록 LGU+창 오픈
	function insertOneClickCard() {
		
		if($.trim($("#insertCardForm input:text[name='cardNm']").val()) == ""){
			alert("카드명을 입력해주세요.");
			$("#insertCardForm input:text[name='cardNm']").focus();
			return false;
		}
		
		var LGD_window_type = '${billkeyRegMap.LGD_WINDOW_TYPE}';
		var CST_PLATFORM = '${billkeyRegMap.CST_PLATFORM}';

		lgdwin = openXpay(document.getElementById('frm_billkeyreg'), CST_PLATFORM, LGD_window_type, null, "", "");
	}
	
	//빌키등록결과 
	function billkey_reg_return() {
		
		didSubmit = false;	<%-- 구매 버튼 연타 방지 초기화 --%>
		
		var fDoc;
		
		fDoc = lgdwin.contentWindow || lgdwin.contentDocument;
			
		if (fDoc.document.getElementById('LGD_RESPCODE').value == "0000") {
/*			
				document.getElementById("LGD_RESPCODE", "frm_billkeyreg").value = fDoc.document.getElementById('LGD_RESPCODE').value;
				document.getElementById("LGD_RESPMSG", "frm_billkeyreg").value = fDoc.document.getElementById('LGD_RESPMSG').value;
				document.getElementById("LGD_BILLKEY", "frm_billkeyreg").value = fDoc.document.getElementById('LGD_BILLKEY').value;
				document.getElementById("LGD_PAYTYPE", "frm_billkeyreg").value = fDoc.document.getElementById('LGD_PAYTYPE').value;
				document.getElementById("LGD_PAYDATE", "frm_billkeyreg").value = fDoc.document.getElementById('LGD_PAYDATE').value;
				document.getElementById("LGD_FINANCECODE", "frm_billkeyreg").value = fDoc.document.getElementById('LGD_FINANCECODE').value;
				document.getElementById("LGD_FINANCENAME", "frm_billkeyreg").value = fDoc.document.getElementById('LGD_FINANCENAME').value;
				document.getElementById("LGD_CARDINSTALLMONTH", "frm_billkeyreg").value = fDoc.document.getElementById('LGD_CARDINSTALLMONTH').value;
				document.getElementById("LGD_BUYERSSN", "frm_billkeyreg").value = fDoc.document.getElementById('LGD_BUYERSSN').value;
				document.getElementById("LGD_CARDNUM", "frm_billkeyreg").value = fDoc.document.getElementById('LGD_CARDNUM').value;
				document.getElementById("LGD_EXPMON", "frm_billkeyreg").value = fDoc.document.getElementById('LGD_EXPMON').value;
				document.getElementById("LGD_EXPYEAR", "frm_billkeyreg").value = fDoc.document.getElementById('LGD_EXPYEAR').value;
				document.getElementById("LGD_CARDNOINTYN", "frm_billkeyreg").value = fDoc.document.getElementById('LGD_CARDNOINTYN').value;
				document.getElementById("LGD_TID", "frm_billkeyreg").value = fDoc.document.getElementById('LGD_TID').value;
				document.getElementById("LGD_VANCODE", "frm_billkeyreg").value = fDoc.document.getElementById('LGD_VANCODE').value;
*/
				$("#frm_billkeyreg input:hidden[name='LGD_RESPCODE']").val(fDoc.document.getElementById('LGD_RESPCODE').value);
				$("#frm_billkeyreg input:hidden[name='LGD_RESPMSG']").val(fDoc.document.getElementById('LGD_RESPMSG').value);
				$("#frm_billkeyreg input:hidden[name='LGD_BILLKEY']").val(fDoc.document.getElementById('LGD_BILLKEY').value);
				$("#frm_billkeyreg input:hidden[name='LGD_PAYTYPE']").val(fDoc.document.getElementById('LGD_PAYTYPE').value);
				$("#frm_billkeyreg input:hidden[name='LGD_PAYDATE']").val(fDoc.document.getElementById('LGD_PAYDATE').value);
				$("#frm_billkeyreg input:hidden[name='LGD_FINANCECODE']").val(fDoc.document.getElementById('LGD_FINANCECODE').value);
				$("#frm_billkeyreg input:hidden[name='LGD_FINANCENAME']").val(fDoc.document.getElementById('LGD_FINANCENAME').value);
				$("#frm_billkeyreg input:hidden[name='LGD_CARDINSTALLMONTH']").val(fDoc.document.getElementById('LGD_CARDINSTALLMONTH').value);
				$("#frm_billkeyreg input:hidden[name='LGD_BUYERSSN']").val(fDoc.document.getElementById('LGD_BUYERSSN').value);
				$("#frm_billkeyreg input:hidden[name='LGD_CARDNUM']").val(fDoc.document.getElementById('LGD_CARDNUM').value);
				$("#frm_billkeyreg input:hidden[name='LGD_EXPMON']").val(fDoc.document.getElementById('LGD_EXPMON').value);
				$("#frm_billkeyreg input:hidden[name='LGD_EXPYEAR']").val(fDoc.document.getElementById('LGD_EXPYEAR').value);
				$("#frm_billkeyreg input:hidden[name='LGD_CARDNOINTYN']").val(fDoc.document.getElementById('LGD_CARDNOINTYN').value);
				$("#frm_billkeyreg input:hidden[name='LGD_TID']").val(fDoc.document.getElementById('LGD_TID').value);
				$("#frm_billkeyreg input:hidden[name='LGD_VANCODE']").val(fDoc.document.getElementById('LGD_VANCODE').value);

				//카드번호
				$("#frm_billkeyreg input:hidden[name='CARD_NM']").val($("#insertCardForm input:text[name='cardNm']").val());
				if ($("#insertCardForm input:radio[name='mainYn']").is(":checked")) {
					$("#frm_billkeyreg input:hidden[name='MAIN_YN']").val("Y");
				} else {
					$("#frm_billkeyreg input:hidden[name='MAIN_YN']").val("N");
				}
				
				$.ajax({			
					url: getContextPath()+"/ajax/order/BillKeyRegAjax.do",
					data: $("#frm_billkeyreg").serialize(),
				 	type: "post",
				 	async: false,
				 	cache: false,
				 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
				 	error: function(request, status, error){ 
						closeIframe();
				 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
					},
					success: function(data){
						closeIframe();
						if(data.result == true){
							alert("원클릭 카드를 등록완료하였습니다.");
							location.reload();
						}else{
							if(data.msg != "" && data.msg !=null){
								alert(data.msg);
							}
						}
					 }
				});
		} else {
			alert("LGD_RESPCODE (결과코드) : " + fDoc.document.getElementById('LGD_RESPCODE').value + "\n" + "LGD_RESPMSG (결과메시지): " + fDoc.document.getElementById('LGD_RESPMSG').value);
			closeIframe();
		}
	}


	
	function billkeyConfirm() {
		
		<%-- 버튼 연타 방지 --%>
		if(didSubmit == true){
			return false;
		}
		
		didSubmit = true;
		<%-- 버튼 연타 방지 끝 --%>

		if($('#billkeypopup_agree_chk').is(":checked")) {
			document.getElementById("frm_billkeyauth").target = "_self";
			document.getElementById("frm_billkeyauth").action = "${CTX}/order/billkeyPayRes.do";
			document.getElementById("frm_billkeyauth").submit();
		}
		else {
			alert("구매진행에 동의 하여야 합니다.");
			$('#billkeypopup_agree_chk').focus()
			return false;
		}
	}
	

	function paycoOpenLayer() {
		
	    // localhost 로 테스트 시 크로스 도메인 문제로 발생하는 오류 
	    $.support.cors = true;

		/* + "&" + $('order_product_delivery_info').serialize() ); */
		$.ajax({
			type: "POST",
			url: getContextPath()+"/ajax/order/paycoReserveAjax.do",
			data: $("#orderForm").serialize(),
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			dataType:"json",
			success:function(data){
				if(data.code == '0') {
					//console.log(data.result.reserveOrderNo);
				//	alert("주문예약 성공! \n결제를 진행해 주세요!");
				//	$('#order_num').val(data.result.reserveOrderNo);
				//	$('#order_url').val(data.result.orderSheetUrl);
				//	$('#order_sellerOrderReferenceKey').val(customerOrderNumber);
					paycoCall (data);
				}else{
					alert("code : " + data.code + "\n" + "message : " + data.message);
				}
			},
	        error: function(request,status,error) {
	            //에러코드
	            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				return false;
	        }
		});
	}
	
	  
	 function paycoCall (vo)
	 {
		if(vo.code == "0"){
			var orderurl = vo.result.orderSheetUrl;
			location.href=orderurl;
//			$("#paycoFrame").attr("src",orderurl);
//			$("#payco_layer").css("display","block");
			
		}else{
			alert(vo.message);
			return;
		}
	}
	 

	 function payco_payment_return(reserveOrderNo, sellerOrderReferenceKey, paymentCertifyToken, totalPaymentAmt, code, message) {
	 	
	   	if (code == "0"){
	   		$("#frm_payco input:hidden[name='code']").val(code);
	   		$("#frm_payco input:hidden[name='message']").val(message);
	   		$("#frm_payco input:hidden[name='reserveOrderNo']").val(reserveOrderNo);
	   		$("#frm_payco input:hidden[name='sellerOrderReferenceKey']").val(sellerOrderReferenceKey);
	   		$("#frm_payco input:hidden[name='paymentCertifyToken']").val(paymentCertifyToken);
	   		$("#frm_payco input:hidden[name='totalPaymentAmt']").val(totalPaymentAmt);
	   		$("#frm_payco input:hidden[name='sessionId']").val(getSessionId());	   		
	   		$("form[name='frm_payco']").attr("action", "${CTX}/order/paycoRes.do");
	   		$("form[name='frm_payco']").submit();
	   			    
	   	}else{
//	   		alert("PAYCO 에러코드:"+code);	
	   	  	$("#payco_layer").css("display","none");
	   	}
	 }

	 <%-- 원더페이 결제창 오픈--%>
	 function wonderpayOpenLayer(){
		    // localhost 로 테스트 시 크로스 도메인 문제로 발생하는 오류 
		    $.support.cors = true;

		    //원더페이 order_info 다시구하기
		    $.ajax({
				type: "POST",
				url: getContextPath()+"/ajax/order/wonderOrderInfoReAjax.do",
				data: $("#orderForm").serialize(),
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				dataType:"json",
				success:function(data){
					if(data.siteCd == '0000') {
						$("#frm_wonderpay input:hidden[name='buyr_name']").val(data.buyrName);
						$("#frm_wonderpay input:hidden[name='buyr_mail']").val(data.buyrMail);
						$("#frm_wonderpay input:hidden[name='buyr_tel2']").val(data.buyrTel2);
						$("#frm_wonderpay input:hidden[name='ordr_info']").val(data.ordrInfo);
				   		$("form[name='frm_wonderpay']").submit();
					}else{
						alert("code : " + data.siteCd+ "\n" + "message : " + data.retUrl);
					}
				},
		        error: function(request,status,error) {
		            //에러코드
		            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					return false;
		        }
			});
			
		}
		
	 
	 <%-- SMILEPAY 결제창 오픈--%>
	 function smilepayOpenLayer(){
		 
		 $.ajax({
			type: "POST",
			url: getContextPath()+"/ajax/order/smilepayTxnidAjax.do",
			data: $("#smilepayForm").serialize(),
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			dataType:"json",
			success:function(data){
				var result = data.result;
								
				if(result.resultCode =="00"){
					$("#resultCode").val(result.resultCode);
					$("#resultMsg").val(result.resultMsg);
					$("#txnId").val(result.txnId);
					$("#prDt").val(result.prDt);
					
					//XD.receiveMessage( smilepay_callback, "${smilepay.webPath}" );
					
		        	//cnspay_L.callPage("smilePay_layer", eval("${smilepay.usePopup}"), smilepay_callback);
		        	smilepay_L.callPopup(result.txnId, smilepay_callback);
		        	//$("#smilePay_layer_div").show();
				}else{
					alert(result.resultCode+"  //" +result.resultMsg);
				}   
			},
	        error: function(request,status,error) {
	            //에러코드
	            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				return false;
	        }
		});
	 }
	 
	 function validResult(message){
			if(message == null || message == ""){
				return;
			}else{
				var data = JSON.parse(message);
				if(data == null){
					return;
				}else{
					//0001은 CNS 내부 처리 실패, 0002는 고객이 스마일페이 결제창에서 닫기 버튼을 누를 경우 
					if(data.resultCode == '0001' || data.resultCode == '0002'){
						return;
					}else{
						if(data.SPU != null && data.SPU_SIGN_TOKEN != null){
							return true;
						}else{
							return;
						}
					}
				}
			}
		}
	 
	 <%-- SMILEPAY CALLBACK 함수 --%>
	 function smilepay_callback(message){
			smilepay_L.destroy();
			
			if(validResult(message)){		 
				var obj = JSON.parse(message);
					$("#smilepayForm input:hidden[name='SPU']").val(obj.SPU);
			   		$("#smilepayForm input:hidden[name='SPU_SIGN_TOKEN']").val(obj.SPU_SIGN_TOKEN);
			   		$("#smilepayForm input:hidden[name='ETC1']").val(obj.ETC1);
			   		$("#smilepayForm input:hidden[name='ETC2']").val(obj.ETC2);
			   		$("#smilepayForm input:hidden[name='ETC3']").val(obj.ETC3);
			   		$("#smilepayForm input:hidden[name='sessionId']").val(getSessionId());	   		
			   		$("#smilepayForm input:hidden[name='merchantTxnNum']").val("${VO.orderCd}");  //실주문번호정보로 변경해줌.
			   		
			   		var frm = document.smilepayForm;
			   		frm.target = "";
			   		frm.action = "${CTX}/order/smilepayRes.do";
			   	//	frm.acceptCharset = "utf-8";
					
				//	if(frm.canHaveHTML){ // detect IE
				//		document.charset = frm.acceptCharset;
				//	}

			   		frm.submit();
			}else{
				//$("#smilePay_layer_div").hide();
				alert("결제가 취소되었습니다.");
			}   	
		 }
	 
	 // 결제 구분 클릭
	 function btnEvent(val){
		 if(val == 5){
			$(".smileBox").show();
		}else{
			$(".smileBox").hide();
		}
		 
		 if(val == 6){
			$(".paycoBox").show();
		}else{
			$(".paycoBox").hide();
		}
		
		if(val == 7){
			$(".wonderpayBox").show();
		}else{
			$(".wonderpayBox").hide();
		}
	 }
</script>
</head>
<body>
	<form name="smilepayForm" id="smilepayForm" method="post" onsubmit="return false;">
		<%-- smilepay 인증 반환값 --%>
		<input type="hidden" name="resultCode" id="resultCode" />
		<input type="hidden" name="resultMsg" id="resultMsg" />
		<input type="hidden" name="txnId" id="txnId" />
		<input type="hidden" name="prDt" id="prDt" />
		
		<%-- 결제 모듈 반환값 --%>
		<input type="hidden" name="SPU"/>			
		<input type="hidden" name="SPU_SIGN_TOKEN"/>
		<input type="hidden" name="ETC1"/>
		<input type="hidden" name="ETC2"/>
		<input type="hidden" name="ETC3"/>
		<input type="hidden" name="sessionId"/>
		
		<%-- smilepay 인증 파라미터--%>	
		<input type="hidden" name="PayMethod" value="${smilepay.payMethod}"/>
		<input type="hidden" name="GoodsName" value="${orderInfo.productInfo}"/> 
		<input type="hidden" name="Amt" value="${smilepay.totalPayPrice}"/>
		<input type="hidden" name="GoodsCnt" value="${smilepay.goodsCnt}"/>
		<input type="hidden" name="MID" value="${smilepay.mid}"/>
		<input type="hidden" name="AuthFlg" value="${smilepay.authFlg}"/>
		<input type="hidden" name="EdiDate" value="${smilepay.ediDate}"/>
		<input type="hidden" name="EncryptData" value="${smilepay.hashString}"/>   
		<input type="hidden" name="merchantTxnNum" value="${smilepay.merchantTxnNum}"/>
		<input type="hidden" name="BuyerName" value="${smilepay.buyerName}"/>
		<input type="hidden" name="certifiedFlag" value="${smilepay.certifiedFlag}"/>
		<input type="hidden" name="currency" value="${smilepay.currency}"/>
		<input type="hidden" name="merchantEncKey" value="${smilepay.merchantEncKey}"/>
		<input type="hidden" name="merchantHashKey" value="${smilepay.merchantHasKey}"/>
		<input type="hidden" name="requestDealApproveUrl" value="${smilepay.webPath}${smilepay.msgName}"/>
		<input type="hidden" name="prType" value="${smilepay.prType}"/>
		<input type="hidden" name="channelType" value="${smilepay.channelType}"/>
		<input type="hidden" name="UsePopup" value="${smilepay.usePopup}"/>
		<input type="hidden" name="returnUrl2" id="returnUrl2" value="${smilepay.returnUrl2}"/>
	</form> 
	<form name="orderForm" id="orderForm" method="post" onsubmit="return false;">
		<input type="hidden" name="orderCd" id="orderCd" value="${VO.orderCd}"/>
		<input type="hidden" name="sessionId" id="sessionId" value=""/>
		<input type="hidden" name="senderEmail" id="senderEmail" value=""/>
		<input type="hidden" name="receiverOldZipCd" id="receiverOldZipCd" value=""/>
		<input type="hidden" name="receiverOldAddr" id="receiverOldAddr" value=""/>
		<input type="hidden" name="addressTabId" id="addressTabId" value=""/>							<%-- 선택한 배송지 탭 ID (상태유지용 필드, addressID1 : 배송지 목록 탭, addressID2 : 새로 입력 탭) --%>
		<input type="hidden" name="giftTabId" id="giftTabId" value=""/>										<%-- 선택한 사은품 탭 ID (상태유지용 필드, allGift : 무료 사은품 탭, over4Gift…) --%>
		<input type="hidden" name="selectPayType" id="selectPayType" value=""/>						<%-- 선택한 결제 수단 (상태유지용 필드, BILLKEY : 원클릭 결제, SC0010 : 신용카드) --%>
		<input type="hidden" name="selectCardCode" id="selectCardCode" value=""/>					<%-- 선택한 카드 코드 (상태유지용 필드) --%>
		<input type="hidden" name="billkeyIdx" id="billkeyIdx" value="${billkeyInfo.billkeyIdx}"/>	<%-- 대표 빌키 --%>
		<input type="hidden" name="freeShippingCouponIdx" id="freeShippingCouponIdx" value="${orderInfo.freeShippingCouponIdx}"/>
		<input type="hidden" name="cartCouponIdx" id="cartCouponIdx" value="${orderInfo.cartCouponIdx}"/>

        <div class="content comm-order order-payment">

			<div class="order-top">
				<div class="order-steps step2">
					<ol>
						<li>
							<span>STEP 01</span>
							<strong>장바구니</strong>
						</li>
						<li class="active">
							<span class="hide">현재단계</span>
							<span>STEP 02</span>
							<strong>주문결제</strong>
						</li>
						<li>
							<span>STEP 03</span>
							<strong>주문완료</strong>
						</li>
					</ol>
				</div>
			</div>

            <div class="page-body">

                <!-- 주문하신 분 -->
                <div class="order-form">
                    <div class="form-group">
                    	<div class="form-title">
	                        <h3 class="title">주문하신 분</h3>
	                        <button type="button" class="btn-toggle active" data-toggle="collapse" data-target="#formUser" ><span class="hide">접기</span></button>
	                    </div>
	                    <div id="formUser" class="form-body">
	                        <div class="row">
	                            <div class="col col-12">
	                                <div class="form-control optgroup">
	                                    <span class="radiobox">
	                                        <input type="radio" id="custInfoClone" class="radio" name="sameAsOrderInfo" value="Y" onclick="setSenderInfo(0);" <c:if test="${orderInfo.sameAsOrderInfo eq 'Y'}">checked</c:if>/>
	                                        <label for="custInfoClone" class="lbl">회원정보와 동일</label>
	                                    </span>
	                                    <span class="radiobox">
	                                        <input type="radio" id="custInfoNew" class="radio" name="sameAsOrderInfo" value="N" onclick="setSenderInfo(1);" <c:if test="${orderInfo.sameAsOrderInfo eq 'N'}">checked</c:if>/>
	                                        <label for="custInfoNew" class="lbl">새로입력</label>
	                                    </span>
	                                </div>
	                            </div>
	                        </div>
	                        <div class="row">
	                            <div class="col col-12">
	                                <div class="form-control">
	                                    <input type="text" class="input" name="senderNm" id="senderNm" value="<c:out value="${orderInfo.senderNm}"/>" maxlength="50" placeholder="주문자 성명" required/>
	                                </div>
	                            </div>
	                        </div>
	                        <div class="row">
	                            <div class="col col-7">
	                                <div class="form-control">
	                                    <input type="text" class="input" name="email1" id="email1" value="<c:out value="${fn:split(orderInfo.senderEmail,'@')[0]}"/>" maxlength="50" placeholder="이메일" required/>
	                                </div>
	                            </div>
	                            <div class="col col-5">
	                                <div class="form-control sym-at">
	                                    <div class="opt_select">
	                                        <select name="emailGubun" id="emailGubun">
												<option value="">선택</option>
												<c:set var="emailExist" value="N"/>
												<c:forEach var="list" items="${mailList}" varStatus="idx"><%--emailGubun에 이메일 도메인이 있는지 확인 --%>
													<c:if test="${list.cdNm eq fn:split(orderInfo.senderEmail,'@')[1]}">
														<c:set var="emailExist" value="Y"/>
													</c:if>
												</c:forEach>
												<c:forEach var="list" items="${mailList}" varStatus="idx">
													<option value="${list.cdNm}" data-cdNm="${list.cdNm}" <c:if test="${list.commonCd eq 'MAIL_KIND_ETC'}">data-etc-target="email1"</c:if> <c:if test="${(emailExist eq 'Y' and list.cdNm eq fn:split(orderInfo.senderEmail,'@')[1]) or (orderInfo.senderEmail ne null and orderInfo.senderEmail ne '' and emailExist eq 'N' and idx.last)}">selected</c:if>>
														${list.cdNm}
													</option>
												</c:forEach>
	                                        </select>
	                                    </div>
	                                </div>
	                            </div>
	                        </div>
							<c:set var="displayStr" value="display:none;"/>
							<c:if test="${emailExist eq 'N' and orderInfo.senderEmail ne null and orderInfo.senderEmail ne ''}">
								<c:set var="displayStr" value="display:block;"/>
							</c:if>
							<div class="row">
								<div class="col col-12">
									<div class="form-control" data-etc-name="email1" style="${displayStr}">
										<input type="text" class="input" name="email2" id="email2" value="<c:if test="${emailExist eq 'N'}"><c:out value="${fn:split(orderInfo.senderEmail,'@')[1]}"/></c:if>" maxlength="50" placeholder="이메일 직접입력">
									</div>
								</div>
							</div>
	                        <div class="row">
	                            <div class="col col-12">
	                                <div class="form-control">
	                                    <input type="tel" class="input" name="senderPhoneNo" id="senderPhoneNo" value="<c:out value="${fn:replace(orderInfo.senderPhoneNo,'-','')}"/>" maxlength="20" onblur="exitInput(this);" placeholder="휴대폰 번호 (‘-’를 제외한 숫자만 입력)" required/>
	                                </div>
	                            </div>
	                        </div>
	                        
	                    </div>
                    </div>
                </div>
                <!-- //주문하신 분 -->

                <!-- 배송지정보 -->
	<c:choose>
		<c:when test="${addressList eq null or fn:length(addressList) eq 0}">
				<!-- 배송지정보 - 없을때 -->
                <div class="order-form">
                    <div class="form-group">
                    	<div class="form-title">
	                        <h3 class="title">배송지 정보</h3>
	                        <button type="button" class="btn-toggle active" data-toggle="collapse" data-target="#formShipping"><span class="hide">접기</span></button>
	                    </div>
	                    <div id="formShipping" class="form-body">
	                        <div class="row" id="divSameOrderInfo">
	                            <div class="col col-12">
	                                <div class="form-control">
	                                    <span class="checkbox">
	                                        <input type="checkbox" class="check" name="sameOrderInfo" id="sameOrderInfo" value="Y" <c:if test="${orderInfo.sameOrderInfo eq 'Y'}">checked</c:if>/>
	                                        <label for="sameOrderInfo" class="lbl">주문하신 분과 상품을 받는 분이 같습니다.</label>
	                                    </span>
	                                </div>
	                            </div>
	                        </div>
	                        <div class="row">
	                            <div class="col col-12">
	                                <div class="form-control">
	                                    <input type="text" class="input" name="receiverNm" id="receiverNm" value="<c:out value="${orderInfo.receiverNm}"/>" maxlength="50" placeholder="받으시는 분" required/>
	                                </div>
	                            </div>
	                        </div>
	                        <div class="row">
	                            <div class="col col-12">
	                                <div class="form-control">
	                                    <input type="text" class="input" name="shippingNm" id="shippingNm" value="<c:out value="${orderInfo.shippingNm}"/>" maxlength="25" placeholder="배송지명"/>
	                                </div>
	                            </div>
	                        </div>
	                        <div class="row">
	                            <div class="col col-12">
	                                <div class="form-control">
	                                    <input type="tel" class="input" name="receiverPhoneNo" id="receiverPhoneNo" value="<c:out value="${orderInfo.receiverPhoneNo}"/>" maxlength="20" onblur="exitInput(this);" placeholder="휴대폰번호 (‘-’를 제외한 숫자만 입력)" required/>
	                                </div>
	                            </div>
	                        </div>
	                        <div class="row">
	                            <div class="col col-12">
	                                <div class="form-control">
	                                    <input type="tel" class="input" name="receiverTelNo" id="receiverTelNo" value="<c:out value="${orderInfo.receiverTelNo}"/>" maxlength="20" onblur="exitInput(this);" placeholder="일반전화 (‘-’를 제외한 숫자만 입력)" />
	                                </div>
	                            </div>
	                        </div>
	                        <div class="row">
	                            <div class="col col-7">
	                                <div class="form-control">
	                                    <input type="tel" class="input" name="receiverZipCd" id="receiverZipCd" value="<c:out value="${orderInfo.receiverZipCd}"/>" maxlength="5" onblur="exitInput(this);" placeholder="우편번호" readonly="readonly" />
	                                </div>
	                            </div>
	                            <div class="col col-5">
	                                <button type="button" class="btn full ico-chev" onclick="addrPopup();"><span class="txt">우편번호 검색</span></button>
	                            </div>
	                        </div>
	                        <div class="row">
	                            <div class="col col-12">
	                                <div class="form-control">
	                                    <input type="text" class="input" name="receiverAddr" id="receiverAddr" value="<c:out value="${orderInfo.receiverAddr}"/>" maxlength="100" placeholder="주소" required/>
	                                </div>
	                            </div>
	                        </div>
	                        <div class="row">
	                            <div class="col col-12">
	                                <div class="form-control">
	                                    <input type="text" class="input" name="receiverAddrDetail" id="receiverAddrDetail" value="<c:out value="${orderInfo.receiverAddrDetail}"/>" maxlength="100" placeholder="나머지 주소" />
	                                </div>
	                            </div>
	                        </div>
							<div class="row">
								<div class="col col-6">
									<div class="form-control">
										<span class="checkbox">
											<input type="checkbox" class="check" name="addToAddress" id="addToAddress" value="Y" <c:if test="${orderInfo.addToAddress eq 'Y'}">checked</c:if>>
											<label for="addToAddress" class="lbl">배송지 목록에 추가</label>
										</span>
									</div>
								</div>
								<div class="col col-6">
									<div class="form-control">
										<span class="checkbox">
											<input type="checkbox" class="check" name="setAsDefaultAddress" id="setAsDefaultAddress" value="Y" <c:if test="${orderInfo.setAsDefaultAddress eq 'Y'}">checked</c:if>>
											<label for="setAsDefaultAddress" class="lbl">기본 배송지로 설정</label>
										</span>
									</div>
								</div>
							</div>
	                        <div class="row">
	                            <div class="col col-12">
	                                <div class="form-control">
	                                    <div class="opt_select">
	                                        <select name="orderMemoVal" id="orderMemoVal">
	                                            <option>배송 시 요청 사항을 선택해주세요.</option>
												<c:set var="displayStr" value="display:none;"/>
												<c:forEach var="list" items="${orderMemoList}">
													<c:if test="${list.commonCd eq 'ORDER_MEMO90' and list.commonCd eq orderInfo.orderMemoVal}">	<%--선택한 배송시 요청사항이 있는지 확인 --%>
														<c:set var="displayStr" value="display:block;"/>
													</c:if>
													<option value="${list.commonCd}" data-cdNm="${list.cdNm}" <c:if test="${list.commonCd eq orderInfo.orderMemoVal}">selected</c:if>>
														${list.cdNm}
													</option>
												</c:forEach>
	                                        </select>
	                                    </div>
	                                </div>
	                            </div>
	                        </div>
	                        <div class="row">
	                            <div class="col col-12">
	                                <div class="form-control" style="${displayStr}">
	                                    <!-- [D] 상단 page script 확인 -->
	                                    <textarea class="text" name="orderMemo" id="orderMemo" maxlength="60" style="height: 88px" placeholder=" 0/30자"><c:out value="${orderInfo.orderMemo}"/></textarea>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
                    </div>
                </div>
				<!-- //배송지정보 -->
		</c:when>
		<c:otherwise>
				<!-- 배송지정보 -->
                <div class="order-form">
                    <div class="form-group">
                    	<div class="form-title">
	                        <h3 class="title">배송지 정보</h3>
	                        <button type="button" class="btn-toggle active" data-toggle="collapse" data-target="#formShipping"><span class="hide">접기</span></button>
	                    </div>
	                    <div id="formShipping" class="form-body">

							<div id="tabmenu" class="tab-menu-line">
								<ul>
									<li <c:if test="${orderInfo.addressTabId ne 'addressID2'}">class="active"</c:if> onclick="changeAddressTab(0);"><a href="#addressID1" class="tab-link">배송지 목록</a></li>
									<li <c:if test="${orderInfo.addressTabId eq 'addressID2'}">class="active"</c:if> onclick="changeAddressTab(1);"><a href="#addressID2" class="tab-link">새로 입력</a></li>
								</ul>
							</div>

							<!-- tabList -->
							<div id="addressID1" class="tab-panel tab-panel2 <c:if test="${orderInfo.addressTabId ne 'addressID2'}">active</c:if>" <c:choose><c:when test="${orderInfo.addressTabId ne 'addressID2'}">style="display:block;"</c:when><c:otherwise>style="display:none;"</c:otherwise></c:choose>>
								<div class="shipping-list type-select">
									<ul>
									<c:forEach var="list" items="${addressList}" varStatus="idx">
										<li>
											<div class="item-radio">
												<span class="radiobox">
													<input type="radio" name="selectAddressIdx" id="selectAddressIdx_${list.addressIdx}" class="radio" value="${list.addressIdx}" <c:if test="${list.addressIdx eq orderInfo.selectAddressIdx or ((orderInfo.selectAddressIdx eq null or orderInfo.selectAddressIdx eq '') and idx.first)}">checked</c:if> data-shippingnm="<c:out value="${list.shippingNm}"/>" data-receivernm="<c:out value="${list.receiverNm}"/>" data-addr="<c:out value="${list.addr}"/>" data-addrdetail="<c:out value="${list.addrDetail}"/>" data-zipcd="<c:out value="${list.zipCd}"/>" data-oldaddr="<c:out value="${list.oldAddr}"/>" data-oldzipcd="<c:out value="${list.oldZipCd}"/>" data-telno="<c:out value="${list.telNo}"/>" data-phoneno="<c:out value="${list.phoneNo}"/>" data-defaultyn="<c:out value="${list.defaultYn}"/>">
													<label for="selectAddressIdx_${list.addressIdx}" class="lbl"><span class="hide">선택</span></label>
												</span>
											</div>
											<div class="item">
												<p class="item-name">[<c:out value="${list.shippingNm}"/>] <c:out value="${list.receiverNm}"/> 
												<c:if test="${list.type eq 'RECENT'}">
													<span class="em">[최근 배송지]</span>
												</c:if>
												<c:if test="${list.defaultYn eq 'Y'}">
													<span class="em">[기본 배송지]</span>
												</c:if>
												</p>
												<p class="item-addr">
													<c:out value="${list.phoneNo}"/><br>
													<c:out value="${list.addr}"/> <c:out value="${list.addrDetail}"/>
												</p>
											</div>
										<c:if test="${list.type eq 'ETC'}">	<%-- 배송지 테이블 목록만 삭제, 수정 버튼 표시 --%>
											<div class="btn-box">
												<div class="col col-6">
													<button type="button" class="btn full outline-green" data-toggle="popup" onclick="javascript:modifyAddress('${list.addressIdx}');"><span class="txt">수정</span></button>
												</div>
												<div class="col col-6">
													<button type="button" class="btn full" onclick="javascript:deleteAddress('${list.addressIdx}');"><span class="txt">삭제</span></button>
												</div>
											</div>
										</c:if>
										</li>
									</c:forEach>
									</ul>
								</div>

							</div>
							<!-- //tabList -->

							<!-- tabNew -->
							<div id="addressID2" class="tab-panel tab-panel2 <c:if test="${orderInfo.addressTabId eq 'addressID2'}">active</c:if>">
		                        <div class="row" id="divSameOrderInfo">
		                            <div class="col col-12">
		                                <div class="form-control">
		                                    <span class="checkbox">
		                                        <input type="checkbox" class="check" name="sameOrderInfo" id="sameOrderInfo" value="Y" <c:if test="${orderInfo.sameOrderInfo eq 'Y'}">checked</c:if>/>
		                                        <label for="sameOrderInfo" class="lbl">주문하신 분과 상품을 받는 분이 같습니다.</label>
		                                    </span>
		                                </div>
		                            </div>
		                        </div>
		                        <div class="row">
		                            <div class="col col-12">
		                                <div class="form-control">
		                                    <input type="text" class="input" name="receiverNm" id="receiverNm" value="<c:out value="${orderInfo.receiverNm}"/>" maxlength="50" placeholder="받으시는 분" required/>
		                                </div>
		                            </div>
		                        </div>
		                        <div class="row">
		                            <div class="col col-12">
		                                <div class="form-control">
		                                    <input type="text" class="input" name="shippingNm" id="shippingNm" value="<c:out value="${orderInfo.shippingNm}"/>" maxlength="25" placeholder="배송지명"/>
		                                </div>
		                            </div>
		                        </div>
		                        <div class="row">
		                            <div class="col col-12">
		                                <div class="form-control">
		                                    <input type="tel" class="input" name="receiverPhoneNo" id="receiverPhoneNo" value="<c:out value="${orderInfo.receiverPhoneNo}"/>" maxlength="20" onblur="exitInput(this);" placeholder="휴대폰번호 (‘-’를 제외한 숫자만 입력)" required/>
		                                </div>
		                            </div>
		                        </div>
		                        <div class="row">
		                            <div class="col col-12">
		                                <div class="form-control">
		                                    <input type="tel" class="input" name="receiverTelNo" id="receiverTelNo" value="<c:out value="${orderInfo.receiverTelNo}"/>" maxlength="20" onblur="exitInput(this);" placeholder="일반전화 (‘-’를 제외한 숫자만 입력)" />
		                                </div>
		                            </div>
		                        </div>
		                        <div class="row">
		                            <div class="col col-7">
		                                <div class="form-control">
		                                    <input type="tel" class="input" name="receiverZipCd" id="receiverZipCd" value="<c:out value="${orderInfo.receiverZipCd}"/>" maxlength="5" onblur="exitInput(this);" placeholder="우편번호" readonly="readonly" />
		                                </div>
		                            </div>
		                            <div class="col col-5">
		                                <button type="button" class="btn full ico-chev" onclick="addrPopup();"><span class="txt">우편번호 검색</span></button>
		                            </div>
		                        </div>
		                        <div class="row">
		                            <div class="col col-12">
		                                <div class="form-control">
		                                    <input type="text" class="input" name="receiverAddr" id="receiverAddr" value="<c:out value="${orderInfo.receiverAddr}"/>" maxlength="100" placeholder="주소" required/>
		                                </div>
		                            </div>
		                        </div>
		                        <div class="row">
		                            <div class="col col-12">
		                                <div class="form-control">
		                                    <input type="text" class="input" name="receiverAddrDetail" id="receiverAddrDetail" value="<c:out value="${orderInfo.receiverAddrDetail}"/>" maxlength="100" placeholder="나머지 주소" />
		                                </div>
		                            </div>
		                        </div>
								<div class="row">
									<div class="col col-6">
										<div class="form-control">
											<span class="checkbox">
												<input type="checkbox" class="check" name="addToAddress" id="addToAddress" value="Y" <c:if test="${orderInfo.addToAddress eq 'Y'}">checked</c:if>>
												<label for="addToAddress" class="lbl">배송지 목록에 추가</label>
											</span>
										</div>
									</div>
									<div class="col col-6">
										<div class="form-control">
											<span class="checkbox">
												<input type="checkbox" class="check" name="setAsDefaultAddress" id="setAsDefaultAddress" value="Y" <c:if test="${orderInfo.setAsDefaultAddress eq 'Y'}">checked</c:if>>
												<label for="setAsDefaultAddress" class="lbl">기본 배송지로 설정</label>
											</span>
										</div>
									</div>
								</div>
							</div>
							<!-- tabNew -->
								
	                        <div class="row">
	                            <div class="col col-12">
	                                <div class="form-control">
	                                    <div class="opt_select">
	                                        <select name="orderMemoVal" id="orderMemoVal">
	                                            <option>배송 시 요청 사항을 선택해주세요.</option>
												<c:set var="displayStr" value="display:none;"/>
												<c:forEach var="list" items="${orderMemoList}">
													<c:if test="${list.commonCd eq 'ORDER_MEMO90' and list.commonCd eq orderInfo.orderMemoVal}">	<%--선택한 배송시 요청사항이 있는지 확인 --%>
														<c:set var="displayStr" value="display:block;"/>
													</c:if>
													<option value="${list.commonCd}" data-cdNm="${list.cdNm}" <c:if test="${list.commonCd eq orderInfo.orderMemoVal}">selected</c:if>>
														${list.cdNm}
													</option>
												</c:forEach>
	                                        </select>
	                                    </div>
	                                </div>
	                            </div>
	                        </div>
	                        <div class="row">
	                            <div class="col col-12">
	                                <div class="form-control" style="${displayStr}">
	                                    <!-- [D] 상단 page script 확인 -->
	                                    <textarea class="text" name="orderMemo" id="orderMemo" maxlength="60" style="height: 88px" placeholder=" 0/30자"><c:out value="${orderInfo.orderMemo}"/></textarea>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
                    </div>
                </div>
				<!-- //배송지정보 -->
		</c:otherwise>
	</c:choose>

                <!-- 주문상품확인 -->
                <div class="order-form">
                    <div class="form-group">
                    	<div class="form-title">
							<h3 class="title">주문상품 확인 <span class="count">( <span class="em"><fmt:formatNumber value="${fn:length(list)}" groupingUsed="true"/></span>개 <i class="bar"></i> 총 <span class="em" id="totalPayPriceTop"><fmt:formatNumber value="${orderInfo.totalPayPrice}" groupingUsed="true"/></span> )</span></h3>
	                        <button type="button" class="btn-toggle" data-toggle="collapse" data-target="#formOrderGoods"><span class="hide">접기</span></button>
	                    </div>
	                    <div id="formOrderGoods" class="form-body" style="display:none;">
	                        <div class="order-goods">
	                            <ul>
						<c:choose>
							<c:when test="${list eq null or fn:length(list) eq 0}">
									<li>
		                        		<!-- 데이타 없을경우 -->
				                        <div class="item-nodata">
				                            <p>상품이 없습니다.</p>
				                        </div>
				                        <!-- //데이타 없을경우 -->
									</li>
							</c:when>
							<c:otherwise>
								<c:set var="totalOrderPrice" value="0"/>	<%-- 총 결제금액 --%>
								<c:forEach var="list" items="${list}" varStatus="idx">
	                                <li class="tr_goods">
	                                    <div class="item">
	                                        <div class="item-view">
	                                            <div class="view-thumb">
							                  		<c:set var="imgSplit" value="${fn:split(list.mainFile ,'.') }"/>
													<img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}" onerror="this.src='${CTX}/images/${DEVICE}/noimage.jpg'" alt="상품 썸네일 이미지">
	                                            </div>
	                                            <div class="view-info">
													<div class="badge-box">
														<c:if test="${list.opoYn eq 'Y' }" ><span class="badge type1">1+1</span></c:if>
														<c:if test="${list.tpoYn eq 'Y' }" ><span class="badge type2">2+1</span></c:if>
														<c:if test="${list.newYn eq 'Y' }" ><span class="badge type3">NEW</span></c:if>
														<c:if test="${list.bestYn eq 'Y' }" ><span class="badge type4">BEST</span></c:if>
														<c:if test="${list.saleiconYn eq 'Y' }" ><span class="badge type5">SALE</span></c:if>
														<c:if test="${list.pointiconYn eq 'Y' }" ><span class="badge type3">POINT <em>X2</em></span></c:if>
													</div>
													<c:choose>
														<c:when test="${list.brandIdx eq 1}"><p class="text-gatsby">${list.brandNm}</p></c:when>		
														<c:when test="${list.brandIdx eq 3}"><p class="text-bifesta">${list.brandNm}</p></c:when>
														<c:when test="${list.brandIdx eq 4}"><p class="text-lucidol">${list.brandNm}</p></c:when>
														<c:when test="${list.brandIdx eq 6}"><p class="text-mama">${list.brandNm}</p></c:when>
														<c:when test="${list.brandIdx eq 7}"><p class="text-dental">${list.brandNm}</p></c:when>
														<c:when test="${list.brandIdx eq 8}"><p class="text-charley">${list.brandNm}</p></c:when>
													</c:choose>															
	                                                <p class="name"><c:out value="${list.goodsNm}"/></p>
	                                                <p class="price"><span class="qty">수량: <fmt:formatNumber value="${list.orderCnt}" groupingUsed="true"/></span></p>
	                                            </div>
	                                        </div>
	                                        <div class="item-payment">
	                                            <dl>
	                                                <dt>판매가</dt>
	                                                <dd><fmt:formatNumber value="${list.price}" groupingUsed="true"/></dd>
	                                                <dt>상품 할인</dt>
	                                                <dd>
	                                                    <div>
															<c:set var="discount" value="${(list.price - list.discountPrice) * list.orderCnt}"/>
	                                                        <span class="sub"><fmt:formatNumber value="${list.discountRate}" groupingUsed="true"/>% 할인</span>
	                                                        <em class="em"><c:if test="${discount ne null and discount ne '' and discount gt 0}">-</c:if><fmt:formatNumber value="${discount}" groupingUsed="true"/></em>
	                                                    </div>
	                                                </dd>
	                                                <dt>쿠폰 할인</dt>
	                                                <dd>
													<c:if test="${list.couponNm ne null}">
	                                                    <div>
                                                            <span class="sub">${list.couponNm}</span>
	                                                        <em class="em">-<fmt:formatNumber value="${list.couponDiscountPrice}" groupingUsed="true"/></em>
	                                                    </div>
													</c:if>
	                                                    <button class="btn outline-green ico-chev" data-toggle="popup" data-target="#PopGoodsCoupon" onclick="javascript:useGoodsCoupon();"><span class="txt">상품 쿠폰 변경</span></button>
	                                                </dd>
	                                                <dt>결제금액</dt>
	                                                <dd><fmt:formatNumber value="${list.discountPrice * list.orderCnt - list.couponDiscountPrice}" groupingUsed="true"/></dd>
	                                            </dl>   
	                                        </div>
	                                    </div>
	                                </li>
										
									<c:set var="totalOrderPrice" value="${totalOrderPrice + list.discountPrice * list.orderCnt - list.couponDiscountPrice}"/>
								</c:forEach>
							</c:otherwise>
						</c:choose>
	                            </ul>
	                        </div>
	                    </div>
                    </div>
                </div>
                <!-- //주문상품확인 -->

                <!-- 추가 할인 및 결제 -->
                <div class="order-form form-payment">
                    <div class="form-group">
                    	<div class="form-title">
	                        <h3 class="title">추가 할인 및 결제</h3>
	                        <button type="button" class="btn-toggle active" data-toggle="collapse" data-target="#formPayment"><span class="hide">접기</span></button>
	                    </div>
	                    <div id="formPayment" class="form-body">
	
	                        <div class="payment-promo">
	                            <div class="promo-item">
	                                <dl>
	                                    <dt>프로모션 코드</dt>
	                                    <dd>
	                                    	<span class="em" id="totalPromotioncodePrice">
	                                    		<c:choose>
	                                    			<c:when test="${orderInfo.randomcodeYn ne 'Y'}">
	                                    				<fmt:formatNumber value="${orderInfo.totalPromotioncodePrice}" groupingUsed="true"/>
	                                    			</c:when>
	                                    			<c:otherwise>
	                                    				0
	                                    			</c:otherwise>
	                                    		</c:choose>
	                                    	</span>원
	                                    </dd>
	                                </dl>
	                                <div class="row">
	                                    <div class="col col-6">
	                                        <div class="form-control">
	                                            <input type="text" name="promotioncode" id="promotioncode" maxlength="50" value="<c:if test="${orderInfo.randomcodeYn ne 'Y'}"><c:out value="${orderInfo.promotioncode}"/></c:if>" <c:if test="${(!empty orderInfo.promotioncode) and (orderInfo.randomcodeYn ne 'Y')}">readonly</c:if> class="input" placeholder="코드 입력" />
	                                        </div>
	                                    </div>
	                                    <div class="col col-3">
	                                        <button type="button" class="btn outline-green full" onclick="javascript:applyPromotioncode();"><span class="txt">등록</span></button>
	                                    </div>
	                                    <div class="col col-3">
	                                        <button type="button" class="btn full" onclick="javascript:resetPromotioncode();"><span class="txt">초기화</span></button>
	                                    </div>
	                                </div>
	                                <p>* 프로모션 코드와 장바구니 쿠폰은 중복 사용이 불가합니다.</p>
	                                <c:if test="${orderInfo.randomcodeYn ne 'Y'}">
										<c:if test="${ ! empty orderInfo.promotioncodeName  }">
			                                <p class="em"><c:out value="${orderInfo.promotioncodeName}"/></p>
										</c:if>
									</c:if>
	                            </div>
	                            
	                            <% /* 20180601 주석처리
								<div class="promo-item">
									<dl>
										<dt>카카오 플친 코드</dt>
										<dd>
											<span class="em" id="totalPromotioncodePrice">
												<c:choose>
	                                    			<c:when test="${orderInfo.randomcodeYn eq 'Y'}">
	                                    				<fmt:formatNumber value="${orderInfo.totalPromotioncodePrice}" groupingUsed="true"/>
	                                    			</c:when>
	                                    			<c:otherwise>
	                                    				0
	                                    			</c:otherwise>
	                                    		</c:choose>
											</span>원
										</dd>
									</dl>
									<div class="row">
										<div class="col col-6">
											<div class="form-control">
												<input type="text" name="promotioncoderandom" id="promotioncoderandom" maxlength="50" class="input" placeholder="코드 입력" value="<c:if test="${orderInfo.randomcodeYn eq 'Y'}"><c:out value="${orderInfo.promotioncode}"/></c:if>" <c:if test="${(!empty orderInfo.promotioncode) and (orderInfo.randomcodeYn eq 'Y')}">readonly</c:if>/>
												<input type="hidden" name="randomcodeYn" id="randomcodeYn" value=""/>
											</div>
										</div>
										<div class="col col-3">
											<button type="button" class="btn outline-green full" onclick="javascript:applyPromotioncodeRandom();"><span class="txt">등록</span></button>
										</div>
										<div class="col col-3">
											<button type="button" class="btn full" onclick="javascript:resetPromotioncodeRandom();"><span class="txt">초기화</span></button>
										</div>
									</div>
									<p>* 카카오 플친 쿠폰 코드와 장바구니 쿠폰은 중복 사용이 불가합니다.</p>
									<c:if test="${orderInfo.randomcodeYn eq 'Y'}">
										<c:if test="${ ! empty orderInfo.promotioncodeName  }">
			                                <p class="em"><c:out value="${orderInfo.promotioncodeName}"/></p>
										</c:if>
									</c:if>
								</div>
								*/ %>
								
	                            <div class="promo-item">
	                                <dl>
	                                    <dt>장바구니 쿠폰 (보유 쿠폰 <span class="em">${fn:length(cartCouponList)}</span> 개)</dt>
	                                    <dd><span class="em"><fmt:formatNumber value="${orderInfo.totalCartCouponPrice}" groupingUsed="true"/></span>원</dd>
	                                </dl>
	                                <div class="row">
	                                    <div class="col col-9">
	                                        <button type="button" class="btn full" <c:choose><c:when test="${fn:length(cartCouponList) gt 0}">data-toggle="popup" data-target="#popCartCoupon"</c:when><c:otherwise>onclick="alert('사용 가능한 장바구니 쿠폰이 존재하지 않습니다.');"</c:otherwise></c:choose>><span class="txt">쿠폰 변경</span></button>
	                                    </div>
	                                    <div class="col col-3">
	                                        <button type="button" class="btn full" onclick="javascript:resetCartCoupon();"><span class="txt">초기화</span></button>
	                                    </div>
	                                </div>
								<c:if test="${ ! empty orderInfo.cartcouponName  }">
	                                <p class="em">[장바구니] <c:out value="${orderInfo.cartcouponName}"/>이 적용되었습니다.</p>
								</c:if>
									<p>* 장바구니 쿠폰은 프로모션 코드와 중복 사용이 불가합니다.</p>
	                            </div>
	                            <div class="promo-item">
	                                <dl>
	                                    <dt>포인트 할인 (보유 포인트 <span class="em"><fmt:formatNumber value="${memberDetail.pointPrice}" groupingUsed="true"/></span>P)</dt>
	                                    <dd><span class="em"><fmt:formatNumber value="${orderInfo.totalPointPrice}" groupingUsed="true"/></span>원</dd>
	                                </dl>
	                                <div class="row">
		                                <!-- 
											수정 : 2017-08-18
											- placeholder 삭제
										-->
	                                    <div class="col col-5">
	                                        <div class="form-control">
	                                            <input type="tel" class="input" id="totalPointPrice" value="<fmt:formatNumber value="${orderInfo.totalPointPrice}" groupingUsed="true"/>" onchange="javascript:applyMyPoint();"/>
	                                        </div>
	                                    </div>
	                                    <!-- //수정 : 2017-08-18 -->
	                                    <div class="col col-4">
	                                        <button type="button" class="btn outline-green full" onclick="javascript:useAllPoint();"><span class="txt">모두 사용</span></button>
	                                    </div>
	                                    <div class="col col-3">
	                                        <button type="button" class="btn full" onclick="javascript:resetPoint();"><span class="txt">초기화</span></button>
	                                    </div>
	                                </div>
	                                <p>* 1P 단위로 사용 가능 합니다.</p>
	                            </div>
	                            <div class="promo-item">
	                                <dl>
	                                    <dt>배송비 쿠폰 (보유 쿠폰 <span class="em">${fn:length(shippingCouponList)}</span> 개)</dt>
	                                    <dd><span class="em" id="deliveryPrice"><fmt:formatNumber value="${orderInfo.shippingPrice}" groupingUsed="true"/></span>원</dd>
	                                </dl>
	                                <div class="row">
	                                    <div class="col col-12">
	                                        <button type="button" class="btn full outline-green ico-chev" <c:choose><c:when test="${fn:length(shippingCouponList) gt 0}">data-toggle="popup" data-target="#PopShippingCoupon"</c:when><c:otherwise>onclick="alert('사용 가능한 배송비 쿠폰이 존재하지 않습니다.');"</c:otherwise></c:choose>><span class="txt">쿠폰 조회 및 적용</span></button>
	                                    </div>
	                                </div>
	                                <p>* 배송비는 실결제금액 3만원 미만일 경우 부과됩니다.</p>
									<p>* 배송비 쿠폰은 마지막에 적용해 주세요. </p>
									<p>* 실결제금액 2만원 이상일 경우만 배송비 쿠폰 사용이 가능합니다.</p>
	                            </div>
	                        </div>
	                        <hr class="split" />
	                        <div class="payment-final">
	                            <ul>
	                                <li>
	                                    <div class="item">
	                                        <strong>총 결제 금액</strong>
	                                        <p id="totalOrderPrice"><fmt:formatNumber value="${orderInfo.totalOrderPrice}" groupingUsed="true"/></p>
	                                    </div>
	                                </li>
	                                <li>
	                                    <div class="item">
	                                        <strong>총 할인금액 <button type="button" class="btn_toggle" data-toggle="collapse" data-target="#discountDetail"><span class="hide">펼치기</span></button></strong>
	                                        <p><span class="em"><fmt:formatNumber value="${-1*(orderInfo.totalGiftCouponPrice + orderInfo.totalPromotioncodePrice + orderInfo.totalCartCouponPrice + orderInfo.totalPointPrice)}" groupingUsed="true"/></span></p>
	                                    </div>
	                                    <dl id="discountDetail" class="item-sub">
	                                        <dt>상품 쿠폰</dt>
	                                        <dd><fmt:formatNumber value="${orderInfo.totalGiftCouponPrice}" groupingUsed="true"/></dd>
	                                        <dt>장바구니 쿠폰</dt>
	                                        <dd><fmt:formatNumber value="${orderInfo.totalCartCouponPrice}" groupingUsed="true"/></dd>
	                                        <dt>포인트</dt>
	                                        <dd><fmt:formatNumber value="${orderInfo.totalPointPrice}" groupingUsed="true"/></dd>
	                                        <dt>프로모션 코드</dt>
	                                        <dd><fmt:formatNumber value="${orderInfo.totalPromotioncodePrice}" groupingUsed="true"/></dd>
	                                    </dl>
	                                </li>
	                                <li>
	                                    <div class="item">
	                                        <strong>총 배송비</strong>
	                                        <p>+<fmt:formatNumber value="${orderInfo.shippingPrice}" groupingUsed="true"/></p>
	                                    </div>
	                                </li>
<%--
	                                <li>
	                                    <div class="item">
	                                        <strong>선물포장비</strong>
	                                        <p>+2,500</p>
	                                    </div>
	                                </li>
 --%>
	                                <li class="total">
	                                    <div class="item">
	                                        <strong>최종 결제 금액</strong>
	                                        <p id="totalPayPrice"><fmt:formatNumber value="${orderInfo.totalPayPrice}" groupingUsed="true"/></p>
	                                    </div>
	                                    <dl class="item-sub type2">
	                                        <dt>적립 예상 포인트</dt>
	                                        <dd><fmt:formatNumber value="${orderInfo.totalSavePoint}" groupingUsed="true"/> P <% /* (<fmt:formatNumber value="${memberDetail.pointRate}" groupingUsed="true"/>%) */ %> </dd>
	                                    </dl>
	                                </li>
	                            </ul>
	                        </div>
	                    </div>
                    </div>
                </div>
                <!-- //추가 할인 및 결제 -->

				<%-- 주문결제 배너 --%>
				<c:if test="${!empty bannerInfo}">
					<c:if test="${bannerInfo.deviceGubun eq 'M' or bannerInfo.deviceGubun eq 'A'}">
						<div class="order-form">
							<c:choose>
								<c:when test="${bannerInfo.moLinkYn eq 'N'}">
								<a href="javascript:">
								</c:when>
								<c:when test="${bannerInfo.moLinkYn eq 'Y'}">
								<a href="${bannerInfo.moLinkUrl}" <c:if test="${bannerInfo.moLinkFlag eq '_BLANK'}">target="_BLANK"</c:if>>
								</c:when>				
							</c:choose>
								<img src="${IMGPATH}/banner/${bannerInfo.bannerIdx}/${bannerInfo.moBannerImg}" alt="${bannerInfo.bannerNm}" style="max-width: 100%"  />
							</a>
						</div>
					</c:if>
				</c:if>
				<%-- //주문결제 배너 --%>
					
			<c:if test="${priceGiftList ne null and fn:length(priceGiftList) gt 0}">
				<!-- 사은품을 선택 -->
				<div class="order-form form-gift">
					<div class="form-group">
						<div class="form-title">
							<h3 class="title">사은품을 선택해 주세요.</h3>
							<button type="button" class="btn-toggle active" data-toggle="collapse" data-target="#formSelectGifts" ><span class="hide">접기</span></button>
						</div>
						<div id="formSelectGifts" class="form-body">
							<div class="gift-list">
								<ul>
								<c:forEach var="list" items="${priceGiftList}" varStatus="idx">
									<li>
										<div class="item">
											<div class="item-thumb">
												<c:set var="imgSplit" value="${fn:split(list.giftImg ,'.') }"/>
												<img src="${IMGPATH}/gift/${list.giftIdx}/${imgSplit[0]}.${imgSplit[1]}" alt="사은품 이미지">	<%--사은품은 원본 이미지로 표시 --%>
											</div>
											<div class="item-info">
												<p class="type"><c:out value="${list.termNm}"/></p>
												<p class="name"><c:out value="${list.giftNm}"/></p>
											</div>
											<span class="radiobox">
												<input type="radio" name="selectPriceGiftIdx" id="optCheckGift${list.giftIdx}" class="radio" value="${list.giftIdx}" <c:if test="${orderInfo.selectPriceGiftIdx eq list.giftIdx}">checked</c:if>/>
												<label for="optCheckGift${list.giftIdx}" class="lbl"><span class="hide">사은품 선택</span></label>
											</span>
										</div>
									</li>
								</c:forEach>
								<c:if test="${noGiftSelectList ne null}">	<%-- 사은품 선택 안함 --%>
									<li>
										<div class="item">
											<div class="item-thumb">
												<c:set var="imgSplit" value="${fn:split(noGiftSelectList.giftImg ,'.') }"/>
												<img src="${IMGPATH}/gift/${noGiftSelectList.giftIdx}/${imgSplit[0]}.${imgSplit[1]}" alt="사은품 이미지">	<%--사은품은 원본 이미지로 표시 --%>
											</div>
											<div class="item-info">
												<p class="type">&nbsp;</p>
												<p class="name"><c:out value="${noGiftSelectList.giftNm}"/></p>
											</div>
											<span class="radiobox">
												<input type="radio" name="selectPriceGiftIdx" id="optCheckGift${noGiftSelectList.giftIdx}" class="radio" value="${noGiftSelectList.giftIdx}" <c:if test="${orderInfo.selectPriceGiftIdx eq noGiftSelectList.giftIdx}">checked</c:if>/>
												<label for="optCheckGift${noGiftSelectList.giftIdx}" class="lbl"><span class="hide">사은품 선택</span></label>
											</span>
										</div>
									</li>
								</c:if>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<!-- //사은품을 선택 -->
			</c:if>

			<c:if test="${freeGiftList ne null and fn:length(freeGiftList) gt 0}">
				<!-- 사은품 -->
				<div class="order-form form-gift">
					<div class="form-group">
						<div class="form-title">
							<h3 class="title">이번 주문에서 받으실 수 있는 사은품입니다.</h3>
							<button type="button" class="btn-toggle active" data-toggle="collapse" data-target="#formGifts" ><span class="hide">접기</span></button>
						</div>
						<div id="formGifts" class="form-body">
							<div class="gift-list">
								<ul>
								<c:forEach var="list" items="${freeGiftList}" varStatus="idx">
									<li>
										<div class="item">
											<div class="item-thumb">
												<c:set var="imgSplit" value="${fn:split(list.giftImg ,'.') }"/>
												<img src="${IMGPATH}/gift/${list.giftIdx}/${imgSplit[0]}.${imgSplit[1]}" alt="사은품 이미지">	<%--사은품은 원본 이미지로 표시 --%>
											</div>
											<div class="item-info">
												<p class="type"><c:out value="${list.termNm}"/></p>
												<p class="name"><c:out value="${list.giftNm}"/></p>
											</div>
										</div>
									</li>
								</c:forEach>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<!-- 사은품 -->
			</c:if>

		<c:choose>
			<c:when test="${totalPayPrice eq 0}">
				<!-- 결제금액 없을때 -->
                <div class="order-form form-payment-info">
				
                	<div  class="form-body">
                	 <div class="form-title">
	                        <h3 class="title">결제 정보</h3>
	                        
	                    </div>
	                    
	                 <div class="method-body">
								<h4 class="tit" align="center">결제금액은 <span>0</span>원 입니다.</h4>
	                            
                          <div class="method-confirm">
                              <div class="row">
                                  <div class="col col-12">
                                       <div class="form-control">
                                          <span class="checkbox">
                                              <input type="checkbox" name="agreement" id="agreement" value="Y" class="check" <c:if test="${orderInfo.agreement eq 'Y'}">checked</c:if>>
                                              <label for="agreement" class="lbl">위 상품의 판매조건을 명확히 확인하였으며, <br/> 구매 진행에 동의합니다. (전자상거래법 제 8조 2항)</label>
                                          </span>
                                      </div>
                                  </div>
                              </div>
                              <div class="btn-box confirm">
                                  <button type="button" class="btn black" onclick="javascript:payment();"><span class="txt">결제하기</span></button>
                              </div>
                          </div>
                    </div>
                    </div>
	                            
				</div>
				<!-- 결제금액 없을때 -->
			</c:when>
			<c:otherwise>
                <!-- 결제 정보 -->
                <div class="order-form form-payment-info">
                	<div class="form-group">
	                	
	                    <div class="form-title">
	                        <h3 class="title">결제 정보</h3>
	                        <button type="button" class="btn-toggle active" data-toggle="collapse" data-target="#formPaymentMethod"><span class="hide">접기</span></button>
	                    </div>
	                    <div id="formPaymentMethod" class="form-body">
	                        
	                        <!--
	                            [D] 개발 참고사항
	                            - 결제 방법 선택시 아래 구분코드를 기준으로 히든필드 'METHOD_TYPE'에 값을 넣습니다.
	                            - 결제하기 버튼 클릭시 'METHOD_TYPE'를 참고 하시면 됩니다.
	                                ** 비회원은 원클릭 없음
	                                
	                            > 0: 원클릭
	                            > 1: 신용카드
	                            > 2: 실시간 계좌 이체
	                            > 3: 가상계좌 입금
	                            > 4: 휴대폰 결제
	                            > 5: PAYCO
	                            > 6: PAYNOW
	                            > 7: SMILEPAY
	                            > 8: 원더페이
	                        -->
	                        <div class="method-type">
	                            <!-- 
	                            <a href="#methodOneclick" class="method-oneclick <c:if test="${orderInfo.selectPayType eq 'BILLKEY' or orderInfo.selectPayType eq null or orderInfo.selectPayType eq ''}">active</c:if>" data-method="BILLKEY"><span>정말 간편한 <span class="em">ONE CLICK 결제</span></span></a>
	                            --> 
	                            <ul>
	                                <li><a href="#methodCard" <c:if test="${orderInfo.selectPayType eq 'SC0010' or orderInfo.selectPayType eq 'BILLKEY' or orderInfo.selectPayType eq null or orderInfo.selectPayType eq ''}">class="active"</c:if> data-method="SC0010" onclick="btnEvent(1);">신용카드</a></li>

	                                <!--
	                                <li><a href="#methodAccount" <c:if test="${orderInfo.selectPayType eq 'SC0030'}">class="active"</c:if> data-method="SC0030" onclick="btnEvent(2);">실시간<br/>계좌 이체</a></li>
	                                <li><a href="#methodVirtual" <c:if test="${orderInfo.selectPayType eq 'SC0040'}">class="active"</c:if> data-method="SC0040" onclick="btnEvent(3);">가상계좌<br/>입금</a></li>
	                                <li><a href="#methodMobile" <c:if test="${orderInfo.selectPayType eq 'SC0060'}">class="active"</c:if> data-method="SC0060" onclick="btnEvent(4);">휴대폰<br/>결제</a></li>
									<li>
	                                	<a href="#methodSmilePay" <c:if test="${orderInfo.selectPayType eq 'SMILEPAY'}">class="active"</c:if> data-method="SMILEPAY" onclick="btnEvent(5);">
	                                		<span class="type_smilepay"><span class="hide">스마일페이</span></span>
	                                	</a>
	                                </li>
	                                <li>
	                                	<a href="#methodPayco" <c:if test="${orderInfo.selectPayType eq 'PAYCO'}">class="active"</c:if> data-method="PAYCO" onclick="btnEvent(6);">
	                                		<span class="type_payco"><span class="hide">페이코</span></span>
	                                	</a>
	                                </li>
	                                -->
	                                
	                                <%--
	                                <li><a href="#methodWonderpay" data-method="8"><span class="type_wonder"><span class="hide">원더페이</span></span></a></li>
	                                --%>
	                                
	                                <%-- 201109 LJH : 위메프페이 제거
	                                <li>
	                                	<a href="#methodWonder" <c:if test="${orderInfo.selectPayType eq 'WONDERPAY'}">class="active"</c:if> data-method="WONDERPAY" onclick="btnEvent(7);">
	                                		<span class="type_wonder"><span class="hide">원더페이</span></span>
	                                	</a>
	                                </li>
	                                --%>
	                                
	                            </ul>
	                            <c:choose>
		                            <c:when test="${orderInfo.selectPayType eq 'BILLKEY' or orderInfo.selectPayType eq null or orderInfo.selectPayType eq ''}">
			                            <input type="hidden" name="METHOD_TYPE" id="METHOD_TYPE" value="SC0010" />
		                            </c:when>
		                            <c:when test="${orderInfo.selectPayType eq 'SC0010'}">
			                            <input type="hidden" name="METHOD_TYPE" id="METHOD_TYPE" value="SC0010" />
		                            </c:when>
		                            <c:when test="${orderInfo.selectPayType eq 'SC0030'}">
			                            <input type="hidden" name="METHOD_TYPE" id="METHOD_TYPE" value="SC0030" />
		                            </c:when>
		                            <c:when test="${orderInfo.selectPayType eq 'SC0040'}">
			                            <input type="hidden" name="METHOD_TYPE" id="METHOD_TYPE" value="SC0040" />
		                            </c:when>
		                            <c:when test="${orderInfo.selectPayType eq 'SC0060'}">
			                            <input type="hidden" name="METHOD_TYPE" id="METHOD_TYPE" value="SC0060" />
		                            </c:when>
		                            <c:when test="${orderInfo.selectPayType eq 'PAYCO'}">
			                            <input type="hidden" name="METHOD_TYPE" id="METHOD_TYPE" value="PAYCO" />
		                            </c:when>
		                            <c:when test="${orderInfo.selectPayType eq 'PAYNOW'}">
			                            <input type="hidden" name="METHOD_TYPE" id="METHOD_TYPE" value="PAYNOW" />
		                            </c:when>
		                            <c:when test="${orderInfo.selectPayType eq 'SMILEPAY'}">
			                            <input type="hidden" name="METHOD_TYPE" id="METHOD_TYPE" value="SMILEPAY" />
		                            </c:when>
		                            <c:when test="${orderInfo.selectPayType eq 'WONDERPAY'}">
			                            <input type="hidden" name="METHOD_TYPE" id="METHOD_TYPE" value="WONDERPAY" />
		                            </c:when>
		                            <c:otherwise>
			                            <input type="hidden" name="METHOD_TYPE" id="METHOD_TYPE" value="" />
		                            </c:otherwise>
	                            </c:choose>
	                        </div>
							
							<%-- 스마일페이 배너 --%>
							<c:if test="${!empty smileBanner}">
								<c:if test="${smileBanner.deviceGubun eq 'M' or smileBanner.deviceGubun eq 'A'}">
									<div class="method-type-banner smileBox" <c:if test="${orderInfo.selectPayType ne 'SMILEPAY' and orderInfo.selectPayType ne null and orderInfo.selectPayType ne ''}">style="display:none;"</c:if>>
										<img src="${IMGPATH}/banner/${smileBanner.bannerIdx}/${smileBanner.moBannerImg}" style="max-width: 100%" alt="${smileBanner.bannerNm}">
									</div>
								</c:if>
							</c:if>
							<%-- //스마일페이 배너 --%>
							
							<%-- 페이코 배너 --%>
							<c:if test="${!empty paycoBanner}">
								<c:if test="${paycoBanner.deviceGubun eq 'M' or paycoBanner.deviceGubun eq 'A'}">
									<div class="method-type-banner paycoBox" <c:if test="${orderInfo.selectPayType ne 'PAYCO' and orderInfo.selectPayType ne null and orderInfo.selectPayType ne ''}">style="display:none;"</c:if>>
										<img src="${IMGPATH}/banner/${paycoBanner.bannerIdx}/${paycoBanner.moBannerImg}" style="max-width: 100%" alt="${paycoBanner.bannerNm}">
									</div>
								</c:if>
							</c:if>
							<%-- //페이코 배너 --%>
							
							<%-- 원더페이 배너 --%>
							<c:if test="${!empty wonderpayBanner}">
								<c:if test="${wonderpayBanner.deviceGubun eq 'P' or wonderpayBanner.deviceGubun eq 'A'}">
									<div class="modify-info-banner wonderpayBox" <c:if test="${orderInfo.selectPayType ne 'WONDERPAY' and orderInfo.selectPayType ne null and orderInfo.selectPayType ne ''}">style="display:none;"</c:if>>
										<img src="${IMGPATH}/banner/${wonderpayBanner.bannerIdx}/${wonderpayBanner.bannerImg}" alt="${wonderpayBanner.bannerNm}" />
									</div>
								</c:if>
							</c:if>
							<%-- //원더페이 배너 --%>
							
	                        <!-- <div class="guidebox" id="smileBox" style="margin-top:10px; display:none;">
								<div class="guide-cont">
									<h3 class="tit">스마일페이 추가 할인 혜택 안내</h3>
									<ul class="bu-list">
										<li><span class="bu">*</span> 1만원 이상 SMILEPAY 결제 시, 스마일캐시 3천원 적립 (ID당 1회)</li>
										<li><span class="bu">*</span> SMILEPAY 현대카드 1만원 이상 결제 시, 10% 청구할인 (현대카드회원 기준 1회)</li>
										<li><span class="bu">*</span> SMILEPAY 결제 시 마다, 0.5% 스마일캐시 무조건 적립</li>
									</ul>
								</div>
							</div> -->
							
							<!-- 앱결제 안내 -->
							<%--
							3월 혜택안내
							<div data-id="methodPayco" id="paycoBox" class="guidebox method-guide" style="display: none; margin-top:10px">
								<div class="guide-cont">
									<h3 class="tit">페이코 추가 할인 혜택 안내</h3>
									<ul class="bu-list">
										<li><span class="bu">*</span> 생애 첫 1만원 이상 결제 시, <span class="em">3500원 즉시 할인</span> (카드사 무제한)</li>
										<li><span class="bu">*</span> 결제 금액의 0.5% 페이코 포인트 적립</li>
									</ul>
								</div>
							</div>
							<div data-id="methodPaynow" id="paynowBox" class="guidebox method-guide" style="display: none; margin-top:10px">
								<div class="guide-cont">
									<h3 class="tit">페이나우 추가 할인 혜택 안내</h3>
									<ul class="bu-list">
										<li><span class="bu">*</span> 생애 첫 2만원 이상 결제 시, <span class="em">3000원 즉시 할인</span></li>
									</ul>
								</div>
							</div>
							 --%>
							<!-- //앱결제 안내 -->
	
	                        <div class="method-body">
	                            <%-- 
	                            <!-- 탭 : 원클릭 결제 - 없을때 -->
							<c:if test="${billkeyInfo eq null or fn:length(billkeyInfo) eq 0}">
	                            <div id="methodOneclick" class="method-item method-oneclick paytype" <c:if test="${orderInfo.selectPayType eq 'BILLKEY'}">style="display:block;"</c:if>>
	                                <h4 class="hide">원클릭 결제</h4> 
	                                <!-- 카드가 없을경우 노출 -->
									<h5 class="tit">결제에 사용할 원클릭 결제 카드가 지정되지 않았습니다.</h5>
									<p>결제 시 클릭 한 번으로 결제되는 ‘원클릭 결제 서비스’를 이용 하시겠습니까?</p>
	                                <!-- //카드가 없을경우 노출 -->
	
	                                <div class="btn-box">
	                                    <button type="button" class="btn outline-green full" data-toggle="popup" data-target="#pop_billkey_reg"><span class="txt">원클릭 결제 카드 등록</span></button>
	                                </div>	
	                            </div>
							</c:if>
	                            <!-- //탭 : 원클릭 결제 - 없을때 -->

	                            <!-- 탭 : 원클릭 결제 - 있을때 -->
							<c:if test="${billkeyInfo ne null and fn:length(billkeyInfo) gt 0}">
	                            <div id="methodOneclick" class="method-item method-oneclick paytype" <c:if test="${orderInfo.selectPayType eq 'BILLKEY'}">style="display:block;"</c:if>>
	                                <h4 class="hide">원클릭 결제</h4> 
	
									<!-- 카드가 있을경우 노출 -->
									<h5 class="tit">나의 원클릭 카드</h5>
									<div class="row">
										<div class="col col-12">
											<div class="form-control">
												<div class="opt_select">
													<select name="selectBillkeyVal" id="selectBillkeyVal" class="form-control" required>
														<option value="">원클릭카드선택</option>
													<c:forEach var="list" items="${billkeyList}" varStatus="idx">
														<option value="<c:out value="${list.billkeyIdx}"/>" data-billkey="<c:out value="${list.billkey}"/>" <c:if test="${((orderInfo.selectBillkeyVal eq null or orderInfo.selectBillkeyVal eq '') and  list.mainYn eq 'Y') or (orderInfo.selectBillkeyVal ne null and orderInfo.selectBillkeyVal ne '' and orderInfo.selectBillkeyVal eq list.billkeyIdx)}">selected</c:if>><c:out value="${list.cardNm}" /> (<c:out value="${list.financename}" />) <c:out value="${list.cardnum}" /></option>
													</c:forEach>
													</select>
												</div>
											</div>
										</div>
									</div>
	
									<div class="btn-box">
										<button type="button" class="btn outline-green full" data-toggle="popup" data-target="#pop_billkey_reg"><span class="txt">원클릭 결제 카드 추가</span></button>
									</div>
									<!-- //카드가 있을경우 노출 -->
	                            </div>
							</c:if>
	                            <!-- //탭 : 원클릭 결제 - 있을때 -->
	                             --%>
	                            <!-- 탭 : 신용카드결제 -->
	                            <div id="methodCard" class="method-item method-card paytype" <c:if test="${orderInfo.selectPayType eq 'SC0010' or orderInfo.selectPayType eq 'BILLKEY' or orderInfo.selectPayType eq null or orderInfo.selectPayType eq ''}">style="display:block;"</c:if>>
	                                <h4 class="hide">신용카드</h4> 
	                                <h5 class="tit">카드 선택</h5>   
	                                <ul class="card-list">
<%	 
/*  LGU+											
													<li><a href="javascript:void(0);" data-cardcode="61" <c:if test="${orderInfo.selectCardCode eq '61'}">class="active"</c:if>>현대카드</a></li>
													<li><a href="javascript:void(0);" data-cardcode="32" <c:if test="${orderInfo.selectCardCode eq '32'}">class="active"</c:if>>KEB하나카드</a></li>
													<li><a href="javascript:void(0);" data-cardcode="31" <c:if test="${orderInfo.selectCardCode eq '31'}">class="active"</c:if>>BC카드</a></li>
													<li><a href="javascript:void(0);" data-cardcode="71" <c:if test="${orderInfo.selectCardCode eq '71'}">class="active"</c:if>>롯데카드</a></li>
													<li><a href="javascript:void(0);" data-cardcode="11" <c:if test="${orderInfo.selectCardCode eq '11'}">class="active"</c:if>>KB국민카드</a></li>
													<li><a href="javascript:void(0);" data-cardcode="91" <c:if test="${orderInfo.selectCardCode eq '91'}">class="active"</c:if>>NH농협카드</a></li>
													<li><a href="javascript:void(0);" data-cardcode="51" <c:if test="${orderInfo.selectCardCode eq '51'}">class="active"</c:if>>삼성카드</a></li>
													<li><a href="javascript:void(0);" data-cardcode="41" <c:if test="${orderInfo.selectCardCode eq '41'}">class="active"</c:if>>신한카드</a></li>
													<li><a href="javascript:void(0);" data-cardcode="36" <c:if test="${orderInfo.selectCardCode eq '36'}">class="active"</c:if>>씨티카드</a></li>
													<li><a href="javascript:void(0);" data-cardcode="15" <c:if test="${orderInfo.selectCardCode eq '15'}">class="active"</c:if>>카카오뱅크카드</a></li>
*/
%>
													<li><a href="javascript:void(0);" data-cardcode="CCDI" class="card  <c:if test="${orderInfo.selectCardCode eq 'CCDI'}"> active </c:if>"><span>현대카드</span></a></li>
													<li><a href="javascript:void(0);" data-cardcode="CCHN" class="card  <c:if test="${orderInfo.selectCardCode eq 'CCHN'}">active</c:if> "><span>하나SK카드</span></a></li>
													<li><a href="javascript:void(0);" data-cardcode="CCBC" class="card  <c:if test="${orderInfo.selectCardCode eq 'CCBC'}">active</c:if> "><span>BC카드</span></a></li>
													<li><a href="javascript:void(0);" data-cardcode="CCLO" class="card  <c:if test="${orderInfo.selectCardCode eq 'CCLO'}">active</c:if> "><span>롯데카드</span></a></li>
													<li><a href="javascript:void(0);" data-cardcode="CCKM" class="card  <c:if test="${orderInfo.selectCardCode eq 'CCKM'}">active</c:if> "><span>KB국민카드</span></a></li>
													<li><a href="javascript:void(0);" data-cardcode="CCNH" class="card  <c:if test="${orderInfo.selectCardCode eq 'CCNH'}">active</c:if> "><span>NH농협카드</span></a></li>
													<li><a href="javascript:void(0);" data-cardcode="CCSS" class="card  <c:if test="${orderInfo.selectCardCode eq 'CCSS'}">active</c:if> "><span>삼성카드</span></a></li>
													<li><a href="javascript:void(0);" data-cardcode="CCLG" class="card  <c:if test="${orderInfo.selectCardCode eq 'CCLG'}">active</c:if> "><span>신한카드</span></a></li>
													<li><a href="javascript:void(0);" data-cardcode="CCCT" class="card  <c:if test="${orderInfo.selectCardCode eq 'CCCT'}">active</c:if> "><span>씨티카드</span></a></li>
													<li><a href="javascript:void(0);" data-cardcode="CCKA" class="card  <c:if test="${orderInfo.selectCardCode eq 'CCKA'}">active</c:if> "><span>카카오뱅크카드</span></a></li>
	                                </ul>
	                                <h5 class="tit">기타 카드 선택</h5>
	                                <div class="card-form">
	                                    <div class="row" id="etc_cart">
	                                        <div class="col col-12">
	                                            <div class="form-control">
	                                                <div class="opt_select">
														<select name="etcCardVal" id="etcCardVal" class="form-control">
															<option value="" <c:if test="${orderInfo.etcCardVal eq '' or orderInfo.etcCardVal eq null}">selected</c:if>>기타 카드선택</option>
														<%	 
/*  LGU+															
														<option value="21" <c:if test="${orderInfo.etcCardVal eq '21'}">selected</c:if>>외환카드</option>
														<option value="30" <c:if test="${orderInfo.etcCardVal eq '30'}">selected</c:if>>KDB 산업체크카드</option>
														<option value="33" <c:if test="${orderInfo.etcCardVal eq '33'}">selected</c:if>>우리(구.평화 VISA)카드</option>
														<option value="34" <c:if test="${orderInfo.etcCardVal eq '34'}">selected</c:if>>수협카드</option>
														<option value="35" <c:if test="${orderInfo.etcCardVal eq '35'}">selected</c:if>>전북카드</option>
														<option value="37" <c:if test="${orderInfo.etcCardVal eq '37'}">selected</c:if>>우체국체크카드</option>
														<option value="38" <c:if test="${orderInfo.etcCardVal eq '38'}">selected</c:if>>MG 새마을금고체크카드</option>
														<option value="39" <c:if test="${orderInfo.etcCardVal eq '39'}">selected</c:if>>저축은행체크카드</option>
														<option value="42" <c:if test="${orderInfo.etcCardVal eq '42'}">selected</c:if>>제주카드</option>
														<option value="46" <c:if test="${orderInfo.etcCardVal eq '46'}">selected</c:if>>광주카드</option>
														<option value="62" <c:if test="${orderInfo.etcCardVal eq '62'}">selected</c:if>>신협체크카드</option>
*/
%>
														<option value="CCKE" <c:if test="${orderInfo.etcCardVal eq 'CCKE'}">selected</c:if>>외환카드</option>
														<option value="CCKD" <c:if test="${orderInfo.etcCardVal eq 'CCKD'}">selected</c:if>>KDB 산업은행</option>
														<option value="CCPH" <c:if test="${orderInfo.etcCardVal eq 'CCPH'}">selected</c:if>>우리카드</option>
														<option value="CCSU" <c:if test="${orderInfo.etcCardVal eq 'CCSU'}">selected</c:if>>수협카드</option>
														<option value="CCCU " <c:if test="${orderInfo.etcCardVal eq 'CCCU '}">selected</c:if>>신협체크카드</option>
														<option value="CCPB" <c:if test="${orderInfo.etcCardVal eq 'CCPB'}">selected</c:if>>우체국체크카드</option>
														<option value="CCSM" <c:if test="${orderInfo.etcCardVal eq 'CCSM'}">selected</c:if>>MG 새마을금고체크카드</option>
														<option value="CCSB" <c:if test="${orderInfo.etcCardVal eq 'CCSB'}">selected</c:if>>저축은행체크카드</option>
														<option value="CCCJ" <c:if test="${orderInfo.etcCardVal eq 'CCCJ'}">selected</c:if>>제주카드</option>
														<option value="CCJB" <c:if test="${orderInfo.etcCardVal eq 'CCJB'}">selected</c:if>>전북카드</option>
														<option value="CCKJ" <c:if test="${orderInfo.etcCardVal eq 'CCKJ'}">selected</c:if>>광주카드</option>
														<option value="CCKK" <c:if test="${orderInfo.etcCardVal eq 'CCKK'}">selected</c:if>>케이뱅크 </option>

	<%--	해외 카드들은 결제 안 되더라
															<option value="3C" <c:if test="${orderInfo.etcCardVal eq '3C'}">selected</c:if>>중국은련카드</option>
															<option value="4J">해외 JCB카드</option>
															<option value="4V">해외 VISA카드</option>
															<option value="4M">해외 MASTER카드</option>
															<option value="6D">해외 DINERS카드</option>
															<option value="6I">해외 DISCOVER카드</option>
	 --%>
														</select>
	                                                </div>
	                                            </div>
	                                        </div>
	                                    </div>
	                                    <div class="row">
	                                        <div class="col col-6">
	                                            <button type="button" class="btn outline-green full ico-chev" data-toggle="popup" data-target="#popCardPlan" onclick="benefit(1);"><span class="txt">무이자 할부 혜택</span></button>
	                                        </div>
	                                        <!-- <div class="col col-6">
	                                            <button type="button" class="btn outline-green full ico-chev" data-toggle="popup" data-target="#popCardPlan" onclick="benefit(2);"><span class="txt">카드 할인 혜택</span></button>
	                                        </div> -->
	                                    </div>
	                                </div>
	                            </div>
	                            <!-- //탭 : 신용카드결제 -->
	
	                            <!-- 탭 : 실시간 계좌 이체 -->
	                            <div id="methodAccount" class="method-item method-account paytype" <c:if test="${orderInfo.selectPayType eq 'SC0030'}">style="display:block;"</c:if>>
	                                <h4 class="hide">실시간 계좌 이체</h4> 
	                                <h5 class="tit">에스크로 결제 여부</h5>
	                                <div class="row">
	                                    <div class="col col-12">
	                                        <div class="form-control optgroup">
	                                            <span class="radiobox">
	                                                <input type="radio" name="escrowYn1Val" value="Y" id="escrowYes" class="radio" <c:if test="${orderInfo.escrowYn1Val eq 'Y'}">checked</c:if>>
	                                                <label for="escrowYes" class="lbl">예</label>
	                                            </span>
	                                            <span class="radiobox">
	                                                <input type="radio" name="escrowYn1Val" value="N" id="escrowNo" class="radio" <c:if test="${orderInfo.escrowYn1Val eq 'N'}">checked</c:if>>
	                                                <label for="escrowNo" class="lbl">아니오</label>
	                                            </span>
	                                        </div>
	                                    </div>
	                                </div>
	                            </div>
	                            <!-- //탭 : 실시간 계좌 이체 -->
	
	                            <!-- 탭 : 가상계좌 -->
	                            <div id="methodVirtual" class="method-item method-virtual paytype" <c:if test="${orderInfo.selectPayType eq 'SC0040'}">style="display:block;"</c:if>>
	                                <h4 class="hide">가상계좌</h4> 
	                                <h5 class="tit" style="margin-top: 0;">입금은행 선택</h5>
	                                <div class="row">
	                                    <div class="col col-12">
	                                        <div class="form-control">
	                                            <div class="opt_select">
													<select name="bankCode" id="bankCode" class="form-control" required>
														<option value="" <c:if test="${orderInfo.bankCode eq '' or orderInfo.bankCode eq null}">selected</c:if>>은행선택</option>
														<c:forEach var="list" items="${bankList}">
															<option value="${list.commonCd}" data-cdNm="${list.cdNm}" <c:if test="${orderInfo.bankCode eq list.commonCd}">selected</c:if>>
																${list.cdNm}
															</option>
														</c:forEach>
													</select>
	                                            </div>
	                                        </div>
	                                    </div>
	                                </div>

	                                <h5 class="tit">에스크로 결제 여부</h5>
	                                <div class="row">
	                                    <div class="col col-12">
	                                        <div class="form-control optgroup">
	                                            <span class="radiobox">
	                                                <input type="radio" name="escrowYn2Val" value="Y" id="VirtualEscrowYes" class="radio" <c:if test="${orderInfo.escrowYn2Val eq 'Y'}">checked</c:if>>
	                                                <label for="VirtualEscrowYes" class="lbl">예</label>
	                                            </span>
	                                            <span class="radiobox">
	                                                <input type="radio" name="escrowYn2Val" value="N" id="VirtualEscrowNo" class="radio" <c:if test="${orderInfo.escrowYn2Val eq 'N'}">checked</c:if>>
	                                                <label for="VirtualEscrowNo" class="lbl">아니오</label>
	                                            </span>
	                                        </div>
	                                    </div>
	                                </div>
<%--	                                
	                                <h5 class="tit">현금영수증</h5>
	                                <div class="row">
	                                    <div class="col col-12">
	                                        <div class="form-control optgroup">
	                                            <span class="radiobox">
	                                                <input type="radio" name="cashReceiptGubun" value="1" id="cashReceipt1" class="radio" <c:if test="${orderInfo.cashReceiptGubun eq '1'}">checked</c:if>>
	                                                <label for="cashReceipt1" class="lbl">개인</label>
	                                            </span>
	                                            <span class="radiobox">
	                                                <input type="radio" name="cashReceiptGubun" value="2" id="cashReceipt2" class="radio" <c:if test="${orderInfo.cashReceiptGubun eq '2'}">checked</c:if>>
	                                                <label for="cashReceipt2" class="lbl">사업자</label>
	                                            </span>
	                                            <span class="radiobox">
	                                                <input type="radio" name="cashReceiptGubun" value="0" id="cashReceipt3" class="radio" <c:if test="${orderInfo.cashReceiptGubun eq '0' or orderInfo.cashReceiptGubun eq '' or orderInfo.cashReceiptGubun eq null}">checked</c:if>>
	                                                <label for="cashReceipt3" class="lbl">미신청</label>
	                                            </span>
	                                        </div>
	                                    </div>
	                                </div>
	                                <div class="row" <c:if test="${orderInfo.cashReceiptGubun ne '1' and orderInfo.cashReceiptGubun ne '2'}">style="display:none;"</c:if>>
	                                    <div class="col col-12">
	                                        <div class="form-control">
	                                            <input type="text" class="input" name="cashReceiptNo" id="cashReceiptNo" value="<c:out value="${orderInfo.cashReceiptNo}"/>" maxlength="20" placeholder="휴대전화 또는 사업자등록번호" />
	                                        </div>
	                                    </div>
	                                </div>
--%>
<%--
	                                <div class="row">
	                                    <div class="col col-12">
	                                        <div class="form-control">
	                                            <span class="checkbox">
	                                                <input type="checkbox" id="chkSaveReceipt" class="check" />
	                                                <label for="chkSaveReceipt" class="lbl">현재 현금영수증 정보를 저장합니다.</label>
	                                            </span>
	                                        </div>
	                                    </div>
	                                </div>
 --%>
	                            </div>
	                            <!-- //탭 : 가상계좌 -->
	
	                            <!-- 탭 : 휴대폰 결제 -->

	                            <div id="methodMobile" class="method-item method-mobile paytype" <c:if test="${orderInfo.selectPayType eq 'SC0060'}">style="display:block;"</c:if>>
	                                <h4 class="hide">휴대폰 결제</h4> 
	                                * 휴대폰 결제취소는 결제한 해당월 말일까지 가능합니다.
	                            </div>
	                            <!-- //탭 : 휴대폰 결제 -->
								
								<!-- 탭 : SmilePay 결제 -->
	                           	<div id="methodSmilePay" class="method-item method-smilepay paytype" <c:if test="${orderInfo.selectPayType eq 'SMILEPAY'}">style="display:block;"</c:if>>
									<ul class="bu-list">
										<li>
											<i class="bu">*</i>
											스마일페이는 신용카드, 은행계좌, 휴대폰 명의자와
											SMS인증받는 휴대폰 명의자가 동일해야 합니다.
										</li>
										<li>
											<i class="bu">*</i>
											개인ID+법인 개인 명의 카드, 법인ID+법인 개인 명의 
											카드의 경우는 등록 가능하나, 법인ID+법인 명의
											카드는 등록 불가합니다.
										</li>
										<li>
											<i class="bu">*</i>
											스파일페이 카드, 휴대폰은 100원 이상, 은행계좌는
											천원 이상 구매 시 결제 가능합니다.
										</li>
									</ul>
                            	</div>
	                           	 <!-- //탭 : SmilePay 결제 -->
								
	                            <!-- 탭 : PAYCO 결제 -->
	                            <div id="methodPayco" class="method-item method-payco paytype" <c:if test="${orderInfo.selectPayType eq 'PAYCO'}">style="display:block;"</c:if>>
									<ul class="bu-list">
										<li>
											<i class="bu">*</i>
											PAYCO는 온/오프라인 쇼핑은 물론 송금, 멤버십 적립
											까지 가능한 통합 서비스입니다.
										</li>
										<li>
											<i class="bu">*</i>
											휴대폰과 카드 명의자가 동일해야 결제 가능하며, 
											결제금액 제한은 없습니다.
											지원카드 : 모든 국내 신용/체크카드
										</li>
										<li>
											<i class="bu">*</i>
											지원카드 : 모든 국내 신용/체크카드
										</li>
									</ul>
	                            </div>
	                            <!-- //탭 : PAYCO 결제 -->
	                            
	                            <!-- 탭 : PAYNOW 결제 -->
	                            <div id="methodPaynow" class="method-item method-paynow paytype" <c:if test="${orderInfo.selectPayType eq 'PAYNOW'}">style="display:block;"</c:if>>
									<ul class="bu-list">
										<li>
											<i class="bu">*</i>
											페이나우 앱이 설치되어 있지 않을 경우 Play 스토어
											및 App Store로 연결됩니다. 
										</li>
										<li>
											<i class="bu">*</i>
											앱 설치 후 회원가입이 필요하며, 등록할 신용카드는
											휴대폰 명의자와 일치해야 합니다.
										</li>
										<li>
											<i class="bu">*</i>
											지원카드 : BC카드, 하나카드, 신한카드, 삼성카드,
											국민카드, 현대카드, NH농협카드, 롯데카드  
										</li>
									</ul>
	                            </div>
	                            <!-- //탭 : PAYNOW 결제 -->
	                             <!-- 탭 : 원더페이 결제 -->
	                            <div id="methodWonder" class="method-item method-paynow paytype" <c:if test="${orderInfo.selectPayType eq 'WONDERPAY'}">style="display:block;"</c:if>>
									<ul class="bu-list">
<!-- 										<li>
											<i class="bu">*</i>
											페이나우 앱이 설치되어 있지 않을 경우 Play 스토어
											및 App Store로 연결됩니다. 
										</li>
										<li>
											<i class="bu">*</i>
											앱 설치 후 회원가입이 필요하며, 등록할 신용카드는
											휴대폰 명의자와 일치해야 합니다.
										</li>
										<li>
											<i class="bu">*</i>
											지원카드 : BC카드, 하나카드, 신한카드, 삼성카드,
											국민카드, 현대카드, NH농협카드, 롯데카드  
										</li> -->
									</ul>
	                            </div>
	                            <!-- //탭 : 원더페이 결제 -->
	                            <div class="method-confirm">
	                                <div class="row">
	                                    <div class="col col-12">
	                                        <div class="form-control">
	                                            <span class="checkbox">
	                                                <input type="checkbox" name="useThisPayTypeToNext" id="useThisPayTypeToNext" value="Y" class="check" <c:if test="${orderInfo.useThisPayTypeToNext eq 'Y'}">checked</c:if>>
	                                                <label for="useThisPayTypeToNext" class="lbl">지금 선택하신 결제수단을 다음에도 사용</label>
	                                            </span>
	                                        </div>
	                                        <div class="form-control">
	                                            <span class="checkbox">
	                                                <input type="checkbox" name="agreement" id="agreement" value="Y" class="check" <c:if test="${orderInfo.agreement eq 'Y'}">checked</c:if>>
	                                                <label for="agreement" class="lbl">위 상품의 판매조건을 명확히 확인하였으며, <br/> 구매 진행에 동의합니다. (전자상거래법 제 8조 2항)</label>
	                                            </span>
	                                        </div>
	                                        <ul class="bu-list type-extra">
												<li><span class="bu">※</span> "포인트"는 현금화 할 수 없으며 회원 탈퇴 시, 자동 소멸됩니다.</li>
											</ul>
	                                    </div>
	                                </div>
	                                <div class="btn-box confirm">
	                                    <button type="button" class="btn black" onclick="javascript:payment();"><span class="txt">결제하기</span></button>
	                                </div>
	                            </div>
	
	                        </div>
                        </div>

                    </div>
                </div>
                <!-- //결제 정보 -->
			</c:otherwise>
		</c:choose>
            
            </div>
        </div>

	</form>
	
				
<input type="hidden" id="myPoint" value="${memberDetail.pointPrice}">
<input type="hidden" id="myPointApply" value="${orderInfo.totalPointPrice}">
		
<form method="post" name="frm_kcp" id="frm_kcp" action="${CTX}/order/kcpPayRes.do">
<c:out value="${kcpHiddenParams}" escapeXml="false"/>
</form>
		
<form method="post" name="frm_lguplus" id="frm_lguplus" action="${CTX}/order/payres.do">
<c:out value="${reqMapHiddenParams}" escapeXml="false"/>
<c:out value="${escrowGoodsHiddenParams}" escapeXml="false"/>
</form>

<form method="post" name="frm_lgcyber" id="frm_lgcyber" action="${CTX}/order/cyberpayres.do">
<c:out value="${cyberMapHiddenParams}" escapeXml="false"/>
</form>

<form method="post" name="frm_billkeyreg" id="frm_billkeyreg" >
<c:out value="${billkeyRegMapHiddenParams}" escapeXml="false"/>
</form>

<!-- 빌키 결제 -->
<form method="post" name="frm_billkeyauth" id="frm_billkeyauth" >
<c:out value="${billkeyAuthMapHiddenParams}" escapeXml="false"/>
</form>				

<form name="frm_payco" id="frm_payco" method="post" action="">
	<input type="hidden" name="PayMethod"		id="PayMethod"	 	value="PAYCO"/> 
	<input type="hidden" name="code"   id="code"  value=""/>
    <input type="hidden" name="message"   id="message"  value=""/>
	<input type="hidden" name="reserveOrderNo"   id="reserveOrderNo"  value=""/>
	<input type="hidden" name="sellerOrderReferenceKey"   id="sellerOrderReferenceKey"  value=""/>
	<input type="hidden" name="paymentCertifyToken"   id="paymentCertifyToken"  value=""/>
	<input type="hidden" name="totalPaymentAmt"   id="totalPaymentAmt"  value=""/>
	<input type="hidden" name="sessionId"    value=""/>
</form>

<!-- 원더페이 -->
<form name="frm_wonderpay" id="frm_wonderpay" method="post" action="${wonderpay.callUrl}" accept-charset="euc-kr">
	<input type="hidden" name="site_cd"    value="${wonderpay.siteCd}"/>
    <input type="hidden" name="good_name"   value="${wonderpay.goodName}"/>
    <input type="hidden" name="good_mny"   value="${wonderpay.goodMny}"/>
    <input type="hidden" name="buyr_name"   value="${wonderpay.buyrName}"/>
    <input type="hidden" name="buyr_mail"   value="${wonderpay.buyrMail}"/>
    <input type="hidden" name="buyr_tel2"   value="${wonderpay.buyrTel2}"/>
    <input type="hidden" name="ret_url"   value="${wonderpay.retUrl}"/>
    <input type="hidden" name="ordr_idxx"   value="${wonderpay.ordrIdxx}"/>
    <input type="hidden" name="ordr_info"   value="${wonderpay.ordrInfo}"/>
    <input type="hidden" name="buyr_tel1"   value="${wonderpay.buyrTel1}"/>
</form>
			

<div id="payco_layer" style="display:none;">
	<div id="payco_layer_div" style="position:fixed; top:0; left:0; width:100%; height:100%; z-index:2000000000000000000000000;">
		<div style="position:absolute; top:0; left:0; width:100%; height:100%; background:#000; background:url(https://xpay.lgdacom.net/xpay/image/red_v25/common/bg.png); line-height:450px;"></div>
		<div style="width:720px;height:650px;border-radius:5px;text-align:center;position:absolute;top:50%;left:50%;margin-left:-325px;margin-top:-325px;z-index:2000000000000000000000; background:#fff;">

	<a href="javascript:payco_payment_return('', '', '', 0, '9999');" style="position:absolute;top:-40px;left:710px;">
		<img src="${CTX}/images/${DEVICE}/contents/ico_pop_close1.png"  alt="닫기">
	</a>

		<iframe id="paycoFrame" name="paycoFrame" src="" width="100%" height="100%" scrolling="no" style="width:100%; height:100%; z-index: 2000000000000000000000; border:none; border-radius:5px;"></iframe>
		</div>
		</div>
</div>

<%-- 스마일 페이 레이어 팝업 --%>
<div  id="smilePay_layer_div"  style="display: none" >
	<div  style="position:fixed; top:0; left:0; width:100%; height:100%; z-index:1000;">
		<div style="position:absolute; top:0; left:0; width:100%; height:100%; background:#000; background:url(https://xpay.lgdacom.net/xpay/image/red_v25/common/bg.png); line-height:450px;"></div>
	</div>
	<div id="smilePay_layer" >
	</div>
</div>


<%-- 다음 우편번호 팝업 --%>
<div id="post_layer_div" style="display:none;position:fixed;overflow:hidden;z-index:2000000000000000000000000;-webkit-overflow-scrolling:touch;">
<img src="//t1.daumcdn.net/localimg/localimages/07/postcode/320/close.png" id="btnCloseLayer" style="cursor:pointer;position:absolute;right:-3px;top:-3px;z-index:1" onclick="closeDaumPostcode()" alt="닫기 버튼">
	<div  style="position:fixed; top:0; left:0; width:100%; height:100%;z-index:-1 ">
		<div style="position:absolute; top:0; left:0; width:100%; height:100%; background:#000; background:url(https://xpay.lgdacom.net/xpay/image/red_v25/common/bg.png); line-height:450px;"></div>
	</div>
</div>

 
<!-- 배송비 쿠폰 조회 -->
<div id="PopShippingCoupon" class="popup type-page">
	<h1 class="hide">GATSBY</h1>
	<!-- pop-top -->
	<div class="pop-top">
		<h2>배송비 쿠폰 조회</h2>
	</div>
	<!-- //pop-top -->
	<!-- pop-mid -->
	<div class="pop-mid">
		
		<div class="coupon-form">
			<h3>배송비 쿠폰</h3>
			<div class="row">
				<div class="col col-12">
					<div class="form-control">
						<span class="opt_select">
							<select id="shipping_coupon_select" required onchange="popShippingCouponChange();">
							<option value="">쿠폰을 선택해 주세요.</option>
							<c:choose>
								<c:when test="${empty shippingCouponList}">
									<option value="">사용 가능한 쿠폰이 없습니다.</option>
								</c:when>
								<c:otherwise>
									<c:forEach var="list" items="${shippingCouponList}" varStatus="idx">
										<c:set var="shipping_coupon_value" value="${list.COUPON_MEMBER_IDX}"/>			
										<c:if test="${list.COUPON_MEMBER_IDX == 0}" >
											<c:set var="shipping_coupon_value" value="${list.COUPON_IDX*-1}"/>
										</c:if>
										
										<c:set var="shipping_select" value=""/>			
										<c:if test="${shipping_coupon_value eq orderInfo.freeShippingCouponIdx}" >
											<c:set var="shipping_select" value="selected"/>				
										</c:if>
										<option value="${shipping_coupon_value}" data-discount="${list.DISCOUNT}" ${shipping_select}>${list.COUPON_NM}</option>
									</c:forEach>
								</c:otherwise>
							</c:choose>
							</select>
						</span>
					</div>
				</div>
			</div>

			<div class="payment-final">
				<ul>
					<li>
						<div class="item">
							<strong>할인금액</strong>
							<p id="shipping_discount"><fmt:formatNumber value="${orderInfo.freeShippingCouponPrice}" groupingUsed="true"/></p>
						</div>
					</li>
				</ul>
			</div>
		</div>

		<div class="btn-box confirm">
			<button type="button" class="btn outline-green" onclick="applyShippingCoupon(false);"><span class="txt">선택 쿠폰 적용</span></button>
		</div>
	</div>
	<!-- //pop-mid -->
	<a href="javascript:void(0);" class="btn_close" data-dismiss="popup"><span class="hide">닫기</span></a>
</div>

<!-- 장바구니 쿠폰 조회 -->
<div id="popCartCoupon" class="popup type-page">
	<h1 class="hide">GATSBY</h1>
	<!-- pop-top -->
	<div class="pop-top">
		<h2>장바구니 쿠폰 조회</h2>
	</div>
	<!-- //pop-top -->
	<!-- pop-mid -->
	<div class="pop-mid">
		
		<div class="coupon-form">
			<h3>장바구니 쿠폰</h3>
			<div class="row">
				<div class="col col-12">
					<div class="form-control">
						<span class="opt_select">
							<select id="cart_coupon_select" required onchange="popCartCouponChange();">
								<option value="">쿠폰을 선택해 주세요.</option>
									<c:choose>
										<c:when test="${empty cartCouponList }">
											<option value="">사용 가능한 쿠폰이 없습니다.</option>
										</c:when>
										<c:otherwise>
											<c:forEach var="list" items="${cartCouponList }" varStatus="idx">
													<c:set var="coupon_value" value="${list.COUPON_MEMBER_IDX}"/>			
													<c:if test="${list.COUPON_MEMBER_IDX == 0}" >
														<c:set var="coupon_value" value="${list.COUPON_IDX*-1}"/>
													</c:if>
													<c:set var="cart_select" value=""/>			
													<c:if test="${coupon_value eq orderInfo.cartCouponIdx}" >
														<c:set var="cart_select" value="selected"/>				
													</c:if>
													<option value="${coupon_value}" data-discount="${list.DISCOUNT_PRICE}" ${cart_select}>${list.COUPON_NM}</option>
											</c:forEach>
										</c:otherwise>
									</c:choose>
							</select>
						</span>
					</div>
				</div>
			</div>

			<div class="payment-final">
				<ul>
					<li>
						<strong>할인금액</strong>
						<p id="cart_discount"><fmt:formatNumber value="${orderInfo.totalCartCouponPrice}" groupingUsed="true"/></p>
					</li>
				</ul>
			</div>
		</div>

		<div class="btn-box confirm">
			<button type="button" class="btn outline-green" onclick="applyCartCoupon(false);"><span class="txt">선택 쿠폰 적용</span></button>
		</div>
	</div>
	<!-- //pop-mid -->
	<a href="javascript:void(0);" class="btn_close" data-dismiss="popup"><span class="hide">닫기</span></a>
</div>

<!-- 상품 쿠폰 조회 -->
<div id="PopGoodsCoupon" class="popup type-page">
	<h1 class="hide">GATSBY</h1>
	<!-- pop-top -->
	<div class="pop-top">
		<h2>상품 쿠폰 조회</h2>
	</div>
	<!-- //pop-top -->
	<!-- pop-mid -->
	<div class="pop-mid">
		
		<div class="order-goods type-coupon">
			<ul id="pop_goods_coupon_list">
		<c:if test="${list ne null and fn:length(list) gt 0}">
			<c:set var="totalOrderPrice" value="0"/>	<%-- 총 결제금액 --%>
			<c:forEach var="list" items="${list}" varStatus="idx">
				<li class="product-info" data-detailidx="${list.orderDetailIdx}" data-giftcouponidx="${list.giftCouponIdx}" data-oldgiftcouponidx="${list.giftCouponIdx}">
					<div class="item">
						<div class="item-view">
							<div class="view-thumb">
		                  		<c:set var="imgSplit" value="${fn:split(list.mainFile ,'.') }"/>
								<img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}" onerror="this.src='${CTX}/images/${DEVICE}/noimage.jpg'" alt="상품 썸네일 이미지">
							</div>
							<div class="view-info">
								<div class="badge-box">
									<c:if test="${list.opoYn eq 'Y' }" ><span class="badge type1">1+1</span></c:if>
									<c:if test="${list.tpoYn eq 'Y' }" ><span class="badge type2">2+1</span></c:if>
									<c:if test="${list.newYn eq 'Y' }" ><span class="badge type3">NEW</span></c:if>
									<c:if test="${list.bestYn eq 'Y' }" ><span class="badge type4">BEST</span></c:if>
									<c:if test="${list.saleiconYn eq 'Y' }" ><span class="badge type5">SALE</span></c:if>
									<c:if test="${list.pointiconYn eq 'Y' }" ><span class="badge type3">POINT <em>X2</em></span></c:if>
								</div>
								<c:choose>
									<c:when test="${list.brandIdx eq 1}"><p class="text-gatsby">${list.brandNm}</p></c:when>		
									<c:when test="${list.brandIdx eq 3}"><p class="text-bifesta">${list.brandNm}</p></c:when>
									<c:when test="${list.brandIdx eq 4}"><p class="text-lucidol">${list.brandNm}</p></c:when>
									<c:when test="${list.brandIdx eq 6}"><p class="text-mama">${list.brandNm}</p></c:when>
									<c:when test="${list.brandIdx eq 7}"><p class="text-dental">${list.brandNm}</p></c:when>
									<c:when test="${list.brandIdx eq 8}"><p class="text-charley">${list.brandNm}</p></c:when>
								</c:choose>															
								<p class="name"><c:out value="${list.goodsNm}"/></p>
								<p class="price">수량: <fmt:formatNumber value="${list.orderCnt}" groupingUsed="true"/></p>
							</div>
						</div>
						<div class="row">
							<div class="col col-12">
								<div class="form-control">
									<span class="opt_select">
										<select id="gift_coupon_select_${list.orderDetailIdx}" required onchange="popGiftCouponChange('${list.orderDetailIdx}');" onfocus="popGiftCouponFocus('${list.orderDetailIdx}');">
											<option value="">쿠폰을 선택해 주세요.</option>
										</select>
									</span>
								</div>
							</div>
						</div>
						<div class="payment-final">
							<ul>
								<li>
									<strong>할인금액</strong>
									<p id="gift_discount_${list.orderDetailIdx}">-<fmt:formatNumber value="${list.giftCouponPrice}" groupingUsed="true"/></p>
								</li>
							</ul>
						</div>
					</div>
				</li>
			</c:forEach>
		</c:if>

				<li>
					<div class="payment-final">
						<ul>
							<li class="total">
								<strong>총 할인금액</strong>
								<p id="total_goods_discount">-<fmt:formatNumber value="${orderInfo.totalGiftCouponPrice}" groupingUsed="true"/></p>
							</li>
						</ul>
					</div>
				</li>
			</ul>
		</div>

		<div class="btn-box confirm">
			<button type="button" class="btn outline-green" onclick="applyGiftCoupon();"><span class="txt">쿠폰 적용</span></button>
		</div>
	</div>
	<!-- //pop-mid -->
	<a href="javascript:void(0);" class="btn_close" data-dismiss="popup"><span class="hide">닫기</span></a>
</div>

	<!-- 무이자 할부 안내 레이어 -->
	<div id="popCardPlan" class="popup type-page popup-cardplan">
    	<h1 class="hide">GATSBY</h1>
		<div class="pop-top">
			<h2 id="purchaseTitle"></h2>
		</div>
		<div class="pop-mid">
			<div id="purchaseDesc1" style="display:none;">
				<c:out value="${cardPlan.mHtmlinfoDesc}" escapeXml="false"/>
			</div>
			<div id="purchaseDesc2" style="display:none;">
				<c:out value="${cardBenefit.mHtmlinfoDesc}" escapeXml="false"/>
			</div>
	        <div class="btn-box confirm">
	            <button type="button" class="btn black" data-dismiss="popup"><span class="txt">확인</span></button>
	        </div>
		</div>
	    <a href="javascript:void(0);" class="btn_close" data-dismiss="popup"><span class="hide">닫기</span></a>
	</div>

<!-- 원클릭 결제 카드 관리 -->
<div id="pop_billkey_reg" class="popup type-page popup-oneclick-manage">
	<form name="insertCardForm" id="insertCardForm" method="post" >
	<h1 class="hide">GATSBY</h1>
	<!-- pop-top -->
	<div class="pop-top">
		<h2>원클릭 결제 카드 관리</h2>
	</div>
	<!-- //pop-top -->
	<!-- pop-mid -->
	<div class="pop-mid">
	
		<div class="order-form">
			<div class="form-group">
				<div class="form-body">
					<div class="row">
						<div class="form-label">
							<label for="">카드명</label>
						</div>
						<div class="col col-12">
							<div class="form-control">
								<input type="text" class="input" placeholder="카드 정보를 구별할 수 있는 별명 입력" id="cardNm" name="cardNm"/>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-label">
							<label for="">카드 종류</label>
						</div>
						<div class="col col-12">
							<div class="optgroup">
								<span class="radiobox">
									<input type="radio" name="cardKind" id="cardType1" value="1" checked="checked" class="radio" />
									<label for="cardType1" class="lbl">개인</label>
								</span>
								<span class="radiobox">
									<input type="radio" name="cardKind" id="cardType2" value="2" class="radio" />
									<label for="cardType2" class="lbl">법인</label>
								</span>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-label">
							<label for="">원클릭 결제카드</label>
						</div>
						<div class="col col-12">
							<div class="optgroup">
								<span class="radiobox">
									<input type="radio" name="mainYn" id="mainYn1" value="Y" checked="checked" class="radio" />
									<label for="mainYn1" class="lbl">지정</label>
								</span>
								<span class="radiobox">
									<input type="radio" name="mainYn" id="mainYn2" value="N" class="radio" />
									<label for="mainYn2" class="lbl">미지정</label>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="btn-box confirm">
			<button type="button" class="btn outline-green" onclick="insertOneClickCard();"><span class="txt">저장</span></button>
		</div>
		
		<div class="guidebox" style="margin-left:0; margin-right: 0;">
			<div class="guide-cont no_bg">
				<ul class="bu-list">
					<li><span class="bu">-</span> 원클릭 결제 카드로 설정된 카드는 원클릭 결제 시 자동으로 결제에 사용됩니다.</li>
					<li><span class="bu">-</span> 카드정보는 (주)KCP 전자결제에서만 관리되며 면역공방에서는 저장되지 않습니다.</li>
					<li><span class="bu">-</span> 휴대폰 분실 등을 통한 타인의 자동결제에 대해서는 면역공방에서 책임지지 않습니다.</li>
				</ul>
			</div>
		</div>
		
	</div>
	<!-- //pop-mid -->
	<a href="javascript:void(0);" class="btn_close" data-dismiss="popup"><span class="hide">닫기</span></a>
	</form>
</div>

<!-- 배송지 수정 -->
<div id="popModifyAddress" class="popup type-page">
	<h1 class="hide">GATSBY</h1>
	<!-- pop-top -->
	<div class="pop-top">
		<h2>배송지 수정</h2>
	</div>
	<!-- //pop-top -->
	<!-- pop-mid -->
	<div class="pop-mid">
	
		<div class="order-form">
			<div class="form-group">
				<div class="form-body">
					<div class="row">
						<div class="col col-12">
							<div class="form-label">
								<label for="">배송지 정보</label>
							</div>
							<div class="form-control">
								<input type="text" name="newShippingNm" id="newShippingNm" value="" maxlength="25" class="input" placeholder="배송지명" required>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
								<input type="text" name="newReceiverNm" id="newReceiverNm" value="" maxlength="50" class="input" placeholder="받으시는 분" required>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
								<input type="tel" name="newReceiverPhoneNo" id="newReceiverPhoneNo" value="" maxlength="20" onblur="exitInput(this);" class="input" placeholder="휴대폰번호 (‘-’를 제외한 숫자만 입력)" required>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
								<input type="tel" name="newReceiverTelNo" id="newReceiverTelNo" value="" maxlength="20" onblur="exitInput(this);" class="input" placeholder="일반전화 (’-’를 제외한 숫자만 입력)">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col col-7">
							<div class="form-control">
								<input type="tel" name="newReceiverZipCd" id="newReceiverZipCd" value="" maxlength="5" onblur="exitInput(this);" class="input" placeholder="우편번호" readonly="readonly">
							</div>
						</div>
						<div class="col col-5">
							<button type="button" class="btn full ico-chev" onclick="newAddrPopup();"><span class="txt">우편번호 검색</span></button>
						</div>
					</div>
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
								<input type="text" name="newReceiverAddr" id="newReceiverAddr" value="" maxlength="100" class="input" placeholder="주소" required readonly>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
								<input type="text" name="newReceiverAddrDetail" id="newReceiverAddrDetail" value="" maxlength="100" class="input" placeholder="나머지 주소">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
								<span class="checkbox">
									<input type="checkbox" name="newSetAsDefaultAddress" id="newSetAsDefaultAddress" value="Y" class="check">
									<label for="newSetAsDefaultAddress" class="lbl">기본 배송지로 설정</label>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="btn-box confirm">
			<button type="button" class="btn" data-dismiss="popup" onclick="javascript:cancelModifyAddress();"><span class="txt">취소</span></button>
			<button type="button" class="btn outline-green" onclick="javascript:saveAddress();"><span class="txt">수정</span></button>
		</div>
		
	</div>
	<!-- //pop-mid -->
	<a href="javascript:void(0);" class="btn_close" data-dismiss="popup" onclick="javascript:cancelModifyAddress();"><span class="hide">닫기</span></a>	
</div>

</body>
</html>