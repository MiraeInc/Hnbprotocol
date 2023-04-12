<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<c:set var="layout_type" value="${gnbBrand}" scope="request" />

<!DOCTYPE>
<html>
<head>
<c:set var="detailImgSplit" value="${fn:split(detail.mainFile ,'.')}"/>
<script>
	dataLayer = [];
</script>
<meta property="og:type" content="website">
<meta property="og:title" content="${detail.goodsTitle}">
<meta property="og:description" content="${detail.shortInfo}">
<meta property="og:image" content="${IMGPATH}/goods/${detail.goodsIdx}/${detailImgSplit[0]}_M.${detailImgSplit[1]}">
<meta name="decorator" content="mobile.main" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<script type="text/javascript">
		// mobon 4.0
		var device = "${DEVICE}".toUpperCase();
		var discountPrice = Math.floor("${detail.discountPrice}");
		var soldOut = "";
		if("${detail.soldoutYn}" == "Y"){
			soldOut = 'Y';
	  	}else{
	  		soldOut = 'N';
	  	}
		
		var ENP_VAR = { 
	        collect: {}, 
	        conversion: { product: [] }
	    };
	    ENP_VAR.collect.productCode = "${detail.goodsCd}";
	    ENP_VAR.collect.productName = "${detail.goodsTitle}";
	    ENP_VAR.collect.price = '${detail.price}';
	    ENP_VAR.collect.dcPrice = discountPrice;
	    ENP_VAR.collect.soldOut = soldOut;
	    ENP_VAR.collect.imageUrl = "${IMGPATH}/goods/${detail.goodsIdx}/${detailImgSplit[0]}_M.${detailImgSplit[1]}";
	    //encodeURIComponent("${IMGPATH}/goods/${detail.goodsIdx}/${detailImgSplit[0]}_M.${detailImgSplit[1]}"));   //전체URL(http포함)
	    var cateNavi = "${detail.cateNavi}";
	    var naviSplit = cateNavi.split(">");
	    var bCate = $.trim(naviSplit[0]);
	    var mCate = $.trim(naviSplit[1]);
	    var sCate = $.trim(naviSplit[2]);
	    
	    ENP_VAR.collect.topCategory = bCate;
	    ENP_VAR.collect.firstSubCategory = bCate;
	    ENP_VAR.collect.secondSubCategory = mCate;
	    ENP_VAR.collect.thirdSubCategory = sCate;
	    
	    /* 간편 결제 시스템을 통한 전환. (이용하지 않는 경우 삭제) */
	    ENP_VAR.conversion.product.push({
	        productCode : "${detail.goodsCd}",
	        productName : "${detail.goodsTitle}",
	        price : '${detail.price}',
	        dcPrice : discountPrice,
	        qty : 1,
	        soldOut : soldOut,
	        imageUrl : "${IMGPATH}/goods/${detail.goodsIdx}/${detailImgSplit[0]}_M.${detailImgSplit[1]}",
	        topCategory : bCate,
	        firstSubCategory : bCate,
	        secondSubCategory : mCate,
	        thirdSubCategory : sCate
	    });

	    (function(a,g,e,n,t){a.enp=a.enp||function(){(a.enp.q=a.enp.q||[]).push(arguments)};n=g.createElement(e);n.async=!0;n.defer=!0;n.src="https://cdn.megadata.co.kr/dist/prod/enp_tracker_self_hosted.min.js";t=g.getElementsByTagName(e)[0];t.parentNode.insertBefore(n,t)})(window,document,"script");
	    
	    /* 상품수집 */
	    enp('create', 'collect', 'mandom', { device: device });
	    /* 장바구니 버튼 타겟팅 (이용하지 않는 경우 삭제) */
	    enp('create', 'cart', 'mandom', { device: device, btnSelector: '#cartBtn' });
	    /* 찜 버튼 타겟팅 (이용하지 않는 경우 삭제) */
	    enp('create', 'wish', 'mandom', { device: device, btnSelector: '#wishBtn' });
	    /* 간편 결제 시스템을 통한 전환. (이용하지 않는 경우 삭제) */
	    enp('create', 'conversion', 'mandom', { device: device, paySys: 'naverPay' });
	</script>

<script>

	if("<spring:message code='server.status'/>" == "LIVE"){
		var discountPrice = Math.floor("${detail.discountPrice}");
		
		dataLayer.push({
			'ecommerce': {
			'detail': {
			'products': [{
			 "id"				:	"${detail.goodsCd}",  
			  "name"			: 	"${detail.goodsTitle}",  
			  "category"		: 	"${detail.cateNavi}",  
			  "price"			:	discountPrice  
		}] } } });
		
		<!-- Facebook Pixel Code -->
		fbq('track', 'ViewContent', {
			content_name:'${detail.goodsNm}',
	        content_ids : ['${detail.goodsCd}'],
	        content_type: 'product',
	        value       : discountPrice,
	        currency    : 'KRW'
	    });
		
	}

	var recmProductSlider = null;

    $(function(){
        var w = $(window); 
        var wh = w.height(); 

        //타임세일
        $(".detail-timer").timesale();
        
        $('[data-ui="recmProducts"]').imagesLoaded( function(e) {
			//추천상품
			recmProductSlider = $('[data-ui="recmProducts"]').lightSlider({
				item:4,
				loop:false,
				controls:true,
				pager:false,
				slideMargin: 20,
				addClass: 'recommend-list',
				responsive : [
					{
						breakpoint:569,
						settings: {
							item:3
						}
					},
					{
						breakpoint:413,
						settings: {
							item:2
						}
					}
				]
			});
		});
      
        //추천상품 액션
        $('.ui-recommend').on('click', '.list-prev', function(e){
            recmProductSlider.goToPrevSlide();
        });
        $('.ui-recommend').on('click', '.list-next', function(e){
            recmProductSlider.goToNextSlide();
        });


        //상품상세 탭
        $.touchflow({
            wrap: '.tab-menu',
            container: '.menu-inner',
            list: '.menu-list',
            tablink: '.menu-link'
        })

       //추가상품 영역 
			$('#productExtra').addClass('active');
			$('#productExtra').on('click', '.btn-toggle', function(e){

				var $this = $(this);
				var $root = $this.closest('.product-extra');
				var $content = $root.find('.extra-product');

				$root.toggleClass('extra-open');
				$this.toggleClass('active');

				if($content.is(':visible')){
					$('body').removeClass('fixed');
					$content.slideUp();
					$this.find('.txt').text('펼치기');
					$root.find('.extra-npay2').hide();
					$root.removeClass('extra-open-npay');
					$root.find('.extra-btns').show();
						
				}else{
					$('body').addClass('fixed');
					$content.slideDown();
					$this.find('.txt').text('접기');
					
					//옵션 선택
					$.touchflow({
						wrap: '#extraItems',
						container: '.item-inner',
						list: '.item-list'
					})
						
				}

			});

			$('#btnNpay').on('click', function(){
				$('.extra-npay2').show();
				$('#productExtra').addClass('extra-open-npay')
				$('#productExtra').find('.btn-toggle').trigger('click');
			});


        var detailImage = $('[data-ui="detailImage"]').lightSlider({
            item:1,
            loop:false,
            slideMargin: 0
        });

        $('#tabDetail').on('click', '.btn-more', function(e){
        alert('asdf');
            $this = $(this);
            $container = $this.prev('.image-container');
            $container.css({
                height: 'auto'
            })
            $this.hide();
        })
    })
</script>

<script>
// 페이지 로딩후 바로 펼쳐지도록 처리
window.onload = function(){
    $this = $('.btn-more');

    $container = $this.prev('.image-container');
    $container.css({
        height: 'auto'
    })
    $this.hide();
};
</script>

