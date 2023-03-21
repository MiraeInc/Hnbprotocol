<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main"/>	
<script>

// 질문 수정
function goUpdate(counselIdx){
	$("#counselIdx").val(counselIdx);
	$("#statusFlag").val("U");
	
	var frm = document.counselForm;
	frm.action = "${CTX}/style/counselWrite.do";
	frm.submit();
}

// 질문 삭제
function goDelete(counselIdx){
	if (confirm("상담 질문을 삭제하시겠습니까?")) {
		$.ajax({			
			url: "${CTX}/ajax/style/counselDeleteAjax.do",
			data: {
						"counselIdx"	:	counselIdx,
					 },
			type: "POST",	
			async: false,
			contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
		 	error: function(request, status, error){ 
	 			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(flag){
				if (Number(flag) == -100) {
					alert("로그인이 필요합니다.");
					location.href="${CTX}/login/loginPage.do?refererYn=Y";
				} else {
					if(Number(flag) > 0){ 
						alert("삭제 되었습니다.");
						location.href="${CTX}/style/counselList.do";
					}else	{
						location.href="${CTX}/style/counselList.do";
					}
				}
			}
		});
	}
}

</script>
</head>
<body>
<form name="counselForm" id="counselForm" method="post" onsubmit="return false;">
	<input type="hidden" name="counselIdx" id="counselIdx" value="${detail.counselIdx}"/>
	<input type="hidden" name="statusFlag" id="statusFlag" value=""/>
	
	<div class="content comm-styleg styleg-counsel">
		<page:applyDecorator  name="mobile.stylemenu" encoding="UTF-8"/>

        <div class="counsel-view">
            <div class="counsel-q">
                <div class="counsel-q-head">
                    <p>${detail.qTitle}</p>
                </div>
                <div class="counsel-q-cont">
                    <span><c:out value="${detail.qContentsValue}" escapeXml="false"/></span>
                    <span class="counsel-q-name">${detail.memberNm}</span>

                    <div class="row fileimg-wrap">
                    	<c:if test="${!empty detail.qImg1}">
                        <div class="col col-3">
                            <div class="fileimg-box">
                                <img src="${IMGPATH}${detail.qImgPath1}" alt="첨부이미지">
                            </div>
                        </div>
                        </c:if>
                        <c:if test="${!empty detail.qImg2}">
                        <div class="col col-3">
                            <div class="fileimg-box">
                                <img src="${IMGPATH}${detail.qImgPath2}" alt="첨부이미지">
                            </div>
                        </div>
                        </c:if>
                        <c:if test="${!empty detail.qImg3}">
                        <div class="col col-3">
                            <div class="fileimg-box">
                                <img src="${IMGPATH}${detail.qImgPath3}" alt="첨부이미지">
                            </div>
                        </div>
                        </c:if>
                        <c:if test="${!empty detail.qImg4}">
                        <div class="col col-3">
                            <div class="fileimg-box">
                                <img src="${IMGPATH}${detail.qImgPath4}" alt="첨부이미지">
                            </div>
                        </div>
                        </c:if>
                    </div>
					<c:if test="${detail.qRegIdx eq memberIdx}">
                    <div class="counsel-util">
                        <a href="javascript:" class="btn" onclick="goDelete('${detail.counselIdx}');"><span class="txt">삭제</span></a>
                        <a href="javascript:" class="btn" onclick="goUpdate('${detail.counselIdx}');"><span class="txt">수정</span></a>
                    </div>
                    </c:if>
                </div>
            </div>
            <div class="counsel-a">
            <c:choose>
				<c:when test="${!empty detail.aContents}">
            	<div class="counsel-a-cont">
                    ${detail.aContents}

                    <div class="row fileimg-wrap">
                    	<c:if test="${!empty detail.aImg1}">
                        <div class="col col-3">
                            <div class="fileimg-box">
                                <img src="${IMGPATH}${detail.aImgPath1}" alt="첨부이미지">
                            </div>
                        </div>
                        </c:if>
                        <c:if test="${!empty detail.aImg2}">
                        <div class="col col-3">
                            <div class="fileimg-box">
                                <img src="${IMGPATH}${detail.aImgPath2}" alt="첨부이미지">
                            </div>
                        </div>
                        </c:if>
                        <c:if test="${!empty detail.aImg3}">
                        <div class="col col-3">
                            <div class="fileimg-box">
                                <img src="${IMGPATH}${detail.aImgPath3}" alt="첨부이미지">
                            </div>
                        </div>
                        </c:if>
                        <c:if test="${!empty detail.aImg4}">
                        <div class="col col-3">
                            <div class="fileimg-box">
                                <img src="${IMGPATH}${detail.aImgPath4}" alt="첨부이미지">
                            </div>
                        </div>
                        </c:if>
                    </div>
                </div>
                </c:when>
                <c:otherwise>
                <div class="counsel-empty">
                    <p>상담에 대한 답변이 없습니다.</p>
                    <p>빠른 시일내에 상담 내역에 대해 답변 드리도록 하겠습니다.</p>
                </div>
                </c:otherwise>
            </c:choose>
            </div>

            <div class="counsel-control">
                <div class="counsel-btn">
                    <a href="${CTX}/style/counselList.do" class="btn"><span class="txt">목록</span></a>
                </div>
            </div>
        </div>
	</div>
</form>
</body>
</html>