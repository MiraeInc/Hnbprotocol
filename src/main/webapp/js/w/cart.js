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
						var cartCnt = Number(uncomma($("#menuCart .lnk-cart .cnt").text()));
						if(cartCnt == 0){
							getCartListAjax();
						}else{
							$("#menuCart .lnk-cart .cnt").text(comma(cartCnt+1));
							$("#header_cart_cnt").text(comma(cartCnt+1)+"개");
						}

						$('[data-remodal-id=pop_go_cart] div.pop-mid p').html(data.msg);
						$('[data-remodal-id=pop_go_cart]').remodal().open();
					}
					return true;
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
						 alert("찜 했습니다.");
					 }else{													// 찜 해제
						 if($this != null){
							 $($this).removeClass("active");
						 }
						 alert("찜 해제했습니다.");
					 }
					 
					// 상품 상세 위시 총 건수
					 $($this).attr("data-like",data.resultCnt);
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
						$('[data-remodal-id=pop_alert] div.pop-mid p').html(data.msg);
						$('[data-remodal-id=pop_alert]').remodal().open();
					}
				}
			 }
		});
	}
	
