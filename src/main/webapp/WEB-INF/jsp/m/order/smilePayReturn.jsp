<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="x-ua-compatible" content="ie=edge">
<title>스마일페이 결제 결과</title>

<script type="text/javascript">
	window.onload = function returnPayResult(){
		window.opener.smilepay_L.t_url('${message}');
	}
</script>
</head>
</html>