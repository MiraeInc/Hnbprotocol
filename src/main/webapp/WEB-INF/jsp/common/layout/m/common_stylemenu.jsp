<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
		<div class="breadcrumb">
			<ul>
				<li>
					<a href="${CTX}/main.do"><span>Home</span></a>
				</li>
				<li>
					<a href="${CTX}/style/tipList.do"><span>TIPS</span></a>
				</li>
				<li class="current">
					<c:if test="${tabType eq '1'}"><a href="${CTX}/style/tipList.do" class="cate-link">스타일링 팁</a></c:if>
					<c:if test="${tabType eq '3'}"><a href="${CTX}/style/howtouseList.do" class="cate-link">상품 사용법</a></c:if>
					<div class="cate-list">
						<ul>
							<li <c:if test="${tabType eq '1'}">class="active"</c:if>><a href="${CTX}/style/tipList.do">스타일링 팁</a></li>
							<li <c:if test="${tabType eq '3'}">class="active"</c:if>><a href="${CTX}/style/howtouseList.do">상품 사용법</a></li>
						</ul>
					</div>
				</li>
			</ul>
		</div>
