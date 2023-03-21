<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_shopping" />
<meta name="menu_no" content="mypage_030" />
<script>

$(function(){
	//datepicker
	$(".form-datepicker").datepicker();
});

// 페이지 이동
function goPage(page){
	$("#pageNo").val(page);
	
	var frm = document.pointForm;
	frm.action = "${CTX}/mypage/point/pointList.do";
	frm.submit();
}

// 검색
function goSearch(){
	$("#pageNo").val(1);
	
	var frm = document.pointForm;
	frm.action = "${CTX}/mypage/point/pointList.do";
	frm.submit();
}

// 날짜 검색 변경
function dateClick(){
	var dt = $("#schType").val();
	if(dt!=0){
		setDate(dt);
	}else if(dt==0){
		$("#schStartDt").val("");
		$("#schEndDt").val("");
	}
}

</script>
</head>
<body>
<form name="pointForm" id="pointForm" method="post" onsubmit="return false;">
	<input type="hidden" name="pageNo" id="pageNo" value="${schVO.pageNo}"/>
	
	<div class="content comm-order comm-mypage mypage-point">

		<div class="page-body">
			<div class="point-status">
				<dl class="status-point">
					<dt>보유 포인트</dt>
					<dd><strong><fmt:formatNumber value="${totalPoint}" pattern="#,###"/></strong>P</dd>
				</dl>
				<div class="status-expiration">
					<p class="date">${nextMonthTxt} 소멸 예정 포인트</p> 
					<p class="point"><fmt:formatNumber value="${spPointDeduct.gapPoint}" pattern="#,###"/>P</p>
				</div>
			</div>

			<div class="list-filter">
				<div class="row">
					<div class="col col-5">
						<div class="form-control">
							<div class="opt_select">
								<select class="form-control" name="schSubType" id="schSubType">
									<option value="" <c:if test="${schVO.schSubType eq null || schVO.schSubType eq ''}">selected</c:if>>전체</option>
									<option value="P" <c:if test="${schVO.schSubType eq 'P'}">selected</c:if>>적립 포인트</option>
									<option value="M" <c:if test="${schVO.schSubType eq 'M'}">selected</c:if>>사용 포인트</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col col-5">
                        <div class="form-control">
                            <div class="opt_select">
                                <select name="schType" id="schType" onchange="dateClick();">
                                    <option value="0" <c:if test="${schVO.schType eq '0'}">selected</c:if>>전체</option>
                                    <option value="2" <c:if test="${schVO.schType eq '2'}">selected</c:if>>1주일</option>
                                    <option value="3" <c:if test="${schVO.schType eq '3'}">selected</c:if>>1개월</option>
                                    <option value="4" <c:if test="${schVO.schType eq null or schVO.schType eq '' or schVO.schType eq '4'}">selected</c:if>>3개월</option>
									<option value="5" <c:if test="${schVO.schType eq '5'}">selected</c:if>>6개월</option>
									<option value="6" <c:if test="${schVO.schType eq '6'}">selected</c:if>>1년</option>
									<option value="7" <c:if test="${schVO.schType eq '7'}">selected</c:if>>2년</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    
                    <input type="hidden" name="schStartDt" id="schStartDt" value="${schVO.schStartDt}"/>
					<input type="hidden" name="schEndDt" id="schEndDt" value="${schVO.schEndDt}"/>
					
					<div class="col col-2">
						<button type="button" class="btn-search" onclick="javascript:goSearch();"><span class="hide">검색</span></button>
					</div>
				</div>
				<%-- <div class="row">
					<div class="col col-5">
						<div class="form-control">
							<input type="text" name="schStartDt" id="schStartDt" class="input form-datepicker" placeholder="기간" value="${schVO.schStartDt}"/>
						</div>
					</div>
					<div class="col col-5">
						<div class="form-control">
							<input type="text" name="schEndDt" id="schEndDt" class="input form-datepicker" placeholder="기간" value="${schVO.schEndDt}"/>
						</div>
					</div>
					<div class="col col-2">
						<button type="button" class="btn-search" onclick="javascript:goSearch();"><span class="hide">검색</span></button>
					</div>
				</div> --%>
			</div>
            <c:choose>
            	<c:when test="${fn:length(pointList) > 0}">
					<div class="point-list">
						<ul>
							<c:forEach var="list" items="${pointList}" varStatus="idx">
							<li>
								<div class="item-point">
									<div class="point-summary">
										<p class="date">${list.payDedDt}</p>
										<p class="desc">${list.payDedReasonNm}</p>
									</div>
									<dl class="point-detail">
										<dt>${list.payDedTypeNm}</dt>
										<dd>
										<c:choose>
											<c:when test="${list.payDedType eq 'P'}">
											<span class="em"><fmt:formatNumber value="${list.point}" pattern="#,###"/>P</span>
											</c:when>
											<c:otherwise>
												<fmt:formatNumber value="${list.point}" pattern="#,###"/>P
											</c:otherwise>
										</c:choose>
										</dd>
										<c:choose>
											<c:when test="${fn:contains(fn:replace(list.payDedReasonNm, ' ',''),'쇼핑지원금')}">
												
											</c:when>
											<c:otherwise>
												<c:if test="${list.payDedType eq 'P'}">
													<dt>소멸 예정일</dt>
													<dd>${list.extinctionDt}</dd>
												</c:if>
											</c:otherwise>
										</c:choose>
									</dl>
								</div>
							</li>
							</c:forEach>
						</ul>
					</div>
					
					<div class="pagin-nav">
						<c:out value="${page.pageStr}" escapeXml="false"/>
					</div>
				</c:when>
				<c:otherwise>
					<div class="form-group">
						<div class="form-body">
							<div class="no-contents">
								<p>조회 가능한 포인트 내역이 없습니다.</p>
							</div>
						</div>
					</div>
				</c:otherwise>
            </c:choose>
			
			<div class="guidebox">
               	<div class="guide-title">
					<h3 class="tit"><span class="i"><img src="${CTX}/images/${DEVICE}/common/ico_helper_alert.png" alt="" /></span> 포인트 안내</h3>
				</div>
				<div class="guide-list">
					<ul>
						<li>
							<span class="i"><img src="${CTX}/images/${DEVICE}/contents/ico_hand.png" style="width: 50px" alt="" /></span>
							<strong class="tit">적립 안내</strong>
             				<div>
 								<ul class="bu-list">
									<li><span class="bu">-</span> 회원 가입 시 <span class="em"> 2,000P</span> 즉시 지급</li>
