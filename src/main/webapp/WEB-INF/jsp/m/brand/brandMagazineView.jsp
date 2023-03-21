<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main" />	
<script>

	// 매거진 목록
	function goList(){
		$("#pageNo").val(1);
		var frm = document.magazineForm;
		frm.action = "${CTX}/brand/brandMagazineList.do";
		frm.submit();
	}
	
	// 매거진 상세
	function goDetail(idx){
		if(idx != ""){
			$("#magazineIdx").val(idx);
			var frm = document.magazineForm;
			frm.action = "${CTX}/brand/brandMagazineView.do";
			frm.submit();
		}
	}

</script>
</head>
<body>
	<form name="magazineForm" id="magazineForm" method="post" onsubmit="return false;">
		<input type="hidden" name="pageNo" id="pageNo" value="${VO.pageNo}"/>
		<input type="hidden" name="magazineIdx" id="magazineIdx" value="${VO.magazineIdx}"/>
		
		<div class="content comm-brand brand-magazine">
	   		<page:applyDecorator  name="mobile.brandmenu" encoding="UTF-8"/>
	   		
		    <div class="page-title type-magazine">
		        <h2>
		            <strong>${detail.title}</strong>
		            <p>${detail.magazineNm}</p>
		        </h2>
		    </div>
			
			<div class="page-body">
			    <div class="magazine-wrap">
			        <div class="magazine-detailview">
			            <c:out value=" ${detail.magazineDesc}" escapeXml="false"/>
			        </div>
			        <div class="magazine-control">
			        	<c:if test="${!empty detailPrev or !empty detailNext}">
				            <div class="magazine-navi">
				                <a href="javascript:" class="magazine-prev <c:if test="${empty detailPrev}"> writing-no</c:if>" onclick="goDetail('${detailPrev.magazineIdx}');">
				                    <span>이전 글</span>
				                </a>
				                <a href="javascript:" class="magazine-next <c:if test="${empty detailNext}"> writing-no</c:if>" onclick="goDetail('${detailNext.magazineIdx}');">
				                    <span>다음 글</span>
				                </a>
				            </div>
			        	</c:if>
			            <div class="magazine-btn">
			                <a href="javascript:" class="btn" onclick="goList();"><span>목록</span></a>
			            </div>
			        </div>
			    </div>
		    </div>
		</div>	
	</form>
</body>
</html>