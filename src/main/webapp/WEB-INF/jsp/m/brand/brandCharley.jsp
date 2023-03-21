<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main" />	
</head>
<body>
	<div class="content comm-brand brand-gatsby">
    	<page:applyDecorator  name="mobile.brandmenu" encoding="UTF-8"/>

		<div class="page-body">
			<div class="brand-filter">
			
				<a href="${CTX}/brand/brandGatsby.do" data-brand="gatsby"><span>갸스비</span></a>
				<a href="${CTX}/brand/brandBifesta.do" data-brand="bifesta"><span>비페스타</span></a>
				<a href="${CTX}/brand/brandLucidoL.do" data-brand="lucidol"><span>루시도엘</span></a>
				<a href="${CTX}/brand/brandBarrier.do" data-brand="barrier"><span>베리어리페어</span></a>
<%--
				<a href="${CTX}/brand/brandMama.do" data-brand="mamabutter"><span>마마버터</span></a>
 --%>
				<a href="${CTX}/brand/brandDental.do" data-brand="dentalpro"><span>덴탈프로</span></a>
<%--
				<a href="${CTX}/brand/brandCharley.do" data-brand="charley" class="active"><span>찰리</span></a>
				<a href="${CTX}/brand/brandGpcreate.do" data-brand="gpcreate"><span>GP크리에이트</span></a>
 --%>
			</div>

			<div class="brand-box">
				<div class="brand-img">
					<img src="${CTX}/images/${DEVICE}/brand/img_charley.png" alt="">
				</div>
				<div class="brand-text" style="text-align: center">
					<p>
						스트레스가 많은 현대인의 생활 속, 정신적 만족도를 위해
						탄생한 브랜드 ‘찰리’의 대표 상품은 일본 발매 이후 17년간 꾸준히 사랑 받고 있는 탄산 타입
						바스 태블릿 입욕제입니다.
					</p>
				</div>
			</div>

			<div class="brand-title type2" style="margin: 0;">
				<h3>상품 Line-up</h3>
			</div>

			<div class="brand-content">
				<div class="brand-product">
					<ul>
						<li>
							<a href="#none">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_charley_1.png" alt="">
								</div>
								<p class="small-txt">BATH PREPARATION Citron</p>
								<p class="big-txt">향긋한 유자향 탄산욕</p>
								<p class="small-txt2">
									그날 그날 향긋한 유자향으로 지친 몸을 리프레쉬 하세요.
								</p>
							</a>
						</li>
						<li>
							<a href="#none">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_charley_2.png" alt="">
								</div>
								<p class="small-txt">BATH PREPARATION Peach</p>
								<p class="big-txt">달콤한 피치향 탄산욕</p>
								<p class="small-txt2">
									집에서 간단하게 즐길 수 있는, 달콤한 피치향의 뽀글뽀글 바스 입욕제 입니다.
								</p>
							</a>
						</li>
						<li>
							<a href="#none">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_charley_3.png" alt="">
								</div>
								<p class="small-txt">BATH PREPARATION HONEY Chamomile</p>
								<p class="big-txt">은은한 허니 카모마일향 탄산욕</p>
								<p class="small-txt2">
									은은한 허니 카모마일향이 하루의 피로를 풀어줍니다.
								</p>
							</a>
						</li>
						<li>
							<a href="#none">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_charley_4.png" alt="">
								</div>
								<p class="small-txt">BATH PREPARATION Strawberry</p>
								<p class="big-txt">상큼한 스트로베리향 탄산욕</p>
								<p class="small-txt2">
									상큼한 향의 탄산 기포가 뽀글뽀글!<br/>
									피부를 촉촉하고 매끈하게 만들어줍니다.
								</p>
							</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>		
</body>
</html>