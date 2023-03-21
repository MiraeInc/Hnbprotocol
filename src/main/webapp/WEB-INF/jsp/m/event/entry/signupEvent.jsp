<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main"/>
<%-- 
	<meta charset="UTF-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge">
	<meta name="description" content="'헤아쟈무 뭐야?' '무빙 뭐야?' 남성 화장품 브랜드 '갸스비'의 사이트. 제품 정보, 제품 사용법, 이벤트, CM도 공개 중">
	<meta name="keywords" content="헤어 스타일링, 헤어 왁스, 무빙, 헤아쟈무 그리스 스타일링 그리스, 헤어 컬러 턴 컬러, 면도 셀프 컷, 좋아 면도기, 헤어 트리머, 눈썹, 몸 종이, 바디 시트 세안 시트 세안 종이, 얼굴 종이, 스킨 케어">
	<meta name="author" content="">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="format-detection" content="telephone=no, address=no, email=no">
	<title>GATSBY [갸스비]</title>
	<link rel="apple-touch-icon" href="http://image.mandom.co.kr/images/favicon-152.png">
	<link rel="shortcut icon" href="http://image.mandom.co.kr/images/favicon.ico">

	 <!-- GATSBY MOBILE CSS -->
	<link rel="stylesheet" href="http://image.mandom.co.kr/css/m/normalize.css">
	<link rel="stylesheet" href="http://image.mandom.co.kr/css/m/swiper.min.css">
	<link rel="stylesheet" href="http://image.mandom.co.kr/css/lightslider.min.css">
	<link rel="stylesheet" href="http://image.mandom.co.kr/css/remodal.min.css">
	<link rel="stylesheet" href="http://image.mandom.co.kr/css/remodal-default-theme.css">
	<link rel="stylesheet" href="http://image.mandom.co.kr/css/jquery-ui.min.css">

	<!-- GATSBY 2018 RENEWAL -->
	<link rel="stylesheet" href="http://image.mandom.co.kr/css/m/common_new.css">
	<link rel="stylesheet" href="http://image.mandom.co.kr/css/m/contents_new.css">
	
	<!-- GATSBY MOBILE JS -->
	<script src="http://image.mandom.co.kr/js/jquery-3.2.1.min.js"></script>
	<script src="http://image.mandom.co.kr/js/m/swiper.min.js"></script>
	<script src="http://image.mandom.co.kr/js/jquery-ui.min.js"></script>
	<script src="http://image.mandom.co.kr/js/jquery-migrate-3.0.0.min.js"></script>
	<script src="http://image.mandom.co.kr/js/lightslider.min.js"></script>
	<script src="http://image.mandom.co.kr/js/m/hammer.min.js"></script>
	<script src="http://image.mandom.co.kr/js/m/masonry.pkgd.min.js"></script>
	<script src="http://image.mandom.co.kr/js/m/ui.site.new.js"></script>
	<script src="http://image.mandom.co.kr/js/remodal.min.js"></script>


<script src="http://image.mandom.co.kr/js/${DEVICE}/cart.js"></script> --%>
<script>
<%-- 
function getSessionId(){
	if(localStorage.getItem("mandomSessionId") == null){
		localStorage.setItem("mandomSessionId","<%=session.getId()%>");
	}

	return localStorage.getItem("mandomSessionId");
}	 --%>
	// 바로구매
	function buyNowEvent(goodsIdx){
		var chk = "${islogin}";
		var nowjoin = "${nowjoin}";
		if (chk == null || chk =='' || chk == 'false') {
			location.href="${CTX}/member/joinStep01.do";
		} else {
			if (nowjoin == 'true') {
				var arrOrder = new Array();
				var goodsObj = new Object();
				goodsObj.goodsIdx = Number(goodsIdx);
				goodsObj.goodsCnt = 1;
				arrOrder.push(goodsObj);
				orderNow(arrOrder);
			} else {
				alert('오늘 신규 가입한 회원님만 구매 하실 수 있습니다.');
			}
		}
	}
	//1:회원가입하러가기, 2:5천포인트받기, 3:자동응모
	function goJoin(type) {
		var chk = "${islogin}";
		var nowjoin = "${nowjoin}";
		if (chk == null || chk =='' || chk == 'false') {
			location.href="${CTX}/member/joinStep01.do";
		} else {
			if(type==2) {
				if (nowjoin=='true') {
					alert('이미 포인트를 받으셨습니다.');
				} else {
					alert('오늘 신규 가입한 회원님만 받으실 수 있습니다.');
				}
			} else if(type==3) {
				if (nowjoin=='true') {
					alert('이미 자동응모 되었습니다.');
				} else {
					alert('오늘 신규 가입한 회원님만 자동응모 됩니다.');
				}
			} else {
				alert("이미 회원 가입 하셨습니다.");
			}
		}
	}
</script>
</head>

<!-- [D] 디자인 적용위해 각 브랜드 body에 data-main 추가 필요 -->
<body data-main="gatsby">

	<!-- 바로구매용 -->
	<!-- <form name="commonOrderForm" id="commonOrderForm" method="post" onsubmit="return false;">
		<input type="hidden" name="sessionId" id="commonSessionId" value=""/>
		<input type="hidden" name="orderGoodsInfoListStr" id="commonOrderGoodsInfoListStr" value=""/>
	</form> -->
