<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_activity" />
<meta name="menu_no" content="mypage_060" />
<script>

// 첨부파일 삭제
function deleteFile(t) {
	$(t).parent().remove();
}

// 리뷰 등록
function reviewSave(flag, reviewIdx, cateIdx1) {
	$("#statusFlag").val(flag);
	$("#reviewIdx").val(reviewIdx);
	
	var rating = $("#reviewRating").attr("data-rate");
	$("#rating").val(rating);
	
	if ($.trim($("#rating").val()) == "") { // 별점
		alert("별점을 선택 해 주세요.");
		$("#reviewRating").focus();
		return false;
	}
	if (cateIdx1 == "2") {
		if ($("input:radio[name='hairStyle']:checked").length < 1) { // 모발 길이
			alert("모발 길이를 선택 해 주세요.");
			$("input:radio[name='hairStyle']").focus();
			return false;
		}
		if ($("input:radio[name='hairType']:checked").length < 1) { // 모발 타입
			alert("모발 타입을 선택 해 주세요.");
			$("input:radio[name='hairType']").focus();
			return false;
		}
	}
	if ($.trim($("#reviewDesc").val()) == "") { // 후기
		alert("후기를 입력 해 주세요.");
		$("#reviewDesc").focus();
		return false;
	}
	
	var frm = document.reviewLayerForm;
	frm.action = "${CTX}/mypage/review/reviewSave.do";
	frm.submit();
}

