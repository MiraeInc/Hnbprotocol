<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp" />
<decorator:body />

<!-- s: 하단고정메뉴 -->
<div class="mandom-menu">
  <a href="javascript:void(0);" id="btnMenu" class="menu-anchor">
    <i class="anchor-icon icon-cate"></i>
    <strong class="anchor-name">카테고리</strong>
  </a>
  <a href="javascript:void(0);" id="btnMenuClose2" class="menu-anchor">
    <i class="anchor-icon icon-cate-close"></i>
    <strong class="anchor-name">닫기</strong>
  </a>
  
  <!-- 2023.03.07 수정 -->
  <!-- 
  <a href="javascript:void(0);" id="btnMenuBrand" class="menu-anchor">
    <i class="anchor-icon icon-brand"></i>
    <strong class="anchor-name">브랜드</strong>
  </a>
   -->
  <a href="${CTX}/main.do" class="menu-anchor">
    <i class="anchor-icon icon-home"></i>
    <strong class="anchor-name">홈</strong>
  </a>
  <a href="${CTX}/mypage/order/main.do?refererYn=Y" class="menu-anchor">
    <i class="anchor-icon icon-mypage"></i>
    <strong class="anchor-name">마이페이지</strong>
  </a>
  <a href="${CTX}/mypage/order/myOrderList.do" class="menu-anchor">
    <i class="anchor-icon icon-order"></i>
    <strong class="anchor-name">주문배송</strong>
  </a>
</div>
<!-- e: 하단고정메뉴 -->


