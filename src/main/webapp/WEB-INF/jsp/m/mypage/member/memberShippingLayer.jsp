<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>

<div class="pop-top">
	<c:choose>
		<c:when test="${VO.statusFlag eq 'I'}">
			<h2>배송지 등록</h2>
		</c:when>
		<c:otherwise>
			<h2>배송지 수정</h2>
		</c:otherwise>
	</c:choose>
</div>

<div class="pop-mid">
	<div class="order-form">
		<div class="form-group">
			<div class="form-body">
				<div class="row">
					<div class="col col-12">
						<div class="form-label">
							<label for="">배송지 정보</label>
						</div>
						<div class="form-control">
							<input type="text" name="shippingNm" id="shippingNm" class="input" value="${detail.shippingNm}" placeholder="배송지명" required maxlength="50">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col col-12">
						<div class="form-control">
							<input type="text" name="receiverNm" id="receiverNm" class="input" value="${detail.receiverNm}" placeholder="받으시는 분" required maxlength="50">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col col-12">
						<div class="form-control">
							<input type="text" name="phoneNo" id="phoneNo" class="input" value="${detail.phoneNo}" placeholder="휴대폰번호 (‘-’를 제외한 숫자만 입력)" required maxlength="11">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col col-12">
						<div class="form-control">
							<input type="text" name="telNo" id="telNo" class="input" value="${detail.telNo}" placeholder="일반전화 (’-’를 제외한 숫자만 입력)" maxlength="20">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col col-7">
						<div class="form-control">
							<input type="text" name="zipCd" id="zipCd" class="input" value="${detail.zipCd}" placeholder="우편번호" readonly="readonly" maxlength="5">
						</div>
					</div>
					<div class="col col-5">
						<button type="button" class="btn full ico-chev" onclick="addrPopup();"><span class="txt">우편번호 검색</span></button>
					</div>
				</div>
				<div class="row">
					<div class="col col-12">
						<div class="form-control">
							<input type="text" name="addr" id="addr" class="input" value="${detail.addr}" placeholder="주소" readonly required>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col col-12">
						<div class="form-control">
							<input type="text" name="addrDetail" id="addrDetail" class="input" value="${detail.addrDetail}" placeholder="나머지 주소">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col col-12">
						<div class="form-control">
							<span class="checkbox">
								<input type="checkbox" id="defaultChk" class="check" <c:if test="${detail.defaultYn eq 'Y' || VO.compareFlag eq 'sample'}">checked</c:if>>
								<label for="defaultChk" class="lbl">기본 배송지로 설정</label>
							</span>
						</div>
					</div>
				</div>
				<input type="hidden" name="defaultYn" id="defaultYn">
				<input type="hidden" name="oldZipCd" id="oldZipCd" value="${detail.oldZipCd}">
				<input type="hidden" name="oldAddr" id="oldAddr" value="${detail.oldAddr}">
			</div>
		</div>
	</div>
	
	<div class="btn-box confirm">
		<button type="button" class="btn" data-dismiss="popup"><span class="txt">취소</span></button>
		<c:choose>
			<c:when test="${VO.statusFlag eq 'I'}">
				<button type="button" class="btn outline-green" onclick="shippingSave('I','0');"><span class="txt">등록</span></button>
			</c:when>
			<c:otherwise>
				<button type="button" class="btn outline-green" onclick="shippingSave('U','${detail.addressIdx}');"><span class="txt">수정</span></button>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<a href="javascript:" class="btn_close" data-dismiss="popup"><span class="hide">닫기</span></a>
	
