<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main"/>	
</head>
<body>
    <div class="content comm-etc page-sitemap">
		<div class="page-body">

			<h2>Sitemap</h2>

            <ul class="sitemap">
                <c:forEach var="list1" items="${categoryList }" varStatus="idx1">
						<c:if test="${list1.depthLv eq 1 }">
							<li class="sitelist" style="height: 160px;">
								<c:choose>
									<c:when test="${list1.cateIdx eq 2}">
										<a class="site-mainmenu" href="${CTX }/product/productList.do?schCateIdx=${list1.cateIdx}&schCateFlag=ALL">${list1.cateNm}</a>
									</c:when>
									<c:otherwise>
										<a class="site-mainmenu" href="${CTX }/product/productList.do?schCateIdx=${list1.cateIdx}&schCateFlag=ALL">${list1.cateNm}</a>
									</c:otherwise>
								</c:choose>
								<ul class="site-submenu">
									<c:forEach var="list2" items="${categoryList}" varStatus="idx2">
										<c:if test="${idx2.index eq 0 }">
											<li><a href="${CTX }/product/productList.do?schCateIdx=${list1.cateIdx}&schCateFlag=ALL"><span>전체</span></a></li>
										</c:if>
										<c:if test="${list1.cateIdx eq list2.upperCateIdx }">
											<c:set var="cateIdx" value=""/>
											<c:set var="cateIdx2" value=""/>
											<c:forEach var="list3" items="${category3DepthList }" varStatus="idx3">
												<c:if test="${list2.cateIdx eq list3.upperCateIdx and cateIdx2 eq '' }">
													<li><a href="${CTX }/product/productList.do?schCateIdx=${list2.cateIdx}&sch2depthCateIdx=${list2.upperCateIdx}&sch3depthCateIdx=0"><span>${list2.cateNm}</span></a></li>
													<c:set var="cateIdx2" value="${list3.upperCateIdx}"/>
												</c:if>
												<c:set var="cateIdx" value="${list2.upperCateIdx}"/>
											</c:forEach>
											<c:if test="${cateIdx ne '' and cateIdx2 eq '' }">
												<li><a href="${CTX }/product/productList.do?schCateIdx=${list2.cateIdx}&sch2depthCateIdx=${list2.upperCateIdx}"><span>${list2.cateNm}</span></a></li>
											</c:if>
										</c:if>
									</c:forEach>
								</ul>
						</c:if>
					</c:forEach>
				<li class="sitelist" style="height: 130px">
                    <a class="site-mainmenu" href="${CTX }/event/exhibition/exhibitionView.do?exhibitionIdx=79">아울렛</a>
                </li>	
                <li class="sitelist" style="height: 130px">
                    <a class="site-mainmenu" href="${CTX }/product/bestProductList.do">BEST</a>
                </li>
                <li class="sitelist" style="height: 130px">
                    <a class="site-mainmenu" href="${CTX}/style/tipList.do">STYLE G</a>
                    <ul class="site-submenu">
                        <li>
                            <a href="${CTX}/style/tipList.do"><span>스타일링 팁</span></a>
                        </li>
                        <%-- <li>
                            <a href="${CTX}/style/counselList.do"><span>스타일 상담</span></a>
                        </li> --%>
                        <li>
                            <a href="${CTX}/style/howtouseList.do"><span>상품 사용법</span></a>
                        </li>
                    </ul>
                </li>
                <li class="sitelist" style="height: 130px">
                    <a class="site-mainmenu" href="${CTX}/event/event/eventList.do">EVENT</a>
                    <ul class="site-submenu">
                        <li>
                            <a href="${CTX}/event/event/eventList.do"><span>이벤트</span></a>
                        </li>
                        <li>
                            <a href="${CTX}/event/exhibition/exhibitionList.do"><span>기획전</span></a>
                        </li>
                        <li>
                            <a href="${CTX}/style/sampleList.do"><span>정품 신청</span></a>
                        </li>
                        <c:if test="${timeSaleCnt > 0}">
                        <li>
                            <a href="${CTX}/event/timeSale/timeSaleList.do"><span>타임세일</span></a>
                        </li>
                        </c:if>
                        <%-- <li>
                            <a href="${CTX}/event/couponBook.do"><span>쿠폰 북</span></a>
                        </li> --%>
                    </ul>
                </li>
                <li class="sitelist" style="height: 130px">
                    <a class="site-mainmenu" href="${CTX}/brand/brandGatsby.do">BRAND</a>
                    <ul class="site-submenu">
                        <li>
                            <a href="${CTX}/brand/brandAboutMandom.do"><span>ABOUT mandom</span></a>
                        </li>
                        <li>
                            <a href="${CTX}/brand/brandAdList.do?brandIdx=1"><span>동영상</span></a>
                        </li>
                        <li>
                            <a href="${CTX}/brand/brandMagazineList.do"><span>매거진</span></a>
                        </li>
                    </ul>
                </li>
                <li class="sitelist" style="height: 250px">
                    <a class="site-mainmenu" href="${CTX}/cscenter/csMain.do">고객센터</a>
                    <ul class="site-submenu">
                        <li>
                            <a href="${CTX}/cscenter/notice/noticeList.do"><span>공지사항</span></a>
                        </li>
                        <li>
                            <a href="${CTX}/cscenter/faq/faqList.do"><span>FAQ</span></a>
                        </li>
                        <li>
                            <a href="${CTX}/cscenter/csInfo/benefitInfo.do"><span>혜택 안내</span></a>
                        </li>
                    </ul>
                </li>
                <li class="sitelist" style="height: 250px">
                    <a class="site-mainmenu" href="#none">마이페이지</a>
                    <ul class="site-submenu">
                        <li>
                            <a href="${CTX}/mypage/order/myOrderList.do"><span>주문 관리</span></a>
                        </li>
                        <li>
                            <a href="${CTX}/mypage/wish/wishList.do"><span>위시리스트</span></a>
                        </li>
                        <li>
                            <a href="${CTX}/mypage/point/pointList.do"><span>포인트</span></a>
                        </li>
                        <li>
                            <a href="${CTX}/mypage/point/couponList.do"><span>쿠폰</span></a>
                        </li>
                        <li>
                            <a href="${CTX}/mypage/sample/sampleList.do"><span>이 달의 정품 신청</span></a>
                        </li>
                        <li>
                            <a href="${CTX}/mypage/review/noWriteReviewList.do"><span>상품후기</span></a>
                        </li>
                        <li>
                            <a href="${CTX}/mypage/inquiry/inquiryList.do"><span>1:1 문의</span></a>
                        </li>
                        <%-- <li>
                            <a href="${CTX}/mypage/member/billkeyList.do"><span>원클릭 결제 카드 관리</span></a>
                        </li> --%>
                        <li>
                            <a href="${CTX}/mypage/member/snsConnect.do"><span>SNS 계정 연결 관리</span></a>
                        </li>
                        <li>
                            <a href="${CTX}/mypage/member/memberShipping.do"><span>배송지 관리</span></a>
                        </li>
                        <li>
                            <a href="${CTX}/mypage/member/memberInfo.do"><span>개인정보 수정</span></a>
                        </li>
                    </ul>
                </li>
            </ul>
       	</div>
    </div>
</body>
</html>