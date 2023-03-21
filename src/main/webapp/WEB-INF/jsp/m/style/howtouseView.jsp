<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main"/>	
</head>
<body>
	<div class="content comm-styleg styleg-howto-view">
		<page:applyDecorator  name="mobile.stylemenu" encoding="UTF-8"/>

        <div class="page-title">
            <h2>
                <p>how to use</p>
                <strong>${master.title}</strong>
            </h2>
        </div>

        <div class="tab-menu">
            <div class="menu-inner">
                <ul class="menu-list">
                	<c:forEach var="list" items="${howtouseDetailList}" varStatus="idx">
                    <li><a href="#tabItem${list.howtouseDetailIdx}" class="menu-link <c:if test="${idx.index eq 0}">active</c:if>"><span>${list.goodsNm}</span></a></li>
                    </c:forEach>
                </ul>
            </div>
        </div>
		
		<c:forEach var="list" items="${howtouseDetailList}" varStatus="idx">
        <div id="tabItem${list.howtouseDetailIdx}" class="tab-panel" <c:if test="${idx.index eq 0}">style="display:block"</c:if>>
        	<div class="howto-wrap">
           		<c:out value="${list.mContents}" escapeXml="false"/>
            </div>
        </div>
        </c:forEach>
        
        <div class="styling-tip-control">
            <div class="styling-tip-btn">
                <a href="${CTX}/style/howtouseList.do" class="btn"><span class="txt">목록</span></a>
            </div>
        </div>
	</div>
	
<script type="text/javascript">
(function(window, document, $){
    //탭
    $.touchflow({
        wrap: '.tab-menu',
        container: '.menu-inner',
        list: '.menu-list',
        tablink: '.menu-link'
    });
})(window, document, jQuery);
</script>

</body>
</html>