<!-- 2023.02.28 수정 : 텍스트 수정 -->
<%-- <footer class="footer">
  <div class="footer-customer">
    <div class="customer-counsel">
      <p>
        <a href="tel:02-544-1191" class="tel">02-544-1191</a>
        <a href="${CTX}/cscenter/csMain.do" class="cscenter">고객센터 바로가기 ▶</a>
      </p>
      <p>평일 10AM - 6AM (토/일/공휴일 휴무)</p>
    </div>
    <div class="customer-notice">
      <dl>
        <dt>공지</dt>
        <dd>
          <a href="${CTX}/cscenter/notice/noticeView.do?noticeIdx=${footerNotice.noticeIdx}">
            <c:if test="${!empty footerNotice.noticeTypeNm}">[${footerNotice.noticeTypeNm}]</c:if>
            ${footerNotice.title}
          </a>
        </dd>
      </dl>
      <a href="${CTX}/cscenter/notice/noticeList.do" class="lnk-more"><span class="hide">공지사항 더보기</span></a>
    </div>
  </div>
  <div class="footer-links">
    <ul>
      <c:choose>
				<c:when test="${IS_LOGIN eq true}"> 
					<li><a href="${CTX}/logout.do">로그아웃</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="${CTX }/login/loginPage.do">로그인</a></li>
					<li><a href="${CTX }/member/joinStep01.do">회원가입</a></li>
				</c:otherwise>
			</c:choose>
      <li><a href="http://www.mandomkorea.co.kr">회사 소개</a></li>
      <li><a href="${CTX}/brand/brandGatsby.do?brandIdx=1">브랜드소개</a></li>
      <li><a href="${CTX}/cscenter/csMain.do">고객센터</a></li>
      <li><a href="${CTX}/etc/contactUs.do">Contact Us</a></li>
    </ul>
  </div>
  <div class="footer-socials">
    <dl>
      <dt>맨담몰</dt>
      <dd>
        <a href="https://blog.naver.com/mandomlab" target="_blank" class="ico ico-blog"><span class="hide">맨담몰 블로그</span></a>
        <a href="https://www.youtube.com/channel/UCGZ2TQsslSH6MJPyDRP9GbA" class="ico ico-youtube" target="_blank"><span class="hide">맨담몰 유튜브</span></a>
      </dd>
    </dl>
    <dl>
      <dt>갸스비</dt>
      <dd>
        <a href="https://www.facebook.com/gatsbykorea/" class="ico ico-facebook" target="_blank"><span class="hide">갸스비 페이스북</span></a>
        <a href="https://www.youtube.com/user/gatsbykorea/" class="ico ico-youtube" target="_blank"><span class="hide">갸스비 유튜브</span></a>
        <a href="https://www.instagram.com/gatsbykorea" class="ico ico-instagram" target="_blank"><span class="hide">갸스비 인스타그램</span></a>
      </dd>
    </dl>
    <dl>
      <dt>비페스타</dt>
      <dd>
        <a href="https://www.facebook.com/bifestakorea" class="ico ico-facebook" target="_blank"><span class="hide">비페스타 페이스북</span></a>
        <a href="https://www.youtube.com/channel/UCU0y5kyRmX5zowHPFWvuk6g" target="_blank" class="ico ico-youtube"><span class="hide">비페스타 유튜브</span></a>
      </dd>
    </dl>
    <dl>
      <dt>루시도엘</dt>
      <dd>
        <a href="https://www.instagram.com/explore/tags/루시도엘" target="_blank" class="ico ico-instagram"><span class="hide">루시도엘 인스타그램</span></a>
      </dd>
    </dl>
  </div>
  <div class="footer-award">
    <a href="https://award.gatsby.jp/kr/" target="_blank"><img src="${CTX}/images/${DEVICE}/common/logo_award.png" alt="갸스비 크리에이티브 어워즈" /></a>
  </div>
  <div class="footer-address">
    <a href="#blockAddress" id="btnTgAddr" class="btn-toggle">(주)엠와이지비 사업자 정보 및 약관 확인 <i></i></a>
    <div id="blockAddress" class="address-wrap">
      <div class="policy-links">
        <a href="${CTX}/etc/terms.do">이용약관</a>
        <a href="${CTX}/etc/privacy.do">개인정보처리방침</a>
      </div>
      <address class="address">
        대표이사 : 문준현 <br />
        사업자등록번호 : 509-88-02366 <br />
        <a href="http://www.ftc.go.kr/info/bizinfo/communicationViewPopup.jsp?wrkr_no=5098802366" target="_blank" class="link-info">사업자정보확인 ▶</a><br />
        통신판매업신고번호 : 제 2022-서울서초-0451 호 <br />
        <a href="https://admin.kcp.co.kr/Modules/escrow/kcp_pop.jsp?site_cd=A834W" target="_blank" class="link-info">안전거래 서비스 가입 확인 ▶</a><br />
        주소 : 서울특별시 강남구 학동로 165 (논현동, 마일스디오빌) 206호 <br />
        고객센터 : 02-544-1191 <br />
        이메일 : mygb_club@naver.com <br />
        개인정보보호책임자 : 문준현<br />
      </address>
    </div>
  </div>

  <div class="footer-copyright">
    <img src="${CTX}/images/${DEVICE}/common/logo_mandom.png" class="award" alt="" />
    <p class="copyright-text">COPYRIGHT©MYGB CORP. All rights reserved.</p>
    <p class="copyright-kor">면역공방의 모든 이미지는 무단 도용 시 (화면캡쳐 포함), 저작권법에 의해 법적 조치를 받을 수 있음을 알려드립니다.</p>
    <img src="${CTX}/images/${DEVICE}/common/webprize-2.png" class="img2"  alt="" />
  </div> --%>
  
  <footer class="footer">
  <div class="footer-customer">
    
    <div class="customer-notice">
      <%-- <dl>
        <dt>공지</dt>
        <dd>

          <a href="${CTX}/cscenter/notice/noticeView.do?noticeIdx=${footerNotice.noticeIdx}">
            <c:if test="${!empty footerNotice.noticeTypeNm}">[${footerNotice.noticeTypeNm}]</c:if>
            ${footerNotice.title}
          </a>

           

	        <c:forEach var="list" items="${footerNoticeList}">
	          <li><a href="${CTX}/cscenter/notice/noticeView.do?noticeIdx=${list.noticeIdx}">
	              <c:if test="${!empty list.noticeTypeNm}">[${list.noticeTypeNm}]</c:if>
	              ${list.title}
	            </a> <span class="date">${list.regDt}</span></li>
	        </c:forEach>
           
        </dd>
      </dl>
      <a href="${CTX}/cscenter/notice/noticeList.do" class="lnk-more"><span class="hide">공지사항 더보기</span></a> --%>

	  <div class="customer-counsel">
	     <p>
	       <a href="tel:02-544-1191" class="tel">02-544-1191</a>
	       <a href="tel:02-544-1191" class="cscenter">전화걸기 ▶</a>
	       <%-- <a href="${CTX}/cscenter/csMain.do" class="cscenter">고객센터 바로가기 ▶</a> --%>
	     </p>
	     <p>평일 10AM - 6AM (토/일/공휴일 휴무)</p>
	   </div>
    </div>
    
  </div>
  <div class="footer-links">
    <ul>
      <%-- <c:choose>
				<c:when test="${IS_LOGIN eq true}"> 
					<li><a href="${CTX}/logout.do">로그아웃</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="${CTX }/login/loginPage.do">로그인</a></li>
					<li><a href="${CTX }/member/joinStep01.do">회원가입</a></li>
				</c:otherwise>
			</c:choose> --%>
      <li><a href="${CTX}/etc/terms.do">이용약관</a></li>
      <li><a href="${CTX}/etc/privacy.do">개인정보처리방침</a></li>
    </ul>
  </div>
  
  <%-- <div class="footer-award">
    <a href="https://award.gatsby.jp/kr/" target="_blank"><img src="${CTX}/images/${DEVICE}/common/logo_award.png" alt="갸스비 크리에이티브 어워즈" /></a>
  </div> --%>
  <div class="footer-address">
  	<address class="address">
  		상호 : 주식회사 엠와이지비<br />
  		대표자명 : 문준현<br />
		사업장 주소 : 서울특별시 강남구 학동로 165, 206호<br>(논현동, 마일스디오빌)<br />
        사업자등록번호 : 509-88-02366<br />
        통신판매업신고 : 제 2022-서울서초-0451 호<br />
        <a href="http://www.ftc.go.kr/info/bizinfo/communicationViewPopup.jsp?wrkr_no=5098802366" target="_blank" class="link-info">사업자정보확인 ▶</a><br />
    </address>
  </div>

  <div class="footer-copyright">
    <%-- <img src="${CTX}/images/${DEVICE}/common/logo_mandom.png" class="award" alt="" /> --%>
    <p class="copyright-text">COPYRIGHT©MYGB ALL RIGHT RESERVED.</p>
    <!-- <p class="copyright-kor">면역공방의 모든 이미지는 무단 도용 시 (화면캡쳐 포함), 저작권법에 의해 법적 조치를 받을 수 있음을 알려드립니다.</p> -->
    <%-- <img src="${CTX}/images/${DEVICE}/common/webprize-2.png" class="img2"  alt="" /> --%>
  </div>

