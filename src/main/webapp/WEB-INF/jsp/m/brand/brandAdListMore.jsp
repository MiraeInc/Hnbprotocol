<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<c:forEach var="list" items="${list}" varStatus="idx">
	<li>
		<iframe width="100%" height="480" src="https://www.youtube.com/embed/${list.videoUrlCode}?rel=0&autohide=1&autoplay=0&showinfo=0" frameborder="0" title="" marginheight="0" marginwidth="0" allowfullscreen></iframe>
		<p class="movie-title">${list.title}</p>
	</li>
</c:forEach>