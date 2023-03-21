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

	var frm = document.faqForm;
	frm.action = "${CTX}/cscenter/faq/faqList.do";
	frm.submit();
}

// FAQ 검색
function goSearch(){
	$("#pageNo").val("1");
	 
	if($("#schValue").val() != ""){
		var len = $("#schValue").val().length;
		
		if(len<2){
			alert("키워드는 최소 2자 이상 입력해 주셔야 합니다.");
			return false;
		}
	}
	
	var frm = document.faqForm;
	frm.action = "${CTX}/cscenter/faq/faqList.do";
	frm.submit();
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

// 1:1 문의 이동
function goInquiry(){
	location.href="${CTX}/mypage/inquiry/inquiryWrite.do";
}
</script>

</head>
<body>
<form name="faqForm" id="faqForm" method="get" onsubmit="return false;">
	<input type="hidden" name="pageNo" id="pageNo" value="${schVO.pageNo}"/>
	<input type="hidden" name="schType" id="schType" value="${schVO.schType}"/>
	
	<div class="content comm-customer customer-faq">
		
		<page:applyDecorator  name="mobile.csmenu" encoding="UTF-8"/>
	
	    <div class="page-title hide">
			<h2>FAQ</h2>
		</div>
		
		<div class="page-tab type2">
			<ul>
				<li class="<c:if test="${schVO.schType eq null || schVO.schType eq ''}">active</c:if>">
					<a href="javascript:void(0);" <c:if test="${schVO.schType ne null && schVO.schType ne ''}">onclick="location.href='${CTX}/cscenter/faq/faqList.do';"</c:if>>전체</a>
				</li>
				<c:forEach var="list" items="${faqType}" varStatus="idx">
		            <li class="<c:if test="${list.commonCd eq schVO.schType}">active</c:if>">
		            	<a href="javascript:void(0);" <c:if test="${schVO.schType ne list.commonCd}">onclick="location.href='${CTX}/cscenter/faq/faqList.do?schType=${list.commonCd}';"</c:if>><span>${list.cdNm}</span></a>
		            </li>
	            </c:forEach>
			</ul>
		</div>
	
		<div class="page-body">
			<div class="box-search">
				<div class="row">
					<div class="col col-4">
						<div class="form-control">
							<div class="opt_select">
								<select name="schSubType" id="schSubType">
	                                <option value="ALL" <c:if test="${schVO.schSubType eq null || schVO.schSubType eq ''}">selected</c:if>>전체</option>
									<option value="TITLE" <c:if test="${schVO.schSubType eq 'TITLE'}">selected</c:if>>제목</option>
									<option value="CONTENT" <c:if test="${schVO.schSubType eq 'CONTENT'}">selected</c:if>>내용</option>
	                            </select>
							</div>
						</div>
					</div>
					<div class="col col-8">
						<div class="form-control search-keyword">
							<input type="text" name="schValue" id="schValue" class="input input-search-keyword" placeholder="키워드를 입력해주세요." value="${schVO.schValue}">
							<input type="submit" class="btn-search-keyword" value="검색" onclick="goSearch();">
						</div>
					</div>
				</div>
				
				<c:if test="${schVO.schValue ne null && schVO.schValue ne ''}">
					<div class="result-search-keyword">
						<p>“<span>${schVO.schValue}</span>”에 대해 총 <span>${totalCount}</span>건 검색 되었습니다.</p>
					</div>
				</c:if>
			</div>
			
			<c:choose>
				<c:when test="${fn:length(faqList) > 0}">
			    <div class="faq-wrap">
			    	<c:forEach var="list" items="${faqList}" varStatus="idx">
			        <div class="faq-box" id="faqList${list.faqIdx}" onclick="addReadCnt('${list.faqIdx}');">
			            <div class="faq-q">
			                <strong>${list.faqNm}</strong>
			                <p>${list.title}</p>
			            </div>
			            <div class="faq-a">
			                <c:out value="${list.faqDesc}" escapeXml="false"/>
			            </div>
			        </div>
			        </c:forEach>
			    </div>
			    <div class="pagin-nav">
			        <c:out value="${page.pageStr}" escapeXml="false"/>
			    </div>
			    </c:when>
		        <c:otherwise>
		        <div class="faq-wrap">
			        <div class="no-contents">
			            <p>
		                <c:choose>
		                	<c:when test="${schVO.schValue ne null && schVO.schValue ne ''}">
		                	검색결과가 없습니다.
		                	</c:when>
		                	<c:otherwise>
		                	등록된 게시물이 없습니다.
		                	</c:otherwise>
		                </c:choose>
		                </p>
			        </div>
			    </div>
		        </c:otherwise>
			</c:choose>

			<div class="helper-box">
				<strong data-icon="chat">1:1 문의</strong>
				<div class="helper-box-inner">
					<p>원하시는 정보를 찾지 못하셨나요?<br>1:1 문의를 통해 문의글을 남겨주시면, <br>빠른 시일 안에 답변 드리겠습니다.</p>
					<button type="button" class="btn ico-chev" onclick="goInquiry();"><span class="txt">1:1 문의</span></button>
				</div>
			</div>
		</div>
	</div>
</form>

</body>
</html>