if("<spring:message code='server.status'/>" == "LIVE") {
    // 현재 페이지 주소 가져오기
    var currentUrl = window.location.href;
    
    // 페이지 주소와 일치하는 패턴 찾기
      var pattern = /m\/main\.do/;
    
    if (pattern.test(currentUrl)) { // 주소가 일치하는 경우
        if (window.navigator.userAgent.indexOf("app_") == -1) {
            alert("앱으로 접근해 주세요");
            window.location.href = "https://play.google.com/store/apps/details?id=kr.co.mygb&hl=ko";
        }
    }
}