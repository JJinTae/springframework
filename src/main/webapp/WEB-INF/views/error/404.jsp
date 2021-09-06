<%@ page contentType="text/html; charset=UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="card m-2">
	<div class="card-header">
		요청 URL 문제
	</div>
	<div class="card-body">
		<p>요청 URL이 잘못되었습니다.</p>
		<a class="btn btn-danger btn-sm" href="${pageContext.request.contextPath }/">홈으로가기</a>
	</div>
</div>


<%@ include file="/WEB-INF/views/common/footer.jsp" %>
