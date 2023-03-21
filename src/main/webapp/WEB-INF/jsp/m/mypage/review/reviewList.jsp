<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>

<script>
	$(function(){
		//상품후기
		var reviewSlider = null;

		$(".dotdotdot").dotdotdot({
			watch: true,
			height: 63,
			callback: function(isTruncated, orgContent){
				if(isTruncated){
					$(this).parents(".item").addClass("active");
				}else{
					$(this).parents(".item").removeClass("active");
				}
			}
		});

		$('.review-list').on('click', '.item .btn_toggle', function(e){
			
			var $this = $(this);
			var $root = $this.closest('.item');

			if($root.hasClass('type-photo')){
				
				if($root.hasClass('close')){
					reviewSlider = $root.find('.photo-slider').lightSlider({
						item:1,
						loop:false,
						pager: false,
						slideMargin: 0
					});
					
				}else{
					reviewSlider.destroy();
				}
			}
			
			if($root.hasClass('close')){
				$root.addClass('open').removeClass('close');
				$this.siblings(".dotdotdot").trigger("destroy");
			}else{
				$root.addClass('close').removeClass('open');
				$this.siblings(".dotdotdot").dotdotdot({
					watch: true,
					height: 63,
					wrap: "letter"
				});
			}

		})
	});

</script>

<c:choose>
	<c:when test="${!empty list}">
		<div class="review-menu">
			<ul>
				<li <c:if test="${VO.reviewType eq 'ALL'}">class='active'</c:if>>
					<a href="javascript:" onclick="reviewList('ALL');"><span>ALL</span> <em>(<i class="em"><fmt:formatNumber value="${totalCount}" /></i>)</em></a>
				</li>
				<li <c:if test="${VO.reviewType eq 'PHOTO'}">class='active'</c:if>>
					<a href="javascript:" onclick="reviewList('PHOTO');"><span>포토후기</span> <em>(<i class="em"><fmt:formatNumber value="${photoCnt}" /></i>)</em></a>
				</li>
			</ul>
			<p>
				다른 분들의 쇼핑에 도움이 될 수 있는 <br/>
				솔직한 후기를 올려 주세요. <br/>
				<strong>포토 후기를 작성해 주시면 <span class="em">500 P</span>, <br/> 일반 후기를 작성해 주시면 <span class="em">100 P</span> 를 적립해 드립니다.</strong>
			</p>
		</div>
		
		<ul class="review-list">
			<c:forEach var="list" items="${list}">
				<li>
					<c:set var="listType" value="type-default"/>
					<c:choose>
						<c:when test="${!empty list.img1}"><c:set var="listType" value="type-photo"/></c:when>
						<c:otherwise><c:set var="listType" value="type-default"/></c:otherwise>
					</c:choose>
					<div class="item ${listType} close">
						<div class="item-author">
							<c:choose>
								<c:when test="${empty reviewAvg}">
									<c:set var="reviewAvg" value="0" />
								</c:when>
								<c:otherwise>
									<fmt:formatNumber  var="reviewAvg" value="${reviewAvg}" type="number" maxFractionDigits="0"/>
								</c:otherwise>
							</c:choose>
							<span class="rate-star" data-rate="${reviewAvg}"><i></i></span>
							<span class="author">${list.memberNm}</span>
						</div>
						<div class="item-content">
							<c:if test="${!empty list.img1}">
								<div class="photo">
									<ul class="photo-slider">
										<c:set var="imgSplit1" value="${fn:split(list.img1,'.')}"/>
		                       			<c:set var="imgSplit2" value="${fn:split(list.img2,'.')}"/>
		                       			<c:set var="imgSplit3" value="${fn:split(list.img3,'.')}"/>
		                       			<c:if test="${!empty imgSplit1[0]}">
			                           		<li><img src="${IMGPATH}/review/${list.reviewIdx}/${imgSplit1[0]}_T420.${imgSplit1[1]}" onerror="this.src='${CTX}/images/${DEVICE}/noimage.jpg'"></li>
		                       			</c:if>
		                           		<c:if test="${!empty imgSplit2[0]}">
			                           		<li><img src="${IMGPATH}/review/${list.reviewIdx}/${imgSplit2[0]}_T420.${imgSplit2[1]}" onerror="this.src='${CTX}/images/${DEVICE}/noimage.jpg'"></li>
		                           		</c:if>
		                           		<c:if test="${!empty imgSplit3[0]}">
			                           		<li><img src="${IMGPATH}/review/${list.reviewIdx}/${imgSplit3[0]}_T420.${imgSplit3[1]}" onerror="this.src='${CTX}/images/${DEVICE}/noimage.jpg'"></li>
		                           		</c:if>
									</ul>
									<c:set var="imgCount" value="0"/>
									<c:if test="${list.img1 ne null && list.img1 ne ''}"><c:set var="imgCount" value="${imgCount+1}"/></c:if>
									<c:if test="${list.img2 ne null && list.img2 ne ''}"><c:set var="imgCount" value="${imgCount+1}"/></c:if>
									<c:if test="${list.img3 ne null && list.img3 ne ''}"><c:set var="imgCount" value="${imgCount+1}"/></c:if>
									<div class="count"><em>${imgCount}</em><span class="hide"></span></div>
								</div>
							</c:if>
							<p class="dotdotdot">${list.reviewDesc}</p>
							<button type="button" class="btn_toggle"><span></span></button>
						</div>
						<c:if test="${list.memberIdx eq VO.memberIdx}">
							<div class="item-btns">
								<button class="btn white" onclick="location.href='${CTX}/mypage/review/reviewWrite.do?statusFlag=U&orderDetailIdx=${list.orderDetailIdx}&winnerIdx=${list.winnerIdx}&reviewIdx=${list.reviewIdx}&layerType=goods';"><span class="txt">수정</span></button>
								<button class="btn white" onclick="goReviewDelete('${list.reviewIdx}', '${list.reviewPoint}');"><span class="txt">삭제</span></button>
							</div>
						</c:if>
					</div>
				</li>
			</c:forEach>
		</ul>
		
		<div class="pagin-nav">
			<c:out value="${page.pageStr}" escapeXml="false"/>
		</div>
	</c:when>
	<c:otherwise>
		<div class="nodata">
			<p class="tit">이 상품의  첫 번째 후기를 작성해 주세요.</p>
			<img src="${CTX}/images/${DEVICE}/common/ico_review_write.png" alt="" />
			<p>
				고객님의 솔직한 상품후기를<br/>
				다른 고객님 들에게도 공유해주세요.<br/>
				<strong>사진과 함께 포토후기 작성시에는 <span class="em">500P</span><br/>일반후기 작성시 <span class="em">100P</span>를 드립니다.</strong>
				<br/><br/>
				(※ 제품을 구매하신 회원만 작성 가능합니다.)
			</p>
			<div class="btn-box">
				<button type="button" class="btn" onclick="goReview();"><span class="txt">상품후기 작성하기</span></button>
			</div>
		</div>
	</c:otherwise>
</c:choose>