<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" value="게시글 목록"/>
<jsp:include page="../common/header.jsp"/>

<h2>게시글 목록</h2>

<form method="get" action="<c:url value='/board/list'/>" class="searchbar">
    <input type="text" name="searchKeyword" value="${searchVO.searchKeyword}" placeholder="제목/내용 검색"/>
    <button type="submit">검색</button>
    <a class="btn" href="<c:url value='/board/form'/>">새 글</a>
</form>

<table class="board-list">
    <thead>
        <tr><th>번호</th><th>제목</th><th>작성자</th><th>조회</th><th>작성일</th></tr>
    </thead>
    <tbody>
        <c:choose>
            <c:when test="${empty boardList}">
                <tr><td colspan="5" class="empty">게시글이 없습니다.</td></tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="b" items="${boardList}">
                    <tr>
                        <td>${b.boardNo}</td>
                        <td>
                            <a href="<c:url value='/board/detail/${b.boardNo}'/>">
                                <c:out value="${b.title}"/>
                            </a>
                        </td>
                        <td><c:out value="${b.writer}"/></td>
                        <td>${b.viewCnt}</td>
                        <td><fmt:formatDate value="${b.regDt}" pattern="yyyy-MM-dd HH:mm"/></td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </tbody>
</table>

<nav class="paging">
    <c:if test="${paginationInfo.hasPreviousPage}">
        <a href="<c:url value='/board/list?currentPageNo=${paginationInfo.firstPageNoOnPageList - 1}&searchKeyword=${searchVO.searchKeyword}'/>">이전</a>
    </c:if>
    <c:forEach var="i" begin="${paginationInfo.firstPageNoOnPageList}" end="${paginationInfo.lastPageNoOnPageList}">
        <c:choose>
            <c:when test="${i == paginationInfo.currentPageNo}">
                <strong>${i}</strong>
            </c:when>
            <c:otherwise>
                <a href="<c:url value='/board/list?currentPageNo=${i}&searchKeyword=${searchVO.searchKeyword}'/>">${i}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <c:if test="${paginationInfo.hasNextPage}">
        <a href="<c:url value='/board/list?currentPageNo=${paginationInfo.lastPageNoOnPageList + 1}&searchKeyword=${searchVO.searchKeyword}'/>">다음</a>
    </c:if>
</nav>

<jsp:include page="../common/footer.jsp"/>
