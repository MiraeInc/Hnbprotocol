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
				<a href="${CTX}/brand/brandMama.do" data-brand="mamabutter" class="active"><span>마마버터</span></a>
				<a href="${CTX}/brand/brandDental.do" data-brand="dentalpro"><span>덴탈프로</span></a>
				<a href="${CTX}/brand/brandCharley.do" data-brand="charley"><span>찰리</span></a>
				<a href="${CTX}/brand/brandGpcreate.do" data-brand="gpcreate"><span>GP크리에이트</span></a>
			</div>
				
			<div class="brand-box">
				<div class="brand-img">
					<img src="${CTX}/images/${DEVICE}/brand/img_mamabutter.png" alt="">
				</div>
				<div class="brand-text" style="text-align: center">
					<p>
						마마버터는 ‘안심하고 피부에 사용할 수 있는 오가닉 뷰티
						브랜드’로서 ‘엄마’인 ‘스키타니 에미’가 설립했습니다.<br/>
						전 라인에 천연 보습 성분 시어버터를 사용한 가족 보습 케어
						브랜드로 엄마도, 아빠도 그리고 아이도!<br/>
						온 가족이 함께 사용할 수 있습니다.
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
							<a href="${CTX}/product/productList.do?schCateIdx=51&schCateFlag=ALL">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_mamabutter_1.png" alt="">
								</div>
								<p class="small-txt">FACE &amp; BODY</p>
								<p class="big-txt">천연 시어버터의 로션/크림/오일</p>
								<p class="small-txt2">
									자연의 순수한 시어버터 성분으로<br/>
									전신을 촉촉하게 관리하세요.
								</p>
							</a>
						</li>
						<li>
							<a href="${CTX}/product/productView.do?goodsCd=4560278233327">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_mamabutter_2.png" alt="">
								</div>
								<p class="small-txt">HAND</p>
								<p class="big-txt">자연주의 핸드크림</p>
								<p class="small-txt2">
									간편하게 휴대하고 다니면서 핸드 &amp; 네일을<br/>
									촉촉 &amp; 윤기있게 관리하세요.
								</p>
							</a>
						</li>
						<li>
							<a href="${CTX}/product/productList.do?schGbn=&schCateIdx=58">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_mamabutter_3.png" alt="">
								</div>
								<p class="small-txt">BATH</p>
								<p class="big-txt">온 가족이 안심하고 사용하는 바디솝</p>
								<p class="small-txt2">
									풍성한 거품으로 깨끗하게 씻어내고, <br/>
									촉촉하게 마무리 하세요.
								</p>
							</a>
						</li>
						<!-- 
							수정 : 2018-11-14
							- 브랜드 추가
						--> 
						<li>
							<a href="${CTX}product/productView.do?goodsCd=4560278232986">
								<div class="brand-img">
									<img src="${CTX}/images/${DEVICE}/brand/img_item_mamabutter_4.png" alt="">
								</div>
								<p class="small-txt">MASK PACK</p>
								<p class="big-txt">순수 시어버터 크림 마스크</p>
								<p class="small-txt2">
									시어버터의 보습력을 담은 크림 마스크팩<br/>
									스킨+로션+에센스를 한 번에, 올인원 스페셜 케어<br/> 
									마스크팩으로 스페셜 케어 하세요. 
								</p>
							</a>
						</li>
						<!-- //수정 : 2018-11-14 -->
						
					</ul>
				</div>
			</div>
		</div>
	</div>		
</body>
</html>