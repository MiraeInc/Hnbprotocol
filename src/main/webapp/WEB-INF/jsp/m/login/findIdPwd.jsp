<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE">
<html>
<head>
<meta name="decorator" content="join.main" />
<script>
	$(function(){
		createNumeric("#phoneNo");
		
		$(".divFindEmail").hide();
		
		//휴대폰번호 UI
		$('input[name="findType"]').on('change', function(){
			$this = $(this);
			if($this.prop('value')==="P"){
				$(".divFindPhone").show();
				$(".divFindEmail").hide();
			}else{
				$(".divFindPhone").hide();
				$(".divFindEmail").show();
			}
		});
	});

	function findIdCheck(){
		var emailGubun = $("#emailGubun option:selected").attr("data-cdNm");
		if($.trim($("#memberNm").val()) ==""){ alert("이름을 입력해 주세요."); $("#memberNm").focus(); return false;}
		if($.trim($("#email1").val()) ==""){ alert("이메일을 입력해 주세요."); $("#email1").focus(); return false;}
		if($.trim($("#emailGubun").val()) == ""){alert("이메일을 선택해주세요.");  return false;}
		// 이메일 직접입력
		var email = "";
		if($("#emailGubun").val() =="MAIL_KIND_ETC"){
			if($.trim($("#email2").val()) == ""){
				alert("이메일을 직접 입력해주세요.");
				$("#email2").focus();
				return false;
			}else{
				email = $.trim($("#email1").val()) + "@" + $.trim($("#email2").val());
			}
		}else{
			email = $.trim($("#email1").val()) + "@" + $.trim(emailGubun);
		}
		
		$.ajax({
			 url: "${CTX}/ajax/login/findMemberId.do",
			 data : {
				 			"memberNm"	: $("#memberNm").val() , 
				 			"email"	: email 
				 		},
			 type: "POST",	
			 async: false,
			 cache: false,
			 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 error: function(request, status, error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			 },
			 success: function(map){ 
				 if(map.flag =="Y"){
					 $("#RESULT1").show();
					 $("#RESULT2").hide();
					 
					 var joinType = map.info.joinType;
					 
					 if(joinType == "0"){						// 사이트 회원
					 	var memberId = map.info.memberId;
					 	var str="";
					 	for(var i=0; i<memberId.length-4; i++){
					 		str +="*";
					 	}
					 	var html = "";
						html += "<p class='text'>"+memberId.substr(0,2)+str+memberId.substr(memberId.length-2,2)+"</p>";
						html += "<p class='date'>가입일: <strong>"+map.info.joinDate+"</strong></p>";
					 	$("#RESULT_TEXT").html(html);
					 }else{												// SNS 회원
					 	var snsTypeNm = map.info.snsTypeNm;
						var html = "";
						html += "<p class='text'>"+snsTypeNm+" SNS 가입 하셨습니다.</p>";
						html += "<p class='date'>가입일: <strong>"+map.info.joinDate+"</strong></p>";
					 	$("#RESULT_TEXT").html(html);
					 }
				 }else{
					 $("#RESULT1").hide();
					 $("#RESULT2").show();
				 }
			 }
		});
	}
	
	function findPwdCheck(){
		var findType = $('input[name="findType"]:checked').val();
		var emailGubun1 = $("#emailGubun1 option:selected").attr("data-cdNm");
		if($.trim($("#memberId").val()) ==""){ alert("아이디을 입력해 주세요."); $("#memberId").focus(); return false;}
		if($.trim($("#email3").val()) ==""){ alert("이메일을 입력해 주세요."); $("#email3").focus(); return false;}
		if($.trim($("#emailGubun1").val()) == ""){alert("이메일을 선택해주세요.");  return false;}
		
		var phoneNo = "";
		if(findType == "P"){
			if($.trim($("#phoneNo").val()) ==""){ alert("휴대폰 번호를 입력해 주세요."); $("#phoneNo").focus(); return false;}
			phoneNo = $.trim($("#phoneNo").val());
		}
		
		// 이메일 직접입력
		var email = "";
		if($("#emailGubun1").val() =="MAIL_KIND_ETC"){ 
			if($.trim($("#email4").val()) == ""){
				alert("이메일을 직접 입력해주세요.");
				$("#email4").focus();
				return false;
			}else{
				email = $.trim($("#email3").val()) + "@" + $.trim($("#email4").val());
			}
		}else{
			email = $.trim($("#email3").val()) + "@" + $.trim(emailGubun1);
		}
		
		$.ajax({
			 url: "${CTX}/ajax/login/findMemberPwd.do",
			 data : {
				 			"memberId"	: $("#memberId").val() , 
				 			"email"	: email,
				 			"phoneNo"	:	phoneNo,
				 			"findType"	:	findType
				 		},
			 type: "POST",	
			 async: false,
			 cache: false,
			 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 error: function(request, status, error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			 },
			 success: function(map){ 
				 if(map.flag =="Y"){
					 $("#RESULT3").show();
					 $("#RESULT4").hide();
					 if(findType=="E"){
						 $("#MAIL_TEXT").empty();
						 var email = map.info.email;
						 var splitEmail = email.split("@");
						 var str="";
						 for(var i=0; i<splitEmail[0].length-2; i++){
							 str +="*";
						 }
						 
						 $("#MAIL_TEXT").html("<p class='find_list'>회원가입 시 입력하신 이메일 주소(<b>"+splitEmail[0].substr(0,2)+str+"@"+splitEmail[1]+"</b>)로 임시 비밀번호를 발송하였습니다.</p>");
					 }else{
						 $("#MAIL_TEXT").empty();
						 var phone = map.info.phoneNo;
						 var splitPhone = phone.split("-");
						 var str="";
						 for(var i=0; i<splitPhone[1].length; i++){
							 str +="*";
						 }
						 
						 $("#MAIL_TEXT").html("<p class='find_list'>입력하신 휴대폰 (<b>"+splitPhone[0]+" "+str+" "+splitPhone[2]+"</b>)로 <br/> 임시 비밀번호를 발송하였습니다.</p>");
					 }
				 }else{
					 $("#RESULT3").hide();
					 $("#RESULT4").show();
				 }
			 }
		});
	}
	
	
	function tabChange(flag){
		if(flag =="1"){
			$("#memberNm,#email1,#email2,#emailGubun").val("");
			$("#RESULT1,#RESULT2").hide();
		}else{
			$("#memberId,#email3,#email4,#emailGubun1").val("");
			$("#RESULT3,#RESULT4").hide();
		}
		
	}
