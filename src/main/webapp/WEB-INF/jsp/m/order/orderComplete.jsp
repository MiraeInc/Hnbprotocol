<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<script>
	dataLayer = [];
</script>
<meta name="decorator" content="mobile.main"/>
<script type="text/javascript">

	if("<spring:message code='server.status'/>" == "LIVE"){
		if("${resultVO.LGD_RESPCODE}" == "0000"){
			// Begin Google Tag Manager
			// Send transaction data with a pageview if available
			// when the page loads. Otherwise, use an event when the transaction
			// data becomes available.
			
			var arrGtm = new Array();	// Google Tag Manager
	
			<c:forEach var="list" items="${list}" varStatus="idx">
				<fmt:parseNumber var="discountPrice" value="${list.discountPrice}" integerOnly="true"/>
	
				var goodsCnt = Number('${list.orderCnt}');
				
				var objGtm = {                            // List of productFieldObjects.
				        'name': '<c:out value="${list.goodsNm}"/>',     // Name or ID is required.
				        'id': '<c:out value="${list.goodsCd}"/>',
				        'price': '<c:out value="${discountPrice}"/>',
				        'brand': 'Gatsby',
				        'category': '',
				        'variant': '',
				        'quantity': goodsCnt,
				        'coupon': ''                            // Optional fields may be omitted or set to empty string.
				       };
	
				arrGtm.push(objGtm);
			</c:forEach>
	
			dataLayer.push({
			  'ecommerce': {
			    'purchase': {
			      'actionField': {
			        'id': '${resultVO.LGD_OID}',                         // Transaction ID. Required for purchases and refunds.
			        'affiliation': 'Online Store',
			        'revenue': '<fmt:formatNumber value="${resultVO.LGD_AMOUNT}" groupingUsed="false"/>',                     // Total transaction value (incl. tax and shipping)
			        'tax':'',
			        'shipping': '',
			        'coupon': ''
			      },
			      'products': arrGtm
			    }
			  }
			});
			// End Google Tag Manager
			
			// 네이버 광고 스크립트
			var _nasa={};
			_nasa["cnv"] = wcs.cnv("1","<fmt:formatNumber value="${resultVO.LGD_AMOUNT}" groupingUsed="false"/>"); // 전환유형, 전환가치 설정해야함. 설치매뉴얼 참고
			
			// 다음 검색 스크립트
			//<![CDATA[ 
			  var DaumConversionDctSv="type=P,orderID=${resultVO.LGD_OID},amount=<fmt:formatNumber value="${resultVO.LGD_AMOUNT}" groupingUsed="false"/>"; 
			  var DaumConversionAccountID="KpsXDQ3Qr_GUnGx-PGVwwg00"; 
			  if(typeof DaumConversionScriptLoaded=="undefined"&&location.protocol!="file:"){ 
			    var DaumConversionScriptLoaded=true; 
			    document.write(unescape("%3Cscript%20type%3D%22text/javas"+"cript%22%20src%3D%22"+(location.protocol=="https:"?"https":"http")+"%3A//t1.daumcdn.net/cssjs/common/cts/vr200/dcts.js%22%3E%3C/script%3E")); 
			  } 
			//]]>
			
			<!-- Facebook Pixel Code -->
			fbq('track', 'Purchase', {value: '${resultVO.LGD_AMOUNT}', currency: 'KRW'});
		}
	}

</script>

<script type="text/javascript" charset="UTF-8" src="//t1.daumcdn.net/adfit/static/kp.js"></script>
<script type="text/javascript">

if("<spring:message code='server.status'/>" == "LIVE"){
	if("${resultVO.LGD_RESPCODE}" == "0000"){
			var itemcnt = Number('${fn:length(list)}');
			
			var arr = new Array();
			<c:forEach var="list" items="${list}" varStatus="idx">	
				var obj = new Object();
				obj.name = '<c:out value="${list.goodsNm}"/>';
				obj.quantity = '<c:out value="${list.orderCnt}"/>';
				obj.price = '<c:out value="${list.discountPrice}"/>';			
				arr.push(obj);
		  </c:forEach>
			
			if (itemcnt > 0) {
		      kakaoPixel('466785529738862663').purchase({
		        total_quantity: itemcnt, // 주문 내 상품 개수(optional)
		        total_price: "${resultVO.LGD_AMOUNT}",  // 주문 총 가격(optional)
		        currency: "KRW",     // 주문 가격의 화폐 단위(optional, 기본 값은 KRW)
		        products: arr
		    });
		}
	}
}	
   
