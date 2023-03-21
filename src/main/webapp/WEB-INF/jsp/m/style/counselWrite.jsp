<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main"/>	
<script>

// 상담 신청
function goSave(gubun){
	if($.trim($("#qTitle").val()) ==""){ // 제목
		alert("제목을 입력 해 주세요."); 
		$("#qTitle").focus(); 
		return false;
	}
	if($("input:radio[name='hairStyle']:checked").length<1){
		alert("모발 길이를 선택 해 주세요.");
		$("input:radio[name='hairStyle']").focus();
		return false;
	}
	if($("input:radio[name='hairType']:checked").length<1){
		alert("모발 타입을 선택 해 주세요."); 
		$("input:radio[name='hairType']").focus();
		return false;
	}
	if($.trim($("#qContents").val()) ==""){ // 제목
		alert("내용을 입력 해 주세요."); 
		$("#qContents").focus(); 
		return false;
	}
	if($("input:checkbox[id='chkAlarmYn']").is(":checked")==true){ // 답변 문자 알람 받기 여부
		$("#alarmYn").val("Y");
	}else{
		$("#alarmYn").val("N");
	}
	
	if(confirm(gubun+" 하시겠습니까?") == true){
		if($("#statusFlag").val()==null || $("#statusFlag").val()==""){
			$("#statusFlag").val("I");
		}
		
		var frm = document.counselForm;
		frm.action = "${CTX}/style/counselSave.do";
		frm.submit();
	}
}

// 이미지 삭제
function deleteFile(t) {
	 $(t).parent().parent().remove();
}

