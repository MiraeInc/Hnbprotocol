<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<%@ page import="java.util.*" %>

<%
	HashMap payReqMap = (HashMap)session.getAttribute("PAYREQ_MAP");//결제 요청시, Session에 저장했던 파라미터 MAP 

/*
  payreq_crossplatform.jsp 에서 세션에 저장했던 파라미터 값이 유효한지 체크 
  세션 유지 시간(로그인 유지시간)을 적당히 유지 하거나 세션을 사용하지 않는 경우 DB처리 하시기 바랍니다.
*/
	if(payReqMap == null) 
	{
		out.println("세션이 만료 되었거나 유효하지 않은 요청 입니다.");
		return;
	}
%>
<html>
<head>
	<script type="text/javascript">
		function setLGDResult() {
			document.getElementById('LGD_PAYINFO').submit();
		}
		
	</script>
</head>
<body onload="setLGDResult()">
<% 
String LGD_RESPCODE = request.getParameter("LGD_RESPCODE");
String LGD_RESPMSG 	= request.getParameter("LGD_RESPMSG");
String LGD_PAYKEY	= "";

if("0000".equals(LGD_RESPCODE)){
	LGD_PAYKEY = request.getParameter("LGD_PAYKEY");
	payReqMap.put("LGD_RESPCODE"	, LGD_RESPCODE);
	payReqMap.put("LGD_RESPMSG"		, LGD_RESPMSG);
	payReqMap.put("LGD_PAYKEY"		, LGD_PAYKEY);

%><form method="post" name="LGD_PAYINFO" id="LGD_PAYINFO" action="${CTX}/order/payres.do"><%		
	for(Iterator i = payReqMap.keySet().iterator(); i.hasNext();){
		Object key = i.next();
		out.println("<input type='hidden' name='" + key + "' value='" + payReqMap.get(key) + "'>" );
	}
%></form><%
}
else{
	out.println("LGD_RESPCODE:" + LGD_RESPCODE + " ,LGD_RESPMSG:" + LGD_RESPMSG); //인증 실패에 대한 처리 로직 추가
%>
<script type="text/javascript">
alert("LGD_RESPCODE:<%=LGD_RESPCODE%> ,LGD_RESPMSG:<%=LGD_RESPMSG%>");
location.replace("${CTX}/main.do");
</script>
<%
}
%>

</body>

</html>