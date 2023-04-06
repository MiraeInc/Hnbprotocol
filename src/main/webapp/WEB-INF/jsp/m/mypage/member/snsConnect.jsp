<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_member" />
<meta name="menu_no" content="mypage_090" />
<script>document.domain = "<spring:message code='server.defaultDomain'/>"</script>
<script>
	
	// SNS 계정 연결 
	function snsConnect(snsType){
		// N : 네이버, F : 페이스북, K : 카카오, P : 페이코
		if(snsType == "N"){
			owScrollYesCenter("${naverAuthUrl}", "_blank", "444", "510");
		}else if(snsType == "K"){
			owScrollYesCenter("${kakaoAuthUrl}", "_blank", "444", "510");
		}else{
			owScrollYesCenter("${paycoAuthUrl}", "_blank", "444", "510");
		}
	}

	// SNS 계정 연동 해제 
	function snsDisconnect(snsType){
		if(confirm("SNS 계정 연결을 해제하시면, SNS 로그인 기능만\n해제됩니다. SNS 계정 연결이 해제되어도, 회원 탈퇴가\n되는 것은 아니므로 아이디 / 비밀번호로\n로그인 하실 수 있습니다.") == true){
			// N : 네이버, F : 페이스북, K : 카카오
			
			if(snsType == "N"){
				owScrollYesCenter("${naverAuthUrl}", "_blank", "444", "510");
			}else if(snsType == "K"){
				owScrollYesCenter("${kakaoAuthUrl}", "_blank", "444", "510");
			}else{
				owScrollYesCenter("${paycoAuthUrl}", "_blank", "444", "510");
			}
		}
	}

</script>
</head>
<body>
	<div class="content comm-mypage mypage-sns">
	    	
	    <div class="page-body">
	        <div class="sns-list">
	        	<ul>
	        		<li>
	        			<div class="item-sns">
	        				<span class="sns-ico">
	        					<img src="${CTX}/images/${DEVICE}/common/img_sns_kakao.png" alt="카카오" />
	        				</span>
	        				<c:choose>
								<c:when test="${empty snsKakaoInfo}">
			        				<div class="sns-info">
			        					<p>연결된 계정이 없습니다.</p>
			        				</div>
								</c:when>
								<c:otherwise>
									<div class="sns-info">
			        					<p class="email">${snsKakaoInfo.snsEmail}</p>
			        					<p class="date"><strong>연결 날짜</strong> ${snsKakaoInfo.regDt} </p>
			        				</div>
								</c:otherwise>
							</c:choose>
	        			</div>
	        			<c:choose>
	        				<c:when test="${empty snsKakaoInfo}">
			        			<button type="buton" class="btn outline-green" onclick="snsConnect('K');"><span class="txt">계정 연결</span></button>
	        				</c:when>
	        				<c:otherwise>
		        				<button type="buton" class="btn" onclick="snsDisconnect('K');"><span class="txt">연결 해제</span></button>
	        				</c:otherwise>
	        			</c:choose>
	        		</li>
	        		<li>
	        			<div class="item-sns">
	        				<span class="sns-ico">
	        					<img src="${CTX}/images/${DEVICE}/common/img_sns_naver.png" alt="네이버" />
	        				</span>
	        				<c:choose>
								<c:when test="${empty snsNaverInfo}">
			        				<div class="sns-info">
			        					<p>연결된 계정이 없습니다.</p>
			        				</div>
								</c:when>
								<c:otherwise>
									<div class="sns-info">
			        					<p class="email">${snsNaverInfo.snsEmail}</p>
			        					<p class="date"><strong>연결 날짜</strong> ${snsNaverInfo.regDt} </p>
			        				</div>
								</c:otherwise>
							</c:choose>
	        			</div>
        				<c:choose>
	        				<c:when test="${empty snsNaverInfo}">
			        			<button type="buton" class="btn outline-green" onclick="snsConnect('N');"><span class="txt">계정 연결</span></button>
	        				</c:when>
	        				<c:otherwise>
		        				<button type="buton" class="btn" onclick="snsDisconnect('N');"><span class="txt">연결 해제</span></button>
	        				</c:otherwise>
	        			</c:choose>
	        		</li>
	        	</ul>
	        </div>
	    </div>
	</div>
</body>
</html>