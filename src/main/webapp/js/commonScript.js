// 다음 API 주소
function execDaumPostcode(callback) {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 도로명 조합형 주소 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
               extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }
            // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
            if(fullRoadAddr !== ''){
                fullRoadAddr += extraRoadAddr;
            }
            
            // 콜백 함수 
			callback(data.zonecode, data.postcode, fullRoadAddr, data.jibunAddress);

        }
    }).open();
}

// 새창 센터띄우기 scroll yes
var winRef;
function owScrollYesCenter(xurl, tar, wid, hei){
	var winl = (screen.width - wid) / 2;
	var wint = (screen.height - hei) / 2;
	set = 'width='+wid+',height='+hei+',top='+wint+',left='+winl+', toolbar=no,location=no,directory=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no';
	
	if(winRef == null){
		winRef = window.open(xurl,tar,set);
	}else{
		if (winRef.closed == false) {
			winRef.focus();
		}else{
			winRef = window.open(xurl,tar,set);
		}
	}
};

// 새창 submit 
function submitPopup(frm, url, formName, wid, hei, top, left){
	var winl;
	var wint;
	
	if (typeof top == "undefined" && typeof left == "undefined"){
		winl = (screen.width - wid) / 2;
		wint = (screen.height - hei) / 2;
	}else{
		wint = top;
		winl = left;
	}
	
	newOpen = window.open("" ,formName,'width='+wid+',height='+hei+',top='+wint+',left='+winl+', toolbar=no,location=no,directory=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=no');
	frm.action = url; 
	frm.method = "post";
	frm.target = formName;
	frm.submit();
}

/**
 * Input 컴포넌트에 숫자만 입력가능하도록 처리
 * 
 * @param selector 
 * @returns	jQuery Object
 */
function createNumeric(selector) {
//	$(selector).attr("disabled",false); 
	jQuery(selector).css('imeMode','disabled').keypress(function(event) {
		if(event.which && (event.which < 48 || event.which > 57) ) {
			event.preventDefault();
		}
	}).keyup(function(){
		if( jQuery(this).val() != null && jQuery(this).val() != '' ) {
			jQuery(this).val( jQuery(this).val().replace(/[^0-9,.]/g, '') );
		}
	});
}


/**
 * Input 컴포넌트에 소수점까지 입력가능
 * 
 * @param selector 
 * @returns	jQuery Object
 */
function createDouble(selector) {
	$(selector).attr("disabled",false); 
	jQuery(selector).css('imeMode','disabled').keypress(function(event) {
		if(event.which && (event.which < 46 || event.which > 57 || event.which ==47) ) {
			event.preventDefault();
		}
	}).keyup(function(){
		if( jQuery(this).val() != null && jQuery(this).val() != '' ) {
			jQuery(this).val( jQuery(this).val().replace(/[^0-9,.]/g, '') );
		}
	});
} 

/**
 * 일반전화 유효성 체크
 * 
 * @param $this    
 * @returns  true/false
*/
function telCheck($this){
	var regExp = /^\d{2,3}-\d{3,4}-\d{4}$/;
	if(regExp.test($this) == false) {  
	    return false;  
	} else {  
		return true;
	}
}

/**
 * 휴대폰 유효성 체크
 * 
 * @param $this    
 * @returns  true/false
*/
function phoneCheck($this){
	var regExp = /^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/;
	if(regExp.test($this) == false) {  
	    return false;  
	} else {  
		return true;
	}
}


/**
 * 이메일 유효성 체크
 * 
 * @param $this    
 * @returns  true/false
 */

function emailCheck($this){
	var regex=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
	
	if(regex.test($this) == false) {  
	    return false;  
	} else {  
		return true;
	}
}


/**
 * 비밀번호 유효성 체크 
 * 
 * @param id  (8~15자리)
 * @returns  true/false
 */

