<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>

    <!-- <script src="/m/js/m/device_check.js"></script> -->
	<script type="text/javascript">
		deviceCheck("<spring:message code='server.status'/>");
	</script>

	<script type="text/javascript">
		$(document).ready(function(){
			$("#wrap").utils();	
			
			var url = document.location.href;
			var urlContain = url.search("productView");
			if(urlContain != -1){
				// css : bottom 70
				$(".util-box").css("bottom", "70px");
			};
			
			srcPath();	// 유입경로 호출
			
			/* $('.main-logo').on('click', 'a', function(){
				$(this).toggleClass('active');
				$('.header-brand').toggle();
			}); */
			
		});
	</script>

	<c:if test="${!empty bannerHeader}">
	<script type="text/javascript">
		$(document).ready(function(){
			var idx = "${bannerHeader.bannerIdx}";
			var gubun = "${bannerHeader.deviceGubun}";
			var cookie = getCookie("todayPop"+gubun+idx);
			
			if(cookie=="" || cookie==null){
				$("#global-banner").css("display", "block");
			}else{
				$("#global-banner").remove();
			}
		});
	
		function closePop(){
			var idx = $("#todayPop").attr("data-idx");
			var gubun = $("#todayPop").attr("data-device");
			
			setCookie("todayPop"+gubun+idx, "todayPop"+gubun+idx, 1);
			$("#global-banner").remove();				
		}
	</script>
	<!-- 최상단 띠 배너 -->
	<%-- AS-IS <div id="global-banner" style="background-color: ${bannerHeader.bgColor}; display: none;"> --%>
	<div id="global-banner" style="display: none;">
		<div class="banner-wrap">
			<a href="<c:if test="${empty bannerHeader.moLinkUrl}">javascript:</c:if><c:if test="${!empty bannerHeader.moLinkUrl}">${bannerHeader.moLinkUrl}</c:if>" <c:if test="${bannerHeader.moLinkFlag eq '_BLANK'}">target="_BLANK"</c:if>" 
				style="background-image: url('${IMGPATH}/banner/${bannerHeader.bannerIdx}/${bannerHeader.moBannerImg}');">
				<span class="sr-only">${bannerHeader.bannerInfo}</span>
			</a>
		</div>
		<%-- 띠배너 배경색 분리 --%>
		<div class="banner-bg">
			<div class="bg-left" style="background-color: ${bannerHeader.bgColor}"></div>
			<div class="bg-right" style="background-color: ${bannerHeader.bgColor2}"></div>
		</div>
		<%-- //띠배너 배경색 분리 --%>
		<a href="javascript:" class="banner-close" id="todayPop" data-idx="${bannerHeader.bannerIdx}" data-device="${bannerHeader.deviceGubun}" onclick="closePop();">
			<span class="hide">배너 닫기</span>
		</a>
	</div>
	<!-- //최상단 띠 배너 -->
	</c:if>
	
	<c:choose>
	<c:when test="${layout_type eq 'gatsby'}">
		<c:set var="g_brand_idx" value="1" />
	</c:when>
	<c:when test="${layout_type eq 'bifesta'}">
		<c:set var="g_brand_idx" value="3" />
	</c:when>
	<c:when test="${layout_type eq 'lucidol'}">
		<c:set var="g_brand_idx" value="4" />
	</c:when>
	<c:when test="${layout_type eq 'mamabutter'}">
		<c:set var="g_brand_idx" value="6" />
	</c:when>
	<c:when test="${layout_type eq 'dentalpro'}">
		<c:set var="g_brand_idx" value="7" />
	</c:when>
	<c:when test="${layout_type eq 'charley'}">
		<c:set var="g_brand_idx" value="8" />
	</c:when>
	<%-- <c:when test="${layout_type eq 'lucido'}">
		<c:set var="g_brand_idx" value="13" />
	</c:when> --%>
	<c:otherwise>
		<c:set var="g_brand_idx" value="1" />
	</c:otherwise>
	</c:choose>

	<header id="header" class="header">
		<%-- <div class="header-brand">
			<h1 class="brand-logo"><a href="${CTX}/main.do"><img src="${CTX}/images/${DEVICE}/common/logo_mandom.png" alt="면역공방" /></a></h1>
			<ul id="brandList" class="brand-list">
				<li class="link-gatsby"><a href="${CTX}/gatsby/main.do" class="<c:if test="${layout_type eq 'gatsby' || layout_type eq '' || layout_type eq null}">active</c:if>">갸스비</a></li>
				<li class="link-bifesta"><a href="${CTX}/bifesta/main.do" class="<c:if test="${layout_type eq 'bifesta'}">active</c:if>">비페스타</a></li>
				<li class="link-lucidol"><a href="${CTX}/lucidol/main.do" class="<c:if test="${layout_type eq 'lucidol'}">active</c:if>">루시도엘</a></li>
				<li class="link-lucido"><a href="${CTX}/lucido/main.do" class="<c:if test="${layout_type eq 'lucido'}">active</c:if>">루시도</a></li>				
				<li class="link-barrier"><a href="${CTX}/barrier/main.do" class="<c:if test="${layout_type eq 'barrier'}">active</c:if>">베리어리페어</a></li>
				
				<li>
					<a href="javascript:void(0);" class="btn-morelink"><span>브랜드 더보기</span></a>
					<ul class="brand-more">
						<li class="link-mamabutter"><a href="${CTX}/mamabutter/main.do" class="<c:if test="${layout_type eq 'mamabutter'}">active</c:if>">마마버터</a></li>
						<li class="link-dentalpro"><a href="${CTX}/dentalpro/main.do" class="<c:if test="${layout_type eq 'dentalpro'}">active</c:if>">덴탈프로</a></li>
						<li class="link-charley"><a href="${CTX}/charley/main.do" class="<c:if test="${layout_type eq 'charley'}">active</c:if>">찰리</a></li>
						<li class="link-gpcreate"><a href="${CTX}/gpcreate/main.do" class="<c:if test="${layout_type eq 'gpcreate'}">active</c:if>">G.P.CREATE</a></li>

					</ul>
				</li>
			</ul>
		</div> --%>
		<div class="header-main">
			<h2 class="main-logo">
				<c:choose>
					<c:when test="${layout_type eq 'gatsby'}">
    					<a href="${CTX}/main.do"><img src="${CTX}/images/${DEVICE}/logo/logo_top_gatsby.png" style="height: 39px;" alt="gatsby"></a>
					</c:when>
					<c:otherwise>
						<a href="${CTX}/main.do"><img src="${CTX}/images/${DEVICE}/logo/logo_top_gatsby.png" style="height: 39px;" alt="gatsby"></a>
					</c:otherwise>
				</c:choose>
			</h2>
			<button type="button" class="btn-search" onclick="location.href='${CTX}/etc/searchResult.do';"><span class="hide">검색</span></button>
			<a href="${CTX}/cart.do" class="btn-cart"   id="btnCart" data-cart-num="<fmt:formatNumber value="${fn:length(headerCartList)}" groupingUsed="true"/>"><span class="hide">장바구니</span></a>
		</div>
		
		<!-- 기존에서 수정됨 -->
		<div class="header-brand">
			<a href="${CTX}/gatsby/main.do" class="lnk-gatsby <c:if test="${layout_type eq 'gatsby' || layout_type eq '' || layout_type eq null}">active</c:if>"><span class="hide">hnb</span></a>
			<a href="${CTX}/bifesta/main.do" class="lnk-bifesta <c:if test="${layout_type eq 'bifesta'}">active</c:if>"><span class="hide">BIFESTA</span></a>
		</div>
		
		<!-- 기존 -->
		<%-- <div class="header-brand">
			<a href="${CTX}/gatsby/main.do" class="lnk-gatsby <c:if test="${layout_type eq 'gatsby' || layout_type eq '' || layout_type eq null}">active</c:if>"><span class="hide">GATSBY</span></a>
			<a href="${CTX}/bifesta/main.do" class="lnk-bifesta <c:if test="${layout_type eq 'bifesta'}">active</c:if>"><span class="hide">BIFESTA</span></a>
			<a href="${CTX}/lucidol/main.do" class="lnk-lucidol <c:if test="${layout_type eq 'lucidol'}">active</c:if>"><span class="hide">LUCIDO-L</span></a>
			<a href="${CTX}/lucido/main.do" class="lnk-lucido <c:if test="${layout_type eq 'lucido'}">active</c:if>"><span class="hide">LUCIDO</span></a>			
			<a href="${CTX}/barrier/main.do" class="lnk-barrier <c:if test="${layout_type eq 'barrier'}">active</c:if>"><span class="hide">BARRIER REPAIR</span></a>
			<a href="${CTX}/mamabutter/main.do" class="lnk-mamabutter <c:if test="${layout_type eq 'mamabutter'}">active</c:if>"><span class="hide">MAMA BUTTER</span></a>
			<a href="${CTX}/dentalpro/main.do" class="lnk-dentalpro <c:if test="${layout_type eq 'dentalpro'}">active</c:if>"><span class="hide">DENTALPRO</span></a>
			<a href="${CTX}/charley/main.do" class="lnk-charley <c:if test="${layout_type eq 'charley'}">active</c:if>"><span class="hide">BATH BOMB</span></a>
			<a href="${CTX}/gpcreate/main.do" class="lnk-gpcreate <c:if test="${layout_type eq 'gpcreate'}">active</c:if>"><span class="hide">BATH SALT</span></a>
		</div> --%>
	</header>
	
	<%-- <div class="nav-wrap">
		<!-- 2023.02.27 수정 : 텍스트 바꾸기 -->
		<ul class="nav-etc">
			<li class="etc-outlet"><a href="${CTX}/event/exhibition/exhibitionView.do?exhibitionIdx=79" <c:if test="${gnbActive eq 'outlet'}">class="active"</c:if>><span>아울렛</span></a></li>
			<li><a href="${CTX}/product/bestProductList.do?gnbBrand=${layout_type}" <c:if test="${gnbActive eq 'best'}">class="active"</c:if>><span>베스트</span></a></li>
			<li><a href="${CTX}/event/event/eventList.do" <c:if test="${gnbActive eq 'event'}">class="active"</c:if>><span>이벤트</span></a></li>
			<li><a href="${CTX}/style/sampleList.do" <c:if test="${gnbActive eq 'sample'}">class="active"</c:if>><span>정품신청</span></a></li>
			<li><a href="${CTX}/event/exhibition/exhibitionList.do" <c:if test="${gnbActive eq 'exhibition'}">class="active"</c:if>><span>기획전</span></a></li>
		</ul>
		<ul class="nav-etc">
			<li><a href="#"><span>추천상품</span></a></li>
			<li><a href="${CTX}/product/bestProductList.do?gnbBrand=${layout_type}" <c:if test="${gnbActive eq 'best'}">class="active"</c:if>><span>베스트상품</span></a></li>
			<li><a href="#"><span>브랜드관</span></a></li>
			<li><a href="#"><span>주문/배송</span></a></li>
		</ul>
		
		<a href="#divNavAll" id="btnNavAll" class="btn-all"><span>면역공방 ALL</span></a>
		<div id="divNavAll" class="nav-all">
			<div class="nav-cate">
				<p class="nav-name">by <strong>카테고리</strong></p>
				<ul class="cate-list">
					<c:forEach var="list1" items="${totalCateList}" varStatus="idx1">
					<c:if test="${list1.depthLv eq 1}">
						<li class="cate-item">
							<a href="#none" class="cate-link">${list1.cateNm}</a>
							<ul class="sub-list">
								<c:if test="${list1.childCnt > 0}">
								<c:forEach var="list2" items="${totalCateList}" varStatus="idx2">
									<c:if test="${list1.cateIdx eq list2.upperCateIdx }">
										<li class="sub-item">
										<c:choose>
											<c:when test="${list2.childCnt > 0}">
											<a href="#none" class="sub-link">${list2.cateNm}</a>
											</c:when>
											<c:otherwise>
											<a href="${CTX }/product/productList.do?schGbn=T&schCateIdx=${list2.cateIdx}&sch2depthCateIdx=${list1.cateIdx}" class="sub-link">${list2.cateNm}</a>
											</c:otherwise>
										</c:choose>
											<c:if test="${list2.childCnt > 0}">
												<ul class="depth-list">
													<c:forEach var="list3" items="${totalCateList }" varStatus="idx3">
													<c:if test="${idx3.index eq 0 }">
														<li class="depth-item"><a href="${CTX }/product/productList.do?schGbn=T&schCateIdx=${list2.cateIdx}&sch2depthCateIdx=${list2.upperCateIdx}" class="depth-link">전체</a></li>
													</c:if>
													<c:if test="${list2.cateIdx eq list3.upperCateIdx }">
														<c:if test="${list2.cateIdx eq list3.upperCateIdx }">
															<li class="depth-item"><a href="${CTX }/product/productList.do?schGbn=T&schCateIdx=${list2.cateIdx}&sch2depthCateIdx=${list1.cateIdx}&sch3depthCateIdx=${list3.cateIdx}" class="depth-link">${list3.cateNm}</a></li>
														</c:if>
													</c:if>
													</c:forEach>
												</ul>
											</c:if>
										</li>
										
									</c:if>
								</c:forEach>
								
								</c:if>
							</ul>
						</li>
						</c:if>
					</c:forEach>
				
					</ul>

			</div>
			<div class="nav-brand">
				<p class="nav-name">by <strong>브랜드</strong></p>
				<div class="brand-list">
					<div class="brand-item">
						<a href="${CTX}/gatsby/main.do" class="brand-anchor"><img src="${CTX}/images/${DEVICE}/common/img_brand_gatsby.png" alt="gatsby" /></a>
					</div>
					<div class="brand-item">
						<a href="${CTX}/bifesta/main.do" class="brand-anchor"><img src="${CTX}/images/${DEVICE}/common/img_brand_bifesta.png" alt="bifesta" /></a>
					</div>
					<div class="brand-item">
						<a href="${CTX}/lucidol/main.do" class="brand-anchor"><img src="${CTX}/images/${DEVICE}/common/img_brand_lucidol.png" alt="lucidoel" /></a>
					</div>
 					<div class="brand-item">
							<a href="${CTX}/lucido/main.do" class="brand-anchor"><img src="${CTX}/images/${DEVICE}/common/img_brand_lucido.png" alt="lucidoe" /></a>
					</div>
				
					<div class="brand-item">
						<a href="${CTX}/barrier/main.do" class="brand-anchor"><img src="${CTX}/images/${DEVICE}/common/img_brand_barrier.png" alt="barrier repair" /></a>
					</div>
					<div class="brand-item">
						<a href="${CTX}/mamabutter/main.do" class="brand-anchor"><img src="${CTX}/images/${DEVICE}/common/img_brand_mamabutter.png" alt="mamabutter" /></a>
					</div>
					<div class="brand-item">
						<a href="${CTX}/dentalpro/main.do" class="brand-anchor"><img src="${CTX}/images/${DEVICE}/common/img_brand_dentalpro.png" alt="dentalpro" /></a>
					</div>
					<div class="brand-item">
						<a href="${CTX}/charley/main.do" class="brand-anchor"><img src="${CTX}/images/${DEVICE}/common/img_brand_charley.png" alt="charley" /></a>
					</div>
					<div class="brand-item">
						<a href="${CTX}/gpcreate/main.do" class="brand-anchor"><img src="${CTX}/images/${DEVICE}/common/img_brand_gpcreate.png" alt="gp create" /></a>
					</div>
				</div>
			</div>
			<button type="button" class="btn-close"><span>닫기</span></button>
		</div>
	</div> --%>
	
	<!-- s: aside -->
	<aside id="gnb" class="aside">
		<button type="button" id="btnMenuClose" class="btn_close"><span class="hide">메뉴닫기</span></button>

		<div class="aside-member">
			<div class="member-login">
				<c:if test="${IS_LOGIN eq false}"> 
					<p class="txt">
						지금 <strong>회원 가입</strong> 하시면<br/>
						매월 무배 쿠폰 + 구매 적립
					</p>
				</c:if>
				<div class="btns">
					<c:choose>
						<c:when test="${IS_LOGIN eq false}"> 
							<a href="${CTX}/login/loginPage.do" class="btn btn-login"><span>로그인</span></a>
							<a href="${CTX}/member/joinStep01.do" class="btn btn-join"><span>회원가입</span></a>
						</c:when>
						<c:otherwise>
							<p class="txt"><strong class="name">${USERINFO.memberNm}</strong>님<br/> 로그인하셨습니다.</p>
							<div class="btns">
								<a href="${CTX}/logout.do" class="btn btn-join"><span class="txt">로그아웃</span></a>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>	

			<div class="member-summary">
				<ul>
					<li>
						<a href="${CTX}/mypage/order/main.do?refererYn=Y">
							<span class="ico_mp"></span>
							<p>마이페이지</p>
						</a>
					</li>
					<li>
						<a href="${CTX}/mypage/order/myOrderList.do">
							<span class="cnt">0</span>
							<p>주문/배송</p>
						</a>
					</li>
					<li>
						<a href="${CTX}/mypage/point/couponList.do">
							<span class="cnt">
								<c:choose>
									<c:when test="${mypageCouponCnt > 0}"><fmt:formatNumber value="${mypageCouponCnt}" groupingUsed="true"/></c:when>
									<c:otherwise>0</c:otherwise>
								</c:choose>
							</span>
							<p>쿠폰</p>
						</a>
					</li>
					<li>
						<a href="${CTX}/mypage/point/pointList.do">
							<span class="cnt">
								<c:choose>
									<c:when test="${mypagePointM > 0}"><fmt:formatNumber value="${mypagePointM}" groupingUsed="true"/></c:when>
									<c:otherwise>0</c:otherwise>
								</c:choose>
							</span>
							<p>포인트</p>
						</a>
					</li>
				</ul>
			</div>
		</div>
		<!-- 2023.03.07 수정 -->
		<%-- 
		<div class="aside-category">
			<div class="nav-all">
				<div class="nav-cate">
					<p class="nav-name">by <strong>카테고리</strong></p>
					<ul class="cate-list">
					<c:forEach var="list1" items="${totalCateList}" varStatus="idx1">
					<c:if test="${list1.depthLv eq 1}">
						<li class="cate-item">
							<a href="#none" class="cate-link">${list1.cateNm}</a>
							<ul class="sub-list">
								<c:if test="${list1.childCnt > 0}">
								<c:forEach var="list2" items="${totalCateList}" varStatus="idx2">
									<c:if test="${list1.cateIdx eq list2.upperCateIdx }">
										<li class="sub-item">
										<c:choose>
											<c:when test="${list2.childCnt > 0}">
											<a href="#none" class="sub-link">${list2.cateNm}</a>
											</c:when>
											<c:otherwise>
											<a href="${CTX }/product/productList.do?schGbn=T&schCateIdx=${list2.cateIdx}&sch2depthCateIdx=${list1.cateIdx}" class="sub-link">${list2.cateNm}</a>
											</c:otherwise>
										</c:choose>
											<c:if test="${list2.childCnt > 0}">
												<ul class="depth-list">
													<c:forEach var="list3" items="${totalCateList }" varStatus="idx3">
													<c:if test="${idx3.index eq 0 }">
														<li class="depth-item"><a href="${CTX }/product/productList.do?schGbn=T&schCateIdx=${list2.cateIdx}&sch2depthCateIdx=${list2.upperCateIdx}" class="depth-link">전체</a></li>
													</c:if>
													<c:if test="${list2.cateIdx eq list3.upperCateIdx }">
														<c:if test="${list2.cateIdx eq list3.upperCateIdx }">
															<li class="depth-item"><a href="${CTX }/product/productList.do?schGbn=T&schCateIdx=${list2.cateIdx}&sch2depthCateIdx=${list1.cateIdx}&sch3depthCateIdx=${list3.cateIdx}" class="depth-link">${list3.cateNm}</a></li>
														</c:if>
													</c:if>
													</c:forEach>
												</ul>
											</c:if>
										</li>
										
									</c:if>
								</c:forEach>
								
								</c:if>
							</ul>
						</li>
						</c:if>
					</c:forEach>
				
					</ul>
				</div>
				<div class="nav-brand">
					<p class="nav-name">by <strong>브랜드</strong></p>
					<div class="brand-list">
						<div class="brand-item">
							<a href="${CTX}/gatsby/main.do" class="brand-anchor"><img src="${CTX}/images/${DEVICE}/common/img_brand_gatsby.png" alt="gatsby" /></a>
						</div>
						<div class="brand-item">
							<a href="${CTX}/bifesta/main.do" class="brand-anchor"><img src="${CTX}/images/${DEVICE}/common/img_brand_bifesta.png" alt="bifesta" /></a>
						</div>
						<div class="brand-item">
							<a href="${CTX}/lucidol/main.do" class="brand-anchor"><img src="${CTX}/images/${DEVICE}/common/img_brand_lucidol.png" alt="lucidol" /></a>
						</div>
 					<div class="brand-item">
							<a href="${CTX}/lucido/main.do" class="brand-anchor"><img src="${CTX}/images/${DEVICE}/common/img_brand_lucido.png" alt="lucido" /></a>
					</div>
		
						<div class="brand-item">
							<a href="${CTX}/barrier/main.do" class="brand-anchor"><img src="${CTX}/images/${DEVICE}/common/img_brand_barrier.png" alt="barrier repair" /></a>
						</div> 

						<div class="brand-item">
							<a href="${CTX}/mamabutter/main.do" class="brand-anchor"><img src="${CTX}/images/${DEVICE}/common/img_brand_mamabutter.png" alt="mamabutter" /></a>
						</div>

						<div class="brand-item">
							<a href="${CTX}/dentalpro/main.do" class="brand-anchor"><img src="${CTX}/images/${DEVICE}/common/img_brand_dentalpro.png" alt="dentalpro" /></a>
						</div>

						<div class="brand-item">
							<a href="${CTX}/charley/main.do" class="brand-anchor"><img src="${CTX}/images/${DEVICE}/common/img_brand_charley.png" alt="charley" /></a>
						</div>
						<div class="brand-item">
						<a href="${CTX}/gpcreate/main.do" class="brand-anchor"><img src="${CTX}/images/${DEVICE}/common/img_brand_gpcreate.png" alt="gp create" /></a>
						</div>

					</div>
				</div>
			</div>
		</div>
		 --%>
		<div class="aside-pane" style="display:block;">
			<ul class="gnb-list">
				<!-- 2023.03.07 수정 -->
				<%-- 
				<li class="item">
					<a class="gnb-link" href="${CTX}/event/event/eventList.do">EVENT</a>
					<ul class="gnb-sub">
						<li><a href="${CTX}/event/event/eventList.do"><span>이벤트</span></a></li>
						<li><a href="${CTX}/event/exhibition/exhibitionList.do"><span>기획전</span></a></li>
						<li><a href="${CTX}/style/sampleList.do"><span>정품신청</span></a></li>
						<c:if test="${timeSaleCnt > 0}">
						<li><a href="${CTX}/event/timeSale/timeSaleList.do"><span>타임세일</span></a></li>
						</c:if>
						<!-- <li><a href="${CTX}/event/couponBook.do"><span>쿠폰 북</span></a></li> -->
					</ul>
				</li>
				<li class="item">
					<a class="gnb-link" href="${CTX}/brand/brandAboutMandom.do">BRAND</a>
					<ul class="gnb-sub">
						<li><a href="${CTX}/brand/brandAboutMandom.do"><span>About mandom</span></a></li>
						<li><a href="${CTX}/brand/brandGatsby.do"><span>브랜드 소개</span></a></li>
						<li><a href="${CTX}/brand/brandAdList.do?brandIdx=${g_brand_idx}"><span>동영상</span></a></li>
						<li><a href="${CTX}/brand/brandMagazineList.do?brandIdx=${g_brand_idx}"><span>매거진</span></a></li>
					</ul>
				</li>
				<li class="item">
					<a class="gnb-link" href="${CTX}/style/tipList.do">TIPS</a>
					<ul class="gnb-sub">
						<li><a href="${CTX}/style/tipList.do"><span>스타일링팁</span></a></li>
						<li><a href="${CTX}/style/howtouseList.do"><span>상품사용법</span></a></li>
					</ul>
				</li>
				 --%>
				<li>
					<a class="gnb-link" href="${CTX}/cscenter/csMain.do">고객센터</a>
					<ul class="gnb-sub">
						<!-- <li><a href="${CTX}/cscenter/csInfo/benefitInfo.do"><span>혜택안내</span></a></li> -->
						<li><a href="${CTX}/cscenter/notice/noticeList.do"><span>공지사항</span></a></li>
						<li><a href="${CTX}/cscenter/faq/faqList.do"><span>FAQ</span></a></li>
						<li><a href="${CTX}/mypage/inquiry/inquiryWrite.do"><span>1:1문의</span></a></li>
					</ul>
				</li>
			</ul>
		</div>
	</aside>
	<!-- e: aside -->
	
	<%-- <ul class="aside-brand">
		<li><a href="${CTX}/gatsby/main.do"><img src="${CTX}/images/${DEVICE}/common/aside_brand_gatsby.png" alt="gatsby" /></a></li>
		<li><a href="${CTX}/bifesta/main.do"><img src="${CTX}/images/${DEVICE}/common/aside_brand_bifesta.png" alt="bifesta" /></a></li>
		<li><a href="${CTX}/lucidol/main.do"><img src="${CTX}/images/${DEVICE}/common/aside_brand_lucidol.png" alt="lucidol" /></a></li>
		<li><a href="${CTX }/product/bestProductList.do?gnbBrand=default" class="btn-best">BEST</a></li>
	</ul>
	
     <aside id="gnb" class="aside">
         <div class="aside-header">
			<a href="${CTX}/main.do" class="btn_home"><span class="hide">첫화면</span></a>
			<p class="logo">
				<a href="${CTX}/main.do"><img src="${CTX}/images/${DEVICE}/logo/logo_mandom.png" alt="mandom" /></a>
			</p>
			<button type="button" id="btnMenuClose" class="btn_close"><span class="hide">메뉴닫기</span></button>
		</div>
		
		<ul class="aside-brand">
			<li><a href="${CTX}/gatsby/main.do"><img src="${CTX}/images/${DEVICE}/common/aside_brand_gatsby.png" alt="gatsby" /></a></li>
			<li><a href="${CTX}/bifesta/main.do"><img src="${CTX}/images/${DEVICE}/common/aside_brand_bifesta.png" alt="bifesta" /></a></li>
			<li><a href="${CTX}/lucidol/main.do"><img src="${CTX}/images/${DEVICE}/common/aside_brand_lucidol.png" alt="lucidol" /></a></li>
			<li><a href="${CTX }/product/bestProductList.do?gnbBrand=default" class="btn-best">BEST</a></li>
		</ul>
		
		<div class="aside-member">
			<c:choose>
				<c:when test="${IS_LOGIN eq true}"> 
					<div class="member-login">
						<p class="txt"><strong class="name">${USERINFO.memberNm}</strong> 님 로그인하셨습니다.</p>
						<div class="btns">
							<a href="${CTX}/logout.do" class="btn sm white"><span class="txt">로그아웃</span></a>
						</div>
					</div>	
				</c:when>
				<c:otherwise>
					<div class="member-login">
						<p class="txt"><strong>로그인</strong> 하시면 다양한 혜택을 받으실 수 있습니다.</p>
						<div class="btns">
							<a href="${CTX }/login/loginPage.do" class="btn sm black"><span class="txt">로그인</span></a>
							<a href="${CTX }/member/joinStep01.do" class="btn sm white"><span class="txt">회원가입</span></a>
						</div>
					</div>	
				</c:otherwise>
			</c:choose>
			
			<div class="member-summary">
				<ul>
					<li>
						<a href="${CTX}/mypage/order/main.do?refererYn=Y">
							<p>마이페이지</p>
							<span class="ico_mp"></span>
						</a>
					</li>
					<li>
						<a href="${CTX}/mypage/order/myOrderList.do">
							<p>주문/배송<br/>조회</p>
							<span class="cnt">
								<strong>
									<c:choose>
										<c:when test="${orderCntM > 0}"><fmt:formatNumber value="${orderCntM}" groupingUsed="true"/></c:when>
										<c:otherwise>0</c:otherwise>
									</c:choose>
								</strong> 건
							</span>
						</a>
					</li>
					<li>
						<a href="${CTX}/mypage/point/couponList.do">
							<p>쿠폰</p>
							<span class="cnt">
								<strong>
									<c:choose>
										<c:when test="${mypageCouponCnt > 0}"><fmt:formatNumber value="${mypageCouponCnt}" groupingUsed="true"/></c:when>
										<c:otherwise>0</c:otherwise>
									</c:choose>
								</strong> 장
							</span>
						</a>
					</li>
					<li>
						<a href="${CTX}/cart.do">
							<p>장바구니</p>
							<span class="cnt"><strong id="header_cart_cnt"><fmt:formatNumber value="${fn:length(headerCartList)}" groupingUsed="true"/></strong> 개</span>
						</a>
					</li>
				</ul>
			</div>
		</div>
		
		<div class="aside-category">
			<p>CATEGORY</p>
			<ul>
				<li>
					<a href="${CTX}/product/productList.do?schCateIdx=8&sch2depthCateIdx=2&sch3depthCateIdx=0" class="cate-lnk">
						<p class="image"><img src="${CTX}/images/${DEVICE}/menu/img_cate_1.jpg" alt="" /></p>
						<span class="txt">왁스</span>
					</a>
				</li>
				<li>
					<a href="${CTX}/product/productList.do?schCateIdx=17&sch2depthCateIdx=2" class="cate-lnk">
						<p class="image"><img src="${CTX}/images/${DEVICE}/menu/img_cate_2.jpg" alt="" /></p>
						<span class="txt">포마드</span>
					</a>
				</li>
				<li>
					<a href="${CTX}/product/productList.do?schCateIdx=9&sch2depthCateIdx=2" class="cate-lnk">
						<p class="image"><img src="${CTX}/images/${DEVICE}/menu/img_cate_3.jpg" alt="" /></p>
						<span class="txt">스프레이</span>
					</a>
				</li>
				<li>
					<a href="${CTX}/product/productList.do?schCateIdx=11&sch2depthCateIdx=2" class="cate-lnk">
						<p class="image"><img src="${CTX}/images/${DEVICE}/menu/img_cate_4.jpg" alt="" /></p>
						<span class="txt">젤</span>
					</a>
				</li>
				<li>
					<a href="${CTX}/product/productList.do?schCateIdx=10&sch2depthCateIdx=2" class="cate-lnk">
						<p class="image"><img src="${CTX}/images/${DEVICE}/menu/img_cate_5.jpg" alt="" /></p>
						<span class="txt">폼</span>
					</a>
				</li>
				<li>
					<a href="${CTX}/product/productList.do?schCateIdx=24&sch2depthCateIdx=2" class="cate-lnk">
						<p class="image"><img src="${CTX}/images/${DEVICE}/menu/img_cate_21.jpg" alt="" /></p>
						<span class="txt">왁스샴푸</span>
					</a>
				</li>
				<li>
					<a href="${CTX}/product/productList.do?schCateIdx=6&sch2depthCateIdx=1" class="cate-lnk">
						<p class="image"><img src="${CTX}/images/${DEVICE}/menu/img_cate_6.jpg" alt="" /></p>
						<span class="txt">페이퍼</span>
					</a>
				</li>
				<li>
					<a href="${CTX}/product/productList.do?schCateIdx=7&sch2depthCateIdx=1" class="cate-lnk">
						<p class="image"><img src="${CTX}/images/${DEVICE}/menu/img_cate_7.jpg" alt="" /></p>
						<span class="txt">쉐이빙</span>
					</a>
				</li>
				<li>
					<a href="${CTX}/product/productList.do?schCateIdx=15&sch2depthCateIdx=3" class="cate-lnk">
						<p class="image"><img src="${CTX}/images/${DEVICE}/menu/img_cate_8.jpg" alt="" /></p>
						<span class="txt">데오도란트</span>
					</a>
				</li>
				<li>
					<a href="${CTX}/product/productList.do?schCateIdx=28&sch2depthCateIdx=26" class="cate-lnk">
						<p class="image"><img src="${CTX}/images/${DEVICE}/menu/img_cate_9.jpg" alt="" /></p>
						<span class="txt">클렌징 워터</span>
					</a>
				</li>
				<li>
					<a href="${CTX}/product/productList.do?schCateIdx=29&sch2depthCateIdx=26" class="cate-lnk">
						<p class="image"><img src="${CTX}/images/${DEVICE}/menu/img_cate_10.jpg" alt="" /></p>
						<span class="txt">클렌징 시트</span>
					</a>
				</li>
				<li>
					<a href="${CTX}/product/productList.do?schCateIdx=30&sch2depthCateIdx=26" class="cate-lnk">
						<p class="image"><img src="${CTX}/images/${DEVICE}/menu/img_cate_11.jpg" alt="" /></p>
						<span class="txt">클렌징 폼</span>
					</a>
				</li>
				<li>
					<a href="${CTX}/product/productList.do?schCateIdx=31&sch2depthCateIdx=26" class="cate-lnk">
						<p class="image"><img src="${CTX}/images/${DEVICE}/menu/img_cate_12.jpg" alt="" /></p>
						<span class="txt">아이리무버</span>
					</a>
				</li>
				<li>
					<a href="${CTX}/product/productList.do?schCateIdx=34&sch2depthCateIdx=33" class="cate-lnk">
						<p class="image"><img src="${CTX}/images/${DEVICE}/menu/img_cate_13.jpg" alt="" /></p>
						<span class="txt">헤어 오일</span>
					</a>
				</li>
				<li>
					<a href="${CTX}/product/productList.do?schCateIdx=35&sch2depthCateIdx=33" class="cate-lnk">
						<p class="image"><img src="${CTX}/images/${DEVICE}/menu/img_cate_14.jpg" alt="" /></p>
						<span class="txt">트리트먼트</span>
					</a>
				</li>
				<li>
					<a href="${CTX}/product/productList.do?schCateIdx=23&sch2depthCateIdx=3" class="cate-lnk">
						<p class="image"><img src="${CTX}/images/${DEVICE}/menu/img_cate_15.jpg" alt="" /></p>
						<span class="txt">바디미스트</span>
					</a>
				</li>
				<li>
					<a href="${CTX}/product/productView.do?goodsCd=4902806167689&choiceCateIdx=24" class="cate-lnk">
						<p class="image"><img src="${CTX}/images/${DEVICE}/menu/img_cate_16.jpg" alt="" /></p>
						<span class="txt">셀프헤어키트</span>
					</a>
				</li>
				<li>
					<a href="${CTX}/product/productList.do?schCateIdx=16&sch2depthCateIdx=3&gnbBrand=default" class="cate-lnk">
						<p class="image"><img src="${CTX}/images/${DEVICE}/menu/img_cate_17.jpg" alt="" /></p>
						<span class="txt">눈썹정리</span>
					</a>
				</li>
				<li>
					<a href="${CTX}/product/productList.do?schCateIdx=16&sch2depthCateIdx=3&gnbBrand=default" class="cate-lnk">
						<p class="image"><img src="${CTX}/images/${DEVICE}/menu/img_cate_18.jpg" alt="" /></p>
						<span class="txt">다리털정리</span>
					</a>
				</li>
				<li>
					<a href="${CTX}/product/productList.do?schCateFlag=ALL&gnbBrand=default&setFlag=Y" class="cate-lnk">
						<p class="image"><img src="${CTX}/images/${DEVICE}/menu/img_cate_19.jpg" alt="" /></p>
						<span class="txt">세트</span>
					</a>
				</li>
				<li>
					<a href="${CTX }/product/bestProductList.do?gnbBrand=default" class="cate-lnk">
						<p class="image"><img src="${CTX}/images/${DEVICE}/menu/img_cate_20.jpg" alt="" /></p>
						<span class="txt">베스트</span>
					</a>
				</li>
			</ul>
		</div>
		
		<div class="aside-pane" style="display:block;">
			<ul class="gnb-list">
				<li class="item">
					<a class="gnb-link" href="${CTX}/event/event/eventList.do">EVENT</a>
					<ul class="gnb-sub">
						<li><a href="${CTX}/event/event/eventList.do"><span>이벤트</span></a></li>
						<li><a href="${CTX}/event/exhibition/exhibitionList.do"><span>기획전</span></a></li>
						<li><a href="${CTX}/style/sampleList.do"><span>정품 신청</span></a></li>
						<c:if test="${timeSaleCnt > 0}">
							<li><a href="${CTX}/event/timeSale/timeSaleList.do"><span>타임세일</span></a></li>
						</c:if>
					</ul>
				</li>
				<li class="item">
					<a class="gnb-link" href="${CTX}/brand/brandAboutMandom.do">BRAND</a>
					<ul class="gnb-sub">
						<li><a href="${CTX}/brand/brandAboutMandom.do"><span>ABOUT mandom</span></a></li>
					 	<li><a href="${CTX}/brand/brandAdList.do"><span>광고AD</span></a></li>
					 	<li><a href="${CTX}/brand/brandMagazineList.do"><span>매거진</span></a></li>						
					</ul>
				</li>
				<li class="item">
					<a class="gnb-link" href="${CTX}/style/tipList.do">TIPS</a>
					<ul class="gnb-sub">
						<li><a href="${CTX}/style/tipList.do"><span>스타일링 팁</span></a></li>
					 	<li><a href="${CTX}/style/howtouseList.do"><span>상품 사용법</span></a></li>
					</ul>
				</li>
			</ul>
		</div>
		
		<div class="aside-customer">
			<p class="center">
				<a href="tel:02-544-1191" class="tel">02-544-1191</a>
				<a href="${CTX}/cscenter/csMain.do" class="cscenter">고객센터 바로가기 ▶</a>
			</p>
			<p class="time">(운영 09:00-17:30 | 점심 12:00-13:00 | 토 · 일 · 공휴일 휴무)</p>
		</div>
		
     </aside> --%>
     
<%-- header, wing 처리 (비회원은 여기서 ajax 처리) --%>
<c:if test="${empty USERINFO.memberId }"> 
	<script type="text/javascript">
		$(document).ready(function(){
			<%--장바구니 대표 상품 표시(비회원은 여기서 ajax 처리) --%>
			$.ajax({
				url: getContextPath()+"/ajax/cart/getCartListAjax.do",
			 	data: {
			 		"sessionId"	: getSessionId()
			 	},
			 	type: "post",
			 	async: false,
			 	cache: false,
			 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 	error: function(request, status, error){ 
			 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
				 },
				 success: function(data){
					 if(data.result == true){
						 $("#header_cart_cnt").text(comma(data.resultList.length));
						 $("#btnMenu").attr("data-cart-num",comma(data.resultList.length));
					 }else{
						 if(data.msg != ""){
							 alert(data.msg);
						 }
					 }
				 }
			});
		});
	</script>
</c:if>

<decorator:body />