<div id="wrap">

	<!-- s: content -->
	<main id="main" class="container" role="main">
		<div class="content comm-event evt-event-view">
			<div class="page-body">
				<div class="event-view">
					
					<style type="text/css">
						.event-inner {position: relative;}
						.event-section {position:relative; max-width: 750px; overflow: hidden;}
						.event-section img {max-width: 100%;}
						.event-goods {background:#888ff6;}
						.event-goods a {position: absolute; }
						.event-footer {background: #303030;}
						.event-view {margin: 0 -15px}
					</style>

					<div class="event-section event-top">
						<div class="hide">
							<p>
								GATSBY 
								STYLE &amp; CARE OIL
								케어가 댄디다
								오늘(11월 13일) 회원 가입 하시는 분께만
								1. 갸스비 스타일 & 케어오일 내츄럴 75ml 정품 (0원, 무료배송)
								2. 현금처럼 사용 가능한 5천포인트 지급
								3. APPLE AirPods 2세대(유선충전방식) 자동응모
							</p>
						</div>
						<div class="event-inner">
							<div>
								<img src="http://image.mandom.co.kr/images/naverevent/mo/img_top.png" alt="" />
							</div>
							<a href="javascript:void(0);"  onclick="goJoin(1);" style="position: absolute; left: 17.2%; top: 89.6%; width: 65.7%; height: 5.8%;"><span class="hide">회원가입하러가기</span></a>
						</div>
					</div>
			
					<div class="event-section event-goods">
						<div class="hide">
							<strong>오늘 맨담공식몰 회원 가입시</strong>
						</div>
						<div class="event-inner">
							<div>
							<c:choose>
								<c:when test="${detail.stockCnt gt 0}">
									<img src="http://image.mandom.co.kr/images/naverevent/mo/img_item.png" alt="" />
								</c:when>
								<c:otherwise>
									<img src="http://image.mandom.co.kr/images/naverevent/mo/img_item_soldout.png" alt="" />
								</c:otherwise>
							</c:choose>
							</div>
							<c:choose>
								<c:when test="${detail.stockCnt gt 0}">
									<a href="javascript:void(0);" onclick="buyNowEvent('823');" style="position: absolute; left: 13.3%; top: 30.2%; width: 73.5%; height: 5.2%;"><span class="hide">선착순 2천명 갸스비 스타일 & 케어오일 내츄럴 75ml 정품 (0원, 무료배송)</span></a>
								</c:when>
								<c:otherwise>
									<a href="javascript:void(0);" style="position: absolute; left: 13.3%; top: 30.2%; width: 73.5%; height: 5.2%;"><span class="hide">선착순 2천명 갸스비 스타일 & 케어오일 내츄럴 75ml 정품 (0원, 무료배송)</span></a>
								</c:otherwise>
							</c:choose>
							<a href="javascript:void(0);"  onclick="goJoin(2);" style="position: absolute; left: 13.3%; top: 54.8%; width: 73.5%; height: 5.2%"><span class="hide">회원가입 전원 현금처럼 사용 가능한 5천포인트 지급</span></a>
							<a href="javascript:void(0);"  onclick="goJoin(3);" style="position: absolute; left: 13.3%; top: 90.7%; width: 73.5%; height: 5.2%"><span class="hide">추첨 5분 APPLE AirPods 2세대(유선충전방식) 자동응모</span></a>
						</div>
					</div>
			
					<div class="event-section event-youtube">
						<div class="hide">
			
						</div>
						<div class="event-inner">
							<div><img src="http://image.mandom.co.kr/images/naverevent/mo/img_youtube.png" alt="" /></div>
							<iframe src="https://www.youtube.com/embed/HlonP3wCkOg?rel=0&autohide=1&autoplay=0&showinfo=0" style="position: absolute; left: 6.3%; top: 15.4%; width: 87.5%; height: 22.2%;" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
						</div>
						<div class="bg"></div>
					</div>
			
					<div class="event-section event-footer">
						<div class="event-inner">
							<div><img src="http://image.mandom.co.kr/images/naverevent/mo/img_footer.png" alt="" /></div>
						</div>
						<div class="hide">
							<strong>유의 사항 </strong>
							<p>
								ㆍ헤어오일은 11월 15일(금)에 일괄 발송됩니다.
								ㆍ헤어오일 0원 구매는 1회 1개만 가능합니다.
								ㆍ아이디가 달라도 배송지 주소나 전화번호가 같을 경우, 같은 분으로 인식하여 오일은 1개만 발송해 드리오니, 양해 부탁드립니다.
								ㆍ에어팟 당첨자는 11월 15일(금)에 맨담공식몰 공지를 통해 발표합니다.
								ㆍ소득세법 제 129조 1항 6조 규정에 따라 5만원이 초과되는 에어팟에 대해서는 제세공과금(22%)이 부과되며, 이는 회원님 부담입니다.
								ㆍ추가 증정 포인트(3천P)는 회원 가입 시 자동 지급되며, 포인트 유효기간은 11월 30일(토)까지 입니다.
								ㆍ본 이벤트의 모든 혜택은 11월 13일(수)에 회원 가입한 분께 드립니다. 기존 회원은 혜택을 받으실 수 없습니다.
								ㆍ경품의 교환 및 환불, 추가 증정은 불가능합니다.
								ㆍ주소 정보 오류로 인해 발생한 배송 문제에 대해서는 맨담 공식몰은 책임을 지지 않으며, 추가 증정은 불가능합니다.
								ㆍ피치 못할 사정으로 인해 경품 발송이 불가능할 경우, 사전 안내 후 비슷한 혹은 더 나은 수준의 경품으로 대체 발송될 수 있습니다.
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>