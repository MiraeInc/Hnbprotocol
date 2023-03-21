<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_activity" />
<meta name="menu_no" content="mypage_070" />
<script>

$(document).ready(function(){
	var inquiryType = "${detail.inquiryType}"; // 문의 유형
	
	// 문의 유형 - 검색 폼 노출
	if(inquiryType == "INQUIRY_TYPE10"){
		$("#orderSearch").hide();
		$("#productSearch").show();
	}else if(inquiryType == "INQUIRY_TYPE50" || inquiryType == "INQUIRY_TYPE70" || inquiryType == "INQUIRY_TYPE80"){
		$("#orderSearch").hide();
		$("#productSearch").hide();
	}else{
		$("#orderSearch").show();
		$("#productSearch").show();
	}
});

// 문의 유형 변경
function changeType(){
	var type = $("#inquiryType").val();
	
	// 문의 유형 - 검색 폼 노출
	if(type == "INQUIRY_TYPE10"){
		$("#orderSearch").hide();
		$("#productSearch").show();
	}else if(type == "INQUIRY_TYPE50" || type == "INQUIRY_TYPE70" || type == "INQUIRY_TYPE80"){
		$("#orderSearch").hide();
		$("#productSearch").hide();
	}else{
		$("#orderSearch").show();
		$("#productSearch").show();
	}
}

//주문 검색
function orderSearch(){
	$("#inquiryPopType").val("order");
	$("#popOrder").empty();
	
	$.ajax({
		url: getContextPath()+"/ajax/mypage/inquiry/inquiryOrderAjax.do",
	 	data: {
	 		"userOrderCd"				: $("#userOrderCd").val(),
	 		"userOrderGoodsNm"   : $("#userOrderGoodsNm").val(),
	 		"pageNo"					: $("#orderPageNo").val()
	 	},
	 	type: "post",
	 	async: false,
	 	cache: false,
	 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
	 	error: function(request, status, error){ 
	 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
		 },
		 success: function(data){
			 if(data.resultCnt == 0){
				 alert("검색하신 내용이 없습니다.");
				 $("#pop_goodsSearch").removeClass("active");
				 return false;
			 }
			 if(data.result == true){
				 if(data.resultCnt > 0){
					var html = "";
					html+="<div class='message'> ";
					html+="		※ <span class='em'>주문번호</span> 혹은 <span class='em'>상품명</span>을 선택해 주세요. ";
					html+="</div> ";
					html+="<div class='order-form first'> ";
					var orderIdx1 = "";
					
					for(var i=0; i< data.resultList.length; i++){
						var orderIdx2 = data.resultList[i].orderIdx;
						
						if(orderIdx1 != orderIdx2 && i != 0){
							html+="			</ul> ";
							html+="		</div> ";
							html+="</div> ";
						}
						
						if(orderIdx1 != orderIdx2){
							html+="<div class='form-group'> ";
							html+="		<div class='form-title'> ";
							html+="			<h3 class='title'>"+data.resultList[i].orderDt+"<br/><span class='sub'>("+data.resultList[i].orderCd+")</span></h3> ";
							html+="			<button type='button' class='btn black btn-select' onclick=\"selectOrderCd('"+data.resultList[i].orderIdx+"','"+data.resultList[i].orderDetailIdx+"','"+data.resultList[i].orderCd+"');\"><span class='txt'>주문번호 선택</span></button> ";
							html+="		</div> ";
							html+="		<div class='order-goods type-srch'> ";
							html+="			<ul> ";
						}
						
						html+="				<li> ";
						html+="					<div class='item'> ";
						html+="						<div class='item-view'> ";
						html+="							<div class='view-thumb'> ";
						var imgSplit = data.resultList[i].mainFile.split(".");
						html+="								<img src='${IMGPATH}/goods/"+data.resultList[i].goodsIdx+"/"+imgSplit[0]+"_S."+imgSplit[1]+"' alt='"+data.resultList[i].goodsNm+"'/> ";
						html+="							</div> ";
						html+="							<div class='view-info'> ";
						html+="								<p class='name'>"+data.resultList[i].goodsNm+" ";
						if(data.resultList[i].orderCnt > 1){
							html+="									("+data.resultList[i].orderCnt+") ";
						}
						html+="								</p> ";
						html+="								<p class='price'>"+comma(data.resultList[i].price)+"</p> ";
						html+="							</div> ";
						html+="						</div> ";
						html+="						<div class='item-payment'> ";
						html+="							<dt><span class='em'>"+data.resultList[i].orderStatusCdNm+"</span></dt> ";
						html+="							<dd><button class='btn full' onclick=\"selectGoodsNm('"+data.resultList[i].orderIdx+"','"+data.resultList[i].orderDetailIdx+"','"+data.resultList[i].orderCd+"','"+data.resultList[i].goodsNm+"');\"><span class='txt'>상품 선택</span></button></dd> ";
						html+="						</div> ";
						html+="					</div> ";
						html+="				</li> ";
						
						if(i == data.resultList.length-1){
							html+="			</ul> ";
							html+="		</div> ";
							html+="</div> ";
						}
						
						orderIdx1 = data.resultList[i].orderIdx;
					}
					html+="</div> ";
					html+="<div class='pagin-nav'> ";
					html+="		<c:out value='"+data.page.pageStr+"' escapeXml='false'/> ";
					html+="</div> ";
					
					$("#popOrder").append(html);
					
					//$("#pop_orderSearch").addClass("active");
				 }
			 }else{
				 if(data.msg != ""){
					 alert(data.msg);
				 }
			 }
		 }
	});
}

