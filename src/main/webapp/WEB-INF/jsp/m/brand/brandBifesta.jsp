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
				<a href="${CTX}/brand/brandBifesta.do" data-brand="bifesta" class="active"><span>비페스타</span></a>
				<a href="${CTX}/brand/brandLucidoL.do" data-brand="lucidol"><span>루시도엘</span></a>
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
					<img src="${CTX}/images/${DEVICE}/brand/img_bifesta.png" alt="">
				</div>
				<div class="brand-text">
					<p>BIFESTA(비페스타)는,일본어로 아름다움을 나타내는 <strong>‘비BI (<span style="font-family: 돋움">美</span>)’</strong>와 <strong>FESTIVAL의 ‘FESTA’</strong>가 합쳐져 만들어진 브랜드명으로 <strong>아름다움의 축제</strong>를 의미합니다.</p>
					<br>
					<p><strong>‘Cleansing for Beauty’</strong>라는 브랜드 컨셉 아래 아름다운 피부를 만들기 위한 <strong>기초 단계 고보습 저자극 클렌징 전문 브랜드</strong>로 단순히 메이크업을 지워내는 것만이 아니라 피부를 케어 하는 기능까지 함께 제공합니다.</p>
				</div>
			</div>

			<div class="brand-title type2" style="margin: 0;">
				<h3>상품 Line-up</h3>
			</div>

			<div class="brand-content">
				<div class="brand-product">
					<ul>
						<li>
							<a href="${CTX}/product/productList.do?schCateIdx=28&sch2depthCateIdx=26&gnbBrand=default">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_bifesta_1.png" alt="">
								</div>
								<p class="small-txt">CLEANSING WATER</p>
								<p class="big-txt">더 깨끗한 클렌징 워터</p>
								<p class="small-txt2">매일 하는 클렌징, 물 하나만 바꿔도 피부 전체가 바뀌어요</p>
							</a>
						</li>

						<li>
							<a href="${CTX}/product/productList.do?schCateIdx=29&sch2depthCateIdx=26&gnbBrand=default">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_bifesta_2.png" alt="">
								</div>
								<p class="small-txt">CLEANSING SHEET</p>
								<p class="big-txt">더 깨끗한 클렌징 시트</p>
								<p class="small-txt2">저자극으로 한 번에! 초극세사 시트로 완벽한 클렌징을 경험하세요</p>
							</a>
						</li>
						<li>
							<a href="${CTX}/product/productList.do?schCateIdx=30&sch2depthCateIdx=26&gnbBrand=default">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_bifesta_3.png" alt="">
								</div>
								<p class="small-txt">MOUSSE FOAM</p>
								<p class="big-txt">더 깨끗한 클렌징 무스 폼</p>
								<p class="small-txt2">쫀쫀한 탄산 거품의 힘으로 모공 속부터 깨끗이 지우세요</p>
							</a>
						</li>
						<li>
							<a href="${CTX}/product/productList.do?schCateIdx=31&sch2depthCateIdx=26&gnbBrand=default ">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_bifesta_4.png" alt="">
								</div>
								<p class="small-txt">EYE MAKEUP REMOVER</p>
								<p class="big-txt">더 깨끗한 아이 리무버</p>
								<p class="small-txt2">문지르지 말고 부드럽게 눈가 자극없이 편하게 지우세요</p>
							</a>
						</li>
					</ul>
				</div>
			</div>

			<div class="brand-title type2" style="margin-bottom: 0;">
				<h3>AD GALLERY</h3>
			</div>

			<div class="brand-movie">
				<ul>
					<li>
						<iframe width="100%" src="https://www.youtube.com/embed/LZ5YJt6lJz4?rel=0&autohide=1&autoplay=0&showinfo=0&controls=0" frameborder="0" title="" marginheight="0" marginwidth="0" allowfullscreen></iframe>
						<p class="movie-title">비페스타 클렌징워터 아오이유우</p>
					</li>
					<li>
						<iframe width="100%" src="https://www.youtube.com/embed/r3VF3pHYEi4?rel=0&autohide=1&autoplay=0&showinfo=0&controls=0" frameborder="0" title="" marginheight="0" marginwidth="0" allowfullscreen></iframe>
						<p class="movie-title">비페스타 탄산클렌징 무스폼 아오이유우</p>
					</li>
				</ul>
			</div>
		</div>
	</div>		
</body>
</html>