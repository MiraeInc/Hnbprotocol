<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>

<div class="section section-styling">
	<h3 class="tit">스타일링 무비</h3>
	<div class="section-cont">
		<c:choose>
			<c:when test="${empty styleInfo}" >
				<div class="no-contents">
					<p>등록된 컨텐츠가 없습니다.</p>
					<span>스타일링 팁 메뉴에서<br>다른 컨텐츠를 확인해보세요.</span>
				</div>
			</c:when>
			<c:otherwise>
				<iframe width="100%" src="${styleInfo.videoUrl}?rel=0&amp;autohide=1&amp;autoplay=0&amp;showinfo=0" frameborder="0" title="" marginheight="0" marginwidth="0" allowfullscreen=""></iframe>
			</c:otherwise>
		</c:choose>
	</div>
	<a href="${CTX}/style/tipList.do" class="link-more"><img src="${CTX}/images/${DEVICE}/contents/btn_more.png" alt="MORE"></a>
</div>


<div class="section section-magazine">
	<h3 class="tit">매거진</h3>
	<div class="section-cont">
		<c:choose>
			<c:when test="${empty magazineInfo}" >
				<div class="no-contents">
					<p>등록된 컨텐츠가 없습니다.</p>
					<span>스타일링 팁 메뉴에서<br>다른 컨텐츠를 확인해보세요.</span>
				</div>
			</c:when>
			<c:otherwise>
				<a href="javascript:" onclick="goMagazineDetail('${magazineInfo.magazineIdx}');">
					<div class="thumb">
						<c:set var="imgSplit" value="${fn:split(magazineInfo.magazineImg ,'.') }"/>
						<img src="${IMGPATH}/magazine/${magazineInfo.magazineIdx}/${magazineInfo.magazineImg}" alt="">
					</div>
					<p class="small-txt">${magazineInfo.magazineNm}</p>
					<p class="big-txt">${magazineInfo.title}</p>
				</a>
			</c:otherwise>
		</c:choose>
	</div>
	<a href="${CTX}/brand/brandMagazineList.do" class="link-more"><img src="${CTX}/images/${DEVICE}/contents/btn_more.png" alt="MORE"></a>
</div>

