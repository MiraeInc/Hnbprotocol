<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
		<div class="breadcrumb">
			<ul>
				<li>
					<a href="${CTX}/main.do"><span>Home</span></a>
				</li>
				<li <c:if test="${tabType eq '0'}">class="current"</c:if>>
					<a href="${CTX}/cscenter/csMain.do"><span>고객센터</span></a>
				</li>
				<c:if test="${tabType ne '0'}">
					<li class="current">
						<c:if test="${tabType eq '1'}"><a href="javascript:" class="cate-link"><span>공지사항</span></a></c:if>
						<c:if test="${tabType eq '2'}"><a href="javascript:" class="cate-link"><span>FAQ</span></a></c:if>
						<c:if test="${tabType eq '4'}"><a href="javascript:" class="cate-link"><span>혜택안내</span></a></c:if>
						<div class="cate-list">
							<ul>
								<c:if test="${tabType ne '1'}"><li><a href="${CTX}/cscenter/notice/noticeList.do">공지사항</a></li></c:if>
								<c:if test="${tabType ne '2'}"><li><a href="${CTX}/cscenter/faq/faqList.do">FAQ</a></li></c:if>
								<c:if test="${tabType ne '4'}"><li><a href="${CTX}/cscenter/csInfo/benefitInfo.do">혜택안내</a></li></c:if>
							</ul>
						</div>
				</c:if>
			</ul>
		</div>