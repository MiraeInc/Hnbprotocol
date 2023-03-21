<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_activity" />
<meta name="menu_no" content="mypage_070" />
<script>

// 페이지 이동
function goPage(page){
	$("#pageNo").val(page);
	
	var frm = document.inquiryForm;
	frm.action = "${CTX}/mypage/inquiry/inquiryList.do";
	frm.submit();
}

// 검색
function goSearch(){
	$("#pageNo").val(1);
	
	var frm = document.inquiryForm;
	frm.action = "${CTX}/mypage/inquiry/inquiryList.do";
	frm.submit();
}

// 1:1 문의 등록
function goWrite(){
	$("#statusFlag").val("I");
	
	var frm = document.inquiryForm;
	frm.action="${CTX}/mypage/inquiry/inquiryWrite.do";
	frm.submit();
}

// 수정
function goUpdate(idx){
	$("#inquiryIdx").val(idx);
	$("#statusFlag").val("U");
	
	var frm = document.inquiryForm;
	frm.action = "${CTX}/mypage/inquiry/inquiryWrite.do";
	frm.submit();
}

// 삭제
function goDelete(idx){
	if(confirm("삭제 하시겠습니까?")){
		$("#inquiryIdx").val(idx);
		
		var frm = document.inquiryForm;
		frm.action = "${CTX}/mypage/inquiry/inquiryDelete.do";
		frm.submit();
	}
}