</footer>

<!-- s: 보노보노이벤트 -->
<div id="cow2021" data-cow="1">
  <a href="javascript:" onclick="bonoClick();"><span>보노보노</span></a>
  <button type="button" class="btn-close" onclick="bonoEventClose()"><span>닫기</span></button>
</div>

<!-- <div class="popup type-notice" data-remodal-mode="multiple" data-remodal-id="popNotice20200316">
    <div class="pop-mid">
        <style>
            .remodal-overlay.type-notice {z-index: 2000; background: rgba(0, 0, 0, 0.6)}
            .remodal-wrapper.type-notice {z-index: 2000; overflow: visible; max-width: 176px; margin: 0 auto; padding: 0;}
            .popup.type-notice {margin: 0; border-radius: 18px;}
            .popup.type-notice .pop-mid:after {content:""; position:absolute; left: -8px; top: -35px; width: 176px; height: 86px; background: url('${CTX}/images/m/promotion/dust/img_pop_box.png') 0 0 no-repeat; background-size: cover}
            .popup.type-notice .pop-close {position: absolute; right: -32px; top: 0; padding: 0;  background: none}
            .popup.type-notice .pop-close .btn-close {display: block; width: 22px; height: 22px; padding: 0; font-size: 0; background: url("${CTX}/images/m/promotion/dust/img_close.png") 0 0 no-repeat; background-size: cover;}
        </style>

        <div id="bonoPopup">
            
        </div>
    </div>
    <div class="pop-close">
        <a href="#none" class="btn-close" data-remodal-action="close">닫기</a>
    </div>
</div> -->

