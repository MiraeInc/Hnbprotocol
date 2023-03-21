<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="CTX" value="${pageContext.request.contextPath}"/>
<c:set var="DEVICE" value="${fn:replace(pageContext.request.contextPath , '/', '')}"/>
<!DOCTYPE>
<html class="no-js" lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge">
	<meta name="description" content="'">
	<meta name="keywords" content="">
	<meta name="author" content="">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<c:if test="${DEVICE eq 'm'}">
	<meta name="format-detection" content="telephone=no, address=no, email=no">
	</c:if>
	<title>[면역공방]</title>
	<link rel="apple-touch-icon" href="${CTX}/images/favicon-152.png">
	<link rel="shortcut icon" href="${CTX}/images/favicon.ico">

	<!-- Stylesheets -->
	<link rel="stylesheet" href="${CTX}/css/lightslider.min.css">
	<link rel="stylesheet" href="${CTX}/css/remodal.min.css">
	<link rel="stylesheet" href="${CTX}/css/remodal-default-theme.css">
	<c:if test="${DEVICE eq 'w'}">
	<link rel="stylesheet" href="${CTX}/css/normalize.css">
	<link rel="stylesheet" href="${CTX}/css/style.css">
	<link rel="stylesheet" href="${CTX}/css/template_2018.css">
	<link rel="stylesheet" href="${CTX}/css/jquery-ui.min.css">
	<link rel="stylesheet" href="${CTX}/css/${DEVICE}/layout_2018.css">
	<link rel="stylesheet" href="${CTX}/css/${DEVICE}/contents_2018.css">
	</c:if>
	<c:if test="${DEVICE eq 'm'}">
	<link rel="stylesheet" href="${CTX}/css/${DEVICE}/normalize.css">
	<link rel="stylesheet" href="${CTX}/css/${DEVICE}/common_2018.css">
	<link rel="stylesheet" href="${CTX}/css/${DEVICE}/contents_2018.css">
	</c:if>
	
	<script src="${CTX}/js/modernizr-2.8.3.min.js"></script>
</head>
<body>
	<%-- PC --%>
	<c:if test="${DEVICE eq 'w'}">
	
	<div class="error">
		<style type="text/css">
			html,body{height: 100%;min-width: auto;}
		</style>
		<div class="error-cont">
			<div class="err-box">
				<strong>ERROR</strong>
				<h1>요청하신 페이지를 찾을 수 없습니다.</h1>
				<p>현재 페이지가 존재하지 않거나, 변경 혹은 삭제되어 페이지를 찾을 수 없습니다.<br>서비스 이용에 불편을 드려 죄송합니다.</p>
			</div>
			<div class="err-btn">
				<a href="javascript:history.back();" class="btn-pc btn-err1"><span>이전 페이지</span></a>
				<a href="${CTX}/main.do" class="btn-pc btn-err2"><span>메인으로</span></a>
			</div>
		</div>
	</div>
	</c:if>
<%-- //PC --%>

<%-- MOBILE --%>
	<c:if test="${DEVICE eq 'm'}">
	<div class="error">
		<style type="text/css">
		html,body{height: 100%;min-width: auto;}
		</style>
		<div class="error-cont">
			<div class="err-box">
				<strong>ERROR</strong>
				<h1>요청하신 페이지를 찾을 수 없습니다.</h1>
				<p>현재 페이지가 존재하지 않거나, 변경 혹은 삭제되어 페이지를 찾을 수 없습니다.<br>서비스 이용에 불편을 드려 죄송합니다.</p>
			</div>
			<div class="err-btn">
				<a href="javascript:history.back();" class="btn btn-err1"><span>이전 페이지</span></a>
				<a href="${CTX}/main.do" class="btn btn-err2"><span>메인으로</span></a>
			</div>
		</div>
	</div>
	</c:if>
<%-- //MOBILE --%>
</body>
</html>