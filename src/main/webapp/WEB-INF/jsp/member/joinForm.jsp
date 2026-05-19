<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="회원가입"/>
<jsp:include page="../common/header.jsp"/>

<h2>회원가입</h2>
<c:if test="${not empty joinError}">
    <p class="error">${joinError}</p>
</c:if>

<form id="joinForm" method="post" action="<c:url value='/member/join'/>">
    <label>아이디
        <input type="text" name="memberId" id="memberId" value="${memberVO.memberId}" required/>
        <button type="button" id="btnIdCheck">중복확인</button>
        <span id="idCheckResult"></span>
    </label>
    <label>비밀번호
        <input type="password" name="password" id="password" required minlength="6"/>
    </label>
    <label>비밀번호 확인
        <input type="password" id="passwordConfirm" required minlength="6"/>
    </label>
    <label>이름
        <input type="text" name="memberName" value="${memberVO.memberName}" required/>
    </label>
    <label>이메일
        <input type="email" name="email" value="${memberVO.email}" required/>
    </label>
    <button type="submit">가입</button>
</form>

<script src="<c:url value='/resources/js/app.js'/>"></script>
<script>
    egovApp.bindJoinForm('<c:url value="/member/idcheck"/>');
</script>

<jsp:include page="../common/footer.jsp"/>
