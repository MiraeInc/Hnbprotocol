<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="description" content="'헤아쟈무 뭐야?' '무빙 뭐야?' 남성 화장품 브랜드 '갸스비'의 사이트. 제품 정보, 제품 사용법, 이벤트, CM도 공개 중">
<meta name="keywords" content="헤어 스타일링, 헤어 왁스, 무빙, 헤아쟈무 그리스 스타일링 그리스, 헤어 컬러 턴 컬러, 면도 셀프 컷, 좋아 면도기, 헤어 트리머, 눈썹, 몸 종이, 바디 시트 세안 시트 세안 종이, 얼굴 종이, 스킨 케어">
<meta name="author" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="format-detection" content="telephone=no, address=no, email=no">
<title><spring:message code='common.title' /></title>
<link rel="apple-touch-icon" href="${CTX}/images/favicon-152.png">
<link rel="shortcut icon" href="${CTX}/images/favicon.ico">

<link rel="stylesheet" href="${CTX}/css/m/normalize.css">
<link rel="stylesheet" href="${CTX}/css/m/common_2018.css">
<link rel="stylesheet" href="${CTX}/css/m/contents_2018.css">
<link rel="stylesheet" href="${CTX}/css/m/promotion.css">

<!-- 리마케팅 -->
<script type="text/javascript" src="//adimg.daumcdn.net/rt/roosevelt.js" async></script>

<script type="text/javascript">
		// GA
		(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
		new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
		j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
		'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
		})(window,document,'script','dataLayer','GTM-NNQDJVC');
		
		// mobon Enliple Tracker Start
		var device = "${DEVICE}".toUpperCase();
		(function(a,g,e,n,t){a.enp=a.enp||function(){(a.enp.q=a.enp.q||[]).push(arguments)};n=g.createElement(e);n.async=!0;n.defer=!0;n.src="https://cdn.megadata.co.kr/dist/prod/enp_tracker_self_hosted.min.js";t=g.getElementsByTagName(e)[0];t.parentNode.insertBefore(n,t)})(window,document,"script");
	    enp('create', 'common', 'mandom', { device: device });    
	    enp('send', 'common', 'mandom');
		// mobon Enliple Tracker End
</script>

<script src="${CTX}/js/jquery-3.2.1.min.js"></script>

<script>

	$(document).ready(function(){
		$("#replyContents").click(function(){
			if("${IS_LOGIN}" == "false"){
				if(confirm("<spring:message code='common.util011'/>") == true){
					location.href="${CTX}/login/loginPage.do?refererYn=Y"; 
				}
			}
		});
	});
	
	// 로그인 페이지
	function goLogin(){
		if("${IS_LOGIN}" == "false"){
			location.href = "${CTX}/login/loginPage.do?refererYn=Y";
		}else{
			alert("이미 로그인 되었습니다.");
			return false;
		}
	}
	
	// 댓글 저장
	function goSave(){
		if($.trim($("#replyContents").val()) ==""){ // 내용
			alert("댓글 내용을 입력 해 주세요."); 
			$("#replyContents").focus(); 
			return false;
		}
		if($.trim($("#replyContents").val().length) > 500){ // 내용 길이
			alert("500자를 초과 할 수 없습니다."); 
			$("#replyContents").focus(); 
			return false;
		}
		
		if(confirm("등록 하시겠습니까?") == true){
			$.ajax({			
				url: "${CTX}/ajax/event/entry/quizEventSaveAjax.do",
				data: {
							"eventIdx"	:	$("#eventIdx").val(),
							"replyContents" : $("#replyContents").val()
						 },
				type: "POST",	
				async: false,
				contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
			 	error: function(request, status, error){ 
			 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
				},
				success: function(flag){
					if(Number(flag) > 0){ 
						alert("등록 되었습니다.");
					}else if(Number(flag)==-1){
						alert("이미 등록 하셨습니다.");
					}else if(Number(flag) == -100) {
						alert("로그인이 필요합니다.");
						location.href="${CTX}/login/loginPage.do?refererYn=Y";
					}else{
						alert("오류가 발생 하였습니다.");
					}
					document.location.reload();
				}
			});
		}
	}
	
	// 댓글 삭제
	function replyDelete(idx){
		if(confirm("삭제 하시겠습니까?") == true){
			$.ajax({			
				url: "${CTX}/ajax/event/entry/quizEventDeleteAjax.do",
				data: {
							"replyIdx"	:	idx,
						 },
				type: "POST",	
				async: false,
				contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
			 	error: function(request, status, error){ 
			 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
				},
				success: function(flag){
					if(Number(flag) > 0){ 
						alert("삭제 되었습니다.");
						document.location.reload();
					}
				}
			});
		}
	}
	
	//페이지 이동
	function goPage(page){
		$("#pageNo").val(page);
		
		var frm = document.quizForm;
		frm.action = "${CTX}/event/entry/quizEvent.do";
		frm.submit();
	}

</script>
</head>
<body>	