<script>
  $(function () {
    $.ajax({
      url: "${CTX}/ajax/getBonoYn.do",
      type: "POST",
      async: false,
      contentType: "application/x-www-form-urlencoded; charset=UTF-8",
      error: function (request, status, error) {
        alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
      },
      success: function (bono) {
    	  var result = bono.result;
          var bonoNo = bono.bonoNo;
          var bonoTop = bono.bonoTop;
          var bonoLeft = bono.bonoLeft;

        if (result == 1) {
          // DOM 준비되고 보노보노 이벤트 실행
        	bonoEventInit(bonoNo, bonoTop, bonoLeft);
        }
      }
    });
  });

  //보노보노 이벤트 세팅
  function bonoEventInit(bonoNo) {
    var $bono = $('#cow2021');
    var wh = $(window).width();

    $bono.attr('data-cow', bonoNo); //캐릭터설정
	
    $(window).off('resize.bono').on('resize.bono', function(){
        $bono.css({
  			top: '330px'
        })
      }).resize();
    
    $bono.show();
  }

  //보노보노 이벤트 삭제
  function bonoEventClose() {
    var $bono = $('#cow2021');

    $bono.remove(); //태그삭제
  }

  // 보노보노 클릭
  function bonoClick() {
    // 로그인 체크
    var loginYn = "${IS_LOGIN}";
    if (loginYn == "false") {
      if (confirm("<spring:message code='common.util011'/>") == true) {
        location.href = "${CTX}/login/loginPage.do?refererYn=Y";
      }
    } else {
      $.ajax({
        url: "${CTX}/ajax/bonoClick.do",
        type: "POST",
        async: false,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        error: function (request, status, error) {
          alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
        },
        success: function (bono) {
        	alert(bono.msg);
          /*var html = "";
          var bonoPoint = Number(bono.bonoPoint);

          if (bonoPoint == 0) {
            alert(bono.msg);
          } else {
            if (bonoPoint == 1) { // 공기청정기 팝업
              html = "<a href='#none'><img src='${CTX}/images/m/promotion/dust/img_pop_004.png' alt=''/></a>";
            } else if (bonoPoint == 2) { // 헤어드라이어 팝업
              html = "<a href='#none'><img src='${CTX}/images/m/promotion/dust/img_pop_002.png' alt=''/></a>";
            } else if (bonoPoint == 3) { // 전상품 팝업
              html = "<a href='#none'><img src='${CTX}/images/m/promotion/dust/img_pop_003.png' alt=''/></a>";
            } else if (bonoPoint == 4) { // 포인트 팝업1
              html = "<a href='#none'><img src='${CTX}/images/m/promotion/dust/img_pop_001.png' alt=''/></a>";
            } else if (bonoPoint == 5) { // 포인트 팝업2
              html = "<a href='#none'><img src='${CTX}/images/m/promotion/dust/img_pop_005.png' alt=''/></a>";
            }
            $("#bonoPopup").append(html);
            $('[data-remodal-id=popNotice20200316]').remodal().open();
          }
          */
          bonoEventClose();
        }
      });
    }
  }
</script>
<!-- e: 보노보노이벤트 -->


<%-- 바로 구매용 폼 --%>
<form name="commonOrderForm" id="commonOrderForm" method="post" onsubmit="return false;">
  <input type="hidden" name="sessionId" id="commonSessionId" value="" />
  <input type="hidden" name="orderGoodsInfoListStr" id="commonOrderGoodsInfoListStr" value="" />
</form>

<script type="text/javascript">
<%-- 사업자정보확인 팝업 --%>
    function openCommunicationView() {
      var url = "http://www.ftc.go.kr/info/bizinfo/communicationViewPopup.jsp?wrkr_no=5098802366";
      window.open(url, "communicationViewPopup", "width=750, height=700;");
    }

    <%-- 세션ID --%>
      function getSessionId() {
        if (localStorage.getItem("mandomSessionId") == null) {
        	localStorage.setItem("mandomSessionId", "<%=session.getId()%>");
        }

        return localStorage.getItem("mandomSessionId");
      }

  // 유입경로 호출
  function srcPath() {
    var srcPathUrl = window.location.search.substring(1);

    if (srcPathUrl != "") {
      $.ajax({
        url: "${CTX}/ajax/srcPathAjax.do",
        data: {
          "srcPathUrl": srcPathUrl
        },
        type: "GET",
        async: false,
        cache: false,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8"
      });
    }
  }
</script>

<!-- 알럿 팝업 -->
<div class="remodal popup" data-remodal-id="pop_alert">
  <div class="pop-top">
    <h2>알림</h2>
  </div>

  <div class="pop-mid">
    <div class="pop-alert">
      <p></p>
    </div>

    <div class="btn-box confirm">
      <button type="button" class="btn outline-green" data-remodal-action="confirm"><span class="txt">확인</span></button>
    </div>
  </div>
  <a href="javascript:void(0);" class="btn_close" data-remodal-action="close"><span class="hide">닫기</span></a>
</div>

<script type="text/javascript">
  (function (window, document, $) {
    $.fn.tabs = function () {
      $(this).find("[data-target]").bind("click", function (e) {
        $("[data-tab]").removeClass('active');
        $("[data-tab=" + $(this).data("target") + "]").addClass("active");
      });
    };

    $(".dropdown-select").tabs();
    $(".dropdown-select").dropdownAction();
    $(".faq-box").faqAction();
  })(window, document, jQuery);
</script>