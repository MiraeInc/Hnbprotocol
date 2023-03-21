<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_member" />
<meta name="menu_no" content="mypage_080" />
<script language="javascript" src="${xpayJsUrl}" type="text/javascript"></script>
<script>	


	//빌키등록 LGU+창 오픈
	function insertOneClickCard() {
		if($.trim($("#insertCardForm input:text[name='cardNm']").val()) == ""){
			alert("카드명을 입력해주세요.");
			return false;
		}

		var LGD_window_type = '${billkeyRegMap.LGD_WINDOW_TYPE}';
		var CST_PLATFORM = '${billkeyRegMap.CST_PLATFORM}';
		
		lgdwin = openXpay(document.getElementById('frm_billkeyreg'), CST_PLATFORM, LGD_window_type, null, "", "");
//		lgdwin = openXpay(document.getElementById('frm_billkeyreg'), CST_PLATFORM, "popup", null, "", "");
	}
	

	//빌키등록결과 
	function billkey_reg_return() {
		
		var fDoc;
		
		fDoc = lgdwin.contentWindow || lgdwin.contentDocument;
			
		if (fDoc.document.getElementById('LGD_RESPCODE').value == "0000") {
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
	
	function cardModify(billkeyIdx, cardNm, cardnum, mainyn) {

		$("#modifyCardForm input:hidden[name='billkeyIdx']").val(billkeyIdx);
		$("#modifyCardForm #cardNum").html(cardnum);
		$("#modifyCardForm input:text[name='cardNm']").val(cardNm);
		if (mainyn == 'Y') {
			$("#modifyCardForm input:radio[id='mainYn3']").attr("checked", true);
		} else  {
			$("#modifyCardForm input:radio[id='mainYn4']").attr("checked", true);
		} 
	}

	function cardDelete(billkeyIdx) {
		if (confirm("카드를 삭제하시겠습니까?")) {

			$.ajax({			
				url: getContextPath()+"/ajax/order/BillKeyDelAjax.do",
				data: {"billkeyIdx" : billkeyIdx},
			 	type: "post",
			 	async: false,
			 	cache: false,
			 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 	error: function(request, status, error){ 
			 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
				},
				success: function(data){
					if(data.result == true){
						alert("원클릭 카드를 삭제하였습니다.");
						location.reload();
					}else{
						if(data.msg != "" && data.msg !=null){
							alert(data.msg);
						}
					}
				 }
			});
		}		
	}
	
	function modifyOneClickCard() {
		if($.trim($("#modifyCardForm input:text[name='cardNm']").val()) == ""){
			alert("카드명을 입력해주세요.");
			return false;
		}

		$.ajax({			
			url: getContextPath()+"/ajax/order/BillKeyModifyAjax.do",
			data: $("#modifyCardForm").serialize(),
		 	type: "post",
		 	async: false,
		 	cache: false,
		 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data){
				if(data.result == true){
					alert("원클릭 카드를 수정하였습니다.");
					location.reload();
				}else{
					if(data.msg != "" && data.msg !=null){
						alert(data.msg);
					}
				}
			 }
		});
	}

</script>
</head>
<body>
									<!-- [D] content start here! -->
			<div class="content comm-mypage mypage-oneclick">

				<div class="page-body">

<c:choose>
<c:when test="${fn:length(billkeyList) > 0}">	
					<div class="mycard-list">
						<ul>	

							<c:forEach var="list" items="${billkeyList}" varStatus="idx">				
							
							<li>
								<div class="item-card">
									<div class="card">
										<c:if test="${list.mainYn eq 'Y' }">
										<p class="selected">원클릭 결제카드</p> <!-- 대표카드 -->
										</c:if>
										<p class="card-name">${list.cardNm} (${list.financename})</p>
										<p class="card-number">${list.cardnum}</p>
									</div>
								</div>
								<div class="btn-box">
									<button type="button" class="btn" data-toggle="popup" data-target="#pop_billkey_edit" onclick="cardModify('${list.billkeyIdx}','${list.cardNm}','${list.cardnum}','${list.mainYn}')"><span class="txt">수정</span></button>
									<button type="button" class="btn black" onclick="cardDelete('${list.billkeyIdx}')"><span class="txt">삭제</span></button>
								</div>
							</li>
							</c:forEach>
							<!-- 카드등록 -->
							<li>
								<div class="item-card">
									<a href="javascript:void(0);" class="card-add" data-toggle="popup" data-target="#pop_billkey_reg"><span class="hide">카드 등록</span></a>
								</div>
							</li>
												
					
							
						</ul>
					</div>
						
</c:when>
<c:otherwise>	
					<div class="mycard-list">
						<ul>	
							<!-- 카드등록 -->
							<li>
								<div class="item-card">
									<a href="javascript:void(0);" class="card-add" data-toggle="popup" data-target="#pop_billkey_reg"><span class="hide">카드 등록</span></a>
								</div>
							</li>
						</ul>
					</div>
					
							
</c:otherwise>
</c:choose>
						
					<ul class="bu-list">
						<li><span class="bu">-</span> 원클릭 결제 카드로 설정된 카드는 원클릭 결제 시 자동으로 결제에 사용됩니다.</li>
						<li><span class="bu">-</span> 카드정보는 (주)KCP 전자결제에서만 관리되며 면역공방에서는 저장되지 않습니다.</li>
						<li><span class="bu">-</span> 휴대폰 분실 등을 통한 타인의 자동결제에 대해서는 면역공방에서 책임지지 않습니다.</li>
					</ul>

				</div>
			</div>
			<!-- [D] //content start end! -->


<form method="post" name="frm_billkeyreg" id="frm_billkeyreg" >
<c:out value="${billkeyRegMapHiddenParams}" escapeXml="false"/>
</form>



<!-- 원클릭 결제 카드 관리 -->
<div id="pop_billkey_reg" class="popup type-page popup-oneclick-manage">
<form name="insertCardForm" id="insertCardForm" method="post" onsubmit="return false;">
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
								<input type="text" class="input" placeholder="카드 정보를 구별할 수 있는 별명 입력" id="cardNm" name="cardNm" />
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
		
		<div class="guidebox" style="margin-left: 0; margin-right: 0;">
			<div class="guide-cont">
				<ul class="bu-list">
					<li><span class="bu">-</span> 원클릭 결제 카드로 설정된 카드는 원클릭 결제 시 자동으로 결제에 사용됩니다.</li>
					<li><span class="bu">-</span> 카드정보는 (주)KCP 전자결제에서만 관리되며 </li>
					<li><span class="bu">-</span> 면역공방에서는 저장되지 않습니다.</li>
					<li><span class="bu">-</span> 휴대폰 분실 등을 통한 타인의 자동결제에 대해서는 면역공방에서 책임지지 않습니다.</li>
				</ul>
			</div>
		</div>
		
	</div>
	<!-- //pop-mid -->
	<a href="#none" class="btn_close" data-dismiss="popup"><span class="hide">닫기</span></a>

	</form>	
</div>




<!-- 원클릭 결제 카드 관리 : 수정 -->
<div id="pop_billkey_edit" class="popup type-page popup-oneclick-manage">
<form name="modifyCardForm" id="modifyCardForm" method="post" onsubmit="return false;">
	<input type="hidden" name="billkeyIdx" value="" >
	<h1 class="hide">GATSBY</h1>
	<!-- pop-top -->
	<div class="pop-top">
		<h2>원클릭 결제 카드 관리</h2>
	</div>
	<!-- //pop-top -->
	<!-- pop-mid -->
	<div class="pop-mid">
	
		<dl class="card-info">
			<dt>카드 정보</dt>
			<dd id="cardNum"></dd>
		</dl>
		
		<div class="order-form">
			<div class="form-group">
				<div class="form-body">
					<div class="row">
						<div class="form-label">
							<label for="">카드명</label>
						</div>
						<div class="col col-12">
							<div class="form-control">
								<input type="text" class="input" placeholder="카드 정보를 구별할 수 있는 별명 입력" id="cardNm" name="cardNm" />
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
									<input type="radio" name="mainYn" id="mainYn3" value="Y" class="radio" />
									<label for="mainYn3" class="lbl">지정</label>
								</span>
								<span class="radiobox">
									<input type="radio" name="mainYn" id="mainYn4" value="N" class="radio" />
									<label for="mainYn4" class="lbl">미지정</label>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="btn-box confirm">
			<button type="button" class="btn outline-green" onclick="modifyOneClickCard();"><span class="txt">저장</span></button>
		</div>
		
		<div class="guidebox" style="margin-left: 0; margin-right: 0;">
			<div class="guide-cont">
				<ul class="bu-list">
					<li><span class="bu">-</span> 원클릭 결제 카드로 설정된 카드는 원클릭 결제 시 자동으로 결제에 사용됩니다.</li>
					<li><span class="bu">-</span> 카드정보는 (주)KCP 전자결제에서만 관리되며 </li>
					<li><span class="bu">-</span> 면역공방에서는 저장되지 않습니다.</li>
					<li><span class="bu">-</span> 휴대폰 분실 등을 통한 타인의 자동결제에 대해서는 면역공방에서 책임지지 않습니다.</li>
				</ul>
			</div>
		</div>
		
	</div>
	<!-- //pop-mid -->
	<a href="#none" class="btn_close" data-dismiss="popup"><span class="hide">닫기</span></a>
	</form>
</div>


</body>
</html>