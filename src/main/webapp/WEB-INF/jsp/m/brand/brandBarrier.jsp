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
				<a href="${CTX}/brand/brandBarrier.do" data-brand="barrier" class="active"><span>베리어리페어</span></a>
<%--
				<a href="${CTX}/brand/brandMama.do" data-brand="mamabutter"><span>마마버터</span></a>
 --%>
				<a href="${CTX}/brand/brandDental.do" data-brand="dentalpro"><span>덴탈프로</span></a>
<%--
				<a href="${CTX}/brand/brandCharley.do" data-brand="charley" ><span>찰리</span></a>
				<a href="${CTX}/brand/brandGpcreate.do" data-brand="gpcreate"><span>GP크리에이트</span></a>
 --%>
			</div>


			<div class="brand-box">
				<div class="brand-img">
					<img src="${CTX}/images/${DEVICE}/brand/img_barrier.png" alt="">
				</div>
				<div class="brand-text" style="text-align: center">
					<p>
						2007년 탄생한 Barrier Repair 는 피부 본연의 아름다움을 
						유지하는데 중요한 피부 장벽을 케어하는 브랜드입니다.
						Barrier ‘장벽’ + Repair ‘개선’ = Barrier Repair 브랜드 명 처럼
						Barrier Repair 는 아기 피부 같이
						촉촉하고 탱탱한 피부를 위한 스킨케어 전문 브랜드로,
						모든 여성들의 아름다운 피부를 응원합니다.
					</p>
				</div>
			</div>

			<div class="brand-title type2" style="margin: 0;">
				<h3>상품 Line-up</h3>
			</div>

			<div class="brand-content">
				<div class="brand-product">
					<ul class="clearfix">
						<li>
							<a href="${CTX}/product/productView.do?goodsCd=4902806438093">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_barrier_1.png" alt="">
								</div>
								<p class="small-txt">MASK PACK</p>
								<p class="big-txt">건조케어 타입 수분촉촉 마스크팩</p>
								<p class="small-txt2">
									극강의 수분 공급으로 피부를 보들보들, 촉촉하게!<br/>
									각질층 깊은 곳까지 구석구석 침투하여 수분을<br/>
									공급합니다.
								</p>
							</a>
						</li>
						<li>
							<a href="${CTX}/product/productView.do?goodsCd=4902806437980">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_barrier_2.png" alt="">
								</div>
								<p class="small-txt">MASK PACK</p>
								<p class="big-txt">모공케어 타입 수분촉촉 마스크팩</p>
								<p class="small-txt2">
									만져보고 싶을 만큼 모공을 매끈하게!<br/>
									피부의 수분 지속력을 높이고, 살결을 정돈하여<br/>
									매끄러운 피부로 만들어 줍니다.
								</p>
							</a>
						</li>
						<li>
							<a href="${CTX}/product/productView.do?goodsCd=4902806438161">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_barrier_3.png" alt="">
								</div>
								<p class="small-txt">MASK PACK</p>
								<p class="big-txt">주름케어 타입 수분촉촉 마스크팩</p>
								<p class="small-txt2">
									속부터 차오르는 윤기로 탄력있게!<br/>
									침투력이 좋은 저분자 골라겐과 미용액이 잔주름을<br/>
									예방합니다.
								</p>
							</a>
						</li>
						<li>
							<a href="${CTX}/product/productView.do?goodsCd=4902806104066">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_barrier_4.png" alt="">
								</div>
								<p class="small-txt">BAMASK PACK</p>
								<p class="big-txt">투명한 피부를 위한 로즈힙 오일 마스크팩</p>
								<p class="small-txt2">
									미용 보습 효과가 높은 로즈힐 오일이 투명한<br/> 피부로 가꾸어 줍니다.
								</p>
							</a>
						</li>
						<li>
							<a href="${CTX}/product/productView.do?goodsCd=4902806103953">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_barrier_5.png" alt="">
								</div>
								<p class="small-txt">MASK PACK</p>
								<p class="big-txt">윤기나는 피부를 위한 코코넛 오일 마스크팩</p>
								<p class="small-txt2">
									야자나무 종자 유래 오일이 윤기있고 매끄러운<br/>피부로 가꾸어 줍니다.
								</p>
							</a>
						</li>
						<li>
							<a href="${CTX}/product/productView.do?goodsCd=4902806103878">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_barrier_6.png" alt="">
								</div>
								<p class="small-txt">MASK PACK</p>
								<p class="big-txt">탄력 피부를 위한 시어버터 오일 마스크팩</p>
								<p class="small-txt2">
									피부에 친숙한 시어버터 나무 유래 오일이 탄력있는<br/>피부로 가꾸어 줍니다.
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