function checkPwd(id){
	var alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	var number = "1234567890";
	var sChar = "-_=+\|()*&^%$#@!~`?></;,.:'";
	var sChar_Count = 0;
	var alphaCheck = false;
	var numberCheck = false;
	var pw = $("#"+id).val();
	if($.trim(pw) != ""){
		
		if(8 <= pw.length && pw.length <= 15){
			for(var i=0; i<pw.length; i++){
				 if(sChar.indexOf(pw.charAt(i)) != -1){	sChar_Count++; }
				 if(alpha.indexOf(pw.charAt(i)) != -1){ alphaCheck = true; }
				 if(number.indexOf(pw.charAt(i)) != -1){ numberCheck = true; }
			}
			var flag = 0;
			if(sChar_Count > 0 && alphaCheck ==true && numberCheck ==true){ 
				flag = 0;
			}else if(sChar_Count > 0 && alphaCheck ==true && numberCheck !=true ){
				flag = 0;
			}else if(sChar_Count > 0 && alphaCheck !=true && numberCheck ==true ){
				flag = 0;
			}else if(sChar_Count < 1 && alphaCheck ==true && numberCheck ==true ){
				flag = 0;
			}else{
				flag = 1;
			}
			if(flag>0){ 
				 return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	} 
}

/**
 * 첨부파일 확장자 , 용량 체크
 * 
 * @param $this , type (image , document)
 * @returns
 */

function fileCheck($this, type){     
	var agt = navigator.userAgent.toLowerCase();
	var fileName = $($this).val();
	var maxSize = 10485760;		//10MB
	
	if(type != "video"){
		if (agt.indexOf("msie") != -1) {
			 if(fileName != ""){
				var myFSO = new ActiveXObject("Scripting.FileSystemObject");
				var filepath = $($this).val();
				var thefile = myFSO.getFile(filepath);
				var size = thefile.size;
				if(size > maxSize){
					 $($this).replaceWith( $this = $($this).clone( true ) );   
					 return callbackFileCheck("1");	//용량 초과시
				 }       
			 }
		}else{
			if(fileName != ""){
				var upfiles = document.getElementById($this.id);
				if (upfiles.files.length > 0) {
					var size = document.getElementById($this.id).files[0].size; 
					 if(size> maxSize){
						 $($this).val(""); 
						 return callbackFileCheck("1");	//용량 초과시
					 }
				}
			}
		}
	}
	
	var fileFormat = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
	if(fileName != ""){
		if(type =="image"){																							
			if(fileFormat !="gif" && fileFormat !="jpg" && fileFormat !="jpeg" && fileFormat !="png"){ 
				if (agt.indexOf("msie") != -1) {
					$($this).replaceWith( $this = $($this).clone( true ) );  //첨부파일 삭제
				}else{ 
					$($this).val("");
				}  
				return callbackFileCheck("2");		//이미지 파일 첨부
			} 
		 }else if(type =="document"){																							
			 if(fileFormat !="hwp" && fileFormat !="xls" && fileFormat !="xlsx" && fileFormat !="ppt" && fileFormat !="pptx"  && fileFormat !="doc"  && fileFormat !="docx"  && fileFormat !="txt" && fileFormat !="zip"  ){ 
				 if (agt.indexOf("msie") != -1) {
					 $($this).replaceWith( $this = $($this).clone( true ) );  //첨부파일 삭제
				 }else{ 
					 $($this).val("");
				 }  
				 return callbackFileCheck("3");	//문서파일 첨부
			 } 
		 }else if(type =="upload"){																							
			 if(fileFormat !="xlsx" ){ 
				 if (agt.indexOf("msie") != -1) {
					 $($this).replaceWith( $this = $($this).clone( true ) );  //첨부파일 삭제
				 }else{ 
					 $($this).val("");
				 }  
				 return callbackFileCheck("4");	//엑셀파일 첨부
			 } 
		 }else if(type == "video"){
			 if(fileFormat !="mp4" && fileFormat !="avi" && fileFormat !="wmv" && fileFormat !="asf" && fileFormat !="flv" && fileFormat !="mov" && fileFormat !="mpeg" && fileFormat !="mpeg1" && fileFormat !="mpeg2" && fileFormat !="mpeg4"){
				 if (agt.indexOf("msie") != -1) {
					 $($this).replaceWith( $this = $($this).clone( true ) );  //첨부파일 삭제
				 }else{ 
					 $($this).val("");
				 }  
				 return callbackFileCheck("5");	//동영상파일 첨부
			 }
		 }
	 }
}


/**
 * 천단위 콤마
 * 
 * @param str
 * @returns
 */
function comma(str) {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

/**
 * 천단위 콤마 제거
 * 
 * @param str
 * @returns
 */
function uncomma(str) {
    str = String(str);
    return str.replace(/[^\d]+/g, '');
}

//아이디 유효성 체크
function checkId(str){
	 var reg_IdPwd = /^.*(?=.{6,15})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;

	 if(!reg_IdPwd.test(str)){
	  	return false;
	 }
	 return true;
}

// 공백 체크
function checkBlank(str){
	var blank_pattern = /[\s]/g;
	
	if(blank_pattern.test(str)){
		return false;
	}
	return true;
}

// 좌, 우 공백 제거
function trimVal(str){
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

// 생년월일 유효성 체크
function checkBirthDate(str){
	var y_sub = parseInt(str.substring(0, 2)); 
	var y = parseInt(str.substr(0, 4), 10); 
	var m = parseInt(str.substr(4, 2), 10); 
	var d = parseInt(str.substr(6, 2), 10); 
	var dt = new Date(y, m-1, d);
	
	if(str.length < 8 ){
		return false;
	}else if(y_sub != 19 && y_sub != 20){
		return false;
	}else if(dt.getDate() != d) {
		return false;
	}else if(dt.getMonth()+1 != m) { 
		return false;
	}else if(dt.getFullYear() != y) { 
		return false;
	}else { 
		return true;
	} 
}

// 날짜 형식 YYYY-MM-DD로 반환
function getFormatDate(date){
	var dt = new Date(date);
	var currentDate="";
	
	var year = Number(dt.getFullYear());
	var month=Number(dt.getMonth() + 1);
	var day = Number(dt.getDate());
	
	if(month <10){
		month = "0"+month;
	}
	if(day <10){
		day = "0"+day;
	}
	
	currentDate = year+"-"+month+"-"+day;

	
	return currentDate;
}

// 1주일, 1개월, 3개월 날짜 세팅
function setDate(val){
	var settingDate = new Date();
	$("#schEndDt").val(getFormatDate(settingDate));
	
	switch(val){
	case "1" :	// 오늘
		$("#schStartDt").val(getFormatDate(settingDate));
		break;
	case "2" :	// 1주일	
		settingDate.setDate(settingDate.getDate()-7);
		$("#schStartDt").val(getFormatDate(settingDate));
		break;
	case "3" :	// 1개월
		settingDate.setMonth(settingDate.getMonth()-1);
		$("#schStartDt").val(getFormatDate(settingDate));
		break;
	case "4" :	// 3개월
		settingDate.setMonth(settingDate.getMonth()-3);
		$("#schStartDt").val(getFormatDate(settingDate));
		break;
	case "5" :	// 6개월
		settingDate.setMonth(settingDate.getMonth()-6);
		$("#schStartDt").val(getFormatDate(settingDate));
		break;
	case "6" :	// 1년
		settingDate.setFullYear(settingDate.getFullYear()-1);
		$("#schStartDt").val(getFormatDate(settingDate));
		break;
	case "7" :	// 2년
		settingDate.setFullYear(settingDate.getFullYear()-2);
		$("#schStartDt").val(getFormatDate(settingDate));
		break;	
	}
}

// 쿠키 저장하기
function setCookie(name, value, expiredays){
	var today = new Date();
	today.setDate(today.getDate() + expiredays);
	document.cookie = name + "=" + escape(value) + "; path=/; expires=" + today.toGMTString() + ";";
}

// 쿠키 가져오기
function getCookie( name ) {  
   var nameOfCookie = name + "=";  
   var x = 0;  
   while ( x <= document.cookie.length )  
   {  
       var y = (x+nameOfCookie.length);  
       if ( document.cookie.substring( x, y ) == nameOfCookie ) {  
           if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )  
               endOfCookie = document.cookie.length;  
           return unescape( document.cookie.substring( y, endOfCookie ) );  
       }  
       x = document.cookie.indexOf( " ", x ) + 1;  
       if ( x == 0 )  
           break;  
   }  
   return "";  
}

// XSS 
function XSSfilter(content) {  
	return content.replace(/\<|\>|\"|\'|\%|\;|\(|\)|\&|\+|\-/g,"");
}