</script>
</head>
<body>
<div id="single">
	<header id="header">
		<h1 id="logo">
			<a href="${CTX}/main.do">
				<img src="${CTX }/images/logo_mandom.png" alt="Mandom">
			</a>
		</h1>
		<h2 class="page-title">아이디/비밀번호 찾기</h2>
	</header>
	<article id="container" class="clearfix">
		<form name="writeform" id="writeform"  method="post" onsubmit="return false;">
			<div id="content" class="inner">
				<div class="tab" data-column="2">
					<div class="tab-buttons">
						<button data-tab-id="findId" type="button" class="active" onclick="tabChange('1');">아이디 찾기</button>
						<button data-tab-id="finfPwd" type="button" onclick="tabChange('2');">비밀번호 찾기</button>
					</div>
					<div class="tab-panel active" id="findId">
						<div class="form-group">
							<input type="text" class="form-control" name="memberNm" id="memberNm"   placeholder="이름">
						</div>
						<div class="form-group form-hold">
							<div class="form-holder" data-extend="@">
								<input type="text" class="form-control" name="email1" id="email1" placeholder="이메일" maxlength="50">
							</div>
							<div class="form-holder">
								<select class="form-control" name="emailGubun" id="emailGubun" required>
									<option value="" disabled hidden selected>선택</option>
									<c:forEach var="list" items="${mailList}">
										<option value="${list.commonCd}" data-cdNm="${list.cdNm}" <c:if test="${list.commonCd eq 'MAIL_KIND_ETC'}">data-etc-target="email1"</c:if>>
											<c:out value="${list.cdNm}"/>
										</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group" data-etc-name="email1">
							<input type="text" class="form-control" name="email2" id="email2" maxlength="50"  placeholder="이메일 직접입력">
						</div>
						<div class="button-group">
							<a href="javascript:" onclick="findIdCheck();" class="btn btn-black">확인</a> 
						</div>
						
						<div id="RESULT1" style="display:none;">
							<div class="find_result">
								<p class="find_status">아이디 조회 결과 입력하신 정보와<br>일치하는 아이디는 아래와 같습니다.</p>
								<div class="find_list" id="RESULT_TEXT"></div>
							</div>
		
							<div class="button-group">
								<a href="${CTX}/login/loginPage.do" class="btn btn-login">로그인</a>
							</div>
						</div>
						
						<div id="RESULT2" style="display:none;">
							<div class="find_result">
								<p class="find_status">등록된 정보와 일치하지 않습니다.<br>다시 입력해 주세요.</p>
							</div>
		
							<div class="button-group">
								<a href="${CTX}/login/loginPage.do" class="btn btn-login">로그인</a>
							</div>
						</div>					
					</div>
	
					<div class="tab-panel" id="finfPwd">
						<div class="form-group" style="text-align: center;">
							<div class="form-custom">
								<input type="radio" id="optFindByPhone" class="form-control" name="findType" value="P" checked="checked" />
								<label for="optFindByPhone">휴대폰</label>
							</div>
							<div class="form-custom">
								<input type="radio" id="optFindByEmail" class="form-control" name="findType" value="E" />
								<label for="optFindByEmail">이메일</label>
							</div>
						</div>
						<div class="form-group">
							<input type="text" class="form-control" name="memberId" id="memberId" placeholder="아이디">
						</div>
						<div class="form-group form-hold">
							<div class="form-holder" data-extend="@">
								<input type="text" class="form-control" name="email3" id="email3" placeholder="이메일">
							</div>
							<div class="form-holder">
								<select class="form-control" name="emailGubun1" id="emailGubun1" required>
									<option value="" disabled hidden selected>선택</option>
									<c:forEach var="list" items="${mailList}">
										<option value="${list.commonCd}" data-cdNm="${list.cdNm}" <c:if test="${list.commonCd eq 'MAIL_KIND_ETC'}">data-etc-target="email3"</c:if>>
											<c:out value="${list.cdNm}"/>
										</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group" data-etc-name="email3">
							<input type="text" class="form-control" name="email4" id="email4" placeholder="이메일 직접입력">
						</div>
						<div class="form-group divFindPhone">
							<input type="tel" class="form-control" name="phoneNo" id="phoneNo" placeholder="휴대폰번호 (‘-’를 제외한 숫자만 입력)">
						</div>
						
						<div class="button-group">
							<a href="javascript:" onclick="findPwdCheck();" class="btn btn-black divFindPhone">휴대폰으로 임시 비밀번호 전송</a>
							<a href="javascript:" onclick="findPwdCheck();" class="btn btn-black divFindEmail">이메일로 임시 비밀번호 전송</a>
						</div> 
						
						<div id="RESULT3" style="display:none;">
							<div class="find_result">
								<p class="find_list" id="MAIL_TEXT"></p>
							</div>
		
							<div class="button-group">
								<a href="${CTX}/login/loginPage.do" class="btn btn-login">로그인</a>
							</div>
						</div>
						
						<div  id="RESULT4" style="display:none;">
							<div class="find_result">
								<p class="find_status">등록된 정보와 일치하지 않습니다.<br>다시 입력해 주세요.</p>
							</div>
		
							<div class="button-group">
								<a href="${CTX}/login/loginPage.do" class="btn btn-login">로그인</a>
							</div>
						</div>						
					</div>
				</div>
			</div>
		</form>
	</article>
</div>
</body>
</html>