<script>

	$(document).ready(function(){
		createNumeric(".productCnt");			//숫자만 입력가능
		var callurl = location.href;
	   var pos = callurl.indexOf("#reviewTab");
	   
	   //메인의 리뷰선택으로 왔을경우 리뷰탭활성화
	   if (pos > -1) {
		   
	   		$("#reviewTab").addClass("active");
	   		$("#detailTab").removeClass("active");
	   		
	   		$("#reviewTab").trigger("click");
	   		var offset = $("#reviewTab").offset();
	   //     $('html, body').animate({scrollTop : offset.top}, 400);
	        
	   }
	});

	//수량 입력 변경
	function changeCnt(obj, type, goodsCd){
		// type 0 : 기본상품, 1 : 옵션 상품
		var productNm;											// 상품명
		var couponDiscountPrice = 0;					// 할인금액
		var maxDiscount = 0;								// 쿠폰 최대 할인금액
		var productPrice = 0;									// 합계금액
		var stockCnt = 0;										// 재고수량
		var discountPrice = 0; 								// 할인가
		var buyMaxCnt = 0; 									// 최대 구매 가능 수량
		var buyMinCnt = 0; 									// 최소 구매 가능 수량
		var discountType; 										//	쿠폰 TYPE = R : 정률,  A : 정액 
		
		var val = obj.value;									// 현재 수량													
			var re=/[^0-9]/gi;
			obj.value = val.replace(re,"");
			
		if(type == 0){
			var goodsCd = $("#goodCd").val();
		}else{
			var goodsCd = goodsCd;
		}
	
		// 재고수량 Ajax
		$.ajax({
			 url: "${CTX}/ajax/product/productStockCntAjax.do",
			 data : {
				 			"goodsCd"	: goodsCd
				 		},
			 type: "POST",	
			 async: false,
			 cache: false,
			 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 error: function(request, status, error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);	
			 },
			 success: function(map){ 
				 stockCnt = Number(map.stockCnt); 
				 couponDiscountPrice = Number(map.couponDiscountPrice);
				 maxDiscount = Number(map.maxDiscount);
				 discountPrice = Number(map.discountPrice);
				 buyMaxCnt = Number(map.buyMaxCnt);
				 buyMinCnt = Number(map.buyMinCnt);
				 productNm = map.goodsTitle;
				 discountType = map.discountType;
			 }
		}); 
			
		if(stockCnt > 0 || buyMaxCnt > 0 || buyMinCnt > 0){
	 		if(obj.value <= buyMaxCnt && obj.value >= buyMinCnt && obj.value <= stockCnt){
	 			$("#hiddenCnt"+goodsCd).val(obj.value);
	 		}
			}
			
		if($("#hiddenCnt"+goodsCd).val() == "" || $("#hiddenCnt"+goodsCd).val() == 0){
			if(buyMinCnt > 0){
				$("#hiddenCnt"+goodsCd).val(buyMinCnt);
			}
		}
		
		// 재고 수량 체크
		if(stockCnt > 0 ){		
			if(obj.value > stockCnt ){		
				alert(productNm+"의 재고 수량은 " + comma(stockCnt) + "개 입니다." );
				$("#productCnt"+goodsCd).val(Number($("#hiddenCnt"+goodsCd).val()));	
				return;
			}
		}
		
		// 최대 구매가능 수량 체크
		if(buyMaxCnt > 0 ){
			if(obj.value > buyMaxCnt ){
				alert(productNm+"의 최대 구매가능한 수량은 " + comma(buyMaxCnt) + "개 입니다." );
				$("#productCnt"+goodsCd).val(Number($("#hiddenCnt"+goodsCd).val()));	
				return;
			}
		}
		
		// 최소 구매가능 수량 체크
		if(buyMinCnt > 0 ){
			if(obj.value < buyMinCnt ){			
				alert(productNm+"의 최소 구매가능한 수량은 " + comma(buyMinCnt) + "개 입니다." );
				$("#productCnt"+goodsCd).val(Number($("#hiddenCnt"+goodsCd).val()));	
				return;
			}
		}
		
		if(obj.value > 0){
			$("#hiddenCnt"+goodsCd).val(obj.value);
		}else{
			if($("#hiddenCnt"+goodsCd).val() == "" || $("#hiddenCnt"+goodsCd).val() == 0){
				$("#hiddenCnt"+goodsCd).val(1);
				$("#productCnt"+goodsCd).val(1);
			}else{
				$("#productCnt"+goodsCd).val(Number($("#hiddenCnt"+goodsCd).val()));
			}
		}
			
			if(isNaN(couponDiscountPrice)){
				couponDiscountPrice = 0;
		}
	
		// 쿠폰 TYPE = R : 정률,  A : 정액
		// 정률일때 (할인금액 수량 * 할인금액)
		if(discountType == "R"){			
 		couponDiscountPrice = Number($("#productCnt"+goodsCd).val() * couponDiscountPrice);		// 할인 금액
		}else{	// 정액일때 (할인금액)
			couponDiscountPrice = couponDiscountPrice;	// 할인 금액
		}
			
		productPrice = Number($("#productCnt"+goodsCd).val() * discountPrice);									// 상품 금액
		
		// 수량 최대 할인 금액 체크
		if(couponDiscountPrice != 0){
			if(couponDiscountPrice < maxDiscount){
				$("#couponPrice"+goodsCd).val(couponDiscountPrice);
				$("#maxDiscountFlag"+goodsCd).val("Y");
			}else{
				
				if(maxDiscount != 0){
					if($("#maxDiscountFlag"+goodsCd).val() == "Y"){
						alert(productNm+"의 최대 할인 금액은 " + comma(maxDiscount) + "원 입니다." );
						$("#maxDiscountFlag"+goodsCd).val("N");
					}
					$("#couponPrice"+goodsCd).val(maxDiscount);
				}else{
					$("#couponPrice"+goodsCd).val(couponDiscountPrice);
				}
			}
		}
			
		$("#productPrice"+goodsCd).text(comma(productPrice));
		totalPrice();
	}

	//수량 버튼 변경
	function changeBtnCnt(flag, type, discountPrice, buyMaxCnt, buyMinCnt, goodsCd, goodsTitle, couponDiscountPrice, maxDiscount, discountType){
		// type 0 : 기본상품, 1 : 옵션 상품
		var productPrice = 0;									// 합계금액
		var stockCnt = 0;										// 재고수량
		
		if(type == 0){	// 대표상품
			var goodsCd = $("#goodCd").val();																						// 상품코드
			var couponDiscountPrice = Number("${detail.couponDiscountPrice}");							// 쿠폰할인가
			var maxDiscount = Number("${detail.maxDiscount}");														// 쿠폰 최대 할인금액
			var discountPrice = Number("${detail.discountPrice}");													// 상품할인가
			var buyMaxCnt = Number("${detail.buyMaxCnt}");															// 최대 구매 가능 수량
			var buyMinCnt = Number("${detail.buyMinCnt}");															// 최소 구매 가능 수량
			var productNm = "${detail.goodsTitle}";																				// 상품명
			var discountType = "${detail.discountType}";																		// 쿠폰 TYPE = R : 정률,  A : 정액
		}else{	// 옵션 상품
			var goodsCd = goodsCd;
			var couponDiscountPrice = couponDiscountPrice;
			var maxDiscount = Number(maxDiscount);													
			var discountPrice = Number(discountPrice);		
			var buyMaxCnt = Number(buyMaxCnt);								
			var buyMinCnt = Number(buyMinCnt);
			var productNm = goodsTitle;
			var discountType = discountType;																		
		}
		
		if(flag == "P"){
			var productMaxCnt = Number($("#productCnt"+goodsCd).val())+1;
			 
			 if(productMaxCnt >= 10000){
				return false;
			 }
			 
			// 재고수량 Ajax
			$.ajax({
				 url: "${CTX}/ajax/product/productStockCntAjax.do",
				 data : {
					 			"goodsCd"	: goodsCd
					 		},
				 type: "POST",	
				 async: false,
				 cache: false,
				 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
				 error: function(request, status, error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);	
				 },
				 success: function(map){ 
					 stockCnt = Number(map.stockCnt); 
				 }
			}); 
			
			// 재고 수량 체크
			if(stockCnt > 0){
				if(Number($("#productCnt"+goodsCd).val())+1 > stockCnt ){				
					alert(productNm+"의 재고 수량은 " + comma(stockCnt) + "개 입니다." );
					return;
				}
			}
			
			// 최대 구매가능 수량 체크
			if(buyMaxCnt > 0){
				if(Number($("#productCnt"+goodsCd).val())+1 > buyMaxCnt ){					
					alert(productNm+"의 최대 구매가능한 수량은 " + comma(buyMaxCnt) + "개 입니다." );
					return;
				}
			}
		
			$("#hiddenCnt"+goodsCd).val(Number($("#productCnt"+goodsCd).val())+1);
			$("#productCnt"+goodsCd).val(Number($("#productCnt"+goodsCd).val())+1);
			
			// 쿠폰 TYPE = R : 정률,  A : 정액
			// 정률일때 (할인금액 수량 * 할인금액)
			if(discountType == "R"){			
				couponDiscountPrice = Number($("#productCnt"+goodsCd).val() * couponDiscountPrice);		// 할인 금액
			}else{	// 정액일때 (할인금액)
				couponDiscountPrice = couponDiscountPrice;	// 할인 금액
			}
			
			productPrice = Number($("#productCnt"+goodsCd).val() * discountPrice);		
			
			// 수량 최대 할인 금액 체크
			if(couponDiscountPrice != 0){
				if(couponDiscountPrice < maxDiscount){
					$("#couponPrice"+goodsCd).val(couponDiscountPrice);				
				}else{
					if(maxDiscount != 0){
						if($("#maxDiscountFlag"+goodsCd).val() == "Y"){
							alert(productNm+"의 최대 할인 금액은 " + comma(maxDiscount) + "원 입니다." );							
							$("#maxDiscountFlag"+goodsCd).val("N");
						}else{
							$("#couponPrice"+goodsCd).val(maxDiscount);
						}
					}else{
						$("#couponPrice"+goodsCd).val(couponDiscountPrice);			
					}
				}
			}
			
			$("#productPrice"+goodsCd).text(comma(productPrice));
			totalPrice();
		}else if(flag == "M"){
			// 최소 구매 수량
			if(buyMinCnt > 0){
				if(Number($("#productCnt"+goodsCd).val() -1 ) < buyMinCnt){
					alert(productNm+"의 최소 구매가능한 수량은 " + comma(buyMinCnt) + "개 입니다." );
					return;
				}
			}
			if(Number($("#productCnt"+goodsCd).val()) >1){
				$("#hiddenCnt"+goodsCd).val(Number($("#productCnt"+goodsCd).val())-1);
				$("#productCnt"+goodsCd).val(Number($("#productCnt"+goodsCd).val())-1);
				
				// 쿠폰 TYPE = R : 정률,  A : 정액
				// 정률일때 (할인금액 수량 * 할인금액)
				if(discountType == "R"){			
					couponDiscountPrice = Number($("#productCnt"+goodsCd).val() * couponDiscountPrice);		// 할인 금액
				}else{
					couponDiscountPrice = couponDiscountPrice;	// 할인 금액
				}
				
				productPrice = Number($("#productCnt"+goodsCd).val() * discountPrice);									// 상품 금액
				
				// 수량 최대 할인 금액 체크
				if(couponDiscountPrice > maxDiscount){
					if($("#maxDiscountFlag"+goodsCd).val() == "N"){
						$("#couponPrice"+goodsCd).val(maxDiscount);
					}else{
						$("#couponPrice"+goodsCd).val(couponDiscountPrice);
					}
				}else{
					$("#couponPrice"+goodsCd).val(couponDiscountPrice);			
					$("#maxDiscountFlag"+goodsCd).val("Y");
				}
				
				$("#productPrice"+goodsCd).text(comma(productPrice));
				totalPrice();
			}
		}	
	}
	
	// 합계 금액 계산
	function totalPrice(){
		var totalPrice = 0;																											//	총 결정예상금액
		var totalCouponDiscountPrice = 0;																				// 총 할인가격
		
		$("[id^='productPrice']").each(function(index) {
			totalPrice += Number(uncomma($(this).text()));
		});
		
		$("[id^='couponPrice']").each(function(index) {
			totalCouponDiscountPrice += Number($(this).val());
		});
		
		$("#totalCouponDiscountPrice").text("-"+comma(totalCouponDiscountPrice));
		$("#totalPrice").text(comma(totalPrice-totalCouponDiscountPrice));
	}
	
	// HOW TO USE (상품 사용법) 페이지
	function goHowtouse(howtouseIdx){
		var  goodsIdx = "${detail.goodsIdx}";
		
		if(howtouseIdx == ""){
			alert("상품사용법에 등록된 상품이 없습니다.");
		}else{
			location.href = "${CTX}/style/howtouseView.do?howtouseIdx="+howtouseIdx+"&goodsIdx="+goodsIdx;			 
		}
	}
	
	// 옵션상품 추가
	function optionProductAdd($this){
		var goodsCd = $($this).attr("data-goodsCd");
		
		if($($this).hasClass("active")){				
			$($this).removeClass("active");
			$("#option"+goodsCd).remove();
			totalPrice();
		}else{
			$($this).addClass("active");
			
			$.ajax({
				 url: "${CTX}/ajax/product/optionProductAddAjax.do",
				 data : {
					 			"goodsCd"	: goodsCd
					 		},
				 type: "POST",	
				 async: false,
				 cache: false,
				 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
				 error: function(request, status, error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);	
				 },
				 success: function(responseData){ 
					 $("#optionProduct").append(responseData);	
					 createNumeric(".form-control");
					 totalPrice();
				 }
			}); 
		}
	}
	
	// 옵션상품 제거
	function productDel(goodsCd){
		$("#option"+goodsCd).remove();
		$("#optionImg"+goodsCd).removeClass("active");
		totalPrice();
	}
	
	// 구매 혜택 레이어 
	function benefit(type){
		if(type == 1){
			$("#popCardPlan").removeClass("popup-paybenefit").addClass("popup-cardplan");
			$("#purchaseTitle").text("무이자 할부 안내");
			$("#purchaseDesc2").hide();
			$("#purchaseDesc3").hide();
			$("#purchaseDesc4").hide();
			$("#purchaseDesc1").show();
		}else if(type == 2){
			$("#popCardPlan").removeClass("popup-paybenefit").addClass("popup-cardplan");
			$("#purchaseTitle").text("카드 할인 혜택");
			$("#purchaseDesc1").hide();
			$("#purchaseDesc3").hide();
			$("#purchaseDesc4").hide();
			$("#purchaseDesc2").show();
		}else if(type == 3){
			$("#popCardPlan").removeClass("popup-paybenefit").addClass("popup-getpoint");
			$("#purchaseTitle").text("포인트 적립 안내");
			$("#purchaseDesc1").hide();
			$("#purchaseDesc2").hide();
			$("#purchaseDesc4").hide();
			$("#purchaseDesc3").show();
		}else if(type == 4){
			$("#popCardPlan").removeClass("popup-cardplan").addClass("popup-paybenefit");
			$("#purchaseTitle").text("추가 결제 혜택 안내");
			$("#purchaseDesc1").hide();
			$("#purchaseDesc2").hide();
			$("#purchaseDesc3").hide();
			$("#purchaseDesc4").show();
		}
	}
	
	// 스타일링, 매거진 레이어 Ajax
	function productLayer(){
		$.ajax({
			 url: "${CTX}/ajax/product/productLayerAjax.do",
			 data : {
				 			"goodsIdx"		:	"${detail.goodsIdx}"
				 		},
			 type: "POST",	
			 async: false,
			 cache: false,
			 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 error: function(request, status, error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);	
			 },
			 success: function(responseData){ 
				 $("#productLayer").html(responseData);	
			 }
		});
	}
	
	// 매거진 상세 이동  (레이어)
	function goMagazineDetail(idx){
		$("#magazineIdx").val(idx);
		var frm = document.magazineForm;
		frm.action = "${CTX}/brand/brandMagazineView.do";
		frm.submit();
	}
	
	// 리뷰 작성
	function goReview(){
		if("${IS_LOGIN}" == "false"){
			if(confirm("<spring:message code='common.util011'/>") == true){
				location.href="${CTX}/login/loginPage.do?refererYn=Y"; 
			 }
		}else{
			location.href="${CTX}/mypage/review/noWriteReviewList.do";
		}
	}
	
	// 리뷰 페이징
	var reviewFlag;
	function goPage(pageNumber){
		reviewList(reviewFlag, pageNumber);
	}
	
	// 상품 후기
	function reviewList(type, pageNumber){
		var pageNo;
		if(typeof pageNumber == "undefined" ){
			pageNo = 1;
		}else{
			pageNo = Number(pageNumber);
		}
		
		reviewFlag = type;
		
		$.ajax({
			 url: "${CTX}/ajax/mypage/review/reviewListAjax.do",
			 data : {
				 			"goodsIdx"		:	"${detail.goodsIdx}",
				 			"memberIdx"	:	"${USERINFO.memberIdx}",
				 			"reviewType"	:	reviewFlag,
				 			"pageNo"		:	pageNo
				 		},
			 type: "POST",	
			 async: false,
			 cache: false,
			 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			 error: function(request, status, error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);	
			 },
			 success: function(responseData){ 
				 $("#reviewList").html(responseData);	
			 }
		});  
	}
	
	// 리뷰 삭제
	function goReviewDelete(reviewIdx, reviewPoint){
		if(confirm("정말 삭제하시겠습니까?\n삭제하시면 적립된 포인트는 회수됩니다.")){
			$.ajax({
				 url: "${CTX}/ajax/mypage/review/reviewDelete.do",
				 data : {
					 			"reviewIdx"		:	reviewIdx,
					 			"reviewPoint"	:	reviewPoint
					 		},
				 type: "POST",	
				 async: false,
				 contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
				 error: function(request, status, error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);	
				 },
				 success: function(flag){ 
					 if(Number(flag) > 0){ 
						alert("삭제가 완료 되었습니다.");
					}else{
						alert("삭제 중 오류가 발생 하였습니다.");
					}
					 document.location.reload();
				 }
			});
		}
	}
	
	// SNS 공유
	function snsShare(snsType){
		// N : 네이버, F : 페이스북, K : 카카오스토리, T : 트위터
		var nowPage = document.location.href;
		var title = "${detail.goodsTitle}";
	 	var size = 'width=550 height=550';
		var url = "";
		
		if(snsType == "N"){
			url = 'http://share.naver.com/web/shareView.nhn?url='+encodeURIComponent(nowPage)+'&title='+encodeURIComponent(title);
		}else if(snsType == "F"){
			url = '//www.facebook.com/sharer/sharer.php?u='+nowPage+'&t='+title;
		}else if(snsType == "K"){
			url = 'https://story.kakao.com/share?url='+encodeURIComponent(nowPage);
		}else if(snsType == "T"){
		 	url = 'http://twitter.com/intent/tweet?text=' +encodeURIComponent(title) + '&url=' + nowPage
		}
		window.open(url,'_blank', size);  
	}
	
	// URL 공유
	function urlShare(){
		var nowPage = document.location.href;
		var isIe = !!document.documentMode;
		if(isIe) {
			window.clipboardData.setData('Text', nowPage);
		    alert('클립보드에 복사가 되었습니다. \n\n\'Ctrl+V를 눌러 붙여넣기 해주세요.');
		} else {
			prompt('아래 주소를 복사 해주세요', nowPage);
		}
	}
	
	// 위시리스트 담기
	function goWish($this,goodsIdx){
		addWishList($this, goodsIdx);
	}
	
	// 장바구니 담기
	function goCart(){
		var arr = new Array();
		$("#optionProduct li").each(function (index) {	
			var obj = new Object();
			
			var goodsCd = $(this).attr("data-goodsCd");
			var goodsIdx = $(this).attr("data-goodsIdx");
			var goodsCnt = $("#productCnt"+goodsCd).val();
			
			obj.goodsIdx = goodsIdx;
			obj.goodsCnt = goodsCnt;
			obj.sessionId = getSessionId();
			
			arr.push(obj);
		});
		// 장바구니 담기
		addCartObject(arr);
	}
	
	// 원클릭 결제 버튼
	function btnOneClickPay(){
		if("${IS_LOGIN}" == "false"){
			if(confirm("<spring:message code='common.util011'/>") == true){
				location.href="${CTX}/login/loginPage.do?refererYn=Y"; 
			 }
		}else{
			//대표 빌키가 있을경우
			if("${billkeyYn}" == "Y"){
				$('[data-remodal-id=popOneclick]').remodal().open();
			}
			else {
				$('[data-remodal-id=popAlarm]').remodal().open();
			}
		}
	}
	
	// 윈클릭 결제
	function oneClickPay(){
		if($('#billkeypopup_agree_chk').is(":checked")) {
			var arr = new Array();
			
			$("#optionProduct li").each(function (index) {	
				var obj = new Object();
				
				var goodsCd = $(this).attr("data-goodsCd");
				var goodsIdx = $(this).attr("data-goodsIdx");
				var goodsCnt = $("#productCnt"+goodsCd).val();
				
				obj.goodsIdx = Number(goodsIdx);
				obj.goodsCnt = Number(goodsCnt);
				
				arr.push(obj);
			});
			
			$("#commonOrderGoodsInfoListStr").val(JSON.stringify(arr));
			var frm = document.commonOrderForm;
			frm.action=getContextPath()+"/order/GoodsBillkeyOrder.do";
			frm.submit();
		}else {
			alert("구매진행에 동의 하여야 합니다.");
			$('#billkeypopup_agree_chk').focus()
			return false;
		}
	}
	
	// 바로구매
	function buyNow(){
		var arr = new Array();
		
		$("#optionProduct li").each(function (index) {	
			var obj = new Object();
			
			var goodsCd = $(this).attr("data-goodsCd");
			var goodsIdx = $(this).attr("data-goodsIdx");
			var goodsCnt = $("#productCnt"+goodsCd).val();
			
			obj.goodsIdx = Number(goodsIdx);
			obj.goodsCnt = Number(goodsCnt);
			
			arr.push(obj);
		});
		
		// 바로구매
		orderNow(arr);
	}
	
	// 해시태그검색
	function hashtagSearchClick(c) {
		 var hairstyle1 = "" ;
		 var hairstyle2 = "" ;
		 var hairstyle3 = "" ;
		 var hairstyle4 = "" ;
		 var glossy1 = "";
		 var glossy2 = "";
		 var glossy3 = "";
		 var strong1 = "";
		 var strong2 = "";
		 var strong3 = "";
		 var hashtag = "";

		 hashtag = $(c).attr("data-hashtag");
		 
		 if (hashtag != "") {
			 hashtag = "#"+hashtag;
			 
			$("#searchFrm input:hidden[name='hairstyle10']").val(hairstyle1);
			$("#searchFrm input:hidden[name='hairstyle20']").val(hairstyle2);
			$("#searchFrm input:hidden[name='hairstyle30']").val(hairstyle3);
			$("#searchFrm input:hidden[name='hairstyle40']").val(hairstyle4);
			$("#searchFrm input:hidden[name='strong1']").val(strong1);	
			$("#searchFrm input:hidden[name='strong2']").val(strong2);
			$("#searchFrm input:hidden[name='strong3']").val(strong3);
			$("#searchFrm input:hidden[name='glossy1']").val(glossy1);	
			$("#searchFrm input:hidden[name='glossy2']").val(glossy2);
			$("#searchFrm input:hidden[name='glossy3']").val(glossy3);
			$("#searchFrm input:hidden[name='hashtag']").val("");	
			$("#searchFrm input:hidden[name='keyword']").val(hashtag);
			$("#searchFrm input:hidden[name='pageNo']").val("1");
			$("#searchFrm input:hidden[name='exhPageNo']").val("1");
			
			$("form[name='searchFrm']").attr("action", "${CTX}/etc/searchResult.do");
			$("form[name='searchFrm']").submit();
		 }
	}
	
	// 추가상품 메뉴 토글
	function addMenuBtn(){
		var flag = $("#addMenu").hasClass("active");
		if(flag==false){
			$(".util-box").hide();
		}else{
			$(".util-box").show();
			// css : bottom 70
			$(".util-box").css("bottom", "70px");
		}
	}
	
