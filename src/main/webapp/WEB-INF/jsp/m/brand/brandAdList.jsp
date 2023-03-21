<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main" />	
<script>

	var pageNo = Number("${VO.pageNo}");
	
	// 리스트 더보기
	function listMore(){
		pageNo = pageNo+1;
		$.ajax({
			 url: "${CTX}/ajax/brand/brandAdListMoreAjax.do",
			 data : {	
				 			"pageNo"	:	pageNo
			 		   },  
			 type: "GET",
			 dataType: "html",
			 async: false,
			 cache: false,
			 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 error: function(request, status, error){ 
			 	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			 },
			 success: function(responseData){ 
				 $("#brandAdList").append(responseData);  
				 
				 var lastPageNo = Number("${page.finalPageNo}");

				 if(pageNo >= lastPageNo){
					 $("#MOREBTN").remove();
				 }
			 }
		});  
	}

</script>
</head>
<body>
	<div class="content comm-brand brand-ad">
	 	<page:applyDecorator  name="mobile.brandmenu" encoding="UTF-8"/>
	
   		<div class="page-body">
			<div class="brand-filter">
				<c:if test="${adBrandCnt.gbCnt ne 0}">
				<a href="${CTX}/brand/brandAdList.do?brandIdx=1" data-brand="gatsby" class="<c:if test="${VO.brandIdx eq 1 || VO.brandIdx eq null || VO.brandIdx eq ''}">active</c:if>"><span>갸스비</span></a>
				</c:if>
				<c:if test="${adBrandCnt.bfCnt ne 0}">
				<a href="${CTX}/brand/brandAdList.do?brandIdx=3" data-brand="bifesta" class="<c:if test="${VO.brandIdx eq 3}">active</c:if>"><span>비페스타</span></a>
				</c:if>
				<c:if test="${adBrandCnt.ldCnt ne 0}">
				<a href="${CTX}/brand/brandAdList.do?brandIdx=4" data-brand="lucidol" class="<c:if test="${VO.brandIdx eq 4}">active</c:if>"><span>루시도엘</span></a>
				</c:if>
<%--
				<c:if test="${adBrandCnt.mmCnt ne 0}">
				<a href="${CTX}/brand/brandAdList.do?brandIdx=6" data-brand="mamabutter" class="<c:if test="${VO.brandIdx eq 6}">active</c:if>"><span>마마버터</span></a>
				</c:if>
 --%>
				<c:if test="${adBrandCnt.dpCnt ne 0}">
				<a href="${CTX}/brand/brandAdList.do?brandIdx=7" data-brand="dentalpro" class="<c:if test="${VO.brandIdx eq 7}">active</c:if>"><span>덴탈프로</span></a>
				</c:if>
<%--
				<c:if test="${adBrandCnt.clCnt ne 0}">
				<a href="${CTX}/brand/brandAdList.do?brandIdx=8" data-brand="charley" class="<c:if test="${VO.brandIdx eq 8}">active</c:if>"><span>찰리</span></a>
				</c:if>
 --%>
			</div>
			
			<c:choose>
				<c:when test="${!empty list}">
					<div class="movie-list">
						<ul id="brandAdList">
							<c:forEach var="list" items="${list}">
								<li>
									<iframe width="100%" src="https://www.youtube.com/embed/${list.videoUrlCode}?rel=0&autohide=1&autoplay=0&showinfo=0" frameborder="0" title="" marginheight="0" marginwidth="0" allowfullscreen></iframe>
									<p class="movie-title">${list.title}</p>
								</li>
							</c:forEach>
						</ul>
					</div>
					
					<c:if test="${VO.pageNo < page.finalPageNo}">
						<div class="btn-box confirm" id="MOREBTN">
							<button type="button" class="btn btn-more" onclick="listMore();"><span class="txt">MORE</span></button>
						</div>
					</c:if>
				</c:when>
				<c:otherwise>
					<div class="preparing">
						<div class="inner-box">
							<img src="${CTX}/images/${DEVICE}/common/txt_nocontent.png" alt="no content.">
							<p class="big-txt">등록된 컨텐츠가 없습니다.</p>
							<p class="small-txt">현재 등록된 컨텐츠가 없습니다.</p>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>