<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="로그인"/>
<jsp:include page="../common/header.jsp"/>

<h2>로그인</h2>
<c:if test="${not empty loginError}">
    <p class="error">${loginError}</p>
</c:if>

<form id="loginForm" action="<c:url value='/member/login/process'/>" method="post">
    <label>아이디
        <input type="text" name="memberId" required autocomplete="username"/>
    </label>
    <label>비밀번호
        <input type="password" name="password" required autocomplete="current-password"/>
    </label>
    <button type="submit">로그인</button>
</form>
<p><a href="<c:url value='/member/join'/>">회원가입</a></p>

<jsp:include page="../common/footer.jsp"/>
