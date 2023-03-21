<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main" />	
</head>
<body>
	<div class="content comm-brand brand-aboutmandom">
		<page:applyDecorator  name="mobile.brandmenu" encoding="UTF-8"/>

		<div class="page-body">
			<div class="brand-box">
				<div class="brand-img">
					<img src="${CTX}/images/${DEVICE}/brand/img_mandom.png" alt="">
					<div class="hide">
						<h3>MANDOM MISSON</h3>
						<strong>Human to Human</strong>
						<p>우리 맨담은 건강과 청결과 아름다움을 통해 자유분방하고 대담하게 당신의 일상을 발견과 감동으로 채워주는 인간계 기업입니다.</p>

						<h3>MANDOM PRINCIPLES</h3>
						<p>생활자발&middot;생활자착, Challenge&middot;Change&middot;Innovation (도전&middot;변화&middot;혁신), 전원참가, 사회와의 공존&middot;공생&middot;공동가치창조, 인재주의</p>

						<h3>MANDOM SPIRIT</h3>
						<p>OYAKUDACHI (이바지함)&middot;인간존중&middot;자유활달</p>
					</div>
				</div>

			</div>

			<div class="brand-title type2" style="margin-top: 45px; margin-bottom: 0;">
				<h3>MANDOM KOREA</h3>
			</div>
			<div class="brand-mandom">
				<p>맨담은 1927년 일본 오사카에 설립되었고 1933년 남성 헤어스타일링 상품으로 ‘단초치쿠’를 최초로 발매하였습니다. 이후, 1969년 인도네시아 현지 법인을 시작으로 현재는 태국, 필리핀, 말레이시아, 싱가포르, 대만, 중국(홍콩), 한국, 인도, 베트남 등 전세계 <b>총 13개국</b>에서 자회사를 가지고 있는 종합 화장품 전문 기업입니다.</p>
				<br>
				<p>맨담은 2017년 12월에 90주년을 맞이하며 그 동안 남성 그루밍 브랜드 ‘GATSBY(갸스비), LUCIDO(루시도)’를 포함하여 여성 헤어케어 브랜드인 ‘LUCIDO-L(루시도엘)’ 그리고, 여성 클렌징 브랜드 ‘Bifesta(비페스타)’ 등 다수의 전문 브랜드를 가지고 있습니다.</p>
				<br>
				<p>맨담코리아는 1994년 한국 총판 대리점을 시작으로 2005년에 ‘일본㈜맨담’의 100% 자회사로 전환하였으며 ‘GATSBY(갸스비), LUCIDO-L(루시도엘), Bifesta(비페스타) 등’의 자체 브랜드와 함께 ‘덴탈프로, 마마버터, 오오시마츠바키 등’의 일본 타사 브랜드 상품을 수입 판매하고 있습니다.</p>
				<br>
				<p>주요 거래처로는 Health &amp; Beauty Shop(Drug Store: 올리브영, 왓슨스, 롭스, 부츠, 판도라 등), CVS(편의점: CU, GS25, 7-11, Ministop, emart24 등), 대형마트 (이마트, 홈플러스 롯데마트 등) 및 온라인 판매 등이 있습니다.</p>
				<br>
				<p>맨담 그룹은 2027년 100주년을 맞이하게 되며 항상 소비자의 미 의식을 환기시킬 수 있는 가치있고 매력있는 상품과 서비스로 ‘쾌적한 생활에 이바지’한다는 미션을 가지고 소비자 만족의 최대화를 지향해 나가겠습니다.</p>
			</div>

			<div class="brand-title type2">
				<h3>취급 브랜드</h3>
			</div>

			<div class="mandom-brand-wrap">
				<div class="mandom-brand">
					<p class="brand-txt">일본 (주)맨담 브랜드</p>
					<ul class="brand-logo">
						<li>
							<img src="${CTX}/images/${DEVICE}/brand/img_brand_logo1.png" alt="GATSBY">
						</li>
						<li>
							<img src="${CTX}/images/${DEVICE}/brand/img_brand_logo2.png" alt="BIFESTA">
						</li>
						<li>
							<img src="${CTX}/images/${DEVICE}/brand/img_brand_logo3.png" alt="LUCIDO-L">
						</li>
						<li>
							<img src="${CTX}/images/${DEVICE}/brand/img_brand_barrierrepair.png" alt="barrierrepair">
						</li>
					</ul>
				</div>
				<div class="mandom-brand">
					<p class="brand-txt">기타 브랜드</p>
					<ul class="brand-logo">
						<li>
							<img src="${CTX}/images/${DEVICE}/brand/img_brand_logo5.png" alt="DENTALPRO">
						</li>
<%--
						<li>
							<img src="${CTX}/images/${DEVICE}/brand/img_brand_logo6.png" alt="MAMABUTTER">
						</li>
						<li>
							<img src="${CTX}/images/${DEVICE}/brand/img_brand_logo7.png" alt="CHARLEY">
						</li>
 --%>
					</ul>
				</div>
			</div>
		</div>
    </div>            
</body>
</html>