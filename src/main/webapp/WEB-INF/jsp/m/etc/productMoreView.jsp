<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>

<c:forEach var="list" items="${productList}" varStatus="idx">
	<li <c:if test="${list.soldoutYn eq 'Y'}">class="soldout"</c:if>>
		<div class="item">
			<div class="item-wrap">
				<a href="${CTX}/product/productView.do?goodsCd=${list.goodsCd}&choiceCateIdx=${list.choiceCateIdx}" class="item-anchor">
					<span class="badge-box">
						<c:if test="${list.opoYn eq 'Y' }" ><span class="badge type1">1+1</span></c:if>
						<c:if test="${list.tpoYn eq 'Y' }" ><span class="badge type2">2+1</span></c:if>
						<c:if test="${list.newYn eq 'Y' }" ><span class="badge type3">NEW</span></c:if>
						<c:if test="${list.bestYn eq 'Y' }" ><span class="badge type4">BEST</span></c:if>
						<c:if test="${list.saleiconYn eq 'Y' }" ><span class="badge type5">SALE</span></c:if>
						<c:if test="${list.pointiconYn eq 'Y' }" ><span class="badge type3"><i>POINT</i> X2</span></c:if>
					</span>
					<div class="item-thumb">
						<c:set var="imgSplit" value="${fn:split(list.mainFile ,'.') }"/>
						<img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_M.${imgSplit[1]}">
					</div>
					<p class="item-summary">${list.shortInfo}</p>
					<p class="item-name">${list.goodsTitle}</p>
					<div class="item-price">
						<fmt:formatNumber var="discountRate" value="${list.discountRate}" type="number" maxFractionDigits="0"/>
							<del class="origin"><c:if test="${discountRate != 0}" ><fmt:formatNumber value="${list.price}" /></c:if></del>
						<c:if test="${discountRate != 0}" >
							<span class="discount"><em>${discountRate}</em>%</span>
						</c:if>
						<span class="price"><fmt:formatNumber value="${list.discountPrice}" /></span>
					</div>
				</a>
				<a href="javascript:" id="cartBtn" class="btn-cart" onclick="goCart('${list.goodsIdx}','<c:out value="${list.goodsCd}"/>','<c:out value="${list.goodsTitle}"/>','<fmt:formatNumber value="${list.discountPrice}" groupingUsed="false"/>');"><span class="hide">장바구니</span></a>
			</div>
			<c:if test="${list.autoCouponYn eq 'Y' || list.onlineYn eq 'Y'}">
			<div class="item-etc">
				<c:if test="${list.autoCouponYn eq 'Y'}">
					<img src="${CTX}/images/${DEVICE}/common/ico_coupon_recommend.png" class="coupon" alt="COUPON" />
				</c:if>
				<c:if test="${list.onlineYn eq 'Y'}">
					<img src="${CTX}/images/${DEVICE}/common/ico_coupon_mobile.png" class="coupon" alt="MOBILE ONLY" />
				</c:if>
			</div>
			</c:if>
		</div>
	</li>
</c:forEach>