</script>

<!-- 카카오픽셀 : 상품조회 -->
<script type="text/javascript" charset="UTF-8" src="//t1.daumcdn.net/adfit/static/kp.js"></script>
<script type="text/javascript">
if("<spring:message code='server.status'/>" == "LIVE") {
      kakaoPixel('466785529738862663').viewContent({
        id: '${detail.goodsCd}'
      });
}
</script>

<!-- Doyouad Start 삭제 하지 마세요. -->
<script type="text/javascript">
<c:forEach var="list" items="${productImgList}" varStatus="idx">
	<c:set var="imgSplit" value="${fn:split(list.imgFile ,'.')}"/>
	<c:if test="${idx.index eq 0}">
		var imgBpath = "${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_B.${imgSplit[1]}";
	</c:if>
</c:forEach>

var DOYOUAD_DATA = {};
DOYOUAD_DATA.goods = "${detail.goodsCd}";
DOYOUAD_DATA.price = "${detail.price}";
DOYOUAD_DATA.salePrice = "${detail.discountPrice}";
DOYOUAD_DATA.title = "${detail.goodsTitle}";
DOYOUAD_DATA.image = imgBpath;
DOYOUAD_DATA.category = "${detail.cateNavi}";
DOYOUAD_DATA.soldOut = "2";
(function (w, d, s, n, t) {n = d.createElement(s);n.type = "text/javascript";n.setAttribute("id", "doyouadScript");n.setAttribute("data-user", "mandom");n.setAttribute("data-page", "shop");n.async = !0;n.defer = !0;n.src = "https://cdn.doyouad.com/js/dyadTracker.js?v=" + new Date().toISOString().slice(0, 10).replace(/-/g, "");t = d.getElementsByTagName(s)[0];t.parentNode.insertBefore(n, t);})(window, document, "script");	
</script>
<!-- Doyouad End -->

