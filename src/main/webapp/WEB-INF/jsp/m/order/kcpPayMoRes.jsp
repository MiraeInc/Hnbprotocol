<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<title>KCP 결제 결과</title>
<script src="${CTX}/js/jquery-3.2.1.min.js"></script>
<script src="${CTX}/js/${DEVICE}/cart.js"></script>
<script type="text/javascript">

		function setResult() {
			try {
				var code							= "${reqMap.res_cd}";
				var message						= "${reqMap.res_msg}";
				
			   	if (code == "0000"){
			   		var frm = document.frm_kcp;
					frm.submit();
			   	}else{
			   		alert("코드:"+code+"\n메시지:"+message);	
			   	  	location.replace("${CTX}/main.do");
			   	}

			} catch (e) {
				alert(e.message);
			}
		}
		
	</script>
</head>
<body onload="setResult()">

<form method="post" name="frm_kcp" id="frm_kcp" action="${CTX}/order/kcpPayRes.do">
<c:out value="${HiddenParams}" escapeXml="false"/>
</form>
		
</body>
</html>