<!-- 									<li><span class="bu">-</span> 바로온 접속 시 결제금액의  <span class="em">0.5%</span> 지급</li> -->
									<li><span class="bu">-</span> 일반 후기 작성 시 <span class="em">100P</span> 지급</li>
									<li><span class="bu">-</span> 포토 후기 작성 시 <span class="em">500P</span> 지급</li>
									<li><span class="bu">-</span> 상품 구매시 등급별 포인트 적립</li>
									<li>
										<span class="bu">-</span> 
										상품 구매시 등급별 포인트 적립<br/>
										<strong>일반회원</strong><br/> : 실 결제금액의 <span class="em">1%</span> 포인트 적립 (배송비 제외)<br/>
										<strong>우수회원</strong><br/> : 실 결제금액의 <span class="em">2%</span> 포인트 적립 (배송비 제외)<br/>
										<strong>VIP 회원</strong><br/> : 실 결제금액의 <span class="em">3% </span> 포인트 적립 (배송비 제외)
									</li>
								</ul>
							</div>
						</li>
						<li>
							<span class="i"><img src="${CTX}/images/${DEVICE}/contents/ico_briefcase.png" style="width: 50px" alt="" /></span>
							<strong class="tit">사용 안내</strong>
							<div>
								<ul class="bu-list">
									<li><span class="bu">-</span> 1P 단위로 주문 결제 시 사용가능 합니다.</li>
									<li><span class="bu">-</span> 적립되신 포인트는 기간 만료 시 자동 소멸됩니다.</li>
									<li><span class="bu">-</span> 주문 취소 시 사용하신 포인트는 자동 환급됩니다.</li>
									<li><span class="bu">-</span> 부정 행위로 적립된 포인트는 고지 없이 회수될 수 있습니다.</li>
								</ul>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</form>
</body>
</html>