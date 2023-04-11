<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<script>
	dataLayer = [];
</script>
<meta name="decorator" content="mobile.main"/>
<script src="${CTX}/js/${DEVICE}/masonry.pkgd.min.js"></script>

	<!-- 전환페이지 설정 -->
	<script type="text/javascript"> 
		if("<spring:message code='server.status'/>" == "LIVE"){
			var _nasa={};
			_nasa["cnv"] = wcs.cnv("3","1"); // 네이버 광고 스크립트 // 전환유형, 전환가치 설정해야함. 설치매뉴얼 참고
		}
	</script> 

<script type="text/javascript">

	<%-- 구매 버튼 연타 방지 --%>
	var didSubmit = false;
	
	window.onpageshow = function (event) {
	//	didSubmit = false;
	}
	
	<%-- 구매 버튼 연타 방지 끝 --%>

	<%-- 배송비 금액 구간 배열에 넣어 놓음 --%>
	var arrShippingPrice = new Array();
	<c:if test="${shippingPriceList ne null}">
		var arrIdx = 0;
		<c:forEach var="list" items="${shippingPriceList}" varStatus="idx">
			var sPrice = [${list.minPrice},${list.maxPrice},${list.shippingPrice}];
			arrShippingPrice[arrIdx] = sPrice; 
			++arrIdx;
		</c:forEach>
	</c:if>

	$(document).ready(function(){
		//탭
		$('.tab-menu-line').tabControl();

		<%-- 숫자만 입력 --%>
		createNumeric(".goodsCnt");
		
		$("#chkCartAll").trigger("click");
		
		doyouAd(); // 금액 계산 완료 후 doyouAd 스크립트 실행
		mobon(); // 금액 계산 완료 후 mobon 스크립트 실행
	});

	<%-- 사은품 탭 --%>
	function changeGiftTab($this, val){
		$(".menu-link").removeClass("active");
		$($this).addClass("active");
		
		$(".gift-list").hide();
		$("#"+val).show();
	}
	
	<%-- 체크박스 전체 선택/해제 --%>
	$(document).on("click","#chkCartAll",function(){
		$("input:checkbox[name='cartIdx']:not([disabled])").prop("checked",$(this).is(":checked"));
		
		calcPrice();	<%-- 금액 재계산 --%>
	});
	
	<%-- 전체 체크 박스 상태 적용 --%>
	$(document).on("click","input:checkbox[name='cartIdx']",function(){
		if($("input:checkbox[name='cartIdx']:not([disabled])").length > $("input:checkbox[name='cartIdx']:not([disabled]):checked").length){
			$("#chkCartAll").prop("checked",false);
		}else{
			$("#chkCartAll").prop("checked",true);
		}
	});
	
	<%-- 환불차감금액 exit 처리 --%>
	function exitGoodsCnt($this){
		var oldCnt = Number($($this).data("oldcnt"));
		var val = $($this).val();
		
		if(!$.isNumeric(val)){
			alert("수량이 부정확합니다!");
			val = oldCnt;			
		}

		if(Number(val) > 9999){
			val = 9999;
		}
		
		if(Number(val) < 1){
			val = 1;
		}
		
		var stockCnt = Number($($this).data("stockcnt"));
		
		if(stockCnt > 0){
			if(stockCnt < Number(val)){
				alert("해당 제품의 잔여 재고는 "+comma(stockCnt)+"개입니다.");
				val = oldCnt;
			}
		}

		$($this).val(val);
		
		$($this).data("oldcnt",val);
		
		calcPrice();	<%-- 금액 재계산 --%>
	}

	<%-- 수량+ --%>
	function addCnt(cartIdx){
		var oldCnt = Number($("#goodsCnt_"+cartIdx).data("oldcnt"));

		if(!$.isNumeric($("#goodsCnt_"+cartIdx).val())){
			alert("수량이 부정확합니다!");
			$("#goodsCnt_"+cartIdx).val(oldCnt);
		}
		
		var goodsCnt = Number($("#goodsCnt_"+cartIdx).val());
		
		++goodsCnt;
		
		if(goodsCnt > 9999){
			goodsCnt = 9999;
		}

		var stockCnt = Number($("#goodsCnt_"+cartIdx).data("stockcnt"));
		
		if(stockCnt > 0){
			if(stockCnt < goodsCnt){
				alert("해당 제품의 잔여 재고는 "+comma(stockCnt)+"개입니다.");
				goodsCnt = oldCnt;
			}
		}
		
		var buyMaxCnt = Number($("#goodsCnt_"+cartIdx).data("buymaxcnt"));	<%-- 최대 구매 가능 수량 --%>

		<%-- 최대 구매가능 수량 체크 --%>
		if(buyMaxCnt > 0){
			if(buyMaxCnt < goodsCnt){					
				alert("최대 구매가능한 수량은 " + comma(buyMaxCnt) + "개 입니다.");
				goodsCnt = oldCnt;
			}
		}
				
		$("#goodsCnt_"+cartIdx).val(goodsCnt);
		
		$("#goodsCnt_"+cartIdx).data("oldcnt",goodsCnt);
		
		calcPrice();	<%-- 금액 재계산 --%>
	}
	
	<%-- 수량- --%>
	function removeCnt(cartIdx){
		var oldCnt = Number($("#goodsCnt_"+cartIdx).data("oldcnt"));

		if(!$.isNumeric($("#goodsCnt_"+cartIdx).val())){
			alert("수량이 부정확합니다!");
			$("#goodsCnt_"+cartIdx).val(oldCnt);
		}
		
		var goodsCnt = Number($("#goodsCnt_"+cartIdx).val());
		
		--goodsCnt;
		
		if(goodsCnt < 1){
			goodsCnt = 1;
		}
		
		var buyMinCnt = Number($("#goodsCnt_"+cartIdx).data("buymincnt"));		<%-- 최소 구매 가능 수량 --%>
		
		<%-- 최소 구매가능 수량 체크 --%>
		if(buyMinCnt > 0){
			if(buyMinCnt > goodsCnt){					
				alert("최소 구매가능한 수량은 " + comma(buyMinCnt) + "개 입니다.");
				goodsCnt = oldCnt;
			}
		}
		
		$("#goodsCnt_"+cartIdx).val(goodsCnt);

		$("#goodsCnt_"+cartIdx).data("oldcnt",goodsCnt);
		
		calcPrice();	<%-- 금액 재계산 --%>
	}

	<%-- 수량 변경 --%>
	function changeCnt(cartIdx){
		
		var frm = document.orderForm;
		$("#cartIdxes").val(cartIdx);
		$("#changeGoodsCnt").val($("#goodsCnt_"+cartIdx).val());
		$("#sessionId").val(getSessionId());
		frm.action="${CTX}/cart/changeCnt.do";
		frm.submit();
	}
	
	<%-- 금액 재계산 --%>
	function calcPrice(){
		var totalPrice = 0;
		var totalDiscountPrice = 0;
		var deliveryPrice = 0;
		var totalGoodsCnt = 0;
		
		$("input:checkbox[name='cartIdx']").each(function(){
//			goodsIdx = $(this).data("goodsidx");
			cartIdx = $(this).val();
			var price = Number($(this).data("price"));
			var discountPrice = Number($(this).data("discountprice"));
			var couponDiscountPrice = Number($(this).data("coupondiscountprice"));
			var goodsCnt = Number($("#goodsCnt_"+cartIdx).val()); 
			var couponDiscountPriceCal = 0;
			if ($(this).data("discounttype") == "R") {
				couponDiscountPriceCal = couponDiscountPrice * goodsCnt;
			}
			else {
				couponDiscountPriceCal = couponDiscountPrice;
			}
			
			if(discountPrice * goodsCnt > 0){
				$("#discountPrice_"+cartIdx).text("-"+comma(discountPrice * goodsCnt));
			}else{
				$("#discountPrice_"+cartIdx).text(comma(discountPrice * goodsCnt));
			}
			$("#couponDiscountPrice_"+cartIdx).text("-"+comma(couponDiscountPriceCal));
			$("#payPrice_"+cartIdx).text(comma(price * goodsCnt - discountPrice * goodsCnt - couponDiscountPriceCal));
			
			if($(this).prop("checked")){
				totalPrice += price * goodsCnt;
				totalDiscountPrice += discountPrice * goodsCnt + couponDiscountPriceCal;
				totalGoodsCnt += goodsCnt;
			}			
		});
		
		<%-- 배송비 계산 --%>
		if(totalPrice > 0){
			if(arrShippingPrice.length > 0){
				var price = totalPrice - totalDiscountPrice;
				for(var i=0;i<arrShippingPrice.length;i++){
					if(price >= arrShippingPrice[i][0] && price <= arrShippingPrice[i][1]){
						deliveryPrice = arrShippingPrice[i][2];
						break;
					}
				}
			}else{
				deliveryPrice = 2500;
			}
		} 

		$("#totalPrice").text(comma(totalPrice));
		$("#totalDiscountPrice").text(comma(totalDiscountPrice));
		$("#deliveryPrice").text(comma(deliveryPrice));
		$("#totalPayPrice").text(comma(totalPrice - totalDiscountPrice + deliveryPrice));
		$("#totalGoodsCnt").val(totalGoodsCnt);
	}
	
	<%-- 바로구매 --%>
	function order(cartIdx){
		<%-- 세션에 저장된 주문 코드 삭제 처리 --%>
		$.ajax({			
			url: getContextPath()+"/ajax/cart/cartOrderAjax.do",
		 	type: "post",
		 	async: false,
		 	cache: false,
		 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data){
				if(data.result == true){
					var arrCart = new Array();
					var goodsIdx = $("input:checkbox[name='cartIdx']:not([disabled])[value='"+cartIdx+"']").data("goodsidx");
					var goodsObj = new Object();
					goodsObj.goodsIdx = Number(goodsIdx);
					goodsObj.goodsCnt = Number($("#goodsCnt_"+cartIdx).val());
					goodsObj.couponIdx = $(this).data("couponidx");
					arrCart.push(goodsObj);
					$("#orderGoodsInfoListStr").val(JSON.stringify(arrCart));
					var frm = document.orderForm;
					$("#sessionId").val(getSessionId());
					frm.action="${CTX}/order/cartOrderProc.do";
					frm.submit();
				}else{
					if(data.msg != ""){
						alert(data.msg);
					}
				}
			 }
		});
	}

	<%-- 장바구니 삭제 --%>
	function removeCart(cartIdx){
		if(!confirm("선택한 상품을 장바구니에서 삭제하시겠습니까?")){
			return false;
		}

		// Begin Google Tag Manager
		// Measure the removal of a product from a shopping cart.
		var goodsCd = $("input:checkbox[name='cartIdx']:not([disabled])[value='"+cartIdx+"']").data("goodscd");
		var goodsNm = $("input:checkbox[name='cartIdx']:not([disabled])[value='"+cartIdx+"']").data("goodsnm");
		var price = Number(uncomma($("input:checkbox[name='cartIdx']:not([disabled])[value='"+cartIdx+"']").data("price")));
		var discountPrice = Number(uncomma($("input:checkbox[name='cartIdx']:not([disabled])[value='"+cartIdx+"']").data("discountprice")));
		var goodsPrice = price - discountPrice;
		var goodsCnt = $("#goodsCnt_"+cartIdx).val();
		
		dataLayer.push({
		  'event': 'removeFromCart',
		  'ecommerce': {
		    'remove': {                               // 'remove' actionFieldObject measures.
		      'products': [{                          //  removing a product to a shopping cart.
		          'name': goodsNm,
		          'id': goodsCd,
		          'price': goodsPrice,
		          'brand': 'Gatsby',
		          'category': '',
		          'variant': '',
		          'quantity': goodsCnt
		      }]
		    }
		  }
		});
		// End Google Tag Manager
		
		var frm = document.orderForm;
		$("#cartIdxes").val(cartIdx);
		$("#sessionId").val(getSessionId());
		frm.action="${CTX}/cart/deleteCart.do";
		frm.submit();
	}
	
	<c:if test="${list ne null and fn:length(list) gt 0}">
		<%-- 선택상품 삭제 --%> 
		function removeSelected(){
			var cnt = $("input:checkbox[name='cartIdx']:not([disabled]):checked").length;
			if(cnt == 0){
				alert("삭제할 상품을 선택하세요!");
				return false;
			}
			
			var arrGtm = new Array();	// Google Tag Manager
			
			var cartIdxes = "";
			$("input:checkbox[name='cartIdx']:not([disabled]):checked").each(function(){
				if(cartIdxes != ""){
					cartIdxes += ",";
				}
				
				cartIdxes += $(this).val();
				// Begin Google Tag Manager			
				var cartIdx = $(this).val();
				var goodsCd = $("input:checkbox[name='cartIdx']:not([disabled])[value='"+cartIdx+"']").data("goodscd");
				var goodsNm = $("input:checkbox[name='cartIdx']:not([disabled])[value='"+cartIdx+"']").data("goodsnm");
				var price = Number(uncomma($("input:checkbox[name='cartIdx']:not([disabled])[value='"+cartIdx+"']").data("price")));
				var discountPrice = Number(uncomma($("input:checkbox[name='cartIdx']:not([disabled])[value='"+cartIdx+"']").data("discountprice")));
				var goodsPrice = price - discountPrice;
				var goodsCnt = $("#goodsCnt_"+cartIdx).val();
				
				var objGtm = { 'name': goodsNm,
				        'id': goodsCd,
				        'price': goodsPrice,
				        'brand': 'Gatsby',
				        'category': '',
				        'variant': '',
				        'quantity': goodsCnt
					};
				
				arrGtm.push(objGtm);
				// End Google Tag Manager
			});
			
			$("#cartIdxes").val(cartIdxes);
			
			if(!confirm("선택한 "+cnt+"개의 상품을 장바구니에서 삭제하시겠습니까?")){
				return false;
			}
	
			if("<spring:message code='server.status'/>" == "LIVE"){
				// Begin Google Tag Manager
				// Measure the removal of a product from a shopping cart.
				dataLayer.push({
				  'event': 'removeFromCart',
				  'ecommerce': {
				    'remove': {                               // 'remove' actionFieldObject measures.
				      'products': arrGtm
				    }
				  }
				});
				// End Google Tag Manager
			}

			var frm = document.orderForm;
			$("#cartIdxes").val(cartIdxes);
			$("#sessionId").val(getSessionId());
			frm.action="${CTX}/cart/deleteCart.do";
			frm.submit();
		}
		
		<%-- 품절상품 삭제 --%>
		function removeSoldOut(){
			if(!confirm("품절상품을 장바구니에서 삭제하시겠습니까?")){
				return false;
			}
	
			var frm = document.orderForm;
			$("#sessionId").val(getSessionId());
			frm.action="${CTX}/cart/deleteSoldOutCart.do";
			frm.submit();
		}
	</c:if>
	
	<%-- 선택상품 주문 --%>
	function orderSelected(){

		<%-- 버튼 연타 방지 --%>
		if(didSubmit == true){
			return false;
		}
		
		didSubmit = true;
		<%-- 버튼 연타 방지 끝 --%>

		var cnt = $("input:checkbox[name='cartIdx']:not([disabled]):checked").length;
		if(cnt == 0){
			alert("상품을 선택하세요!");
			didSubmit = false;
			return false;
		}

		orderProc();
	}
	
	<%-- 전체상품 주문 --%>
	function orderTotal(){

		<%-- 버튼 연타 방지 --%>
		if(didSubmit == true){
			return false;
		}
		
		didSubmit = true;
		<%-- 버튼 연타 방지 끝 --%>

		$("input:checkbox[name='cartIdx']:not([disabled])").prop("checked",true);
		
		var cnt = $("input:checkbox[name='cartIdx']:not([disabled]):checked").length;
		if(cnt == 0){
			alert("상품을 선택하세요!");
			didSubmit = false;
			return false;
		}

		orderProc();
	}
	
	<%-- 주문 --%>
	function orderProc(){
		<%-- 세션에 저장된 주문 코드 삭제 처리 --%>
		$.ajax({			
			url: getContextPath()+"/ajax/cart/cartOrderAjax.do",
		 	type: "post",
		 	async: false,
		 	cache: false,
		 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data){
				if(data.result == true){
					var arrCart = new Array();
					$("input:checkbox[name='cartIdx']:not([disabled]):checked").each(function(){
						var cartIdx = $(this).val();
						var goodsIdx = $(this).data("goodsidx");
						var goodsObj = new Object();
						goodsObj.goodsIdx = Number(goodsIdx);
						goodsObj.goodsCnt = Number($("#goodsCnt_"+cartIdx).val());
						goodsObj.couponIdx = $(this).data("couponidx");
						arrCart.push(goodsObj);
					});
					$("#orderGoodsInfoListStr").val(JSON.stringify(arrCart));
					var frm = document.orderForm;
					$("#sessionId").val(getSessionId());
					frm.action="${CTX}/order/cartOrderProc.do";
					frm.submit();
				}else{
					if(data.msg != ""){
						alert(data.msg);
					}
				}
			 }
		});
	}
	
	
	var recmProductSlider = null;
	
    <!-- PAGE SCRIPT -->
    $(function(){

        //사은품 UI
        $.touchflow({
            wrap: '.tab-menu',
            container: '.menu-inner',
            list: '.menu-list',
            tablink : '.menu-link'
        })

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


    });
    
 // 장바구니 담기
	function goCartRecommend(goodsIdx, goodsCd, goodsNm, goodsPrice){
		addCartRecommend(goodsIdx, 1, goodsCd, goodsNm, goodsPrice);
	}
	
	// 장바구니 저장
	function addCartRecommend(goodsIdx, goodsCnt, goodsCd, goodsNm, goodsPrice){
		if(confirm("장바구니에 추가 하시겠습니까?") ==true){
			if($.trim(goodsIdx) == ""){
				alert("상품정보가 없습니다!");
				return false;
			}
			
			if(!$.isNumeric(goodsCnt)){
				alert("수량을 확인하세요!");
				return false;
			}
	
			var arr = new Array();
			var obj = new Object();
			
			obj.goodsIdx = goodsIdx;
			obj.goodsCnt = goodsCnt;
			obj.sessionId = getSessionId();
			
			arr.push(obj);
			
			addCartRecommendObject(arr);
			
			// Begin Google Tag Manager
			// Measure adding a product to a shopping cart by using an 'add' actionFieldObject
			// and a list of productFieldObjects.
			dataLayer.push({
			  'event': 'addToCart',
			  'ecommerce': {
			    'currencyCode': 'KRW',
			    'add': {                                // 'add' actionFieldObject measures.
			      'products': [{                        //  adding a product to a shopping cart.
			        'name': goodsNm,
			        'id': goodsCd,
			        'price': goodsPrice,
			        'brand': 'Gatsby',
			        'category': '',
			        'variant': '',
			        'quantity': goodsCnt
			       }]
			    }
			  }
			});
			// End Google Tag Manager
		}
	}
	
	// 장바구니 저장
	function addCartRecommendObject(arrayOfGoods){		
		var arrObjStr = JSON.stringify(arrayOfGoods);
		
		$.ajax({			
			url: getContextPath()+"/ajax/cart/addCartAjax.do",
		 	data: arrObjStr,
		 	type: "post",
		 	async: false,
		 	cache: false,
	        dataType : 'json',
	        contentType: "application/json", 
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data){
				if(data.result == true){
					alert("장바구니에 담았습니다.");
					document.location.reload();
				}else{
					if(data.msg != ""){
						$('[data-remodal-id=pop_alert] div.pop-mid p').html(data.msg);
						$('[data-remodal-id=pop_alert]').remodal().open();
					}
					return false;
				}
			 }
		});
	}
	
	// Doyouad Start 삭제 하지 마세요.
	function doyouAd(){
		var totalPayPrice = $("#totalPayPrice").text();
		var totalGoodsCnt = $("#totalGoodsCnt").val();
		
		var DOYOUAD_DATA = {};
		DOYOUAD_DATA.qty = totalGoodsCnt;
		DOYOUAD_DATA.price = totalPayPrice;
		(function (w, d, s, n, t) {n = d.createElement(s);n.type = "text/javascript";n.setAttribute("id", "doyouadScript");n.setAttribute("data-user", "mandom");n.setAttribute("data-page", "basket");n.async = !0;n.defer = !0;n.src = "https://cdn.doyouad.com/js/dyadTracker.js?v=" + new Date().toISOString().slice(0, 10).replace(/-/g, "");t = d.getElementsByTagName(s)[0];t.parentNode.insertBefore(n, t);})(window, document, "script");		
	}
	// Doyouad End
	
	// Mobon Tracker v4.0 start
	function mobon(){
		var device = "${DEVICE}".toUpperCase();
		var ENP_VAR = { conversion: { product: [] } };
		
	 	// 주문한 각 제품들을 배열에 저장
	    <c:forEach var="list" items="${list}" varStatus="idx">
		    var goodsInfo = {};	
		    goodsInfo.productCode = "${list.goodsCd}";
	    	goodsInfo.productName = "${list.goodsNm}";
	    	goodsInfo.price = "${list.price}";
	    	goodsInfo.dcPrice = "${list.discountPrice}";
	    	goodsInfo.qty = "${list.goodsCnt}";
	    	
	    	ENP_VAR.conversion.product.push(goodsInfo);
	    </c:forEach>
	    
	    var totalPayPrice = $("#totalPayPrice").text();
		var totalGoodsCnt = $("#totalGoodsCnt").val();
	    
	    ENP_VAR.conversion.totalPrice = totalPayPrice;  // 없는 경우 단일 상품의 정보를 이용해 계산
	    ENP_VAR.conversion.totalQty = totalGoodsCnt;  // 없는 경우 단일 상품의 정보를 이용해 계산
	
	    (function(a,g,e,n,t){a.enp=a.enp||function(){(a.enp.q=a.enp.q||[]).push(arguments)};n=g.createElement(e);n.async=!0;n.defer=!0;n.src="https://cdn.megadata.co.kr/dist/prod/enp_tracker_self_hosted.min.js";t=g.getElementsByTagName(e)[0];t.parentNode.insertBefore(n,t)})(window,document,"script");  
	    enp('create', 'conversion', 'mandom', { device: device, paySys: 'naverPay' });
	}
	// Mobon Tracker v4.0 end