// 상품 검색
function goodsSearch(){
	$("#inquiryPopType").val("goods");
	$("#popGoods").empty();
	
	$.ajax({
		url: getContextPath()+"/ajax/mypage/inquiry/inquiryGoodsAjax.do",
	 	data: {
	 		"userQuestnGoodsNm"	: $("#userQuestnGoodsNm").val(),
	 		"pageNo"					: $("#goodsPageNo").val()
	 	},
	 	type: "post",
	 	async: false,
	 	cache: false,
	 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
	 	error: function(request, status, error){ 
	 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
		 },
		 success: function(data){
			 if(data.resultCnt == 0){
				 alert("검색하신 내용이 없습니다.");
				 $("#pop_goodsSearch").removeClass("active");
				 return false;
			 }
			 if(data.result == true){
				 if(data.resultCnt > 0){
					var html = "";
					html+="<div class='message'> ";
					html+="		※ <span class='em'>상품명</span>을 선택해 주세요. ";
					html+="</div> ";
					html+="	<div class='order-form first'> ";
					html+="		<div class='form-group'> ";
					html+="			<div class='order-goods type-srch'> ";
					html+="				<ul> ";
					for(var i=0; i< data.resultList.length; i++){
						html+="					<li> ";
						html+="						<div class='item'> ";
						html+="							<div class='item-view'> ";
						html+="								<div class='view-thumb'> ";
						var imgSplit = data.resultList[i].mainFile.split(".");
						html+="									<img src='${IMGPATH}/goods/"+data.resultList[i].goodsIdx+"/"+imgSplit[0]+"_S."+imgSplit[1]+"' alt='"+data.resultList[i].goodsNm+"'/> ";
						html+="								</div> ";
						html+="								<div class='view-info'> ";
						html+="									<p class='name'>"+data.resultList[i].goodsNm+"</p> ";
						html+="									<button type='button' class='btn btn-select' onclick=\"goodsSelect('"+data.resultList[i].goodsIdx+"','"+data.resultList[i].goodsNm+"');\"><span class='txt'>상품 선택</span></button> ";
						html+="								</div> ";
						html+="							</div> ";
						html+="						</div> ";
						html+="					</li> ";
					}
					html+="					</ul> ";
					html+="				</div> ";
					html+="			</div> ";
					html+="		</div> ";
					html+="	</div> ";
					html+="<div class='pagin-nav'> ";
					html+="		<c:out value='"+data.page.pageStr+"' escapeXml='false'/> ";
					html+="</div> ";
					
					$("#popGoods").append(html);
					
					//$("#pop_goodsSearch").addClass("active");
				 }
			 }else{
				 if(data.msg != ""){
					 alert(data.msg);
				 }
			 }
		 }
	});
}

