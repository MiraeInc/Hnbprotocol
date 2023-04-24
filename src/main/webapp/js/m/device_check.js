// 현재 페이지 주소 가져오기
var currentUrl = window.location.href;

// 페이지 주소와 일치하는 패턴 찾기
// var pattern = /http:\/\/hnb\.miraeint\.co\.kr:8080\/m\/mypage\/order\/(main|myOrderList|token\/tokenList)\.do/;

var pattern = /m\/mypage\/(order|token)\/(main|myOrderList|tokenList)\.do/;


if (pattern.test(currentUrl)) { // 주소가 일치하는 경우
    // 디바이스가 웹 브라우저에서 접속한 경우
    if (window.navigator.userAgent.indexOf("Mozilla") !== -1) {
        // alert 메세지
        alert("앱으로 접근해 주세요");
        // 링크 이동
        window.location.href = "https://play.google.com/store/apps/details?id=kr.co.mygb&hl=ko";
    } else { // 디바이스가 앱에서 접속한 경우
        // 원래 페이지 링크로 이동
        // 예시: http://hnb.miraeint.co.kr:8080/m/mypage/order/main.do -> hnb://mypage/order/main.do
        
  //지워도됨
  // var appUrl = currentUrl.replace(/http:\/\/hnb\.miraeint\.co\.kr:8080\/m\//, "hnb://");

  var appUrl = currentUrl.replace(/m\//, "hnb://");

        window.location.href = appUrl;
    }
}