<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>npay 찜등록</title>
</head>
<body>

<form name="frm" method="GET" action="${resultMap.orderUrl}"> 
<input type="hidden" name="SHOP_ID" value="${resultMap.merchantNo}">
<input type="hidden" name="ITEM_ID" value="${resultMap.itemId}"> 
 </form>
 <script>
 document.frm.target = "_top"; 
 document.frm.submit();
 </script>
</body>
</html>