//페이지 이동
function goPage(page){
	var type = $("#inquiryPopType").val();
	
	if(type=="goods"){
		$("#goodsPageNo").val(page);
		$("#popGoods").empty();
		
		goodsSearch();
	}else if(type=="order"){
		$("#orderPageNo").val(page);
		$("#popOrder").empty();
		
		orderSearch();
	}
}

// 상품검색 - 상품선택
function goodsSelect(goodsIdx, goodsNm){
	$("#userQuestnGoodsNm").val(goodsNm);
	$("#questnGoodsIdx").val(goodsIdx);
	$("#questnGoodsNm").val(goodsNm);

	$("#goodsPageNo").val("1");
	$("#pop_goodsSearch").removeClass("active");
}

// 주문검색 - 주문선택
function selectOrderCd(orderIdx, orderDetailIdx, orderCd){
	$("#userOrderCd").val(orderCd);
	$("#orderIdx").val(orderIdx);
	$("#orderDetailIdx").val(orderDetailIdx);
	$("#orderCd").val(orderCd);
	
	$("#orderPageNo").val("1");
	$("#pop_orderSearch").removeClass("active");
}

// 주문검색 - 상품선택
function selectGoodsNm(orderIdx, orderDetailIdx, orderCd, goodsNm){
	$("#userOrderCd").val(orderCd);
	$("#orderIdx").val(orderIdx);
	$("#orderDetailIdx").val(orderDetailIdx);
	$("#orderCd").val(orderCd);
	$("#userOrderGoodsNm").val(goodsNm);
	$("#orderGoodsNm").val(goodsNm);
	
	$("#orderPageNo").val("1");
	$("#pop_orderSearch").removeClass("active");
}


// 등록, 수정
function goSave(gubun){
	if($.trim($("#inquiryType").val()) ==""){ // 문의 유형
		alert("문의 유형을 선택 해 주세요."); 
		$("#inquiryType").focus(); 
		return false;
	}
	if($.trim($("#questnEmail").val()) ==""){ // 이메일 주소
		alert("이메일 주소를 입력 해 주세요."); 
		$("#questnEmail").focus(); 
		return false;
	}
	if($.trim($("#questnPhone").val()) ==""){ // 휴대폰 번호
		alert("휴대폰 번호를 입력 해 주세요."); 
		$("#questnPhone").focus(); 
		return false;
	}
	if($.trim($("#questnTitle").val()) ==""){ // 제목
		alert("제목을 입력 해 주세요."); 
		$("#questnTitle").focus(); 
		return false;
	}
	if($.trim($("#questnDesc").val()) ==""){ // 내용
		alert("내용을 입력 해 주세요."); 
		$("#questnDesc").focus(); 
		return false;
	}
	
	if(confirm(gubun+" 하시겠습니까?") == true){
		if($("#statusFlag").val()==null || $("#statusFlag").val()==""){
			$("#statusFlag").val("I");
		}
		
		var frm = document.inquiryForm;
		frm.action = "${CTX}/mypage/inquiry/inquirySave.do";
		frm.submit();
	}
}

// 첨부파일 삭제
function deleteFile(t) {
	$(t).parent().remove();
}

