<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<title>원더페이 결제 결과</title>
<script src="${CTX}/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript">

	function setResult() {
		try {
		
			var res_cd 			= "${reqMap.res_cd}";
			var res_msg			= "${reqMap.res_msg}";
			var pay_type			= "${reqMap.pay_type}";
			var ordr_idxx			= "${reqMap.ordr_idxx}";
			var good_mny		= "${reqMap.good_mny}";
			var coupon_use_yn = "${reqMap.coupon_use_yn}";
			var coupon_mny	= "${reqMap.coupon_mny}";
			var cash_receipt_flag	 = "${reqMap.cash_receipt_flag}";
			var enc_code = "${reqMap.enc_code}";
		
		   	if (res_cd == "0000"){
		   		$("#frm_wonder_res input:hidden[name='res_cd']").val(res_cd);
		   		$("#frm_wonder_res input:hidden[name='res_msg']").val(res_msg);
		   		$("#frm_wonder_res input:hidden[name='pay_type']").val(pay_type);
		   		$("#frm_wonder_res input:hidden[name='ordr_idxx']").val(ordr_idxx);
		   		$("#frm_wonder_res input:hidden[name='good_mny']").val(good_mny);
		   		$("#frm_wonder_res input:hidden[name='coupon_use_yn']").val(coupon_use_yn);
		   		$("#frm_wonder_res input:hidden[name='coupon_mny']").val(coupon_mny);	   		
		   		$("#frm_wonder_res input:hidden[name='cash_receipt_flag']").val(cash_receipt_flag);
		   		$("#frm_wonder_res input:hidden[name='enc_code']").val(enc_code);

		   		$("form[name='frm_wonder_res']").attr("action", "${CTX}/order/wonderRes.do");
		   		$("form[name='frm_wonder_res']").submit();
		   	}else{
		   		if (res_cd != "WEND") {
		   			alert("에러코드:"+res_cd);
		   		}
		   	  	location.replace("${CTX}/main.do");
		   	}
	
		} catch (e) {
			alert(e.message);
		}
	}
	</script>
</head>
<body onload="setResult()">
<c:choose>
<c:when test="${reqMap.res_cd eq '0000'}">
처리중....
</c:when>
<c:otherwise>
<p>결과코드 : ${reqMap.res_cd}</p>
<p>${reqMap.res_msg}</p>
</c:otherwise>
</c:choose>

<form name="frm_wonder_res" id="frm_wonder_res" method="post" >
	<input type="hidden" name="res_cd" />
    <input type="hidden" name="res_msg" />
    <input type="hidden" name="pay_type"/>
    <input type="hidden" name="ordr_idxx"/>
    <input type="hidden" name="good_mny"/>
    <input type="hidden" name="coupon_use_yn"/>
    <input type="hidden" name="coupon_mny"/>
    <input type="hidden" name="cash_receipt_flag"/>
    <input type="hidden" name="enc_code"/>
</form>
</body>

</html>