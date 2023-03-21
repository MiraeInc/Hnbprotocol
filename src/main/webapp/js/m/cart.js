	dataLayer = [];	// Google Tag Manager

	function getContextPath(){
	    var offset=location.href.indexOf(location.host)+location.host.length;
	    var ctxPath=location.href.substring(offset,location.href.indexOf('/',offset+1));
	    return ctxPath;
	}
/*
	// 세션ID
	function getSessionId(){
		if(localStorage.getItem("mandomSessionId") == null){
			localStorage.setItem("mandomSessionId","<%=session.getId()%>");
		}
	
		return localStorage.getItem("mandomSessionId");
	}	
*/
	// 장바구니 저장
	function addCart(goodsIdx, goodsCnt, goodsCd, goodsNm, goodsPrice){
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
		
		addCartObject(arr);
		
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
	
	// 장바구니 저장
	function addCartObject(arrayOfGoods){		
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
					if(data.msg != ""){
					
						
						if(confirm("장바구니에 담았습니다.\n장바구니로 이동하시겠습니까?")){
							location.href=getContextPath()+"/cart.do";
						}
						
						var cartCnt = Number(uncomma($("#header_cart_cnt").text()));
						$("#header_cart_cnt").text(comma(cartCnt+1));
						if ($("#btnMenu").attr("data-cart-num"))
						{
							$("#btnMenu").attr("data-cart-num",comma(cartCnt+1));
						}
						if ($("#btnCart").attr("data-cart-num"))  //모바일
						{
							$("#btnCart").attr("data-cart-num",comma(cartCnt+1));
						}
					}
					return true;
				}else{
					if(data.msg != ""){
						alert(data.msg);
					}
					return false;
				}
			 }
		});
	}

	// 찜하기
	function addWishList($this, goodsIdx){		
		$.ajax({
			url: getContextPath()+"/ajax/wish/addWishAjax.do",
		 	data: {
		 		"goodsIdx"	: goodsIdx
		 	},
		 	type: "post",
		 	async: false,
		 	cache: false,
		 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			 },
			 success: function(data){
				 if(data.result == true){
					 if(data.wishFlag == true){				// 찜 하기
						if($this != null){
							$($this).addClass("active");
						}
						alert("찜했습니다.");
					 }else{
						 if($this != null){
							 $($this).removeClass("active");
						 }
						 alert("찜 해제했습니다.");
					 }
					 
					// 상품 상세 위시 총 건수
//					$("#addProductWish").attr("data-like",data.resultCnt);
					$("#wishBtn").attr("data-like",data.resultCnt);
					
				 }else{
					 if(data.errorCode == "99"){
						if(confirm("찜하려면 로그인 해야 합니다. 로그인 하시겠습니까?")){
							location.href=getContextPath()+"/login/loginPage.do?refererYn=Y"; 
						}
					 }else{
						 if(data.msg != ""){
							 alert(data.msg);
						 }
					 }
				 }
			 }
		});
	}

	// 바로 구매
	function orderNow(arrayOfGoods){
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
					$("#commonOrderGoodsInfoListStr").val(JSON.stringify(arrayOfGoods));
					var frm = document.commonOrderForm;
					$("#commonSessionId").val(getSessionId());
					frm.action=getContextPath()+"/order/cartOrderProc.do";
					frm.submit();
				}else{
					if(data.msg != ""){
						alert(data.msg);
					}
				}
			 }
		});
	}
	
