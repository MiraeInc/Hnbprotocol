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
				<a href="${CTX}/brand/brandCharley.do" data-brand="charley" ><span>찰리</span></a>
				<a href="${CTX}/brand/brandGpcreate.do" data-brand="gpcreate" class="active"><span>GP크리에이트</span></a>
 --%>
			</div>


			<div class="brand-box">
				<div class="brand-img">
					<img src="${CTX}/images/${DEVICE}/brand/img_gpcreate.png" alt="">
				</div>
				<div class="brand-text" style="text-align: center">
					<p>
						‘나만의 카페가 욕조 속으로!’ 천연 소금에 카페의 향을 더한
						SALT BAR, 그 날의 기분에 따라 자유자재로 조합하여
						나만의 BATH TIME을 즐길 수 있습니다.  
						내추럴한 카페 느낌의 패키지는 선물용으로도 좋습니다.
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
							<a href="${CTX}/product/productView.do?goodsCd=4968324039860">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_gpcreate_1.png" alt="">
								</div>
								<p class="small-txt">BATH PREPARATION Lassi </p>
								<p class="big-txt">라씨 향 기분 전환 입욕제</p>
								<p class="small-txt2">
									신선한 레몬향과 차분한 우유향이 레이어링 되면<br/>
									기분전환에 좋은 라씨향 가득 아로마 테라피를<br/>
									즐길 수 있습니다.
								</p>
							</a>
						</li>
						<li>
							<a href="${CTX}/product/productView.do?goodsCd=4968324039877">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_gpcreate_2.png" alt="">
								</div>
								<p class="small-txt">BATH PREPARATION Apple Ginger</p>
								<p class="big-txt">애플진져 향 릴렉스 입욕제</p>
								<p class="small-txt2">
									상큼한 사과향과 진한 진저향이 레이어링 되면<br/>
									온몸이 릴렉스 되는 애플진저향이<br/>
									솔솔 퍼집니다.
								</p>
							</a>
						</li>
						<li>
							<a href="${CTX}/product/productView.do?goodsCd=4968324039853">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_gpcreate_3.png" alt="">
								</div>
								<p class="small-txt">BATH PREPARATION Lemon Tea </p>
								<p class="big-txt">레몬티 향 산뜻 입욕제</p>
								<p class="small-txt2">
									산뜻한 레몬향과 그윽한 홍차향이 레이어링 되면<br/>
									기분이 산뜻해 지는 레몬티 향 아로마 테라피<br/>
									효과를 느끼실 수 있습니다.
								</p>
							</a>
						</li>
						<li>
							<a href="${CTX}/product/productView.do?goodsCd=4968324039846">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_gpcreate_4.png" alt="">
								</div>
								<p class="small-txt">BATH PREPARATION Caffe Latte</p>
								<p class="big-txt">카페라떼 향 입욕제</p>
								<p class="small-txt2">
									부드러운 우유향과 진한 에스프레소향이
									레이어링 되면 유럽카페에 온 듯한 카페라떼 향이
									솔솔 퍼집니다.
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