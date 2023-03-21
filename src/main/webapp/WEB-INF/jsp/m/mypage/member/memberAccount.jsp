<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_member" />
<meta name="menu_no" content="mypage_110" />
<script>

	$(document).ready(function(){
		createNumeric("#account");
	})

	// 회원정보관리 이동
	function goMemberInfo(){
		location.href = "${CTX}/mypage/member/memberInfo.do";
	}
	
	// 저장
	function goSave(){
		
		if($.trim($("#bankSelect").val()) == ""){
			alert("은행을 선택해 주세요.");
			return false;
		}
		
		if($.trim($("#account").val()) == ""){
			alert("계좌번호를 입력해 주세요.");
			$("#email1").focus();
			return false;
		}
		
		if($.trim($("#depositor").val()) == ""){
			alert("예금주를 입력해 주세요.");
			$("#email1").focus();
			return false;
		}
		
		$.ajax({
			 url: "${CTX}/ajax/mypage/member/memberAccountSaveAjax.do",
			 data : {
				 			"bankCode"	:	$("#bankSelect").val(),
				 			"account"		:	$("#account").val(),
				 			"depositor"		:	$("#depositor").val()
				 		},
			 type: "post",	
			 async: false,
			 cache: false,
			 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 error: function(request, status, error){ 
			 	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			 },
			 success: function(flag){
				 if(Number(flag) > 0){
					 alert("저장 되었습니다.");
				 }
				 document.location.reload();
			 }
		});
	}
	
	// 삭제
	function goDelete(){
		$.ajax({
			 url: "${CTX}/ajax/mypage/member/memberAccountDeleteAjax.do",
			 type: "post",	
			 async: false,
			 cache: false,
			 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 error: function(request, status, error){ 
			 	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			 },
			 success: function(flag){
				 if(Number(flag) > 0){
					 alert("삭제 되었습니다.");
				 }
				 document.location.reload();
			 }
		});
	}
	
</script>
</head>
<body>
	<div class="content comm-mypage mypage-modify">

         <div class="page-body">
             
         	<div class="tab-menu-line tab-2">
          		<ul>
                     <li><a href="javascript:" onclick="goMemberInfo();">회원정보 관리</a></li>
                     <li class="active"><a href="javascript:">환불계좌 관리</a></li>
                 </ul>
          	</div>

             <div class="order-form">
                 <div class="row">
                     <div class="col col-12">
                         <div class="form-control">
                             <div class="opt_select">
                                 <select id="bankSelect">
                                     <c:forEach var="list" items="${bankList}">
                                     	<option value="${list.commonCd}" data-cdNm="${list.cdNm}" <c:if test="${detail.bankCode eq list.commonCd}">selected</c:if>>
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
                         <div class="form-control">
                             <input type="text" name="account" id="account" class="input" value="${detail.account}" placeholder="계좌번호 (‘-’없이 숫자로만 입력 해주세요.)" maxlength="100" />
                         </div>
                     </div>
                 </div>
                 <div class="row">
                     <div class="col col-12">
                         <div class="form-control">
                             <input type="text" name="depositor" id="depositor" class="input" value="${detail.depositor}" placeholder="예금주" />
                         </div>
                     </div>
                 </div>
             </div>
              
             <ul class="bu-list" style="margin-top: 5px">                        
                 <li><span class="bu">*</span> 환불 계좌를 저장하시면, 서비스 이용 시 별도의 입력 없이 이용하실 수 있습니다.</li>
                 <li><span class="bu">*</span> 환불 계좌는 1개의 계좌만 등록 관리가 됩니다.</li>
                 <li><span class="bu">*</span> 기 등록하신 계좌정보가 기본으로 노출이 되며 수정도 바로 가능합니다.</li>
                 <li><span class="bu">*</span> 수정을 하실 경우 변경하실 계좌정보로 변경하시고 저장 버튼을 클릭해주세요.</li>
                 <li><span class="bu">*</span> 등록된 계좌 삭제를 원하실 경우 하단에 삭제 버튼을 클릭해주세요.</li>
             </ul>


			<c:choose>
			
			<c:when test="${!empty detail}">
			<div class="btn-box">
                 <div class="col col-4">
                     <button type="button" class="btn full" onclick="goDelete();"><span class="txt">삭제</span></button>
                 </div>
                 <div class="col col-4 <c:if test="${empty detail}">col-4-offset</c:if>">
                     <button type="button" class="btn full" onclick="location.href='${CTX}/mypage/order/main.do'"><span class="txt">취소</span></button>
                 </div>
                 <div class="col col-4">
                     <button type="button" class="btn black full" onclick="goSave();">
                     	<span class="txt">
                     	<c:choose>
                     		<c:when test="${!empty detail}">수정</c:when>
                     		<c:otherwise>등록</c:otherwise>
                     	</c:choose>
                     	</span>
                     </button>
                 </div>
             </div>
			</c:when>
            <c:otherwise>
            <div class="btn-box">
                 <div class="col col-6">
                     <button type="button" class="btn full" onclick="location.href='${CTX}/mypage/order/main.do'"><span class="txt">취소</span></button>
                 </div>
                 <div class="col col-6">
                     <button type="button" class="btn black full" onclick="goSave();">
                     	<span class="txt">
                     	<c:choose>
                     		<c:when test="${!empty detail}">수정</c:when>
                     		<c:otherwise>등록</c:otherwise>
                     	</c:choose>
                     	</span>
                     </button>
                 </div>
             </div>
            </c:otherwise> 
            </c:choose>

         </div>
         
         <div class="guidebox">
             <div class="guide-cont">
                 <ul class="bu-list">
                     <li><span class="bu">-</span> 회원정보는 개인정보 보호방침ㆍ취급방침에 따라 안전하게 보호됩니다.</li>
                     <li><span class="bu">-</span> 회원님의 동의 없이는 기재하신 회원정보가 공개되지 않습니다.</li>
                     <li><span class="bu">-</span> 보다 다양한 서비스를 받으시려면 정확한 정보를 항상 유지해 주셔야 합니다.</li>
                     <li><span class="bu">-</span> 비밀번호가 타인에게 노출되지 않도록 주의하시고, 비밀번호는 주기적으로 변경하시는 것이 좋습니다.</li>
                 </ul>
             </div>
         </div>
         
	</div>		
</body>
</html>