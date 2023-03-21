<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>

						<c:if test="${(productResultList eq null or fn:length(productResultList) eq 0) and (hashtagResultList eq null or fn:length(hashtagResultList) eq 0) }">
								검색결과가 없습니다.
						</c:if>
							
							<div class="showcase_list">
								<c:if test="${!(productResultList eq null or fn:length(productResultList) eq 0)  }">
								<div class="showcase_item">
									<ul>
									<c:forEach var="plist" items="${productResultList}" varStatus="idx">
										<c:set var="imgSplit" value="${fn:split(plist.mainFile ,'.') }"/>
										<li <c:if test="${idx.index == 0}">class="active"</c:if> >
											<a href="javascript:" onclick="goTopSearch('${plist.goodsTitle}');" data-img="${IMGPATH}/goods/${plist.goodsIdx}/${imgSplit[0]}_B.${imgSplit[1]}" data-title="${plist.goodsTitle}" data-goodscd="${plist.goodsCd}" data-keyword="${plist.goodsTitle}"><span>${plist.goodsTitle}</span></a>
										</li>										
									</c:forEach>
									
									</ul>
								</div>
								</c:if>
								<c:if test="${!(hashtagResultList eq null or fn:length(hashtagResultList) eq 0) }">
								<div class="showcase_tag">
									<ul>
									
									<c:forEach var="hlist" items="${hashtagResultList}" varStatus="idx">
										<li>
											<a href="#" onclick="goTopSearch('#${hlist.hashtagNm}');" data-img="${IMGPATH}/goods/${hlist.goodsIdx}/${imgSplit[0]}_B.${imgSplit[1]}" data-title="${hlist.goodsTitle}" data-goodscd="${hlist.goodsCd}" data-keyword="#${hlist.hashtagNm}"><span>#${hlist.hashtagNm}</span></a>
										</li>										
									</c:forEach>

									</ul>
								</div>
								</c:if>
							</div>