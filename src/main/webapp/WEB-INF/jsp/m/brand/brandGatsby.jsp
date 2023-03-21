<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main" />

<script type="text/javascript">
	(function(window, document, $){
	    $.fn.tabs = function(){
	        $(this).find("[data-target]").bind("click", function(e){
	            $("[data-tab]").removeClass('active');
	            $("[data-tab=" + $(this).data("target") + "]").addClass("active");
	        });
	    };
	
	    $(".dropdown-select").tabs();
	    $(".dropdown-select").dropdownAction();
	    $(".faq-box").faqAction();
	})(window, document, jQuery);
</script>

</head>
<body>
	<div class="content comm-brand brand-gatsby">
    	<page:applyDecorator  name="mobile.brandmenu" encoding="UTF-8"/> 
    	           
		<div class="page-body">
			<div class="brand-filter">
				<a href="${CTX}/brand/brandGatsby.do" data-brand="gatsby"  class="active"><span>갸스비</span></a>
				<a href="${CTX}/brand/brandBifesta.do" data-brand="bifesta"><span>비페스타</span></a>
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
					<img src="${CTX}/images/${DEVICE}/brand/img_gatsby.png" alt="">
				</div>
				<div class="brand-text">
					<p>GATSBY(갸스비)는 스콧 피츠 제럴드의 소설 『THE GREAT GATSBY』에서 유래된 브랜드 명으로, 1978년 발매 이래, 그 시대의 가장 스타일리쉬한 스타일을 제안하는 <b>MEN’S COSMETIC BRAND</b>입니다.</p>
					<br>
					<p>스타일에 민감한 16-30세 남성을 메인 타겟으로 <strong>HAIR · FACE · BODY · SHAVING</strong> 등 다양한 상품을 제안하고 있습니다.</p>
				</div>
			</div>

			<div class="dropdown-select">
				<ul class="">
					<li>
						<a href="javasciprt:void(0);" data-target="faceCare">FACE CARE</a>
					</li>
					<li>
						<a href="javasciprt:void(0);" data-target="hairStyling">HAIR STYLING</a>
					</li>
					<li class="active">
						<a href="javasciprt:void(0);" data-target="bodyCare">BODY CARE</a>
					</li>
				</ul>
			</div>

			<div class="brand-content">
					<div class="tab-panel" data-tab="faceCare">
						<div class="brand-product">
							<ul>
								<li>
									<a href="${CTX}/product/productList.do?schCateIdx=6&sch2depthCateIdx=1">
										<div class="brand-img">
											<img src="${CTX}/images/${DEVICE}/brand/img_item_gatsby_facecare_1.png" alt="">
										</div>
										<p class="small-txt">OIL CLEAR PAPER</p>
										<p class="big-txt">No.1 피지 흡수 필름 &amp; 페이퍼</p>
									</a>
								</li>
								<li>
									<a href="${CTX}/product/productView.do?goodsCd=4902806569353">
										<div class="brand-img">
											<img src="${CTX}/images/${DEVICE}/brand/img_item_gatsby_facecare_2.png" alt="">
										</div>
										<p class="small-txt">FACIAL PAPER</p>
										<p class="big-txt">밖에서도 세안한 듯!<br>땀과 피지 끈적임을 한번에</p>
									</a>
								</li>
								<li>
									<a href="${CTX}/product/productView.do?goodsCd=4902806251449">
										<div class="brand-img">
											<img src="${CTX}/images/${DEVICE}/brand/img_item_gatsby_facecare_3.png" alt="">
										</div>
										<p class="small-txt">SHAVING</p>
										<p class="big-txt">WET &amp; DRY 원하는 대로<br>상쾌한 쉐이빙</p>
									</a>
								</li>
								<li>
									<a href="${CTX}/product/productView.do?goodsCd=4902806122961">
										<div class="brand-img">
											<img src="${CTX}/images/${DEVICE}/brand/img_item_gatsby_facecare_4.png" alt="">
										</div>
										<p class="small-txt">EYEBROW KIT</p>
										<p class="big-txt">멋진 눈썹 만들기 남성용 셀프 킷트</p>
									</a>
								</li>
							</ul>
						</div>
					</div>
					<div class="tab-panel" data-tab="hairStyling">
						<div class="brand-product">
							<ul>
								<li>
									<a href="${CTX}/product/productList.do?schCateIdx=8&sch2depthCateIdx=2&sch3depthCateIdx=12">
										<div class="brand-img">
											<img src="${CTX}/images/${DEVICE}/brand/img_item_gatsby_hairstyling_1.png" alt="">
										</div>
										<p class="small-txt">MOVING RUBBER</p>
										<p class="big-txt">톱스타일리스트가 개발한<br>갸스비 무빙러버</p>
									</a>
								</li>
								<li>
									<a href="${CTX}/product/productList.do?schCateIdx=8&sch2depthCateIdx=2&sch3depthCateIdx=14">
										<div class="brand-img">
											<img src="${CTX}/images/${DEVICE}/brand/img_item_gatsby_hairstyling_2.png" alt="">
										</div>
										<p class="small-txt">HAIR JAM</p>
										<p class="big-txt">새로운 스타일링 기술로<br>내츄럴 스타일링</p>
									</a>
								</li>
								<li>
									<a href="${CTX}/product/productList.do?schCateIdx=8&sch2depthCateIdx=2&sch3depthCateIdx=18">
										<div class="brand-img">
											<img src="${CTX}/images/${DEVICE}/brand/img_item_gatsby_hairstyling_3.png" alt="">
										</div>
										<p class="small-txt">PERFECT HOLD WAX</p>
										<p class="big-txt">당신을 완벽한 직장인으로<br>만들어 줄 헤어스타일</p>
									</a>
								</li>
								<li>
									<a href="${CTX}/product/productView.do?goodsCd=4902806126549">
										<div class="brand-img">
											<img src="${CTX}/images/${DEVICE}/brand/img_item_gatsby_hairstyling_4.png" alt="">
										</div>
										<p class="small-txt">ULTRA HARD WAX</p>
										<p class="big-txt">최강 셋팅력 역동 스타일</p>
									</a>
								</li>
								<li>
									<a href="${CTX}/product/productList.do?schCateIdx=8&sch2depthCateIdx=2&sch3depthCateIdx=13">
										<div class="brand-img">
											<img src="${CTX}/images/${DEVICE}/brand/img_item_gatsby_hairstyling_5.png" alt="">
										</div>
										<p class="small-txt">STYLING WAX</p>
										<p class="big-txt">강력한 고정력으로<br>다양한 스타일 연출</p>
									</a>
								</li>
								<li>
									<a href="${CTX}/product/productList.do?schCateIdx=17&sch2depthCateIdx=2">
										<div class="brand-img">
											<img src="${CTX}/images/${DEVICE}/brand/img_item_gatsby_hairstyling_6.png" alt="">
										</div>
										<p class="small-txt">POMADE</p>
										<p class="big-txt">글로시한 윤기, 하드한 스타일 지속</p>
									</a>
								</li>
								<li>
									<a href="${CTX}/product/productList.do?schCateIdx=9&sch2depthCateIdx=2">
										<div class="brand-img">
											<img src="${CTX}/images/${DEVICE}/brand/img_item_gatsby_hairstyling_7.png" alt="">
										</div>
										<p class="small-txt">SPRAY</p>
										<p class="big-txt">습기가 많은 날에도 강력한 고정력</p>
									</a>
								</li>
								<li>
									<a href="${CTX}/product/productList.do?schCateIdx=11&sch2depthCateIdx=2">
										<div class="brand-img">
											<img src="${CTX}/images/${DEVICE}/brand/img_item_gatsby_hairstyling_8.png" alt="">
										</div>
										<p class="small-txt">GEL</p>
										<p class="big-txt">원하는 대로 스타일링 하고<br>끈적임 없이 빠른 건조</p>
									</a>
								</li>
								<li>
									<a href="${CTX}/product/productList.do?schCateIdx=10&sch2depthCateIdx=2">
										<div class="brand-img">
											<img src="${CTX}/images/${DEVICE}/brand/img_item_gatsby_hairstyling_9.png" alt="">
										</div>
										<p class="small-txt">FOAM</p>
										<p class="big-txt">발림이 좋은 마이크로 입자의<br>거품이 강한 셋팅력 지속</p>
									</a>
								</li>
								<li>
									<a href="${CTX}/product/productView.do?goodsCd=4902806222531&choiceCateIdx=24&gnbBrand=default">
										<div class="brand-img">
											<img src="${CTX}/images/${DEVICE}/brand/img_item_gatsby_hairstyling_10.png" alt="">
										</div>
										<p class="small-txt">SHAMPOO</p>
										<p class="big-txt">풍성한 거품으로 한 번만 감아도<br/>상쾌한 퍼펙트 클리어 샴푸</p>
									</a>
								</li>
							</ul>
						</div>
					</div>
					<div class="tab-panel active" data-tab="bodyCare">
						<div class="brand-product">
							<ul>
								<li>
									<a href="${CTX}/product/productView.do?goodsCd=4902806417340">
										<div class="brand-img">
											<img src="${CTX}/images/${DEVICE}/brand/img_item_gatsby_bodycare_1.png" alt="">
										</div>
										<p class="small-txt">BODY TRIMMER</p>
										<p class="big-txt">매너남들의 필수품!<br>편하게 숱과 길이 조절 가능</p>
									</a>
								</li>
								<li>
									<a href="${CTX}/product/productView.do?goodsCd=4902806132397">
										<div class="brand-img">
											<img src="${CTX}/images/${DEVICE}/brand/img_item_gatsby_bodycare_2.png" alt="">
										</div>
										<p class="small-txt">BODY PAPER</p>
										<p class="big-txt">밖에서도 샤워 한 듯<br>한 장으로 간편하게</p>
									</a>
								</li>
								<li>
									<a href="${CTX}/product/productView.do?goodsCd=4902806484403">
										<div class="brand-img">
											<img src="${CTX}/images/${DEVICE}/brand/img_item_gatsby_bodycare_3.png" alt="">
										</div>
										<p class="small-txt">DEODORANT</p>
										<p class="big-txt">한 번 사용으로 밤까지<br>끈적임 제로, 간편하고 상쾌하게</p>
									</a>
								</li>
								<li>
									<a href="${CTX}/product/productList.do?schCateIdx=23&sch2depthCateIdx=3&gnbBrand=default">
										<div class="brand-img">
											<img src="${CTX}/images/${DEVICE}/brand/img_item_gatsby_bodycare_4.png" alt="">
										</div>
										<p class="small-txt">SHOWER FRESH</p>
										<p class="big-txt">
											막 샤워한 남자의 향기<br/>
											뿌린듯, 안 뿌린듯! <br/>
											라이트한 샤워프레시
										</p>
									</a>
								</li>
							</ul>
						</div>
					</div>
				</div>

				<div class="brand-title type2">
					<h3>AD GALLERY</h3>
				</div>

				<div class="brand-movie">
					<ul>
						<li>
							<iframe width="100%" src="https://www.youtube.com/embed/5wxXZEhAFAM?rel=0&autohide=1&autoplay=0&showinfo=0&controls=0" frameborder="0" title="" marginheight="0" marginwidth="0" allowfullscreen></iframe>
							<p class="movie-title">GATSBY Brand Movie：The Small Things</p>
						</li>
						<li>
							<iframe width="100%" src="https://www.youtube.com/embed/QQw5b0Zt_l0?rel=0&autohide=1&autoplay=0&showinfo=0&controls=0" frameborder="0" title="" marginheight="0" marginwidth="0" allowfullscreen></iframe>
							<p class="movie-title">RESPECT YOUR STYLE!  FULL M/V</p>
						</li>
						<li>
							<iframe width="100%" src="https://www.youtube.com/embed/oTWN2lK7yk8?rel=0&autohide=1&autoplay=0&showinfo=0&controls=0" frameborder="0" title="" marginheight="0" marginwidth="0" allowfullscreen></iframe>
							<p class="movie-title">RESPECT YOUR STYLE! 60” ver.</p>
						</li>
					</ul>
				</div>
		</div>
	</div>
</body>
</html>