</script>

<!-- Doyouad Start 삭제 하지 마세요. -->
<script type="text/javascript">
var DOYOUAD_DATA = {};
DOYOUAD_DATA.orderNo = "${resultVO.LGD_OID}";
DOYOUAD_DATA.price = "${resultVO.LGD_AMOUNT}";
(function (w, d, s, n, t) {n = d.createElement(s);n.type = "text/javascript";n.setAttribute("id", "doyouadScript");n.setAttribute("data-user", "mandom");n.setAttribute("data-page", "conv");n.async = !0;n.defer = !0;n.src = "https://cdn.doyouad.com/js/dyadTracker.js?v=" + new Date().toISOString().slice(0, 10).replace(/-/g, "");t = d.getElementsByTagName(s)[0];t.parentNode.insertBefore(n, t);})(window, document, "script");
</script>
<!-- Doyouad End -->

</head>
<body>
            <div class="content comm-order order-complete">

				<div class="order-top">
					<div class="order-steps step3">
						<ol>
							<li>
								<span>STEP 01</span>
								<strong>장바구니</strong>
							</li>
							<li>
								<span>STEP 02</span>
								<strong>주문결제</strong>
							</li>
							<li class="active">
								<span class="hide">현재단계</span>
								<span>STEP 03</span>
								<strong>주문완료</strong>
							</li>
						</ol>
					</div>
				</div>

                <div class="page-body">

				<c:choose>
					<c:when test="${resultVO.LGD_RESPCODE eq '0000'}">
	                    <p class="complete-message"><strong><c:out value="${resultVO.LGD_BUYER}"/>님</strong>이 결제하신<br/> 주문이 완료되었습니다.<br/> 주문해 주셔서 감사합니다.</p>
					</c:when>
					<c:otherwise>
	                    <p class="complete-message"><strong>${resultVO.LGD_RESPMSG}</strong></p>
					</c:otherwise>
				</c:choose>

				<c:if test="${resultVO.LGD_RESPCODE eq '0000'}">
                    <div class="complete-info info-summary">
                        <dl>
                            <dt>주문일</dt>
                            <dd><span class="em">${fn:substring(resultVO.LGD_PAYDATE,0,4)}.${fn:substring(resultVO.LGD_PAYDATE,4,6)}.${fn:substring(resultVO.LGD_PAYDATE,6,8)}</span></dd>
                            <dt>주문번호</dt>
                            <dd><span class="em">${resultVO.LGD_OID}</span> </dd>
                            <dt>최종 결제 금액</dt>
                            <dd><span class="em"><fmt:formatNumber value="${resultVO.LGD_AMOUNT}" groupingUsed="true"/></span></dd>
                        </dl>
                    </div>
				</c:if>
				
				<c:if test="${resultVO.LGD_RESPCODE eq '0000' and resultVO.LGD_PAYTYPE eq 'SC0040'}">	<%--가상계좌 --%>
					<!-- *** 무통장 입금시 *** -->
                    <div class="complete-info">
                        <h3 class="tit">입금정보</h3>
                        <dl>
                            <dt>입금 은행명</dt>
                            <dd><span class="em"><c:out value="${resultVO.LGD_FINANCENAME}"/></span></dd>
                            <dt>입금 계좌번호</dt>
                            <dd><span class="em"><c:out value="${resultVO.LGD_ACCOUNTNUM}"/></span></dd>
						<c:if test="${resultVO.LGD_PAYER ne null and resultVO.LGD_PAYER ne ''}">
                            <dt>입금 예금주</dt>
                            <dd><span class="em"><c:out value="${resultVO.LGD_PAYER}"/></span></dd>
						</c:if>
						<c:if test="${resultVO.LGD_CLOSEDATE ne null and resultVO.LGD_CLOSEDATE ne ''}">
                            <dt>입금 기한</dt>
                            <dd><span class="em">${fn:substring(resultVO.LGD_CLOSEDATE,0,4)}년 ${fn:substring(resultVO.LGD_CLOSEDATE,4,6)}월 ${fn:substring(resultVO.LGD_CLOSEDATE,6,8)}일 23시 59분까지</span></dd>
                            <dd>(※ 입금기한이 지나면 주문은 자동으로 취소됩니다.)</dd>
						</c:if>
                        </dl>
                    </div>
					<!-- *** 무통장 입금시 *** -->
				</c:if>
					
					<%-- 주문완료 배너 --%>
					<c:if test="${!empty bannerInfo}">
						<c:if test="${bannerInfo.deviceGubun eq 'M' or bannerInfo.deviceGubun eq 'A'}">
							<div style="margin-top: 35px;">
								<c:choose>
									<c:when test="${bannerInfo.moLinkYn eq 'N'}">
									<a href="javascript:">
									</c:when>
									<c:when test="${bannerInfo.moLinkYn eq 'Y'}">
									<a href="${bannerInfo.moLinkUrl}" <c:if test="${bannerInfo.moLinkFlag eq '_BLANK'}">target="_BLANK"</c:if>>
									</c:when>				
								</c:choose>
									<img src="${IMGPATH}/banner/${bannerInfo.bannerIdx}/${bannerInfo.moBannerImg}" style="max-width: 100%" alt="${bannerInfo.bannerNm}">
								</a>
							</div>
						</c:if>
					</c:if>
					<%-- //주문완료 배너 --%>
					
					<div class="helper-box" style="padding-top: 0;">
					<!-- 후기이벤트 종료후 원상복귀 <div class="helper-box"> -->
						<div class="helper-box-inner">
							<strong class="tit" data-icon="attention">꼭 알아두세요!</strong>
							<ul>
								<li>면역공방은 환경을 생각하여 종이로 된 거래내역 서나 반품신청서 등을 제공하지 않습니다.</li>
								<li>마이페이지에서 확인 및 신청 하실 수 있으며, 불편하신 고객님께서는 고객센터를 이용해 주시면 친절하게 안내해 드리겠습니다.</li>
							</ul>
						</div>
					</div>

                    <div class="btn-box confirm">
                        <button type="button" class="btn" onclick="location.href='${CTX}/main.do';"><span class="txt">계속 쇼핑</span></button>
					<c:if test="${resultVO.LGD_RESPCODE eq '0000'}">
                        <button type="button" class="btn black" onclick="location.href='${CTX}/mypage/order/orderDetail.do?orderCd=${resultVO.LGD_OID}';"><span class="txt">주문 배송 조회</span></button>
					</c:if>
                    </div>
                </div>
            </div>
            