<c:set var="status"><spring:message code="server.status" /></c:set>
<c:if test="${status eq 'LIVE'}" >
	<!-- Google Tag Manager (noscript) -->
	<noscript>
		<iframe src="https://www.googletagmanager.com/ns.html?id=GTM-NNQDJVC" height="0" width="0" style="display:none;visibility:hidden"></iframe>
	</noscript>
	<!-- End Google Tag Manager (noscript) -->
</c:if>

<form name="quizForm" id="quizForm" method="get" onsubmit="return false;">
	<input type="hidden" name="pageNo" id="pageNo" value="${schVO.pageNo}"/>
	<input type="hidden" name="eventIdx" id="eventIdx" value="51"/>
	
	<div id="wrap">
		<section class="promo-section-1">
			<img src="${CTX}/images/m/promotion/img_promo_1.png" alt="" />
			<div class="video">
				<iframe src="https://www.youtube.com/embed/QQw5b0Zt_l0?rel=0&autohide=1&autoplay=0&showinfo=0" frameborder="0" title="" marginheight="0" marginwidth="0" allowfullscreen></iframe>
			</div>
			<a href="${CTX}/gatsby/main.do" class="link-hint"><span class="sr-only">힌트 보러가기</span></a>
		</section>
	
		<section class="promo-section-2">
			<img src="${CTX}/images/m/promotion/img_promo_2.png" alt="" />
			<a href="javascript:"  class="link-login" onclick="goLogin();"><span class="hide">로그인하러가기</span></a>
			<a href="${CTX}/main.do" class="link-mall"><span class="hide">쇼핑 하러가기</span></a>
		</section>
	
		<c:if test="${detail.replyYn eq 'Y'}">
			<section class="promo-comment">
				<div class="comment-box">
					<p class="comment-txt1">퀴즈 이벤트 댓글</p>
					<div class="reply-box">
						<div class="textarea-box">
							<textarea rows="3" name="replyContents" id="replyContents" placeholder="내용과 무관한 댓글, 악플, 홍보성 광고글 등은 삭제될 수 있습니다. 댓글은 300자 이내로 작성해 주세요."></textarea>
						</div>
						<small>*욕설이나 비방 내용 또는 관련 없는 내용을 등록하실 경우 안내 없이 삭제 처리될 수 있습니다.</small>
						<div class="btn-box confirm">
							<a href="javascript:" class="btn black" onclick="goSave();"><span class="txt">등록하기</span></a>
						</div>
					</div>
					<p class="comment-txt2">총 <strong><fmt:formatNumber value="${totalCount}"/></strong>개의 댓글이 달렸습니다.</p>
					<div class="comment-list">
						<ul>
							<c:forEach var="list" items="${quizEventList}" varStatus="idx">
								<li>
									<div class="comment-wrap">
										<div class="comment-inner">
											<c:choose>
												<c:when test="${list.snsType eq 'SNS_TYPE10'}">
													<p class="comment-ti ico_k">${list.memberNm}</p>
												</c:when>
												<c:when test="${list.snsType eq 'SNS_TYPE20'}">
													<p class="comment-ti ico_n">${list.memberNm}</p>
												</c:when>
												<c:when test="${list.snsType eq 'SNS_TYPE30'}">
													<p class="comment-ti ico_f">${list.memberNm}</p>
												</c:when>
												<c:when test="${list.snsType eq 'SNS_TYPE40'}">
													<p class="comment-ti ico_p">${list.memberNm}</p>
												</c:when>
												<c:otherwise>
													<p class="comment-author">${list.memberNm}</p>
												</c:otherwise>
											</c:choose>
											<p class="comment-date"><span>${list.regYmd}</span><span>${list.regHms}</span></p>
											<p class="comment-txt"><c:out value="${list.replyContents}" escapeXml="false"/></p>
											<c:if test="${USERINFO.memberIdx eq list.regIdx}">
				                                <a href="javascript:" class="comment-del" onclick="replyDelete('${list.replyIdx}');">
				                                    <img src="${CTX}/images/${DEVICE}/common/ico_comment_del.png" alt="삭제">
				                                </a>
			                                </c:if>
										</div>
									</div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
		
				<div class="pagin-nav">
					<c:out value="${page.pageStr}" escapeXml="false"/>
				</div>
			</section>
		</c:if>
	</div>
</form>

<c:if test="${status eq 'LIVE'}" >
	<!-- WIDERPLANET  SCRIPT START 2017.8.29 -->
	<div id="wp_tg_cts" style="display:none;"></div>
	<script type="text/javascript">
	var wptg_tagscript_vars = wptg_tagscript_vars || [];
	wptg_tagscript_vars.push(
	(function() {
		return {
			wp_hcuid:"",  	/*Cross device targeting을 원하는 광고주는 로그인한 사용자의 Unique ID (ex. 로그인 ID, 고객넘버 등)를 암호화하여 대입.
					 *주의: 로그인 하지 않은 사용자는 어떠한 값도 대입하지 않습니다.*/
			ti:"37336",	/*광고주 코드*/
			ty:"Home",	/*트래킹태그 타입*/
			device:"mobile"	/*디바이스 종류 (web 또는 mobile)*/
		};
	}));
	</script>
</c:if>

</body>
</html>