</head>
<body>

<!-- 페북 카달로그 -->
<script src="//umzzi.com/public/aw.js?69b4383bfbdfbaef1d00fec475f069b2"></script>
<div hidden class="aw-product" style="display:none">
<div data-product-property="id">${detail.goodsCd}</div>
<div data-product-property="title">${detail.goodsTitle}</div>
<div data-product-property="image_link">${IMGPATH}/goods/${detail.goodsIdx}/${detailImgSplit[0]}_B.${detailImgSplit[1]}</div>
<div data-product-property="price">${detail.discountPrice}</div>
<div data-product-property="product_type">${detail.cateNavi}</div>
<div data-product-property="brand">${detail.brandNm}</div>
<div data-product-property="description">${detail.shortInfo}</div>
<c:choose>
	<c:when test="${detail.soldoutYn eq 'N' }">
		<div data-product-property="availability">in stock</div>
	</c:when>
	<c:otherwise>
		<div data-product-property="availability">out of stock</div>
	</c:otherwise>
</c:choose>
</div>

	<form name="searchFrm" id="searchFrm">
		<input type="hidden" name="hairstyle10" id="hairstyle10" value="">
		<input type="hidden" name="hairstyle20" id="hairstyle20" value="">
		<input type="hidden" name="hairstyle30" id="hairstyle30" value="">
		<input type="hidden" name="hairstyle40" id="hairstyle40" value="">
		<input type="hidden" name="strong1" id="strong1" value="">
		<input type="hidden" name="strong2" id="strong2" value="">
		<input type="hidden" name="strong3" id="strong3" value="">
		<input type="hidden" name="glossy1" id="glossy1" value="">
		<input type="hidden" name="glossy2" id="glossy2" value="">
		<input type="hidden" name="glossy3" id="glossy3" value="">
		<input type="hidden" name="hashtag" id="hashtag" value="">
		<input type="hidden" name="keyword" id="keyword" value="">
		<input type="hidden" name="pageNo" id="pageNo" value="1">
		<input type="hidden" name="pageBlock" id="pageBlock" value="4">
	</form>
	<form name="magazineForm" id="magazineForm" method="post" onsubmit="return false;">
		<input type="hidden" name="magazineIdx" id="magazineIdx">
	</form>
	<form onsubmit="return false;">
		<input type="hidden" id="goodCd" value="${detail.goodsCd}" >
	
		<div class="content comm-product">
			<div class="page-body">
				<div class="product-detail">
					<div class="detail-header">
					
						<c:if test="${!empty detail.timesaleIdx}">
							<div class="detail-timer">
								<c:set var="timeSale" value="${fn:split(detail.timeSale ,':')}"/>
								<strong class="detail-timer-title">
									<span>TIME SALE</span>
									<span>
										<i>남</i><i>은</i><i>시</i><i>간</i>
									</span>
								</strong>
								<div class="detail-clock" data-time-hour="${timeSale[0]}" data-time-min="${timeSale[1]}" data-time-sec="${timeSale[2]}"></div>
							</div>
						</c:if>
						
						<div class="detail-top">
							<div class="badge-box">
								<c:if test="${detail.opoYn eq 'Y' }" ><span class="badge type1">1+1</span></c:if>
								<c:if test="${detail.tpoYn eq 'Y' }" ><span class="badge type2">2+1</span></c:if>
								<c:if test="${detail.newYn eq 'Y' }" ><span class="badge type3">NEW</span></c:if>
								<c:if test="${detail.bestYn eq 'Y' }" ><span class="badge type4">BEST</span></c:if>
								<c:if test="${detail.saleiconYn eq 'Y' }" ><span class="badge type5">SALE</span></c:if>
								<c:if test="${detail.pointiconYn eq 'Y' }" ><span class="badge type3">POINT <em>x2</em></span></c:if>
							</div>
							<c:if test="${detail.autoCouponYn eq 'Y'}">
								<img src="${CTX}/images/${DEVICE}/common/ico_coupon_recommend.png" alt="쿠폰">
							</c:if>
							<c:if test="${detail.onlineYn eq 'Y'}">
								<img src="${CTX}/images/${DEVICE}/common/ico_coupon_mobile.png" alt="쿠폰">
							</c:if>
							
							<div class="detail-social">
								<button type="button" id="wishBtn" class="btn_like<c:if test="${detail.isWish eq 'Y'}"> active</c:if>" onclick="goWish(this,'${detail.goodsIdx}');"><span class="hide">좋아요</span></button>
								<div class="social-share">
									<span class="btn_share" data-toggle="collapse" data-target="#popSocailShare" ><span class="hide">공유</span></span>
									<div id="popSocailShare" class="box-share" style="display: none;">
										<strong>공유하기</strong>
										<a href="javascript:" onclick="snsShare('N');"><img src="${CTX}/images/${DEVICE}/common/ico_share_naver.jpg" alt="네이버 공유"></a>
										<a href="javascript:" onclick="snsShare('F');"><img src="${CTX}/images/${DEVICE}/common/ico_share_facebook.jpg" alt="페이스북 공유"></a>
										<a href="javascript:" onclick="snsShare('K');"><img src="${CTX}/images/${DEVICE}/common/ico_share_kakao.jpg" alt="카카오 공유"></a>
										<a href="javascript:" onclick="snsShare('T');"><img src="${CTX}/images/${DEVICE}/common/ico_share_twit.jpg" alt="트위터 공유"></a>
										<a href="javascript:" onclick="urlShare();"><img src="${CTX}/images/${DEVICE}/common/ico_share_url.jpg" alt="URL 공유"></a>
									</div>
								</div>
							</div>
						</div>
					
						<p class="detail-brand">${detail.brandNm}</p>
						<p class="detail-desc">${detail.shortInfo}</p>
						<div class="detail-title">${detail.goodsTitle}</div>

						<div class="detail-price">
							<fmt:formatNumber  var="discountRate" value="${detail.discountRate}" type="number" maxFractionDigits="0"/>
							<c:if test="${discountRate != 0}" >
								<span class="discount">${discountRate}<i>%</i></span>
			             	</c:if>
			             	
			             	<c:if test="${discountRate != 0}" >
								<del class="origin"><fmt:formatNumber value="${detail.price}" /></del>
			             	</c:if>
			             	<span class="price"><fmt:formatNumber value="${detail.discountPrice}" /></span>
						</div>
						
						<div class="detail-screen">
							<ul data-ui="detailImage">
								<c:forEach var="list" items="${productImgList}" varStatus="idx">
									<c:set var="imgSplit" value="${fn:split(list.imgFile ,'.')}"/>
									<li><img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_B.${imgSplit[1]}" ></li>
								</c:forEach>
							</ul>
						</div>

						<!-- 2023.03.07 수정 -->
						<%-- <div class="detail-magazine">
							<ul>
								<li><a href="javascript:" data-toggle="popup" data-target="#popLearnmore" onclick="productLayer();">스타일링 무비 <span class="count"><fmt:formatNumber value="${productSubInfo.styleMovieCnt}" /></span></a></li>
								<li><a href="javascript:" data-toggle="popup" data-target="#popLearnmore" onclick="productLayer();">매거진 <span class="count"><fmt:formatNumber value="${productSubInfo.magazineCnt}" /></span></a></li>
								<li><a href="javascript:" onclick="goHowtouse('${productSubInfo.howtouseIdx}','${detail.goodsIdx}');">HOW TO USE</a></li>
							</ul>
						</div> --%>
						
						<div class="detail-tags">
							<c:if test="${!empty productHashList}" >
								<c:forEach var="list" items="${productHashList}">
									<p class="detail-tag" data-hashtag="${list.hashtagNm}" onclick="hashtagSearchClick(this);"><span>#${list.hashtagNm}</span></p>
								</c:forEach>
							</c:if>
						</div>
						
						<div class="detail-exhibition">
							<c:forEach var="list" items="${productExhibitionList}">
								<a href="${CTX}/event/exhibition/exhibitionView.do?exhibitionIdx=${list.exhibitionIdx}"><span>${list.title}</span></a>
							</c:forEach>
						</div>
						
						<%-- 상품상세 배너 --%>
						<c:if test="${!empty bannerInfo}">
							<c:if test="${bannerInfo.deviceGubun eq 'M' or bannerInfo.deviceGubun eq 'A'}">
								<div class="detail-banner-vote">
									<c:choose>
										<c:when test="${bannerInfo.moLinkYn eq 'N'}">
										<a href="javascript:">
										</c:when>
										<c:when test="${bannerInfo.moLinkYn eq 'Y'}">
										<a href="${bannerInfo.moLinkUrl}" <c:if test="${bannerInfo.moLinkFlag eq '_BLANK'}">target="_BLANK"</c:if>>
										</c:when>				
									</c:choose>
										<img src="${IMGPATH}/banner/${bannerInfo.bannerIdx}/${bannerInfo.moBannerImg}" alt="${bannerInfo.bannerNm}" />
									</a>
								</div>
							</c:if>
						</c:if>
						<%-- //상품상세 배너 --%>
						
					</div>

					<c:if test="${detail.setting ne 0}" >
						<!-- 상품요약 -->
						<div class="detail-stats">
							<div class="detail-box detail-hard">
								<p class="detail-attr">셋팅력</p>
								<div class="detail-val">
									<div class="detail-val-horizon">
										<figure class="circle">
											<figcaption data-step="${detail.setting}"></figcaption>
											<svg>
												<circle class="line" cx="25" cy="25" r="24" transform="rotate(-90, 25, 25)"></circle>
											</svg>
										</figure>
									</div>
								</div>
							</div>
							<div class="detail-box detail-shine">
								<p class="detail-attr">윤기</p>
								<div class="detail-val">
									<div class="detail-val-horizon">
										<figure class="circle">
											<figcaption data-step="${detail.burnish}"></figcaption>
											<svg>
												<circle class="line" cx="25" cy="25" r="24" transform="rotate(-90, 25, 25)"></circle>
											</svg>
										</figure>
									</div>
								</div>
							</div>
							<div class="detail-box detail-hair">
								<p class="detail-attr">머리길이</p>
								<div class="detail-val">
									<div class="detail-val-horizon">
										<c:forEach var="list" items="${hairStyleList}">
											<c:forTokens var="item" items="${detail.hairstyle}" delims=",">
												<c:if test="${list.commonCd eq item}" >
													<strong><c:out value="${list.cdNm}"/></strong>
												</c:if>
											</c:forTokens>
										</c:forEach>
									</div>
								</div>
							</div>
							<div class="detail-box detail-volume">
								<p class="detail-attr">상품용량</p>
								<div class="detail-val">
									<div class="detail-val-horizon">
										<strong>${detail.goodsUnit}</strong>
									</div>
								</div>
							</div>
						</div>
						<!-- //상품요약 -->
					
					</c:if>

					<!-- 2023.04.10 임시주석 처리 -->
					<!-- s: 결제관련 옵션 -->
					<!-- <div class="detail-payinfo">
						<dl>
                     		<dt>배송비</dt>
	                     	<dd>2,500 (3만원 이상 구매시 무료)</dd>
	                     	<c:if test="${!empty detail.couponNm}">
                     			<dt>할인 혜택</dt>
                     			<c:choose>
                     				<c:when test="${IS_LOGIN}">
				                     	<dd>${detail.couponNm}<span class="em"><fmt:formatNumber value="${detail.couponDiscountPrice}" /></span></dd>
                     				</c:when>
                     				<c:otherwise>
										<dd>${detail.couponNm}<span class="em"></span></dd>	                     					
                     				</c:otherwise>
                     			</c:choose>
	                     	</c:if>
	                     	<dt>구매 혜택</dt>
	                     	<dd>
	                     		<c:if test="${!empty addBenefit.mHtmlinfoDesc}">
		                     		<a href="javascript:" class="payinfo-link" data-toggle="popup" data-target="#popCardPlan" onclick="benefit(4);">추가 결제 혜택 안내</a>
	                     		</c:if>
                         		<a href="javascript:" class="payinfo-link" data-toggle="popup" data-target="#popCardPlan" onclick="benefit(1);">무이자 할부 안내</a>
	                         	<!-- <a href="javascript:" class="payinfo-link" data-toggle="popup" data-target="#popCardPlan" onclick="benefit(2);">카드 할인 혜택</a> --
	                         	<a href="javascript:" class="payinfo-link" data-toggle="popup" data-target="#popCardPlan" onclick="benefit(3);">포인트 적립 안내</a>
	                         	<a href="javascript:" class="payinfo-link nolink" data-toggle="popup" data-target="#">모든 구매 고객 및 금액대별 사은품 증정</a>
	                     	</dd>
	                 	</dl>
	                 
	                 	
					</div> -->
					<!-- e: 결제관련 옵션 -->
					
					<!-- s: 추천상품 -->
					<div class="product-showcase">
						<div class="ui-recmgoods">
							<ul data-ui="recmProducts">
								<li>
									<c:forEach var="list" items="${categoryBestProduct }" begin="0" end="0">
										<a href="${CTX}/product/productView.do?goodsCd=${list.goodsCd}">
											<div class="title">
												<img src="${CTX}/images/m/contents/tit_recm_best.png" alt="">
												<p>카테고리 인기상품</p>
											</div>
											<div class="item">
	                                       		<div class="item-thumb">
	                                       			<c:set var="imgSplit" value="${fn:split(list.mainFile ,'.')}"/>
	                                       			<img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}">
	                                         	</div>
	                                         	<div class="item-price">
	                                             	<span class="price"><fmt:formatNumber value="${list.discountPrice}" /></span>
	                                         	</div>
	                                         	<div class="item-name">${list.goodsTitle}</div>
	                                     	</div>
										</a>
									</c:forEach>
								</li>    
								<li>
									<c:choose>
                             			<c:when test="${!empty wishProduct}">
		                                	<a href="${CTX}/product/productView.do?goodsCd=${wishProduct.goodsCd}">
		                                    	<div class="title">
		                                       		<img src="${CTX}/images/${DEVICE}/contents/tit_with.png" alt="">
		                                         	<p>같이쓰면 좋은 상품</p>
		                                     	</div>
		                                     	<div class="item">
		                                       		<div class="item-thumb">
		                                       			<c:set var="imgSplit" value="${fn:split(wishProduct.mainFile ,'.')}"/>
		                                            	<img src="${IMGPATH}/goods/${wishProduct.goodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}">
		                                         	</div>
		                                         	<div class="item-price">
		                                             	<span class="price"><fmt:formatNumber value="${wishProduct.discountPrice}" /></span>
		                                         	</div>
		                                         	<div class="item-name">${wishProduct.goodsTitle}</div>
		                                     	</div>
		                                 	</a>
                             			</c:when>
                             			<c:otherwise>
                             				<c:forEach var="list" items="${categoryBestProduct }" begin="1" end="1">
	                             				<a href="${CTX}/product/productView.do?goodsCd=${list.goodsCd}">
			                                     	<div class="item">
			                                       		<div class="item-thumb">
			                                       			<c:set var="imgSplit" value="${fn:split(list.mainFile ,'.')}"/>
			                                            	<img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}">
			                                         	</div>
			                                         	<div class="item-price">
			                                             	<span class="price"><fmt:formatNumber value="${list.discountPrice}" /></span>
			                                         	</div>
			                                         	<div class="item-name">${list.goodsTitle}</div>
			                                     	</div>
			                                 	</a>
                             				</c:forEach>
                             			</c:otherwise>
                             		</c:choose>
								</li>   
								<li>
									<c:forEach var="list" items="${recommendProduct }" begin="0" end="0">
	                                	<a href="${CTX}/product/productView.do?goodsCd=${list.goodsCd}">
	                                    	<div class="title">
	                                       		<img src="${CTX}/images/${DEVICE}/contents/tit_recm_recommend.png" alt="">
                                         	 	<c:if test="${detail.brandIdx eq '1' }"><c:set var="brandNm" value="갸스비"/></c:if>
												<c:if test="${detail.brandIdx eq '3' }"><c:set var="brandNm" value="비페스타"/></c:if>
												<c:if test="${detail.brandIdx eq '4' }"><c:set var="brandNm" value="루시도엘"/></c:if>
	                                         	<p>${brandNm} 추천 상품</p>
	                                     	</div>
	                                     	<div class="item">
	                                       		<div class="item-thumb">
	                                       			<c:set var="imgSplit" value="${fn:split(list.mainFile ,'.')}"/>
	                                       			<img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}">
	                                         	</div>
	                                         	<div class="item-price">
	                                             	<span class="price"><fmt:formatNumber value="${list.discountPrice}" /></span>
	                                         	</div>
	                                         	<div class="item-name">${list.goodsTitle}</div>
	                                     	</div>
	                                 	</a>
                             		</c:forEach>
								</li>    
								<li>
									<c:choose>
										<c:when test="${!empty setProduct}">
		                                	<a href="${CTX}/product/productView.do?goodsCd=${setProduct.goodsCd}">
		                                    	<div class="title">
		                                       		<img src="${CTX}/images/${DEVICE}/contents/tit_recm_set.png" alt="">
		                                         	<p>이 상품이 포함된 세트 상품</p>
		                                     	</div>
		                                     	<div class="item">
		                                       		<div class="item-thumb">
		                                       			<c:set var="imgSplit" value="${fn:split(setProduct.mainFile ,'.')}"/>
		                                       			<img src="${IMGPATH}/goods/${setProduct.goodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}">
		                                         	</div>
		                                         	<div class="item-price">
		                                             	<span class="price"><fmt:formatNumber value="${setProduct.discountPrice}" /></span>
		                                         	</div>
		                                         	<div class="item-name">${setProduct.goodsTitle}</div>
		                                     	</div>
		                                 	</a>
										</c:when>
										<c:otherwise>
											<c:forEach var="list" items="${recommendProduct }" begin="1" end="1">
												<a href="${CTX}/product/productView.do?goodsCd=${list.goodsCd}">
			                                     	<div class="item">
			                                       		<div class="item-thumb">
			                                       			<c:set var="imgSplit" value="${fn:split(list.mainFile ,'.')}"/>
			                                            	<img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}">
			                                         	</div>
			                                         	<div class="item-price">
			                                             	<span class="price"><fmt:formatNumber value="${list.discountPrice}" /></span>
			                                         	</div>
			                                         	<div class="item-name">${list.goodsTitle}</div>
			                                     	</div>
			                                 	</a>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</li>  
							</ul>
							<a class="list-prev"></a>
							<a class="list-next"></a>
						</div>
					</div>
					<!-- e: 추천상품 -->
				</div>
