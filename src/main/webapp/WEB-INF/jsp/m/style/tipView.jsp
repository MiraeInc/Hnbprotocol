<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main"/>	
</head>
<body>
	<div class="content comm-styleg styleg-tip-view">
		<page:applyDecorator  name="mobile.stylemenu" encoding="UTF-8"/>  

        <div class="styling-tip">
            <div class="styling-tip-title">
                <div class="styling-tip-horizon">
                   <p>${detail.tipTitle}</p>
                </div>
            </div>

            <div class="styling-tip-cont">
                <c:out value="${detail.tipContents}" escapeXml="false"/>
            </div>

            <div class="styling-tip-control">
                <div class="styling-tip-btn">
                    <a href="${CTX}/style/tipList.do" class="btn"><span class="txt">목록</span></a>
                </div>
            </div>
        </div>
	</div>
</body>
</html>