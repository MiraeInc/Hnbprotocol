<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<title>Payco 결제 결과</title>
<script src="${CTX}/js/jquery-3.2.1.min.js"></script>
<script src="${CTX}/js/${DEVICE}/cart.js"></script>
<script type="text/javascript">
		//세션ID
		function getSessionId(){
			if(localStorage.getItem("mandomSessionId") == null){
				localStorage.setItem("mandomSessionId","<%=session.getId()%>");
			}
		
			return localStorage.getItem("mandomSessionId");
		}

		function setResult() {
			try {
			
			var reserveOrderNo 			= "${reqMap.reserveOrderNo}";
			var sellerOrderReferenceKey	= "${reqMap.sellerOrderReferenceKey}";
			var paymentCertifyToken		= "${reqMap.paymentCertifyToken}";
			var totalPaymentAmt			= "${reqMap.totalPaymentAmt}";
			var code							= "${reqMap.code}";
			var message						= "${reqMap.message}";
			
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
		   		alert("PAYCO 에러코드:"+code);	
		   	  	location.replace("${CTX}/main.do");
		   	}

			} catch (e) {
				alert(e.message);
			}
		}
		
	</script>
</head>
<body onload="setResult()">
<%--
<p>결과코드 : ${reqMap.code}</p>
<p>${reqMap.message}</p>
 --%>
<form name="frm_payco" id="frm_payco" method="post" action="">
	<input type="hidden" name="PayMethod" id="PayMethod" value="PAYCO"/> 
	<input type="hidden" name="code" id="code" value=""/>
    <input type="hidden" name="message" id="message" value=""/>
	<input type="hidden" name="reserveOrderNo" id="reserveOrderNo" value=""/>
	<input type="hidden" name="sellerOrderReferenceKey" id="sellerOrderReferenceKey" value=""/>
	<input type="hidden" name="paymentCertifyToken" id="paymentCertifyToken" value=""/>
	<input type="hidden" name="totalPaymentAmt" id="totalPaymentAmt" value=""/>
	<input type="hidden" name="sessionId" value=""/>
</form>

</body>
</html>