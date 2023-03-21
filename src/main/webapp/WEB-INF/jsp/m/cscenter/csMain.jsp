<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main"/>	

<script>

// FAQ 검색
function goSearch(){
	var frm = document.mainForm;
	frm.action = "${CTX}/cscenter/csMain.do";
	frm.submit();
}

// 다른 페이지 이동
function goLink(type){
	if(type == "inquiry"){
		location.href="${CTX}/mypage/inquiry/inquiryWrite.do";
	}else if(type == "benefit"){
		location.href="${CTX}/cscenter/csInfo/benefitInfo.do";
	}
}

// FAQ 조회수 증가
function addReadCnt(faqIdx){
	if($("#faqList"+faqIdx).hasClass("check")==false){
		$("#faqIdx").val(faqIdx);
		$("#faqList"+faqIdx).addClass("check");
		
		$.ajax({
			 url: "${CTX}/ajax/cscenter/faqReadCntAjax.do",
			 data : {	
				 			"faqIdx"	:	faqIdx
			 		   },  
			 type: "POST",
			 dataType: "html",
			 async: false,
			 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 error: function(request, status, error){ 
			 	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			 },
			 success: function(flag){ 
				 
			 }
		});  
	}
}

</script>

</head>
<body>
<form name="mainForm" id="mainForm" method="get" onsubmit="return false;">
	<div class="content comm-customer customer-main">
		
		<page:applyDecorator  name="mobile.csmenu" encoding="UTF-8"/>
		
		<div class="page-title hide">
			<h2>고객센터</h2>
		</div>
	    
	    <div class="page-body">
			<div class="box-title">
				<h3>FAQ</h3>
				<a href="${CTX}/cscenter/faq/faqList.do" class="btn-more">MORE</a>
			</div>

			<div class="box-search">
				<div class="row">
					<div class="col col-12">
						<div class="form-control search-keyword">
							<input type="text" name="schValue" id="schValue" class="input input-search-keyword" placeholder="키워드를 입력해주세요." value="${schVO.schValue}">
							<input type="submit" class="btn-search-keyword" onclick="goSearch();">
						</div>
					</div>
				</div>
			</div>  

			<div class="faq-wrap">
		    	<c:forEach var="list" items="${faqTop5List}" varStatus="idx">
			        <div class="faq-box">
			            <div class="faq-q"  id="faqList${list.faqIdx}" onclick="addReadCnt('${list.faqIdx}');">
			                <strong>${list.faqNm}</strong>
			                <p>${list.title}</p>
			            </div>
			            <div class="faq-a">
			                <c:out value="${list.faqDesc}" escapeXml="false"/>
			            </div>
			        </div>
		        </c:forEach>
		    </div>

			<div class="box-wrap">
				<div class="box-title">
					<h3>공지사항</h3>
					<a href="${CTX}/cscenter/notice/noticeList.do" class="btn-more">MORE</a>
				</div>
			</div>

			<div class="notice-wrap">
		    	<c:forEach var="list" items="${noticeList}" varStatus="idx" end="4">
			        <a href="${CTX}/cscenter/notice/noticeView.do?noticeIdx=${list.noticeIdx}" class="notice-box">
			            <span class="notice-label">${list.noticeTypeNm}</span>
			            <p class="truncate">${list.title}</p>
			            <span class="notice-date">${list.regDt}</span>
			        </a>
		        </c:forEach>
		    </div>

			<div class="btn-box confirm">
				<div class="row">
					<div class="col col-6">
						<button type="button" class="btn full ico-chev" onclick="goLink('inquiry');"><span class="txt">1:1 문의</span></button>
					</div>
					<div class="col col-6">
						<button type="button" class="btn full ico-chev" onclick="goLink('benefit')"><span class="txt">혜택안내</span></button>
					</div>
				</div>
				<div class="row">
					<div class="col col-12">
						<a href="tel:02-544-1191" class="btn customer-info">
							<strong>고객센터</strong>
							<span class="telnum">02-544-1191</span>
							<span>평일 : 09:00 - 17:30</span>
							<span>점심시간 : 12:00 ~13:00</span>
							<span>토/일/공휴일 휴무</span>
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>

<!-- <script type="text/javascript">
(function(window, document, $){
    $(".faq-box").faqAction();
})(window, document, jQuery);
</script> -->

</body>
</html>