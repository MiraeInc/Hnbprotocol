<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main"/>	
<script>
	var removeChk = "N";

	$(window).on('load', function(){
		$('.product-items').productList();
		$('.product-tabs').productTabs();
	});
	
	$(document).ready(function(){
		$("#search_keywords").hide();
		$("#search_helper").hide();
		
		// 검색창 포커스 됐을 때
		$("#top_search_keyword").focusin(function(){
			keyword = $("#top_search_keyword").val();
			
			if(keyword==null || keyword==""){
				$("#search_keywords").show();
			}else if(keyword!=null && keyword!=""){
				$("#top_search_keyword").trigger("keyup");
			}
		});
		
		// 검색영역 제외한 곳 클릭 시 검색어 리스트 hide
		$("body").click(function(e){
			if($("#search_keywords").css("display") == "block" && removeChk == "N"){
				if(!$("#box_search").has(e.target).length){
					$("#search_keywords").hide();
				}
			}else if($("#search_helper").css("display") == "block"){
				if(!$("#box_search").has(e.target).length && removeChk == "N"){
					$("#search_helper").hide();
				}
			}
			removeChk = "N";
		});
		
		// 검색창 X버튼 눌렀을 시 검색어 리스트 초기화
		$(".clear-form").click(function(){
			$("#search_helper").hide();
			$("#search_keywords").show();
		});
		
		// 검색어 입력 시
		$("#top_search_keyword").keyup(function(){
			keyword = $("#top_search_keyword").val();
			if(keyword!=""){
				$("#search_keywords").hide();
				
				$.ajaxSetup({cache:false});
				$.ajax({
					url: getContextPath()+"/ajax/common/topSearchAjax.do",
					data: {"query":keyword},
				 	type: "post",
				 	async: false,
				 	cache: false,
				 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
				 	error: function(request, status, error){ 
				 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
					},
					success: function(data){
						$(".helper_inner").html(data);
						
						$(".search_helper").find(".showcase_item a").on("click", function(e){
							var keyword =$(this).data("keyword");
							goTopSearch(keyword);
						});
						
						$(".search_helper").find(".showcase_tag a").on("click", function(e){
							var keyword =$(this).data("keyword");
							goTopSearch(keyword);
						});
						
						$("#search_helper").show();
					 }
				});
			}else{
				$("#search_helper").hide();
				$("#search_keywords").show();
			}
		});
		
		recentSearchCall();
	});

	// 상품 리스트 더보기
	function productMore() {
		var currentpage = $("#searchFrm input:hidden[name='pageNo']").val();
		var totalcnt = Number("${productCnt}");
		
		currentpage++;
		 $("#searchFrm input:hidden[name='pageNo']").val(currentpage);
		 
		$.ajax({			
			url: getContextPath()+"/ajax/etc/productMoreAjax.do",
			data: $("#searchFrm").serialize(),
		 	type: "post",
		 	async: false,
		 	cache: false,
		 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data){
				$("#product_view_box").append(data);
				
				//목록정렬
				$('.product-items').productList();
				$('.product-tabs').productTabs();
				
				var li_cnt = $("#product_view_box > li").length;
				
				//more버튼 숨기기
				if (totalcnt <= li_cnt) {
					$("#productMoreBtn").hide();
				}
			 }
		});	 
	}

	//해시태그검색
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
	
		hashtag = $(c).val();
		 
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
			
			$("form[name='searchFrm']").attr("action", "${CTX}/etc/searchResult.do");
			$("form[name='searchFrm']").submit();
		 }
	}

	// 스타일검색
	function styleSearchClick() {
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
		 
		 if ($("#wax-hair-1").is(":checked")) {
			 hairstyle1 = "Y";
		 }
		 if ($("#wax-hair-2").is(":checked") ) {
			 hairstyle2 = "Y";
		 }
		 if ($("#wax-hair-3").is(":checked") ) {
			 hairstyle3 = "Y";
		 }
		 if ($("#wax-hair-4").is(":checked") ) {
			 hairstyle4 = "Y";
		 }
		 
		 
		 if ($("#wax-strong-1").is(":checked")) {
			 strong1 = "Y";
		 }
		 if ($("#wax-strong-2").is(":checked") ) {
			 strong2 = "Y";
		 }
		 if ($("#wax-strong-3").is(":checked") ) {
			 strong3 = "Y";
		 }
		 
		 if ($("#wax-glossy-1").is(":checked")) {
			 glossy1 = "Y";
		 }
		 if ($("#wax-glossy-2").is(":checked") ) {
			 glossy2 = "Y";
		 }
		 if ($("#wax-glossy-3").is(":checked") ) {
			 glossy3 = "Y";
		 }
	
		 if (hairstyle1 == "" && hairstyle2 == "" && hairstyle3 == "" &&  hairstyle4 == "" && glossy1 == ""&& glossy2 == ""&& glossy3 == "" && strong1 == "" && strong2 == "" && strong3 == "") {
				$('[data-remodal-id=pop_alert] div.pop-mid p').html("검색할 모발길이, 세팅력, 윤기 조건을 선택해 주세요.");
				$('[data-remodal-id=pop_alert]').remodal().open();
				return false;
		 }
	
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
		$("#searchFrm input:hidden[name='keyword']").val("");
		$("#searchFrm input:hidden[name='pageNo']").val("1");
		
		$("form[name='searchFrm']").attr("action", "${CTX}/etc/searchResult.do");
		$("form[name='searchFrm']").submit();
	}

	// 장바구니 담기
	function goCart(goodsIdx, goodsCd, goodsNm, goodsPrice){
		addCart(goodsIdx, 1, goodsCd, goodsNm, goodsPrice);
	}

	// 바로구매
	function buyNow(goodsIdx){
		var arrOrder = new Array();
		var goodsObj = new Object();
		goodsObj.goodsIdx = Number(goodsIdx);
		goodsObj.goodsCnt = 1;
		arrOrder.push(goodsObj);
		orderNow(arrOrder);
	}

	// 최근 검색어 목록 
	function recentSearchCall(){
		var searchItem = JSON.parse(localStorage.getItem("searchItem"));					// 로컬스토리지 값
		var html = "";
		if(searchItem != null){
			var totCnt = searchItem.length;
			if (totCnt == 0) {
				html = "<li>";
				html += "	<a href='javascript:void(0)'><span>최근 검색어가 존재 하지 않습니다.</span></a>";
				html += "</li>";			
			} else {
				for(var i=0; i < searchItem.length; i++){
					var keyword = searchItem[i].keyword;
					var date = searchItem[i].date;
					html += "<li data-keyword-date='"+date+"'>";
					html += "	<a href='javascript:void(0)' onclick=\"goTopSearch('"+keyword+"')\" ><span>"+keyword+"</span></a> ";
					html += "	<button type='button' onclick=\"removeStorage('"+keyword+"')\" >삭제</button> ";
					html += "</li>";
				}
			}
		}else{
			html = "<li>";
			html += "	<a href='javascript:void(0)'><span>최근 검색어가 존재 하지 않습니다.</span></a>";
			html += "</li>";		
		}
		
		$("#recentSearchList").html(html);				// 리스트
	}

	// 최근 검색어 목록  삭제
	function recentSearchClear(){
		localStorage.removeItem("searchItem");
		recentSearchCall();
	}
	
	// 검색
	function goTopSearch(keyword) {
		$("#top_search_keyword").val($.trim(keyword));
		$("#topSearchFrm").submit();
	}
	
	// 검색
	function topsearch() {
		if( ($.trim($("#top_search_keyword").val()) == "") ){
			alert('검색어를 입력해주세요.'); 
			return false; 						
		} else {
			searchItemSave($.trim($("#top_search_keyword").val()));
			return true;
		}
	}
	
	// 최근검색어 저장
	function searchItemSave(keyword) {
		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth()+1; //January is 0!
		var yyyy = today.getFullYear();
		if (mm < 10) {
			mm ="0"+mm;
		}
		if (dd < 10) {
			dd ="0"+dd;
		}
		
		var curr = mm+"."+dd;
		var array = new Array();
		var obj = new Object();
		obj.keyword = keyword;
		obj.date = curr;
		
		//최근검색어
		var searchItem = JSON.parse(localStorage.getItem("searchItem"));
		
		if(searchItem != null){
			
			for(var i=0; i < searchItem.length; i++){
				if(keyword != searchItem[i].keyword){
					var itemObj = new Object();
					itemObj.keyword = searchItem[i].keyword;
					itemObj.date = searchItem[i].date;
					array.push(itemObj);
				}
			}
			
			array.unshift(obj);
			
			if(searchItem.length > 9){					// 10개 제한
				array.pop();
			}
			
		}else{
			array.push(obj);
		}
		localStorage.setItem("searchItem", JSON.stringify(array));
	}
	
	// 로컬 스토리지 검색어 삭제
	function removeStorage(keyword){
		removeChk = "Y";
		var array = new Array();
		var obj = new Object();
		
		var searchItem = JSON.parse(localStorage.getItem("searchItem"));
		localStorage.removeItem("searchItem");
		
		if(searchItem != null){
			for(var i=0; i < searchItem.length; i++){
				if(keyword != searchItem[i].keyword){
					var itemObj = new Object();
					itemObj.keyword = searchItem[i].keyword;
					itemObj.date = searchItem[i].date;
					array.push(itemObj);
				}
			}
		}else{
			array.push(obj);
		}
		localStorage.setItem("searchItem", JSON.stringify(array));
		recentSearchCall();
	}
	if("<spring:message code='server.status'/>" == "LIVE") {
		<!-- Facebook Pixel Code -->
		fbq('track', 'Search',  { 'search_string' : '${searchVo.keyword}'});
	}
