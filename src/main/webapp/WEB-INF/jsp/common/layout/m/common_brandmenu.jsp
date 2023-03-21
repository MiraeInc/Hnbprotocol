<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
	<div class="breadcrumb">
		<ul>
			<li>
				<a href="${CTX}/main.do"><span>Home</span></a>
			</li>
			<li class="current">
				<c:if test="${tabType eq '1'}"><a href="${CTX}/brand/brandAboutMandom.do" class="cate-link">ABOUT MANDOM</a></c:if>
				<c:if test="${tabType eq '4'}"><a href="${CTX}/brand/brandGatsby.do?brandIdx=1" class="cate-link">BRAND 소개</a></c:if>
				<c:if test="${tabType eq '2'}"><a href="${CTX}/brand/brandAdList.do?brandIdx=1" class="cate-link">동영상</a></c:if>
				<c:if test="${tabType eq '3'}"><a href="${CTX}/brand/brandMagazineList.do?brandIdx=1" class="cate-link">매거진</a></c:if>
				<div class="cate-list">
					<ul>
						<li <c:if test="${tabType eq '1'}">class="active"</c:if>><a href="${CTX}/brand/brandAboutMandom.do">ABOUT MANDOM</a></li>
						<li <c:if test="${tabType eq '4'}">class="active"</c:if>><a href="${CTX}/brand/brandGatsby.do?brandIdx=1">BRAND 소개</a></li>
						<li <c:if test="${tabType eq '2'}">class="active"</c:if>><a href="${CTX}/brand/brandAdList.do?brandIdx=1">동영상</a></li>
						<li <c:if test="${tabType eq '3'}">class="active"</c:if>><a href="${CTX}/brand/brandMagazineList.do?brandIdx=1">매거진</a></li>
					</ul>
				</div>
			</li>
		</ul>
	</div>

	<div class="tab-style" data-size="4">
		<a href="${CTX}/brand/brandAboutMandom.do" class="tab-link <c:if test="${tabType eq '1'}">active</c:if>"><span>ABOUT<br/>MANDOM</span></a>
		<a href="${CTX}/brand/brandGatsby.do?brandIdx=1" class="tab-link <c:if test="${tabType eq '4'}">active</c:if>"><span>BRAND<br/>소개</span></a>
		<a href="${CTX}/brand/brandAdList.do?brandIdx=1" class="tab-link <c:if test="${tabType eq '2'}">active</c:if>"><span>동영상</span></a>
		<a href="${CTX}/brand/brandMagazineList.do?brandIdx=1" class="tab-link <c:if test="${tabType eq '3'}">active</c:if>"><span>매거진</span></a>
	</div>
	
	<%-- <div class="breadcrumb">
		<ul>
			<li>
				<a href="${CTX}/main.do"><span>H</span></a>
			</li>
			<li>
				<a href="${CTX}/brand/brandAboutMandom.do"><span>브랜드</span></a>
			</li>
			<li class="current">
				<c:if test="${tabType eq '1'}"><a href="javascript:" class="cate-link">ABOUT mandom</a></c:if>
				<c:if test="${tabType eq '2'}"><a href="javascript:" class="cate-link">광고AD</a></c:if>
				<c:if test="${tabType eq '3'}"><a href="javascript:" class="cate-link">매거진</a></c:if>
				<div class="cate-list">
					<ul>
						<c:if test="${tabType ne '1'}"><li><a href="${CTX}/brand/brandAboutMandom.do">ABOUT mandom</a></li></c:if>
						<c:if test="${tabType ne '2'}"><li><a href="${CTX}/brand/brandAdList.do">광고AD</a></li></c:if>
						<c:if test="${tabType ne '3'}"><li><a href="${CTX}/brand/brandMagazineList.do">매거진</a></li></c:if>
	                 </ul>
				</div>
			</li>
		</ul>
	</div> --%>