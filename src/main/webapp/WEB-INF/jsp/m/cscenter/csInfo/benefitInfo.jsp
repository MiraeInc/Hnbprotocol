<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main"/>	

<script>
	$(function(){
		$('#tabmenu').tabControl();
	});
</script>

</head>
<body>
    <div class="content comm-customer customer-benefit">
    	
    	<page:applyDecorator  name="mobile.csmenu" encoding="UTF-8"/>

		<div class="page-title hide">
			<h2>혜택안내</h2>
		</div>
		
		<div class="page-body">
			<div class="member-benefit">
				<h2>혜택안내</h2>
				<img src="${CTX}/images/${DEVICE}/contents/img_member_benefit.png" alt="">
				<ul class="hide">
					<li>회원가입 포인트 2,000P 지급</li>
					<li>무료 배송 쿠폰 매월 지급</li>
					<li>할인 쿠폰 매월 등급별 지급</li>
					<li>회원등급에 따라 구매포인트 적립</li>
				</ul>
			</div>

			<div class="grade-detail">
				<div id="tabmenu" class="tab-menu-line tab-3">
					<ul>
						<li class="active"><a href="#tabGeneral" class="tab-link">일반회원</a></li>
						<li><a href="#tabSilver" class="tab-link">우수회원</a></li>
						<li><a href="#tabVip" class="tab-link">VIP회원</a></li>
					</ul>
				</div>
				<div class="grade-benefit">
					<table class="benefit-table" id="tabGeneral">
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

					<table class="benefit-table" id="tabSilver" style="display:none">
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

					<table class="benefit-table" id="tabVip" style="display:none">
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

			<!--
			<div class="benefit-gifts">
				<h2>구매회원 사은품 혜택</h2>
				<ul>
                    <li>
						<img src="${CTX}/images/${DEVICE}/customer/m_img_gift_202102_1.png" alt=""/>
					</li>
				</ul>
				<div class="giftinfo">
					※ 단, 비회원 & <img src="${CTX}/images/${DEVICE}/contents/ico_npay.gif" style="width: 35px; height: 14px;" alt="네이버페이"> 구매 시에는 사은품이 증정되지 않습니다.<br/>
					※ 사은품 재고 소진 시, 다른 사은품으로 대체되어 발송될 수 있습니다.
				</div>
			</div>
			-->
			
			<%-- 결제혜택 배너 --%>
			<c:if test="${!empty bannerInfo}">
				<c:if test="${bannerInfo.deviceGubun eq 'M' or bannerInfo.deviceGubun eq 'A'}">
					<div class="event-list">
						<h2>결제혜택</h2>
						<div style="text-align: center">
							<img src="${IMGPATH}/banner/${bannerInfo.bannerIdx}/${bannerInfo.moBannerImg}" alt="${bannerInfo.bannerNm}" />
						</div>
					</div>
				</c:if>
			</c:if>
			<%-- //결제혜택 배너 --%>
		</div>
	</div>
</body>
</html>