</script>
</head>
<body>
	<div class="content comm-search">
		
		<div class="box-search" id="box_search"> <%-- 검색영역 --%>
			<form action="${CTX}/etc/searchResult.do" id="topSearchFrm" method="post" onsubmit="return topsearch();">
			<div class="row">
				<div class="col col-12">
					<div class="form-control search-keyword">
						<input type="text" id="top_search_keyword" name="keyword" class="input input-search-keyword" placeholder="키워드를 입력해주세요." value="${keyword}">
						<input type="submit" class="btn-search-keyword" onclick="">
					</div>
				</div>
			</div>
			</form>
			
			<div class="search_keywords" id="search_keywords"> <%-- 최근 검색어 --%>
				<div class="keyword_inner">
					<div class="search_recent">
						<strong class="keyword_title">최근 검색어</strong>
						<ol id="recentSearchList">
						
						</ol>
						<button type="button" class="keyword_delete" onclick="recentSearchClear();">검색기록 전체 삭제</button>
					</div>
				</div>
			</div> <%-- //최근 검색어 --%>
				
			<div class="search_helper" id="search_helper"> <%-- 자동 완성 --%>
				<div class="helper_inner">
					
				</div>
			</div> <%-- //자동 완성 --%>
		</div> <%-- //검색영역 --%>
		
		<c:if test = "${!empty(searchVo.keyword)}">
		<div class="search-result">
			“<strong><c:out value="${searchVo.keyword}"></c:out></strong>”<br>로 검색된 결과
		</div>
		</c:if>
		
		<c:if test = "${(! empty searchVo.keyword) or (searchVo.hairstyle10 eq 'Y') or (searchVo.hairstyle20 eq 'Y') or (searchVo.hairstyle30 eq 'Y') or (searchVo.hairstyle40 eq 'Y') or (searchVo.strong1 eq 'Y') or (searchVo.strong2 eq 'Y') or (searchVo.strong3 eq 'Y') or (searchVo.glossy1 eq 'Y') or (searchVo.glossy2 eq 'Y') or (searchVo.glossy3 eq 'Y')}">
			<c:if test = "${(searchVo.hairstyle10 eq 'Y') or (searchVo.hairstyle20 eq 'Y') or (searchVo.hairstyle30 eq 'Y') or (searchVo.hairstyle40 eq 'Y') or (searchVo.strong1 eq 'Y') or (searchVo.strong2 eq 'Y')or (searchVo.strong3 eq 'Y')  or (searchVo.glossy1 eq 'Y') or (searchVo.glossy2 eq 'Y') or (searchVo.glossy3 eq 'Y')}">
				<c:if test ="${productCnt  > 0}">
				<div class="pick-wax search-nodata">
					<div class="nodata-head">
						<strong><em>PICK YOUR WAX</em>에서</strong>
						<div class="wax-picker">
							<c:if test="${searchVo.hairstyle10 eq 'Y'.toString() }">
							<div class="form-wax">
								<input type="checkbox" class="form-control wax-hair-1" checked="checked">
								<label for="">베리숏</label>
							</div>
							</c:if>
							<c:if test="${searchVo.hairstyle20 eq 'Y'.toString() }">
							<div class="form-wax">
								<input type="checkbox" class="form-control wax-hair-2" checked="checked">
								<label for="">숏</label>
							</div>
							</c:if>
							<c:if test="${searchVo.hairstyle30 eq 'Y'.toString() }">
							<div class="form-wax">
								<input type="checkbox" class="form-control wax-hair-3" checked="checked">
								<label for="">미디움 숏</label>
							</div>
							</c:if>
							<c:if test="${searchVo.hairstyle40 eq 'Y'.toString() }">
							<div class="form-wax">
								<input type="checkbox" class="form-control wax-hair-4" checked="checked">
								<label for="">미디움 이상</label>
							</div>
							</c:if>
							
							<c:if test="${searchVo.strong1 eq 'Y'.toString()}">
							<div class="form-wax">
								<input type="checkbox" class="form-control wax-strong-1" checked="checked">
								<label for="">엑스트라 하드</label>
							</div>
							</c:if>
							<c:if test="${searchVo.strong2 eq 'Y'.toString()}">
							<div class="form-wax">
								<input type="checkbox" class="form-control wax-strong-2" checked="checked">
								<label for="">하드</label>
							</div>
							</c:if>
							<c:if test="${searchVo.strong3 eq 'Y'.toString()}">
							<div class="form-wax">
								<input type="checkbox" class="form-control wax-strong-3" checked="checked">
								<label for="">내츄럴</label>
							</div>
							</c:if>
							
							<c:if test="${searchVo.glossy1 eq 'Y'.toString()}">
							<div class="form-wax">
								<input type="checkbox" class="form-control wax-glossy-1" checked="checked">
								<label for="">강함</label>
							</div>
							</c:if>
							<c:if test="${searchVo.glossy2 eq 'Y'.toString()}">
							<div class="form-wax">
								<input type="checkbox" class="form-control wax-glossy-2" checked="checked">
								<label for="">보통</label>
							</div>
							</c:if>
							<c:if test="${searchVo.glossy3 eq 'Y'.toString()}">
							<div class="form-wax">
								<input type="checkbox" class="form-control wax-glossy-3" checked="checked">
								<label for="">약함</label>
							</div>
							</c:if>
						</div>
					</div>
				</div>
				</c:if>
			</c:if>
		<div class="search-result-item">
			<c:if test = "${!empty(searchVo.keyword)}">
				<c:if test ="${productCnt == 0}">
					<div class="no-contents">
						<p>
							검색 결과가 없습니다.<br />
							다른 검색어를 입력하시거나<br />
							철자와 띄어쓰기를 확인해 주세요.
						</p>
					</div>
				</c:if>
			</c:if>
			
			<c:if test ="${productCnt > 0}">
			<p class="search-result-count">총 <span>${productCnt}</span>개의 상품이 있습니다.</p>
			<div class="product-items">
				<ul id="product_view_box" class="type-default">
					<c:forEach var="list" items="${productList}" varStatus="idx">
						<li <c:if test="${list.soldoutYn eq 'Y'}">class="soldout"</c:if>>
							<div class="item">
								<div class="item-wrap">
									<a href="${CTX}/product/productView.do?goodsCd=${list.goodsCd}&choiceCateIdx=${list.choiceCateIdx}" class="item-anchor">
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
											<img src="${IMGPATH}/goods/${list.goodsIdx}/${imgSplit[0]}_M.${imgSplit[1]}">
										</div>
										<p class="item-summary">${list.shortInfo}</p>
										<p class="item-name">${list.goodsTitle}</p>
										<div class="item-price">
											<fmt:formatNumber var="discountRate" value="${list.discountRate}" type="number" maxFractionDigits="0"/>
											<del class="origin"><c:if test="${discountRate != 0}" ><fmt:formatNumber value="${list.price}" /></c:if></del>
											<c:if test="${discountRate != 0}" >
												<span class="discount"><em>${discountRate}</em>%</span>
											</c:if>
											<span class="price"><fmt:formatNumber value="${list.discountPrice}" /></span>
										</div>
									</a>
									<a href="javascript:" id="cartBtn" class="btn-cart" onclick="goCart('${list.goodsIdx}','<c:out value="${list.goodsCd}"/>','<c:out value="${list.goodsTitle}"/>','<fmt:formatNumber value="${list.discountPrice}" groupingUsed="false"/>');"><span class="hide">장바구니</span></a>
								</div>
								<c:if test="${list.autoCouponYn eq 'Y' || list.onlineYn eq 'Y'}">
								<div class="item-etc">
									<c:if test="${list.autoCouponYn eq 'Y'}">
										<img src="${CTX}/images/${DEVICE}/common/ico_coupon_recommend.png" class="coupon" alt="COUPON" />
									</c:if>
									<c:if test="${list.onlineYn eq 'Y'}">
										<img src="${CTX}/images/${DEVICE}/common/ico_coupon_mobile.png" class="coupon" alt="MOBILE ONLY" />
									</c:if>
								</div>
								</c:if>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
			</c:if>
			<c:if test="${fn:length(productList) < productCnt }">
				<div class="btn-box confirm" id="productMoreBtn">
					<button type="button" class="btn btn-more" onclick="productMore();"><span class="txt">MORE</span></button>
				</div>
				<br>
			</c:if>
		</div>
		</c:if>
		
		
		<%-- 
		<div class="pick-hash"> 해시태그 검색 영역
			<div class="pick-head">
				<strong>#</strong>
				<p>트렌드에 맞게! 스타일 태그 검색 해보기!</p>
			</div>
			<div class="hash-picker">
				<c:set var="hashtagnames" value=""/>
				<c:set var="coloridx" value=""/>
				<c:forEach var="list" items="${hashList}" begin="0" end="4" varStatus="idx">
				<div class="form-hash-wrap">
					<div class="form-hash">
						<input type="checkbox" name="hashtagchk" value="${list.hashtagNm}" class="form-control hash-color-${idx.count}" id="hasharea${idx.index}" onclick="hashtagSearchClick(this);">
						<label for="hasharea${idx.index}">#${list.hashtagNm}</label>
					</div>
				</div>
				</c:forEach>
			</div>
		</div> //해시태그 검색 영역
				
		<div class="pick-wax"> 왁스 검색 영역
			<div class="pick-head">
				<strong>PICK YOUR WAX</strong>
				<p>방황하지 말고 한 번에 찾자! 내 스타일에 맞는 왁스 검색하기!</p>
			</div>
			
			<div class="wax-picker">
				<div class="wax-step">
					<div class="wax-step-label">
						<span>
							<b>1</b>
							<p>모발 길이</p>
						</span>
					</div>
					<div class="wax-hair row">
						<div class="form-wax col col-3">
							<input type="checkbox" class="form-control wax-hair-1" id="wax-hair-1" <c:if test="${searchVo.hairstyle10 eq 'Y'.toString()  }">checked</c:if>>
							<label for="wax-hair-1">베리숏</label>
						</div>
						<div class="form-wax col col-3">
							<input type="checkbox" class="form-control wax-hair-2" id="wax-hair-2" <c:if test="${searchVo.hairstyle20 eq 'Y'.toString()   }">checked</c:if>>
							<label for="wax-hair-2">숏</label>
						</div>
						<div class="form-wax col col-3">
							<input type="checkbox" class="form-control wax-hair-3" id="wax-hair-3" <c:if test="${searchVo.hairstyle30 eq 'Y'.toString()   }">checked</c:if>>
							<label for="wax-hair-3">미디움 숏</label>
						</div>
						<div class="form-wax col col-3">
							<input type="checkbox" class="form-control wax-hair-4" id="wax-hair-4" <c:if test="${searchVo.hairstyle40 eq 'Y'.toString() }">checked</c:if>>
							<label for="wax-hair-4">미디움 이상</label>
						</div>
					</div>
				</div>
				<div class="wax-step">
					<div class="wax-step-label">
						<span>
							<b>2</b>
							<p>셋팅력</p>
						</span>
					</div>
					<div class="wax-strong row">
						<div class="form-wax col col-4">
							<input type="checkbox" class="form-control wax-strong-1" id="wax-strong-1" <c:if test="${searchVo.strong1 eq 'Y'.toString()}">checked</c:if>>
							<label for="wax-strong-1">엑스트라 하드</label>
						</div>
						<div class="form-wax col col-4">
							<input type="checkbox" class="form-control wax-strong-2" id="wax-strong-2" <c:if test="${searchVo.strong2 eq 'Y'.toString()}">checked</c:if>>
							<label for="wax-strong-2">하드</label>
						</div>
						<div class="form-wax col col-4">
							<input type="checkbox" class="form-control wax-strong-3" id="wax-strong-3" <c:if test="${searchVo.strong3 eq 'Y'.toString()}">checked</c:if>>
							<label for="wax-strong-3">내츄럴</label>
						</div>
					</div>
				</div>
				<div class="wax-step">
					<div class="wax-step-label">
						<span>
							<b>3</b>
							<p>윤기</p>
						</span>
					</div>
					<div class="wax-glossy row">
						<div class="form-wax col col-4">
							<input type="checkbox" class="form-control wax-glossy-1" id="wax-glossy-1" <c:if test="${searchVo.glossy1 eq 'Y'.toString()}">checked</c:if>>
							<label for="wax-glossy-1">강함</label>
						</div>
						<div class="form-wax col col-4">
							<input type="checkbox" class="form-control wax-glossy-2" id="wax-glossy-2" <c:if test="${searchVo.glossy2 eq 'Y'.toString()}">checked</c:if>>
							<label for="wax-glossy-2">보통</label>
						</div>
						<div class="form-wax col col-4">
							<input type="checkbox" class="form-control wax-glossy-3" id="wax-glossy-3" <c:if test="${searchVo.glossy3 eq 'Y'.toString()}">checked</c:if>>
							<label for="wax-glossy-3">약함</label>
						</div>
					</div>
				</div>
			</div>
			<div class="pick-foot btn-box">
				<a href="javascript:void(0);" class="btn black btn-search-wax" onclick="styleSearchClick();"><span class="txt">검색</span></a>
			</div>
		</div> //왁스 검색 영역
		
		<c:if test = "${(searchVo.hairstyle10 eq 'Y') or (searchVo.hairstyle20 eq 'Y') or (searchVo.hairstyle30 eq 'Y') or (searchVo.hairstyle40 eq 'Y') or (searchVo.strong1 eq 'Y') or (searchVo.strong2 eq 'Y')or (searchVo.strong3 eq 'Y')  or (searchVo.glossy1 eq 'Y') or (searchVo.glossy2 eq 'Y') or (searchVo.glossy3 eq 'Y')}">
			<c:if test ="${productCnt == 0}">
			<div class="pick-wax search-nodata">
				<div class="nodata-head">
					<strong><em>PICK YOUR WAX</em>에서</strong>
					<div class="wax-picker">
						<c:if test="${searchVo.hairstyle10 eq 'Y'.toString() }">
						<div class="form-wax">
							<input type="checkbox" class="form-control wax-hair-1" checked="checked">
							<label for="">베리숏</label>
						</div>
						</c:if>
						<c:if test="${searchVo.hairstyle20 eq 'Y'.toString() }">
						<div class="form-wax">
							<input type="checkbox" class="form-control wax-hair-2" checked="checked">
							<label for="">숏</label>
						</div>
						</c:if>
						<c:if test="${searchVo.hairstyle30 eq 'Y'.toString() }">
						<div class="form-wax">
							<input type="checkbox" class="form-control wax-hair-3" checked="checked">
							<label for="">미디움 숏</label>
						</div>
						</c:if>
						<c:if test="${searchVo.hairstyle40 eq 'Y'.toString() }">
						<div class="form-wax">
							<input type="checkbox" class="form-control wax-hair-4" checked="checked">
							<label for="">미디움 이상</label>
						</div>
						</c:if>
						
						<c:if test="${searchVo.strong1 eq 'Y'.toString()}">
						<div class="form-wax">
							<input type="checkbox" class="form-control wax-strong-1" checked="checked">
							<label for="">엑스트라 하드</label>
						</div>
						</c:if>
						<c:if test="${searchVo.strong2 eq 'Y'.toString()}">
						<div class="form-wax">
							<input type="checkbox" class="form-control wax-strong-2" checked="checked">
							<label for="">하드</label>
						</div>
						</c:if>
						<c:if test="${searchVo.strong3 eq 'Y'.toString()}">
						<div class="form-wax">
							<input type="checkbox" class="form-control wax-strong-3" checked="checked">
							<label for="">내츄럴</label>
						</div>
						</c:if>
						
						<c:if test="${searchVo.glossy1 eq 'Y'.toString()}">
						<div class="form-wax">
							<input type="checkbox" class="form-control wax-glossy-1" checked="checked">
							<label for="">강함</label>
						</div>
						</c:if>
						<c:if test="${searchVo.glossy2 eq 'Y'.toString()}">
						<div class="form-wax">
							<input type="checkbox" class="form-control wax-glossy-2" checked="checked">
							<label for="">보통</label>
						</div>
						</c:if>
						<c:if test="${searchVo.glossy3 eq 'Y'.toString()}">
						<div class="form-wax">
							<input type="checkbox" class="form-control wax-glossy-3" checked="checked">
							<label for="">약함</label>
						</div>
						</c:if>
					</div>
				</div>
				<div class="nodata-content">
					 <p class="nodata-text">
						 해당 되는 상품이 없습니다. <br/>
						 아래 내용을 참고하시어 재검색 해주세요.
					 </p>
					<ul class="nodata-recommend">
						<li>
							<div class="thumb">
								<img src="${CTX}/images/m/common/ico_srch_vs.png" alt="VERY SHORT" />
								<img src="${CTX}/images/m/common/ico_srch_sh.png" alt="SHORT HAIR" />
							</div>
							<p>셋팅력이 강하고 윤기가 없는 왁스가 추천됩니다.</p>
						</li>
						<li>
							<div class="thumb">
								<img src="${CTX}/images/m/common/ico_srch_ms.png" alt="VERY SHORT" />
								<img src="${CTX}/images/m/common/ico_srch_mh.png" alt="SHORT HAIR" />
							</div>
							<p>적당한 셋팅력과 윤기가 있는 왁스가 추천됩니다.</p>
						</li>
					</ul>
				</div>
			</div>
			</c:if>
		</c:if>
		 --%>
	</div>
	
