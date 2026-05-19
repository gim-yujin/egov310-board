<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="${mode == 'edit' ? '게시글 수정' : '새 글 작성'}"/>
<jsp:include page="../common/header.jsp"/>

<h2>${pageTitle}</h2>

<form id="boardForm" method="post"
      action="${mode == 'edit'
                ? pageContext.request.contextPath.concat('/board/form/').concat(board.boardNo)
                : pageContext.request.contextPath.concat('/board/form')}">
    <label>제목
        <input type="text" name="title" maxlength="200" value="${board.title}" required/>
    </label>
    <label>내용
        <textarea name="content" rows="10" required>${board.content}</textarea>
    </label>
    <button type="submit">${mode == 'edit' ? '수정' : '등록'}</button>
    <a class="btn" href="<c:url value='/board/list'/>">취소</a>
</form>

<script src="<c:url value='/resources/js/app.js'/>"></script>
<script>egovApp.bindBoardForm();</script>

<jsp:include page="../common/footer.jsp"/>