</script>
</head>
<body>
<form name="counselForm" id="counselForm" method="post" onsubmit="return false;">
	<input type="hidden" name="counselIdx" id="counselIdx" value="${detail.counselIdx}"/>
	<input type="hidden" name="statusFlag" id="statusFlag" value="${VO.statusFlag}"/>
	
	<div class="content comm-styleg styleg-counsel">
  		<page:applyDecorator  name="mobile.stylemenu" encoding="UTF-8"/>              

        <div class="helper-box">
            <strong data-icon="question">스타일 상담안내</strong>
            <div class="helper-box-inner inset">
                <ul>
                    <li>상담을 원하시는 부분에 대해 상세한 고민 내용, 사진과 함께 신청을 해주시면 스타일 전문가께서 빠른 시일내에 상담해드리도록 하겠습니다.</li>
                    <li style="margin-top: 10px;">개인정보보호법에 의거 개인의 연락처,이메일, 카드 번호 등의 개인정보는 기재하지 말아 주십시오.<br>(※ 고객님의 부주의로 인한 피해는 면역공방에서는 책임 지지 않습니다.)</li>
                </ul>
            </div>
        </div>

        <div class="form-wrap">
            <div class="form-box">
                <p class="tit required">제목</p>
                <div class="row">
                    <div class="col col-12">
                        <div class="form-control">
                            <input type="text" class="input" placeholder="제목" name="qTitle" id="qTitle" value="${detail.qTitle}" maxlength="32"/>
                        </div>
                    </div>
                </div>

                <p class="tit required">모발길이</p>
                <div class="row">
                    <div class="col col-6">
                        <span class="radiobox">
                            <input type="radio" name="hairStyle" id="hairStyle1" class="radio" value="HAIR_STYLE10" <c:if test="${detail.hairStyle eq 'HAIR_STYLE10'}">checked</c:if>>
                            <label for="hairStyle1" class="lbl">VERY SHORT</label>
                        </span>
                    </div>
                    <div class="col col-6">
                        <span class="radiobox">
                            <input type="radio" name="hairStyle" id="hairStyle2" class="radio" value="HAIR_STYLE20" <c:if test="${detail.hairStyle eq 'HAIR_STYLE20'}">checked</c:if>>
                            <label for="hairStyle2" class="lbl">SHORT</label>
                        </span>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-6">
                        <span class="radiobox">
                            <input type="radio" name="hairStyle" id="hairStyle3" class="radio" value="HAIR_STYLE30"<c:if test="${detail.hairStyle eq 'HAIR_STYLE30'}">checked</c:if>>
                            <label for="hairStyle3" class="lbl">MEDIUM SHORT</label>
                        </span>
                    </div>
                    <div class="col col-6">
                        <span class="radiobox">
                            <input type="radio" name="hairStyle" id="hairStyle4" class="radio" value="HAIR_STYLE40"<c:if test="${detail.hairStyle eq 'HAIR_STYLE40'}">checked</c:if>>
                            <label for="hairStyle4" class="lbl">MEDIUM</label>
                        </span>
                    </div>
                </div>

                <p class="tit required">모발타입</p>
                <div class="row">
                    <div class="col col-3">
                        <span class="radiobox">
                            <input type="radio" name="hairType" id="hairType1" class="radio" value="HAIR_TYPE10" <c:if test="${detail.hairType eq 'HAIR_TYPE10'}">checked</c:if>/>
                            <label for="hairType1" class="lbl">곱슬</label>
                        </span>
                    </div>
                    <div class="col col-3">
                        <span class="radiobox">
                            <input type="radio" name="hairType" id="hairType2" class="radio" value="HAIR_TYPE20" <c:if test="${detail.hairType eq 'HAIR_TYPE20'}">checked</c:if>/>
                            <label for="hairType2" class="lbl">반 곱슬</label>
                        </span>
                    </div>
                    <div class="col col-3">
                        <span class="radiobox">
                            <input type="radio" name="hairType" id="hairType3" class="radio" value="HAIR_TYPE30" <c:if test="${detail.hairType eq 'HAIR_TYPE30'}">checked</c:if>/>
                            <label for="hairType3" class="lbl">직모</label>
                        </span>
                    </div>
                    <div class="col col-3">
                        <span class="radiobox">
                            <input type="radio" name="hairType" id="hairType4" class="radio" value="HAIR_TYPE40" <c:if test="${detail.hairType eq 'HAIR_TYPE40'}">checked</c:if>/>
                            <label for="hairType4" class="lbl">펌</label>
                        </span>
                    </div>
                    <div class="col col-12">
                        <small class="form-info">* 모발 길이, 모발 타입은 관리자 에게만 보입니다.</small>
                    </div>
                </div>

                <p class="tit required">내용</p>
                <div class="row">
                    <div class="col col-12">
                        <div class="form-control">
                            <textarea class="text" rows="5" placeholder="상담 내용을 입력해 주세요." name="qContents" id="qContents"><c:out value="${detail.qContents}" escapeXml="false"/></textarea>
                        </div>
                    </div>
                </div>

                <p class="tit required">첨부사진</p>
                <div class="row">
                    <div class="col col-12">
                        <div class="fileimg-upload">
                            <p class="btn full">이미지 첨부</p>
                            <input type="file" name="fileData" id="upFile" class="real-upload">
                        </div>
                    </div>
                </div>
                <div class="row fileimg-wrap" id="filebox">
                	<c:if test="${!empty detail.qImg1}">
                    <div class="col col-3">
                        <div class="fileimg-box">
                            <img src="${IMGPATH}${detail.qImgPath1}" alt="첨부이미지">
                            <input type='hidden' name='qImg1' value='${detail.qImg1}'>
                            <a href="javascript:" class="file-delete" onclick='deleteFile(this);'>
                                <img src="${CTX}/images/${DEVICE}/common/ico_delete_fileimg.png" alt="첨부이미지 제거">
                            </a>
                        </div>
                    </div>
                    </c:if>
                    <c:if test="${!empty detail.qImg2}">
                    <div class="col col-3">
                        <div class="fileimg-box">
                            <img src="${IMGPATH}${detail.qImgPath2}" alt="첨부이미지">
                            <input type='hidden' name='qImg2' value='${detail.qImg2}'>
                            <a href="javascript:" class="file-delete" onclick='deleteFile(this);'>
                                <img src="${CTX}/images/${DEVICE}/common/ico_delete_fileimg.png" alt="첨부이미지 제거">
                            </a>
                        </div>
                    </div>
                    </c:if>
                    <c:if test="${!empty detail.qImg3}">
                    <div class="col col-3">
                        <div class="fileimg-box">
                            <img src="${IMGPATH}${detail.qImgPath3}" alt="첨부이미지">
                            <input type='hidden' name='qImg3' value='${detail.qImg3}'>
                            <a href="javascript:" class="file-delete" onclick='deleteFile(this);'>
                                <img src="${CTX}/images/${DEVICE}/common/ico_delete_fileimg.png" alt="첨부이미지 제거">
                            </a>
                        </div>
                    </div>
                    </c:if>
                    <c:if test="${!empty detail.qImg4}">
                    <div class="col col-4">
                        <div class="fileimg-box">
                            <img src="${IMGPATH}${detail.qImgPath4}" alt="첨부이미지">
                            <input type='hidden' name='qImg4' value='${detail.qImg4}'>
                            <a href="javascript:" class="file-delete" onclick='deleteFile(this);'>
                                <img src="${CTX}/images/${DEVICE}/common/ico_delete_fileimg.png" alt="첨부이미지 제거">
                            </a>
                        </div>
                    </div>
                    </c:if>
                </div>
                <div class="col col-12">
					<div class="helper-box">
						<div class="helper-box-inner inset">
							<ul>
								<li>사진은 정면, 좌측, 우측, 후면 사진을 등록하시면 좀더 상세한 맞춤 상담이 가능하십니다.</li>
								<li>첨부파일은 이미지 형태의 JPG, JPEG, PNG, GIF, BMP 형태로 총 40MB내에서 등록 가능합니다.</li>
								<li>첨부사진은 관리자 검수를 통해서만 노출이 됩니다.</li>
							</ul>
						</div>
					</div>
				</div>

                <p class="tit">답변 알림 받기</p>
                <div class="row">
                    <div class="col col-12">
                        <div class="form-control" style="display: inline-block;">
                            <span class="checkbox">
                                <input type="checkbox" class="check" name="chkAlarmYn" id="chkAlarmYn" value="N" <c:if test="${detail.alarmYn eq 'Y'}">checked</c:if>/>
                                <label for="chkAlarmYn" class="lbl"></label>
                            </span>
                            <input type="text" name="qEmail" id="qEmail" class="input" placeholder="이메일주소" 
                            		value="<c:choose><c:when test="${detail.qEmail ne null && detail.qEmail ne ''}">${detail.qEmail}</c:when><c:otherwise>${USERINFO.email}</c:otherwise></c:choose>" 
                            		style="width: auto;display: inline-block;">
                            <input type="hidden" name="alarmYn" id="alarmYn" value="N"/>
                        </div>
                    </div>
                </div>
            </div>

            <div class="btn-box confirm">
                <a href="${CTX}/style/counselList.do" class="btn"><span class="txt">취소</span></a>
                <c:choose>
					<c:when test="${VO.statusFlag eq 'I'}">
	                <a href="javascript:" class="btn black" onclick="goSave('상담신청');"><span class="txt">상담신청</span></a>
	                </c:when>
	                <c:otherwise>		
					<a href="javascript:" class="btn black" onclick="goSave('수정');"><span class="txt">수정</span></a>
					</c:otherwise>
				</c:choose>
            </div>
        </div>
	</div>
