<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_shopping" />
<meta name="menu_no" content="mypage_030" />

<script>
function cancel(idx) {
    $.ajax({
        type: "GET",
        url: "/m/mypage/token/tokenCancel/" + idx
    })
    .done(function(data) {
        if (data.result == false) {
            alert(data.msg);
        }
        else {
            alert('취소 요청이 정상적으로 처리되었습니다.');
            location.href = 'tokenList';
        }
    })
    .fail(function(error) {
        alert(error);
    });
}

// 페이지 이동
function goPage(page){
	$("#pageNo").val(page);

	var frm = document.pointForm;
	frm.action = "${CTX}/mypage/token/tokenList.do";
	frm.submit();
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
            <br>

            <div class="btn_box btn_right mb10 mr15">
                <button class="btn btn_b01 btn_02" onclick="location.href='tokenWrite'">교환신청</button>
            </div>

            <c:choose>
				<c:when test="${fn:length(tokenList) > 0}">
					<div class="point-list token_cont">

						<ul>
							<c:forEach var="list" items="${tokenList}" varStatus="idx">
							<li>
								<div class="item-point">
									<div class="point-summary">
										<p class="desc"><span class="tit">날 짜</span><span class="blit">:</span><span class="txt">${list.viewDate}</span></p>
										<p class="desc"><span class="tit">포 인 트</span><span class="blit">:</span><span class="txt">${list.requestPoint}</span></p>
										<p class="desc"><span class="tit">토 큰</span><span class="blit">:</span><span class="txt">${list.changeToken}</span></p>
										<p class="desc"><span class="tit">상 태</span><span class="blit">:</span><span class="txt">${list.statusValue}</span></p>
										<p class="desc border_top one_st"><span class="tit">지 갑 주 소</span><span class="blit">:</span><span class="txt">${list.walletAddress}</span></p>
									</div>

                                    <c:if test="${list.statusCode eq 'REQUEST' }" >
									    <button class="btn" onclick="cancel('${list.tokenRequestIdx}')">취소</button>
									</c:if>
								</div>
							</li>
							</c:forEach>
						</ul>
					</div>
					<div class="pagin-nav nav_s01">
                        <c:out value="${page.pageStr}" escapeXml="false"/>
                    </div>
				</c:when>
				<c:otherwise>
					<div class="form-group">
						<div class="form-body">
							<div class="no-contents">
								<p>조회 가능한 토큰 교환 내역이 없습니다.</p>
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
							<span class="i"><img src="${CTX}/images/${DEVICE}/contents/ico_briefcase.png" style="width: 50px" alt="" /></span>
							<strong class="tit">사용 안내</strong>
							<div>
								<ul class="bu-list">
									<li><span class="bu">-</span> 포인트는 상품 구매 시 언제든지 사용가능 합니다.</li>
									<li><span class="bu">-</span> HNB로 토큰 교환은 3,000포인트 이상 시 가능합니다.</li>
									<li><span class="bu">-</span> 1HNB는 100포인트에 해당하며 교환 신청 시 차감됩니다.</li>
									<li><span class="bu">-</span> 토큰 교환반영은 지갑이동 특성상 Time Delay가 있습니다.</li>
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