</script>
</head>
<body>
		<form name="orderForm" id="orderForm" method="post" onsubmit="return false;">
			<input type="hidden" name="sessionId" id="sessionId" value=""/>
			<input type="hidden" name="cartIdxes" id="cartIdxes" value=""/>
			<input type="hidden" name="changeGoodsCnt" id="changeGoodsCnt" value=""/>
			<input type="hidden" name="orderGoodsInfoListStr" id="orderGoodsInfoListStr" value=""/>
			<input type="hidden" id="totalGoodsCnt" value=""/>
			
            <div class="content comm-order comm-cart">

 				<div class="order-top">
					<div class="order-steps step1">
						<ol>
							<li class="active">
								<span class="hide">현재단계</span>
								<span>STEP 01</span>
								<strong>장바구니</strong>
							</li>
							<li>
								<span>STEP 02</span>
								<strong>주문결제</strong>
							</li>
							<li>
								<span>STEP 03</span>
								<strong>주문완료</strong>
							</li>
						</ol>
					</div>
					<c:choose>
						<c:when test="${empty USERINFO.memberId}">
							<div class="member-pocket">
								<div class="pocket-left">
									<p>
										로그인 후 구매하시면 
										<strong class="em">할인 쿠폰,<br/> 포인트</strong> 및 <strong class="em">사은품</strong> 등 다양한 혜택을<br/> 
										받으실 수 있습니다.
									</p>
									<!-- 2023.04.11 수정 -->
									<!-- <a href="javascript:void(0);" data-toggle="popup" data-target="#pop_grade_benefit">온라인 회원 혜택</a> -->
								</div>
								<div class="pocket-login">
									<a href="${CTX}/member/joinStep01.do" class="btn"><span class="txt">회원가입</span></a>
									<a href="${CTX}/login/loginPage.do?refererYn=Y" class="btn outline-green"><span class="txt">로그인</span></a>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="member-pocket" style="height: 90px;">
								<div class="pocket-left">
									<p>
										<strong>${USERINFO.memberNm}</strong> 님
									</p>
									<!-- 2023.04.11 수정 -->
									<!-- <a href="javascript:void(0);" data-toggle="popup" data-target="#pop_grade_benefit">회원 등급별 혜택</a> -->
								</div>
								<ul class="pocket-info">
									<li>
										<strong>포인트</strong>
										<p><a href="/m/mypage/point/pointList.do"><fmt:formatNumber value="${point}" groupingUsed="true"/></a>p</p>
									</li>
									<li>
										<strong>쿠폰</strong>
										<p><a href="/m/mypage/point/couponList.do"><fmt:formatNumber value="${couponCnt}" groupingUsed="true"/></a>장</p>
									</li>
								</ul>
							</div>
						</c:otherwise>
					</c:choose>
				</div>

                <div class="page-body">

                    <div class="order-form">
                    
					<c:if test="${list ne null and fn:length(list) gt 0}">	<%-- 상품이 있을때만 표시 --%>
                        <div class="order-util">
                            <span class="checkbox">
                                <input type="checkbox" id="chkCartAll" class="check" />
                                <label for="chkCartAll" class="lbl">전체상품 <span class="em"><fmt:formatNumber value="${fn:length(list)}"/></span>개</label>
                            </span>
                            <div class="btns">
                                <button type="button" class="btn" onclick="removeSelected();"><span class="txt">선택 삭제</span></button>
                                <button type="button" class="btn" onclick="removeSoldOut();"><span class="txt">품절 삭제</span></button>
                            </div>
                        </div>
					</c:if>
						
                        <div class="form-group">
                        	<div class="form-body">
                        		
				<c:choose>
					<c:when test="${list eq null or fn:length(list) eq 0}">
                        		<!-- 데이타 없을경우 -->
		                        <div class="no-contents">
		                            <p>장바구니에 담긴 상품이 없습니다.</p>
		                        </div>
		                        <!-- //데이타 없을경우 -->
					</c:when>
					<c:otherwise>
	                            <div class="order-goods">
	                                <ul>
									<c:forEach var="list" items="${list}" varStatus="idx">
										<c:set var="soldOutClass" value=""/>
										<c:set var="soldOutStr" value=""/>
										<c:if test="${list.saleStatus eq 'R' or (list.stockFlag eq 'Y' and list.stockCnt eq 0)}">
											<c:set var="soldOutClass" value="soldout"/>
											<c:set var="soldOutStr" value="disabled"/>
										</c:if>
	                                    <li>
	                                        <div class="item ${soldOutClass}">
	                                            <div class="item-check">
	                                                <span class="checkbox">
	                                                    <input type="checkbox" name="cartIdx" id="cartIdx_${list.cartIdx}" value="${list.cartIdx}" class="check" data-goodsidx="${list.goodsIdx}" data-price="${list.price}" data-discountprice="${list.price - list.discountPrice}" data-coupondiscountprice="${list.couponDiscountPrice}" data-couponidx="${list.couponIdx}" data-discounttype="${list.discountType}" data-goodscd="<c:out value="${list.goodsCd}"/>" data-goodsnm="<c:out value="${list.goodsNm}"/>" onclick="calcPrice();" ${soldOutStr}/>
	                                                    <label for="cartIdx_${list.cartIdx}" class="lbl"><span class="hide">선택</span></label>
	                                                </span>
	                                            </div>
	                                            <div class="item-view">
													<div class="view-thumb">
								                  		<c:set var="imgSplit" value="${fn:split(list.mainFile ,'.') }"/>
														<img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}" onerror="this.src='${CTX}/images/${DEVICE}/noimage.jpg'" alt="상품 썸네일 이미지">
													</div>
													<div class="view-info">
														<div class="badge-box">
															<c:if test="${list.opoYn eq 'Y' }" ><span class="badge type1">1+1</span></c:if>
															<c:if test="${list.tpoYn eq 'Y' }" ><span class="badge type2">2+1</span></c:if>
															<c:if test="${list.newYn eq 'Y' }" ><span class="badge type3">NEW</span></c:if>
															<c:if test="${list.bestYn eq 'Y' }" ><span class="badge type4">BEST</span></c:if>
															<c:if test="${list.saleiconYn eq 'Y' }" ><span class="badge type5">SALE</span></c:if>
															<c:if test="${list.pointiconYn eq 'Y' }" ><span class="badge type3">POINT <em>X2</em></span></c:if>
														</div>
														<c:choose>
															<c:when test="${list.brandIdx eq 1}"><p class="text-gatsby">${list.brandNm}</p></c:when>		
															<c:when test="${list.brandIdx eq 3}"><p class="text-bifesta">${list.brandNm}</p></c:when>
															<c:when test="${list.brandIdx eq 4}"><p class="text-lucidol">${list.brandNm}</p></c:when>
															<c:when test="${list.brandIdx eq 6}"><p class="text-mama">${list.brandNm}</p></c:when>
															<c:when test="${list.brandIdx eq 7}"><p class="text-dental">${list.brandNm}</p></c:when>
															<c:when test="${list.brandIdx eq 8}"><p class="text-charley">${list.brandNm}</p></c:when>
														</c:choose>															
														<a href="${CTX}/product/productView.do?goodsCd=${list.goodsCd}" target="_blank"><p class="name"><c:out value="${list.goodsNm}"/></p></a>
														<div class="mod_qty">
															<span class="spinner">
	                                                            <input type="tel" name="goodsCnt" id="goodsCnt_${list.cartIdx}" class="goodsCnt" value="${list.goodsCnt}" maxlength="4" onblur="exitGoodsCnt(this);" data-buymincnt="${list.buyMinCnt}" data-buymaxcnt="${list.buyMaxCnt}" data-stockcnt="<c:choose><c:when test="${list.stockFlag eq 'Y'}">${list.stockCnt}</c:when><c:otherwise>0</c:otherwise></c:choose>" data-oldcnt="${list.goodsCnt}"/>
	                                                            <button type="button" class="btn-minus" <c:if test="${soldOutClass eq ''}">onclick="removeCnt('${list.cartIdx}');"</c:if>>-</button>
	                                                            <button type="button" class="btn-plus" <c:if test="${soldOutClass eq ''}">onclick="addCnt('${list.cartIdx}');"</c:if>>+</button>
															</span>
															<c:if test="${list.saleStatus eq 'Y' and (list.stockFlag eq 'N' or (list.stockFlag eq 'Y' and list.stockCnt gt 0))}">
		                                                        <button type="button" class="btn sm" onclick="changeCnt('${list.cartIdx}');"><span class="txt">변경</span></button>
															</c:if>
														</div>
													</div>
													<c:if test="${not empty USERINFO.memberId}">	<%--회원 --%>
		                                                <button type="button" class="btn-wish <c:if test="${list.isWished eq 'Y'}">active</c:if>" onclick="addWishList(this, '${list.goodsIdx}');"><span class="hide">좋아요</span></button>
													</c:if>
	                                            </div>
	                                            <div class="item-payment">
	                                                <dl>
	                                                    <dt>판매가</dt>
	                                                    <dd><fmt:formatNumber value="${list.price}" groupingUsed="true"/></dd>
	                                                    <dt>상품 할인</dt>
	                                                    <dd>
	                                                        <div>
	                                                            <span class="sub"><fmt:formatNumber value="${list.discountRate}" groupingUsed="true"/>% 할인</span>
	                                                            <em class="em" id="discountPrice_${list.cartIdx}"><c:if test="${discount ne null and discount ne '' and discount gt 0}">-</c:if><fmt:formatNumber value="${discount}" groupingUsed="true"/></em>
	                                                        </div>
	                                                    </dd>
												<c:if test="${not empty USERINFO.memberId}">	<%--회원 --%>
													<c:if test="${list.couponNm ne null}">
	                                                    <dt>쿠폰 할인</dt>
	                                                    <dd>
	                                                        <div>
	                                                            <span class="sub">${list.couponNm}</span>
	                                                            <em class="em" id="couponDiscountPrice_${list.cartIdx}">-<fmt:formatNumber value="${list.couponDiscountPrice * list.goodsCnt}" groupingUsed="true"/></em>
	                                                        </div>
	                                                    </dd>
													</c:if>
												</c:if>
	                                                    <dt>결제예상금액</dt>
	                                                    <dd id="payPrice_${list.cartIdx}"><fmt:formatNumber value="${list.discountPrice * list.goodsCnt - list.couponDiscountPrice * list.goodsCnt}" groupingUsed="true"/></dd>
	                                                </dl>   
	                                            </div>
	                                            <div class="btn-box">
	                                                <div class="row">
	                                                    <div class="col col-4">
	                                                        <button type="button" class="btn full" onclick="removeCart('${list.cartIdx}');"><span class="txt">삭제</span></button>
	                                                    </div>
													<c:if test="${soldOutClass eq ''}">	<%-- 품절이 아니면 바로구매 버튼 표시 --%>
	                                                    <div class="col col-8">
	                                                        <button type="button" class="btn outline-green full" onclick="order('${list.cartIdx}');"><span class="txt">바로구매</span></button>
	                                                    </div>
													</c:if>
	                                                </div>
	                                            </div>
	                                        </div>
	                                    </li>
									</c:forEach>
	                                </ul>
	                            </div>
					</c:otherwise>
				</c:choose>
	                        </div>
                        </div>
                    </div>
				
				<c:if test="${list ne null and fn:length(list) gt 0}">	<%-- 상품이 있을때만 표시 --%>
                    <!-- //결제정보 -->
                    <div class="order-form form-payment">
                        <div class="form-group">
                        	<div class="form-body">
	                            <div class="payment-final">
	                                <ul>
	                                    <li>
	                                        <div class="item">
	                                            <strong>총 판매가</strong>
	                                            <p id="totalPrice">0</p>
	                                        </div>
	                                    </li>
	                                    <li>
	                                        <div class="item">
	                                            <strong>총 할인금액</strong>
	                                            <p>-<span id="totalDiscountPrice">0</span></p>
	                                        </div>
	                                    </li>
	                                    <li>
	                                        <div class="item">
	                                            <strong>배송비</strong>
	                                            <p>+<span id="deliveryPrice">0</span></p>
	                                        </div>
	                                    </li>