<form name="searchFrm" id="searchFrm">
	<input type="hidden" name="hairstyle10" id="hairstyle10" value="${searchVo.hairstyle10}">
	<input type="hidden" name="hairstyle20" id="hairstyle20" value="${searchVo.hairstyle20}">
	<input type="hidden" name="hairstyle30" id="hairstyle30" value="${searchVo.hairstyle30}">
	<input type="hidden" name="hairstyle40" id="hairstyle40" value="${searchVo.hairstyle40}">
	<input type="hidden" name="strong1" id="strong1" value="${searchVo.strong1}">
	<input type="hidden" name="strong2" id="strong2" value="${searchVo.strong2}">
	<input type="hidden" name="strong3" id="strong3" value="${searchVo.strong3}">
	<input type="hidden" name="glossy1" id="glossy1" value="${searchVo.glossy1}">
	<input type="hidden" name="glossy2" id="glossy2" value="${searchVo.glossy2}">
	<input type="hidden" name="glossy3" id="glossy3" value="${searchVo.glossy3}">
	<input type="hidden" name="hashtag" id="hashtag" value="${searchVo.hashtag}">
	<input type="hidden" name="keyword" id="keyword" value="${searchVo.keyword}">
	<input type="hidden" name="pageNo" id="pageNo" value="1">
</form>

</body>
</html>