<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>

<li id="option${detail.goodsCd}" data-goodsIdx="${detail.goodsIdx}" data-goodsCd="${detail.goodsCd}">
	<div class="item">
		<div class="item-view">
			<div class="view-thumb">
				<c:set var="imgSplit" value="${fn:split(detail.mainFile ,'.')}"/>
				<img src="${IMGPATH}/goods/${detail.goodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}">
			</div>
			<div class="view-info">
				<c:set var="buyMinCnt" value="${detail.buyMinCnt}" />
				<c:set var="discountPrice" value="${detail.discountPrice}" />
				<c:set var="couponDiscountPrice" value="${detail.couponDiscountPrice}" />
				<c:choose>
					<c:when test="${buyMinCnt eq 0}">
						<c:set var="productCnt" value="1" />
						<c:set var="productPrice" value="${productCnt * discountPrice}" />
						<c:set var="couponDiscountPrice" value="${productCnt * couponDiscountPrice}" />
					</c:when>
					<c:when test="${buyMinCnt > 0}">
						<c:set var="productCnt" value="${buyMinCnt}" />
						<c:set var="productPrice" value="${buyMinCnt * discountPrice}" />
						<c:set var="couponDiscountPrice" value="${buyMinCnt * couponDiscountPrice}" />
					</c:when>
				</c:choose>
				<p class="name">${detail.goodsTitle}</p>
				<div class="mod_qty">
					<span class="spinner">
						<input type="text" id="productCnt${detail.goodsCd}" class="productCnt" value="${productCnt}" onblur="changeCnt(this, '1','${detail.goodsCd}');" maxlength="4">
						<button type="button" class="btn-minus" onclick="changeBtnCnt('M','1','${detail.discountPrice}','${detail.buyMaxCnt}','${detail.buyMinCnt}','${detail.goodsCd}','${detail.goodsTitle}','${detail.couponDiscountPrice}','${detail.maxDiscount}','${detail.discountType}');">-</button>
						<button type="button" class="btn-plus" onclick="changeBtnCnt('P','1','${detail.discountPrice}','${detail.buyMaxCnt}','${detail.buyMinCnt}','${detail.goodsCd}','${detail.goodsTitle}','${detail.couponDiscountPrice}','${detail.maxDiscount}','${detail.discountType}');">+</button>
						<input type="hidden" id="hiddenCnt${detail.goodsCd}" value="" >
						<input type="hidden" id="maxDiscountFlag${detail.goodsCd}" value="Y" >
						<input type="hidden" id="couponPrice${detail.goodsCd}" value="${detail.couponDiscountPrice}" >
					</span>
					<span class="price" id="productPrice${detail.goodsCd}"><fmt:formatNumber value="${productPrice}"/></span>
				</div>
			</div>
		</div>
		<button type="button" class="btn-delete" onclick="productDel('${detail.goodsCd}');"><span class="hide">삭제</span></button>
	</div>
</li>