</script>
</head>
<body>
<form name="inquiryForm" id="inquiryForm" method="post" onsubmit="return false;">
	<input type="hidden" name="inquiryIdx" id="inquiryIdx" value="${detail.inquiryIdx}"/>
	<input type="hidden" name="statusFlag" id="statusFlag" value="${VO.statusFlag}"/>
	<input type="hidden" name="goodsPageNo" id="goodsPageNo" value="1"/> <%-- 상품검색 페이징 --%>
	<input type="hidden" name="orderPageNo" id="orderPageNo" value="1"/> <%-- 주문검색 페이징 --%>
	<input type="hidden" name="inquiryPopType" id="inquiryPopType" value=""/> <%-- 주문검색, 상품검색 판단 --%>
	
	<div class="content comm-order comm-mypage mypage-qna">

        <div class="page-body">
            <div class="qna-category">
				<div class="form-control">
					<div class="opt_select">
                        <select name="inquiryType" id="inquiryType" onchange="changeType();">
                            <option value="">문의 유형</option>
                            <c:forEach var="inquiryType" items="${inquiryType}" varStatus="idx">
								<option value="${inquiryType.commonCd}" <c:if test="${detail.inquiryType eq inquiryType.commonCd}">selected</c:if>>${inquiryType.cdNm}</option>
							</c:forEach>
                        </select>
                    </div>
				</div>                    
            </div>
           
			<div class="order-form form-qna">
				<div class="form-group">
					<div class="row-srch" id="orderSearch">
						<div class="row">
							<div class="form-label"><label for="">주문 검색</label></div>
							<div class="col col-9">
								<div class="form-control">
									<input type="text" name="userOrderCd" id="userOrderCd" class="input" placeholder="주문번호" value="${detail.orderCd}"/>
									<input type="hidden" name="orderIdx" id="orderIdx" value="${detail.orderIdx}"/>
									<input type="hidden" name="orderDetailIdx" id="orderDetailIdx" value="${detail.orderDetailIdx}"/>
									<input type="hidden" name="orderCd" id="orderCd" value="${detail.orderCd}"/>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col col-9">
								<div class="form-control">
									<input type="text" name="userOrderGoodsNm" id="userOrderGoodsNm" class="input" placeholder="상품명" />
									<input type="hidden" name="orderGoodsNm" id="orderGoodsNm" value="${detail.orderGoodsNm}"/>
								</div>
							</div>
						</div>
						<div class="col col-3 col-srch">
							<button type="button" class="btn black full btn-srch" data-toggle="popup" data-target="#pop_orderSearch" onclick="orderSearch();"><span class="hide">검색</span></button>
						</div>
					</div>
		
					<div class="row" id="productSearch">
						<div class="form-label"><label for="">상품 검색</label></div>
						<div class="col col-9">
							<div class="form-control">
								<input type="text" name="userQuestnGoodsNm" id="userQuestnGoodsNm" class="input" placeholder="상품명" value="${detail.questnGoodsNm}"/>
								<input type="hidden" name="questnGoodsIdx" id="questnGoodsIdx" value="${detail.questnGoodsIdx}"/>
								<input type="hidden" name="questnGoodsNm" id="questnGoodsNm" value="${detail.questnGoodsNm}"/>
							</div>
						</div>
						<div class="col col-3">
							<button type="button" class="btn black full btn-srch" data-toggle="popup" data-target="#pop_goodsSearch" onclick="goodsSearch();"><span class="hide">검색</span></button>
						</div>						
					</div>
		
					<div class="row">
						<div class="form-label"><label for="">답변 받으실 이메일 주소</label></div>
						<div class="col col-12">
							<div class="form-control">
							<c:choose>
								<c:when test="${MEMBERFLAG eq 'N' && !empty orderDetail}">
								<input type="text" name="questnEmail" id="questnEmail" class="input" placeholder="이메일" 
										value="${orderDetail.senderEmail}"/>
								</c:when>
								<c:otherwise>
								<input type="text" name="questnEmail" id="questnEmail" class="input" placeholder="이메일" 
										value="<c:choose><c:when test="${detail.questnEmail ne null && detail.questnEmail ne ''}">${detail.questnEmail}</c:when><c:otherwise>${USERINFO.email}</c:otherwise></c:choose>"/>
								</c:otherwise>
							</c:choose>
							</div>
						</div>
					</div>
		
		
					<div class="row">
						<div class="form-label"><label for="">핸드폰 답변 수신 여부</label></div>
						<div class="col col-12">
							<div class="optgroup">
								<span class="radiobox">
									<input type="radio" name="phoneSendYn" id="phoneSendY" class="radio" <c:if test="${detail.phoneSendYn eq 'Y'}">checked</c:if> value="Y"/>
									<label for="phoneSendY" class="lbl">수신</label>
								</span>
								<span class="radiobox">
									<input type="radio" name="phoneSendYn" id="phoneSendN" class="radio" <c:if test="${detail.phoneSendYn eq 'N' || detail.phoneSendYn eq null || detail.phoneSendYn eq ''}">checked</c:if> value="N"/>
									<label for="phoneSendN" class="lbl">미수신</label>
								</span>
							</div>
						</div>				
					</div>
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
							<c:choose>
								<c:when test="${MEMBERFLAG eq 'N' && !empty orderDetail}">
								<input type="text" name="questnPhone" id="questnPhone" class="input" placeholder="핸드폰번호"
										value="${orderDetail.senderPhoneNo}"/>
								</c:when>
								<c:otherwise>
								<input type="text" name="questnPhone" id="questnPhone" class="input" placeholder="핸드폰번호"
										value="<c:choose><c:when test="${detail.questnPhone ne null && detail.questnPhone ne ''}">${detail.questnPhone}</c:when><c:otherwise><c:out value="${fn:replace(USERINFO.phoneNo, '-', '')}"/></c:otherwise></c:choose>"/>
								</c:otherwise>
							</c:choose>
							</div>
						</div>
					</div>
		
					<div class="row">
						<div class="form-label"><label for="">문의 내용</label></div>
						<div class="col col-12">
							<div class="form-control">
								<input type="text" name="questnTitle" id="questnTitle" class="input" placeholder="제목을 입력해 주세요." value="${detail.questnTitle}"/>
							</div>
						</div>				
					</div>
					<div class="row">
						<div class="col col-12">
							<div class="form-control">
								<textarea class="text" name="questnDesc" id="questnDesc" rows="10" placeholder="문의 내용을 입력해 주세요."><c:out value="${detail.questnDesc}" escapeXml="false"/></textarea>
							</div>
						</div>
					</div>
					<ul class="bu-list">
						<li><span class="bu">*</span> 개인정보(주민번호, 계좌번호, 카드번호 등)가 포함되지 않도록 유의해 주세요.</li>
					</ul>
				</div>
			</div>

			<!-- 포토후기 -->	                    
			<div class="photo-form">
				<div class="form-title">
					<h3 class="tit">이미지</h3>
				</div>
				<div class="form-body" style="display: block">
					<div class="filebox">
			            <span class="btn btn-fake"><span class="txt">이미지 첨부</span></span>
			            <input type="file" name="fileData" id="upFile" class="file" accept="image/*" />
			        </div>
					
					<ul class="photo-list" id="filebox">
						<c:if test="${!empty detail.qImg1}">
						<li>
							<img src="${IMGPATH}${detail.qImgPath1}" alt="첨부이미지"/>
							<input type='hidden' name='qImg1' value='${detail.qImg1}'>
							<button type="button" class="btn-delete" onclick='deleteFile(this);'><span class="hide">삭제</span></button>
						</li>
						</c:if>
						<c:if test="${!empty detail.qImg2}">
						<li>
							<img src="${IMGPATH}${detail.qImgPath2}" alt="첨부이미지"/>
							<input type='hidden' name='qImg2' value='${detail.qImg2}'>
							<button type="button" class="btn-delete" onclick='deleteFile(this);'><span class="hide">삭제</span></button>
						</li>
						</c:if>
						<c:if test="${!empty detail.qImg3}">
						<li>
							<img src="${IMGPATH}${detail.qImgPath3}" alt="첨부이미지"/>
							<input type='hidden' name='qImg3' value='${detail.qImg3}'>
							<button type="button" class="btn-delete" onclick='deleteFile(this);'><span class="hide">삭제</span></button>
						</li>
						</c:if>
						<c:if test="${!empty detail.qImg4}">
						<li>
							<img src="${IMGPATH}${detail.qImgPath4}" alt="첨부이미지"/>
							<input type='hidden' name='qImg4' value='${detail.qImg4}'>
							<button type="button" class="btn-delete" onclick='deleteFile(this);'><span class="hide">삭제</span></button>
						</li>
						</c:if>
					</ul>
					
					<ul class="bu-list">
						<li><span class="bu">*</span> 첨부파일은 이미지 형태의 JPG, JPEG, PNG, GIF, BMP 형태로 총 30MB내에서 등록 가능합니다.</li>
					</ul>
				</div>
			</div>
			<!-- //포토후기 -->

			<div class="btn-box confirm">
				<button type="button" class="btn" onclick="location.href='${CTX}/mypage/inquiry/inquiryList.do'"><span class="txt">취소</span></button>
				<c:choose>
					<c:when test="${VO.statusFlag eq 'U'}">
						<button type="button" class="btn outline-green" onclick="goSave('수정');"><span class="txt">수정</span></button>
					</c:when>
					<c:otherwise>
						<button type="button" class="btn outline-green" onclick="goSave('등록');"><span class="txt">등록</span></button>
					</c:otherwise>
				</c:choose>
			</div>
        </div>
	</div>
