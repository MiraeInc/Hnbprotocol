<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_shopping" />
<meta name="menu_no" content="mypage_030" />
<script>

$(function(){
	//datepicker
	$(".form-datepicker").datepicker();
});

// 페이지 이동
function goPage(page){
	$("#pageNo").val(page);
	
	var frm = document.pointForm;
	frm.action = "${CTX}/mypage/point/pointList.do";
	frm.submit();
}

// 검색
function goSearch(){
	$("#pageNo").val(1);
	
	var frm = document.pointForm;
	frm.action = "${CTX}/mypage/point/pointList.do";
	frm.submit();
}

// 날짜 검색 변경
function dateClick(){
	var dt = $("#schType").val();
	if(dt!=0){
		setDate(dt);
	}else if(dt==0){
		$("#schStartDt").val("");
		$("#schEndDt").val("");
	}
}

</script>
</head>
<body>
<form name="pointForm" id="pointForm" method="post" onsubmit="return false;">
	<input type="hidden" name="pageNo" id="pageNo" value="${schVO.pageNo}"/>
	<div class="content comm-order comm-mypage mypage-point">

		<div class="page-body">
			<div class="point-status">
				<dl class="status-point">
					<dt>보유 포인트</dt>
					<dd><strong><fmt:formatNumber value="${totalPoint}" pattern="#,###"/></strong>P</dd>
				</dl>
				<div class="status-expiration">
					<p class="date">${nextMonthTxt} 소멸 예정 포인트</p> 
					<p class="point"><fmt:formatNumber value="${spPointDeduct.gapPoint}" pattern="#,###"/>P</p>
				</div>
			</div>

            <br>
            <div class="point-list token_cont">
				<ul>
					<li>
						<div class="item-point">
							<div class="token_input">
								<p><span class="tit">신청포인트</span> <input type="text" placeholder="포인트입력"></p>
								<p><span class="tit">HNB 토큰</span> <input type="text" placeholder=""></p>
								<p><span class="tit">지 갑 주 소</span> <input type="text" placeholder="주소입력"></p>
							</div>
							<div class="btn_box btn_center">
								<button class="btn btn_02 btn_b01">확인</button>
							</div>
						</div>
					</li>
				</ul>
            </div>

			<!-- <div class="guidebox">
               	<div class="guide-title">
					<h3 class="tit"><span class="i"><img src="${CTX}/images/${DEVICE}/common/ico_helper_alert.png" alt="" /></span> 포인트 안내</h3>
				</div>
				<div class="guide-list">
					<ul>
						<li>
							<span class="i"><img src="${CTX}/images/${DEVICE}/contents/ico_hand.png" style="width: 50px" alt="" /></span>
							<strong class="tit">적립 안내</strong>
             				<div>
 								<ul class="bu-list">
									<li><span class="bu">-</span> 회원 가입 시 <span class="em"> 2,000P</span> 즉시 지급</li>
									<li><span class="bu">-</span> 바로온 접속 시 결제금액의  <span class="em">0.5%</span> 지급</li>
									<li><span class="bu">-</span> 일반 후기 작성 시 <span class="em">100P</span> 지급</li>
									<li><span class="bu">-</span> 포토 후기 작성 시 <span class="em">500P</span> 지급</li>
									<li><span class="bu">-</span> 상품 구매시 등급별 포인트 적립</li>
									<li>
										<span class="bu">-</span>
										상품 구매시 등급별 포인트 적립<br/>
										<strong>일반회원</strong><br/> : 실 결제금액의 <span class="em">1%</span> 포인트 적립 (배송비 제외)<br/>
										<strong>우수회원</strong><br/> : 실 결제금액의 <span class="em">2%</span> 포인트 적립 (배송비 제외)<br/>
										<strong>VIP 회원</strong><br/> : 실 결제금액의 <span class="em">3% </span> 포인트 적립 (배송비 제외)
									</li>
								</ul>
							</div>
						</li>
						<li>
							<span class="i"><img src="${CTX}/images/${DEVICE}/contents/ico_briefcase.png" style="width: 50px" alt="" /></span>
							<strong class="tit">사용 안내</strong>
							<div>
								<ul class="bu-list">
									<li><span class="bu">-</span> 1P 단위로 주문 결제 시 사용가능 합니다.</li>
									<li><span class="bu">-</span> 적립되신 포인트는 기간 만료 시 자동 소멸됩니다.</li>
									<li><span class="bu">-</span> 주문 취소 시 사용하신 포인트는 자동 환급됩니다.</li>
									<li><span class="bu">-</span> 부정 행위로 적립된 포인트는 고지 없이 회수될 수 있습니다.</li>
								</ul>
							</div>
						</li>
					</ul>
				</div>
			</div> -->
		</div>
	</div>
</form>
</body>
</html>