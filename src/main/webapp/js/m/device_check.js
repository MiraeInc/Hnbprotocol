// 앱이 아닌 web 으로 접근할시 경고메세지 출력 및 구글스토어 링크로 이동
// function deviceCheck(activeProfilesCode) {
//     if(activeProfilesCode == "LIVE") {
//         var currentUrl = window.location.href;
        
//           var pattern = /m\/main\.do/;
        
//         if (pattern.test(currentUrl)) {
//             if (window.navigator.userAgent.indexOf("app_") == -1) {
//                 alert("앱으로 접근해 주세요");
//                 window.location.href = "https://play.google.com/store/apps/details?id=kr.co.mygb&hl=ko";
//             }
//         }
//     }
// }

// 아이폰일 경우 sns 간편로그인 숨김처리
// function hideSnsSimpleLinkOniOS() {
//     if (window.navigator.userAgent.indexOf("app_ios") !== -1) {
//         $(".sns_simple_link").css("display", "none");
//     }
// }

