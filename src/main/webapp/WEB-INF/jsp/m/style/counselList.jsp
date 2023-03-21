<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main"/>	
<script>

// 페이지 이동
function goPage(page){
	$("#pageNo").val(page);
	
	var frm = document.counselForm;
	frm.action = "${CTX}/style/counselList.do";
	frm.submit();
}

// 상담 신청 로그인 체크
function goWrite(){
	var flag = "${IS_LOGIN}";
	
	if(flag=="false"){
		if(confirm("<spring:message code='common.util011'/>") == true){
			location.href="${CTX}/login/loginPage.do?refererYn=Y";
		}
	}else if(flag=="true"){
		var frm = document.counselForm;
		frm.action = "${CTX}/style/counselWrite.do";
		frm.submit();
	}
}

</script>
</head>
<body>
<form name="counselForm" id="counselForm" method="post" onsubmit="return false;">
	<input type="hidden" name="pageNo" id="pageNo" value="${schVO.pageNo}"/>
	<input type="hidden" name="counselIdx" id="counselIdx" value=""/>
	<input type="hidden" name="statusFlag" id="statusFlag" value="I"/>
	
	<div class="content comm-styleg styleg-counsel">
		<page:applyDecorator  name="mobile.stylemenu" encoding="UTF-8"/>
		
        <div class="counselor">
            <div class="page-title">
                <h2 class="img-title">
                    <img src="${CTX}/images/${DEVICE}/common/txt_title_stylecounsel.png" alt="style counsel - 전문가가 직업 Styling과 Grooming에 대한 고민을 풀어 드립니다.">
                </h2>
            </div>
            <div class="counselor_profile">
				<img src="${CTX}/images/${DEVICE}/contents/img_stylecounsel.png" class="img-counselor" alt="counselor 사진">
				<div class="counselor-box">
					<strong>COUNSELOR</strong>
					<span>에반스타일(EVANSTYLE) 송인한 원장님</span>
					<p class="detail">
						송샘의 모든 노하우를 GATSBY ONLINE<br> 회원님들께 전달해 드리겠습니다.
					</p>
					<div class="btn_box">
						<a href="javascript:" class="btn" onclick="goWrite();">스타일 상담하기</a>    
					</div>
				</div>
			</div>
        </div>

        <div class="event-list counsel">
            <ul>
            	<c:forEach var="list" items="${counselList}" varStatus="idx">
                <li>
                    <a href="${CTX}/style/counselView.do?counselIdx=${list.counselIdx}">
                        <span class="event-img">
                            <img src="${CTX}/images/${DEVICE}/common/bg_styleg_gatsby.png" alt="배경이미지">
                            <span class="q-box">
                                <strong>${list.qTitle}</strong>
                            </span>
                        </span>
                        <p class="writing-info">${list.qRegDt}<c:if test="${list.ansYn eq 'Y'}"><span>답변완료</span></c:if></p>
                    </a>
                </li>
                </c:forEach>
            </ul>
        </div>

        <div class="pagin-nav">
            <c:out value="${page.pageStr}" escapeXml="false"/>
        </div>
	</div>
</form>
</body>
</html>