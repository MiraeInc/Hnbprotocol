<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main"/>	
<script>

	// 바로구매
	function buyNow(goodsIdx){
		var arrOrder = new Array();
		var goodsObj = new Object();
		goodsObj.goodsIdx = Number(goodsIdx);
		goodsObj.goodsCnt = 1;
		arrOrder.push(goodsObj);
		orderNow(arrOrder);
	}

</script>
</head>
<body>

			<!-- # 기획전 스타일정의 -->
			<style type="text/css">
				.evt-event-view {padding: 0;}
				.event-view {padding: 0;}
				.event-section {position: relative;}
				.event-section img {max-width: 100%;}
				.event-section--bifesta a {position: absolute; left: 18.1%; top: 48.9%; width: 63.9%; height: 6.2%;}
				.event-section--gatsby a {position: absolute; left: 2%; top: 48.9%; width: 96%; height: 6.2%;}
			</style>

			<div class="event-section">
				<div class="hide">
					gatsby, bifesta<br/>
					우리가 여행 갈 때 필요한 것들<br/>
					(클렌징, 무빙러버, 기내반입, 핸디왁스, 기름종이, 훼이셜페이퍼)<br/>
					오늘 회원 가입하신 선착순 1만 회원님께 여행 갈 때 더 좋은 상품을 공짜로 보내 드려요~!
				</div>
				<img src="${CTX}/images/${DEVICE}/exhibition201804/img_top.png" alt="">
			</div>
			
			<div class="event-section event-section--bifesta">
				<div class="hide">
					Women’s Travel Kit<br/>
					1장으로 충분한 촉촉 클렌징 티슈 [정품] 비페스타 시트 모이스트 10매,
					기내 반입이 가능한 산뜻 클렌징폼 [사은품] 비페스타 탄산폼 브라이트닝 40g
				</div>
				<img src="${CTX}/images/${DEVICE}/exhibition201804/img_bifesta_close.png" alt="">
<%--
				<a href="javascript:void(0);" onclick="buyNow('319');"><span class="hide">0원 바로구매하기</span></a>
 --%>
			</div>
			
			<div class="event-section event-section--gatsby">
				<div class="hide">
					Men’s Travel Kit<br/>
					주머니에 쏙!~ 갸스비 대표 왁스 [정품] 갸스비 무빙러버 스파이키엣지_핸디,
					초강력 피지 흡수 인생 필름 [사은품] 갸스비 오일크리어 필름 5매,
					여행할 때 최고! 상쾌한 세안티슈 [사은품] 갸스비 훼이셜 페이퍼 5매
				</div>
				<img src="${CTX}/images/${DEVICE}/exhibition201804/img_gatsby.png" alt="">
				<a href="javascript:void(0);" onclick="buyNow('314');"><span class="hide">0원 바로구매하기</span></a>
			</div>

			<div class="event-section">
				<div class="hide">
					유의사항<br/>
					1)  4/26(목) 일괄 발송 됩니다.,
					2) 한 세트만 1회에 한하여 구매 가능합니다.,    
					3) 기존 회원은 구매 하실 수 없습니다. 
				</div>
				<img src="${CTX}/images/${DEVICE}/exhibition201804/img_footer.png" alt="">
			</div>
</body>
</html>