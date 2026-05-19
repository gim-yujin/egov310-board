<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" value="게시글 상세"/>
<jsp:include page="../common/header.jsp"/>

<article class="board-detail">
    <h2><c:out value="${board.title}"/></h2>
    <p class="meta">
        작성자 <strong><c:out value="${board.writer}"/></strong> ·
        조회 ${board.viewCnt} ·
        <fmt:formatDate value="${board.regDt}" pattern="yyyy-MM-dd HH:mm"/>
        <c:if test="${board.updDt != null}">
            (수정 <fmt:formatDate value="${board.updDt}" pattern="yyyy-MM-dd HH:mm"/>)
        </c:if>
    </p>
    <pre class="content"><c:out value="${board.content}"/></pre>
</article>

<div class="actions">
    <a class="btn" href="<c:url value='/board/list'/>">목록</a>
    <c:if test="${currentUser == board.writer}">
        <a class="btn" href="<c:url value='/board/form/${board.boardNo}'/>">수정</a>
        <form id="deleteForm" method="post" action="<c:url value='/board/delete/${board.boardNo}'/>" style="display:inline">
            <button type="submit" class="btn danger" id="btnDelete">삭제</button>
        </form>
    </c:if>
</div>

<script src="<c:url value='/resources/js/app.js'/>"></script>
<script>egovApp.bindDeleteConfirm();</script>

<jsp:include page="../common/footer.jsp"/>
