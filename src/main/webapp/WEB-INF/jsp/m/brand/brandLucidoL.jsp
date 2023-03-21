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
				<a href="${CTX}/brand/brandLucidoL.do" data-brand="lucidol" class="active"><span>루시도엘</span></a>
				<a href="${CTX}/brand/brandBarrier.do" data-brand="barrier"><span>베리어리페어</span></a>
<%--
				<a href="${CTX}/brand/brandMama.do" data-brand="mamabutter"><span>마마버터</span></a>
 --%>
				<a href="${CTX}/brand/brandDental.do" data-brand="dentalpro"><span>덴탈프로</span></a>
<%--
				<a href="${CTX}/brand/brandCharley.do" data-brand="charley"><span>찰리</span></a>
				<a href="${CTX}/brand/brandGpcreate.do" data-brand="gpcreate"><span>GP크리에이트</span></a>
 --%>
			</div>

			<div class="brand-box">
				<div class="brand-img">
					<img src="${CTX}/images/${DEVICE}/brand/img_lucidol.png" alt="루시도엘" />
				</div>
				<div class="brand-text">
					‘자신의 오리지널 스타일을 창조하는 헤어 디자이닝 브랜드＇
					로서 1993년 탄생한 ‘루시도 엘＇은 자신만의 멋을 즐기는 
					여성들을 위하여 시대의 트렌드에 맞는 뛰어난 품질의 제품을
					출시해 온 토탈 헤어 메이크 브랜드입니다.
				</div>
			</div>

			<div class="brand-title type2" style="margin: 0;">
				<h3>상품 Line-up</h3>
			</div>

			<div class="brand-content">
				<div class="brand-product">
					<ul>
						<li>
							<a href="${CTX}/product/productView.do?goodsCd=4902806407211">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_lucido_1.png" alt="">
								</div>
									<p class="small-txt">HAIR TREATMENT OIL</p>
									<p class="big-txt">고압축 아르간 오일이 배합된<br/>오일 타입 에센스</p>
									<p class="small-txt2">갈라지고 뚝뚝 끊어지는 손상모에 밀착, <br/>윤기와 보습 전달, 가벼운 사용감</p>
							</a>
						</li>
						<li>
							<a href="${CTX}/product/productView.do?goodsCd=4902806434996">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_lucido_2.png" alt="">
								</div>
									<p class="small-txt">HAIR TREATMENT OIL RICH MOISTURE</p>
									<p class="big-txt">고압축 아르간 오일이 배합된 <br/>고농축 오일 타입 에센스</p>
									<p class="small-txt2">
										최상의 보습, 고농축 리치 타입, 갈라지고 뚝뚝 끊어지는<br/>
										손상모에 밀착, 윤기와 보습 전달, 가벼운 사용감
									</p>
							</a>
						</li>
						<li>
							<a href="${CTX}/product/productView.do?goodsCd=4902806100587S">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_lucido_3.png" alt="">
								</div>
								<p class="small-txt">HAIR TREATMENT OIL </p>
								<p class="big-txt">극 손상모 집중 리페어<br/>시리즈 No.1 데이지케어 오일</p>
								<p class="small-txt2">
									끊기고 상한 모발, 시간이 흐르면 푸석해지는 모발<br/>
									극손상 모발의 고민 해결 리페어 오일
								</p>

							</a>
						</li>
						<li>
							<a href="${CTX}/product/productView.do?goodsCd=4902806407143&choiceCateIdx=35&gnbBrand=default">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_lucido_4.png" alt="">
								</div>
								<p class="small-txt">HAIR TREATMENT CREAM</p>
									<p class="big-txt">고압축 아르간 오일이 배합된 <br/>크림 타입 에센스</p>
									<p class="small-txt2">
										고농도의 진한 크림이 손상 모발을 밀착 보수, 모발 내부의<br/>
										수분을 보호하고 모발 끝까지 건조함과 푸석임을 방지
									</p>
							</a>
						</li>
						<li>
							<a href="${CTX}/product/productView.do?goodsCd=4902806407389&choiceCateIdx=35&gnbBrand=default">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_lucido_5.png" alt="">
								</div>
								<p class="small-txt">HAIR TREATMENT GELEE</p>
								<p class="big-txt">고압축 아르간 오일이 배합된<br>젤리 타입 에센스</p>
								<p class="small-txt2">보습 성분이 함유된 젤 타입 에센스가 쉽게 엉키는 손상모발을<br>부드럽고 차분하게 정돈</p>
							</a>
						</li>
						<li>
							<a href="${CTX}/product/productView.do?goodsCd=4902806326055&choiceCateIdx=36&gnbBrand=default">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_lucido_6.png" alt="">
								</div>
								<p class="small-txt">HAIR SPRAY SUPER HARD</p>
								<p class="big-txt">무향 강력 고정 헤어 스프레이</p>
								<p class="small-txt2">무향으로 부담없고 습기에도 강한 강력 고정 스프레이.<br>모발 케어 토코페롤 함유</p>
							</a>
						</li>
					</ul>
				</div>
			</div>

			<div class="brand-box">
				<div class="brand-img">
					<img src="${CTX}/images/${DEVICE}/contents/img_lucido_detail_2.png" alt="">
					<div class="hide">
						<p>최고급 소재 [아르간 오일]의 놀라운 힘! 끈적임 없이 부드럽게 빛나는 머릿결을 만나보세요.</p>
						<p>모로코의 건조한 사막에서만 자라는 아르간 나무, 그 열매에서 추출한 오일은 뛰어난 보습력과 영양분으로 현지에서는 신이 내린 원액이라 불리며 각광받고 있습니다.</p>
						<p>끈적임 없이 가벼운 질감으로 전 세계인들의 사랑을 받고 있는 루시도엘 아르간 오일로 당신의 머릿결도 더 윤기 있고 생기 있게 가꿔보세요.</p>
					</div>
				</div>
			</div>
		</div>
	</div>		
</body>
</html>