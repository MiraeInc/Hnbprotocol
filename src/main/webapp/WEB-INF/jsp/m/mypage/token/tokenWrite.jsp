<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.6.3.js"></script>
<meta name="decorator" content="mobile.mypage.main" />
<meta name="menu_2depth" content="mypage_shopping" />
<meta name="menu_no" content="mypage_030" />

<script>
$(function(){
	//datepicker
	$(".form-datepicker").datepicker();
});

function up() {
	var requestPoint = $("#requestPoint").val();
	if ($("#requestPoint").val() != "" && ($("#requestPoint").val() % 100 == 0)) {
		$("#hnbToken").html(($("#requestPoint").val() / 100) + ' 토큰');
	}
	else {
		$("#hnbToken").html("");
	}

}

function checkNumber(event) {
    if (event.key === '.' || event.key === '-' || event.key >= 0 && event.key <= 9) {
        return true;
    }

  return false;
}

function writeOk() {
    var requestPoint = $("#requestPoint").val();
    var walletAddress = $("#walletAddress").val();

    if (requestPoint == "") {
        alert("포인트를 입력하세요.");
        return false;
    }

    if (walletAddress == "") {
        alert("지갑주소를 정확히 입력하세요.");
        return false;
    }

    let tokenRequestJsonData = {
        requestPoint: requestPoint,
        walletAddress: walletAddress
    }

    $.ajax({
        type: "POST",
        url: "/m/mypage/token/tokenWriteOk",
        data: JSON.stringify(tokenRequestJsonData),
        contentType: "application/json; charset=utf-8",
        dataType: "json"
    })
    .done(function(data) {
        if (data.result == false) {
            alert(data.msg);
        }
        else {
            alert('요청이 정상적으로 처리되었습니다.');
            location.href = 'tokenList';
        }
    })
    .fail(function(error) {
        alert(error);
    });
}
</script>

</head>
<body>

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
        <div class="point-list token_cont">
            <ul>
                <li>
                    <div class="item-point">
                        <div class="token_input">
                            <p><span class="tit">신청포인트</span> <input type="number" name="requestPoint" id="requestPoint" onkeypress='return checkNumber(event)' onkeyup="up()" placeholder="포인트입력"></p>
			                <p><span class="tit">HNB토큰</span> <span id="hnbToken"></span></p>
                            <p><span class="tit">지 갑 주 소</span> <input type="text" name="walletAddress" id="walletAddress" placeholder="주소입력"></p>
                        </div>
                        <div class="btn_box btn_center">
                            <button class="btn btn_02 btn_b01" onclick="writeOk()">저장</button>
                            <button class="btn btn_02" onclick="location.href='tokenList'">목록으로</button>
                        </div>
                    </div>
                </li>
            </ul>

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
</div>

</body>
</html>