</script>
</head>
<body>
<form name="reviewLayerForm" id="reviewLayerForm" method="post" onsubmit="return false;">
	<input type="hidden" name="reviewIdx" id="reviewIdx" value="${detail.reviewIdx}"/>
	<input type="hidden" name="goodsIdx" id="goodsIdx" value="${VO.goodsIdx}"/>
	<input type="hidden" name="goodsCd" id="goodsCd" value="${goodsDetail.goodsCd}"/>
	<input type="hidden" name="winnerIdx" id="winnerIdx" value="${VO.winnerIdx}"/>
	<input type="hidden" name="orderDetailIdx" id="orderDetailIdx" value="${VO.orderDetailIdx}"/>
	<input type="hidden" name="rating" id="rating" value="${detail.rating}"/>
	
	<input type="hidden" name="layerType" id="layerType" value="${VO.layerType}"/>
	<input type="hidden" name="reviewType" id="reviewType" value="${VO.reviewType}"/>
	<input type="hidden" name="statusFlag" id="statusFlag" value="${VO.statusFlag}"/>
	
	<input type="hidden" name="chkReview" id="chkReview" value=""/>
	
	<c:choose>
		<c:when test="${ VO.layerType eq 'order' }">
			<% // 구매확정하면서 리뷰작성 %>
	
			<div class="content comm-order comm-mypage mypage-confirm">
				<!-- 페이지 스크립트 -->
				<script>
					$(function(){
						//별점
						$('#rating').rating();
					})
				</script>

				<!-- s: 히스토리 -->
				<div class="breadcrumb">
					<ul>
						<li>
							<a href="#none">마이페이지</a>
						</li>
						<li class="current">
							<span>구매확정</span>
						</li>
					</ul>
				</div>
				<!-- e: 히스토리 -->

				<div class="page-body">
					<div class="confirm-intro">
						<div class="message">
							<ul class="bu-list">
								<li><span class="bu">-</span> 아래 ‘구매 확정’ 버튼을 누르시면 구매가 확정됩니다.</li>
								<li><span class="bu">-</span> 구매 확정 이후에는 반품/교환 진행이 불가하므로 반드시 상품을 받으신 후에 진행해주세요.</li> 
							</ul>
						</div>
						
						<ul class="confirm-product">
							<li><a href="${CTX}/product/productView.do?goodsCd=${goodsDetail.goodsCd}"><span>${goodsDetail.goodsNm}</span></a></li>
						</ul>
						
						<div class="confirm-benefit">
							<p>구매 확정 시 회원등급별로<br/> 아래와 같이 포인트가 적립됩니다.</p>
							<ul>
								<li>
									<span class="grade grade-g"></span>
									<p class="txt-grade">일반회원</p>
									<p class="txt-benefit"> 
										실 결제  금액의
										<strong>1% 포인트 적립</strong>
										(배송비 제외)
									</p>
								</li>
								<li>
									<span class="grade grade-s"></span>
									<p class="txt-grade">우수회원</p>
									<p class="txt-benefit">
										실 결제 금액의
										<strong>2% 포인트 적립</strong>
										(배송비 제외)
									</p>
								</li>
								<li>
									<span class="grade grade-v"></span>
									<p class="txt-grade">VIP회원</p>
									<p class="txt-benefit">
										실 결제 금액의
										<strong>3% 포인트 적립</strong>
										(배송비 제외)
									</p>
								</li>
							</ul>
						</div>
					</div>
					
					<div class="order-form review-form">
						<div class="form-group">
							<div class="form-title">
								<h3 class="title">상품 후기 작성 <span class="em">(선택)</span></h3>
								<button type="button" class="btn-toggle active" data-toggle="collapse" data-target="#formReview" ><span class="hide">접기</span></button>
							</div>
							<div id="formReview" class="form-body">

								<div class="row">
									<div class="col col-12">
										<span class="checkbox">
											<input type="checkbox" name="chkReviewBox" id="chkReviewBox" class="check" value="Y" onclick="chkReivewClick(this);">
											<label for="chkReviewBox" class="lbl">후기 작성 하기</label>
										</span>
									</div>
									<script>
										function chkReivewClick(t) {
											if (t.checked) {
												$("#reviewTableDiv").show();
											}
											else {
												$("#reviewTableDiv").hide();
											}
										}
									</script>
								</div>
								
								<ul class="bu-list">
									<li><span class="bu">-</span> 후기 작성은 선택 사항입니다.</li>
									<li>
										<span class="bu">-</span> 후기를 작성해주시면 포인트를 적립해 드립니다.<br/>
										<span class="em">(일반 후기 100P, 포토후기 500P)</span>
									</li> 
								</ul>
								
								<!-- 작성폼 -->
								<div id="reviewTableDiv" style="display: none;">
									<div class="write-form">
										<div class="row">
											<div class="form-label">
												<label for="">별점</label>
											</div>
											<div class="col col-12">
												<div class="star-vote">
													<span id="reviewRating" class="rate-star-big type-vote" data-rate="${detail.rating}">
														<input type="radio" name="optVote" class="star" value="1"/>
														<input type="radio" name="optVote" class="star" value="2"/>
														<input type="radio" name="optVote" class="star" value="3"/>
														<input type="radio" name="optVote" class="star" value="4"/>
														<input type="radio" name="optVote" class="star" value="5"/>
														<i></i>
													</span>
												</div>
											</div>
										</div>
										
					<c:if test="${goodsDetail.cateIdx1 eq '2'}">	
					<c:forEach var="hairStyle" items="${hairStyleList}" varStatus="idx">
					<c:if test="${(idx.index+1) mod 3 eq 0}">
					</div>
					</c:if>
					<c:if test="${idx.first or (idx.index+1) mod 3 eq 0}">
					<div class="row">
					</c:if>
						<c:if test="${idx.first}">
						<div class="form-label">
							<label for="">모발길이</label>
						</div>
						</c:if>
						<div class="col col-6">
							<span class="radiobox">
								<input type="radio" name="hairStyle" id="${hairStyle.commonCd}" class="radio" value="${hairStyle.commonCd}" <c:if test="${detail.hairStyle eq hairStyle.commonCd}">checked</c:if>/>
								<label for="${hairStyle.commonCd}" class="lbl">${hairStyle.cdNm}</label>
							</span>
						</div>
					<c:if test="${idx.last}">
					</div>
					</c:if>
					</c:forEach>
						
					<div class="row">
						<div class="form-label">
							<label for="">모발타입</label>
						</div>
						<div class="col col-12">
							<div class="optgroup">
								<c:forEach var="hairType" items="${hairTypeList}" varStatus="idx">
								<span class="radiobox">
									<input type="radio" name="hairType" id="${hairType.commonCd}" class="radio" value="${hairType.commonCd}" <c:if test="${detail.hairType eq hairType.commonCd}">checked</c:if>/>
									<label for="${hairType.commonCd}" class="lbl">${hairType.cdNm}</label>
								</span>
								</c:forEach>
							</div>
						</div>
					</div>
					</c:if>
					<div class="row">
						<div class="form-label">
							<label for="">상품후기</label>
						</div>
						<div class="col col-12">
							<div class="form-control">
								<textarea name="reviewDesc" id="reviewDesc" rows="10" class="text" placeholder="내용을 입력해주세요."><c:out value="${detail.reviewDesc}" escapeXml="false"/></textarea>
							</div>
						</div>
					</div>
				</div>
					
				<div class="photo-form">
				<c:choose>
					<c:when test="${VO.statusFlag eq 'I'}">
					<div class="form-link">
						<a href="#formPhotoUpload" id="btn-photoreview" data-toggle="collapse"><span class="txt">포토 후기를 작성하시면 500P를 드립니다.</span></a>
					</div>
					</c:when>
					<c:otherwise>
					<div class="form-title">
						<h3 class="tit">이미지</h3>
					</div>
					</c:otherwise>
				</c:choose>
					<div id="formPhotoUpload" class="form-body" <c:if test="${VO.statusFlag eq 'U'}"> style="display: block;"</c:if>>
						<div class="filebox">
							<span class="btn btn-fake"><span class="txt">이미지 첨부</span></span>
							<input type="file" name="fileData" id="upFile" class="file" accept="image/*" />
						</div>
                    		
						<ul class="photo-list" id="filebox">
							<c:if test="${!empty detail.img1}">
							<li>
								<img src='${IMGPATH}${detail.imgPath1}' alt='첨부이미지'/>
								<input type='hidden' name='img1' value='${detail.img1}'/>
								<button type="button" class="btn-delete" onclick='deleteFile(this);'><span class="hide">삭제</span></button>
							</li>
							</c:if>
							<c:if test="${!empty detail.img2}">
							<li>
								<img src='${IMGPATH}${detail.imgPath2}' alt='첨부이미지'/>
								<input type='hidden' name='img2' value='${detail.img2}'/>
								<button type="button" class="btn-delete" onclick='deleteFile(this);'><span class="hide">삭제</span></button>
							</li>
							</c:if>
							<c:if test="${!empty detail.img3}">
							<li>
								<img src='${IMGPATH}${detail.imgPath3}' alt='첨부이미지'/>
								<input type='hidden' name='img3' value='${detail.img3}'/>
								<button type="button" class="btn-delete" onclick='deleteFile(this);'><span class="hide">삭제</span></button>
							</li>
							</c:if>
				  		</ul>
                    		
                   		<ul class="bu-list">
                   			<li><span class="bu">*</span> 첨부파일은 이미지 형태의 JPG, JPEG, PNG, GIF, BMP 형태로 총 30MB내에서 등록 가능합니다.</li>
                   		</ul>
                   	</div>
				</div>
			</div>
								
					
					<div class="btn-box confirm">
						<button type="button" class="btn" onclick="javascript:history.back();"><span class="txt">취소</span></button>
						<button type="button" class="btn outline-green" onclick="reviewSaveAndComplet('I', '0', '${goodsDetail.cateIdx1}');"><span class="txt">구매 확정</span></button>
					</div>
					
				</div>
			</div>
			
		</c:when>
		<c:otherwise>
		
	<div class="content comm-order comm-mypage mypage-review">
            
		<div class="page-body">
			<div class="review-edit">
				<div class="order-goods">
					<ul>
						<li>
							<div class="item">
								<div class="item-view">
									<div class="view-thumb">
										<c:set var="imgSplit" value="${fn:split(goodsDetail.imgFile ,'.')}"/>
										<img src="${IMGPATH}/goods/${goodsDetail.goodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}" alt="상품 썸네일 이미지">
									</div>
									<div class="view-info">
										<p class="name">${goodsDetail.goodsNm}</p>
									</div>
								</div>
							</div>
						</li>
					</ul>
				</div>	
					
				<div class="write-form">
					<div class="row">
						<div class="form-label">
							<label for="">별점</label>
						</div>
						<div class="col col-12">
							<div class="star-vote">
								<span id="reviewRating" class="rate-star-big type-vote" data-rate="${detail.rating}">
									<input type="radio" name="optVote" class="star" value="1"/>
									<input type="radio" name="optVote" class="star" value="2"/>
									<input type="radio" name="optVote" class="star" value="3"/>
									<input type="radio" name="optVote" class="star" value="4"/>
									<input type="radio" name="optVote" class="star" value="5"/>
									<i></i>
								</span>
							</div>
						</div>
					</div>
					<c:if test="${goodsDetail.cateIdx1 eq '2'}">	
					<c:forEach var="hairStyle" items="${hairStyleList}" varStatus="idx">
					<c:if test="${(idx.index+1) mod 3 eq 0}">
					</div>
					</c:if>
					<c:if test="${idx.first or (idx.index+1) mod 3 eq 0}">
					<div class="row">
					</c:if>
						<c:if test="${idx.first}">
						<div class="form-label">
							<label for="">모발길이</label>
						</div>
						</c:if>
						<div class="col col-6">
							<span class="radiobox">
								<input type="radio" name="hairStyle" id="${hairStyle.commonCd}" class="radio" value="${hairStyle.commonCd}" <c:if test="${detail.hairStyle eq hairStyle.commonCd}">checked</c:if>/>
								<label for="${hairStyle.commonCd}" class="lbl">${hairStyle.cdNm}</label>
							</span>
						</div>
					<c:if test="${idx.last}">
					</div>
					</c:if>
					</c:forEach>
						
					<div class="row">
						<div class="form-label">
							<label for="">모발타입</label>
						</div>
						<div class="col col-12">
							<div class="optgroup">
								<c:forEach var="hairType" items="${hairTypeList}" varStatus="idx">
								<span class="radiobox">
									<input type="radio" name="hairType" id="${hairType.commonCd}" class="radio" value="${hairType.commonCd}" <c:if test="${detail.hairType eq hairType.commonCd}">checked</c:if>/>
									<label for="${hairType.commonCd}" class="lbl">${hairType.cdNm}</label>
								</span>
								</c:forEach>
							</div>
						</div>
					</div>
					</c:if>
					<div class="row">
						<div class="form-label">
							<label for="">상품후기</label>
						</div>
						<div class="col col-12">
							<div class="form-control">
								<textarea name="reviewDesc" id="reviewDesc" rows="10" class="text" placeholder="내용을 입력해주세요."><c:out value="${detail.reviewDesc}" escapeXml="false"/></textarea>
							</div>
						</div>
					</div>
				</div>
					
				<div class="photo-form">
				<c:choose>
					<c:when test="${VO.statusFlag eq 'I'}">
					<div class="form-link">
						<a href="#formPhotoUpload" id="btn-photoreview" data-toggle="collapse"><span class="txt">포토 후기를 작성하시면 500P를 드립니다.</span></a>
					</div>
					</c:when>
					<c:otherwise>
					<div class="form-title">
						<h3 class="tit">이미지</h3>
					</div>
					</c:otherwise>
				</c:choose>
					<div id="formPhotoUpload" class="form-body" <c:if test="${VO.statusFlag eq 'U'}"> style="display: block;"</c:if>>
						<div class="filebox">
							<span class="btn btn-fake"><span class="txt">이미지 첨부</span></span>
							<input type="file" name="fileData" id="upFile" class="file" accept="image/*" />
						</div>
                    		
						<ul class="photo-list" id="filebox">
							<c:if test="${!empty detail.img1}">
							<li>
								<img src='${IMGPATH}${detail.imgPath1}' alt='첨부이미지'/>
								<input type='hidden' name='img1' value='${detail.img1}'/>
								<button type="button" class="btn-delete" onclick='deleteFile(this);'><span class="hide">삭제</span></button>
							</li>
							</c:if>
							<c:if test="${!empty detail.img2}">
							<li>
								<img src='${IMGPATH}${detail.imgPath2}' alt='첨부이미지'/>
								<input type='hidden' name='img2' value='${detail.img2}'/>
								<button type="button" class="btn-delete" onclick='deleteFile(this);'><span class="hide">삭제</span></button>
							</li>
							</c:if>
							<c:if test="${!empty detail.img3}">
							<li>
								<img src='${IMGPATH}${detail.imgPath3}' alt='첨부이미지'/>
								<input type='hidden' name='img3' value='${detail.img3}'/>
								<button type="button" class="btn-delete" onclick='deleteFile(this);'><span class="hide">삭제</span></button>
							</li>
							</c:if>
				  		</ul>
                    		
                   		<ul class="bu-list">
                   			<li><span class="bu">*</span> 첨부파일은 이미지 형태의 JPG, JPEG, PNG, GIF, BMP 형태로 총 30MB내에서 등록 가능합니다.</li>
                   		</ul>
                   	</div>
				</div>
			</div>
				
			<div class="btn-box confirm">
				<button type="button" class="btn" onclick="javascript:history.back();"><span class="txt">취소</span></button>
				<c:choose>
					<c:when test="${VO.statusFlag eq 'I'}">
						<button type="button" class="btn outline-green" onclick="reviewSave('I', '0', '${goodsDetail.cateIdx1}');"><span class="txt">등록</span></button>
					</c:when>
					<c:otherwise>
						<button type="button" class="btn outline-green" onclick="reviewSave('U', '${detail.reviewIdx}', '${goodsDetail.cateIdx1}');"><span class="txt">수정</span></button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
		</c:otherwise>
	</c:choose>
	
	
