<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/system/taglib.jsp"/>
<!DOCTYPE>
<html>
<head>
<meta name="decorator" content="mobile.main"/>	

<!-- <script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?clientId=V8QDuMofZK1bHMqxt7mf"></script> -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d3899f0215997e8cbbc80d3c6ee36127"></script>

<script type="text/javascript">
$(document).ready(function(){
	// 네이버 지도 API
	/* var position = new naver.maps.LatLng(37.4980680, 127.0293830);
	
	var map = new naver.maps.Map('map',{
	    center : position,
	    zoom : 13
	});
	
	// 기본 마커
	var marker = new naver.maps.Marker({
	    position : position,
	    map : map
	}); */

	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new daum.maps.LatLng(37.4980680, 127.0293830), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

	var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
	
	// 마커가 표시될 위치입니다 
	var markerPosition  = new daum.maps.LatLng(37.4980680, 127.0293830); 
	
	// 마커를 생성합니다
	var marker = new daum.maps.Marker({
	    position: markerPosition
	});
	
	// 마커가 지도 위에 표시되도록 설정합니다
	marker.setMap(map);

});
</script>

</head>
<body>
    <div class="content comm-etc etc-contactus">
    	<div class="page-body">
    	
        	<h2>Contact Us</h2>
        	
        	<div class="map" id="map" style="width:100%; height:400px;">
            	
           	</div>

			<table class="table type-data">
                <colgroup>
                    <col style="width: 35%" />
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th>회사명</th>
                        <td>(주)엠와이지비</td>
                    </tr>
                    <tr>
                        <th>대표이사</th>
                        <td>문준현</td>
                    </tr>
                    <tr>
                        <th>소재지</th>
                        <td>서울특별시 강남구 학동로 165 (논현동, 마일스디오빌) 206호</td>
                    </tr>
                    <tr>
                        <th>전화번호</th>
                        <td>02-544-1191</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>