<a name="reviewTab"></a>
				<div class="product-content">
					<div class="tab-menu">
						<div class="menu-inner">
							<ul class="menu-list">
	                     		<li><a href="#tabDetail" class="menu-link active" id="detailTab"><span>상품정보</span></a></li>
		                     	<li><a href="#tabReview" class="menu-link" onclick="reviewList('ALL');" id="reviewTab"><span>상품후기 (<fmt:formatNumber value="${productSubInfo.reviewCnt}" />)</span></a></li>
		                     	<li><a href="#tabShipping" class="menu-link"><span>배송/교환/반품</span></a></li>
		                     	<li><a href="#tabInfo" class="menu-link"><span>상품정보고시</span></a></li>
		                 	</ul>
						</div>
					</div>

					<!-- TAB - 상품정보 -->
					<div id="tabDetail" class="tab-panel tab-detail" style="display:block">
	             		<div class="image-container" style="height:500px">
	             			<br>
			             	<!-- 상품정보 TOP -->
							<div>
								<c:if test="${detail.brandIdx eq '1' }">
									<c:out value="${productTop.mHtmlinfoDesc}" escapeXml="false"/>
								</c:if>
								<c:if test="${detail.brandIdx eq '3' }">
									<c:out value="${befestaTop.mHtmlinfoDesc}" escapeXml="false"/>
								</c:if>
								<c:if test="${detail.brandIdx eq '4' }">
									<c:out value="${lucidolTop.mHtmlinfoDesc}" escapeXml="false"/>
								</c:if>
								<c:if test="${detail.brandIdx eq '6' }">
									<c:out value="${mamaTop.mHtmlinfoDesc}" escapeXml="false"/>
								</c:if>
								<c:if test="${detail.brandIdx eq '7' }">
									<c:out value="${dentalTop.mHtmlinfoDesc}" escapeXml="false"/>
								</c:if>
								<c:if test="${detail.brandIdx eq '8' }">
									<c:out value="${charleyTop.mHtmlinfoDesc}" escapeXml="false"/>
								</c:if>
								<c:if test="${detail.brandIdx eq '9' }">
									<c:out value="${barrierTop.mHtmlinfoDesc}" escapeXml="false"/>
								</c:if>
								<c:if test="${detail.brandIdx eq '10' }">
									<c:out value="${gpTop.mHtmlinfoDesc}" escapeXml="false"/>
								</c:if>
							</div>
							<!-- 상품정보 내용-->
							<div>
								 <c:out value="${detail.mGoodsDesc}" escapeXml="false"/>
							</div>
							<!-- 상품정보 BOTTOM -->
							<div>
								<c:if test="${detail.brandIdx eq '1' }">
									<c:out value="${productBottom.mHtmlinfoDesc}" escapeXml="false"/>
								</c:if>
								<c:if test="${detail.brandIdx eq '3' }">
									<c:out value="${befestaBottom.mHtmlinfoDesc}" escapeXml="false"/>
								</c:if>
								<c:if test="${detail.brandIdx eq '4' }">
									<c:out value="${lucidolBottom.mHtmlinfoDesc}" escapeXml="false"/>
								</c:if>
								<c:if test="${detail.brandIdx eq '6' }">
									<c:out value="${mamaBottom.mHtmlinfoDesc}" escapeXml="false"/>
								</c:if>
								<c:if test="${detail.brandIdx eq '7' }">
									<c:out value="${dentalBottom.mHtmlinfoDesc}" escapeXml="false"/>
								</c:if>
								<c:if test="${detail.brandIdx eq '8' }">
									<c:out value="${charleyBottom.mHtmlinfoDesc}" escapeXml="false"/>
								</c:if>
								<c:if test="${detail.brandIdx eq '9' }">
									<c:out value="${barrierBottom.mHtmlinfoDesc}" escapeXml="false"/>
								</c:if>
								<c:if test="${detail.brandIdx eq '10' }">
									<c:out value="${gpBottom.mHtmlinfoDesc}" escapeXml="false"/>
								</c:if>
							</div>
		             	</div>
		             	<a href="#none" class="btn-more"><span class="hide">상품정보 더보기</span></a>
		         	</div>
					<!-- //TAB - 상품정보 -->
					<!-- TAB - 상품후기 -->
					<div id="tabReview" class="tab-panel tab-review">
		         		<div id="reviewList">
		         		</div>
	         		</div>
					<!-- //TAB - 상품후기 -->

					<!-- TAB - 배송/교환/반품 -->
		          	<div id="tabShipping" class="tab-panel tab-shipping">
	             		<c:out  value="${productTab3.mHtmlinfoDesc}" escapeXml="false"/>
		         	</div>

					<!-- TAB - 상품정보고시 -->
					<div id="tabInfo" class="tab-panel tab-info">
	             		<table class="table type-info">
	                 		<caption>상품정보고시 테이블</caption>
	                 		<colgroup>
	                     		<col style="width: 28%;">
	                     		<col style="width: 72%;">
			                 </colgroup>
		                 	<tbody>
	                     		<tr>
	                         		<th><p>상품코드</p></th>
		                         	<td>${detail.goodsCd}</td>
		                     	</tr>
		                     	<tr><%-- 버튼 추가 -> 레이어로 띄움 --%>
	                         		<th><p>전성분</p></th>
	                        	 	<td><button class="btn" data-remodal-target="ingredientPop"><span class="txt">전성분 상세보기</span></button></td>
		                     	</tr>
		                     	<tr>
	                         		<th><p>고객상담번호</p></th>
	                         		<td>${detail.customerTel}</td>
		                     	</tr>
		                     	<tr>
	                         		<th><p>제조국</p></th>
		                         	<td>${detail.makeCountry}</td> 
		                     	</tr>
		                     	<tr>
	                        		<th><p>제조업자</p></th>
		                         	<td>${detail.manufacturer}</td>
		                     	</tr>
		                     	<tr>
	                         		<th><p>제조판매업자</p></th>
		                         	<td>${detail.dealer}</td>
		                     	</tr>
		                     	<tr>
		                         	<th><p>용량</p></th>
		                         	<td>${detail.volume}</td>  
		                     	</tr>
		                     	<tr>
		                         	<th><p>품질보증기준</p></th>
		                         	<td>${detail.warrantyPeriod}</td>  
		                     	</tr>
		                     	<tr>
		                         	<th><p>사용 기간</p></th>
		                         	<td>${detail.limitUse}</td>  
		                     	</tr>
		                     	<tr>
		                         	<th><p>사용 방법</p></th>
		                         	<td>${detail.howUse}</td>  
		                     	</tr>
		                     	<tr>
		                         	<th><p>주요 사양</p></th>
		                         	<td>${detail.mainInfo}</td>  
		                     	</tr>
		                     	<tr>
		                         	<th><p>기능성 화장품<br/>심사필 여부</p></th>
		                         	<td>${detail.judgeUse}</td>  
		                     	</tr>
		                     	<tr>
		                         	<th><p>사용시 주의사항</p></th>
		                         	<td>${fn:replace(detail.precautions,crcn,br)}</td>
		                     	</tr>
	                 		</tbody>
	             		</table>
	         		</div>
					<!-- //TAB - 상품정보고시 -->
				</div>
			</div>

			<!-- 추가상품 -->
			<div id="productExtra" class="product-extra">
	        	<div class="extra-toggle">
	            	<button type="button" class="btn-toggle" id="addMenu" onclick="addMenuBtn();"><i></i><span class="hide">추가메뉴 <span class="txt">펼치기</span></span></button>
	         	</div>
	         	<div class="extra-product">
	       			<div class="extra-container">
	       				<div class="extra-list">
	                  		<div class="order-goods type-extra">
	                   			<ul id="optionProduct">
	                          		<li class="optionProduct" data-goodsIdx="${detail.goodsIdx}" data-goodsCd="${detail.goodsCd}">
	                              		<div class="item">
	                                  		<div class="item-view">
	                                   			<div class="view-thumb">
	                                  				<c:set var="imgSplit" value="${fn:split(detail.mainFile ,'.')}"/>
													<img src="${IMGPATH}/goods/${detail.goodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}">
	                                      		</div>
	                                   			<div class="view-info">
		                                  			<c:set var="buyMinCnt" value="${detail.buyMinCnt}" />
													<c:set var="discountPrice" value="${detail.discountPrice}" />
													<c:set var="couponDiscountPrice" value="${detail.couponDiscountPrice}" />
													<c:choose>
														<c:when test="${buyMinCnt eq 0}">
															<c:set var="productCnt" value="1" />
															<c:set var="productPrice" value="${productCnt * discountPrice}" />
															<c:set var="couponDiscountPrice" value="${productCnt * couponDiscountPrice}" />
														</c:when>
														<c:when test="${buyMinCnt > 0}">
															<c:set var="productCnt" value="${buyMinCnt}" />
															<c:set var="productPrice" value="${buyMinCnt * discountPrice}" />
															<c:set var="couponDiscountPrice" value="${buyMinCnt * couponDiscountPrice}" />
														</c:when>
													</c:choose>
	                                         		<p class="name">${detail.goodsTitle}</p>
		                                          	<div class="mod_qty">
		                                          		<span class="spinner">
		                                               		<input type="text" id="productCnt${detail.goodsCd}" class="productCnt" value="${productCnt}" onblur="changeCnt(this,'0');" maxlength="4"/>
		                                                  	<button type="button" class="btn-minus" onclick="changeBtnCnt('M','0');">-</button>
		                                                  	<button type="button" class="btn-plus" onclick="changeBtnCnt('P','0');">+</button>
		                                                  	<input type="hidden" id="hiddenCnt${detail.goodsCd}" value="" >
														  	<input type="hidden" id="maxDiscountFlag${detail.goodsCd}" value="Y" >
														  	<input type="hidden" id="couponPrice${detail.goodsCd}" value="${couponDiscountPrice}" >
		                                              	</span>
		                                              	<span class="price" id="productPrice${detail.goodsCd}"><fmt:formatNumber value="${productPrice}"/></span>
		                                       		</div>
	                                      		</div>
	                                  		</div>
	                              		</div>
	                          		</li>
	                      		</ul>
	                  		</div>
	              		</div>
						<c:if test="${!empty optionProductList}" >
		            		<div class="extra-items">
		                		<p>이미지를 클릭하시면 상품이 추가됩니다.</p>
	                			<div id="extraItems" class="item-wrap">
		                  			<div class="item-inner">
		                  				<ul class="item-list">
		                  					<c:forEach var="list" items="${optionProductList}">
	               								<c:set var="imgSplit" value="${fn:split(list.mainFile ,'.')}"/>
						                        <li>
						                        	<a href="javascript:" id="optionImg${list.goodsCd}" onclick="optionProductAdd(this);" data-goodsCd="${list.goodsCd}">
						                        		<img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}">
						                        	</a>
						                        </li>
		                  					</c:forEach>
					                  	</ul>
		                  			</div>
		                  		</div>
		           			</div>
						</c:if>
		         	</div>
		
					<div class="extra-result">
						<c:if test="${IS_LOGIN and !empty detail.couponNm}">
							<dl>	
			                	<dt>할인 금액</dt>
			                    <dd><span class="em" id="totalCouponDiscountPrice"><fmt:formatNumber value="-${detail.couponDiscountPrice * productCnt}" /></span></dd>
			                </dl>
						</c:if>
	                	<dl class="total">
		                	<dt>결제 예상 금액</dt>
		                    <dd id="totalPrice"><fmt:formatNumber value="${productPrice-(detail.couponDiscountPrice * productCnt)}" /></dd>
		                </dl>
					</div>
				</div>
				
					
						
						<div class="extra-npay2" style="display:none; background-color:#ffffff;padding-bottom: 15px; ">
								<script type="text/javascript" src="https://pay.naver.com/customer/js/mobile/naverPayButton.js" charset="UTF-8"></script>
								<script type="text/javascript" >
									// 오늘 하루 안 보기
								 	function closePop(){
										setCookie("npayPop", "Y", 1);
									}
									
								 	<c:choose>
									<c:when test="${detail.soldoutYn eq 'N' }">
								 	function npayClick(){
								 		var cookie = getCookie("npayPop");
								 			
							 			//if(cookie=="Y"){
							 				buy_nc();
							 			//}else{
							 			//	noticePopup("popNotice20190827");
							 			//}
							 		}
									
									function buy_nc() {     
											var arr = new Array();
											var countchk1 = 0;
											var countchk2 = 0;
											
											$("#optionProduct li").each(function (index) {	
												var obj = new Object();
												
												var goodsCd = $(this).attr("data-goodsCd");
												var goodsIdx = $(this).attr("data-goodsIdx");
												var goodsCnt = $("#productCnt"+goodsCd).val();
												if (goodsCnt == 0 || goodsCnt < 0 ||goodsCnt == "") {
													countchk1++;
													return false;
												}

												if (goodsCnt > 999) {
													countchk2++;
													return false;
												}
												obj.goodsIdx = Number(goodsIdx);
												obj.goodsCnt = Number(goodsCnt);
												
												arr.push(obj);
											});
									
											if (countchk1 > 0) {
												alert("주문수량을 입력해 주세요.");
												return false;
											}
											if (countchk2 > 0) {
												alert("주문수량을 999이하로 입력해 주세요.");
												return false;
											}
											
											$.ajax({			
												url: getContextPath()+"/ajax/order/nPayOrderRegister.do",
											 	data: JSON.stringify(arr),
											 	type: "post",
											 	async: false,
											 	cache: false,
										        dataType : 'json',
										        contentType: "application/json", 
												 error: function(request, status, error){
													alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);	
												 },
												 success: function(data){ 
													if (data.result == false) {
														alert(data.msg);
													}
													else {
														$("[data-remodal-id=popNotice20190827]").remodal().close();
														location.href=data.orderUrl;  //모바일일경우 현재창분기
 													}
												 }
											}); 
											
									 }
									</c:when>
									<c:otherwise>
										function npayClick(){
											alert('일시 품절 상품은 주문할 수 없습니다.');
										}
										function buy_nc() {	
											alert('일시 품절 상품은 주문할 수 없습니다.');
										}
									</c:otherwise>
								</c:choose>
									  function nhn_wishlist_nc( url ) {
											var goodsIdx ="${detail.goodsIdx}";	
											location.href=url+"?goodsIdx="+goodsIdx; //모바일일경우 현재창분기
									        return false;
									    }
									 
									  //<![CDATA[     
									 	            naver.NaverPayButton.apply({
									 	            	BUTTON_KEY: "1001E478-1B94-47E0-9539-6B43E8163A78", // 페이에서 제공받은 버튼 인증 키 입력         
									 	           	 TYPE: "MA", // 버튼 모음 종류 설정         
									 	           	 COLOR: 1, // 버튼 모음의 색 설정           
									 	           	 COUNT: 2, // 버튼 개수 설정. 구매하기 버튼만 있으면 1, 찜하기 버튼도 있으면 2를 입력.          
									 	<c:choose>
									 		<c:when test="${detail.soldoutYn eq 'N' }">
									 	            	 ENABLE: "Y" ,                                       // 품절 등의 이유로 버튼 모음을 비활성화할 때에는 "N" 입력         "":""
									 		</c:when>
									 		<c:otherwise>
									 					 ENABLE: "N" ,                                       // 품절 등의 이유로 버튼 모음을 비활성화할 때에는 "N" 입력         "":""
									 		</c:otherwise>
									 	</c:choose>
									 	
									 	           	 BUY_BUTTON_HANDLER: npayClick,
									 	          // 	  WISHLIST_BUTTON_HANDLER:wishlist_nc
									 	                WISHLIST_BUTTON_HANDLER:nhn_wishlist_nc, // 찜하기 버튼 이벤트 Handler 함수 등록
									 	                WISHLIST_BUTTON_LINK_URL:getContextPath()+"/order/nPayWishRegister.do" // 찜하기 팝업 링크 주소
									 	
									 	           }); 
									 //]]>
								 
								 </script> 
								                 
							<!-- 수정: 2019-04-01, NPAY 공지추가 -->
							<p class="notice-npay"><img src="${CTX}/images/m/contents/img_npay_notice.png" alt="" /></p>
							<!-- //수정: 2019-04-01 -->
							</div>

				
				<div class="extra-btns extra-btns--old"> <!-- extra-btns--old -->
				
						<div class="btns-row" data-group="">  <!-- data-group="open" -->
							<!-- 2023.03.20 수정 -->
							<!-- <button type="button" id="btnNpay" class="btn-npay"><span class="hide">npay</span></button> -->
							<c:choose>
								<c:when test="${detail.soldoutYn eq 'N' }">
									<button type="button" class="btn-buy" onclick="buyNow();"><span class="hide">바로구매</span></button>
								</c:when>
								<c:otherwise>
									<p class="btn-soldout"><span class="hide">품절</span></p>
								</c:otherwise>
							</c:choose>
							<button type="button" class="btn-cart" onclick="goCart();" id='cartBtn'><span class="hide">장바구니</span></button>
						</div>
					</div>
				
				<!-- 팝업 - 20190827 -->
				<div class="popup type-notice" data-remodal-mode="multiple" data-remodal-id="popNotice20190827">
				    <div class="pop-mid">
				        <style>
				            .remodal-overlay.type-notice {z-index: 2000; background: rgba(0, 0, 0, 0.6)}
				            .remodal-wrapper.type-notice {padding: 0 10%; z-index: 2000; overflow: visible}
				            .popup.type-notice {overflow: hidden; background: transparent; box-shadow: none; overflow: visible}
				            .popup.type-notice .pop-close {position: absolute; right: 0; top: -25px; padding: 0; background: none}
				            .popup.type-notice .pop-close .btn-close {float: right; line-height: 22px; padding: 0 0 0 25px; text-align: center; font-weight: normal; font-size: 15px; color: #fff; background: url('${CTX}/images/${DEVICE}/popup/notice_close_20190827.png') left center no-repeat; background-size: 21px 21px;}
				            .notice20190827 {position: relative;}
				            .notice20190827 a {position: absolute; left: 50%; width: 72%; height: 10.3%; transform: translateX(-50%); }
				        </style>
				        <div class="notice20190827">
				            <img src="${CTX}/images/${DEVICE}/popup/notice_20190827.png" alt="" />
				            
				            <a href="javascript:void(0);" style="top: 69.6%;" onclick="buyNow();"><span class="hide">혜택받고 공식몰에서 구매하기</span></a>
				            <a href="javascript:void(0);" style="top: 82.8%;" onclick="buy_nc();"><span class="hide">혜택 안 받고 NPay 로 구매하기</span></a>
				        </div>
				    </div>
				    <div class="pop-close">
				        <a href="javascript:void(0);" class="btn-close" data-remodal-action="close" onclick="closePop();">오늘 하루 보지 않기</a>
				    </div>
				</div>
				<!-- //팝업 - 20190827 -->
				
				
	   		</div>
			<!-- //추가상품 -->
				
		</div>
	                 	
	</form>    
	 
	<!-- 레이어팝업 -->
	<div class="remodal popup popup-oneclick" data-remodal-id="popOneclick">
   		<div class="pop-top">
       		<h2>원클릭 결제 이용 안내</h2>
   		</div>
   		<div class="pop-mid">
       		<ul>
           		<li>- 원클릭 결제는 원클릭 결제카드와 기본 배송지가 지정 되어야 이용이 가능합니다.</li>
	           	<li>- 원클릭 결제는 일시불만 가능합니다.</li>
	       	</ul>  
       		<div class="agree">
          		<span class="checkbox">
               		<input type="checkbox" name="billkeypopup_agree_chk" id="billkeypopup_agree_chk" class="check" />
               			<label for="billkeypopup_agree_chk" class="lbl">
                   			주문상품의 상품명, 상품가격, 배송정보를 확인하였
	                   		으며, 구매진행에 동의하시겠습니까?<br/>
	                   		(전자상거래법 제 8조 제 2항)
	               		</label>
           		</span>
       		</div>
	       	<p>원클릭 결제를 이용하시겠습니까?</p>
	       	<div class="btn-box confirm">
           		<button type="button" class="btn" data-remodal-action="cancel"><span class="txt">취소</span></button>
	           	<button type="button" class="btn black" onclick="oneClickPay();"><span class="txt">결제</span></button>
	       	</div>
	   	</div>
    	<a href="#none" class="btn_close" data-remodal-action="close"><span class="hide">닫기</span></a>
	</div>
	
	<div class="remodal popup" data-remodal-id="popAlarm">
    	<div class="pop-top">
        	<h2>알림</h2>
    	</div>
    	<div class="pop-mid">
        	<div class="message">
	            <p>원클릭 결제 카드가 등록되어있지 않습니다.<br>마이페이지 > 원클릭 결제 카드 관리 페이지로 이동하시겠습니까?</p>
	        </div>
	        <div class="btn-box confirm">
	            <button type="button" class="btn" data-remodal-action="cancel"><span class="txt">취소</span></button>
	            <button type="button" class="btn black" onclick="location.href='${CTX}/mypage/member/billkeyList.do'"><span class="txt">확인</span></button>
	        </div>
    	</div>
    	<a href="#none" class="btn_close" data-remodal-action="close"><span class="hide">닫기</span></a>
	</div>
	
	<div class="remodal popup" data-remodal-id="popGoodsInfo">
    	<div class="pop-top">
	        <h2>전성분</h2>
	    </div>
	    <div class="pop-mid">
	        <div class="message">
	            정제수, 에탄올, 피이지-20글리세릴이소스테아레이트, 피이지
	            -20, 부틸렌글라이콜, 실리카, 소듐마그네슘플루오로실리케이트, 
	            잔탄검, 향료, 시트릭애씨드, 소듐디라우라미도글루타마이드
	            라이신, 청색1호, 황색203호, 리모넨, 리날룰, 알파-이소메칠
	            이오논, 부틸페닐메칠프로피오날, 헥실신남알, 벤질벤조에이트, 
	            시트로넬올
	        </div>
	    </div>
	    <a href="#none" class="btn_close" data-remodal-action="close" ><span class="hide">닫기</span></a>
	</div>
	
	<!-- 무이자 할부 안내 레이어 -->
	<div id="popCardPlan" class="popup type-page popup-cardplan">
    	<h1 class="hide">GATSBY</h1>
		<div class="pop-top">
			<h2 id="purchaseTitle"></h2>
		</div>
		<div class="pop-mid">
			<div id="purchaseDesc1" style="display:none;">
				<c:out value="${cardPlan.mHtmlinfoDesc}" escapeXml="false"/>
			</div>
			<div id="purchaseDesc2" style="display:none;">
				<c:out value="${cardBenefit.mHtmlinfoDesc}" escapeXml="false"/>
			</div>
			<div id="purchaseDesc3"style="display:none;">
				<c:out value="${pointInfo.mHtmlinfoDesc}" escapeXml="false"/>
			</div>
			<div id="purchaseDesc4"style="display:none;">
				<c:out value="${addBenefit.mHtmlinfoDesc}" escapeXml="false"/>
			</div>
	        <div class="btn-box confirm">
	            <button type="button" class="btn black" data-dismiss="popup"><span class="txt">확인</span></button>
	        </div>
		</div>
	    <a href="javascript:" class="btn_close" data-dismiss="popup"><span class="hide">닫기</span></a>
	</div>

	<!-- 스타일링 무비, 매거진 팝업 -->
	<div id="popLearnmore" class="popup type-page popup-learnmore">
	    <h1 class="hide">GATSBY</h1>
		<div class="pop-top">
			<h2>LEARN MORE<br/>ABOUT THIS PRODUCT</h2>
		</div>
		<div class="pop-mid">
			<div id="productLayer">
			
			</div>	        
			<div class="btn-box confirm">
			    <button type="button" class="btn black" data-dismiss="popup"><span class="txt">확인</span></button>
			</div>
		</div>
	    <a href="javascript:" class="btn_close" data-dismiss="popup"><span class="hide">닫기</span></a>
	</div>
	
	<!-- 전성분 상세보기 -->
	<div class="remodal popup" data-remodal-id="ingredientPop">
		<div class="pop-top">
			<h2>전성분 상세보기</h2>
		</div>
	
		<div class="pop-mid">
			<div class="pop-alert">
				<p>${detail.ingredient}</p>
			</div>
		</div>
	
		<a href="javascript:" class="btn_close" data-remodal-action="close"><span class="hide">닫기</span></a>
	</div>
	<!-- //전성분 상세보기 -->
	
	<c:set var="status"><spring:message code="server.status" /></c:set>
	<c:if test="${status eq 'LIVE'}" >
		<div id="wp_tg_cts" style="display:none;"></div>
		<script type="text/javascript">
			var wptg_tagscript_vars = wptg_tagscript_vars || [];
			wptg_tagscript_vars.push(
			(function() {
				return {
					wp_hcuid:"",  	/*Cross device targeting을 원하는 광고주는 로그인한 사용자의 Unique ID (ex. 로그인 ID, 고객넘버 등)를 암호화하여 대입.
							 *주의: 로그인 하지 않은 사용자는 어떠한 값도 대입하지 않습니다.*/
					ti:"37336",
					ty:"Item",
					device:"mobile"
					,items:[{i:"${detail.goodsCd}",	t:"${detail.goodsTitle}"}] /* i:상품 식별번호 (Feed로 제공되는 식별번호와 일치하여야 합니다.) t:상품명 */
					};
			}));
		</script>
	</c:if>
</body>
</html>