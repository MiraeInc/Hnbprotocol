<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main"/>	
<script>
// 페이지 이동
function goPage(page){
	$("#pageNo").val(page);
	
	var frm = document.couponFrm;
	frm.action = "${CTX}/event/couponBook.do";
	frm.submit();
}

function couponDownload(couponIdx) {
	if (couponIdx == 0 || couponIdx == "" || couponIdx == "0") {
		alert("쿠폰번호가 없습니다.");	
		return false;
	}
	
	$("#couponIdx").val(couponIdx);
	
	$.ajax({			
		url: getContextPath()+"/ajax/event/couponDownloadAjax.do",
		data: $("#couponFrm").serialize(),
	 	type: "post",
	 	async: false,
	 	cache: false,
	 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
	 	error: function(request, status, error){ 
	 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
		},
		success: function(data){
			if(data.result == true){
				$('[data-remodal-id=pop_alert] div.pop-mid p').html("쿠폰다운로드를 완료하였습니다.");
				$('[data-remodal-id=pop_alert]').remodal().open();
			}else{
				if(data.msg != ""){
					$('[data-remodal-id=pop_alert] div.pop-mid p').html(data.msg);
					$('[data-remodal-id=pop_alert]').remodal().open();
				}
			}
		 }
	});		
}

function AllDownload() {
	$.ajax({			
		url: getContextPath()+"/ajax/event/couponAllDownAjax.do",
		data: $("#couponFrm").serialize(),
	 	type: "post",
	 	async: false,
	 	cache: false,
	 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
	 	error: function(request, status, error){ 
	 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
		},
		success: function(data){
			if(data.result == true){
				console.log(data);
				$('[data-remodal-id=pop_alert] div.pop-mid p').html('총'+data.data1+'개의 쿠폰이 다운로드 되었습니다.');
				$('[data-remodal-id=pop_alert]').remodal().open();
			}else{
				if(data.msg != ""){
					$('[data-remodal-id=pop_alert] div.pop-mid p').html(data.msg);
					$('[data-remodal-id=pop_alert]').remodal().open();
				}
			}
		 }
	});		
}
</script>
</head>
<body>
	<div class="content comm-event event-cpnbook">
		<page:applyDecorator  name="mobile.eventmenu" encoding="UTF-8"/>
		
		<div class="page-title">
			<h2><img src="${CTX}/images/${DEVICE}/event/tit_evt_cpn.png" alt="쿠폰북" /></h2>
		</div>

		<div class="page-body">
			<c:choose>
				<c:when test="${fn:length(couponBook) > 0}">
					<div class="coupon-title">
						<a href="javascript:void(0);" class="btn btn-down" onclick="AllDownload();"><span>전체 쿠폰 <i>DOWN</i></span></a>
						<p>다운받은 쿠폰은 <a href="${CTX}/mypage/point/couponList.do" class="em">마이페이지>쿠폰</a> 페이지에서 확인 할 수 있습니다.</p>
					</div>
			
					<div class="coupon-list">
						<ul>
							<c:forEach var="list" items="${couponBook}" varStatus="idx">
							<li>
								<div class="coupon-box">
									<c:choose>
										<c:when test="${list.gubun eq 'S' }">
											<div class="coupon-image type2">무료배송</div>
										</c:when>
										<c:otherwise>
											<c:set var="brandType" value="type-mandom"/>
											<c:if test="${list.gubun eq 'G' and list.targetGoods eq 'B'}">	<%--상품 쿠폰이고 브랜드선택일때 --%>
												<c:choose>
													<c:when test="${list.targetBrandIdx eq 1}"><c:set var="brandType" value="type-gatsby"/></c:when>		
													<c:when test="${list.targetBrandIdx eq 3}"><c:set var="brandType" value="type-bifesta"/></c:when>
													<c:when test="${list.targetBrandIdx eq 4}"><c:set var="brandType" value="type-lucidol"/></c:when>
												</c:choose>
											</c:if>
											<div class="coupon-image ${brandType}">
											<fmt:formatNumber value="${list.discount}" groupingUsed="true"/>
											<c:if test="${list.discountType eq 'R' }"><span>%</span></c:if>
											</div>
										</c:otherwise>
									</c:choose>
									<div class="coupon-name">
										<div class="coupon-vertical">
											<p>${list.couponNm}</p>
										</div>
									</div>
								</div>
								<a href="javascript:void(0);" class="btn btn-coupon-down" onclick="couponDownload('${list.couponIdx}');">DOWN</a>
							</li>
							</c:forEach>
						</ul>
					</div>
					<div class="pagin-nav">
						<c:out value="${page.pageStr}" escapeXml="false"/>
					</div>
				</c:when>
				<c:otherwise>
					<div class="preparing">
						<div class="inner-box">
							<img src="${CTX}/images/m/common/txt_comingsoon.png" alt="coming soon!">
							<p class="big-txt">다운가능한 쿠폰이 존재 하지 않습니다.</p>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
		
		<c:if test="${fn:length(couponBook) > 0}">
			<div class="helper-box">
				<div class="helper-box-inner inset">
					<strong class="tit" data-icon="attention">꼭 알아두세요!</strong>
					<ul>
						<li>기간 내 미사용 쿠폰은 자동 소멸 되며, 유효기간이 지난 쿠폰은 사용할 수 없습니다.</li>
						<li>쿠폰종류에 따라 일부 중복 사용이 제한됩니다.</li>
						<li>상품 쿠폰의 경우 상품 1개에 1:1로 사용이 가능합니다.</li>
						<li>쿠폰 사용 전 사용 조건 및 유효기간 등을 반드시 확인 하시기 바랍니다.</li>
					</ul>
				</div>
			</div>
		</c:if>
	</div>
		
<form name="couponFrm" id="couponFrm" method="POST">
	<input type="hidden" name="couponIdx"  id="couponIdx" value="">
	<input type="hidden" name="pageNo" id="pageNo" value="${schVO.pageNo}"/>
</form>		
</body>
</html>