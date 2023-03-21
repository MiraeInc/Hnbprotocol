<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
	<script src="${CTX}/js/jquery-3.2.1.min.js"></script>
	<script src="${CTX}/js/${DEVICE}/cart.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#sessionId").val(getSessionId());

			var frm = document.cartForm;
			frm.action = "${CTX}/cart.do";
			frm.submit();
		});
		
		<%-- 세션ID --%>
		function getSessionId(){
			if(localStorage.getItem("mandomSessionId") == null){
				localStorage.setItem("mandomSessionId","<%=session.getId()%>");
			}
		
			return localStorage.getItem("mandomSessionId");
		}
	</script>
</head>
<body>
	<form name="cartForm" id="cartForm"  method="post">
		<input type="hidden" name="sessionId" id="sessionId" value="" />
	</form>
</body>
</html>