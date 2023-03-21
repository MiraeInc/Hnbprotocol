<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<c:forEach var="list" items="${list}" varStatus="idx">
	<li>
		<a href="${CTX}/brand/brandMagazineView.do?magazineIdx=${list.magazineIdx}">
   			<span class="thumbnail-img">
      			<img src="${IMGPATH}/magazine/${list.magazineIdx}/${list.magazineImg}"> 
      		</span>
      		<span class="small-txt">${list.magazineNm}</span>
      		<span class="big-txt">${list.title}</span>
   		</a>
	</li>
</c:forEach>