<c:set var="status"><spring:message code="server.status" /></c:set>

			<!-- Mobon Tracker v4.0 [결제전환] start -->
			<c:set var="firstGoodsCd" value=""/>
				<c:set var="firstGoodsNm" value=""/>
				<c:set var="totalGoodsCnt" value="${0}"/>
				<c:forEach var="list" items="${list}" varStatus="idx">
					<c:if test="${idx.first}">
						<c:set var="firstGoodsCd" value="${list.goodsCd}"/>
						<c:set var="firstGoodsNm" value="${list.goodsNm}"/>
					</c:if>
					<c:set var="totalGoodsCnt" value="${totalGoodsCnt + list.orderCnt}"/>								 
				</c:forEach>
				
			<script type="text/javascript">
			var device = "${DEVICE}".toUpperCase();
			var ENP_VAR = { conversion: { product: [] } };
			
		    // 주문한 각 제품들을 배열에 저장
		    <c:forEach var="list" items="${list}" varStatus="idx">
			    var goodsInfo = {};	
			    goodsInfo.productCode = "${list.goodsCd}";
		    	goodsInfo.productName = "${list.goodsNm}";
		    	goodsInfo.price = "${list.price}";
		    	goodsInfo.dcPrice = "${list.discountPrice}";
		    	goodsInfo.qty = "${list.orderCnt}";
		    	
		    	ENP_VAR.conversion.product.push(goodsInfo);
		    </c:forEach>

		    ENP_VAR.conversion.ordCode= '${resultVO.LGD_OID}';
		    ENP_VAR.conversion.totalPrice = '<fmt:formatNumber value="${resultVO.LGD_AMOUNT}" groupingUsed="false"/>';
		    ENP_VAR.conversion.totalQty = '<fmt:formatNumber value="${totalGoodsCnt}" groupingUsed="false"/>';

		    (function(a,g,e,n,t){a.enp=a.enp||function(){(a.enp.q=a.enp.q||[]).push(arguments)};n=g.createElement(e);n.async=!0;n.defer=!0;n.src="https://cdn.megadata.co.kr/dist/prod/enp_tracker_self_hosted.min.js";t=g.getElementsByTagName(e)[0];t.parentNode.insertBefore(n,t)})(window,document,"script");  
		    enp('create', 'conversion', 'mandom', { device: device });
		    enp('send', 'conversion', 'mandom');
			</script>
			<!-- Mobon Tracker v4.0 [결제전환] end -->