</form>

<script src="${CTX}/js/vendor/jquery.ui.widget.js"></script>
<script src="${CTX}/js/jquery.iframe-transport.js"></script>
<script src="${CTX}/js/jquery.fileupload.js"></script>

<script>
$(document).ready(function() {
    $('#upFile').fileupload({
        url : '${CTX}/style/counselFileupload.do', 
        dataType: 'json',
        replaceFileInput: false,
        add: function(e, data){
        	var cnt = $("#filebox > li").length;
        	if (cnt ==4 ) {
        		alert("이미지는 최대 4개까지 첨부 할 수 있습니다.");
        		return;
        	}
        	
        	var uploadFile = data.files[0];
            var isValid = true;
            if (!(/png|jpe?g|gif/i).test(uploadFile.name)) {
                alert('png, jpg, gif 만 첨부가 가능합니다');
                isValid = false;
            } else if (uploadFile.size > 10000000) { // 10mb
                alert('파일 용량은 10메가를 초과할 수 없습니다.');
                isValid = false;
            }
            if (isValid) {
                data.submit();              
            } else {
            	$('#upFile').val('');
            }
        }, progressall: function(e,data) {
        	/*
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .bar').css(
                'width',
                progress + '%'
            );
          */
        }, beforeSend: function() {
        	$("#loadingImg").show();
        }, done: function (e, data) {
            var code = data.result.code;
            var msg = data.result.msg;
            var files = data.result.files;
            var ofiles = data.result.ofiles; //실제파일경로
            if(code == '1') {
            	$("#loadingImg").hide();
                //alert(msg);
                for (i=0; i <files.length; i++) {
                	appendFile("${IMGPATH}"+files[i], ofiles[i]);
                }
            } else {
                alert(code + ' : ' + msg);
            } 
            $('#upFile').val('');
        }, fail: function(e, data){
            // data.errorThrown
            // data.textStatus;
            // data.jqXHR;
            alert('서버와 통신 중 문제가 발생했습니다');
            foo = data;
            $('#upFile').val('');
        }
    });
    
    // 이미지 추가
    function appendFile(filepath, originpath) {
    	var html = "";
    	html+="<div class='col col-3'> ";
    	html+="		<div class='fileimg-box'> ";
    	html+="			<img src='"+filepath+"' alt='첨부이미지'> ";
    	html+="			<input type='hidden' name='qImg' value='"+originpath+"'> ";
    	html+="			<a href='javascript:' class='file-delete' onclick='deleteFile(this);'> ";
    	html+="				<img src='${CTX}/images/${DEVICE}/common/ico_delete_fileimg.png' alt='첨부이미지 제거'> ";
    	html+="			</a> ";
    	html+="		</div> ";
    	html+="</div> ";
    	
    	$("#filebox").append(html);
    }
    
}); 
	
</script>

</body>
</html>