</form>

<script src="${CTX}/js/vendor/jquery.ui.widget.js"></script>
<script src="${CTX}/js/jquery.iframe-transport.js"></script>
<script src="${CTX}/js/jquery.fileupload.js"></script>

<script>
$(document).ready(function() {
	//별점
	$("#reviewRating").rating();
	
	/* var statusFlag = '${VO.statusFlag}';
	
	// 수정일 경우 파일 첨부 영역 클릭
	if(statusFlag=="U"){
		$("#btn-photoreview").trigger("click");
	} */
	
    $('#upFile').fileupload({
        url : '${CTX}/ajax/mypage/review/reviewFileupload.do', 
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
	


// 구매확정 + 리뷰 등록
function reviewSaveAndComplet(flag, reviewIdx, cateIdx1) {
	$("#statusFlag").val(flag);
	$("#reviewIdx").val(reviewIdx);
	$("#layerType").val("order");
	
	var rating = $("#reviewRating").attr("data-rate");
	$("#rating").val(rating);
	
	if($("#chkReviewBox:checked").length > 0) {
		$("#chkReview").val("Y");
		if ($.trim($("#rating").val()) == "") { // 별점
			alert("별점을 선택 해 주세요.");
			$("#reviewRating").focus();
			return false;
		}
		if (cateIdx1 == "2") {
			if ($("input:radio[name='hairStyle']:checked").length < 1) { // 모발 길이
				alert("모발 길이를 선택 해 주세요.");
				$("input:radio[name='hairStyle']").focus();
				return false;
			}
			if ($("input:radio[name='hairType']:checked").length < 1) { // 모발 타입
				alert("모발 타입을 선택 해 주세요.");
				$("input:radio[name='hairType']").focus();
				return false;
			}
		}
		if ($.trim($("#reviewDesc").val()) == "") { // 후기
			alert("후기를 입력 해 주세요.");
			$("#reviewDesc").focus();
			return false;
		}
	}
	else {
		$("#chkReview").val("N");
	}
	
	$.ajax({
		url : "${CTX}/ajax/mypage/review/reviewSave.do",
		data : $("#reviewLayerForm").serialize(),
		type : "post",
		async : false,
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		error : function(request, status, error) {
			alert("code:" + request.status + "\n" + "message:"
					+ request.responseText + "\n" + "error:" + error);
		},
		success : function(flag) {
			alert("구매확정 하였습니다.");
			location.href = "${CTX}/mypage/order/myOrderList.do";
		}
	});
}
</script>

</body>
</html>