//날짜 검색 변경
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
<form name="inquiryForm" id="inquiryForm" method="post" onsubmit="return false;">
	<input type="hidden" name="pageNo" id="pageNo" value="${schVO.pageNo}"/>
	<input type="hidden" name="inquiryIdx" id="inquiryIdx" value=""/>
	<input type="hidden" name="statusFlag" id="statusFlag" value=""/>
	
	<div class="content comm-order comm-mypage mypage-qna">

		<div class="page-body" style="padding: 0;">
			<div class="list-filter">
				<div class="row">
					<div class="col col-5">
						<div class="form-control">
							<div class="opt_select">
								<select name="schSubType" id="schSubType">
									<option value="">전체</option>
									<c:forEach var="inquiryType" items="${inquiryType}" varStatus="idx">
									<option value="${inquiryType.commonCd}" <c:if test="${schVO.schSubType eq inquiryType.commonCd}">selected</c:if>>${inquiryType.cdNm}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<div class="col col-5">
						<div class="form-control">
							<div class="opt_select">
								<select name="schType" id="schType" onchange="dateClick();">
									<option value="">기간</option>
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
							<input type="text" class="input form-datepicker" placeholder="기간" />
						</div>
					</div>
					<div class="col col-5">
						<div class="form-control">
							<input type="text" class="input form-datepicker" placeholder="기간" />
						</div>
					</div>
					<div class="col col-2">
						<button type="button" class="btn-search"><span class="hide">검색</span></button>
					</div>
				</div> --%>
			</div>
			
			<div class="qna-add">
				<div class="row">
					<div class="col col-12">
						<a href="javascript:" class="btn outline-green ico-chev full" onclick="goWrite();"><span class="txt">1:1 문의 등록</span></a>
					</div>
				</div>
			</div>
			
			<div class="qna-list">
				<c:choose>
					<c:when test="${fn:length(inquiryList) > 0}">
						<ul>
							<c:forEach var="list" items="${inquiryList}" varStatus="idx">
							<li>
								<a href="#qnaItem${list.inquiryIdx}" class="item" data-toggle="collapse">
									<div class="badge-box">
										<c:if test="${list.ansYn eq 'N'}"><span class="badge type1">답변 대기</span></c:if>
										<c:if test="${list.ansYn eq 'Y'}"><span class="badge type2">답변 완료</span></c:if>
									</div>
									<p class="title"><c:out value="${list.questnTitle}" escapeXml="false"/></p>
									<p class="date">${list.inquiryNm} | ${list.questnRegDt}</p>
								</a>
								<div id="qnaItem${list.inquiryIdx}" class="item-detail">
									<div class="order-goods">
										<ul>
											<li>
												<c:if test="${list.goodsIdx != null || list.userGoodsNm != null}">
												<div class="item">
													<div class="item-view">
														<c:if test="${list.goodsIdx != null || list.orderDetailIdx != null}">
														<div class="view-thumb">
															<c:if test="${list.rImgFile != null && list.rImgFile != ''}">
																<c:set var="imgSplit" value="${fn:split(list.rImgFile ,'.')}"/>
																<img src="${IMGPATH}/goods/${list.rGoodsIdx}/${imgSplit[0]}_S.${imgSplit[1]}">
															</c:if>
														</div>
														</c:if>
														<div class="view-info">
															<c:if test="${!empty list.vGoodsIdx}">
																<div class="badge-box">
																	<c:if test="${list.opoYn eq 'Y' }" ><span class="badge type1">1+1</span></c:if>
																	<c:if test="${list.tpoYn eq 'Y' }" ><span class="badge type2">2+1</span></c:if>
																	<c:if test="${list.newYn eq 'Y' }" ><span class="badge type3">NEW</span></c:if>
																	<c:if test="${list.bestYn eq 'Y' }" ><span class="badge type4">BEST</span></c:if>
																	<c:if test="${list.saleiconYn eq 'Y' }" ><span class="badge type5">SALE</span></c:if>
																	<c:if test="${list.pointiconYn eq 'Y' }" ><span class="badge type3"><i>POINT</i> X2</span></c:if>
																</div>
																<c:choose>
																	<c:when test="${list.brandIdx eq 1}"><p class="text-gatsby">GATSBY</p></c:when>
																	<c:when test="${list.brandIdx eq 3}"><p class="text-bifesta">BIFESTA</p></c:when>
																	<c:when test="${list.brandIdx eq 4}"><p class="text-lucidol">LUCIDO-L</p></c:when>
																	<c:when test="${list.brandIdx eq 6}"><p class="text-mama">MAMA BUUTER</p></c:when>
																	<c:when test="${list.brandIdx eq 7}"><p class="text-dental">DENTALPRO</p></c:when>
																	<c:when test="${list.brandIdx eq 8}"><p class="text-charley">CHARLEY</p></c:when>
																</c:choose>
															</c:if>
															<p class="name">
																<c:choose>
																	<c:when test="${list.goodsIdx != null || list.orderDetailIdx != null}">
																		${list.rGoodsNm}
																	</c:when>
																	<c:otherwise>
																		${list.userGoodsNm}
																	</c:otherwise>
																</c:choose>
															</p>
														</div>
													</div>
												</div>
												</c:if>
											</li>
										</ul>
									</div>
									<div class="qna-cont">
										<div class="article"><c:out value="${list.questnDesc}" escapeXml="false"/></div>
										<c:if test="${list.qImg1 ne null && list.qImg1 ne ''}">
											<div class="image">
												<c:set var="imgSplit" value="${fn:split(list.qImg1 ,'.')}"/>
												<img src="${IMGPATH}/inquiry/${list.inquiryIdx}/${imgSplit[0]}_T162.${imgSplit[1]}">
											</div>
										</c:if>
										<c:if test="${list.qImg2 ne null && list.qImg2 ne ''}">
				           					<div class="image">
				           						<c:set var="imgSplit" value="${fn:split(list.qImg2 ,'.')}"/>
												<img src="${IMGPATH}/inquiry/${list.inquiryIdx}/${imgSplit[0]}_T162.${imgSplit[1]}">
				           					</div>
										</c:if>
										<c:if test="${list.qImg3 ne null && list.qImg3 ne ''}">
			             					<div class="image">
			             						<c:set var="imgSplit" value="${fn:split(list.qImg3 ,'.')}"/>
												<img src="${IMGPATH}/inquiry/${list.inquiryIdx}/${imgSplit[0]}_T162.${imgSplit[1]}">
			             					</div>
		           						</c:if>
		           						<c:if test="${list.qImg4 ne null && list.qImg4 ne ''}">
			             					<div class="image">
			             						<c:set var="imgSplit" value="${fn:split(list.qImg4 ,'.')}"/>
												<img src="${IMGPATH}/inquiry/${list.inquiryIdx}/${imgSplit[0]}_T162.${imgSplit[1]}">
			             					</div>
		           						</c:if>
										<c:if test="${list.ansYn eq 'N'}">
			             					<div class="btn-box">
				              					<div class="row">
				              						<div class="col right">
				              							<a href="javascript:" class="btn" onclick="goUpdate('${list.inquiryIdx}');"><span class="txt">수정</span></a>
				              							<a href="javascript:" class="btn" onclick="goDelete('${list.inquiryIdx}');"><span class="txt">삭제</span></a>
				              						</div>
				              					</div>
				              				</div>
		              					</c:if>
									</div>
									<c:if test="${list.ansYn eq 'Y'}">
			             				<div class="qna-answer">
			               					<div><c:out value="${list.ansDesc}" escapeXml="false"/></div>
			               				</div>
		            				</c:if>
								</div>
							</li>
							</c:forEach>
						</ul>
					</c:when>
					<c:otherwise>
						<div class="no-contents">
							<p>문의 하신 내역이 없습니다.</p>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			
			<c:if test="${fn:length(inquiryList) > 0}">
				<div class="pagin-nav">
					<c:out value="${page.pageStr}" escapeXml="false"/>
				</div>
			</c:if>
		</div>
		<script>
			$(function(){
				//datepicker
				$(".form-datepicker").datepicker();
			});
		</script>
	</div>
</form>
</body>
</html>