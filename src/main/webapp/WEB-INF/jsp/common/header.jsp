<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8"/>
    <title>${pageTitle != null ? pageTitle : 'eGov 3.10 게시판'}</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/app.css'/>"/>
    <script src="<c:url value='/resources/js/jquery-3.6.0.min.js'/>"></script>
</head>
<body>
<header class="topbar">
    <div class="brand"><a href="<c:url value='/board/list'/>">eGov 3.10 게시판</a></div>
    <nav class="auth">
        <sec:authorize access="isAuthenticated()">
            <span class="user"><sec:authentication property="name"/> 님</span>
            <form action="<c:url value='/member/logout'/>" method="post" style="display:inline">
                <button type="submit" class="link">로그아웃</button>
            </form>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <a href="<c:url value='/member/login'/>">로그인</a>
            <a href="<c:url value='/member/join'/>">회원가입</a>
        </sec:authorize>
    </nav>
</header>
<main class="container">
