<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="join.main" />
<script>

	// SNS 간편가입
	function snsJoinPopup(snsType){
		// N : 네이버, F : 페이스북, K : 카카오, P : 페이코
		if(snsType == "N"){
			owScrollYesCenter("${naverAuthUrl}", "_blank", "444", "510");
		}else if(snsType == "F"){
			owScrollYesCenter("${facebookAuthUrl}", "_blank", "600", "600");
		}else if(snsType == "K"){
			owScrollYesCenter("${kakaoAuthUrl}", "_blank", "444", "510");
		}else if(snsType == "W"){
			owScrollYesCenter("${wonderAuthUrl}", "_blank", "444", "510");
		}else{
			owScrollYesCenter("${paycoAuthUrl}", "_blank", "444", "510");
		}
	}

</script>
</head>
<body>
	<div id="single">
		<header id="header">
			<h1 id="logo">
				<a href="${CTX}/main.do">
					<img src="${CTX }/images/logo_mandom.png" alt="Mandom">
				</a>
			</h1>
			<h2 class="page-title">회원가입</h2>
		</header>
		<article id="container" class="clearfix">
			<div id="content" class="inner">
				<div class="join_benefit">
					<strong>회원혜택</strong>
					<ul class="clearfix" align="center">
						<li class="item_1">
							<p>회원가입 포인트<br>2,000P 즉시 지급</p>
						</li>
						<li class="item_2">
							<p>무료 배송 쿠폰<br>매월 지급</p>
						</li>
						<li class="item_3">
							<p>할인 쿠폰<br>매월 등급별 지급</p>
						</li>
						<li class="item_4">
							<p>회원등급에 따라<br>구매포인트 적립</p>
						</li>
					</ul>
				</div>

				<div class="easy_join">
					<h2 class="box_title">회원가입</h2>
					<p class="box_desc">본인인증 없이 간편하게 가입하실 수 있습니다.</p>

					<div class="button-group">
						<a href="${CTX }/member/joinStep02.do" class="btn btn-black">회원가입</a>
					</div>
				</div>

				<div class="sns_join">
					<h2 class="box_title">SNS 간편가입</h2>
					<p class="box_desc">SNS 계정으로 간편하게 가입하세요.</p>

					<div class="sns_btns">
						<a href="javascript:" class="btn_sns_naver" onclick="snsJoinPopup('N');">네이버 계정으로 가입</a>
						<a href="javascript:" class="btn_sns_kakao" onclick="snsJoinPopup('K');">카카오톡 계정으로 가입</a>
                        <%--
                        <a href="javascript:" class="btn_sns_facebook" onclick="snsJoinPopup('F');">페이스북 계정으로 가입</a>
						<a href="javascript:" class="btn_sns_payco" onclick="snsJoinPopup('P');">페이코 계정으로 가입</a>
						<a href="javascript:" class="btn_sns_wonder" onclick="snsJoinPopup('W');">원더로그인 계정으로 가입</a>
						 --%>
					</div>
				</div>
			</div>
		</article>
	</div>
</body>
</html>