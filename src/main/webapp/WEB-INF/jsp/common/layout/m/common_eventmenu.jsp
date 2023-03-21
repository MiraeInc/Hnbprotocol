<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<c:choose>
	<c:when test="${gnbBrand eq 'default'}">
		<c:set var="brandIdx" value="1"  />
	</c:when>
	<c:when test="${gnbBrand eq 'gatsby'}">
		<c:set var="g_brand_idx" value="1"  />
	</c:when>
	<c:when test="${gnbBrand eq 'bifesta'}">
		<c:set var="g_brand_idx" value="3" />
	</c:when>
	<c:when test="${gnbBrand eq 'lucidol'}">
		<c:set var="g_brand_idx" value="4"/>
	</c:when>
	<c:when test="${gnbBrand eq 'mamabutter'}">
		<c:set var="g_brand_idx" value="6"/>
	</c:when>
	<c:when test="${gnbBrand eq 'dentalpro'}">
		<c:set var="g_brand_idx" value="7"  />
	</c:when>
	<c:when test="${gnbBrand eq 'charley'}">
		<c:set var="g_brand_idx" value="8" />
	</c:when>
	<c:otherwise>
		<c:set var="g_brand_idx" value="1"  />
	</c:otherwise>
</c:choose>
		<div class="breadcrumb">
			<ul>
				<li>
					<a href="${CTX}/main.do"><span>Home</span></a>
				</li>
				<li>
					<a href="${CTX}/event/event/eventList.do" class="cate-link"><span>이벤트</span></a>
					<div class="cate-list">
						<ul>
							<c:forEach var="list1" items="${categoryList}" varStatus="idx1">
								<c:if test="${list1.depthLv eq 1 and list1.brandIdx eq g_brand_idx}">
									<li><a href="${CTX }/product/productList.do?schGbn=T&schCateIdx=${list1.cateIdx}&schCateFlag=ALL" ><span>${list1.cateNm}</span></a></li>
								</c:if>
							</c:forEach>
							<li><a href="${CTX}/event/exhibition/exhibitionView.do?exhibitionIdx=79"><span>아울렛</span></a></li>
							<li><a href="${CTX}/product/bestProductList.do"><span>베스트</span></a></li>
							<li><a href="${CTX}/style/sampleList.do"><span>정품신청</span></a></li>
							<li><a href="${CTX}/style/tipList.do"><span>TIPS</span></a></li>
							<li><a href="${CTX}/brand/brandAboutMandom.do"><span>브랜드</span></a></li>
						</ul>
					</div>
				</li>
				<li class="current">
					<c:if test="${tabType eq '1'}"><a href="${CTX}/event/event/eventList.do" class="cate-link"><span>이벤트</span></a></c:if>
					<c:if test="${tabType eq '2'}"><a href="${CTX}/event/exhibition/exhibitionList.do" class="cate-link"><span>기획전</span></a></c:if>
					<c:if test="${tabType eq '5'}"><a href="${CTX}/style/sampleList.do" class="cate-link"><span>정품신청</span></a></c:if>
					<c:if test="${timeSaleCnt > 0}">
						<c:if test="${tabType eq '3'}"><a href="${CTX}/event/timeSale/timeSaleList.do" class="cate-link"><span>타임세일</span></a></c:if>
					</c:if>
					<%-- <c:if test="${tabType eq '4'}"><a href="${CTX}/event/couponBook.do" class="cate-link"><span>쿠폰 북</span></a></c:if> --%>
					<div class="cate-list">
						<ul>
							<li <c:if test="${tabType eq '1'}">class="active"</c:if>><a href="${CTX}/event/event/eventList.do">이벤트</a></li>
							<li <c:if test="${tabType eq '2'}">class="active"</c:if>><a href="${CTX}/event/exhibition/exhibitionList.do">기획전</a></li>
							<li <c:if test="${tabType eq '5'}">class="active"</c:if>><a href="${CTX}/style/sampleList.do">정품신청</a></li>
							<c:if test="${timeSaleCnt > 0}">
								<li <c:if test="${tabType eq '3'}">class="active"</c:if>><a href="${CTX}/event/timeSale/timeSaleList.do">타임세일</a></li>
							</c:if>
							<%-- <li <c:if test="${tabType eq '4'}">class="active"</c:if>><a href="#none">쿠폰 북</a></li> --%>
						</ul>
					</div>
				</li>
			</ul>
		</div>
		
		<c:set var="tabSize" value="3"/>
		<c:choose>
			<c:when test="${timeSaleCnt > 0}">
				<c:set var="tabSize" value="4"/>
			</c:when>
			<c:otherwise>
				<c:set var="tabSize" value="3"/>
			</c:otherwise>
		</c:choose>
		
		<div class="tab-style" data-size="${tabSize}">
			<a href="${CTX}/event/event/eventList.do" class="tab-link <c:if test="${tabType eq '1'}">active</c:if>"><span>이벤트</span></a>
			<a href="${CTX}/event/exhibition/exhibitionList.do" class="tab-link <c:if test="${tabType eq '2'}">active</c:if>"><span>기획전</span></a>
			<a href="${CTX}/style/sampleList.do" class="tab-link <c:if test="${tabType eq '5'}">active</c:if>"><span>정품신청</span></a>
			<c:if test="${timeSaleCnt > 0}">
				<a href="${CTX}/event/timeSale/timeSaleList.do" class="tab-link <c:if test="${tabType eq '3'}">active</c:if>"><span>타임세일</span></a>
			</c:if>
			<%-- <a href="${CTX}/event/couponBook.do" class="tab-link <c:if test="${tabType eq '4'}">active</c:if>"><span>쿠폰 북</span></a> --%>
		</div>
		
		<%-- <div class="breadcrumb">
			<ul>
				<li>
					<a href="${CTX}/main.do"><span>H</span></a>
				</li>
				<li>
					<a href="${CTX}/event/event/eventList.do"><span>EVENT</span></a>
				</li>
				<li class="current">
					<c:if test="${tabType eq '1'}"><a href="${CTX}/event/event/eventList.do" class="cate-link"><span>이벤트</span></a></c:if>
					<c:if test="${tabType eq '2'}"><a href="${CTX}/event/exhibition/exhibitionList.do" class="cate-link"><span>기획전</span></a></c:if>
					<c:if test="${tabType eq '5'}"><a href="${CTX}/style/sampleList.do" class="cate-link"><span>정품신청</span></a></c:if>
					<c:if test="${timeSaleCnt > 0}">
						<c:if test="${tabType eq '3'}"><a href="${CTX}/event/timeSale/timeSaleList.do" class="cate-link"><span>타임세일</span></a></c:if>
					</c:if>
					<c:if test="${tabType eq '4'}"><a href="${CTX}/event/couponBook.do" class="cate-link"><span>쿠폰 북</span></a></c:if>
					<div class="cate-list">
						<ul>
							<c:if test="${tabType ne '1'}"><li><a href="${CTX}/event/event/eventList.do">이벤트</a></li></c:if>
							<c:if test="${tabType ne '2'}"><li><a href="${CTX}/event/exhibition/exhibitionList.do">기획전</a></li></c:if>
							<c:if test="${tabType ne '5'}"><li><a href="${CTX}/style/sampleList.do">정품신청</a></li></c:if>
							<c:if test="${timeSaleCnt > 0}">
								<c:if test="${tabType ne '3'}"><li><a href="${CTX}/event/timeSale/timeSaleList.do">타임세일</a></li></c:if>
							</c:if>
							<c:if test="${tabType ne '4'}"><li><a href="#none">쿠폰 북</a></li></c:if>
						</ul>
					</div>
				</li>
			</ul>
		</div> --%>
