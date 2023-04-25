
function check(activeProfilesCode) {
    if(activeProfilesCode == "LIVE") {
        var currentUrl = window.location.href;
        
          var pattern = /m\/main\.do/;
        
        if (pattern.test(currentUrl)) {
            if (window.navigator.userAgent.indexOf("app_") == -1) {
                alert("앱으로 접근해 주세요");
                window.location.href = "https://play.google.com/store/apps/details?id=kr.co.mygb&hl=ko";
            }
        }
    }
}

