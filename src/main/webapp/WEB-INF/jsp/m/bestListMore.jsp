<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
	<c:forEach var="list" items="${bestList}" varStatus="idx">
	<li onclick="location.href='${CTX}/product/productView.do?goodsCd=${list.goodsCd}';">
		<c:set var="imgSplit" value="${fn:split(list.mainFile ,'.') }"/>
		<img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_M.${imgSplit[1]}" alt="상품 썸네일">
		<strong>${list.goodsTitle}</strong>
		<p>${list.shortInfo}</p>
		<a href="javascript:" class="btn" onclick="buyNow('${list.goodsIdx}');">BUY</a>
	</li>
	</c:forEach>