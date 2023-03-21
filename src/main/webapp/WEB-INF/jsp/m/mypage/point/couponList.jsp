<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_shopping" />
<meta name="menu_no" content="mypage_040" />
<script>

// 페이지 이동
function goPage(page){
	$("#pageNo").val(page);
	
	var frm = document.couponForm;
	frm.action = "${CTX}/mypage/point/couponList.do";
	frm.submit();
}

// 검색
function typeChange(type){
	$("#pageNo").val(1);
	$("#schType").val(type);
	
	var frm = document.couponForm;
	frm.action = "${CTX}/mypage/point/couponList.do";
	frm.submit();
}

</script>
</head>
<body>
<form name="couponForm" id="couponForm" method="get" onsubmit="return false;">
	<input type="hidden" name="pageNo" id="pageNo" value="${schVO.pageNo}"/>
	<input type="hidden" name="schType" id="schType" value="${schVO.schType}"/>
</form>
<!-- [D] content start here! -->
			<div class="content comm-order comm-mypage mypage-coupon">
				<script>
					$(function(){
						$(".dropdown-select").dropdownAction();
					});
				</script>

				<div class="page-body">
<%--
					<div class="coupon-add">
						<div class="row">
							<div class="col col-8">
								<div class="form-control">
									<input type="text" class="input" placeholder="가지고 계신 쿠폰번호를 입력해주세요." />
								</div>
							</div>
							<div class="col col-4">
								<button type="button" class="btn black full"><span class="txt">쿠폰 등록</span></button>
							</div>
						</div>
					</div>
					
							
					<div class="coupon-sort">
						<div class="row">
							<div class="col col-12">
								<div class="form-control">
									<div class="opt_select">
										<select id="sortCoupon">
											<option value="0">사용 가능한 쿠폰(2)</option>
											<option value="1">7일 이내 만료 쿠폰</option>
											<option value="2">사용 만료된 쿠폰</option>
										</select>
									</div>
								</div>
							</div>
						</div>
					</div>
 --%>
					<div class="page-filter">
						<ul>
							<li>
								<a href="#" <c:if test="${schVO.schType eq '1'.toString()}"> class="active" </c:if><c:if test="${schVO.schType ne '1'.toString()}"> onclick="typeChange('1')" </c:if>>사용 가능한 쿠폰(${type1Count})</a>
							</li>
							<li>
								<a href="#" <c:if test="${schVO.schType eq '2'.toString()}"> class="active" </c:if><c:if test="${schVO.schType ne '2'.toString()}"> onclick="typeChange('2')" </c:if>>7일 이내 만료 쿠폰(${type2Count})</a>
							</li>
							<li>
								<a href="#" <c:if test="${schVO.schType eq '3'.toString()}"> class="active" </c:if><c:if test="${schVO.schType ne '3'.toString()}"> onclick="typeChange('3')" </c:if>>사용 만료된 쿠폰(${type3Count})</a>
							</li>
						</ul>
					</div>

					<div class="page-body">

						<div class="order-form first">
							<c:choose>
							<c:when test="${fn:length(couponList) > 0}">
							<div class="form-group">
								<div class="form-body">
									<div class="coupon-list">
									
					
										<c:forEach var="list" items="${couponList}" varStatus="idx">
										<div class="item">
											<div class="coupon-thumb">
											<c:choose>
												<c:when test="${list.gubun eq 'S' }">
													<div class="coupon-box">
														<div class="coupon-image type-mandom">
															<span class="txt">무료배송</span>
														</div>
													</div>
												</c:when>
												<c:otherwise>
													<c:set var="brandType" value="type-mandom"/>
													<c:if test="${list.gubun eq 'G' and list.targetGoods eq 'B'}">	<%--상품 쿠폰이고 브랜드선택일때 --%>
														<c:choose>
															<c:when test="${list.targetBrandIdx eq 1}"><c:set var="brandType" value="type-gatsby"/></c:when>		
															<c:when test="${list.targetBrandIdx eq 3}"><c:set var="brandType" value="type-bifesta"/></c:when>
															<c:when test="${list.targetBrandIdx eq 4}"><c:set var="brandType" value="type-lucidol"/></c:when>
														</c:choose>
													</c:if>															
													<div class="coupon-box">
														<div class="coupon-image ${brandType}">
															<c:choose>
																<c:when test="${list.discountType eq 'R'}">	<%--정률할인 --%>
																	<fmt:formatNumber value="${list.discount}" groupingUsed="true"/><span>%</span>
																</c:when>
																<c:otherwise>											<%--정액할인 --%>
																	<span class="txt"><fmt:formatNumber value="${list.discount}" groupingUsed="true"/><span>원</span></span>
																</c:otherwise>										
															</c:choose>
														</div>
													</div>
												</c:otherwise>
												</c:choose>
												
											</div>
											<a href="#couponDetail${idx.index}" class="btn ico-toggle btn-coupon" data-toggle="collapse"><span class="txt">${list.couponNm}</span></a>
											<dl id="couponDetail${idx.index}" class="coupon-detail">
											<dt>쿠폰종류</dt>
											<dd>
									<c:choose>
										<c:when test="${list.gubun eq 'G'}">상품쿠폰</c:when>
										<c:when test="${list.gubun eq 'C'}">장바구니</c:when>
										<c:when test="${list.gubun eq 'S'}">무료배송</c:when>
									</c:choose>
											</dd>
												<dt>혜택</dt>
												<c:choose>
												<c:when test="${list.gubun eq 'S' }">
													<dd><strong class="em">무료배송</strong></dd>
												</c:when>
												<c:otherwise>
													<dd><strong class="em"><fmt:formatNumber value="${list.discount}" groupingUsed="true"/>
															<c:if test="${list.discountType eq 'R' }"><span>%</span></c:if>
															<c:if test="${list.discountType eq 'A' }"><span>원</span></c:if>
															</strong>
													</dd>
												</c:otherwise>
												</c:choose>
												
												<c:choose>
												<c:when test="${ list.useYn eq 'Y'}">
													<dt>사용일자</dt>
													<dd><strong class="em">
														<fmt:parseDate  value="${list.useDt}"  var="dateFmt1"  pattern="yyyy-MM-dd"/>
														<fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/> 사용
														</strong>
													</dd>
												</c:when>
												<c:otherwise>
													<dt>만료일자</dt>
													<dd>
													<c:choose>
														<c:when test="${list.useDateLimitYn eq 'N'}">기간제한없음</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${list.diffDay > 0}">
																	<fmt:parseDate  value="${list.realUseEndDt}"  var="dateFmt"  pattern="yyyy-MM-dd"/>
																	<fmt:formatDate value="${dateFmt}" pattern="yyyy.MM.dd"/>	만료 
																</c:when>
																<c:when test="${list.diffDay == 0}">
																	오늘 만료 
																</c:when>
																<c:otherwise>
																	<fmt:parseDate  value="${list.realUseEndDt}"  var="dateFmt"  pattern="yyyy-MM-dd"/>
																	<fmt:formatDate value="${dateFmt}" pattern="yyyy.MM.dd"/>	까지 
																	<c:if test="${list.diffDay > -10 }" >
																		<strong class="color-1">(${list.diffDay*-1}일뒤 만료)</strong>
																	</c:if>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>
													</dd>
												</c:otherwise>
											</c:choose>
											
											
												
												<dt>최소 결제금액</dt>
												<dd>
												<c:choose>
												<c:when test="${list.gubun eq 'S'}">	<%--무료배송 쿠폰 --%>
														15,000원
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${list.orderAmtLimitYn eq 'N'}">제한없음</c:when>
														<c:otherwise>
															<fmt:formatNumber value="${list.minOrderAmt}" groupingUsed="true"/>원
														</c:otherwise>
													</c:choose>
													</c:otherwise>
												</c:choose>
												</dd>
												
											<c:if test="${list.maxDiscount ne null}">
												<dt>최대 할인금액</dt>
												<dd>
												<c:choose>
													<c:when test="${list.gubun eq 'S'}">	<%--무료배송 쿠폰 --%>
															2,500원
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${list.maxDiscount eq '0.00'}">제한없음</c:when>
															<c:otherwise>
																<fmt:formatNumber value="${list.maxDiscount}" groupingUsed="true"/>원
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
												</dd>
											</c:if>
											</dl>
										</div>
										</c:forEach>
										
									</div>
								</div>
							</div>
							<div class="pagin-nav">
								<c:out value="${page.pageStr}" escapeXml="false"/>
							</div>
							</c:when>
							<c:otherwise>
								<!-- 쿠폰 내역이 없을경우 -->
								<div class="form-group">
									<div class="no-contents">
										<p>쿠폰이 없습니다.</p>
									</div>
								</div>
								<!-- //쿠폰 내역이 없을경우 -->
							</c:otherwise>
							</c:choose>
	
						</div>
	
						<div class="guidebox">
						<div class="guide-title">
							<h3 class="tit"><span class="i"><img src="${CTX}/images/${DEVICE}/common/ico_helper_alert.png" alt="" /></span> 쿠폰 별 사용안내</h3>
						</div>
						<div class="guide-list">
							<ul>
								<li>
									<span class="i"><img src="${CTX}/images/${DEVICE}/contents/ico_prod.png" style="width: 48px" alt="" /></span>
									<strong class="tit">상품 쿠폰 </strong>
									<div>
										상품에 적용되는 할인 쿠폰으로 주문 시 자동 적용됩니다. 1상품에 1쿠폰만 적용되며, 주문 시 변경할 수 있습니다.<br/>상품쿠폰은 장바구니 쿠폰/프로모션 코드/배송비 쿠폰과 함께 중복 사용 가능합니다.
									</div>
								</li>
								<li>
									<span class="i"><img src="${CTX}/images/${DEVICE}/contents/ico_basket.png" style="width: 44px" alt="" /></span>
									<strong class="tit">장바구니 쿠폰</strong>
									<div>
										실 결제 금액에 대해 할인이 적용되며, 장바구니 쿠폰/프로모션 코드 간의 중복 사용은 불가능합니다.  
									</div>
								</li>
								<li>
									<span class="i"><img src="${CTX}/images/${DEVICE}/contents/ico_promo.png" style="width: 50px" alt="" /></span>
									<strong class="tit">프로모션 코드</strong>
									<div>
										프로모션 코드는 주문 시 입력하시면 실 결제 금액에 대한 할인이 적용되는 장바구니 쿠폰 형태의 할인 수단입니다.
									</div>
								</li>
								<li>
									<span class="i"><img src="${CTX}/images/${DEVICE}/contents/ico_car.png" style="width: 50px" alt="" /></span>
									<strong class="tit">배송비 쿠폰</strong>
									<div>
										배송비에 적용되는 할인 쿠폰입니다. 회원 등급에 따라 차등 지급되며 (<a href="${CTX}/cscenter/csInfo/benefitInfo.do" class="em">회원 혜택 안내 참고</a>) 매달 발행됩니다.
									</div>
								</li>
								<li>
									<span class="i"><img src="${CTX}/images/${DEVICE}/contents/ico_cake.png" style="width: 48px" alt="" /></span>
									<strong class="tit">생일 쿠폰</strong>
									<div>
										회원 가입 시 생년월일 등록하시면, 생일 1주일 전에 발급되는 할인 쿠폰 입니다.<br/>생년월일은 <a href="${CTX}/mypage/member/memberInfo.do" class="em">마이페이지 &gt; 개인정보 수정</a>에서 등록하실 수 있습니다.
									</div>
								</li>
								<li>
									<span class="i"><img src="${CTX}/images/${DEVICE}/contents/ico_mobile.png" style="width: 29px" alt="" /></span>
									<strong class="tit">모바일 쿠폰</strong>
									<div>
										모바일에서만 사용이 가능한 할인 쿠폰입니다.
									</div>
								</li>
							</ul>
						</div>
						
						<div class="guide-cont">
							<ul class="bu-list">
								<li><span class="bu">-</span> 일부 카테고리 및 상품은 쿠폰 적용이 제한될 수 있습니다.</li>
								<li><span class="bu">-</span> 주문 취소 시 사용한 쿠폰은 유효기간이 만료된 경우 재발급이 되지 않을 수 있습니다.<br/>
									판매자 귀책사유로 취소 / 반품 처리되었을 경우에는 재발급됩니다.
								</li>
							</ul>
						</div>

					</div>

					</div>
				</div>
			</div>
			<!-- [D] //content start end! -->


</body>
</html>