<c:if test="${status eq 'LIVE'}">
	<c:if test="${resultVO.LGD_RESPCODE eq '0000'}">
		<c:if test="${list ne null}">
			<!-- WIDERPLANET PURCHASE SCRIPT START 2017.8.29 -->
			<div id="wp_tg_cts" style="display:none;"></div>
			<script type="text/javascript">
			var wptg_tagscript_vars = wptg_tagscript_vars || [];
			wptg_tagscript_vars.push(
			(function() {
				return {
					wp_hcuid:"",  	/*Cross device targeting을 원하는 광고주는 로그인한 사용자의 Unique ID (ex. 로그인 ID, 고객넘버 등)를 암호화하여 대입.
							 *주의: 로그인 하지 않은 사용자는 어떠한 값도 대입하지 않습니다.*/
					ti:"37336",
					ty:"PurchaseComplete",
					device:"mobile"
					,items:[
					<c:forEach var="list" items="${list}" varStatus="idx">
						<fmt:parseNumber var="discountPrice" value="${list.discountPrice}" integerOnly="true"/>
						<c:if test="${not idx.first}">,</c:if>
						 {i:"<c:out value="${list.goodsCd}"/>",	t:"<c:out value="${list.goodsNm}"/>", p:"<c:out value="${discountPrice}"/>", q:"<c:out value="${list.orderCnt}"/>"} /* 상품 - i:상품 식별번호(Feed로 제공되는 식별번호와 일치) t:상품명 p:단가 q:수량 */
					</c:forEach>
					]
				};
			}));
			</script>
			<script type="text/javascript" async src="//cdn-aitg.widerplanet.com/js/wp_astg_4.0.js"></script>
			<!-- // WIDERPLANET PURCHASE SCRIPT END 2017.8.29 -->
		</c:if>
		
		<%-- 구글 / 구매 전환 스크립트 --%>
		<!-- Google Code for &#44088;&#49828;&#48708;_&#44396;&#47588; Conversion Page -->
		<script type="text/javascript">
		/* <![CDATA[ */
		var google_conversion_id = 833778563;
		var google_conversion_language = "en";
		var google_conversion_format = "3";
		var google_conversion_color = "ffffff";
		var google_conversion_label = "euu4CJGx1nUQg-fJjQM";
		var google_conversion_value = <fmt:formatNumber value="${resultVO.LGD_AMOUNT}" groupingUsed="false"/>;
		var google_conversion_currency = "KRW";
		var google_remarketing_only = false;
		/* ]]> */
		</script>
		<script type="text/javascript" src="//www.googleadservices.com/pagead/conversion.js">
		</script>
		<noscript>
		<div style="display:inline;">
		<img height="1" width="1" style="border-style:none;" alt="" src="//www.googleadservices.com/pagead/conversion/833778563/?value=<fmt:formatNumber value="${resultVO.LGD_AMOUNT}" groupingUsed="false"/>&amp;currency_code=KRW&amp;label=euu4CJGx1nUQg-fJjQM&amp;guid=ON&amp;script=0"/>
		</div>
		</noscript>
		
		<!-- Event snippet for 2019_갸스비전환 conversion page -->
		<script>
		  gtag('event', 'conversion', {
		      'send_to': 'AW-833778563/go7lCIzNi5UBEIPnyY0D',
		      'value': 1.0,
		      'currency': 'KRW',
		      'transaction_id': ''
		  });
		</script>
		
	</c:if>
</c:if>
</body>
</html>