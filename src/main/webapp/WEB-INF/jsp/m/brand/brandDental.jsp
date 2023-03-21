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
				<a href="${CTX}/brand/brandDental.do" data-brand="dentalpro" class="active" ><span>덴탈프로</span></a>
<%--
				<a href="${CTX}/brand/brandCharley.do" data-brand="charley"><span>찰리</span></a>
				<a href="${CTX}/brand/brandGpcreate.do" data-brand="gpcreate"><span>GP크리에이트</span></a>
 --%>
			</div>

			<div class="brand-box">
				<div class="brand-img">
					<img src="${CTX}/images/${DEVICE}/brand/img_dentalpro.png" alt="">
				</div>
				<div class="brand-text" style="text-align: center">
					<p>
						일본 오랄 케어의 명가 ‘덴탈프로’는 일본 치간 칫솔 
						1위 브랜드로 1927년에 설립 되었습니다.<br/>
						창업 이래 ‘제품을 사용하는 분들의 건강과 행복을 기원하며, 
						생산과 판매를 한다’라는 이념을 바탕으로
						정열을 쏟고있는 덴탈프로는 국내외 고객뿐 아니라 
						학회에서도 높은 평가를 받고 있습니다.
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
							<a href="${CTX}/product/productList.do?schCateIdx=53&schCateFlag=ALL">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_dentalpro_1.png" alt="">
								</div>
								<p class="small-txt">INTERDENTAL BRUSH</p>
								<p class="big-txt">다양한 사이즈 LINE UP, 치간 칫솔(0~5 사이즈)</p>
								<p class="small-txt2">
									칫솔로는 닿지 않는 치아 사이의 이물질을 제거!<br/>
									소중한 치아를 건강하게 관리하세요.
								</p>
							</a>
						</li>
						<li>
							<a href="${CTX}/product/productList.do?schCateIdx=54&schCateFlag=ALL">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_dentalpro_2.png" alt="">
								</div>
								<p class="small-txt">TONGUE BRUSH</p>
								<p class="big-txt">입냄새 원인을 제거하는 혀클리너</p>
								<p class="small-txt2">
									구취의 원인이 되는 혀의 이물질(설태)을<br/>
									효과적으로 제거합니다.
								</p>
							</a>
						</li>
						<li>
								<a href="${CTX}/product/productView.do?goodsCd=4973227413266">
									<div class="brand-img">
										<img src="${CTX}/images/${DEVICE}/brand/img_item_dentalpro_3.png" alt="">
									</div>
									<p class="small-txt">INTEREDNTAL BRUSH</p>
									<p class="big-txt">소프트 러버 타입<br/>치간 칫솔</p>
									<p class="">
										칫솔 모가 부드러운 고무 타입으로 제작되어<br/>
										자극을 최소화 한 치간 칫솔 입니다.
									</p>
								</a>
							</li>

							<li>
								<a href="${CTX}/product/productView.do?goodsCd=4973227217031">
									<div class="brand-img">
										<img src="${CTX}/images/${DEVICE}/brand/img_item_dentalpro_4.png" alt="">
									</div>
									<p class="small-txt">ORTHODONTIC TOOTH BRUSH</p>
									<p class="big-txt">치아 교정 중인 분들의 필수 칫솔,<br/>교정용 칫솔</p>
									<p class="">
										교정용 보철물로 닿기 힘든 부분까지<br/>
										말끔히 양치할 수 있는 교정용 칫솔 입니다.
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