</form>

<!-- 검색 결과 : 주문 -->
<div id="pop_orderSearch" class="popup type-page popup-prodsrch">
    <h1 class="hide">GATSBY</h1>
	<div class="pop-top">
		<h2>검색 결과</h2>
	</div>
	<div class="pop-mid" id="popOrder">
		
	</div>
    <a href="javascript:" class="btn_close" data-dismiss="popup"><span class="hide">닫기</span></a>
</div>

<!-- 검색 결과 : 상품 -->
<div id="pop_goodsSearch" class="popup type-page popup-prodsrch">
    <h1 class="hide">GATSBY</h1>
	<div class="pop-top">
		<h2>검색 결과</h2>
	</div>
	<div class="pop-mid" id="popGoods">
	</div>
    <a href="javascript:" class="btn_close" data-dismiss="popup"><span class="hide">닫기</span></a>
</div>


<script src="${CTX}/js/vendor/jquery.ui.widget.js"></script>
<script src="${CTX}/js/jquery.iframe-transport.js"></script>
<script src="${CTX}/js/jquery.fileupload.js"></script>

<script>
$(document).ready(function() {
    $('#upFile').fileupload({
        url : '${CTX}/mypage/inquiry/inquiryFileupload.do', 
        dataType: 'json',
        replaceFileInput: false,
        add: function(e, data){
        	var cnt = $("#filebox > li").length;
        	if (cnt ==4 ) {
        		alert("이미지는 최대 4개까지 첨부 할 수 있습니다.");
        		return;
        	}
        	
        	var uploadFile = data.files[0];
            var isValid = true;
            if (!(/png|jpe?g|gif/i).test(uploadFile.name)) {
                alert('png, jpg, gif 만 첨부가 가능합니다');
                isValid = false;
            } else if (uploadFile.size > 10000000) { // 10mb
                alert('파일 용량은 10메가를 초과할 수 없습니다.');
                isValid = false;
            }
            if (isValid) {
                data.submit();              
            } else {
            	$('#upFile').val('');
            }
        }, progressall: function(e,data) {
        	/*
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .bar').css(
                'width',
                progress + '%'
            );
          */
        }, beforeSend: function() {
        	$("#loadingImg").show();
        }, done: function (e, data) {
            var code = data.result.code;
            var msg = data.result.msg;
            var files = data.result.files;
            var ofiles = data.result.ofiles; //실제파일경로
            if(code == '1') {
            	$("#loadingImg").hide();
                //alert(msg);
                for (i=0; i <files.length; i++) {
                	appendFile("${IMGPATH}"+files[i], ofiles[i]);
                }
            } else {
                alert(code + ' : ' + msg);
            } 
            $('#upFile').val('');
        }, fail: function(e, data){
            // data.errorThrown
            // data.textStatus;
            // data.jqXHR;
            alert('서버와 통신 중 문제가 발생했습니다');
            foo = data;
            $('#upFile').val('');
        }
    });
    
    // 이미지 추가
    function appendFile(filepath, originpath) {
    	var html = "";
    	html+="<li> ";
    	html+="		<img src='"+filepath+"' alt='첨부이미지'> ";
    	html+="		<input type='hidden' name='qImg' value='"+originpath+"'> ";
    	html+="		<button type='button' class='btn-delete' onclick='deleteFile(this);'><span class='hide'>삭제</span></button> ";
    	html+="</li> ";
    	
    	$("#filebox").append(html);
    }
    
}); 
	
</script>

</body>
</html>