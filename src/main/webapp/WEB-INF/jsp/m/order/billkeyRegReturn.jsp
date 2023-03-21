<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<title>Gatsby Mall BillKey 등록 결과</title>
<script src="${CTX}/js/jquery-3.2.1.min.js"></script>
<script src="${CTX}/js/${DEVICE}/cart.js"></script>
<script type="text/javascript">
		function setLGDResult() {
			try {
				if ("${reqMap.LGD_RESPCODE}" == "0000") {
						$("#frm_billkeyreg input:hidden[name='LGD_RESPCODE']").val("${reqMap.LGD_RESPCODE}");
						$("#frm_billkeyreg input:hidden[name='LGD_RESPMSG']").val("${reqMap.LGD_RESPMSG}");
						$("#frm_billkeyreg input:hidden[name='LGD_BILLKEY']").val("${reqMap.LGD_BILLKEY}");
						$("#frm_billkeyreg input:hidden[name='LGD_PAYTYPE']").val("${reqMap.LGD_PAYTYPE}");
						$("#frm_billkeyreg input:hidden[name='LGD_PAYDATE']").val("${reqMap.LGD_PAYDATE}");
						$("#frm_billkeyreg input:hidden[name='LGD_FINANCECODE']").val("${reqMap.LGD_FINANCECODE}");
						$("#frm_billkeyreg input:hidden[name='LGD_FINANCENAME']").val("${reqMap.LGD_FINANCENAME}");
						$("#frm_billkeyreg input:hidden[name='LGD_CARDINSTALLMONTH']").val("${reqMap.LGD_CARDINSTALLMONTH}");
						$("#frm_billkeyreg input:hidden[name='LGD_BUYERSSN']").val("${reqMap.LGD_BUYERSSN}");
						$("#frm_billkeyreg input:hidden[name='LGD_CARDNUM']").val("${reqMap.LGD_CARDNUM}");
						$("#frm_billkeyreg input:hidden[name='LGD_EXPMON']").val("${reqMap.LGD_EXPMON}");
						$("#frm_billkeyreg input:hidden[name='LGD_EXPYEAR']").val("${reqMap.LGD_EXPYEAR}");
						$("#frm_billkeyreg input:hidden[name='LGD_CARDNOINTYN']").val("${reqMap.LGD_CARDNOINTYN}");
						$("#frm_billkeyreg input:hidden[name='LGD_TID']").val("${reqMap.LGD_TID}");
						$("#frm_billkeyreg input:hidden[name='LGD_VANCODE']").val("${reqMap.LGD_VANCODE}");

						//카드번호
						$("#frm_billkeyreg input:hidden[name='CARD_NM']").val($(opener.document).find("#insertCardForm input:text[name='cardNm']").val());
						if ($(opener.document).find("#insertCardForm input:radio[name='mainYn']").is(":checked")) {
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
						 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
						 		self.close();
							},
							success: function(data){
								if(data.result == true){
									alert("원클릭 카드를 등록완료하였습니다.");
									opener.location.replace("${CTX}/order/cartOrder.do");
									self.close();
								}else{
									if(data.msg != "" && data.msg !=null){
										alert(data.msg);
										self.close();
									}
								}
							 }
						});
				} else {
					alert("LGD_RESPCODE (결과코드) : ${reqMap.LGD_RESPCODE}\nLGD_RESPMSG (결과메시지): ${reqMap.LGD_RESPMSG}");
					self.close();
				}

			} catch (e) {
				alert(e.message);
				self.close();
			}
		}
	</script>
</head>
<body onload="setLGDResult()">
<p>결과코드 : ${reqMap.LGD_RESPCODE}</p>
<p>${reqMap.LGD_RESPMSG}</p>
<%--
	<form method="post" name="LGD_RETURNINFO" id="LGD_RETURNINFO">
	<c:out value="${HiddenParams}" escapeXml="false"/>
	</form>
 --%>
<form method="post" name="frm_billkeyreg" id="frm_billkeyreg" >
<c:out value="${HiddenParams}" escapeXml="false"/>
</form>

</body>

</html>