<%--
	                                    <li>
	                                        <div class="item">
	                                            <strong>선물포장비</strong>
	                                            <p>+2,500</p>
	                                        </div>
	                                    </li>
 --%>
	                                    <li class="total">
	                                        <div class="item">
	                                            <strong>결제예상금액</strong>
	                                            <p id="totalPayPrice">0</p>
	                                        </div>
	                                    </li>
	                                </ul>
	                                <div class="btn-box confirm">
	                                    <button type="button" class="btn" onclick="orderSelected();"><span class="txt">선택상품 주문</span></button>
	                                    <button type="button" class="btn black" onclick="orderTotal();"><span class="txt">전체상품 주문</span></button>
	                                </div>
	                                
	                                <!-- 
										수정 : 2018-06-21 (정진택)
										- n페이추가
									-->
									
									<!-- 2023.03.20 수정 : npay 주석처리 -->
									<!-- <div class="btn-npay" style="margin-top: 15px;">
										<script type="text/javascript" src="https://pay.naver.com/customer/js/mobile/naverPayButton.js" charset="UTF-8"></script>
										<script type="text/javascript">
										// 오늘 하루 안 보기
									 	function closePop(){
											setCookie("npayPop", "Y", 1);
										}
										
									 	function npayClick(){
									 		var cookie = getCookie("npayPop");
									 			
								 			//if(cookie=="Y"){
								 				buy_nc();
								 			//}else{
								 				noticePopup("popNotice20190827");
								 			//}
								 		}
									 	
										function buy_nc() {     
	
											var cnt = $("input:checkbox[name='cartIdx']:not([disabled]):checked").length;
											if(cnt == 0){
												$('[data-remodal-id=pop_alert] div.pop-mid p').html("상품을 선택하세요!");
												$('[data-remodal-id=pop_alert]').remodal().open();
												return false;
											}
											var countchk = 0;
											var arrCart = new Array();
											$("input:checkbox[name='cartIdx']:not([disabled]):checked").each(function(){
												var cartIdx = $(this).val();
												var goodsIdx = $(this).data("goodsidx");
												var goodsObj = new Object();
												goodsObj.goodsIdx = Number(goodsIdx);
												goodsObj.goodsCnt = Number($("#goodsCnt_"+cartIdx).val());
												

												if (goodsObj.goodsCnt > 999) {
													countchk = 1;
													return false;
												}
												
												if (goodsObj.goodsCnt  == 0 || goodsObj.goodsCnt  < 0 ||goodsObj.goodsCnt  == "") {
													countchk = 2;
													return false;
												}
												
												arrCart.push(goodsObj);
											});	
											
											if (countchk == 1) {
												alert("주문수량을 999이하로 입력해 주세요.");
												return false;
											}
											
											if (countchk == 2) {
												alert("주문수량을 입력해 주세요.");
												return false;
											}
										
											$.ajax({			
												url: getContextPath()+"/ajax/order/nPayOrderRegister.do",
											 	data: JSON.stringify(arrCart),
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
														location.href=data.orderUrl;
													}
												 }
											}); 
										 }
										
										
									 	//<![CDATA[     
										naver.NaverPayButton.apply({
											BUTTON_KEY: "1001E478-1B94-47E0-9539-6B43E8163A78", // 페이에서 제공받은 버튼 인증 키 입력         
											TYPE: "MA", // 버튼 모음 종류 설정         
											COLOR: 1, // 버튼 모음의 색 설정           
											COUNT: 1, // 버튼 개수 설정. 구매하기 버튼만 있으면 1, 찜하기 버튼도 있으면 2를 입력.          
											ENABLE: "Y", // 품절 등의 이유로 버튼 모음을 비활성화할 때에는 "N" 입력      
											BUY_BUTTON_HANDLER: npayClick
										}); 
										//]]> 
										</script>
									</div> -->
									
									<!-- 팝업 - 20190827 -->
									<!-- 2023.03.20 수정 : npay 주석처리 -->
									<!-- <div class="popup type-notice" data-remodal-mode="multiple" data-remodal-id="popNotice20190827">
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
									            
									            <a href="javascript:void(0);" style="top: 69.6%;" onclick="orderSelected();"><span class="hide">혜택받고 공식몰에서 구매하기</span></a>
									            <a href="javascript:void(0);" style="top: 82.8%;" onclick="buy_nc();"><span class="hide">혜택 안 받고 NPay 로 구매하기</span></a>
									        </div>
									    </div>
									    <div class="pop-close">
									        <a href="javascript:void(0);" class="btn-close" data-remodal-action="close" onclick="closePop();">오늘 하루 보지 않기</a>
									    </div>
									</div> --!>
									<!-- //팝업 - 20190827 -->
	                            </div>
	                            <!-- 수정: 2019-04-01, NPAY 공지추가 -->
	                            <!-- 2023.03.20 수정 : npay 주석처리 -->
								<!-- <div class="notice-npay">
									<img src="${CTX}/images/m/contents/img_npay_notice.png" alt="" srcset="">
								</div> --!>
								<!-- //수정: 2019-04-01 -->
	                        </div>
                        </div>
                    </div>
                    <!-- //결제정보 -->
				</c:if>

					<%-- 장바구니 배너 --%>
					<c:if test="${!empty bannerInfo}">
						<c:if test="${bannerInfo.deviceGubun eq 'M' or bannerInfo.deviceGubun eq 'A'}">
							<div class="order-form">
								<c:choose>
									<c:when test="${bannerInfo.moLinkYn eq 'N'}">
									<a href="javascript:">
									</c:when>
									<c:when test="${bannerInfo.moLinkYn eq 'Y'}">
									<a href="${bannerInfo.moLinkUrl}" <c:if test="${bannerInfo.moLinkFlag eq '_BLANK'}">target="_BLANK"</c:if>>
									</c:when>				
								</c:choose>
									<img src="${IMGPATH}/banner/${bannerInfo.bannerIdx}/${bannerInfo.moBannerImg}" alt="${bannerInfo.bannerNm}" style="max-width: 100%"  />
								</a>
							</div>
						</c:if>
					</c:if>
					<%-- //장바구니 배너 --%>
				
				<c:if test="${cartRecommendList ne null and fn:length(cartRecommendList) gt 0}">
					<!-- 추천상품 -->
					<div class="order-form form-recmgoods">
						<div class="form-group">
							<div class="form-title">
								<h3 class="title">면역공방의 추천상품 입니다.</h3>
								<button type="button" class="btn-toggle active" data-toggle="collapse" data-target="#formRecmGoods" ><span class="hide">접기</span></button>
							</div>
							<div id="formRecmGoods" class="form-body">
								<div class="recommend-products">
									<div class="product-items">
										<ul id="recommendSlider" class="type-default">
											<c:forEach var="list" items="${cartRecommendList}" varStatus="idx">
											<li>
												<div class="item">
													<div class="item-wrap">
														<a href="${CTX}/product/productView.do?goodsCd=${list.goodsCd}" class="item-anchor" target="_blank">
															<span class="badge-box">
																<c:if test="${list.opoYn eq 'Y' }" ><span class="badge type1">1+1</span></c:if>
																<c:if test="${list.tpoYn eq 'Y' }" ><span class="badge type2">2+1</span></c:if>
																<c:if test="${list.newYn eq 'Y' }" ><span class="badge type3">NEW</span></c:if>
																<c:if test="${list.bestYn eq 'Y' }" ><span class="badge type4">BEST</span></c:if>
																<c:if test="${list.saleiconYn eq 'Y' }" ><span class="badge type5">SALE</span></c:if>
																<c:if test="${list.pointiconYn eq 'Y' }" ><span class="badge type3"><i>POINT</i> X2</span></c:if>
															</span>
															<div class="item-thumb">
																<c:set var="imgSplit" value="${fn:split(list.mainFile ,'.') }"/>
																<img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_M.${imgSplit[1]}" onerror="this.src='${CTX}/images/${DEVICE}/noimage.jpg'" alt="상품이미지${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_M.${imgSplit[1]}"/>
															</div>
															<p class="item-summary">${list.shortInfo}</p>
															<p class="item-name">${list.goodsNm}</p>
															<div class="item-price">
																<c:choose>
																	<c:when test="${list.discountRate gt 0}">
																		<del class="origin"><fmt:formatNumber value="${list.price}" groupingUsed="true"/></del>
																		<span class="discount"><em><fmt:formatNumber value="${list.discountRate}" groupingUsed="true"/></em>%</span>
																		<span class="price"><fmt:formatNumber value="${list.discountPrice}" groupingUsed="true"/></span>
																	</c:when>
																	<c:otherwise>
																		<span class="price"><fmt:formatNumber value="${list.discountPrice}" groupingUsed="true"/></span>
																	</c:otherwise>
																</c:choose>
															</div>
														</a>
														<a href="javascript:void(0);" id="cartBtn" class="btn-cart" onclick="goCartRecommend('${list.goodsIdx}','<c:out value="${list.goodsCd}"/>','<c:out value="${list.goodsTitle}"/>','<fmt:formatNumber value="${list.discountPrice}" groupingUsed="false"/>');"><span class="hide">장바구니</span></a>
													</div>
													<div class="item-etc">
														<c:if test="${list.onlineYn eq 'Y'}">
															<img src="${CTX}/images/${DEVICE}/common/ico_coupon_online.png" class="coupon" alt="쿠폰">
														</c:if>
														<c:if test="${list.autoCouponYn eq 'Y'}">
															<img src="${CTX}/images/${DEVICE}/common/ico_coupon_recommend.png" class="coupon" alt="쿠폰">
														</c:if>
													</div>
												</div>
											</li>
											</c:forEach>
										</ul>
									</div>
								</div>

								<!-- s: 추천상품 스크립트 -->
								<script>
									$(function(){
										$('#recommendSlider').lightSlider({
											item:2,
											loop:true,
											pager:false,
											slideMargin: 0
										});
									})
								</script>
								<!-- w: 추천상품 스크립트 -->
							</div>
						</div>
					</div>
					<!-- //추천상품 -->
				</c:if>
				
				<c:if test="${(freeGiftList ne null and fn:length(freeGiftList) gt 0) or (priceGiftList ne null and fn:length(priceGiftList) gt 0)}">
					<!-- 사은품 -->
					<div class="order-form form-gift">
						<div class="form-group">
							<div class="form-title">
								<h3 class="title">면역공방의 사은품 입니다.</h3>
								<button type="button" class="btn-toggle active" data-toggle="collapse" data-target="#formGifts" ><span class="hide">접기</span></button>
							</div>
							<div id="formGifts" class="form-body">
								<ul class="bu-list">
									<li><span class="bu">*</span> 사은품 가이드: 할인금액을 제외한 실결제금액 기준으로, 여러 사은품 중복 증정이 가능합니다. 
									 <br />단, 네이버페이로 구매 시에는 사은품 증정이 되지 않습니다.
									</li>
								</ul>
								<div class="gift-list">
									<ul>
									<c:forEach var="list" items="${freeGiftList}" varStatus="idx">
										<li>
											<div class="item">
												<div class="item-thumb">
													<c:set var="imgSplit" value="${fn:split(list.giftImg ,'.') }"/>
													<img src="${IMGPATH}/gift/${list.giftIdx}/${imgSplit[0]}.${imgSplit[1]}" onerror="this.src='${CTX}/images/${DEVICE}/noimage.jpg'" alt="사은품 이미지">	<%--사은품은 원본 이미지로 표시 --%>
												</div>
												<div class="item-info">
													<p class="type"><c:out value="${list.termNm}"/></p>
													<p class="name"><c:out value="${list.giftNm}"/></p>
												</div>
											</div>
										</li>
									</c:forEach>
									<c:forEach var="list" items="${priceGiftList}" varStatus="idx">
										<li>
											<div class="item">
												<div class="item-thumb">
													<c:set var="imgSplit" value="${fn:split(list.giftImg ,'.') }"/>
													<img src="${IMGPATH}/gift/${list.giftIdx}/${imgSplit[0]}.${imgSplit[1]}" onerror="this.src='${CTX}/images/${DEVICE}/noimage.jpg'" alt="사은품 이미지">	<%--사은품은 원본 이미지로 표시 --%>
												</div>
												<div class="item-info">
													<p class="type"><c:out value="${list.termNm}"/></p>
													<p class="name"><c:out value="${list.giftNm}"/></p>
												</div>
											</div>
										</li>
									</c:forEach>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<!-- 사은품 -->
				</c:if>
                </div>
            </div>
		</form>

<!-- 회원등급별혜택 -->
<div id="pop_grade_benefit" class="popup type-page">
	<h1 class="hide">GATSBY</h1>
	<!-- pop-top -->
	<div class="pop-top">
	<c:choose>
		<c:when test="${empty USERINFO.memberId}">
			<h2>온라인회원혜택</h2>
		</c:when>
		<c:otherwise>
			<h2>회원 등급별 혜택</h2>
		</c:otherwise>
	</c:choose>
	</div>
	<!-- //pop-top -->
	<!-- pop-mid -->
	<div class="pop-mid">
		
	<c:choose>
		<c:when test="${empty USERINFO.memberId}">
	        <h3 class="member-grade-title"><span class="em">온라인 쇼핑몰 회원</span>에게만 주어지는 혜택! </h3>
	        <div class="member-benefit">
	            <img src="${CTX}/images/${DEVICE}/contents/img_member_benefit.png" alt="" />
	            <ul class="hide">
	                <li>회원가입 포인트 2,000P 지급</li>
	                <li>무료 배송 쿠폰 매월 지급</li>
	                <li>할인 쿠폰 매월 등급별 지급</li>
	                <li>회원등급에 따라 구매포인트 적립</li>
	            </ul>
	        </div>
		</c:when>
		<c:otherwise>
	        <p class="member-grade"><strong>${USERINFO.memberNm}</strong>님 회원등급은<Br/> <strong class="em">${USERINFO.gradeNm}</strong>입니다.</p>
		</c:otherwise>
	</c:choose>
		
		<div class="grade-detail">

			<div id="tabmenu" class="tab-menu-line tab-3">
				<ul>
					<li class="active"><a href="#tabGeneral1" class="tab-link">일반회원</a></li>
					<li><a href="#tabSilver1" class="tab-link">우수회원</a></li>
					<li><a href="#tabVip1" class="tab-link">VIP회원</a></li>
				</ul>
			</div>
			
			<div class="grade-benefit">
				<ul>
					<li id="tabGeneral1">
						<div class="grade-box">
							<span class="grade grade-g"></span>
							<p><span class="em">회원가입</span></p>
						</div>
						<div class="grade-cont">
							<dl>
								<dt>구매 포인트 적립</dt>
								<dd>
									실결제 금액의 1%<br/>
									(배송비 제외)
								</dd>
								<dt>할인 쿠폰</dt>
								<dd>
									무료 배송 쿠폰 1장<br/>
									생일 쿠폰 10% 1장<br/>
									(면역공방 회원가입 시 생년월일 입력한 회원. <br />SNS 간편가입회원은 제외)
								</dd>
							</dl>
						</div>

					</li>
					<li id="tabSilver1">
						<div class="grade-box">
							<span class="grade grade-s"></span>
							<p>최근 3개월간 <span class="em">실결제 금액 3만원</span></p>
						</div>
						<div class="grade-cont">
							<dl>
								<dt>구매 포인트 적립</dt>
								<dd>실결제 금액의 2%<br/>(배송비제외)</dd>
								<dt>할인 쿠폰</dt>
								<dd>
									무료 배송 쿠폰 1장<br/>
									상품 쿠폰 5% 1장<br/>
									장바구니 쿠폰 5% 1장 <br/>
									생일 쿠폰 10% 1장<br/>
									(면역공방 회원가입 시 생년월일 입력한 회원. <br />SNS 간편가입회원은 제외)
								</dd>
							</dl>
						</div>
					</li>
					<li id="tabVip1">
						<div class="grade-box">
							<span class="grade grade-v"></span>
							<p>최근 3개월간 <span class="em">실결제 금액 5만원</span></p>
						</div>
						<div class="grade-cont">
							<dl>
								<dt>구매 포인트 적립</dt>
								<dd>실결제 금액의 3%<br/>(배송비제외)</dd>
								<dt>할인 쿠폰</dt>
								<dd>
									무료 배송 쿠폰 1장<br/>
									상품 쿠폰 10% 1장<br/>
									장바구니 쿠폰 10% 1장 <br/>
									생일 쿠폰 10% 1장<br/>
									(면역공방 회원가입 시 생년월일 입력한 회원. <br />SNS 간편가입회원은 제외)
								</dd>
							</dl>
						</div>
					</li>
				</ul>
			</div>
		</div>

		<div class="btn-box confirm">
			<button type="button" class="btn black" data-dismiss="popup"><span class="txt">확인</span></button>
		</div>
	</div>
	<!-- //pop-mid -->
	<a href="javascript:void(0);" class="btn_close" data-dismiss="popup"><span class="hide">닫기</span></a>
</div>


<c:set var="status"><spring:message code="server.status" /></c:set>
<c:if test="${status eq 'LIVE'}">
	<!-- WIDERPLANET  SCRIPT START 2017.8.29 -->
	<div id="wp_tg_cts" style="display:none;"></div>
	<script type="text/javascript">
		var wptg_tagscript_vars = wptg_tagscript_vars || [];
		wptg_tagscript_vars.push(
		(function() {
			return {
				wp_hcuid:"",  	/*Cross device targeting을 원하는 광고주는 로그인한 사용자의 Unique ID (ex. 로그인 ID, 고객넘버 등)를 암호화하여 대입.
						 *주의: 로그인 하지 않은 사용자는 어떠한 값도 대입하지 않습니다.*/
				ti:"37336",
				ty:"Cart",
				device:"mobile"
				,items:[
				<c:forEach var="list" items="${list}" varStatus="idx">
					<c:if test="${not idx.first}">,</c:if>
					 {i:"<c:out value="${list.goodsCd}"/>",	t:"<c:out value="${list.goodsNm}"/>"} /* 상품 - i:상품 식별번호 (Feed로 제공되는 식별번호와 일치) t:상품명 */
				</c:forEach>
				]
			};
		}));
		<!-- Facebook Pixel Code -->
		fbq('track', 'AddToCart', {
	        content_ids : [<c:forEach var="list" items="${list}" varStatus="idx"><c:if test="${not idx.first}">,</c:if>'<c:out value="${list.goodsCd}"/>'</c:forEach>],
	        content_type: 'product'
	    });
	</script>
	<script type="text/javascript" async src="//cdn-aitg.widerplanet.com/js/wp_astg_4.0.js"></script>
	<!-- // WIDERPLANET  SCRIPT END 2017.8.29 -->	
</c:if>

	<!-- 카카오픽셀스크립트 : 장바구니 -->
	<script type="text/javascript" charset="UTF-8" src="//t1.daumcdn.net/adfit/static/kp.js"></script>
	<script type="text/javascript">
	      kakaoPixel('466785529738862663').viewCart();
	</script>
</body>
</html>