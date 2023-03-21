<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_main" />
<meta name="menu_no" content="mypage_000" />

<script type="text/javascript">
	$(function(){
		//탭
		$('#tabmenu').tabControl();
	})
</script>

</head>
<body>
      <div class="content comm-mypage mypage-membership">

				<!-- s: 히스토리 -->
				<div class="breadcrumb">
					<ul>
						<li>
							<a href="${CTX}/main.do"><span>Home</span></a>
						</li>
						<li class="current">
							<a href="${CTX}/mypage/order/main.do"><span>마이페이지</span></a>
						</li>
					</ul>
				</div>
				<!-- e: 히스토리 -->

                <div class="page-body">
					<c:choose>
						<c:when test="${USERINFO.levelIdx eq 1}">
							<c:set var="nowGrade" value="grade-g"/>
							<c:set var="nextGrade" value="우수회원"/>
						</c:when>
						<c:when test="${USERINFO.levelIdx eq 2}">
							<c:set var="nowGrade" value="grade-s"/>
							<c:set var="nextGrade" value="VIP회원"/>
						</c:when>
						<c:when test="${USERINFO.levelIdx eq 3}">
							<c:set var="nowGrade" value="grade-v"/>
							<c:set var="nextGrade" value="VIP회원"/>
						</c:when>
					</c:choose>
					<!-- 회원정보 -->
					<div class="member-summry">
						<span class="grade ${nowGrade}"><span class="hide">${USERINFO.gradeNm}</span></span>
						<div class="summary-info">
							<p>${currentMonth}월 회원 등급은 <strong class="em">${USERINFO.gradeNm}</strong>입니다.</p>
							<ul>
								<li>
									<strong>등급산정기간</strong>
									<span class="em">${startYear}년 ${startMonth}월~${endYear}년 ${endMonth}월</span>
								</li>
								<li>
									<strong>해당 기간 누적 실결제 금액</strong>
									<span class="em"><fmt:formatNumber value="${sumOrderPrice}" groupingUsed="true"/></span>
								</li>
								<li>(구매확정 기준/ 취소, 환불 주문건의 금액 제외)</li>
							</ul>
						</div>
						<a href="${CTX}/mypage/order/myOrderList.do" class="btn ico-chev btn-history"><span class="txt">나의 주문내역 보기</span></a>
					</div>
					<!-- //회원정보 -->
					
					<div class="member-next">
						<div class="message">
							다음달<c:if test="${USERINFO.levelIdx eq 3}">에도</c:if> <span class="em">${nextGrade}</span>이 되시려면<br/> 이달 말까지 <strong><fmt:formatNumber value="${nextLevelPrice}" groupingUsed="true"/></strong>원을 더 결제하시면 됩니다.
						</div> 
					</div>

					<div class="helper-box">
						<div class="helper-box-inner">
							<strong class="tit" data-icon="star">등급 별 혜택 안내</strong>
							<ul>
								<li>등급에 따라 매월 제공되는 혜택을 확인하세요.</li>
								<li>회원등급은 최근 3개월간의 실결제 금액을 기준으로 매월 1일 자동으로 등급이 변경됩니다. (구매확정 기준 / 취소, 환불 주문건의 금액 제외)</li>
								<li>쿠폰은 매월 1일 등급에 맞춰 자동으로 발급되며, 해당 월의 마지막 날까지만 사용이 가능합니다.</li>
								<li>회원 등급에 맞춰 발급된 쿠폰은 <a href="${CTX}/mypage/point/couponList.do" class="em">마이페이지>쿠폰</a> 페이지에서 확인 가능합니다.</li>
								<li>생일 쿠폰은 회원 가입 시 생년월일을 등록하시면 생일 1주일 전에 발급되는 할인 쿠폰으로, 생년월일은 <a href="${CTX}/mypage/member/memberInfo.do" class="em">마이페이지>개인정보 수정</a> 페이지에서 등록하실 수 있습니다. 
									<br />단, SNS 간편가입을 하셨다면 생년월일 입력/수정이 불가능합니다.</li>
							</ul>
						</div>
					</div>
					
					<c:choose>
					    <c:when test="${USERINFO.levelIdx eq 1}">
					        <c:set var="gradeG" value="display:table"/> 
					        <c:set var="gradeS" value="display:none"/> 
					        <c:set var="gradeV" value="display:none"/> 
					    </c:when>
					    <c:when test="${USERINFO.levelIdx eq 2}">
					    	 <c:set var="gradeG" value="display:none"/> 
					        <c:set var="gradeS" value="display:table"/> 
					        <c:set var="gradeV" value="display:none"/> 
					    </c:when>
					    <c:when test="${USERINFO.levelIdx eq 3}">
					    	 <c:set var="gradeG" value="display:none"/> 
					        <c:set var="gradeS" value="display:none"/> 
					        <c:set var="gradeV" value="display:table"/> 
					    </c:when>
					</c:choose>

					<div class="grade-detail">
						<div id="tabmenu" class="tab-menu-line tab-3">
							<ul>
								<li <c:if test="${USERINFO.levelIdx eq 1}">class="active"</c:if>><a href="#tabGeneral" class="tab-link">일반회원</a></li>
								<li <c:if test="${USERINFO.levelIdx eq 2}">class="active"</c:if>><a href="#tabSilver" class="tab-link">우수회원</a></li>
								<li <c:if test="${USERINFO.levelIdx eq 3}">class="active"</c:if>><a href="#tabVip" class="tab-link">VIP회원</a></li>
							</ul>
						</div>
						<div class="grade-benefit">
							<table class="benefit-table" id="tabGeneral" style="${gradeG}">
								<caption class="hide">회원 등급별 혜택</caption>
								<colgroup>
									<col style="width: 27%" />
									<col />
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">등급</th>
										<td>
											<div class="grade grade-g"></div>
											<strong>일반회원</strong>
										</td>
									</tr>
									<tr>
										<th scope="row">기준</th>
										<td>회원가입</td>
									</tr>
									<tr>
										<th scope="row">구매시<br/>포인트적립</th>
										<td>
											실결제 금액의 1%<br/>
											(배송비 제외)
										</td>
									</tr>
									<tr>
										<th scope="row">할인 쿠폰</th>
										<td>
											<ul class="bu-list">
												<li><span class="bu">·</span> 무료 배송 쿠폰 1장<br/>(실결제 2만원 이상 시 사용 가능)</li>
												<li><span class="bu">·</span> 10% 생일 쿠폰 1장<br/>(면역공방 회원가입 시 생년월일 입력한 회원. <br />SNS 간편가입회원은 제외)</li>
											</ul>
										</td>
									</tr>
								</tbody>
							</table>
		
							<table class="benefit-table" id="tabSilver"  style="${gradeS}">
								<caption class="hide">회원 등급별 혜택</caption>
								<colgroup>
									<col style="width: 27%" />
									<col />
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">등급</th>
										<td>
											<div class="grade grade-s"></div>
											<strong>우수회원</strong>
										</td>
									</tr>
									<tr>
										<th scope="row">기준</th>
										<td>최근 3개월간<br/>실결제금액 3만원</td>
									</tr>
									<tr>
										<th scope="row">구매시<br/>포인트적립</th>
										<td>
											실결제 금액의 2%<br/>
											(배송비 제외)														
										</td>
									</tr>
									<tr class="row-cpn">
										<th scope="row">할인 쿠폰</th>
										<td>
											<ul class="bu-list">
												<li><span class="bu">·</span> 무료 배송 쿠폰 1장 <br/>(실결제 2만원 이상 시 사용 가능)</li>
												<li><span class="bu">·</span> 5% 상품 쿠폰 1장</li>
												<li><span class="bu">·</span> 5% 장바구니 쿠폰 1장</li>
												<li><span class="bu">·</span> 10% 생일 쿠폰 1장<br/>(면역공방 회원가입 시 생년월일 입력한 회원. <br />SNS 간편가입회원은 제외)</li>
											</ul>
										</td>
									</tr>
								</tbody>
							</table>
		
							<table class="benefit-table" id="tabVip"   style="${gradeV}">
								<caption class="hide">회원 등급별 혜택</caption>
								<colgroup>
									<col style="width: 27%" />
									<col />
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">등급</th>
										<td>
											<div class="grade grade-v"></div>
											<strong>VIP회원</strong>
										</td>
									</tr>
									<tr>
										<th scope="row">기준</th>
										<td>최근 3개월간<br/>실결제금액 5만원</td>
									</tr>
									<tr>
										<th scope="row">구매시<br/>포인트적립</th>
										<td>
											실결제 금액의 3%<br/>
											(배송비 제외)
										</td>
									</tr>
									<tr class="row-cpn">
										<th scope="row">할인 쿠폰</th>
										<td>
											<ul class="bu-list">
												<li><span class="bu">·</span> 무료 배송 쿠폰 1장 <br/>(실결제 2만원 이상 시 사용 가능)</li>
												<li><span class="bu">·</span> 10% 상품 쿠폰 1장</li>
												<li><span class="bu">·</span> 10% 장바구니 쿠폰 1장</li>
												<li><span class="bu">·</span> 10% 생일 쿠폰 1장<br/>(면역공방 회원가입 시 생년월일 입력한 회원. <br />SNS 간편가입회원은 제외)</li>
											</ul>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
      </div>
</body>
</html>